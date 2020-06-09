package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData02 implements DataFromCan {
	private int maxChgCurrent;
	private int maxDChgCurrent;
	private short systemCurrent;
	private int string1Voltage;

	public EdlData02() {
		super();
//		setId(486487392); // 1cff 3560
	}

	/**
	 * Get value in A
	 */
	public double getMaxChgCurrent() {
		return maxChgCurrent * 0.1;
	}

	/**
	 * Get value in A
	 */
	public double getMaxDChgCurrent() {
		return maxDChgCurrent * 0.1;
	}

	/**
	 * Get value in A
	 */
	public float getSystemCurrent() {
		return (float) (systemCurrent * 0.1);
	}

	/**
	 * Get value in V
	 */
	public float getString1Voltage() {
		return (float) (string1Voltage * 0.1);
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			byte[] b2 = new byte[2];
			System.arraycopy(data, 0, b2, 0, b2.length);
			maxChgCurrent = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 2, b2, 0, b2.length);
			maxDChgCurrent = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 4, b2, 0, b2.length);
			systemCurrent = (short) BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 6, b2, 0, b2.length);
			string1Voltage = BigEndByteParser.unsignedIntToInt(b2);
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}
}
