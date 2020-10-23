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

public class Configuration_Frame extends JFrame implements ActionListener{

	private static final int N_COM_PORTS = 10;
	private static final int N_CONTROLLERS_ID = 255;
	private static final int N_MULTIMETER_CHANNELS = 10;

	private MainController mainController = null;
	private InstrumentsData instrumentsData = null;  //  @jve:decl-index=0:
	private TemperatureSensorData temperatureSensorData = null;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel communicationsPanel = null;
	private JLabel multimeterLabel = null;
	private JLabel ovenLabel = null;
	private JLabel multimeterSerialPortLabel = null;
	private JLabel ovenSerialPortLabel = null;
	private JLabel ovenControllerIDLabel = null;
	private JComboBox multimeterSerialPortComboBox = null;
	private JComboBox ovenSerialPortComboBox = null;
	private JComboBox ovenControllerIDComboBox = null;
	private JPanel saveCancelPanel = null;
	private JButton saveButton = null;

	private JLabel instrumentsConfigurationLabel = null;
	private JLabel multimeterControllerIDjLabel = null;
	private JComboBox multimeterControllerIDjComboBox = null;
	private JLabel multimeterModeljLabel = null;
	private JLabel multimeterManufacturerjLabel = null;
	private JTextField multimeterModeljTextField = null;
	private JTextField multimeterManufacturerjTextField = null;
	private JLabel ovenModeljLabel = null;
	private JLabel ovenManufacturerjLabel = null;
	private JTextField ovenModeljTextField = null;
	private JTextField ovenManufacturerjTextField = null;
	private JPanel temperatureSensorPanel = null;
	private JLabel multimeterTemperatureSensorChannelLabel = null;
	private JComboBox temperatureSensorTypeComboBox = null;
	private JComboBox temperatureSensorChannelComboBox = null;
	private JLabel instrumentsConfigurationLabel1 = null;
	private JLabel temperatureSensorTypejLabel = null;


/**
	 * This method initializes jTabbedPane
	 *
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(0, 0, 571, 451));
			jTabbedPane.addTab("Communications", null, getCommunicationsPanel(), null);
			jTabbedPane.addTab("Temperature Sensor", null, getTemperatureSensorPanel(), null);
	}
		return jTabbedPane;
	}

	/**
	 * This method initializes communicationsPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getCommunicationsPanel() {
		if (communicationsPanel == null) {
			ovenManufacturerjLabel = new JLabel();
			ovenManufacturerjLabel.setBounds(new Rectangle(240, 285, 91, 31));
			ovenManufacturerjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenManufacturerjLabel.setText("Manufacturer");
			ovenModeljLabel = new JLabel();
			ovenModeljLabel.setBounds(new Rectangle(75, 285, 61, 31));
			ovenModeljLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenModeljLabel.setText("Model");
			multimeterManufacturerjLabel = new JLabel();
			multimeterManufacturerjLabel.setBounds(new Rectangle(240, 105, 91, 31));
			multimeterManufacturerjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterManufacturerjLabel.setText("Manufacturer");
			multimeterModeljLabel = new JLabel();
			multimeterModeljLabel.setBounds(new Rectangle(75, 105, 61, 31));
			multimeterModeljLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterModeljLabel.setText("Model");
			multimeterControllerIDjLabel = new JLabel();
			multimeterControllerIDjLabel.setBounds(new Rectangle(76, 195, 150, 31));
			multimeterControllerIDjLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterControllerIDjLabel.setText("Controller ID");
			instrumentsConfigurationLabel = new JLabel();
			instrumentsConfigurationLabel.setBounds(new Rectangle(30, 15, 481, 31));
			instrumentsConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			instrumentsConfigurationLabel.setText("INSTRUMENTS CONFIGURATION");
			instrumentsConfigurationLabel.setBackground(new Color(204, 204, 255));
			ovenControllerIDLabel = new JLabel();
			ovenControllerIDLabel.setBounds(new Rectangle(75, 375, 151, 31));
			ovenControllerIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenControllerIDLabel.setText("Controller ID");
			ovenSerialPortLabel = new JLabel();
			ovenSerialPortLabel.setBounds(new Rectangle(75, 330, 151, 31));
			ovenSerialPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenSerialPortLabel.setText("Serial Comm Port");
			multimeterSerialPortLabel = new JLabel();
			multimeterSerialPortLabel.setBounds(new Rectangle(75, 150, 151, 31));
			multimeterSerialPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterSerialPortLabel.setText("Serial Comm Port");
			ovenLabel = new JLabel();
			ovenLabel.setText("Oven");
			ovenLabel.setSize(new Dimension(91, 31));
			ovenLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenLabel.setLocation(new Point(30, 240));
			multimeterLabel = new JLabel();
			multimeterLabel.setText("Multimeter");
			multimeterLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterLabel.setBounds(new Rectangle(30, 60, 91, 31));
			communicationsPanel = new JPanel();
			communicationsPanel.setName("Instruments");
			communicationsPanel.setLayout(null);
			communicationsPanel.add(multimeterLabel, null);
			communicationsPanel.add(ovenLabel, null);
			communicationsPanel.add(multimeterSerialPortLabel, null);
			communicationsPanel.add(ovenSerialPortLabel, null);
			communicationsPanel.add(ovenControllerIDLabel, null);
			communicationsPanel.add(getMultimeterSerialPortComboBox(), null);
			communicationsPanel.add(getOvenSerialPortComboBox(), null);
			communicationsPanel.add(getOvenControllerIDComboBox(), null);
			communicationsPanel.add(instrumentsConfigurationLabel, null);
			communicationsPanel.add(multimeterControllerIDjLabel, null);
			communicationsPanel.add(getMultimeterControllerIDjComboBox(), null);
			communicationsPanel.add(multimeterModeljLabel, null);
			communicationsPanel.add(multimeterManufacturerjLabel, null);
			communicationsPanel.add(getMultimeterModeljTextField(), null);
			communicationsPanel.add(getMultimeterManufacturerjTextField(), null);
			communicationsPanel.add(ovenModeljLabel, null);
			communicationsPanel.add(ovenManufacturerjLabel, null);
			communicationsPanel.add(getOvenModeljTextField(), null);
			communicationsPanel.add(getOvenManufacturerjTextField(), null);
		}
		return communicationsPanel;
	}

	/**
	 * This method initializes multimeterSerialPortComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getMultimeterSerialPortComboBox() {
		if (multimeterSerialPortComboBox == null) {
			multimeterSerialPortComboBox = new JComboBox();
			multimeterSerialPortComboBox.setBounds(new Rectangle(240, 150, 91, 31));
			multimeterSerialPortComboBox.setMaximumRowCount(8);
			for (int i=0;i<N_COM_PORTS;i++){
				multimeterSerialPortComboBox.addItem("COM"+Integer.toString(i));
			}
		}
		return multimeterSerialPortComboBox;
	}

	/**
	 * This method initializes ovenSerialPortComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getOvenSerialPortComboBox() {
		if (ovenSerialPortComboBox == null) {
			ovenSerialPortComboBox = new JComboBox();
			ovenSerialPortComboBox.setBounds(new Rectangle(240, 330, 91, 31));
			ovenSerialPortComboBox.setMaximumRowCount(8);
			for (int i=0;i<N_COM_PORTS;i++){
				ovenSerialPortComboBox.addItem("COM"+Integer.toString(i));
			}
		}
		return ovenSerialPortComboBox;
	}

	/**
	 * This method initializes ovenControllerIDComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getOvenControllerIDComboBox() {
		if (ovenControllerIDComboBox == null) {
			ovenControllerIDComboBox = new JComboBox();
			ovenControllerIDComboBox.setBounds(new Rectangle(240, 375, 91, 31));
			ovenControllerIDComboBox.setMaximumRowCount(8);
			for (int i=0;i<N_CONTROLLERS_ID;i++){
				ovenControllerIDComboBox.addItem(Integer.toString(i));
			}
		}
		return ovenControllerIDComboBox;
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
	 * This is the default constructor
	 */
	public Configuration_Frame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,MainController _mainController)throws Exception {
		super("Configuration_Frame");
		mainController = _mainController;
		addWindowListener(mainController);
		initialize(_instrumentsDataFilePath,_temperatureSensorDataFilePath);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath) throws Exception {
		this.setSize(575, 510);
		this.setName("Configuration_Frame");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Configuration");
		this.setResizable(false);
		this.instrumentsData = new InstrumentsData(_instrumentsDataFilePath);
		this.temperatureSensorData = new TemperatureSensorData(_temperatureSensorDataFilePath);
		this.loadInstrumentsData(instrumentsData);
		this.loadTemperatureSensorData(temperatureSensorData);
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
	private void loadInstrumentsData(InstrumentsData _instrumentsData){
		ovenControllerIDComboBox.setSelectedItem(String.valueOf(_instrumentsData.getOvenData().getControllerID()));
		ovenSerialPortComboBox.setSelectedItem(_instrumentsData.getOvenData().getComPort());
		ovenManufacturerjTextField.setText(_instrumentsData.getOvenData().getManufacturer());
		ovenModeljTextField.setText(_instrumentsData.getOvenData().getModel());
		multimeterControllerIDjComboBox.setSelectedItem(String.valueOf(_instrumentsData.getMultimeterData().getControllerID()));
		multimeterSerialPortComboBox.setSelectedItem(_instrumentsData.getMultimeterData().getComPort());
		multimeterManufacturerjTextField.setText(_instrumentsData.getMultimeterData().getManufacturer());
		multimeterModeljTextField.setText(_instrumentsData.getMultimeterData().getModel());
	}
	private void loadTemperatureSensorData(TemperatureSensorData _temperatureSensorData){
		temperatureSensorTypeComboBox.setSelectedItem(String.valueOf(_temperatureSensorData.getType()));
		temperatureSensorChannelComboBox.setSelectedItem(String.valueOf(_temperatureSensorData.getChannel()));
	}
	public void actionPerformed (ActionEvent event) {
		  String cmd = event.getActionCommand ();
		  System.out.println(cmd);
		  if (cmd.equals("Save")) {
			  if (!validateInstrumentsData()){return;}
			  try {
				  InstrumentsData i = new InstrumentsData(
						  this.instrumentsData.getFilePath(),
						  new InstrumentData(this.multimeterModeljTextField.getText(),this.multimeterManufacturerjTextField.getText(),Integer.valueOf((String)this.multimeterControllerIDjComboBox.getSelectedItem()),(String)this.multimeterSerialPortComboBox.getSelectedItem()),
						  new InstrumentData(this.ovenModeljTextField.getText(),this.ovenManufacturerjTextField.getText(),Integer.valueOf((String)this.ovenControllerIDComboBox.getSelectedItem()),(String)this.ovenSerialPortComboBox.getSelectedItem())
				  );
				  this.instrumentsData = i;
				  this.instrumentsData.toXMLFile("InstrumentsData");
				  TemperatureSensorData t = new TemperatureSensorData(
						  this.temperatureSensorData.getFilePath(),
						  (String)this.temperatureSensorTypeComboBox.getSelectedItem(),
						  Integer.valueOf((String)this.temperatureSensorChannelComboBox.getSelectedItem())
						  );
				  this.temperatureSensorData = t;
				  this.temperatureSensorData.toXMLFile("TemperatureSensorData");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  if (cmd.equals("Cancel")) {}
	  }
	public boolean validateInstrumentsData(){
		if (((String)multimeterSerialPortComboBox.getSelectedItem()).equals((String)ovenSerialPortComboBox.getSelectedItem())){
		  JOptionPane.showMessageDialog(null,"Los puertos del multimetro y del horno no pueden ser el mismo.");
		  return false;
		}
		return true;
	}
	/**
	 * This method initializes multimeterControllerIDjComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMultimeterControllerIDjComboBox() {
		if (multimeterControllerIDjComboBox == null) {
			multimeterControllerIDjComboBox = new JComboBox();
			multimeterControllerIDjComboBox.setBounds(new Rectangle(240, 195, 91, 31));
			for (int i=0;i<N_CONTROLLERS_ID;i++){
				multimeterControllerIDjComboBox.addItem(Integer.toString(i));
			}
		}
		return multimeterControllerIDjComboBox;
	}

	/**
	 * This method initializes multimeterModeljTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMultimeterModeljTextField() {
		if (multimeterModeljTextField == null) {
			multimeterModeljTextField = new JTextField();
			multimeterModeljTextField.setBounds(new Rectangle(150, 105, 76, 31));
			multimeterModeljTextField.setHorizontalAlignment(JTextField.CENTER);
			multimeterModeljTextField.setText("2700");
		}
		return multimeterModeljTextField;
	}

	/**
	 * This method initializes multimeterManufacturerjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMultimeterManufacturerjTextField() {
		if (multimeterManufacturerjTextField == null) {
			multimeterManufacturerjTextField = new JTextField();
			multimeterManufacturerjTextField.setBounds(new Rectangle(345, 105, 91, 31));
			multimeterManufacturerjTextField.setHorizontalAlignment(JTextField.CENTER);
			multimeterManufacturerjTextField.setText("Keithley");
		}
		return multimeterManufacturerjTextField;
	}

	/**
	 * This method initializes ovenModeljTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getOvenModeljTextField() {
		if (ovenModeljTextField == null) {
			ovenModeljTextField = new JTextField();
			ovenModeljTextField.setBounds(new Rectangle(150, 285, 76, 31));
			ovenModeljTextField.setHorizontalAlignment(JTextField.CENTER);
			ovenModeljTextField.setText("2404");
		}
		return ovenModeljTextField;
	}

	/**
	 * This method initializes ovenManufacturerjTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getOvenManufacturerjTextField() {
		if (ovenManufacturerjTextField == null) {
			ovenManufacturerjTextField = new JTextField();
			ovenManufacturerjTextField.setBounds(new Rectangle(345, 285, 91, 31));
			ovenManufacturerjTextField.setHorizontalAlignment(JTextField.CENTER);
			ovenManufacturerjTextField.setText("Obersal");
		}
		return ovenManufacturerjTextField;
	}

	/**
	 * This method initializes temperatureSensorPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTemperatureSensorPanel() {
		if (temperatureSensorPanel == null) {
			temperatureSensorTypejLabel = new JLabel();
			temperatureSensorTypejLabel.setBounds(new Rectangle(30, 90, 181, 31));
			temperatureSensorTypejLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureSensorTypejLabel.setText("Sensor Type");
			instrumentsConfigurationLabel1 = new JLabel();
			instrumentsConfigurationLabel1.setBounds(new Rectangle(30, 30, 481, 31));
			instrumentsConfigurationLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			instrumentsConfigurationLabel1.setText("TEMPERATURE SENSOR CONFIGURATION");
			instrumentsConfigurationLabel1.setBackground(new Color(204, 204, 255));
			multimeterTemperatureSensorChannelLabel = new JLabel();
			multimeterTemperatureSensorChannelLabel.setBounds(new Rectangle(30, 135, 181, 31));
			multimeterTemperatureSensorChannelLabel.setText("Sensor Channel (Multimeter)");
			multimeterTemperatureSensorChannelLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureSensorPanel = new JPanel();
			temperatureSensorPanel.setLayout(null);
			temperatureSensorPanel.setName("Instruments");
			temperatureSensorPanel.add(multimeterTemperatureSensorChannelLabel, null);
			temperatureSensorPanel.add(getTemperatureSensorTypeComboBox(), null);
			temperatureSensorPanel.add(getTemperatureSensorChannelComboBox(), null);
			temperatureSensorPanel.add(instrumentsConfigurationLabel1, null);
			temperatureSensorPanel.add(temperatureSensorTypejLabel, null);
		}
		return temperatureSensorPanel;
	}

	/**
	 * This method initializes temperatureSensorTypeComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTemperatureSensorTypeComboBox() {
		if (temperatureSensorTypeComboBox == null) {
			temperatureSensorTypeComboBox = new JComboBox();
			temperatureSensorTypeComboBox.setBounds(new Rectangle(225, 90, 91, 31));
			temperatureSensorTypeComboBox.setMaximumRowCount(8);
			temperatureSensorTypeComboBox.addItem("PT100");
			temperatureSensorTypeComboBox.addItem("TC-K");
			temperatureSensorTypeComboBox.addItem("TC-B");
		}
		return temperatureSensorTypeComboBox;
	}

	/**
	 * This method initializes multimeterTemperatureSensorChannelComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTemperatureSensorChannelComboBox() {
		if (temperatureSensorChannelComboBox == null) {
			temperatureSensorChannelComboBox = new JComboBox();
			temperatureSensorChannelComboBox.setBounds(new Rectangle(225, 135, 91, 31));
			temperatureSensorChannelComboBox.setMaximumRowCount(8);
			for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
				temperatureSensorChannelComboBox.addItem (Integer.toString(i));
			}
		}
		return temperatureSensorChannelComboBox;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String instrumentsDataFilePath = "c:\\instrumentsData.xml";
				String temperatureSensorDataFilePath = "c:\\temperatureSensorData.xml";
				Configuration_Frame thisClass;
				try {
					thisClass = new Configuration_Frame(instrumentsDataFilePath,temperatureSensorDataFilePath,null);
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
