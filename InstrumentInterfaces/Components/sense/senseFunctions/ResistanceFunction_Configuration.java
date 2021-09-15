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
 * Configuracion exclusiva para la funcion de medida Resistance que suele usar un multimetro.
 * @author DavidS
 *
 */
public class ResistanceFunction_Configuration extends SenseFunction_Configuration implements I_Resistance_Function_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(ResistanceFunction_Configuration.class);
	
	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	static {
		
		logger.info("Initializing Resistance Function Valid Parameters from file " + FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);
		
		try {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);

			//Read JSON file
			Object obj = jsonParser.parse(reader);
			jsonParser = null;
			 
			org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
			
			Set<String> keySet = jObj.keySet();
			
			JSONObject resistance = (JSONObject)jObj.get("RESistance");
			
			JSONObject nplc = (JSONObject) resistance.get("nplc");
			max_nplc_cycles = ((Double)nplc.get("max")).floatValue();
			min_nplc_cycles = ((Double)nplc.get("min")).floatValue();
			default_nplc_cycles = ((Double)nplc.get("default")).floatValue();
			
			JSONObject range = (JSONObject) resistance.get("range");
			max_range = ((Long)range.get("max")).intValue();
			min_range = ((Long)range.get("min")).intValue();
			default_range = ((Long)range.get("default")).intValue();
			
			JSONObject digits = (JSONObject) resistance.get("digits");
			max_digits = ((Long)digits.get("max")).byteValue();
			min_digits = ((Long)digits.get("min")).byteValue();
			default_digits = ((Long)digits.get("default")).byteValue();

			JSONObject reference = (JSONObject) resistance.get("reference");
			max_reference = ((Long)reference.get("max")).intValue();
			min_reference = ((Long)reference.get("min")).intValue();
			default_reference = ((Long)reference.get("default")).intValue();
			
			JSONObject average = (JSONObject) resistance.get("average");
			
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
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public ResistanceFunction_Configuration() throws Exception 
	{
		super(Sense_Subsystem.FUNCTION_RESISTANCE);
		this.setNPLC(default_nplc_cycles);
		this.setRange(default_range);
		this.setResolutionDigits(default_digits);
		this.setReference(default_reference);
		this.setAverageWindow(default_average_window);
		this.setAverageCount(default_average_count);
		this.enableAverage(true);
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
		logger.info("Initializing FResistance Function Configuration from jObj ... ");
		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResistanceFunction_Configuration [toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
}
