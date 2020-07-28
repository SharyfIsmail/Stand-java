package stand.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import stand.app.module.battery.model.BatteryDataMonitor;
import stand.app.module.pcm.PcmDataMonitor;
import stand.app.module.pcm.PcmLineChartUpdater;
import stand.app.module.semikron.mode.CntrlMode;
import stand.app.module.semikron.mode.SpeedCntrlMode;
import stand.app.module.semikron.mode.TorqueCntrlMode;
import stand.app.module.semikron.model.SemikronDataMonitor;
import stand.app.thread.ReceiveThread;
import stand.app.thread.SensorComunicationThread;
import stand.battery.BatteryService;
import stand.charger.ChargerService;
import stand.semikron.SemikronService;
import stand.semikron.rx.DigitalState;
import stand.semikron.rx.LimitationMode;
import stand.util.ExcelPcmFileWriter;
import stand.util.PcmFileWriter;
import stand.util.T_45Exception;
import stand.util.WinUsbDataReceiver;

@Component
public class MainController implements Initializable {

	@Autowired
	private SemikronService semikron;
	@Autowired
	private SemikronDataMonitor semikronDataMonitor;
	String warningMessage = "0x01 Cutback limiter is active. The warning is only indicated in enabled state if the limitation is really applied\n"
			+ "0x02 Reference limits corrected\n" + "0x04 The actual speed is higher than the reference limits\n"
			+ "0x08 PSM/IPM Iq reduced to zero. In field ctrl the maximum speed with the actual DC link voltage is reached\n"
			+ "0x10 Position sensor default offset\n";

	@Autowired
	private ReceiveThread receiveThread;
	/*
	 * SEMIKRON FIELDS
	 */

	// CANOPEN COMMAND

	@FXML
	Button openComButton;
	@FXML
	Button closeComButton;
	@FXML
	TextField intervalTextField;

	@FXML
	Button startNodeButton;
	@FXML
	Button resetNodeButton;
	@FXML
	CheckBox nodeGuarding;
	@FXML
	CheckBox syncButton;
	//fucj
	// CONTROL MODE
	@FXML
	CheckBox digOut1CheckBox;
	@FXML
	CheckBox digOut2CheckBox;
	@FXML
	CheckBox asymLimitsCheckBox;

	@FXML
	ComboBox<CntrlMode> modeBox;

	@Autowired
	private TorqueCntrlMode torqueCntrlMode;
	@Autowired
	private SpeedCntrlMode speedCntrlMode;

	private ObservableList<CntrlMode> mode;
	private boolean enablePWMClick;

	@FXML
	Button enableDisablePWMButton;
	@FXML
	Button applyValuesButton;
	@FXML
	TextField refTorqCntrlTextField;
	@FXML
	TextField maxTorqCntrlTextField;
	@FXML
	TextField refSpeedCntrlTextField;
	@FXML
	TextField maxSpeedCntrlTextField;

	// STATE INFO

	@FXML
	Label nmtStateLabel;
	@FXML
	Label ascStateLabel;
	@FXML
	Label activeDischargeLabel;

	@FXML
	Label inverterStateLabel;
	@FXML
	Label motorControlModeLabel;
	@FXML
	Label controlStrategyLabel;
	@FXML
	Label limitationModeLabel;

	// ERROR AND WARNING INFO
	@FXML
	Label systemWarningLabel;
	@FXML
	Label lastErrorLabel;
	@FXML
	Label causingErrorLabel;

	// ACTUAL VALUES
	@FXML
	Label phaseCurrentLabel;
	@FXML
	Label dcLinkVoltageLabel;
	@FXML
	Label speedLabel;
	@FXML
	Label refTorqueLabel;
	@FXML
	Label actTorqueLabel;
	@FXML
	Label maxAvalTorqueLabel;
	@FXML
	Label dcLinkPowerLabel;
	@FXML
	Label mechPowerLabel;
	@FXML
	Label spoInputLabel;

	@FXML
	Label maxJuncTempLabel;
	@FXML
	Label motorTempLabel;
	@FXML
	Label digIn1Label;
	@FXML
	Label digIn2Label;
	@FXML
	Label digOut1Label;
	@FXML
	Label digOut2Label;
	@FXML
	Label motorLossesLabel;
	@FXML
	Label inverterLossesLabel;
	@FXML
	Label analogIn1Label;
	@FXML
	Label analogIn2Label;

