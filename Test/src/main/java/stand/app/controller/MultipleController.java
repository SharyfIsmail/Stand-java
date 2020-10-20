package stand.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import stand.app.module.semikron.model.SemikronLineChartUpdater;

public class MultipleController implements Initializable
{
	@FXML
	LineChart<Number, Number> lineChartMultiple;
	@FXML
	Button autoZoomButton;
	@FXML
	Label XYLabel;
	
	private SemikronLineChartUpdater semikronLineChartUpdater;
	public void  autoZoomAction(ActionEvent action)
	{
		lineChartMultiple.getXAxis().setAutoRanging(true);
		lineChartMultiple.getYAxis().setAutoRanging(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lineChartMultiple.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				double axisXRelativeMousePosition = lineChartMultiple.getXAxis().getValueForDisplay(mouseEvent.getX()).intValue();
				double axisYRelativeMousePosition = lineChartMultiple.getYAxis().getValueForDisplay(mouseEvent.getY()).intValue();
			XYLabel.setText(String.format( "(%d, %d)",(int) axisXRelativeMousePosition, (int)axisYRelativeMousePosition));

			}
		});
		
	}

	public SemikronLineChartUpdater getSemikronLineChartUpdater() {
		return semikronLineChartUpdater;
	}

	public void setSemikronLineChartUpdater(SemikronLineChartUpdater semikronLineChartUpdater) {
		this.semikronLineChartUpdater = semikronLineChartUpdater;
	}
	
}
