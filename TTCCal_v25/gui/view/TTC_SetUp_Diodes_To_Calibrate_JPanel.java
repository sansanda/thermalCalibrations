package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Data.TTCSetUpData;
import Data.TTCToCalibrateData;
import Data.TTCsToCalibrateData;
import Data.TemperatureSensorData;

public class TTC_SetUp_Diodes_To_Calibrate_JPanel extends JPanel {

	//Constants
	private static final long 	serialVersionUID = 1L;
	private static final int 	N_DIODES_TO_CALIBRATE = 7;
	private static final int 	N_MULTIMETER_CHANNELS = 10;
	private static final int 	MIN_MULTIMETER_CHANNEL_NUMBER = 1;

	//Variables
	private TemperatureSensorData 	temperatureSensorData = null;

	private JLabel 		ttcMultimeterChannelLabel = null;
	private JLabel 		ttcRefLabel = null;
	private JLabel 		ttcsToCalibrateConfigurationLabel = null;

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

	private JTextField 		warningTextField = null;
	private JTextField temperatureSensorChannelIndicatorTextField = null;

	private ItemListener 	ttc1MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc2MultimeterChannelComboBoxItemListener = null;
	private ItemListener 	ttc3MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc4MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc5MultimeterChannelComboBoxItemListener = null;
	private ItemListener 	ttc6MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	ttc7MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:



	/**
	 * This is the default constructor
	 * @throws Exception
	 */
	public TTC_SetUp_Diodes_To_Calibrate_JPanel(String _panelName, TemperatureSensorData 	_temperatureSensorData) throws Exception {
		super();
		initialize(_panelName,_temperatureSensorData);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 * @throws Exception
	 */
	private void initialize(String _panelName,TemperatureSensorData _temperatureSensorData) throws Exception {
		this.temperatureSensorData = _temperatureSensorData;
		this.setBounds(new Rectangle(30, 30, 676, 406));
		this.setName(_panelName);
		this.setLayout(null);

		ttcsToCalibrateConfigurationLabel = new JLabel();
		ttcsToCalibrateConfigurationLabel.setBounds(new Rectangle(75, 30, 516, 31));
		ttcsToCalibrateConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ttcsToCalibrateConfigurationLabel.setText("DIODES TO CALIBRATE");
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

		this.add(ttcsToCalibrateConfigurationLabel, null);
		this.add(ttcMultimeterChannelLabel, null);
		this.add(ttcRefLabel, null);

		this.add(ttc1Label, null);
		this.add(ttc2Label, null);
		this.add(ttc3Label, null);
		this.add(ttc4Label, null);
		this.add(ttc5Label, null);
		this.add(ttc6Label, null);
		this.add(ttc7Label, null);

		this.add(getTTC1RefTextField(), null);
		this.add(getTTC2RefTextField(), null);
		this.add(getTTC3RefTextField(), null);
		this.add(getTTC4RefTextField(), null);
		this.add(getTTC5RefTextField(), null);
		this.add(getTTC6RefTextField(), null);
		this.add(getTTC7RefTextField(), null);

		this.add(getTTC1CheckBox(), null);
		this.add(getTTC2CheckBox(), null);
		this.add(getTTC3CheckBox(), null);
		this.add(getTTC4CheckBox(), null);
		this.add(getTTC5CheckBox(), null);
		this.add(getTTC6CheckBox(), null);
		this.add(getTTC7CheckBox(), null);

		this.add(getTTC1MultimeterChannelComboBox(), null);
		this.add(getTTC2MultimeterChannelComboBox(), null);
		this.add(getTTC3MultimeterChannelComboBox(), null);
		this.add(getTTC4MultimeterChannelComboBox(), null);
		this.add(getTTC5MultimeterChannelComboBox(), null);
		this.add(getTTC6MultimeterChannelComboBox(), null);
		this.add(getTTC7MultimeterChannelComboBox(), null);

		this.add(getWarningTextField(), null);
		this.add(getTemperatureSensorChannelIndicatorTextField(), null);


		this.setDefaultTTCsToCalibrateData();

	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	public TTCsToCalibrateData getTTCsToCalibrateData() throws NumberFormatException, Exception{
		if (!this.validateTTCsToCalibrateData()){
			return null;
		}
		 TTCToCalibrateData[] deviceToMeasureData = new TTCToCalibrateData[N_TTCs_TO_MEASURE];
		  System.out.println("---------------------------> "+(Integer)ttc1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[0] = new TTCToCalibrateData(ttc1CheckBox.isSelected(),ttc1RefTextField.getText(),(Integer)ttc1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[1] = new TTCToCalibrateData(ttc2CheckBox.isSelected(),ttc2RefTextField.getText(),(Integer)ttc2MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[2] = new TTCToCalibrateData(ttc3CheckBox.isSelected(),ttc3RefTextField.getText(),(Integer)ttc3MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[3] = new TTCToCalibrateData(ttc4CheckBox.isSelected(),ttc4RefTextField.getText(),(Integer)ttc4MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[4] = new TTCToCalibrateData(ttc5CheckBox.isSelected(),ttc5RefTextField.getText(),(Integer)ttc5MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[5] = new TTCToCalibrateData(ttc6CheckBox.isSelected(),ttc6RefTextField.getText(),(Integer)ttc6MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[6] = new TTCToCalibrateData(ttc7CheckBox.isSelected(),ttc7RefTextField.getText(),(Integer)ttc7MultimeterChannelComboBox.getSelectedItem());


		  return new TTCsToCalibrateData (deviceToMeasureData);

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
}  //  @jve:decl-index=0:visual-constraint="10,10"
