package multimeters;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.CommPort_I;
import rs232.JSSC_S_Port;

/**
 * Keithley2700_v5 es una copia de Keithley2700 pero modificada para trabajar
 * en un sistema de 64 bits. Por lo tanto hace uso de las librerias RXTX compiladas 
 * para trabajar con esta arquitectura.
 * @author david
 *
 */
public class Keithley2700_v6 {
	
	
	final static String VERSION = "6.0.0";
	
	//********************************************************************************************
	//********************************KEITHLEY PARAMETERS*****************************************
	//********************************************************************************************
	

	////////////////////////////////////FUNCTION CONSTANTS
	public final static String FUNCTION_VOLTAGE_DC = 				"VOLTage:DC";
	public final static String FUNCTION_VOLTAGE_AC = 				"VOLTage:AC";
	public final static String FUNCTION_CURRENT_DC = 				"CURRent:DC";
	public final static String FUNCTION_CURRENT_AC = 				"CURRent:AC";
	public final static String FUNCTION_RESISTANCE = 				"RESistance";
	public final static String FUNCTION_FOUR_WIRE_RESISTANCE = 	"FRESistance";
	public final static String FUNCTION_FREQUENCY = 				"FREQuency";
	public final static String FUNCTION_PERIOD = 					"PERiod";
	public final static String FUNCTION_TEMPERATURE = 				"TEMPerature";
	public final static String FUNCTION_CONTINUITY = 				"CONTinuity";
	public final static String[] FUNCTIONS_SET =
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
	
	
	////////////////////////////////////TEMPERATURE CONSTANTS
	
	
	///////////////////////////////////UNITS
	
	public final static String TEMPERATURE_UNIT_C = 		"C";
	public final static String TEMPERATURE_UNIT_CEL = 		"CEL";
	public final static String TEMPERATURE_UNIT_F = 		"F";
	public final static String TEMPERATURE_UNIT_FAR = 		"FAR";
	public final static String TEMPERATURE_UNIT_K = 		"K";
	
	public final static String[] TEMPERATURE_UNITS_SET = {
			TEMPERATURE_UNIT_C,
			TEMPERATURE_UNIT_CEL,
			TEMPERATURE_UNIT_F,
			TEMPERATURE_UNIT_FAR,
			TEMPERATURE_UNIT_K
			
	};
	
	///////////////////////////////////SENSORS 
	
	public final static String TEMPERATURE_SENSOR_TCOUPLE = 		"TCouple";
	public final static String TEMPERATURE_SENSOR_THERmistor = 	"THERmistor";
	public final static String TEMPERATURE_SENSOR_FRTD = 			"FRTD";
	
	public final static String[] TEMPERATURE_SENSORS_SET = {
			TEMPERATURE_SENSOR_TCOUPLE,
			TEMPERATURE_SENSOR_THERmistor,
			TEMPERATURE_SENSOR_FRTD
	};
	
	//////////////////////////////////TCOUPLE SENSOR TYPES
	
	public final static String TEMPERATURE_TCOUPLE_TYPE_J = 		"J";
	public final static String TEMPERATURE_TCOUPLE_TYPE_K = 		"K";
	public final static String TEMPERATURE_TCOUPLE_TYPE_T = 		"T";
	public final static String TEMPERATURE_TCOUPLE_TYPE_E = 		"E";
	public final static String TEMPERATURE_TCOUPLE_TYPE_R = 		"R";
	public final static String TEMPERATURE_TCOUPLE_TYPE_S = 		"S";
	public final static String TEMPERATURE_TCOUPLE_TYPE_B = 		"B";
	public final static String TEMPERATURE_TCOUPLE_TYPE_N = 		"N";
	
	public final static String[] TEMPERATURE_TCOUPLE_TYPES_SET = {
			TEMPERATURE_TCOUPLE_TYPE_J,
			TEMPERATURE_TCOUPLE_TYPE_K,
			TEMPERATURE_TCOUPLE_TYPE_T,
			TEMPERATURE_TCOUPLE_TYPE_E,
			TEMPERATURE_TCOUPLE_TYPE_R,
			TEMPERATURE_TCOUPLE_TYPE_S,
			TEMPERATURE_TCOUPLE_TYPE_B,
			TEMPERATURE_TCOUPLE_TYPE_N
	};

	//////////////////////////////////THERmistor SENSOR TYPES
	
	public final static int TEMPERATURE_THERmistor_TYPE_MIN = 1950;
	public final static int TEMPERATURE_THERmistor_TYPE_MAX = 10050;
	
	//////////////////////////////////FRTD SENSOR TYPES
	
	public final static String TEMPERATURE_FRTD_TYPE_PT100 = 		"PT100";
	public final static String TEMPERATURE_FRTD_TYPE_D100 = 		"D100";
	public final static String TEMPERATURE_FRTD_TYPE_F100 = 		"F100";
	public final static String TEMPERATURE_FRTD_TYPE_PT3916 = 		"PT3916";
	public final static String TEMPERATURE_FRTD_TYPE_PT385 = 		"PT385";
	public final static String TEMPERATURE_FRTD_TYPE_USER = 		"USER";
	
	public final static String[] TEMPERATURE_FRTD_TYPES_SET = {
			TEMPERATURE_FRTD_TYPE_PT100,
			TEMPERATURE_FRTD_TYPE_D100,
			TEMPERATURE_FRTD_TYPE_F100,
			TEMPERATURE_FRTD_TYPE_PT3916,
			TEMPERATURE_FRTD_TYPE_PT385,
			TEMPERATURE_FRTD_TYPE_USER
	};
	
	////////////////////////////////////TRIGGER CONSTANTS
	
	//	SCPI commands — triggering 
	//	ABORt Reset trigger system
	//	INITiate[:IMMediate] Initiate one trigger cycle
	//	INITiate:CONTinuous <b> Enable/disable continuous initiation; ON or OFF.
	//	FETCh? Request the last reading(s)
	//	READ? Perform an ABORt, INITiate, and a FETCh? 
	//	TRIGger:SOURce <name> Select control source; IMMediate, TIMer, MANual, BUS, or EXTernal. Default: IMM
	//	TRIGger:TIMer <n> Set timer interval; 0 to 999999.999 (sec). Default: 0.1
	//	TRIGger:COUNt <NRf> Set trigger count; 1 to 55000 or INFinity.
	//	TRIGger:DELay <n> Set delay; 0 to 999999.999 (sec). Default: 0
	//	TRIGger:DELay:AUTO <b> Enable or disable auto delay. 
	//	TRIGger:SIGNal Loop around control source. 
	//	SAMPle :COUNt <NRf> Set sample count; 1 to 55000. Default: 1 
	//	[:SENSe[1] Optional root command for HOLD commands. 
	//		:HOLD:WINDow <NRf> Set Hold window in %; <NRf> = 0.01 to 20. 1
	//		:HOLD:COUNt <NRf> Set Hold count; <NRf> = 2 to 100. 5
	//		:HOLD:STATe <b> Enable or disable Hold. OFF
	//	:SYSTem:BEEPer:STATe <b> Enable or disable the beeper. Default: ON
	//	*RST Restore *RST defaults (see “Default” column of this table). Places 2700 in the idle state.
	
	//TRIGGER_SOURCE
	public final static String TRIGGER_SOURCE_IMMEDIATE = 	"IMMediate";
	public final static String TRIGGER_SOURCE_TIMER = 		"TIMer";
	public final static String TRIGGER_SOURCE_MANUAL= 		"MANual";
	public final static String TRIGGER_SOURCE_BUS= 		"BUS";
	public final static String TRIGGER_SOURCE_EXTERNAL= 	"EXTernal";
	
	public final static String[] TRIGGER_SOURCE_SET = {
			TRIGGER_SOURCE_IMMEDIATE,
			TRIGGER_SOURCE_TIMER,
			TRIGGER_SOURCE_MANUAL,
			TRIGGER_SOURCE_BUS,
			TRIGGER_SOURCE_EXTERNAL
	};
	
	//TRIGGER_TIMER
	public final static double TRIGGER_SOURCE_TIMER_MAX = 999999.999; 	//seconds
	public final static double TRIGGER_SOURCE_TIMER_MIN = 0.001;		//seconds
	
	//TRIGGER_COUNT
	public final static int 	TRIGGER_COUNT_MAX = 55000; 			//counts
	public final static int 	TRIGGER_COUNT_MIN = 1;				//counts
	public final static String TRIGGER_COUNT_INF = "INF";			//counts
	
	//TRIGGER DELAY
	public final static double TRIGGER_DELAY_MAX = 55000; 			//seconds
	public final static double TRIGGER_DELAY_MIN = 0;				//seconds
	
	//SAMPLE_COUNT
	public final static int 	SAMPLE_COUNT_MAX = 55000; 	//counts
	public final static int 	SAMPLE_COUNT_MIN = 1;		//counts

	
	//AVERAGE
	public final static int 		AVERAGE_FILTER_COUNT_MAX = 100;
	public final static int 		AVERAGE_FILTER_COUNT_MIN = 1;
	public final static float 		AVERAGE_FILTER_WINDOW_MIN = 0f;
	public final static float 		AVERAGE_FILTER_WINDOW_MAX = 10f;
	
