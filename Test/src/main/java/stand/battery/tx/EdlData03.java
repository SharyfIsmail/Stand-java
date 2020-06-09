package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData03 implements DataFromCan {
	private int maxChgPower;
	private int maxDChgPower;
	private long totalDChgEnergy;

	/*
	 * Get value in KW
	 */
	public double getMaxChgPower() {
		return maxChgPower * 0.1;
	}

	/*
	 * Get value in KW
	 */
	public double getMaxDChgPower() {
		return maxDChgPower * 0.1;
	}

	/*
	 * Get value in KW
	 */
	public double getTotalDChgEnergy() {
		return totalDChgEnergy * 0.1;
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			byte[] b2 = new byte[2];

			System.arraycopy(data, 0, b2, 0, b2.length);
			maxChgPower = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 2, b2, 0, b2.length);
			maxDChgPower = BigEndByteParser.unsignedIntToInt(b2);

			byte[] b4 = new byte[4];
			System.arraycopy(data, 4, b4, 0, b4.length);
			totalDChgEnergy = BigEndByteParser.unsignedLongToLong(b4);
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}
}
