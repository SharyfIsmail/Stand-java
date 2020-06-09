package stand.charger.tx;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class ChargerCommandFeedback extends CanCdr implements DataFromCan {

	private int voltageLimit;
	private int acCurrentLimit;
	private String chargerCommand;

	public ChargerCommandFeedback() {
		super();
		setId(1558); // 616
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		// [2]Voltage Limit MSB 3940V
		// [3]Voltage Limit LSB 3940V
		// [5]AC Current Limit MSB 100A
		// [6]AC Current Limit LSB 100A
		// [7]Charger Command.
		// 1 - enables the charger
		// 0 - disables the charger
		byte[] b2 = new byte[2];
		System.arraycopy(data, 2, b2, 0, b2.length);
		voltageLimit = BigEndByteParser.unsignedIntToInt(b2);

		b2 = new byte[2];
		System.arraycopy(data, 5, b2, 0, b2.length);
		acCurrentLimit = BigEndByteParser.unsignedIntToInt(b2);

		if (data[7] == 1) {
			chargerCommand = "Enable";
		} else if (data[7] == 0) {
			chargerCommand = "Disnable";
		} else {
			chargerCommand = null;
		}
	}

	public float getVoltageLimit() {
		return (float) (voltageLimit * 0.1);
	}

	public float getACcurrentLimit() {
		return (float) (acCurrentLimit * 0.1);
	}

	public String getChargeCommand() {
		return chargerCommand;
	}

}
