/**
 * 
 */
package sense.senseFunctions;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

/**
 * Configuracion exclusiva para la funcion de medida voltage DC que suele usar un multimetro.
 * @author DavidS
 *
 */
public class VoltageDCFunction_Configuration extends SenseFunction_Configuration implements I_VoltageDCFunction_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(VoltageDCFunction_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private boolean is10MOhmsInputDividerEnabled;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public VoltageDCFunction_Configuration() {
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
		logger.info("Initializing Valid Function Parameters from jObj for Voltage DC Function... ");
	}
	
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Voltage DC Function Configuration from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("idivider")) 
		{
			this.enable10MOhmsInputDivider(((((String)jObj.get("idivider")).equals("ON") ? true:false)));
		}	
	}
	
	@Override
	public void enable10MOhmsInputDivider(boolean enable) throws Exception {
		this.is10MOhmsInputDividerEnabled = enable;
	}

	@Override
	public boolean is10MOhmsInputDividerEnabled() throws Exception {
		return this.is10MOhmsInputDividerEnabled;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VoltageDCFunction_Configuration [is10MOhmsInputDividerEnabled=")
				.append(is10MOhmsInputDividerEnabled).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

	
}
