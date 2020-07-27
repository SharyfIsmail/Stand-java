package stand.app.thread;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import stand.app.module.pcm.PcmDataMonitor;
import stand.app.module.pcm.model.TurnoverSensorModel;
import stand.t_45.data.DataFromT_45Model;
import stand.util.WinUsbDataReceiver;

public class SensorComunicationThread extends  Thread
{
	private PcmDataMonitor pcmDataMonitor;
	private WinUsbDataReceiver winUsbDataReceiver;
	Alert alert = new Alert(AlertType.ERROR);
	
	@Override
	public void run()
	{
		while(true)
		{
			if(winUsbDataReceiver.getPointer() != null)
			{
				try {
					objectMapping(winUsbDataReceiver.receive());
				} catch (IOException e) {
					Platform.runLater(() -> {
					alert.setTitle("Connection Error");
					alert.setHeaderText(e.getMessage());
					alert.showAndWait();				
					e.printStackTrace();
					});	
				}
			}
			try {
				TimeUnit.MICROSECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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

}
