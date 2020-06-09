package stand.semikron.sdo;

public interface SDO<T> {
	byte getCommand();

	int getIndex();

	byte getSubindex();

	T getValue();

	void setCommand(byte command);

	void setValue(byte[] data);

	byte[] request();
}
