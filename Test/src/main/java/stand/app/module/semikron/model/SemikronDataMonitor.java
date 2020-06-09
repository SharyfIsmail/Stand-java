package stand.app.module.semikron.model;

import java.util.ArrayList;
import java.util.List;

import stand.can.candata.DataFromCanModel;

public class SemikronDataMonitor {
	private List<DataFromCanModel> dataFromCanModelList;
	private NodeGuardingSlaveModel nodeGuardingSlave;
	private TxPDO1Model txPDO1;
	private TxPDO2Model txPDO2;
	private TxPDO3Model txPDO3;
	private TxPDO4Model txPDO4;
	private TxPDO5Model txPDO5;
	private TxSdoModel txSDO;

	public SemikronDataMonitor() {
		super();
		this.nodeGuardingSlave = new NodeGuardingSlaveModel();
		this.txPDO1 = new TxPDO1Model();
		this.txPDO2 = new TxPDO2Model();
		this.txPDO3 = new TxPDO3Model();
		this.txPDO4 = new TxPDO4Model();
		this.txPDO5 = new TxPDO5Model();
		this.txSDO = new TxSdoModel();

		dataFromCanModelList = new ArrayList<>();
		dataFromCanModelList.add(nodeGuardingSlave);
		dataFromCanModelList.add(txPDO1);
		dataFromCanModelList.add(txPDO2);
		dataFromCanModelList.add(txPDO3);
		dataFromCanModelList.add(txPDO4);
		dataFromCanModelList.add(txPDO5);
		dataFromCanModelList.add(txSDO);
	}

	public List<DataFromCanModel> getDataFromCanModelList() {
		return dataFromCanModelList;
	}

	public NodeGuardingSlaveModel getNodeGuardingSlaveModel() {
		return nodeGuardingSlave;
	}

	public TxPDO1Model getTxPDO1() {
		return txPDO1;
	}

	public TxPDO2Model getTxPDO2() {
		return txPDO2;
	}

	public TxPDO3Model getTxPDO3() {
		return txPDO3;
	}

	public TxPDO4Model getTxPDO4() {
		return txPDO4;
	}

	public TxPDO5Model getTxPDO5() {
		return txPDO5;
	}

	public TxSdoModel getTxSDO() {
		return txSDO;
	}
}
