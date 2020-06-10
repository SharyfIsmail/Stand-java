package stand.app.module.pcm.model;

import java.time.LocalTime;
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
import stand.util.IstopWatch;

@Component
public class TurnoverSensorModel implements DataFromCanModel, IstopWatch <Long>{
	private TurnoverSensor turnoverSensor;
	private StringProperty error;
	private Deque<Integer> turnover;
	private Deque<Integer> Torque;
	private Deque<Long> turnOverTime;
	private Deque<Long> torqueTime;
	private StringProperty turnOverValue;
	private StringProperty TorqueValue;
	private long start;
	private long time ;
	private boolean showTurnOver = false;
	private boolean showTorque = false;
	
	public TurnoverSensorModel() {
		super();
		Date date = new Date();
		date.getTime();
		turnoverSensor = new TurnoverSensor();
		error = new SimpleStringProperty();
		turnover = new ConcurrentLinkedDeque<>();
		Torque = new ConcurrentLinkedDeque<>();
		turnOverTime = new ConcurrentLinkedDeque<>();
		torqueTime = new ConcurrentLinkedDeque<>();
		turnOverValue = new SimpleStringProperty();
		TorqueValue = new  SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		if (turnoverSensor.getError() != null)
			error.setValue(turnoverSensor.getError());
		time = elapsedTime();
		turnOverTime.add(time);
		torqueTime.add(time);
		turnover.add(turnoverSensor.getTurnover());
		Torque.add(turnoverSensor.getTorque());
		
		
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if(showTurnOver)
				{
					turnOverValue.setValue(String.valueOf(turnoverSensor.getTurnover()));
					
				}
				
				else 
					turnOverValue.setValue(" ");
				if(showTorque)
				{
					TorqueValue.setValue(String.valueOf(turnoverSensor.getTorque()));
				}
				
				else 
					TorqueValue.setValue(" ");
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
	public Deque<Integer> getTorque()
	{
		return Torque;
	}
	public Deque<Long> getTorqueTime()
	{
		return torqueTime;
	}
	public Deque<Long> getTurnoverTime()
	{
		return turnOverTime;
	}
	public StringProperty getturnOverValue() {
		return turnOverValue;
	}
	public StringProperty getTorqueValue() {
		return TorqueValue;
	}
	public void setTurnOverVisible(boolean showTurnOver)
	{
		this.showTurnOver = showTurnOver;
	}
	public void setTorqueVisible(boolean showTorque)
	{
		this.showTorque = showTorque;
	}

	@Override
	public Long elapsedTime() {
		 long now = System.currentTimeMillis();
	        return  ((now - start) / 1000);
	}

	@Override
	public void Stopwatch() {
		start = System.currentTimeMillis();			
	}

	@Override
	public boolean isRunning() {
		if(start > 0)
			return true;
		return false;
	}
	public void setStart(long start) {
		this.start = start;
	}
}
