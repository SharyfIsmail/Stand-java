package stand.app.module.semikron.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO5;
import stand.util.StopWatch;

public class TxPDO5Model implements DataFromCanModel {

	private TxPDO5 txPDO5;
	private StopWatch stopWatch;

	private StringProperty junctionTempOrHighestDCBtemp;
	private StringProperty motorTemp;
	private StringProperty controlStrategy;
	
	private Deque<Integer> motorTempQueue;
	private Deque<Integer> maxJunctionTempQueue;
	private Deque<Long> experimentDuration;
	private long time = 0;

	public TxPDO5Model() {
		super();
		stopWatch = new  StopWatch();
		txPDO5 = new TxPDO5();

		junctionTempOrHighestDCBtemp = new SimpleStringProperty();
		motorTemp = new SimpleStringProperty();
		controlStrategy = new SimpleStringProperty();
		
		motorTempQueue = new ConcurrentLinkedDeque<>();
		maxJunctionTempQueue = new ConcurrentLinkedDeque<>();
		experimentDuration = new ConcurrentLinkedDeque<>();

	}

	@Override
	public void updateModel() {
		
		if(stopWatch.isRunning())
		{
			time = stopWatch.getElapsedTime()/1000;
			experimentDuration.add(time);
			motorTempQueue.add(txPDO5.getMotorTemp());
			maxJunctionTempQueue.add(txPDO5.getJunctionTempOrHighestDCBtemp());
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				junctionTempOrHighestDCBtemp.setValue(String.valueOf(txPDO5.getJunctionTempOrHighestDCBtemp()));

				motorTemp.setValue(String.valueOf(txPDO5.getMotorTemp()));

				if (txPDO5.getControlStrategy() != null)
					controlStrategy.setValue(txPDO5.getControlStrategy());
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO5;
	}

	public StringProperty getJunctionTempOrHighestDCBtemp() {
		return junctionTempOrHighestDCBtemp;
	}

	public StringProperty getMotorTemp() {
		return motorTemp;
	}

	public StringProperty getControlStrategy() {
		return controlStrategy;
	}
	
	public Deque<Integer> getMotorTempQueue()
	{
		return motorTempQueue;
	}
	
	public Deque<Integer> getMaxJunctionTempQueue()
	{
		return maxJunctionTempQueue ;
	}
	
	public Deque<Long> getExperimentDuration()
	{
		return experimentDuration;
	}
	public void clearAllQueue()
	{
		motorTempQueue.clear();
		maxJunctionTempQueue.clear();
		experimentDuration.clear();
	}
	public StopWatch getStopWatch()
	{
		return stopWatch;
	}
}
