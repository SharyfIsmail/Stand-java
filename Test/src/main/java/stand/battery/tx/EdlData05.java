package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData05 implements DataFromCan {
	private int sysBusVolt;
	private int sysPackVolt;
	private int sysIsoRes;
	private int string2Voltage;

	/*
	 * Get value in V
	 */
	public double getSysBusVolt() {
		return sysBusVolt * 0.1;
	}

	/*
	 * Get value in V
	 */
	public double getSysPackVolt() {
		return sysPackVolt * 0.1;
	}

	/*
	 * Get value in KOhm
	 */
	public int getSysIsoRes() {
		return sysIsoRes;
	}

	/*
	 * Get value in V
	 */
	public double getString2Voltage() {
		return string2Voltage * 0.1;
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			int i = 0;
			byte[] b2 = new byte[2];
			System.arraycopy(data, i, b2, 0, b2.length);
			sysBusVolt = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			System.arraycopy(data, i, b2, 0, b2.length);
			sysPackVolt = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			System.arraycopy(data, i, b2, 0, b2.length);
			sysIsoRes = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			System.arraycopy(data, i, b2, 0, b2.length);
			string2Voltage = BigEndByteParser.unsignedIntToInt(b2);
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

}
