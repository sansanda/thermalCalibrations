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
public class FRTDBased_TemperatureFunction_Configuration extends TemperatureFunction_Configuration
		implements I_FRTDBased_TemperatureFunction_Configuration {
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
		
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(FRTDBased_TemperatureFunction_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private int 	rZero;
	private float 	alpha;
	private float 	beta;
	private float 	delta;
	
	private int 	min_rZero_value;
	private int 	max_rZero_value;
	private int 	default_rZero_value;
	
	private float 	min_alpha_value;
	private float 	max_alpha_value;
	private float 	default_alpha_value;

	private float 	min_beta_value;
	private float 	max_beta_value;
	private float 	default_beta_value;

	private float 	min_delta_value;
	private float 	max_delta_value;
	private float 	default_delta_value;

	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public FRTDBased_TemperatureFunction_Configuration() throws Exception {
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
		logger.info("Initializing FRTD Base Temperature Valid Function Parameters from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("FRTD"))
		{
			JSONObject frtd = (JSONObject)jObj.get("FRTD");
			
			if (frtd.containsKey("USER"))
			{
				JSONObject user = (JSONObject)frtd.get("USER");
				
				if (user.containsKey("RZERO"))
				{
					JSONObject rZero = (JSONObject) user.get("RZERO");
					this.max_rZero_value = ((Long)rZero.get("max")).intValue();
					this.min_rZero_value = ((Long)rZero.get("min")).intValue();
					this.default_rZero_value = ((Long)rZero.get("default")).intValue();
				}
				
				if (user.containsKey("ALPHA"))
				{
					JSONObject alpha = (JSONObject) user.get("ALPHA");
					this.max_alpha_value = ((Double)alpha.get("max")).floatValue();
					this.min_alpha_value = ((Double)alpha.get("min")).floatValue();
					this.default_alpha_value = ((Double)alpha.get("default")).floatValue();
				}
				
				if (user.containsKey("BETA"))
				{
					JSONObject beta = (JSONObject) user.get("BETA");
					this.max_beta_value = ((Double)beta.get("max")).floatValue();
					this.min_beta_value = ((Double)beta.get("min")).floatValue();
					this.default_beta_value = ((Double)beta.get("default")).floatValue();
				}
				
				if (user.containsKey("DELTA"))
				{
					JSONObject delta = (JSONObject) user.get("DELTA");
					this.max_delta_value = ((Double)delta.get("max")).floatValue();
					this.min_delta_value = ((Double)delta.get("min")).floatValue();
					this.default_delta_value = ((Double)delta.get("default")).floatValue();
				}
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
		logger.info("Initializing FRTD Based Temperature Function Configuration from jObj ... ");
				
		String transducerType = (String)jObj.get("transducerType");
				
		if (transducerType.equals(SenseFunction_Configuration.FRTD_USER_TYPE_TRANSDUCER))
		{
			if (jObj.containsKey("transducerConfiguration"))
			{
				JSONObject transducerConfiguration = (JSONObject)jObj.get("transducerConfiguration");
				if (transducerConfiguration.containsKey("userTypeConstants"))
				{
					JSONObject userTypeConstants = (JSONObject)transducerConfiguration.get("userTypeConstants");
					this.setRZero(((Long)userTypeConstants.get("rZero")).intValue());
					this.setAlpha(((Double)userTypeConstants.get("alpha")).floatValue());
					this.setBeta(((Double)userTypeConstants.get("beta")).floatValue());
					this.setDelta(((Double)userTypeConstants.get("delta")).floatValue());		
				}
			}	
		}	
	}
		
	@Override
	public void setRZero(int value) throws Exception {
		this.rZero = DataValidators.range_Validator(value, this.min_rZero_value, this.max_rZero_value, this.default_rZero_value);
	}

	@Override
	public int getRZero() throws Exception {
		return this.rZero;
	}

	@Override
	public void setAlpha(float value) throws Exception {
		this.alpha = DataValidators.range_Validator(value, this.min_alpha_value, this.max_alpha_value, this.default_alpha_value);
	}

	@Override
	public float getAlpha() throws Exception {
		return this.alpha;
	}

	@Override
	public void setBeta(float value) throws Exception {
		this.beta = DataValidators.range_Validator(value, this.min_beta_value, this.max_beta_value, this.default_beta_value);
	}

	@Override
	public float getBeta() throws Exception {
		return this.beta;
	}

	@Override
	public void setDelta(float value) throws Exception {
		this.delta = DataValidators.range_Validator(value, this.min_delta_value, this.max_delta_value, this.default_delta_value);

	}

	@Override
	public float getDelta() throws Exception {
		return this.delta;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FRTDBased_TemperatureFunction_Configuration [rZero=").append(rZero).append(", alpha=")
				.append(alpha).append(", beta=").append(beta).append(", delta=").append(delta)
				.append(", min_rZero_value=").append(min_rZero_value).append(", max_rZero_value=")
				.append(max_rZero_value).append(", default_rZero_value=").append(default_rZero_value)
				.append(", min_alpha_value=").append(min_alpha_value).append(", max_alpha_value=")
				.append(max_alpha_value).append(", default_alpha_value=").append(default_alpha_value)
				.append(", min_beta_value=").append(min_beta_value).append(", max_beta_value=").append(max_beta_value)
				.append(", default_beta_value=").append(default_beta_value).append(", min_delta_value=")
				.append(min_delta_value).append(", max_delta_value=").append(max_delta_value)
				.append(", default_delta_value=").append(default_delta_value).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}

	
}
