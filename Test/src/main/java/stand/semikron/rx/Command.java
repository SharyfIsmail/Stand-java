package stand.semikron.rx;

public enum Command {

	DISABLE_MOTOR_CONTROL(0, true), CLEAR_ERRORS(4, true), STOP_CLEAR_ERRORS(4, false), RESTART_REQUEST(8, true),
	STOP_RESTART_REQUEST(8, false), ACTIVE_DISCHARGE_DISABLE(16, false), ACTIVE_DISCHARGE_ENABLE(16, true),
	LIMITATION_MODE_SYM(32, false), LIMITATION_MODE_ASYM(32, true), DIGITAL_OUTPUT2_LOW(64, false),
	DIGITAL_OUTPUT2_HIGHT(64, true), DIGITAL_OUTPUT1_LOW(-128, false), DIGITAL_OUTPUT1_HIGHT(-128, true);
	;
	private int priority;
	private boolean on;

	private Command(int priority, boolean on) {
		this.priority = priority;
		this.on = on;
	}

	public boolean getOn() {
		return on;
	}

	public int getPriority() {
		return priority;
	}

}
