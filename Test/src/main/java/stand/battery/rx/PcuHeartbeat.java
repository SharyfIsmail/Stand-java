package stand.battery.rx;

import stand.can.CanCdr;

public class PcuHeartbeat extends CanCdr {

	public PcuHeartbeat() {
		super();
		setFreq(200); // 200ms
		setId(419365395); // 18ff0213h
		setTypeId(TypeId.EXTD); // extd
		setData(new byte[2]); // {00 , 00}
	}
}
