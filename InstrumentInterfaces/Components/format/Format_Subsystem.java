package format;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import information.GeneralInformation_Component;

public class Format_Subsystem extends InstrumentComponent implements I_Format_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Format_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	

	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Format_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);

	}

	public Format_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);

	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Format_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Format_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Format_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Format_Subsystem from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		Format_Subsystem format_subsystem = null;
		
		String name = "";
		Long id = 0l;
		InstrumentComponent parent = null;
		boolean enable = true;
		boolean selected = true;
		GeneralInformation_Component generalInformation = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		format_subsystem = new Format_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			format_subsystem.addSubComponent(generalInformation);
		}
		
		if (keySet.contains("Configuration")) {
			JSONObject configurationObj = (JSONObject)jObj.get("Configuration");
			Set<String> configurationObj_keySet = configurationObj.keySet();
//			if (configurationObj_keySet.contains("temperatureUnits")) units_subsystem.temp_units =  (String) configurationObj.get("temperatureUnits");
//			if (configurationObj_keySet.contains("dcVoltageUnits")) units_subsystem.dc_volt_units =  (String) configurationObj.get("dcVoltageUnits");
//			if (configurationObj_keySet.contains("acVoltageUnits")) units_subsystem.ac_volt_units =  (String) configurationObj.get("acVoltageUnits");
//			if (configurationObj_keySet.contains("dcDBReference")) units_subsystem.dc_db_reference =  ((Double) configurationObj.get("dcDBReference")).floatValue();
//			if (configurationObj_keySet.contains("acDBReference")) units_subsystem.ac_db_reference =  ((Double) configurationObj.get("acDBReference")).floatValue();
//			
//			if (configurationObj_keySet.contains("channelsConfiguration")) 
//			{
//				JSONArray channelsConfiguration = (JSONArray) configurationObj.get("channelsConfiguration");
//				Iterator<JSONObject> i = channelsConfiguration.iterator();
//
//				while (i.hasNext()) {
//					JSONObject channelConfiguration = i.next();
//					units_subsystem.channelsConfiguration.put(((Long)channelConfiguration.get("channelNumber")).intValue(),(String)channelConfiguration.get("channelConfiguration"));
//				}
//			}

			
		}
		
		return format_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	
	

	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		logger.info("Initializing Format SubSystem ... ");	
	}

	
}
