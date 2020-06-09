package stand.app.module.pcm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import stand.app.module.pcm.model.CurrentVoltageSensorModel;
import stand.app.module.pcm.model.TurnoverSensorModel;
import stand.can.candata.DataFromCanModel;

@Component
public class PcmDataMonitor {
	private List<DataFromCanModel> dataFromCanModelList;
	private CurrentVoltageSensorModel currentVoltageSensorModel;
	private TurnoverSensorModel turnoverSensorModel;

	@Autowired
	public PcmDataMonitor(CurrentVoltageSensorModel currentVoltageSensorModel,
			TurnoverSensorModel turnoverSensorModel) {
		super();
		this.currentVoltageSensorModel = currentVoltageSensorModel;
		this.turnoverSensorModel = turnoverSensorModel;

		dataFromCanModelList = new ArrayList<>();
		dataFromCanModelList.add(currentVoltageSensorModel);
		dataFromCanModelList.add(turnoverSensorModel);
	}

	public CurrentVoltageSensorModel getCurrentVoltageSensorModel() {
		return currentVoltageSensorModel;
	}

	public TurnoverSensorModel getTurnoverSensorModel() {
		return turnoverSensorModel;
	}

	public List<DataFromCanModel> getDataFromCanModelList() {
		return dataFromCanModelList;
	}
}
