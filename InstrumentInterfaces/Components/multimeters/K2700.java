/**
 * 
 */
package multimeters;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.CommunicationsModule_Component;
import expansion_slots.Expansion_Slots;
import format.Format_Subsystem;
import information.GeneralInformation_Component;
import system.System_Subsystem;
import trigger.Trigger_Subsystem;
import units.Units_Subsystem;
import sense.Sense_Subsystem;
import sense.senseFunctions.SenseFunction_Configuration;
import status.Status_Subsystem;
import route.Route_Subsystem;

import expansion_cards.K7700;

/**
 * @author DavidS
 *
 */
public class K2700 extends InstrumentComponent{

	//version 102:  added handle of unit_subsystem from json file and added initialize method 
	//version 101:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final String COMMUNICATIONS_SUBSYSTEM = "comunications_module";
	private static final String UNIT_SUBSYSTEM = "comunications_module";
	
	//////////////////////ERRORS
	
	
	//////////////////////VERSIONS
	private static final int classVersion = 102;
	
	//////////////////////VERSION
	final static Logger logger = LogManager.getLogger(K2700.class);
	
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public K2700(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	//Inicializacion 
	
	public static K2700 parseFromJSON(String filename) throws Exception
	{
		logger.info("Parsing K2700 from filename " + filename);
		
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
		
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		
		return K2700.parseFromJSON((JSONObject)obj);
		 
	}
	
	public static K2700 parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing K2700 from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		K2700 k2700 = null;
		
		String name = "";
		Long id = 0l;
		InstrumentComponent parent = null;
		boolean enable = true;
		boolean selected = true;
		CommunicationsModule_Component communicationsModule = null;
		GeneralInformation_Component generalInformation = null;
		Units_Subsystem units_subsystem = null;
		Format_Subsystem format_subsystem = null;
		System_Subsystem system_subsystem = null;
		Sense_Subsystem sense_subsystem = null;
		Route_Subsystem route_subsystem = null;
		Status_Subsystem status_subsystem = null;
		Trigger_Subsystem trigger_subsystem = null;
		Expansion_Slots expansion_slots = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		k2700 = new K2700(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("CommunicationsModule")) {
			communicationsModule = CommunicationsModule_Component.parseFromJSON((JSONObject)jObj.get("CommunicationsModule"));
			k2700.addSubComponent(communicationsModule);
		}
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			k2700.addSubComponent(generalInformation);
		}
		
		if (keySet.contains("UnitSubsystem")) {
			units_subsystem = Units_Subsystem.parseFromJSON((JSONObject)jObj.get("UnitSubsystem"));
			k2700.addSubComponent(units_subsystem);
		}
		
		if (keySet.contains("FormatSubsystem")) {
			format_subsystem = Format_Subsystem.parseFromJSON((JSONObject)jObj.get("FormatSubsystem"));
			k2700.addSubComponent(format_subsystem);
		}
		
		if (keySet.contains("SystemSubsystem")) {
			system_subsystem = System_Subsystem.parseFromJSON((JSONObject)jObj.get("SystemSubsystem"));
			k2700.addSubComponent(system_subsystem);
		}
		
		if (keySet.contains("ExpansionSlots")) {
			expansion_slots = Expansion_Slots.parseFromJSON((JSONObject)jObj.get("ExpansionSlots"));
			k2700.addSubComponent(expansion_slots);
		}
		
		
		
		sense_subsystem = Sense_Subsystem.parseFromJSON("./Components/sense/sense_configuration.json");
		k2700.addSubComponent(sense_subsystem);

//		if (keySet.contains("SenseSubsystem")) {
//			sense_subsystem = Sense_Subsystem.parseFromJSON((JSONObject)jObj.get("SenseSubsystem"));
//			k2700.addSubComponent(sense_subsystem);
//		}
		
		if (keySet.contains("RouteSubsystem")) {
			route_subsystem = Route_Subsystem.parseFromJSON((JSONObject)jObj.get("RouteSubsystem"));
			k2700.addSubComponent(route_subsystem);
		}
		
