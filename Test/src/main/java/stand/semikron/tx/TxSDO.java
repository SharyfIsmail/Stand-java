package stand.semikron.tx;

import java.util.Deque;
import java.util.Map;

import stand.can.candata.DataFromCan;
import stand.semikron.sdo.SDO;
import stand.util.LilEndByteParser;
import stand.util.StopWatch;

public class TxSDO implements DataFromCan {
	private Map<Integer, SDO> objectDictionary;
	private Map<Integer, Deque<Long>> timeQueue;
	private Map<Integer, Deque<? extends Number>> values;
	private StopWatch stopWatch;
	private long time = 0;


	public TxSDO(Map<Integer, SDO> objectDictionary, Map<Integer,Deque<Long>>time,
			Map<Integer,Deque<? extends Number>> values, StopWatch stopWatch) {
		super();
		this.objectDictionary = objectDictionary;
		this.timeQueue = time;
		this.values = values;
		this.stopWatch = stopWatch;
	}

	public void setObjectDictionary(Map<Integer, SDO> objectDictionary) {
		this.objectDictionary = objectDictionary;
	}

	public Map<Integer, SDO> getObjectDictionary() {
		return objectDictionary;
	}

	@Override
	public void parseDataFromCan(byte[] data) {
		byte[] b2 = { data[1], data[2] };
		int index = LilEndByteParser.unsignedIntToInt(b2);
		SDO sdo = objectDictionary.get(index);
		if (sdo != null) {
			byte[] b4 = { data[4], data[5], data[6], data[7] };
			sdo.setValue(b4);
			if(stopWatch.isRunning())
			{
				time = stopWatch.getElapsedTime()/1000;
			}
		}
	}

}
