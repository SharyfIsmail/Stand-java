package stand.app.module.pcm;

import java.util.Deque;

public interface ChartUpdater<T> {

	void startUpdateChart();

	void stopUpdateChart();

	void addSeries(String seriesName, Deque<? extends T> chartDataModel);

	void deleteSeries(String seriesName);
}
