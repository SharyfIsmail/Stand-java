package stand.semikron.tx;

import java.util.HashMap;
import java.util.Map;

public class VAL {
	private static Map<Integer, String> lossesVALCodes = new HashMap<>();

	static {
		lossesVALCodes = new HashMap<>();
		lossesVALCodes.put(0, "Valid signal");
		lossesVALCodes.put(1, "Inaccurate signal");
		lossesVALCodes.put(2, "Error indicator: invalid signal");
		lossesVALCodes.put(3, "Not available: invalid signal");
	}

	public static String getLossesVALByCode(int code) {
		return lossesVALCodes.get(code);
	}
}
