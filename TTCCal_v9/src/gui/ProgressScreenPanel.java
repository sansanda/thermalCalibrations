package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import de.progra.charting.DefaultChart;
import de.progra.charting.model.EditableChartDataModel;
import de.progra.charting.render.LineChartRenderer;
import de.progra.charting.swing.ChartPanel;
import Data.ThermalCalibrationProgramData;

public class ProgressScreenPanel extends JPanel{

	private static final Logger logger = Logger.getLogger(ProgressScreenPanel.class);
	private static final long serialVersionUID = 1L;
	JTextPane	textPane;
    JTextArea 	changeLog;
    ChartPanel 	chartPanel;
    String[]	tableColumnNames;
    JTable 		calibrationResultTable;
    DefaultTableModel defaultTableModel;
    JProgressBar progressBar;
    GridBagConstraints constraints;

    public ProgressScreenPanel(JFrame _frame,ThermalCalibrationProgramData _program, int _nTableRows, int _nTableColumns,Dimension _dimension){

        constraints = new GridBagConstraints ();

        //Create table and Init table
        createCalibrationResultTable(_program, _nTableRows, _nTableColumns);

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


        //Create a split pane for the change log and the text area.
        JSplitPane splitPane = new JSplitPane(
                                       JSplitPane.VERTICAL_SPLIT,
                                       scrollPane, scrollPaneForLog);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);


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

        
        
        //Add the components.
        Container content_pane = _frame.getContentPane();
        content_pane.setPreferredSize(_dimension);
		content_pane.setSize(_dimension);
        content_pane.add(splitPane, BorderLayout.CENTER);
        content_pane.add(statusPane, BorderLayout.PAGE_END);
    }
    private void createChartPanel(){
    	EditableChartDataModel data;

    	double[][] model = {{25.0, 22.0, 23.0},
                {13.0, 11.0, 12.0}};

        double[] columns = {0.0,1.0,2.0};

        String[] rows = {"Desired. Temp.", "Real. Temp."};

        String title = "TTC calibrate proccess. Desired Temp vs Real Temp";

        data = new EditableChartDataModel(model, columns, rows);
        //ObjectChartDataModel odata = new ObjectChartDataModel(model, columnString, rows);
        chartPanel = new ChartPanel(data, title, DefaultChart.LINEAR_X_LINEAR_Y);
        chartPanel.addChartRenderer(new LineChartRenderer(chartPanel.getCoordSystem(), data), 1);

        //data.addChartDataModelListener(this);
        /*chartPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent m) {
				if (logger.isDebugEnabled()) {
					logger.debug("$MouseAdapter.mouseClicked(MouseEvent) - start"); //$NON-NLS-1$
				}

                if(SwingUtilities.isRightMouseButton(m)) {
                    jPopupMenu1.setLocation(m.getX(), m.getY());
                    jPopupMenu1.setVisible(true);
                } else
                    jPopupMenu1.setVisible(false);


				if (logger.isDebugEnabled()) {
					logger.debug("$MouseAdapter.mouseClicked(MouseEvent) - end"); //$NON-NLS-1$
				}
            }
        });*/

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
}


