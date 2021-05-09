package units;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	
	private String 		temp_units = DEFAULT_TEMP_UNITS;
	
	//Esta estructurá contendrá las unidades de medida de todos los canales disponibles en el instrumento para medida VOLTAGE:DC.
	//Es decir, si el instrumento dispone de una tarjeta expansora en el slot 1 de 20 canales entonces dicha estructura podría estar 
	//llena de la siguiente forma: [V,V,V,V,V,V,V,V,V,V,DB,DB,V,V,V,V,V,V,V,V] <-- Values para keys del 101 al 120  
	//Esta estructura se debería crear y actualizar durante la llamada al método initialize
	private HashMap<Integer,String> 	dc_volt_units = null;
	//Esta estructurá contendrá las unidades de medida de todos los canales disponibles en el instrumento para medida VOLTAGE:AC.
	//Es decir, si el instrumento dispone de una tarjeta expansora en el slot 1 de 20 canales entonces dicha estructura podría estar 
	//llena de la siguiente forma: [DB,V,V,V,V,V,V,V,V,V,DB,DB,V,V,V,V,V,V,V,V] <-- Values para keys del 101 al 120
	//Esta estructura se debería crear y actualizar durante la llamada al método initialize
	private HashMap<Integer,String> 	ac_volt_units = null;
	private float 		dc_db_reference = DEFAULT_DB_REFERENCE;
	private float 		ac_db_reference = DEFAULT_DB_REFERENCE;
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
		this.dc_volt_units = new HashMap<Integer,String>();
		this.ac_volt_units = new HashMap<Integer,String>();
	}

	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
		this.dc_volt_units = new HashMap<Integer,String>();
		this.ac_volt_units = new HashMap<Integer,String>();
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
			//if (configurationObj_keySet.contains("dcVoltageUnits")) units_subsystem.dc_volt_units =  (String) configurationObj.get("dcVoltageUnits");
			//if (configurationObj_keySet.contains("acVoltageUnits")) units_subsystem.ac_volt_units =  (String) configurationObj.get("acVoltageUnits");
			if (configurationObj_keySet.contains("dcDBReference")) units_subsystem.dc_db_reference =  ((Double) configurationObj.get("dcDBReference")).floatValue();
			if (configurationObj_keySet.contains("acDBReference")) units_subsystem.ac_db_reference =  ((Double) configurationObj.get("acDBReference")).floatValue();
			
			if (configurationObj_keySet.contains("voltageChannelsConfiguration")) 
			{
				JSONArray channelsConfiguration = (JSONArray) configurationObj.get("voltageChannelsConfiguration");
				Iterator<JSONObject> i = channelsConfiguration.iterator();

				while (i.hasNext()) {
					JSONObject channelConfiguration = i.next();
					//units_subsystem.voltageChannelsConfiguration.put(((Long)channelConfiguration.get("channelNumber")).intValue(),(String)channelConfiguration.get("channelConfiguration"));
				}
			}

			
		}
		
		return units_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	

	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	@Override
	public void setTemperatureUnits(String units) throws Exception {
		logger.info("Setting temperature units to " + units);
		this.temp_units = units; 
	}

	@Override
	public String getTemperatureUnits() throws Exception {
		logger.info("Getting temperature units...");
		return this.temp_units;
	}

	@Override
	public void setChannelVoltageUnits(int channel, String units) throws Exception {
		logger.info("Setting Voltage units of channel " + channel + " as " + units);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getDCVoltageUnits(int[] channels) throws Exception {
		logger.info("Getting DC Voltage units of channels" + channels.toString());
		return null;
	}

	@Override
	public String[] getACVoltageUnits(int[] channels) throws Exception {
		logger.info("Getting AC Voltage units of channels" + channels.toString());
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDCVoltageDBReference(float reference) throws Exception {
		logger.info("Setting VOLTAGE:DC:DB reference to " + reference);
		this.dc_db_reference = reference;	
	}

	@Override
	public float getDCVoltageDBReference() throws Exception {
		logger.info("Getting VOLTAGE:DC:DB reference...");
		return this.dc_db_reference;
	}

	@Override
	public void setACVoltageDBReference(float reference) throws Exception {
		logger.info("Setting VOLTAGE:AC:DB reference to " + reference);
		this.ac_db_reference = reference;
	}

	@Override
	public float getACVoltageDBReference() throws Exception {
		logger.info("Getting VOLTAGE:AC:DB reference...");
		return this.ac_db_reference;
	}
	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Unit SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
		//FASE DE IDENTIFICACION DEL TOTAL DE CANALES DE VOLTAJE DISPONIBLES 
		//E INICIALIZACION DE LAS ESTRUCTURAS PARA ALMACENAR SU CONFIGURACION 
		//EN TERMINOS DE UNIDADES DE MEDIDA
		
		//Preguntamos por el numero de tarjetas expansoras instaladas en el multimetro
		logger.info("Asking to multimeter for installed cards ... ");
		String[] tarjetas = new String(this.communicationsInterface.ask("*OPT?")).split(",");
		
		//Recorremos todos los slots. Solo visitaremos los que presentan tarjeta instalada  
		logger.info("Creating and initializing structures for saving volatge channels units configuration  ... ");
		for (int i=0;i<tarjetas.length;i++)
		{
			String tarjeta = tarjetas[i];
			if (!tarjeta.equals("NONE"))
			{
				//hay tarjeta
				int min = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(i+1)+":VCHANNEL:START?")));
				int max = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(i+1)+":VCHANNEL:END?")));
				for (int channel=min;channel<=max;channel++)
				{
					this.dc_volt_units.put(100*(i+1) + channel, "");
					this.ac_volt_units.put(100*(i+1) + channel, "");
				}
				
			}
		}
		
		////////////
		//FIN FASE//
		////////////
		
		
		
		//logger.info(new String(this.communicationsInterface.ask("UNIT:VOLTAGE:DC? (@101:120)")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:VCHANNEL:START?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:VCHANNEL:END?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:ACHANNEL:START?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:ACHANNEL:END?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:AOUTPUT:START?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:AOUTPUT:END?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:DOUTPUT:START?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:DOUTPUT:END?")));
