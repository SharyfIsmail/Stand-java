package stand.app.module.battery.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import stand.can.candata.DataFromCanModel;

public class BatteryDataMonitor {
	private List<DataFromCanModel> dataFromCanModelList;

	private ActiveFaultDataModel activeFaultDataModel;
	private Data00Model data00Model;
	private Data01Model data01Model;
	private Data02Model data02Model;

	@Autowired
	public BatteryDataMonitor() {
		super();

		this.activeFaultDataModel = new ActiveFaultDataModel();
		this.data00Model = new Data00Model();
		this.data01Model = new Data01Model();
		this.data02Model = new Data02Model();

		dataFromCanModelList = new ArrayList<>();
		dataFromCanModelList.add(activeFaultDataModel);
		dataFromCanModelList.add(data00Model);
		dataFromCanModelList.add(data01Model);
		dataFromCanModelList.add(data02Model);
	}

	public ActiveFaultDataModel getActiveFaultDataModel() {
		return activeFaultDataModel;
	}

	public Data00Model getData00Model() {
		return data00Model;
	}

	public Data01Model getData01Model() {
		return data01Model;
	}

	public Data02Model getData02Model() {
		return data02Model;
	}

	public List<DataFromCanModel> getDataFromCanModelList() {
		return dataFromCanModelList;
	}
}
