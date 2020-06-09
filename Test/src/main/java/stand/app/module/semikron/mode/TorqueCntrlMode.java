package stand.app.module.semikron.mode;

import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.scene.control.TextField;
import stand.semikron.SemikronService;
import stand.semikron.rx.MotorControlModeRx;

@Component
public class TorqueCntrlMode implements CntrlMode {
	private boolean select = false;
	private TextField maxTorqCntrlTextField;
	private TextField refTorqCntrlTextField;
	private TextField maxSpeedCntrlTextField;
	private TextField refSpeedCntrlTextField;

	@Override
	public String toString() {
		return "Torque Control";
	}

	@Override
	public void selectMode() {
		select = true;
		refSpeedCntrlTextField.setDisable(true);
		maxTorqCntrlTextField.setDisable(true);

		refTorqCntrlTextField.setDisable(false);
		maxSpeedCntrlTextField.setDisable(false);
	}

	@Override
	public boolean isSelected() {
		return select;
	}

	@Override
	public void applyingValues(SemikronService semikron) throws NumberFormatException, IOException {
		short maxSpeed = Short.parseShort(maxSpeedCntrlTextField.getText());
		short refTorque = Short.parseShort(refTorqCntrlTextField.getText());

		if (refTorque < -100 || refTorque > 100) {
			refTorqCntrlTextField.setText(refTorqCntrlTextField.getText() + "!!!!");
			throw new NumberFormatException("torque cannot be " + refTorque + "%");
		}

		semikron.applyPwmValues(MotorControlModeRx.TORQUE_CM, refTorque, maxSpeed);
	}

	@Override
	public void setSpeedTorqueTextField(TextField refTorqCntrlTextField, TextField refSpeedCntrlTextField,
			TextField maxTorqCntrlTextField, TextField maxSpeedCntrlTextField) {
		this.maxTorqCntrlTextField = maxTorqCntrlTextField;
		this.refTorqCntrlTextField = refTorqCntrlTextField;
		this.maxSpeedCntrlTextField = maxSpeedCntrlTextField;
		this.refSpeedCntrlTextField = refSpeedCntrlTextField;
	}
}
