package stand.app.module.semikron.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO5;

public class TxPDO5Model implements DataFromCanModel {

	private TxPDO5 txPDO5;

	private StringProperty junctionTempOrHighestDCBtemp;
	private StringProperty motorTemp;
	private StringProperty controlStrategy;

	public TxPDO5Model() {
		super();
		txPDO5 = new TxPDO5();

		junctionTempOrHighestDCBtemp = new SimpleStringProperty();
		motorTemp = new SimpleStringProperty();
		controlStrategy = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
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
}
