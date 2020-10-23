package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.SimpleTimeZone;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import Data.ThermalCalibrationProgramData;

import de.progra.charting.DefaultChart;
import de.progra.charting.event.ChartDataModelEvent;
import de.progra.charting.event.ChartDataModelListener;
import de.progra.charting.model.EditableChartDataModel;
import de.progra.charting.render.LineChartRenderer;
import de.progra.charting.swing.ChartPanel;



public class ProgressScreenPanel2 extends JPanel implements ItemListener, ChartDataModelListener{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JPanel resultsAndLogPanel;
	private JPanel chartDataPanel;
	private JPanel[] panels;
	private Dimension d;

    private EditableChartDataModel chartData;
    private ChartPanel chartPanel;

    private JTextPane	textPane;
    private JTextArea 	changeLog;
    private String[]	tableColumnNames;
    private JTable 		calibrationResultTable;
    private DefaultTableModel defaultTableModel;
    private JProgressBar progressBar;
    private GridBagConstraints constraints;


	public ProgressScreenPanel2(JFrame _frame,ThermalCalibrationProgramData _program, int _nTableRows, int _nTableColumns,Dimension _dimension){
		super();

		constraints = new GridBagConstraints ();
		d = new Dimension(800,600);

		//Create table and Init table
        createCalibrationResultTable(_program, _nTableRows, _nTableColumns);

		tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(d);
		tabbedPane.setSize(d);

		//The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		panels = createPanels(_dimension);
		addPanelsToTabbedPane(panels,tabbedPane);
		add(tabbedPane);

		//Add the components.
        Container content_pane = _frame.getContentPane();
        content_pane.setPreferredSize(_dimension);
		content_pane.setSize(_dimension);
        content_pane.add(tabbedPane, BorderLayout.CENTER);
	}
	private JPanel[] createPanels(Dimension _dimension){
		JPanel[] panels = new JPanel[2];
		panels[0] = createResultsAndLogPanel(_dimension);
		panels[1] = createChartDataPanel(chartPanel);
		return panels;
	}
	private JPanel createChartDataPanel(ChartPanel _chartPanel){
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setName("Chart");
		panel.setPreferredSize(d);
		panel.setSize(d);

    	double[][] model = {{0,0},
                {0,0}};

        double[] columns = {0.0,1.0};

        String[] rows = {"Desired. Temp.", "Real. Temp."};

        String title = "TTC calibrate proccess. Desired Temp vs Real Temp";

        chartData = new EditableChartDataModel(model, columns, rows);
        //ObjectChartDataModel odata = new ObjectChartDataModel(model, columnString, rows);
        _chartPanel = new ChartPanel(chartData, title, DefaultChart.LINEAR_X_LINEAR_Y);
        _chartPanel.addChartRenderer(new LineChartRenderer(_chartPanel.getCoordSystem(), chartData), 1);
        chartData.addChartDataModelListener(this);
        panel.add(_chartPanel, BorderLayout.CENTER);

	    return panel;
	}
	private JPanel createResultsAndLogPanel(Dimension _dimension){

		JPanel panel = new JPanel();

        //Create the text pane and configure it.
        textPane = new JTextPane();
        textPane.setCaretPosition(0);
        textPane.setMargin(new Insets(5,5,5,5));


        JScrollPane scrollPane = new JScrollPane(calibrationResultTable);
        scrollPane.setPreferredSize(new Dimension(_dimension.width, _dimension.height-_dimension.height/2));

        //Create the text area for the status log and configure it.
        changeLog = new JTextArea(5, 30);
        changeLog.setEditable(false);
        JScrollPane scrollPaneForLog = new JScrollPane(changeLog);
        scrollPaneForLog.setPreferredSize(new Dimension(1000, 200));

        //Create the progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        //Create the progress area.
        JPanel statusPane = new JPanel(new GridLayout(1, 2));
        JLabel calibrationProgressLabel =
                new JLabel("Calibration Progress");
        statusPane.add(calibrationProgressLabel);
        setComponentInGrid(statusPane, calibrationProgressLabel, 1, 1, 1, 1, 1, 1,  GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL);
        setComponentInGrid(statusPane, progressBar, 1, 2, 1, 1, 1, 1,  GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL);
        progressBar.setValue(0);


        //Create a split pane for the change log and the text area.
        JSplitPane splitPane = new JSplitPane(
                                       JSplitPane.VERTICAL_SPLIT,
                                       scrollPane, scrollPaneForLog);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);
        splitPane.setName("Results Table and Log");

