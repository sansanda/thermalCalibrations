package views;

import javax.swing.JPanel;
import javax.swing.JFrame;

import Main.Calibration_MainController;
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

import controller.ActionFactory;
import controller.ActionRequest;

public class CalibrationSetUp_MainScreen_JFrame extends JFrame implements ActionListener{

	private static int RUN_PROGRAM_MODE = 1;
	private static int STOP_PROGRAM_MODE = 0;

	private static final long 			serialVersionUID = 1L;
	private Calibration_MainController 	mainController = null;

	private JMenuBar 	mainScreenFrameMenuBar = null;

	private JMenu 		fileMenu = null;
	private JMenuItem 	fileMenuItem1 = null;
	private JMenu 		calibrationSetUp_Menu = null;
	private JMenuItem 	calibrationSetUpMenu_RunItem = null;
	private JMenuItem 	calibrationSetUpMenu_AbortItem = null;
	private JMenuItem 	calibrationSetUpMenu_NewForDiodesItem = null;
	private JMenuItem 	calibrationSetUpMenu_NewForResistancesItem = null;
	private JMenuItem 	calibrationSetUpMenu_EditItem = null;
	private JMenuItem 	calibrationSetUpMenu_OpenItem = null;

	private JMenu 		helpMenu = null;
	private JMenuItem 	helpMenu_HelpItem = null;
	private JMenuItem 	helpMenu_AboutItem = null;

