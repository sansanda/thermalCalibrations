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
import dataValidators.DataValidators;
import sense.Sense_Subsystem;

/**
 * @author DavidS
 *
 */
public class FRTDBased_TemperatureFunction_Configuration extends TemperatureFunction_Configuration
		implements I_FRTDBased_TemperatureFunction_Configuration {
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(FRTDBased_TemperatureFunction_Configuration.class);
	
	public static	int 	min_rZero_value;
	public static   int 	max_rZero_value;
	public static   int 	default_rZero_value;
	
	public static   float 	min_alpha_value;
	public static   float 	max_alpha_value;
	public static   float 	default_alpha_value;

	public static   float 	min_beta_value;
	public static   float 	max_beta_value;
	public static   float 	default_beta_value;

	public static   float 	min_delta_value;
	public static   float 	max_delta_value;
	public static 	float 	default_delta_value;
	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	static {
		
		logger.info("Initializing FRTD Temperature Function Valid Parameters from file " + FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);
		
		try {
			//JSON parser object to parse read file
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);

			//Read JSON file
			Object obj = jsonParser.parse(reader);
			jsonParser = null;
			 
			org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
			
			Set<String> keySet = jObj.keySet();
			
			JSONObject temperature = (JSONObject)jObj.get("TEMPerature");
			
			JSONObject nplc = (JSONObject) temperature.get("nplc");
			max_nplc_cycles = ((Double)nplc.get("max")).floatValue();
			min_nplc_cycles = ((Double)nplc.get("min")).floatValue();
			default_nplc_cycles = ((Double)nplc.get("default")).floatValue();
			
			JSONObject digits = (JSONObject) temperature.get("digits");
			max_digits = ((Long)digits.get("max")).byteValue();
			min_digits = ((Long)digits.get("min")).byteValue();
			default_digits = ((Long)digits.get("default")).byteValue();

			
			JSONObject reference = (JSONObject) temperature.get("reference");
			max_reference = ((Long)reference.get("max")).intValue();
			min_reference = ((Long)reference.get("min")).intValue();
			default_reference = ((Long)reference.get("default")).intValue();
			
			JSONObject average = (JSONObject) temperature.get("average");
			
			JSONObject window = (JSONObject) average.get("window");
			JSONObject count = (JSONObject) average.get("count");
			
			max_average_window = ((Double)window.get("max")).floatValue();
			min_average_window = ((Double)window.get("min")).floatValue();
			default_average_window = ((Double)window.get("default")).floatValue();
			
			max_average_count = ((Long)count.get("max")).intValue();
			min_average_count = ((Long)count.get("min")).intValue();
			default_average_count = ((Long)count.get("default")).intValue();
			
			JSONObject frtd = (JSONObject)temperature.get("FRTD");
				
			JSONObject user = (JSONObject)frtd.get("USER");				

			JSONObject rZero = (JSONObject) user.get("RZERO");
			max_rZero_value = ((Long)rZero.get("max")).intValue();
			min_rZero_value = ((Long)rZero.get("min")).intValue();
			default_rZero_value = ((Long)rZero.get("default")).intValue();
		
			JSONObject alpha = (JSONObject) user.get("ALPHA");
			max_alpha_value = ((Double)alpha.get("max")).floatValue();
			min_alpha_value = ((Double)alpha.get("min")).floatValue();
			default_alpha_value = ((Double)alpha.get("default")).floatValue();

			JSONObject beta = (JSONObject) user.get("BETA");
			max_beta_value = ((Double)beta.get("max")).floatValue();
			min_beta_value = ((Double)beta.get("min")).floatValue();
			default_beta_value = ((Double)beta.get("default")).floatValue();

			JSONObject delta = (JSONObject) user.get("DELTA");
			max_delta_value = ((Double)delta.get("max")).floatValue();
			min_delta_value = ((Double)delta.get("min")).floatValue();
			default_delta_value = ((Double)delta.get("default")).floatValue();
						
		} catch (NullPointerException|IOException|ParseException e) {
			
			e.printStackTrace();
			
			max_nplc_cycles = 50f;
			min_nplc_cycles = 0.01f;
			default_nplc_cycles = 5f;
			
			max_digits = 7;
			min_digits = 4;
			default_digits = 6;
			
			max_reference = 3310;
			min_reference = -328;
			default_reference = 0;
			
			min_average_window = 0f;
			max_average_window = 10f;
			default_average_window = 0.1f;
			
			max_average_count = 100;
			min_average_count = 1;
			default_average_count = 10;
			
			max_rZero_value = 10000;
			min_rZero_value = 0;
			default_rZero_value = 100;
		
			max_alpha_value = 0.01f;
			min_alpha_value = 0f;
			default_alpha_value = 0.00385f;

			max_beta_value = 1f;
			min_beta_value = 0f;
			default_beta_value = 0.111f;

			max_delta_value = 5f;
			min_delta_value = 0f;
			default_delta_value = 1.507f;
			
		} 
	}
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private int 	rZero;
	private float 	alpha;
	private float 	beta;
	private float 	delta;

	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public FRTDBased_TemperatureFunction_Configuration() throws Exception 
	{
		super(Sense_Subsystem.FRTD_TEMPERATURE_TRANSDUCER, Sense_Subsystem.FRTD_PT100_TYPE_TRANSDUCER);
		this.setNPLC(default_nplc_cycles);
		this.setResolutionDigits(default_digits);
		this.setReference(default_reference);
		this.setAverageWindow(default_average_window);
		this.setAverageCount(default_average_count);
		this.enableAverage(true);
		this.setRZero(default_rZero_value);
		this.setAlpha(default_alpha_value);
		this.setBeta(default_beta_value);
		this.setDelta(default_delta_value);
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
		
	/**
	 * Metodo para la configuracion de los atributos especificos de un sensor de temperatura FRTD. 
	 * @param jObj objeto del tipo JSONObject que contiene una estructura tipo JSON con la configuracion 
	 * de un canal de medida en concreto del tipo funcion TEMPERATURE cuyo atributo transducer corresponde
	 * al texto "FRTD".  
	 * @throws Exception
	 */
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing FRTD Based Temperature Function Configuration from jObj ... ");
				
		try 
		{
			JSONObject transducerConfiguration = (JSONObject)jObj.get("transducerConfiguration");
			JSONObject userTypeConstants = (JSONObject)transducerConfiguration.get("userTypeConstants");
			this.setRZero(((Long)userTypeConstants.get("rZero")).intValue());
			this.setAlpha(((Double)userTypeConstants.get("alpha")).floatValue());
			this.setBeta(((Double)userTypeConstants.get("beta")).floatValue());
			this.setDelta(((Double)userTypeConstants.get("delta")).floatValue());
		} 
		catch (NullPointerException|IOException|ParseException e) 
		{
			e.printStackTrace();
			this.setRZero(default_rZero_value);
			this.setAlpha(default_alpha_value);
			this.setBeta(default_beta_value);
			this.setDelta(default_delta_value);
		}	
	}
		
	@Override
	public void setRZero(int value) throws Exception {
		this.rZero = DataValidators.range_Validator(value, min_rZero_value, max_rZero_value, default_rZero_value);
	}

	@Override
	public int getRZero() throws Exception {
		return this.rZero;
	}

	@Override
	public void setAlpha(float value) throws Exception {
		this.alpha = DataValidators.range_Validator(value, min_alpha_value, max_alpha_value, default_alpha_value);
	}

	@Override
	public float getAlpha() throws Exception {
		return this.alpha;
	}

	@Override
	public void setBeta(float value) throws Exception {
		this.beta = DataValidators.range_Validator(value, min_beta_value, max_beta_value, default_beta_value);
	}

	@Override
	public float getBeta() throws Exception {
		return this.beta;
	}

	@Override
	public void setDelta(float value) throws Exception {
		this.delta = DataValidators.range_Validator(value, min_delta_value, max_delta_value, default_delta_value);

	}

	@Override
	public float getDelta() throws Exception {
		return this.delta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FRTDBased_TemperatureFunction_Configuration [rZero=").append(rZero).append(", alpha=")
				.append(alpha).append(", beta=").append(beta).append(", delta=").append(delta).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}



	
}
