package views;

import instruments.InstrumentsData;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.Rectangle;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import calibrationSetUp.CalibrationSetUp;
import sensors.TemperatureSensor;
import Main.MainController;

public class CalibrationSetUp_Viewer_JFrame extends JFrame implements ActionListener{

	private static final long 		serialVersionUID = 1L;
	private static final int 		CREATOR_MODE = 0; 		//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
	private static final int 		VIEWER_MODE = 1;		//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
	private static final int 		EDIT_MODE = 2;			//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None

	private static final String 	saveCalibrationSetUpAs_Action = "Save CalibrationSetUp As";
	private static final String 	saveCalibrationSetUp_Action = "Save CalibrationSetUp";

	private String 					calibrationSetUpFilePath = "";  //  @jve:decl-index=0:

	private JFileChooser 			fileChooser = null;
	private CalibrationSetUp 		calibrationSetUp = null;
	private MainController 			mainController = null;
	private InstrumentsData 		instrumentsData = null;  //  @jve:decl-index=0:
	private TemperatureSensor 		temperatureSensorData = null;

	private JPanel 					jContentPane = null;
	private JTabbedPane 			jTabbedPane = null;
	private JPanel 					saveCancelPanel = null;
	private JButton 				saveButton = null;
	private JButton 				saveAsButton = null;

	private CalibrationSetUp_TemperatureProfile_JPanel 					calibrationSetUp_TemperatureProfile_JPanel;
	private CalibrationSetUp_DevicesToCalibrate_JPanel 					calibrationSetUp_DevicesToCalibrate_JPanel;
	private CalibrationSetUp_TemperatureStabilizationCriteria_JPanel 	calibrationSetUp_TemperatureStabilizationCriteria_JPanel;
	private CalibrationSetUp_Description_JPanel 						calibrationSetUp_Description_JPanel;

