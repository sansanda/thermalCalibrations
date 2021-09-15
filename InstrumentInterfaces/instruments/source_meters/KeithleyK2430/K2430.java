package source_meters.KeithleyK2430;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.CommunicationsModule_Component;
import output.Output_Subsystem;
import route.Route_Subsystem;
import sense.Sense_Subsystem;
import source.Source_Subsystem;
import source_meters.I_Meter;
import source_meters.I_Source;
import source_meters.KeithleyK2430.configuration_files.FILES_PATHS;
import system.System_Subsystem;
import trigger.Trigger_Subsystem;

public class K2430 extends InstrumentComponent implements I_Source, I_Meter{

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	public static final String COMMUNICATIONS_SUBSYSTEM 	= "CommunicationsModule";
	
	public static final String COMMUNICATIONS_SUBSYSTEM_NAME 	= "comunications_module";
	public static final String OUTPUT_SUBSYSTEM_NAME 			= "output_subsystem";
	public static final String SOURCE_SUBSYSTEM_NAME 			= "source_subsystem";
	public static final String SENSE_SUBSYSTEM_NAME 			= "sense_subsystem";
	public static final String ROUTE_SUBSYSTEM_NAME 			= "route_subsystem";
	public static final String TRIGGER_SUBSYSTEM_NAME 			= "trigger_subsystem";
	public static final String SYSTEM_SUBSYSTEM_NAME 			= "system_subsystem";
	
	
	
	//////////////////////ERRORS
	
	
	//////////////////////VERSIONS
	private static final int classVersion = 100;
	
	//////////////////////LOGGER
	final static Logger logger = LogManager.getLogger(K2430.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	CommunicationsModule_Component communicationsModule = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public K2430(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	public K2430(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}

	public K2430(JSONObject jObj) throws Exception {
		
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing K2430 from jObj ... ");
		
		CommunicationsModule_Component communicationsModule = new CommunicationsModule_Component((JSONObject)jObj.get(COMMUNICATIONS_SUBSYSTEM));
		this.addSubComponent(communicationsModule);
		
		System_Subsystem system_subsystem = new System_Subsystem(FILES_PATHS.SYSTEMSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(system_subsystem);
		
		Trigger_Subsystem trigger_subsystem = new Trigger_Subsystem(FILES_PATHS.TRIGGERSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(trigger_subsystem);
		
		Output_Subsystem output_subsystem = new Output_Subsystem(FILES_PATHS.OUTPUTSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(output_subsystem);
		
		Sense_Subsystem sense_subsystem = new Sense_Subsystem(FILES_PATHS.SENSESUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(sense_subsystem);
		
		Source_Subsystem source_subsystem = new Source_Subsystem(FILES_PATHS.SOURCESUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(source_subsystem);
		
		Route_Subsystem route_subsystem = new Route_Subsystem(FILES_PATHS.ROUTESUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(route_subsystem);
		

	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	//****************************VERSION***************************************
	
	public static int getVersion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS***************************************
	//**************************************************************************
	
	public void initialize() throws Exception {
		
		logger.info("Initializing Keithley 2430  ... ");
			
		this.communicationsModule = (CommunicationsModule_Component)this.getSubComponent(COMMUNICATIONS_SUBSYSTEM_NAME);
		this.communicationsModule.initialize();
		this.communicationsModule.open();
		
		
		((System_Subsystem)this.getSubComponent(SYSTEM_SUBSYSTEM_NAME)).initialize(this.communicationsModule);
		((System_Subsystem)this.getSubComponent(SYSTEM_SUBSYSTEM_NAME)).uploadConfiguration();
		
		
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).initialize(this.communicationsModule);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).uploadConfiguration();
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).configureInitContinousInitiation(true);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).configureTriggerSource(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_IMM);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).configureTriggerCount(1);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM_NAME)).configureSampleCount(1);
		
		
		((Output_Subsystem)this.getSubComponent(OUTPUT_SUBSYSTEM_NAME)).initialize(this.communicationsModule);
		((Output_Subsystem)this.getSubComponent(OUTPUT_SUBSYSTEM_NAME)).uploadConfiguration();
		
		((Sense_Subsystem)this.getSubComponent(SENSE_SUBSYSTEM_NAME)).initialize(this.communicationsModule);
		((Sense_Subsystem)this.getSubComponent(SENSE_SUBSYSTEM_NAME)).uploadConfiguration();
		
		((Source_Subsystem)this.getSubComponent(SOURCE_SUBSYSTEM_NAME)).initialize(this.communicationsModule);
		((Source_Subsystem)this.getSubComponent(SOURCE_SUBSYSTEM_NAME)).uploadConfiguration();
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM_NAME)).initialize(this.communicationsModule, null);
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM_NAME)).uploadConfiguration();
		
	}
