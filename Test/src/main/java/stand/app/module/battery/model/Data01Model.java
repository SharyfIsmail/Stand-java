package stand.app.module.battery.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.battery.tx.EdlData01;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;

public class Data01Model implements DataFromCanModel {
	private EdlData01 data01;
	private StringProperty sys_SOC;

	public Data01Model() {
		super();
		data01 = new EdlData01();

		sys_SOC = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				sys_SOC.setValue(String.valueOf(data01.getSys_SOC()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return data01;
	}

	public StringProperty getSys_SOC() {
		return sys_SOC;
	}
}
