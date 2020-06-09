package stand.semikron.tx;

import stand.can.candata.DataFromCan;
import stand.util.LilEndByteParser;

public class TxPDO5 implements DataFromCan {
	// [0]byte
	private int junctionTempOrHighestDCBtemp; // unsigned char = short
	// [1]byte
	private int motorTemp; // unsigned char = short
	// [2]byte
	private String controlStrategy;// {0-2}bit
	private String motorTempVAL;// {3-4}bit
	private String junctionTempOrHighestDCBtempVAL;// {5-6}bit
	// [3]byte
	private byte messageCount;// {0-3}bit

	@Override
	public void parseDataFromCan(byte[] data) {
		junctionTempOrHighestDCBtemp = LilEndByteParser.unsignedCharToInt(data[0]);
		motorTemp = LilEndByteParser.unsignedCharToInt(data[1]);
		controlStrategy = ControlStrategy.getControlStrategyByCode(Integer.reverseBytes(data[2] << 29) >>> 5);
		motorTempVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[2] << 27) >>> 6);
		junctionTempOrHighestDCBtempVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[2] << 25) >>> 6);
		messageCount = data[3];
	}

	public int getJunctionTempOrHighestDCBtemp() {
		return junctionTempOrHighestDCBtemp - 40;
	}

	public int getMotorTemp() {
		return motorTemp - 40;
	}

	public String getControlStrategy() {
		return controlStrategy;
	}

	public String getMotorTempVAL() {
		return motorTempVAL;
	}

	public String getJunctionTempOrHighestDCBtempVAL() {
		return junctionTempOrHighestDCBtempVAL;
	}

	public byte getMessageCount() {
		return messageCount;
	}

}
