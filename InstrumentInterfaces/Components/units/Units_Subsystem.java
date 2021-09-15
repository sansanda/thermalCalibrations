package units;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import dataValidators.DataValidators;
import expansion_cards.MeasureChannels_DifferentialMultiplexer;

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
	
	public static final String 	VOLT_DCUNITS_IN_V 	= "DC:V";
	public static final String 	VOLT_DCUNITS_IN_DB 	= "DC:DB";
	public static final String 	VOLT_ACUNITS_IN_V 	= "AC:V";
	public static final String 	VOLT_ACUNITS_IN_DB 	= "AC:DB";
	public static final String 	DEFAULT_VOLT_UNITS 	= VOLT_DCUNITS_IN_V;
	
	public static final String[] AVAILABLE_VOLT_UNITS = {VOLT_DCUNITS_IN_V,VOLT_DCUNITS_IN_DB,VOLT_ACUNITS_IN_V,VOLT_ACUNITS_IN_DB};
	
	public static final String 	VOLT_DC_MODE = "DC";
	public static final String 	VOLT_AC_MODE = "AC";
	public static final String 	DEFAULT_VOLT_MODE = VOLT_DC_MODE;
	public static final String[] AVAILABLE_VOLT_MODES = {VOLT_DC_MODE,VOLT_AC_MODE};
	

	
	public static final float 	DEFAULT_DB_REFERENCE = 1f;
	public static final float 	MIN_DB_VALUE = 1E-7f;
	public static final float 	MAX_DB_VALUE = 1000f;
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private String 		temp_units = DEFAULT_TEMP_UNITS;
	
	//Esta estructurá contendrá las unidades de medida de todos los canales disponibles en el instrumento.
	//Es decir, si el instrumento dispone de una tarjeta expansora en el slot 1 de 20 canales entonces dicha estructura podría estar 
	//configurada para medir voltage en los canales 101 y 103 entonces esta estructura podría estar llena de 
	//de la siguiente forma: [V,DB] <-- Values para keys 101 y 103
	//Esta estructura se debería crear y actualizar durante la llamada al método initialize
	private HashMap<Integer,String> 	voltage_channels_units = null;

	private float 		dc_db_reference = DEFAULT_DB_REFERENCE;
	private float 		ac_db_reference = DEFAULT_DB_REFERENCE;
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
		this.voltage_channels_units = new HashMap<Integer,String>();
	}

	public Units_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
		this.voltage_channels_units = new HashMap<Integer,String>();
	}

	public Units_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Units_Subsystem(JSONObject jObj) throws Exception
	{
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Creating new instance of Units_Subsystem from jObj ... ");
		
		
		try {
			JSONObject configurationObj = (JSONObject)jObj.get("Configuration");
			this.setTemperatureUnits((String) configurationObj.get("temperatureUnits"));
			//(String) configurationObj.get("dcVoltageUnits");//
			//(String) configurationObj.get("acVoltageUnits");//
			this.setDCVoltageDBReference(((Double) configurationObj.get("dcDBReference")).floatValue());
			this.setACVoltageDBReference(((Double) configurationObj.get("acDBReference")).floatValue());
			

			JSONArray voltageChannelsConfiguration = (JSONArray) configurationObj.get("VoltageChannelsConfiguration");
			Iterator<JSONObject> i = voltageChannelsConfiguration.iterator();

			while (i.hasNext()) {
				JSONObject channelConfiguration = i.next();
				this.setChannelVoltageUnits(((Long)channelConfiguration.get("channelNumber")).intValue(),(String)channelConfiguration.get("unit"));
			}
		} catch (NullPointerException|IOException|ParseException e) {
			e.printStackTrace();
			this.setTemperatureUnits(TEMP_UNITS_IN_CELSIUS);
			this.setDCVoltageDBReference(DEFAULT_DB_REFERENCE);
			this.setACVoltageDBReference(DEFAULT_DB_REFERENCE);
		}	
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	
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
		String validatedUnits = DataValidators.discreteSet_Validator(units, Units_Subsystem.AVAILABLE_VOLT_UNITS, Units_Subsystem.DEFAULT_VOLT_UNITS);
		this.voltage_channels_units.put(channel, validatedUnits);	
	}

	@Override
	public String getChannelVoltageUnits(int channel) throws Exception {
		logger.info("Getting DC Voltage units of channels" + channel);
		return this.voltage_channels_units.get(channel);
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
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Units SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		Set<Integer> ks = this.voltage_channels_units.keySet();
		Iterator<Integer> ksi = ks.iterator();
		while (ksi.hasNext())
		{
			Integer channelNumber = ksi.next();
			String voltageUnit = this.getChannelVoltageUnits(channelNumber);
			String[] voltageMode_Unit = voltageUnit.split(":");
			String command = "UNIT:VOLTage:" + voltageMode_Unit[0] + " " + voltageMode_Unit[1] + "," + MeasureChannels_DifferentialMultiplexer.createChannelsList(channelNumber,channelNumber);
			this.communicationsInterface.write(command);
		}
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Units SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Downloading Units SubSystem VOLTAGE:DC channels configuration from the instrument ... ");
		
	}
}
