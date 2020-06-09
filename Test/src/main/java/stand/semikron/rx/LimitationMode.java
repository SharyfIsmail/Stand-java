package stand.semikron.rx;

public enum LimitationMode {
	SYMMETRIC(0), ASYMMETRIC(1);
	private final int priority;

	private LimitationMode(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