	public final static String 	AVERAGE_FILTER_CONTROL_TYPE_MOVING = "MOVing";
	public final static String 	AVERAGE_FILTER_CONTROL_TYPE_REPEAT = "REPeat";
	
	public final static String[] AVERAGE_FILTER_CONTROL_TYPES_SET = {
			AVERAGE_FILTER_CONTROL_TYPE_MOVING,
			AVERAGE_FILTER_CONTROL_TYPE_REPEAT
	};
	
	
	//FORMAT COMMANDS 
	
	//DATA TYPE
	
	public final static String FORMAT_TYPE_ASCII = "ASCii"; 	//ASCii format
	public final static String FORMAT_TYPE_SREAL = "SREal";	//Binary IEEE-754 single precision format
	public final static String FORMAT_TYPE_REAL  = "REAL"; 	//if length = 32 --> Binary IEEE-754 single precision format
														//if length = 32 --> Binary IEEE-754 single precision format
	public final static String FORMAT_TYPE_DREAL  = "DREal"; 	//DREal = Binary IEEE-754 double precision format
	
	public final static String[] FORMAT_TYPES_SET = {
			FORMAT_TYPE_ASCII,
			FORMAT_TYPE_SREAL,
			FORMAT_TYPE_REAL,
			FORMAT_TYPE_DREAL
	};
	
	
	//ELEMENTS RETURNED
	
	public final static String FORMAT_ELEMENT_READING = "READing"; 	//DMM reading
	public final static String FORMAT_ELEMENT_UNITS 	= "UNITs";		//Units
	public final static String	FORMAT_ELEMENT_TSTAMP 	= "TSTamp"; 	//Timestamp
	public final static String	FORMAT_ELEMENT_RNUMBER = "RNUMber";	//Reading number
	public final static String FORMAT_ELEMENT_CHANNEL = "CHANnel";	//Channel number
	public final static String FORMAT_ELEMENT_LIMITS 	= "LIMits";		//Limits reading
	
	public final static String[] FORMAT_ELEMENTS_SET = {
			FORMAT_ELEMENT_READING,
			FORMAT_ELEMENT_UNITS,
			FORMAT_ELEMENT_TSTAMP,
			FORMAT_ELEMENT_RNUMBER,
			FORMAT_ELEMENT_CHANNEL,
			FORMAT_ELEMENT_LIMITS
	};
	
	//********************************************************************************************
	//******************************************CONSTANTS*****************************************
	//********************************************************************************************
	
	private static int MAX_NUMBER_OF_CHANNELS = 22;
	private static int MIN_NUMBER_OF_CHANNELS = 1;
	private static int MAX_AVG = 100;
	private static int MIN_AVG = 1;
	private static int MAX_NUMBER_OF_READINGS_IN_BUFFER = 55000;
	private static int MIN_NUMBER_OF_READINGS_IN_BUFFER = 1;
	private static double MAX_TRIGGER_DELAY_IN_SECONDS = 999999.999;
	private static double MIN_DELAY_IN_MILLISECONDS = 0;
	
	//Use of regular expressions to match Strings containing substrings like:
	//-1.23E99 | 1E0 | -9.999e-999 --> Match
	//+10E0 | 2.3e5.4 --> Not Match
	//The objective is to extract +1.23456789E-03 from +1.23456789E-03VDC, for example
	public static Pattern SCIENTIFIC_NOTATION_PATTERN = Pattern.compile("[+-]?\\d(\\.\\d+)?[Ee][+-]?\\d+");
	

	//********************************************************************************************
	//******************************************VARIABLES*****************************************
	//********************************************************************************************

	private CommPort_I commAdapter = null;
	private boolean checkErrors = true;
	private int debug_level = 5;
	
	//default constructor
	public Keithley2700_v6(CommPort_I commAdapter, boolean _checkErrors, int debug_level)throws Exception{
		super();
		this.commAdapter = commAdapter; 
		this.checkErrors = _checkErrors;
		this.debug_level = debug_level;
	}
	
	
	//Getters and Setters
	//Other Methods
	
	
	//********************************************************************************************
	//*******************************PARAM AND DATA VALIDATORS************************************
	//********************************************************************************************
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////GENERIC VALIDATORS/////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////DISCRETE SET VALIDATORS
	
	private String discreteSet_Validator(String _value, String[] _set, String _notMatchValue) 
	{
		
		for (int i=0;i<_set.length;i++)
		{
			if (_value.equals(_set[i])) return _value;  
		}
		return _notMatchValue;
	}
	
	private int discreteSet_Validator(int _value, int[] _set, int _notMatchValue) 
	{
		
		for (int i=0;i<_set.length;i++)
		{
			if (_value == _set[i]) return _value;  
		}
		return _notMatchValue;
	}
	
	private double discreteSet_Validator(double _value, double[] _set, double _notMatchValue) 
	{
		
		for (int i=0;i<_set.length;i++)
		{
			if (Double.compare(_value, _set[i]) == 0) return _value;  
		}
		return _notMatchValue;
	}
	
	
	private int range_Validator(int _value, int _min, int _max, int _notMatchValue) 
	{
		
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
	
	private float range_Validator(float _value, float _min, float _max, float _notMatchValue) 
	{
		
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
	
	private double range_Validator(double _value, double _min, double _max, double _notMatchValue) 
	{
		
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////SPECIFIC VALIDATORS////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	private void validateFRTDSensorType(String _type) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating FRTD sensor type " + _type);
		
		if (this.discreteSet_Validator(_type, TEMPERATURE_FRTD_TYPES_SET, "NO").equals("NO")) throw new Exception ("Not valid FRTD sensor type");
	}
	
	private int validateTHERmistorValue(int _value, int _min, int _max, int _default_value) throws Exception, NumberFormatException 
	{
		if (this.debug_level<5) System.out.println("Validating THERmistor sensor value " + _value);
		
		return this.range_Validator(_default_value, _min, _max, _default_value);
	}
	
	private String validateTCOUPLESensorType(String _type, String[] _set, String _defaultValue) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating TCOUPLE sensor type " + _type);

		return this.discreteSet_Validator(_type, TEMPERATURE_TCOUPLE_TYPES_SET, _defaultValue);
	}
	
	private void validateTemperatureSensorType(String _type) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating temperature sensor type " + _type);
		
		if (this.discreteSet_Validator(_type, TEMPERATURE_SENSORS_SET, "NO").equals("NO")) throw new Exception ("Not valid temperature sensor");

	}
	
	private String validateTemperatureUnit(String _unit, String[] set, String _defaultValue) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating temperature unit " + _unit);
		