        panel.add(splitPane,BorderLayout.NORTH);
        panel.add(statusPane,BorderLayout.SOUTH);

	    return panel;
	}
	private void addPanelsToTabbedPane(JPanel[] _panels, JTabbedPane _tabbedPane){
		for (int i=0;i<_panels.length;i++){
			_tabbedPane.addTab(_panels[i].getName(), _panels[i]);
			_tabbedPane.setTitleAt(i,_panels[i].getName());
		}
	}
	private void createCalibrationResultTable(ThermalCalibrationProgramData _program, int _rows, int _columns){
    	defaultTableModel = new DefaultTableModel();
    	defaultTableModel.setColumnCount(_columns);
    	defaultTableModel.setRowCount(_rows);
    	calibrationResultTable = new JTable(defaultTableModel);
    	calibrationResultTable.setFillsViewportHeight(true);
    }
	public void appendTextToLogPane(String _newLine){
    	changeLog.append(_newLine+"\n");
    }
    public void insertRowAt(Object[] _data, int _row){
    	defaultTableModel.insertRow(_row, _data);
    }
    public void refreshCalibrationResultTableData(Object[][] _data){
    	for (int i=0;i<_data.length;i++){
    		insertRowAt(_data[i], i);
    	}
    }
    public void refreshProgressBarData(int _progress){
    	progressBar.setValue(_progress);
    }
	public void setComponentInGrid(Container content_pane, Component comp,int gridX, int gridY, int gridWidth, int gridHeight, int weightX, int weightY, int anchor, int fill  ){
	   	constraints.gridx = gridX;
	    constraints.gridy = gridY;
	    constraints.gridwidth = gridWidth;
	    constraints.gridheight = gridHeight;
	    constraints.weighty = weightY; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = weightX; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.fill =fill;
	    constraints.anchor = anchor;
	    content_pane.add(comp, constraints);
	    constraints.fill = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.anchor = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.weighty = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	}
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}
	/*
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CheckBoxDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = new Dimension(850,650);
        frame.setPreferredSize(d);
        frame.setSize(new Dimension(d));

        //Create and set up the content pane.
        JComponent progressScreenPanel2 = new ProgressScreenPanel2();
        progressScreenPanel2.setOpaque(true); //content panes must be opaque
        frame.setContentPane(progressScreenPanel2);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }*/

    /*public static void main(String[] args) {*/
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });*/
    	 //Create and set up the window.
        /*JFrame frame = new JFrame("CheckBoxDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = new Dimension(850,650);
        frame.setPreferredSize(d);
        frame.setSize(new Dimension(d));

        //Create and set up the content pane.
        ProgressScreenPanel2 progressScreenPanel2 = new ProgressScreenPanel2();
        progressScreenPanel2.setOpaque(true); //content panes must be opaque
        frame.setContentPane(progressScreenPanel2);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        for (int i=-100;i<100;i++){
        	progressScreenPanel2.insertData(0,  (double)i,(double)(i*i*i*i + i*i*i + i*i + i + 10));
        	progressScreenPanel2.insertData(1,  (double)i,-(double)(i*i*i*i + i*i*i + i*i + i + 10));
			//p.data.insertValue(1, (double)i,100*Math.sin(i/100));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

    }*/

    public void insertData(int _serie, double _x,double _y){
    	chartData.insertValue(_serie, _y, _x);
	}
	public void removeDataInSerie(int _serie, int _x){
		chartData.removeValueAt(_serie, _x);
	}
	public void chartDataChanged(ChartDataModelEvent evt) {
		//chartPanel.invalidate();
		if (chartPanel!=null)
		{
			chartPanel.revalidate();
			repaint();
		}

	}
}
