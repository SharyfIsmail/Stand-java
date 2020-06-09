package stand.battery.rx;

import stand.can.CanCdr;

public class PcuContactorReq extends CanCdr {

	private byte[] data = new byte[3]; // [0] byte MSB;
										// [1] byte LSB;
										// [2] byte PCU_Commands;

	/**
	 * default state freq=200ms, id=18ff0203h, typeId=4, data{00,00,00};
	 */
	public PcuContactorReq() {
		super();
		setFreq(200); // 200ms
		setId(419365379); // 18ff0203h
		setTypeId(TypeId.EXTD); // extd
		setData(data);
	}

	public void setMsb(MSB MSB) {
		data[0] = (byte) MSB.getPriority();
		setData(data);
	}

	public void setLsb(LSB LSB) {
		data[1] = (byte) LSB.getPriority();
		setData(data);
	}

	public void setPcu_Commands(PCU_Commands PCU_Commands) {
		data[2] = (byte) PCU_Commands.getPriority();
		setData(data);
	}

	////// Data0///////
	public enum LSB {
		ZERO(0), CONTACTOR_REQUEST_PACK_0(1), CONTACTOR_REQUEST_PACK_1(2), CONTACTOR_REQUEST_PACK_2(4),
		CONTACTOR_REQUEST_PACK_3(8), CONTACTOR_REQUEST_PACK_4(16), CONTACTOR_REQUEST_PACK_5(32),
		CONTACTOR_REQUEST_PACK_6(64), CONTACTOR_REQUEST_PACK_7(128);
		private int priority;

		private LSB(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}

	////// Data1///////
	public enum MSB {
		ZERO(0), CONTACTOR_REQUEST_PACK_8(1), CONTACTOR_REQUEST_PACK_9(2), CONTACTOR_REQUEST_PACK_10(4),
		CONTACTOR_REQUEST_PACK_11(8), CONTACTOR_REQUEST_PACK_12(16);
		private int priority;

		private MSB(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}

	////// Data2///////
	public enum PCU_Commands {
		ZERO(0), PCU_FAULT(1), PCU_CRITICAL_FAULT(16), EXEC_SEND_LOCAL_DATA_ONLY(32), EXT_ISO_ENABLE(64),
		FAN_REQUEST(128);
		private int priority;

		private PCU_Commands(int priority) {
			this.priority = priority;
		}

		public int getPriority() {
			return priority;
		}
	}

}
