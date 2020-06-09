package stand.app.module.semikron.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO2;

public class TxPDO2Model implements DataFromCanModel {
	private TxPDO2 txPDO2;

	private StringProperty torqueAfterLimitation;
	private StringProperty maxAvailableTorque;
	private StringProperty torque;
	private StringProperty cutbackNumber;

	public TxPDO2Model() {
		super();
		txPDO2 = new TxPDO2();

		torqueAfterLimitation = new SimpleStringProperty();
		maxAvailableTorque = new SimpleStringProperty();
		torque = new SimpleStringProperty();
		cutbackNumber = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
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
}
