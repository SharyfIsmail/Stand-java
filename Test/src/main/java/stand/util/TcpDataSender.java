package stand.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;

public class TcpDataSender implements DataSender {

	private Socket socket;
	private int serverPort;
	private String host;

	public TcpDataSender(int serverport, String host) {
		this.serverPort = serverport;
		this.host = host;
	}

	/**
	 *
	 * @param data - byte array for sending
	 * @throws IOException                  - if an error occurs during the
	 *                                      connection
	 * @throws IllegalBlockingModeException - if this socket has an associated
	 *                                      channel, and the channel is in
	 *                                      non-blocking mode
	 * 
	 * @throws SocketTimeoutException-      if timeout expires before connecting
	 * 
	 * @throws IllegalArgumentException     - if endpoint is null or is a
	 *                                      SocketAddress subclass not supported by
	 *                                      this socket
	 * @throws ConnectException             - Signals that an error occurred while
	 *                                      attempting to connect a socket to a
	 *                                      remote address and port. Typically, the
	 *                                      connection was refused remotely (e.g.,
	 *                                      no process is listening on the remote
	 *                                      address/port).
	 */
	@Override
	public void send(byte[] data) throws IOException {
		try {
			if (socket == null) {
				socket = new Socket();
				System.out.println("NEW SOCKET");
				socket.connect(new InetSocketAddress(host, serverPort), 500); // SocketTimeoutException if timeout
																				// expires before connecting
				System.out.println("CONNECT SOCKET");
				socket.setSoTimeout(200); // If connect is true but server no response during 200 ms
			}
			socket.getOutputStream().write(data);
			socket.getOutputStream().flush();
		} catch (IOException e) {
			close();
			throw new IOException(e.getMessage());
		}
		response();
	}

	private void response() throws IOException {
		int response = 0;
		try {
			response = socket.getInputStream().read();
		} catch (IOException e) {
			close();
			throw new IOException(e.getMessage());
		}
		switch (response) {
		case 1:
			break;
		case 2:
			throw new IOException("CAN-1 connection problem");
		case 3:
			throw new IOException("CAN-2 connection problem");
		case 4:
			throw new IOException("CAN-1 CAN-2 connection problems");
		default:
			close();
			throw new IOException();
		}
	}

	@Override
	public void close() throws IOException {
		if (socket != null) {
			socket.close();
			socket = null;
			System.out.println("CLOSE SOCKET");
		}
	}
}
