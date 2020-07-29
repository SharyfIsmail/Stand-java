package stand.util;

import java.io.File;
import java.io.IOException;
import java.util.Deque;

public interface PcmFileWriter {

	/**
	 * The method write data in file use dequeue. Remove elements in dequeue.
	 */

	void write(File file, Deque<? extends Number> turnovers,Deque<? extends Number> turnOverTime, Deque<? extends Number> torques,
			Deque<? extends Number> torqueTime,Deque<? extends Number> tempValue,Deque<? extends Number> tempTime) throws IOException;
}
