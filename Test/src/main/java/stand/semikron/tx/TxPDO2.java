package stand.semikron.tx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stand.can.candata.DataFromCan;

public class TxPDO2 implements DataFromCan {

	// [0,1]byte
	private int refTorqueAfterLimitation; // {0-13}bit signed int16 = short
	private String refTorqueAfterLimitationVAL; // {14-15}bit
	// [2,3]byte
	private int maxAvailableTorque; // {0-13}bit signed int16 = short
	private String maxAvailableTorqueVAL; // {14-15}bit
	// [4]byte
	private String cutbackNumber; // signed int8 = byte
	// [5,6]byte
	private int actTorque; // {0-13}bit signed int16 = short
	private String actTorqueVAL; // {14-15}bit
	// [7]byte
	private byte messageCount; // {0-3}bit

	public TxPDO2() {
		super();
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		byte[] b2 = new byte[2];
		System.arraycopy(data, 0, b2, 0, b2.length);
		refTorqueAfterLimitation = getTorque(b2);
		refTorqueAfterLimitationVAL = getVAL(data[1]);

		System.arraycopy(data, 2, b2, 0, b2.length);
		maxAvailableTorque = getTorque(b2);
		maxAvailableTorqueVAL = getVAL(data[3]);

		cutbackNumber = CutbackNumber.getCutbackByNumber(data[4]);

		System.arraycopy(data, 5, b2, 0, b2.length);
		actTorque = getTorque(b2);
		actTorqueVAL = getVAL(data[6]);

		messageCount = data[7];
	}

	private int getTorque(byte[] b2) {
		byte torqueZeroByte = b2[0];
		byte torqueFirstByte = (byte) (Integer.reverseBytes(b2[1] << 26) >>> 2);
		byte[] torqueBytes = { torqueZeroByte, torqueFirstByte };
		return ByteBuffer.wrap(torqueBytes, 0, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
	}

	private String getVAL(byte b) {
		return VAL.getLossesVALByCode(Integer.reverseBytes(b << 24) >>> 6);
	}

	public float getRefTorqueAfterLimitation() {
		return (float) (refTorqueAfterLimitation * 0.02 - 100);
	}

	public String getRefTorqueAfterLimitationVAL() {
		return refTorqueAfterLimitationVAL;
	}

	public float getMaxAvailableTorque() {
		return (float) (maxAvailableTorque * 0.02 );
	}

	public String getMaxAvailableTorqueVAL() {
		return maxAvailableTorqueVAL;
	}

	public String getCutbackNumber() {
		return cutbackNumber;
	}

	public float getActTorque() {
		return (float) (actTorque * 0.02 - 100);
	}

	public String getActTorqueVAL() {
		return actTorqueVAL;
	}

	public byte getMessageCount() {
		return messageCount;
	}

}
