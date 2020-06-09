package stand.app.module.battery.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.battery.tx.EdlActiveFaultData;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;

public class ActiveFaultDataModel implements DataFromCanModel {

	private EdlActiveFaultData activeFaultData;
	private StringProperty fault;

	public ActiveFaultDataModel() {
		activeFaultData = new EdlActiveFaultData();
		fault = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (activeFaultData.getFaults() != null) {
					fault.setValue(activeFaultData.getFaults());
				}
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return activeFaultData;
	}

	public StringProperty getFault() {
		return fault;
	}
}
