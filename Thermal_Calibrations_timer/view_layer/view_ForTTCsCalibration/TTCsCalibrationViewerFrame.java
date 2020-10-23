package view_ForTTCsCalibration;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import profiles.InstrumentsData;
import profiles.TTCSetUpData;
import profiles.TemperatureSensorData;

import Main.MainController;
import Main.TTC_Calibration_MainController;
import view_CommonParts.Thermal_Calibration_SetUp_Description_JPanel;
import view_CommonParts.Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel;
import view_CommonParts.Thermal_Calibration_SetUp_Temperature_Profile_JPanel;

public class TTCsCalibrationViewerFrame extends JFrame implements ActionListener{
	//Constants
	private static final long serialVersionUID = 1L;
	private static final int CREATOR_MODE = 0; 		//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
	private static final int VIEWER_MODE = 1;		//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
	private static final int EDIT_MODE = 2;			//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None

	//Variables
	private String 					ttcSetUpFilePath = "";  //  @jve:decl-index=0:

	private JFileChooser 			fileChooser = null;
	private TTCSetUpData 			program = null;
	private MainController 			mainController = null;
	private InstrumentsData 		instrumentsData = null;  //  @jve:decl-index=0:
	private TemperatureSensorData 	temperatureSensorData = null;

	private JPanel 		jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel 		saveCancelPanel = null;
	private JButton 	saveButton = null;
	private JButton 	saveAsButton = null;

	private Thermal_Calibration_SetUp_Temperature_Profile_JPanel 		ttc_SetUp_Temperature_Profile_JPanel;
	private TTCs_To_Calibrate_JPanel 									ttc_SetUp_TTCs_To_Calibrate_JPanel;
	private Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel 	ttc_SetUp_Stabilization_Criterium_JPanel;
	private Thermal_Calibration_SetUp_Description_JPanel 				ttc_SetUp_Description_JPanel;

