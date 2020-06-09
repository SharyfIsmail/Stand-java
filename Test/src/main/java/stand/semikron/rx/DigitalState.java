package stand.semikron.rx;

public enum DigitalState {
	LOW(0), HIGH(1);
	private final int priority;

	private DigitalState(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
