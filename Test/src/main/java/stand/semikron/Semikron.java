package stand.semikron;

import java.io.IOException;

import stand.can.Can;
import stand.ethernet.EthernetCan;
import stand.semikron.rx.ActiveDischargeState;
import stand.semikron.rx.DigitalState;
import stand.semikron.rx.LimitationMode;
import stand.semikron.rx.MotorControlModeRx;
import stand.semikron.rx.NetworkManagementMaster;
import stand.semikron.rx.NetworkManagementMaster.NmtCommand;
import stand.semikron.rx.NodeGuardingMaster;
import stand.semikron.rx.RxPDO3;
import stand.semikron.rx.RxSDO;
import stand.semikron.rx.Sync;
import stand.semikron.sdo.ActualCurrentId;
import stand.semikron.sdo.ActualCurrentIq;
import stand.semikron.sdo.ActualUd;
import stand.semikron.sdo.ActualUdqVoltageLenght;
import stand.semikron.sdo.ActualUq;
import stand.semikron.sdo.AnalogIn1;
import stand.semikron.sdo.AnalogIn2;
import stand.semikron.sdo.ReferenceCurrentId;
import stand.semikron.sdo.ReferenceCurrentIq;
import stand.util.DataSender;

public class Semikron implements SemikronService {

	private NetworkManagementMaster nmtMaster;
	private Sync sync;
	private NodeGuardingMaster nodeGuarding;

	private RxSDO analogIn1;
	private RxSDO analogIn2;
	private RxSDO actualIq;
	private RxSDO actualId;
	private RxSDO referenceIq;
	private RxSDO referenceId;
	private RxSDO actualUd;
	private RxSDO actualUq;
	private RxSDO actualUdq;

	private RxPDO3 rxPDO3;
	private MotorControlModeRx motorControlMode;
	private int speed;
	private int torque;
	private LimitationMode limitationMode;
	private DigitalState out1;
	private DigitalState out2;
	private ActiveDischargeState activeDischarge;

	private EthernetCan ethernetCan;
	private DataSender dataSender;

	public Semikron(EthernetCan ethernetCan, DataSender dataSender) {
		super();
		this.ethernetCan = ethernetCan;
		this.dataSender = dataSender;

		nmtMaster = new NetworkManagementMaster();
		sync = new Sync();
		nodeGuarding = new NodeGuardingMaster();

		analogIn1 = new RxSDO(new AnalogIn1());
		analogIn2 = new RxSDO(new AnalogIn2());

		actualIq = new RxSDO(new ActualCurrentIq());
		actualId = new RxSDO(new ActualCurrentId());

		referenceIq = new RxSDO(new ReferenceCurrentIq());
		referenceId = new RxSDO(new ReferenceCurrentId());

		actualUq = new RxSDO(new ActualUq());
		actualUd = new RxSDO(new ActualUd());
		actualUdq = new RxSDO(new ActualUdqVoltageLenght());

		rxPDO3 = new RxPDO3();
		limitationMode = LimitationMode.SYMMETRIC;
		out1 = DigitalState.LOW;
		out2 = DigitalState.LOW;
		activeDischarge = ActiveDischargeState.UnActive;
	}

	private void addCan(Can addingCan) {
		boolean contains = false;
		Can[] cans = ethernetCan.getAllCan();
		for (Can can : cans) {
			if (can.equals(addingCan)) {
				contains = true;
				break;
			}
		}
		if (!contains)
			ethernetCan.addCan(addingCan);
	}

