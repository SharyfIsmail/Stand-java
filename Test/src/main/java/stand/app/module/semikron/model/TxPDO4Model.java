package stand.app.module.semikron.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO4;
import stand.util.StopWatch;

public class TxPDO4Model implements DataFromCanModel {
	private TxPDO4 txPDO4;
	private StopWatch stopWatch;
	private StringProperty phaseCurrent;
	private StringProperty linkVoltageDC;
	private StringProperty controlMode;
	private StringProperty systemWarning;
	private Deque<Float> phaseCurrentQueue;
	private Deque<Float> dcLinkVoltageQueue;
	private Deque<Long> experimentDuration;
	private long time = 0;

	public TxPDO4Model() {
		super();
		txPDO4 = new TxPDO4();
		phaseCurrent = new SimpleStringProperty();
		linkVoltageDC = new SimpleStringProperty();
		controlMode = new SimpleStringProperty();
		systemWarning = new SimpleStringProperty();
		
		phaseCurrentQueue = new ConcurrentLinkedDeque<>();
		dcLinkVoltageQueue = new ConcurrentLinkedDeque<>();
		experimentDuration = new ConcurrentLinkedDeque<>();
		stopWatch = new StopWatch();
	}

	@Override
	public void updateModel() {
		if(stopWatch.isRunning())
		{
			time = stopWatch.getElapsedTime()/1000;
			experimentDuration.add(time);
			phaseCurrentQueue.add(txPDO4.getPhaseCurrent());
			dcLinkVoltageQueue.add(txPDO4.getLinkVoltageDC());
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				phaseCurrent.setValue(String.valueOf(txPDO4.getPhaseCurrent()));

				linkVoltageDC.setValue(String.valueOf(txPDO4.getLinkVoltageDC()));

				if (txPDO4.getControlMode() != null)
					controlMode.setValue(txPDO4.getControlMode());

				systemWarning.setValue("0x".concat(String.valueOf(txPDO4.getSystemWarningByte())));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO4;
	}

	public StringProperty getPhaseCurrent() {
		return phaseCurrent;
	}

	public StringProperty getLinkVoltageDC() {
		return linkVoltageDC;
	}

	public StringProperty getControlMode() {
		return controlMode;
	}

	public StringProperty getSystemWarning() {
		return systemWarning;
	}

	public Deque<Float> getPhaseCurrentQueue()
	{
		return phaseCurrentQueue;
	}
	
	public Deque<Float> getdcLinkVoltageQueue()
	{
		return dcLinkVoltageQueue;
	}
	public Deque<Long> getExperimentDuration()
	{
		return experimentDuration;
	}
	public void clearAllQueue()
	{
		phaseCurrentQueue.clear();
		dcLinkVoltageQueue.clear();
		experimentDuration.clear();
	}
	public StopWatch getStopWatch()
	{
		return stopWatch;
	}
}
