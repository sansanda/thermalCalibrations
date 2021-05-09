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
 * Configuracion base para gran parte de las funciones de medida que suele usar un multimetro.
 * Esta configuracion base podria ser adoptada por, por ejemplo, las siguientes funciones:
 * TEMPERATURE o FRESistance, etc...
 * @author DavidS
 *
 */
public abstract class SenseFunction_Configuration implements I_SenseFunction_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(SenseFunction_Configuration.class);
	
	////////////////////////////////////FUNCTION CONSTANTS
	public final static String FUNCTION_VOLTAGE_DC = 				"VOLTage:DC";
	public final static String FUNCTION_VOLTAGE_AC = 				"VOLTage:AC";
	public final static String FUNCTION_CURRENT_DC = 				"CURRent:DC";
	public final static String FUNCTION_CURRENT_AC = 				"CURRent:AC";
	public final static String FUNCTION_RESISTANCE = 				"RESistance";
	public final static String FUNCTION_FOUR_WIRE_RESISTANCE = 		"FRESistance";
	public final static String FUNCTION_FREQUENCY = 				"FREQuency";
	public final static String FUNCTION_PERIOD = 					"PERiod";
	public final static String FUNCTION_TEMPERATURE = 				"TEMPerature";
	public final static String FUNCTION_CONTINUITY = 				"CONTinuity";
	
	public final static String[] AVAILABLE_FUNCTIONS_SET =
	{
		FUNCTION_VOLTAGE_DC,
		FUNCTION_VOLTAGE_AC,
		FUNCTION_CURRENT_DC,
		FUNCTION_CURRENT_AC,
		FUNCTION_RESISTANCE,
		FUNCTION_FOUR_WIRE_RESISTANCE,
		FUNCTION_FREQUENCY,
		FUNCTION_PERIOD,
		FUNCTION_TEMPERATURE,
		FUNCTION_CONTINUITY
	};

	public final static String AVERAGE_MOVING_TCONTROL = "MOVing";
	public final static String AVERAGE_REPEAT_TCONTROL = "REPeat";

	public final static String[] AVAILABLE_AVERAGE_TCONTROL_TYPES =
	{
		AVERAGE_MOVING_TCONTROL,
		AVERAGE_REPEAT_TCONTROL,
	};
	
	public final static String TCOUPLE_TEMPERATURE_TRANSDUCER 		= "TCouple";
	public final static String FRTD_TEMPERATURE_TRANSDUCER 			= "FRTD";
	public final static String THERMISTOR_TEMPERATURE_TRANSDUCER 	= "THERmistor";
	
	public final static String[] AVAILABLE_TEMPERATURE_TRANSDUCERS =
	{
		TCOUPLE_TEMPERATURE_TRANSDUCER,
		FRTD_TEMPERATURE_TRANSDUCER,
		THERMISTOR_TEMPERATURE_TRANSDUCER
	};
	
	public final static String TCOUPLE_J_TYPE_TRANSDUCER 		= "J";
	public final static String TCOUPLE_K_TYPE_TRANSDUCER 		= "K";
	public final static String TCOUPLE_T_TYPE_TRANSDUCER 		= "T";
	public final static String TCOUPLE_E_TYPE_TRANSDUCER 		= "E";
	public final static String TCOUPLE_R_TYPE_TRANSDUCER 		= "R";
	public final static String TCOUPLE_S_TYPE_TRANSDUCER 		= "S";
	public final static String TCOUPLE_B_TYPE_TRANSDUCER 		= "B";
	public final static String TCOUPLE_N_TYPE_TRANSDUCER 		= "N";
	
	public final static String[] AVAILABLE_TCOUPLE_TRANSDUCER_TYPES =
	{
		TCOUPLE_J_TYPE_TRANSDUCER,
		TCOUPLE_K_TYPE_TRANSDUCER,
		TCOUPLE_T_TYPE_TRANSDUCER,
		TCOUPLE_E_TYPE_TRANSDUCER,
		TCOUPLE_R_TYPE_TRANSDUCER,
		TCOUPLE_S_TYPE_TRANSDUCER,
		TCOUPLE_B_TYPE_TRANSDUCER,
		TCOUPLE_N_TYPE_TRANSDUCER
	};
	
	public final static String TCOUPLE_SIMULATED_REFERENCE_JUNCTION_TYPE = "SIMulated";
	public final static String TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE = "INTernal";
	public final static String TCOUPLE_EXTERNAL_REFERENCE_JUNCTION_TYPE = "EXTernal";
	
	public final static String[] TCOUPLE_REFERENCE_JUNCTION_TYPES = 
	{
		TCOUPLE_SIMULATED_REFERENCE_JUNCTION_TYPE,
		TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE,
		TCOUPLE_EXTERNAL_REFERENCE_JUNCTION_TYPE
	};
	
	public final static String FRTD_PT100_TYPE_TRANSDUCER 		= "PT100";
	public final static String FRTD_D100_TYPE_TRANSDUCER 		= "D100";
	public final static String FRTD_F100_TYPE_TRANSDUCER 		= "F100";
	public final static String FRTD_PT3916_TYPE_TRANSDUCER 		= "PT3916";
	public final static String FRTD_PT385_TYPE_TRANSDUCER 		= "PT385";
	public final static String FRTD_USER_TYPE_TRANSDUCER 		= "USER";
	
	public final static String[] AVAILABLE_FRTD_TRANSDUCER_TYPES =
	{
		FRTD_PT100_TYPE_TRANSDUCER,
		FRTD_D100_TYPE_TRANSDUCER,
		FRTD_F100_TYPE_TRANSDUCER,
		FRTD_PT3916_TYPE_TRANSDUCER,
		FRTD_PT385_TYPE_TRANSDUCER,
		FRTD_USER_TYPE_TRANSDUCER
	};
	
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private float max_nplc_cycles = 0.01f;
	private float min_nplc_cycles = 0.01f;
	private float default_nplc_cycles = 5.0f;
	
	private int max_range;
	private int min_range;
	private int default_range;
	
	private byte max_digits;
	private byte min_digits;
	private byte default_digits;
	
	private int max_reference;
	private int min_reference;
	private int default_reference;
	
	private float min_average_window;
	private float max_average_window;
	private float default_average_window;
	
	private int max_average_count;
	private int min_average_count;
	private int default_average_count;

	private String	function_name;
	private float 	nplc_cycles = default_nplc_cycles;
	private int 	range;
	private byte 	resolution_digits;
	private int 	reference;
	private String 	averageTControlType;
	private float 	average_window;
	private int 	average_count;
	private boolean isAverageEnable;
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************

	/**
	 * 
	 */
	public SenseFunction_Configuration() {
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
		this.initializeValidFunctionParametersFromJSON(validFunctionParameters);
		this.initializeAttributesFromJSON(attributes);
	}
	
	private void initializeValidFunctionParametersFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Valid Function Parameters from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("nplc")) 
		{
			
			JSONObject nplc = (JSONObject) jObj.get("nplc");
			this.max_nplc_cycles = ((Double)nplc.get("max")).floatValue();
			this.min_nplc_cycles = ((Double)nplc.get("min")).floatValue();
			this.default_nplc_cycles = ((Double)nplc.get("default")).floatValue();
		}
		
		if (keySet.contains("range")) 
		{
			
			JSONObject range = (JSONObject) jObj.get("range");
			this.max_range = ((Long)range.get("max")).intValue();
			this.min_range = ((Long)range.get("min")).intValue();
			this.default_range = ((Long)range.get("default")).intValue();
		}
		
		if (keySet.contains("digits")) 
		{
			
			JSONObject digits = (JSONObject) jObj.get("digits");
			this.max_digits = ((Long)digits.get("max")).byteValue();
			this.min_digits = ((Long)digits.get("min")).byteValue();
			this.default_digits = ((Long)digits.get("default")).byteValue();
		}
		
		if (keySet.contains("reference")) 
		{
			
			JSONObject reference = (JSONObject) jObj.get("reference");
			this.max_reference = ((Long)reference.get("max")).intValue();
			this.min_reference = ((Long)reference.get("min")).intValue();
			this.default_reference = ((Long)reference.get("default")).intValue();
		}
		
		if (keySet.contains("average")) 
		{
			
			JSONObject average = (JSONObject) jObj.get("average");
			
			JSONObject window = (JSONObject) average.get("window");
			JSONObject count = (JSONObject) average.get("count");
			
			this.max_average_window = ((Double)window.get("max")).floatValue();
			this.min_average_window = ((Double)window.get("min")).floatValue();
			this.default_average_window = ((Double)window.get("default")).floatValue();
			
			this.max_average_count = ((Long)count.get("max")).intValue();
			this.min_average_count = ((Long)count.get("min")).intValue();
			this.default_average_count = ((Long)count.get("default")).intValue();
		}
		
	}
	
	private void initializeAttributesFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Initializing Sense_Function Configuration from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		if (keySet.contains("function")) 	this.function_name = (String)jObj.get("function");
		if (keySet.contains("nplc")) 		this.setNPLC(((Double)jObj.get("nplc")).floatValue());
		if (keySet.contains("range")) 		this.setRange(((Long)jObj.get("range")).intValue());
		if (keySet.contains("digits")) 		this.setResolutionDigits(((Long)jObj.get("digits")).byteValue());
		if (keySet.contains("reference"))	this.setReference(((Long)jObj.get("reference")).intValue());
		if (keySet.contains("average")) 
		{
			JSONObject average = (JSONObject) jObj.get("average");
			
			this.setAverageWindow(((Double)average.get("window")).floatValue());
			this.setAverageTControl((String)average.get("tcontrol"));
			this.setAverageCount(((Long)average.get("count")).intValue());
			this.enableAverage((((String)average.get("state")).equals("ON") ? true:false));	
		}
	}
	
	@Override
	public String getFunctionName() throws Exception {
		// TODO Auto-generated method stub
		return this.function_name;
	}

	@Override
	public void setFunctionName(String functionName) throws Exception {	
		this.function_name = DataValidators.discreteSet_Validator(functionName, AVAILABLE_FUNCTIONS_SET, FUNCTION_VOLTAGE_DC);
	}

	@Override
	public float getNPLC() throws Exception {
		return this.nplc_cycles;
	}

	@Override
	public void setNPLC(float nplc) throws Exception {
		this.nplc_cycles = DataValidators.range_Validator(nplc, this.min_nplc_cycles, this.max_nplc_cycles, this.default_nplc_cycles);
	}

	@Override
	public int getRange() throws Exception {
		return this.range;
	}

	@Override
	public void setRange(int range) throws Exception {
		this.range = DataValidators.range_Validator(range, this.min_range, this.max_range, this.default_range);	
	}

	@Override
	public byte getResolutionDigits() throws Exception {
		return this.resolution_digits;
	}

	@Override
	public void setResolutionDigits(byte digits) throws Exception {
		this.resolution_digits = (byte)DataValidators.range_Validator(digits, this.min_digits, this.max_digits, this.default_digits);
	}

	@Override
	public int getReference() throws Exception {
		return this.reference;
	}

	@Override
	public void setReference(int reference) throws Exception {
		this.reference = DataValidators.range_Validator(reference, this.min_reference, this.max_reference, this.default_reference);
	}

	@Override
	public String getAverageTControl() throws Exception {
		return this.averageTControlType;
	}

	@Override
	public void setAverageTControl(String averageTControl) throws Exception {
		this.averageTControlType = DataValidators.discreteSet_Validator(averageTControl, AVAILABLE_AVERAGE_TCONTROL_TYPES, AVERAGE_REPEAT_TCONTROL);
	}

	@Override
	public float getAverageWindow() throws Exception {
		return this.average_window;
	}

	@Override
	public void setAverageWindow(float window) throws Exception {
		this.average_window = DataValidators.range_Validator(window, this.min_average_window, this.max_average_window, this.default_average_window);
	}

	@Override
	public int getAverageCount() throws Exception {
		return this.average_count;
	}

	@Override
	public void setAverageCount(int count) throws Exception {
		this.average_count = DataValidators.range_Validator(count, this.min_average_count, this.max_average_count, this.default_average_count);
	}

	@Override
	public boolean isAverageEnable() throws Exception {
		return isAverageEnable;
	}

	@Override
	public void enableAverage(boolean enable) throws Exception {
		this.isAverageEnable = enable;
	}

	public float getMax_nplc_cycles() {
		return max_nplc_cycles;
	}

	public float getMin_nplc_cycles() {
		return min_nplc_cycles;
	}

	public float getDefault_nplc_cycles() {
		return default_nplc_cycles;
	}

	public int getMax_range() {
		return max_range;
	}

	public int getMin_range() {
		return min_range;
	}

	public int getDefault_range() {
		return default_range;
	}

	public byte getMax_digits() {
		return max_digits;
	}

	public byte getMin_digits() {
		return min_digits;
	}

	public byte getDefault_digits() {
		return default_digits;
	}

	public int getMax_reference() {
		return max_reference;
	}

	public int getMin_reference() {
		return min_reference;
	}

	public int getDefault_reference() {
		return default_reference;
	}

	public float getMin_average_window() {
		return min_average_window;
	}

	public float getMax_average_window() {
		return max_average_window;
	}

	public float getDefault_average_window() {
		return default_average_window;
	}

	public int getMax_average_count() {
		return max_average_count;
	}

	public int getMin_average_count() {
		return min_average_count;
	}

	public int getDefault_average_count() {
		return default_average_count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SenseFunction_Configuration [max_nplc_cycles=").append(max_nplc_cycles)
				.append(", min_nplc_cycles=").append(min_nplc_cycles).append(", default_nplc_cycles=")
				.append(default_nplc_cycles).append(", max_range=").append(max_range).append(", min_range=")
				.append(min_range).append(", default_range=").append(default_range).append(", max_digits=")
				.append(max_digits).append(", min_digits=").append(min_digits).append(", default_digits=")
				.append(default_digits).append(", max_reference=").append(max_reference).append(", min_reference=")
				.append(min_reference).append(", default_reference=").append(default_reference)
				.append(", min_average_window=").append(min_average_window).append(", max_average_window=")
				.append(max_average_window).append(", default_average_window=").append(default_average_window)
				.append(", max_average_count=").append(max_average_count).append(", min_average_count=")
				.append(min_average_count).append(", default_average_count=").append(default_average_count)
				.append(", function_name=").append(function_name).append(", nplc_cycles=").append(nplc_cycles)
				.append(", range=").append(range).append(", resolution_digits=").append(resolution_digits)
				.append(", reference=").append(reference).append(", averageTControlType=").append(averageTControlType)
				.append(", average_window=").append(average_window).append(", average_count=").append(average_count)
				.append(", isAverageEnable=").append(isAverageEnable).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}

	
}
