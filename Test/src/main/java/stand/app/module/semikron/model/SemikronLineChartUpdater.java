package stand.app.module.semikron.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.TimeZone;

import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import stand.app.module.pcm.ChartUpdater;
import stand.util.StopWatch;

public class SemikronLineChartUpdater extends AnimationTimer implements ChartUpdater<Number> 
{
	private List<Series<Number, Number>>allSeries;
	private NumberAxis xAxis;

	private LineChart<Number, Number> lineChart;
	private List<Deque<? extends Number>> semikronChartDataModel;
	private StopWatch stopWatch;
	private long lastUpdate = 0;
	//private static final int MAX_DATA_POINTS = 200;
	private SimpleDateFormat format ;
	private double xSeriesData = 0;
	int x = 0;
	int y = 0;


	public  SemikronLineChartUpdater(LineChart<Number, Number> lineChart) 
	{
		this.lineChart = lineChart;
		
		ChartPanManager panner = new ChartPanManager( lineChart );
		panner.setMouseFilter( new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
						 ( mouseEvent.getButton() == MouseButton.PRIMARY &&
						   mouseEvent.isShortcutDown() ) ) {
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
		
		this.lineChart.setCreateSymbols(false);
		this.lineChart.setAnimated(false);
		
		format = new SimpleDateFormat("HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone( "GMT" ));  
		
		xAxis =  (NumberAxis) lineChart.getXAxis();
		xAxis.setTickLabelFormatter(new StringConverter<Number>(){
		    @Override
		    public String toString(Number object) {
		      return format.format(new Date(object.longValue()));
		    }
			@Override
			public Number fromString(String string) {
				return null;
			}
			});

		stopWatch = new StopWatch();
		allSeries = new ArrayList<>();
		semikronChartDataModel = new ArrayList<>();
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
			xSeriesData = stopWatch.getElapsedTime();
			XYChart.Data<Number, Number> data = new XYChart.Data<>(xSeriesData, semikronChartDataModel.get(i).peekLast());
			//XYChart.Data<Number, Number> data = new XYChart.Data<>(xSeriesData, 50);
			allSeries.get(i).getData().add(data);
			//if (allSeries.get(i).getData().size() > MAX_DATA_POINTS) {
				//allSeries.get(i).getData().remove(0, allSeries.get(i).getData().size() - MAX_DATA_POINTS);
				
			//}
		}
		// update
	//	yAxis.setLowerBound(0 );
	
//		yAxis.setUpperBound(y + 1 );
//	
	}
	@Override
	public void addSeries(String seriesName, Deque<? extends Number> chartDataModel) {
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(seriesName);
		allSeries.add(series);
		semikronChartDataModel.add(chartDataModel);
		lineChart.getData().add(series);
		
		}

	@Override
	public void deleteSeries(String seriesName) {
		System.out.println(seriesName);
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
	public void deleteAllSeries() {
		lineChart.getData().clear();;
		semikronChartDataModel.clear();;
		allSeries.clear();	
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