		return this.discreteSet_Validator(_unit, set, _defaultValue);
	}
	
	private String validateFormatType(String _type, String[] set, String _defaultValue) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating format type " + _type);
		
		return this.discreteSet_Validator(_type, set, _defaultValue);
	}
	
	private String validateFilterType(String _type, String[] set, String _defaultValue) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating filter type " + _type);
		
		return this.discreteSet_Validator(_type, set, _defaultValue);
	}
	
	private float validateFilterWindow(float _value, float _min, float _max, float _defaultValue) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating filter window " + String.valueOf(_value));
		
		return this.range_Validator(_value, _min, _max, _defaultValue);
	}
	
	private int validateFilterCount(int _value, int _min, int _max, int _defaultValue) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating filter count " + String.valueOf(_value));
		
		return this.range_Validator(_value, _min, _max, _defaultValue);
	}
	
	private String[] validateFormatElements(String[] _elements, String[] _set, String[] _defaultElements) throws Exception
	{
		if (this.debug_level<5) System.out.println("Validating format elements... ");
		
		for (int i=0;i<_elements.length;i++)
		{
			if (this.discreteSet_Validator(_elements[i], _set, "NO").equals("NO")) return _defaultElements;
		}
		return _elements;
	}
	
	private void validateFunctions(String[] _functions) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating functions " + Arrays.toString(_functions));
		
		for (int i=0;i<_functions.length;i++)
		{
			this.validateFunction(_functions[i]);
		}
	}
	
	private void validateFunction(String _function) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating function " + _function);
		
		if (this.discreteSet_Validator(_function, FUNCTIONS_SET, "NO").equals("NO")) throw new Exception ("Not valid function");
	}
	
	private String validateTriggerSource(String _type, String[] _set, String _defaultValue) throws Exception 
	{
		if (this.debug_level<5) System.out.println("Validating trigger source " + _type);
		
		return this.discreteSet_Validator(_type, _set, _defaultValue);
	}
	
	private double validateTriggerSource_Timer(double _valueInSecs, double _min, double _max, double _defaultValue) throws Exception {
		
		if (this.debug_level<5) System.out.println("Validating trigger timer " + String.valueOf(_valueInSecs) + " secs...");
		
		
		return this.range_Validator(_valueInSecs, _min, _max, _defaultValue);

	}
	
	private int validateTriggerCount(int _value, int _min, int _max, int _defaultValue) throws Exception {
		
		if (this.debug_level<5) System.out.println("Validating trigger count " + String.valueOf(_value) + " counts");
		
		
		return this.range_Validator(_value, _min, _max, _defaultValue);

	}
	
	private double validateTriggerDelay(double _valueInSecs, double _min, double _max, double _defaultValue) throws Exception {
		
		if (this.debug_level<5) System.out.println("Validating trigger delay " + String.valueOf(_valueInSecs) + " secs...");
		
		return this.range_Validator(_valueInSecs, _min, _max, _defaultValue);

	}

	private int validateSampleCount(int _value, int _min, int _max, int _defaultValue) throws Exception {
		
		if (this.debug_level<5) System.out.println("Validating sample count " + String.valueOf(_value) + " counts");
		
		
		return this.range_Validator(_value, _min, _max, _defaultValue);

	}
	
	//********************************************************************************************
	//*******************************COMMON SCPI COMMANDS*****************************************
	//********************************************************************************************

	public byte[] opc() throws Exception {
		if (this.debug_level<5) System.out.println("Asking for Operation Complete...");
		return this.commAdapter.ask("*OPC?");	
	}
	
	
	//********************************************************************************************
	//*******************************SYSTEM RELATED COMMANDS**************************************
	//********************************************************************************************
	public String errors() throws Exception {
		return new String(this.commAdapter.ask("SYSTem:ERRor?"));
	}
	
	//********************************************************************************************
	//*********************REGISTERS AND QUEUES RELATED COMMANDS**********************************
	//********************************************************************************************
	
	public String resetRegisters() throws Exception {
		/*
		 * *CLS -->
		 * Reset all bits of the following event registers to 0: 1
			Standard Event Register
			Operation Event Register
			Measurement Event Register
			Questionable Event Register
		 */
		/*
		 * STATus:PRESet --> 
		 * Reset all bits of the following enable registers to 0:
			Operation Event Enable Register
			Measurement Event Enable Register
			Questionable Event Enable Register
		 */
		if (this.debug_level<5) System.out.println("Reseting registers...");
		
		this.commAdapter.write("*CLS; STATus:PRESet");
		
		if (this.checkErrors) return "resetRegisters: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String clearErrorQueue() throws Exception {
		if (this.debug_level<5) System.out.println("Clearing error queues...");
		
		//*CLS --> Clear all messages from Error Queue 2
		//STATus:QUEue:CLEar --> Clear messages from Error Queue 3
		//SYSTem:CLEar --> Clear messages from Error Queue
		
		this.commAdapter.write("*CLS;STATus:QUEue:CLEar"); 
		
		if (this.checkErrors) return "clearErrorQueue: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public byte[] getStatusByteRegister() throws Exception{
		
		if (this.debug_level<5) System.out.println("Asking for Status Byte Register...");
		return this.commAdapter.ask("*STB?");
	}
	
	public byte[] getServiceRequestEnableRegister() throws Exception{
		if (this.debug_level<5) System.out.println("Asking for Service Request Enable Register...");
		return this.commAdapter.ask("*SRE?");
	}
	
	//********************************************************************************************
	//*********************FUNCTION CONFIGURATION RELATED COMMANDS********************************
	//********************************************************************************************
	
	

	
	//********************************************************************************************
	//*****************************SENSE RELATED COMMANDS*****************************************
	//********************************************************************************************
	
	
	/**
	 * 
	 * @param _temperature_sensor
	 * @param _sensor_type_OR_value
	 * @param _unit
	 * @param _filterType
	 * @param _filterWindow
	 * @param _filterCount
	 * @param _enableFilter
	 * @return
	 * @throws Exception
	 */
	public String configureForMeasuring_Temperature(String _temperature_sensor, 
													String _sensor_type_OR_value,
													String _unit,
													String _filterType,
													float _filterWindow,
													int _filterCount,
													boolean _enableFilter) throws Exception{
		
		//TODO Test

		this.validateTemperatureSensorType(_temperature_sensor);
		String unit = this.validateTemperatureUnit(_unit, TEMPERATURE_UNITS_SET, TEMPERATURE_UNIT_C);
		String filterType = this.validateFilterType(_filterType, AVERAGE_FILTER_CONTROL_TYPES_SET, AVERAGE_FILTER_CONTROL_TYPE_REPEAT);
		float filterWindow = this.validateFilterWindow(_filterWindow, AVERAGE_FILTER_WINDOW_MIN, AVERAGE_FILTER_WINDOW_MAX, AVERAGE_FILTER_WINDOW_MIN);
		int filterCount = this.validateFilterCount(_filterCount, AVERAGE_FILTER_COUNT_MIN, AVERAGE_FILTER_COUNT_MAX, AVERAGE_FILTER_COUNT_MIN);
		
		//Order for setting the temperature sensor (transducer)
		
		String errors 							= "";
		String temperatureSensorType_or_Value 	= "";
		String transducerOrder 					= "SENSe:TEMPerature:TRANsducer " + _temperature_sensor;
		String transducerTypeOrder 				= "SENSe:TEMPerature:" + _temperature_sensor + ":TYPE " + _sensor_type_OR_value;
		String unitOrder 						= "UNIT:TEMPerature " + _unit;
		String filterCountOrder 				= "SENSe:TEMPerature:AVERage:COUNt " + String.valueOf(filterCount);
		String filterWindowOrder 				= "SENSe:TEMPerature:AVERage:WINDow " + String.valueOf(filterWindow);
		
		String filterTypeOrder 					= "SENSe:TEMPerature:AVERage:TCONtrol " + _filterType;
		String enablefilterOrder 				= "SENSe:TEMPerature:AVERage:STATe " + (_enableFilter?"ON":"OFF");
		
		switch (_temperature_sensor) {
		
			case TEMPERATURE_SENSOR_TCOUPLE:
				temperatureSensorType_or_Value = this.validateTCOUPLESensorType(_sensor_type_OR_value, TEMPERATURE_TCOUPLE_TYPES_SET, TEMPERATURE_TCOUPLE_TYPE_K); 
				break;
				
			case TEMPERATURE_SENSOR_THERmistor:
				int tValue = Integer.parseInt(temperatureSensorType_or_Value);
				
				tValue = this.validateTHERmistorValue(  tValue, 
														TEMPERATURE_THERmistor_TYPE_MIN, 
														TEMPERATURE_THERmistor_TYPE_MAX, 
														TEMPERATURE_THERmistor_TYPE_MIN);	
				temperatureSensorType_or_Value = String.valueOf(tValue);
				break;
						
			case TEMPERATURE_SENSOR_FRTD:
				temperatureSensorType_or_Value = this.validateTCOUPLESensorType(_sensor_type_OR_value, TEMPERATURE_FRTD_TYPES_SET, TEMPERATURE_FRTD_TYPE_PT100); 
				break;
				
	
			default:
				break;
		}
		
		if (this.debug_level<5) System.out.println("Configuring the Keith for measurting temperature");
		
		
		///////////////////////////////TRANSDUCER CONFIGURATION 
		
		this.commAdapter.write(transducerOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS - ";
		
		this.commAdapter.write(transducerTypeOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS - ";
		
		/////////////////////////////UNITS CONFIGURATION
		
		this.commAdapter.write(unitOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS";

		//////////////////////////FILTER CONFIGURATION
		
		this.commAdapter.write(filterTypeOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS";
		
		this.commAdapter.write(filterWindowOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS";
		
		this.commAdapter.write(filterCountOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS";

		this.commAdapter.write(enablefilterOrder);
		
		if (this.checkErrors) errors = errors + "sense_configureTemperatureSensor: " + this.errors(); else return errors = errors + " NO CHECK ERRORS";

		
		return errors;
	}
	
	//********************************************************************************************
	//***************************TRIGGER RELATED COMMANDS*****************************************
	//********************************************************************************************

	/**
	 * Initiate one trigger cycle.
	 * @throws Exception
	 */
	public String trigger_init() throws Exception {
		
		if (this.debug_level<5) System.out.println("Initiating trigger....");
		this.commAdapter.write("INIT");
		if (this.checkErrors) return "trigger_init: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	public String trigger_abort() throws Exception {
		if (this.debug_level<5) System.out.println("Aborting trigger....");
		this.commAdapter.write("ABORt");
		if (this.checkErrors) return "trigger_abort: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	public String sendTriggerCommand() throws Exception {
		if (this.debug_level<5) System.out.println("Sending *TRG command to the bus....");
		this.commAdapter.write("*TRG");
		if (this.checkErrors) return "sendTriggerCommand: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String setTriggerSource(String _triggerSource) throws Exception {
		//TODO Test
		
		String triggerSource = this.validateTriggerSource(_triggerSource, TRIGGER_SOURCE_SET, TRIGGER_SOURCE_BUS);
		
		String order = "TRIGger:SOURce " + triggerSource;
		
		if (this.debug_level<5) System.out.println("Configuring trigger source as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setTriggerSource: "+ this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String setTriggerTimerInterval(float _time_insecs) throws Exception {
		//TODO Test
		
		float time = (float)this.validateTriggerSource_Timer(   _time_insecs, 
											TRIGGER_SOURCE_TIMER_MIN, 
											TRIGGER_SOURCE_TIMER_MAX,
											TRIGGER_SOURCE_TIMER_MIN);
		
		String order =  "TRIGger:TIMer "+ String.valueOf(time) + "; ";
		
		if (this.debug_level<5) System.out.println("Configuring trigger timer interval as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setTriggerTimerInterval: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String setTriggerDelay(float _triggerDelay_insecs) throws Exception {
		//TODO Test
		
		float delay = (float)this.validateTriggerDelay(	_triggerDelay_insecs, 
														TRIGGER_DELAY_MIN, 
														TRIGGER_DELAY_MAX,
														TRIGGER_DELAY_MIN);
		
		String order = "TRIGger:DELay "+ String.valueOf(delay) + "; ";	
		
		if (this.debug_level<5) System.out.println("Configuring trigger delay as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setTriggerDelay: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	public String enableTriggerContinousInitiation(boolean _enable) throws Exception {
		//TODO Test
		
		String order = "INITiate:CONTinuous " + (_enable?"ON":"OFF");
		
		if (this.debug_level<5) System.out.println("Configuring trigger continous initiation as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "enableTriggerContinousInitiation: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	/**
	 *  SAMPle:COUNt and TRIGger:COUNt — Sample count specifies the number of
		readings to scan and store in the buffer, while the trigger count specifies the
		number of scans to perform.
		If the sample count is greater than the number of channels in the scan list (scan list
		length), operation wraps around to the beginning of the scan list and continues. For
		example, assume the scan list is made up of channels 101, 102, and 103, and the
		sample count is set to 4. After channels 101, 102, and 103 are scanned, operation
		loops around to scan channel 101 again. The first and last readings in the buffer
		will be channel 101.
		When performing multiple scans (trigger count >1), sample readings overwrite the
		readings stored for the previous scan.
		Continuous initiation must be disabled in order to set the sample counter >1 (see
		Reference c).
	 * @param _triggerCount
	 * @return
	 * @throws Exception
	 */
	public String setTriggerCount(int _triggerCount) throws Exception {
		//TODO Test
		
		int triggerCount = this.validateTriggerCount(_triggerCount, TRIGGER_COUNT_MIN, TRIGGER_COUNT_MAX, TRIGGER_COUNT_MIN);
		
		String order = "TRIGger:COUNt "+ String.valueOf(triggerCount);
		
		
		if (this.debug_level<5) System.out.println("Configuring trigger count as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setTriggerCount: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	
	
	//********************************************************************************************
	//**************************GET DATA REALTED COMMANDS*****************************************
	//********************************************************************************************
	
	
	public byte[] read() throws Exception{
			
		/*  The READ? command performs an INITiate and then a FETCh?. The INITiate triggers a
			measurement cycle which puts new data in the sample buffer. With no math function
			enabled, FETCh? reads the data arrays from the sample buffer. With a math function
			enabled, the readings are the result of the math calculation.
			The following conditions must be met in order to use READ?:
				• Continuous initiation must be disabled. It can be disabled by sending *RST or
				  INIT:CONT OFF.
				• If there are readings stored in the data store, the sample count (SAMP:COUN)
			      must be set to 1.
				• To use a sample count >1, the data store must be cleared (empty). It can be cleared
				  by sending TRAC:CLE. 
		 */
		
		if (this.debug_level<5) System.out.println("Asking for READ?...");
		
		return this.commAdapter.ask("READ?");
	}
	
	
	/**
	 * Convierte una reading proveniente del buffer de lecturas del 2700 a un array de numeros reales.
	 * Dado que cada una de las readings almacenadas en el buffer de lecturas del 2700 puede estar formada 
	 * por una serie de campos (numeros reales) separados por el signo "," (dependiendo del numeros de elementos por lectura pedidos al keithley),
	 * es posible que cada una de las reading contenga más de un campo, por eso se convierte a un array de numeros reales. 
	 * @param reading
	 * @return Array de numeros reales con los campos de la reading
	 */
	public float[] convertReadingToFloatArray(byte[] reading) 
	{
		//TODO: En este metodo asumimos que la reading, despues de convertir de byte[], estará en formato ASCII.
		//		Es necesario implementar las otras posibilidades, es decir, que la reading pueda venir en formato 
		//		smple real o doble real. 
		
		
		float[] res = null;
		
		if (reading.length>0){
			
			String str = new String(reading);
			
			if (str!=null)
			{
				if (this.debug_level<5) System.out.println("Converting reading (byte[]) to float[]");
				
				//System.out.println(str);
				
				String[] rawData = str.split(",");
				res = new float[rawData.length];
				
				for (int i=0;i<rawData.length;i++)
				{
					Matcher matcher = SCIENTIFIC_NOTATION_PATTERN.matcher(rawData[i]);
					if (matcher.find())
					{
						res[i] = Float.parseFloat(matcher.group(0));
					}
				}
				
			}
		}
		
		return res;
	}
	
	//********************************************************************************************
	//**************************CHANNEL REALTED COMMANDS******************************************
	//********************************************************************************************
	
	
	public String closeIndividualChannel(int cardNumber, int channel, long closeTime_ms) throws Exception {
		//TODO Test
		
		
		if (cardNumber<1 || cardNumber>2) throw new Exception("Not valid card number");
		if (channel<1 || channel>20) throw new Exception("Not valid channel number");
		
		if (this.debug_level<5) System.out.println("Closing individual channel nº" + channel);
		
		this.commAdapter.write("ROUT:CLOS " + "(@" + String.valueOf(cardNumber) + String.format("%02d",channel) +")");
		Thread.sleep(closeTime_ms);
		if (this.checkErrors) return "closeIndividualChannel: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	public String closeConsecutiveChannels(int _cardNumber, int _minChannel, int _maxChannel, long _closeTime_ms) throws Exception {
		
		//TODO Test
		if (this.debug_level<5) System.out.println("Closing consecutive channels from " + Integer.toString(_minChannel) + " to " + Integer.toString(_maxChannel));
		this.commAdapter.write("ROUT:CLOS " + createChannelsList(_cardNumber, _minChannel, _maxChannel));
		Thread.sleep(_closeTime_ms * (_maxChannel-_minChannel));
		if (this.checkErrors) return "closeConsecutiveChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String closeMultipleChannels(int _cardNumber, int[] _channelsList, long closeTime_ms) throws Exception {
		
		//TODO Test
		if (this.debug_level<5) System.out.println("Closing multiple channels " + Arrays.toString(_channelsList));
		this.commAdapter.write("ROUTe:MULTiple:CLOSe " + createChannelsList(_cardNumber, _channelsList));
		Thread.sleep(closeTime_ms * (_channelsList.length));
		if (this.checkErrors) return "closeMultipleChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}

	public String openAllChannels(long _openTime_ms) throws Exception {
		if (this.debug_level<5) System.out.println("Opening all channels...");
		this.commAdapter.write("ROUT:OPEN:ALL");
		if (this.checkErrors) return "openAllChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}

	/**
	 * Be careful. If the channel list is too large we could have error 363 "input buffer overrun" error. 
	 * @param _cardNumber
	 * @param _channelsList
	 * @return
	 */
	public static String createChannelsList(int _cardNumber, int[] _channelsList)
	{
		//TODO Test
		
		String channelListAsString = "";
		
		if (_channelsList.length > 0)
		{
			channelListAsString = "(@";
			
			for (int i=0;i<_channelsList.length;i++)
			{
				String channel = "";
				channel = channel.concat(String.valueOf(_cardNumber) + String.format("%02d", (_channelsList[i])));
				
				channelListAsString = channelListAsString + channel + ",";
			}
			channelListAsString = channelListAsString.substring(0, channelListAsString.length()-1);
			channelListAsString = channelListAsString + ")";
		}
		
		return channelListAsString;
		
	}
	
	public static String createChannelsList(int _cardNumber, int _minChannel, int _maxChannel) throws Exception
	{
		//TODO Test
		
		String channelListAsString = "";
		
		if (_cardNumber<1 || _cardNumber>2) return channelListAsString;
		if (_minChannel<1 || _minChannel>20) return channelListAsString;
		if (_maxChannel<1 || _maxChannel>20) return channelListAsString;
		if (_minChannel>_maxChannel) return channelListAsString;
		
		
		
		if (_minChannel==_maxChannel) channelListAsString = "(@" + String.valueOf(_cardNumber) + String.format("%02d", _minChannel) + ")";
		else channelListAsString = "(@" + String.valueOf(_cardNumber) + String.format("%02d", _minChannel) + ":" + String.valueOf(_cardNumber) + String.format("%02d", _maxChannel) + ")";
					
		return channelListAsString;
		
	}
	//********************************************************************************************
	//********************************SCAN RELATED COMMANDS***************************************
	//********************************************************************************************
	
	/**
	 * 	
	 * Parameters <rang> = Range parameter for the specified function. For example, for
		DCV, range parameter value 10 selects the 10V range. See
		the “NOTES” that follow Table 13-1 for additional information.
		<res> = 0.1 i.e., 100.0 V (3H digits)
		0.01 i.e., 10.00 V (3H digits)
		0.001 i.e., 1.000 V (3H digits)
		0.0001 i.e., 1.0000 V (4H digits)
		0.00001 i.e., 1.00000 V (5H digits)
		0.000001 i.e., 1.000000 V (6H digits)
		The resolution of the <res> parameter value and the selected range
		sets the number of display digits. As shown above, with the 100V
		range selected and <res> = 0.1, a 100V reading will be displayed
		as 100.0 V (3H digits).
		The display will default to 3H digits when using parameter values
		that attempt to set the display below 3H digits. For example, a 10V
		reading using <res> = 0.1 for the 10V range is displayed as 10.00
		V, not 10.0 V.
		A command using parameter values that attempt to set the
		display above 7H digits is ignored, and generates error -221
		(settings conflict error).
		The <res> parameter is ignored when a <clist> is included in
		the command string. Resolution for the scanlist channel(s) is
		determined by the present setting for the specified function and
		by the present resolution setting for the specified function.
		See the “NOTES” that follow Table 13-1 for additional
		information.
		<clist> = Channel(s) in the scanlist to be configured. When the channel
		list parameter (<clist>) is included, the present instrument
		settings are not affected. Instead, the channel(s) in the <clist>
		for the specified function is configured. See the “NOTES” that
		follow Table 13-1 for additional information.
		
		Query CONFigure? Query the selected function.
		Description <clist> included — When the <clist> parameter is included with
		CONFigure command, the specified channel(s) for the scanlist assumes
		the *RST default settings for the specified function. Range can also be
		set for the channel(s) by including the <rang> parameter. If the
		resolution parameter (<res>) is included, it will be ignored.
		The present measurement function and the trigger model settings are not
		affected when the CONFigure command is sent with the <clist>
		parameter.
		<clist> not included — When the <clist> parameter is not included, the
		CONFigure command configures the instrument for subsequent measurements
		on the specified function. Range and resolution can also be
		set for the specified function.
		This command places the instrument in a “one-shot” measurement
		mode. You can then use the READ? command to trigger a measurement
		and acquire a reading (see READ?).
		When this command is sent without the <clist> parameter, the
		Model 2700 will be configured as follows:
		• The function specified by this command is selected. If specified,
		range and/or resolution are also set.
		• All controls related to the selected function are defaulted to the
		*RST values.
		• Continuous initiation is disabled (INITiate:CONTinuous OFF).
		• The control source of the Trigger Model is set to Immediate.
		• The count values of the Trigger Model are set to one.
		• The delay of the Trigger Model is set to zero.
		• The Model 2700 is placed in the idle state.
		• All math calculations are disabled.
		• Buffer operation is disabled. A storage operation presently in
		process will be aborted.
		• Autozero is enabled.
		Programming examples:
		Programming example #1 — The following command configures
		scanlist channels 101 through 105 for 4-wire resistance measurements
		on the 1M range.
		CONF:FRES 1e6, (@101:105)
		Programming example #2 — The following command selects the
		DCV function, 10V range, 3H digit resolution, and performs the “no
		<clist>” CONFigure operations:
		CONF:VOLT 10, 3.5
		
	 * @param _function
	 * @param _range
	 * @param _resolution
	 * @param _channelList
	 * @throws Exception
	 */
	
	public String setFunctionsForScanListChannels(String[] _functions, int _cardNumber, int[] _channelsList) throws Exception{	
		
		if (_functions.length!=_channelsList.length) throw new Exception("Functions List length and Channels List length must be same value");
		this.validateFunctions(_functions);
		
		String r = "";
		
		for (int i=0;i<_functions.length;i++)
		{
			r = r + this.setFunctionForScanChannel(_functions[i], _cardNumber, _channelsList[i]) + "\n";
		}
		
		return r;
		
	}
	
	public String setFunctionForScanListChannels(String _function, int _cardNumber, int[] _channelsList) throws Exception{	
		String channelsList = createChannelsList(_cardNumber, _channelsList);
		return "setFunctionForScanListChannels: " + this.setFunctionForScanListChannels(_function, channelsList);		
	}
	
	public String setFunctionForScanListChannels(String _function, int _cardNumber, int _minChannel, int _maxChannel) throws Exception{
		String channelsList = createChannelsList(_cardNumber,_minChannel,_maxChannel);
		return "setFunctionForScanListChannels: " + this.setFunctionForScanListChannels(_function, channelsList);			
	}

	public String setFunctionForScanChannel(String _function, int _cardNumber, int _channel) throws Exception{
		String channelsList = createChannelsList(_cardNumber,_channel,_channel);
		return "setFunctionForScanChannel:" + this.setFunctionForScanListChannels(_function, channelsList);			
	}
	
	private String setFunctionForScanListChannels(String _function, String _channelsList) throws Exception{
		
		//TODO check _range
		//TODO check _resolution
		//TODO check combination _range and _resolution
		//TODO Test

		if (this.debug_level<5) System.out.println("Configuring the instrument measure function.... as " + _function);
		
		this.validateFunction(_function);
		
		if (this.debug_level<5) System.out.println("Configuring FUNCTION for channels list =  " + _channelsList + " for scanning" );
		if (this.debug_level<5) System.out.println("Configuration values = Function: " + _function);
		
		String order = "CONFigure:" + _function + " " + _channelsList; 

		
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setFunctionForScanListChannels: " + this.errors(); else return " NO CHECK ERRORS";
				
	}
	
	public String setFiltersCountForScanListChannels(String[] _functions, int[] _filters,  int _cardNumber, int[] _channelsList) throws Exception{
		
		
		if (_functions.length!=_channelsList.length ||
			_functions.length!=_filters.length) 
			
			throw new Exception("Functions List length, Channels List and Filters List must be same value");
		
		this.validateFunctions(_functions);
		
		String r = "";
		
		for (int i=0;i<_functions.length;i++)
		{
			r = r + this.setFilterCountForScanListChannel(_functions[i], _filters[i], _cardNumber, _channelsList[i]) + "\n";
		}
		
		return r;			
	}

	public String setFilterCountForScanListChannel(String _function, int _filterCount, int _cardNumber, int _channel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_channel,_channel);
		return this.setFilterCountForScanListChannels(_function, _filterCount, channelsList);				
	}
	
	public String setFilterCountForScanListChannels(String _function, int _filterCount,  int _cardNumber, int[] _channelsList) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber, _channelsList);
		return this.setFilterCountForScanListChannels(_function, _filterCount, channelsList);
				
	}
	
	public String setFilterCountForScanListChannels(String _function, int _filterCount, int _cardNumber, int _minChannel, int _maxChannel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_minChannel,_maxChannel);
		return this.setFilterCountForScanListChannels(_function, _filterCount, channelsList);				
	}
	
	private String setFilterCountForScanListChannels(String _function, int _filterCount, String _channelsList) throws Exception {
		
		this.validateFunction(_function);
		
				
		String order = "";
		String filterCountOrder = "AVERage:COUNt " + String.valueOf(_filterCount);
		String filterEnableOrder = "AVERage:STATe " + "ON";
		
		
		if (this.debug_level<5) System.out.println("Configuring NPLC for channels list =  " + _channelsList + " for scanning" );
		if (this.debug_level<5) System.out.println("Configuration values = Function: " + _function + 
				", Filter Count: " + String.valueOf(_filterCount));
		
		order = _function + ":" + filterCountOrder + ", " + _channelsList;
		
		
		
		this.commAdapter.write(order);
		
		order = _function + ":" + filterEnableOrder + ", " + _channelsList;
		
		
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setFilterCountForScanListChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}
	

	public String setNPLCsForScanListChannels(String[] _functions, float[] _nplcs,  int _cardNumber, int[] _channelsList) throws Exception{
		
		
		if (_functions.length!=_channelsList.length ||
			_functions.length!=_nplcs.length) 
			
			throw new Exception("Functions List length, Channels List and NPLCs List must be same value");
		
		this.validateFunctions(_functions);
		
		String r = "";
		
		for (int i=0;i<_functions.length;i++)
		{
			r = r + this.setNPLCForScanListChannel(_functions[i], _nplcs[i], _cardNumber, _channelsList[i]) + "\n";
		}
		
		return r;			
	}

	public String setNPLCForScanListChannel(String _function, float _nplc, int _cardNumber, int _channel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_channel,_channel);
		return this.setNPLCForScanListChannels(_function, _nplc, channelsList);				
	}

	public String setNPLCForScanListChannels(String _function, float _nplc,  int _cardNumber, int[] _channelsList) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber, _channelsList);
		return this.setNPLCForScanListChannels(_function, _nplc, channelsList);
				
	}
	
	public String setNPLCForScanListChannels(String _function, float _nplc, int _cardNumber, int _minChannel, int _maxChannel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_minChannel,_maxChannel);
		return this.setNPLCForScanListChannels(_function, _nplc, channelsList);				
	}
	
	private String setNPLCForScanListChannels(String _function, float _nplc, String _channelsList) throws Exception {
		
		this.validateFunction(_function);
		
				
		String order = "";
		String nplc = "NPLC " + String.valueOf(_nplc);
		
		if (this.debug_level<5) System.out.println("Configuring NPLC for channels list =  " + _channelsList + " for scanning" );
		if (this.debug_level<5) System.out.println("Configuration values = Function: " + _function + 
				", NPLC: " + String.valueOf(_nplc));
		
		order = _function + ":" + nplc + ", " + _channelsList;
		
		
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setNPLCForScanListChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	
	public String setRangesForScanListChannels(String[] _functions, float[] _ranges,  int _cardNumber, int[] _channelsList) throws Exception{
		
		
		if (_functions.length!=_channelsList.length ||
			_functions.length!=_ranges.length) 
			
			throw new Exception("Functions List length, Channels List and Ranges List must be same value");
		
		this.validateFunctions(_functions);
		
		String r = "";
		
		for (int i=0;i<_functions.length;i++)
		{
			r = r + this.setRangeForScanListChannel(_functions[i], _ranges[i], _cardNumber, _channelsList[i]) + "\n";
		}
		
		return r;			
	}

	public String setRangeForScanListChannel(String _function, float _range, int _cardNumber, int _channel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_channel,_channel);
		return this.setRangeForScanListChannels(_function, _range, channelsList);				
	}
	
	public String setRangeForScanListChannels(String _function, float _range,  int _cardNumber, int[] _channelsList) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber, _channelsList);
		return this.setRangeForScanListChannels(_function, _range, channelsList);
				
	}
	
	public String setRangeForScanListChannels(String _function, float _range, int _cardNumber, int _minChannel, int _maxChannel) throws Exception{
		
		String channelsList = createChannelsList(_cardNumber,_minChannel,_maxChannel);
		return this.setRangeForScanListChannels(_function, _range, channelsList);				
	}
	
	private String setRangeForScanListChannels(String _function, float _range, String _channelsList) throws Exception {
		
		this.validateFunction(_function);
		
		if (_function.equals(FUNCTION_TEMPERATURE) || _function.equals(FUNCTION_CONTINUITY))
		{
			System.out.println("No range configuration needed for Temperature or Continuity function");
			return "setRangeForScanListChannels: 0,\"No error\" \n";
		}
				
		String order = "";
		String range = "";
		
		if (_range<0) range = "RANGe:AUTO ON";
		else range = "RANGe " + String.valueOf(_range);
		
		if (this.debug_level<5) System.out.println("Configuring channels list =  " + _channelsList + " for scanning" );
		if (this.debug_level<5) System.out.println("Configuration values = Function: " + _function + 
				", Range: " + String.valueOf(_range));
		
		order = _function + ":" + range + ", " + _channelsList;
			
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setRangeForScanListChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String setScanListChannels(int _cardNumber, int[] _channelList) throws Exception {
		return this.setScanListChannels(createChannelsList(_cardNumber, _channelList));
	}
	
	public String setScanListChannels(int _cardNumber, int _minChannel, int _maxChannel) throws Exception {
		return this.setScanListChannels(createChannelsList(_cardNumber, _minChannel, _maxChannel));		
	}
	
	private String setScanListChannels(String _channelsList) throws Exception {
		if (this.debug_level<5) System.out.println("Setting " + _channelsList + " channels " + "for scan list...");
		this.commAdapter.write("ROUT:SCAN " + _channelsList);
		if (this.checkErrors) return "setScanListChannels: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	/**
	 *  SAMPle:COUNt and TRIGger:COUNt — Sample count specifies the number of
		readings to scan and store in the buffer, while the trigger count specifies the
		number of scans to perform.
		If the sample count is greater than the number of channels in the scan list (scan list
		length), operation wraps around to the beginning of the scan list and continues. For
		example, assume the scan list is made up of channels 101, 102, and 103, and the
		sample count is set to 4. After channels 101, 102, and 103 are scanned, operation
		loops around to scan channel 101 again. The first and last readings in the buffer
		will be channel 101.
		When performing multiple scans (trigger count >1), sample readings overwrite the
		readings stored for the previous scan.
		Continuous initiation must be disabled in order to set the sample counter >1 (see
		Reference c).
	 * @param _count
	 * @return
	 * @throws Exception
	 */
	public String setImmediateScan() throws Exception {
		
		String order = "ROUT:SCAN:TSO IMM";
		
		if (this.debug_level<5) System.out.println("Enabling immediate scan as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setImmediateScan: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	public String enableScan(boolean _enable) throws Exception {
		
		String order = "ROUT:SCAN:LSEL " + (_enable?"INT":"NONE");
		
		if (this.debug_level<5) System.out.println("Enabling scan as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "enableScan: " + this.errors(); else return " NO CHECK ERRORS";
	}

	public String setSampleCount(int _sampleCount) throws Exception {
		
		int sampleCount = this.validateSampleCount(_sampleCount, SAMPLE_COUNT_MIN, SAMPLE_COUNT_MAX, SAMPLE_COUNT_MIN);
		
		String order = "SAMPle:COUNt "+ String.valueOf(sampleCount);
		
		if (this.debug_level<5) System.out.println("Setting sample count as " + order);
		
		this.commAdapter.write(order);
		
		if (this.checkErrors) return "setSampleCount: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	//********************************************************************************************
	//******************************FORMAT RELATED COMMANDS***************************************
	//********************************************************************************************
	
	/**
	 * Especifica en tipo de formato que tendrán los datos devueltos por el 2700. El formato podrá ser ASCii o Binario
	 * Solo afectará a las querys READ?, FETCh?, MEASure?, TRACe:DATA?, CALC1:DATA?, or CALC2:DATA?
	 * Las demás querys serán devueltas en formato ASCii independientemente del tipo de formato de los datos.
	 * Si se trabaja sobre ASCii, solo está permitido el formato AScii para los datos.  
	 * Por otro lado, independientemente del formato de datos deleccionado, el 2700 solo responderá a comandos en
	 * recibidos por este en formato ASCii.
	 * @param type. El formato de los datos devueltos por el 2700 para las querys READ?, FETCh?, MEASure?, TRACe:DATA?, CALC1:DATA?, or CALC2:DATA?  
	 * @param length puede ser 32 o 64 bits y solo se usará para formato de tipo REAL
	 * @return Si estamos en modo checkErrors se devuelve el resultado de la ejecucion del comando. Devuelve "NO CHECK ERRORS" en caso contrario.  
	 * @throws Exception
	 */
	public String formatData(String _type, int length) throws Exception
	{
		String order = "";
		
		
		String type = this.validateFormatType(_type, FORMAT_TYPES_SET, FORMAT_TYPE_ASCII);
		
		
		if (type==FORMAT_TYPE_REAL)
		{
			if (length == 32 || length == 64)
			{
				order = "FORMat:DATA " + type + "," + String.valueOf(length);
			} 
			else throw new Exception("Invalid Real Foermat Length");
				
		}
		order = "FORMat:DATA " + type;
		
		
		if (this.debug_level<5) System.out.println("Formating Data...");
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "formatData: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	
	public String formatElements(String[] _elements, String[] _defaultElements) throws Exception
	{
		String order = "";
		
		String[] elements = this.validateFormatElements(_elements, FORMAT_ELEMENTS_SET, _defaultElements);
		
		if (this.debug_level<5) System.out.println("Formating Elements...");
		
		for (int i=0;i<elements.length;i++)
		{ 
			order = order + elements[i] + ",";
			
		}
		order = order.substring(0, order.length()-1);
		
		this.commAdapter.write("FORMat:ELEMents " + order);
		if (this.checkErrors) return "formatElements: " + this.errors(); else return " NO CHECK ERRORS";	
	}
	
	
	//********************************************************************************************
	//***************************MEASURE REALTED COMMANDS*****************************************
	//********************************************************************************************
	
	/**
	 * 	
	 * 	
	 * 	Parameters <rang> = Range parameter for the specified function. For example, for
		DCV, range parameter value 10 selects the 10V range. See the
		“NOTES” that follow Table 13-1 for additional information.
		
		Resolution <res> = 0.1 i.e., 100.0 V (3H digits)
		0.01 i.e., 10.00 V (3H digits)
		0.001 i.e., 1.000 V (3H digits)
		0.0001 i.e., 1.0000 V (4H digits)
		0.00001 i.e., 1.00000 V (5H digits)
		0.000001 i.e., 1.000000 V (6H digits)
		The resolution of the <res> parameter value and the selected
		range sets the number of display digits. As shown above, with
		the 100V range selected and <res> = 0.1, a 100V reading will
		be displayed as 100.0 V (3H digits).
		The display will default to 3H digits when using parameter
		values that attempt to set the display below 3H digits. For
		example, a 10V reading using <res> = 0.1 for the 10V range is
		displayed as 10.00 V, not 10.0 V.
		A command using parameter values that attempt to set the
		display above 7H digits is ignored, and generates error -221
		(settings conflict error).
		See the “NOTES” that follow Table 13-1 for additional information.
		\n
		Channels List <clist> = Single channel only. When included, this is the channel to be
		closed and measured.
		Description The MEASure? command combines all of the other signal oriented
		measurement commands to perform a “one-shot” measurement and
		acquire the reading. If the <clist> parameter is included, the specified
		channel will close before performing the measurement.
		When a MEASure? command is sent, the specified function is selected.
		If specified, range and resolution will also set.
		
		Depending on the specified resolution, the measurement rate is set as
		follows:
		6H-digits NPLC = 1.0 Medium
		5H-digits NPLC = 0.1 Fast
		3H or 4H-digits NPLC = 0.01 >Fast
		If resolution is not specified, 6H-digit resolution and medium speed will
		be selected when MEAS? is sent.
		All other instrument settings related to the selected function are reset to
		the *RST defaults.
		If only MEASure? is sent, the Medium measurement rate is selected.
		NOTE If a function is not specified, the command executes as if the present function is
		specified. For example, assume the Ohm2 function is presently selected. When
		MEAS? is sent, the instrument resets to the *RST defaults for the Ohm2 function,
		and then performs a measurement.
		When this command is sent, the following commands execute in the
		order that they are presented:
		ABORt
		CONFigure:<function>
		READ?
		When ABORt is executed, the instrument goes into the idle state if
		continuous initiation is disabled. If continuous initiation is enabled, the
		operation re-starts at the beginning of the Trigger Model.
		When CONfigure is executed, the MEASure? parameters (<rang>,
		<res> and <clist>) are executed and the instrument goes into the “oneshot”
		measurement mode. It is similar to sending the CONFigure
		command with no <clist> parameter. See CONFigure for more details.
		When READ? is executed, its operations will then be performed. In
		general, another ABORt is performed, then an INITiate, and finally a
		FETCh? to acquire the reading. See READ? for more details.
		Programming examples:
		Programming example #1 — The following command measures DCV
		on channel 101 using the 10V range with 3H digit display resolution:
		MEAS:VOLT? 10, 0.01, (@101)
		Programming example #2 — The following command measures DCV
		on the 100V range:
		MEAS:VOLT? 100
		*/
	public float[] measure(String _function, float _range, float _resolution, int cardNumber, int channel) throws Exception{
		
		//TODO check _range
		//TODO check _resolution
		//TODO check combination _range and _resolution
		//TODO Test
		//TODO Bug when returning measure of more than one channel in channels list
		
		this.validateFunction(_function);
		
		if (this.debug_level<5) System.out.println("Measuring...." + _function );
		
		String order = "";
		
		String channelsList = createChannelsList(cardNumber, channel, channel);
		
		String resolution = String.valueOf(_resolution);
		
		byte[] reading = null;
		
		
		if (	_function == FUNCTION_TEMPERATURE ||
				_function == FUNCTION_CONTINUITY)
		{
			order = "MEASure:" + _function + "? " + channelsList;
			 
		}
		else 
		{
			order  = "MEASure:" + _function + "? " + _range + ", " + resolution + ", " + channelsList;
		}
			
		reading = this.commAdapter.ask(order);
		
		return this.convertReadingToFloatArray(reading);
	}
	
	
	
//	//********************************************************************************************
//	//**************************DC VOLTAGE REALTED COMMANDS***************************************
//	//********************************************************************************************
//	
//	public void configureAsMeasureDCVoltage(float range) throws Exception{
//		
//		
//		if (this.debug_level<5) System.out.println("Configuring the instrument as DC Measure VOLTAGE....");
//		
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("SENS:FUNC 'VOLT:DC'");
//		
//		if (range < 0 || range > 1010) this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
//		else this.commAdapter.write("SENS:VOLT:DC:RANG " + Float.toString(range));
//		
//	}
//	
//	public void configureAsDCVoltageAverageMeasure(float range, int _avg) throws Exception{
//
//		if (this.debug_level<5) System.out.println("Configuring the instrument as DC VOLTAGE Average measure"+" for "+Integer.toString(_avg)+" samples...");
//
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("SENS:FUNC 'VOLT:DC'");
//		
//		if (range < 0 || range > 1010) this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
//		else this.commAdapter.write("SENS:VOLT:DC:RANG " + Float.toString(range));
//		
//		this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
//		this.commAdapter.write("SENS:VOLT:AVER:STAT ON");
//		this.commAdapter.write("SENSE:VOLT:AVER:COUN "+Integer.toString(_avg));
//		
//	}
//	
//	
//	//********************************************************************************************
//	//**************************2W RESISTANCE REALTED COMMANDS************************************
//	//********************************************************************************************
//	
//	public void configureAsMeasure2WireResistance(float _range, boolean _filtered, String _filterType, int _filterCount, float _filterWindow) throws Exception{
//		
//		
//		if (this.debug_level<5) System.out.println("Configuring the instrument as 2W Measure....");
//		
//		this.commAdapter.write("FUNC 'RES'");
//		if (_range < 0 || _range > 120e6) this.commAdapter.write("SENS:RESistance:RANGe:AUTO ON");
//		else this.commAdapter.write("SENS:RESistance:RANGe " + String.valueOf(_range));
//		
//		if (_filtered)
//		{
//			this.commAdapter.write("SENS:RESistance:AVERage:STATe ON");
//			if (	_filterType != AVERAGE_FILTER_CONTROL_TYPE_MOVING && 
//					_filterType != AVERAGE_FILTER_CONTROL_TYPE_REPEAT) throw new Exception("Not permited filter type");
//			this.commAdapter.write("SENS:RESistance:AVERage:TCONtrol " + _filterType);
//			if (	_filterCount < AVERAGE_FILTER_COUNT_MIN || _filterCount > AVERAGE_FILTER_COUNT_MAX ) throw new Exception("Not valid filter count value");
//			this.commAdapter.write("SENS:RESistance:AVERage:COUNt " + String.valueOf(_filterCount));
//			if (	_filterWindow < AVERAGE_FILTER_WINDOW_MIN || _filterWindow > AVERAGE_FILTER_WINDOW_MAX ) throw new Exception("Not valid filter window value");
//			this.commAdapter.write("SENS:RESistance:AVERage:WINDow " + String.valueOf(_filterWindow));	
//		}
//		else
//		{
//			this.commAdapter.write("SENS:RESistance:AVERage:STATe OFF");
//		}
//
//	}
//	
//	
//	public void configureAs2WireResistance_Trigger_DataBuffer(float range, double _triggerDelayInSeconds)throws Exception{
//
//		
//		verifyParameters(-1, -1, -1, _triggerDelayInSeconds);
//
//		if (this.debug_level<5) System.out.println("Configuring the instrument as 2W measure, init by trigger command and store in internal Keithley data buffer");	
//		
//		//Configuramos para medida de resistencia 2wire
//		this.commAdapter.write("FUNC 'RES'");
//		
//		//Configuramos el rango de medida
//		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:RESistance:RANGe:AUTO ON");
//		else this.commAdapter.write("SENS:RESistance:RANGe " + Float.toString(range));
//		
//		//CONFIGURE THE TRIGGER LAYER
//		
//		this.commAdapter.write("TRIG:SOUR BUS"); //' Select BUS control source.
//
//		this.commAdapter.write("TRIG:COUN INF"); //Set trigger layer count to infinity.
//		
//		this.commAdapter.write("TRIG:DEL " + Double.toString(_triggerDelayInSeconds));
//		
//		this.commAdapter.write("INIT"); //Take 2700 out of idle.
//		
//		//Indicamos el tamaño del buffer 
////		this.commAdapter.write("TRAC:POIN "+ Integer.toString(_nSamples));
//		
//	}
//	
//	//********************************************************************************************
//	//**************************4W RESISTANCE REALTED COMMANDS************************************
//	//********************************************************************************************
//	
//	public void configureAsMeasure4WireResistance(float range) throws Exception{
//		
//		
//		if (this.debug_level<5) System.out.println("Configuring the instrument as 4W Measure....");
//		
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("FUNC 'FRES'");
//		
//		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
//		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
//		
//	}
//	
//	public void configureAs4WireResistanceAverageMeasure(float range, int _avg) throws Exception{
//
//		if (this.debug_level<5) System.out.println("Configuring the instrument as 4W Average Measure....");;
//		
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("FUNC 'FRES'");
//		
//		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
//		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
//		
//		this.commAdapter.write("SENSE:FRES:AVER:STAT ON");
//		this.commAdapter.write("SENSE:FRES:AVER:COUN "+Integer.toString(_avg));
//		this.commAdapter.write("FRES:OCOM ON");
//
//	}
//	
//	public void configureAs4WireResistance_Trigger_DataBuffer(float range, int _nSamples,double _triggerDelayInSeconds)throws Exception{
//		
//		verifyParameters(-1, -1, _nSamples, _triggerDelayInSeconds);
//
//		if (this.debug_level<5) System.out.println("Configuring the instrument as 4W measure, init by trigger command and store in internal Keithley data buffer");	
//		
//		//Configuramos para medida de resistencia 2wire
//		this.commAdapter.write("FUNC 'FRES'");
//		
//		//Configuramos el multimetro para medida con OCOMP
//		this.commAdapter.write("FRES:OCOM ON");
//		
//		//Configuramos el rango de medida
//		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
//		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
//		
//		this.commAdapter.write("INIT:CONT OFF");
//		//configures the instrument to perform one measurement cycle
//		this.commAdapter.write("TRIG:COUN 1");
//		
//		this.commAdapter.write("TRIG:DEL " + Double.toString(_triggerDelayInSeconds));
//		
//		//Take only one sample for every measurement cycle
//		this.commAdapter.write("SAMP:COUN 1");
//		
//		//Indicamos el tamaño del buffer 
//		this.commAdapter.write("TRAC:POIN "+ Integer.toString(_nSamples));
//		
//	}
//	
//
//	//********************************************************************************************
//	//***********************TEMPERATURE RESISTANCE RELATED COMMANDS******************************
//	//********************************************************************************************
//	
//	
//	public void configureAsTemperatureMeasure_PT100() throws Exception{
//		
//		
//		if (this.debug_level<5) System.out.println("Configuring the instrument as Temperature Measure with PT100....");
//		
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("FUNC 'TEMP'");
//		this.commAdapter.write("UNIT:TEMP C");
//		this.commAdapter.write("TEMP:TRAN FRTD");
//		this.commAdapter.write("TEMP:FRTD:TYPE PT100");
//		
//	}
//
//	public void configureAsTemperatureAverageMeasure_PT100(int _avg) throws Exception{
//		
//		
//		if (this.debug_level<5) System.out.println("Configuring the instrument as Average Temperature measure"+" for "+Integer.toString(_avg)+" samples...");
//		
//		//this.commAdapter.write("*RST");
//		this.commAdapter.write("INIT:CONT OFF");
//		this.commAdapter.write("TRIG:COUN 1");
//		this.commAdapter.write("SAMP:COUN 1");
//		this.commAdapter.write("FUNC 'TEMP'");
//		this.commAdapter.write("UNIT:TEMP C");
//		this.commAdapter.write("TEMP:TRAN FRTD");
//		this.commAdapter.write("TEMP:FRTD:TYPE PT100");
//		this.commAdapter.write("TEMP:AVER:STAT ON");
//		this.commAdapter.write("TEMP:AVER:COUN "+Integer.toString(_avg));
//		
//	}

	

	//********************************************************************************************
	//*************************BUFFER AND STATISTICS RELATED COMMANDS*****************************
	//********************************************************************************************
	
	public String clearDataBuffer() throws Exception 
	{
		if (this.debug_level<5) System.out.println("Clearing data buffer...");
		this.commAdapter.write("TRACe:CLEar");
		if (this.checkErrors) return "clearDataBuffer: " + this.errors(); else return " NO CHECK ERRORS";
	}

	/**
	 * Use TRACE:DATA? to retrieve all readings that are stored in the buffer. You can send this
		command even if the instrument is still storing readings. When TRACe:DATA? is sent, it
		will return the readings stored up to that point in time. Subsequent TRACe:DATA?
		commands will not retrieve readings already returned. However, once the buffer has filled
		and you have retrieved all buffer readings, you can again send TRACe:DATA? to retrieve all
		the stored readings.
		The data elements returned with each stored reading depends on which ones are selected
		with FORMat:ELEMents command (see Section 14 for details).
	 * @param nSamples
	 * @return
	 * @throws Exception
	 */
	public float[] getBufferData(int nSamples) throws Exception
	{
		float[] res = null;
		byte[] data;
		
		if (this.debug_level<5) System.out.println("Getting buffer data...");
		
		data = this.commAdapter.ask("TRACE:DATA?");
		
		//System.out.println(Arrays.toString(data));
		
		if (data.length>0){
			
			String str = new String(data);
			
			if (str!=null)
			{	
				res = new float[nSamples];
				Matcher matcher = null;
				//System.out.println(str);
				String[] rawData = str.split(",");
				//System.out.println(rawData.length);
				
				for (int i=0;i<rawData.length;i++) {
					matcher = SCIENTIFIC_NOTATION_PATTERN.matcher(rawData[i]);
					if (matcher.find())
					{
						res[i] = Float.parseFloat(matcher.group(0));
					}
				}
			}
		}
		
		return res;
	}
	
	public String enableAutoClearBuffer(boolean enable) throws Exception
	{
		String order = (enable?"TRAC:CLE:AUTO ON":"TRAC:CLE:AUTO OFF");
		
		if (this.debug_level<5) System.out.println("Configuring auto clear buffer as " + order);
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "enableAutoClearBuffer: " + this.errors(); else return " NO CHECK ERRORS";			
	}
	
	public String enableStatisticsCalculation(boolean enable) throws Exception
	{
		String order = (enable?"CALCulate2:STATe ON":"CALCulate2:STATe OFF");
		
		if (this.debug_level<5) System.out.println("Configuring statistics calculation as " + order);
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "enableStatisticsCalculation: " + this.errors(); else return " NO CHECK ERRORS";
	
	}
	
	public String calculateStandardDeviationOfBufferData() throws Exception{
		
		//TODO: Test
		
		if (this.debug_level<5) System.out.println("Calculing the stDev of the data stored in the K2700 buffer.....");

		String order = "CALC2:FORM SDEV; ";
		order = order + "CALC2:STAT ON; ";
		order = order + "CALC2:IMM";
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "calculateStandardDeviationOfBufferData: " + this.errors(); else return " NO CHECK ERRORS";
		
	}
	
	public String calculateMeanOfBufferData() throws Exception{
		
		//TODO: Test
		if (this.debug_level<5) System.out.println("Calculing the Mean of the data stored in the K2700 buffer.....");

		String order = "CALC2:FORM MEAN; ";
		order = order + "CALC2:STAT ON; ";
		order = order + "CALC2:IMM";
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "calculateMeanOfBufferData: " + this.errors(); else return " NO CHECK ERRORS";

	}
	
	
	public float getStatisitcs() throws Exception{
		
		
		float res = -1;
		byte[] data;

		
		
		/*  After the selected buffer statistic is enabled, IMMediate or IMMediate? must be sent to
			calculate the statistic from the data in the buffer. The CALCulate2:DATA? command does
			not initiate a calculate operation. It simply returns the result of the last calculation. If new
			data is stored in the buffer, you must again send IMMediate or IMMediate? to recalculate
			the statistic from that new data.
			
			NOTE: If the standard deviation calculation is being performed on a buffer that has
			more than 1000 readings, the “CALCULATING” message will flash to indicate
			that the Model 2700 is busy. While busy with the calculation, remote
			programming commands will not execute. 
		 */
		
		if (this.debug_level<5) System.out.println("Getting buffer statistics...");
		
		data = this.commAdapter.ask("CALC2:DATA?");
		
		
		if (data.length>0){
			String str = new String(data);
			
			
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				Matcher matcher = SCIENTIFIC_NOTATION_PATTERN.matcher(rawData[0]);
				if (matcher.find())
				{
					res = Float.parseFloat(matcher.group(0));
				}
			}
		}
		
		return res;
	}

	/**
	 * 
	 * @param enable
	 * @throws Exception 
	 */
	public String enableBeeper(boolean enable) throws Exception {
		
		String order = (enable?"SYST:BEEP:STAT ON":"SYST:BEEP:STAT OFF");
		
		if (this.debug_level<5) System.out.println("Configuring beeper as " + order);
		
		this.commAdapter.write(order);
		if (this.checkErrors) return "enableBeeper: " + this.errors(); else return " NO CHECK ERRORS";
	}
	public String getIdentification() throws Exception{
		
		if (this.debug_level<5) System.out.println("Asking instrument for identification...");
		
		return new String(this.commAdapter.ask("*IDN?"));
	}
	/**
	 * 	Use this query command to determine which switching modules are installed in the
		Model 2700. For example, if a Model 7703 is installed in slot 1, and the other slot is
		empty, the response message will look like this:
		7703, NONE
	 * @return String with the installed modules
	 * @throws Exception
	 */
	public String getInstalledModules() throws Exception{
		
		if (this.debug_level<5) System.out.println("Asking instrument for installed modules...");
		
		byte[] data;
	
		data= this.commAdapter.ask("*OPT?");
			
		return new String(data);
	}
	
	/**
	 * Tests the ROM memory of the instrument
	 * @return 	0 if test passed
	 * 			1 if test failed
	 * @throws Exception
	 */
	public int testROM () throws Exception{
		
		if (this.debug_level<5) System.out.println("Testing the ROM memory of the instrument...");
		
		byte[] data;
		
		resetInstrument();
		Thread.sleep(1000);
		this.commAdapter.write("*TST?");
		Thread.sleep(1000);
		
//		String[] errors = this.errors().split(",");
//		if (Integer.parseInt(errors[0]) != 0) throw new Exception(errors[1]);
		
		data = this.commAdapter.read();
		String str = new String(data);
		return Integer.parseInt(str);
	}
	
	/**
	 * 	When the *RST command is sent, Model 2700 performs the following operations:
		1. Returns Model 2700 to the RST default conditions (see “Default” column of SCPI tables).
		2. Cancels all pending commands.
		3. Cancels response to any previously received *OPC and *OPC? commands.
	 * @throws Exception 
	 */
	public String resetInstrument() throws Exception{
		if (this.debug_level<5) System.out.println("Reseting the instrument....");
		this.commAdapter.write("*RST");
		if (this.checkErrors) return "resetInstrument: " + this.errors(); else return " NO CHECK ERRORS";
	}
	
	
	public void execute_script(String _scriptFile) throws Exception{
		
		//TODO Test
		
		if (this.debug_level<5) System.out.println("Executing script name: " + _scriptFile);
		
		File instrumentConfigFile;
		BufferedReader 	fReader 	= null;
		instrumentConfigFile = new File(_scriptFile);
		fReader  = new BufferedReader(new FileReader(instrumentConfigFile));
		String line;
		while (!((line = fReader.readLine()) == null))
		{
			if (line.startsWith("#")){
				line = line.replaceFirst("#", "");
				line = line.substring(0, line.length());
				System.out.println(line);
			}else
			{
				this.commAdapter.write(line);
				
				String[] errors = this.errors().split(",");
				if (Integer.parseInt(errors[0]) != 0) throw new Exception(errors[1]);
				
			}
		}
		fReader.close();
	}
	
	
	public String isTemperatureFilterEnable() throws Exception 
	{
		return new String(this.commAdapter.ask("SENSe:TEMPerature:AVERage:STATe?"));
	}
	
	
	
	//********************************************************************************************
	//*************************************VERSION CONTROL****************************************
	//********************************************************************************************
	
	public static String getVersion() {
		return VERSION;
	}
	
}
