package units;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

public class Units_Subsystem extends InstrumentComponent implements I_Units_Subsystem {

	//version 102: first operative version. Added parseFromJSON and initialize methods
	//version 101:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method. Still not operative
	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 102;
	
	final static Logger logger = LogManager.getLogger(Units_Subsystem.class);
	
	public static final String TEMP_UNITS_IN_CELSIUS = "CEL";
	public static final String TEMP_UNITS_IN_FAHRENHEIT = "FAR";
	public static final String TEMP_UNITS_IN_KELVIN = "K";
	public static final String DEFAULT_TEMP_UNITS = TEMP_UNITS_IN_CELSIUS;
	
	public static final String 	VOLT_UNITS_IN_V = "V";
	public static final String 	VOLT_UNITS_IN_DB = "DB";
	public static final String 	DEFAULT_VOLT_UNITS = VOLT_UNITS_IN_V;
	
	public static final float 	DEFAULT_DB_REFERENCE = 1f;
	public static final float 	MIN_DB_VALUE = 1E-7f;
	public static final float 	MAX_DB_VALUE = 1000f;
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private String 	temp_units = DEFAULT_TEMP_UNITS;
	private String 	dc_volt_units = DEFAULT_VOLT_UNITS;
	private String 	ac_volt_units = DEFAULT_VOLT_UNITS;
	private float 	dc_db_reference = DEFAULT_DB_REFERENCE;
	private float 	ac_db_reference = DEFAULT_DB_REFERENCE;
	
	private HashMap<Integer,String> channelsConfiguration = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
		channelsConfiguration = new HashMap<Integer,String>(30);

	}

	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
		channelsConfiguration = new HashMap<Integer,String>(30);

	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Units_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Units_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Units_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Units_Subsystem from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		Units_Subsystem units_subsystem = null;
		
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
		
		units_subsystem = new Units_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			units_subsystem.addSubComponent(generalInformation);
		}
		
		if (keySet.contains("Configuration")) {
			JSONObject configurationObj = (JSONObject)jObj.get("Configuration");
			Set<String> configurationObj_keySet = configurationObj.keySet();
			if (configurationObj_keySet.contains("temperatureUnits")) units_subsystem.temp_units =  (String) configurationObj.get("temperatureUnits");
			if (configurationObj_keySet.contains("dcVoltageUnits")) units_subsystem.dc_volt_units =  (String) configurationObj.get("dcVoltageUnits");
			if (configurationObj_keySet.contains("acVoltageUnits")) units_subsystem.ac_volt_units =  (String) configurationObj.get("acVoltageUnits");
			if (configurationObj_keySet.contains("dcDBReference")) units_subsystem.dc_db_reference =  ((Double) configurationObj.get("dcDBReference")).floatValue();
			if (configurationObj_keySet.contains("acDBReference")) units_subsystem.ac_db_reference =  ((Double) configurationObj.get("acDBReference")).floatValue();
			
			if (configurationObj_keySet.contains("channelsConfiguration")) 
			{
				JSONArray channelsConfiguration = (JSONArray) configurationObj.get("channelsConfiguration");
				Iterator<JSONObject> i = channelsConfiguration.iterator();

				while (i.hasNext()) {
					JSONObject channelConfiguration = i.next();
					units_subsystem.channelsConfiguration.put(((Long)channelConfiguration.get("channelNumber")).intValue(),(String)channelConfiguration.get("channelConfiguration"));
				}
			}

			
		}
		
		return units_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	
	
	@Override
	public void setUnits(String magnitude, String unit, int[] channels) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getUnits(String magnitude, int[] channels) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnitReference(String unit, String reference) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUnitReference(String unit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		logger.info("Initializing Unit SubSystem ... ");
		i_CommunicationsInterface.write("UNIT:TEMPERATURE " + this.temp_units);
		i_CommunicationsInterface.write("UNIT:VOLTAGE:DC " + this.dc_volt_units);
		i_CommunicationsInterface.write("UNIT:VOLTAGE:AC " + this.ac_volt_units);
		Iterator<Entry<Integer,String>> it = this.channelsConfiguration.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Integer, String> pair = it.next();
	        i_CommunicationsInterface.write(pair.getValue() + ", (@" + pair.getKey() + ")");
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }	
	}

	
}
