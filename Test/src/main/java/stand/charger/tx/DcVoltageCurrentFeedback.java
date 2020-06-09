package stand.charger.tx;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class DcVoltageCurrentFeedback extends CanCdr implements DataFromCan {
	private int highVoltageBatteryPackVoltage;
	private int highVoltageBatteryCurrent;
	private int lowVoltageAuxBatteryVoltage;

	public DcVoltageCurrentFeedback() {
		super();
		setId(1559); // 617
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		// [0] high Voltage Battery PackVoltage MSB
		// [1] high Voltage Battery PackVoltage LSB
		// [2] high Voltage Battery Current MSB
		// [3] high Voltage Battery Current LSB
		// [4] low Voltage Aux Battery Voltage
		byte[] b2 = new byte[2];
		System.arraycopy(data, 0, b2, 0, b2.length);
		highVoltageBatteryPackVoltage = BigEndByteParser.unsignedIntToInt(b2);

		b2 = new byte[2];
		System.arraycopy(data, 2, b2, 0, b2.length);
		highVoltageBatteryCurrent = BigEndByteParser.unsignedIntToInt(b2);

		lowVoltageAuxBatteryVoltage = data[4];
	}

	public float getHighVoltageBatteryPackVoltage() {
		return (float) (highVoltageBatteryPackVoltage * 0.1);
	}

	public float getHighVoltageBatteryCurrent() {
		return (float) (highVoltageBatteryCurrent * 0.1);
	}

	public float getLowVoltageAuxBatteryVoltage() {
		return (float) (lowVoltageAuxBatteryVoltage * 0.1);
	}
}
