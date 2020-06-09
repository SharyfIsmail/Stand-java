package stand.app.module.battery.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.battery.tx.EdlData02;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;

public class Data02Model implements DataFromCanModel {
	private EdlData02 data02;
	private StringProperty systemCurrent;
	private StringProperty string1Voltage;

	public Data02Model() {
		super();
		data02 = new EdlData02();

		systemCurrent = new SimpleStringProperty();
		string1Voltage = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				systemCurrent.setValue(String.valueOf(data02.getSystemCurrent()));
				string1Voltage.setValue(String.valueOf(data02.getString1Voltage()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return data02;
	}

	public StringProperty getSystemCurrent() {
		return systemCurrent;
	}

	public StringProperty getString1Voltage() {
		return string1Voltage;
	}

}