		if (keySet.contains("StatusSubsystem")) {
			status_subsystem = Status_Subsystem.parseFromJSON((JSONObject)jObj.get("StatusSubsystem"));
			k2700.addSubComponent(status_subsystem);
		}
		
		if (keySet.contains("TriggerSubsystem")) {
			trigger_subsystem = Trigger_Subsystem.parseFromJSON((JSONObject)jObj.get("TriggerSubsystem"));
			k2700.addSubComponent(trigger_subsystem);
		}
		
		
		return k2700;
		 
	}
	
	//****************************VERSION***************************************
	
	public static int getVersion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS***************************************
	//**************************************************************************
	
	public void initialize() throws Exception {
		
		logger.info("Initializing Keithley 2700  ... ");
			
		CommunicationsModule_Component communicationsModule = (CommunicationsModule_Component)this.getSubComponent("comunications_module");
		communicationsModule.initialize();
		communicationsModule.open();
		
		
		((Status_Subsystem)this.getSubComponent("status_subsystem")).initialize(communicationsModule);
		((Status_Subsystem)this.getSubComponent("status_subsystem")).downloadConfiguration();
		((Status_Subsystem)this.getSubComponent("status_subsystem")).clearAllErrorMessagesFromQueue();
		
		//logger.info(((Status_Subsystem)this.getSubComponent("status_subsystem")).getMostrecentErrorMessage());
		
		
		//((Units_Subsystem)this.getSubComponent("unit_subsystem")).initialize(communicationsModule);
		//((Units_Subsystem)this.getSubComponent("unit_subsystem")).downloadConfiguration();
		
		((Format_Subsystem)this.getSubComponent("format_subsystem")).initialize(communicationsModule);
		((Format_Subsystem)this.getSubComponent("format_subsystem")).uploadConfiguration();
		((Format_Subsystem)this.getSubComponent("format_subsystem")).downloadConfiguration();
		logger.info(((Format_Subsystem)this.getSubComponent("format_subsystem")).getFormatElements());
		
		((System_Subsystem)this.getSubComponent("system_subsystem")).initialize(communicationsModule);
		((System_Subsystem)this.getSubComponent("system_subsystem")).downloadConfiguration();
		
		((Expansion_Slots)this.getSubComponent("expansion_slots")).initialize(communicationsModule);
		((Expansion_Slots)this.getSubComponent("expansion_slots")).downloadConfiguration();
		
		((Sense_Subsystem)this.getSubComponent("sense_subsystem")).initialize(communicationsModule);
		((Sense_Subsystem)this.getSubComponent("sense_subsystem")).downloadConfiguration();
		
		((Route_Subsystem)this.getSubComponent("route_subsystem")).initialize(communicationsModule, ((Expansion_Slots)this.getSubComponent("expansion_slots")));
		((Route_Subsystem)this.getSubComponent("route_subsystem")).downloadConfiguration();
		
		((Trigger_Subsystem)this.getSubComponent("trigger_subsystem")).initialize(communicationsModule);
		((Trigger_Subsystem)this.getSubComponent("trigger_subsystem")).downloadConfiguration();
		((Trigger_Subsystem)this.getSubComponent("trigger_subsystem")).configureInitContinousInitiation(false);
		
		
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
		
		String k2700_filename	= "./Components/testing/k2700.json";
		String senseSubsystem_filename	= "./Components/sense/sense_configuration.json";
		
		
		logger.info("TESTING k2700 creation from JSON file");
		K2700 k2700 = K2700.parseFromJSON(k2700_filename);
		k2700.initialize();
		int[] cs = {101,102,103};
//		int[] scanList = {101,102,103,105,106,107,120};
//		
//		//((Route_Subsystem)k2700.getSubComponent("route_subsystem")).getClosureCountOfChannels(cs);
//		((Route_Subsystem)k2700.getSubComponent("route_subsystem")).openAllChannels();
//		//((Route_Subsystem)k2700.getSubComponent("route_subsystem")).closeChannel(103);
//		//logger.info(Arrays.toString(((Route_Subsystem)k2700.getSubComponent("route_subsystem")).getClosedChannels()));
//		//((Route_Subsystem)k2700.getSubComponent("route_subsystem")).closeChannel(104);
//		//logger.info(Arrays.toString(((Route_Subsystem)k2700.getSubComponent("route_subsystem")).getClosedChannels()));
//		((Route_Subsystem)k2700.getSubComponent("route_subsystem")).setScanChannelsList(scanList);
//		logger.info(Arrays.toString(((Route_Subsystem)k2700.getSubComponent("route_subsystem")).getScanChannelsList()));
//		((Route_Subsystem)k2700.getSubComponent("route_subsystem")).enableScan(false);
//		//enable scan requiere un tiempo para procesar el comando
//		Thread.sleep(1000);
//		try {
//			logger.info(((Route_Subsystem)k2700.getSubComponent("route_subsystem")).isScanEnable());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		logger.info(((Status_Subsystem)k2700.getSubComponent("status_subsystem")).getMostrecentErrorMessage());
//		((Route_Subsystem)k2700.getSubComponent("route_subsystem")).setTriggerSourceAsImmediate();
//		
//		try {
//			logger.info(((Route_Subsystem)k2700.getSubComponent("route_subsystem")).getTriggerSourceThatStartsScan());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		logger.info(((Status_Subsystem)k2700.getSubComponent("status_subsystem")).getMostrecentErrorMessage());
		
//		logger.info("queryTriggerSource " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerSource());
//		logger.info("querySampleCount " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).querySampleCount());
//		logger.info("queryTriggerCount " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerCount());
//		logger.info("queryTriggerDelay " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerDelay());
//		logger.info("queryTriggerTimer " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerTimer());
//		logger.info("TriggerDelay is in Auto Mode? " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).isTriggerDelayInAutoMode());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).abortTriggerCycle();
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureInitContinousInitiation(false);
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).initOneTriggerCycle();
//		logger.info("Is init contimous initiation enable? " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).isInitContinousInitiationEnable());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureTriggerCount(Integer.MAX_VALUE);
//		logger.info("queryTriggerCount " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerCount());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureTriggerDelay(Float.MAX_VALUE);
//		logger.info("queryTriggerDelay " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerDelay());
//		logger.info("TriggerDelay is in Auto Mode? " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).isTriggerDelayInAutoMode());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureTriggerDelay(Trigger_Subsystem.TRIGGER_DELAY_MAX);
//		logger.info("queryTriggerDelay " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerDelay());
//		logger.info("TriggerDelay is in Auto Mode? " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).isTriggerDelayInAutoMode());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureTriggerSource("IMM");
//		logger.info("Trigger source is  " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerSource());
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureTriggerTimer(2d);
//		logger.info("Trigger timer is  " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).queryTriggerTimer());
//		//((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).sendTriggerSignal();
//		((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).configureSampleCount(2);
//		logger.info("Sample count is  " + ((Trigger_Subsystem)k2700.getSubComponent("trigger_subsystem")).querySampleCount());
		
		Sense_Subsystem sense_subsystem = (Sense_Subsystem)k2700.getSubComponent("sense_subsystem");
		
		sense_subsystem.configureSenseFunction(SenseFunction_Configuration.FUNCTION_TEMPERATURE, 101);
		Thread.sleep(500);
		sense_subsystem.configureSenseFunction(SenseFunction_Configuration.FUNCTION_VOLTAGE_DC, 102);
		Thread.sleep(500);
		sense_subsystem.configureSenseFunction(SenseFunction_Configuration.FUNCTION_VOLTAGE_AC, 103);
		Thread.sleep(500);
		sense_subsystem.configureSenseFunction(SenseFunction_Configuration.FUNCTION_FOUR_WIRE_RESISTANCE, null);
		Thread.sleep(500);
//		Thread.sleep(1000);
//		
//		logger.info(((Status_Subsystem)k2700.getSubComponent("status_subsystem")).getMostrecentErrorMessage());
		
		logger.info("Sense configuration is  " + sense_subsystem.querySenseFunction(cs));
		
		//logger.info(k2700.toString());

		logger.info(((K7700)(((Expansion_Slots)k2700.getSubComponent("expansion_slots")).getExpansionCardAt(1))).toString());
	}

}
