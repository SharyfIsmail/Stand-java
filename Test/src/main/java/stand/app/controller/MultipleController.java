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

public class MultipleController implements Initializable
{
	@FXML
	LineChart<Number, Number> lineChartMultiple;
	@FXML
	Button autoZoomButton;
	@FXML
	Label XYLabel;
	
	public void  autoZoomAction(ActionEvent action)
	{
		lineChartMultiple.getXAxis().setAutoRanging(true);
		lineChartMultiple.getYAxis().setAutoRanging(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lineChartMultiple.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double xStart = lineChartMultiple.getXAxis().getLocalToParentTransform().getTx();
				double axisXRelativeMousePosition = event.getX() - xStart;	
			XYLabel.setText(String.format( "(%.2f, %.2f)", xStart,axisXRelativeMousePosition));
				System.out.println("fuck yes");

			}
		});
		
	}
}
