package stand.semikron.rx;

/**
 * 
 * User Command to set the desired control mode. E.g. to enable and disable the
 * inverter.
 */
public enum MotorControlModeRx {

	DISABLE(0, "Disable"), TORQUE_CM(1, "Torque control mode"), SPEED_CM(2, "Speed control mode"),
	BRAKE_CHOPPER(4, "Brake chopper"), POSITION_SENSOR_CALIBRATION(5, "Position sensor calibration"),
	TEST_ID_IQ(8, "Test Id/Iq"), TEST_CLOSED_LOOP(9, "Test Closed Loop");

	private final String state;
	private final int priority;

	private MotorControlModeRx(int priority, String state) {
		this.state = state;
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public String getState() {
		return state;
	}
}
