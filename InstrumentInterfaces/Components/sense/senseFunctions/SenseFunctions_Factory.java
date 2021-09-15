package sense.senseFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import common.I_InstrumentComponent;
import expansion_cards.K7700;
import multimeters.I_Multimeter;
import multimeters.Multimeter;

public class SenseFunctions_Factory {
	
	//version 100: First operative version
	
	private static final int classVersion = 100;
		
	//Function types
	
	public static final String VOLTAGEDC_FUNCTION_TYPE	= "VOLTage:DC";
	public static final String VOLTAGEAC_FUNCTION_TYPE	= "VOLTage:AC";
	public static final String CURRENTDC_FUNCTION_TYPE	= "CURRent:DC";
	public static final String CURRENTAC_FUNCTION_TYPE	= "CURRent:AC";
	public static final String RESISTANCE_FUNCTION_TYPE	= "RESistance";
	
	public static final String FWRESISTANCE_FUNCTION_TYPE	= "FRESistance";
	public static final String FREQUENCY_FUNCTION_TYPE		= "FREQuency";
	public static final String PERIOD_FUNCTION_TYPE			= "PERiod";
	public static final String CONTINUITY_FUNCTION_TYPE		= "CONTinuity";
	
	
	public static final String TEMPERATURE_FUNCTION_TYPE	= "TEMPerature";
	public static final String TEMPERATURE_THERMISTOR_BASED_FUNCTION_TRANSDUCER		= "THERmistor";
	public static final String TEMPERATURE_FRTD_BASED_FUNCTION_TRANSDUCER			= "FRTD";
	public static final String TEMPERATURE_TCOUPLE_BASED_FUNCTION_TRANSDUCER		= "TCouple";
	
	public static ArrayList<String> senseFunctionsTypes = new ArrayList<String>(
			Arrays.asList(
					VOLTAGEDC_FUNCTION_TYPE,
					VOLTAGEAC_FUNCTION_TYPE,
					CURRENTDC_FUNCTION_TYPE,
					CURRENTAC_FUNCTION_TYPE,
					RESISTANCE_FUNCTION_TYPE,
					FWRESISTANCE_FUNCTION_TYPE,
					FREQUENCY_FUNCTION_TYPE,
					PERIOD_FUNCTION_TYPE,
					CONTINUITY_FUNCTION_TYPE,
					TEMPERATURE_FUNCTION_TYPE
					)
			);

	final static Logger logger = LogManager.getLogger(SenseFunctions_Factory.class);
	
	public static I_SenseFunction_Configuration getConfiguredSenseFunction(String senseFunctionType, JSONObject configuration) throws Exception
	{
		I_SenseFunction_Configuration function = null;

		if (!senseFunctionsTypes.contains(senseFunctionType)) return null;
		else
		{
			switch (senseFunctionType) 
			{
			
				case VOLTAGEDC_FUNCTION_TYPE:
					function = new VoltageDCFunction_Configuration();
					if (configuration!=null) ((VoltageDCFunction_Configuration)function).initializeFromJSON(configuration);
					break;
	
				case CURRENTDC_FUNCTION_TYPE:
					function = new CurrentDCFunction_Configuration();
					if (configuration!=null) ((CurrentDCFunction_Configuration)function).initializeFromJSON(configuration);
					break;
					
				case RESISTANCE_FUNCTION_TYPE:
					function = new ResistanceFunction_Configuration();
					if (configuration!=null) ((ResistanceFunction_Configuration)function).initializeFromJSON(configuration);
					break;
					
				case FWRESISTANCE_FUNCTION_TYPE:
					function = new FWResistanceFunction_Configuration();
					if (configuration!=null) ((FWResistanceFunction_Configuration)function).initializeFromJSON(configuration);
					break;
					
					
				case TEMPERATURE_FUNCTION_TYPE:
					
					TemperatureFunction_Configuration tFunction = new TemperatureFunction_Configuration();
					if (configuration!=null) tFunction.initializeFromJSON(configuration);
					
					switch (tFunction.getTransducer()) 
					{	
						case TEMPERATURE_FRTD_BASED_FUNCTION_TRANSDUCER:
							function = new FRTDBased_TemperatureFunction_Configuration();
							if (configuration!=null) ((FRTDBased_TemperatureFunction_Configuration)function).initializeFromJSON(configuration);
							break;
							
						case TEMPERATURE_TCOUPLE_BASED_FUNCTION_TRANSDUCER:
							function = new TCoupleBased_TemperatureFunction_Configuration();
							if (configuration!=null) ((TCoupleBased_TemperatureFunction_Configuration)function).initializeFromJSON(configuration);
							break;
							
						case TEMPERATURE_THERMISTOR_BASED_FUNCTION_TRANSDUCER:
							function = new ThermistorBased_TemperatureFunction_Configuration();
							if (configuration!=null) ((ThermistorBased_TemperatureFunction_Configuration)function).initializeFromJSON(configuration);
							break;
	
						default:
							break;
					}
			}
		}
		
		return function;
		
	}

