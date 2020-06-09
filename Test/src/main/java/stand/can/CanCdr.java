package stand.can;

import java.util.Arrays;

import stand.util.LilEndByteParser;

public class CanCdr implements Can {

	private int freq;
	private int time;
	private int id;
	private TypeId typeId = TypeId.STD;
	private int DLC;
	private byte[] data = new byte[8];
	public static final int CAN_CDR_SIZE = 20;

	public int getFreq() {
		return freq;
	}

	public int getTime() {
		return time;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public TypeId getTypeId() {
		return typeId;
	}

	@Override
	public int getDLC() {
		return DLC;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setDLC(int dlc) {
		this.DLC = dlc;
	}

	@Override
	public void setTypeId(TypeId typeId) {
		if (typeId != null)
			this.typeId = typeId;
	}

	@Override
	public void setData(byte[] data) throws ArrayIndexOutOfBoundsException {
		if (data.length >= 0 && data.length <= 8) {
			DLC = data.length;
			this.data = new byte[8];
			System.arraycopy(data, 0, this.data, 0, data.length);
		} else {
			throw new ArrayIndexOutOfBoundsException("Can data length = " + data.length);
		}
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + DLC;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + id;
		result = prime * result + time;
		result = prime * result + typeId.getValue();
		result = prime * result + freq;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanCdr other = (CanCdr) obj;
		if (DLC != other.getDLC())
			return false;
		if (!Arrays.equals(data, other.getData()))
			return false;
		if (id != other.getId())
			return false;
		if (time != other.getTime())
			return false;
		if (typeId.getValue() != other.getTypeId().getValue())
			return false;
		if (freq != other.getFreq())
			return false;
		return true;
	}

	@Override
	public byte[] collectCan() {
		byte[] allBytes = new byte[CAN_CDR_SIZE];
		int bytePosition = 0;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(freq), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToLongInt(time), 0, allBytes, bytePosition, 4);
		bytePosition += 4;
		System.arraycopy(LilEndByteParser.intToLongInt(id), 0, allBytes, bytePosition, 4);
		bytePosition += 4;
		allBytes[bytePosition] = LilEndByteParser.intToUnsignedChar(typeId.getValue());
		bytePosition++;
		allBytes[bytePosition] = LilEndByteParser.intToUnsignedChar(DLC);
		bytePosition++;
		for (int j = 0; j < 8; j++) {
			allBytes[bytePosition + j] = this.data[j];
		}
		return allBytes;
	}

	@Override
	public void parseCan(byte[] canPacket) throws ArrayIndexOutOfBoundsException {
		if (canPacket.length == CAN_CDR_SIZE) {
			int bytePosition = 0;
			byte[] partArray = new byte[2];

			System.arraycopy(canPacket, bytePosition, partArray, 0, 2);
			bytePosition += 2;
			freq = LilEndByteParser.unsignedIntToInt(partArray);

			partArray = new byte[4];
			System.arraycopy(canPacket, bytePosition, partArray, 0, 4);
			bytePosition += 4;
			time = LilEndByteParser.longIntToInt(partArray);

			System.arraycopy(canPacket, bytePosition, partArray, 0, 4);
			bytePosition += 4;
			id = LilEndByteParser.longIntToInt(partArray);

			typeId = (LilEndByteParser.unsignedCharToInt(canPacket[bytePosition])) == 0 ? TypeId.STD : TypeId.EXTD;
			bytePosition++;

			DLC = LilEndByteParser.unsignedCharToInt(canPacket[bytePosition]);
			bytePosition++;
			data = new byte[8];
			for (int j = 0; j < DLC; j++) {
				data[j] = canPacket[bytePosition + j];
			}
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Can data length is not " + CAN_CDR_SIZE + ".  can data length = " + canPacket.length);
		}

	}

	@Override
	public int getSize() {
		return CAN_CDR_SIZE;
	}

	@Override
	public String toString() {
		return "CanCdr [freq=" + freq + ", time=" + time + ", id=" + id + ", typeId=" + typeId + ", DLC=" + DLC
				+ ", data=" + Arrays.toString(data) + "]";
	}
}
