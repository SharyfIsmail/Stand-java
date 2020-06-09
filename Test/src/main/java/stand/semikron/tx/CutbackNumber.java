package stand.semikron.tx;

import java.util.HashMap;
import java.util.Map;

public class CutbackNumber {
	private static Map<Integer, String> cutbackNumbers = new HashMap<>();

	static {
		cutbackNumbers = new HashMap<>();
		cutbackNumbers.put(0, "Not active");
		cutbackNumbers.put(1, "Motor temperature");
		cutbackNumbers.put(2, "Motor accelleration");
		cutbackNumbers.put(3, "PCB temperature");
		cutbackNumbers.put(4, "Junction/DCB temperature");
		cutbackNumbers.put(5, "Minimum DC link ");
		cutbackNumbers.put(6, "Maximum DC link");
		cutbackNumbers.put(7, "SOA");
		cutbackNumbers.put(8, "I2T");
		cutbackNumbers.put(9, "Maximum speed");
		cutbackNumbers.put(10, "Speed reference");
		cutbackNumbers.put(11, "Mechanical power");
		cutbackNumbers.put(12, "DC link current");
		cutbackNumbers.put(13, "Max. table");
		cutbackNumbers.put(14, "DC link power");
		cutbackNumbers.put(15, "Low speed limiter");
	}

	public static String getCutbackByNumber(int code) {
		return cutbackNumbers.get(code);
	}
}
