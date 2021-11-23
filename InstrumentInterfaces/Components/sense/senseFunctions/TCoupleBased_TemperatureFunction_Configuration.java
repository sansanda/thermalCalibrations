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
public class TCoupleBased_TemperatureFunction_Configuration extends TemperatureFunction_Configuration
		implements I_TCoupleBased_TemperatureFunction_Configuration {
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
		
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(TCoupleBased_TemperatureFunction_Configuration.class);
	
	private static int min_simulated_reference_temperature;
	private static int max_simulated_reference_temperature;
	private static int default_simulated_reference_temperature;
	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	static {
		
		logger.info("Initializing TCouple Temperature Function Valid Parameters from file " + FILES_PATHS.VALID_SENSE_FUNCTIONS_PARAMETERS_FILENAME);
		
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
			
			JSONObject tcouple = (JSONObject)temperature.get("tcouple");			

			JSONObject simulated_reference_temperature = (JSONObject) tcouple.get("simulated_reference_temperature");	
			max_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("max")).intValue();
			min_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("min")).intValue();
			default_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("default")).intValue();
						
		} 
		
		catch (NullPointerException|IOException|ParseException e)
		
		{
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
			
			max_simulated_reference_temperature = 65;
			min_simulated_reference_temperature = 0;
			default_simulated_reference_temperature = 23;
		
		}
	}
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************

	private boolean openDetectionEnable;
	private String 	referenceJuntionType;
	private int 	simulatedReferenceTemperature;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public TCoupleBased_TemperatureFunction_Configuration() throws Exception 
	{
		super(Sense_Subsystem.TCOUPLE_TEMPERATURE_TRANSDUCER, Sense_Subsystem.TCOUPLE_K_TYPE_TRANSDUCER);
		this.setNPLC(default_nplc_cycles);
		this.setResolutionDigits(default_digits);
		this.setReference(default_reference);
		this.setAverageWindow(default_average_window);
		this.setAverageCount(default_average_count);
		this.enableAverage(true);
		this.setReferenceJunctionType(Sense_Subsystem.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE);
		this.setSimulatedReferenceJunctionTemperature(default_simulated_reference_temperature);
		this.enableOpenDetection(true);
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
	 * al texto "Tcouple".  
	 * @throws Exception
	 */
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Thermo Couple Based Temperature Function Configuration from jObj ... ");
				
		try {
			JSONObject transducerConfiguration = (JSONObject)jObj.get("transducerConfiguration");
			this.enableOpenDetection((((String)transducerConfiguration.get("odetect")).equals("ON")? true:false));
			JSONObject rjunction = (JSONObject)transducerConfiguration.get("rjunction");
			this.setReferenceJunctionType((String)rjunction.get("referenceJunctionType"));
			this.setSimulatedReferenceJunctionTemperature(((Long)rjunction.get("simulatedTemperature")).intValue());
		} 
		catch (NullPointerException|IOException|ParseException e)  
		{
			e.printStackTrace();
			this.setReferenceJunctionType(Sense_Subsystem.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE);
			this.setSimulatedReferenceJunctionTemperature(default_simulated_reference_temperature);
			this.enableOpenDetection(true);
		}	
		
	}

	@Override
	public void enableOpenDetection(boolean enable) throws Exception {
		this.openDetectionEnable = enable;
	}

	@Override
	public boolean isOpenDetectionEnable() throws Exception {
		return this.openDetectionEnable;
	}

	@Override
	public void setReferenceJunctionType(String type) throws Exception {
		this.referenceJuntionType = DataValidators.discreteSet_Validator(type, Sense_Subsystem.TCOUPLE_REFERENCE_JUNCTION_TYPES, Sense_Subsystem.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE);
		
	}

	@Override
	public String getReferenceJunctionType() throws Exception {
		return this.referenceJuntionType;
	}

	@Override
	public void setSimulatedReferenceJunctionTemperature(int value) throws Exception {
		this.simulatedReferenceTemperature = DataValidators.range_Validator(
				value, 
				min_simulated_reference_temperature, 
				max_simulated_reference_temperature, 
				default_simulated_reference_temperature);
	}

	@Override
	public int getSimulatedReferenceJunctionTemperature() throws Exception {
		return this.simulatedReferenceTemperature;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TCoupleBased_TemperatureFunction_Configuration [openDetectionEnable=")
				.append(openDetectionEnable).append(", referenceJuntionType=").append(referenceJuntionType)
				.append(", simulatedReferenceTemperature=").append(simulatedReferenceTemperature)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}


	

}