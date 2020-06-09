package stand.app.module.pcm;

import java.util.ArrayList;
import java.util.Deque;

import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import stand.util.IstopWatch;

public class PcmLineChartUpdater<T> implements ChartUpdater<T>,IstopWatch{

	private LineChart<Number, Number> lineChart;
	private NumberAxis xAxis;
	private static final int MAX_DATA_POINTS = 200;
	private double xSeriesData = 0;
	private ArrayList<Deque> pcmLineChartDataModels;
	private ArrayList<Series<Number, Number>> allSeries;
//	private StopWatch stopWatch;
	private  long start = 0;;
	private AnimationTimer animationTimer = new AnimationTimer() {

		private long lastUpdate = 0;

		@Override
		public void handle(long now) {
			// every250ms
			if (now - lastUpdate >= 250_000_000) {// 250ms
				addDataToSeries();
				lastUpdate = now;
			}
		}
	};

	public PcmLineChartUpdater(LineChart<Number, Number> lineChart) {
		super();
		this.lineChart = lineChart;
		xAxis = (NumberAxis) lineChart.getXAxis();
		pcmLineChartDataModels = new ArrayList<>();
		allSeries = new ArrayList<>();
		//stopWatch = new StopWatch();

		}

	@SuppressWarnings("unchecked")
	private void addDataToSeries() {
		for (int i = 0; i < 1; i++) { // -- add 1 numbers to the plot+
			for (int j = 0; j < allSeries.size(); j++) {
				if (pcmLineChartDataModels.get(j).isEmpty())
					continue;
				System.out.println(elapsedTime());
				xSeriesData = elapsedTime();
				Data data = new XYChart.Data<>(xSeriesData, pcmLineChartDataModels.get(j).peekLast());
				allSeries.get(j).getData().add(data);

				// remove points to keep us at no more than MAX_DATA_POINTS
				if (allSeries.get(j).getData().size() > MAX_DATA_POINTS) {
					allSeries.get(j).getData().remove(0, allSeries.get(j).getData().size() - MAX_DATA_POINTS);
				}
			}
		}
		// update
		//if(xSeriesData < MAX_DATA_POINTS)
			xAxis.setLowerBound(xSeriesData - 12);
//		else
//			xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		xAxis.setUpperBound(xSeriesData );
	}

	@Override
	public void deleteSeries(String seriesName) {
		for (int j = 0; j < allSeries.size(); j++) {
			if (allSeries.get(j).getName().equals(seriesName)) {
				lineChart.getData().remove(allSeries.get(j));
				pcmLineChartDataModels.remove(j);
				allSeries.remove(j);
			}
		}
	}

	@Override
	public void addSeries(String seriesName, Deque<T> lineChartDataModel) {
		XYChart.Series series = new XYChart.Series<>();
		series.setName(seriesName);
		allSeries.add(series);
		pcmLineChartDataModels.add(lineChartDataModel);
		lineChart.getData().add(series);
	}

	@Override
	public void startUpdateChart() {
		// Timeline gets called in the JavaFX Main thread
		// Every frame to take any data from queue and add to chart
		animationTimer.start();
	}

	@Override
	public void stopUpdateChart() {
		animationTimer.stop();
	}
//	private class StopWatch
//	{
//		 private  long start;
//		 public StopWatch()
//		 {
//		        start = System.currentTimeMillis();
//		 } 
//		 public double elapsedTime()
//		 {
//		        long now = System.currentTimeMillis();
//		        return  ((now - start) / 1000.0);
//		 }
//		 
//	}
	@Override
	public double elapsedTime() {
		 long now = System.currentTimeMillis();
	        return  ((now - start) / 1000.0);
	}

	@Override
	public void Stopwatch() {
		start = System.currentTimeMillis();		
	}

	@Override
	public boolean isRunning() {
		
		if(start > 0)
			return true;
		return false;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}
	
}
