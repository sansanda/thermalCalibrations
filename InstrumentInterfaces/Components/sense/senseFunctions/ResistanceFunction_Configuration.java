/**
 * 
 */
package sense.senseFunctions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

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
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public ResistanceFunction_Configuration() {
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
		logger.info("Initializing Valid Function Parameters from jObj for Resistance Function... ");
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
