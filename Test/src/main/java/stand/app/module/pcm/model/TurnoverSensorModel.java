package stand.app.module.pcm.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.pcm.tx.TurnoverSensor;

@Component
public class TurnoverSensorModel implements DataFromCanModel {
	private TurnoverSensor turnoverSensor;
	private StringProperty error;
	private Deque<Integer> turnover;
	private Deque<Integer> Torque;
	private StringProperty turnOverValue;
	private StringProperty TorqueValue;
	private boolean showTurnOver = false;
	private boolean showTorque = false;
	
	public TurnoverSensorModel() {
		super();
		turnoverSensor = new TurnoverSensor();
		error = new SimpleStringProperty();
		turnover = new ConcurrentLinkedDeque<>();
		Torque = new ConcurrentLinkedDeque<>();
		turnOverValue = new SimpleStringProperty();
		TorqueValue = new  SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		if (turnoverSensor.getError() != null)
			error.setValue(turnoverSensor.getError());
		turnover.add(turnoverSensor.getTurnover());
		Torque.add(turnoverSensor.getTorque());
		
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if(showTurnOver)
					turnOverValue.setValue(String.valueOf(turnoverSensor.getTurnover()));
				else 
					turnOverValue.setValue(" ");
				if(showTorque)
					TorqueValue.setValue(String.valueOf(turnoverSensor.getTorque()));
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
}
