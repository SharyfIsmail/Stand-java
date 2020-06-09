package stand.ethernet;

import java.util.ArrayList;
import java.util.List;

import stand.can.Can;
import stand.can.CanCdr;
import stand.util.LilEndByteParser;

public class EthCanCdr implements EthernetCan {

	private int code = 1500;
	private int comand;
	private int Nz = 0;
	private int K1, K2, K3, K4, K5;
	private List<Can> cans;
	public static final int ETH_CAN_SIZE = 616;

	public EthCanCdr() {
		super();
		cans = new ArrayList<>();
	}

	public int getCode() {
		return code;
	}

	public int getComand() {
		return comand;
	}

	@Override
	public int getNz() {
		return Nz;
	}

	public int getK1() {
		return K1;
	}

	public int getK2() {
		return K2;
	}

	public int getK3() {
		return K3;
	}

	public int getK4() {
		return K4;
	}

	public int getK5() {
		return K5;
	}

	@Override
	public Can[] getAllCan() {
		return cans.toArray(new Can[cans.size()]);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setComand(int comand) {
		this.comand = comand;
	}

	public void setK1(int K1) {
		this.K1 = K1;
	}

	public void setK2(int K2) {
		this.K2 = K2;
	}

	public void setK3(int K3) {
		this.K3 = K3;
	}

	public void setK4(int K4) {
		this.K4 = K4;
	}

	public void setK5(int K5) {
		this.K5 = K5;
	}

	@Override
	public boolean addCan(Can addingCan) throws ArrayIndexOutOfBoundsException {
		if (addingCan != null) {
			if (Nz < 30) {
				if (cans.add(addingCan)) {
					Nz++;
					return true;
				}
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Количество возможных CAN привысило допустимое значение CAN[30]. NZ = " + (Nz + 1));
			}
		}
		return false;
	}

	@Override
	public void removeCan(Can removingCan) {
		if (cans.remove(removingCan))
			Nz--;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + K1;
		result = prime * result + K2;
		result = prime * result + K3;
		result = prime * result + K4;
		result = prime * result + K5;
		result = prime * result + Nz;
		result = prime * result + ((cans == null) ? 0 : cans.hashCode());
		result = prime * result + code;
		result = prime * result + comand;
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
		EthCanCdr other = (EthCanCdr) obj;
		if (K1 != other.K1)
			return false;
		if (K2 != other.K2)
			return false;
		if (K3 != other.K3)
			return false;
		if (K4 != other.K4)
			return false;
		if (K5 != other.K5)
			return false;
		if (Nz != other.Nz)
			return false;
		if (cans == null) {
			if (other.cans != null)
				return false;
		} else if (!cans.equals(other.cans))
			return false;
		if (code != other.code)
			return false;
		if (comand != other.comand)
			return false;
		return true;
	}

	@Override
	public byte[] collectEthernetPacket() {
		byte[] allBytes = new byte[ETH_CAN_SIZE];
		int bytePosition = 0;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(code), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(comand), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(Nz), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(K1), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(K2), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(K3), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(K4), 0, allBytes, bytePosition, 2);
		bytePosition += 2;
		System.arraycopy(LilEndByteParser.intToUnsignedInt(K5), 0, allBytes, bytePosition, 2);
		bytePosition += 2;

		for (Can can : cans) {
			if (can != null) {
				byte[] canPacket = can.collectCan();
				System.arraycopy(canPacket, 0, allBytes, bytePosition, can.getSize());
				bytePosition += can.getSize();
			}
		}
		return allBytes;
	}

	@Override
	public void parseEthernetPacket(byte[] ethernetPacket) throws ArrayIndexOutOfBoundsException {
		if (ethernetPacket.length >= 376 && ethernetPacket.length <= ETH_CAN_SIZE) {
			byte[] partArray = new byte[2];
			int start = 0;

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			code = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			comand = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			Nz = LilEndByteParser.unsignedIntToInt(partArray);
			if (Nz > 30) {
				throw new ArrayIndexOutOfBoundsException(
						"Количество возможных CAN привысило допустимое значение CAN[30]. NZ = " + Nz);
			}
			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			K1 = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			K2 = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			K3 = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			K4 = LilEndByteParser.unsignedIntToInt(partArray);

			System.arraycopy(ethernetPacket, start, partArray, 0, 2);
			start += 2;
			K5 = LilEndByteParser.unsignedIntToInt(partArray);

			cans.clear();

			for (int j = 0; j < Nz; j++) {

				Can currentCan = new CanCdr();
				byte[] byteCan = new byte[currentCan.getSize()];

				System.arraycopy(ethernetPacket, start, byteCan, 0, byteCan.length);
				currentCan.parseCan(byteCan);
				cans.add(currentCan);
				start += byteCan.length;
			}
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Размер ethCanData не соответствует диапазону[376,616]. ethCanData = " + ethernetPacket.length);
		}
	}

	@Override
	public String toString() {
		return String.format("EthCanCdr [code=%s, comand=%s, Nz=%s, K1=%s, K2=%s, K3=%s, K4=%s, K5=%s,\ncans=%s]", code,
				comand, Nz, K1, K2, K3, K4, K5, cans);
	}

}
