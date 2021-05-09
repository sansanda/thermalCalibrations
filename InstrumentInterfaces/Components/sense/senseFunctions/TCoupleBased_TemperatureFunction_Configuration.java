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
public class TCoupleBased_TemperatureFunction_Configuration extends TemperatureFunction_Configuration
		implements I_TCoupleBased_TemperatureFunction_Configuration {
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
		
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(TCoupleBased_TemperatureFunction_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************

	private boolean openDetectionEnable;
	private String referenceJuntionType;
	private int simulatedReferenceTemperature;

	private int 	min_simulated_reference_temperature;
	private int 	max_simulated_reference_temperature;
	private int 	default_simulated_reference_temperature;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public TCoupleBased_TemperatureFunction_Configuration() throws Exception {
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
		logger.info("Initializing Thermo Couple Based Temperature Valid Function Parameters from jObj ... ");
		
		
		if (jObj.containsKey("tcouple"))
		{
			JSONObject tcouple = (JSONObject)jObj.get("tcouple");
			
			if (tcouple.containsKey("simulated_reference_temperature"))
			{
				JSONObject simulated_reference_temperature = (JSONObject)tcouple.get("simulated_reference_temperature");
				this.max_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("max")).intValue();
				this.min_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("min")).intValue();
				this.default_simulated_reference_temperature = ((Long)simulated_reference_temperature.get("default")).intValue();
			}		
		}
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
		logger.info("Initializing Thermo Couple Based Temperature Function Configuration from jObj ... ");
				
		String transducerType = (String)jObj.get("transducerType");
				
		
		if (!(DataValidators.discreteSet_Validator(transducerType, SenseFunction_Configuration.AVAILABLE_TCOUPLE_TRANSDUCER_TYPES, "NONE").equals("NONE")))
		{
			if (jObj.containsKey("transducerConfiguration"))
			{
				JSONObject transducerConfiguration = (JSONObject)jObj.get("transducerConfiguration");
				
				this.enableOpenDetection((((String)transducerConfiguration.get("odetect")).equals("ON")? true:false));
				
				if (transducerConfiguration.containsKey("rjunction"))
				{
					JSONObject rjunction = (JSONObject)transducerConfiguration.get("rjunction");
					this.setReferenceJunctionType((String)rjunction.get("referenceJunctionType"));
					this.setSimulatedReferenceJunctionTemperature(((Long)rjunction.get("simulatedTemperature")).intValue());	
				}
			}	
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
		this.referenceJuntionType = DataValidators.discreteSet_Validator(type, SenseFunction_Configuration.TCOUPLE_REFERENCE_JUNCTION_TYPES, SenseFunction_Configuration.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE);
		
	}

	@Override
	public String getReferenceJunctionType() throws Exception {
		return this.referenceJuntionType;
	}

	@Override
	public void setSimulatedReferenceJunctionTemperature(int value) throws Exception {
		this.simulatedReferenceTemperature = DataValidators.range_Validator(value, this.min_simulated_reference_temperature, this.max_simulated_reference_temperature, this.default_simulated_reference_temperature);
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
				.append(", min_simulated_reference_temperature=").append(min_simulated_reference_temperature)
				.append(", max_simulated_reference_temperature=").append(max_simulated_reference_temperature)
				.append(", default_simulated_reference_temperature=").append(default_simulated_reference_temperature)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
	
	

}
