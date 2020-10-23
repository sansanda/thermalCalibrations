/* -------------------
* TimeSeriesDemo.java
* -------------------
* (C) Copyright 2002-2005, by Object Refinery Limited.
*
*/
package jFreeChart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import com.schneide.quantity.mechanicalQuantities.Second;
/**
* An example of a time series chart. For the most part, default settings are
CHAPTER 8. TIME SERIES CHARTS 64
* used, except that the renderer is modified to show filled shapes (as well as
* lines) at each data point.
* <p>
* IMPORTANT NOTE: THIS DEMO IS DOCUMENTED IN THE JFREECHART DEVELOPER GUIDE.
* DO NOT MAKE CHANGES WITHOUT UPDATING THE GUIDE ALSO!!
*/
public class TemperatureVSMeasureNumberFrame extends ApplicationFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* A demonstration application showing how to create a simple time series
	* chart. This example uses monthly data.
	*
	* @param title the frame title.
	*/

	
	public TemperatureVSMeasureNumberFrame(String title, XYSeries serie) throws InterruptedException {
		super(title);
		Dimension minimumSize = new Dimension(600,400);
		super.setMinimumSize(minimumSize );
		//super.setUndecorated(true);
		
		//super.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
		//super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(serie);
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	* Creates a chart.
	*
	* @param dataset a dataset.
	*
	* @return A chart.
	*/
	private static JFreeChart createChart(XYDataset dataset) {
		// create the chart...
		JFreeChart chart = ChartFactory.createXYLineChart(
		"Line Chart Demo 2", // chart title
		"X", // x axis label
		"Y", // y axis label
		dataset, // data
		PlotOrientation.VERTICAL,
		true, // include legend
		true, // tooltips
		false // urls
		);
		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);
		// get a reference to the plot for further customisation...
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		XYLineAndShapeRenderer renderer
		= (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setShapesFilled(true);
		// change the auto tick unit selection to integer units only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		domainAxis.setNumberFormatOverride(new DecimalFormat("######"));
		
		
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		// OPTIONAL CUSTOMISATION COMPLETED.
		return chart;
	}
}
