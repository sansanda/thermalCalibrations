package gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import Main.MainController;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSeparator;
import java.awt.HeadlessException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import frontController.ActionRequest;

public class MainScreen_Frame extends JFrame implements ActionListener{

	private static int RUN_PROGRAM_MODE = 1;
	private static int STOP_PROGRAM_MODE = 0;

	private static final long serialVersionUID = 1L;
	private MainController mainController = null;

	private JMenuBar mainScreenFrameMenuBar = null;

	private JMenu fileMenu = null;
	private JMenuItem fileMenuItem1 = null;
	private JMenu diodesCalibrationSetUp_Menu = null;
	private JMenuItem diodesCalibrationSetUpMenu_RunItem = null;
	private JMenuItem diodesCalibrationSetUpMenu_AbortItem = null;
	private JMenuItem diodesCalibrationSetUpMenu_NewItem = null;
	private JMenuItem diodesCalibrationSetUpMenu_EditItem = null;
	private JMenuItem diodesCalibrationSetUpMenu_OpenItem = null;
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
	public MainScreen_Frame(MainController _mainController) {
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
			diodesCalibrationSetUp_Menu.setEnabled(true);
			diodesCalibrationSetUpMenu_RunItem.setEnabled(true);
			diodesCalibrationSetUpMenu_AbortItem.setEnabled(false);
			diodesCalibrationSetUpMenu_NewItem.setEnabled(true);
			diodesCalibrationSetUpMenu_EditItem.setEnabled(true);
			diodesCalibrationSetUpMenu_OpenItem.setEnabled(true);
			fileMenu.setEnabled(true);
			return;
		}
		if (_mode == RUN_PROGRAM_MODE){
			printActionMessage("MainScreen. Configuring Menu Bar in Run Mode");
			helpMenu.setEnabled(false);
			diodesCalibrationSetUp_Menu.setEnabled(true);
			diodesCalibrationSetUpMenu_RunItem.setEnabled(false);
			diodesCalibrationSetUpMenu_AbortItem.setEnabled(true);
			diodesCalibrationSetUpMenu_NewItem.setEnabled(false);
			diodesCalibrationSetUpMenu_EditItem.setEnabled(false);
			diodesCalibrationSetUpMenu_OpenItem.setEnabled(false);
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
			mainScreenFrameMenuBar.add(getHelp_Menu());
			mainScreenFrameMenuBar.add(getDiodesCalibrationSetUp_Menu());
			mainScreenFrameMenuBar.add(getInstrumentsAndTemperatureSensorConfiguration_Menu());
			mainScreenFrameMenuBar.add(getFile_Menu());
		}
		return mainScreenFrameMenuBar;
	}

	/**
	 * This method initializes jMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelp_Menu() {
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
	private JMenu getFile_Menu() {
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
	private JMenu getDiodesCalibrationSetUp_Menu() {
		if (diodesCalibrationSetUp_Menu == null) {
			diodesCalibrationSetUp_Menu = new JMenu();
			diodesCalibrationSetUp_Menu.setText("DiodesCalibrationSetUp");
			diodesCalibrationSetUp_Menu.setName("DiodesCalibrationSetUp");
			diodesCalibrationSetUp_Menu.add(getDiodesCalibrationSetUpMenu_RunItem());
			diodesCalibrationSetUp_Menu.add(getDiodesCalibrationSetUpMenu_AbortItem());
			diodesCalibrationSetUp_Menu.add(new JSeparator());
			diodesCalibrationSetUp_Menu.add(getDiodesCalibrationSetUpMenu_NewItem());
			diodesCalibrationSetUp_Menu.add(getDiodesCalibrationSetUpMenu_EditItem());
			diodesCalibrationSetUp_Menu.add(getDiodesCalibrationSetUpMenu_OpenItem());
		}
		return diodesCalibrationSetUp_Menu;
	}

	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDiodesCalibrationSetUpMenu_RunItem() {
		if (diodesCalibrationSetUpMenu_RunItem == null) {
			diodesCalibrationSetUpMenu_RunItem = new JMenuItem();
			diodesCalibrationSetUpMenu_RunItem.setName("Run");
			diodesCalibrationSetUpMenu_RunItem.setText("Run");
			diodesCalibrationSetUpMenu_RunItem.addActionListener(this);
		}
		return diodesCalibrationSetUpMenu_RunItem;
	}
	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDiodesCalibrationSetUpMenu_AbortItem() {
		if (diodesCalibrationSetUpMenu_AbortItem == null) {
			diodesCalibrationSetUpMenu_AbortItem = new JMenuItem();
			diodesCalibrationSetUpMenu_AbortItem.setName("Abort");
			diodesCalibrationSetUpMenu_AbortItem.setText("Abort");
			diodesCalibrationSetUpMenu_AbortItem.addActionListener(this);
		}
		return diodesCalibrationSetUpMenu_AbortItem;
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
	private JMenuItem getDiodesCalibrationSetUpMenu_NewItem() {
		if (diodesCalibrationSetUpMenu_NewItem == null) {
			diodesCalibrationSetUpMenu_NewItem = new JMenuItem();
			diodesCalibrationSetUpMenu_NewItem.setText("New");
			diodesCalibrationSetUpMenu_NewItem.setName("New");
			diodesCalibrationSetUpMenu_NewItem.addActionListener(this);
		}
		return diodesCalibrationSetUpMenu_NewItem;
	}

	/**
	 * This method initializes jMenuItem112
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDiodesCalibrationSetUpMenu_EditItem() {
		if (diodesCalibrationSetUpMenu_EditItem == null) {
			diodesCalibrationSetUpMenu_EditItem = new JMenuItem();
			diodesCalibrationSetUpMenu_EditItem.setText("Edit");
			diodesCalibrationSetUpMenu_EditItem.setName("Edit");
			diodesCalibrationSetUpMenu_EditItem.addActionListener(this);
		}
		return diodesCalibrationSetUpMenu_EditItem;
	}

	/**
	 * This method initializes jMenuItem113
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getDiodesCalibrationSetUpMenu_OpenItem() {
		if (diodesCalibrationSetUpMenu_OpenItem == null) {
			diodesCalibrationSetUpMenu_OpenItem = new JMenuItem();
			diodesCalibrationSetUpMenu_OpenItem.setText("Open");
			diodesCalibrationSetUpMenu_OpenItem.setName("Open");
			diodesCalibrationSetUpMenu_OpenItem.addActionListener(this);
		}
		return diodesCalibrationSetUpMenu_OpenItem;
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
			if (command.equals ("Open")) { //Open a existing Diodes Calibration SetUp
				mainController.doAction(new ActionRequest("ViewExistingDiodesCalibrationSetUpAction",this));
			}
			if (command.equals ("New")) { //Create a new Diodes Calibration SetUp
				mainController.doAction(new ActionRequest("CreateNewDiodesCalibrationSetUpAction",this));
			}
			if (command.equals ("Edit")) { //Edit a existing Diodes Calibration SetUp
				mainController.doAction(new ActionRequest("EditExistingDiodesCalibrationSetUpAction",this));
			}
			if (command.equals ("About")) { //get the application About
				mainController.doAction(new ActionRequest("ViewAboutContentsAction",this));
			}
			if (command.equals ("Contents")) { //get the application help contents
				mainController.doAction(new ActionRequest("ViewHelpContentsAction",this));
			}
			if (command.equals ("Run")) { // Run a existing Diodes Calibration SetUp
				//configureMainScreenMenuBarMode(RUN_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("RunDiodesCalibrationSetUpAction",this));
			}
			if (command.equals ("Stop")) { // Stop a running Diodes Calibration SetUp
				//configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
				mainController.doAction(new ActionRequest("AbortDiodesCalibrationSetUpAction",this));
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
	private JMenu getInstrumentsAndTemperatureSensorConfiguration_Menu() {
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