	@Override
	public void openCommunication() throws IOException {

		addCan(nmtMaster);
		addCan(analogIn1);
		addCan(analogIn2);

		addCan(actualIq);
		addCan(actualId);

		addCan(referenceIq);
		addCan(referenceId);

		addCan(actualUq);
		addCan(actualUd);
		addCan(actualUdq);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			ethernetCan.removeCan(nmtMaster);
			ethernetCan.removeCan(analogIn1);
			ethernetCan.removeCan(analogIn2);

			ethernetCan.removeCan(actualIq);
			ethernetCan.removeCan(actualId);

			ethernetCan.removeCan(referenceIq);
			ethernetCan.removeCan(referenceId);

			ethernetCan.removeCan(actualUq);
			ethernetCan.removeCan(actualUd);
			ethernetCan.removeCan(actualUdq);
			throw new IOException(e.getMessage());
		}
	}
	@Override
	public void openSdoCommunication() throws IOException
	{
		addCan(actualIq);
		addCan(actualId);

		addCan(referenceIq);
		addCan(referenceId);

		addCan(actualUq);
		addCan(actualUd);
		addCan(actualUdq);
		try
		{
			dataSender.send(ethernetCan.collectEthernetPacket());
		}catch (Exception e) {
			ethernetCan.removeCan(actualIq);
			ethernetCan.removeCan(actualId);

			ethernetCan.removeCan(referenceIq);
			ethernetCan.removeCan(referenceId);

			ethernetCan.removeCan(actualUq);
			ethernetCan.removeCan(actualUd);
			ethernetCan.removeCan(actualUdq);
			throw new IOException(e.getMessage());
		}
	}
	@Override
	public void closeSdoCommunication() throws IOException
	{
		ethernetCan.removeCan(actualIq);
		ethernetCan.removeCan(actualId);
		ethernetCan.removeCan(referenceIq);
		ethernetCan.removeCan(referenceId);
		ethernetCan.removeCan(actualUq);
		ethernetCan.removeCan(actualUd);
		ethernetCan.removeCan(actualUdq);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			ethernetCan.addCan(actualIq);
			ethernetCan.addCan(actualId);
			ethernetCan.addCan(referenceIq);
			ethernetCan.addCan(referenceId);
			ethernetCan.addCan(actualUq);
			ethernetCan.addCan(actualUd);
			ethernetCan.addCan(actualUdq);
			throw new IOException(e.getMessage());
		}
		ethernetCan.removeCan(nmtMaster);
	}
	@Override
	public void closeCommunication() throws IOException {
		nmtMaster.setCommand(NmtCommand.RESET_COMMUNICATION);

		ethernetCan.removeCan(rxPDO3);

		ethernetCan.removeCan(analogIn1);
		ethernetCan.removeCan(analogIn2);
		ethernetCan.removeCan(actualIq);
		ethernetCan.removeCan(actualId);
		ethernetCan.removeCan(referenceIq);
		ethernetCan.removeCan(referenceId);
		ethernetCan.removeCan(actualUq);
		ethernetCan.removeCan(actualUd);
		ethernetCan.removeCan(actualUdq);

		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			ethernetCan.addCan(rxPDO3);

			ethernetCan.addCan(analogIn1);
			ethernetCan.addCan(analogIn2);
			ethernetCan.addCan(actualIq);
			ethernetCan.addCan(actualId);
			ethernetCan.addCan(referenceIq);
			ethernetCan.addCan(referenceId);
			ethernetCan.addCan(actualUq);
			ethernetCan.addCan(actualUd);
			ethernetCan.addCan(actualUdq);
			throw new IOException(e.getMessage());
		}
		ethernetCan.removeCan(nmtMaster);

	}

	@Override
	public void startNode() throws IOException {

		nmtMaster.setCommand(NmtCommand.START_OPERATIONAL);
		rxPDO3.setDefaulState();
		addCan(rxPDO3);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			ethernetCan.removeCan(nmtMaster);
		} catch (IOException e) {
			nmtMaster.setDefaultState();
			ethernetCan.removeCan(rxPDO3);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void resetNode() throws IOException {

		nmtMaster.setCommand(NmtCommand.RESET_NODE);
		addCan(nmtMaster);
		ethernetCan.removeCan(rxPDO3);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			ethernetCan.removeCan(nmtMaster);
		} catch (IOException e) {
			nmtMaster.setDefaultState();
			ethernetCan.removeCan(nmtMaster);
			addCan(rxPDO3);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void clearError() throws IOException {
		rxPDO3.clearError(true);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			Thread.sleep(200);
		} catch (IOException e) {
			rxPDO3.clearError(false);
			throw new IOException(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rxPDO3.clearError(false);
		dataSender.send(ethernetCan.collectEthernetPacket());
	}

	@Override
	public void applySyncInterval(int interval) throws NumberFormatException, IOException {
		sync.setFreq(interval);
		dataSender.send(ethernetCan.collectEthernetPacket());
	}

	@Override
	public void syncSending(boolean active) throws IOException {
		if (active) {
			addCan(sync);
			try {
				dataSender.send(ethernetCan.collectEthernetPacket());
			} catch (IOException e) {
				ethernetCan.removeCan(sync);
				throw new IOException(e.getMessage());
			}
		} else {
			ethernetCan.removeCan(sync);
			try {
				dataSender.send(ethernetCan.collectEthernetPacket());
			} catch (IOException e) {
				addCan(sync);
				throw new IOException(e.getMessage());
			}
		}
	}

	@Override
	public void nodeGuardingSending(boolean active) throws IOException {
		if (active) {
			addCan(nodeGuarding);
			try {
				dataSender.send(ethernetCan.collectEthernetPacket());
			} catch (IOException e) {
				ethernetCan.removeCan(nodeGuarding);
				throw new IOException(e.getMessage());
			}
		} else {
			ethernetCan.removeCan(nodeGuarding);
			try {
				dataSender.send(ethernetCan.collectEthernetPacket());
			} catch (IOException e) {
				addCan(nodeGuarding);
				throw new IOException(e.getMessage());
			}
		}
	}
	@Override
	public void setActiveDischarge(ActiveDischargeState active) throws IOException
	{
		rxPDO3.setActiveDischarge(active);
		try
		{
			dataSender.send(ethernetCan.collectEthernetPacket());
			this.activeDischarge = active;
		}catch(IOException e)
		{
			rxPDO3.setActiveDischarge(this.activeDischarge);
			throw new IOException(e.getMessage());
		}
	}
	@Override
	public void setLimitationMode(LimitationMode limitationMode) throws IOException {
		rxPDO3.setLimitationMode(limitationMode);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			this.limitationMode = limitationMode;
		} catch (IOException e) {
			rxPDO3.setLimitationMode(this.limitationMode);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void setDigitalOutput1(DigitalState digitalState) throws IOException {
		rxPDO3.setDigitalOutput1(digitalState);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			this.out1 = digitalState;
		} catch (IOException e) {
			rxPDO3.setDigitalOutput1(this.out1);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void setDigitalOutput2(DigitalState digitalState) throws IOException {
		rxPDO3.setDigitalOutput2(digitalState);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			this.out2 = digitalState;
		} catch (IOException e) {
			rxPDO3.setDigitalOutput2(this.out2);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void applyPwmValues(MotorControlModeRx motorControlMode, int torque, int speed)
			throws NumberFormatException, IOException {
		rxPDO3.setControlMode(motorControlMode);
		rxPDO3.setLimitationMode(limitationMode);
		rxPDO3.setDigitalOutput1(out1);
		rxPDO3.setDigitalOutput2(out2);
		rxPDO3.setTorque(torque);
		rxPDO3.setSpeed(speed);
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
			this.torque = torque;
			this.speed = speed;
			this.motorControlMode = motorControlMode;
		} catch (IOException e) {
			rxPDO3.setControlMode(this.motorControlMode);
			rxPDO3.setLimitationMode(limitationMode);
			rxPDO3.setDigitalOutput1(out1);
			rxPDO3.setDigitalOutput2(out2);
			rxPDO3.setTorque(this.torque);
			rxPDO3.setSpeed(this.speed);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void disablePWM() throws IOException {
		rxPDO3.setDefaulState();
		try {
			dataSender.send(ethernetCan.collectEthernetPacket());
		} catch (IOException e) {
			rxPDO3.setControlMode(this.motorControlMode);
			rxPDO3.setLimitationMode(limitationMode);
			rxPDO3.setDigitalOutput1(out1);
			rxPDO3.setDigitalOutput2(out2);
			rxPDO3.setTorque(this.torque);
			rxPDO3.setSpeed(this.speed);
			throw new IOException(e.getMessage());
		}
	}
}
