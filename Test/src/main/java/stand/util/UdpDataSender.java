package stand.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpDataSender implements DataSender {

	private DatagramSocket sendDatagramSocket; // datagramSocket instance for sending
	private int port;
	private String inetAddress;

	public UdpDataSender(int port, String inetAddress) {
		super();
		this.port = port;
		this.inetAddress = inetAddress;
	}

	@Override
	public void send(byte[] data) throws IOException {
		if (sendDatagramSocket == null)
			sendDatagramSocket = new DatagramSocket();

		DatagramPacket sendDatagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(inetAddress),
				port);
		sendDatagramSocket.send(sendDatagramPacket);
	}

	public int getPort() {
		return port;
	}

	public String getInetAddress() {
		return inetAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setInetAddress(String inetAddress) {
		this.inetAddress = inetAddress;
	}

	@Override
	public void close() throws IOException {
		if (sendDatagramSocket != null)
			sendDatagramSocket.close();
	}
}
