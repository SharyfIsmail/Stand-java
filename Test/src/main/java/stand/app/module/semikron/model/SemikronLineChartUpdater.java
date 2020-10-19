package stand.app.module.semikron.model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import stand.app.module.pcm.ChartUpdater;
import stand.util.StopWatch;

public class SemikronLineChartUpdater extends AnimationTimer implements ChartUpdater<Number> 
{
	private List<Series<Number, Number>>allSeries;
	private NumberAxis xAxis;
	private LineChart<Number, Number> lineChart;
	//private Map<String, Series<Number, Number>> allSeriesMap;
	private List<Deque<? extends Number>> semikronChartDataModel;
	private StopWatch stopWatch;
	private long lastUpdate = 0;
	private static final int MAX_DATA_POINTS = 200;
	private double xSeriesData = 0;
	int x = 0;
	int y = 0;


	public  SemikronLineChartUpdater(LineChart<Number, Number> lineChart) 
	{
		this.lineChart = lineChart;
//		lineChart.setOnMouseMoved(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				double xStart = lineChart.getXAxis().getLocalToParentTransform().getTx();
//				double axisXRelativeMousePosition = event.getX() - xStart;	
//				
//			}
//		});
		ChartPanManager panner = new ChartPanManager( lineChart );
		panner.setMouseFilter( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
						 ( mouseEvent.getButton() == MouseButton.PRIMARY &&
						   mouseEvent.isShortcutDown() ) ) {
					//let it through
				} else {
					mouseEvent.consume();
				}
			}
		} );
		panner.start();
		
		JFXChartUtil.setupZooming( lineChart, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
				     mouseEvent.isShortcutDown() )
					mouseEvent.consume();
			}
		} );
		JFXChartUtil.addDoublePrimaryClickAutoRangeHandler( lineChart );
	
		this.lineChart.setCreateSymbols(true);
		xAxis =  (NumberAxis) lineChart.getXAxis();
		stopWatch = new StopWatch();
		allSeries = new ArrayList<>();
		semikronChartDataModel = new ArrayList<>();
		//allSeriesMap = new HashMap<>();
	}
	@Override
	public void handle(long now) {
		if(now - lastUpdate >= 250_000_000)
		{
			addDataToSeries();
			lastUpdate = now;
		}

	}

	private void addDataToSeries() {
	
		for(int i = 0; i<allSeries.size(); i++)
		{
			System.out.println(semikronChartDataModel.get(i).isEmpty());
			//if(semikronChartDataModel.get(i).isEmpty())
			//	continue;
			xSeriesData = stopWatch.getElapsedTime()/1000.0;
			System.out.println(xSeriesData);
			//XYChart.Data<Number, Number> data = new XYChart.Data<>(xSeriesData, semikronChartDataModel.get(i).peekLast());
			XYChart.Data<Number, Number> data = new XYChart.Data<>(x++, y++);

			allSeries.get(i).getData().add(data);
			//if (allSeries.get(i).getData().size() > MAX_DATA_POINTS) {
				//allSeries.get(i).getData().remove(0, allSeries.get(i).getData().size() - MAX_DATA_POINTS);
			//}
		}
		// update
		
		 //xAxis.setLowerBound(xSeriesData );
		// xAxis.setUpperBound(xSeriesData );
	}
	@Override
	public void addSeries(String seriesName, Deque<? extends Number> chartDataModel) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(seriesName);
		allSeries.add(series);
		semikronChartDataModel.add(chartDataModel);
		//allSeriesMap.put(seriesName, series);
		lineChart.getData().add(series);
		
		}

	@Override
	public void deleteSeries(String seriesName) {
		for(int i = 0 ; i < allSeries.size() ; i++)
		{
			if(allSeries.get(i).getName().equals(seriesName))
			{
				lineChart.getData().remove(allSeries.get(i));
				semikronChartDataModel.remove(i);
				allSeries.remove(i);
				
			}
		}
	}
	@Override
	public void startUpdateChart() {
		this.start();
		stopWatch.start();
	}

	@Override
	public void stopUpdateChart() {

		this.stop();
		stopWatch.reset();
		
	}
}
