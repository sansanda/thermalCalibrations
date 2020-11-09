package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import calibrationSetUp.CalibrationSetUp_TemperatureStabilizationCriteria;


public class CalibrationSetUp_TemperatureStabilizationCriteria_JPanel extends JPanel {

	//Constants
	private static final long 	serialVersionUID = 1L;
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
	private static final int 	N_MEASURES_PER_WINDOW_MIN_VALUE = 10;
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
	//Variables
	private JLabel 				maxAdmissibleSteadyStateErrorLabel1;
	private JLabel 				temperatureStabilitzationMethodjLabel;
	private JLabel 				maxAdmissibleSteadyStateErrorLabel;
	private JLabel 				stDevLabel;
	private JLabel 				measurementWindowLabel;
	private JLabel 				measurementWindowLabel2;
	private JLabel 				samplingPeriodLabel;
	private JLabel 				samplingPeriodLabel2;
	private JLabel 				nOfSuccessiveValidWindowsUnderStDev;
	private JLabel 				nOfSuccessiveValidWindowsUnderStDev2;
	private JLabel 				temperatureStabiltizationTimejLabel;
	private JLabel 				temperatureStabiltizationTimejLabel2;
	private JComboBox 			maxAdmissibleSteadyStateErrorComboBox;
	private JComboBox 			stDevComboBox;  //  @jve:decl-index=0:
	private JComboBox 			measurementWindowComboBox;
	private JTextField 			samplingPeriodeTextField;
	private JComboBox 			numberOfSuccessiveWindowsUnderStDevComboBox;
	private JRadioButton 		temperatureStabilitzationByStDevjRadioButton;  //  @jve:decl-index=0:
	private JRadioButton 		temperatureStabilitzationByTimejRadioButton;  //  @jve:decl-index=0:
	private JComboBox 			temperatureStabilitzationTimeComboBox;
	private JLabel 				firstTemperatureStepStabiltizationTimejLabel;  //  @jve:decl-index=0:
	private JLabel 				firstTemperatureStepStabiltizationTimejLabel2;
	private JComboBox 			firstTemperatureStepStabiltizationTimeComboBox;
	private JLabel 				temperatureStabilizationCriteriumConfigurationLabel;

	private ItemListener 		temperatureStabilitzationByTimejRadioButtonItemListener = null;
	private ItemListener 		temperatureStabilitzationByStDevjRadioButtonItemListener = null;  //  @jve:decl-index=0:


