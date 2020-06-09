package stand.app.module.semikron.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.TxPDO1;

public class TxPDO1Model implements DataFromCanModel {
	private TxPDO1 txPDO1;

	private StringProperty inverterLosses;
	private StringProperty inverterState;

	private StringProperty motorLosses;

	private StringProperty lastError;
	private StringProperty digitalOutput1State;
	private StringProperty digitalOutput2State;

	private StringProperty causingError;

	private StringProperty digitalInput1State;
	private StringProperty digitalInput2State;
	private StringProperty limitationMode;
	private StringProperty ascState;
	private StringProperty activeDischargeState;
	private StringProperty spoInput;

	public TxPDO1Model() {
		super();
		txPDO1 = new TxPDO1();

		inverterLosses = new SimpleStringProperty();
		inverterState = new SimpleStringProperty();
		motorLosses = new SimpleStringProperty();
		lastError = new SimpleStringProperty();
		digitalOutput1State = new SimpleStringProperty();
		digitalOutput2State = new SimpleStringProperty();
		causingError = new SimpleStringProperty();
		digitalInput1State = new SimpleStringProperty();
		digitalInput2State = new SimpleStringProperty();
		limitationMode = new SimpleStringProperty();
		ascState = new SimpleStringProperty();
		activeDischargeState = new SimpleStringProperty();
		spoInput = new SimpleStringProperty();
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txPDO1;
	}

	@Override
	public void updateModel() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				inverterLosses.setValue(String.valueOf(txPDO1.getInverterLosses()));

				if (txPDO1.getInverterState() != null)
					inverterState.setValue(txPDO1.getInverterState());

				motorLosses.setValue(String.valueOf(txPDO1.getMotorLosses()));

				if (txPDO1.getLastError() != null)
					lastError.setValue(txPDO1.getLastError());
				if (txPDO1.getDigitalOutput1State() != null)
					digitalOutput1State.setValue(txPDO1.getDigitalOutput1State());
				if (txPDO1.getDigitalOutput2State() != null)
					digitalOutput2State.setValue(txPDO1.getDigitalOutput2State());

				if (txPDO1.getCausingError() != null)
					causingError.setValue(txPDO1.getCausingError());

				if (txPDO1.getDigitalInput1State() != null)
					digitalInput1State.setValue(txPDO1.getDigitalInput1State());
				if (txPDO1.getDigitalOutput2State() != null)
					digitalInput2State.setValue(txPDO1.getDigitalInput2State());
				if (txPDO1.getLimitationMode() != null)
					limitationMode.setValue(txPDO1.getLimitationMode());
				if (txPDO1.getAscState() != null)
					ascState.setValue(txPDO1.getAscState());
				if (txPDO1.getActiveDischargeState() != null)
					activeDischargeState.setValue(txPDO1.getActiveDischargeState());
				if (txPDO1.getSpoInput() != null)
					spoInput.setValue(txPDO1.getSpoInput());

			}
		});
	}

	public StringProperty getInverterLosses() {
		return inverterLosses;
	}

	public StringProperty getInverterState() {
		return inverterState;
	}

	public StringProperty getMotorLosses() {
		return motorLosses;
	}

	public StringProperty getLastError() {
		return lastError;
	}

	public StringProperty getDigitalOutput1State() {
		return digitalOutput1State;
	}

	public StringProperty getDigitalOutput2State() {
		return digitalOutput2State;
	}

	public StringProperty getCausingError() {
		return causingError;
	}

	public StringProperty getDigitalInput1State() {
		return digitalInput1State;
	}

	public StringProperty getDigitalInput2State() {
		return digitalInput2State;
	}

	public StringProperty getLimitationMode() {
		return limitationMode;
	}

	public StringProperty getAscState() {
		return ascState;
	}

	public StringProperty getActiveDischargeState() {
		return activeDischargeState;
	}

	public StringProperty getSpoInput() {
		return spoInput;
	}
}
