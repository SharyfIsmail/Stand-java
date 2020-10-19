package stand.app.controller;

import java.util.Deque;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class MultipleController 
{
	@FXML
	LineChart<Number, Number> lineChartMultiple;
	@FXML
	Button autoZoomButton;
	public void fuck()
	{
		System.out.println("MultipleController");
	}
	private  void addChartToSuperCarDev(CheckBox parametr, String seriesName, Deque<? extends Number> dataModel) {
	
	}
	
	
}
