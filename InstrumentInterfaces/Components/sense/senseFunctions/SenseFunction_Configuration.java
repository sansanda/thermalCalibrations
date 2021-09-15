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

import dataValidators.DataValidators;
import sense.Sense_Subsystem;

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
	
	
	public static float max_nplc_cycles;
	public static float min_nplc_cycles;
	public static float default_nplc_cycles;
	
	public static int max_range;
	public static int min_range;
	public static int default_range;
	
	public static byte max_digits;
	public static byte min_digits;
	public static byte default_digits;
	
	public static int max_reference;
	public static int min_reference;
	public static int default_reference;
	
	public static float min_average_window;
	public static float max_average_window;
	public static float default_average_window;
	
	public static int max_average_count;
	public static int min_average_count;
	public static int default_average_count;


	
	//**************************************************************************
	//****************************STATIC CODE***********************************
	//**************************************************************************
	
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************

	private String	function_name;
	private float 	nplc_cycles;
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
	 * @throws Exception 
	 * 
	 */
	public SenseFunction_Configuration(String function_name) throws Exception 
	{
		this.function_name = function_name;
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
		this.initializeAttributesFromJSON(attributes);
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
		this.function_name = functionName;
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
		this.averageTControlType = DataValidators.discreteSet_Validator(averageTControl, Sense_Subsystem.AVAILABLE_AVERAGE_TCONTROL_TYPES, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL);
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
		builder.append("SenseFunction_Configuration [function_name=").append(function_name).append(", nplc_cycles=")
				.append(nplc_cycles).append(", range=").append(range).append(", resolution_digits=")
				.append(resolution_digits).append(", reference=").append(reference).append(", averageTControlType=")
				.append(averageTControlType).append(", average_window=").append(average_window)
				.append(", average_count=").append(average_count).append(", isAverageEnable=").append(isAverageEnable)
				.append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}



	
}
