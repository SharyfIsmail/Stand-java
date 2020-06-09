package stand.battery.tx;

import stand.can.candata.DataFromCan;

public class EdlData04 implements DataFromCan {

	private Exec_Contactor_Status exec_0_Contactor_Status;
	private Exec_Contactor_Status exec_1_Contactor_Status;
	private Exec_Contactor_Status exec_2_Contactor_Status;
	private Exec_Contactor_Status exec_3_Contactor_Status;
	private Exec_Contactor_Status exec_4_Contactor_Status;
	private Exec_Contactor_Status exec_5_Contactor_Status;
	private Exec_Contactor_Status exec_6_Contactor_Status;
	private Exec_Contactor_Status exec_7_Contactor_Status;

	@Override
	public void parseDataFromCan(byte[] data) {
		exec_0_Contactor_Status = new Exec_Contactor_Status(data[0]);
		exec_1_Contactor_Status = new Exec_Contactor_Status(data[1]);
		exec_2_Contactor_Status = new Exec_Contactor_Status(data[2]);
		exec_3_Contactor_Status = new Exec_Contactor_Status(data[3]);
		exec_4_Contactor_Status = new Exec_Contactor_Status(data[4]);
		exec_5_Contactor_Status = new Exec_Contactor_Status(data[5]);
		exec_6_Contactor_Status = new Exec_Contactor_Status(data[6]);
		exec_7_Contactor_Status = new Exec_Contactor_Status(data[7]);
	}

	public Exec_Contactor_Status getExec_0_Contactor_Status() {
		return exec_0_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_1_Contactor_Status() {
		return exec_1_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_2_Contactor_Status() {
		return exec_2_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_3_Contactor_Status() {
		return exec_3_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_4_Contactor_Status() {
		return exec_4_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_5_Contactor_Status() {
		return exec_5_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_6_Contactor_Status() {
		return exec_6_Contactor_Status;
	}

	public Exec_Contactor_Status getExec_7_Contactor_Status() {
		return exec_7_Contactor_Status;
	}

	public class Exec_Contactor_Status {
		private boolean stuck_Check_in_Progress;
		private boolean iso_Check_in_Progress;
		private boolean current_Limit_Active;
		private boolean precharge_Status;
		private boolean cont_A_Status;
		private boolean cont_B_Status;
		private boolean cont_1_Status;
		private boolean cont_2_Status;

		public Exec_Contactor_Status(byte exec_Contactor_Status) {
			stuck_Check_in_Progress = getValue(exec_Contactor_Status, 0);
			iso_Check_in_Progress = getValue(exec_Contactor_Status, 1);
			current_Limit_Active = getValue(exec_Contactor_Status, 2);
			precharge_Status = getValue(exec_Contactor_Status, 3);
			cont_A_Status = getValue(exec_Contactor_Status, 4);
			cont_B_Status = getValue(exec_Contactor_Status, 5);
			cont_1_Status = getValue(exec_Contactor_Status, 6);
			cont_2_Status = getValue(exec_Contactor_Status, 7);
		}

		private boolean getValue(byte exec_Contactor_Status, int i) {
			byte b = (byte) ((exec_Contactor_Status >> i) << 7);
			if (b == -128) {
				return true;
			} else {
				return false;
			}
		}

		public boolean isStuck_Check_in_Progress() {
			return stuck_Check_in_Progress;
		}

		public boolean isIso_Check_in_Progress() {
			return iso_Check_in_Progress;
		}

		public boolean isCurrent_Limit_Active() {
			return current_Limit_Active;
		}

		public boolean isPrecharge_Status() {
			return precharge_Status;
		}

		public boolean isCont_A_Status() {
			return cont_A_Status;
		}

		public boolean isCont_B_Status() {
			return cont_B_Status;
		}

		public boolean isCont_1_Status() {
			return cont_1_Status;
		}

		public boolean isCont_2_Status() {
			return cont_2_Status;
		}

	}
}
