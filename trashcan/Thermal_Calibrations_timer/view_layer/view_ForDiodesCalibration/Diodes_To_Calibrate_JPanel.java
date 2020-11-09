package view_ForDiodesCalibration;

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

import profiles.DiodeToCalibrateData;
import profiles.DiodesToCalibrateData;
import profiles.TTCSetUpData;
import profiles.TTCToCalibrateData;
import profiles.TTCsToCalibrateData;
import profiles.TemperatureSensorData;



public class Diodes_To_Calibrate_JPanel extends JPanel {

	//Constants
	private static final long 	serialVersionUID = 1L;
	private static final int 	N_DIODES_TO_CALIBRATE = 7;
	private static final int 	N_MULTIMETER_CHANNELS = 10;
	private static final int 	MIN_MULTIMETER_CHANNEL_NUMBER = 1;

	private static final double DIODES_CURRENT_INmA_MAX_VALUE = 10000;
	private static final double DIODES_CURRENT_INmA_MIN_VALUE = 1;
	private static final double DIODES_CURRENT_INmA_INCREMENT = 0.1;

	//Variables
	private TemperatureSensorData 	temperatureSensorData = null;

	private JLabel 		diodesToCalibrateConfigurationLabel = null;
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


	private ItemListener 	diode1MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode2MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode3MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode4MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode5MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode6MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	diode7MultimeterChannelComboBoxItemListener = null;  //  @jve:decl-index=0:

	private JTextField 		warningTextField = null;
	private JTextField temperatureSensorChannelIndicatorTextField = null;
	private JLabel diodesCurrentjLabel = null;
	private JLabel diodesCurrent2jLabel = null;
	private JTextField diodesCurrentInMilliampsjTextField = null;

