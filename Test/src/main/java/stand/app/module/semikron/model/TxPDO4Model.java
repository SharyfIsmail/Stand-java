package stand.app.module.semikron.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO4;

public class TxPDO4Model implements DataFromCanModel {
	private TxPDO4 txPDO4;

	private StringProperty phaseCurrent;
	private StringProperty linkVoltageDC;
	private StringProperty controlMode;
	private StringProperty systemWarning;

	public TxPDO4Model() {
		super();
		txPDO4 = new TxPDO4();
		phaseCurrent = new SimpleStringProperty();
		linkVoltageDC = new SimpleStringProperty();
		controlMode = new SimpleStringProperty();
		systemWarning = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				phaseCurrent.setValue(String.valueOf(txPDO4.getPhaseCurrent()));

				linkVoltageDC.setValue(String.valueOf(txPDO4.getLinkVoltageDC()));

				if (txPDO4.getControlMode() != null)
					controlMode.setValue(txPDO4.getControlMode());

				systemWarning.setValue("0x".concat(String.valueOf(txPDO4.getSystemWarningByte())));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO4;
	}

	public StringProperty getPhaseCurrent() {
		return phaseCurrent;
	}

	public StringProperty getLinkVoltageDC() {
		return linkVoltageDC;
	}

	public StringProperty getControlMode() {
		return controlMode;
	}

	public StringProperty getSystemWarning() {
		return systemWarning;
	}

}
