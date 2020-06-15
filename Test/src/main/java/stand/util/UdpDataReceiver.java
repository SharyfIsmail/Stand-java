package stand.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import stand.ethernet.EthCanCdr;

public class UdpDataReceiver implements DataReceiver {
	private DatagramPacket receiveDatagramPacket; // datagramPacket instance for receiving
	private DatagramSocket receiveDatagramSocket; // datagramSocket instance for receiving
	private int bufferSize;
	private int receivePort;
	private boolean vergin;

	public UdpDataReceiver(int receivePort, int bufferSize) {
		super();
		this.bufferSize = bufferSize;
		this.receivePort = receivePort;
	}

	@Override
	public byte[] receive() throws IOException {
		if (receiveDatagramSocket == null && receiveDatagramPacket == null) {
			receiveDatagramSocket = new DatagramSocket(receivePort);
			vergin = true;
			receiveDatagramPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
		}
		if (vergin) {
		
			new UdpDataSender(receivePort, "255.255.255.255").send(new byte[EthCanCdr.ETH_CAN_SIZE]);
			vergin = false;
		}
		receiveDatagramPacket.setData(new byte[bufferSize]);
		receiveDatagramSocket.receive(receiveDatagramPacket);
		System.out.println(receiveDatagramSocket.getLocalPort());
		return receiveDatagramPacket.getData();
	}

	@Override
	public void close() throws IOException {
		if (receiveDatagramSocket != null)
			receiveDatagramSocket.close();
	}
}
