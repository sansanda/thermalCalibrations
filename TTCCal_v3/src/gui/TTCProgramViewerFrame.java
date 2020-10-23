package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Data.GeneralProgramData;
import Data.InstrumentData;
import Data.InstrumentsData;
import Data.MeasuresConfigurationData;
import Data.TTCData;
import Data.TemperatureProfileData;
import Data.TemperatureSensorData;
import Data.ThermalCalibrationProgramData;

import javax.swing.JFileChooser;

/**
  * This program illustrates how to do a Frame user interface
  * in the basic AWT framework rather than Swing.
**/
public class TTCProgramViewerFrame extends JFrame implements ActionListener
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int N_COM_PORTS = 10;
	private static final int N_MULTIMETER_CHANNELS = 10;
	private static final int N_CONTROLLERS_ID = 255;
	private static final int N_TEMPERATURE_VALUES = 500;
	
	
	 GridBagConstraints constraints = new GridBagConstraints ();
	 JFileChooser fileChooser = new JFileChooser();
	 AWTEvent awtEvent;
	 
	 JLabel multimeterSerialPortLabel;
	 Choice multimeterSerialPortChoice;
	 JLabel _2404SerialPortLabel;
	 Choice _2404SerialPortChoice;
	 JLabel _2404ControllerIDLabel;
	 Choice _2404ControllerID;
	 JLabel pt100SensorChannelLabel;
	 Choice pt100SensorChannel;
	 JLabel _TTCSenseChannelLabel;
	 Choice _TTCSenseChannel;
	 JLabel _TTCHeatChannelLabel;
	 Choice _TTCHeatChannel;
	 JTextArea programNameTextArea;
	 JLabel programNameLabel;
	 JButton saveButton;
	 JButton saveAsButton;
	 JButton cancelButton;
	 JLabel _TTCReferenceLabel;
	 JTextArea _TTCReferenceTextArea;
	 JLabel temperatureProfileLabel;
	 List temperatureProfileList;
	 
	 JButton _0To100TemperatureProfile;
	 JButton _0To125TemperatureProfile;
	 JButton _0To150TemperatureProfile;
	 JButton _0To175TemperatureProfile;
	 JButton _0To200TemperatureProfile;
	 JButton clearTemperatureProfile;
	 
	 JLabel maxProfileTempLabel;
	 Choice maxProfileTempChoice;
	 JLabel minProfileTempLabel;
	 Choice minProfileTempChoice;
	 JLabel numberOfTempProfileStepsLabel;
	 Choice numberOfTempProfileStepsChoice;
	 JLabel stepsValueLabel;
	 Choice stepsValueChoice;
	 
	 
	 ThermalCalibrationProgramData program;
	 
	 public TTCProgramViewerFrame(ThermalCalibrationProgramData _program,int _mode){
		 setDefaultLookAndFeelDecorated(true);
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 setName("TTCProgramViewerFrame");
		 program = _program;
		 Container content_pane = this.getContentPane();
		 content_pane.setPreferredSize(new Dimension(1000, 500));
		 content_pane.setSize(new Dimension(1000, 500));   
		 content_pane.setLayout(new GridBagLayout());
		 createFrameComponents();
		 //0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save 
	  	 //1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		 //2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		 configureFrameMode(_mode); 
		 setComponentsInFrame(content_pane);
	 }
	 private void createFrameComponents(){
		   	
		 	saveButton = new JButton("Save");
		    saveAsButton = new JButton("Save As");
		    cancelButton = new JButton("Cancel");
			
		    saveButton.addActionListener(this);
		    saveAsButton.addActionListener(this);
		    cancelButton.addActionListener(this); 
		     
		    programNameTextArea = new JTextArea("Program Name");
		    programNameTextArea.setPreferredSize(new Dimension(200,20));
		    programNameLabel = new JLabel("Program Name");
		      
		    multimeterSerialPortLabel = new JLabel("Multimeter Serial Port");
		    multimeterSerialPortChoice = new Choice ();
		    for (int i=0;i<=N_COM_PORTS;i++){
		  	  multimeterSerialPortChoice.addItem ("COM"+Integer.toString(i));
		    }
		  
		    _2404SerialPortLabel = new JLabel("2404 Serial Port");
		    _2404SerialPortChoice = new Choice ();
		    for (int i=0;i<=N_COM_PORTS;i++){
		  	  _2404SerialPortChoice.addItem ("COM"+Integer.toString(i));
		    }
		    
		     
		    _2404ControllerIDLabel = new JLabel("2404 ControllerID");
		    _2404ControllerID = new Choice ();
		    for (int i=0;i<=N_CONTROLLERS_ID;i++){
		  	  _2404ControllerID.addItem (Integer.toString(i));
		    }
		    
		    pt100SensorChannelLabel = new JLabel("PT100 Sensor Channel");
		    pt100SensorChannel = new Choice ();
		    for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
		  	  pt100SensorChannel.addItem (Integer.toString(i));
		    }
		      
		    _TTCSenseChannelLabel = new JLabel("TTCSense Channel");
		    _TTCSenseChannel = new Choice ();
		    for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
		  	  _TTCSenseChannel.addItem (Integer.toString(i));
		    }
		  	 
		    _TTCHeatChannelLabel = new JLabel("TTCHeat Channel");
		    _TTCHeatChannel = new Choice ();
		    for (int i=0;i<=N_MULTIMETER_CHANNELS;i++){
		  	  _TTCHeatChannel.addItem (Integer.toString(i));
		    }
		   
		    _TTCReferenceLabel = new JLabel("TTC Reference");
		    _TTCReferenceTextArea = new JTextArea("TTC Reference");
		    _TTCReferenceTextArea.setPreferredSize(new Dimension(200,20));
		 
		    temperatureProfileLabel = new JLabel("Temperature Profile");
		    temperatureProfileList = new List(10,true);
		    temperatureProfileList.setPreferredSize(new Dimension(200,200));
		    temperatureProfileList.setSize(new Dimension(200,200));
		    for (int i=0;i<=500;i++){
		    	temperatureProfileList.addItem(Integer.toString(i));
		    }
		    temperatureProfileList.addItemListener(new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent e) 
		        {
		        	if (e.getStateChange()==ItemEvent.SELECTED)
		        	{
		        		int oldValue = Integer.valueOf(numberOfTempProfileStepsChoice.getSelectedItem());
		        		int newValue = oldValue + 1;
		        		numberOfTempProfileStepsChoice.select(String.valueOf(newValue)); 
		        	}
		        	if (e.getStateChange()==ItemEvent.DESELECTED){
		        		int oldValue = Integer.valueOf(numberOfTempProfileStepsChoice.getSelectedItem());
		        		int newValue = oldValue-1;
		        		numberOfTempProfileStepsChoice.select(String.valueOf(newValue));
		        	}
		        	//Actualizamos el minimo o el maximo del rango de temperaturas
	        		String[] selectedItems = temperatureProfileList.getSelectedItems();
	        		maxProfileTempChoice.select(selectedItems[selectedItems.length-1]);
	        		minProfileTempChoice.select(selectedItems[0]);
		        }
		      }
		    );
		    
		    _0To100TemperatureProfile = new JButton("Mark_0To100ºC");
		    _0To100TemperatureProfile.setSize(new Dimension(25,10));
		    _0To100TemperatureProfile.addActionListener(this);
		    _0To125TemperatureProfile = new JButton("Mark_0To125ºC");;
		    _0To125TemperatureProfile.setSize(new Dimension(25,10));
		    _0To125TemperatureProfile.addActionListener(this);
		    _0To150TemperatureProfile = new JButton("Mark_0To150ºC");;
		    _0To150TemperatureProfile.setSize(new Dimension(25,10));
		    _0To150TemperatureProfile.addActionListener(this);
			_0To175TemperatureProfile = new JButton("Mark_0To175ºC");;
			_0To175TemperatureProfile.setSize(new Dimension(25,10));
			_0To175TemperatureProfile.addActionListener(this);
			_0To200TemperatureProfile = new JButton("Mark_0To200ºC");;
			_0To200TemperatureProfile.setSize(new Dimension(25,10));
			_0To200TemperatureProfile.addActionListener(this);
			clearTemperatureProfile = new JButton("Clear T-Profile");;
			clearTemperatureProfile.setSize(new Dimension(25,10));
			clearTemperatureProfile.addActionListener(this);
		  
			//3,7
			maxProfileTempLabel = new JLabel("MaxTemp");
			minProfileTempLabel = new JLabel("MinTemp");
			numberOfTempProfileStepsLabel = new JLabel("Steps");
			maxProfileTempChoice = new Choice();
			for (int i=0;i<=N_TEMPERATURE_VALUES;i++){
				maxProfileTempChoice.addItem (Integer.toString(i));
			}
			minProfileTempChoice = new Choice();
			for (int i=0;i<=N_TEMPERATURE_VALUES;i++){
				minProfileTempChoice.addItem (Integer.toString(i));
			}
			numberOfTempProfileStepsChoice = new Choice();
			for (int i=1;i<=(N_TEMPERATURE_VALUES+1);i++){
				numberOfTempProfileStepsChoice.addItem (Integer.toString(i));
			}
			stepsValueLabel= new JLabel("StepValue");;
			stepsValueChoice  = new Choice();
			for (int i=1;i<=N_TEMPERATURE_VALUES;i++){
				stepsValueChoice.addItem (Integer.toString(i));
			} 
			stepsValueChoice.addItemListener(new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent e) 
		        {
		        	int newMaxProfileTempChoiceValue = Integer.parseInt(stepsValueChoice.getSelectedItem())*(Integer.parseInt(numberOfTempProfileStepsChoice.getSelectedItem())-1);
		        	
		        	clearTemperatureProfileList();
		        	if (!validateTemperatureProfileData()){
		        		setDefaultProgramData();
		        	}else{
		        		fillTemperatureProfileList();
		        	}
		        }
		      }
		    );
			maxProfileTempChoice.addItemListener(new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent e) 
		        {
		        	clearTemperatureProfileList();
		        	if (!validateTemperatureProfileData()){
		        		setDefaultProgramData();
		        	}else{
		        		fillTemperatureProfileList();
		        	}
		        }
		      }
		    );
			minProfileTempChoice.addItemListener(new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent e) 
		        {
		        	clearTemperatureProfileList();
		        	if (!validateTemperatureProfileData()){
		        		
		        		setDefaultProgramData();
		        	}else{
		        		fillTemperatureProfileList();
		        	}
		        }
		      }
		    );
			numberOfTempProfileStepsChoice.addItemListener(new ItemListener() 
		    {
		        public void itemStateChanged(ItemEvent e) 
		        {
		        	if (Integer.parseInt(numberOfTempProfileStepsChoice.getSelectedItem())==1){
		        		maxProfileTempChoice.select(minProfileTempChoice.getSelectedItem());
		        	}
		        	clearTemperatureProfileList();
		        	if (!validateTemperatureProfileData()){
		        		
		        		setDefaultProgramData();
		        	}else{
		        		fillTemperatureProfileList();
		        	}
		        }
		      }
		    );
	 }
	 private void setComponentsInFrame(Container _content_pane){
		 	//0,0
		    setComponentInGrid(_content_pane,programNameLabel,0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,0
		    setComponentInGrid(_content_pane,programNameTextArea,1,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,1
		    //0,1
		    //2,1
		    //0,2
		    setComponentInGrid(_content_pane,multimeterSerialPortLabel,0,2,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,2
		    setComponentInGrid(_content_pane,multimeterSerialPortChoice,1,2,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);	     
		    //0,3
		    setComponentInGrid(_content_pane,_2404SerialPortLabel,0,3,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,3
		    setComponentInGrid(_content_pane,_2404SerialPortChoice,1,3,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //0,4
		    setComponentInGrid(_content_pane,_2404ControllerIDLabel,0,4,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,4
		    setComponentInGrid(_content_pane,_2404ControllerID,1,4,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //0,5
		    setComponentInGrid(_content_pane,pt100SensorChannelLabel,0,5,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,5
		    setComponentInGrid(_content_pane,pt100SensorChannel,1,5,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //0,6
		    setComponentInGrid(_content_pane,_TTCSenseChannelLabel,0,6,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,6
		    setComponentInGrid(_content_pane,_TTCSenseChannel,1,6,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //0,7
		    setComponentInGrid(_content_pane,_TTCHeatChannelLabel,0,7,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,7
		    setComponentInGrid(_content_pane,_TTCHeatChannel,1,7,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //0,8
		    setComponentInGrid(_content_pane,_TTCReferenceLabel,0,8,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL);
		    //1,8
		    setComponentInGrid(_content_pane,_TTCReferenceTextArea,1,8,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //3,0
		    setComponentInGrid(_content_pane,temperatureProfileLabel,3,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.VERTICAL);
		    //3,1
		    setComponentInGrid(_content_pane,temperatureProfileList,3,1,1,6,1,1,GridBagConstraints.CENTER,GridBagConstraints.VERTICAL);
		    //4,1
			setComponentInGrid(_content_pane,_0To100TemperatureProfile,4,1,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,2
			setComponentInGrid(_content_pane,_0To125TemperatureProfile,4,2,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,3
			setComponentInGrid(_content_pane,_0To150TemperatureProfile,4,3,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,4
			setComponentInGrid(_content_pane,_0To175TemperatureProfile,4,4,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,5
			setComponentInGrid(_content_pane,_0To200TemperatureProfile,4,5,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,6
			setComponentInGrid(_content_pane,clearTemperatureProfile,4,6,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//3,8
		    setComponentInGrid(_content_pane,maxProfileTempLabel,3,8,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE);
			//3,7
		    setComponentInGrid(_content_pane,minProfileTempLabel,3,7,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE);
			//3,9
		    setComponentInGrid(_content_pane,numberOfTempProfileStepsLabel,3,9,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE);
			//4,8
			setComponentInGrid(_content_pane,maxProfileTempChoice,4,8,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,7
			setComponentInGrid(_content_pane,minProfileTempChoice,4,7,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//4,9
			setComponentInGrid(_content_pane,numberOfTempProfileStepsChoice,4,9,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//3,10
		    setComponentInGrid(_content_pane,stepsValueLabel,3,10,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE);
			//4,10
			setComponentInGrid(_content_pane,stepsValueChoice,4,10,1,1,1,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
			//0,15
		    setComponentInGrid(_content_pane,saveButton,0,15,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //1,15
		    setComponentInGrid(_content_pane,saveAsButton,1,15,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
		    //2,15
		    setComponentInGrid(_content_pane,cancelButton,2,15,2,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);
 
	 }
	 private void configureFrameMode(int _mode){
		 //0 --> Creator Mode, Enable Buttons --> Cancel y SaveAs, Cancel Buttons --> Save 
		 //1 --> Viewer Mode, Enable Buttons --> None, , Cancel Buttons --> Save, Save As, Cancel
		 //2 --> Edit Mode, Enable Buttons --> Cancel y SaveAs, Save Cancel Buttons --> None
		 switch (_mode){
		    case 0:
		    	cancelButton.setEnabled(true);
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(true);
			    setDefaultProgramData();
			    break;
		    case 1:
		    	cancelButton.setEnabled(false);
		    	saveButton.setEnabled(false);
			    saveAsButton.setEnabled(false);
			    loadProgramData(program);
			    break;
		    case 2:
		    	cancelButton.setEnabled(true);
		    	saveButton.setEnabled(true);
			    saveAsButton.setEnabled(true);
			    loadProgramData(program);
			    break;
		    default:	
		    } 
	 }
	 private void setDefaultProgramData(){
		 multimeterSerialPortChoice.select("COM5");
		 _2404SerialPortChoice.select("COM6");
		 _2404ControllerID.select("1");
		 _TTCHeatChannel.select("6");
		 _TTCSenseChannel.select("5");
		 pt100SensorChannel.select("4");
		 minProfileTempChoice.select("0");
		 maxProfileTempChoice.select("1");
		 numberOfTempProfileStepsChoice.select("2");
		 temperatureProfileList.select(0);
		 temperatureProfileList.select(1);
		 stepsValueChoice.select("1");
	 }
	 private void setComponentInGrid(Container content_pane, Component comp,int gridX, int gridY, int gridWidth, int gridHeight, int weightX, int weightY, int anchor, int fill  ){
	   	constraints.gridx = gridX; 
	    constraints.gridy = gridY; 
	    constraints.gridwidth = gridWidth; 
	    constraints.gridheight = gridHeight; 
	    constraints.weighty = weightY; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = weightX; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.fill =fill;
	    constraints.anchor = anchor;
	    content_pane.add(comp, constraints);
	    constraints.fill = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.anchor = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.weighty = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.	
  }
  //public Container getContentPane(){return this;}
  private boolean validateProgramData(){
	  if (multimeterSerialPortChoice.getSelectedItem().equals(_2404SerialPortChoice.getSelectedItem())){
		  JOptionPane.showMessageDialog(null,"Los puertos del multimetro y del horno no pueden ser el mismo.");
		  return false;
	  }
	  if (_TTCHeatChannel.getSelectedItem().equals(_TTCSenseChannel.getSelectedItem())){
		  JOptionPane.showMessageDialog(null,"Los canales de medida de TTC-RHeating y la TTC-RSense no pueden ser el mismo.");
		  return false;
	  }
	  if (_TTCHeatChannel.getSelectedItem().equals(pt100SensorChannel.getSelectedItem())){
		  JOptionPane.showMessageDialog(null,"Los canales de medida de TTC-RHeating y la PT100 no pueden ser el mismo.");
		  return false;
	  }
	 
	  if (programNameTextArea.getText().equals("")){
		  JOptionPane.showMessageDialog(null,"Debe introducir un nombre para el programa a crear.");
		  return false;
	  }
	  
	  if (_TTCReferenceTextArea.getText().equals("")){
		  JOptionPane.showMessageDialog(null,"Debe introducir un nombre valido para la referencia del TTC a calibrar.");
		  return false;
	  }
	  return true;
  }
  private boolean validateTemperatureProfileData(){
	  int temperatureProfileMaxTemp = Integer.valueOf(maxProfileTempChoice.getSelectedItem());
	  int temperatureProfileMinTemp = Integer.valueOf(minProfileTempChoice.getSelectedItem());
	  int temperatureProfileNSteps = Integer.valueOf(numberOfTempProfileStepsChoice.getSelectedItem()); 
	  if (temperatureProfileNSteps<1){
		  JOptionPane.showMessageDialog(null,"El numero de steps del perfil de temperatura no puede ser menor de 1.");
		  return false;
	  }
	  if (temperatureProfileMaxTemp<temperatureProfileMinTemp){
		  JOptionPane.showMessageDialog(null,"La temperatura maxima debe ser superior o igual a la temperatura minima.");
		  return false;
	  }
	  int distanceBetweenMaxMinTemp = temperatureProfileMaxTemp - temperatureProfileMinTemp;
	  if (!((distanceBetweenMaxMinTemp%(temperatureProfileNSteps))==0)){
		  JOptionPane.showMessageDialog(null,"el numero de steps deber ser coherente con el rango de temperaturas seleccionado.");
		  return false;
	  }
	  return true;
  }
  public void loadProgramData(ThermalCalibrationProgramData _program){  
	  _2404ControllerID.select(String.valueOf(_program.getInstrumentsData().getOvenData().getControllerID()));
	  _2404SerialPortChoice.select(_program.getInstrumentsData().getOvenData().getComPort());
	  multimeterSerialPortChoice.select(_program.getInstrumentsData().getMultimeterData().getComPort());
	  pt100SensorChannel.select(String.valueOf(_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()));
	  _TTCSenseChannel.select(String.valueOf(_program.getMeasuresConfigurationData().getTTCsData()[0].getRSenseChannel()));
	  _TTCHeatChannel.select(String.valueOf(_program.getMeasuresConfigurationData().getTTCsData()[0].getRHeatChannel()));
	  _TTCReferenceTextArea.setText(_program.getMeasuresConfigurationData().getTTCsData()[0].getRefernce());
	  
	  programNameTextArea.setText(_program.getGeneralProgramData().getName());
	  
	  maxProfileTempChoice.select(String.valueOf(_program.getTemperatureProfileData().getTemperatures()[_program.getTemperatureProfileData().getTemperatures().length-1]));
	  minProfileTempChoice.select(String.valueOf(_program.getTemperatureProfileData().getTemperatures()[0]));
	  numberOfTempProfileStepsChoice.select(String.valueOf(_program.getTemperatureProfileData().getTemperatures().length)); 
	  int[] temperatures = _program.getTemperatureProfileData().getTemperatures();
	  for (int i=0;i<temperatures.length;i++){
		 temperatureProfileList.select(temperatures[i]);
	  }
  }
  private void saveToXMLFile(String _programFilePath) throws Exception{
	  GeneralProgramData generalProgramData = new GeneralProgramData(programNameTextArea.getText(),"CalibrateTTC",_programFilePath);
	  InstrumentsData instrumentsData = new InstrumentsData
	  (
			  new InstrumentData("2700","Keithley",0,multimeterSerialPortChoice.getSelectedItem()),
			  new InstrumentData("2404","Obersal",Integer.parseInt(_2404ControllerID.getSelectedItem()),_2404SerialPortChoice.getSelectedItem())
	  );	
	  
	  TTCData[] ttcsData = new TTCData[4];
	  ttcsData[0] = new TTCData(_TTCReferenceTextArea.getText(),Integer.parseInt(_TTCSenseChannel.getSelectedItem()),Integer.parseInt(_TTCHeatChannel.getSelectedItem()));  
	  ttcsData[1] = new TTCData("none",-1,-1);  
	  ttcsData[2] = new TTCData("none",-1,-1);
	  ttcsData[3] = new TTCData("none",-1,-1);
	  
	  MeasuresConfigurationData measuresConfigurationData = new MeasuresConfigurationData
	  (
			ttcsData,
			new TemperatureSensorData("PT100",Integer.parseInt(pt100SensorChannel.getSelectedItem()))
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
	  ThermalCalibrationProgramData thermalCalibrationProgramData = new ThermalCalibrationProgramData
	  (
			  generalProgramData,
			  instrumentsData,
			  measuresConfigurationData,
			  temperatureProfileData
	  );
	  program = thermalCalibrationProgramData;
	  thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData");
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
	  if (cmd.equals("Mark_0To100ºC")){
		  clearTemperatureProfileList();
		  for (int i=0;i<=100;i++){
			  if ((i%5)==0){temperatureProfileList.select(i);}
		  }
		  maxProfileTempChoice.select("100");
		  minProfileTempChoice.select("0");
		  numberOfTempProfileStepsChoice.select("21"); 
	  }
	  if (cmd.equals("Mark_0To125ºC")){
		  clearTemperatureProfileList();
		  for (int i=0;i<=125;i++){
			  if ((i%5)==0){temperatureProfileList.select(i);}
		  }
		  maxProfileTempChoice.select("125");
		  minProfileTempChoice.select("0");
		  numberOfTempProfileStepsChoice.select("26");
	  }
	  if (cmd.equals("Mark_0To150ºC")){
		  clearTemperatureProfileList();
		  for (int i=0;i<=150;i++){
			  if ((i%5)==0){temperatureProfileList.select(i);}
		  }
		  maxProfileTempChoice.select("150");
		  minProfileTempChoice.select("0");
		  numberOfTempProfileStepsChoice.select("31");
	  }
	  if (cmd.equals("Mark_0To175ºC")){
		  clearTemperatureProfileList();
		  for (int i=0;i<=175;i++){
			  if ((i%5)==0){temperatureProfileList.select(i);}
		  }
		  maxProfileTempChoice.select("175");
		  minProfileTempChoice.select("0");
		  numberOfTempProfileStepsChoice.select("36");
	  }
	  if (cmd.equals("Mark_0To200ºC")){
		  clearTemperatureProfileList();
		  for (int i=0;i<=200;i++){
			  if ((i%5)==0){temperatureProfileList.select(i);}
		  }
		  maxProfileTempChoice.select("200");
		  minProfileTempChoice.select("0");
		  numberOfTempProfileStepsChoice.select("41");
	  }
	  if (cmd.equals("Clear T-Profile")){
		  clearTemperatureProfileList();
	  }
  }
  
  private void fillTemperatureProfileList(){
	  if (!validateTemperatureProfileData()) return;
	  int temperatureProfileMaxTemp = Integer.valueOf(maxProfileTempChoice.getSelectedItem());
	  int temperatureProfileMinTemp = Integer.valueOf(minProfileTempChoice.getSelectedItem());
	  int temperatureProfileNSteps = Integer.valueOf(numberOfTempProfileStepsChoice.getSelectedItem()); 
	  int temperatureIncrement = (temperatureProfileMaxTemp - temperatureProfileMinTemp)/ temperatureProfileNSteps ;
	  temperatureProfileList.select(temperatureProfileMinTemp);
	  for (int i=0;i<temperatureProfileNSteps;i++){
		 temperatureProfileList.select(i*temperatureIncrement + temperatureProfileMinTemp);
	  }
  }
  private void clearTemperatureProfileList(){
	  for (int i=0;i<=500;i++){temperatureProfileList.deselect(i);}
  }
  
  private void printExceptionMessage(Exception e){
	System.out.println(e.getMessage());
	System.out.println(e.getCause());
	e.printStackTrace();
  }
  
  
  
  
  	/**
	* @param args
	*/
	public static void main(String[] args) {
		try {
			JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ver.");
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(null);
			String programFilePath = "";
			if (returnVal == JFileChooser.APPROVE_OPTION) {
		      programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
		      //This is where a real application would open the file.
		      //log.append("Opening: " + file.getName() + "." + newline);
			} else {
		      //log.append("Open command cancelled by user." + newline);
			}
			System.out.println("Creando la instancia para el programa.");
			
			ThermalCalibrationProgramData _program = new ThermalCalibrationProgramData(programFilePath);
			System.out.println(_program.toString());
			TTCProgramViewerFrame ttcProgramViewer = new TTCProgramViewerFrame(_program,1);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
  
  	
	
	
	
} // class GUIApplet