	/**
	 * This is the default constructor
	 * @throws Exception
	 */
	public Diodes_To_Calibrate_JPanel(String _panelName, TemperatureSensorData 	_temperatureSensorData) throws Exception {
		super();
		initialize(_panelName,_temperatureSensorData);
	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * This method initializes this
	 *
	 * @return void
	 * @throws Exception
	 */
	private void initialize(String _panelName,TemperatureSensorData _temperatureSensorData) throws Exception {
		this.temperatureSensorData = _temperatureSensorData;
		this.setBounds(new Rectangle(30, 30, 528, 406));
		this.setName(_panelName);
		this.setLayout(null);

		diodesCurrent2jLabel = new JLabel();
		diodesCurrent2jLabel.setBounds(new Rectangle(195, 210, 76, 16));
		diodesCurrent2jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		diodesCurrent2jLabel.setText("MilliAmps");
		diodesCurrentjLabel = new JLabel();
		diodesCurrentjLabel.setBounds(new Rectangle(30, 210, 91, 16));
		diodesCurrentjLabel.setText("Diodes Current");
		diodesToCalibrateConfigurationLabel = new JLabel();
		diodesToCalibrateConfigurationLabel.setBounds(new Rectangle(30, 30, 481, 31));
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

		this.add(diodesToCalibrateConfigurationLabel, null);
		this.add(diodeMultimeterChannelLabel, null);
		this.add(diodeRefLabel, null);

		this.add(diode1Label, null);
		this.add(diode2Label, null);
		this.add(diode3Label, null);
		this.add(diode4Label, null);
		this.add(diode5Label, null);
		this.add(diode6Label, null);
		this.add(diode7Label, null);

		this.add(getDiode1RefTextField(), null);
		this.add(getDiode2RefTextField(), null);
		this.add(getDiode3RefTextField(), null);
		this.add(getDiode4RefTextField(), null);
		this.add(getDiode5RefTextField(), null);
		this.add(getDiode6RefTextField(), null);
		this.add(getDiode7RefTextField(), null);

		this.add(getDiode1CheckBox(), null);
		this.add(getDiode2CheckBox(), null);
		this.add(getDiode3CheckBox(), null);
		this.add(getDiode4CheckBox(), null);
		this.add(getDiode5CheckBox(), null);
		this.add(getDiode6CheckBox(), null);
		this.add(getDiode7CheckBox(), null);

		this.add(getDiode1MultimeterChannelComboBox(), null);
		this.add(getDiode2MultimeterChannelComboBox(), null);
		this.add(getDiode3MultimeterChannelComboBox(), null);
		this.add(getDiode4MultimeterChannelComboBox(), null);
		this.add(getDiode5MultimeterChannelComboBox(), null);
		this.add(getDiode6MultimeterChannelComboBox(), null);
		this.add(getDiode7MultimeterChannelComboBox(), null);

		this.add(getWarningTextField(), null);
		this.add(getTemperatureSensorChannelIndicatorTextField(), null);
		this.add(diodesCurrentjLabel, null);
		this.add(diodesCurrent2jLabel, null);
		this.add(getDiodesCurrentInMilliampsjTextField(), null);

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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
			for (int i=MIN_MULTIMETER_CHANNEL_NUMBER;i<=N_MULTIMETER_CHANNELS;i++){
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
	 * This method initializes temperatureSensorChannelIndicatorTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTemperatureSensorChannelIndicatorTextField() {
		if (temperatureSensorChannelIndicatorTextField == null) {
			temperatureSensorChannelIndicatorTextField = new JTextField();
			temperatureSensorChannelIndicatorTextField.setBounds(new Rectangle(30, 240, 481, 61));
			temperatureSensorChannelIndicatorTextField.setBackground(new Color(238, 238, 238));
			temperatureSensorChannelIndicatorTextField.setHorizontalAlignment(JTextField.CENTER);
			temperatureSensorChannelIndicatorTextField.setText(" WARNING: Temperature Sensor in Multimeter Channel Number "+Integer.toString(temperatureSensorData.getChannel()));
		}
		return temperatureSensorChannelIndicatorTextField;
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
	private void setDefaultDiodesToCalibrateData(){
		this.diode1CheckBox.setSelected(true);
		this.diode1RefTextField.setText("Diode1");
		this.diode1MultimeterChannelComboBox.setSelectedItem("1");
		this.diode2CheckBox.setSelected(false);
		this.diode2RefTextField.setText("Diode2");
		this.diode2MultimeterChannelComboBox.setSelectedItem("2");
		this.diode3CheckBox.setSelected(false);
		this.diode3RefTextField.setText("Diode3");
		this.diode3MultimeterChannelComboBox.setSelectedItem("3");
		this.diode4CheckBox.setSelected(false);
		this.diode4RefTextField.setText("Diode4");
		this.diode4MultimeterChannelComboBox.setSelectedItem("4");
		this.diode5CheckBox.setSelected(false);
		this.diode5RefTextField.setText("Diode5");
		this.diode5MultimeterChannelComboBox.setSelectedItem("5");
		this.diode6CheckBox.setSelected(false);
		this.diode6RefTextField.setText("Diode6");
		this.diode6MultimeterChannelComboBox.setSelectedItem("6");
		this.diode7CheckBox.setSelected(false);
		this.diode7RefTextField.setText("Diode7");
		this.diode7MultimeterChannelComboBox.setSelectedItem("7");

		this.diodesCurrentInMilliampsjTextField.setText("1.0");
	}
	public void loadDiodesToCalibrateData(DiodesToCalibrateData _diodesToCalibrate_Data){

		this.diode1CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[0].isSelected());
		this.diode1RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[0].getDeviceReference());
		this.diode1MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[0].getDeviceChannel());

		this.diode2CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[1].isSelected());
		this.diode2RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[1].getDeviceReference());
		this.diode2MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[1].getDeviceChannel());

		this.diode3CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[2].isSelected());
		this.diode3RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[2].getDeviceReference());
		this.diode3MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[2].getDeviceChannel());

		this.diode4CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[3].isSelected());
		this.diode4RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[3].getDeviceReference());
		this.diode4MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[3].getDeviceChannel());

		this.diode5CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[4].isSelected());
		this.diode5RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[4].getDeviceReference());
		this.diode5MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[4].getDeviceChannel());

		this.diode6CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[5].isSelected());
		this.diode6RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[5].getDeviceReference());
		this.diode6MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[5].getDeviceChannel());

		this.diode7CheckBox.setSelected(_diodesToCalibrate_Data.getDiodesToMeasureData()[6].isSelected());
		this.diode7RefTextField.setText(_diodesToCalibrate_Data.getDiodesToMeasureData()[6].getDeviceReference());
		this.diode7MultimeterChannelComboBox.setSelectedItem(_diodesToCalibrate_Data.getDiodesToMeasureData()[6].getDeviceChannel());

		this.diodesCurrentInMilliampsjTextField.setText(Double.toString(_diodesToCalibrate_Data.getDevicesCurrentInMilliAmps()));
	}
	public DiodesToCalibrateData getDiodesToCalibrateData() throws Exception{
		  DiodeToCalibrateData[] deviceToMeasureData = new DiodeToCalibrateData[N_DIODES_TO_CALIBRATE];
		  System.out.println("---------------------------> "+(Integer)diode1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[0] = new DiodeToCalibrateData(diode1CheckBox.isSelected(),diode1RefTextField.getText(),(Integer)diode1MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[1] = new DiodeToCalibrateData(diode2CheckBox.isSelected(),diode2RefTextField.getText(),(Integer)diode2MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[2] = new DiodeToCalibrateData(diode3CheckBox.isSelected(),diode3RefTextField.getText(),(Integer)diode3MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[3] = new DiodeToCalibrateData(diode4CheckBox.isSelected(),diode4RefTextField.getText(),(Integer)diode4MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[4] = new DiodeToCalibrateData(diode5CheckBox.isSelected(),diode5RefTextField.getText(),(Integer)diode5MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[5] = new DiodeToCalibrateData(diode6CheckBox.isSelected(),diode6RefTextField.getText(),(Integer)diode6MultimeterChannelComboBox.getSelectedItem());
		  deviceToMeasureData[6] = new DiodeToCalibrateData(diode7CheckBox.isSelected(),diode7RefTextField.getText(),(Integer)diode7MultimeterChannelComboBox.getSelectedItem());

		  return new DiodesToCalibrateData(deviceToMeasureData, Double.parseDouble(diodesCurrentInMilliampsjTextField.getText()));

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
}  //  @jve:decl-index=0:visual-constraint="10,10"
