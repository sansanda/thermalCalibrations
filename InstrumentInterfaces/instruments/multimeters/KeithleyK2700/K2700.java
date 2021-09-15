/**
 * 
 */
package multimeters.KeithleyK2700;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.CommunicationsModule_Component;

import multimeters.KeithleyK2700.configuration_files.FILES_PATHS;

import expansion_cards.MeasureChannels_DifferentialMultiplexer;
import expansion_slots.Expansion_Slots;
import format.Format_Subsystem;
import multimeters.I_Multimeter;
import multimeters.Multimeter;
import system.System_Subsystem;
import trace.Trace_Subsystem;
import trigger.Trigger_Subsystem;
import units.Units_Subsystem;
import sense.Sense_Subsystem;
import sense.senseFunctions.FWResistanceFunction_Configuration;
import sense.senseFunctions.ResistanceFunction_Configuration;
import sense.senseFunctions.SenseFunctions_Factory;
import sense.senseFunctions.TCoupleBased_TemperatureFunction_Configuration;
import sense.senseFunctions.VoltageDCFunction_Configuration;
import status.Status_Subsystem;
import route.Route_Subsystem;

/**
 * @author DavidS
 *
 */
public class K2700 extends InstrumentComponent implements I_Multimeter{

	//version 102:  added handle of unit_subsystem from json file and added initialize method 
	//version 101:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	public static final String COMMUNICATIONS_SUBSYSTEM 	= "comunications_module";
	public static final String UNIT_SUBSYSTEM 				= "unit_subsystem";
	public static final String STATUS_SUBSYSTEM 			= "status_subsystem";
	public static final String FORMAT_SUBSYSTEM 			= "format_subsystem";
	public static final String SYSTEM_SUBSYSTEM 			= "system_subsystem";
	public static final String EXPANSION_SLOTS 				= "expansion_slots";
	public static final String SENSE_SUBSYSTEM 				= "sense_subsystem";
	public static final String ROUTE_SUBSYSTEM 				= "route_subsystem";
	public static final String TRIGGER_SUBSYSTEM 			= "trigger_subsystem";
	public static final String TRACE_SUBSYSTEM 				= "trace_subsystem";
	
	//////////////////////ERRORS
	
	
	//////////////////////VERSIONS
	private static final int classVersion = 102;
	
