package gui;

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
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import frontController.ActionRequest;

public class MainScreenFrame2 extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private MainController mainController;
	private JMenuBar mainScreenFrameMenuBar = null;
	private JMenu helpMenu = null;
	private JMenu fileMenu = null;
	private JMenu programMenu = null;
	private JMenuItem programMenuItem1 = null;
	private JMenu ovenMenu = null;
	private JMenuItem ovenMenuItem1 = null;
	private JMenuItem helpMenuItem1 = null;
	private JMenuItem helpMenuItem2 = null;
	private JMenuItem fileMenuItem1 = null;
	private JMenuItem fileMenuItem2 = null;
	private JMenuItem fileMenuItem3 = null;
	private JMenuItem fileMenuItem4 = null;
	private JPanel jContentPane = null;
	private JPanel progressPanel = null;
	private JScrollBar doneInPercentProgressBar = null;
	private JTextField elapsedTimeField = null;
	private JTextField remainingTimeField = null;
	private JTabbedPane logResultsTableAndGraphTabbedPane = null;
	private JSplitPane logAndResultsTableSplitPane = null;
	private JPanel graphPanel = null;
	private JTextArea logTextArea = null;
	private JScrollPane resultsTableScrollPane = null;

	private JTable resultsTable = null;
    private DefaultTableModel defaultTableModel;
    private int nTableColumns;
    private int nTableRows;

	/**
	 * This is the default constructor
	 */
	public MainScreenFrame2(MainController _mainController) {
		super();
		mainController = _mainController;
		initialize();

	}
	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
		this.setSize(1000, 600);
		this.setResizable(false);
		this.setName("MainScreenFrame");
		this.setTitle("MainScreenFrame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(getJContentPane());
        this.setJMenuBar(getJJMenuBar());
	}
	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	public void setDefaultTableModel(DefaultTableModel _tableModel) {
		this.defaultTableModel = new DefaultTableModel();
		this.jContentPane.setVisible(true);
	}
	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (mainScreenFrameMenuBar == null) {
			mainScreenFrameMenuBar = new JMenuBar();
			mainScreenFrameMenuBar.add(getJMenu());
			mainScreenFrameMenuBar.add(getJMenu2());
			mainScreenFrameMenuBar.add(getOven());
			mainScreenFrameMenuBar.add(getJMenu1());
		}
		return mainScreenFrameMenuBar;
	}

	/**
	 * This method initializes jMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.setName("Help");
			helpMenu.add(getHelpMenuItem1());
			helpMenu.add(getJMenuItem2());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenu1
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu1() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.setName("FileMenu");
			fileMenu.add(getJMenuItem111());
			fileMenu.add(getJMenuItem112());
			fileMenu.add(getJMenuItem113());
			fileMenu.add(getJMenuItem114());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu2
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu2() {
		if (programMenu == null) {
			programMenu = new JMenu();
			programMenu.setText("Program");
			programMenu.setName("Program");
			programMenu.add(getRun());
		}
		return programMenu;
	}

	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getRun() {
		if (programMenuItem1 == null) {
			programMenuItem1 = new JMenuItem();
			programMenuItem1.setName("Run");
			programMenuItem1.setText("Run");
			programMenuItem1.addActionListener(this);
		}
		return programMenuItem1;
	}

	/**
	 * This method initializes Oven
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getOven() {
		if (ovenMenu == null) {
			ovenMenu = new JMenu();
			ovenMenu.setText("Oven");
			ovenMenu.setName("Oven");
			ovenMenu.add(getJMenuItem());
		}
		return ovenMenu;
	}

	/**
	 * This method initializes jMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
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
	private JMenuItem getHelpMenuItem1() {
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
	private JMenuItem getJMenuItem2() {
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
	private JMenuItem getJMenuItem111() {
		if (fileMenuItem1 == null) {
			fileMenuItem1 = new JMenuItem();
			fileMenuItem1.setText("New");
			fileMenuItem1.setName("New");
			fileMenuItem1.addActionListener(this);
		}
		return fileMenuItem1;
	}

	/**
	 * This method initializes jMenuItem112
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem112() {
		if (fileMenuItem2 == null) {
			fileMenuItem2 = new JMenuItem();
			fileMenuItem2.setText("Edit");
			fileMenuItem2.setName("Edit");
			fileMenuItem2.addActionListener(this);
		}
		return fileMenuItem2;
	}

	/**
	 * This method initializes jMenuItem113
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem113() {
		if (fileMenuItem3 == null) {
			fileMenuItem3 = new JMenuItem();
			fileMenuItem3.setText("Open");
			fileMenuItem3.setName("Open");
			fileMenuItem3.addActionListener(this);
		}
		return fileMenuItem3;
	}

	/**
	 * This method initializes jMenuItem114
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem114() {
		if (fileMenuItem4 == null) {
			fileMenuItem4 = new JMenuItem();
			fileMenuItem4.setText("Exit");
			fileMenuItem4.setName("Exit");
			fileMenuItem4.addActionListener(this);
		}
		return fileMenuItem4;
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
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			progressPanel = new JPanel();
			progressPanel.setLayout(null);
			progressPanel.setBounds(new Rectangle(0, 523, 991, 26));
			progressPanel.add(getJScrollBar(), null);
			progressPanel.add(getJTextField(), null);
			progressPanel.add(getJTextField1(), null);
		}
		return progressPanel;
	}

	/**
	 * This method initializes jScrollBar
	 *
	 * @return javax.swing.JScrollBar
	 */
	private JScrollBar getJScrollBar() {
		if (doneInPercentProgressBar == null) {
			doneInPercentProgressBar = new JScrollBar();
			doneInPercentProgressBar.setOrientation(JScrollBar.HORIZONTAL);
			doneInPercentProgressBar.setSize(new Dimension(270, 17));
			doneInPercentProgressBar.setLocation(new Point(675, 5));
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
			elapsedTimeField.setSize(new Dimension(270, 17));
			elapsedTimeField.setLocation(new Point(360, 5));
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
			remainingTimeField.setSize(new Dimension(270, 17));
			remainingTimeField.setLocation(new Point(45, 5));
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
			logResultsTableAndGraphTabbedPane.addTab(null, null, getLogAndResultsTableSplitPane(), null);
			logResultsTableAndGraphTabbedPane.addTab(null, null, getGraphPanel(), null);
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
			logAndResultsTableSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			logAndResultsTableSplitPane.setTopComponent(getResultsTableScrollPane());
			logAndResultsTableSplitPane.setBottomComponent(getLogTextArea());
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
	 * This method initializes resultsTable
	 *
	 * @return javax.swing.JTable
	 */
	private JTable getResultsTable() {
		if (resultsTable == null) {
			resultsTable = new JTable();
			resultsTable.setFillsViewportHeight(true);
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
    public void insertRowAtResultsTable(Object[] _data, int _row){
    	this.defaultTableModel.insertRow(_row, _data);
    }
    public void refreshCalibrationResultTableData(Object[][] _data){
    	for (int i=0;i<_data.length;i++){
    		insertRowIn(_data[i], i);
    	}
    }
    public void insertRowIn(Object[] _data, int _row){
    	defaultTableModel.insertRow(_row, _data);
    }
    public void refreshProgressBarData(int _progress){
    	this.doneInPercentProgressBar.setValue(_progress);
    }

}  //  @jve:decl-index=0:visual-constraint="29,1"
