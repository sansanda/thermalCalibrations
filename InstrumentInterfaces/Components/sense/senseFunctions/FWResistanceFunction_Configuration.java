/**
 * 
 */
package sense.senseFunctions;

import java.io.FileNotFoundException;
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
 * Configuracion exclusiva para la funcion de medida Four Wire Resistance que suele usar un multimetro.
 * @author DavidS
 *
 */
public class FWResistanceFunction_Configuration extends SenseFunction_Configuration implements I_FWResistance_Function_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(FWResistanceFunction_Configuration.class);
	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	static {
		
		logger.info("Initializing FW Resistance Function Valid Parameters from file " + FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);
		
		try {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);

			//Read JSON file
			Object obj = jsonParser.parse(reader);
			jsonParser = null;
			 
			org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
			
			Set<String> keySet = jObj.keySet();
			
			JSONObject fwResistance = (JSONObject)jObj.get("FRESistance");
			
			JSONObject nplc = (JSONObject) fwResistance.get("nplc");
			max_nplc_cycles = ((Double)nplc.get("max")).floatValue();
			min_nplc_cycles = ((Double)nplc.get("min")).floatValue();
			default_nplc_cycles = ((Double)nplc.get("default")).floatValue();
			
			JSONObject range = (JSONObject) fwResistance.get("range");
			max_range = ((Long)range.get("max")).intValue();
			min_range = ((Long)range.get("min")).intValue();
			default_range = ((Long)range.get("default")).intValue();
			
			JSONObject digits = (JSONObject) fwResistance.get("digits");
			max_digits = ((Long)digits.get("max")).byteValue();
			min_digits = ((Long)digits.get("min")).byteValue();
			default_digits = ((Long)digits.get("default")).byteValue();

			JSONObject reference = (JSONObject) fwResistance.get("reference");
			max_reference = ((Long)reference.get("max")).intValue();
			min_reference = ((Long)reference.get("min")).intValue();
			default_reference = ((Long)reference.get("default")).intValue();
			
			JSONObject average = (JSONObject) fwResistance.get("average");
			
			JSONObject window = (JSONObject) average.get("window");
			JSONObject count = (JSONObject) average.get("count");
			
			max_average_window = ((Double)window.get("max")).floatValue();
			min_average_window = ((Double)window.get("min")).floatValue();
			default_average_window = ((Double)window.get("default")).floatValue();
			
			max_average_count = ((Long)count.get("max")).intValue();
			min_average_count = ((Long)count.get("min")).intValue();
			default_average_count = ((Long)count.get("default")).intValue();
						
		} catch (NullPointerException|IOException|ParseException e) {
			
			e.printStackTrace();
			
			max_nplc_cycles = 50f;
			min_nplc_cycles = 0.01f;
			default_nplc_cycles = 5f;
			
			max_range = 120000000;
			min_range = 0;
			default_range = max_range;
			
			max_digits = 7;
			min_digits = 4;
			default_digits = max_digits;
			
			max_reference = max_range;
			min_reference = min_range;
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
	
	private boolean isOffsetCompensatedEnabled;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public FWResistanceFunction_Configuration() throws Exception 
	{
		super(Sense_Subsystem.FUNCTION_FOUR_WIRE_RESISTANCE);
		this.setNPLC(default_nplc_cycles);
		this.setRange(default_range);
		this.setResolutionDigits(default_digits);
		this.setReference(default_reference);
		this.setAverageWindow(default_average_window);
		this.setAverageCount(default_average_count);
		this.enableAverage(true);
		this.enableOffsetCompensation(true);
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
		logger.info("Initializing FW Resistance Function Configuration from jObj ... ");
		
		try 
		{
			this.enableOffsetCompensation(((((String)jObj.get("ocompensated")).equals("ON") ? true:false)));
		} 
		catch (NullPointerException|IOException|ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.enableOffsetCompensation(true);
		}
		
	}
	
	@Override
	public void enableOffsetCompensation(boolean enable) throws Exception {
		this.isOffsetCompensatedEnabled = enable;
	}

	@Override
	public boolean isOffsetCompensatedEnabled() throws Exception {
		return this.isOffsetCompensatedEnabled;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FWResistanceFunction_Configuration [isOffsetCompensatedEnabled=")
				.append(isOffsetCompensatedEnabled).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

	
	
}
