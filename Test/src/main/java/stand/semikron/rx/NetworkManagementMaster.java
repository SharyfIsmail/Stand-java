package stand.semikron.rx;

import stand.can.CanCdr;

public class NetworkManagementMaster extends CanCdr {

	private byte[] data; // byte[0] command
							// byte[1] nodeId (Broadcast=0)

	public NetworkManagementMaster() {
		super();
		setDefaultState();
	}

	/**
	 * freq 1; id 0; command 0(no command); NodeId Broadcast=0;
	 */
	public void setDefaultState() {
		setFreq(1);
		setId(0);
		setTypeId(TypeId.STD);
		data = new byte[2];
		setData(data);
	}

	public void setCommand(NmtCommand nmtCommand) {
		data[0] = (byte) nmtCommand.getPriority();
		setData(data);
	}

	public void setNodeID(byte nodeID) {
		if (nodeID >= 0 && nodeID < 127) {
			data[1] = nodeID;
		} else {
			throw new NumberFormatException("Node ID должен быть в диапазоне от 0 до 127 (0 для широковещания)");
		}
		setData(data);
	}

	public enum NmtCommand {
		START_OPERATIONAL(1), STOP(2), PRE_OPERATION(128), RESET_NODE(129), RESET_COMMUNICATION(130);
		private int priority;

		private NmtCommand(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}
}
