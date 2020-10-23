package gui;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.List;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import Data.TemperatureStabilizationCriterium_Data;
import Data.DiodesCalibrationSetUpDescription_Data;
import Data.Instruments_Data;
import Data.DiodesToCalibrate_Data;
import Data.DiodeToCalibrate_Data;
import Data.TemperatureProfile_Data;
import Data.TemperatureSensor_Data;
import Data.DiodesCalibrationSetUp_Data;
import Main.MainController;
import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.Font;

public class DiodesCalibrationViewer_Frame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private static final int 	N_DIODES_TO_CALIBRATE = 7;
	private static final int 	N_MULTIMETER_CHANNELS = 10;
	private static final int 	MAX_TEMP_OVEN = 500;
	private static final int 	N_TEMPERATURE_VALUES = 500;
	private static final int 	MIN_TEMP_OVEN = 0;
	private static final double STANDARD_DEVIATION_MAX_VALUE = 5.00;
	private static final double STANDARD_DEVIATION_MIN_VALUE = 0.01;
	private static final double STANDARD_DEVIATION_INCREMENT = 0.01;
	private static final int 	N_MEASURES_PER_WINDOW_MAX_VALUE = 240;
	private static final int 	N_MEASURES_PER_WINDOW_MIN_VALUE = 60;
	private static final int 	N_MEASURES_PER_WINDOW_INCREMENT = 1;
	private static final long 	SAMPLING_PERIODE_MIN_VALUE_MILLISECONDS = 300;
	private static final long 	SAMPLING_PERIODE_MAX_VALUE_MILLISECONDS = 10000;
	private static final int 	SAMPLING_PERIODE_MILLISECONDS_INCREMENT = 100;
	private static final int 	N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_MAX_VALUE = 10;
	private static final int 	N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_MIN_VALUE = 1;
	private static final int 	N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_INCREMENT = 1;
	private static final int 	TEMPERATURE_STABILITZATION_TIME_MINUTES_MIN_VALUE = 1;
	private static final int 	TEMPERATURE_STABILITZATION_TIME_MINUTES_INCREMENT = 1;
	private static final int 	TEMPERATURE_STABILITZATION_TIME_MINUTES_MAX_VALUE = 120;
	private static final double DIODES_CURRENT_INmA_MAX_VALUE = 10000;
	private static final double DIODES_CURRENT_INmA_MIN_VALUE = 1;
	private static final double DIODES_CURRENT_INmA_INCREMENT = 0.1;


	private String 					diodesCalibrationSetUpFilePath = "";  //  @jve:decl-index=0:

	private JFileChooser 			fileChooser = null;
	private DiodesCalibrationSetUp_Data 			program = null;
	private MainController 			mainController = null;
	private Instruments_Data 		instrumentsData = null;
	private TemperatureSensor_Data 	temperatureSensorData = null;  //  @jve:decl-index=0:

	private JPanel 		jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel 		temperatureProfilePanel = null;
	private JPanel 		diodesToCalibratePanel = null;
	private JPanel 		temperatureStabilizationCriteriumDataPanel = null;
	private JPanel 		diodesCalibrationSetUpDescriptionPanel = null;
	private JLabel 		temperatureProfileConfigurationLabel = null;
	private List 		temperatureProfileList = null;
	private JPanel 		saveCancelPanel = null;
	private JButton 	saveButton = null;
	private JButton 	saveAsButton = null;

	private JButton 	mark0To100TemperatureProfileButton = null;
	private JButton 	mark0To125TemperatureProfileButton = null;
	private JButton 	mark0To150TemperatureProfileButton = null;
	private JButton 	mark0To175TemperatureProfileButton = null;
	private JButton 	mark0To200TemperatureProfileButton = null;
	private JButton 	mark0To225TemperatureProfileButton = null;
	private JButton 	mark0To250TemperatureProfileButton = null;

	private JLabel 		minTempLabel = null;
	private JComboBox 	minTempComboBox = null;
	private JLabel 		maxTempLabel = null;
	private JTextField 	maxTempTextField = null;
	private JLabel 		nStepsLabel = null;
	private JComboBox 	nStepsComboBox = null;
	private JLabel 		stepValueLabel = null;
	private JComboBox 	stepValueComboBox = null;
	private JButton 	clearTemperatureProfileButton = null;
	private JLabel 		diodesToCalibrateConfigurationLabel = null;
	private JLabel 		diodesCalibrationSetUpConfigurationLabel = null;
	private JLabel 		programNameLabel = null;
	private JLabel 		programAuthorLabel = null;
	private JLabel 		programDescriptionLabel = null;
	private JTextField 	diodesCalibrationSetUpDescriptionTextField = null;
	private JTextField 	diodesCalibrationSetUpDescriptionAuthorTextField = null;
	private JTextArea 	diodesCalibrationSetUpDescriptionTextArea = null;
	private JLabel 		temperatureStabilizationCriteriumConfigurationLabel = null;
	private JLabel 		stDevLabel = null;
	private JLabel 		measurementWindowLabel = null;
	private JLabel 		samplingPeriodLabel = null;

	private JLabel 		diodeMultimeterChannelLabel = null;
	private JLabel 		diodeRefLabel = null;

	private JLabel 		diode1Label = null;
	private JLabel 		diode2Label = null;
	private JLabel 		diode3Label = null;
	private JLabel 		diode4Label = null;
	private JLabel 		diode5Label = null;
	private JLabel 		diode6Label = null;
	private JLabel 		diode7Label = null;

	private JTextField 	diode1RefTextField = null;
	private JTextField 	diode2RefTextField = null;
	private JTextField 	diode3RefTextField = null;
	private JTextField 	diode4RefTextField = null;
	private JTextField 	diode5RefTextField = null;
	private JTextField 	diode6RefTextField = null;
	private JTextField 	diode7RefTextField = null;

	private JCheckBox 		diode1CheckBox = null;
	private JCheckBox 		diode2CheckBox = null;
	private JCheckBox 		diode3CheckBox = null;
	private JCheckBox 		diode4CheckBox = null;
	private JCheckBox 		diode5CheckBox = null;
	private JCheckBox 		diode6CheckBox = null;
	private JCheckBox 		diode7CheckBox = null;

	private JComboBox 		diode1MultimeterChannelComboBox = null;
	private JComboBox 		diode2MultimeterChannelComboBox = null;
	private JComboBox 		diode3MultimeterChannelComboBox = null;
	private JComboBox 		diode4MultimeterChannelComboBox = null;
	private JComboBox 		diode5MultimeterChannelComboBox = null;
	private JComboBox 		diode6MultimeterChannelComboBox = null;
	private JComboBox 		diode7MultimeterChannelComboBox = null;


	private JComboBox 		stDevComboBox = null;
	private JComboBox 		measurementWindowComboBox = null;
	private JTextField 		samplingPeriodeTextField = null;

	private ItemListener 	temperatureProfileListItemListener = null;
	private ItemListener 	minTempComboBoxItemListener = null;
	private ItemListener 	nStepsComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	stepValueComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	temperatureStabilitzationByTimejRadioButtonItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	temperatureStabilitzationByStDevjRadioButtonItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode1MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode2MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode3MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode4MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode5MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode6MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode7MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:

	private JComboBox 		numberOfSuccessiveWindowsUnderStDevComboBox = null;
	private JLabel 			nOfSuccessiveValidWindowsUnderStDev = null;
	private JRadioButton 	temperatureStabilitzationByStDevjRadioButton = null;
	private JRadioButton 	temperatureStabilitzationByTimejRadioButton = null;
	private JLabel 			temperatureStabilitzationMethodjLabel = null;
	private JLabel 			temperatureStabiltizationTimejLabel = null;
	private JComboBox 		temperatureStabilitzationTimeComboBox = null;
	private JLabel 			measurementWindowLabel2 = null;
	private JLabel 			samplingPeriodLabel2 = null;
	private JLabel 			nOfSuccessiveValidWindowsUnderStDev2 = null;
	private JLabel 			temperatureStabiltizationTimejLabel2 = null;

	private JTextField 		warningTextField = null;

	private JCheckBox waitForGoodRoomTemperatureStabilizationjCheckBox = null;

	private JLabel diodesCurrentjLabel = null;
	private JLabel diodesCurrent2jLabel = null;

	private JTextField diodesCurrentInMilliampsjTextField = null;

	/**
	 * This is the default constructor
	 */
	public DiodesCalibrationViewer_Frame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _diodesCalibrationSetUpFilePath,DiodesCalibrationSetUp_Data _program,int _programMode, MainController _mainController) throws Exception{
		super("ThermalCalibrationProgramViewerFrame");
		mainController = _mainController;
		addWindowListener(mainController);
		fileChooser = new JFileChooser();
		initialize(_instrumentsDataFilePath,_temperatureSensorDataFilePath,_diodesCalibrationSetUpFilePath,_program,_programMode);
	}

	/**
	 * This method initializes jTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() throws Exception{
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(0, 0, 736, 451));
			jTabbedPane.addTab(getTemperatureProfilePanel().getName(), null, getTemperatureProfilePanel(), null);
			jTabbedPane.addTab(getDiodesToCalibratePanel().getName(), null, getDiodesToCalibratePanel(), null);
			jTabbedPane.addTab(getTemperatureStabilizationCriteriumDataPanel().getName(), null, getTemperatureStabilizationCriteriumDataPanel(), null);
			jTabbedPane.addTab(getDiodesCalibrationSetUpDescriptionPanel().getName(), null, getDiodesCalibrationSetUpDescriptionPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes temperatureProfilePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTemperatureProfilePanel() {
		if (temperatureProfilePanel == null) {
			temperatureProfilePanel = new JPanel();
			temperatureProfilePanel.setName("Temperature Profile");
			temperatureProfilePanel.setLayout(null);

			temperatureProfileConfigurationLabel = new JLabel();
			temperatureProfileConfigurationLabel.setBounds(new Rectangle(30, 30, 601, 31));
			temperatureProfileConfigurationLabel.setBackground(new Color(204, 204, 255));
			temperatureProfileConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel.setText("TEMPERATURE PROFILE CONFIGURATION");
			temperatureProfilePanel.add(temperatureProfileConfigurationLabel, null);

			temperatureProfilePanel.add(getWaitForGoodRoomTemperatureStabilizationjCheckBox(), null);
			addPredefinedProfilesButtonsToTemperatureProfilePanel(temperatureProfilePanel);
			addTemperatureProfileListToTemperatureProfilePanel(temperatureProfilePanel);
			addButtonsForDefiningManualTemperatureProfileToTemperatureProfilePanel(temperatureProfilePanel);
		}
		return temperatureProfilePanel;
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addButtonsForDefiningManualTemperatureProfileToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		stepValueLabel = new JLabel();
		stepValueLabel.setBounds(new Rectangle(345, 210, 76, 31));
		stepValueLabel.setText("Step Value");
		stepValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nStepsLabel = new JLabel();
		nStepsLabel.setBounds(new Rectangle(345, 165, 76, 31));
		nStepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nStepsLabel.setText("N Steps");
		maxTempLabel = new JLabel();
		maxTempLabel.setBounds(new Rectangle(345, 120, 76, 31));
		maxTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
		maxTempLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		maxTempLabel.setText("Max Temp");
		minTempLabel = new JLabel();
		minTempLabel.setBounds(new Rectangle(345, 75, 76, 31));
		minTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
		minTempLabel.setText("Min Temp");


		_temperatureProfilePanel.add(minTempLabel, null);
		_temperatureProfilePanel.add(getMinTempComboBox(), null);
		_temperatureProfilePanel.add(maxTempLabel, null);
		_temperatureProfilePanel.add(getMaxTempTextField(), null);
		_temperatureProfilePanel.add(nStepsLabel, null);
		_temperatureProfilePanel.add(getNStepsComboBox(), null);
		_temperatureProfilePanel.add(stepValueLabel, null);
		_temperatureProfilePanel.add(getStepValueComboBox(), null);
		_temperatureProfilePanel.add(getClearTemperatureProfileButton(), null);
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addTemperatureProfileListToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		_temperatureProfilePanel.add(getTemperatureProfileList(), null);
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addPredefinedProfilesButtonsToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		_temperatureProfilePanel.add(getMark0To100TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To125TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To150TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To175TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To200TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To225TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To250TemperatureProfileButton(), null);
	}

	/**
	 * This method initializes diodesToCalibratePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getDiodesToCalibratePanel() throws Exception{
		if (diodesToCalibratePanel == null) {

			diodesCurrent2jLabel = new JLabel();
			diodesCurrent2jLabel.setBounds(new Rectangle(195, 210, 76, 16));
			diodesCurrent2jLabel.setHorizontalAlignment(SwingConstants.CENTER);
			diodesCurrent2jLabel.setText("MilliAmps");
			diodesCurrentjLabel = new JLabel();
			diodesCurrentjLabel.setBounds(new Rectangle(30, 210, 91, 16));
			diodesCurrentjLabel.setText("Diodes Current");
			diodesToCalibrateConfigurationLabel = new JLabel();
			diodesToCalibrateConfigurationLabel.setBounds(new Rectangle(30, 30, 601, 31));
			diodesToCalibrateConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			diodesToCalibrateConfigurationLabel.setText("DIODES TO CALIBRATE");
			diodesToCalibrateConfigurationLabel.setBackground(new Color(204, 204, 255));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			diode1Label = new JLabel();
			diode2Label = new JLabel();
			diode3Label = new JLabel();
			diode4Label = new JLabel();
			diode5Label = new JLabel();
			diode6Label = new JLabel();
			diode7Label = new JLabel();

			diode1Label.setText("Diode1");
			diode2Label.setText("Diode2");
			diode3Label.setText("Diode3");
			diode4Label.setText("Diode4");
			diode5Label.setText("Diode5");
			diode6Label.setText("Diode6");
			diode7Label.setText("Diode7");

			diode1Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode2Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode3Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode4Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode5Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode6Label.setHorizontalAlignment(SwingConstants.CENTER);
			diode7Label.setHorizontalAlignment(SwingConstants.CENTER);

			diode1Label.setBounds(new Rectangle(90, 120, 61, 16));
			diode2Label.setBounds(new Rectangle(150, 120, 61, 16));
			diode3Label.setBounds(new Rectangle(210, 120, 61, 16));
			diode4Label.setBounds(new Rectangle(270, 120, 61, 16));
			diode5Label.setBounds(new Rectangle(330, 120, 61, 16));
			diode6Label.setBounds(new Rectangle(390, 120, 61, 16));
			diode7Label.setBounds(new Rectangle(450, 120, 61, 16));



			diodeRefLabel = new JLabel();
			diodeRefLabel.setText("Reference");
			diodeRefLabel.setBounds(new Rectangle(30, 150, 61, 16));
			diodeRefLabel.setHorizontalAlignment(SwingConstants.LEFT);

			diodeMultimeterChannelLabel = new JLabel();
			diodeMultimeterChannelLabel.setBounds(new Rectangle(30, 180, 61, 16));
			diodeMultimeterChannelLabel.setText("Channel");
			diodeMultimeterChannelLabel.setHorizontalAlignment(SwingConstants.LEFT);

			diodesToCalibratePanel = new JPanel();
			diodesToCalibratePanel.setName("Diodes To Calibrate");
			diodesToCalibratePanel.setLayout(null);

			diodesToCalibratePanel.add(diodesToCalibrateConfigurationLabel, null);
			diodesToCalibratePanel.add(diodeMultimeterChannelLabel, null);
			diodesToCalibratePanel.add(diodeRefLabel, null);

			diodesToCalibratePanel.add(diode1Label, null);
			diodesToCalibratePanel.add(diode2Label, null);
			diodesToCalibratePanel.add(diode3Label, null);
			diodesToCalibratePanel.add(diode4Label, null);
			diodesToCalibratePanel.add(diode5Label, null);
			diodesToCalibratePanel.add(diode6Label, null);
			diodesToCalibratePanel.add(diode7Label, null);

			diodesToCalibratePanel.add(getDiode1RefTextField(), null);
			diodesToCalibratePanel.add(getDiode2RefTextField(), null);
			diodesToCalibratePanel.add(getDiode3RefTextField(), null);
			diodesToCalibratePanel.add(getDiode4RefTextField(), null);
			diodesToCalibratePanel.add(getDiode5RefTextField(), null);
			diodesToCalibratePanel.add(getDiode6RefTextField(), null);
			diodesToCalibratePanel.add(getDiode7RefTextField(), null);

			diodesToCalibratePanel.add(getDiode1CheckBox(), null);
			diodesToCalibratePanel.add(getDiode2CheckBox(), null);
			diodesToCalibratePanel.add(getDiode3CheckBox(), null);
			diodesToCalibratePanel.add(getDiode4CheckBox(), null);
			diodesToCalibratePanel.add(getDiode5CheckBox(), null);
			diodesToCalibratePanel.add(getDiode6CheckBox(), null);
			diodesToCalibratePanel.add(getDiode7CheckBox(), null);

			diodesToCalibratePanel.add(getDiode1MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode2MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode3MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode4MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode5MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode6MultimeterChannelComboBox(), null);
			diodesToCalibratePanel.add(getDiode7MultimeterChannelComboBox(), null);

			diodesToCalibratePanel.add(getWarningTextField(), null);
			diodesToCalibratePanel.add(diodesCurrentjLabel, null);
			diodesToCalibratePanel.add(diodesCurrent2jLabel, null);
			diodesToCalibratePanel.add(getDiodesCurrentInMilliampsjTextField(), null);
		}
		return diodesToCalibratePanel;
	}

	/**
	 * This method initializes TemperatureStabilizationCriteriumDataPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTemperatureStabilizationCriteriumDataPanel() {
		if (temperatureStabilizationCriteriumDataPanel == null) {



			temperatureStabilitzationMethodjLabel = new JLabel();
			temperatureStabilitzationMethodjLabel.setBounds(new Rectangle(30, 90, 226, 31));
			temperatureStabilitzationMethodjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilitzationMethodjLabel.setText("Temperature Stabilitzation Method");

			stDevLabel = new JLabel();
			stDevLabel.setBounds(new Rectangle(30, 150, 226, 31));
			stDevLabel.setHorizontalAlignment(SwingConstants.LEFT);
			stDevLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			stDevLabel.setText("Standard Deviation");

			measurementWindowLabel = new JLabel();
			measurementWindowLabel.setBounds(new Rectangle(30, 180, 226, 31));
			measurementWindowLabel.setText("Measurement Window");
			measurementWindowLabel.setHorizontalAlignment(SwingConstants.LEFT);
			measurementWindowLabel2 = new JLabel();
			measurementWindowLabel2.setBounds(new Rectangle(390, 180, 151, 31));
			measurementWindowLabel2.setText("Number of Samples");
			measurementWindowLabel2.setHorizontalAlignment(SwingConstants.LEFT);

			samplingPeriodLabel = new JLabel();
			samplingPeriodLabel.setBounds(new Rectangle(30, 210, 226, 31));
			samplingPeriodLabel.setText("Sampling Period");
			samplingPeriodLabel.setHorizontalAlignment(SwingConstants.LEFT);
			samplingPeriodLabel2 = new JLabel();
			samplingPeriodLabel2.setBounds(new Rectangle(390, 210, 151, 31));
			samplingPeriodLabel2.setText("Milliseconds");
			samplingPeriodLabel2.setHorizontalAlignment(SwingConstants.LEFT);

			nOfSuccessiveValidWindowsUnderStDev = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev.setBounds(new Rectangle(30, 240, 226, 31));
			nOfSuccessiveValidWindowsUnderStDev.setHorizontalAlignment(SwingConstants.LEFT);
			nOfSuccessiveValidWindowsUnderStDev.setText("N of Successive Valid Windows");
			nOfSuccessiveValidWindowsUnderStDev2 = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev2.setBounds(new Rectangle(390, 240, 151, 31));
			nOfSuccessiveValidWindowsUnderStDev2.setText("Number of Windows");
			nOfSuccessiveValidWindowsUnderStDev2.setHorizontalAlignment(SwingConstants.LEFT);

			temperatureStabiltizationTimejLabel = new JLabel();
			temperatureStabiltizationTimejLabel.setBounds(new Rectangle(30, 315, 226, 31));
			temperatureStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			temperatureStabiltizationTimejLabel.setText("StabilitzationTime");
			temperatureStabiltizationTimejLabel2 = new JLabel();
			temperatureStabiltizationTimejLabel2.setBounds(new Rectangle(390, 315, 151, 31));
			temperatureStabiltizationTimejLabel2.setText("Minutes");
			temperatureStabiltizationTimejLabel2.setHorizontalAlignment(SwingConstants.LEFT);

			temperatureStabilizationCriteriumConfigurationLabel = new JLabel();
			temperatureStabilizationCriteriumConfigurationLabel.setBounds(new Rectangle(30, 30, 601, 31));
			temperatureStabilizationCriteriumConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilizationCriteriumConfigurationLabel.setText("TEMPERATURE STABILIZATION CRITERIUM ");
			temperatureStabilizationCriteriumConfigurationLabel.setBackground(new Color(204, 204, 255));
			temperatureStabilizationCriteriumDataPanel = new JPanel();
			temperatureStabilizationCriteriumDataPanel.setName("Temperature Stabilization Criterium");
			temperatureStabilizationCriteriumDataPanel.setLayout(null);
			temperatureStabilizationCriteriumDataPanel.add(temperatureStabilizationCriteriumConfigurationLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(stDevLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(measurementWindowLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(samplingPeriodLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(getStandardDeviationComboBox(), null);
			temperatureStabilizationCriteriumDataPanel.add(getNMeasuresPerCicleComboBox(), null);
			temperatureStabilizationCriteriumDataPanel.add(getSamplingPeriodeTextField(), null);
			temperatureStabilizationCriteriumDataPanel.add(getJNPassesBelowStandardDeviationComboBox(), null);
			temperatureStabilizationCriteriumDataPanel.add(nOfSuccessiveValidWindowsUnderStDev, null);
			temperatureStabilizationCriteriumDataPanel.add(getTemperatureStabilitzationByStDevjRadioButton(), null);
			temperatureStabilizationCriteriumDataPanel.add(getTemperatureStabilitzationByTimejRadioButton(), null);
			temperatureStabilizationCriteriumDataPanel.add(temperatureStabilitzationMethodjLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(temperatureStabiltizationTimejLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(getTemperatureStabilitzationTimejComboBox(), null);
			temperatureStabilizationCriteriumDataPanel.add(measurementWindowLabel2, null);
			temperatureStabilizationCriteriumDataPanel.add(samplingPeriodLabel2, null);
			temperatureStabilizationCriteriumDataPanel.add(nOfSuccessiveValidWindowsUnderStDev2, null);
			temperatureStabilizationCriteriumDataPanel.add(temperatureStabiltizationTimejLabel2, null);
		}
		return temperatureStabilizationCriteriumDataPanel;
	}

	/**
	 * This method initializes generalPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getDiodesCalibrationSetUpDescriptionPanel() {
		if (diodesCalibrationSetUpDescriptionPanel == null) {
			programDescriptionLabel = new JLabel();
			programDescriptionLabel.setBounds(new Rectangle(30, 180, 91, 76));
			programDescriptionLabel.setText("Description");
			programDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programAuthorLabel = new JLabel();
			programAuthorLabel.setBounds(new Rectangle(30, 135, 91, 31));
			programAuthorLabel.setText("Author");
			programAuthorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programNameLabel = new JLabel();
			programNameLabel.setBounds(new Rectangle(30, 90, 91, 31));
			programNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programNameLabel.setText("Name");
			diodesCalibrationSetUpConfigurationLabel = new JLabel();
			diodesCalibrationSetUpConfigurationLabel.setBounds(new Rectangle(30, 30, 601, 31));
			diodesCalibrationSetUpConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			diodesCalibrationSetUpConfigurationLabel.setText("DIODES CALIBRATION SET-UP DESCRIPTION");
			diodesCalibrationSetUpConfigurationLabel.setBackground(new Color(204, 204, 255));
			diodesCalibrationSetUpDescriptionPanel = new JPanel();
			diodesCalibrationSetUpDescriptionPanel.setName("Diodes Calibration Set-Up Description");
			diodesCalibrationSetUpDescriptionPanel.setLayout(null);
			diodesCalibrationSetUpDescriptionPanel.add(diodesCalibrationSetUpConfigurationLabel, null);
			diodesCalibrationSetUpDescriptionPanel.add(programNameLabel, null);
			diodesCalibrationSetUpDescriptionPanel.add(programAuthorLabel, null);
			diodesCalibrationSetUpDescriptionPanel.add(programDescriptionLabel, null);
			diodesCalibrationSetUpDescriptionPanel.add(getProgramNameTextField(), null);
			diodesCalibrationSetUpDescriptionPanel.add(getProgramAuthorTextField(), null);
			diodesCalibrationSetUpDescriptionPanel.add(getProgramDescriptionTextArea(), null);
		}
		return diodesCalibrationSetUpDescriptionPanel;
	}

	/**
	 * This method initializes temperatureProfileList
	 *
	 * @return java.awt.List
	 */
	private List getTemperatureProfileList() {
		if (temperatureProfileList == null) {
			temperatureProfileList = new List(10,true);
			temperatureProfileList.setBounds(new Rectangle(180, 75, 151, 301));
			temperatureProfileList.setBackground(new Color(238, 238, 238));
			for (int i=MIN_TEMP_OVEN;i<=MAX_TEMP_OVEN;i++){
		    	temperatureProfileList.addItem(Integer.toString(i));
		    }
			temperatureProfileListItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
	        		int nSelectedItems=0;

	        		String[] selectedItems = temperatureProfileList.getSelectedItems();
	        		nSelectedItems = selectedItems.length;
		        	if (nSelectedItems<=0){
		        		setDefaultTempProfileData();
		        	}else
		        	{
		        		removeTemperatureProfileItemListeners();
		        		nStepsComboBox.setSelectedItem(String.valueOf(nSelectedItems));
		        		maxTempTextField.setText(selectedItems[selectedItems.length-1]);
		        		minTempComboBox.setSelectedItem(selectedItems[0]);
		        		addTemperatureProfileItemListeners();
		        	}
		        }
		      };
		      temperatureProfileList.addItemListener(this.temperatureProfileListItemListener);
		}
		return temperatureProfileList;
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
			saveCancelPanel.setBounds(new Rectangle(0, 450, 791, 31));
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
			saveButton.setText("Save Diodes Calibration SetUp");
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
			saveAsButton.setText("Save Diodes Calibration SetUp As");
			saveAsButton.addActionListener(this);
		}
		return saveAsButton;
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _diodesCalibrationSetUpFilePath,DiodesCalibrationSetUp_Data _program,int _mode) throws Exception{
		initializeInstrumentsAndSensorData(_instrumentsDataFilePath,_temperatureSensorDataFilePath);
		initializeDiodesCalibrationSetUpFilePath(_diodesCalibrationSetUpFilePath);
		this.setSize(759, 511);
		this.setName("ThermalCalibrationProgramViewerFrame");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.program = _program;
		//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
	  	//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		configureFrameMode(_mode);
	}
	/**
	 *
	 * @param _diodeCalibrationSetUpFilePath
	 */
	private void initializeDiodesCalibrationSetUpFilePath(String _diodeCalibrationSetUpFilePath) {
		diodesCalibrationSetUpFilePath = _diodeCalibrationSetUpFilePath;
	}

	/**
	 *
	 * @param _instrumentsDataFilePath
	 * @param _temperatureSensorDataFilePath
	 * @throws Exception
	 */
	private void initializeInstrumentsAndSensorData(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath) throws Exception{
		instrumentsData = new Instruments_Data(_instrumentsDataFilePath);
		temperatureSensorData = new TemperatureSensor_Data(_temperatureSensorDataFilePath);
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
		    case 0:
		    	this.setTitle("New DiodesCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    setDefaultDiodesCalibrationSetUpData();
			    break;
		    case 1:
		    	this.setTitle("View DiodesCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadDiodesCalibrationSetUpData(program);
			    break;
		    case 2:
		    	this.setTitle("Edit DiodesCalibrationSetUp");
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadDiodesCalibrationSetUpData(program);
			    break;
		    default:
		    }
	 }
	public void loadDiodesCalibrationSetUpData(DiodesCalibrationSetUp_Data _diodesCalibrationSetUpData){
		loadTemperatureProfileProgramData(_diodesCalibrationSetUpData);
		loadDiodesToCalibrateData(_diodesCalibrationSetUpData);
		loadTemperatureStabilizationCriteriumData(_diodesCalibrationSetUpData);
		loadDescriptionData(_diodesCalibrationSetUpData);
	}
	private void loadTemperatureProfileProgramData(DiodesCalibrationSetUp_Data _diodesCalibrationSetUpData){
		removeTemperatureProfileItemListeners();
		maxTempTextField.setText(String.valueOf(_diodesCalibrationSetUpData.getTemperatureProfileData().getTemperatures()[_diodesCalibrationSetUpData.getTemperatureProfileData().getTemperatures().length-1]));
		minTempComboBox.setSelectedItem(String.valueOf(_diodesCalibrationSetUpData.getTemperatureProfileData().getTemperatures()[0]));
		nStepsComboBox.setSelectedItem(String.valueOf(_diodesCalibrationSetUpData.getTemperatureProfileData().getTemperatures().length));
		int[] temperatures = _diodesCalibrationSetUpData.getTemperatureProfileData().getTemperatures();
		for (int i=0;i<temperatures.length;i++){
			temperatureProfileList.select(temperatures[i]);
		}
		waitForGoodRoomTemperatureStabilizationjCheckBox.setSelected(_diodesCalibrationSetUpData.getTemperatureProfileData().isWaitForGoodRoomTemperatureStabilization());
		addTemperatureProfileItemListeners();
	}
	private void loadDiodesToCalibrateData(DiodesCalibrationSetUp_Data _diodesCalibrationSetUpData){

		this.diode1CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[0].isSelected());
		this.diode1RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[0].getDeviceReference());
		this.diode1MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[0].getDeviceChannel());

		this.diode2CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[1].isSelected());
		this.diode2RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[1].getDeviceReference());
		this.diode2MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[1].getDeviceChannel());

		this.diode3CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[2].isSelected());
		this.diode3RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[2].getDeviceReference());
		this.diode3MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[2].getDeviceChannel());

		this.diode4CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[3].isSelected());
		this.diode4RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[3].getDeviceReference());
		this.diode4MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[3].getDeviceChannel());

		this.diode5CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[4].isSelected());
		this.diode5RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[4].getDeviceReference());
		this.diode5MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[4].getDeviceChannel());

		this.diode6CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[5].isSelected());
		this.diode6RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[5].getDeviceReference());
		this.diode6MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[5].getDeviceChannel());

		this.diode7CheckBox.setSelected(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[6].isSelected());
		this.diode7RefTextField.setText(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[6].getDeviceReference());
		this.diode7MultimeterChannelComboBox.setSelectedItem(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDiodesToMeasureData()[6].getDeviceChannel());

		this.diodesCurrentInMilliampsjTextField.setText(Double.toString(_diodesCalibrationSetUpData.getDiodesToCalibrateData().getDevicesCurrentInMilliAmps()));
		}
	private void loadTemperatureStabilizationCriteriumData(DiodesCalibrationSetUp_Data _diodesCalibrationSetUpData){
		this.stDevComboBox.setSelectedItem(Double.toString(_diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getStDev()));
		this.measurementWindowComboBox.setSelectedItem(Integer.toString(_diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getMeasurementWindow()));
		this.samplingPeriodeTextField.setText(Long.toString(_diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getSamplingPeriode()));
		this.numberOfSuccessiveWindowsUnderStDevComboBox.setSelectedItem(Integer.toString(_diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getNumberOfWindowsUnderStDev()));
		this.temperatureStabilitzationTimeComboBox.setSelectedItem(Integer.toString(_diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationTime()));
		int temperatureStabilitzationMethod = _diodesCalibrationSetUpData.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationMethod();
		// 0 temperature stabilitzation by St Dev, 1 temperature stabilitzation by Time
		if (temperatureStabilitzationMethod==0) this.temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		if (temperatureStabilitzationMethod==1) this.temperatureStabilitzationByTimejRadioButton.setSelected(true);
	}
	private void loadDescriptionData(DiodesCalibrationSetUp_Data _diodesCalibrationSetUpData){
		this.diodesCalibrationSetUpDescriptionTextField.setText((String)_diodesCalibrationSetUpData.getDiodesCalibrationSetUpDescriptionData().getProgramName());
		this.diodesCalibrationSetUpDescriptionAuthorTextField.setText((String)_diodesCalibrationSetUpData.getDiodesCalibrationSetUpDescriptionData().getProgramAuthor());
		this.diodesCalibrationSetUpDescriptionTextArea.setText((String)_diodesCalibrationSetUpData.getDiodesCalibrationSetUpDescriptionData().getProgramDescription());
	}
	private void setDefaultDiodesCalibrationSetUpData(){
		setDefaultTempProfileData();
		setDefaultDiodesToCalibrateData();
		setDefaultTemperatureStabilizationCriteriumData();
		setDefaultDiodesCalibrationSetUpDescriptionData();
	}
	private void setDefaultDiodesCalibrationSetUpDescriptionData(){
		diodesCalibrationSetUpDescriptionTextField.setText("Program1");
		diodesCalibrationSetUpDescriptionAuthorTextField.setText("Author x");
		diodesCalibrationSetUpDescriptionTextArea.setText("Program for calibrating Diodes Voltage Drop under Temp.");
	}
	private void setDefaultTemperatureStabilizationCriteriumData(){
		stDevComboBox.setSelectedItem("0.03");
		measurementWindowComboBox.setSelectedItem("120");
		samplingPeriodeTextField.setText(Long.toString(SAMPLING_PERIODE_MIN_VALUE_MILLISECONDS + 700));
		numberOfSuccessiveWindowsUnderStDevComboBox.setSelectedItem("1");
		temperatureStabilitzationTimeComboBox.setSelectedItem("10");
		temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		samplingPeriodeTextField.setEnabled(false);
	}
	private void setDefaultDiodesToCalibrateData(){
		this.diode1CheckBox.setSelected(true);
		this.diode1RefTextField.setText("Diode1");
		this.diode2CheckBox.setSelected(false);
		this.diode2RefTextField.setText("Diode2");
		this.diode3CheckBox.setSelected(false);
		this.diode3RefTextField.setText("Diode3");
		this.diode4CheckBox.setSelected(false);
		this.diode4RefTextField.setText("Diode4");
		this.diode5CheckBox.setSelected(false);
		this.diode5RefTextField.setText("Diode5");
		this.diode6CheckBox.setSelected(false);
		this.diode6RefTextField.setText("Diode6");
		this.diode7CheckBox.setSelected(false);
		this.diode7RefTextField.setText("Diode7");
		this.diodesCurrentInMilliampsjTextField.setText("1.0");
	}
	private void setDefaultTempProfileData(){
		removeTemperatureProfileItemListeners();
		minTempComboBox.setSelectedItem("0");
		maxTempTextField.setText("0");
		nStepsComboBox.setSelectedItem("1");
		temperatureProfileList.select(0);
		stepValueComboBox.setSelectedItem("0");
		waitForGoodRoomTemperatureStabilizationjCheckBox.setSelected(false);
		addTemperatureProfileItemListeners();
	}
	private void removeTemperatureProfileItemListeners(){
		minTempComboBox.removeItemListener(minTempComboBoxItemListener);
		nStepsComboBox.removeItemListener(nStepsComboBoxItemListener);
		temperatureProfileList.removeItemListener(temperatureProfileListItemListener);
		stepValueComboBox.removeItemListener(stepValueComboBoxItemListener);
	}
	private void addTemperatureProfileItemListeners(){
		stepValueComboBox.addItemListener(stepValueComboBoxItemListener);
		temperatureProfileList.addItemListener(temperatureProfileListItemListener);
		nStepsComboBox.addItemListener(nStepsComboBoxItemListener);
		minTempComboBox.addItemListener(minTempComboBoxItemListener);
	}
	public void actionPerformed (ActionEvent event) {
		  String cmd = event.getActionCommand ();
		  System.out.println(cmd);

		  if (cmd.equals("Save Diodes Calibration SetUp As")) {
			  if (!validateDiodesCalibrationSetUpData()){return;}
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
		  if (cmd.equals("Save Diodes Calibration SetUp")) {
			  if (!validateDiodesCalibrationSetUpData()){return;}
			  //grabar con el nombre actual del fichero
			  try {
				  saveToXMLFile(diodesCalibrationSetUpFilePath);
				  System.out.println(diodesCalibrationSetUpFilePath);
			  } catch (Exception e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  if (cmd.equals("Cancel")) {}
		  if (cmd.equals("0-100 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("21");
			  maxTempTextField.setText("100");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-125 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("26");
			  maxTempTextField.setText("125");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-150 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("31");
			  maxTempTextField.setText("150");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-175 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("36");
			  maxTempTextField.setText("175");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-200 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("41");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-225 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("46");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-250 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("51");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("Clear Temp Profile")){
			  clearTemperatureProfileList();
			  setDefaultTempProfileData();
		  }
	  }
	private boolean validateDiodesCalibrationSetUpData(){
		if (validateTemperatureProfileData()==false) return false;
		if (validateDiodesToCalibrateData()==false) return false;
		if (validateTemperatureStabilizationCriteriumData()==false) return false;
		if (validateDiodesCalibrationSetUpDescriptionData()==false) return false;
		return true;
	  }
	/*
	public boolean validateInstrumentsData(){
		if (((String)multimeterSerialPortComboBox.getSelectedItem()).equals((String)ovenSerialPortComboBox.getSelectedItem())){
		  JOptionPane.showMessageDialog(null,"Los puertos del multimetro y del horno no pueden ser el mismo.");
		  return false;
		}

		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev1MultimeterChannelComboBox.getSelectedItem()) & dev1CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev2MultimeterChannelComboBox.getSelectedItem())& dev2CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev3MultimeterChannelComboBox.getSelectedItem())& dev3CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev4MultimeterChannelComboBox.getSelectedItem())& dev4CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev5MultimeterChannelComboBox.getSelectedItem())& dev5CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem())& dev6CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (((String)multimeterTemperatureSensorChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())& dev7CheckBox.isSelected()){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el sensor de temperatura no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		return true;
	}*/
	public boolean validateTemperatureProfileData(){
		return true;
	}
	public boolean validateDiodesToCalibrateData(){

		if (diodesCurrentInMilliampsjTextField.getText().isEmpty()){
			JOptionPane.showMessageDialog(null,"Debe indicar un valor real para la corriente aplicada a los diodos.");
			return false;
		}
		try {
			Double.parseDouble(diodesCurrentInMilliampsjTextField.getText());
		} catch (NumberFormatException nfe) {
			diodesCurrentInMilliampsjTextField.setText("");
			JOptionPane.showMessageDialog(null,"Debe introducir un valor real valido para la corriente aplicada a los diodos.");
			return false;
		}
		if (
				((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode2MultimeterChannelComboBox.getSelectedItem()) 		& diode2CheckBox.isSelected())
				|| ((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode3MultimeterChannelComboBox.getSelectedItem()) 	& diode3CheckBox.isSelected())
				|| ((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode4MultimeterChannelComboBox.getSelectedItem()) 	& diode4CheckBox.isSelected())
				|| ((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode5MultimeterChannelComboBox.getSelectedItem()) 	& diode5CheckBox.isSelected())
				|| ((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode6MultimeterChannelComboBox.getSelectedItem()) 	& diode6CheckBox.isSelected())
				|| ((diode1MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem()) 	& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº1 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((diode2MultimeterChannelComboBox.getSelectedItem()).equals(diode3MultimeterChannelComboBox.getSelectedItem())	& diode3CheckBox.isSelected())
				|| ((diode2MultimeterChannelComboBox.getSelectedItem()).equals(diode4MultimeterChannelComboBox.getSelectedItem())	& diode4CheckBox.isSelected())
				|| ((diode2MultimeterChannelComboBox.getSelectedItem()).equals(diode5MultimeterChannelComboBox.getSelectedItem())	& diode5CheckBox.isSelected())
				|| ((diode2MultimeterChannelComboBox.getSelectedItem()).equals(diode6MultimeterChannelComboBox.getSelectedItem())	& diode6CheckBox.isSelected())
				|| ((diode2MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem())	& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº2 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((diode3MultimeterChannelComboBox.getSelectedItem()).equals(diode4MultimeterChannelComboBox.getSelectedItem())	& diode4CheckBox.isSelected())
				|| ((diode3MultimeterChannelComboBox.getSelectedItem()).equals(diode5MultimeterChannelComboBox.getSelectedItem())	& diode5CheckBox.isSelected())
				|| ((diode3MultimeterChannelComboBox.getSelectedItem()).equals(diode6MultimeterChannelComboBox.getSelectedItem())	& diode6CheckBox.isSelected())
				|| ((diode3MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem())	& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº3 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((diode4MultimeterChannelComboBox.getSelectedItem()).equals(diode5MultimeterChannelComboBox.getSelectedItem())	& diode5CheckBox.isSelected())
				|| ((diode4MultimeterChannelComboBox.getSelectedItem()).equals(diode6MultimeterChannelComboBox.getSelectedItem())& diode6CheckBox.isSelected())
				|| ((diode4MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem())& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº4 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((diode5MultimeterChannelComboBox.getSelectedItem()).equals(diode6MultimeterChannelComboBox.getSelectedItem())& diode6CheckBox.isSelected())
				|| ((diode5MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem())& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº5 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((diode6MultimeterChannelComboBox.getSelectedItem()).equals(diode7MultimeterChannelComboBox.getSelectedItem())& diode7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº6 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		return true;
	}
	public boolean validateTemperatureStabilizationCriteriumData(){
		return true;
	}
	public boolean validateDiodesCalibrationSetUpDescriptionData(){
		if (diodesCalibrationSetUpDescriptionTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para el programa a crear.");
			return false;
		}
		if (diodesCalibrationSetUpDescriptionAuthorTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para author del programa a crear.");
			return false;
		}
		if (diodesCalibrationSetUpDescriptionTextArea.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir la descripción del programa a crear.");
			return false;
		}
		return true;
	}

	private void fillTemperatureProfileList(){

		  int temperatureProfileMinTemp = Integer.valueOf((String)minTempComboBox.getSelectedItem());
		  int temperatureProfileNSteps = Integer.valueOf((String)nStepsComboBox.getSelectedItem());
		  int stepsValue = Integer.valueOf((String)stepValueComboBox.getSelectedItem());
		  if (((temperatureProfileNSteps-1)*stepsValue + temperatureProfileMinTemp)>MAX_TEMP_OVEN){
			JOptionPane.showMessageDialog(null,"El valor maximo de la rampa de temperaturas no puede superar la temperatura máxima del horno.");
			setDefaultTempProfileData();
			clearTemperatureProfileList();
			return;
		  }else
		  {
			  temperatureProfileList.select(temperatureProfileMinTemp);
			  maxTempTextField.setText(String.valueOf((temperatureProfileNSteps-1)*stepsValue + temperatureProfileMinTemp));
			  for (int i=0;i<temperatureProfileNSteps;i++){
					 temperatureProfileList.select(i*stepsValue + temperatureProfileMinTemp);
			  }
		  }
	  }
	  private void reFillTemperatureProfileList(){
		  clearTemperatureProfileList();
		  fillTemperatureProfileList();
	  }
	  private void clearTemperatureProfileList(){
		  for (int i=0;i<=MAX_TEMP_OVEN;i++){temperatureProfileList.deselect(i);}
	  }

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To100TemperatureProfileButton() {
		if (mark0To100TemperatureProfileButton == null) {
			mark0To100TemperatureProfileButton = new JButton("0-100 Step-5ºC");
			mark0To100TemperatureProfileButton.addActionListener(this);
			mark0To100TemperatureProfileButton.setBounds(new Rectangle(30, 75, 121, 31));
		}
		return mark0To100TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To125TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To125TemperatureProfileButton() {
		if (mark0To125TemperatureProfileButton == null) {
			mark0To125TemperatureProfileButton = new JButton("0-125 Step-5ºC");
			mark0To125TemperatureProfileButton.addActionListener(this);
			mark0To125TemperatureProfileButton.setBounds(new Rectangle(30, 120, 121, 31));
		}
		return mark0To125TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To150TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To150TemperatureProfileButton() {
		if (mark0To150TemperatureProfileButton == null) {
			mark0To150TemperatureProfileButton = new JButton("0-150 Step-5ºC");
			mark0To150TemperatureProfileButton.addActionListener(this);
			mark0To150TemperatureProfileButton.setBounds(new Rectangle(30, 165, 121, 31));
		}
		return mark0To150TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To175TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To175TemperatureProfileButton() {
		if (mark0To175TemperatureProfileButton == null) {
			mark0To175TemperatureProfileButton = new JButton("0-175 Step-5ºC");
			mark0To175TemperatureProfileButton.addActionListener(this);
			mark0To175TemperatureProfileButton.setBounds(new Rectangle(30, 210, 121, 31));
		}
		return mark0To175TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To200TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To200TemperatureProfileButton() {
		if (mark0To200TemperatureProfileButton == null) {
			mark0To200TemperatureProfileButton = new JButton("0-200 Step-5ºC");
			mark0To200TemperatureProfileButton.addActionListener(this);
			mark0To200TemperatureProfileButton.setBounds(new Rectangle(30, 255, 121, 31));
		}
		return mark0To200TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To225TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To225TemperatureProfileButton() {
		if (mark0To225TemperatureProfileButton == null) {
			mark0To225TemperatureProfileButton = new JButton("0-225 Step-5ºC");
			mark0To225TemperatureProfileButton.setBounds(new Rectangle(30, 300, 118, 31));
			mark0To225TemperatureProfileButton.addActionListener(this);
		}
		return mark0To225TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To250TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To250TemperatureProfileButton() {
		if (mark0To250TemperatureProfileButton == null) {
			mark0To250TemperatureProfileButton = new JButton("0-250 Step-5ºC");
			mark0To250TemperatureProfileButton.setBounds(new Rectangle(30, 345, 118, 31));
			mark0To250TemperatureProfileButton.addActionListener(this);
		}
		return mark0To250TemperatureProfileButton;
	}

	/**
	 * This method initializes minTempComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getMinTempComboBox() {
		if (minTempComboBox == null) {
			minTempComboBox = new JComboBox();
			minTempComboBox.setBounds(new Rectangle(435, 75, 76, 31));
			for (int i=MIN_TEMP_OVEN;i<=N_TEMPERATURE_VALUES;i++){
				minTempComboBox.addItem (Integer.toString(i));
			}
			minTempComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	reFillTemperatureProfileList();
		        }
		     };
			minTempComboBox.addItemListener(minTempComboBoxItemListener);
		}
		return minTempComboBox;
	}

	/**
	 * This method initializes maxTempTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getMaxTempTextField() {
		if (maxTempTextField == null) {
			maxTempTextField = new JTextField();
			maxTempTextField.setBounds(new Rectangle(435, 120, 76, 31));
			maxTempTextField.setBackground(new Color(238, 238, 238));
			maxTempTextField.setEnabled(false);
		}
		return maxTempTextField;
	}

	/**
	 * This method initializes nStepsComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getNStepsComboBox() {
		if (nStepsComboBox == null) {
			nStepsComboBox = new JComboBox();
			nStepsComboBox.setBounds(new Rectangle(435, 165, 76, 31));
			for (int i=1;i<=(N_TEMPERATURE_VALUES+1);i++){
				nStepsComboBox.addItem (Integer.toString(i));
			}
			nStepsComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	switch(Integer.parseInt((String)stepValueComboBox.getSelectedItem())){
		        		case(0):
							JOptionPane.showMessageDialog(null,"Debe indicar primero un valor superior a 0 para Step.");
							clearTemperatureProfileList();
							setDefaultTempProfileData();
				        	break;
		        		default:
		        			reFillTemperatureProfileList();
		        	}
		        	reFillTemperatureProfileList();
		        }
		      };
			nStepsComboBox.addItemListener(nStepsComboBoxItemListener);
		}
		return nStepsComboBox;
	}

	/**
	 * This method initializes stepValueComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getStepValueComboBox() {
		if (stepValueComboBox == null) {
			stepValueComboBox = new JComboBox();
			stepValueComboBox.setBounds(new Rectangle(435, 210, 76, 31));
			for (int i=MIN_TEMP_OVEN;i<=N_TEMPERATURE_VALUES;i++){
				stepValueComboBox.addItem (Integer.toString(i));
			}
			stepValueComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if(((String)stepValueComboBox.getSelectedItem()).equals("0")){
						setDefaultTempProfileData();
					}
		        	reFillTemperatureProfileList();
		        }
		      };
		      stepValueComboBox.addItemListener(stepValueComboBoxItemListener);
		}
		return stepValueComboBox;
	}
	private void saveToXMLFile(String _programFilePath) throws Exception{

		  DiodeToCalibrate_Data[] deviceToMeasureData = new DiodeToCalibrate_Data[N_DIODES_TO_CALIBRATE];
		  System.out.println("---------------------------> "+(Integer)diode1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[0] = new DiodeToCalibrate_Data(diode1CheckBox.isSelected(),diode1RefTextField.getText(),(Integer)diode1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[1] = new DiodeToCalibrate_Data(diode2CheckBox.isSelected(),diode2RefTextField.getText(),(Integer)diode2MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[2] = new DiodeToCalibrate_Data(diode3CheckBox.isSelected(),diode3RefTextField.getText(),(Integer)diode3MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[3] = new DiodeToCalibrate_Data(diode4CheckBox.isSelected(),diode4RefTextField.getText(),(Integer)diode4MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[4] = new DiodeToCalibrate_Data(diode5CheckBox.isSelected(),diode5RefTextField.getText(),(Integer)diode5MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[5] = new DiodeToCalibrate_Data(diode6CheckBox.isSelected(),diode6RefTextField.getText(),(Integer)diode6MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[6] = new DiodeToCalibrate_Data(diode7CheckBox.isSelected(),diode7RefTextField.getText(),(Integer)diode7MultimeterChannelComboBox.getSelectedItem());

		  DiodesToCalibrate_Data measuresConfigurationData = new DiodesToCalibrate_Data(deviceToMeasureData, Double.parseDouble(diodesCurrentInMilliampsjTextField.getText()));

		  Object[] temperaturesList = temperatureProfileList.getSelectedObjects();
		  int[] temperatures = new int[temperaturesList.length];
		  for (int i=0;i<temperaturesList.length;i++){
			  temperatures[i] = Integer.parseInt((String)temperaturesList[i]);
		  }
		  TemperatureProfile_Data temperatureProfileData = new TemperatureProfile_Data
		  (
				  "TemperatureProfileData_1",
				  waitForGoodRoomTemperatureStabilizationjCheckBox.isSelected(),
				  temperatures
		  );
		  DiodesCalibrationSetUpDescription_Data generalProgramData = new DiodesCalibrationSetUpDescription_Data(this.diodesCalibrationSetUpDescriptionTextField.getText(),this.diodesCalibrationSetUpDescriptionAuthorTextField.getText(),this.diodesCalibrationSetUpDescriptionTextArea.getText());

		  int temperatureStabilitzationMethod = 0; //By Default St Dev
		  if (temperatureStabilitzationByTimejRadioButton.isSelected()) temperatureStabilitzationMethod = 1;
		  TemperatureStabilizationCriterium_Data temperatureStabilizationCriteriumProgramData = new TemperatureStabilizationCriterium_Data(Double.parseDouble((String)this.stDevComboBox.getSelectedItem()),Integer.parseInt((String)this.measurementWindowComboBox.getSelectedItem()),Long.parseLong((String)this.samplingPeriodeTextField.getText()),Integer.parseInt((String)this.numberOfSuccessiveWindowsUnderStDevComboBox.getSelectedItem()),temperatureStabilitzationMethod,Integer.parseInt((String)this.temperatureStabilitzationTimeComboBox.getSelectedItem()));

		  DiodesCalibrationSetUp_Data thermalCalibrationProgramData = new DiodesCalibrationSetUp_Data
		  (
				  temperatureProfileData,
				  measuresConfigurationData,
				  temperatureStabilizationCriteriumProgramData,
				  generalProgramData
		  );
		  program = thermalCalibrationProgramData;
		  thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData",_programFilePath);
	  }

	/**
	 * This method initializes clearTemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getClearTemperatureProfileButton() {
		if (clearTemperatureProfileButton == null) {
			clearTemperatureProfileButton = new JButton("Clear Temp Profile");
			clearTemperatureProfileButton.setBounds(new Rectangle(345, 345, 166, 31));
			clearTemperatureProfileButton.setText("Clear Temp Profile");
			clearTemperatureProfileButton.addActionListener(this);
		}
		return clearTemperatureProfileButton;
	}

	/**
	 * This method initializes programNameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getProgramNameTextField() {
		if (diodesCalibrationSetUpDescriptionTextField == null) {
			diodesCalibrationSetUpDescriptionTextField = new JTextField();
			diodesCalibrationSetUpDescriptionTextField.setBounds(new Rectangle(135, 90, 496, 31));
			diodesCalibrationSetUpDescriptionTextField.setHorizontalAlignment(JTextField.LEFT);
		}
		return diodesCalibrationSetUpDescriptionTextField;
	}

	/**
	 * This method initializes programAuthorTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getProgramAuthorTextField() {
		if (diodesCalibrationSetUpDescriptionAuthorTextField == null) {
			diodesCalibrationSetUpDescriptionAuthorTextField = new JTextField();
			diodesCalibrationSetUpDescriptionAuthorTextField.setBounds(new Rectangle(135, 135, 496, 31));
			diodesCalibrationSetUpDescriptionAuthorTextField.setHorizontalAlignment(JTextField.LEFT);
		}
		return diodesCalibrationSetUpDescriptionAuthorTextField;
	}

	/**
	 * This method initializes programDescriptionTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getProgramDescriptionTextArea() {
		if (diodesCalibrationSetUpDescriptionTextArea == null) {
			diodesCalibrationSetUpDescriptionTextArea = new JTextArea();
			diodesCalibrationSetUpDescriptionTextArea.setBounds(new Rectangle(135, 180, 496, 76));
			diodesCalibrationSetUpDescriptionTextArea.setLineWrap(false);
			diodesCalibrationSetUpDescriptionTextArea.setRows(10);
		}
		return diodesCalibrationSetUpDescriptionTextArea;
	}

	/**
	 * This method initializes dev1CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode1CheckBox() {
		if (diode1CheckBox == null) {
			diode1CheckBox = new JCheckBox();
			diode1CheckBox.setBounds(new Rectangle(90, 90, 61, 16));
			diode1CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode1CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode1RefTextField.setEnabled(true);
		        		diode1MultimeterChannelComboBox.setEnabled(true);
		        		diode2CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode1RefTextField.setEnabled(false);
		        		diode1MultimeterChannelComboBox.setEnabled(false);
		        		diode2CheckBox.setSelected(false);
		        		diode2CheckBox.setEnabled(false);
		        		diode2RefTextField.setEnabled(false);
		        		diode2MultimeterChannelComboBox.setEnabled(false);
		        		diode3CheckBox.setSelected(false);
		        		diode3RefTextField.setEnabled(false);
		        		diode3MultimeterChannelComboBox.setEnabled(false);
		        		diode3CheckBox.setEnabled(false);
		        		diode4CheckBox.setSelected(false);
		        		diode4RefTextField.setEnabled(false);
		        		diode4MultimeterChannelComboBox.setEnabled(false);
		        		diode4CheckBox.setEnabled(false);
		        		diode5CheckBox.setSelected(false);
		        		diode5RefTextField.setEnabled(false);
		        		diode5MultimeterChannelComboBox.setEnabled(false);
		        		diode5CheckBox.setEnabled(false);
		        		diode6CheckBox.setSelected(false);
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode1CheckBox;
	}
	/**
	 * This method initializes dev2CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode2CheckBox() {
		if (diode2CheckBox == null) {
			diode2CheckBox = new JCheckBox();
			diode2CheckBox.setBounds(new Rectangle(150, 90, 61, 16));
			diode2CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode2CheckBox.setVisible(true);
			diode2CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode2RefTextField.setEnabled(true);
		        		diode2MultimeterChannelComboBox.setEnabled(true);
		        		diode3CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode2RefTextField.setEnabled(false);
		        		diode2MultimeterChannelComboBox.setEnabled(false);
		        		diode3CheckBox.setSelected(false);
		        		diode3RefTextField.setEnabled(false);
		        		diode3MultimeterChannelComboBox.setEnabled(false);
		        		diode3CheckBox.setEnabled(false);
		        		diode4CheckBox.setSelected(false);
		        		diode4RefTextField.setEnabled(false);
		        		diode4MultimeterChannelComboBox.setEnabled(false);
		        		diode4CheckBox.setEnabled(false);
		        		diode5CheckBox.setSelected(false);
		        		diode5RefTextField.setEnabled(false);
		        		diode5MultimeterChannelComboBox.setEnabled(false);
		        		diode5CheckBox.setEnabled(false);
		        		diode6CheckBox.setSelected(false);
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode2CheckBox;
	}
	/**
	 * This method initializes dev3CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode3CheckBox() {
		if (diode3CheckBox == null) {
			diode3CheckBox = new JCheckBox();
			diode3CheckBox.setBounds(new Rectangle(210, 90, 61, 16));
			diode3CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode3CheckBox.setEnabled(false);
			diode3CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode3RefTextField.setEnabled(true);
		        		diode3MultimeterChannelComboBox.setEnabled(true);
		        		diode3CheckBox.setEnabled(true);
		        		diode4CheckBox.setEnabled(true);

		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode3RefTextField.setEnabled(false);
		        		diode3MultimeterChannelComboBox.setEnabled(false);
		        		diode4CheckBox.setSelected(false);
		        		diode4RefTextField.setEnabled(false);
		        		diode4MultimeterChannelComboBox.setEnabled(false);
		        		diode4CheckBox.setEnabled(false);
		        		diode5CheckBox.setSelected(false);
		        		diode5RefTextField.setEnabled(false);
		        		diode5MultimeterChannelComboBox.setEnabled(false);
		        		diode5CheckBox.setEnabled(false);
		        		diode6CheckBox.setSelected(false);
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode3CheckBox;
	}
	/**
	 * This method initializes dev4CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode4CheckBox() {
		if (diode4CheckBox == null) {
			diode4CheckBox = new JCheckBox();
			diode4CheckBox.setBounds(new Rectangle(270, 90, 61, 16));
			diode4CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode4CheckBox.setEnabled(false);
			diode4CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode4RefTextField.setEnabled(true);
		        		diode4MultimeterChannelComboBox.setEnabled(true);
		        		diode4CheckBox.setEnabled(true);
		        		diode5CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode4RefTextField.setEnabled(false);
		        		diode4MultimeterChannelComboBox.setEnabled(false);
		        		diode5CheckBox.setSelected(false);
		        		diode5RefTextField.setEnabled(false);
		        		diode5MultimeterChannelComboBox.setEnabled(false);
		        		diode5CheckBox.setEnabled(false);
		        		diode6CheckBox.setSelected(false);
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode4CheckBox;
	}
	/**
	 * This method initializes dev5CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode5CheckBox() {
		if (diode5CheckBox == null) {
			diode5CheckBox = new JCheckBox();
			diode5CheckBox.setBounds(new Rectangle(330, 90, 61, 16));
			diode5CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode5CheckBox.setEnabled(false);
			diode5CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode5RefTextField.setEnabled(true);
		        		diode5MultimeterChannelComboBox.setEnabled(true);
		        		diode5CheckBox.setEnabled(true);
		        		diode6CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode5RefTextField.setEnabled(false);
		        		diode5MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setSelected(false);
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode6CheckBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode5CheckBox;
	}
	/**
	 * This method initializes dev6CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode6CheckBox() {
		if (diode6CheckBox == null) {
			diode6CheckBox = new JCheckBox();
			diode6CheckBox.setBounds(new Rectangle(390, 90, 61, 16));
			diode6CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode6CheckBox.setEnabled(false);
			diode6CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode6RefTextField.setEnabled(true);
		        		diode6MultimeterChannelComboBox.setEnabled(true);
		        		diode6CheckBox.setEnabled(true);
		        		diode7CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode6RefTextField.setEnabled(false);
		        		diode6MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setSelected(false);
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        		diode7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode6CheckBox;
	}
	/**
	 * This method initializes dev7CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDiode7CheckBox() {
		if (diode7CheckBox == null) {
			diode7CheckBox = new JCheckBox();
			diode7CheckBox.setBounds(new Rectangle(450, 90, 61, 17));
			diode7CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			diode7CheckBox.setEnabled(false);
			diode7CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		diode7RefTextField.setEnabled(true);
		        		diode7MultimeterChannelComboBox.setEnabled(true);
		        		diode7CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		diode7RefTextField.setEnabled(false);
		        		diode7MultimeterChannelComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return diode7CheckBox;
	}

	/**
	 * This method initializes dev2NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode1RefTextField() {
		if (diode1RefTextField == null) {
			diode1RefTextField = new JTextField();
			diode1RefTextField.setEnabled(true);
			diode1RefTextField.setBounds(new Rectangle(90, 150, 61, 16));
		}
		return diode1RefTextField;
	}
	/**
	 * This method initializes dev1NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode2RefTextField() {
		if (diode2RefTextField == null) {
			diode2RefTextField = new JTextField();
			diode2RefTextField.setEnabled(false);
			diode2RefTextField.setBounds(new Rectangle(150, 150, 61, 16));
		}
		return diode2RefTextField;
	}
	/**
	 * This method initializes dev3NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode3RefTextField() {
		if (diode3RefTextField == null) {
			diode3RefTextField = new JTextField();
			diode3RefTextField.setBounds(new Rectangle(210, 150, 61, 16));
			diode3RefTextField.setEnabled(false);
		}
		return diode3RefTextField;
	}

	/**
	 * This method initializes dev4NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode4RefTextField() {
		if (diode4RefTextField == null) {
			diode4RefTextField = new JTextField();
			diode4RefTextField.setBounds(new Rectangle(270, 150, 61, 16));
			diode4RefTextField.setEnabled(false);
		}
		return diode4RefTextField;
	}

	/**
	 * This method initializes dev5NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode5RefTextField() {
		if (diode5RefTextField == null) {
			diode5RefTextField = new JTextField();
			diode5RefTextField.setEnabled(false);
			diode5RefTextField.setBounds(new Rectangle(330, 150, 61, 16));
		}
		return diode5RefTextField;
	}

	/**
	 * This method initializes dev6RefTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode6RefTextField() {
		if (diode6RefTextField == null) {
			diode6RefTextField = new JTextField();
			diode6RefTextField.setBounds(new Rectangle(390, 150, 61, 16));
			diode6RefTextField.setEnabled(false);
		}
		return diode6RefTextField;
	}
	/**
	 * This method initializes dev5NameTextField11
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiode7RefTextField() {
		if (diode7RefTextField == null) {
			diode7RefTextField = new JTextField();
			diode7RefTextField.setBounds(new Rectangle(450, 150, 61, 16));
			diode7RefTextField.setEnabled(false);
		}
		return diode7RefTextField;
	}

	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode1MultimeterChannelComboBox() throws Exception{
		if (diode1MultimeterChannelComboBox == null) {
			diode1MultimeterChannelComboBox = new JComboBox();
			diode1MultimeterChannelComboBox.setEnabled(false);
			diode1MultimeterChannelComboBox.setBounds(new Rectangle(90, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode1MultimeterChannelComboBox.addItem(i);
			}
			diode1MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode1MultimeterChannel = (Integer)diode1MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode1MultimeterChannel){
		           	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
		         	  JOptionPane.showMessageDialog(null,"DIODE1 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
		           	  diode1MultimeterChannelComboBox.setSelectedItem("1");
		        	}
		        }
		     };
		}
	     diode1MultimeterChannelComboBox.addItemListener(diode1MultimeterChannelComboBoxItemListener);

		return diode1MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode2MultimeterChannelComboBox() {
		if (diode2MultimeterChannelComboBox == null) {
			diode2MultimeterChannelComboBox = new JComboBox();
			diode2MultimeterChannelComboBox.setEnabled(false);
			diode2MultimeterChannelComboBox.setBounds(new Rectangle(150, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode2MultimeterChannelComboBox.addItem(i);
			}
			diode2MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode2MultimeterChannel = (Integer)diode2MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode2MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE2 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode2MultimeterChannelComboBox.setSelectedItem("2");
		        	}
		        }
		     };
		     diode2MultimeterChannelComboBox.addItemListener(diode2MultimeterChannelComboBoxItemListener);
		}
		return diode2MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode3MultimeterChannelComboBox() {
		if (diode3MultimeterChannelComboBox == null) {
			diode3MultimeterChannelComboBox = new JComboBox();
			diode3MultimeterChannelComboBox.setEnabled(false);
			diode3MultimeterChannelComboBox.setBounds(new Rectangle(210, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode3MultimeterChannelComboBox.addItem(i);
			}
			diode3MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode3MultimeterChannel = (Integer)diode3MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode3MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE3 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode3MultimeterChannelComboBox.setSelectedItem("3");
		        	}
		        }
		     };
		     diode3MultimeterChannelComboBox.addItemListener(diode3MultimeterChannelComboBoxItemListener);
		}
		return diode3MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode4MultimeterChannelComboBox() {
		if (diode4MultimeterChannelComboBox == null) {
			diode4MultimeterChannelComboBox = new JComboBox();
			diode4MultimeterChannelComboBox.setEnabled(false);
			diode4MultimeterChannelComboBox.setBounds(new Rectangle(270, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode4MultimeterChannelComboBox.addItem(i);
			}
			diode4MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode4MultimeterChannel = (Integer)diode4MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode4MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE4 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode4MultimeterChannelComboBox.setSelectedItem("5");
		        	}
		        }
		     };
		     diode4MultimeterChannelComboBox.addItemListener(diode4MultimeterChannelComboBoxItemListener);

		}
		return diode4MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode5MultimeterChannelComboBox() {
		if (diode5MultimeterChannelComboBox == null) {
			diode5MultimeterChannelComboBox = new JComboBox();
			diode5MultimeterChannelComboBox.setEnabled(false);
			diode5MultimeterChannelComboBox.setBounds(new Rectangle(330, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode5MultimeterChannelComboBox.addItem(i);
			}
			diode5MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode5MultimeterChannel = (Integer)diode5MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode5MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE5 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode5MultimeterChannelComboBox.setSelectedItem("6");
		        	}
		        }
		     };
		     diode5MultimeterChannelComboBox.addItemListener(diode5MultimeterChannelComboBoxItemListener);
		}
		return diode5MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode6MultimeterChannelComboBox() {
		if (diode6MultimeterChannelComboBox == null) {
			diode6MultimeterChannelComboBox = new JComboBox();
			diode6MultimeterChannelComboBox.setEnabled(false);
			diode6MultimeterChannelComboBox.setBounds(new Rectangle(390, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode6MultimeterChannelComboBox.addItem(i);
			}
			diode6MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode6MultimeterChannel = (Integer)diode6MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode6MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE6 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode6MultimeterChannelComboBox.setSelectedItem("7");
		        	}
		        }
		     };
		     diode6MultimeterChannelComboBox.addItemListener(diode6MultimeterChannelComboBoxItemListener);

		}
		return diode6MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDiode7MultimeterChannelComboBox() {
		if (diode7MultimeterChannelComboBox == null) {
			diode7MultimeterChannelComboBox = new JComboBox();
			diode7MultimeterChannelComboBox.setEnabled(false);
			diode7MultimeterChannelComboBox.setBounds(new Rectangle(450, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) diode7MultimeterChannelComboBox.addItem(i);
			}
			diode7MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int diode7MultimeterChannel = (Integer)diode7MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==diode7MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"DIODE7 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  diode7MultimeterChannelComboBox.setSelectedItem("8");
		        	}
		        }
		     };
		     diode7MultimeterChannelComboBox.addItemListener(diode7MultimeterChannelComboBoxItemListener);
		}
		return diode7MultimeterChannelComboBox;
	}
	/**
	 * This method initializes standardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getStandardDeviationComboBox() {
		if (stDevComboBox == null) {
			stDevComboBox = new JComboBox();
			stDevComboBox.setBounds(new Rectangle(270, 150, 106, 31));
			for (double i=1;i<=500;i=i+0.5){
				stDevComboBox.addItem (Double.toString(i/100));
			}
		}
		return stDevComboBox;
	}

	/**
	 * This method initializes nMeasuresPerCicleComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getNMeasuresPerCicleComboBox() {
		if (measurementWindowComboBox == null) {
			measurementWindowComboBox = new JComboBox();
			measurementWindowComboBox.setBounds(new Rectangle(270, 180, 106, 31));
			for (int i=N_MEASURES_PER_WINDOW_MIN_VALUE;i<=this.N_MEASURES_PER_WINDOW_MAX_VALUE;i=i+N_MEASURES_PER_WINDOW_INCREMENT){
				measurementWindowComboBox.addItem (Integer.toString(i));
			}
		}
		return measurementWindowComboBox;
	}

	/**
	 * This method initializes timeBetweenSuccesivesMeasuresComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JTextField getSamplingPeriodeTextField() {
		if (samplingPeriodeTextField == null) {
			samplingPeriodeTextField = new JTextField();
			samplingPeriodeTextField.setBounds(new Rectangle(270, 210, 106, 31));
			samplingPeriodeTextField.setBackground(new Color(238, 238, 238));
			samplingPeriodeTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			samplingPeriodeTextField.setText(String.valueOf(SAMPLING_PERIODE_MIN_VALUE_MILLISECONDS + 700));
			samplingPeriodeTextField.enable(false);
		}
		return samplingPeriodeTextField;
	}

	/**
	 * This method initializes jNPassesBelowStandardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJNPassesBelowStandardDeviationComboBox() {
		if (numberOfSuccessiveWindowsUnderStDevComboBox == null) {
			numberOfSuccessiveWindowsUnderStDevComboBox = new JComboBox();
			numberOfSuccessiveWindowsUnderStDevComboBox.setBounds(new Rectangle(270, 240, 106, 31));
			for (int i=N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_MIN_VALUE;i<=N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_MAX_VALUE;i=i+N_SUCCESSIVE_WINDOWS_BELOW_STANDARD_DEVIATION_INCREMENT){
				numberOfSuccessiveWindowsUnderStDevComboBox.addItem (Integer.toString(i));
			}
		}
		return numberOfSuccessiveWindowsUnderStDevComboBox;
	}

	/**
	 * This method initializes temperatureStabilitzationByStDevjRadioButton
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getTemperatureStabilitzationByStDevjRadioButton() {
		if (temperatureStabilitzationByStDevjRadioButton == null) {
			temperatureStabilitzationByStDevjRadioButton = new JRadioButton();
			temperatureStabilitzationByStDevjRadioButton.setBounds(new Rectangle(270, 90, 166, 31));
			temperatureStabilitzationByStDevjRadioButton.setText("By Standard Deviation");
			temperatureStabilitzationByStDevjRadioButtonItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		temperatureStabilitzationByTimejRadioButton.setSelected(false);
		        		stDevComboBox.enable(true);
		        		measurementWindowComboBox.enable(true);
		        		samplingPeriodeTextField.enable(false);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.enable(true);
		        		temperatureStabilitzationTimeComboBox.enable(false);
		        		stDevComboBox.setForeground(Color.BLACK);
		        		measurementWindowComboBox.setForeground(Color.BLACK);
		        		samplingPeriodeTextField.setForeground(Color.BLACK);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.setForeground(Color.BLACK);
		        		temperatureStabilitzationTimeComboBox.setForeground(Color.LIGHT_GRAY);
		        	}else{
		        		if (temperatureStabilitzationByTimejRadioButton.isSelected()==false) temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		        	}
		        }
		     };
		     temperatureStabilitzationByStDevjRadioButton.addItemListener(temperatureStabilitzationByStDevjRadioButtonItemListener);
		}
		return temperatureStabilitzationByStDevjRadioButton;
	}

	/**
	 * This method initializes temperatureStabilitzationByTimejRadioButton
	 *
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getTemperatureStabilitzationByTimejRadioButton() {
		if (temperatureStabilitzationByTimejRadioButton == null) {
			temperatureStabilitzationByTimejRadioButton = new JRadioButton();
			temperatureStabilitzationByTimejRadioButton.setBounds(new Rectangle(435, 90, 77, 31));
			temperatureStabilitzationByTimejRadioButton.setText("By Time");
			temperatureStabilitzationByTimejRadioButtonItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		temperatureStabilitzationByStDevjRadioButton.setSelected(false);
		        		stDevComboBox.enable(false);
		        		measurementWindowComboBox.enable(false);
		        		samplingPeriodeTextField.enable(false);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.enable(false);
		        		temperatureStabilitzationTimeComboBox.enable(true);
		        		stDevComboBox.setForeground(Color.LIGHT_GRAY);
		        		measurementWindowComboBox.setForeground(Color.LIGHT_GRAY);
		        		samplingPeriodeTextField.setForeground(Color.LIGHT_GRAY);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.setForeground(Color.LIGHT_GRAY);
		        		temperatureStabilitzationTimeComboBox.setForeground(Color.BLACK);
		        	}else{
		        		if (temperatureStabilitzationByStDevjRadioButton.isSelected()==false) temperatureStabilitzationByTimejRadioButton.setSelected(true);
		        	}
		        }
		     };
		     temperatureStabilitzationByTimejRadioButton.addItemListener(temperatureStabilitzationByTimejRadioButtonItemListener);

		}
		return temperatureStabilitzationByTimejRadioButton;
	}

	/**
	 * This method initializes temperatureStabilitzationTimejComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTemperatureStabilitzationTimejComboBox() {
		if (temperatureStabilitzationTimeComboBox == null) {
			temperatureStabilitzationTimeComboBox = new JComboBox();
			temperatureStabilitzationTimeComboBox.setBounds(new Rectangle(270, 315, 106, 31));
			for (int i=TEMPERATURE_STABILITZATION_TIME_MINUTES_MIN_VALUE;i<=TEMPERATURE_STABILITZATION_TIME_MINUTES_MAX_VALUE;i=i+TEMPERATURE_STABILITZATION_TIME_MINUTES_INCREMENT){
				temperatureStabilitzationTimeComboBox.addItem (Integer.toString(i));
			}
		}
		return temperatureStabilitzationTimeComboBox;
	}

	/**
	 * This method initializes warningTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getWarningTextField() {
		if (warningTextField == null) {
			warningTextField = new JTextField();
			warningTextField.setBounds(new Rectangle(30, 330, 481, 61));
			warningTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			warningTextField.setHorizontalAlignment(JTextField.CENTER);
			warningTextField.setBackground(Color.red);
			warningTextField.setText("WARNING: CHANNEL DEVICES MUST DIFFERENT FROM TEMP SENSOR CHANNEL.");
			warningTextField.setVisible(false);
		}
		return warningTextField;
	}

	/**
	 *
	 * @return
	 */
	private JCheckBox getWaitForGoodRoomTemperatureStabilizationjCheckBox() {
		if (waitForGoodRoomTemperatureStabilizationjCheckBox == null) {
			waitForGoodRoomTemperatureStabilizationjCheckBox = new JCheckBox();
			waitForGoodRoomTemperatureStabilizationjCheckBox.setBounds(new Rectangle(25, 380, 297, 24));
			waitForGoodRoomTemperatureStabilizationjCheckBox.setText("Wait for good room temperature stabilization");
		}
		return waitForGoodRoomTemperatureStabilizationjCheckBox;
	}

	/**
	 * This method initializes diodesCurrentInMilliampsjTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDiodesCurrentInMilliampsjTextField() {
		if (diodesCurrentInMilliampsjTextField == null) {
			diodesCurrentInMilliampsjTextField = new JTextField();
			diodesCurrentInMilliampsjTextField.setBounds(new Rectangle(120, 210, 76, 16));
		}
		return diodesCurrentInMilliampsjTextField;
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
						String programFilePath = "c:\\prueba1.xml";
					    //This is where a real application would open the file.
				        //log.append("Opening: " + file.getName() + "." + newline);
				        System.out.println("Creando la instancia para el programa.");
				    	DiodesCalibrationSetUp_Data _program = null;
						_program = new DiodesCalibrationSetUp_Data(programFilePath);
						System.out.println(_program.toString());
				    	String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
				    	String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
						DiodesCalibrationViewer_Frame thisClass = new DiodesCalibrationViewer_Frame(instrumentsDataFilePath,temperatureSensorDataFilePath,programFilePath,_program,1,null);
						thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						thisClass.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}

}
