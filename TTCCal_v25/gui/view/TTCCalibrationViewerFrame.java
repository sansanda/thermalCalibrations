package view;

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

import Data.temperatureStabilizationCriteriumData;
import Data.TTCSetUpDescriptionData;
import Data.InstrumentData;
import Data.InstrumentsData;
import Data.TTCsToCalibrateData;
import Data.TTCToCalibrateData;
import Data.TemperatureProfileData;
import Data.TemperatureSensorData;
import Data.TTCSetUpData;
import FilesManagement.FileChooser;
import Main.MainController;

import javax.swing.SwingConstants;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.util.Enumeration;

public class TTCCalibrationViewerFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private static final int 	N_TTCs_TO_MEASURE = 7;
	private static final int 	N_MULTIMETER_CHANNELS = 10;
	private static final int 	MIN_MULTIMETER_CHANNEL_NUMBER = 1;
	private static final int 	MULTIMETER_CHANNEL_INCREMENT = 1;
	private static final int 	MAX_TEMP_OVEN = 500;
	private static final int 	N_TEMPERATURE_VALUES = 500;
	private static final int 	MIN_TEMP_OVEN = 0;
	private static final double MAX_ADMISSIBLE_STEADY_STATE_ERROR_MAX_VALUE = 5.00;
	private static final double MAX_ADMISSIBLE_STEADY_STATE_ERROR_MIN_VALUE = 0.01;
	private static final double MAX_ADMISSIBLE_STEADY_STATE_ERROR_INCREMENT = 0.01;
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
	private static final int 	FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_MAX_VALUE = 30;
	private static final int 	FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_MIN_VALUE = 1;
	private static final int 	FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_INCREMENT = 1;


	private String 					ttcSetUpFilePath = "";  //  @jve:decl-index=0:

	private JFileChooser 			fileChooser = null;
	private TTCSetUpData 			program = null;
	private MainController 			mainController = null;
	private InstrumentsData 		instrumentsData = null;  //  @jve:decl-index=0:
	private TemperatureSensorData 	temperatureSensorData = null;

	private JPanel 		jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel 		temperatureProfilePanel = null;
	private JPanel 		ttcsToCalibratePanel = null;
	private JPanel 		temperatureStabilizationCriteriumDataPanel = null;
	private JPanel 		ttcSetUpDescriptionPanel = null;
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
	private JLabel 		ttcsToCalibrateConfigurationLabel = null;
	private JLabel 		temperatureProfileConfigurationLabel1 = null;
	private JLabel 		programNameLabel = null;
	private JLabel 		programAuthorLabel = null;
	private JLabel 		programDescriptionLabel = null;
	private JTextField 	programNameTextField = null;
	private JTextField 	programAuthorTextField = null;
	private JTextArea 	programDescriptionTextArea = null;
	private JLabel 		temperatureStabilizationCriteriumConfigurationLabel = null;
	private JLabel 		maxAdmissibleSteadyStateErrorLabel = null;
	private JLabel 		stDevLabel = null;
	private JLabel 		measurementWindowLabel = null;
	private JLabel 		samplingPeriodLabel = null;

	private JLabel 		ttcMultimeterChannelLabel = null;
	private JLabel 		ttcRefLabel = null;

	private JLabel 		ttc1Label = null;
	private JLabel 		ttc2Label = null;
	private JLabel 		ttc3Label = null;
	private JLabel 		ttc4Label = null;
	private JLabel 		ttc5Label = null;
	private JLabel 		ttc6Label = null;
	private JLabel 		ttc7Label = null;


	private JTextField 	ttc2RefTextField = null;
	private JTextField 	ttc1RefTextField = null;
	private JTextField 	ttc3RefTextField = null;
	private JTextField 	ttc4RefTextField = null;
	private JTextField 	ttc5RefTextField = null;
	private JTextField 	ttc6RefTextField = null;
	private JTextField 	ttc7RefTextField = null;

	private JCheckBox 		ttc1CheckBox = null;
	private JCheckBox 		ttc2CheckBox = null;
	private JCheckBox 		ttc3CheckBox = null;
	private JCheckBox 		ttc4CheckBox = null;
	private JCheckBox 		ttc5CheckBox = null;
	private JCheckBox 		ttc6CheckBox = null;
	private JCheckBox 		ttc7CheckBox = null;

	private JComboBox 		ttc1MultimeterChannelComboBox = null;
	private JComboBox 		ttc2MultimeterChannelComboBox = null;
	private JComboBox 		ttc3MultimeterChannelComboBox = null;
	private JComboBox 		ttc4MultimeterChannelComboBox = null;
	private JComboBox 		ttc5MultimeterChannelComboBox = null;
	private JComboBox 		ttc6MultimeterChannelComboBox = null;
	private JComboBox 		ttc7MultimeterChannelComboBox = null;


	private JComboBox 		maxAdmissibleSteadyStateErrorComboBox = null;
	private JComboBox 		stDevComboBox = null;
	private JComboBox 		measurementWindowComboBox = null;
	private JTextField 		samplingPeriodeTextField = null;

	private ItemListener 	temperatureProfileListItemListener = null;
	private ItemListener 	minTempComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	nStepsComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	stepValueComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	temperatureStabilitzationByTimejRadioButtonItemListener = null;
	private ItemListener 	temperatureStabilitzationByStDevjRadioButtonItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc1MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc2MultimeterChannelComboBoxItemListener = null;
	private ItemListener 	ttc3MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc4MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc5MultimeterChannelComboBoxItemListener = null;
	private ItemListener 	ttc6MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc7MultimeterChannelComboBoxItemListener = null;

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
	private JLabel 			maxAdmissibleSteadyStateErrorLabel1 = null;

	private JLabel 			firstTemperatureStepStabiltizationTimejLabel = null;
	private JLabel 			firstTemperatureStepStabiltizationTimejLabel2 = null;
	private JComboBox 		firstTemperatureStepStabiltizationTimeComboBox = null;

	private JTextField temperatureSensorChannelIndicatorTextField = null;

	/**
	 * This is the default constructor
	 */
	public TTCCalibrationViewerFrame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _ttcSetUpFilePath,TTCSetUpData _program,int _mode, MainController _mainController) throws Exception{
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
			jTabbedPane.addTab(getTemperatureProfilePanel().getName(), null, getTemperatureProfilePanel(), null);
			jTabbedPane.addTab(getTTCSToCalibratePanel().getName(), null, getTTCSToCalibratePanel(), null);
			jTabbedPane.addTab(getTemperatureStabilizationCriteriumDataPanel().getName(), null, getTemperatureStabilizationCriteriumDataPanel(), null);
			jTabbedPane.addTab(getTTCSetUpDescriptionPanel().getName(), null, getTTCSetUpDescriptionPanel(), null);
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
	 * This method initializes TTCSToCalibratePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTTCSToCalibratePanel() throws Exception{
		if (ttcsToCalibratePanel == null) {

			ttcsToCalibrateConfigurationLabel = new JLabel();
			ttcsToCalibrateConfigurationLabel.setBounds(new Rectangle(75, 30, 601, 31));
			ttcsToCalibrateConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ttcsToCalibrateConfigurationLabel.setText("TTCs TO CALIBRATE");
			ttcsToCalibrateConfigurationLabel.setBackground(new Color(204, 204, 255));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			ttc1Label = new JLabel();
			ttc2Label = new JLabel();
			ttc3Label = new JLabel();
			ttc4Label = new JLabel();
			ttc5Label = new JLabel();
			ttc6Label = new JLabel();
			ttc7Label = new JLabel();

			ttc1Label.setText("TTC1");
			ttc2Label.setText("TTC2");
			ttc3Label.setText("TTC3");
			ttc4Label.setText("TTC4");
			ttc5Label.setText("TTC5");
			ttc6Label.setText("TTC6");
			ttc7Label.setText("TTC7");

			ttc1Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc2Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc3Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc4Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc5Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc6Label.setHorizontalAlignment(SwingConstants.CENTER);
			ttc7Label.setHorizontalAlignment(SwingConstants.CENTER);

			ttc1Label.setBounds(new Rectangle(135, 120, 61, 16));
			ttc2Label.setBounds(new Rectangle(195, 120, 61, 16));
			ttc3Label.setBounds(new Rectangle(255, 120, 61, 16));
			ttc4Label.setBounds(new Rectangle(315, 120, 61, 16));
			ttc5Label.setBounds(new Rectangle(375, 120, 61, 16));
			ttc6Label.setBounds(new Rectangle(435, 120, 61, 16));
			ttc7Label.setBounds(new Rectangle(495, 120, 61, 16));



			ttcRefLabel = new JLabel();
			ttcRefLabel.setText("Device Reference");
			ttcRefLabel.setBounds(new Rectangle(0, 150, 136, 16));
			ttcRefLabel.setHorizontalAlignment(SwingConstants.CENTER);

			ttcMultimeterChannelLabel = new JLabel();
			ttcMultimeterChannelLabel.setBounds(new Rectangle(0, 180, 136, 16));
			ttcMultimeterChannelLabel.setText("Multimeter Channel");
			ttcMultimeterChannelLabel.setHorizontalAlignment(SwingConstants.CENTER);

			ttcsToCalibratePanel = new JPanel();
			ttcsToCalibratePanel.setName("TTCs To Calibrate");
			ttcsToCalibratePanel.setLayout(null);

			ttcsToCalibratePanel.add(ttcsToCalibrateConfigurationLabel, null);
			ttcsToCalibratePanel.add(ttcMultimeterChannelLabel, null);
			ttcsToCalibratePanel.add(ttcRefLabel, null);

			ttcsToCalibratePanel.add(ttc1Label, null);
			ttcsToCalibratePanel.add(ttc2Label, null);
			ttcsToCalibratePanel.add(ttc3Label, null);
			ttcsToCalibratePanel.add(ttc4Label, null);
			ttcsToCalibratePanel.add(ttc5Label, null);
			ttcsToCalibratePanel.add(ttc6Label, null);
			ttcsToCalibratePanel.add(ttc7Label, null);

			ttcsToCalibratePanel.add(getTTC1RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC2RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC3RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC4RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC5RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC6RefTextField(), null);
			ttcsToCalibratePanel.add(getTTC7RefTextField(), null);

			ttcsToCalibratePanel.add(getTTC1CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC2CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC3CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC4CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC5CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC6CheckBox(), null);
			ttcsToCalibratePanel.add(getTTC7CheckBox(), null);

			ttcsToCalibratePanel.add(getTTC1MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC2MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC3MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC4MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC5MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC6MultimeterChannelComboBox(), null);
			ttcsToCalibratePanel.add(getTTC7MultimeterChannelComboBox(), null);

			ttcsToCalibratePanel.add(getWarningTextField(), null);
			ttcsToCalibratePanel.add(getTemperatureSensorChannelIndicatorTextField(), null);
		}
		return ttcsToCalibratePanel;
	}

	/**
	 * This method initializes TemperatureStabilizationCriteriumDataPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTemperatureStabilizationCriteriumDataPanel() {
		if (temperatureStabilizationCriteriumDataPanel == null) {

			maxAdmissibleSteadyStateErrorLabel1 = new JLabel();
			maxAdmissibleSteadyStateErrorLabel1.setBounds(new Rectangle(390, 240, 151, 31));
			maxAdmissibleSteadyStateErrorLabel1.setHorizontalAlignment(SwingConstants.LEFT);
			maxAdmissibleSteadyStateErrorLabel1.setText("ºC");
			maxAdmissibleSteadyStateErrorLabel1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);

			temperatureStabilitzationMethodjLabel = new JLabel();
			temperatureStabilitzationMethodjLabel.setBounds(new Rectangle(30, 75, 226, 31));
			temperatureStabilitzationMethodjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilitzationMethodjLabel.setText("Temperature Stabilitzation Method");


			maxAdmissibleSteadyStateErrorLabel = new JLabel();
			maxAdmissibleSteadyStateErrorLabel.setBounds(new Rectangle(30, 240, 226, 31));
			maxAdmissibleSteadyStateErrorLabel.setHorizontalAlignment(SwingConstants.LEFT);
			maxAdmissibleSteadyStateErrorLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			maxAdmissibleSteadyStateErrorLabel.setText("Max Temperature Steady State Error");

			stDevLabel = new JLabel();
			stDevLabel.setBounds(new Rectangle(30, 120, 226, 31));
			stDevLabel.setHorizontalAlignment(SwingConstants.LEFT);
			stDevLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			stDevLabel.setText("Standard Deviation");

			measurementWindowLabel = new JLabel();
			measurementWindowLabel.setBounds(new Rectangle(30, 150, 226, 31));
			measurementWindowLabel.setText("Measurement Window");
			measurementWindowLabel.setHorizontalAlignment(SwingConstants.LEFT);
			measurementWindowLabel2 = new JLabel();
			measurementWindowLabel2.setBounds(new Rectangle(390, 150, 151, 31));
			measurementWindowLabel2.setText("Number of Samples");
			measurementWindowLabel2.setHorizontalAlignment(SwingConstants.LEFT);

			samplingPeriodLabel = new JLabel();
			samplingPeriodLabel.setBounds(new Rectangle(30, 180, 226, 31));
			samplingPeriodLabel.setText("Sampling Period");
			samplingPeriodLabel.setHorizontalAlignment(SwingConstants.LEFT);
			samplingPeriodLabel2 = new JLabel();
			samplingPeriodLabel2.setBounds(new Rectangle(390, 180, 151, 31));
			samplingPeriodLabel2.setText("Milliseconds");
			samplingPeriodLabel2.setHorizontalAlignment(SwingConstants.LEFT);

			nOfSuccessiveValidWindowsUnderStDev = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev.setBounds(new Rectangle(30, 210, 226, 31));
			nOfSuccessiveValidWindowsUnderStDev.setHorizontalAlignment(SwingConstants.LEFT);
			nOfSuccessiveValidWindowsUnderStDev.setText("N of Successive Valid Windows");
			nOfSuccessiveValidWindowsUnderStDev2 = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev2.setBounds(new Rectangle(390, 210, 151, 31));
			nOfSuccessiveValidWindowsUnderStDev2.setText("Number of Windows");
			nOfSuccessiveValidWindowsUnderStDev2.setHorizontalAlignment(SwingConstants.LEFT);

			temperatureStabiltizationTimejLabel = new JLabel();
			temperatureStabiltizationTimejLabel.setBounds(new Rectangle(30, 285, 226, 31));
			temperatureStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			temperatureStabiltizationTimejLabel.setText("StabilitzationTime");
			temperatureStabiltizationTimejLabel2 = new JLabel();
			temperatureStabiltizationTimejLabel2.setBounds(new Rectangle(390, 285, 151, 31));
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
			temperatureStabilizationCriteriumDataPanel.add(maxAdmissibleSteadyStateErrorLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(stDevLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(measurementWindowLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(samplingPeriodLabel, null);
			temperatureStabilizationCriteriumDataPanel.add(getMaximunAdmissibleSteadyStateErrorComboBox(), null);
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
			temperatureStabilizationCriteriumDataPanel.add(maxAdmissibleSteadyStateErrorLabel1, null);
			temperatureStabilizationCriteriumDataPanel.add(getFirstTemperatureStepStabiltizationTimejLabel(), null);
			temperatureStabilizationCriteriumDataPanel.add(getFirstTemperatureStepStabiltizationTimejLabel2(), null);
			temperatureStabilizationCriteriumDataPanel.add(getFirstTemperatureStepStabiltizationTimejComboBox(), null);
		}
		return temperatureStabilizationCriteriumDataPanel;
	}

	private JLabel getFirstTemperatureStepStabiltizationTimejLabel() {
		if (firstTemperatureStepStabiltizationTimejLabel == null) {
			firstTemperatureStepStabiltizationTimejLabel = new JLabel();
			firstTemperatureStepStabiltizationTimejLabel.setBounds(new Rectangle(30, 345, 226, 31));
			firstTemperatureStepStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			firstTemperatureStepStabiltizationTimejLabel.setText("First Temp Step Stabilitzation Time");
		}
		return firstTemperatureStepStabiltizationTimejLabel;
	}

	private JLabel getFirstTemperatureStepStabiltizationTimejLabel2() {
		if (firstTemperatureStepStabiltizationTimejLabel2 == null) {
			firstTemperatureStepStabiltizationTimejLabel2 = new JLabel();
			firstTemperatureStepStabiltizationTimejLabel2.setBounds(new Rectangle(390, 345, 151, 31));
			firstTemperatureStepStabiltizationTimejLabel2.setText("Minutes");
			firstTemperatureStepStabiltizationTimejLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return firstTemperatureStepStabiltizationTimejLabel2;
	}
	private JComboBox getFirstTemperatureStepStabiltizationTimejComboBox() {
		if (firstTemperatureStepStabiltizationTimeComboBox== null) {
			firstTemperatureStepStabiltizationTimeComboBox = new JComboBox();
			firstTemperatureStepStabiltizationTimeComboBox.setBounds(new Rectangle(270, 345, 106, 31));
			for (int i=FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_MIN_VALUE;i<=FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_MAX_VALUE;i=i+FIRST_TEMPERATURE_STEP_STABILIZATION_TIME_INCREMENT){
				firstTemperatureStepStabiltizationTimeComboBox.addItem (Integer.toString(i));
			}
		}
		return firstTemperatureStepStabiltizationTimeComboBox;
	}

	/**
	 * This method initializes generalPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTTCSetUpDescriptionPanel() {
		if (ttcSetUpDescriptionPanel == null) {
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
			temperatureProfileConfigurationLabel1 = new JLabel();
			temperatureProfileConfigurationLabel1.setBounds(new Rectangle(30, 30, 601, 31));
			temperatureProfileConfigurationLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel1.setText("TTC SET-UP DESCRIPTION");
			temperatureProfileConfigurationLabel1.setBackground(new Color(204, 204, 255));
			ttcSetUpDescriptionPanel = new JPanel();
			ttcSetUpDescriptionPanel.setName("TTC Set-Up Description");
			ttcSetUpDescriptionPanel.setLayout(null);
			ttcSetUpDescriptionPanel.add(temperatureProfileConfigurationLabel1, null);
			ttcSetUpDescriptionPanel.add(programNameLabel, null);
			ttcSetUpDescriptionPanel.add(programAuthorLabel, null);
			ttcSetUpDescriptionPanel.add(programDescriptionLabel, null);
			ttcSetUpDescriptionPanel.add(getProgramNameTextField(), null);
			ttcSetUpDescriptionPanel.add(getProgramAuthorTextField(), null);
			ttcSetUpDescriptionPanel.add(getProgramDescriptionTextArea(), null);
		}
		return ttcSetUpDescriptionPanel;
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
	private void initialize(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,String _ttcSetUpFilePath,TTCSetUpData _program,int _mode) throws Exception{
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
		configureFrameMode(_mode);
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
		    case 0:
		    	this.setTitle("New TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    setDefaultThermalCalibrationData();
			    break;
		    case 1:
		    	this.setTitle("View TTCalibrationSetUp");
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadThermalCalibrationData(program);
			    break;
		    case 2:
		    	this.setTitle("Edit TTCalibrationSetUp");
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadThermalCalibrationData(program);
			    break;
		    default:
		    }
	 }
	public void loadThermalCalibrationData(TTCSetUpData _thermalCalibrationData){
		loadTemperatureProfileProgramData(_thermalCalibrationData);
		loadTTCsToCalibrateData(_thermalCalibrationData);
		loadTemperatureStabilizationCriteriumData(_thermalCalibrationData);
		loadDescriptionData(_thermalCalibrationData);
	}
	private void loadTemperatureProfileProgramData(TTCSetUpData _thermalCalibrationData){
		removeTemperatureProfileItemListeners();
		maxTempTextField.setText(String.valueOf(_thermalCalibrationData.getTemperatureProfileData().getTemperatures()[_thermalCalibrationData.getTemperatureProfileData().getTemperatures().length-1]));
		minTempComboBox.setSelectedItem(String.valueOf(_thermalCalibrationData.getTemperatureProfileData().getTemperatures()[0]));
		nStepsComboBox.setSelectedItem(String.valueOf(_thermalCalibrationData.getTemperatureProfileData().getTemperatures().length));
		int[] temperatures = _thermalCalibrationData.getTemperatureProfileData().getTemperatures();
		for (int i=0;i<temperatures.length;i++){
			temperatureProfileList.select(temperatures[i]);
		}
		addTemperatureProfileItemListeners();
	}
	private void loadTTCsToCalibrateData(TTCSetUpData _thermalCalibrationData){

		this.ttc1CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[0].isSelected());
		this.ttc1RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[0].getDeviceReference());
		this.ttc1MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[0].getDeviceChannel());

		this.ttc2CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[1].isSelected());
		this.ttc2RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[1].getDeviceReference());
		this.ttc2MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[1].getDeviceChannel());

		this.ttc3CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[2].isSelected());
		this.ttc3RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[2].getDeviceReference());
		this.ttc3MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[2].getDeviceChannel());

		this.ttc4CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[3].isSelected());
		this.ttc4RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[3].getDeviceReference());
		this.ttc4MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[3].getDeviceChannel());

		this.ttc5CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[4].isSelected());
		this.ttc5RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[4].getDeviceReference());
		this.ttc5MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[4].getDeviceChannel());

		this.ttc6CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[5].isSelected());
		this.ttc6RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[5].getDeviceReference());
		this.ttc6MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[5].getDeviceChannel());

		this.ttc7CheckBox.setSelected(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[6].isSelected());
		this.ttc7RefTextField.setText(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[6].getDeviceReference());
		this.ttc7MultimeterChannelComboBox.setSelectedItem(_thermalCalibrationData.getTTCsToCalibrateData().getDevicesToMeasureData()[6].getDeviceChannel());

		}
	private void loadTemperatureStabilizationCriteriumData(TTCSetUpData _thermalCalibrationData){
		this.firstTemperatureStepStabiltizationTimeComboBox.setSelectedItem(Integer.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getFirstTemperatureStepStabilitzationTimeInMinutes()));
		this.maxAdmissibleSteadyStateErrorComboBox.setSelectedItem(Double.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getMaxAdminssibleTemperatureError()));
		this.stDevComboBox.setSelectedItem(Double.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getStDev()));
		this.measurementWindowComboBox.setSelectedItem(Integer.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getMeasurementWindow()));
		this.samplingPeriodeTextField.setText(Long.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getSamplingPeriode()));
		this.numberOfSuccessiveWindowsUnderStDevComboBox.setSelectedItem(Integer.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getNumberOfWindowsUnderStDev()));
		this.temperatureStabilitzationTimeComboBox.setSelectedItem(Integer.toString(_thermalCalibrationData.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationTime()));
		int temperatureStabilitzationMethod = _thermalCalibrationData.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationMethod();
		// 0 temperature stabilitzation by St Dev, 1 temperature stabilitzation by Time
		if (temperatureStabilitzationMethod==0) this.temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		if (temperatureStabilitzationMethod==1) this.temperatureStabilitzationByTimejRadioButton.setSelected(true);
	}
	private void loadDescriptionData(TTCSetUpData _thermalCalibrationData){
		this.programNameTextField.setText((String)_thermalCalibrationData.getTTCSetUpDescriptionData().getProgramName());
		this.programAuthorTextField.setText((String)_thermalCalibrationData.getTTCSetUpDescriptionData().getProgramAuthor());
		this.programDescriptionTextArea.setText((String)_thermalCalibrationData.getTTCSetUpDescriptionData().getProgramDescription());
	}
	private void setDefaultThermalCalibrationData(){
		setDefaultTempProfileData();
		setDefaultTTCsToCalibrateData();
		setDefaultTemperatureStabilizationCriteriumData();
		setDefaultDescriptionData();
	}
	private void setDefaultDescriptionData(){
		this.programNameTextField.setText("Program1");
		this.programAuthorTextField.setText("Author x");
		this.programDescriptionTextArea.setText("Program for calibrating Thermal Test Chips.");
	}
	private void setDefaultTemperatureStabilizationCriteriumData(){
		firstTemperatureStepStabiltizationTimeComboBox.setSelectedItem("10");
		maxAdmissibleSteadyStateErrorComboBox.setSelectedItem("5.0");
		stDevComboBox.setSelectedItem("0.03");
		measurementWindowComboBox.setSelectedItem("120");
		samplingPeriodeTextField.setText(Long.toString(SAMPLING_PERIODE_MIN_VALUE_MILLISECONDS + 700));
		numberOfSuccessiveWindowsUnderStDevComboBox.setSelectedItem("1");
		temperatureStabilitzationTimeComboBox.setSelectedItem("10");
		temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		samplingPeriodeTextField.setEnabled(false);
	}
	private void setDefaultTTCsToCalibrateData(){
		this.ttc1CheckBox.setSelected(true);
		this.ttc1RefTextField.setText("TTC_1");
		this.ttc2CheckBox.setSelected(false);
		this.ttc2RefTextField.setText("TTC_2");
		this.ttc3CheckBox.setSelected(false);
		this.ttc3RefTextField.setText("TTC_3");
		this.ttc4CheckBox.setSelected(false);
		this.ttc4RefTextField.setText("TTC_4");
		this.ttc5CheckBox.setSelected(false);
		this.ttc5RefTextField.setText("TTC_5");
		this.ttc6CheckBox.setSelected(false);
		this.ttc6RefTextField.setText("TTC_6");
		this.ttc7CheckBox.setSelected(false);
		this.ttc7RefTextField.setText("TTC_7");


	}
	private void setDefaultTempProfileData(){
		removeTemperatureProfileItemListeners();
		minTempComboBox.setSelectedItem("0");
		maxTempTextField.setText("0");
		nStepsComboBox.setSelectedItem("1");
		temperatureProfileList.select(0);
		stepValueComboBox.setSelectedItem("0");
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
	private boolean validateProgramData(){
		if (validateTemperatureProfileData()==false) return false;
		if (validateTTCsToCalibrateData()==false) return false;
		if (validateTemperatureStabilizationCriteriumData()==false) return false;
		if (validateDescriptionData()==false) return false;
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
	public boolean validateTTCsToCalibrateData(){
		if (
				((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc2MultimeterChannelComboBox.getSelectedItem()) 		& ttc2CheckBox.isSelected())
				|| ((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc3MultimeterChannelComboBox.getSelectedItem()) 	& ttc3CheckBox.isSelected())
				|| ((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc4MultimeterChannelComboBox.getSelectedItem()) 	& ttc4CheckBox.isSelected())
				|| ((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc5MultimeterChannelComboBox.getSelectedItem()) 	& ttc5CheckBox.isSelected())
				|| ((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc6MultimeterChannelComboBox.getSelectedItem()) 	& ttc6CheckBox.isSelected())
				|| ((ttc1MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem()) 	& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº1 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((ttc2MultimeterChannelComboBox.getSelectedItem()).equals(ttc3MultimeterChannelComboBox.getSelectedItem())	& ttc3CheckBox.isSelected())
				|| ((ttc2MultimeterChannelComboBox.getSelectedItem()).equals(ttc4MultimeterChannelComboBox.getSelectedItem())	& ttc4CheckBox.isSelected())
				|| ((ttc2MultimeterChannelComboBox.getSelectedItem()).equals(ttc5MultimeterChannelComboBox.getSelectedItem())	& ttc5CheckBox.isSelected())
				|| ((ttc2MultimeterChannelComboBox.getSelectedItem()).equals(ttc6MultimeterChannelComboBox.getSelectedItem())	& ttc6CheckBox.isSelected())
				|| ((ttc2MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem())	& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº2 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((ttc3MultimeterChannelComboBox.getSelectedItem()).equals(ttc4MultimeterChannelComboBox.getSelectedItem())	& ttc4CheckBox.isSelected())
				|| ((ttc3MultimeterChannelComboBox.getSelectedItem()).equals(ttc5MultimeterChannelComboBox.getSelectedItem())	& ttc5CheckBox.isSelected())
				|| ((ttc3MultimeterChannelComboBox.getSelectedItem()).equals(ttc6MultimeterChannelComboBox.getSelectedItem())	& ttc6CheckBox.isSelected())
				|| ((ttc3MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem())	& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº3 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((ttc4MultimeterChannelComboBox.getSelectedItem()).equals(ttc5MultimeterChannelComboBox.getSelectedItem())	& ttc5CheckBox.isSelected())
				|| ((ttc4MultimeterChannelComboBox.getSelectedItem()).equals(ttc6MultimeterChannelComboBox.getSelectedItem())& ttc6CheckBox.isSelected())
				|| ((ttc4MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem())& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº4 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((ttc5MultimeterChannelComboBox.getSelectedItem()).equals(ttc6MultimeterChannelComboBox.getSelectedItem())& ttc6CheckBox.isSelected())
				|| ((ttc5MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem())& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº5 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				((ttc6MultimeterChannelComboBox.getSelectedItem()).equals(ttc7MultimeterChannelComboBox.getSelectedItem())& ttc7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº6 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		return true;
	}
	public boolean validateTemperatureStabilizationCriteriumData(){
		return true;
	}
	public boolean validateDescriptionData(){
		if (programNameTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para el programa a crear.");
			return false;
		}
		if (programAuthorTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para author del programa a crear.");
			return false;
		}
		if (programDescriptionTextArea.getText().equals("")){
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

		  TTCToCalibrateData[] deviceToMeasureData = new TTCToCalibrateData[N_TTCs_TO_MEASURE];
		  System.out.println("---------------------------> "+(Integer)ttc1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[0] = new TTCToCalibrateData(ttc1CheckBox.isSelected(),ttc1RefTextField.getText(),(Integer)ttc1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[1] = new TTCToCalibrateData(ttc2CheckBox.isSelected(),ttc2RefTextField.getText(),(Integer)ttc2MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[2] = new TTCToCalibrateData(ttc3CheckBox.isSelected(),ttc3RefTextField.getText(),(Integer)ttc3MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[3] = new TTCToCalibrateData(ttc4CheckBox.isSelected(),ttc4RefTextField.getText(),(Integer)ttc4MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[4] = new TTCToCalibrateData(ttc5CheckBox.isSelected(),ttc5RefTextField.getText(),(Integer)ttc5MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[5] = new TTCToCalibrateData(ttc6CheckBox.isSelected(),ttc6RefTextField.getText(),(Integer)ttc6MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[6] = new TTCToCalibrateData(ttc7CheckBox.isSelected(),ttc7RefTextField.getText(),(Integer)ttc7MultimeterChannelComboBox.getSelectedItem());


		  TTCsToCalibrateData measuresConfigurationData = new TTCsToCalibrateData
		  (
				deviceToMeasureData
		  );


		  Object[] temperaturesList = temperatureProfileList.getSelectedObjects();
		  int[] temperatures = new int[temperaturesList.length];
		  for (int i=0;i<temperaturesList.length;i++){
			  temperatures[i] = Integer.parseInt((String)temperaturesList[i]);
		  }
		  TemperatureProfileData temperatureProfileData = new TemperatureProfileData("TemperatureProfileData_1",temperatures);
		  TTCSetUpDescriptionData generalProgramData = new TTCSetUpDescriptionData(this.programNameTextField.getText(),this.programAuthorTextField.getText(),this.programDescriptionTextArea.getText());

		  int temperatureStabilitzationMethod = 0; //By Default St Dev
		  if (temperatureStabilitzationByTimejRadioButton.isSelected()) temperatureStabilitzationMethod = 1;
		  temperatureStabilizationCriteriumData temperatureStabilizationCriteriumProgramData = new temperatureStabilizationCriteriumData(Integer.parseInt((String)firstTemperatureStepStabiltizationTimeComboBox.getSelectedItem()),Double.parseDouble((String)this.maxAdmissibleSteadyStateErrorComboBox.getSelectedItem()),Double.parseDouble((String)this.stDevComboBox.getSelectedItem()),Integer.parseInt((String)this.measurementWindowComboBox.getSelectedItem()),Long.parseLong((String)this.samplingPeriodeTextField.getText()),Integer.parseInt((String)this.numberOfSuccessiveWindowsUnderStDevComboBox.getSelectedItem()),temperatureStabilitzationMethod, Integer.parseInt((String)this.temperatureStabilitzationTimeComboBox.getSelectedItem()));

		  TTCSetUpData thermalCalibrationProgramData = new TTCSetUpData
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
		if (programNameTextField == null) {
			programNameTextField = new JTextField();
			programNameTextField.setBounds(new Rectangle(135, 90, 496, 31));
			programNameTextField.setHorizontalAlignment(JTextField.LEFT);
		}
		return programNameTextField;
	}

	/**
	 * This method initializes programAuthorTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getProgramAuthorTextField() {
		if (programAuthorTextField == null) {
			programAuthorTextField = new JTextField();
			programAuthorTextField.setBounds(new Rectangle(135, 135, 496, 31));
			programAuthorTextField.setHorizontalAlignment(JTextField.LEFT);
		}
		return programAuthorTextField;
	}

	/**
	 * This method initializes programDescriptionTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getProgramDescriptionTextArea() {
		if (programDescriptionTextArea == null) {
			programDescriptionTextArea = new JTextArea();
			programDescriptionTextArea.setBounds(new Rectangle(135, 180, 496, 76));
			programDescriptionTextArea.setLineWrap(false);
			programDescriptionTextArea.setRows(10);
		}
		return programDescriptionTextArea;
	}

	/**
	 * This method initializes dev1CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC1CheckBox() {
		if (ttc1CheckBox == null) {
			ttc1CheckBox = new JCheckBox();
			ttc1CheckBox.setBounds(new Rectangle(135, 90, 61, 16));
			ttc1CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc1CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc1RefTextField.setEnabled(true);
		        		ttc1MultimeterChannelComboBox.setEnabled(true);
		        		ttc2CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc1RefTextField.setEnabled(false);
		        		ttc1MultimeterChannelComboBox.setEnabled(false);
		        		ttc2CheckBox.setSelected(false);
		        		ttc2CheckBox.setEnabled(false);
		        		ttc2RefTextField.setEnabled(false);
		        		ttc2MultimeterChannelComboBox.setEnabled(false);
		        		ttc3CheckBox.setSelected(false);
		        		ttc3RefTextField.setEnabled(false);
		        		ttc3MultimeterChannelComboBox.setEnabled(false);
		        		ttc3CheckBox.setEnabled(false);
		        		ttc4CheckBox.setSelected(false);
		        		ttc4RefTextField.setEnabled(false);
		        		ttc4MultimeterChannelComboBox.setEnabled(false);
		        		ttc4CheckBox.setEnabled(false);
		        		ttc5CheckBox.setSelected(false);
		        		ttc5RefTextField.setEnabled(false);
		        		ttc5MultimeterChannelComboBox.setEnabled(false);
		        		ttc5CheckBox.setEnabled(false);
		        		ttc6CheckBox.setSelected(false);
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc1CheckBox;
	}
	/**
	 * This method initializes dev2CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC2CheckBox() {
		if (ttc2CheckBox == null) {
			ttc2CheckBox = new JCheckBox();
			ttc2CheckBox.setBounds(new Rectangle(195, 90, 61, 16));
			ttc2CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc2CheckBox.setVisible(true);
			ttc2CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc2RefTextField.setEnabled(true);
		        		ttc2MultimeterChannelComboBox.setEnabled(true);
		        		ttc3CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc2RefTextField.setEnabled(false);
		        		ttc2MultimeterChannelComboBox.setEnabled(false);
		        		ttc3CheckBox.setSelected(false);
		        		ttc3RefTextField.setEnabled(false);
		        		ttc3MultimeterChannelComboBox.setEnabled(false);
		        		ttc3CheckBox.setEnabled(false);
		        		ttc4CheckBox.setSelected(false);
		        		ttc4RefTextField.setEnabled(false);
		        		ttc4MultimeterChannelComboBox.setEnabled(false);
		        		ttc4CheckBox.setEnabled(false);
		        		ttc5CheckBox.setSelected(false);
		        		ttc5RefTextField.setEnabled(false);
		        		ttc5MultimeterChannelComboBox.setEnabled(false);
		        		ttc5CheckBox.setEnabled(false);
		        		ttc6CheckBox.setSelected(false);
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc2CheckBox;
	}
	/**
	 * This method initializes dev3CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC3CheckBox() {
		if (ttc3CheckBox == null) {
			ttc3CheckBox = new JCheckBox();
			ttc3CheckBox.setBounds(new Rectangle(255, 90, 61, 16));
			ttc3CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc3CheckBox.setEnabled(false);
			ttc3CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc3RefTextField.setEnabled(true);
		        		ttc3MultimeterChannelComboBox.setEnabled(true);
		        		ttc3CheckBox.setEnabled(true);
		        		ttc4CheckBox.setEnabled(true);

		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc3RefTextField.setEnabled(false);
		        		ttc3MultimeterChannelComboBox.setEnabled(false);
		        		ttc4CheckBox.setSelected(false);
		        		ttc4RefTextField.setEnabled(false);
		        		ttc4MultimeterChannelComboBox.setEnabled(false);
		        		ttc4CheckBox.setEnabled(false);
		        		ttc5CheckBox.setSelected(false);
		        		ttc5RefTextField.setEnabled(false);
		        		ttc5MultimeterChannelComboBox.setEnabled(false);
		        		ttc5CheckBox.setEnabled(false);
		        		ttc6CheckBox.setSelected(false);
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc3CheckBox;
	}
	/**
	 * This method initializes dev4CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC4CheckBox() {
		if (ttc4CheckBox == null) {
			ttc4CheckBox = new JCheckBox();
			ttc4CheckBox.setBounds(new Rectangle(315, 90, 61, 16));
			ttc4CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc4CheckBox.setEnabled(false);
			ttc4CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc4RefTextField.setEnabled(true);
		        		ttc4MultimeterChannelComboBox.setEnabled(true);
		        		ttc4CheckBox.setEnabled(true);
		        		ttc5CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc4RefTextField.setEnabled(false);
		        		ttc4MultimeterChannelComboBox.setEnabled(false);
		        		ttc5CheckBox.setSelected(false);
		        		ttc5RefTextField.setEnabled(false);
		        		ttc5MultimeterChannelComboBox.setEnabled(false);
		        		ttc5CheckBox.setEnabled(false);
		        		ttc6CheckBox.setSelected(false);
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc4CheckBox;
	}
	/**
	 * This method initializes dev5CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC5CheckBox() {
		if (ttc5CheckBox == null) {
			ttc5CheckBox = new JCheckBox();
			ttc5CheckBox.setBounds(new Rectangle(375, 90, 61, 16));
			ttc5CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc5CheckBox.setEnabled(false);
			ttc5CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc5RefTextField.setEnabled(true);
		        		ttc5MultimeterChannelComboBox.setEnabled(true);
		        		ttc5CheckBox.setEnabled(true);
		        		ttc6CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc5RefTextField.setEnabled(false);
		        		ttc5MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setSelected(false);
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc6CheckBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc5CheckBox;
	}
	/**
	 * This method initializes dev6CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC6CheckBox() {
		if (ttc6CheckBox == null) {
			ttc6CheckBox = new JCheckBox();
			ttc6CheckBox.setBounds(new Rectangle(435, 90, 61, 16));
			ttc6CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc6CheckBox.setEnabled(false);
			ttc6CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc6RefTextField.setEnabled(true);
		        		ttc6MultimeterChannelComboBox.setEnabled(true);
		        		ttc6CheckBox.setEnabled(true);
		        		ttc7CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc6RefTextField.setEnabled(false);
		        		ttc6MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setSelected(false);
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        		ttc7CheckBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc6CheckBox;
	}
	/**
	 * This method initializes dev7CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getTTC7CheckBox() {
		if (ttc7CheckBox == null) {
			ttc7CheckBox = new JCheckBox();
			ttc7CheckBox.setBounds(new Rectangle(495, 90, 61, 17));
			ttc7CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			ttc7CheckBox.setEnabled(false);
			ttc7CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		ttc7RefTextField.setEnabled(true);
		        		ttc7MultimeterChannelComboBox.setEnabled(true);
		        		ttc7CheckBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		ttc7RefTextField.setEnabled(false);
		        		ttc7MultimeterChannelComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return ttc7CheckBox;
	}

	/**
	 * This method initializes dev2NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC1RefTextField() {
		if (ttc1RefTextField == null) {
			ttc1RefTextField = new JTextField();
			ttc1RefTextField.setEnabled(true);
			ttc1RefTextField.setBounds(new Rectangle(135, 150, 61, 16));
		}
		return ttc1RefTextField;
	}
	/**
	 * This method initializes dev1NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC2RefTextField() {
		if (ttc2RefTextField == null) {
			ttc2RefTextField = new JTextField();
			ttc2RefTextField.setEnabled(false);
			ttc2RefTextField.setBounds(new Rectangle(195, 150, 61, 16));
		}
		return ttc2RefTextField;
	}
	/**
	 * This method initializes dev3NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC3RefTextField() {
		if (ttc3RefTextField == null) {
			ttc3RefTextField = new JTextField();
			ttc3RefTextField.setBounds(new Rectangle(255, 150, 61, 16));
			ttc3RefTextField.setEnabled(false);
		}
		return ttc3RefTextField;
	}

	/**
	 * This method initializes dev4NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC4RefTextField() {
		if (ttc4RefTextField == null) {
			ttc4RefTextField = new JTextField();
			ttc4RefTextField.setBounds(new Rectangle(315, 150, 61, 16));
			ttc4RefTextField.setEnabled(false);
		}
		return ttc4RefTextField;
	}

	/**
	 * This method initializes dev5NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC5RefTextField() {
		if (ttc5RefTextField == null) {
			ttc5RefTextField = new JTextField();
			ttc5RefTextField.setEnabled(false);
			ttc5RefTextField.setBounds(new Rectangle(375, 150, 61, 16));
		}
		return ttc5RefTextField;
	}

	/**
	 * This method initializes dev6RefTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC6RefTextField() {
		if (ttc6RefTextField == null) {
			ttc6RefTextField = new JTextField();
			ttc6RefTextField.setBounds(new Rectangle(435, 150, 61, 16));
			ttc6RefTextField.setEnabled(false);
		}
		return ttc6RefTextField;
	}
	/**
	 * This method initializes dev5NameTextField11
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTTC7RefTextField() {
		if (ttc7RefTextField == null) {
			ttc7RefTextField = new JTextField();
			ttc7RefTextField.setBounds(new Rectangle(495, 150, 61, 16));
			ttc7RefTextField.setEnabled(false);
		}
		return ttc7RefTextField;
	}

	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC1MultimeterChannelComboBox() throws Exception{
		if (ttc1MultimeterChannelComboBox == null) {
			ttc1MultimeterChannelComboBox = new JComboBox();
			ttc1MultimeterChannelComboBox.setEnabled(false);
			ttc1MultimeterChannelComboBox.setBounds(new Rectangle(135, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc1MultimeterChannelComboBox.addItem(i);
			}
			ttc1MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc1MultimeterChannel = (Integer)ttc1MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc1MultimeterChannel){
		           	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
		         	  JOptionPane.showMessageDialog(null,"TTC1 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
		           	  ttc1MultimeterChannelComboBox.setSelectedItem("1");
		        	}
		        }
		     };
		}
	     ttc1MultimeterChannelComboBox.addItemListener(ttc1MultimeterChannelComboBoxItemListener);

		return ttc1MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC2MultimeterChannelComboBox() {
		if (ttc2MultimeterChannelComboBox == null) {
			ttc2MultimeterChannelComboBox = new JComboBox();
			ttc2MultimeterChannelComboBox.setEnabled(false);
			ttc2MultimeterChannelComboBox.setBounds(new Rectangle(195, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc2MultimeterChannelComboBox.addItem(i);
			}
			ttc2MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc2MultimeterChannel = (Integer)ttc2MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc2MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC2 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc2MultimeterChannelComboBox.setSelectedItem("2");
		        	}
		        }
		     };
		     ttc2MultimeterChannelComboBox.addItemListener(ttc2MultimeterChannelComboBoxItemListener);
		}
		return ttc2MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC3MultimeterChannelComboBox() {
		if (ttc3MultimeterChannelComboBox == null) {
			ttc3MultimeterChannelComboBox = new JComboBox();
			ttc3MultimeterChannelComboBox.setEnabled(false);
			ttc3MultimeterChannelComboBox.setBounds(new Rectangle(255, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc3MultimeterChannelComboBox.addItem(i);
			}
			ttc3MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc3MultimeterChannel = (Integer)ttc3MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc3MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC3 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc3MultimeterChannelComboBox.setSelectedItem("3");
		        	}
		        }
		     };
		     ttc3MultimeterChannelComboBox.addItemListener(ttc3MultimeterChannelComboBoxItemListener);
		}
		return ttc3MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC4MultimeterChannelComboBox() {
		if (ttc4MultimeterChannelComboBox == null) {
			ttc4MultimeterChannelComboBox = new JComboBox();
			ttc4MultimeterChannelComboBox.setEnabled(false);
			ttc4MultimeterChannelComboBox.setBounds(new Rectangle(315, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc4MultimeterChannelComboBox.addItem(i);
			}
			ttc4MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc4MultimeterChannel = (Integer)ttc4MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc4MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC4 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc4MultimeterChannelComboBox.setSelectedItem("5");
		        	}
		        }
		     };
		     ttc4MultimeterChannelComboBox.addItemListener(ttc4MultimeterChannelComboBoxItemListener);

		}
		return ttc4MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC5MultimeterChannelComboBox() {
		if (ttc5MultimeterChannelComboBox == null) {
			ttc5MultimeterChannelComboBox = new JComboBox();
			ttc5MultimeterChannelComboBox.setEnabled(false);
			ttc5MultimeterChannelComboBox.setBounds(new Rectangle(375, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc5MultimeterChannelComboBox.addItem(i);
			}
			ttc5MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc5MultimeterChannel = (Integer)ttc5MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc5MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC5 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc5MultimeterChannelComboBox.setSelectedItem("6");
		        	}
		        }
		     };
		     ttc5MultimeterChannelComboBox.addItemListener(ttc5MultimeterChannelComboBoxItemListener);
		}
		return ttc5MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC6MultimeterChannelComboBox() {
		if (ttc6MultimeterChannelComboBox == null) {
			ttc6MultimeterChannelComboBox = new JComboBox();
			ttc6MultimeterChannelComboBox.setEnabled(false);
			ttc6MultimeterChannelComboBox.setBounds(new Rectangle(435, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc6MultimeterChannelComboBox.addItem(i);
			}
			ttc6MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc6MultimeterChannel = (Integer)ttc6MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc6MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC6 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc6MultimeterChannelComboBox.setSelectedItem("7");
		        	}
		        }
		     };
		     ttc6MultimeterChannelComboBox.addItemListener(ttc6MultimeterChannelComboBoxItemListener);

		}
		return ttc6MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTTC7MultimeterChannelComboBox() {
		if (ttc7MultimeterChannelComboBox == null) {
			ttc7MultimeterChannelComboBox = new JComboBox();
			ttc7MultimeterChannelComboBox.setEnabled(false);
			ttc7MultimeterChannelComboBox.setBounds(new Rectangle(495, 180, 61, 16));
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
				if (!(temperatureSensorData.getChannel()==i)) ttc7MultimeterChannelComboBox.addItem(i);
			}
			ttc7MultimeterChannelComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int ttc7MultimeterChannel = (Integer)ttc7MultimeterChannelComboBox.getSelectedItem();
		        	if (temperatureSensorData.getChannel()==ttc7MultimeterChannel){
		            	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensorData.getChannel());
			         	  JOptionPane.showMessageDialog(null,"TTC7 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensorData.getChannel());
			           	  ttc7MultimeterChannelComboBox.setSelectedItem("8");
		        	}
		        }
		     };
		     ttc7MultimeterChannelComboBox.addItemListener(ttc7MultimeterChannelComboBoxItemListener);
		}
		return ttc7MultimeterChannelComboBox;
	}
	/**
	 * This method initializes standardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getMaximunAdmissibleSteadyStateErrorComboBox() {
		if (maxAdmissibleSteadyStateErrorComboBox == null) {
			maxAdmissibleSteadyStateErrorComboBox = new JComboBox();
			maxAdmissibleSteadyStateErrorComboBox.setBounds(new Rectangle(270, 240, 106, 31));
			for (double i=1;i<=500;i=i+1){
				maxAdmissibleSteadyStateErrorComboBox.addItem (Double.toString(i/100));
			}
		}
		return maxAdmissibleSteadyStateErrorComboBox;
	}
	/**
	 * This method initializes standardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getStandardDeviationComboBox() {
		if (stDevComboBox == null) {
			stDevComboBox = new JComboBox();
			stDevComboBox.setBounds(new Rectangle(270, 120, 106, 31));
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
			measurementWindowComboBox.setBounds(new Rectangle(270, 150, 106, 31));
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
			samplingPeriodeTextField.setBounds(new Rectangle(270, 180, 106, 31));
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
			numberOfSuccessiveWindowsUnderStDevComboBox.setBounds(new Rectangle(270, 210, 106, 31));
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
			temperatureStabilitzationByStDevjRadioButton.setBounds(new Rectangle(270, 75, 166, 31));
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
		        		//maxAdmissibleTempErrorComboBox.enable(true);
		        		temperatureStabilitzationTimeComboBox.enable(false);
		        		stDevComboBox.setForeground(Color.BLACK);
		        		measurementWindowComboBox.setForeground(Color.BLACK);
		        		samplingPeriodeTextField.setForeground(Color.BLACK);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.setForeground(Color.BLACK);
		        		maxAdmissibleSteadyStateErrorComboBox.setForeground(Color.BLACK);
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
			temperatureStabilitzationByTimejRadioButton.setBounds(new Rectangle(435, 75, 77, 31));
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
		        		//maxAdmissibleTempErrorComboBox.enable(false);
		        		temperatureStabilitzationTimeComboBox.enable(true);
		        		stDevComboBox.setForeground(Color.LIGHT_GRAY);
		        		measurementWindowComboBox.setForeground(Color.LIGHT_GRAY);
		        		samplingPeriodeTextField.setForeground(Color.LIGHT_GRAY);
		        		numberOfSuccessiveWindowsUnderStDevComboBox.setForeground(Color.LIGHT_GRAY);
		        		maxAdmissibleSteadyStateErrorComboBox.setForeground(Color.LIGHT_GRAY);
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
			temperatureStabilitzationTimeComboBox.setBounds(new Rectangle(270, 285, 106, 31));
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
			warningTextField.setBounds(new Rectangle(135, 300, 421, 61));
			warningTextField.setFont(new Font("Dialog", Font.BOLD, 12));
			warningTextField.setHorizontalAlignment(JTextField.CENTER);
			warningTextField.setBackground(Color.red);
			warningTextField.setText("WARNING: CHANNEL DEVICES MUST DIFFERENT FROM TEMP SENSOR CHANNEL.");
			warningTextField.setVisible(false);
		}
		return warningTextField;
	}

	/**
	 * This method initializes temperatureSensorChannelIndicatorTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTemperatureSensorChannelIndicatorTextField() {
		if (temperatureSensorChannelIndicatorTextField == null) {
			temperatureSensorChannelIndicatorTextField = new JTextField();
			temperatureSensorChannelIndicatorTextField.setBounds(new Rectangle(135, 225, 421, 61));
			temperatureSensorChannelIndicatorTextField.setBackground(new Color(238, 238, 238));
			temperatureSensorChannelIndicatorTextField.setHorizontalAlignment(JTextField.CENTER);
			temperatureSensorChannelIndicatorTextField.setText(" WARNING: Temperature Sensor in Multimeter Channel Number "+Integer.toString(temperatureSensorData.getChannel()));
		}
		return temperatureSensorChannelIndicatorTextField;
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
				    	TTCSetUpData _program = null;
						_program = new TTCSetUpData(programFilePath);
						System.out.println(_program.toString());
				    	String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
				    	String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
						TTCCalibrationViewerFrame thisClass = new TTCCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,programFilePath,_program,1,null);
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
