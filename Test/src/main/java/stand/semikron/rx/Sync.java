package stand.semikron.rx;

import stand.can.CanCdr;

public class Sync extends CanCdr {

	private byte[] data;

	public Sync() {
		super();
		setDefaultState();
	}

	/**
	 * freq 100ms; id 0x080; DLC 0;
	 */
	public void setDefaultState() {
		setFreq(100); // 100ms
		setId(128); // 0x080
		setTypeId(TypeId.STD);
		setDLC(0);
	}

	public void setNodeID(byte nodeID) {
		if (nodeID >= 0 && nodeID < 127) {
			setDLC(2);
			data = new byte[2];
			data[1] = nodeID;
		} else {
			throw new NumberFormatException("Node ID должен быть в диапазоне от 0 до 127 (0 для широковещания)");
		}
		setData(data);
	}
}
