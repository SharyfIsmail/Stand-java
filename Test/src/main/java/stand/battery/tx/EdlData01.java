package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData01 implements DataFromCan {
	private int avg_CV;
	private int delta_CV;
	private int sys_Total_Ratio;
	private short sys_SOUE;
	private short sys_SOC;

	public EdlData01() {
		super();
//		setId(486487136);// 1cff 3460
	}

	/**
	 * Get value in V
	 */
	public double getAvg_CV() {
		return avg_CV * 0.0024414;
	}

	/**
	 * Get value in V
	 */
	public double getDelta_CV() {
		return delta_CV * 0.0024414;
	}

	public int getSys_Total_Ratio() {
		return sys_Total_Ratio;
	}

	public short getSys_SOUE() {
		return sys_SOUE;
	}

	public float getSys_SOC() {
		return (float) (sys_SOC * 0.4);
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			byte[] b2 = new byte[2];
			System.arraycopy(data, 0, b2, 0, b2.length);
			avg_CV = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 2, b2, 0, b2.length);
			delta_CV = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 4, b2, 0, b2.length);
			sys_Total_Ratio = BigEndByteParser.unsignedIntToInt(b2);

			sys_SOUE = (short) BigEndByteParser.unsignedCharToInt(data[6]);
			sys_SOC = (short) BigEndByteParser.unsignedCharToInt(data[7]);

		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}
}
