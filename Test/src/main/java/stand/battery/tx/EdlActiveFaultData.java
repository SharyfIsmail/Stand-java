package stand.battery.tx;

import stand.can.candata.DataFromCan;
import stand.util.BitSetter;

public class EdlActiveFaultData implements DataFromCan {
	private StringBuffer faults;

	public EdlActiveFaultData() {
		super();
		faults = new StringBuffer("");
//		setId(486486368); // 1cff 3160
	}

	public String getFaults() {
		return faults.toString();
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		for (ActiveFaultData active_Fault_Data : ActiveFaultData.values()) {
			int numberOfByte = active_Fault_Data.ordinal() / 8;
			if (BitSetter.getBit(data[numberOfByte], active_Fault_Data.ordinal()) == 1) {
				faults.append(active_Fault_Data.name());
				faults.append(" ");
			}
		}
	}
}
