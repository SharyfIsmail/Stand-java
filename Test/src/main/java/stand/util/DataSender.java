package stand.util;

import java.io.IOException;

public interface DataSender {

	/**
	 * The method send data
	 * 
	 * @throws IOException
	 */
	void send(byte[] data) throws IOException;

	/**
	 * The method is close connection
	 * 
	 * @throws IOException
	 */
	void close() throws IOException;
}
