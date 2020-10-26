package views;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import Main.MainController;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.BoxLayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Shape;
import java.util.Date;

import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;

import sensors.TemperatureSensor;
import calibrationSetUp.CalibrationSetUp;

import controller.ActionRequest;

public class CalibrationSetUp_ProgressScreen_JFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private static String 		MADRID_TIME_ZONE = "GMT+1";

	private MainController 		mainController = null;  //  @jve:decl-index=0:
	private DefaultTableModel 	defaultTableModel = null;



	private JPanel 			jContentPane = null;
	private JPanel 			progressPanel = null;
	private JProgressBar 	doneInPercentProgressBar = null;
	private JTextField 		elapsedTimeField = null;
	private JTextField 		remainingTimeField = null;
	private JTabbedPane 	logResultsTableAndGraphTabbedPane = null;
	private JSplitPane 		logAndResultsTableSplitPane = null;
	private JPanel 			graphPanel = null;
	private JTextArea 		logTextArea = null;
	private JScrollPane 	resultsTableScrollPane = null;
	private JScrollPane 	logTextAreaScrollPane = null;

	private JTable resultsTable = null;
	private int tableRowPointer;
	private JLabel remainingTimeLabel = null;
	private JLabel elapsedTimeLabel = null;
	private JLabel progressBarLabel = null;

	private org.jfree.data.time.TimeSeries realOvenTempSerie = null;  //  @jve:decl-index=0:
	private org.jfree.data.time.TimeSeries desiredOvenTempSerie = null;  //  @jve:decl-index=0:
	private org.jfree.data.time.TimeSeries ovenOutputPowerSerie = null;
	private org.jfree.data.time.TimeSeries temperatureErrorSerie = null;

	private TimeSeriesCollection principalGraphDataset = null;
	private TimeSeriesCollection secondaryGraphDataset = null;
	private JFreeChart chart = null;
	private ChartPanel chartPanel = null;

	private Dimension frameDimension = new Dimension(1000, 800);
	private Dimension tabbedPaneDimension = new Dimension((int)(frameDimension.getWidth()-50), (int)(frameDimension.getHeight()-50));
	private Dimension chartPaneDimension = new Dimension((int)(frameDimension.getWidth()-150), (int)(frameDimension.getHeight()-150));
	private Dimension graphPaneDimension = new Dimension((int)(frameDimension.getWidth()-100), (int)(frameDimension.getHeight()-100));



	/**
	 * This is the default constructor
	 */
	public CalibrationSetUp_ProgressScreen_JFrame(MainController _mainController) {
		super();
		mainController = _mainController;
		addWindowListener(mainController);
		initialize();
	}
	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
		printActionMessage("Initializing Program Progress Screen Frame.");
		this.setSize(new Dimension(1000, 746));
		this.setResizable(false);
		this.setName("ProgressScreenFrame");
		this.setTitle("ProgressScreenFrame");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(getJContentPane());
   		this.getContentPane().setVisible(true);
   		this.getContentPane().repaint();
    	this.show(true);
   		this.repaint();
	}
	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	public void setDefaultTableModel(DefaultTableModel _tableModel) {
		this.resultsTable.setModel(_tableModel);
		this.jContentPane.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getProgressPanel(), null);
			jContentPane.add(getLogResultsTableAndGraphTabbedPane(), null);
			jContentPane.setVisible(false);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getProgressPanel() {
		if (progressPanel == null) {
			progressBarLabel = new JLabel();
			progressBarLabel.setBounds(new Rectangle(685, 5, 121, 26));
			progressBarLabel.setText("Overall Progress");
			progressBarLabel.setHorizontalAlignment(SwingConstants.CENTER);
			elapsedTimeLabel = new JLabel();
			elapsedTimeLabel.setBounds(new Rectangle(360, 5, 138, 26));
			elapsedTimeLabel.setText("Elapsed Time");
			elapsedTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			remainingTimeLabel = new JLabel();
			remainingTimeLabel.setBounds(new Rectangle(0, 5, 140, 26));
			remainingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			remainingTimeLabel.setText("Remaining Time");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			progressPanel = new JPanel();
			progressPanel.setLayout(null);
			progressPanel.setBounds(new Rectangle(0, 690, 991, 33));
			progressPanel.add(getJScrollBar(), null);
			progressPanel.add(getJTextField(), null);
			progressPanel.add(getJTextField1(), null);
			progressPanel.add(remainingTimeLabel, null);
			progressPanel.add(elapsedTimeLabel, null);
			progressPanel.add(progressBarLabel, null);
		}
		return progressPanel;
	}

	/**
	 * This method initializes jScrollBar
	 *
	 * @return javax.swing.JScrollBar
	 */
	private JProgressBar getJScrollBar() {
		if (doneInPercentProgressBar == null) {
			doneInPercentProgressBar = new JProgressBar();
			doneInPercentProgressBar.setOrientation(JScrollBar.HORIZONTAL);
			doneInPercentProgressBar.setBounds(new Rectangle(807, 5, 184, 26));
		}
		return doneInPercentProgressBar;
	}

	/**
	 * This method initializes jTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (elapsedTimeField == null) {
			elapsedTimeField = new JTextField();
			elapsedTimeField.setPreferredSize(new Dimension(4, 17));
			elapsedTimeField.setBounds(new Rectangle(500, 5, 165, 26));
			elapsedTimeField.setHorizontalAlignment(JTextField.CENTER);
		}
		return elapsedTimeField;
	}

	/**
	 * This method initializes jTextField1
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (remainingTimeField == null) {
			remainingTimeField = new JTextField();
			remainingTimeField.setPreferredSize(new Dimension(4, 17));
			remainingTimeField.setBounds(new Rectangle(140, 5, 184, 26));
			remainingTimeField.setHorizontalAlignment(JTextField.CENTER);
		}
		return remainingTimeField;
	}

	/**
	 * This method initializes logResultsTableAndGraphTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getLogResultsTableAndGraphTabbedPane() {
		if (logResultsTableAndGraphTabbedPane == null) {
			logResultsTableAndGraphTabbedPane = new JTabbedPane();
			logResultsTableAndGraphTabbedPane.setName("logAndResultsTable");
			logResultsTableAndGraphTabbedPane.setToolTipText("logAndResultsTable");
			logResultsTableAndGraphTabbedPane.setBounds(new Rectangle(0, 0, 990, 682));
			logResultsTableAndGraphTabbedPane.addTab(getLogAndResultsTableSplitPane().getName(), null, getLogAndResultsTableSplitPane(), null);
			logResultsTableAndGraphTabbedPane.addTab(getOvenTemperatureAndOvenPowerEvolutionGraphPanel().getName(), null, getOvenTemperatureAndOvenPowerEvolutionGraphPanel(), null);
		}
		return logResultsTableAndGraphTabbedPane;
	}

	/**
	 * This method initializes logAndResultsTableSplitPane
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getLogAndResultsTableSplitPane() {
		if (logAndResultsTableSplitPane == null) {
			logAndResultsTableSplitPane = new JSplitPane();
			logAndResultsTableSplitPane.setName("Logging Data and Results Table");
			logAndResultsTableSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			logAndResultsTableSplitPane.setTopComponent(getResultsTableScrollPane());
			logAndResultsTableSplitPane.setBottomComponent(getLogTextAreaScrollPane());
			logAndResultsTableSplitPane.setDividerLocation(150);
		}
		return logAndResultsTableSplitPane;
	}

	/**
	 * This method initializes graphPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getOvenTemperatureAndOvenPowerEvolutionGraphPanel() {
		if (graphPanel == null) {
			graphPanel = new JPanel();
			graphPanel.setName("Graph");

			realOvenTempSerie = new org.jfree.data.time.TimeSeries("Real Temp In Oven",  FixedMillisecond.class);
			desiredOvenTempSerie = new org.jfree.data.time.TimeSeries("Desired Oven Temp",  FixedMillisecond.class);
			ovenOutputPowerSerie = new org.jfree.data.time.TimeSeries("Oven Output Power",  FixedMillisecond.class);
			temperatureErrorSerie = new org.jfree.data.time.TimeSeries("Temperature Error",  FixedMillisecond.class);

			principalGraphDataset = new TimeSeriesCollection();
			principalGraphDataset.addSeries(realOvenTempSerie);
			principalGraphDataset.addSeries(desiredOvenTempSerie);

			secondaryGraphDataset = new TimeSeriesCollection();
			secondaryGraphDataset.addSeries(ovenOutputPowerSerie);
			secondaryGraphDataset.addSeries(temperatureErrorSerie);

			chart = ChartFactory.createTimeSeriesChart(
					"Oven Temperature - Power",
					"Hour",
					"Temperature",
					null,
					true,
					true,
					false);


			XYPlot plot = chart.getXYPlot();
			//plot.getDomainAxis().setVerticalTickLabels(true);
			plot.setBackgroundPaint(Color.CYAN);
			plot.setDomainGridlinePaint(Color.BLACK);
			plot.setRangeGridlinePaint(Color.BLACK);
			plot.setDomainCrosshairVisible(true);
			plot.setRangeCrosshairVisible(true);
			plot.setBackgroundImage(null);


		    NumberAxis axis1 = new NumberAxis("Temperature");
			axis1.setAutoRangeIncludesZero(false);
			plot.setRangeAxis(0, axis1);
			plot.setDataset(0,principalGraphDataset);
			plot.mapDatasetToRangeAxis(0, 0);

			XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer();
		    renderer1.setSeriesPaint(0, Color.RED);
		    renderer1.setSeriesPaint(1, Color.BLUE);
		    //renderer1.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		    renderer1.setBaseItemLabelsVisible(true);
	        renderer1.setSeriesStroke(0,new BasicStroke(1.0f));
	        renderer1.setSeriesStroke(1,new BasicStroke(1.0f));
		    renderer1.setSeriesShapesVisible(0, false);
		    renderer1.setSeriesShapesVisible(1, false);
	        plot.setRenderer(0, renderer1);

	        NumberAxis axis2 = new NumberAxis("Percent");
			axis2.setAutoRangeIncludesZero(false);
			plot.setRangeAxis(1, axis2);
			plot.setDataset(1,secondaryGraphDataset);
			plot.mapDatasetToRangeAxis(1, 1);


			XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
	        renderer2.setSeriesPaint(0, Color.black);
	        renderer2.setSeriesPaint(1, Color.GREEN);
	        //renderer2.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
	        renderer2.setBaseItemLabelsVisible(true);
            renderer2.setSeriesStroke(0,new BasicStroke(1.0f));
            renderer2.setSeriesStroke(1,new BasicStroke(1.0f));
		    renderer2.setSeriesShapesVisible(0, false);
		    renderer2.setSeriesShapesVisible(1, false);
            plot.setRenderer(1, renderer2);

	        //TODO Termina de añadir el segundo y axis para el Oven Ouput Power

			chartPanel = new ChartPanel(chart,false);
			chartPanel.setMouseZoomable(true, false);

			chartPanel.setPreferredSize(chartPaneDimension);
			graphPanel.add(chartPanel);
			graphPanel.setPreferredSize(graphPaneDimension);
			graphPanel.setLayout(new GridBagLayout());
		}
		return graphPanel;
	}

	/**
	 * This method initializes logTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getLogTextArea() {
		if (logTextArea == null) {
			logTextArea = new JTextArea();
			logTextArea.setBounds(new Rectangle(0, 0, 980, 548));
		}
		return logTextArea;
	}

	/**
	 * This method initializes resultsTableScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getResultsTableScrollPane() {
		if (resultsTableScrollPane == null) {
			resultsTableScrollPane = new JScrollPane();
			resultsTableScrollPane.setViewportView(getResultsTable());
		}
		return resultsTableScrollPane;
	}
	/**
	 * This method initializes resultsTableScrollPane
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getLogTextAreaScrollPane() {
		if (logTextAreaScrollPane == null) {
			logTextAreaScrollPane = new JScrollPane();
			logTextAreaScrollPane.setViewportView(this.getLogTextArea());
		}
		return logTextAreaScrollPane;
	}


	/**
	 * This method initializes resultsTable
	 *
	 * @return javax.swing.JTable
	 */
	private JTable getResultsTable() {
		if (resultsTable == null) {
			resultsTable = new JTable();
			resultsTable.setFillsViewportHeight(true);
			tableRowPointer = 0;
		}
		return resultsTable;
	}
	public void insertTempPointAtRealOvenTempGraphSerie(long _SystemTimeInMilliseconds,double _temp){
		realOvenTempSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_temp);
	}
	public void insertTempPointAtDesiredOvenTempGraphSerie(long _SystemTimeInMilliseconds,double _temp){
		desiredOvenTempSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_temp);
	}
	public void insertPowerPointAtOvenOutputPowerSerie(long _SystemTimeInMilliseconds,double _outputPower){
		ovenOutputPowerSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_outputPower);
	}
	public void insertTempErrorPointAtTemperatureErrorSerie(long _SystemTimeInMilliseconds,double _tempError){
		temperatureErrorSerie.add(new FixedMillisecond(_SystemTimeInMilliseconds),_tempError);
	}
	public void appendTextToLogTextArea(String _newLine){
		logTextArea.append(_newLine+"\n");
    }
	public void insertRowAtResultsTable(Object[] _data){
    	((DefaultTableModel)this.resultsTable.getModel()).insertRow(tableRowPointer, _data);
    	tableRowPointer++;
    }
    private void insertRowAtResultsTable(Object[] _data, int _row){
    	((DefaultTableModel)this.resultsTable.getModel()).insertRow(_row, _data);
    }
    public void refreshCalibrationResultTableData(Object[][] _data){
    	for (int i=0;i<_data.length;i++){
    		insertRowIn(_data[i], i);
    	}
    }
    private void insertRowIn(Object[] _data, int _row){
    	((DefaultTableModel)this.resultsTable.getModel()).insertRow(_row, _data);
    }
    public void refreshProgressBarData(int _progress){
    	this.doneInPercentProgressBar.setValue(_progress);
    	this.doneInPercentProgressBar.setString(_progress+"%");
    	this.doneInPercentProgressBar.setStringPainted(true);
    }
    public void refreshRemainingTimeField(String _remainingTime){
    	this.remainingTimeField.setText(_remainingTime);
    }
    public void refreshElapsedTimeField(String _elapsedTime){
    	this.elapsedTimeField.setText(_elapsedTime);
    }
    public int initializeResultsTable(Object _header[]){
    	if (defaultTableModel==null) defaultTableModel = new DefaultTableModel();
		defaultTableModel.setRowCount(0);
		defaultTableModel.setRowCount(0);
		defaultTableModel.setColumnCount(_header.length);
		setDefaultTableModel(defaultTableModel);
		insertRowAtResultsTable(_header);
		return 0;
	}
    private void printActionMessage(String _msg){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(_msg);
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			if((((k/2)-(_msg.length()/2))+_msg.length()+i)>100) break;
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.println("\n");
	}
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		CalibrationSetUp_ProgressScreen_JFrame progressScreenFrame = new CalibrationSetUp_ProgressScreen_JFrame(null);
		long _SystemTimeInMilliseconds;
		double realOvenTemp = 0;
		double desiredOvenTemp = 0;
		double ovenOutputPower = 0;
		double tempError = 0;
		while(true){
			try {
				Thread.sleep(200);
				_SystemTimeInMilliseconds = System.currentTimeMillis();

				desiredOvenTemp = Math.random()*100;
				realOvenTemp = Math.random()*100;
				ovenOutputPower = _SystemTimeInMilliseconds%100;
				tempError = 100;

				progressScreenFrame.insertTempPointAtRealOvenTempGraphSerie(_SystemTimeInMilliseconds,realOvenTemp);
				progressScreenFrame.insertTempPointAtDesiredOvenTempGraphSerie(_SystemTimeInMilliseconds, desiredOvenTemp);
				progressScreenFrame.insertPowerPointAtOvenOutputPowerSerie(_SystemTimeInMilliseconds, ovenOutputPower);
				progressScreenFrame.insertTempErrorPointAtTemperatureErrorSerie(_SystemTimeInMilliseconds, tempError);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


}  //  @jve:decl-index=0:visual-constraint="29,1"
