package stand.semikron;

import java.io.IOException;

import stand.semikron.rx.DigitalState;
import stand.semikron.rx.LimitationMode;
import stand.semikron.rx.MotorControlModeRx;

public interface SemikronService {
	/**
	 * Start sending RxSDOs (0x67A)
	 */
	void openCommunication() throws IOException;

	/**
	 * Send NMT Command Reset Communication. Stop sending RxPDO
	 */
	void closeCommunication() throws IOException;

	/**
	 * Send NMT Command Start Operational and start sending default RxPDO. Enter
	 * device into Operational state.
	 */
	void startNode() throws IOException;

	/**
	 * Send NMT Command Reset Node and stop sending RxPDO
	 */
	void resetNode() throws IOException;

	void clearError() throws IOException;

	void applySyncInterval(int interval) throws NumberFormatException, IOException;

	void syncSending(boolean active) throws IOException;

	void nodeGuardingSending(boolean active) throws IOException;

	void setLimitationMode(LimitationMode limitationMode) throws IOException;

	void setDigitalOutput1(DigitalState digitalState) throws IOException;

	void setDigitalOutput2(DigitalState digitalState) throws IOException;

	void applyPwmValues(MotorControlModeRx motorControlMode, int torque, int speed)
			throws NumberFormatException, IOException;
	public void openSdoCommunication() throws IOException;
	public void closeSdoCommunication() throws IOException;

	void disablePWM() throws IOException;
}
