package stand.battery;

import java.io.IOException;

import stand.battery.rx.PcuContactorReq;
import stand.battery.rx.PcuContactorReq.LSB;
import stand.battery.rx.PcuHeartbeat;
import stand.ethernet.EthernetCan;
import stand.util.DataSender;

public class Battery implements BatteryService {
	private PcuContactorReq pcuContactorReq;
	private PcuHeartbeat pcuHeartbeat;
	private EthernetCan ethernetCan;
	private DataSender dataSender;

	public Battery(EthernetCan ethernetCan, DataSender dataSender) {
		pcuContactorReq = new PcuContactorReq();
		pcuHeartbeat = new PcuHeartbeat();

		this.ethernetCan = ethernetCan;
		this.dataSender = dataSender;
	}

	@Override
	public void turnOn() throws IOException {
		pcuContactorReq.setLsb(LSB.CONTACTOR_REQUEST_PACK_0);
		pcuContactorReq.setFreq(200);
		pcuHeartbeat.setFreq(200);
		ethernetCan.addCan(pcuContactorReq);
		ethernetCan.addCan(pcuHeartbeat);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			pcuContactorReq.setLsb(LSB.ZERO);
			pcuContactorReq.setFreq(1);
			pcuHeartbeat.setFreq(1);
			ethernetCan.removeCan(pcuContactorReq);
			ethernetCan.removeCan(pcuHeartbeat);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void turnOff() throws IOException {
		pcuContactorReq.setLsb(LSB.ZERO);
		pcuContactorReq.setFreq(1);
		pcuHeartbeat.setFreq(1);
		ethernetCan.removeCan(pcuContactorReq);
		ethernetCan.removeCan(pcuHeartbeat);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			pcuContactorReq.setLsb(LSB.CONTACTOR_REQUEST_PACK_0);
			pcuContactorReq.setFreq(200);
			pcuHeartbeat.setFreq(200);
			ethernetCan.addCan(pcuContactorReq);
			ethernetCan.addCan(pcuHeartbeat);
			throw new IOException(e.getMessage());
		}
	}

}
