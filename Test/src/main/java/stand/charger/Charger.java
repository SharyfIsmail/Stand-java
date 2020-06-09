package stand.charger;

import java.io.IOException;

import stand.charger.rx.ChargerCommand;
import stand.charger.rx.ChargerCommand.ChargerCmd;
import stand.ethernet.EthernetCan;
import stand.util.DataSender;

public class Charger implements ChargerService {
	private ChargerCommand chargerCommand;
	private EthernetCan ethernetCan;
	private DataSender dataSender;

	public Charger(EthernetCan ethernetCan, DataSender dataSender) {
		chargerCommand = new ChargerCommand();

		this.ethernetCan = ethernetCan;
		this.dataSender = dataSender;
	}

	@Override
	public void turnOn() throws IOException {
		chargerCommand.setChargerCommand(ChargerCmd.ENABLE_THE_CHARGER);
		chargerCommand.setFreq(100);
		ethernetCan.addCan(chargerCommand);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			chargerCommand.setChargerCommand(ChargerCmd.DISABLE_THE_CHARGER);
			chargerCommand.setFreq(1);
			ethernetCan.removeCan(chargerCommand);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void turnOff() throws IOException {
		chargerCommand.setChargerCommand(ChargerCmd.DISABLE_THE_CHARGER);
		chargerCommand.setFreq(1);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			chargerCommand.setChargerCommand(ChargerCmd.ENABLE_THE_CHARGER);
			chargerCommand.setFreq(100);
			throw new IOException(e.getMessage());
		}
		ethernetCan.removeCan(chargerCommand);
	}
}
