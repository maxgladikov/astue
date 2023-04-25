package astue.service.implementation.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.List;

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
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import astue.model.PowerRecord;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Chart {
	private final String name;
	private final List<PowerRecord> records;
	
	
	
	
	
	public BufferedImage get() {
		XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        return chart.createBufferedImage(500, 300);
	}
	
	 private JFreeChart createChart(XYDataset dataset) {

	        JFreeChart chart = ChartFactory.createTimeSeriesChart(
	                name,
	                "Date",
	                "kWh",
	                dataset,
	                true,
	                true,
	                false
	        );
	        DateAxis axis=(DateAxis)chart.getXYPlot().getDomainAxis();
	        axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY,1));
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

	        chart.setTitle(new TextTitle(name,
	                        new Font("Serif", java.awt.Font.BOLD, 18)
	                )
	        );

	        return chart;
	    }

	        
	 
	 private XYDataset createDataset() {
		 final TimeSeriesCollection dataset = new TimeSeriesCollection();
//	     dataset.setDomainIsPointsInTime(true);
		 final TimeSeries series = new TimeSeries(" ");
		 records.stream().forEach(x -> 
		 series.add(new Minute(x.getCreated().getMinute(), x.getCreated().getHour(), x.getCreated().getDayOfMonth(), x.getCreated().getMonthValue(), x.getCreated().getYear()), x.getActivePowerConsumption()));
		 dataset.addSeries(series);
		 return dataset;
	 }
	 
	
}
