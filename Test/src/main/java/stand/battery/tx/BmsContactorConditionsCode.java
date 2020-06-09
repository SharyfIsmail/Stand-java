package stand.battery.tx;

import java.util.HashMap;
import java.util.Map;

public class BmsContactorConditionsCode {
	private static Map<Integer, String> bmsContactorConditions = new HashMap<>();

	static {
		bmsContactorConditions = new HashMap<>();
		bmsContactorConditions.put(0, "Conditions OK");
		bmsContactorConditions.put(1, "Emergency Power Off Not Present");
		bmsContactorConditions.put(2, "All Internal Slave Data Not Received");
		bmsContactorConditions.put(3, "Cell Over Voltage");
		bmsContactorConditions.put(4, "Cell Under-Voltage");
		bmsContactorConditions.put(5, "Pack Over Current");
		bmsContactorConditions.put(6, "Pack Over Temperature");
		bmsContactorConditions.put(7, "Pack Under-Temperature");
		bmsContactorConditions.put(8, "Circuit Board Over Temperature");
		bmsContactorConditions.put(9, "PreCharge Retry Fault");
		bmsContactorConditions.put(10, "PreCharge Short Circuit Fault");
		bmsContactorConditions.put(11, "No PCU Data Received (Battery & Heartbeat)");
		bmsContactorConditions.put(12, "Reserved");
		bmsContactorConditions.put(13, "Reserved");
		bmsContactorConditions.put(14, "PCU Fault (Battery command bit)");
		bmsContactorConditions.put(15, "Isolation Fault with Contactors On");
		bmsContactorConditions.put(16, "Isolation Fault with Contactors Off");
		bmsContactorConditions.put(17, "Low Voltage Pack Recover Mode");
		bmsContactorConditions.put(18, "Key Cycle Category Fault");
		bmsContactorConditions.put(19, "Service Category Fault");
		bmsContactorConditions.put(20, "Circuit Board Under-Temperature");
		bmsContactorConditions.put(21, "Powerup Self Test Fail");
		bmsContactorConditions.put(22, "No CAN Contactor Request");
		bmsContactorConditions.put(23, "Secondary ContA or FuseA Fault");
		bmsContactorConditions.put(24, "Contactor 1 Stuck On Fault");
		bmsContactorConditions.put(25, "Contactor 2 Stuck On Fault");
		bmsContactorConditions.put(26, "Reserved");
		bmsContactorConditions.put(27, "Secondary Contactor Stuck On Fault");
		bmsContactorConditions.put(28, "Latched Contactor Fault");
		bmsContactorConditions.put(29, "Contactor 1 Dropout Fault");
		bmsContactorConditions.put(30, "Contactors Engaged During Shutdown");
		bmsContactorConditions.put(31, "Contactor 2 Dropout Fault");
		bmsContactorConditions.put(32, "Contactor 1 Stuck Open Fault");
		bmsContactorConditions.put(33, "Contactor 2 Stuck Open Fault");
		bmsContactorConditions.put(34, "Secondary ContB Or FuseB Fault");
		bmsContactorConditions.put(35, "Pack Over-current Regulation Fault");
		bmsContactorConditions.put(36, "Aux Batt Undervoltage Fault");
		bmsContactorConditions.put(37, "Discharge during Charge Fault");
		bmsContactorConditions.put(38, "Cell Voltage Connection Fault");
		bmsContactorConditions.put(39, "Extreme Cell Undervoltage Fault");
		bmsContactorConditions.put(40, "Both Current Sensors AD Fault");
		bmsContactorConditions.put(41, "Low Current Correlation Fault");
		bmsContactorConditions.put(42, "High Current Correlation Fault");
		bmsContactorConditions.put(43, "Reserved");
		bmsContactorConditions.put(44, "CellV Compare Fault");
		bmsContactorConditions.put(45, "ModuleV To CellV Compare Fault");
		bmsContactorConditions.put(46, "Module Voltage Outlier Fault");
		bmsContactorConditions.put(47, "High Contactor Current Fault");
		bmsContactorConditions.put(48, "No System Configuration Loaded");
	}

	public static String getContactorConditionsByCode(int code) {
		return bmsContactorConditions.get(code);

	}
}
