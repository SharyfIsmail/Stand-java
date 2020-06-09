package stand.can.candata;

public interface DataFromCan {
	/**
	 * Parses byte array from CAN to Object
	 */
	void parseDataFromCan(byte[] data);
}
