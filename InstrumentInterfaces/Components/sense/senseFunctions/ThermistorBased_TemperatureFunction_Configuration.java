/**
 * 
 */
package sense.senseFunctions;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import dataValidators.DataValidators;

/**
 * @author DavidS
 *
 */
public class ThermistorBased_TemperatureFunction_Configuration extends TemperatureFunction_Configuration
		implements I_ThermistorBased_TemperatureFunction_Configuration {
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
		
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(ThermistorBased_TemperatureFunction_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private int 	resistorValue;
	
	private int 	min_resistor_value;
	private int 	max_resistor_value;
	private int 	default_resistor_value;

	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public ThermistorBased_TemperatureFunction_Configuration() throws Exception {
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
	
	public void initializeFromJSON(JSONObject validFunctionParameters, JSONObject attributes) throws Exception
	{
		super.initializeFromJSON(validFunctionParameters, attributes);
		this.initializeValidFunctionParametersFromJSON(validFunctionParameters);
		this.initializeAttributesFromJSON(attributes);
	}
	
	private void initializeValidFunctionParametersFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Thermistor Base Temperature Valid Function Parameters from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("thermistor"))
		{
			JSONObject thermistor = (JSONObject)jObj.get("thermistor");
			
			if (thermistor.containsKey("resistorValue"))
			{
				JSONObject resistorValue = (JSONObject)thermistor.get("resistorValue");
				this.max_resistor_value = ((Long)resistorValue.get("max")).intValue();
				this.min_resistor_value = ((Long)resistorValue.get("min")).intValue();
				this.default_resistor_value = ((Long)resistorValue.get("default")).intValue();
			}		
		}
	}
		
	/**
	 * Metodo para la configuracion de los atributos especificos de un sensor de temperatura Thermistor. 
	 * @param jObj objeto del tipo JSONObject que contiene una estructura tipo JSON con la configuracion 
	 * de un canal de medida en concreto del tipo funcion TEMPERATURE cuyo atributo transducer corresponde
	 * al texto "thermistor".  
	 * @throws Exception
	 */
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Thermistor Based Temperature Function Configuration from jObj ... ");
					
		if (jObj.containsKey("transducerConfiguration"))
		{	
			JSONObject transducerConfiguration = (JSONObject)jObj.get("transducerConfiguration");
			if (transducerConfiguration.containsKey("resistorValue"))
			{
				this.setResistorValue(((Long)transducerConfiguration.get("resistorValue")).intValue());
			}
		}
	}

	@Override
	public void setResistorValue(int value) throws Exception {
		this.resistorValue = DataValidators.range_Validator(value, this.min_resistor_value, this.max_resistor_value, this.default_resistor_value);
	}

	@Override
	public int getResistorValue() throws Exception {
		return this.resistorValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThermistorBased_TemperatureFunction_Configuration [resistorValue=").append(resistorValue)
				.append(", min_resistor_value=").append(min_resistor_value).append(", max_resistor_value=")
				.append(max_resistor_value).append(", default_resistor_value=").append(default_resistor_value)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
	
}
