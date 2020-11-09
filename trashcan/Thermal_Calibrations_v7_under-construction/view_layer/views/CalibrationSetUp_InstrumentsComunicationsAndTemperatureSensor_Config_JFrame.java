package views;

import instruments.InstrumentData;
import instruments.InstrumentsData;

import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Point;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import Main.MainController;
import javax.swing.SwingConstants;

import sensors.TemperatureSensor;


public class CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame extends JFrame implements ActionListener{

	private static final int N_COM_PORTS = 10;
	private static final int N_CONTROLLERS_ID = 255;
	private static final int N_MULTIMETER_CHANNELS = 10;

	private MainController mainController = null;
	private InstrumentsData instrumentsData = null;
	private TemperatureSensor temperatureSensorData = null;
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
			jTabbedPane.addTab("Instruments Communications", null, getCommunicationsPanel(), null);
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
			instrumentsConfigurationLabel = new JLabel();
			instrumentsConfigurationLabel.setBounds(new Rectangle(30, 30, 481, 31));
			instrumentsConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			instrumentsConfigurationLabel.setText("INSTRUMENTS COMMUNICATIONS CONFIGURATION");
			instrumentsConfigurationLabel.setBackground(new Color(204, 204, 255));
			ovenControllerIDLabel = new JLabel();
			ovenControllerIDLabel.setBounds(new Rectangle(90, 300, 151, 31));
			ovenControllerIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenControllerIDLabel.setText("Controller ID");
			ovenSerialPortLabel = new JLabel();
			ovenSerialPortLabel.setBounds(new Rectangle(90, 255, 151, 31));
			ovenSerialPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenSerialPortLabel.setText("Serial Comm Port");
			multimeterSerialPortLabel = new JLabel();
			multimeterSerialPortLabel.setBounds(new Rectangle(90, 150, 151, 31));
			multimeterSerialPortLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterSerialPortLabel.setText("Serial Comm Port");
			ovenLabel = new JLabel();
			ovenLabel.setText("Hobersal - 500ºC MAX - 2404EuroTherm");
			ovenLabel.setSize(new Dimension(406, 31));
			ovenLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ovenLabel.setLocation(new Point(60, 210));
			multimeterLabel = new JLabel();
			multimeterLabel.setText("Multimeter Keithley 2700");
			multimeterLabel.setHorizontalAlignment(SwingConstants.CENTER);
			multimeterLabel.setBounds(new Rectangle(60, 105, 406, 31));
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
			multimeterSerialPortComboBox.setBounds(new Rectangle(255, 150, 91, 31));
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
			ovenSerialPortComboBox.setBounds(new Rectangle(255, 255, 91, 31));
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
			ovenControllerIDComboBox.setBounds(new Rectangle(255, 300, 91, 31));
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
	public CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame(String _instrumentsDataFilePath,String _temperatureSensorDataFilePath,MainController _mainController)throws Exception {
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
		this.setName("InstrumentsComunicationsAndTemperatureSensorConfig_Frame");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Instruments Communications And Temperature Sensor Configuration");
		this.setResizable(false);
		this.instrumentsData = new InstrumentsData(_instrumentsDataFilePath);
		this.temperatureSensorData = new TemperatureSensor(_temperatureSensorDataFilePath);
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
		multimeterSerialPortComboBox.setSelectedItem(_instrumentsData.getMultimeterData().getComPort());
	}
	private void loadTemperatureSensorData(TemperatureSensor _temperatureSensorData){
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
						  new InstrumentData("","",0,(String)this.multimeterSerialPortComboBox.getSelectedItem()),
						  new InstrumentData("","",Integer.valueOf((String)this.ovenControllerIDComboBox.getSelectedItem()),(String)this.ovenSerialPortComboBox.getSelectedItem())
				  );
				  this.instrumentsData = i;
				  this.instrumentsData.toXMLFile("InstrumentsData", "ConfigurationFiles\\instrumentsData.xml");
				  TemperatureSensor t = new TemperatureSensor(
						  (String)this.temperatureSensorTypeComboBox.getSelectedItem(),
						  Integer.valueOf((String)this.temperatureSensorChannelComboBox.getSelectedItem())
						  );
				  this.temperatureSensorData = t;
				  this.temperatureSensorData.toXMLFile("TemperatureSensorData","ConfigurationFiles\\temperatureSensorData.xml");
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
	 * This method initializes temperatureSensorPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getTemperatureSensorPanel() {
		if (temperatureSensorPanel == null) {
			temperatureSensorTypejLabel = new JLabel();
			temperatureSensorTypejLabel.setBounds(new Rectangle(60, 105, 181, 31));
			temperatureSensorTypejLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureSensorTypejLabel.setText("Sensor Type");
			instrumentsConfigurationLabel1 = new JLabel();
			instrumentsConfigurationLabel1.setBounds(new Rectangle(30, 30, 481, 31));
			instrumentsConfigurationLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			instrumentsConfigurationLabel1.setText("TEMPERATURE SENSOR CONFIGURATION");
			instrumentsConfigurationLabel1.setBackground(new Color(204, 204, 255));
			multimeterTemperatureSensorChannelLabel = new JLabel();
			multimeterTemperatureSensorChannelLabel.setBounds(new Rectangle(60, 150, 181, 31));
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
			temperatureSensorTypeComboBox.setBounds(new Rectangle(255, 105, 91, 31));
			temperatureSensorTypeComboBox.setMaximumRowCount(8);
			temperatureSensorTypeComboBox.addItem("PT100");
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
			temperatureSensorChannelComboBox.setBounds(new Rectangle(255, 150, 91, 31));
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
				CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame thisClass;
				try {
					thisClass = new CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,null);
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