	/**
	 * This is the default constructor
	 */
	public CalibrationSetUp_Viewer_JFrame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _calibrationSetUpFilePath,CalibrationSetUp _calibrationSetUp,int _mode, MainController _mainController) throws Exception{
		super("ThermalCalibrationProgramViewerFrame");
		mainController = _mainController;
		addWindowListener(mainController);
		fileChooser = new JFileChooser();
		initialize(_instrumentsDataFilePath,_temperatureSensorDataFilePath,_calibrationSetUpFilePath,_calibrationSetUp,_mode);
	}

	/**
	 * This method initializes jTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() throws Exception{
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(0, 0, 736, 466));
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes saveCancelPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getSaveCancelPanel() {
		if (saveCancelPanel == null) {
			saveCancelPanel = new JPanel();
			saveCancelPanel.setLayout(null);
			saveCancelPanel.setBounds(new Rectangle(0, 465, 791, 31));
			saveCancelPanel.add(getSaveButton(), null);
			saveCancelPanel.add(getSaveAsButton(), null);
		}
		return saveCancelPanel;
	}

	/**
	 * This method initializes saveButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setBounds(new Rectangle(510, 0, 226, 31));
			saveButton.setText(saveCalibrationSetUp_Action);
			saveButton.addActionListener(this);
		}
		return saveButton;
	}

	/**
	 * This method initializes saveAsButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getSaveAsButton() {
		if (saveAsButton == null) {
			saveAsButton = new JButton();
			saveAsButton.setBounds(new Rectangle(285, 0, 226, 31));
			saveAsButton.setText(saveCalibrationSetUpAs_Action);
			saveAsButton.addActionListener(this);
		}
		return saveAsButton;
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(
			String _instrumentsDataFilePath,
			String _temperatureSensorDataFilePath,
			String _calibrationSetUpFilePath,
			CalibrationSetUp _calibrationSetUp,
			int _mode) throws Exception
			{
				initializeInstrumentsAndSensorData(_instrumentsDataFilePath,_temperatureSensorDataFilePath);
				initializeCalibrationSetUpFilePath(_calibrationSetUpFilePath);
				this.setSize(759, 529);
				this.setName("ThermalCalibrationProgramViewerFrame");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setContentPane(getJContentPane());
				this.setResizable(false);
				this.calibrationSetUp = _calibrationSetUp;
				createPanelsAndAddToJTabbedPane(_mode);
				//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
			  	//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
				//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
				configureFrameMode(_mode);
	}
	private void createPanelsAndAddToJTabbedPane(int _mode) throws Exception {
		if (_mode==CREATOR_MODE)	calibrationSetUp_Description_JPanel = new CalibrationSetUp_Description_JPanel("SetUP Description","","","");
		else calibrationSetUp_Description_JPanel = new CalibrationSetUp_Description_JPanel("SetUP Description",calibrationSetUp.getDescription().getCalibrationSetUp_Name(),calibrationSetUp.getDescription().getCalibrationSetUp_Author(),calibrationSetUp.getDescription().getCalibrationSetUp_Description());

		calibrationSetUp_TemperatureProfile_JPanel = new CalibrationSetUp_TemperatureProfile_JPanel("Temperature Profile");
		calibrationSetUp_DevicesToCalibrate_JPanel = new CalibrationSetUp_DevicesToCalibrate_JPanel("Diodes To Calibrate",temperatureSensorData);
		//JScrollPane scroller = new JScrollPane(calibrationSetUp_DevicesToCalibrate_JPanel);  		
		calibrationSetUp_TemperatureStabilizationCriteria_JPanel = new CalibrationSetUp_TemperatureStabilizationCriteria_JPanel("Stabilization Criteria Panel");
		jTabbedPane.addTab(calibrationSetUp_TemperatureProfile_JPanel.getName(),null,calibrationSetUp_TemperatureProfile_JPanel,null);
		jTabbedPane.addTab(calibrationSetUp_DevicesToCalibrate_JPanel.getName(), null,calibrationSetUp_DevicesToCalibrate_JPanel, null);
		jTabbedPane.addTab(calibrationSetUp_TemperatureStabilizationCriteria_JPanel.getName(), null,calibrationSetUp_TemperatureStabilizationCriteria_JPanel, null);
		jTabbedPane.addTab(calibrationSetUp_Description_JPanel.getName(), null,calibrationSetUp_Description_JPanel, null);
	}
	/**
	 *
	 * @param _calibrationSetUpFilePath
	 */
	private void initializeCalibrationSetUpFilePath(String _calibrationSetUpFilePath) {
		calibrationSetUpFilePath = _calibrationSetUpFilePath;
	}

	/**
	 *
	 * @param _instrumentsDataFilePath
	 * @param _temperatureSensorDataFilePath
	 * @throws Exception
	 */
	private void initializeInstrumentsAndSensorData(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath) throws Exception{
		instrumentsData = new InstrumentsData(_instrumentsDataFilePath);
		temperatureSensorData = new TemperatureSensor(_temperatureSensorDataFilePath);
	}
	/**
	 *
	 * @return
	 * @throws Exception
	 */
	private JPanel getJContentPane() throws Exception{
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTabbedPane(), null);
			jContentPane.add(getSaveCancelPanel(), null);
		}
		return jContentPane;
	}
	private void configureFrameMode(int _mode){
		 //0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
		 //1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		 //2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		 switch (_mode){
		    case CREATOR_MODE:
		    	switch (calibrationSetUp.getType()){
		    	case CalibrationSetUp.TEMPERATURE_VS_RESISTANCE_TYPE:
		    		this.setTitle("New CalibrationSetUp For Resistances");
		    		break;
		    	case CalibrationSetUp.TEMPERATURE_VS_VOLTAGE_TYPE:
		    		this.setTitle("New CalibrationSetUp For Diodes");
		    		break;
		    		default:
		    	}
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    break;
		    case VIEWER_MODE:
		    	switch (calibrationSetUp.getType()){
		    	case CalibrationSetUp.TEMPERATURE_VS_RESISTANCE_TYPE:
		    		this.setTitle("CalibrationSetUp For Resistances");
		    		break;
		    	case CalibrationSetUp.TEMPERATURE_VS_VOLTAGE_TYPE:
		    		this.setTitle("CalibrationSetUp For Diodes");
		    		break;
		    		default:
		    	}
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadThermalCalibrationData(calibrationSetUp);
			    break;
		    case EDIT_MODE:
		    	switch (calibrationSetUp.getType()){
		    	case CalibrationSetUp.TEMPERATURE_VS_RESISTANCE_TYPE:
		    		this.setTitle("CalibrationSetUp For Resistances");
		    		break;
		    	case CalibrationSetUp.TEMPERATURE_VS_VOLTAGE_TYPE:
		    		this.setTitle("CalibrationSetUp For Diodes");
		    		break;
		    		default:
		    	}
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadThermalCalibrationData(calibrationSetUp);
			    break;
		    default:
		    }
	 }
	public void loadThermalCalibrationData(CalibrationSetUp _calibrationSetUp){
		calibrationSetUp_TemperatureProfile_JPanel.loadTemperatureProfileData(_calibrationSetUp.getTemperatureProfile());
		calibrationSetUp_DevicesToCalibrate_JPanel.loadDevicesToCalibrateData(_calibrationSetUp.getDevicesToCalibrate());
		calibrationSetUp_TemperatureStabilizationCriteria_JPanel.loadTemperatureStabilizationCriteriumData(_calibrationSetUp.getTemperatureStabilizationCriteria());
		calibrationSetUp_Description_JPanel.loadDescriptionData(_calibrationSetUp.getDescription());
	}
	public void actionPerformed (ActionEvent event) {
		  String cmd = event.getActionCommand ();
		  System.out.println(cmd);
		  if (cmd.equals(saveCalibrationSetUpAs_Action)) {
			  if (!validateProgramData()){return;}
			  int retVal = fileChooser.showSaveDialog(null);
			  if(retVal == JFileChooser.APPROVE_OPTION) {
				  try {
					saveToXMLFile(fileChooser.getSelectedFile().getAbsolutePath()+".xml",calibrationSetUp.getType());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
		  if (cmd.equals(saveCalibrationSetUp_Action)) {
			  if (!validateProgramData()){return;}
			  //grabar con el nombre actual del fichero
			  try {
				  saveToXMLFile(calibrationSetUpFilePath,calibrationSetUp.getType());
				  System.out.println(calibrationSetUpFilePath);
			  } catch (Exception e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  if (cmd.equals("Cancel")) {}
	  }
	private boolean validateProgramData(){
		if (calibrationSetUp_TemperatureProfile_JPanel.validateTemperatureProfileData()==false) return false;
		if (calibrationSetUp_DevicesToCalibrate_JPanel.validateDevicesToCalibrateData()==false) return false;
		if (calibrationSetUp_TemperatureStabilizationCriteria_JPanel.validateTemperatureStabilizationCriteriumData()==false) return false;
		if (calibrationSetUp_Description_JPanel.validateDescriptionData()==false) return false;
		return true;
	  }
	private void saveToXMLFile(String _programFilePath, int _calibrationSetUpType) throws Exception{
		CalibrationSetUp _calibrationSetUp = new CalibrationSetUp
		(
				_calibrationSetUpType,
				calibrationSetUp_TemperatureProfile_JPanel.getTemperatureProfileData(),
				calibrationSetUp_DevicesToCalibrate_JPanel.getDevicesToCalibrateData(_calibrationSetUpType),
				calibrationSetUp_TemperatureStabilizationCriteria_JPanel.getTemperatureStabilizationCriteria(),
				calibrationSetUp_Description_JPanel.getTTCSetUpDescriptionData()
		);
		calibrationSetUp = _calibrationSetUp;
		calibrationSetUp.toXMLFile("ThermalCalibrationProgramData",_programFilePath);
	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					try {
						JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ver.");
						String programFilePath = "p1.xml";
					    //This is where a real application would open the file.
				        //log.append("Opening: " + file.getName() + "." + newline);
				        System.out.println("Creando la instancia para el programa.");
				    	CalibrationSetUp _program = null;
						_program = new CalibrationSetUp(programFilePath);
						System.out.println(_program.toString());
				    	String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
				    	String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
						CalibrationSetUp_Viewer_JFrame thisClass = new CalibrationSetUp_Viewer_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,programFilePath,_program,1,null);
						thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						thisClass.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"