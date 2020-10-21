package stand.app.thread;

import java.io.IOException;
import java.util.Map;

import stand.can.Can;
import stand.can.candata.DataFromCanModel;
import stand.ethernet.EthCanCdr;
import stand.ethernet.EthernetCan;
import stand.util.DataReceiver;

public class ReceiveThread extends Thread {

	private DataReceiver dataReceiver;// = new UdpReceiveData(31000);

	private Map<Integer, DataFromCanModel> canId;

	@Override
	public void run() {
		while (true) {
			try {
				objectMapping(dataReceiver.receive());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void objectMapping(byte[] ethernetPacket) {
		EthernetCan receivedEthCan = new EthCanCdr();
		receivedEthCan.parseEthernetPacket(ethernetPacket);

		for (Can can : receivedEthCan.getAllCan()) {
			if (canId.get(can.getId()) != null) {
				DataFromCanModel dataFromCanModel = canId.get(can.getId());
				dataFromCanModel.getDataFromCan().parseDataFromCan(can.getData());
				dataFromCanModel.updateModel();
			}
		}
	}

	public void setReceiveData(DataReceiver dataReceiver) {
		this.dataReceiver = dataReceiver;
	}

	public void setUnitIdMapper(Map<Integer, DataFromCanModel> canId) {
		this.canId = canId;
	}
}
