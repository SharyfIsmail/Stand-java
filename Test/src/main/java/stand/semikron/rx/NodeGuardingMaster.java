package stand.semikron.rx;

import stand.can.CanCdr;

public class NodeGuardingMaster extends CanCdr {
	private byte[] data;

	public NodeGuardingMaster() {
		super();
		setDefaultState();
	}

	/**
	 * freq 500; NodeId 122(id = 77a); DLC = 1;
	 */
	public void setDefaultState() {
		setFreq(500); // 500ms
		setTypeId(TypeId.STD); // std
		setNodeID((byte) 122); // 77a
		data = new byte[1];
		setData(data); // DLC=1 data={}
	}

	public void setNodeID(byte nodeID) {
		if (nodeID >= 0 && nodeID < 127) {
			setId(nodeID + 1792);
		} else {
			throw new NumberFormatException("Node ID должен быть в диапазоне от 0 до 127 (0 для широковещания)");
		}
	}
}