//		logger.info(new String(this.communicationsInterface.ask("SYSTEM:CARD1:TCHANNEL?")));
		
		
		
//		this.communicationsInterface.write("UNIT:TEMPERATURE " + this.temp_units);
//		this.communicationsInterface.write("UNIT:VOLTAGE:DC " + this.dc_volt_units);
//		this.communicationsInterface.write("UNIT:VOLTAGE:AC " + this.ac_volt_units);
//		Iterator<Entry<Integer,String>> it = this.channelsConfiguration.entrySet().iterator();
//	    while (it.hasNext()) {
//	        Entry<Integer, String> pair = it.next();
//	        this.communicationsInterface.write(pair.getValue() + ", (@" + pair.getKey() + ")");
//	        //System.out.println(pair.getKey() + " = " + pair.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }	
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Units SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
//		logger.info("Uploading Format data type ... ");
//		this.communicationsInterface.write("FORMAT:DATA " + this.getFormatDataType());
//		
//		logger.info("Uploading Format border ... ");
//		this.communicationsInterface.write("FORMAT:BORDER " + this.getFormatBorder());
//		
//		
//		logger.info("Uploading Format elements ... ");
//		this.communicationsInterface.write("FORMAT:ELEMENTS " + this.getFormatElements());
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Units SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Downloading Units SubSystem VOLTAGE:DC channels configuration from the instrument ... ");
		
		if ((this.dc_volt_units != null) && (!this.dc_volt_units.isEmpty()))
		{
			Iterator<Integer> i = this.dc_volt_units.keySet().iterator();
			while(i.hasNext())
			{
				int channel = i.next();
				this.dc_volt_units.put(channel, new String(this.communicationsInterface.ask("UNIT:VOLTAGE:DC? (@" + channel +")")));	
			}
		}
		
		logger.info("Downloading Units SubSystem VOLTAGE:AC channels configuration from the instrument ... ");
		
		if ((this.ac_volt_units != null) && (!this.ac_volt_units.isEmpty()))
		{
			Iterator<Integer> i = this.ac_volt_units.keySet().iterator();
			while(i.hasNext())
			{
				int channel = i.next();
				this.ac_volt_units.put(channel, new String(this.communicationsInterface.ask("UNIT:VOLTAGE:AC? (@" + channel +")")));	
			}
		}
		
		
//		logger.info("Downloading Format data type ... ");
//		this.format_data_type = new String(this.communicationsInterface.ask("FORMAT:DATA?"));
//		
//		logger.info("Downloading Format border ... ");
//		this.format_border = new String(this.communicationsInterface.ask("FORMAT:BORDER?"));
//		
//		logger.info("Downloading Format elements ... ");
//		this.format_border = new String(this.communicationsInterface.ask("FORMAT:ELEMENTS?"));
		
	}
}
