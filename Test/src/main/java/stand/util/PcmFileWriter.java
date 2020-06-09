package stand.util;

import java.io.File;
import java.io.IOException;
import java.util.Deque;

public interface PcmFileWriter {

	/**
	 * The method write data in file use dequeue. Remove elements in dequeue.
	 */

	void write(File file, Deque<? extends Number> turnovers,
			Deque<? extends Number> voltages) throws IOException;
	public void write(File file, Deque<? extends Number> value, String name) throws IOException ;

}
