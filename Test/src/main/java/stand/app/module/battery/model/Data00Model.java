package stand.app.module.battery.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.battery.tx.EdlData00;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;

public class Data00Model implements DataFromCanModel {

	private EdlData00 data00;

	private StringProperty max_CV;
	private StringProperty min_CV;
	private StringProperty max_Pack_Temp;
	private StringProperty min_Pack_Temp;
	private StringProperty bms_Contactor_Condition;

	public Data00Model() {
		super();
		data00 = new EdlData00();

		max_CV = new SimpleStringProperty();
		min_CV = new SimpleStringProperty();
		max_Pack_Temp = new SimpleStringProperty();
		min_Pack_Temp = new SimpleStringProperty();
		bms_Contactor_Condition = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				max_CV.setValue(String.valueOf(data00.getMax_CV()));
				min_CV.setValue(String.valueOf(data00.getMin_CV()));
				max_Pack_Temp.setValue(String.valueOf(data00.getMax_Pack_Temp()));
				min_Pack_Temp.setValue(String.valueOf(data00.getMin_Pack_Temp()));
				bms_Contactor_Condition.setValue(String.valueOf(data00.getBMS_Contactor_Condition()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return data00;
	}

	public StringProperty getMax_CV() {
		return max_CV;
	}

	public StringProperty getMin_CV() {
		return min_CV;
	}

	public StringProperty getMax_Pack_Temp() {
		return max_Pack_Temp;
	}

	public StringProperty getMin_Pack_Temp() {
		return min_Pack_Temp;
	}

	public StringProperty getBms_Contactor_Condition() {
		return bms_Contactor_Condition;
	}

}
