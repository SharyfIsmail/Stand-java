package stand.semikron.tx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stand.can.candata.DataFromCan;

public class TxPDO3 implements DataFromCan {
	// [0,1] byte
	private short motorSpeed; // signed int16 = short
	// [2,3] byte
	private short mechanicPower; // signed int16 = short
	// [4,5]byte
	private short linkPowerDC; // signed int16 = short
	// [6]byte
	private byte messageCount; // {0-3}bit
	private String motorSpeedVAL;// {4-5}bit
	private String mechanicPowerVAL;// {6-7}bit
	// [7]byte
	private String linkPowerDCVAL; // {0-1}bit

	public TxPDO3() {
		super();
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		motorSpeed = ByteBuffer.wrap(data, 0, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();

		mechanicPower = ByteBuffer.wrap(data, 2, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
		linkPowerDC = ByteBuffer.wrap(data, 4, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();

		messageCount = (byte) (Integer.reverseBytes(data[6] << 28) >>> 4);
		mechanicPowerVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[6] << 26) >>> 6);
		motorSpeedVAL = VAL.getLossesVALByCode(Integer.reverseBytes(data[6] << 24) >>> 6);

		linkPowerDCVAL = VAL.getLossesVALByCode(data[7]);
	}

	public int getLinkPowerDC() {
		return ((short) ( linkPowerDC - 32768)) * 10;
	}

	public String getLinkPowerDCVAL() {
		return linkPowerDCVAL;
	}

	public short getMotorSpeed() {
		return (short) (motorSpeed - 32768);
	}

	public int getMechanicPower() {
		return ((short) (mechanicPower - 32768)) * 10;
	}

	public byte getMessageCount() {
		return messageCount;
	}

	public String getMotorSpeedVAL() {
		return motorSpeedVAL;
	}

	public String getMechanicPowerVAL() {
		return mechanicPowerVAL;
	}

}
