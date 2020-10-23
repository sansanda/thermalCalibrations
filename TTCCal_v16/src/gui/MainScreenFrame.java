package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import Data.ThermalCalibrationProgramData;
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

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frontController.ActionRequest;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainScreenFrame extends JFrame implements ActionListener{

	private static int RUN_PROGRAM_MODE = 1;
	private static int STOP_PROGRAM_MODE = 0;

	private static final long serialVersionUID = 1L;
	private MainController mainController = null;
	private DefaultTableModel defaultTableModel = null;

	private JMenuBar mainScreenFrameMenuBar = null;

	private JMenu fileMenu = null;
	private JMenuItem fileMenuItem1 = null;
	private JMenu programMenu = null;
	private JMenuItem programMenu_RunItem = null;
	private JMenuItem programMenu_StopItem = null;
	private JMenuItem programMenu_NewItem = null;
	private JMenuItem programMenu_EditItem = null;
	private JMenuItem programMenu_OpenItem = null;
	private JMenu ovenMenu = null;
	private JMenuItem ovenMenuItem1 = null;
	private JMenu helpMenu = null;
	private JMenuItem helpMenuItem1 = null;
	private JMenuItem helpMenuItem2 = null;


	private JPanel jContentPane = null;
	private JPanel progressPanel = null;
	private JProgressBar doneInPercentProgressBar = null;
	private JTextField elapsedTimeField = null;
	private JTextField remainingTimeField = null;
	private JTabbedPane logResultsTableAndGraphTabbedPane = null;
	private JSplitPane logAndResultsTableSplitPane = null;
	private JPanel graphPanel = null;
	private JTextArea logTextArea = null;
	private JScrollPane resultsTableScrollPane = null;
	private JScrollPane logTextAreaScrollPane = null;

	private JTable resultsTable = null;
	private int tableRowPointer;
	private JLabel remainingTimeLabel = null;
	private JLabel elapsedTimeLabel = null;
	private JLabel progressBarLabel = null;
	/**
	 * This is the default constructor
	 */
	public MainScreenFrame(MainController _mainController) {
		super();
		mainController = _mainController;
		initialize();
	}
	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
		printActionMessage("Initializing MainScreen Frame.");
		this.setSize(1000, 609);
		this.setResizable(false);
		this.setName("MainScreenFrame");
		this.setTitle("MainScreenFrame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setJMenuBar(getJMenuBar());
        this.configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
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
	public void configureMainScreenMenuBarMode(int _mode) {
		// TODO Auto-generated method stub
		if (_mode == STOP_PROGRAM_MODE){
			printActionMessage("MainScreen. Configuring Menu Bar in Stop Mode");
			helpMenu.setEnabled(true);
			programMenu.setEnabled(true);
			programMenu_RunItem.setEnabled(true);
			programMenu_StopItem.setEnabled(false);
			programMenu_NewItem.setEnabled(true);
			programMenu_EditItem.setEnabled(true);
			programMenu_OpenItem.setEnabled(true);
			ovenMenu.setEnabled(true);
			fileMenu.setEnabled(true);
			return;
		}
		if (_mode == RUN_PROGRAM_MODE){
			printActionMessage("MainScreen. Configuring Menu Bar in Run Mode");
			helpMenu.setEnabled(false);
			programMenu.setEnabled(true);
			programMenu_RunItem.setEnabled(false);
			programMenu_StopItem.setEnabled(true);
			programMenu_NewItem.setEnabled(false);
			programMenu_EditItem.setEnabled(false);
			programMenu_OpenItem.setEnabled(false);
			ovenMenu.setEnabled(false);
			fileMenu.setEnabled(false);
			return;
		}
	}
	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	public JMenuBar getJMenuBar() {
		if (mainScreenFrameMenuBar == null) {
			mainScreenFrameMenuBar = new JMenuBar();
			mainScreenFrameMenuBar.add(getHelpMenu());
			mainScreenFrameMenuBar.add(getProgramMenu());
			mainScreenFrameMenuBar.add(getOvenMenu());
			mainScreenFrameMenuBar.add(getFileMenu());
		}
		return mainScreenFrameMenuBar;
	}

	/**
	 * This method initializes jMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.setName("Help");
			helpMenu.add(getContentsMenuItem());
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenu1
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.setName("FileMenu");
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu2
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getProgramMenu() {
		if (programMenu == null) {
			programMenu = new JMenu();
			programMenu.setText("Program");
			programMenu.setName("Program");
			programMenu.add(getProgramMenu_RunItem());
			programMenu.add(getProgramMenu_StopItem());
			programMenu.add(new JSeparator());
			programMenu.add(getProgramMenu_NewItem());
			programMenu.add(getProgramMenu_EditItem());
			programMenu.add(getProgramMenu_OpenItem());
		}
		return programMenu;
	}

	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getProgramMenu_RunItem() {
		if (programMenu_RunItem == null) {
			programMenu_RunItem = new JMenuItem();
			programMenu_RunItem.setName("Run");
			programMenu_RunItem.setText("Run");
			programMenu_RunItem.addActionListener(this);
		}
		return programMenu_RunItem;
	}
	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getProgramMenu_StopItem() {
		if (programMenu_StopItem == null) {
			programMenu_StopItem = new JMenuItem();
			programMenu_StopItem.setName("Stop");
			programMenu_StopItem.setText("Stop");
			programMenu_StopItem.addActionListener(this);
		}
		return programMenu_StopItem;
	}
	/**
	 * This method initializes Oven
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getOvenMenu() {
		if (ovenMenu == null) {
			ovenMenu = new JMenu();
			ovenMenu.setText("Oven");
			ovenMenu.setName("Oven");
			ovenMenu.add(getSetTemperatureMenuItem());
		}
		return ovenMenu;
	}

	/**
	 * This method initializes jMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSetTemperatureMenuItem() {
		if (ovenMenuItem1 == null) {
			ovenMenuItem1 = new JMenuItem();
			ovenMenuItem1.setText("SetTemperature");
			ovenMenuItem1.setName("SetTemperature");
			ovenMenuItem1.addActionListener(this);
		}
		return ovenMenuItem1;
	}

	/**
	 * This method initializes HelpMenuItem1
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getContentsMenuItem() {
		if (helpMenuItem1 == null) {
			helpMenuItem1 = new JMenuItem();
			helpMenuItem1.setText("Contents");
			helpMenuItem1.setName("Contents");
			helpMenuItem1.addActionListener(this);
		}
		return helpMenuItem1;
	}

	/**
	 * This method initializes jMenuItem2
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (helpMenuItem2 == null) {
			helpMenuItem2 = new JMenuItem();
			helpMenuItem2.setText("About");
			helpMenuItem2.setName("About");
			helpMenuItem2.addActionListener(this);
		}
		return helpMenuItem2;
	}

	/**
	 * This method initializes jMenuItem111
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getProgramMenu_NewItem() {
		if (programMenu_NewItem == null) {
			programMenu_NewItem = new JMenuItem();
			programMenu_NewItem.setText("New");
			programMenu_NewItem.setName("New");
			programMenu_NewItem.addActionListener(this);
		}
		return programMenu_NewItem;
	}

	/**
	 * This method initializes jMenuItem112
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getProgramMenu_EditItem() {
		if (programMenu_EditItem == null) {
			programMenu_EditItem = new JMenuItem();
			programMenu_EditItem.setText("Edit");
			programMenu_EditItem.setName("Edit");
			programMenu_EditItem.addActionListener(this);
		}
		return programMenu_EditItem;
	}

	/**
	 * This method initializes jMenuItem113
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getProgramMenu_OpenItem() {
		if (programMenu_OpenItem == null) {
			programMenu_OpenItem = new JMenuItem();
			programMenu_OpenItem.setText("Open");
			programMenu_OpenItem.setName("Open");
			programMenu_OpenItem.addActionListener(this);
		}
		return programMenu_OpenItem;
	}

	/**
	 * This method initializes jMenuItem114
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (fileMenuItem1 == null) {
			fileMenuItem1 = new JMenuItem();
			fileMenuItem1.setText("Exit");
			fileMenuItem1.setName("Exit");
			fileMenuItem1.addActionListener(this);
		}
		return fileMenuItem1;
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
			progressPanel.setBounds(new Rectangle(0, 523, 991, 33));
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
			logResultsTableAndGraphTabbedPane.setBounds(new Rectangle(0, 0, 990, 520));
			logResultsTableAndGraphTabbedPane.addTab(getLogAndResultsTableSplitPane().getName(), null, getLogAndResultsTableSplitPane(), null);
			logResultsTableAndGraphTabbedPane.addTab(getGraphPanel().getName(), null, getGraphPanel(), null);
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
	private JPanel getGraphPanel() {
		if (graphPanel == null) {
			graphPanel = new JPanel();
			graphPanel.setName("Graph");
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

	public void actionPerformed (ActionEvent e){
		try {
			String command = e.getActionCommand ();
			System.out.println(command);
			if (command.equals ("Exit")) { //Exit from the application
				mainController.doAction(new ActionRequest("ExitApplicationAction",this));
			}
			if (command.equals ("Open")) { //Open a existing TTC Calibration Program
				mainController.doAction(new ActionRequest("ViewExistingCalibrationProgramAction",this));
			}
			if (command.equals ("New")) { //Create a new TTC Calibration Program
				mainController.doAction(new ActionRequest("CreateNewCalibrationProgramAction",this));
			}
			if (command.equals ("Edit")) { //Edit a existing TTC Calibration Program
				mainController.doAction(new ActionRequest("EditExistingCalibrationProgramAction",this));
			}
			if (command.equals ("About")) { //get the application About
				mainController.doAction(new ActionRequest("ViewAboutContentsAction",this));
			}
			if (command.equals ("Contents")) { //get the application help contents
				mainController.doAction(new ActionRequest("ViewHelpContentsAction",this));
			}
			if (command.equals ("Run")) { // Run a existing program
				mainController.doAction(new ActionRequest("StartCalibrationProgramAction",this));
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} // actionPerformed
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
	public int createProgressScreen(ThermalCalibrationProgramData _program){
		initializeResultsTable(_program);
		getContentPane().setVisible(true);
		getContentPane().repaint();
		repaint();
		return 0;
	}
    public int initializeResultsTable(ThermalCalibrationProgramData _program){
    	if (defaultTableModel==null) defaultTableModel = new DefaultTableModel();
		defaultTableModel.setRowCount(0);
		defaultTableModel.setColumnCount(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+6);
		setDefaultTableModel(defaultTableModel);
		Object[] _data = new Object[_program.getMeasuresConfigurationData().getNDevicesToMeasure()+6];
		_data[0]="# step";
		_data[1]="time";
		_data[2]="Desired TºC";
		_data[3]="Oven TºC";
		_data[4]="Real TºC (MChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")";
		_data[5]="Real T(Ohms) "+"(MChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")";
		 for (int i=6,j=0;i<(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+6);i++,j++){
			 _data[i] = _program.getMeasuresConfigurationData().getDevicesToMeasureData()[j].getDeviceReference()+"(MChannel="+_program.getMeasuresConfigurationData().getDevicesToMeasureData()[j].getDeviceChannel()+")";
		 }
		insertRowAtResultsTable(_data);
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

}  //  @jve:decl-index=0:visual-constraint="29,1"
