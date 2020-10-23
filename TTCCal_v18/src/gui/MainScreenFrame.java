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

	private JMenuBar mainScreenFrameMenuBar = null;

	private JMenu fileMenu = null;
	private JMenuItem fileMenuItem1 = null;
	private JMenu programMenu = null;
	private JMenuItem programMenu_RunItem = null;
	private JMenuItem programMenu_StopItem = null;
	private JMenuItem programMenu_NewItem = null;
	private JMenuItem programMenu_EditItem = null;
	private JMenuItem programMenu_OpenItem = null;
	private JMenu instrumentsMenu = null;
	private JMenuItem ovenMenuItem1 = null;
	private JMenu helpMenu = null;
	private JMenuItem helpMenuItem1 = null;
	private JMenuItem helpMenuItem2 = null;

	private JPanel jContentPane = null;
	private JTable resultsTable = null;
	private JMenu ovenjMenu = null;
	private JMenu multimeterjMenu = null;
	private JMenuItem instrumentsComunicationsjMenuItem = null;
	private JMenu configurationjMenu = null;
	private JMenuItem temperatureSensorConfigurationjMenuItem = null;
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
			programMenu.setEnabled(true);
			programMenu_RunItem.setEnabled(true);
			programMenu_StopItem.setEnabled(false);
			programMenu_NewItem.setEnabled(true);
			programMenu_EditItem.setEnabled(true);
			programMenu_OpenItem.setEnabled(true);
			instrumentsMenu.setEnabled(true);
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
			instrumentsMenu.setEnabled(false);
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
			mainScreenFrameMenuBar.add(getInstrumentsMenu());
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
	private JMenu getInstrumentsMenu() {
		if (instrumentsMenu == null) {
			instrumentsMenu = new JMenu();
			instrumentsMenu.setText("Instruments");
			instrumentsMenu.setName("Oven");
			instrumentsMenu.add(getOvenjMenu());
			instrumentsMenu.add(getMultimeterjMenu());
		}
		return instrumentsMenu;
	}

	/**
	 * This method initializes jMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSetTemperatureMenuItem() {
		if (ovenMenuItem1 == null) {
			ovenMenuItem1 = new JMenuItem();
			ovenMenuItem1.setText("Manual Operation");
			ovenMenuItem1.setActionCommand("StartOvenInManualOperation");
			ovenMenuItem1.setName("ManualOperation");
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
				//configureMainScreenMenuBarMode(RUN_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("StartCalibrationProgramAction",this));
			}
			if (command.equals ("Stop")) { // Run a existing program
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("StopCalibrationProgramAction",this));
			}
			if (command.equals ("StartOvenInManualOperation")) { // Run a existing program
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("StartOvenInManualOperation_Action",this));
			}
			if (command.equals ("InstrumentsComunications")) { // Run a existing program
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("Configuration_Action",this));
			}
			if (command.equals ("TemperatureSensor")) { // Run a existing program
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("Configuration_Action",this));
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} // actionPerformed

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
	 * This method initializes ovenjMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getOvenjMenu() {
		if (ovenjMenu == null) {
			ovenjMenu = new JMenu();
			ovenjMenu.setText("Oven");
			ovenjMenu.add(getSetTemperatureMenuItem());
		}
		return ovenjMenu;
	}
	/**
	 * This method initializes multimeterjMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMultimeterjMenu() {
		if (multimeterjMenu == null) {
			multimeterjMenu = new JMenu();
			multimeterjMenu.setText("Multimeter");
		}
		return multimeterjMenu;
	}
	/**
	 * This method initializes instrumentsComunicationsjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getInstrumentsComunicationsjMenuItem() {
		if (instrumentsComunicationsjMenuItem == null) {
			instrumentsComunicationsjMenuItem = new JMenuItem();
			instrumentsComunicationsjMenuItem.setText("Instruments Communications");
			instrumentsComunicationsjMenuItem.setActionCommand("InstrumentsComunications");
			instrumentsComunicationsjMenuItem.addActionListener(this);
		}
		return instrumentsComunicationsjMenuItem;
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
			configurationjMenu.add(getInstrumentsComunicationsjMenuItem());
			configurationjMenu.add(getTemperatureSensorConfigurationjMenuItem());
		}
		return configurationjMenu;
	}
	/**
	 * This method initializes temperatureSensorConfigurationjMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getTemperatureSensorConfigurationjMenuItem() {
		if (temperatureSensorConfigurationjMenuItem == null) {
			temperatureSensorConfigurationjMenuItem = new JMenuItem();
			temperatureSensorConfigurationjMenuItem.setActionCommand("TemperatureSensor");
			temperatureSensorConfigurationjMenuItem.setText("Temperature Sensor");
			temperatureSensorConfigurationjMenuItem.addActionListener(this);
		}
		return temperatureSensorConfigurationjMenuItem;
	}

}  //  @jve:decl-index=0:visual-constraint="29,1"
