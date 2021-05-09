package sense.senseFunctions;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import dataValidators.DataValidators;

public abstract class TemperatureFunction_Configuration extends SenseFunction_Configuration
		implements I_TemperatureFunction_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
		
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(TemperatureFunction_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private String transducer;
	private String type;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public TemperatureFunction_Configuration() {
		// TODO Auto-generated constructor stub
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
		logger.info("Initializing Valid Function Parameters from jObj ... ");
		
		//Nothing to do here
		
	}
	
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Sense_Function Configuration from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("transducer")) 	this.setTransducer((String)jObj.get("transducer"));
		if (keySet.contains("transducerType")) 	this.setType((String)jObj.get("transducerType"));
	}
	
	@Override
	public void setType(String tt) throws Exception {
		//TODO: Validate param
		this.type = tt;
	}

	@Override
	public String getType() throws Exception {
		return this.type;
	}
	
	@Override
	public void setTransducer(String t) throws Exception {
		this.transducer = DataValidators.discreteSet_Validator(t, SenseFunction_Configuration.AVAILABLE_TEMPERATURE_TRANSDUCERS, SenseFunction_Configuration.FRTD_TEMPERATURE_TRANSDUCER);
	}

	@Override
	public String getTransducer() throws Exception {
		return this.transducer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TemperatureFunction_Configuration [transducer=").append(transducer).append(", type=")
				.append(type).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

	
	
	

}
