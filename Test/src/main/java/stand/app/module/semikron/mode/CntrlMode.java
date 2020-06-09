package stand.app.module.semikron.mode;

import java.io.IOException;

import javafx.scene.control.TextField;
import stand.semikron.SemikronService;

public interface CntrlMode {
	public void setSpeedTorqueTextField(TextField refTorqCntrlTextField, TextField refSpeedCntrlTextField,
			TextField maxTorqCntrlTextField, TextField maxSpeedCntrlTextField);

	public void selectMode();

	public boolean isSelected();

	public void applyingValues(SemikronService semikron) throws NumberFormatException, IOException;
}
