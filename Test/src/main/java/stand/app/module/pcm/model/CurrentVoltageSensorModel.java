package stand.app.module.pcm.model;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.stereotype.Component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.pcm.tx.CurrentVoltageSensor;

@Component
public class CurrentVoltageSensorModel implements DataFromCanModel {
	private CurrentVoltageSensor currentVoltageSensor;
	private StringProperty error;
	private Deque<Float> current;
	private Deque<Float> voltage;

	public CurrentVoltageSensorModel() {
		super();
		currentVoltageSensor = new CurrentVoltageSensor();
		error = new SimpleStringProperty();
		current = new ConcurrentLinkedDeque<>();
		voltage = new ConcurrentLinkedDeque<>();
	}

	@Override
	public void updateModel() {
		if (currentVoltageSensor.getError() != null)
			error.setValue(currentVoltageSensor.getError());
		current.add(currentVoltageSensor.getCurrent());
		voltage.add(currentVoltageSensor.getVoltage());
	}

	@Override
	public DataFromCan getDataFromCan() {
		return currentVoltageSensor;
	}

	public StringProperty getError() {
		return error;
	}

	public Deque<Float> getCurrent() {
		return current;
	}

	public Deque<Float> getVoltage() {
		return voltage;
	}
}
