package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import calibrationSetUp.CalibrationSetUp_DevicesToCalibrate;
import devices.Device;
import devices.Diode;
import devices.Resistance;
import sensors.TemperatureSensor;
import uoc.ei.tads.sequencies.LlistaAfSeq;


public class CalibrationSetUp_DevicesToCalibrate_JPanel extends JPanel {

	//Constants
	private static final long 	serialVersionUID = 1L;
	private static final int 	N_DEVICES_TO_CALIBRATE = 7;
	private static final int 	N_MULTIMETER_CHANNELS = 10;
	private static final int 	MIN_MULTIMETER_CHANNEL_NUMBER = 1;

	//Variables
	private TemperatureSensor 	temperatureSensor = null;

	private JLabel 			devicesToCalibrateConfiguration_JLabel = null;
	private JLabel 			devicesMultimeterChannel_JLabel = null;
	private JLabel 			devicesRef_JLabel = null;

	private LlistaAfSeq 	devicesLabel_JLabel = null;
	private LlistaAfSeq 	devicesRef_TextField = null;
	private LlistaAfSeq 	devicesCheckBox_CheckBox = null;
	private LlistaAfSeq 	devicesMultimeterChannel_ComboBox = null;
	private LlistaAfSeq 	devicesMultimeterChannelComboBox_ItemListener = null;
	private JTextField 		warningTextField = null;
	private JTextField 		temperatureSensorChannelIndicatorTextField = null;

