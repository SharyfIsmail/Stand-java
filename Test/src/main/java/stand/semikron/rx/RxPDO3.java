package stand.semikron.rx;

import stand.can.CanCdr;
import stand.util.LilEndByteParser;

public class RxPDO3 extends CanCdr {
	private byte[] data;
	// [0,1] {0-13}bit torque {14-15}bit TorqueRefLimVAL;
	// [2,3] Speed;
	// [4] {0-3} byte ControlMode;
	// [5] byte {2-7}bit Command, {0-1}bit SpeedRefLimVal;

	public RxPDO3() {
		super();
		setDefaulState();
	}

	public void setDefaulState() {
		data = new byte[6];
		setData(data);
		setSpeed((short) 0);
		setTorque((short) 0);
		setId(1146);
		setTypeId(TypeId.STD);
		setFreq(100);
	}

	public void setTorque(int torque) {
		int canTorque = torque * 50 + 5000;
		byte[] b2 = LilEndByteParser.intToUnsignedInt(canTorque);
		System.arraycopy(b2, 0, data, 0, 2);
		setData(data);
	}

	public void setSpeed(int speed) {
		int canSpeed = speed + 32768;
		byte[] b2 = LilEndByteParser.intToUnsignedInt(canSpeed);
		System.arraycopy(b2, 0, data, 2, 2);
		setData(data);
	}

	public void clearError(boolean active) {
		if (active)
			setCommand(Command.CLEAR_ERRORS);
		else
			setCommand(Command.STOP_CLEAR_ERRORS);
	}

	public void setDigitalOutput1(DigitalState digitalState) {
		if (digitalState.equals(DigitalState.LOW))
			setCommand(Command.DIGITAL_OUTPUT1_LOW);
		else
			setCommand(Command.DIGITAL_OUTPUT1_HIGHT);
	}

	public void setDigitalOutput2(DigitalState digitalState) {
		if (digitalState.equals(DigitalState.LOW))
			setCommand(Command.DIGITAL_OUTPUT2_LOW);
		else
			setCommand(Command.DIGITAL_OUTPUT2_HIGHT);
	}

	public void setLimitationMode(LimitationMode limitationMode) {
		if (limitationMode.equals(LimitationMode.SYMMETRIC))
			setCommand(Command.LIMITATION_MODE_SYM);
		else
			setCommand(Command.LIMITATION_MODE_ASYM);
	}

	public void setControlMode(MotorControlModeRx motorControlModRx) {
		data[4] = (byte) motorControlModRx.getPriority();
		setData(data);
	}

	public void setActiveDischarge(ActiveDischargeState active) {
		if (active.equals(ActiveDischargeState.Active))
			setCommand(Command.ACTIVE_DISCHARGE_ENABLE);
		else
			setCommand(Command.ACTIVE_DISCHARGE_DISABLE);
	}

	public void setCommand(Command command) {
		if (command.getPriority() == 0) {
			data[5] = 0;
		} else {
			if (command.getOn()) {
				data[5] |= (byte) command.getPriority();
			} else {
				data[5] &= (byte) ~command.getPriority();
			}
		}
		setData(data);
	}
}
