package stand.util;

import java.util.BitSet;

public class BitSetter {

	private BitSetter() {
		super();
	}

	/**
	 * Get the value from startBitIndex to toBitIndex
	 * 
	 * @param byteArray     - array containing the number
	 * @param startBitIndex - the start bit of value
	 * @param lastBitIndex  - the last bit of value
	 * @return number
	 * 
	 */
	public static int getNumber(byte[] byteArray, int startBitIndex, int lastBitIndex) {

		StringBuffer binaryNumber = new StringBuffer("");
		BitSet bitData = BitSet.valueOf(byteArray);
		for (int i = startBitIndex; i <= lastBitIndex; i++) {
			if (bitData.get(i)) {
				binaryNumber.append("1");
			} else {
				binaryNumber.append("0");
			}
		}
		return Integer.parseInt(binaryNumber.reverse().toString(), 2);
	}

	/**
	 * Get bit state from number
	 * 
	 * @param position - bit position
	 * @return bit state
	 * 
	 */
	public static byte getBit(byte number, int position) {
		return (byte) ((number >> position) & 1);
	}

	/**
	 * Set the value to byte array
	 * 
	 * @param actualByteDataSize - data size in bytes
	 * @param number             - set the value in the byte array
	 * @param startBitIndex      - start bit for recording value
	 * @param toBitIndex         - last bit for recording value
	 * @return byte data array with length actualByteDataSize
	 */
	public static byte[] setBitsToByteData(BitSet bitData, int actualByteDataSize, int number, int startBitIndex,
			int toBitIndex) {

		char[] binaryString = Integer.toBinaryString(number).toCharArray();
		int binaryStringLength = binaryString.length + startBitIndex - 1;
		int currentCharIndex = binaryString.length - 1;

		for (int i = startBitIndex; i <= toBitIndex; i++) {

			if (i <= binaryStringLength) {

				if (binaryString[currentCharIndex] == '1') {
					bitData.set(i);
					currentCharIndex--;
				} else {
					bitData.clear(i);
					currentCharIndex--;
				}

			} else {
				bitData.clear(i);
			}
		}
		return getData(bitData, actualByteDataSize);
	}

	private static byte[] getData(BitSet bitData, int actualByteDataSize) {
		byte[] data = new byte[actualByteDataSize];

		byte[] bitDataByteArray = bitData.toByteArray();
		int currentByteDataSize = bitDataByteArray.length;

		if (actualByteDataSize != bitData.length() / 8) {
			for (int i = 0; i < currentByteDataSize; i++) {
				data[i] = bitDataByteArray[i];
			}
			for (int i = currentByteDataSize; i < actualByteDataSize; i++) {
				data[i] = 0;
			}
		} else {
			data = bitDataByteArray;
		}
		return data;
	}
}