	// DETAILS
	@FXML
	Label iqActual;
	@FXML
	Label idActual;
	@FXML
	Label iqReference;
	@FXML
	Label idReference;
	@FXML
	Label uqActual;
	@FXML
	Label udActual;
	@FXML
	Label udqActual;
	@FXML
	Label turnOverValue;
	@FXML
	Label torqueValue;

	/*
	 * SEMIKRON FIELDS END
	 */

	/*
	 * BATTERY FIELDS
	 */
	@Autowired
	private BatteryDataMonitor batteryDataMonitor;
	@Autowired
	private BatteryService battery;
	@Autowired
	private ChargerService charger;

	@FXML
	RadioButton batteryOn;
	@FXML
	RadioButton batteryOff;
	@FXML
	RadioButton chargerOn;
	@FXML
	RadioButton chargerOff;

	@FXML
	Label historyFaultLabel;

	@FXML
	Label minCvLabel;

	@FXML
	Label maxCvLabel;

	@FXML
	Label string1VoltageLabel;

	@FXML
	Label minPackTempLabel;

	@FXML
	Label maxPackTempLabel;

	@FXML
	Label sysSocLabel;

	@FXML
	Label systemCurrentLabel;

	@FXML
	Label bmsContactorConditionLabel;
	/*
	 * BATTERY FIELDS END
	 */

	/*
	 * PCM FIELDS
	 */
	@Autowired
	PcmDataMonitor pcmDataMonitor;

	@FXML
	LineChart<Number, Number> lineChartPCM;
	PcmLineChartUpdater<Number> pcmLineChartUpdater;

	@FXML
	RadioButton turnoverRadioButton;
	@FXML
	RadioButton torqueRadioButton;
	

	@FXML
	Label currentVoltageSensorErrorLabel;
	@FXML
	Label turnoverSensorErrorLabel;

	/*
	 * PCM FIELDS END
	 */
	@FXML
	Button ConnectToT_45Button;
	@FXML
	private boolean ConnectionButtonIsPressed = false;
	@Autowired
	SensorComunicationThread sensorComunicationThread;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		receiveThread.start();
		sensorComunicationThread.start();
		
		//////////////// SEMIKRON/////////////////

		// control mode
		mode = FXCollections.observableArrayList(torqueCntrlMode, speedCntrlMode);
		for (CntrlMode m : mode) {
			m.setSpeedTorqueTextField(refTorqCntrlTextField, refSpeedCntrlTextField, maxTorqCntrlTextField,
					maxSpeedCntrlTextField);
		}
		modeBox.setItems(mode);
		modeBox.setValue(mode.get(0));
		mode.get(0).selectMode();

		// state info
		bind(nmtStateLabel, semikronDataMonitor.getNodeGuardingSlaveModel().getNmtState());
		bind(ascStateLabel, semikronDataMonitor.getTxPDO1().getAscState());
		bind(activeDischargeLabel, semikronDataMonitor.getTxPDO1().getActiveDischargeState());
		bind(inverterStateLabel, semikronDataMonitor.getTxPDO1().getInverterState());
		bind(motorControlModeLabel, semikronDataMonitor.getTxPDO4().getControlMode());
		bind(controlStrategyLabel, semikronDataMonitor.getTxPDO5().getControlStrategy());
		bind(limitationModeLabel, semikronDataMonitor.getTxPDO1().getLimitationMode());

		// error and warning info
		bind(systemWarningLabel, semikronDataMonitor.getTxPDO4().getSystemWarning());
		bind(lastErrorLabel, semikronDataMonitor.getTxPDO1().getLastError());
		bind(causingErrorLabel, semikronDataMonitor.getTxPDO1().getCausingError());

		// actual values
		bind(phaseCurrentLabel, semikronDataMonitor.getTxPDO4().getPhaseCurrent());
		bind(dcLinkVoltageLabel, semikronDataMonitor.getTxPDO4().getLinkVoltageDC());
		bind(speedLabel, semikronDataMonitor.getTxPDO3().getMotorSpeed());

		bind(refTorqueLabel, semikronDataMonitor.getTxPDO2().getTorqueAfterLimitation());
		bind(actTorqueLabel, semikronDataMonitor.getTxPDO2().getTorque());
		bind(maxAvalTorqueLabel, semikronDataMonitor.getTxPDO2().getMaxAvailableTorque());

