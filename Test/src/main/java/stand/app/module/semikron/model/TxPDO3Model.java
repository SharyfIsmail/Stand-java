package stand.app.module.semikron.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO3;

public class TxPDO3Model implements DataFromCanModel {

	private TxPDO3 txPDO3;

	private StringProperty linkPowerDC;
	private StringProperty mechanicPower;
	private StringProperty motorSpeed;

	public TxPDO3Model() {
		super();
		txPDO3 = new TxPDO3();
		linkPowerDC = new SimpleStringProperty();
		mechanicPower = new SimpleStringProperty();
		motorSpeed = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				linkPowerDC.setValue(String.valueOf(txPDO3.getLinkPowerDC()));

				mechanicPower.setValue(String.valueOf(txPDO3.getMechanicPower()));

				motorSpeed.setValue(String.valueOf(txPDO3.getMotorSpeed()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO3;
	}

	public StringProperty getLinkPowerDC() {
		return linkPowerDC;
	}

	public StringProperty getMechanicPower() {
		return mechanicPower;
	}

	public StringProperty getMotorSpeed() {
		return motorSpeed;
	}
}
