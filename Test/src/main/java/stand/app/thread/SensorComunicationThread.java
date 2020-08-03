package stand.app.thread;

import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import stand.app.module.pcm.PcmDataMonitor;
import stand.t_45.data.DataFromT_45Model;
import stand.util.WinUsbDataReceiver;

public class SensorComunicationThread extends  Thread
{
	
	private Button ConnectToT_45Button;
	private PcmDataMonitor pcmDataMonitor;
	private WinUsbDataReceiver winUsbDataReceiver;
	private Alert alert = new Alert(AlertType.ERROR);
	
	@Override
	public void run()
	{
		while(true)
		{
			if(winUsbDataReceiver.getPointerTest().get() != null)
			{
				try {
					objectMapping(winUsbDataReceiver.receive());
				} catch (IOException e) {
					winUsbDataReceiver.close();
					pcmDataMonitor.getTurnoverSensorModel().getStopWatch().pause();
					Platform.runLater(() -> {
						ConnectToT_45Button.setEffect(new Lighting(new Light.Distant(45.0, 45.0, Color.ORANGERED)));
						alert.setTitle("Connection Error");
						alert.setHeaderText(e.getMessage());
						alert.showAndWait();
						
					});	
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
		}
	}
	private void objectMapping(byte[] data)
	{
		DataFromT_45Model dataFromT_45Model = pcmDataMonitor.getTurnoverSensorModel();
		dataFromT_45Model.getDataFromT_45().parseDataFromT_45(data);
		dataFromT_45Model.updateModel_T45();
		
	}
	public void setTurnOverSensorModel(PcmDataMonitor pcmDataMonitor)
	{
		this.pcmDataMonitor = pcmDataMonitor;
	}
	public void setWinUsbDataReceiver(WinUsbDataReceiver winUsbDataReceiver)
	{
		this.winUsbDataReceiver = winUsbDataReceiver;
	}
	public WinUsbDataReceiver getWinUsbDataReceiver()
	{
		return this.winUsbDataReceiver;
	}
	public void setButton(Button button)
	{
		ConnectToT_45Button = button;
	}

}
