package stand.semikron.tx;

import java.util.HashMap;
import java.util.Map;

public class InverterState {

	private static Map<Integer, String> inverterStateCodes = new HashMap<>();

	static {
		inverterStateCodes = new HashMap<>();
		inverterStateCodes.put(1, "Initializing");
		inverterStateCodes.put(2, "Ready (PWM off)");
		inverterStateCodes.put(3, "Enable (PWM on)");
		inverterStateCodes.put(5, "Error");
		inverterStateCodes.put(6, "Initialization Failed");
		inverterStateCodes.put(9, "Restart");
		inverterStateCodes.put(10, "Shutting Down");
		inverterStateCodes.put(11, "Position Sensor Calibration");
		inverterStateCodes.put(12, "HV Disabled");
	}

	public static String getStateByCode(int code) {
		return inverterStateCodes.get(code);
	}
}