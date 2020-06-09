package stand.battery.tx;

import java.util.HashMap;
import java.util.Map;

public class BmsHighestErrorReason {
	private static Map<Integer, String> bms_highest_error_reasons = new HashMap<>();

	static {
		bms_highest_error_reasons = new HashMap<>();
		bms_highest_error_reasons.put(0, "No Error");
		bms_highest_error_reasons.put(1, "PCU Fault");
		bms_highest_error_reasons.put(2, "No Charge Current");
		bms_highest_error_reasons.put(3, "Circuit Board Temperature Warning");
		bms_highest_error_reasons.put(4, "Current Limit On Low Temperature");
		bms_highest_error_reasons.put(5, "Current Limit On High Temperature");
		bms_highest_error_reasons.put(6, "External Isolation Fault");
		bms_highest_error_reasons.put(7, "Internal Isolation Fault");
		bms_highest_error_reasons.put(8, "Reserved");
		bms_highest_error_reasons.put(9, "Reserved");
		bms_highest_error_reasons.put(10, "No PCU Data Received (Contactor & Heartbeat)");
		bms_highest_error_reasons.put(11, "PreCharge Short Circuit Fault");
		bms_highest_error_reasons.put(12, "PreCharge Retry Fault");
		bms_highest_error_reasons.put(13, "Circuit Board Over-Temperature Fault");
		bms_highest_error_reasons.put(14, "Pack Under-Temperature Fault");
		bms_highest_error_reasons.put(15, "Pack Over-Temperature Fault");
		bms_highest_error_reasons.put(16, "Pack Over-Current Fault");
		bms_highest_error_reasons.put(17, "Cell Under-Voltage Fault");
		bms_highest_error_reasons.put(18, "Cell Over-Voltage Fault");
		bms_highest_error_reasons.put(19, "Contactor 2 Stuck On Fault");
		bms_highest_error_reasons.put(20, "Secondary Contactor Open Fault");
		bms_highest_error_reasons.put(21, "Emergency Power Off Active");
		bms_highest_error_reasons.put(22, "Circuit Board Under-Temperature Fault");
		bms_highest_error_reasons.put(23, "Contactor 1 Stuck On Fault");
		bms_highest_error_reasons.put(24, "Slave Data Not Received Fault");
		bms_highest_error_reasons.put(25, "Powerup Self Test Fault");
		bms_highest_error_reasons.put(26, "Secondary Contactor Stuck On Fault");
		bms_highest_error_reasons.put(27, "Contactor Dropout Fault");
		bms_highest_error_reasons.put(28, "Fan Current Low Fault");
		bms_highest_error_reasons.put(29, "Fan Current High Fault");
		bms_highest_error_reasons.put(30, "Aux Batt Under-Volt Fault");
		bms_highest_error_reasons.put(31, "Contactor 1 Stuck Open Fault");
		bms_highest_error_reasons.put(32, "Contactor 2 Stuck Open Fault");
		bms_highest_error_reasons.put(33, "Discharge during Charge Fault");
		bms_highest_error_reasons.put(34, "Key Cycle Category Fault");
		bms_highest_error_reasons.put(35, "Service Category Fault");
		bms_highest_error_reasons.put(36, "High Contactor Coil Current Fault");
		bms_highest_error_reasons.put(37, "Cell Voltage Connection Fault");
		bms_highest_error_reasons.put(38, "Extreme Undervoltage Fault");
		bms_highest_error_reasons.put(39, "One Current Sensor Fault");
		bms_highest_error_reasons.put(40, "Both Current Sensors Fault");
		bms_highest_error_reasons.put(41, "Low Current Correlation Fault");
		bms_highest_error_reasons.put(42, "High Current Correlation Fault");
		bms_highest_error_reasons.put(43, "Pack Voltage Sensor Fault");
		bms_highest_error_reasons.put(44, "String Voltage Mismatch Fault");
		bms_highest_error_reasons.put(45, "Aux Batt Under-Volt Warning");
		bms_highest_error_reasons.put(46, "Main Contactor Open Timeout Fault");
		bms_highest_error_reasons.put(47, "Reserved");
		bms_highest_error_reasons.put(48, "CellV Compare Fault");
		bms_highest_error_reasons.put(49, "ModuleV To CellV Compare Fault");
		bms_highest_error_reasons.put(50, "Module Voltage Outlier Fault");
		bms_highest_error_reasons.put(51, "No CAN Contactor Request");
		bms_highest_error_reasons.put(52, "System High Current Warn");
		bms_highest_error_reasons.put(53, "First PreCharge Fail Fault");
		bms_highest_error_reasons.put(54, "Analog Input Lost Calibration");
		bms_highest_error_reasons.put(55, "No System Configuration Loaded");
	}

	public static String getBMS_Highest_Error_Reason_By_Code(int code) {
		return bms_highest_error_reasons.get(code);
	}

}
