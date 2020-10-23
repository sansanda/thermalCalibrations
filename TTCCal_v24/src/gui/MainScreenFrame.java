package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import Main.MainController;
import Products.Data.TTCSetUpData;

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

import Factories.;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainScreenFrame extends JFrame implements ActionListener{

	private static int RUN_PROGRAM_MODE = 1;
	private static int STOP_PROGRAM_MODE = 0;

	private static final long serialVersionUID = 1L;
	private MainController mainController = null;

	private JMenuBar mainScreenFrameMenuBar = null;

	private JMenu fileMenu = null;
	private JMenuItem fileMenuItem1 = null;
	private JMenu TTC_CalibrationSetUpMenu = null;
	private JMenuItem TTC_CalibrationSetUpMenu_RunItem = null;
	private JMenuItem TTC_CalibrationSetUpMenu_AbortItem = null;
	private JMenuItem TTC_CalibrationSetUpMenu_NewItem = null;
	private JMenuItem TTC_CalibrationSetUpMenu_EditItem = null;
	private JMenuItem TTC_CalibrationSetUpMenu_OpenItem = null;
	private JMenu helpMenu = null;
	private JMenuItem helpMenuItem1 = null;
	private JMenuItem helpMenuItem2 = null;
	private JPanel jContentPane = null;
	private JTable resultsTable = null;
	private JMenuItem instrumentsComunicationsAndTemperatureSensorjMenuItem = null;
	private JMenu configurationjMenu = null;
	/**
	 * This is the default constructor
	 */
	public MainScreenFrame(MainController _mainController) {
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
		printActionMessage("Initializing MainScreen Frame.");
		this.setSize(1000, 609);
		this.setResizable(false);
		this.setName("MainScreenFrame");
		this.setTitle("MainScreenFrame");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
			TTC_CalibrationSetUpMenu.setEnabled(true);
			TTC_CalibrationSetUpMenu_RunItem.setEnabled(true);
			TTC_CalibrationSetUpMenu_AbortItem.setEnabled(false);
			TTC_CalibrationSetUpMenu_NewItem.setEnabled(true);
			TTC_CalibrationSetUpMenu_EditItem.setEnabled(true);
			TTC_CalibrationSetUpMenu_OpenItem.setEnabled(true);
			fileMenu.setEnabled(true);
			return;
		}
		if (_mode == RUN_PROGRAM_MODE){
			printActionMessage("MainScreen. Configuring Menu Bar in Run Mode");
			helpMenu.setEnabled(false);
			TTC_CalibrationSetUpMenu.setEnabled(true);
			TTC_CalibrationSetUpMenu_RunItem.setEnabled(false);
			TTC_CalibrationSetUpMenu_AbortItem.setEnabled(true);
			TTC_CalibrationSetUpMenu_NewItem.setEnabled(false);
			TTC_CalibrationSetUpMenu_EditItem.setEnabled(false);
			TTC_CalibrationSetUpMenu_OpenItem.setEnabled(false);
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
			mainScreenFrameMenuBar.add(getTTC_CalibrationSetUpMenu());
			mainScreenFrameMenuBar.add(getConfigurationjMenu());
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
	private JMenu getTTC_CalibrationSetUpMenu() {
		if (TTC_CalibrationSetUpMenu == null) {
			TTC_CalibrationSetUpMenu = new JMenu();
			TTC_CalibrationSetUpMenu.setText("TTC_CalibrationSetUp");
			TTC_CalibrationSetUpMenu.setName("TTC_CalibrationSetUp");
			TTC_CalibrationSetUpMenu.add(get_TTC_CalibrationSetUpMenu_RunItem());
			TTC_CalibrationSetUpMenu.add(get_TTC_CalibrationSetUpMenu_AbortItem());
			TTC_CalibrationSetUpMenu.add(new JSeparator());
			TTC_CalibrationSetUpMenu.add(get_TTC_CalibrationSetUpMenu_NewItem());
			TTC_CalibrationSetUpMenu.add(get_TTC_CalibrationSetUpMenu_EditItem());
			TTC_CalibrationSetUpMenu.add(get_TTC_CalibrationSetUpMenu_OpenItem());
		}
		return TTC_CalibrationSetUpMenu;
	}

	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem get_TTC_CalibrationSetUpMenu_RunItem() {
		if (TTC_CalibrationSetUpMenu_RunItem == null) {
			TTC_CalibrationSetUpMenu_RunItem = new JMenuItem();
			TTC_CalibrationSetUpMenu_RunItem.setName("Run");
			TTC_CalibrationSetUpMenu_RunItem.setText("Run");
			TTC_CalibrationSetUpMenu_RunItem.addActionListener(this);
		}
		return TTC_CalibrationSetUpMenu_RunItem;
	}
	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem get_TTC_CalibrationSetUpMenu_AbortItem() {
		if (TTC_CalibrationSetUpMenu_AbortItem == null) {
			TTC_CalibrationSetUpMenu_AbortItem = new JMenuItem();
			TTC_CalibrationSetUpMenu_AbortItem.setName("Abort");
			TTC_CalibrationSetUpMenu_AbortItem.setText("Abort");
			TTC_CalibrationSetUpMenu_AbortItem.addActionListener(this);
		}
		return TTC_CalibrationSetUpMenu_AbortItem;
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
	private JMenuItem get_TTC_CalibrationSetUpMenu_NewItem() {
		if (TTC_CalibrationSetUpMenu_NewItem == null) {
			TTC_CalibrationSetUpMenu_NewItem = new JMenuItem();
			TTC_CalibrationSetUpMenu_NewItem.setText("New");
			TTC_CalibrationSetUpMenu_NewItem.setName("New");
			TTC_CalibrationSetUpMenu_NewItem.addActionListener(this);
		}
		return TTC_CalibrationSetUpMenu_NewItem;
	}

	/**
	 * This method initializes jMenuItem112
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem get_TTC_CalibrationSetUpMenu_EditItem() {
		if (TTC_CalibrationSetUpMenu_EditItem == null) {
			TTC_CalibrationSetUpMenu_EditItem = new JMenuItem();
			TTC_CalibrationSetUpMenu_EditItem.setText("Edit");
			TTC_CalibrationSetUpMenu_EditItem.setName("Edit");
			TTC_CalibrationSetUpMenu_EditItem.addActionListener(this);
		}
		return TTC_CalibrationSetUpMenu_EditItem;
	}

	/**
	 * This method initializes jMenuItem113
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem get_TTC_CalibrationSetUpMenu_OpenItem() {
		if (TTC_CalibrationSetUpMenu_OpenItem == null) {
			TTC_CalibrationSetUpMenu_OpenItem = new JMenuItem();
			TTC_CalibrationSetUpMenu_OpenItem.setText("Open");
			TTC_CalibrationSetUpMenu_OpenItem.setName("Open");
			TTC_CalibrationSetUpMenu_OpenItem.addActionListener(this);
		}
		return TTC_CalibrationSetUpMenu_OpenItem;
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
	public void actionPerformed (ActionEvent e){
		try {
			String command = e.getActionCommand ();
			System.out.println(command);
			if (command.equals ("Exit")) { //Exit from the application
				mainController.doAction(new ActionRequest("ExitApplicationAction",this));
			}
			if (command.equals ("Open")) { //Open a existing TTC Calibration SetUp
				mainController.doAction(new ActionRequest("ViewExistingTTCalibrationSetUpAction",this));
			}
			if (command.equals ("New")) { //Create a new TTC Calibration SetUp
				mainController.doAction(new ActionRequest("CreateNewTTCalibrationSetUpAction",this));
			}
			if (command.equals ("Edit")) { //Edit a existing TTC Calibration SetUp
				mainController.doAction(new ActionRequest("EditExistingTTCalibrationSetUpAction",this));
			}
			if (command.equals ("About")) { //get the application About
				mainController.doAction(new ActionRequest("ViewAboutContentsAction",this));
			}
			if (command.equals ("Contents")) { //get the application help contents
				mainController.doAction(new ActionRequest("ViewHelpContentsAction",this));
			}
			if (command.equals ("Run")) { // Run a existing TTC Calibration SetUp
				//configureMainScreenMenuBarMode(RUN_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("RunTTCalibrationSetUpAction",this));
			}
			if (command.equals ("Stop")) { // Stop a running TTC Calibration SetUp
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("AbortTTCalibrationSetUpAction",this));
			}
			if (command.equals ("InstrumentsComunicationsAndTemperatureSensorConfig")) { // Configure Instruments communications and sensor data
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("InstrumentsComunicationsAndTemperatureSensorConfig_Action",this));
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} // actionPerformed
	/**
	 * This method initializes instrumentsComunicationsjMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getInstrumentsComunicationsAndTemperatureSensorjMenuItem() {
		if (instrumentsComunicationsAndTemperatureSensorjMenuItem == null) {
			instrumentsComunicationsAndTemperatureSensorjMenuItem = new JMenuItem();
			instrumentsComunicationsAndTemperatureSensorjMenuItem.setText("Instruments Communications --- Temperature Sensor ");
			instrumentsComunicationsAndTemperatureSensorjMenuItem.setActionCommand("InstrumentsComunicationsAndTemperatureSensorConfig");
			instrumentsComunicationsAndTemperatureSensorjMenuItem.addActionListener(this);
		}
		return instrumentsComunicationsAndTemperatureSensorjMenuItem;
	}
	/**
	 * This method initializes configurationjMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getConfigurationjMenu() {
		if (configurationjMenu == null) {
			configurationjMenu = new JMenu();
			configurationjMenu.setText("Configuration");
			configurationjMenu.add(getInstrumentsComunicationsAndTemperatureSensorjMenuItem());
		}
		return configurationjMenu;
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
