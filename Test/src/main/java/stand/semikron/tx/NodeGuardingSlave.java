package stand.semikron.tx;

import stand.can.candata.DataFromCan;

public class NodeGuardingSlave implements DataFromCan {

	private String nmtState;
	private byte toggle;

	public NodeGuardingSlave() {
		super();
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		byte bi = (byte) (data[0] << 1);
		for (NMTState nmt_State : NMTState.values()) {
			byte xi = (byte) ((nmt_State.getPriority()) << 1);
			if (xi == bi) {
				this.nmtState = nmt_State.getNMTState();
			}
		}
		if (data[0] >> 7 == -1) {
			toggle = 1;
		} else {
			toggle = 0;
		}
	}

	public String getNmtState() {
		return nmtState;
	}

	public byte getToggle() {
		return toggle;
	}

	public enum NMTState {
		BOOTS_UP(0, "BootUp"), STOPPED(4, "Stopped"), PRE_OPERATION(127, "Pre-Operational"),
		OPERATIONAL(5, "Operational");
		private int priority;
		private String nmtState;

		private NMTState(int priority, String nmtState) {
			this.priority = priority;
			this.nmtState = nmtState;
		}

		public int getPriority() {
			return priority;
		}

		public String getNMTState() {
			return nmtState;
		}
	}
}
