package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData06 implements DataFromCan {
	private int maxChgVoltage;
	private int minDChgVoltage;
	private short maxSOC;
	private short minSOC;
	private byte avgPackTemp;
	private short systemStatus;

	/*
	 * Get value in V
	 */
	public double getMaxChgVoltage() {
		return maxChgVoltage * 0.1;
	}

	/*
	 * Get value in V
	 */
	public double getMinDChgVoltage() {
		return minDChgVoltage * 0.1;
	}

	/*
	 * Get value in %
	 */
	public double getMaxSOC() {
		return maxSOC * 0.4;
	}

	/*
	 * Get value in %
	 */
	public double getMinSOC() {
		return minSOC * 0.4;
	}

	/*
	 * Get value in DegC (signed)
	 */
	public byte getAvgPackTemp() {
		return avgPackTemp;
	}

	public String getSystemStatus() {
		return "System Status не прописано " + systemStatus;
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			int i = 0;
			byte[] b2 = new byte[2];
			System.arraycopy(data, i, b2, 0, b2.length);
			maxChgVoltage = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			System.arraycopy(data, i, b2, 0, b2.length);
			minDChgVoltage = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			maxSOC = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i += 1;

			minSOC = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i += 1;

			avgPackTemp = data[i];
			i += 1;

			systemStatus = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i += 1;
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

}
