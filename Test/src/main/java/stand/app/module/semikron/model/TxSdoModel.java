package stand.app.module.semikron.model;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.sdo.ActualCurrentId;
import stand.semikron.sdo.ActualCurrentIq;
import stand.semikron.sdo.ActualUd;
import stand.semikron.sdo.ActualUdqVoltageLenght;
import stand.semikron.sdo.ActualUq;
import stand.semikron.sdo.AnalogIn1;
import stand.semikron.sdo.AnalogIn2;
import stand.semikron.sdo.ReferenceCurrentId;
import stand.semikron.sdo.ReferenceCurrentIq;
import stand.semikron.sdo.SDO;
import stand.semikron.tx.TxSDO;

public class TxSdoModel implements DataFromCanModel {

	private TxSDO txSDO;

	private AnalogIn1 in1;
	private AnalogIn2 in2;
	private ActualCurrentIq actualCurrentIq;
	private ActualCurrentId actualCurrentId;
	private ReferenceCurrentIq referenceCurrentIq;
	private ReferenceCurrentId referenceCurrentId;
	private ActualUd ud;
	private ActualUq uq;
	private ActualUdqVoltageLenght actualUdqVoltageLenght;

	private StringProperty actualIq;
	private StringProperty actualId;
	private StringProperty referenceIq;
	private StringProperty referenceId;
	private StringProperty actualUq;
	private StringProperty actualUd;
	private StringProperty actualUdq;
	private StringProperty analogIn1;
	private StringProperty analogIn2;

	public TxSdoModel() {
		super();

		in1 = new AnalogIn1();
		in2 = new AnalogIn2();
		actualCurrentIq = new ActualCurrentIq();
		actualCurrentId = new ActualCurrentId();
		referenceCurrentIq = new ReferenceCurrentIq();
		referenceCurrentId = new ReferenceCurrentId();
		uq = new ActualUq();
		ud = new ActualUd();
		actualUdqVoltageLenght = new ActualUdqVoltageLenght();

		Map<Integer, SDO> map = new HashMap<Integer, SDO>();
		map.put(in1.getIndex(), in1);
		map.put(in2.getIndex(), in2);
		map.put(actualCurrentIq.getIndex(), actualCurrentIq);
		map.put(actualCurrentId.getIndex(), actualCurrentId);
		map.put(referenceCurrentIq.getIndex(), referenceCurrentIq);
		map.put(uq.getIndex(), uq);
		map.put(ud.getIndex(), ud);
		map.put(actualUdqVoltageLenght.getIndex(), actualUdqVoltageLenght);

		txSDO = new TxSDO(map);
		actualIq = new SimpleStringProperty();
		actualId = new SimpleStringProperty();
		referenceIq = new SimpleStringProperty();
		referenceId = new SimpleStringProperty();

		actualUq = new SimpleStringProperty();
		actualUd = new SimpleStringProperty();
		actualUdq = new SimpleStringProperty();
		analogIn1 = new SimpleStringProperty();
		analogIn2 = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				actualIq.setValue(String.valueOf(actualCurrentIq.getValue()));
				actualId.setValue(String.valueOf(actualCurrentId.getValue()));
				referenceId.setValue(String.valueOf(referenceCurrentId.getValue()));
				referenceIq.setValue(String.valueOf(referenceCurrentIq.getValue()));

				actualUq.setValue(String.valueOf(uq.getValue()));
				actualUd.setValue(String.valueOf(ud.getValue()));
				actualUdq.setValue(String.valueOf(actualUdqVoltageLenght.getValue()));

				analogIn1.setValue(String.valueOf(in1.getValue()));
				analogIn2.setValue(String.valueOf(in2.getValue()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return txSDO;
	}

	public StringProperty getActualIq() {
		return actualIq;
	}

	public StringProperty getActualId() {
		return actualId;
	}

	public StringProperty getReferenceIq() {
		return referenceIq;
	}

	public StringProperty getReferenceId() {
		return referenceId;
	}

	public StringProperty getActualUq() {
		return actualUq;
	}

	public StringProperty getActualUd() {
		return actualUd;
	}

	public StringProperty getActualUdq() {
		return actualUdq;
	}

	public StringProperty getAnalogIn1() {
		return analogIn1;
	}

	public StringProperty getAnalogIn2() {
		return analogIn2;
	}

}
