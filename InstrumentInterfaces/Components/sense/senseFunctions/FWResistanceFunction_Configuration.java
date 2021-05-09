/**
 * 
 */
package sense.senseFunctions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

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
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private boolean isOffsetCompensatedEnabled;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public FWResistanceFunction_Configuration() {
		super();
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
		logger.info("Initializing Valid Function Parameters from jObj for FW Resistance Function... ");
	}
	
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing FW Resistance Function Configuration from jObj ... ");
		
		if (jObj.containsKey("ocompensated")) 
		{
			this.enableOffsetCompensation(((((String)jObj.get("ocompensated")).equals("ON") ? true:false)));
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
