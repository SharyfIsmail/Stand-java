package stand.semikron.rx;

import stand.can.CanCdr;
import stand.semikron.sdo.SDO;

public class RxSDO extends CanCdr {

	public RxSDO(SDO sdo) {
		super();
		setId(1658);
		setFreq(1000);
		setDLC(8);
		setTypeId(TypeId.STD);
		setData(sdo.request());
	}
}
