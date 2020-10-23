package gui;

import java.awt.BasicStroke;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Color;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeriesCollection;

import org.jfree.chart.title.TextTitle;

public class OvenManualOperation_JFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel = null;
	public JTextField actualOvenTempjTextField = null;
	private JLabel actualOvenTempjLabel = null;
	private JLabel desiredOvenTempjLabel = null;
	private JLabel temperatureSetPointConfigurationjLabel = null;
	private JSeparator jSeparator = null;
	public JTextField setPointTemperaturejTextField = null;
	private org.jfree.data.time.TimeSeries realOvenTempSerie = null;  //  @jve:decl-index=0:
	private org.jfree.data.time.TimeSeries desiredOvenTempSerie = null;  //  @jve:decl-index=0:

	private TimeSeriesCollection graphDataset = null;
	private JFreeChart chart = null;
	private ChartPanel chartPanel = null;
	private JPanel graphPanel = null;
	private JTabbedPane jTabbedPane = null;
	
	public OvenManualOperation_JFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}
	public OvenManualOperation_JFrame(GraphicsConfiguration gc) {
		super(gc);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public OvenManualOperation_JFrame(String title) throws HeadlessException {
		super(title);
		initialize();
		// TODO Auto-generated constructor stub
	}

	public OvenManualOperation_JFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setTitle("Oven Manual Operation");
        this.setName("OvenManualOperationJFrame");
        this.setContentPane(getJTabbedPane());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(new Rectangle(0, 0, 609, 382));
        this.setResizable(false);
	}


	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			temperatureSetPointConfigurationjLabel = new JLabel();
			temperatureSetPointConfigurationjLabel.setBounds(new Rectangle(15, 15, 436, 31));
			temperatureSetPointConfigurationjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureSetPointConfigurationjLabel.setToolTipText("ffdbfdbfd");
			temperatureSetPointConfigurationjLabel.setText("TEMPERATURE SETPOINT CONFIGURATION");
			desiredOvenTempjLabel = new JLabel();
			desiredOvenTempjLabel.setBounds(new Rectangle(90, 60, 211, 31));
			desiredOvenTempjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			desiredOvenTempjLabel.setText("SetPoint Temperature");
			actualOvenTempjLabel = new JLabel();
			actualOvenTempjLabel.setBounds(new Rectangle(15, 120, 196, 31));
			actualOvenTempjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			actualOvenTempjLabel.setText("ACTUAL OVEN TEMPERATURE");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(1, 1));
			jPanel.add(actualOvenTempjLabel, null);
			jPanel.add(desiredOvenTempjLabel, null);
			jPanel.add(temperatureSetPointConfigurationjLabel, null);
			jPanel.add(getActualOvenTempjTextField(), null);
			jPanel.add(getSetPointTemperaturejTextField(), null);
			jPanel.add(getJSeparator(), null);
		}
		return jPanel;
	}
	
	/**
	 * This method initializes graphPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getGraphPanel() {
		if (graphPanel == null) {
			graphPanel = new JPanel();
			graphPanel.setName("Graph");
			
			realOvenTempSerie = new org.jfree.data.time.TimeSeries("Real Temp In Oven",  FixedMillisecond.class);
			desiredOvenTempSerie = new org.jfree.data.time.TimeSeries("Desired Temp In Oven",  FixedMillisecond.class);
			
			
			graphDataset = new TimeSeriesCollection();

			graphDataset.addSeries(realOvenTempSerie);
			graphDataset.addSeries(desiredOvenTempSerie);
			
			chart = ChartFactory.createTimeSeriesChart(
					"",
					"",
					"Temperature",
					graphDataset,
					true,
					true,
					false);
			
			chart.getXYPlot().getDomainAxis().setVisible(false);
			chart.getXYPlot().setBackgroundPaint(Color.CYAN);
			chart.getXYPlot().setDomainGridlinePaint(Color.BLACK);
			chart.getXYPlot().setRangeGridlinePaint(Color.BLACK);
			chart.getXYPlot().setDomainCrosshairVisible(true);
			chart.getXYPlot().setRangeCrosshairVisible(true);
			chart.getXYPlot().setBackgroundImage(null);
			
			XYItemRenderer r = chart.getXYPlot().getRenderer();
	        if (r instanceof XYLineAndShapeRenderer) {
	            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
	            renderer.setBaseItemLabelsVisible(true);
	            renderer.setSeriesStroke(0,new BasicStroke(2.0f));
	            renderer.setSeriesStroke(1,new BasicStroke(3.0f));
	            renderer.setShapesVisible(false);
	            renderer.setShapesFilled(false);
	        }
		        
			chartPanel = new ChartPanel(chart,false);
			chartPanel.setMouseZoomable(true, false);
			
			chartPanel.setPreferredSize(new Dimension(250,250));	
			TextTitle textTitle = new TextTitle("");
			textTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
			chart.setTitle(textTitle);
			chartPanel.setFont(new Font("Dialog", Font.PLAIN, 24));
			chartPanel.setBounds(new Rectangle(0, 0, 594, 301));
			graphPanel.setPreferredSize(new Dimension(250,250));
			
			graphPanel.setLayout(null);
			graphPanel.add(chartPanel, null);
		}
		return graphPanel;
	}

	/**
	 * This method initializes actualOvenTempjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getActualOvenTempjTextField() {
		if (actualOvenTempjTextField == null) {
			actualOvenTempjTextField = new JTextField();
			actualOvenTempjTextField.setText("20ºC");
			actualOvenTempjTextField.setHorizontalAlignment(JTextField.CENTER);
			actualOvenTempjTextField.setBounds(new Rectangle(225, 120, 76, 31));
			actualOvenTempjTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
		}
		return actualOvenTempjTextField;
	}


	/**
	 * This method initializes jSeparator	
	 * 	
	 * @return javax.swing.JSeparator	
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBackground(Color.green);
			jSeparator.setBounds(new Rectangle(15, 105, 436, 8));
			jSeparator.setPreferredSize(new Dimension(0, 10));
		}
		return jSeparator;
	}


	/**
	 * This method initializes setPointTemperaturejTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getSetPointTemperaturejTextField() {
		if (setPointTemperaturejTextField == null) {
			setPointTemperaturejTextField = new JTextField();
			setPointTemperaturejTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
			setPointTemperaturejTextField.setHorizontalAlignment(JTextField.CENTER);
			setPointTemperaturejTextField.setBounds(new Rectangle(15, 60, 61, 36));
			setPointTemperaturejTextField.setText("0");
		}
		return setPointTemperaturejTextField;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Temperature", null, getJPanel(), null);
			jTabbedPane.addTab("Temperature Evolution", null, getGraphPanel(), null);
		}
		return jTabbedPane;
	}
	public void insertTempPointAtRealOvenTempGraphSerie(long _SystemTimeInMilliseconds,double _temp){
		realOvenTempSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_temp);
	}
	public void insertTempPointAtDesiredOvenTempGraphSerie(long _SystemTimeInMilliseconds,double _temp){
		desiredOvenTempSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_temp);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OvenManualOperation_JFrame j = new OvenManualOperation_JFrame();
		j.show();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
