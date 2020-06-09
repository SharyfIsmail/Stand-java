package stand.app.module.semikron.model;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import stand.can.candata.DataFromCan;
import stand.can.candata.DataFromCanModel;
import stand.semikron.tx.NodeGuardingSlave;

@Component
public class NodeGuardingSlaveModel implements DataFromCanModel {

	private NodeGuardingSlave nodeGuardingSlave;

	private StringProperty nmtState;
	private StringProperty toggle;

	public NodeGuardingSlaveModel() {
		super();
		nodeGuardingSlave = new NodeGuardingSlave();

		nmtState = new SimpleStringProperty();
		toggle = new SimpleStringProperty();
	}

	@Override
	public void updateModel() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (nodeGuardingSlave.getNmtState() != null)
					nmtState.setValue(nodeGuardingSlave.getNmtState());

				toggle.setValue(String.valueOf(nodeGuardingSlave.getToggle()));
			}
		});
	}

	@Override
	public DataFromCan getDataFromCan() {
		return nodeGuardingSlave;
	}

	public StringProperty getNmtState() {
		return nmtState;
	}

	public StringProperty getToggle() {
		return toggle;
	}

}