	/**
	 * This is the default constructor
	 */
	public CalibrationSetUp_TemperatureStabilizationCriteria_JPanel(String _panelName) {
		super();
		initialize(_panelName);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(String _panelName) {
		this.setBounds(new Rectangle(30, 30, 557, 406));
		this.setName(_panelName);
		this.setLayout(null);
		this.add(createTemperatureStabilizationCriteriumConfigurationLabel(), null);
		this.add(createMaxAdmissibleSteadyStateErrorLabel(), null);
		this.add(createStDevLabel(), null);
		this.add(createMeasurementWindowLabel(), null);
		this.add(createSamplingPeriodLabel(), null);
		this.add(createMaximunAdmissibleSteadyStateErrorComboBox(), null);
		this.add(createStandardDeviationComboBox(), null);
		this.add(createNMeasuresPerCicleComboBox(), null);
		this.add(createSamplingPeriodeTextField(), null);
		this.add(createJNPassesBelowStandardDeviationComboBox(), null);
		this.add(createNOfSuccessiveValidWindowsUnderStDev(), null);
		this.add(createTemperatureStabilitzationByStDevjRadioButton(), null);
		this.add(createTemperatureStabilitzationByTimejRadioButton(), null);
		this.add(createTemperatureStabilitzationMethodjLabel(), null);
		this.add(createTemperatureStabiltizationTimejLabel(), null);
		this.add(createTemperatureStabilitzationTimejComboBox(), null);
		this.add(createMeasurementWindowLabel2(), null);
		this.add(createSamplingPeriodLabel2(), null);
		this.add(createNOfSuccessiveValidWindowsUnderStDev2(), null);
		this.add(createTemperatureStabiltizationTimejLabel2(), null);
		this.add(createMaxAdmissibleSteadyStateErrorLabel1(), null);
		this.add(createFirstTemperatureStepStabiltizationTimejLabel(), null);
		this.add(createFirstTemperatureStepStabiltizationTimejLabel2(), null);
		this.add(createFirstTemperatureStepStabiltizationTimejComboBox(), null);
		this.setDefaultTemperatureStabilizationCriteriumData();

	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the temperatureStabilizationCriteriumConfigurationLabel
	 */
	public JLabel createTemperatureStabilizationCriteriumConfigurationLabel() {
		if(temperatureStabilizationCriteriumConfigurationLabel==null){
			temperatureStabilizationCriteriumConfigurationLabel = new JLabel();
			temperatureStabilizationCriteriumConfigurationLabel.setBounds(new Rectangle(30, 30, 512, 31));
			temperatureStabilizationCriteriumConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilizationCriteriumConfigurationLabel.setText("TEMPERATURE STABILIZATION CRITERIUM ");
			temperatureStabilizationCriteriumConfigurationLabel.setBackground(new Color(204, 204, 255));
		}
		return temperatureStabilizationCriteriumConfigurationLabel;
	}
	/**
	 * This method initializes standardDeviationComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox createMaximunAdmissibleSteadyStateErrorComboBox() {
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
	private JComboBox createStandardDeviationComboBox() {
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
	private JComboBox createNMeasuresPerCicleComboBox() {
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
	private JTextField createSamplingPeriodeTextField() {
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
	private JComboBox createJNPassesBelowStandardDeviationComboBox() {
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
	private JRadioButton createTemperatureStabilitzationByStDevjRadioButton() {
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
	private JRadioButton createTemperatureStabilitzationByTimejRadioButton() {
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
	private JComboBox createTemperatureStabilitzationTimejComboBox() {
		if (temperatureStabilitzationTimeComboBox == null) {
			temperatureStabilitzationTimeComboBox = new JComboBox();
			temperatureStabilitzationTimeComboBox.setBounds(new Rectangle(270, 285, 106, 31));
			for (int i=TEMPERATURE_STABILITZATION_TIME_MINUTES_MIN_VALUE;i<=TEMPERATURE_STABILITZATION_TIME_MINUTES_MAX_VALUE;i=i+TEMPERATURE_STABILITZATION_TIME_MINUTES_INCREMENT){
				temperatureStabilitzationTimeComboBox.addItem (Integer.toString(i));
			}
		}
		return temperatureStabilitzationTimeComboBox;
	}
	private JLabel createFirstTemperatureStepStabiltizationTimejLabel() {
		if (firstTemperatureStepStabiltizationTimejLabel == null) {
			firstTemperatureStepStabiltizationTimejLabel = new JLabel();
			firstTemperatureStepStabiltizationTimejLabel.setBounds(new Rectangle(30, 345, 226, 31));
			firstTemperatureStepStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			firstTemperatureStepStabiltizationTimejLabel.setText("First Temp Step Stabilitzation Time");
		}
		return firstTemperatureStepStabiltizationTimejLabel;
	}

	private JLabel createFirstTemperatureStepStabiltizationTimejLabel2() {
		if (firstTemperatureStepStabiltizationTimejLabel2 == null) {
			firstTemperatureStepStabiltizationTimejLabel2 = new JLabel();
			firstTemperatureStepStabiltizationTimejLabel2.setBounds(new Rectangle(390, 345, 151, 31));
			firstTemperatureStepStabiltizationTimejLabel2.setText("Minutes");
			firstTemperatureStepStabiltizationTimejLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return firstTemperatureStepStabiltizationTimejLabel2;
	}
	private JComboBox createFirstTemperatureStepStabiltizationTimejComboBox() {
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
	 * @return the maxAdmissibleSteadyStateErrorLabel1
	 */
	private JLabel createMaxAdmissibleSteadyStateErrorLabel1() {
		if (maxAdmissibleSteadyStateErrorLabel1==null){
			maxAdmissibleSteadyStateErrorLabel1 = new JLabel();
			maxAdmissibleSteadyStateErrorLabel1.setBounds(new Rectangle(390, 240, 151, 31));
			maxAdmissibleSteadyStateErrorLabel1.setHorizontalAlignment(SwingConstants.LEFT);
			maxAdmissibleSteadyStateErrorLabel1.setText("ºC");
			maxAdmissibleSteadyStateErrorLabel1.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		}
		return maxAdmissibleSteadyStateErrorLabel1;
	}

	/**
	 * @return the temperatureStabilitzationMethodjLabel
	 */
	private JLabel createTemperatureStabilitzationMethodjLabel() {
		if (temperatureStabilitzationMethodjLabel==null){
			temperatureStabilitzationMethodjLabel = new JLabel();
			temperatureStabilitzationMethodjLabel.setBounds(new Rectangle(30, 75, 226, 31));
			temperatureStabilitzationMethodjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureStabilitzationMethodjLabel.setText("Temperature Stabilitzation Method");
		}
		return temperatureStabilitzationMethodjLabel;
	}

	/**
	 * @return the maxAdmissibleSteadyStateErrorLabel
	 */
	private JLabel createMaxAdmissibleSteadyStateErrorLabel() {
		if (maxAdmissibleSteadyStateErrorLabel==null){
			maxAdmissibleSteadyStateErrorLabel = new JLabel();
			maxAdmissibleSteadyStateErrorLabel.setBounds(new Rectangle(30, 240, 226, 31));
			maxAdmissibleSteadyStateErrorLabel.setHorizontalAlignment(SwingConstants.LEFT);
			maxAdmissibleSteadyStateErrorLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			maxAdmissibleSteadyStateErrorLabel.setText("Max Temperature Steady State Error");
		}
		return maxAdmissibleSteadyStateErrorLabel;
	}

	/**
	 * @return the stDevLabel
	 */
	private JLabel createStDevLabel() {
		if (stDevLabel==null){
			stDevLabel = new JLabel();
			stDevLabel.setBounds(new Rectangle(30, 120, 226, 31));
			stDevLabel.setHorizontalAlignment(SwingConstants.LEFT);
			stDevLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			stDevLabel.setText("Standard Deviation");
		}
		return stDevLabel;
	}

	/**
	 * @return the measurementWindowLabel
	 */
	private JLabel createMeasurementWindowLabel() {
		if (measurementWindowLabel==null){
			measurementWindowLabel = new JLabel();
			measurementWindowLabel.setBounds(new Rectangle(30, 150, 226, 31));
			measurementWindowLabel.setText("Measurement Window");
			measurementWindowLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return measurementWindowLabel;
	}

	/**
	 * @return the measurementWindowLabel2
	 */
	private JLabel createMeasurementWindowLabel2() {
		if (measurementWindowLabel2==null){
			measurementWindowLabel2 = new JLabel();
			measurementWindowLabel2.setBounds(new Rectangle(390, 150, 151, 31));
			measurementWindowLabel2.setText("Number of Samples");
			measurementWindowLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return measurementWindowLabel2;
	}

	/**
	 * @return the samplingPeriodLabel
	 */
	private JLabel createSamplingPeriodLabel() {
		if (samplingPeriodLabel==null){
			samplingPeriodLabel = new JLabel();
			samplingPeriodLabel.setBounds(new Rectangle(30, 180, 226, 31));
			samplingPeriodLabel.setText("Sampling Period");
			samplingPeriodLabel.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return samplingPeriodLabel;
	}

	/**
	 * @return the samplingPeriodLabel2
	 */
	private JLabel createSamplingPeriodLabel2() {
		if (samplingPeriodLabel2==null){
			samplingPeriodLabel2 = new JLabel();
			samplingPeriodLabel2.setBounds(new Rectangle(390, 180, 151, 31));
			samplingPeriodLabel2.setText("Milliseconds");
			samplingPeriodLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return samplingPeriodLabel2;
	}

	/**
	 * @return the nOfSuccessiveValidWindowsUnderStDev
	 */
	private JLabel createNOfSuccessiveValidWindowsUnderStDev() {
		if (nOfSuccessiveValidWindowsUnderStDev==null){
			nOfSuccessiveValidWindowsUnderStDev = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev.setBounds(new Rectangle(30, 210, 226, 31));
			nOfSuccessiveValidWindowsUnderStDev.setHorizontalAlignment(SwingConstants.LEFT);
			nOfSuccessiveValidWindowsUnderStDev.setText("N of Successive Valid Windows");
		}
		return nOfSuccessiveValidWindowsUnderStDev;
	}

	/**
	 * @return the nOfSuccessiveValidWindowsUnderStDev2
	 */
	private JLabel createNOfSuccessiveValidWindowsUnderStDev2() {
		if (nOfSuccessiveValidWindowsUnderStDev2==null){
			nOfSuccessiveValidWindowsUnderStDev2 = new JLabel();
			nOfSuccessiveValidWindowsUnderStDev2.setBounds(new Rectangle(390, 210, 151, 31));
			nOfSuccessiveValidWindowsUnderStDev2.setText("Number of Windows");
			nOfSuccessiveValidWindowsUnderStDev2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return nOfSuccessiveValidWindowsUnderStDev2;
	}

	/**
	 * @return the temperatureStabiltizationTimejLabel
	 */
	private JLabel createTemperatureStabiltizationTimejLabel() {
		if (temperatureStabiltizationTimejLabel==null){
			temperatureStabiltizationTimejLabel = new JLabel();
			temperatureStabiltizationTimejLabel.setBounds(new Rectangle(30, 285, 226, 31));
			temperatureStabiltizationTimejLabel.setHorizontalAlignment(SwingConstants.LEFT);
			temperatureStabiltizationTimejLabel.setText("StabilitzationTime");
		}
		return temperatureStabiltizationTimejLabel;
	}

	/**
	 * @return the temperatureStabiltizationTimejLabel2
	 */
	private JLabel createTemperatureStabiltizationTimejLabel2() {
		if (temperatureStabiltizationTimejLabel2==null){
			temperatureStabiltizationTimejLabel2 = new JLabel();
			temperatureStabiltizationTimejLabel2.setBounds(new Rectangle(390, 285, 151, 31));
			temperatureStabiltizationTimejLabel2.setText("Minutes");
			temperatureStabiltizationTimejLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return temperatureStabiltizationTimejLabel2;
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
	public void loadTemperatureStabilizationCriteriumData(CalibrationSetUp_TemperatureStabilizationCriteria _temperatureStabilizationCriteriumData){
		this.firstTemperatureStepStabiltizationTimeComboBox.setSelectedItem(Integer.toString(_temperatureStabilizationCriteriumData.getFirstTemperatureStepStabilitzationTimeInMinutes()));
		this.maxAdmissibleSteadyStateErrorComboBox.setSelectedItem(Double.toString(_temperatureStabilizationCriteriumData.getMaxAdminssibleTemperatureError()));
		this.stDevComboBox.setSelectedItem(Double.toString(_temperatureStabilizationCriteriumData.getStDev()));
		this.measurementWindowComboBox.setSelectedItem(Integer.toString(_temperatureStabilizationCriteriumData.getMeasurementWindow()));
		this.samplingPeriodeTextField.setText(Long.toString(_temperatureStabilizationCriteriumData.getSamplingPeriode()));
		this.numberOfSuccessiveWindowsUnderStDevComboBox.setSelectedItem(Integer.toString(_temperatureStabilizationCriteriumData.getNumberOfWindowsUnderStDev()));
		this.temperatureStabilitzationTimeComboBox.setSelectedItem(Integer.toString(_temperatureStabilizationCriteriumData.getTemperatureStabilitzationTime()));
		int temperatureStabilitzationMethod = _temperatureStabilizationCriteriumData.getTemperatureStabilitzationMethod();
		// 0 temperature stabilitzation by St Dev, 1 temperature stabilitzation by Time
		if (temperatureStabilitzationMethod==0) this.temperatureStabilitzationByStDevjRadioButton.setSelected(true);
		if (temperatureStabilitzationMethod==1) this.temperatureStabilitzationByTimejRadioButton.setSelected(true);
	}
	public CalibrationSetUp_TemperatureStabilizationCriteria getTemperatureStabilizationCriteria() throws NumberFormatException, Exception{
		if (!this.validateTemperatureStabilizationCriteriumData()){
			return null;
		}
		int temperatureStabilitzationMethod = 0; //By Default St Dev
		if (temperatureStabilitzationByTimejRadioButton.isSelected()) temperatureStabilitzationMethod = 1;
		return new CalibrationSetUp_TemperatureStabilizationCriteria(Integer.parseInt((String)firstTemperatureStepStabiltizationTimeComboBox.getSelectedItem()),Double.parseDouble((String)this.maxAdmissibleSteadyStateErrorComboBox.getSelectedItem()),Double.parseDouble((String)this.stDevComboBox.getSelectedItem()),Integer.parseInt((String)this.measurementWindowComboBox.getSelectedItem()),Long.parseLong((String)this.samplingPeriodeTextField.getText()),Integer.parseInt((String)this.numberOfSuccessiveWindowsUnderStDevComboBox.getSelectedItem()),temperatureStabilitzationMethod, Integer.parseInt((String)this.temperatureStabilitzationTimeComboBox.getSelectedItem()));
	}
	public boolean validateTemperatureStabilizationCriteriumData(){
		return true;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
