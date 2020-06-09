package stand.app.module.semikron.mode;

import java.io.IOException;

import org.springframework.stereotype.Component;

import javafx.scene.control.TextField;
import stand.semikron.SemikronService;
import stand.semikron.rx.MotorControlModeRx;

@Component
public class SpeedCntrlMode implements CntrlMode {
	private boolean select = false;
	private TextField maxTorqCntrlTextField;
	private TextField refTorqCntrlTextField;
	private TextField maxSpeedCntrlTextField;
	private TextField refSpeedCntrlTextField;

	@Override
	public String toString() {
		return "Speed Control";
	}

	@Override
	public void selectMode() {
		select = true;
		refTorqCntrlTextField.setDisable(true);
		maxSpeedCntrlTextField.setDisable(true);

		refSpeedCntrlTextField.setDisable(false);
		maxTorqCntrlTextField.setDisable(false);
	}

	@Override
	public boolean isSelected() {
		return select;
	}

	@Override
	public void applyingValues(SemikronService semikron) throws NumberFormatException, IOException {
		short maxTorque = Short.parseShort(maxTorqCntrlTextField.getText());
		short refSpeed = Short.parseShort(refSpeedCntrlTextField.getText());

		if (maxTorque < 0 || maxTorque > 100) {
			maxTorqCntrlTextField.setText(maxTorqCntrlTextField.getText() + "!!!!");
			throw new NumberFormatException("max torque cannot be " + maxTorque + "%");
		}

		semikron.applyPwmValues(MotorControlModeRx.SPEED_CM, maxTorque, refSpeed);
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
