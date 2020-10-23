package view_ForDiodesCalibration;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTabbedPane;

import java.awt.Choice;
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Point;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.List;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import Main.MainController;
import Main.TTC_Calibration_MainController;

import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import data.DiodesCalibrationSetUpData;
import data.InstrumentData;
import data.InstrumentsData;
import data.TTCSetUpData;
import data.TTCSetUpDescriptionData;
import data.TTCToCalibrateData;
import data.TTCsToCalibrateData;
import data.TemperatureProfileData;
import data.TemperatureSensorData;
import data.temperatureStabilizationCriteriumData;
import filesManagement.FileChooser;

import view_CommonParts.Thermal_Calibration_SetUp_Description_JPanel;
import view_CommonParts.Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel;
import view_CommonParts.Thermal_Calibration_SetUp_Temperature_Profile_JPanel;

import java.awt.SystemColor;
import java.awt.Font;
import java.util.Enumeration;

public class ThermalCalibrationViewerFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int CREATOR_MODE = 0; 		//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
	private static final int VIEWER_MODE = 1;		//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
	private static final int EDIT_MODE = 2;			//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None

	private String 					ttcSetUpFilePath = "";  //  @jve:decl-index=0:

	private JFileChooser 			fileChooser = null;
	private DiodesCalibrationSetUpData 			program = null;
	private MainController 			mainController = null;
	private InstrumentsData 		instrumentsData = null;  //  @jve:decl-index=0:
	private TemperatureSensorData 	temperatureSensorData = null;

	private JPanel 		jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel 		saveCancelPanel = null;
	private JButton 	saveButton = null;
	private JButton 	saveAsButton = null;

	private Thermal_Calibration_SetUp_Temperature_Profile_JPanel 		ttc_SetUp_Temperature_Profile_JPanel;
	private Diodes_To_Calibrate_JPanel 									diodes_To_Calibrate_JPanel;
	private Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel 	ttc_SetUp_Stabilization_Criterium_JPanel;
	private Thermal_Calibration_SetUp_Description_JPanel 				ttc_SetUp_Description_JPanel;

	/**
	 * This is the default constructor
	 */
	public ThermalCalibrationViewerFrame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _ttcSetUpFilePath,DiodesCalibrationSetUpData _program,int _mode, MainController _mainController) throws Exception{
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
			DiodesCalibrationSetUpData program,
			int _mode) throws Exception
			{
				initializeInstrumentsAndSensorData(_instrumentsDataFilePath,_temperatureSensorDataFilePath);
				initializeTTCSetUpFilePath(_ttcSetUpFilePath);
				this.setSize(759, 529);
				this.setName("ThermalCalibrationProgramViewerFrame");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setContentPane(getJContentPane());
				this.setResizable(false);
				this.program = program;
				createPanelsAndAddToJTabbedPane(_mode);
				//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
			  	//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
				//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
				configureFrameMode(_mode);
	}
	private void createPanelsAndAddToJTabbedPane(int _mode) throws Exception {
		if (_mode==CREATOR_MODE)	ttc_SetUp_Description_JPanel = new Thermal_Calibration_SetUp_Description_JPanel("SetUP Description","","","");
		else ttc_SetUp_Description_JPanel = new Thermal_Calibration_SetUp_Description_JPanel("SetUP Description",program.getDiodesCalibrationSetUpDescriptionData().getProgramName(),program.getDiodesCalibrationSetUpDescriptionData().getProgramAuthor(),program.getDiodesCalibrationSetUpDescriptionData().getProgramDescription());

		ttc_SetUp_Temperature_Profile_JPanel = new Thermal_Calibration_SetUp_Temperature_Profile_JPanel("Temperature Profile");
		diodes_To_Calibrate_JPanel = new Diodes_To_Calibrate_JPanel("Diodes To Calibrate",temperatureSensorData);
		ttc_SetUp_Stabilization_Criterium_JPanel = new Thermal_Calibration_SetUp_Stabilization_Criterium_JPanel("Stabilization Criteria Panel");
		jTabbedPane.addTab(ttc_SetUp_Temperature_Profile_JPanel.getName(),null,ttc_SetUp_Temperature_Profile_JPanel,null);
		jTabbedPane.addTab(diodes_To_Calibrate_JPanel.getName(), null,diodes_To_Calibrate_JPanel, null);
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
	private void configureFrameMode(int _mode){
		 //0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
		 //1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		 //2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		 switch (_mode){
		    case CREATOR_MODE:
		    	this.setTitle("New TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    break;
		    case VIEWER_MODE:
		    	this.setTitle("View TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadThermalCalibrationData(program);
			    break;
		    case EDIT_MODE:
		    	this.setTitle("Edit TTCalibrationSetUp");
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadThermalCalibrationData(program);
			    break;
		    default:
		    }
	 }
	public void loadThermalCalibrationData(data.DiodesCalibrationSetUpData _thermalCalibrationData){
		ttc_SetUp_Temperature_Profile_JPanel.loadTemperatureProfileData(_thermalCalibrationData.getTemperatureProfileData());
		diodes_To_Calibrate_JPanel.loadDiodesToCalibrateData(_thermalCalibrationData.getDiodesToCalibrateData());
		ttc_SetUp_Stabilization_Criterium_JPanel.loadTemperatureStabilizationCriteriumData(_thermalCalibrationData.getTemperatureStabilizationCriteriumData());
		this.ttc_SetUp_Description_JPanel.loadDescriptionData(_thermalCalibrationData.getDiodesCalibrationSetUpDescriptionData());
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
		if (diodes_To_Calibrate_JPanel.validateDiodesToCalibrateData()==false) return false;
		if (ttc_SetUp_Stabilization_Criterium_JPanel.validateTemperatureStabilizationCriteriumData()==false) return false;
		if (ttc_SetUp_Description_JPanel.validateDescriptionData()==false) return false;
		return true;
	  }
	private void saveToXMLFile(String _programFilePath) throws Exception{
		DiodesCalibrationSetUpData diodesCalibrationSetUp_Data = new DiodesCalibrationSetUpData
		  (
				  ttc_SetUp_Temperature_Profile_JPanel.getTemperatureProfileData(),
				  diodes_To_Calibrate_JPanel.getDiodesToCalibrateData(),
				  ttc_SetUp_Stabilization_Criterium_JPanel.getTemperatureStabilizationCriteriumData(),
				  ttc_SetUp_Description_JPanel.getTTCSetUpDescriptionData()
		  );
		  program = diodesCalibrationSetUp_Data;
		  diodesCalibrationSetUp_Data.toXMLFile("ThermalCalibrationProgramData",_programFilePath);
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
				    	DiodesCalibrationSetUpData _program = null;
						_program = new DiodesCalibrationSetUpData(programFilePath);
						System.out.println(_program.toString());
				    	String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
				    	String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
						ThermalCalibrationViewerFrame thisClass = new ThermalCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,programFilePath,_program,1,null);
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