	//////////////////////LOGGER
	final static Logger logger = LogManager.getLogger(K2700.class);
	
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
	public K2700(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	public K2700(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}

	public K2700(JSONObject jObj) throws Exception {
		
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing K2700 from jObj ... ");
		
		CommunicationsModule_Component communicationsModule = new CommunicationsModule_Component((JSONObject)jObj.get("CommunicationsModule"));
		this.addSubComponent(communicationsModule);
	
		Units_Subsystem units_subsystem = new Units_Subsystem(FILES_PATHS.UNITS_PARAMETERS_FILENAME);
		this.addSubComponent(units_subsystem);
	
		Format_Subsystem format_subsystem = new Format_Subsystem(FILES_PATHS.FORMATSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(format_subsystem);
	
		System_Subsystem system_subsystem = new System_Subsystem(FILES_PATHS.SYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(system_subsystem);
	
		Expansion_Slots expansion_slots = new Expansion_Slots(FILES_PATHS.EXPANSIONSLOTS_PARAMETERS_FILENAME);
		this.addSubComponent(expansion_slots);
		
		Sense_Subsystem sense_subsystem = new Sense_Subsystem(FILES_PATHS.SENSE_PARAMETERS_FILENAME);
		this.addSubComponent(sense_subsystem);

		Route_Subsystem route_subsystem = new Route_Subsystem(FILES_PATHS.ROUTESUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(route_subsystem);

		Status_Subsystem status_subsystem = new Status_Subsystem(FILES_PATHS.STATUSSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(status_subsystem);

		Trigger_Subsystem trigger_subsystem = new Trigger_Subsystem(FILES_PATHS.TRIGGERSUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(trigger_subsystem);
		
		Trace_Subsystem trace_subsystem = new Trace_Subsystem(FILES_PATHS.TRACESUBSYSTEM_PARAMETERS_FILENAME);
		this.addSubComponent(trace_subsystem);
		
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
		
		logger.info("Initializing Keithley 2700  ... ");
			
		this.communicationsModule = (CommunicationsModule_Component)this.getSubComponent("comunications_module");
		this.communicationsModule.initialize();
		this.communicationsModule.open();
		
		this.reset();
		
		Thread.sleep(2000);
		
		((Status_Subsystem)this.getSubComponent(STATUS_SUBSYSTEM)).initialize(this.communicationsModule);
		((Status_Subsystem)this.getSubComponent(STATUS_SUBSYSTEM)).downloadConfiguration();
		((Status_Subsystem)this.getSubComponent(STATUS_SUBSYSTEM)).clearAllErrorMessagesFromQueue();
		
		((Trace_Subsystem)this.getSubComponent(TRACE_SUBSYSTEM)).initialize(this.communicationsModule);
		((Trace_Subsystem)this.getSubComponent(TRACE_SUBSYSTEM)).uploadConfiguration();
		
		//logger.info(((Status_Subsystem)this.getSubComponent(STATUS_SUBSYSTEM)).getMostrecentErrorMessage());
		
		
		((Units_Subsystem)this.getSubComponent(UNIT_SUBSYSTEM)).initialize(this.communicationsModule);
		((Units_Subsystem)this.getSubComponent(UNIT_SUBSYSTEM)).uploadConfiguration();
		
		((Format_Subsystem)this.getSubComponent(FORMAT_SUBSYSTEM)).initialize(this.communicationsModule);
		((Format_Subsystem)this.getSubComponent(FORMAT_SUBSYSTEM)).uploadConfiguration();
		((Format_Subsystem)this.getSubComponent(FORMAT_SUBSYSTEM)).downloadConfiguration();
		logger.info(((Format_Subsystem)this.getSubComponent(FORMAT_SUBSYSTEM)).getFormatElements());
		
		((System_Subsystem)this.getSubComponent(SYSTEM_SUBSYSTEM)).initialize(this.communicationsModule);
		((System_Subsystem)this.getSubComponent(SYSTEM_SUBSYSTEM)).uploadConfiguration();
		
		((Expansion_Slots)this.getSubComponent(EXPANSION_SLOTS)).initialize(this.communicationsModule);
		((Expansion_Slots)this.getSubComponent(EXPANSION_SLOTS)).downloadConfiguration();
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).initialize(this.communicationsModule, ((Expansion_Slots)this.getSubComponent(EXPANSION_SLOTS)));
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).downloadConfiguration();
		
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).initialize(this.communicationsModule);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).downloadConfiguration();
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureInitContinousInitiation(false);
		
		((Sense_Subsystem)this.getSubComponent(SENSE_SUBSYSTEM)).initialize(this.communicationsModule);
		((Sense_Subsystem)this.getSubComponent(SENSE_SUBSYSTEM)).uploadConfiguration();
		
	}

	@Override
	public void reset() throws Exception {
		this.communicationsModule.write("*RST");	
	}
	
	
	public String queryOPC() throws Exception {
		return new String(this.communicationsModule.ask("*OPC?"));	
	}
	
	public void sendBUSTrigger() throws Exception {
		logger.info("Sending the *TRG command to the instrument...");
		this.communicationsModule.write("*TRG");	
	}
	
	@Override
	public String measureVoltage(int channelNumber) throws Exception 
	{
		return new String(this.communicationsModule.ask("MEAS:VOLT? " + MeasureChannels_DifferentialMultiplexer.createChannelsList(channelNumber, channelNumber)));
	}
	
	
	@Override
	public void configureAs4WOhmeter(float nplc, int range, byte resolutionDigits, int reference, int averageCount,
			float averageWindow, String averageTControl, boolean enableAverage, boolean enableOffsetCompensation) throws Exception {
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureEnableScan(false);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerSource(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_IMM);
		
		FWResistanceFunction_Configuration resistanceFunction = (FWResistanceFunction_Configuration) SenseFunctions_Factory.getConfiguredSenseFunction(Sense_Subsystem.FUNCTION_FOUR_WIRE_RESISTANCE, null);
		resistanceFunction.setNPLC(nplc);
		resistanceFunction.setRange(range);
		resistanceFunction.setResolutionDigits(resolutionDigits);
		resistanceFunction.setReference(reference);
		resistanceFunction.setAverageCount(averageCount);
		resistanceFunction.setAverageWindow(averageWindow);
		resistanceFunction.setAverageTControl(averageTControl);
		resistanceFunction.enableAverage(enableAverage);
		resistanceFunction.enableOffsetCompensation(enableOffsetCompensation);
		ArrayList<String> commandArray = SenseFunctions_Factory.getConfigurationCommandArrayForInstrument(resistanceFunction, Multimeter.K2700_NAME, null);
		Iterator<String> commandArrayIt = commandArray.iterator();
		while (commandArrayIt.hasNext())
		{
			this.communicationsModule.write(commandArrayIt.next());
		}
		
	}

	@Override
	public void configureAs2WOhmeter(float nplc, int range, byte resolutionDigits, int reference, int averageCount,
			float averageWindow, String averageTControl, boolean enableAverage) throws Exception {
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureEnableScan(false);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerSource(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_IMM);
		
		ResistanceFunction_Configuration resistanceFunction = (ResistanceFunction_Configuration) SenseFunctions_Factory.getConfiguredSenseFunction(Sense_Subsystem.FUNCTION_RESISTANCE, null);
		resistanceFunction.setNPLC(nplc);
		resistanceFunction.setRange(range);
		resistanceFunction.setResolutionDigits(resolutionDigits);
		resistanceFunction.setReference(reference);
		resistanceFunction.setAverageCount(averageCount);
		resistanceFunction.setAverageWindow(averageWindow);
		resistanceFunction.setAverageTControl(averageTControl);
		resistanceFunction.enableAverage(enableAverage);
		ArrayList<String> commandArray = SenseFunctions_Factory.getConfigurationCommandArrayForInstrument(resistanceFunction, Multimeter.K2700_NAME, null);
		Iterator<String> commandArrayIt = commandArray.iterator();
		while (commandArrayIt.hasNext())
		{
			this.communicationsModule.write(commandArrayIt.next());
		}
		
	}
	
	@Override
	public void configureAsDCVoltmeter(float nplc, int range, byte resolutionDigits, int reference, int averageCount,
			float averageWindow, String averageTControl, boolean enableAverage, boolean enable10mOhmsDivider) throws Exception {
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureEnableScan(false);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerSource(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_IMM);
		
		VoltageDCFunction_Configuration voltageDCFunction = (VoltageDCFunction_Configuration) SenseFunctions_Factory.getConfiguredSenseFunction(Sense_Subsystem.FUNCTION_VOLTAGE_DC, null);
		voltageDCFunction.setNPLC(nplc);
		voltageDCFunction.setRange(range);
		voltageDCFunction.setResolutionDigits(resolutionDigits);
		voltageDCFunction.setReference(reference);
		voltageDCFunction.setAverageCount(averageCount);
		voltageDCFunction.setAverageWindow(averageWindow);
		voltageDCFunction.setAverageTControl(averageTControl);
		voltageDCFunction.enableAverage(enableAverage);
		voltageDCFunction.enable10MOhmsInputDivider(enable10mOhmsDivider);
		ArrayList<String> commandArray = SenseFunctions_Factory.getConfigurationCommandArrayForInstrument(voltageDCFunction, Multimeter.K2700_NAME, null);
		Iterator<String> commandArrayIt = commandArray.iterator();
		while (commandArrayIt.hasNext())
		{
			this.communicationsModule.write(commandArrayIt.next());
		}
		
	}
	
	@Override
	public void configureAsTCThermometer(float nplc, byte resolutionDigits, int reference, int averageCount,
			float averageWindow, String averageTControl, boolean enableAverage, String transducerType,
			boolean enableODetect, String rJuntionType, int simulatedTemperature) throws Exception {
		
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureEnableScan(false);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerSource(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_IMM);
		
		TCoupleBased_TemperatureFunction_Configuration temperatureFunction = (TCoupleBased_TemperatureFunction_Configuration) SenseFunctions_Factory.getConfiguredSenseFunction(Sense_Subsystem.FUNCTION_TEMPERATURE, null);
		temperatureFunction.setNPLC(nplc);
		temperatureFunction.setResolutionDigits(resolutionDigits);
		temperatureFunction.setReference(reference);
		temperatureFunction.setAverageCount(averageCount);
		temperatureFunction.setAverageWindow(averageWindow);
		temperatureFunction.setAverageTControl(averageTControl);
		temperatureFunction.enableAverage(enableAverage);
		temperatureFunction.setType(transducerType);
		temperatureFunction.enableOpenDetection(enableODetect);
		temperatureFunction.setReferenceJunctionType(rJuntionType);
		temperatureFunction.setSimulatedReferenceJunctionTemperature(simulatedTemperature);
		
		ArrayList<String> commandArray = SenseFunctions_Factory.getConfigurationCommandArrayForInstrument(temperatureFunction, Multimeter.K2700_NAME, null);
		Iterator<String> commandArrayIt = commandArray.iterator();
		while (commandArrayIt.hasNext())
		{
			this.communicationsModule.write(commandArrayIt.next());
		}
		
		
	}
	
	@Override
	public void configureAsChannelScanner(
			String triggerSource, 
			int numberOfScans, 
			int numberOfChannelsToScan,
			int[] channelsToScan
			) throws Exception 
	{
		((Trace_Subsystem)this.getSubComponent(TRACE_SUBSYSTEM)).sendClearBufferCommand();
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureInitContinousInitiation(false);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerSource(triggerSource);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerCount(numberOfScans);
		((Trigger_Subsystem)this.getSubComponent(TRIGGER_SUBSYSTEM)).configureSampleCount(numberOfChannelsToScan);
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureScanChannelsList(channelsToScan);
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureTriggerSourceAsImmediate();
		((Route_Subsystem)this.getSubComponent(ROUTE_SUBSYSTEM)).configureEnableScan(true);
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ***************** K2700_Component Instance Description ****************** \n");
		
		builder.append(", ").append(super.toString()).append("]\n");
		
		return builder.toString();
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
//		byte[] input = { -84 };
//		String s = new String(input, Charset.forName("UTF8"));
//		System.out.println(s);
//		System.out.println(Arrays.toString(s.getBytes())); // prints [63] 
//		System.out.println(Arrays.toString(s.getBytes("UTF8"))); // prints [-17, -65, -67]
		
		String k2700_filename	= FILES_PATHS.K2700_PARAMETERS_FILENAME;

		logger.info("TESTING k2700 creation from JSON file");
		K2700 k2700 = new K2700(k2700_filename);
		
		k2700.initialize();
		
		String reading;
		
//		k2700.configureAsDCVoltmeter(1.0f, 10, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true, false);
//		

//		reading = k2700.measure(102);
//		logger.info("voltage ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading));
		
//		k2700.configureAsTCThermometer(1.0f, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true,
//				Sense_Subsystem.TCOUPLE_K_TYPE_TRANSDUCER,
//				true,
//				Sense_Subsystem.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE,
//				23);
//		
//		reading = k2700.measure(110);
//		logger.info(reading);
//		logger.info("temperature ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading));
		
//		k2700.configureAs2WOhmeter(1.0f, 10000, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true);
//		
//		reading = k2700.measure(109);
//		logger.info(reading);
//		logger.info("2W Resistance ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading));

//		k2700.configureAs4WOhmeter(1.0f, 10000, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true, true);
//	
//		((Route_Subsystem)k2700.getSubComponent(ROUTE_SUBSYSTEM)).sendCloseChannelCommand(109);
//		((Trace_Subsystem)k2700.getSubComponent(TRACE_SUBSYSTEM)).sendClearBufferCommand();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerAsIdle();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).initOneTriggerCycle();
//
//
//		
//		Thread.sleep(2000); //Exception in thread "main" java.io.IOException: JPIB C++:  while reading GPIB command result  [EABO] en medidas que duran mucho tiempo
////		//debemos solventar esto
//		
//		reading =  ((Sense_Subsystem)k2700.getSubComponent(SENSE_SUBSYSTEM)).queryLastFreshReading();
//		
//		logger.info(reading);
//		
//		logger.info("4W Resistance ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading).get(0));

//		int[] channelsToScan = new int[]{102,109,110};
//		int numberOfScans = 1;
//		
//		k2700.configureAsChannelScanner(Trigger_Subsystem.TRIGGER_SOURCE_CONTROL_BUS, numberOfScans, channelsToScan.length, channelsToScan);
//		((Trace_Subsystem)k2700.getSubComponent(TRACE_SUBSYSTEM)).sendClearBufferCommand();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerAsIdle();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).initOneTriggerCycle();
//
//		k2700.sendBUSTrigger();
//		Thread.sleep(2000);
//		reading = ((Trace_Subsystem)k2700.getSubComponent(TRACE_SUBSYSTEM)).queryTraceData();
//		logger.info(reading);
//		
//		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READING_DATA_ELEMENT));
//		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READINGNUMBER_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READINGNUMBER_DATA_ELEMENT));
//		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.TIMESTAMP_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.TIMESTAMP_DATA_ELEMENT));
//	
//	
//		k2700.configureAs4WOhmeter(1.0f, 10000, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true, true);
//		
//		((Route_Subsystem)k2700.getSubComponent(ROUTE_SUBSYSTEM)).sendCloseChannelCommand(109);
//		((Trace_Subsystem)k2700.getSubComponent(TRACE_SUBSYSTEM)).sendClearBufferCommand();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerAsIdle();
//		((Trigger_Subsystem)k2700.getSubComponent(TRIGGER_SUBSYSTEM)).initOneTriggerCycle();
//		
//		Thread.sleep(2000); //Exception in thread "main" java.io.IOException: JPIB C++:  while reading GPIB command result  [EABO] en medidas que duran mucho tiempo
//		//debemos solventar esto
//		
//		reading =  ((Sense_Subsystem)k2700.getSubComponent(SENSE_SUBSYSTEM)).queryLastFreshReading();
//	
//		logger.info(reading);
//	
//		logger.info("4W Resistance ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READING_DATA_ELEMENT).get(0));

		//k2700.configureAs4WOhmeter(1.0f, 10000, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true, true);
		
		((Route_Subsystem) k2700.getSubComponent(ROUTE_SUBSYSTEM)).sendOpenAllChannelsCommand();
		
		while (true) {
			
			((Trace_Subsystem) k2700.getSubComponent(TRACE_SUBSYSTEM)).sendClearBufferCommand();
			((Trigger_Subsystem) k2700.getSubComponent(TRIGGER_SUBSYSTEM)).configureTriggerAsIdle();
			((Trigger_Subsystem) k2700.getSubComponent(TRIGGER_SUBSYSTEM)).init();
			Thread.sleep(1000); //Exception in thread "main" java.io.IOException: JPIB C++:  while reading GPIB command result  [EABO] en medidas que duran mucho tiempo
			//debemos solventar esto
			reading = ((Sense_Subsystem) k2700.getSubComponent(SENSE_SUBSYSTEM)).queryLastFreshReading();
			logger.info(reading);
			logger.info("4W Resistance ----> "
					+ Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT)
					+ Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READING_DATA_ELEMENT)
							.get(0));
		}

	}

}