//	 //**************************************************************************
//	 //****************************CONSTANTES************************************
//	 //**************************************************************************
//
//	final static String VERSION = "1.0.0";
//	
//	//Variables
//
//	//default constructor
//	public K2430(String wantedPortName)throws Exception{
//		super(wantedPortName);
//	}
//	//Getters and Setters
//	//Other Methods
//	public float read4WireResistance() throws Exception{
//		System.out.println("Reading the instantaneous 4-WIRE RESISTANCE in K2430....");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("FUNC 'RES'");
//		sendMessageToSerialPort("SENS:RES:OCOM ON");
//		sendMessageToSerialPort("RES:MODE AUTO");
//		sendMessageToSerialPort("RES:RANG 20E1");
//		sendMessageToSerialPort("SYST:RSEN ON");
//		sendMessageToSerialPort("FORM:ELEM RES");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");
//		return res;
//	}
//	public float read2WireResistance() throws Exception{
//		System.out.println("Reading the instantaneous 2-WIRE RESISTANCE in K2430....");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("FUNC 'RES'");
//		sendMessageToSerialPort("SENS:RES:OCOM ON");
//		sendMessageToSerialPort("RES:MODE AUTO");
//		sendMessageToSerialPort("RES:RANG 20E1");
//		sendMessageToSerialPort("SYST:RSEN OFF");
//		sendMessageToSerialPort("FORM:ELEM RES");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");
//		return res;
//	}
//	public float measureVoltage() throws Exception{
//		System.out.println("Reading the instantaneous VOLTAGE in K2430....");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("SOUR:FUNC CURR");
//		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
//		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
//		sendMessageToSerialPort("SOUR:CURR:RANG MIN");
//		sendMessageToSerialPort("SOUR:CURR:LEV 0");
//		sendMessageToSerialPort("SENS:VOLT:PROT 25");
//		sendMessageToSerialPort("SENS:VOLT:RANG 20");
//		sendMessageToSerialPort("FORM:ELEM VOLT");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");
//		return res;
//	}
//	public float measureCurrent() throws Exception{
//
//		System.out.println("Reading the instantaneous CURRENT in K2430....");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");					//
//		sendMessageToSerialPort("SOUR:FUNC VOLT");			//
//		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");	//
//		sendMessageToSerialPort("SENS:FUNC 'CURR'");		//
//		sendMessageToSerialPort("SOUR:CURR:RANG MIN");		//
//		sendMessageToSerialPort("SOUR:VOLT:LEV 0");			//
//		sendMessageToSerialPort("SENS:CURR:PROT 1E-3");		//
//		sendMessageToSerialPort("SENS:CURR:RANG 1E-3");		//
//		sendMessageToSerialPort("FORM:ELEM CURR");			//
//		sendMessageToSerialPort("OUTP ON");					//
//		sendMessageToSerialPort("READ?");					//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");				//
//		return res;
//	}
//	public float applyVoltageAndMeasureCurrent(float _voltageLevel,String _voltageRange, String _currentCompliance, String _currentRange) throws Exception{
//
//		String voltageLevel = String.valueOf(_voltageLevel);
//		System.out.println("Applying VOLTAGE to the output in K2430 and measuring CURRENT...");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("SOUR:FUNC VOLT");
//		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");
//		sendMessageToSerialPort("SOUR:VOLT:RANG "+ _voltageRange);
//		sendMessageToSerialPort("SOUR:VOLT:LEV "+ 	voltageLevel);
//		sendMessageToSerialPort("SENS:CURR:PROT "+ 	_currentCompliance);
//		sendMessageToSerialPort("SENS:FUNC 'CURR'");
//		sendMessageToSerialPort("SENS:CURR:RANG "+	_currentRange);
//		sendMessageToSerialPort("FORM:ELEM CURR");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//
//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");
//		return res;
//	}
//	public float applyCurrentAndMeasureVoltage(float _currentLevel,String _currentRange, String _voltageCompliance, String _voltageRange) throws Exception{
//
//		String currentLevel = String.valueOf(_currentLevel);
//		System.out.println("Applying CURRENT to the output in K2430 and measuring VOLTAGE...");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("SOUR:FUNC CURR");
//		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
//		sendMessageToSerialPort("SOUR:CURR:RANG "+ _currentRange);
//		sendMessageToSerialPort("SOUR:CURR:LEV "+ 	currentLevel);
//		sendMessageToSerialPort("SENS:VOLT:PROT "+ 	_voltageCompliance);
//		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
//		sendMessageToSerialPort("SENS:VOLT:RANG "+	_voltageRange);
//		sendMessageToSerialPort("FORM:ELEM VOLT");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//
//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		sendMessageToSerialPort("OUTP OFF");
//		return res;
//	}
//	public float applyVoltageAndMeasureCurrentAndLeftInstrumentOn(float _voltageLevel,String _voltageRange, String _currentCompliance, String _currentRange) throws Exception{
//
//		String voltageLevel = String.valueOf(_voltageLevel);
//		System.out.println("Applying VOLTAGE to the output in K2430 and measuring CURRENT...");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("SOUR:FUNC VOLT");
//		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");
//		sendMessageToSerialPort("SOUR:VOLT:RANG "+ _voltageRange);
//		sendMessageToSerialPort("SOUR:VOLT:LEV "+ 	voltageLevel);
//		sendMessageToSerialPort("SENS:CURR:PROT "+ 	_currentCompliance);
//		sendMessageToSerialPort("SENS:FUNC 'CURR'");
//		sendMessageToSerialPort("SENS:CURR:RANG "+	_currentRange);
//		sendMessageToSerialPort("FORM:ELEM CURR");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//
//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		return res;
//	}
//	public float applyCurrentAndMeasureVoltageAndLeftInstrumentOn(float _currentLevel,String _currentRange, String _voltageCompliance, String _voltageRange) throws Exception{
//
//		String currentLevel = String.valueOf(_currentLevel);
//		System.out.println("Applying CURRENT to the output in K2430 and measuring VOLTAGE...");
//		float res = -1;
//		byte[] data;
//		int dataLength;
//		sendMessageToSerialPort("*RST");
//		sendMessageToSerialPort("SOUR:FUNC CURR");
//		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
//		sendMessageToSerialPort("SOUR:CURR:RANG "+ _currentRange);
//		sendMessageToSerialPort("SOUR:CURR:LEV "+ 	currentLevel);
//		sendMessageToSerialPort("SENS:VOLT:PROT "+ 	_voltageCompliance);
//		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
//		sendMessageToSerialPort("SENS:VOLT:RANG "+	_voltageRange);
//		sendMessageToSerialPort("FORM:ELEM VOLT");
//		sendMessageToSerialPort("OUTP ON");
//		sendMessageToSerialPort("READ?");
//
//
//		waitForIncomingData();
//		dataLength = this.getReadDataLength();
//		data = this.getReadData();
//		if (dataLength>0){
//			String str = new String(data,0,dataLength);
//			if (str!=null)
//			{
//				//System.out.println(str);
//				String[] rawData = str.split(",");
//				res = Float.valueOf(rawData[0]);
//				//System.out.println(res);
//			}
//		}
//		return res;
//	}
//	
//	//**************************************************************************
//	//****************************VERSION***************************************
//	//**************************************************************************
//		
//	public static String getVersion() {
//		return VERSION;
//	}
	
	
	public static void main(String[] args) throws Exception {
		
		String k2430_filename	= FILES_PATHS.K2430_PARAMETERS_FILENAME;

		logger.info("TESTING k2430 creation from JSON file");
		K2430 k2430 = new K2430(k2430_filename);
		
		k2430.initialize();
		
		while(true)
		{
			Thread.sleep(2000);			
			((Trigger_Subsystem)k2430.getSubComponent(K2430.TRIGGER_SUBSYSTEM_NAME)).abort();
			//((Output_Subsystem)k2430.getSubComponent(K2430.OUTPUT_SUBSYSTEM_NAME)).setOutputOn(true);
			Thread.sleep(2000);
			//((Output_Subsystem)k2430.getSubComponent(K2430.OUTPUT_SUBSYSTEM_NAME)).setOutputOn(false);
			((Trigger_Subsystem)k2430.getSubComponent(K2430.TRIGGER_SUBSYSTEM_NAME)).init();
			
			
		}
			
			
		
		
//		 try
//		 {
//			 K2430 k = new K2430("COM6");
//			 k.initialize("k2430_InitFile_For_TTC.txt");
//			 k.test("k2430_TestFile_For_TTC.txt");
//			 k.configure("k2430_ConfigFile_For_TTC.txt");
//
//
//			 System.out.println("4-WIRE RESISTANCE --> "+k.read4WireResistance());
//			 System.out.println("2-WIRE RESISTANCE --> "+k.read2WireResistance());
//			 //System.out.println("VOLTAGE MEASURE --> "+k.measureVoltage());
//			 //System.out.println("CURRENT MEASURE --> "+k.measureCurrent());
//			 float voltageToBeApplied = (float)10.2515;
//			 float currentToBeApplied = (float)0.2179847;
//
//			 System.out.println("APPLYING VOLTAGE "+voltageToBeApplied+" AND MEASURING CURRENT --> "+k.applyVoltageAndMeasureCurrent(voltageToBeApplied,"20","10E-1","10E-1"));
//			 System.out.println("APPLYING CURRENT "+currentToBeApplied+" AND MEASURING VOLTAGE --> "+k.applyCurrentAndMeasureVoltage(currentToBeApplied, "10E-1", "20", "20"));
//			 //System.out.println("APPLYING VOLTAGE "+voltageToBeApplied+" AND MEASURING CURRENT. LEFT INSTRUMENT ON. --> "+k.applyVoltageAndMeasureCurrentAndLeftInstrumentOn(voltageToBeApplied,"20","10E-1","10E-1"));
//			 //System.out.println("APPLYING CURRENT "+currentToBeApplied+" AND MEASURING VOLTAGE. LEFT INSTRUMENT ON. --> "+k.applyCurrentAndMeasureVoltageAndLeftInstrumentOn(currentToBeApplied, "10E-1", "20", "20"));
//
//			 System.out.println("Ending process...");
//			 System.out.println("Closing ports...");
//			 Thread.sleep(500);
//			 k.closeSerialPort();
//			 Thread.sleep(500);
//			 System.exit(1);
//
//		 }
//		 catch(Exception e)
//		 {
//			 e.printStackTrace();
//			 System.err.println(
//					 "StackTrace = " + e.getStackTrace()+ "\n" +
//					 "Message = "+ e.getMessage()+ "\n" +
//					 "Cause = "+ e.getCause());
//
//		 }
	}
}
