package stand.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LilEndByteParser {

	private LilEndByteParser() {
		super();
	}

	public static int longIntToInt(byte[] b4) throws ArrayIndexOutOfBoundsException {
		// для BIG_ENDIAN
		// return ByteBuffer.wrap(b4, 0, 4).order(ByteOrder.BIG_ENDIAN).getInt();
		if (b4.length == 4) {
			return ByteBuffer.wrap(b4, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
		} else {
			throw new ArrayIndexOutOfBoundsException("byte[] b4 != 4  b4.length = " + b4.length);
		}
	}

	public static int unsignedCharToInt(byte b1) {
		return Byte.toUnsignedInt(b1);
	}

	public static int unsignedIntToInt(byte[] b2) throws ArrayIndexOutOfBoundsException {
		// для BIG_ENDIAN
		// byte[] bytes = {0,0,b[index],b[index+1]};
		if (b2.length == 2) {
			byte[] bytes = { 0, 0, b2[1], b2[0] };
			int number = ByteBuffer.wrap(bytes).getInt();
			return number;
		} else {
			throw new ArrayIndexOutOfBoundsException("byte[] b2 > 2  b2.length = " + b2.length);
		}
	}

	public static byte[] intToLongInt(int i) {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}

	public static byte intToUnsignedChar(int i) throws NumberFormatException {
		if (i <= 255 & i >= 0) {
			return Integer.valueOf(i).byteValue();

		} else {
			throw new NumberFormatException("Число i=" + i + " не относится к типу UnsignedChar(от 0 до 255)");
		}
	}

	public static byte[] intToUnsignedInt(int i) throws NumberFormatException {
		if (i <= 65535 & i >= 0) {
			byte[] b4 = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
			byte[] b2 = new byte[2];
			System.arraycopy(b4, 0, b2, 0, b2.length);
			return b2;
		} else {
			throw new NumberFormatException("Число i=" + i + " не относится к типу UnsignedInt(от 0 до 65535)");
		}
	}

	public static float byteArrayToFloat(byte[] b4) {
		int i = LilEndByteParser.longIntToInt(b4);
		return Float.intBitsToFloat(i);
	}

}
