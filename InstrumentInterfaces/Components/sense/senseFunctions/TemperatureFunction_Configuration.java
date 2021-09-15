package sense.senseFunctions;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import dataValidators.DataValidators;
import sense.Sense_Subsystem;

public class TemperatureFunction_Configuration extends SenseFunction_Configuration
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
	
	public TemperatureFunction_Configuration() throws Exception 
	{
		this(Sense_Subsystem.TCOUPLE_TEMPERATURE_TRANSDUCER,Sense_Subsystem.TCOUPLE_K_TYPE_TRANSDUCER);
	}
	
	public TemperatureFunction_Configuration(String transducer, String transducerType) throws Exception 
	{
		super(Sense_Subsystem.FUNCTION_TEMPERATURE);
		this.setTransducer(transducer);
		this.setType(transducerType);
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
		logger.info("Initializing Temperature Sense_Function Configuration from jObj ... ");
		
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
		this.transducer = t;
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
