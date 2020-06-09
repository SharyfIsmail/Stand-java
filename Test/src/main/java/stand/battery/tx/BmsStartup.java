package stand.battery.tx;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class BmsStartup extends CanCdr implements DataFromCan {

	private short sW_Major_Ver;
	private short sW_Minor_Ver;
	private short sW_Build;
	private int sW_Program_Target;
	private short bms_HW_Ver;
	private short bms_Init_Successful;
	private short highest_Err_Cat;

	/*
	 * Software version information
	 */
	public short getsW_Major_Ver() {
		return sW_Major_Ver;
	}

	/*
	 * Software version information
	 */
	public short getsW_Minor_Ver() {
		return sW_Minor_Ver;
	}

	/*
	 * Software version information
	 */
	public short getsW_Build() {
		return sW_Build;
	}

	/*
	 * Software program information
	 */
	public int getsW_Program_Target() {
		return sW_Program_Target;
	}

	/*
	 * Hardware version information
	 */
	public short getBms_HW_Ver() {
		return bms_HW_Ver;
	}

	/*
	 * True if system initialized properly
	 */
	public boolean getBms_Init_Successful() {
		return bms_Init_Successful == 1 ? true : false;
	}

	/*
	 * Highest error category 0 - 4
	 */
	public String getHighest_Err_Cat() {
		if (highest_Err_Cat == 0) {
			return "No error";
		}
		if (highest_Err_Cat == 2) {
			return "Continue operation, possible reduced power";
		}
		if (highest_Err_Cat == 3) {
			return "Delayed switch off, till timeout or Key Off";
		}
		if (highest_Err_Cat == 4) {
			return "Immediate switch off, with current ramp-down if possible";
		}
		return null;
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			int i = 0;

			sW_Major_Ver = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i++;

			sW_Minor_Ver = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i++;

			sW_Build = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i++;

			byte[] b2 = new byte[2];
			System.arraycopy(data, i, b2, 0, b2.length);
			sW_Program_Target = BigEndByteParser.unsignedIntToInt(b2);
			i += 2;

			bms_HW_Ver = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i += 1;

			bms_Init_Successful = (short) BigEndByteParser.unsignedCharToInt(data[i]);
			i += 1;

			highest_Err_Cat = (short) BigEndByteParser.unsignedCharToInt(data[i]);
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

}
