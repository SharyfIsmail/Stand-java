package stand.can;

public interface Can {
	int getSize();

	int getId();

	TypeId getTypeId();

	int getDLC();

	byte[] getData();

	void setId(int id);

	void setDLC(int dlc);

	void setTypeId(TypeId typeId);

	void setData(byte[] data) throws ArrayIndexOutOfBoundsException;

	byte[] collectCan();

	void parseCan(byte[] canPacket) throws ArrayIndexOutOfBoundsException;

	public enum TypeId {
		STD(0), EXTD(4);

		private final int value;

		private TypeId(int priority) {
			this.value = priority;
		}

		public int getValue() {
			return value;
		}
	}
}
