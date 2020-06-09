package stand.app.thread;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import stand.ethernet.EthCanCdr;
import stand.util.DataSender;
import stand.util.UdpDataSender;

@Component
public class SendThread extends Thread {
	@Autowired
	private Queue<byte[]> ethCanQueue;
	@Autowired
	private DataSender dataSender;

	public SendThread(Queue<byte[]> ethCanQueue, DataSender dataSender) {
		super();
		this.ethCanQueue = ethCanQueue;
		this.dataSender = dataSender;
	}

	public SendThread() {
		super();
		ethCanQueue = new ConcurrentLinkedQueue<byte[]>();
	}

	@Override
	public void run() {

		dataSender = new UdpDataSender(31000, "255.255.255.255");
		byte[] data = new byte[8];
		byte i = 0;
		while (true) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			EthCanCdr ethCanCdr = new EthCanCdr();
//
			for (int j = 0; j < data.length; j++)
				data[j] = i;
			i++;
//			Can can1 = new CanCdr();
//			can1.setId(506);
//			can1.setData(data);
//
//			Can can2 = new CanCdr();
//			can2.setId(762);
//			can2.setData(data);
//
//			Can can3 = new CanCdr();
//			can3.setId(1018);
//			can3.setData(data);
//
//			Can can4 = new CanCdr();
//			can4.setId(1274);
//			can4.setData(data);
//
//			Can can5 = new CanCdr();
//			can5.setId(442);
//			can5.setData(data);
//
//			Can can6 = new CanCdr();
//			can6.setId(1530);
//			can6.setData(data);
//
//			Can can7 = new CanCdr();
//			can7.setId(1914);
//			can7.setData(data);
//
//			ethCanCdr.addCan(can1);
//			ethCanCdr.addCan(can2);
//			ethCanCdr.addCan(can3);
//			ethCanCdr.addCan(can4);
//			ethCanCdr.addCan(can5);
//			ethCanCdr.addCan(can6);
//			ethCanCdr.addCan(can7);

//			Can can8 = new CanCdr();
//			can8.setId(486486368);
//			can8.setData(data);
//			ethCanCdr.addCan(can8);
			ethCanQueue.add(ethCanCdr.collectEthernetPacket());
			if (!ethCanQueue.isEmpty()) {
				try {
					dataSender.send(ethCanQueue.poll());
				} catch (NullPointerException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Queue<byte[]> getEthCanQueue() {
		return ethCanQueue;
	}

	public DataSender getSendData() {
		return dataSender;
	}

	public void setEthCanQueue(Queue<byte[]> ethCanQueue) {
		this.ethCanQueue = ethCanQueue;
	}

	public void setSendData(DataSender dataSender) {
		this.dataSender = dataSender;
	}
}
