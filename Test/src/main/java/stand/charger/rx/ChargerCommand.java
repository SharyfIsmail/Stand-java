package stand.charger.rx;

import stand.can.CanCdr;
import stand.util.BigEndByteParser;

public class ChargerCommand extends CanCdr {

	private final int voltageLimit = 3940; // 394 V
	private final int acCurrentLimit = 100; // 10 A
	private byte[] data = new byte[8]; // [2]Voltage Limit MSB
										// [3]Voltage Limit LSB
										// [5]AC Current Limit MSB
										// [6]AC Current Limit LSB
										// [7]Charger Command.
										// 1 enables the charger
										// 0 disables the charger

	public ChargerCommand() {
		super();
		setDLC(8); // 8 data bytes
		setTypeId(TypeId.STD); // uses 11 bit ID’s
		setId(80); // ID 0x50
		setFreq(100); // transmit the command at a rate of 100ms

		// DC
		data[1] = (byte) 220;
		// 394 V
		byte[] b2 = BigEndByteParser.intToUnsignedInt(voltageLimit);
		System.arraycopy(b2, 0, data, 2, b2.length);
		// 10 A
		b2 = BigEndByteParser.intToUnsignedInt(acCurrentLimit);
		System.arraycopy(b2, 0, data, 5, b2.length);
		setData(data);
	}

	public void setChargerCommand(ChargerCmd chargerCmd) {
		data[7] = (byte) chargerCmd.getPriority();
		setData(data);
	}

	public enum ChargerCmd {
		DISABLE_THE_CHARGER(0), ENABLE_THE_CHARGER(1);
		private int priority;

		private ChargerCmd(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}

}