	/**
	 * This is the default constructor
	 * @throws Exception
	 */
	public CalibrationSetUp_DevicesToCalibrate_JPanel(String _panelName, TemperatureSensor 	_temperatureSensorData) throws Exception {
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
	private void initialize(String _panelName,TemperatureSensor _temperatureSensorData) throws Exception {
		temperatureSensor = _temperatureSensorData;
		setBounds(new Rectangle(30, 30, 528, 406));
		setName(_panelName);
		setLayout(null);

		devicesToCalibrateConfiguration_JLabel = new JLabel();
		devicesToCalibrateConfiguration_JLabel.setBounds(new Rectangle(30, 30, 481, 31));
		devicesToCalibrateConfiguration_JLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devicesToCalibrateConfiguration_JLabel.setText("DEVICES TO CALIBRATE");
		devicesToCalibrateConfiguration_JLabel.setBackground(new Color(204, 204, 255));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.add(devicesToCalibrateConfiguration_JLabel, null);

		//Initialize devices Check Boxes
		initializeDevicesCheckBoxes();
		//Initialize devices labels
		initializeDevicesLabels();
		//Initialize devices references
		initializeDevicesReferences();
		//Initialize devices Multimeter Channel
		initializeDevicesMultimeterChannelsComboBoxes();

		this.add(getWarningTextField(), null);
		this.add(getTemperatureSensorChannelIndicatorTextField(), null);
	}
	private JCheckBox getDeviceCheckBoxNumber(int _number){
		Enumeration checkBoxesEnum = devicesCheckBox_CheckBox.elements();
		int i=1;
		boolean trobat = false;
		Object o = null;
		while (checkBoxesEnum.hasMoreElements() & !trobat){
			trobat = _number==i;
			o = checkBoxesEnum.nextElement();
			i++;
		}
		if (trobat) return (JCheckBox)o;
		else return null;
	}
	private JTextField getDeviceReferenceNumber(int _number){
		Enumeration referenceEnum = devicesRef_TextField.elements();
		int i=1;
		boolean trobat = false;
		Object o = null;
		while (referenceEnum.hasMoreElements() & !trobat){
			trobat = _number==i;
			o = referenceEnum.nextElement();
			i++;
		}
		if (trobat) return (JTextField)o;
		else return null;
	}
	private JComboBox getDeviceMultimeterChannelComboBoxNumber(int _number){
		Enumeration multimeterChannelComboBoxEnum = devicesMultimeterChannel_ComboBox.elements();
		int i=1;
		boolean trobat = false;
		Object o = null;
		while (multimeterChannelComboBoxEnum.hasMoreElements() & !trobat){
			trobat = _number==i;
			o = multimeterChannelComboBoxEnum.nextElement();
			i++;
		}
		if (trobat) return (JComboBox)o;
		else return null;
	}
	private ItemListener getDeviceMultimeterChannelComboBoxItemListenerNumber(int _number){
		Enumeration multimeterChannelComboBoxItemListenerEnum = devicesMultimeterChannelComboBox_ItemListener.elements();
		int i=1;
		boolean trobat = false;
		Object o = null;
		while (multimeterChannelComboBoxItemListenerEnum.hasMoreElements() & !trobat){
			trobat = _number==i;
			o = multimeterChannelComboBoxItemListenerEnum.nextElement();
			i++;
		}
		if (trobat) return (ItemListener)o;
		else return null;
	}
	private void initializeDevicesCheckBoxes() {
		devicesCheckBox_CheckBox = new LlistaAfSeq();
		int devicesLabelXIncrement = 60;
		int devicesLabelXOffset = 90;
		int i=1;
		while (i<=N_DEVICES_TO_CALIBRATE){
				JCheckBox checkBox= new JCheckBox();
				checkBox.setName(Integer.toString(i));
				checkBox.setBounds(new Rectangle(devicesLabelXOffset, 90, 61, 16));
				checkBox.setHorizontalAlignment(SwingConstants.CENTER);
				checkBox.addItemListener(new ItemListener()
			    {
			        public void itemStateChanged(ItemEvent e)
			        {
			        	int checkBoxNumber = Integer.valueOf(((JCheckBox)e.getSource()).getName());
			        	if (e.getStateChange()==ItemEvent.SELECTED){
			        		getDeviceReferenceNumber(checkBoxNumber).setEnabled(true);
			        		getDeviceMultimeterChannelComboBoxNumber(checkBoxNumber).setEnabled(true);
			        		getDeviceCheckBoxNumber(checkBoxNumber+1).setEnabled(true);
			        	}
			        	if (e.getStateChange()==ItemEvent.DESELECTED){
			        		getDeviceReferenceNumber(checkBoxNumber).setEnabled(false);
			        		getDeviceMultimeterChannelComboBoxNumber(checkBoxNumber).setEnabled(false);
			        		int j=checkBoxNumber+1;
			        		while (j<=N_DEVICES_TO_CALIBRATE){
			        			getDeviceCheckBoxNumber(j).setSelected(false);
			        			getDeviceCheckBoxNumber(j).setEnabled(false);
			        			getDeviceReferenceNumber(j).setEnabled(false);
				        		getDeviceMultimeterChannelComboBoxNumber(j).setEnabled(false);
				        		j++;
			        		}
			        	}
			        }
			      }
			    );
			if (i!=1) checkBox.setEnabled(false);
			devicesCheckBox_CheckBox.afegeix(checkBox);
			this.add(checkBox);
			devicesLabelXOffset = devicesLabelXOffset + devicesLabelXIncrement;
			i++;
		}
	}
	private void initializeDevicesLabels(){
		devicesLabel_JLabel = new LlistaAfSeq();
		int devicesLabelXIncrement = 60;
		int devicesLabelXOffset = 90;
		for (int i=1;i<=N_DEVICES_TO_CALIBRATE;i++){
			JLabel label = new JLabel();
			label.setText("Device"+Integer.toString(i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(new Rectangle(devicesLabelXOffset, 120, 61, 16));
			devicesLabel_JLabel.afegeix(label);
			this.add(label);
			devicesLabelXOffset = devicesLabelXOffset + devicesLabelXIncrement;
		}
	}
	private void initializeDevicesReferences() {
		devicesRef_JLabel = new JLabel();
		devicesRef_JLabel.setText("Reference");
		devicesRef_JLabel.setBounds(new Rectangle(30, 150, 61, 16));
		devicesRef_JLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(devicesRef_JLabel);

		int devicesLabelXIncrement = 60;
		int devicesLabelXOffset = 90;
		devicesRef_TextField = new LlistaAfSeq();
		for (int i=1;i<=N_DEVICES_TO_CALIBRATE;i++){
			JTextField textField = new JTextField();
			textField.setEnabled(true);
			textField.setBounds(new Rectangle(devicesLabelXOffset, 150, 61, 16));
			devicesRef_TextField.afegeix(textField);
			this.add(textField);
			devicesLabelXOffset = devicesLabelXOffset + devicesLabelXIncrement;
		}
	}
	private void initializeDevicesMultimeterChannelsComboBoxes() {

		devicesMultimeterChannel_JLabel = new JLabel();
		devicesMultimeterChannel_JLabel.setBounds(new Rectangle(30, 180, 61, 16));
		devicesMultimeterChannel_JLabel.setText("Channel");
		devicesMultimeterChannel_JLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(devicesMultimeterChannel_JLabel, null);

		devicesMultimeterChannel_ComboBox = new LlistaAfSeq();
		devicesMultimeterChannelComboBox_ItemListener = new LlistaAfSeq();

		int devicesLabelXIncrement = 60;
		int devicesLabelXOffset = 90;
		for (int i=1;i<=N_DEVICES_TO_CALIBRATE;i++){
			final JComboBox comboBox = new JComboBox();
			comboBox.setEnabled(false);
			comboBox.setBounds(new Rectangle(devicesLabelXOffset, 180, 61, 16));
			for (int j=MIN_MULTIMETER_CHANNEL_NUMBER;j<=N_MULTIMETER_CHANNELS;j++){
				if (!(temperatureSensor.getChannel()==j)) comboBox.addItem(j);
			}
			ItemListener deviceItemListener  = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	int deviceMultimeterChannel = (Integer)comboBox.getSelectedItem();
		        	if (temperatureSensor.getChannel()==deviceMultimeterChannel){
		           	  JOptionPane.showMessageDialog(null,"WARNING: TEMPERATURE SENSOR CHANNEL = " + temperatureSensor.getChannel());
		         	  JOptionPane.showMessageDialog(null,"DIODE1 CHANNEL MUST DIFFERENT FROM CHANNEL " + temperatureSensor.getChannel());
		         	  comboBox.setSelectedItem("1");
		        	}
		        }
		     };
		     comboBox.addItemListener(deviceItemListener);
		     devicesMultimeterChannelComboBox_ItemListener.afegeix(deviceItemListener);
		     devicesMultimeterChannel_ComboBox.afegeix(comboBox);
		     this.add(comboBox);
		     devicesLabelXOffset = devicesLabelXOffset + devicesLabelXIncrement;
		}
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
			temperatureSensorChannelIndicatorTextField.setText(" WARNING: Temperature Sensor in Multimeter Channel Number "+Integer.toString(temperatureSensor.getChannel()));
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
	public void loadDevicesToCalibrateData(CalibrationSetUp_DevicesToCalibrate _devicesToCalibrate){
		Enumeration devicesToCalibrateEnumeration = _devicesToCalibrate.getEnumeration();
		int i=1;
		while (devicesToCalibrateEnumeration.hasMoreElements()){
			Device d = (Device)devicesToCalibrateEnumeration.nextElement();
			getDeviceCheckBoxNumber(i).setSelected(true);
			getDeviceReferenceNumber(i).setText(d.getDeviceReference());
			getDeviceMultimeterChannelComboBoxNumber(i).setSelectedItem(d.getConnectedToMultimeterChannelNumber());
			i++;
		}
	}
	public CalibrationSetUp_DevicesToCalibrate getDevicesToCalibrateData(int _deviceType) throws Exception{
		  LlistaAfSeq devicesToCalibrate = new LlistaAfSeq();
		  Enumeration eDevices_CheckBox = devicesCheckBox_CheckBox.elements();
		  Enumeration eDevices_Reference = devicesRef_TextField.elements();
		  Enumeration eDevices_MultimeterChannel = devicesMultimeterChannel_ComboBox.elements();
		  //Enumeration
		  while (eDevices_CheckBox.hasMoreElements()){
			  JCheckBox 	checkBox = (JCheckBox)eDevices_CheckBox.nextElement();
			  JTextField 	refTextField = (JTextField)eDevices_Reference.nextElement();
			  JComboBox 	mChannelComboBox = (JComboBox)eDevices_MultimeterChannel.nextElement();

			  Resistance r = null;
			  Diode d = null;
			  if (checkBox.isSelected()){
				  switch (_deviceType){
					case Device.RESISTANCE:
						r = new Resistance(refTextField.getText());
						r.setConnectedToMultimeterChannelNumber((Integer)mChannelComboBox.getSelectedItem());
						devicesToCalibrate.afegeix(r);
						break;
					case Device.DIODE:
						d = new Diode(refTextField.getText());
						d.setConnectedToMultimeterChannelNumber((Integer)mChannelComboBox.getSelectedItem());
						devicesToCalibrate.afegeix(d);
						break;
					default:
				}
			  }
		  }
		  return new CalibrationSetUp_DevicesToCalibrate(devicesToCalibrate);
	}
	public boolean validateDevicesToCalibrateData(){
		for (int i=1;i<N_DEVICES_TO_CALIBRATE;i++){
			for (int j=i+1;j<=N_DEVICES_TO_CALIBRATE;j++){
				if (getDeviceMultimeterChannelComboBoxNumber(i).getSelectedItem().equals(getDeviceMultimeterChannelComboBoxNumber(j).getSelectedItem()) & getDeviceCheckBoxNumber(j).isSelected()){
					JOptionPane.showMessageDialog(null,"El canal de medida seleccionado para el dispositivo nº"+Integer.toString(i)+" no puede utilizarse para ningún otro dispositivo");
					return false;
				}
			}
		}
		return true;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
