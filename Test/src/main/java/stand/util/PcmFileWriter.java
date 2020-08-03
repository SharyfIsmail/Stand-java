package stand.util;

import java.io.File;
import java.io.IOException;
import java.util.Deque;

public interface PcmFileWriter {

	/**
	 * The method write data in file use dequeue. Remove elements in dequeue.
	 */

	 void write(File file, Deque<? extends Number> turnoverValue, Deque<? extends Number> torqueValue,
			Deque<? extends Number> tempValue,Deque<? extends Number> experimentDuration) throws IOException;
}
