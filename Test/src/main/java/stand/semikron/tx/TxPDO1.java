package stand.semikron.tx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stand.can.candata.DataFromCan;

public class TxPDO1 implements DataFromCan {
	// [0,1]byte
	private int inverterLosses; // {0-11}bit signed int16 = short
	private String inverterState;// {12-15}bit
	// [2,3]byte
	private int motorLosses; // {0-11}bit signed int16 = short
	private byte messageCount; // {12-15}bit
	// [4,5]byte
	private String lastError; // {0-9}bit signed int16 = short
	private String inverterLossesVAL; // {10-11}bit
	private String motorLossesVAL;// {12-13}bit
	private String digitalOutput1State;// {14}bit
	private String digitalOutput2State;// {15}bit

	// [6,7]byte
	private String causingError; // {0-9}bit signed int16 = short
	private String digitalInput1State;// {10}bit
	private String digitalInput2State;// {11}bit
	private String limitationMode;// {12}bit
	private String ascState;// {13}bit
	private String activeDischargeState;// {14}bit
	private String spoInput;// {15}bit

	public TxPDO1() {
		super();
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {

			byte[] b2 = new byte[2];
			System.arraycopy(data, 0, b2, 0, b2.length);
			inverterLosses = getLosses(b2);
			System.arraycopy(data, 2, b2, 0, b2.length);
			motorLosses = getLosses(b2);
			System.arraycopy(data, 4, b2, 0, b2.length);
			lastError = getError(b2);
			System.arraycopy(data, 6, b2, 0, b2.length);
			valueFromLast2Bytes(b2);

			inverterState = InverterState.getStateByCode(Integer.reverseBytes(data[1] << 24) >>> 4);
			messageCount = (byte) (Integer.reverseBytes(data[3] << 24) >>> 4);

			inverterLossesVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[5] << 28) >>> 6);
			motorLossesVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[5] << 26) >>> 6);
			valueFromDigitalOutput(data[5]);
			System.out.println(
					"0100 0000 / 1000 0000 Digital Output 1/2 State ?: " + Integer.toBinaryString(data[5]) + " ");
			System.out.println("2/3bit ?: " + Integer.toBinaryString(data[3]) + " ");
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

	private int getLosses(byte[] b2) {
		byte lossesZeroByte = b2[0];
		byte lossesFirstByte = (byte) (Integer.reverseBytes(b2[1] << 28) >>> 4);
		byte[] lossesBytes = { lossesZeroByte, lossesFirstByte };
		return ByteBuffer.wrap(lossesBytes, 0, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}

	private String getError(byte[] b2) {
		byte errorZeroByte = b2[0];
		byte errorFirstByte = (byte) (Integer.reverseBytes(b2[1] << 30) >>> 6);
		byte[] errorBytes = { errorZeroByte, errorFirstByte };
		return ErrorCodes.getErrorByCode(ByteBuffer.wrap(errorBytes, 0, 2).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}

	private void valueFromDigitalOutput(byte b) {
		// 0100 0000
		// Digital Output 1 State
		if ((b & 4) != 64) {
			digitalOutput1State = "high";
		} else {
			digitalOutput1State = "low";
		}
		// 1000 0000
		// Digital Output 2 State
		if ((b & 8) != -128) {
			digitalOutput2State = "high";
		} else {
			digitalOutput2State = "low";
		}
	}

	private void valueFromLast2Bytes(byte[] b2) {
		causingError = getError(b2);
		// 0000 0100
		// Digital Input 1 State
		if ((b2[1] & 4) != 0) {
			digitalInput1State = "high";
		} else {
			digitalInput1State = "low";
		}
		// 0000 0100
		// Digital Input 2 State
		if ((b2[1] & 8) != 0) {
			digitalInput2State = "high";
		} else {
			digitalInput2State = "low";
		}
		// 0001 0000
		// Limitation Mode
		if ((b2[1] & 16) != 0) {
			limitationMode = "Asymmetric";
		} else {
			limitationMode = "Symmetric";
		}
		// 0010 0000
		// ASC State
		if ((b2[1] & 32) != 0) {
			ascState = "active";
		} else {
			ascState = "off";
		}
		// 0100 0000
		// Active Discharge State
		if ((b2[1] & 64) != 0) {
			activeDischargeState = "active";
		} else {
			activeDischargeState = "off";
		}
		// 1000 0000
		// Spo Input
		if ((b2[1] & -128) != 0) {
			spoInput = "high (pulse on )";
		} else {
			spoInput = "low (pulse off)";
		}
	}

	public int getInverterLosses() {
		return inverterLosses * 10;
	}

	public String getInverterState() {
		return inverterState;
	}

	public int getMotorLosses() {
		return motorLosses * 10;
	}

	public byte getMessageCount() {
		return messageCount;
	}

	public String getLastError() {
		return lastError;
	}

	public String getInverterLossesVAL() {
		return inverterLossesVAL;
	}

	public String getMotorLossesVAL() {
		return motorLossesVAL;
	}

	public String getDigitalOutput1State() {
		return digitalOutput1State;
	}

	public String getDigitalOutput2State() {
		return digitalOutput2State;
	}

	public String getCausingError() {
		return causingError;
	}

	public String getDigitalInput1State() {
		return digitalInput1State;
	}

	public String getDigitalInput2State() {
		return digitalInput2State;
	}

	public String getLimitationMode() {
		return limitationMode;
	}

	public String getAscState() {
		return ascState;
	}

	public String getActiveDischargeState() {
		return activeDischargeState;
	}

	public String getSpoInput() {
		return spoInput;
	}

}
