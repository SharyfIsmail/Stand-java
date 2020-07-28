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
import stand.t_45.data.DataFromT_45;
import stand.t_45.data.DataFromT_45Model;
import stand.util.IstopWatch;

@Component
public class TurnoverSensorModel implements DataFromT_45Model,DataFromCanModel, IstopWatch <Long>{
	private TurnoverSensor turnoverSensor;
	
	private StringProperty error;
	private Deque<Integer> turnover;
	private Deque<Integer> Torque;
	
	private Deque<Float> turnoverT_45;
	private Deque<Float> TorqueT_45;
	private Deque<Float> tempT_45;
	
	private Deque<Long> turnOverTime;
	private Deque<Long> torqueTime;
	
	private StringProperty turnOverValue;
	private StringProperty TorqueValue;
	private StringProperty tempValue;
	
	private long start;
	private long time ;
	
	private boolean showTurnOver = false;
	private boolean showTorque = false;
	private boolean showTemp = false;
	
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

		turnOverTime = new ConcurrentLinkedDeque<>();
		torqueTime = new ConcurrentLinkedDeque<>();
		
		turnOverValue = new SimpleStringProperty();
		TorqueValue = new  SimpleStringProperty();
		tempValue = new SimpleStringProperty();
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
	public StringProperty getTempValue() {
		return tempValue;
	}
	public void setTurnOverVisible(boolean showTurnOver)
	{
		this.showTurnOver = showTurnOver;
	}
	public void setTorqueVisible(boolean showTorque)
	{
		this.showTorque = showTorque;
	}
	public void setTempVisible(boolean showTemp)
	{
		this.showTemp = showTemp;
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

	@Override
	public void updateModel_T45()
	{
		time = elapsedTime();
		turnOverTime.add(time);
		torqueTime.add(time);
		turnoverT_45.add(turnoverSensor.getTurnoverT_45());
		TorqueT_45.add(turnoverSensor.getTorqueT_45());
		tempT_45.add(turnoverSensor.getTempT_45());
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//if(showTurnOver)
				//{
					turnOverValue.setValue(String.valueOf(turnoverSensor.getTurnoverT_45()));
					
				//}
				
			//	else 
				//	turnOverValue.setValue(" ");
			//	if(showTorque)
			//	{
					TorqueValue.setValue(String.valueOf(turnoverSensor.getTorqueT_45()));
			//	}
				
			//	else 
			//		TorqueValue.setValue(" ");
			//	if(showTemp)
			//	{
					tempValue.setValue(String.valueOf(turnoverSensor.getTempT_45()));
			//	}
				//else
				//{
				//	tempValue.setValue(" ");
				//}
			}
		});
	}

	@Override
	public DataFromT_45 getDataFromT_45() {
		return turnoverSensor;
	}
}
