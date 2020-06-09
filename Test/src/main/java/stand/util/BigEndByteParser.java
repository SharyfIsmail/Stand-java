package stand.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BigEndByteParser {

	private BigEndByteParser() {
		super();
	}

	public static long unsignedLongToLong(byte[] b4) {
		if (b4.length == 4) {
			byte[] bytes = { 0, 0, 0, 0, b4[0], b4[1], b4[2], b4[3] };
			long number = ByteBuffer.wrap(bytes).getLong();
			return number;
		} else {
			throw new ArrayIndexOutOfBoundsException("byte[] b4 != 4  b4.length = " + b4.length);
		}
	}

	public static int longIntToInt(byte[] b4) {
		if (b4.length == 4) {
			return ByteBuffer.wrap(b4, 0, 4).order(ByteOrder.BIG_ENDIAN).getInt();
		} else {
			throw new ArrayIndexOutOfBoundsException("byte[] b4 != 4  b4.length = " + b4.length);
		}
	}

	public static int unsignedCharToInt(byte b1) {
		return Byte.toUnsignedInt(b1);
	}

	public static int unsignedIntToInt(byte[] b2) {
		if (b2.length == 2) {
			byte[] bytes = { 0, 0, b2[0], b2[1] };
			int number = ByteBuffer.wrap(bytes).getInt();
			return number;
		} else {
			throw new ArrayIndexOutOfBoundsException("byte[] b2 > 2  b2.length = " + b2.length);
		}
	}

//	public static byte[] intToLongInt(int i) {
//		return null;
//		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
//	}

	public static byte intToUnsignedChar(int i) {
		if (i <= 255 & i >= 0) {
			return Integer.valueOf(i).byteValue();
		} else {
			throw new NumberFormatException("Число i=" + i + " не относится к типу UnsignedChar(от 0 до 255)");
		}
	}

	public static byte[] intToUnsignedInt(int i) {
		if (i <= 65535 & i >= 0) {
			byte[] b4 = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
			byte[] b2 = new byte[2];
			System.arraycopy(b4, 2, b2, 0, b2.length);
			return b2;
		} else {
			throw new NumberFormatException("Число i=" + i + " не относится к типу UnsignedInt(от 0 до 65535)");
		}
	}

}