		bind(dcLinkPowerLabel, semikronDataMonitor.getTxPDO3().getLinkPowerDC());
		bind(mechPowerLabel, semikronDataMonitor.getTxPDO3().getMechanicPower());
		bind(spoInputLabel, semikronDataMonitor.getTxPDO1().getSpoInput());

		bind(maxJuncTempLabel, semikronDataMonitor.getTxPDO5().getJunctionTempOrHighestDCBtemp());
		bind(motorTempLabel, semikronDataMonitor.getTxPDO5().getMotorTemp());
		bind(digIn1Label, semikronDataMonitor.getTxPDO1().getDigitalInput1State());
		bind(digIn2Label, semikronDataMonitor.getTxPDO1().getDigitalInput2State());

		bind(digOut1Label, semikronDataMonitor.getTxPDO1().getDigitalOutput1State());
		bind(digOut2Label, semikronDataMonitor.getTxPDO1().getDigitalOutput2State());
		bind(motorLossesLabel, semikronDataMonitor.getTxPDO1().getMotorLosses());
		bind(inverterLossesLabel, semikronDataMonitor.getTxPDO1().getInverterLosses());

		bind(analogIn1Label, semikronDataMonitor.getTxSDO().getAnalogIn1());
		bind(analogIn2Label, semikronDataMonitor.getTxSDO().getAnalogIn2());

		// details
		bind(iqActual, semikronDataMonitor.getTxSDO().getActualIq());
		bind(idActual, semikronDataMonitor.getTxSDO().getActualId());
		bind(iqReference, semikronDataMonitor.getTxSDO().getReferenceIq());
		bind(idReference, semikronDataMonitor.getTxSDO().getReferenceId());

		bind(uqActual, semikronDataMonitor.getTxSDO().getActualUq());
		bind(udActual, semikronDataMonitor.getTxSDO().getActualUd());
		bind(udqActual, semikronDataMonitor.getTxSDO().getActualUdq());
		//////////////// SEMIKRON END/////////////////

		//////////////// BATTERY /////////////////
		bind(historyFaultLabel, batteryDataMonitor.getActiveFaultDataModel().getFault());

		bind(minCvLabel, batteryDataMonitor.getData00Model().getMin_CV());
		bind(maxCvLabel, batteryDataMonitor.getData00Model().getMax_CV());
		bind(minPackTempLabel, batteryDataMonitor.getData00Model().getMin_Pack_Temp());
		bind(maxPackTempLabel, batteryDataMonitor.getData00Model().getMax_Pack_Temp());
		bind(bmsContactorConditionLabel, batteryDataMonitor.getData00Model().getBms_Contactor_Condition());

		bind(string1VoltageLabel, batteryDataMonitor.getData02Model().getString1Voltage());
		bind(systemCurrentLabel, batteryDataMonitor.getData02Model().getSystemCurrent());

		bind(sysSocLabel, batteryDataMonitor.getData01Model().getSys_SOC());
		sensorComunicationThread.setButton(ConnectToT_45Button);
		//////////////// BATTERY END/////////////////

		//////////////// PCM /////////////////
		bind(currentVoltageSensorErrorLabel, pcmDataMonitor.getCurrentVoltageSensorModel().getError());
		bind(turnoverSensorErrorLabel, pcmDataMonitor.getTurnoverSensorModel().getError());
		bind(turnOverValue, pcmDataMonitor.getTurnoverSensorModel().getturnOverValue());
		bind(torqueValue, pcmDataMonitor.getTurnoverSensorModel().getTorqueValue());
		
		//////////////// PCM END///////////////
		systemWarningLabel.setTooltip(new Tooltip(warningMessage));

		systemWarningLabel.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue.equals("0x0")) {
					systemWarningLabel.setTextFill(Paint.valueOf("#008229"));
				} else {
					systemWarningLabel.setTextFill(Paint.valueOf("#d11414"));
				}
			}
		});
		causingErrorLabel.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue.equals("No error")) {
					causingErrorLabel.setTextFill(Paint.valueOf("#008229"));
				} else {
					causingErrorLabel.setTextFill(Paint.valueOf("#d11414"));
				}
			}
		});
		lastErrorLabel.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (newValue.equals("No error")) {
					lastErrorLabel.setTextFill(Paint.valueOf("#008229"));
				} else {
					lastErrorLabel.setTextFill(Paint.valueOf("#d11414"));
				}
			}
		});
	}
