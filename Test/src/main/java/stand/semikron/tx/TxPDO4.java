package stand.semikron.tx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stand.can.candata.DataFromCan;
import stand.semikron.rx.MotorControlModeRx;

public class TxPDO4 implements DataFromCan {

	// [0,1]byte
	private int phaseCurrent; // {0-13}bit. signed int16 = short
	private String phaseCurrentVAL; // {14-15}bit
	// [2,3]byte
	private int linkVoltageDC; // {0-13}bit. signed int16 = short
	private String linkVoltageDCVAL; // {14-15}bit
	// [4]byte
	private String controlMode; // {0-3}bit
	private byte messageCount;// {4-7}bit
	// [5]byte
	private String systemWarning; // unsigned byte = byte
	private byte systemWarningByte;

	public TxPDO4() {
		super();
	}

	@Override
	public void parseDataFromCan(byte[] data) {

		byte[] b2 = new byte[2];
		System.arraycopy(data, 0, b2, 0, b2.length);
		phaseCurrent = getValue(b2);
		phaseCurrentVAL = getValueVAL(data[1]);

		System.arraycopy(data, 2, b2, 0, b2.length);
		linkVoltageDC = getValue(b2);
		linkVoltageDCVAL = getValueVAL(data[3]);

		int controlModeCode = (Integer.reverseBytes(data[4] << 28) >>> 4);
		for (MotorControlModeRx motorControlModeRx : MotorControlModeRx.values()) {
			if (controlModeCode == motorControlModeRx.getPriority()) {
				controlMode = motorControlModeRx.getState();
			}
		}
		messageCount = (byte) (Integer.reverseBytes(data[4] << 24) >>> 4);

		systemWarningByte = data[5];
		systemWarning = getSystemWarningMessage(systemWarningByte);
	}

	private int getValue(byte[] b2) {
		byte valueZeroByte = b2[0];
		byte valueFirstByte = (byte) (Integer.reverseBytes(b2[1] << 26) >>> 2);
		byte[] valueBytes = { valueZeroByte, valueFirstByte };
		return ByteBuffer.wrap(valueBytes, 0, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}

	private String getValueVAL(byte b) {
		return VAL.getLossesVALByCode(Integer.reverseBytes(b << 24) >>> 6);
	}

	private String getSystemWarningMessage(byte b) {

		StringBuilder systemWarningMessage = new StringBuilder("");
		final byte CUTBACL_LIMITER_IS_ACTIVE = 1; // 0000 0001
		final byte REFERENCE_LIMITS_CORRECTED = 2; // 0000 0010
		final byte THE_ACTUAL_SPEED_IS_HIGHER = 8; // 0000 1000
		final byte IQ_REDUCED_TO_ZERO = 16; // 0001 0000
		final byte POSITION_SENSOR_DEFAULT_OFFSET = 32; // 0010 0000

		if ((b & CUTBACL_LIMITER_IS_ACTIVE) != 0) {
			systemWarningMessage.append(
					"Cutback limiter is active. The warning is only indicated in enabled state if the limitation is really applied\n");
		}
		if ((b & REFERENCE_LIMITS_CORRECTED) != 0) {
			systemWarningMessage.append("Reference limits corrected");
		}

		if ((b & THE_ACTUAL_SPEED_IS_HIGHER) != 0) {
			systemWarningMessage.append("The actual speed is higher than the reference limits\n");
		}
		if ((b & IQ_REDUCED_TO_ZERO) != 0) {
			systemWarningMessage.append(
					"PSM/IPM Iq reduced to zero. In field ctrl the maximum speed with the actual DC link voltage is reached\n");
		}
		if ((b & POSITION_SENSOR_DEFAULT_OFFSET) != 0) {
			systemWarningMessage.append("Position sensor default offset\n");
		}
		return systemWarningMessage.toString();
	}

	public int getSystemWarningByte() {
		return systemWarningByte;
	}

	public float getPhaseCurrent() {
		return (float) (phaseCurrent * 0.1);
	}

	public String getPhaseCurrentVAL() {
		return phaseCurrentVAL;
	}

	public float getLinkVoltageDC() {
		return (float) (linkVoltageDC * 0.1);
	}

	public String getLinkVoltageDCVAL() {
		return linkVoltageDCVAL;
	}

	public String getControlMode() {
		return controlMode;
	}

	public byte getMessageCount() {
		return messageCount;
	}

	public String getSystemWarning() {
		return systemWarning;
	}
}
