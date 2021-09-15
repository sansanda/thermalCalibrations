/**
 * 
 */
package sense.senseFunctions;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import configuration_files.FILES_PATHS;
import sense.Sense_Subsystem;

/**
 * Configuracion exclusiva para la funcion de medida voltage DC que suele usar un multimetro.
 * @author DavidS
 *
 */
public class VoltageDCFunction_Configuration extends SenseFunction_Configuration implements I_VoltageDCFunction_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(VoltageDCFunction_Configuration.class);
	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	static {
		
		logger.info("Initializing DC Voltage Function Valid Parameters from file " + FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);
		
		try {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);

			//Read JSON file
			Object obj = jsonParser.parse(reader);
			jsonParser = null;
			 
			org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
			
			Set<String> keySet = jObj.keySet();
			
			JSONObject dcVoltage = (JSONObject)jObj.get("VOLTage:DC");
			
			JSONObject nplc = (JSONObject) dcVoltage.get("nplc");
			max_nplc_cycles = ((Double)nplc.get("max")).floatValue();
			min_nplc_cycles = ((Double)nplc.get("min")).floatValue();
			default_nplc_cycles = ((Double)nplc.get("default")).floatValue();
			
			JSONObject range = (JSONObject) dcVoltage.get("range");
			max_range = ((Long)range.get("max")).intValue();
			min_range = ((Long)range.get("min")).intValue();
			default_range = ((Long)range.get("default")).intValue();
				
			JSONObject digits = (JSONObject) dcVoltage.get("digits");
			max_digits = ((Long)digits.get("max")).byteValue();
			min_digits = ((Long)digits.get("min")).byteValue();
			default_digits = ((Long)digits.get("default")).byteValue();

			JSONObject reference = (JSONObject) dcVoltage.get("reference");
			max_reference = ((Long)reference.get("max")).intValue();
			min_reference = ((Long)reference.get("min")).intValue();
			default_reference = ((Long)reference.get("default")).intValue();
			
			JSONObject average = (JSONObject) dcVoltage.get("average");
			
			JSONObject window = (JSONObject) average.get("window");
			JSONObject count = (JSONObject) average.get("count");
			
			max_average_window = ((Double)window.get("max")).floatValue();
			min_average_window = ((Double)window.get("min")).floatValue();
			default_average_window = ((Double)window.get("default")).floatValue();
			
			max_average_count = ((Long)count.get("max")).intValue();
			min_average_count = ((Long)count.get("min")).intValue();
			default_average_count = ((Long)count.get("default")).intValue();
						
		} 
		catch (NullPointerException|IOException|ParseException e)
		{
			e.printStackTrace();
			
			max_nplc_cycles = 50f;
			min_nplc_cycles = 0.01f;
			default_nplc_cycles = 5f;
			
			max_range = 1010;
			min_range = 0;
			default_range = max_range;
			
			max_digits = 7;
			min_digits = 4;
			default_digits = max_digits;
			
			max_reference = max_range;
			min_reference = -max_range;
			default_reference = 0;
			
			min_average_window = 0f;
			max_average_window = 10f;
			default_average_window = 0.1f;
			
			max_average_count = 100;
			min_average_count = 1;
			default_average_count = 10;
			
		}
	}
		
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private boolean is10MOhmsInputDividerEnabled;
	//Los siguientes atributos son exclusivos del sense en instrumentos tipo source meter 
	private double 	protectionLevel; //compliance
	private boolean range_synchronization;
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public VoltageDCFunction_Configuration() throws Exception 
	{
		super(Sense_Subsystem.FUNCTION_VOLTAGE_DC);
		this.setNPLC(default_nplc_cycles);
		this.setRange(default_range);
		this.setResolutionDigits(default_digits);
		this.setReference(default_reference);
		this.setAverageWindow(default_average_window);
		this.setAverageCount(default_average_count);
		this.enableAverage(true);
		this.enable10MOhmsInputDivider(false);
		this.setProtectionLevel(10);
		this.enableRangeSynchronization(true);
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
		
	//****************************VERSION***************************************
		
	public static int getVersion() {
		return classVersion;
	}
		
	//**************************************************************************
	//****************************METODOS **************************************
	//**************************************************************************

	public void initializeFromJSON(JSONObject attributes) throws Exception
	{
		super.initializeFromJSON(attributes);
		this.initializeAttributesFromJSON(attributes);
	}
	
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Voltage DC Function Configuration from jObj ... ");
		
		try 
		{
			this.enable10MOhmsInputDivider(((((String)jObj.get("idivider")).equals("ON") ? true:false)));
			
			JSONObject protectionConfig = (JSONObject) jObj.get("protection");
			this.setProtectionLevel((Long)protectionConfig.get("level"));
			this.enableRangeSynchronization(((((String)protectionConfig.get("range_synchronization")).equals("ON") ? true:false)));
			
		} 
		catch (NullPointerException|IOException|ParseException e)  
		{
			e.printStackTrace();
			this.enable10MOhmsInputDivider(false);
			this.setProtectionLevel(10);
			this.enableRangeSynchronization(false);
		}	
	}
	
	@Override
	public void enable10MOhmsInputDivider(boolean enable) throws Exception {
		this.is10MOhmsInputDividerEnabled = enable;
	}

	@Override
	public boolean is10MOhmsInputDividerEnabled() throws Exception {
		return this.is10MOhmsInputDividerEnabled;
	}
	
	@Override
	public double getProtectionLevel() {
		return protectionLevel;
	}

	@Override
	public void setProtectionLevel(double protectionLevel) {
		this.protectionLevel = protectionLevel;
	}

	@Override
	public boolean isRangeSynchronizationEnabled() {
		return range_synchronization;
	}

	@Override
	public void enableRangeSynchronization(boolean range_synchronization) {
		this.range_synchronization = range_synchronization;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VoltageDCFunction_Configuration [is10MOhmsInputDividerEnabled=")
				.append(is10MOhmsInputDividerEnabled).append(", protectionLevel=").append(protectionLevel)
				.append(", range_synchronization=").append(range_synchronization).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}
	




	
}