////////////////////////////////////////////////////////////////////////////////////////////////////

	public void bind(Label label, StringProperty s) {
		label.textProperty().bind(s);
	}
	//public void bind(Label label, )

	private void callAlert(AlertType alertType, String title, String headerText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

	private void callAlert(String title, String headerText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.showAndWait();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * BOSH INTERFACE
	 */
	public void applyBoshValues(ActionEvent event) {

//		if (startBoshButton.isDisable()) {
//			try {
//				vcu_INV1_04.setDesiredSpeed(Integer.parseInt(desiredSpeedTextField.getText()));
//				eth_canDeque.add(eth_CAN);
//			} catch (NumberFormatException e) {
//				callAlert(AlertType.WARNING, "Desired Speed", e.getMessage());
//			}
//		} else {
//			startBosh(new ActionEvent());
//		}
	}

	public void startBosh(ActionEvent event) {

//		startBoshButton.setDisable(true);
//
//		vcu_DC1_01.setCurrentDemandHV(15);
//		vcu_DC1_01.setCurrentDemandLV(170);
//		vcu_DC1_01.setModeRequest(ModeRequest.OFF);
//
//		vcu_INV1_01.setActivityOfCAN(true);
//		vcu_INV1_01.setIdcMax(200);
//		vcu_INV1_01.setIdcMin(-100);
//		vcu_INV1_01.setUdcMax(410);
//		vcu_INV1_01.setUdcMin(290);
//
//		vcu_INV1_04.setRequestState(RequestedState.N_CTRL_INT);
//		vcu_INV1_04.setStHvbMailRly(StHvbMaiRly.CLOSED);
//		vcu_INV1_04.setUdcSetP(0);
//		try {
//			vcu_INV1_04.setDesiredSpeed(Integer.parseInt(desiredSpeedTextField.getText()));
//		} catch (NumberFormatException e) {
//			callAlert(AlertType.WARNING, "Desired Speed", e.getMessage());
//		}
//
//		eth_CAN.addCAN_Cdr(vcu_DC1_01);
//		eth_CAN.addCAN_Cdr(vcu_INV1_01);
//		eth_CAN.addCAN_Cdr(vcu_INV1_04);
//		eth_CAN.addCAN_Cdr(diag_ALL_01);
//
//		eth_canDeque.add(eth_CAN);
	}

//
	public void stopBosh(ActionEvent event) {
//		if (startBoshButton.isDisable()) {
//			eth_CAN.removeCAN_Cdr(vcu_DC1_01);
//			eth_CAN.removeCAN_Cdr(vcu_INV1_01);
//			eth_CAN.removeCAN_Cdr(vcu_INV1_04);
//			eth_CAN.removeCAN_Cdr(diag_ALL_01);
//
//			eth_canDeque.add(eth_CAN);
//			startBoshButton.setDisable(false);
//		}
	}

	/*
	 * BOSH INTERFACE END
	 */

	/*
	 * SEMIKRON INTERFACE
	 */

	public void openCom(ActionEvent event) {
		// if nmt state PREOP начать если нет предупредить
		// Начинает отправлять 0x67A
//		String nmtState = nmtStateLabel.getText();
//		if (nmtState.equals("BootUp") || nmtState.equals("Pre-Operational")) {
		try {
			semikron.openCommunication();
			openComButton.setDisable(true);
			closeComButton.setDisable(false);
		} catch (IOException e) {
			callAlert("Open Communication", e.getMessage());
			e.printStackTrace();
		}
//		} else {
//			callAlert(AlertType.WARNING, "NMT STATE", "Состояние NMT не PRE-OPERATIONAL \nNMT : " + nmtState);
//		}
	}

	public void closeCom(ActionEvent event) {
		try {
			semikron.closeCommunication();
			openComButton.setDisable(false);
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Close Communication", e.getMessage());
		}
	}

	public void startNode(ActionEvent event) {
		/*
		 * Отправляет NMT команду и начинает отправлять default rx_PDO3. Вводит
		 * устройство в состояние OPERATIONAL. Устройство начинает в ответ на SYNC(0x80)
		 * отправлять 0x1FA - 0x4FA.
		 */
		try {
			semikron.startNode();
			startNodeButton.setDisable(true);
			enableDisablePWMButton.setDisable(false);
			applyValuesButton.setDisable(false);
			digOut1CheckBox.setDisable(false);
			digOut2CheckBox.setDisable(false);
			asymLimitsCheckBox.setDisable(false);
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Start Node", e.getMessage());
		}
	}

	public void resetNode(ActionEvent event) {
		/*
		 * Отправляет NMT команду RESET_NODE и перестает отправлять rx_PDO3. Устройство
		 * перестает отвечать на SYNC(0x80) (отправлять 0x1FA - 0x4FA)
		 */
		try {
			semikron.resetNode();
			startNodeButton.setDisable(false);
			enableDisablePWMButton.setDisable(true);
			applyValuesButton.setDisable(true);
			digOut1CheckBox.setDisable(true);
			digOut2CheckBox.setDisable(true);
			asymLimitsCheckBox.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Reset Node", e.getMessage());
		}
	}

	public void clearError(ActionEvent event) {
		/*
		 * Метод отправляет Rx_PDO3(0x47A) сообщение с командой "Clear error" The method
		 * sends Rx_PDO3(0x47A) message with command "Clear error"
		 */
		try {
			semikron.clearError();
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Clear Error", e.getMessage());
		}
	}

	public void applyInterval(ActionEvent event) {
		if (syncButton.isSelected()) {
			int freq;
			try {
				freq = Integer.parseInt(intervalTextField.getText());
				try {
					semikron.applySyncInterval(freq);
				} catch (IOException e) {
					e.printStackTrace();
					callAlert("Apply Interval", e.getMessage());
				}
			} catch (NumberFormatException exception) {
				intervalTextField.setText("!!!");
				callAlert(AlertType.INFORMATION, "Interval", "Некоректный ввод");
			}
		} else {
			callAlert(AlertType.INFORMATION, "SYNC + Interval", "Необходимо включить SYNC");
		}
	}

	public void syncSending(ActionEvent event) {
		/*
		 * Контролирует отправку SYNC(0x80) сообщения, с заданным интервалом, понажатию.
		 * The method controls the sending SYNC(0x80) message(with set frequency)
		 */
		if (syncButton.isSelected()) {
			try {
				semikron.syncSending(true);
			} catch (IOException e) {
				syncButton.setSelected(false);
				e.printStackTrace();
				callAlert("SYNC", e.getMessage());
			}
		} else {
			try {
				semikron.syncSending(false);
			} catch (IOException e) {
				syncButton.setSelected(true);
				e.printStackTrace();
				callAlert("SYNC", e.getMessage());
			}
		}
	}

	public void nodeGuardingSending(ActionEvent event) {
		/*
		 * Контролирует отправку node guarding(0x77A) сообщения, с интервалом 500ms.
		 */
		if (nodeGuarding.isSelected()) {
			try {
				semikron.nodeGuardingSending(true);
			} catch (IOException e) {
				nodeGuarding.setSelected(false);
				e.printStackTrace();
				callAlert("Node Guarding", e.getMessage());
			}
		} else {
			try {
				semikron.nodeGuardingSending(false);
			} catch (IOException e) {
				nodeGuarding.setSelected(true);
				e.printStackTrace();
				callAlert("Node Guarding", e.getMessage());
			}
		}
	}

	public void digOut1Click(ActionEvent event) {
		if (digOut1CheckBox.isSelected())
			try {
				semikron.setDigitalOutput1(DigitalState.HIGH);
			} catch (IOException e) {
				digOut1CheckBox.setSelected(false);
				e.printStackTrace();
				callAlert("Digital Out 1", e.getMessage());
			}
		else
			try {
				semikron.setDigitalOutput1(DigitalState.LOW);
			} catch (IOException e) {
				digOut1CheckBox.setSelected(true);
				e.printStackTrace();
				callAlert("Digital Out 1", e.getMessage());
			}

	}

	public void digOut2Click(ActionEvent event) {
		if (digOut2CheckBox.isSelected())
			try {
				semikron.setDigitalOutput1(DigitalState.HIGH);
			} catch (IOException e) {
				digOut2CheckBox.setSelected(false);
				e.printStackTrace();
				callAlert("Digital Out 2", e.getMessage());
			}
		else
			try {
				semikron.setDigitalOutput1(DigitalState.LOW);
			} catch (IOException e) {
				digOut2CheckBox.setSelected(true);
				e.printStackTrace();
				callAlert("Digital Out 2", e.getMessage());
			}
	}

	public void setAsymmetricLimitation(ActionEvent event) {
		if (asymLimitsCheckBox.isSelected())
			try {
				semikron.setLimitationMode(LimitationMode.ASYMMETRIC);
			} catch (IOException e) {
				asymLimitsCheckBox.setSelected(false);
				e.printStackTrace();
				callAlert("Asymmetric Limitation", e.getMessage());
			}
		else
			try {
				semikron.setLimitationMode(LimitationMode.SYMMETRIC);
			} catch (IOException e) {
				asymLimitsCheckBox.setSelected(true);
				e.printStackTrace();
				callAlert("Asymmetric Limitation", e.getMessage());
			}
	}

	public void selectMode(ActionEvent event) {
		((CntrlMode) modeBox.getValue()).selectMode();
	}

	public void enableDisablePWM(ActionEvent event) {
		if (enablePWMClick == false) {
			try {
				try {
					((CntrlMode) modeBox.getValue()).applyingValues(semikron);

					closeComButton.setDisable(true);
					resetNodeButton.setDisable(true);
					enablePWMClick = true;
					enableDisablePWMButton.setText("Disable PWM");
					enableDisablePWMButton.setEffect(new Lighting(new Light.Distant(45.0, 45.0, Color.CHARTREUSE)));

				} catch (IOException e) {
					e.printStackTrace();
					callAlert("Enable PWM", e.getMessage());
				}
			} catch (NumberFormatException e) {
				callAlert(AlertType.WARNING, "Enable PWM", e.getMessage());
			}
		} else {
			try {
				semikron.disablePWM();

				closeComButton.setDisable(false);
				resetNodeButton.setDisable(false);
				enablePWMClick = false;
				enableDisablePWMButton.setText("Enable PWM");
				enableDisablePWMButton.setEffect(null);
			} catch (IOException e) {
				e.printStackTrace();
				callAlert("Disable PWM", e.getMessage());
			}
		}
	}

	public void applyValues(ActionEvent event) {
		if (enablePWMClick == true) {
			try {
				try {
					((CntrlMode) modeBox.getValue()).applyingValues(semikron);
				} catch (IOException e) {
					e.printStackTrace();
					callAlert("Apply PWM values", e.getMessage());
				}
			} catch (NumberFormatException e) {
				callAlert(AlertType.WARNING, "Enable PWM", e.getMessage());
			}
		}
	}

	/*
	 * SEMIKRON INTERFACE END
	 */

	/*
	 * PCM INTERFACE
	 */
	private <T> void addPCM(RadioButton parametr, String seriesName, Deque dataModel) {
		if (pcmLineChartUpdater == null) {
			pcmLineChartUpdater = new PcmLineChartUpdater<>(lineChartPCM);
		}
		if (parametr.isSelected()) {
			pcmLineChartUpdater.addSeries(seriesName, dataModel);
			pcmLineChartUpdater.startUpdateChart();
			if(!pcmLineChartUpdater.isRunning())
				pcmLineChartUpdater.Stopwatch();
			if(!pcmDataMonitor.getTurnoverSensorModel().isRunning())
				pcmDataMonitor.getTurnoverSensorModel().Stopwatch();
			
		} else {
			pcmLineChartUpdater.deleteSeries(seriesName);
		}
		if(!turnoverRadioButton.isSelected() && !torqueRadioButton.isSelected())
		{
			pcmLineChartUpdater.setStart(0);
			pcmDataMonitor.getTurnoverSensorModel().setStart(0);
		}
	}

	/*public void addAmperage(ActionEvent event) {
		addPCM(currentRadioButton, "Ток(А)", pcmDataMonitor.getCurrentVoltageSensorModel().getCurrent());
	}*/

	public void openConntectionT_45(ActionEvent event)
	{
		if(ConnectionButtonIsPressed == false)
		{
			sensorComunicationThread.getWinUsbDataReceiver().createCommunication();
			try {
				sensorComunicationThread.getWinUsbDataReceiver().openCommunication();
				
				ConnectToT_45Button.setEffect(new Lighting(new Light.Distant(45.0, 45.0, Color.CHARTREUSE)));
				ConnectToT_45Button.setText("Close Connection");
				ConnectionButtonIsPressed = true;
			} catch (T_45Exception e)
			{
				callAlert("Connection error", e.getMessage());
				e.printStackTrace();
			}
		}
		else
		{
			sensorComunicationThread.getWinUsbDataReceiver().close();
			ConnectToT_45Button.setEffect(null);
			ConnectionButtonIsPressed = false;
			ConnectToT_45Button.setText("Open Connection");
		}
	}
	public void addTurnover(ActionEvent event) {
		
		pcmDataMonitor.getTurnoverSensorModel().getTurnoverT_45().clear();
		pcmDataMonitor.getTurnoverSensorModel().getTurnoverTime().clear();
		addPCM(turnoverRadioButton, "Частота вращения(Об/мин)", pcmDataMonitor.getTurnoverSensorModel().getTurnoverT_45());
		if(turnoverRadioButton.isSelected())
		{
			pcmDataMonitor.getTurnoverSensorModel().setTurnOverVisible(true);
		}
		
		else
		{
			pcmDataMonitor.getTurnoverSensorModel().setTurnOverVisible(false);
			
		}
	}

	public void addTorque(ActionEvent event) {
		pcmDataMonitor.getTurnoverSensorModel().getTorqueT_45().clear();
		pcmDataMonitor.getTurnoverSensorModel().getTorqueTime().clear();
		addPCM(torqueRadioButton, "Момент(Н.м)", pcmDataMonitor.getTurnoverSensorModel().getTorqueT_45());
		if(torqueRadioButton.isSelected())
		{
			pcmDataMonitor.getTurnoverSensorModel().setTorqueVisible(true);
		}
		else
		{
			pcmDataMonitor.getTurnoverSensorModel().setTorqueVisible(false);
		}
	}

	public void saveParam(ActionEvent event) {
		boolean isSave = true;
		boolean isTorqueChoosen = true;
		boolean isTurnoverChoosen = true;
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Microsoft Excel (*.xls)", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog((Stage) lineChartPCM.getScene().getWindow());

		if (file != null) {
			PcmFileWriter paramCMWriter = new ExcelPcmFileWriter();
			isTorqueChoosen = torqueRadioButton.isSelected();
			isTurnoverChoosen = turnoverRadioButton.isSelected();
			
			if(isTurnoverChoosen || isTorqueChoosen)
			{
				try {
					if(!isTurnoverChoosen)
						pcmDataMonitor.getTurnoverSensorModel().getTurnoverT_45().clear();
					if(!isTorqueChoosen)
						pcmDataMonitor.getTurnoverSensorModel().getTorqueT_45().clear();
					
					paramCMWriter.write(file, pcmDataMonitor.getTurnoverSensorModel().getTurnoverT_45(),pcmDataMonitor.getTurnoverSensorModel().getTurnoverTime(),
						pcmDataMonitor.getTurnoverSensorModel().getTorqueT_45(), pcmDataMonitor.getTurnoverSensorModel().getTorqueTime());
				
				
				} catch (IOException e) {
					callAlert(AlertType.ERROR, "Сохранение файла", e.getMessage());
					isSave = false;
				}
			
				if (isSave)
					callAlert(AlertType.INFORMATION, "Сохранение файла",
							"Файл " + file.getName() + " успешно сохранен :" + "\n" + file.getAbsolutePath());
			}
			else
			{
				callAlert(AlertType.INFORMATION,  "Сохранение файла", "Выберите один из параметров для сохранения!!! ");
			}
		}
	}
	/*
	 * PCM INTERFACE END
	 */

	/*
	 * BATTERY INTERFACE
	 */
	public void batteryOff(ActionEvent event) {
		try {
			battery.turnOff();
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Turn Off Battery", e.getMessage());
		}
		if (chargerOn.isSelected()) {
			chargerOff(event);
		}
	}

	public void batteryOn(ActionEvent event) {

		try {
			battery.turnOn();
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Turn On Battery", e.getMessage());
		}
	}

	public void chargerOff(ActionEvent event) {
		try {
			charger.turnOff();
		} catch (IOException e) {
			e.printStackTrace();
			callAlert("Turn Off Charger", e.getMessage());
		}
		chargerOff.setSelected(true);
	}

	public void chargerOn(ActionEvent event) {

		if (batteryOn.isSelected()) {
			try {
				charger.turnOn();
			} catch (IOException e) {
				e.printStackTrace();
				callAlert("Turn On Battery", e.getMessage());
			}
		} else {
			chargerOff.setSelected(true);
		}
	}
	/*
	 * BATTERY INTERFACE END
	 */
}