	/**
	 * Requieres previous channel number validation
	 * @param sense_function_configuration
	 * @param multimeterName
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> getConfigurationCommandArrayForInstrument(I_SenseFunction_Configuration sense_function_configuration, String multimeterName, int[] channelsList) throws Exception
	{
		ArrayList<String> configurationCommandArray = new ArrayList<String>();

		if (!Multimeter.AVAILABLE_MULTIMETERS.contains(multimeterName)) return null;
		else
		{
			switch (multimeterName) {
			
				case Multimeter.K2700_NAME:
					
					//Common parameters
					String functionName = sense_function_configuration.getFunctionName();
					
					String channelsListOption;
					if (channelsList!=null) channelsListOption = "," + K7700.createChannelsList(channelsList);
					else channelsListOption = "";
					
					configurationCommandArray.add("SENSe:FUNCtion '" + functionName + "'" + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":NPLCycles " + String.valueOf(sense_function_configuration.getNPLC()) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":RANGe:UPPer " + String.valueOf(sense_function_configuration.getRange()) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":DIGits " + String.valueOf(sense_function_configuration.getResolutionDigits()) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":REFerence " + String.valueOf(sense_function_configuration.getReference()) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":AVERage:COUNt " + String.valueOf(sense_function_configuration.getAverageCount()) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":AVERage:STATe " + (sense_function_configuration.isAverageEnable()? "ON":"OFF" ) + channelsListOption);
					configurationCommandArray.add("SENSe:" + functionName + ":AVERage:TCONtrol " + sense_function_configuration.getAverageTControl());
					configurationCommandArray.add("SENSe:" + functionName + ":AVERage:WINDow " + String.valueOf(sense_function_configuration.getAverageWindow()));

					//Common parameters to temperature function
					if (sense_function_configuration instanceof TemperatureFunction_Configuration) {
						
						String transducer = ((TemperatureFunction_Configuration)sense_function_configuration).getTransducer();
						
						configurationCommandArray.add("SENSe:" + functionName + ":TRANsducer " + transducer + channelsListOption);
						
						//FRTD based temperature function parameters
						if (sense_function_configuration instanceof FRTDBased_TemperatureFunction_Configuration) {
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":TYPE " + 
									((FRTDBased_TemperatureFunction_Configuration)sense_function_configuration).getType() + 
									channelsListOption);
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":RZERo " + 
									String.valueOf(((FRTDBased_TemperatureFunction_Configuration)sense_function_configuration).getRZero()) + 
									channelsListOption);
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":ALPHa " + 
									String.valueOf(((FRTDBased_TemperatureFunction_Configuration)sense_function_configuration).getAlpha()) + 
									channelsListOption);
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":BETA " + 
									String.valueOf(((FRTDBased_TemperatureFunction_Configuration)sense_function_configuration).getBeta()) + 
									channelsListOption);
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":DELTa " + 
									String.valueOf(((FRTDBased_TemperatureFunction_Configuration)sense_function_configuration).getDelta()) + 
									channelsListOption);
							
						}
						
						//Thermistor based temperature function parameters
						if (sense_function_configuration instanceof ThermistorBased_TemperatureFunction_Configuration) {
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":TYPE " + 
									((ThermistorBased_TemperatureFunction_Configuration)sense_function_configuration).getResistorValue() + 
									channelsListOption);
						}
						
						//TCouple based temperature function parameters
						if (sense_function_configuration instanceof TCoupleBased_TemperatureFunction_Configuration) {
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":TYPE " + 
									((TCoupleBased_TemperatureFunction_Configuration)sense_function_configuration).getType() + 
									channelsListOption);
							
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":ODETect " + (((TCoupleBased_TemperatureFunction_Configuration)sense_function_configuration).isOpenDetectionEnable()? "ON":"OFF"));
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":RJUNction:RSELect " + 
									((TCoupleBased_TemperatureFunction_Configuration)sense_function_configuration).getReferenceJunctionType() + 
									channelsListOption);
							configurationCommandArray.add("SENSe:" + functionName + ":" + transducer + ":RJUNction:SIMulated " + 
									String.valueOf(((TCoupleBased_TemperatureFunction_Configuration)sense_function_configuration).getSimulatedReferenceJunctionTemperature()) + 
									channelsListOption);
							
						}
						
						
					}
					
					//FW Resistance function parameters
					if (sense_function_configuration instanceof FWResistanceFunction_Configuration) {
						configurationCommandArray.add("SENSe:" + functionName + ":OCOMpensated " + (sense_function_configuration.isAverageEnable()? "ON":"OFF" ) + channelsListOption);
					}
					
					//Resistance function parameters
					if (sense_function_configuration instanceof ResistanceFunction_Configuration) {
						//Nothing todo here
					}
					
					//VoltageDC function parameters
					if (sense_function_configuration instanceof VoltageDCFunction_Configuration) {
						configurationCommandArray.add("SENSe:" + functionName + ":IDIVider " + (((VoltageDCFunction_Configuration)sense_function_configuration).is10MOhmsInputDividerEnabled()? "ON":"OFF" ));
						configurationCommandArray.add("SENSe:" + functionName + ":PROTection:LEVel " + ((VoltageDCFunction_Configuration)sense_function_configuration).getProtectionLevel());
						configurationCommandArray.add("SENSe:" + functionName + ":PROTection:RSYNchronize " + (((VoltageDCFunction_Configuration)sense_function_configuration).isRangeSynchronizationEnabled()? "ON":"OFF"));
					}
					
					//CurrentDC function parameters
					if (sense_function_configuration instanceof CurrentDCFunction_Configuration) {
						configurationCommandArray.add("SENSe:" + functionName + ":PROTection:LEVel " + ((CurrentDCFunction_Configuration)sense_function_configuration).getProtectionLevel());
						configurationCommandArray.add("SENSe:" + functionName + ":PROTection:RSYNchronize " + (((CurrentDCFunction_Configuration)sense_function_configuration).isRangeSynchronizationEnabled()? "ON":"OFF"));
						
					}

				default:
					break;
			}
		}
		
		return configurationCommandArray;
		
	}
	
	public static int getClassversion() {
		return classVersion;
	}
	
}
