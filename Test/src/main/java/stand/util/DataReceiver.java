package stand.util;

import java.io.IOException;

public interface DataReceiver {

	/**
	 * 
	 * @return receive data
	 * @throws IOException
	 */
	byte[] receive() throws IOException;

	/**
	 * The method is close connection
	 * 
	 * @throws IOException
	 */
	void close() throws IOException;
}
