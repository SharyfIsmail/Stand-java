package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class EdlData00 implements DataFromCan {

	private int max_CV;
	private int min_CV;
	private short max_Pack_Temp;
	private short min_Pack_Temp;
	private String bms_Contactor_Condition;
	private String bms_Highest_Error_Reason;
	private boolean remote_Comm_Fault;

	public EdlData00() {
		super();
		bms_Contactor_Condition = "";
		bms_Highest_Error_Reason = "";
//		setId(486486880); // 1cff 3360
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			byte[] b2 = new byte[2];
			System.arraycopy(data, 0, b2, 0, b2.length);
			max_CV = BigEndByteParser.unsignedIntToInt(b2);

			System.arraycopy(data, 2, b2, 0, b2.length);
			min_CV = BigEndByteParser.unsignedIntToInt(b2);

			max_Pack_Temp = (short) BigEndByteParser.unsignedCharToInt(data[4]);

			min_Pack_Temp = (short) BigEndByteParser.unsignedCharToInt(data[5]);

			bms_Contactor_Condition = BmsContactorConditionsCode.getContactorConditionsByCode(data[6]);

			bms_Highest_Error_Reason = BmsHighestErrorReason.getBMS_Highest_Error_Reason_By_Code(data[7]);
			byte b = (byte) (data[7] >> 7);
			if (b == -1)
				remote_Comm_Fault = true;
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

	/**
	 * Get value in V
	 */
	public double getMax_CV() {
		return max_CV * 0.0024414;
	}

	/**
	 * Get value in V
	 */
	public double getMin_CV() {
		return min_CV * 0.0024414;
	}

	/**
	 * Get value in 1 degC
	 */
	public short getMax_Pack_Temp() {
		return max_Pack_Temp;
	}

	/**
	 * Get value in 1 degC
	 */
	public short getMin_Pack_Temp() {
		return min_Pack_Temp;
	}

	/**
	 * Get Present Status BMS Contactor Conditions Code
	 */
	public String getBMS_Contactor_Condition() {
		return bms_Contactor_Condition;
	}

	/**
	 * Get Highest detected error
	 */
	public String getBMS_Highest_Error_Reason() {
		return bms_Highest_Error_Reason;
	}

	/**
	 * Get Remote Comm Fault. If internal RLEC communications fault
	 */
	public boolean getRemote_Comm_Fault() {
		return remote_Comm_Fault;
	}
}
