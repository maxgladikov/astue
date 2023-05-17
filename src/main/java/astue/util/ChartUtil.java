package astue.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import astue.service.implementation.report.ElectricReport;

public class ChartUtil {
	
	private ChartUtil() {
		throw new IllegalStateException("Utility class");
	};
	
	public static BufferedImage getChartImage(ElectricReport report) {
		XYDataset dataset = createDataset(report);
		JFreeChart chart = createChart(report,dataset);
		return chart.createBufferedImage(500, 300);
	}

	private static JFreeChart createChart(ElectricReport report,XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(report.getName(), "Date", "kWh", dataset, true, true, false);
		DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
		axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setDateFormatOverride(new SimpleDateFormat("dd"));
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		ValueAxis range = plot.getRangeAxis();
		var renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);
		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle(report.getName(), new Font("Serif", java.awt.Font.BOLD, 18)));
		return chart;
	}

	private static XYDataset createDataset(ElectricReport report) {
		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		final TimeSeries activeSeries = new TimeSeries("active");
		final TimeSeries reactiveSeries = new TimeSeries("reactive");
		final TimeSeries totalSeries = new TimeSeries("total");
		var active=report.getSeriesActive();
		var reactive=report.getSeriesReactive();
		var total=report.getSeriesTotal();
		active.keySet().stream().sorted()
				.forEach(dt -> activeSeries.add(getHour(dt),active.get(dt)) );
		reactive.keySet().stream().sorted()
		.forEach(dt -> reactiveSeries.add(getHour(dt),reactive.get(dt)) );
		total.keySet().stream().sorted()
		.forEach(dt -> totalSeries.add(getHour(dt),total.get(dt)) );
		dataset.addSeries(activeSeries);
		dataset.addSeries(reactiveSeries);
		dataset.addSeries(totalSeries);
		return dataset;
	}
	
	private static Hour getHour(LocalDateTime dt) {
		return new Hour(dt.getHour(),dt.getDayOfMonth(),dt.getMonthValue(),dt.getYear());
	}

}