	/**
	 * This is the default constructor
	 */
	public TTCsCalibrationViewerFrame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _ttcSetUpFilePath,TTCSetUpData _program,int _mode, MainController _mainController) throws Exception{
		super("ThermalCalibrationProgramViewerFrame");
		mainController = _mainController;
		addWindowListener(mainController);
		fileChooser = new JFileChooser();
		initialize(_instrumentsDataFilePath,_temperatureSensorDataFilePath,_ttcSetUpFilePath,_program,_mode);
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
			saveButton.setText("Save TTC Calibration SetUp");
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
			saveAsButton.setText("Save TTC Calibration SetUp As");
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
			String _ttcSetUpFilePath,
			TTCSetUpData _program,
			int _mode) throws Exception
			{
				initializeInstrumentsAndSensorData(_instrumentsDataFilePath,_temperatureSensorDataFilePath);
				initializeTTCSetUpFilePath(_ttcSetUpFilePath);
				this.setSize(759, 529);
				this.setName("ThermalCalibrationProgramViewerFrame");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setContentPane(getJContentPane());
				this.setResizable(false);
				this.program = _program;
				//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
			  	//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
				//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
				configureFrame(_mode);
	}
	private void createPanelsAndAddToJTabbedPane(int _mode) throws Exception {
		if (_mode==CREATOR_MODE)	ttc_SetUp_Description_JPanel = new Thermal_Calibration_SetUp_Description_JPanel("SetUP Description","","","");
		else ttc_SetUp_Description_JPanel = new Thermal_Calibration_SetUp_Description_JPanel("SetUP Description",program.getTTCSetUpDescriptionData().getProgramName(),program.getTTCSetUpDescriptionData().getProgramAuthor(),program.getTTCSetUpDescriptionData().getProgramDescription());

		ttc_SetUp_Temperature_Profile_JPanel = new Thermal_Calibration_SetUp_Temperature_Profile_JPanel("Temperature Profile");
		ttc_SetUp_TTCs_To_Calibrate_JPanel = new TTCs_To_Calibrate_JPanel("TTCs To Calibrate",temperatureSensorData);
		ttc_SetUp_Stabilization_Criterium_JPanel = new Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel("Stabilization Criteria Panel");
		jTabbedPane.addTab(ttc_SetUp_Temperature_Profile_JPanel.getName(),null,ttc_SetUp_Temperature_Profile_JPanel,null);
		jTabbedPane.addTab(ttc_SetUp_TTCs_To_Calibrate_JPanel.getName(), null,ttc_SetUp_TTCs_To_Calibrate_JPanel, null);
		jTabbedPane.addTab(ttc_SetUp_Stabilization_Criterium_JPanel.getName(), null,ttc_SetUp_Stabilization_Criterium_JPanel, null);
		jTabbedPane.addTab(ttc_SetUp_Description_JPanel.getName(), null,ttc_SetUp_Description_JPanel, null);
	}

	/**
	 *
	 * @param _ttcSetUpFilePath
	 */
	private void initializeTTCSetUpFilePath(String _ttcSetUpFilePath) {
		ttcSetUpFilePath = _ttcSetUpFilePath;
	}

	/**
	 *
	 * @param _instrumentsDataFilePath
	 * @param _temperatureSensorDataFilePath
	 * @throws Exception
	 */
	private void initializeInstrumentsAndSensorData(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath) throws Exception{
		instrumentsData = new InstrumentsData(_instrumentsDataFilePath);
		temperatureSensorData = new TemperatureSensorData(_temperatureSensorDataFilePath);
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
	private void configureFrame(int _mode) throws Exception{
		 //0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
		 //1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		 //2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		 switch (_mode){
		    case 0:
		    	this.setTitle("New TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
				createPanelsAndAddToJTabbedPane(_mode);
			    break;
		    case 1:
		    	this.setTitle("View TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    createPanelsAndAddToJTabbedPane(_mode);
			    loadThermalCalibrationData(program);
			    break;
		    case 2:
		    	this.setTitle("Edit TTCalibrationSetUp");
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    createPanelsAndAddToJTabbedPane(_mode);
			    loadThermalCalibrationData(program);
			    break;
		    default:
		    }
	 }
	public void loadThermalCalibrationData(TTCSetUpData _thermalCalibrationData){
		ttc_SetUp_Temperature_Profile_JPanel.loadTemperatureProfileData(_thermalCalibrationData.getTemperatureProfileData());
		ttc_SetUp_TTCs_To_Calibrate_JPanel.loadTTCsToCalibrateData(_thermalCalibrationData.getTTCsToCalibrateData());
		ttc_SetUp_Stabilization_Criterium_JPanel.loadTemperatureStabilizationCriteriumData(_thermalCalibrationData.getTemperatureStabilizationCriteriumData());
		ttc_SetUp_Description_JPanel.loadDescriptionData(_thermalCalibrationData.getTTCSetUpDescriptionData());
	}
	public void actionPerformed (ActionEvent event) {
		  String cmd = event.getActionCommand ();
		  System.out.println(cmd);

		  if (cmd.equals("Save TTC Calibration SetUp As")) {
			  if (!validateProgramData()){return;}
			  int retVal = fileChooser.showSaveDialog(null);
			  if(retVal == JFileChooser.APPROVE_OPTION) {
				  try {
					saveToXMLFile(fileChooser.getSelectedFile().getAbsolutePath()+".xml");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		  }
		  if (cmd.equals("Save TTC Calibration SetUp")) {
			  if (!validateProgramData()){return;}
			  //grabar con el nombre actual del fichero
			  try {
				  saveToXMLFile(ttcSetUpFilePath);
				  System.out.println(ttcSetUpFilePath);
			  } catch (Exception e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  if (cmd.equals("Cancel")) {}
	  }
	private boolean validateProgramData(){
		if (ttc_SetUp_Temperature_Profile_JPanel.validateTemperatureProfileData()==false) return false;
		if (ttc_SetUp_TTCs_To_Calibrate_JPanel.validateTTCsToCalibrateData()==false) return false;
		if (ttc_SetUp_Stabilization_Criterium_JPanel.validateTemperatureStabilizationCriteriumData()==false) return false;
		if (ttc_SetUp_Description_JPanel.validateDescriptionData()==false) return false;
		return true;
	  }
	private void saveToXMLFile(String _programFilePath) throws Exception{
		  TTCSetUpData thermalCalibrationProgramData = new TTCSetUpData
		  (
				  ttc_SetUp_Temperature_Profile_JPanel.getTemperatureProfileData(),
				  ttc_SetUp_TTCs_To_Calibrate_JPanel.getTTCsToCalibrateData(),
				  ttc_SetUp_Stabilization_Criterium_JPanel.getTemperatureStabilizationCriteriumData(),
				  ttc_SetUp_Description_JPanel.getTTCSetUpDescriptionData()
		  );
		  program = thermalCalibrationProgramData;
		  thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData",_programFilePath);
	  }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					try {
						//JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ver.");
						String programFilePath = "c:\\p1_ttc.xml";
					    //This is where a real application would open the file.
				        //log.append("Opening: " + file.getName() + "." + newline);
				        System.out.println("Creando la instancia para el programa.");
				    	TTCSetUpData _program = null;
						_program = new TTCSetUpData(programFilePath);
						System.out.println(_program.toString());
				    	String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
				    	String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
						//TTCsCalibrationViewerFrame thisClass = new TTCsCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,programFilePath,_program,1,null);
						TTCsCalibrationViewerFrame thisClass = new TTCsCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,"",_program,0,null);

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
