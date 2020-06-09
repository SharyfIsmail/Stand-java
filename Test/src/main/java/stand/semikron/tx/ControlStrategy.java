package stand.semikron.tx;

import java.util.HashMap;
import java.util.Map;

public class ControlStrategy {

	private static Map<Integer, String> controlStrategyCodes = new HashMap<>();

	static {
		controlStrategyCodes = new HashMap<>();
		controlStrategyCodes.put(0, "none (no operation)");
		controlStrategyCodes.put(1, "FOC PSM");
		controlStrategyCodes.put(2, "FOC IPM");
		controlStrategyCodes.put(3, "IFOC ASM");
		controlStrategyCodes.put(5, "BLDC");
		controlStrategyCodes.put(6, "BRC");
	}

	public static String getControlStrategyByCode(int code) {
		return controlStrategyCodes.get(code);
	}
}
