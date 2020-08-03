package stand.app.module.pcm.model;

import java.util.Date;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.pcm.tx.TurnoverSensor;
import stand.t_45.data.DataFromT_45;
import stand.t_45.data.DataFromT_45Model;
import stand.util.StopWatch;

@Component
public class TurnoverSensorModel implements DataFromT_45Model,DataFromCanModel{
	private StopWatch stopWatch;
	private TurnoverSensor turnoverSensor;
	
	private StringProperty error;
	private Deque<Integer> turnover;
	private Deque<Integer> Torque;
	
	private Deque<Float> turnoverT_45;
	private Deque<Float> TorqueT_45;
	private Deque<Float> tempT_45;
	
	private Deque<Long> experimentDuration;
	
	private StringProperty turnOverValue;
	private StringProperty TorqueValue;
	private StringProperty tempValue;
	
	private long time ;
	

	public TurnoverSensorModel() {
		super();
		Date date = new Date();
		date.getTime();
		turnoverSensor = new TurnoverSensor();
		error = new SimpleStringProperty();
		turnover = new ConcurrentLinkedDeque<>();
		Torque = new ConcurrentLinkedDeque<>();
		
		turnoverT_45 = new ConcurrentLinkedDeque<>();
		TorqueT_45= new ConcurrentLinkedDeque<>();
		tempT_45= new ConcurrentLinkedDeque<>();

		experimentDuration = new ConcurrentLinkedDeque<>();
		
		turnOverValue = new SimpleStringProperty();
		TorqueValue = new  SimpleStringProperty();
		tempValue = new SimpleStringProperty();
		stopWatch = new StopWatch();
	}

	@Override
	public void updateModel() {
		if (turnoverSensor.getError() != null)
			error.setValue(turnoverSensor.getError());
		if(!stopWatch.isRunning())
			stopWatch.start();
		time =	stopWatch.getElapsedTime()/ 1000;
		
		experimentDuration.add(time);
		turnover.add(turnoverSensor.getTurnover());
		Torque.add(turnoverSensor.getTorque());
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
		
				turnOverValue.setValue(String.valueOf(turnoverSensor.getTurnover()));
				TorqueValue.setValue(String.valueOf(turnoverSensor.getTorque()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return turnoverSensor;
	}

	public StringProperty getError() {
		return error;
	}

	public Deque<Integer> getTurnover() {
		return turnover;
	}
	public Deque<Float> getTurnoverT_45()
	{
		return turnoverT_45;
	}
	public Deque<Float> getTorqueT_45()
	{
		return TorqueT_45;
	}
	public Deque<Float> getTempT_45()
	{
		return tempT_45;
	}
	public Deque<Integer> getTorque()
	{
		return Torque;
	}

	public Deque<Long> getExperimentDuration()
	{
		return experimentDuration;
	}

	public StringProperty getturnOverValue() {
		return turnOverValue;
	}
	public StringProperty getTorqueValue() {
		return TorqueValue;
	}
	public StringProperty getTempValue() {
		return tempValue;
	}

	@Override
	public void updateModel_T45()
	{

		if(!stopWatch.isRunning())
			stopWatch.start();
		time =	stopWatch.getElapsedTime()/ 1000;
		
		experimentDuration.add(time);
		turnoverT_45.add(turnoverSensor.getTurnoverT_45());
		TorqueT_45.add(turnoverSensor.getTorqueT_45());
		tempT_45.add(turnoverSensor.getTempT_45());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				turnOverValue.setValue(String.valueOf(turnoverSensor.getTurnoverT_45()));
		    	TorqueValue.setValue(String.valueOf(turnoverSensor.getTorqueT_45()));
				tempValue.setValue(String.valueOf(turnoverSensor.getTempT_45()));

			}
		});
	}

	@Override
	public DataFromT_45 getDataFromT_45() {
		return turnoverSensor;
	}
	public TurnoverSensor getTurnoverSensor()
	{
		return turnoverSensor;
	}
	public void ClearAllQueue()
	{
		turnoverT_45.clear();
		TorqueT_45.clear();
		tempT_45.clear();
		experimentDuration.clear();
	}
	public StopWatch getStopWatch()
	{
		return stopWatch;
	}
}
