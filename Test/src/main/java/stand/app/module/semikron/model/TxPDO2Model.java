package stand.app.module.semikron.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO2;
import stand.util.StopWatch;

public class TxPDO2Model implements DataFromCanModel {
	private TxPDO2 txPDO2;
	private StopWatch stopWatch;

	
	private StringProperty torqueAfterLimitation;
	private StringProperty maxAvailableTorque;
	private StringProperty torque;
	private StringProperty cutbackNumber;
	private Deque<Float> referenceTorqueQueue;
	private Deque<Float> actualTorqueQueue;
	private Deque<Float> maxAvailableTorqueQueue;
	private Deque<Long> experimentDuration;
	private long time = 0;


	public TxPDO2Model() {
		super();
		txPDO2 = new TxPDO2();

		torqueAfterLimitation = new SimpleStringProperty();
		maxAvailableTorque = new SimpleStringProperty();
		torque = new SimpleStringProperty();
		cutbackNumber = new SimpleStringProperty();
		
		referenceTorqueQueue = new ConcurrentLinkedDeque<>();
		actualTorqueQueue = new ConcurrentLinkedDeque<>();
		maxAvailableTorqueQueue = new ConcurrentLinkedDeque<>();
		experimentDuration = new ConcurrentLinkedDeque<>();
		stopWatch = new StopWatch();

	}

	@Override
	public void updateModel() {
		if(stopWatch.isRunning())
		{
			time = stopWatch.getElapsedTime()/1000;
			experimentDuration.add(time);
			referenceTorqueQueue.add(txPDO2.getRefTorqueAfterLimitation());
			actualTorqueQueue.add(txPDO2.getActTorque());
			maxAvailableTorqueQueue.add(txPDO2.getMaxAvailableTorque());
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				torqueAfterLimitation.setValue(String.valueOf(txPDO2.getRefTorqueAfterLimitation()));
				maxAvailableTorque.setValue(String.valueOf(txPDO2.getMaxAvailableTorque()));
				torque.setValue(String.valueOf(txPDO2.getActTorque()));

				if (txPDO2.getCutbackNumber() != null)
					cutbackNumber.setValue(txPDO2.getCutbackNumber());
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO2;
	}

	public StringProperty getTorqueAfterLimitation() {
		return torqueAfterLimitation;
	}

	public StringProperty getMaxAvailableTorque() {
		return maxAvailableTorque;
	}

	public StringProperty getTorque() {
		return torque;
	}

	public StringProperty getCutbackNumber() {
		return cutbackNumber;
	}
	public Deque<Float> getReferenceTorqueQueue()
	{
		return referenceTorqueQueue;
	}
	
	public Deque<Float> getMaxAvailableTorqueQueue()
	{
		return maxAvailableTorqueQueue;
	}
	
	public Deque<Float> getActualTorqueQueue()
	{
		return actualTorqueQueue ;
	}
	
	public Deque<Long> getExperimentDuration()
	{
		return experimentDuration;
	}
	
	public void clearAllQueue()
	{
		referenceTorqueQueue.clear();
		actualTorqueQueue.clear();
		maxAvailableTorqueQueue.clear();
		experimentDuration.clear();
	}
	public StopWatch getStopWatch()
	{
		return stopWatch;
	}
	
}
