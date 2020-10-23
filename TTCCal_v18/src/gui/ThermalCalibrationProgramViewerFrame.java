package gui;

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

import Data.AdvanceProgramData;
import Data.GeneralProgramData;
import Data.InstrumentData;
import Data.InstrumentsData;
import Data.MeasuresConfigurationData;
import Data.DeviceToMeasureData;
import Data.TemperatureProfileData;
import Data.TemperatureSensorData;
import Data.ThermalCalibrationProgramData;
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

public class ThermalCalibrationProgramViewerFrame extends JFrame implements ActionListener{

	private static final int N_DEVICES_TO_MEASURE = 7;
	private static final int N_MULTIMETER_CHANNELS = 10;
	private static final int MAX_TEMP_OVEN = 500;
	private static final int N_TEMPERATURE_VALUES = 500;
	private static final int MIN_TEMP_OVEN = 0;
	private static final double STANDARD_DEVIATION_MAX_VALUE = 5.00;
	private static final double STANDARD_DEVIATION_MIN_VALUE = 0.01;
	private static final double STANDARD_DEVIATION_INCREMENT = 0.01;
	private static final int N_MEASURES_PER_CLICLE_MIN_VALUE = 30;
	private static final int N_MEASURES_PER_CLICLE_MAX_VALUE = 240;
	private static final int N_MEASURES_PER_CLICLE_INCREMENT = 1;
	private static final long TIME_BETWEEN_SUCCESIVES_MEASURES_MIN_VALUE_MILLISECONDS = 300;
	private static final long TIME_BETWEEN_SUCCESIVES_MEASURES_MAX_VALUE_MILLISECONDS = 10000;
	private static final int TIME_BETWEEN_SUCCESIVES_MEASURES_MAX_VALUE_MILLISECONDS_INCREMENT = 100;
	private static final int N_PASSES_BELOW_STANDARD_DEVIATION_MIN_VALUE = 1;
	private static final int N_PASSES_BELOW_STANDARD_DEVIATION_INCREMENT = 1;
	private static final int N_PASSES_BELOW_STANDARD_DEVIATION_MAX_VALUE = 10;
	private static final int TEMPERATURE_STABILITZATION_TIME_MINUTES_MIN_VALUE = 5;
	private static final int TEMPERATURE_STABILITZATION_TIME_MINUTES_INCREMENT = 1;
	private static final int TEMPERATURE_STABILITZATION_TIME_MINUTES_MAX_VALUE = 120;
	private static final int TEMP_OVEN_INCREMENT = 1;
	private static final int MAX_TIME_AT_CONSTANT_TEMP_HOURS = 200;
	private static final int TIME_AT_CONSTANT_TEMP_INCREMENT_HOURS = 1;
	private static final int MIN_TIME_AT_CONSTANT_TEMP_HOURS = 1;
	private static final int MAX_N_MEASURES_AT_CONSTANT_TEMP = 1000;
	private static final int MIN_N_MEASURES_AT_CONSTANT_TEMP = 1;
	private static final int N_MEASURES_AT_CONSTANT_TEMP_INCREMENT = 1;


	JFileChooser fileChooser = null;
	ThermalCalibrationProgramData program = null;  //  @jve:decl-index=0:
	private MainController mainController = null;

	private static final long serialVersionUID = 1L;


	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel temperatureProfilePanel = null;
	private JPanel devicesToCalibratePanel = null;
	private JPanel advanceDataPanel = null;
	private JPanel generalPanel = null;
	private JLabel temperatureProfileConfigurationLabel = null;
	private List temperatureProfileList = null;
	private JPanel saveCancelPanel = null;
	private JButton saveButton = null;
	private JButton saveAsButton = null;
	private JButton mark0To100TemperatureProfileButton = null;
	private JButton mark0To125TemperatureProfileButton = null;
	private JButton mark0To150TemperatureProfileButton = null;
	private JButton mark0To175TemperatureProfileButton = null;
	private JButton mark0To200TemperatureProfileButton = null;
	private JButton mark0To225TemperatureProfileButton = null;
	private JButton mark0To250TemperatureProfileButton = null;
	private JLabel minTempLabel = null;
	private JComboBox minTempComboBox = null;
	private JLabel maxTempLabel = null;
	private JTextField maxTempTextField = null;
	private JLabel nStepsLabel = null;
	private JComboBox nStepsComboBox = null;
	private JLabel stepValueLabel = null;
	private JComboBox stepValueComboBox = null;
	private JButton clearTemperatureProfileButton = null;
	private JLabel devicesToCalibrateConfigurationLabel = null;
	private JLabel temperatureProfileConfigurationLabel1 = null;
	private JLabel programNameLabel = null;
	private JLabel programAuthorLabel = null;
	private JLabel programDescriptionLabel = null;
	private JTextField programNameTextField = null;
	private JTextField programAuthorTextField = null;
	private JTextArea programDescriptionTextArea = null;
	private JLabel advanceConfigurationLabel = null;
	private JLabel standardDeviationLabel = null;
	private JLabel nMeasuresPerCicleLabel = null;
	private JLabel timeBetweenSucesivesMeasuresLabel = null;
	private JLabel dev1Label = null;
	private JLabel dev2Label = null;
	private JLabel dev3Label = null;
	private JLabel devTypeLabel = null;
	private JLabel devMultimeterChannelLabel = null;
	private JLabel devRefLabel = null;
	private JLabel dev6Label = null;
	private JLabel dev7Label = null;
	private JLabel dev4Label = null;
	private JLabel dev5Label = null;
	private JTextField dev2RefTextField = null;
	private JTextField dev1RefTextField = null;
	private JTextField dev3RefTextField = null;
	private JTextField dev4RefTextField = null;
	private JTextField dev5RefTextField = null;
	private JCheckBox dev1CheckBox = null;
	private JTextField dev7RefTextField = null;
	private JCheckBox dev2CheckBox = null;
	private JCheckBox dev3CheckBox = null;
	private JTextField dev6RefTextField = null;
	private JCheckBox dev4CheckBox = null;
	private JCheckBox dev5CheckBox = null;
	private JCheckBox dev6CheckBox = null;
	private JCheckBox dev7CheckBox = null;
	private JComboBox dev1MultimeterChannelComboBox = null;
	private JComboBox dev2MultimeterChannelComboBox = null;
	private JComboBox dev3MultimeterChannelComboBox = null;
	private JComboBox dev4MultimeterChannelComboBox = null;
	private JComboBox dev5MultimeterChannelComboBox = null;
	private JComboBox dev6MultimeterChannelComboBox = null;
	private JComboBox dev7MultimeterChannelComboBox = null;

	private JComboBox dev1TypeComboBox = null;
	private JComboBox dev2TypeComboBox = null;
	private JComboBox dev3TypeComboBox = null;
	private JComboBox dev4TypeComboBox = null;
	private JComboBox dev5TypeComboBox = null;
	private JComboBox dev6TypeComboBox = null;
	private JComboBox dev7TypeComboBox = null;


	private JComboBox standardDeviationComboBox = null;
	private JComboBox nMeasuresPerCicleComboBox = null;
	private JComboBox timeBetweenSuccesivesMeasuresComboBox = null;

	private ItemListener temperatureProfileListItemListener = null;  //  @jve:decl-index=0:
	private ItemListener minTempComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener nStepsComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener stepValueComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener temperatureStabilitzationByTimejRadioButtonItemListener = null;  //  @jve:decl-index=0:
	private ItemListener temperatureStabilitzationByStDevjRadioButtonItemListener = null;  //  @jve:decl-index=0:
	private ItemListener tempProfileTypejComboBoxItemListener = null;  //  @jve:decl-index=0:

