package stand.app.module.semikron.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO3;
import stand.util.StopWatch;

public class TxPDO3Model implements DataFromCanModel {

	private TxPDO3 txPDO3;
	private StopWatch stopWatch;
	private StringProperty linkPowerDC;
	private StringProperty mechanicPower;
	private StringProperty motorSpeed;
	private Deque<Short> motorSpeedQueue;
	private Deque<Integer> dcLinkPowerQueue;
	private Deque<Integer> MechanicPowerQueue;

	private Deque<Long> experimentDuration;

	private long time = 0;

	public TxPDO3Model() {
		super();
		txPDO3 = new TxPDO3();
		linkPowerDC = new SimpleStringProperty();
		mechanicPower = new SimpleStringProperty();
		motorSpeed = new SimpleStringProperty();
		motorSpeedQueue = new ConcurrentLinkedDeque<>();
		experimentDuration = new ConcurrentLinkedDeque<>();
		stopWatch = new StopWatch();
	}

	@Override
	public void updateModel() {
		if(!stopWatch.isRunning())
		{
			stopWatch.start();
		}
		time = stopWatch.getElapsedTime()/1000;
		experimentDuration.add(time);
		motorSpeedQueue.add(txPDO3.getMotorSpeed());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				linkPowerDC.setValue(String.valueOf(txPDO3.getLinkPowerDC()));

				mechanicPower.setValue(String.valueOf(txPDO3.getMechanicPower()));

				motorSpeed.setValue(String.valueOf(txPDO3.getMotorSpeed()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO3;
	}

	public StringProperty getLinkPowerDC() {
		return linkPowerDC;
	}

	public StringProperty getMechanicPower() {
		return mechanicPower;
	}

	public StringProperty getMotorSpeed() {
		return motorSpeed;
	}
}