	private JPanel 		jContentPane = null;
	private JTable 		resultsTable = null;
	private JMenuItem 	instrumentsConfiguration_Item = null;
	private JMenuItem 	temperatureSensorConfiguration_Item = null;
	private JMenu 		configurationjMenu = null;
	/**
	 * This is the default constructor
	 */
	public CalibrationSetUp_MainScreen_JFrame(MainController _mainController) {
		super();
		mainController = (Calibration_MainController)_mainController;
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
			calibrationSetUp_Menu.setEnabled(true);
			calibrationSetUpMenu_RunItem.setEnabled(true);
			calibrationSetUpMenu_AbortItem.setEnabled(false);
			calibrationSetUpMenu_NewForDiodesItem.setEnabled(true);
			calibrationSetUpMenu_EditItem.setEnabled(true);
			calibrationSetUpMenu_OpenItem.setEnabled(true);
			fileMenu.setEnabled(true);
			return;
		}
		if (_mode == RUN_PROGRAM_MODE){
			printActionMessage("MainScreen. Configuring Menu Bar in Run Mode");
			helpMenu.setEnabled(true);
			calibrationSetUp_Menu.setEnabled(true);
			calibrationSetUpMenu_RunItem.setEnabled(false);
			calibrationSetUpMenu_AbortItem.setEnabled(true);
			calibrationSetUpMenu_NewForDiodesItem.setEnabled(false);
			calibrationSetUpMenu_EditItem.setEnabled(false);
			calibrationSetUpMenu_OpenItem.setEnabled(false);
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
			mainScreenFrameMenuBar.add(getFile_Menu());
			mainScreenFrameMenuBar.add(getCalibrationSetUp_Menu());
			mainScreenFrameMenuBar.add(getInstrumentsAndTemperatureSensorConfiguration_Menu());
			mainScreenFrameMenuBar.add(getHelp_Menu());
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
			helpMenu.setText("?");
			helpMenu.setName("?");
			helpMenu.add(getHelpMenuItem());
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
	private JMenu getCalibrationSetUp_Menu() {
		if (calibrationSetUp_Menu == null) {
			calibrationSetUp_Menu = new JMenu();
			calibrationSetUp_Menu.setText("CalibrationSetUp");
			calibrationSetUp_Menu.setName("CalibrationSetUp");
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_RunItem());
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_AbortItem());
			calibrationSetUp_Menu.add(new JSeparator());
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_NewDiodesCalibrationItem());
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_NewResistancesCalibrationItem());
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_EditItem());
			calibrationSetUp_Menu.add(getCalibrationSetUpMenu_OpenItem());
		}
		return calibrationSetUp_Menu;
	}

	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_RunItem() {
		if (calibrationSetUpMenu_RunItem == null) {
			calibrationSetUpMenu_RunItem = new JMenuItem();
			calibrationSetUpMenu_RunItem.setActionCommand(ActionFactory.RunCalibrationSetUp_Action);
			calibrationSetUpMenu_RunItem.setText("Run");
			calibrationSetUpMenu_RunItem.addActionListener(this);
		}
		return calibrationSetUpMenu_RunItem;
	}
	/**
	 * This method initializes Run
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_AbortItem() {
		if (calibrationSetUpMenu_AbortItem == null) {
			calibrationSetUpMenu_AbortItem = new JMenuItem();
			calibrationSetUpMenu_AbortItem.setActionCommand(ActionFactory.AbortCalibrationSetUp_Action);
			calibrationSetUpMenu_AbortItem.setText("Abort");
			calibrationSetUpMenu_AbortItem.addActionListener(this);
		}
		return calibrationSetUpMenu_AbortItem;
	}
	/**
	 * This method initializes HelpMenuItem1
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getHelpMenuItem() {
		if (helpMenu_HelpItem == null) {
			helpMenu_HelpItem = new JMenuItem();
			helpMenu_HelpItem.setActionCommand(ActionFactory.ShowHelpContents_Action);
			helpMenu_HelpItem.setText("Help");
			helpMenu_HelpItem.addActionListener(this);
		}
		return helpMenu_HelpItem;
	}


	/**
	 * This method initializes jMenuItem2
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (helpMenu_AboutItem == null) {
			helpMenu_AboutItem = new JMenuItem();
			helpMenu_AboutItem.setActionCommand(ActionFactory.ShowAboutContents_Action);
			helpMenu_AboutItem.setText("About");
			helpMenu_AboutItem.addActionListener(this);
		}
		return helpMenu_AboutItem;
	}

	/**
	 * This method initializes jMenuItem111
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_NewDiodesCalibrationItem() {
		if (calibrationSetUpMenu_NewForDiodesItem == null) {
			calibrationSetUpMenu_NewForDiodesItem = new JMenuItem();
			calibrationSetUpMenu_NewForDiodesItem.setActionCommand(ActionFactory.NewDiodeCalibrationSetUp_Action);
			calibrationSetUpMenu_NewForDiodesItem.setText("New For Diodes");
			calibrationSetUpMenu_NewForDiodesItem.addActionListener(this);
		}
		return calibrationSetUpMenu_NewForDiodesItem;
	}

	/**
	 * This method initializes jMenuItem111
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_NewResistancesCalibrationItem() {
		if (calibrationSetUpMenu_NewForResistancesItem == null) {
			calibrationSetUpMenu_NewForResistancesItem = new JMenuItem();
			calibrationSetUpMenu_NewForResistancesItem.setActionCommand(ActionFactory.NewTTCCalibrationSetUp_Action);
			calibrationSetUpMenu_NewForResistancesItem.setText("New For Resistances");
			calibrationSetUpMenu_NewForResistancesItem.addActionListener(this);
		}
		return calibrationSetUpMenu_NewForResistancesItem;
	}
	/**
	 * This method initializes jMenuItem112
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_EditItem() {
		if (calibrationSetUpMenu_EditItem == null) {
			calibrationSetUpMenu_EditItem = new JMenuItem();
			calibrationSetUpMenu_EditItem.setActionCommand(ActionFactory.EditCalibrationSetUp_Action);
			calibrationSetUpMenu_EditItem.setText("Edit");
			calibrationSetUpMenu_EditItem.addActionListener(this);
		}
		return calibrationSetUpMenu_EditItem;
	}

	/**
	 * This method initializes jMenuItem113
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCalibrationSetUpMenu_OpenItem() {
		if (calibrationSetUpMenu_OpenItem == null) {
			calibrationSetUpMenu_OpenItem = new JMenuItem();
			calibrationSetUpMenu_OpenItem.setActionCommand(ActionFactory.ViewCalibrationSetUp_Action);
			calibrationSetUpMenu_OpenItem.setText("Open");
			calibrationSetUpMenu_OpenItem.addActionListener(this);
		}
		return calibrationSetUpMenu_OpenItem;
	}

	/**
	 * This method initializes jMenuItem114
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (fileMenuItem1 == null) {
			fileMenuItem1 = new JMenuItem();
			fileMenuItem1.setActionCommand(ActionFactory.ExitApplication_Action);
			fileMenuItem1.setText("Exit");
			fileMenuItem1.addActionListener(this);
		}
		return fileMenuItem1;
	}
	public void actionPerformed (ActionEvent e){
		try {
			String command = e.getActionCommand();
			//System.out.println(" command = " + command);
			mainController.doAction(new ActionRequest(command,this));
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
	private JMenuItem getConfigurationMenuMenu_InstrumentsConfigurationItem() {
		if (instrumentsConfiguration_Item == null) {
			instrumentsConfiguration_Item = new JMenuItem();
			instrumentsConfiguration_Item.setText("Instruments");
			instrumentsConfiguration_Item.setActionCommand(ActionFactory.InstrumentsConfig_Action);
			instrumentsConfiguration_Item.addActionListener(this);
		}
		return instrumentsConfiguration_Item;
	}
	/**
	 * This method initializes instrumentsComunicationsjMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getConfigurationMenuMenu_TemperatureSensorConfigurationItem() {
		if (temperatureSensorConfiguration_Item == null) {
			temperatureSensorConfiguration_Item = new JMenuItem();
			temperatureSensorConfiguration_Item.setText("Temperature Sensor ");
			temperatureSensorConfiguration_Item.setActionCommand(ActionFactory.TemperatureSensorConfig_Action);
			temperatureSensorConfiguration_Item.addActionListener(this);
		}
		return temperatureSensorConfiguration_Item;
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
			configurationjMenu.add(this.getConfigurationMenuMenu_InstrumentsConfigurationItem());
			configurationjMenu.add(this.getConfigurationMenuMenu_TemperatureSensorConfigurationItem());
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