	private JLabel programTypeLabel = null;
	private JComboBox programTypeComboBox = null;
	private JComboBox nPassesBelowStandardDeviationComboBox = null;
	private JLabel nPassesBelowStandardDeviationjLabel = null;
	private JRadioButton temperatureStabilitzationByStDevjRadioButton = null;
	private JRadioButton temperatureStabilitzationByTimejRadioButton = null;
	private JLabel temperatureStabilitzationMethodjLabel = null;
	private JLabel temperatureStabiltizationTimejLabel = null;
	private JComboBox temperatureStabilitzationTimejComboBox = null;
	private JLabel tempProfileTypejLabel = null;
	private JComboBox tempProfileTypejComboBox = null;
	private JLabel constantTemperaturejLabel = null;
	private JComboBox constantTemperaturejComboBox = null;
	private JLabel totalTimeAtConstantTempjLabel = null;
	private JComboBox totalTimeAtConstantTempjComboBox = null;
	private JComboBox nMeasuresAtConstantTempjComboBox = null;
	private JLabel nMeasuresAtConstantTempjLabel = null;
	private JLabel measuresAtConstantTempEveryjLabel = null;
	private JTextField measuresAtConstantTempEveryjTextField = null;
	/**
	 * This method initializes jTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(0, 0, 571, 451));
			jTabbedPane.addTab(getTemperatureProfilePanel().getName(), null, getTemperatureProfilePanel(), null);
			jTabbedPane.addTab(getDevicesToCalibratePanel().getName(), null, getDevicesToCalibratePanel(), null);
			jTabbedPane.addTab(getAdvanceDataPanel().getName(), null, getAdvanceDataPanel(), null);
			jTabbedPane.addTab(getGeneralPanel().getName(), null, getGeneralPanel(), null);
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
			measuresAtConstantTempEveryjLabel = new JLabel();
			measuresAtConstantTempEveryjLabel.setBounds(new Rectangle(160, 385, 111, 21));
			measuresAtConstantTempEveryjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			measuresAtConstantTempEveryjLabel.setText("Measures Freq (h)");
			nMeasuresAtConstantTempjLabel = new JLabel();
			nMeasuresAtConstantTempjLabel.setBounds(new Rectangle(160, 355, 111, 21));
			nMeasuresAtConstantTempjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nMeasuresAtConstantTempjLabel.setText("N Measures");
			totalTimeAtConstantTempjLabel = new JLabel();
			totalTimeAtConstantTempjLabel.setBounds(new Rectangle(160, 325, 111, 21));
			totalTimeAtConstantTempjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			totalTimeAtConstantTempjLabel.setText("TotalTime (h)");
			constantTemperaturejLabel = new JLabel();
			constantTemperaturejLabel.setBounds(new Rectangle(160, 295, 111, 21));
			constantTemperaturejLabel.setHorizontalAlignment(SwingConstants.CENTER);
			constantTemperaturejLabel.setText("Temperature");
			tempProfileTypejLabel = new JLabel();
			tempProfileTypejLabel.setBounds(new Rectangle(30, 75, 86, 21));
			tempProfileTypejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			tempProfileTypejLabel.setText("Profile Type");
			stepValueLabel = new JLabel();
			stepValueLabel.setBounds(new Rectangle(160, 150, 111, 21));
			stepValueLabel.setText("Step Value");
			stepValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nStepsLabel = new JLabel();
			nStepsLabel.setBounds(new Rectangle(160, 125, 111, 21));
			nStepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nStepsLabel.setText("N Steps");
			maxTempLabel = new JLabel();
			maxTempLabel.setBounds(new Rectangle(160, 100, 111, 21));
			maxTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			maxTempLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			maxTempLabel.setText("Max Temp");
			minTempLabel = new JLabel();
			minTempLabel.setBounds(new Rectangle(160, 75, 111, 21));
			minTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			minTempLabel.setText("Min Temp");
			temperatureProfileConfigurationLabel = new JLabel();
			temperatureProfileConfigurationLabel.setBounds(new Rectangle(30, 30, 481, 31));
			temperatureProfileConfigurationLabel.setBackground(new Color(204, 204, 255));
			temperatureProfileConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel.setText("TEMPERATURE PROFILE CONFIGURATION");
			temperatureProfilePanel = new JPanel();
			temperatureProfilePanel.setName("TemperatureProfile");
			temperatureProfilePanel.setLayout(null);
			temperatureProfilePanel.add(temperatureProfileConfigurationLabel, null);
			temperatureProfilePanel.add(getTemperatureProfileList(), null);
			temperatureProfilePanel.add(get0To100TempProfileJButton(), null);
			temperatureProfilePanel.add(getMark0To125TemperatureProfileButton(), null);
			temperatureProfilePanel.add(getMark0To150TemperatureProfileButton(), null);
			temperatureProfilePanel.add(getMark0To175TemperatureProfileButton(), null);
			temperatureProfilePanel.add(getMark0To200TemperatureProfileButton(), null);
			temperatureProfilePanel.add(getMark0To225TemperatureProfileButton(), null);
			temperatureProfilePanel.add(getMark0To250TemperatureProfileButton(), null);
			temperatureProfilePanel.add(minTempLabel, null);
			temperatureProfilePanel.add(getMinTempComboBox(), null);
			temperatureProfilePanel.add(maxTempLabel, null);
			temperatureProfilePanel.add(getMaxTempTextField(), null);
			temperatureProfilePanel.add(nStepsLabel, null);
			temperatureProfilePanel.add(getNStepsComboBox(), null);
			temperatureProfilePanel.add(stepValueLabel, null);
			temperatureProfilePanel.add(getStepValueComboBox(), null);
			temperatureProfilePanel.add(getClearTemperatureProfileButton(), null);
			temperatureProfilePanel.add(tempProfileTypejLabel, null);
			temperatureProfilePanel.add(getTempProfileTypejComboBox(), null);
			temperatureProfilePanel.add(constantTemperaturejLabel, null);
			temperatureProfilePanel.add(getConstantTemperaturejComboBox(), null);
			temperatureProfilePanel.add(totalTimeAtConstantTempjLabel, null);
			temperatureProfilePanel.add(getTotalTimeAtConstantTempjComboBox(), null);
			temperatureProfilePanel.add(getNMeasuresAtConstantTempjComboBox(), null);
			temperatureProfilePanel.add(nMeasuresAtConstantTempjLabel, null);
			temperatureProfilePanel.add(measuresAtConstantTempEveryjLabel, null);
			temperatureProfilePanel.add(getMeasuresAtConstantTempEveryjTextField(), null);
		}
		return temperatureProfilePanel;
	}

	/**
	 * This method initializes devicesToCalibratePanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getDevicesToCalibratePanel() {
		if (devicesToCalibratePanel == null) {
			dev5Label = new JLabel();
			dev5Label.setBounds(new Rectangle(330, 120, 61, 16));
			dev5Label.setText("Dev5");
			dev5Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev4Label = new JLabel();
			dev4Label.setBounds(new Rectangle(270, 120, 61, 16));
			dev4Label.setText("Dev4");
			dev4Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev7Label = new JLabel();
			dev7Label.setBounds(new Rectangle(450, 120, 61, 16));
			dev7Label.setText("Dev7");
			dev7Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev6Label = new JLabel();
			dev6Label.setBounds(new Rectangle(390, 120, 61, 16));
			dev6Label.setText("Dev6");
			dev6Label.setHorizontalAlignment(SwingConstants.CENTER);
			devRefLabel = new JLabel();
			devRefLabel.setBounds(new Rectangle(30, 150, 61, 16));
			devRefLabel.setText("Reference");
			devRefLabel.setHorizontalAlignment(SwingConstants.LEFT);
			devMultimeterChannelLabel = new JLabel();
			devMultimeterChannelLabel.setBounds(new Rectangle(30, 180, 61, 16));
			devMultimeterChannelLabel.setText("Channel");
			devMultimeterChannelLabel.setHorizontalAlignment(SwingConstants.LEFT);

			devTypeLabel = new JLabel();
			devTypeLabel.setText("Type");
			devTypeLabel.setBounds(new Rectangle(30, 210, 61, 16));
			devTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);


			dev3Label = new JLabel();
			dev3Label.setBounds(new Rectangle(210, 120, 61, 16));
			dev3Label.setText("Dev3");
			dev3Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev2Label = new JLabel();
			dev2Label.setBounds(new Rectangle(150, 120, 61, 16));
			dev2Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev2Label.setText("Dev2");
			dev1Label = new JLabel();
			dev1Label.setBounds(new Rectangle(90, 120, 61, 16));
			dev1Label.setHorizontalAlignment(SwingConstants.CENTER);
			dev1Label.setText("Dev1");
			devicesToCalibrateConfigurationLabel = new JLabel();
			devicesToCalibrateConfigurationLabel.setBounds(new Rectangle(30, 30, 481, 31));
			devicesToCalibrateConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			devicesToCalibrateConfigurationLabel.setText("DEVICES TO CALIBRATE CONFIGURATION");
			devicesToCalibrateConfigurationLabel.setBackground(new Color(204, 204, 255));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;

			devicesToCalibratePanel = new JPanel();
			devicesToCalibratePanel.setName("Devices To Calibrate");
			devicesToCalibratePanel.setLayout(null);

			devicesToCalibratePanel.add(devicesToCalibrateConfigurationLabel, null);
			devicesToCalibratePanel.add(devMultimeterChannelLabel, null);
			devicesToCalibratePanel.add(devRefLabel, null);

			devicesToCalibratePanel.add(dev1Label, null);
			devicesToCalibratePanel.add(dev2Label, null);
			devicesToCalibratePanel.add(dev3Label, null);
			devicesToCalibratePanel.add(dev4Label, null);
			devicesToCalibratePanel.add(dev5Label, null);
			devicesToCalibratePanel.add(dev6Label, null);
			devicesToCalibratePanel.add(dev7Label, null);


			devicesToCalibratePanel.add(getDev2RefTextField(), null);
			devicesToCalibratePanel.add(getDev1RefTextField(), null);
			devicesToCalibratePanel.add(getDev3RefTextField(), null);
			devicesToCalibratePanel.add(getDev4RefTextField(), null);
			devicesToCalibratePanel.add(getDev5RefTextField(), null);
			devicesToCalibratePanel.add(getDev6RefTextField(), null);
			devicesToCalibratePanel.add(getDev7RefTextField(), null);

			devicesToCalibratePanel.add(getDev1CheckBox(), null);
			devicesToCalibratePanel.add(getDev2CheckBox(), null);
			devicesToCalibratePanel.add(getDev3CheckBox(), null);
			devicesToCalibratePanel.add(getDev4CheckBox(), null);
			devicesToCalibratePanel.add(getDev5CheckBox(), null);
			devicesToCalibratePanel.add(getDev6CheckBox(), null);
			devicesToCalibratePanel.add(getDev7CheckBox(), null);

			devicesToCalibratePanel.add(getDev1MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev2MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev3MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev4MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev5MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev6MultimeterChannelComboBox(), null);
			devicesToCalibratePanel.add(getDev7MultimeterChannelComboBox(), null);


			devicesToCalibratePanel.add(this.getDev1TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev2TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev3TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev4TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev5TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev6TypeComboBox(),null);
			devicesToCalibratePanel.add(this.getDev7TypeComboBox(),null);
			devicesToCalibratePanel.add(devTypeLabel, null);
		}
		return devicesToCalibratePanel;
	}

	/**
	 * This method initializes advanceDataPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getAdvanceDataPanel() {
		if (advanceDataPanel == null) {
			temperatureStabiltizationTimejLabel = new JLabel();
			temperatureStabiltizationTimejLabel.setBounds(new Rectangle(30, 315, 226, 31));
			temperatureStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			temperatureStabiltizationTimejLabel.setText("StabilitzationTime (ms)");
			temperatureStabilitzationMethodjLabel = new JLabel();
			temperatureStabilitzationMethodjLabel.setBounds(new Rectangle(30, 90, 226, 31));
			temperatureStabilitzationMethodjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilitzationMethodjLabel.setText("Temperature Stabilitzation Method");
			nPassesBelowStandardDeviationjLabel = new JLabel();
			nPassesBelowStandardDeviationjLabel.setBounds(new Rectangle(30, 240, 226, 31));
			nPassesBelowStandardDeviationjLabel.setHorizontalAlignment(SwingConstants.LEFT);
			nPassesBelowStandardDeviationjLabel.setText("Passes On Standard Deviation");
			timeBetweenSucesivesMeasuresLabel = new JLabel();
			timeBetweenSucesivesMeasuresLabel.setBounds(new Rectangle(30, 210, 226, 31));
			timeBetweenSucesivesMeasuresLabel.setText("Time Between Succ Measures (ms)");
			timeBetweenSucesivesMeasuresLabel.setHorizontalAlignment(SwingConstants.LEFT);
			nMeasuresPerCicleLabel = new JLabel();
			nMeasuresPerCicleLabel.setBounds(new Rectangle(30, 180, 226, 31));
			nMeasuresPerCicleLabel.setText("Measures Per Cicle");
			nMeasuresPerCicleLabel.setHorizontalAlignment(SwingConstants.LEFT);
			standardDeviationLabel = new JLabel();
			standardDeviationLabel.setBounds(new Rectangle(30, 150, 226, 31));
			standardDeviationLabel.setHorizontalAlignment(SwingConstants.LEFT);
			standardDeviationLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			standardDeviationLabel.setText("Standard Deviation");
			advanceConfigurationLabel = new JLabel();
			advanceConfigurationLabel.setBounds(new Rectangle(30, 30, 481, 31));
			advanceConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			advanceConfigurationLabel.setText("ADVANCE PROGRAM CONFIGURATION");
			advanceConfigurationLabel.setBackground(new Color(204, 204, 255));
			advanceDataPanel = new JPanel();
			advanceDataPanel.setName("Advance");
			advanceDataPanel.setLayout(null);
			advanceDataPanel.add(advanceConfigurationLabel, null);
			advanceDataPanel.add(standardDeviationLabel, null);
			advanceDataPanel.add(nMeasuresPerCicleLabel, null);
			advanceDataPanel.add(timeBetweenSucesivesMeasuresLabel, null);
			advanceDataPanel.add(getStandardDeviationComboBox(), null);
			advanceDataPanel.add(getNMeasuresPerCicleComboBox(), null);
			advanceDataPanel.add(getTimeBetweenSuccesivesMeasuresComboBox(), null);
			advanceDataPanel.add(getJNPassesBelowStandardDeviationComboBox(), null);
			advanceDataPanel.add(nPassesBelowStandardDeviationjLabel, null);
			advanceDataPanel.add(getTemperatureStabilitzationByStDevjRadioButton(), null);
			advanceDataPanel.add(getTemperatureStabilitzationByTimejRadioButton(), null);
			advanceDataPanel.add(temperatureStabilitzationMethodjLabel, null);
			advanceDataPanel.add(temperatureStabiltizationTimejLabel, null);
			advanceDataPanel.add(getTemperatureStabilitzationTimejComboBox(), null);
		}
		return advanceDataPanel;
	}

	/**
	 * This method initializes generalPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getGeneralPanel() {
		if (generalPanel == null) {
			programTypeLabel = new JLabel();
			programTypeLabel.setBounds(new Rectangle(30, 135, 91, 31));
			programTypeLabel.setText("Type");
			programTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programDescriptionLabel = new JLabel();
			programDescriptionLabel.setBounds(new Rectangle(30, 225, 91, 76));
			programDescriptionLabel.setText("Description");
			programDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programAuthorLabel = new JLabel();
			programAuthorLabel.setBounds(new Rectangle(30, 180, 91, 31));
			programAuthorLabel.setText("Author");
			programAuthorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programNameLabel = new JLabel();
			programNameLabel.setBounds(new Rectangle(30, 90, 91, 31));
			programNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programNameLabel.setText("Name");
			temperatureProfileConfigurationLabel1 = new JLabel();
			temperatureProfileConfigurationLabel1.setBounds(new Rectangle(30, 30, 481, 31));
			temperatureProfileConfigurationLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel1.setText("GENERAL PROGRAM DATA CONFIGURATION");
			temperatureProfileConfigurationLabel1.setBackground(new Color(204, 204, 255));
			generalPanel = new JPanel();
			generalPanel.setName("General");
			generalPanel.setLayout(null);
			generalPanel.add(temperatureProfileConfigurationLabel1, null);
			generalPanel.add(programNameLabel, null);
			generalPanel.add(programAuthorLabel, null);
			generalPanel.add(programDescriptionLabel, null);
			generalPanel.add(getProgramNameTextField(), null);
			generalPanel.add(getProgramAuthorTextField(), null);
			generalPanel.add(getProgramDescriptionTextArea(), null);
			generalPanel.add(programTypeLabel, null);
			generalPanel.add(getProgramTypeComboBox(), null);
		}
		return generalPanel;
	}

	/**
	 * This method initializes temperatureProfileList
	 *
	 * @return java.awt.List
	 */
	private List getTemperatureProfileList() {
		if (temperatureProfileList == null) {
			temperatureProfileList = new List(10,true);
			temperatureProfileList.setBounds(new Rectangle(340, 75, 81, 196));
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
			saveButton.setBounds(new Rectangle(435, 0, 136, 31));
			saveButton.setText("Save");
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
			saveAsButton.setBounds(new Rectangle(270, 0, 146, 31));
			saveAsButton.setText("Save As");
			saveAsButton.addActionListener(this);
		}
		return saveAsButton;
	}
	/**
	 * This is the default constructor
	 */
	public ThermalCalibrationProgramViewerFrame(ThermalCalibrationProgramData _program,int _mode, MainController _mainController) {
		super("ThermalCalibrationProgramViewerFrame");
		mainController = _mainController;
		addWindowListener(mainController);
		fileChooser = new JFileChooser();
		initialize(_program,_mode);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(ThermalCalibrationProgramData _program,int _mode) {

		this.setSize(575, 511);
		this.setContentPane(getJContentPane());
		this.setName("ThermalCalibrationProgramViewerFrame");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setResizable(false);
		this.program = _program;
		//0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save
	  	//1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		//2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		configureFrameMode(_mode);
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
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    setDefaultProgramData();
			    break;
		    case 1:
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadProgramData(program);
			    break;
		    case 2:
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadProgramData(program);
			    break;
		    default:
		    }
	 }
	public void loadProgramData(ThermalCalibrationProgramData _program){
		loadTemperatureProfileProgramData(_program);
		loadMeasuresConfigurationProgramData(_program);
		loadAdvanceProgramData(_program);
		loadGeneralProgramData(_program);
	}
	private void loadTemperatureProfileProgramData(ThermalCalibrationProgramData _program){
		this.removeTemperatureProfileItemListeners();
		this.maxTempTextField.setText(String.valueOf(_program.getTemperatureProfileData().getTemperatures()[_program.getTemperatureProfileData().getTemperatures().length-1]));
		this.minTempComboBox.setSelectedItem(String.valueOf(_program.getTemperatureProfileData().getTemperatures()[0]));
		this.nStepsComboBox.setSelectedItem(String.valueOf(_program.getTemperatureProfileData().getTemperatures().length));
		int[] temperatures = _program.getTemperatureProfileData().getTemperatures();
		for (int i=0;i<temperatures.length;i++){
			temperatureProfileList.select(temperatures[i]);
		}
		this.addTemperatureProfileItemListeners();
	}
	private void loadMeasuresConfigurationProgramData(ThermalCalibrationProgramData _program){

		this.dev1CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[0].isSelected());
		this.dev1RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[0].getDeviceReference());
		this.dev1MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[0].getDeviceChannel()));
		this.dev1TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[0].getDeviceType());

		this.dev2CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[1].isSelected());
		this.dev2RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[1].getDeviceReference());
		this.dev2MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[1].getDeviceChannel()));
		this.dev2TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[1].getDeviceType());

		this.dev3CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[2].isSelected());
		this.dev3RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[2].getDeviceReference());
		this.dev3MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[2].getDeviceChannel()));
		this.dev3TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[2].getDeviceType());

		this.dev4CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[3].isSelected());
		this.dev4RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[3].getDeviceReference());
		this.dev4MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[3].getDeviceChannel()));
		this.dev4TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[3].getDeviceType());

		this.dev5CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[4].isSelected());
		this.dev5RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[4].getDeviceReference());
		this.dev5MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[4].getDeviceChannel()));
		this.dev5TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[4].getDeviceType());

		this.dev6CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[5].isSelected());
		this.dev6RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[5].getDeviceReference());
		this.dev6MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[5].getDeviceChannel()));
		this.dev6TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[5].getDeviceType());

		this.dev7CheckBox.setSelected(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[6].isSelected());
		this.dev7RefTextField.setText(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[6].getDeviceReference());
		this.dev7MultimeterChannelComboBox.setSelectedItem(Integer.toString(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[6].getDeviceChannel()));
		this.dev7TypeComboBox.setSelectedItem(_program.getMeasuresConfigurationData().getDevicesToMeasureData()[6].getDeviceType());

		}
	private void loadAdvanceProgramData(ThermalCalibrationProgramData _program){
		this.standardDeviationComboBox.setSelectedItem(Double.toString(_program.getAdvanceProgramData().getStandardDeviation()));
		this.nMeasuresPerCicleComboBox.setSelectedItem(Integer.toString(_program.getAdvanceProgramData().getMeasuresPerCicle()));
		this.timeBetweenSuccesivesMeasuresComboBox.setSelectedItem(Long.toString(_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures()));
		this.nPassesBelowStandardDeviationComboBox.setSelectedItem(Integer.toString(_program.getAdvanceProgramData().getNPassesBelowStandardDeviation()));
		this.temperatureStabilitzationTimejComboBox.setSelectedItem(Integer.toString(_program.getAdvanceProgramData().getTemperatureStabilitzationTime()));
		int temperatureStabilitzationMethod = _program.getAdvanceProgramData().getTemperatureStabilitzationMethod();
		// 0 temperature stabilitzation by St Dev, 1 temperature stabilitzation by Time
		if (temperatureStabilitzationMethod==0) this.temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		if (temperatureStabilitzationMethod==1) this.temperatureStabilitzationByTimejRadioButton.setSelected(true);
	}
	private void loadGeneralProgramData(ThermalCalibrationProgramData _program){
		this.programNameTextField.setText((String)_program.getGeneralProgramData().getProgramName());
		this.programTypeComboBox.setSelectedItem((String)_program.getGeneralProgramData().getProgramType());
		this.programAuthorTextField.setText((String)_program.getGeneralProgramData().getProgramAuthor());
		this.programDescriptionTextArea.setText((String)_program.getGeneralProgramData().getProgramDescription());
	}
	private void setDefaultProgramData(){
		setDefaultTempProfileData();
		setDefaultDevicesToCalibrateData();
		setDefaultAdvanceData();
		setDefaultGeneralData();
	}
	private void setDefaultGeneralData(){
		this.programNameTextField.setText("Program1");
		this.programAuthorTextField.setText("Author x");
		this.programDescriptionTextArea.setText("Program for calibrating Thermal Test Chips.");
	}
	private void setDefaultAdvanceData(){
		standardDeviationComboBox.setSelectedItem("0.03");
		nMeasuresPerCicleComboBox.setSelectedItem("120");
		timeBetweenSuccesivesMeasuresComboBox.setSelectedItem("1000");
		nPassesBelowStandardDeviationComboBox.setSelectedItem("1");
		temperatureStabilitzationTimejComboBox.setSelectedItem("10");
		temperatureStabilitzationByStDevjRadioButton.setSelected(true);
	}
	private void setDefaultDevicesToCalibrateData(){
		this.dev1CheckBox.setSelected(true);
		this.dev1RefTextField.setText("Dev1");
		this.dev1MultimeterChannelComboBox.setSelectedItem("1");
		this.dev2CheckBox.setSelected(false);
		this.dev2RefTextField.setText("Dev2");
		this.dev2MultimeterChannelComboBox.setSelectedItem("2");
		this.dev3CheckBox.setSelected(false);
		this.dev3RefTextField.setText("Dev3");
		this.dev3MultimeterChannelComboBox.setSelectedItem("3");
		this.dev4CheckBox.setSelected(false);
		this.dev4RefTextField.setText("Dev4");
		this.dev4MultimeterChannelComboBox.setSelectedItem("5");
		this.dev5CheckBox.setSelected(false);
		this.dev5RefTextField.setText("Dev5");
		this.dev5MultimeterChannelComboBox.setSelectedItem("6");
		this.dev6CheckBox.setSelected(false);
		this.dev6RefTextField.setText("Dev6");
		this.dev6MultimeterChannelComboBox.setSelectedItem("7");
		this.dev7CheckBox.setSelected(false);
		this.dev7RefTextField.setText("Dev7");
		this.dev7MultimeterChannelComboBox.setSelectedItem("8");


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

		  if (cmd.equals("Save As")) {
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
		  if (cmd.equals("Save")) {
			  if (!validateProgramData()){return;}
			  //grabar con el nombre actual del fichero
			  try {
				  saveToXMLFile(program.getGeneralProgramData().getProgramFilePath());
				  System.out.println(program.getGeneralProgramData().getProgramFilePath());
			  } catch (Exception e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  if (cmd.equals("Cancel")) {}
		  if (cmd.equals("0-100 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("21");
			  maxTempTextField.setText("100");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-125 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("26");
			  maxTempTextField.setText("125");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-150 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("31");
			  maxTempTextField.setText("150");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-175 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("36");
			  maxTempTextField.setText("175");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-200 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("41");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-225 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("46");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-250 ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("51");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("Clear")){
			  clearTemperatureProfileList();
			  setDefaultTempProfileData();
		  }
	  }
	private boolean validateProgramData(){
		if (validateTemperatureProfileData()==false) return false;
		if (validateDevicesToCalibrateData()==false) return false;
		if (validateAdvanceData()==false) return false;
		if (validateGeneralData()==false) return false;
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
	public boolean validateDevicesToCalibrateData(){
		if (
				(((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev2MultimeterChannelComboBox.getSelectedItem()) 		& dev2CheckBox.isSelected())
				|| (((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev3MultimeterChannelComboBox.getSelectedItem()) 	& dev3CheckBox.isSelected())
				|| (((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev4MultimeterChannelComboBox.getSelectedItem()) 	& dev4CheckBox.isSelected())
				|| (((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev5MultimeterChannelComboBox.getSelectedItem()) 	& dev5CheckBox.isSelected())
				|| (((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem()) 	& dev6CheckBox.isSelected())
				|| (((String)dev1MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem()) 	& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº1 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				(((String)dev2MultimeterChannelComboBox.getSelectedItem()).equals((String)dev3MultimeterChannelComboBox.getSelectedItem())	& dev3CheckBox.isSelected())
				|| (((String)dev2MultimeterChannelComboBox.getSelectedItem()).equals((String)dev4MultimeterChannelComboBox.getSelectedItem())	& dev4CheckBox.isSelected())
				|| (((String)dev2MultimeterChannelComboBox.getSelectedItem()).equals((String)dev5MultimeterChannelComboBox.getSelectedItem())	& dev5CheckBox.isSelected())
				|| (((String)dev2MultimeterChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem())	& dev6CheckBox.isSelected())
				|| (((String)dev2MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())	& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº2 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				(((String)dev3MultimeterChannelComboBox.getSelectedItem()).equals((String)dev4MultimeterChannelComboBox.getSelectedItem())	& dev4CheckBox.isSelected())
				|| (((String)dev3MultimeterChannelComboBox.getSelectedItem()).equals((String)dev5MultimeterChannelComboBox.getSelectedItem())	& dev5CheckBox.isSelected())
				|| (((String)dev3MultimeterChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem())	& dev6CheckBox.isSelected())
				|| (((String)dev3MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())	& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº3 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				(((String)dev4MultimeterChannelComboBox.getSelectedItem()).equals((String)dev5MultimeterChannelComboBox.getSelectedItem())	& dev5CheckBox.isSelected())
				|| (((String)dev4MultimeterChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem())& dev6CheckBox.isSelected())
				|| (((String)dev4MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº4 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				(((String)dev5MultimeterChannelComboBox.getSelectedItem()).equals((String)dev6MultimeterChannelComboBox.getSelectedItem())& dev6CheckBox.isSelected())
				|| (((String)dev5MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº5 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		if (
				(((String)dev6MultimeterChannelComboBox.getSelectedItem()).equals((String)dev7MultimeterChannelComboBox.getSelectedItem())& dev7CheckBox.isSelected())
		){
			JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº6 no puede utilizarse para ningún otro dispositivo");
			return false;
		}
		return true;
	}
	public boolean validateAdvanceData(){
		return true;
	}
	public boolean validateGeneralData(){
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
	private JButton get0To100TempProfileJButton() {
		if (mark0To100TemperatureProfileButton == null) {
			mark0To100TemperatureProfileButton = new JButton("0-100 Step-5ºC");
			mark0To100TemperatureProfileButton.addActionListener(this);
			mark0To100TemperatureProfileButton.setBounds(new Rectangle(430, 75, 81, 21));
			mark0To100TemperatureProfileButton.setText("0-100 ºC");
			mark0To100TemperatureProfileButton.setHorizontalAlignment(SwingConstants.CENTER);
			mark0To100TemperatureProfileButton.setToolTipText("0-100 Step-5ºC");
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
			mark0To125TemperatureProfileButton.setBounds(new Rectangle(430, 100, 81, 21));
			mark0To125TemperatureProfileButton.setText("0-125 ºC");
			mark0To125TemperatureProfileButton.setToolTipText("0-125 Step-5ºC");
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
			mark0To150TemperatureProfileButton.setBounds(new Rectangle(430, 125, 81, 21));
			mark0To150TemperatureProfileButton.setText("0-150 ºC");
			mark0To150TemperatureProfileButton.setToolTipText("0-150 Step-5ºC");
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
			mark0To175TemperatureProfileButton.setBounds(new Rectangle(430, 150, 81, 21));
			mark0To175TemperatureProfileButton.setText("0-175 ºC");
			mark0To175TemperatureProfileButton.setToolTipText("0-175 Step-5ºC");
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
			mark0To200TemperatureProfileButton.setBounds(new Rectangle(430, 175, 81, 21));
			mark0To200TemperatureProfileButton.setText("0-200 ºC");
			mark0To200TemperatureProfileButton.setToolTipText("0-200 Step-5ºC");
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
			mark0To225TemperatureProfileButton.setBounds(new Rectangle(430, 200, 81, 21));
			mark0To225TemperatureProfileButton.setText("0-225 ºC");
			mark0To225TemperatureProfileButton.setToolTipText("0-225 Step-5ºC");
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
			mark0To250TemperatureProfileButton.setBounds(new Rectangle(430, 225, 81, 21));
			mark0To250TemperatureProfileButton.setText("0-250 ºC");
			mark0To250TemperatureProfileButton.setToolTipText("0-250 Step-5ºC");
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
			minTempComboBox.setBounds(new Rectangle(275, 75, 61, 21));
			for (int i=MIN_TEMP_OVEN;i<=MAX_TEMP_OVEN;i=i+TEMP_OVEN_INCREMENT){
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
			maxTempTextField.setBounds(new Rectangle(275, 100, 61, 21));
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
			nStepsComboBox.setBounds(new Rectangle(275, 125, 61, 21));
			for (int i=MIN_TEMP_OVEN+1;i<=(MAX_TEMP_OVEN+1);i=i+TEMP_OVEN_INCREMENT){
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
			stepValueComboBox.setBounds(new Rectangle(275, 150, 61, 21));
			for (int i=MIN_TEMP_OVEN;i<=MAX_TEMP_OVEN;i=i+TEMP_OVEN_INCREMENT){
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
		
		  DeviceToMeasureData[] deviceToMeasureData = new DeviceToMeasureData[N_DEVICES_TO_MEASURE];
		  deviceToMeasureData[0] = new DeviceToMeasureData(dev1CheckBox.isSelected(),dev1RefTextField.getText(),Integer.parseInt((String)this.dev1MultimeterChannelComboBox.getSelectedItem()),(String)this.dev1TypeComboBox.getSelectedItem());
		  deviceToMeasureData[1] = new DeviceToMeasureData(dev2CheckBox.isSelected(),dev2RefTextField.getText(),Integer.parseInt((String)this.dev2MultimeterChannelComboBox.getSelectedItem()),(String)this.dev2TypeComboBox.getSelectedItem());
		  deviceToMeasureData[2] = new DeviceToMeasureData(dev3CheckBox.isSelected(),dev3RefTextField.getText(),Integer.parseInt((String)this.dev3MultimeterChannelComboBox.getSelectedItem()),(String)this.dev3TypeComboBox.getSelectedItem());
		  deviceToMeasureData[3] = new DeviceToMeasureData(dev4CheckBox.isSelected(),dev4RefTextField.getText(),Integer.parseInt((String)this.dev4MultimeterChannelComboBox.getSelectedItem()),(String)this.dev4TypeComboBox.getSelectedItem());
		  deviceToMeasureData[4] = new DeviceToMeasureData(dev5CheckBox.isSelected(),dev5RefTextField.getText(),Integer.parseInt((String)this.dev5MultimeterChannelComboBox.getSelectedItem()),(String)this.dev5TypeComboBox.getSelectedItem());
		  deviceToMeasureData[5] = new DeviceToMeasureData(dev6CheckBox.isSelected(),dev6RefTextField.getText(),Integer.parseInt((String)this.dev6MultimeterChannelComboBox.getSelectedItem()),(String)this.dev6TypeComboBox.getSelectedItem());
		  deviceToMeasureData[6] = new DeviceToMeasureData(dev7CheckBox.isSelected(),dev7RefTextField.getText(),Integer.parseInt((String)this.dev7MultimeterChannelComboBox.getSelectedItem()),(String)this.dev7TypeComboBox.getSelectedItem());


		  MeasuresConfigurationData measuresConfigurationData = new MeasuresConfigurationData
		  (
				deviceToMeasureData
		  );


		  Object[] temperaturesList = temperatureProfileList.getSelectedObjects();
		  int[] temperatures = new int[temperaturesList.length];
		  for (int i=0;i<temperaturesList.length;i++){
			  temperatures[i] = Integer.parseInt((String)temperaturesList[i]);
		  }
		  TemperatureProfileData temperatureProfileData = new TemperatureProfileData
		  (
				  "TemperatureProfileData_1",
				  temperatures
		  );
		  GeneralProgramData generalProgramData = new GeneralProgramData(this.programNameTextField.getText(),(String)this.programTypeComboBox.getSelectedItem(),_programFilePath,this.programAuthorTextField.getText(),this.programDescriptionTextArea.getText());
		  
		  int temperatureStabilitzationMethod = 0; //By Default St Dev
		  if (temperatureStabilitzationByTimejRadioButton.isSelected()) temperatureStabilitzationMethod = 1;
		  AdvanceProgramData advanceProgramData = new AdvanceProgramData(Double.parseDouble((String)this.standardDeviationComboBox.getSelectedItem()),Integer.parseInt((String)this.nMeasuresPerCicleComboBox.getSelectedItem()),Long.parseLong((String)this.timeBetweenSuccesivesMeasuresComboBox.getSelectedItem()),Integer.parseInt((String)this.nPassesBelowStandardDeviationComboBox.getSelectedItem()),temperatureStabilitzationMethod,Integer.parseInt((String)this.temperatureStabilitzationTimejComboBox.getSelectedItem()));
		  
		  ThermalCalibrationProgramData thermalCalibrationProgramData = new ThermalCalibrationProgramData
		  (
				  temperatureProfileData,
				  measuresConfigurationData,
				  advanceProgramData,
				  generalProgramData
		  );
		  program = thermalCalibrationProgramData;
		  thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData");
	  }

	/**
	 * This method initializes clearTemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getClearTemperatureProfileButton() {
		if (clearTemperatureProfileButton == null) {
			clearTemperatureProfileButton = new JButton("Clear Temp Profile");
			clearTemperatureProfileButton.setBounds(new Rectangle(430, 250, 81, 21));
			clearTemperatureProfileButton.setToolTipText("Clear Temp Profile");
			clearTemperatureProfileButton.setText("Clear");
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
			programNameTextField.setBounds(new Rectangle(135, 90, 376, 31));
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
			programAuthorTextField.setBounds(new Rectangle(135, 180, 376, 31));
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
			programDescriptionTextArea.setBounds(new Rectangle(135, 225, 376, 76));
			programDescriptionTextArea.setRows(10);
		}
		return programDescriptionTextArea;
	}

	/**
	 * This method initializes dev1NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev2RefTextField() {
		if (dev2RefTextField == null) {
			dev2RefTextField = new JTextField();
			dev2RefTextField.setEnabled(true);
			dev2RefTextField.setBounds(new Rectangle(150, 150, 61, 16));
		}
		return dev2RefTextField;
	}

	/**
	 * This method initializes dev2NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev1RefTextField() {
		if (dev1RefTextField == null) {
			dev1RefTextField = new JTextField();
			dev1RefTextField.setBounds(new Rectangle(90, 150, 61, 16));
		}
		return dev1RefTextField;
	}

	/**
	 * This method initializes dev3NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev3RefTextField() {
		if (dev3RefTextField == null) {
			dev3RefTextField = new JTextField();
			dev3RefTextField.setBounds(new Rectangle(210, 150, 61, 16));
			dev3RefTextField.setEnabled(false);
		}
		return dev3RefTextField;
	}

	/**
	 * This method initializes dev4NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev4RefTextField() {
		if (dev4RefTextField == null) {
			dev4RefTextField = new JTextField();
			dev4RefTextField.setBounds(new Rectangle(270, 150, 61, 16));
			dev4RefTextField.setEnabled(false);
		}
		return dev4RefTextField;
	}

	/**
	 * This method initializes dev5NameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev5RefTextField() {
		if (dev5RefTextField == null) {
			dev5RefTextField = new JTextField();
			dev5RefTextField.setEnabled(false);
			dev5RefTextField.setBounds(new Rectangle(330, 150, 61, 16));
		}
		return dev5RefTextField;
	}

	/**
	 * This method initializes dev1CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev1CheckBox() {
		if (dev1CheckBox == null) {
			dev1CheckBox = new JCheckBox();
			dev1CheckBox.setBounds(new Rectangle(90, 90, 61, 16));
			dev1CheckBox.setSelected(true);
			dev1CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev1CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev1CheckBox.setSelected(true);
		        	}
		        }
		      }
		    );
		}
		return dev1CheckBox;
	}

	/**
	 * This method initializes dev5NameTextField11
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev7RefTextField() {
		if (dev7RefTextField == null) {
			dev7RefTextField = new JTextField();
			dev7RefTextField.setBounds(new Rectangle(450, 150, 61, 16));
			dev7RefTextField.setEnabled(false);
		}
		return dev7RefTextField;
	}

	/**
	 * This method initializes dev2CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev2CheckBox() {
		if (dev2CheckBox == null) {
			dev2CheckBox = new JCheckBox();
			dev2CheckBox.setBounds(new Rectangle(150, 90, 61, 16));
			dev2CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev2CheckBox.setVisible(true);
			dev2CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		dev3RefTextField.setEnabled(true);
		        		dev3MultimeterChannelComboBox.setEnabled(true);
		        		dev3CheckBox.setEnabled(true);
		        		dev3TypeComboBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev3CheckBox.setSelected(false);
		        		dev3RefTextField.setEnabled(false);
		        		dev3MultimeterChannelComboBox.setEnabled(false);
		        		dev3CheckBox.setEnabled(false);
		        		dev3TypeComboBox.setEnabled(false);
		        		dev4CheckBox.setSelected(false);
		        		dev4RefTextField.setEnabled(false);
		        		dev4MultimeterChannelComboBox.setEnabled(false);
		        		dev4CheckBox.setEnabled(false);
		        		dev4TypeComboBox.setEnabled(false);
		        		dev5CheckBox.setSelected(false);
		        		dev5RefTextField.setEnabled(false);
		        		dev5MultimeterChannelComboBox.setEnabled(false);
		        		dev5CheckBox.setEnabled(false);
		        		dev5TypeComboBox.setEnabled(false);
		        		dev6CheckBox.setSelected(false);
		        		dev6RefTextField.setEnabled(false);
		        		dev6MultimeterChannelComboBox.setEnabled(false);
		        		dev6CheckBox.setEnabled(false);
		        		dev6TypeComboBox.setEnabled(false);
		        		dev7CheckBox.setSelected(false);
		        		dev7RefTextField.setEnabled(false);
		        		dev7MultimeterChannelComboBox.setEnabled(false);
		        		dev7CheckBox.setEnabled(false);
		        		dev7TypeComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return dev2CheckBox;
	}

	/**
	 * This method initializes dev3CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev3CheckBox() {
		if (dev3CheckBox == null) {
			dev3CheckBox = new JCheckBox();
			dev3CheckBox.setBounds(new Rectangle(210, 90, 61, 16));
			dev3CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev3CheckBox.setEnabled(false);
			dev3CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		dev4RefTextField.setEnabled(true);
		        		dev4MultimeterChannelComboBox.setEnabled(true);
		        		dev4CheckBox.setEnabled(true);
		        		dev4TypeComboBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev4CheckBox.setSelected(false);
		        		dev4RefTextField.setEnabled(false);
		        		dev4MultimeterChannelComboBox.setEnabled(false);
		        		dev4CheckBox.setEnabled(false);
		        		dev4TypeComboBox.setEnabled(false);
		        		dev5CheckBox.setSelected(false);
		        		dev5RefTextField.setEnabled(false);
		        		dev5MultimeterChannelComboBox.setEnabled(false);
		        		dev5CheckBox.setEnabled(false);
		        		dev5TypeComboBox.setEnabled(false);
		        		dev6CheckBox.setSelected(false);
		        		dev6RefTextField.setEnabled(false);
		        		dev6MultimeterChannelComboBox.setEnabled(false);
		        		dev6CheckBox.setEnabled(false);
		        		dev6TypeComboBox.setEnabled(false);
		        		dev7CheckBox.setSelected(false);
		        		dev7RefTextField.setEnabled(false);
		        		dev7MultimeterChannelComboBox.setEnabled(false);
		        		dev7CheckBox.setEnabled(false);
		        		dev7TypeComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return dev3CheckBox;
	}

	/**
	 * This method initializes dev6RefTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getDev6RefTextField() {
		if (dev6RefTextField == null) {
			dev6RefTextField = new JTextField();
			dev6RefTextField.setBounds(new Rectangle(390, 150, 61, 16));
			dev6RefTextField.setEnabled(false);
		}
		return dev6RefTextField;
	}

	/**
	 * This method initializes dev4CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev4CheckBox() {
		if (dev4CheckBox == null) {
			dev4CheckBox = new JCheckBox();
			dev4CheckBox.setBounds(new Rectangle(270, 90, 61, 16));
			dev4CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev4CheckBox.setEnabled(false);
			dev4CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		dev5RefTextField.setEnabled(true);
		        		dev5MultimeterChannelComboBox.setEnabled(true);
		        		dev5CheckBox.setEnabled(true);
		        		dev5TypeComboBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev5CheckBox.setSelected(false);
		        		dev5RefTextField.setEnabled(false);
		        		dev5MultimeterChannelComboBox.setEnabled(false);
		        		dev5CheckBox.setEnabled(false);
		        		dev5TypeComboBox.setEnabled(false);
		        		dev6CheckBox.setSelected(false);
		        		dev6RefTextField.setEnabled(false);
		        		dev6MultimeterChannelComboBox.setEnabled(false);
		        		dev6CheckBox.setEnabled(false);
		        		dev6TypeComboBox.setEnabled(false);
		        		dev7CheckBox.setSelected(false);
		        		dev7RefTextField.setEnabled(false);
		        		dev7MultimeterChannelComboBox.setEnabled(false);
		        		dev7CheckBox.setEnabled(false);
		        		dev7TypeComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return dev4CheckBox;
	}

	/**
	 * This method initializes dev5CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev5CheckBox() {
		if (dev5CheckBox == null) {
			dev5CheckBox = new JCheckBox();
			dev5CheckBox.setBounds(new Rectangle(330, 90, 61, 16));
			dev5CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev5CheckBox.setEnabled(false);
			dev5CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		dev6RefTextField.setEnabled(true);
		        		dev6MultimeterChannelComboBox.setEnabled(true);
		        		dev6CheckBox.setEnabled(true);
		        		dev6TypeComboBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev6CheckBox.setSelected(false);
		        		dev6RefTextField.setEnabled(false);
		        		dev6MultimeterChannelComboBox.setEnabled(false);
		        		dev6CheckBox.setEnabled(false);
		        		dev6TypeComboBox.setEnabled(false);
		        		dev7CheckBox.setSelected(false);
		        		dev7RefTextField.setEnabled(false);
		        		dev7MultimeterChannelComboBox.setEnabled(false);
		        		dev7CheckBox.setEnabled(false);
		        		dev7TypeComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return dev5CheckBox;
	}

	/**
	 * This method initializes dev6CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev6CheckBox() {
		if (dev6CheckBox == null) {
			dev6CheckBox = new JCheckBox();
			dev6CheckBox.setBounds(new Rectangle(390, 90, 61, 16));
			dev6CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev6CheckBox.setEnabled(false);
			dev6CheckBox.addItemListener(new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED){
		        		dev7RefTextField.setEnabled(true);
		        		dev7MultimeterChannelComboBox.setEnabled(true);
		        		dev7CheckBox.setEnabled(true);
		        		dev7TypeComboBox.setEnabled(true);
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		dev7CheckBox.setSelected(false);
		        		dev7RefTextField.setEnabled(false);
		        		dev7MultimeterChannelComboBox.setEnabled(false);
		        		dev7CheckBox.setEnabled(false);
		        		dev7TypeComboBox.setEnabled(false);
		        	}
		        }
		      }
		    );
		}
		return dev6CheckBox;
	}

	/**
	 * This method initializes dev7CheckBox
	 *
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getDev7CheckBox() {
		if (dev7CheckBox == null) {
			dev7CheckBox = new JCheckBox();
			dev7CheckBox.setBounds(new Rectangle(450, 90, 61, 17));
			dev7CheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			dev7CheckBox.setEnabled(false);
			dev7CheckBox.addActionListener(this);
		}
		return dev7CheckBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev1MultimeterChannelComboBox() {
		if (dev1MultimeterChannelComboBox == null) {
			dev1MultimeterChannelComboBox = new JComboBox();
			dev1MultimeterChannelComboBox.setBounds(new Rectangle(90, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev1MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev1MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev1TypeComboBox() {
		if (dev1TypeComboBox == null) {
			dev1TypeComboBox = new JComboBox();
			dev1TypeComboBox.setBounds(new Rectangle(90, 210, 61, 16));
			dev1TypeComboBox.setEnabled(true);
			dev1TypeComboBox.addItem ("None");
			dev1TypeComboBox.addItem ("Resistor");
		}
		return dev1TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev2TypeComboBox() {
		if (dev2TypeComboBox == null) {
			dev2TypeComboBox = new JComboBox();
			dev2TypeComboBox.setBounds(new Rectangle(150, 210, 61, 16));
			dev2TypeComboBox.setEnabled(true);
			dev2TypeComboBox.addItem ("None");
			dev2TypeComboBox.addItem ("Resistor");
		}
		return dev2TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev3TypeComboBox() {
		if (dev3TypeComboBox == null) {
			dev3TypeComboBox = new JComboBox();
			dev3TypeComboBox.setBounds(new Rectangle(210, 210, 61, 16));
			dev3TypeComboBox.setEnabled(false);
			dev3TypeComboBox.addItem ("None");
			dev3TypeComboBox.addItem ("Resistor");
		}
		return dev3TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev4TypeComboBox() {
		if (dev4TypeComboBox == null) {
			dev4TypeComboBox = new JComboBox();
			dev4TypeComboBox.setBounds(new Rectangle(270, 210, 61, 16));
			dev4TypeComboBox.setEnabled(false);
			dev4TypeComboBox.addItem ("None");
			dev4TypeComboBox.addItem ("Resistor");
		}
		return dev4TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev5TypeComboBox() {
		if (dev5TypeComboBox == null) {
			dev5TypeComboBox = new JComboBox();
			dev5TypeComboBox.setBounds(new Rectangle(330, 210, 61, 16));
			dev5TypeComboBox.setEnabled(false);
			dev5TypeComboBox.addItem ("None");
			dev5TypeComboBox.addItem ("Resistor");
		}
		return dev5TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev6TypeComboBox() {
		if (dev6TypeComboBox == null) {
			dev6TypeComboBox = new JComboBox();
			dev6TypeComboBox.setBounds(new Rectangle(390, 210, 61, 16));
			dev6TypeComboBox.setEnabled(false);
			dev6TypeComboBox.addItem ("None");
			dev6TypeComboBox.addItem ("Resistor");
		}
		return dev6TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev7TypeComboBox() {
		if (dev7TypeComboBox == null) {
			dev7TypeComboBox = new JComboBox();
			dev7TypeComboBox.setBounds(new Rectangle(450, 210, 61, 16));
			dev7TypeComboBox.setEnabled(false);
			dev7TypeComboBox.addItem ("None");
			dev7TypeComboBox.addItem ("Resistor");
		}
		return dev7TypeComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev2MultimeterChannelComboBox() {
		if (dev2MultimeterChannelComboBox == null) {
			dev2MultimeterChannelComboBox = new JComboBox();
			dev2MultimeterChannelComboBox.setEnabled(true);
			dev2MultimeterChannelComboBox.setBounds(new Rectangle(150, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev2MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev2MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev3MultimeterChannelComboBox() {
		if (dev3MultimeterChannelComboBox == null) {
			dev3MultimeterChannelComboBox = new JComboBox();
			dev3MultimeterChannelComboBox.setEnabled(false);
			dev3MultimeterChannelComboBox.setBounds(new Rectangle(210, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev3MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev3MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev4MultimeterChannelComboBox() {
		if (dev4MultimeterChannelComboBox == null) {
			dev4MultimeterChannelComboBox = new JComboBox();
			dev4MultimeterChannelComboBox.setEnabled(false);
			dev4MultimeterChannelComboBox.setBounds(new Rectangle(270, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev4MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev4MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev5MultimeterChannelComboBox() {
		if (dev5MultimeterChannelComboBox == null) {
			dev5MultimeterChannelComboBox = new JComboBox();
			dev5MultimeterChannelComboBox.setEnabled(false);
			dev5MultimeterChannelComboBox.setBounds(new Rectangle(330, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev5MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev5MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev6MultimeterChannelComboBox() {
		if (dev6MultimeterChannelComboBox == null) {
			dev6MultimeterChannelComboBox = new JComboBox();
			dev6MultimeterChannelComboBox.setEnabled(false);
			dev6MultimeterChannelComboBox.setBounds(new Rectangle(390, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev6MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev6MultimeterChannelComboBox;
	}
	/**
	 * This method initializes dev1MultimeterChannelComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getDev7MultimeterChannelComboBox() {
		if (dev7MultimeterChannelComboBox == null) {
			dev7MultimeterChannelComboBox = new JComboBox();
			dev7MultimeterChannelComboBox.setEnabled(false);
			dev7MultimeterChannelComboBox.setBounds(new Rectangle(450, 180, 61, 16));
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				dev7MultimeterChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return dev7MultimeterChannelComboBox;
	}
	/**
	 * This method initializes standardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getStandardDeviationComboBox() {
		if (standardDeviationComboBox == null) {
			standardDeviationComboBox = new JComboBox();
			standardDeviationComboBox.setBounds(new Rectangle(270, 150, 106, 31));
			for (double i=1;i<=500;i=i+0.5){
				standardDeviationComboBox.addItem (Double.toString(i/100));
			}
		}
		return standardDeviationComboBox;
	}

	/**
	 * This method initializes nMeasuresPerCicleComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getNMeasuresPerCicleComboBox() {
		if (nMeasuresPerCicleComboBox == null) {
			nMeasuresPerCicleComboBox = new JComboBox();
			nMeasuresPerCicleComboBox.setBounds(new Rectangle(270, 180, 106, 31));
			for (int i=N_MEASURES_PER_CLICLE_MIN_VALUE;i<=this.N_MEASURES_PER_CLICLE_MAX_VALUE;i=i+N_MEASURES_PER_CLICLE_INCREMENT){
				nMeasuresPerCicleComboBox.addItem (Integer.toString(i));
			}
		}
		return nMeasuresPerCicleComboBox;
	}

	/**
	 * This method initializes timeBetweenSuccesivesMeasuresComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getTimeBetweenSuccesivesMeasuresComboBox() {
		if (timeBetweenSuccesivesMeasuresComboBox == null) {
			timeBetweenSuccesivesMeasuresComboBox = new JComboBox();
			timeBetweenSuccesivesMeasuresComboBox.setBounds(new Rectangle(270, 210, 106, 31));
			for (long i=TIME_BETWEEN_SUCCESIVES_MEASURES_MIN_VALUE_MILLISECONDS;i<=TIME_BETWEEN_SUCCESIVES_MEASURES_MAX_VALUE_MILLISECONDS;i=i+TIME_BETWEEN_SUCCESIVES_MEASURES_MAX_VALUE_MILLISECONDS_INCREMENT){
				timeBetweenSuccesivesMeasuresComboBox.addItem (Long.toString(i));
			}
		}
		return timeBetweenSuccesivesMeasuresComboBox;
	}

	/**
	 * This method initializes programTypeComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getProgramTypeComboBox() {
		if (programTypeComboBox == null) {
			programTypeComboBox = new JComboBox();
			programTypeComboBox.setBounds(new Rectangle(135, 135, 376, 31));
			programTypeComboBox.setMaximumRowCount(3);
			programTypeComboBox.addItem("TTC Thermal Calibration");
		}
		return programTypeComboBox;
	}

	/**
	 * This method initializes jNPassesBelowStandardDeviationComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJNPassesBelowStandardDeviationComboBox() {
		if (nPassesBelowStandardDeviationComboBox == null) {
			nPassesBelowStandardDeviationComboBox = new JComboBox();
			nPassesBelowStandardDeviationComboBox.setBounds(new Rectangle(270, 240, 106, 31));
			for (int i=N_PASSES_BELOW_STANDARD_DEVIATION_MIN_VALUE;i<=N_PASSES_BELOW_STANDARD_DEVIATION_MAX_VALUE;i=i+N_PASSES_BELOW_STANDARD_DEVIATION_INCREMENT){
				nPassesBelowStandardDeviationComboBox.addItem (Integer.toString(i));
			}
		}
		return nPassesBelowStandardDeviationComboBox;
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
		        		standardDeviationComboBox.enable(true);
		        		nMeasuresPerCicleComboBox.enable(true);
		        		timeBetweenSuccesivesMeasuresComboBox.enable(true);
		        		nPassesBelowStandardDeviationComboBox.enable(true);
		        		temperatureStabilitzationTimejComboBox.enable(false);
		        		standardDeviationComboBox.setForeground(Color.BLACK);
		        		nMeasuresPerCicleComboBox.setForeground(Color.BLACK);
		        		timeBetweenSuccesivesMeasuresComboBox.setForeground(Color.BLACK);
		        		nPassesBelowStandardDeviationComboBox.setForeground(Color.BLACK);
		        		temperatureStabilitzationTimejComboBox.setForeground(Color.LIGHT_GRAY);
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
		        		standardDeviationComboBox.enable(false);
		        		nMeasuresPerCicleComboBox.enable(false);
		        		timeBetweenSuccesivesMeasuresComboBox.enable(false);
		        		nPassesBelowStandardDeviationComboBox.enable(false);
		        		temperatureStabilitzationTimejComboBox.enable(true);
		        		standardDeviationComboBox.setForeground(Color.LIGHT_GRAY);
		        		nMeasuresPerCicleComboBox.setForeground(Color.LIGHT_GRAY);
		        		timeBetweenSuccesivesMeasuresComboBox.setForeground(Color.LIGHT_GRAY);
		        		nPassesBelowStandardDeviationComboBox.setForeground(Color.LIGHT_GRAY);
		        		temperatureStabilitzationTimejComboBox.setForeground(Color.BLACK);
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
		if (temperatureStabilitzationTimejComboBox == null) {
			temperatureStabilitzationTimejComboBox = new JComboBox();
			temperatureStabilitzationTimejComboBox.setBounds(new Rectangle(270, 315, 106, 31));
			for (int i=TEMPERATURE_STABILITZATION_TIME_MINUTES_MIN_VALUE;i<=TEMPERATURE_STABILITZATION_TIME_MINUTES_MAX_VALUE;i=i+TEMPERATURE_STABILITZATION_TIME_MINUTES_INCREMENT){
				temperatureStabilitzationTimejComboBox.addItem (Integer.toString(i));
			}
		}
		return temperatureStabilitzationTimejComboBox;
	}

	/**
	 * This method initializes tempProfileTypejComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTempProfileTypejComboBox() {
		if (tempProfileTypejComboBox == null) {
			tempProfileTypejComboBox = new JComboBox();
			tempProfileTypejComboBox.setBounds(new Rectangle(29, 101, 87, 20));
			tempProfileTypejComboBox.addItem("Sweep");
			tempProfileTypejComboBox.addItem("Constant");
			tempProfileTypejComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if (e.getItem().equals("Sweep")){
		        		
		        	}
		        	if (e.getItem().equals("Constant")){
		        		
		        	}
		        }
		    };
		    tempProfileTypejComboBox.addItemListener(tempProfileTypejComboBoxItemListener);
		}
		return tempProfileTypejComboBox;
	}

	/**
	 * This method initializes constantTemperaturejComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getConstantTemperaturejComboBox() {
		if (constantTemperaturejComboBox == null) {
			constantTemperaturejComboBox = new JComboBox();
			constantTemperaturejComboBox.setBounds(new Rectangle(275, 295, 61, 21));
			for (int i=MIN_TEMP_OVEN;i<=MAX_TEMP_OVEN;i=i+TEMP_OVEN_INCREMENT){
				constantTemperaturejComboBox.addItem (Integer.toString(i));
			}
		}
		return constantTemperaturejComboBox;
	}

	/**
	 * This method initializes totalTimeAtConstantTempjComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTotalTimeAtConstantTempjComboBox() {
		if (totalTimeAtConstantTempjComboBox == null) {
			totalTimeAtConstantTempjComboBox = new JComboBox();
			totalTimeAtConstantTempjComboBox.setBounds(new Rectangle(275, 325, 61, 21));
			for (int i=MIN_TIME_AT_CONSTANT_TEMP_HOURS;i<=MAX_TIME_AT_CONSTANT_TEMP_HOURS;i=i+TIME_AT_CONSTANT_TEMP_INCREMENT_HOURS){
				totalTimeAtConstantTempjComboBox.addItem (Integer.toString(i));
			}
		}
		return totalTimeAtConstantTempjComboBox;
	}

	/**
	 * This method initializes nMeasuresAtConstantTempjComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getNMeasuresAtConstantTempjComboBox() {
		if (nMeasuresAtConstantTempjComboBox == null) {
			nMeasuresAtConstantTempjComboBox = new JComboBox();
			nMeasuresAtConstantTempjComboBox.setBounds(new Rectangle(275, 355, 61, 21));
			for (int i=MIN_N_MEASURES_AT_CONSTANT_TEMP;i<=MAX_N_MEASURES_AT_CONSTANT_TEMP;i=i+N_MEASURES_AT_CONSTANT_TEMP_INCREMENT){
				nMeasuresAtConstantTempjComboBox.addItem (Integer.toString(i));
			}
		}
		return nMeasuresAtConstantTempjComboBox;
	}

	/**
	 * This method initializes measuresAtConstantTempEveryjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMeasuresAtConstantTempEveryjTextField() {
		if (measuresAtConstantTempEveryjTextField == null) {
			measuresAtConstantTempEveryjTextField = new JTextField();
			measuresAtConstantTempEveryjTextField.setBounds(new Rectangle(275, 385, 61, 21));
			measuresAtConstantTempEveryjTextField.setEnabled(false);
			measuresAtConstantTempEveryjTextField.setBackground(new Color(238, 238, 238));
			measuresAtConstantTempEveryjTextField.setText("1");
		}	
		return measuresAtConstantTempEveryjTextField;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ver.");
				String programFilePath = "c:\\prueba1.xml";
			       //This is where a real application would open the file.
		            //log.append("Opening: " + file.getName() + "." + newline);
		            System.out.println("Creando la instancia para el programa.");
		    		ThermalCalibrationProgramData _program = null;
					try {
						_program = new ThermalCalibrationProgramData(programFilePath);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		System.out.println(_program.toString());

				ThermalCalibrationProgramViewerFrame thisClass = new ThermalCalibrationProgramViewerFrame(_program,1,null);
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
