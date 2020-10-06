package stand.app.module.semikron.model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import stand.app.module.pcm.ChartUpdater;
import stand.util.StopWatch;

public class SemikronLineChartUpdater extends AnimationTimer implements ChartUpdater<Number> 
{
	private List<Series<Number, Number>>allSeries;
	private LineChart<Number, Number> lineChart;
	//private Map<String, Series<Number, Number>> allSeriesMap;
	private List<Deque<Number>> semikronChartDataModel;
	private StopWatch stopWatch;
	private long lastUpdate = 0;
	
	public  SemikronLineChartUpdater(LineChart<Number, Number> lineChart) 
	{
		this.lineChart = lineChart;
		this.lineChart.setCreateSymbols(false);
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
			if(semikronChartDataModel.get(i).isEmpty())
				continue;
			XYChart.Data<Number, Number> data = new XYChart.Data<>(stopWatch.getElapsedTime(), semikronChartDataModel.get(i).peekLast());
			allSeries.get(i).getData().add(data);
			
		}
		
		
	}
	@Override
	public void addSeries(String seriesName, Deque<Number> chartDataModel) {
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
