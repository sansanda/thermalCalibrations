package multimeters;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	
	
	//********************************************************************************************
	//********************************KEITHLEY PARAMETERS*****************************************
	//********************************************************************************************
	
	//FORMAT COMMANDS 
	
	//DATA TYPE
	
	final static String FORMAT_TYPE_ASCII = "ASCii"; 	//ASCii format
	final static String FORMAT_TYPE_SREAL = "SREal";	//Binary IEEE-754 single precision format
	final static String FORMAT_TYPE_REAL  = "REAL"; 	//if length = 32 --> Binary IEEE-754 single precision format
														//if length = 32 --> Binary IEEE-754 single precision format
	final static String FORMAT_TYPE_DREAL  = "DREal"; 	//DREal = Binary IEEE-754 double precision format
	
	
	//ELEMENTS RETURNED
	
	final static String FORMAT_ELEMENT_READING = "READing"; 	//DMM reading
	final static String FORMAT_ELEMENT_UNITS 	= "UNITs";		//Units
	final static String	FORMAT_ELEMENT_TSTAMP 	= "TSTamp"; 	//Timestamp
	final static String	FORMAT_ELEMENT_RNUMBER = "RNUMber";	//Reading number
	final static String FORMAT_ELEMENT_CHANNEL = "CHANnel";	//Channel number
	final static String FORMAT_ELEMENT_LIMITS 	= "LIMits";		//Limits reading
	
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
	
	//default constructor
	public Keithley2700_v6(CommPort_I commAdapter)throws Exception{
		super();
		this.commAdapter = commAdapter; 
	}
	
	
	//Getters and Setters
	//Other Methods
	
	
	
	//********************************************************************************************
	//***************************TRIGGER REALTED COMMANDS*****************************************
	//********************************************************************************************

	public void initTrigger() throws Exception {
		
		this.commAdapter.write("INIT");
		//Thread.sleep(totalDelayInMilliseconds + 5000);
		
	}
	//********************************************************************************************
	//**************************GET DATA REALTED COMMANDS*****************************************
	//********************************************************************************************
	
	
	public float read() throws Exception{
		
		
		float res = -1;
		byte[] data;

		
		
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
		
		this.commAdapter.write("READ?");
		
		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			
			if (str!=null)
			{
				System.out.println(str);
				
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
	
	
	
	//********************************************************************************************
	//**************************CHANNEL REALTED COMMANDS******************************************
	//********************************************************************************************
	
	public void closeChannel(int _ch) throws Exception {
		
		verifyParameters(_ch, -1,-1,-1);
		String closeChannelOrder = createCloseChannelOrder(_ch);
		this.commAdapter.write(closeChannelOrder);
		
	}
	
	public void openAllChannels() throws Exception {
		this.commAdapter.write("ROUT:OPEN:ALL");
	}
	
	public String createCloseChannelOrder (int _ch){
		String channel="";
		if (_ch>=1 & _ch<=9) channel = channel.concat("@10"+Integer.toString(_ch));
		if (_ch>=10) channel = channel.concat("@1"+Integer.toString(_ch));
		return "ROUT:CLOS ("+channel+")";	
	}
	
	
	//********************************************************************************************
	//******************************FORMAT REALTED COMMANDS***************************************
	//********************************************************************************************
	
	public void formatData(String type, int length) throws Exception
	{
		if (	type==FORMAT_TYPE_ASCII ||
				type==FORMAT_TYPE_DREAL ||
				type==FORMAT_TYPE_SREAL ||
				type==FORMAT_TYPE_REAL) 
		{
			if (type==FORMAT_TYPE_REAL)
			{
				if (length == 32 || length == 64)
				{
					this.commAdapter.write("FORMat:DATA " + type + "," + String.valueOf(length));
				} 
				else throw new Exception("Invalid Real Foermat Length");
					
			}
			else this.commAdapter.write("FORMat:DATA " + type);
		}
		else throw new Exception("Invalid Format Type");
			
	}
	
	public void formatElements(String[] elements) throws Exception
	{
		String elements_list_as_string = "";
		
		for (int i=0;i<elements.length;i++)
		{
			if (	elements[i]==FORMAT_ELEMENT_CHANNEL ||
					elements[i]==FORMAT_ELEMENT_LIMITS ||
					elements[i]==FORMAT_ELEMENT_READING ||
					elements[i]==FORMAT_ELEMENT_RNUMBER ||
					elements[i]==FORMAT_ELEMENT_TSTAMP ||
					elements[i]==FORMAT_ELEMENT_UNITS) 
			{
				elements_list_as_string = elements_list_as_string + elements[i] + ",";
			}
			else throw new Exception("Invalid Format Element in list");
		}
		
		elements_list_as_string = elements_list_as_string.substring(0, elements_list_as_string.length()-1);
		
		this.commAdapter.write("FORMat:ELEMents " + elements_list_as_string);
			
	}
	
	
	//********************************************************************************************
	//**************************DC VOLTAGE REALTED COMMANDS***************************************
	//********************************************************************************************
	
	public void configureAsMeasureDCVoltage(float range) throws Exception{
		
		
		System.out.println("Configuring the instrument as DC Measure VOLTAGE....");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("SENS:FUNC 'VOLT:DC'");
		
		if (range < 0 || range > 1010) this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
		else this.commAdapter.write("SENS:VOLT:DC:RANG " + Float.toString(range));
		
	}
	
	public void configureAsDCVoltageAverageMeasure(float range, int _avg) throws Exception{

		System.out.println("Configuring the instrument as DC VOLTAGE Average measure"+" for "+Integer.toString(_avg)+" samples...");

		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("SENS:FUNC 'VOLT:DC'");
		
		if (range < 0 || range > 1010) this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
		else this.commAdapter.write("SENS:VOLT:DC:RANG " + Float.toString(range));
		
		this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");
		this.commAdapter.write("SENS:VOLT:AVER:STAT ON");
		this.commAdapter.write("SENSE:VOLT:AVER:COUN "+Integer.toString(_avg));
		
	}
	
	
	//********************************************************************************************
	//**************************2W RESISTANCE REALTED COMMANDS************************************
	//********************************************************************************************
	
	public void configureAsMeasure2WireResistance(float range) throws Exception{
		
		
		System.out.println("Configuring the instrument as 2W Measure....");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'RES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:RESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:RESistance:RANGe " + Float.toString(range));
		
	}
	
	public void configureAs2WireResistanceAverageMeasure(float range, int _avg) throws Exception{
		
		System.out.println("Configuring the instrument as 2W Average Measure....");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'RES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:RESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:RESistance:RANGe " + Float.toString(range));
		
		this.commAdapter.write("SENSE:RES:AVER:STAT ON");
		this.commAdapter.write("SENSE:RES:AVER:COUN "+Integer.toString(_avg));

	}
	
	public void configureAs2WireResistance_NMeasures_ForStoringInBuffer(float range, int _nSamples,double _triggerDelayInSeconds)throws Exception{

		verifyParameters(-1, -1,_nSamples, _triggerDelayInSeconds);

		System.out.println("Configuring the instrument as 2W measure for taking " + Integer.toString(_nSamples) + " samples and store them in the Keithley Internal Buffer. Sampling delay = " + _triggerDelayInSeconds +" seconds");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		this.commAdapter.write("TRAC:CLE");
		//Especificamos el tamaño del buffer
		this.commAdapter.write("TRIG:DEL " + Double.toString(_triggerDelayInSeconds));
		//Indicamos que las medidas se almacenen en el buffer
		this.commAdapter.write("SAMP:COUN "+ Integer.toString(_nSamples));
		//Configuramos para medida de resistencia 2wire
		this.commAdapter.write("FUNC 'RES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:RESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:RESistance:RANGe " + Float.toString(range));
		
		//Configuramos el multimetro para medida con OCOMP`
		this.commAdapter.write("FRES:OCOM ON");
	
	}
	
	//********************************************************************************************
	//**************************4W RESISTANCE REALTED COMMANDS************************************
	//********************************************************************************************
	
	public void configureAsMeasure4WireResistance(float range) throws Exception{
		
		
		System.out.println("Configuring the instrument as 4W Measure....");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'FRES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
		
	}
	
	public void configureAs4WireResistanceAverageMeasure(float range, int _avg) throws Exception{

		System.out.println("Configuring the instrument as 4W Average Measure....");;
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'FRES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
		
		this.commAdapter.write("SENSE:FRES:AVER:STAT ON");
		this.commAdapter.write("SENSE:FRES:AVER:COUN "+Integer.toString(_avg));
		this.commAdapter.write("FRES:OCOM ON");

	}
	
	public void configureAs4WireResistance_NMeasures_ForStoringInBuffer(float range, int _nSamples,double _triggerDelayInSeconds)throws Exception{
		
		verifyParameters(-1, -1,_nSamples, _triggerDelayInSeconds);
 		
		System.out.println("Configuring the instrument as 4 measure for taking " + Integer.toString(_nSamples) + " samples and store them in the Keithley Internal Buffer. Sampling delay = " + _triggerDelayInSeconds +" seconds");

		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		this.commAdapter.write("TRAC:CLE");
		//Especificamos el tamaño del buffer
		this.commAdapter.write("TRAC:POIN " + Integer.toString(_nSamples));
		//Especificamos un delay para el trigger
		this.commAdapter.write("TRIG:DEL " + Double.toString(_triggerDelayInSeconds));
		//Indicamos que las medidas se almacenen en el buffer
		this.commAdapter.write("SAMP:COUN "+ Integer.toString(_nSamples));
		//Configuramos para medida de resistencia 4wire
		this.commAdapter.write("FUNC 'FRES'");
		
		if (range < 0 || range > 120e6) this.commAdapter.write("SENS:FRESistance:RANGe:AUTO ON");
		else this.commAdapter.write("SENS:FRESistance:RANGe " + Float.toString(range));
		
		//Configuramos el multimetro para medida con OCOMP
		this.commAdapter.write("FRES:OCOM ON");

	}
	

	//********************************************************************************************
	//***********************TEMPERATURE RESISTANCE RELATED COMMANDS******************************
	//********************************************************************************************
	
	
	public void configureAsTemperatureMeasure_PT100() throws Exception{
		
		
		System.out.println("Configuring the instrument as Temperature Measure with PT100....");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'TEMP'");
		this.commAdapter.write("UNIT:TEMP C");
		this.commAdapter.write("TEMP:TRAN FRTD");
		this.commAdapter.write("TEMP:FRTD:TYPE PT100");
		
	}

	public void configureAsTemperatureAverageMeasure_PT100(int _avg) throws Exception{
		
		
		System.out.println("Configuring the instrument as Average Temperature measure"+" for "+Integer.toString(_avg)+" samples...");
		
		//this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		this.commAdapter.write("TRIG:COUN 1");
		this.commAdapter.write("SAMP:COUN 1");
		this.commAdapter.write("FUNC 'TEMP'");
		this.commAdapter.write("UNIT:TEMP C");
		this.commAdapter.write("TEMP:TRAN FRTD");
		this.commAdapter.write("TEMP:FRTD:TYPE PT100");
		this.commAdapter.write("TEMP:AVER:STAT ON");
		this.commAdapter.write("TEMP:AVER:COUN "+Integer.toString(_avg));
		
	}

	

	//********************************************************************************************
	//*************************BUFFER AND STATISTICS RELATED COMMANDS*****************************
	//********************************************************************************************
	
	public void clearDataBuffer() throws Exception 
	{
		this.commAdapter.write("TRACe:CLEar");		
	}
	
	public void enableAutoClearBuffer(boolean enable) throws Exception
	{
		if (enable) this.commAdapter.write("TRAC:CLE:AUTO ON");
		else this.commAdapter.write("TRAC:CLE:AUTO OFF");
	}
	
	public void enableStatisticsCalculation(boolean enable) throws Exception
	{
		if (enable) this.commAdapter.write("CALCulate2:STATe ON");
		else this.commAdapter.write("CALCulate2:STATe OFF");
	}
	
	public void calculateStandardDeviationOfBufferData() throws Exception{
		
		System.out.println(" Calculing the stDev of the data stored in the K2700 buffer.....");

		this.commAdapter.write("CALC2:FORM SDEV");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM");

	}
	
	public void calculateMeanOfBufferData() throws Exception{
		
		System.out.println(" Calculing the Mean of the data stored in the K2700 buffer.....");

		this.commAdapter.write("CALC2:FORM MEAN");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM");

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
		
		this.commAdapter.write("CALC2:DATA?");
		
		data = this.commAdapter.read();
		
		
		if (data.length>0){
			String str = new String(data);
			
			
			if (str!=null)
			{
				System.out.println(str);
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

	
	
	public void readVoltageAndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{

		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);
		
		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/10000));

		System.out.println("taking "+_nSamples+" voltage samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");
		
		this.commAdapter.write("*RST");
		this.commAdapter.write("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		this.commAdapter.write("TRAC:CLE");
		//Especificamos el tamaño del buffer
		this.commAdapter.write(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		this.commAdapter.write(sampleCountOrder);

		this.commAdapter.write("SENS:FUNC 'VOLT:DC'");
		this.commAdapter.write("SENS:VOLT:DC:RANG:AUTO ON");

		this.commAdapter.write("ROUT:OPEN:ALL");
		this.commAdapter.write(closeChannelOrder);
		//Configuramos el multimetro para medida con OCOMP`
		this.commAdapter.write("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
	}
	
	public void readTemperaturePT100AndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		
		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);

		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
 		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/1000));

		System.out.println("taking "+_nSamples+" temperature samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");

 		this.commAdapter.write("*RST");
 		this.commAdapter.write("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		this.commAdapter.write("TRAC:CLE:AUTO ON");
		//Especificamos el tamaño del buffer
		this.commAdapter.write(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		this.commAdapter.write(sampleCountOrder);

		this.commAdapter.write("FUNC 'TEMP'");
		this.commAdapter.write("UNIT:TEMP C");
		this.commAdapter.write("TEMP:TRAN FRTD");
		this.commAdapter.write("TEMP:FRTD:TYPE PT100");

		this.commAdapter.write("ROUT:OPEN:ALL");
		this.commAdapter.write(closeChannelOrder);
		this.commAdapter.write("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
	}
	
	public float calculeVoltageStandardDeviationFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;

		System.out.println(" Calculing the stDev of voltage.");
		
		this.commAdapter.write("CALC2:FORM SDEV");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("stDev of voltage = "+res);
		return res;
	}
	
	public float calculeVoltageMeanFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;

		System.out.println(" Calculing the mean of voltage.");
		
		this.commAdapter.write("CALC2:FORM MEAN");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("mean of voltage = "+res);
		return res;
	}
	
	public float calculeTemperatureStandardDeviationFromBufferData() throws Exception{

		float res = -1;
		byte[] data;

		System.out.println(" Calculing the stDev of Temperature.");

		this.commAdapter.write("CALC2:FORM SDEV");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("stDev of Temperature = "+res);
		return res;
	}
	
	public float calculeTemperatureMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		
		System.out.println(" Calculing the mean of Temperature.");

		this.commAdapter.write("CALC2:FORM MEAN");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		//this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("mean of Temperature = "+res);
		return res;
	}
	
	public float calcule2WireResistanceStandardDeviationFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;

		System.out.println(" Calculing the stDev of 2-wire resistance.");

		this.commAdapter.write("CALC2:FORM SDEV");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("stDev of 2-wire resistance = "+res);
		return res;
	}
	
	public float calcule2WireResistanceMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;

		System.out.println(" Calculing the mean of 2-wire resistance.");
		
		this.commAdapter.write("CALC2:FORM MEAN");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("mean of 2-wire resistance = "+res);
		return res;
	}
	
	public float calcule4WireResistanceStandardDeviationFromBufferData() throws Exception{

		float res = -1;
		byte[] data;

		System.out.println(" Calculing the stDev of 4-wire resistance.");

		//this.commAdapter.write("FUNC 'FRES'");
		//this.commAdapter.write("FRES:OCOM ON");

		this.commAdapter.write("CALC2:FORM SDEV");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("stDev of 4-wire resistance = "+res);
		return res;
	}
	
	public float calcule4WireResistanceMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;

		System.out.println(" Calculing the mean of 4-wire resistance.");

		//this.commAdapter.write("FUNC 'FRES'");
		//this.commAdapter.write("FRES:OCOM ON");

		this.commAdapter.write("CALC2:FORM MEAN");
		this.commAdapter.write("CALC2:STAT ON");
		this.commAdapter.write("CALC2:IMM?");

		data = this.commAdapter.read();
		
		if (data.length>0){
			String str = new String(data);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		this.commAdapter.write("ROUT:OPEN:ALL");
		//System.out.println("mean of 4-wire resistance = "+res);
		return res;
	}
	
	public float takeNVoltageMeasuresWithDelayAndReturnStDev(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float voltageStDev;
		readVoltageAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		voltageStDev = calculeVoltageStandardDeviationFromBufferData();
		return voltageStDev;
	}
	public float takeNVoltageMeasuresWithDelayAndReturnMean(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float voltageMean;
		readVoltageAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		voltageMean = calculeVoltageMeanFromBufferData();
		return voltageMean;
	}
	public float takeN2WireResistanceMeasuresWithDelayAndReturnMean(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float _2WireResistanceMean;
		read2WireResistanceAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		_2WireResistanceMean = calcule2WireResistanceMeanFromBufferData();
		return _2WireResistanceMean;
	}
	public float takeN2WireResistanceMeasuresWithDelayAndReturnStDev(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float _2WireResistanceStDev;
		read2WireResistanceAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		_2WireResistanceStDev = calcule2WireResistanceStandardDeviationFromBufferData();
		return _2WireResistanceStDev;
	}
	public float takeN4WireResistanceMeasuresWithDelayAndReturnMean(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float _4WireResistanceMean;
		read4WireResistanceAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		_4WireResistanceMean = calcule4WireResistanceMeanFromBufferData();
		return _4WireResistanceMean;
	}
	public float takeN4WireResistanceMeasuresWithDelayAndReturnStDev(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float _4WireResistanceStDev;
		read4WireResistanceAndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		_4WireResistanceStDev = calcule4WireResistanceStandardDeviationFromBufferData();
		return _4WireResistanceStDev;
	}
	public float takeNTemperatureMeasuresWithDelayAndReturnStDev(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float temperatureStDev;
		readTemperaturePT100AndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		temperatureStDev = calculeTemperatureStandardDeviationFromBufferData();
		return temperatureStDev;
	}
	public float takeNTemperatureMeasuresWithDelayAndReturnMean(int _ch,int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		float temperatureMean;
		readTemperaturePT100AndStoreInBuffer(_ch, _nSamples, _delayBetweenSamplesInMilliseconds);
		temperatureMean = calculeTemperatureMeanFromBufferData();
		return temperatureMean;
	}
	/**
	 * 
	 * @param b
	 * @throws Exception 
	 */
	public void enableBeeper(boolean b) throws Exception {
		if (b==true) 	this.commAdapter.write("SYST:BEEP:STAT ON");
		else 			this.commAdapter.write("SYST:BEEP:STAT OFF");
	}
	public String getIdentification() throws Exception{
		byte[] data;
	
		this.commAdapter.write("*IDN?");
		Thread.sleep(1000);
		data = this.commAdapter.read();
		
		return new String(data);
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
		byte[] data;
	
		this.commAdapter.write("*OPT?");
		Thread.sleep(1000);
		data = this.commAdapter.read();
		
		return new String(data);
	}
	/**
	 * Tests the ROM memory of the instrument
	 * @return 	0 if test passed
	 * 			1 if test failed
	 * @throws Exception
	 */
	public int testROM () throws Exception{
		
		byte[] data;
		
		resetInstrument();
		Thread.sleep(1000);
		this.commAdapter.write("*TST?");
		Thread.sleep(1000);
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
	public void resetInstrument() throws Exception{
		System.out.println("Reseting the instrument....");
		this.commAdapter.write("*RST");
	}
	
	public void verifyParameters(int _nChannels, int _avg, int _nSamples, double _triggerDelayInSeconds) throws Exception{
		if (_nChannels != -1){
			if(_nChannels>MAX_NUMBER_OF_CHANNELS || _nChannels<MIN_NUMBER_OF_CHANNELS){
				throw new Exception("Error en parametros de entrada. Numero de canal incorrecto.");
			}
		}
		if (_avg != -1){
			if(_avg>MAX_AVG || _avg<MIN_AVG){
				throw new Exception("Error en parametros de entrada. Numero de avg incorrecto");
			}
		}
		if (_nSamples != -1){
			if(_nSamples>MAX_NUMBER_OF_READINGS_IN_BUFFER || _nSamples<MIN_NUMBER_OF_READINGS_IN_BUFFER){
				throw new Exception("Error en parametros de entrada. Numero de samples incorrecto");
			}
		}
		if (_triggerDelayInSeconds != -1){
			if(_triggerDelayInSeconds > MAX_TRIGGER_DELAY_IN_SECONDS || _triggerDelayInSeconds<MIN_DELAY_IN_MILLISECONDS){
				throw new Exception("Error en parametros de entrada. Numero de delay incorrecto");
			}
		}	
	}
	
	public void execute_script(String _scriptFile) throws Exception{
		System.out.println("Executing script name: " + _scriptFile);
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
				Thread.sleep(500);
			}
		}
		fReader.close();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		 try
		 {
			 //CommPort_I commPort = new S_Port_64bits("COM9", "\n");
			 CommPort_I commPort = new JSSC_S_Port("COM9", 19200, 8, 1, 0, "\n", 250, 0);
			 
			  
			 Keithley2700_v6 k = new Keithley2700_v6(commPort);
			 
			 k.resetInstrument();
			 k.enableAutoClearBuffer(true);
			 k.clearDataBuffer();
			 k.enableStatisticsCalculation(true);
			 
			 //k.initialize("k2700_InitFile_For_TTC.txt");
			 //k.test("k2700_TestFile_For_TTC.txt");
			 //k.configure("k2700_ConfigFile_For_TTC.txt");

//			 String[] ELEMENTS = {Keithley2700_v6.FORMAT_ELEMENT_READING,
//					 			Keithley2700_v6.FORMAT_ELEMENT_RNUMBER,
//					 			Keithley2700_v6.FORMAT_ELEMENT_TSTAMP,
//					 			Keithley2700_v6.FORMAT_ELEMENT_UNITS};

			 String[] ELEMENTS = {	Keithley2700_v6.FORMAT_ELEMENT_READING,
			 						Keithley2700_v6.FORMAT_ELEMENT_UNITS
			 					};
			 
			 k.formatElements(ELEMENTS);
			 k.formatData(Keithley2700_v6.FORMAT_TYPE_ASCII, 0);
			 
			 
//			 k.openAllChannels();
//			 k.configureAsMeasureDCVoltage(10);
//			 k.closeChannel(3);
//			 System.out.println("VOLTAGE MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAsDCVoltageAverageMeasure(10, 2);
//			 k.closeChannel(3);
//			 System.out.println("Average voltage --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAsMeasure2WireResistance(10000);
//			 k.closeChannel(2);
//			 System.out.println("2W MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAs2WireResistanceAverageMeasure(10000, 10);
//			 k.closeChannel(2);
//			 System.out.println("4W MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAsMeasure4WireResistance(10000);
//			 k.closeChannel(2);
//			 System.out.println("4W MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAs4WireResistanceAverageMeasure(10000, 10);
//			 k.closeChannel(2);
//			 System.out.println("4W MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAsTemperatureMeasure_PT100();
//			 k.closeChannel(2);
//			 System.out.println("PT100 Temp MEASURE --> " + k.read());
//			 
//			 k.openAllChannels();
//			 k.configureAsTemperatureAverageMeasure_PT100(10);
//			 k.closeChannel(3);
//			 System.out.println("PT100 Average temperature --> " + k.read());
			 
//			 k.openAllChannels();
//			 k.configureAs2WireResistance_NMeasures_ForStoringInBuffer(10000, 10, 500);
//			 k.closeChannel(2);
//			 System.out.println("2W meaasure and storing in buffer --> " + k.read());
			 
			 
			 k.openAllChannels();
			 k.configureAs4WireResistance_NMeasures_ForStoringInBuffer(10000, 2, 0.1);
			 k.closeChannel(2);
			 k.initTrigger();
			 Thread.sleep(1000);
			 k.calculateStandardDeviationOfBufferData();
			 System.out.println(k.getStatisitcs());			 
			 
			 
			 
			 System.out.println("Ending process...");
			 System.out.println("Closing ports...");
			 Thread.sleep(500);
			 commPort.close();
			 Thread.sleep(500);
			 System.exit(1);
			 
			 
			 int i = 0;
			 
			  
//			 while (i<20)
//			 {
//				 
//
//			 
//			 //System.out.println("VOLTAGE MEASURE --> "+k.read());
//			 
//			 //k.readVoltageAndStoreInBuffer(6,10,500);
//			 //System.out.println("DESVIACION ESTANDARD PARA EL VOLTAGE--> "+k.calculeVoltageStandardDeviationFromBufferData());
//			 //System.out.println("DESVIACION ESTANDARD PARA EL VOLTAGE con 10 muestras--> "+k.takeNVoltageMeasuresWithDelayAndReturnStDev(6, 100, 100));
//
//
//			 //System.out.println("4-WIRE RESISTANCE --> "+k.measure4WireResistance(4));
//			 //System.out.println("2-WIRE RESISTANCE --> "+k.read2WireResistance(4));
//			 //System.out.println("TEMPERATURE --> "+k.readPT100Temperature(4));
//
//
//			 //k.read4WireResistanceAndStoreInBuffer(4,5,1000);
//			 //System.out.println("DESVIACION ESTANDARD PARA 4-WIRE RESISTANCE --> "+k.calcule4WireResistanceStandardDeviationFromBufferData());
//
//			 //k.read2WireResistanceAndStoreInBuffer(4,5,1000);
//			 //System.out.println("DESVIACION ESTANDARD PARA 2-WIRE RESISTANCE --> "+k.calcule2WireResistanceStandardDeviationFromBufferData());
//			 
//
//			 //System.out.println("MEDIA PARA TEMPERATURA--> "+					k.takeNTemperatureMeasuresWithDelayAndReturnMean(4, 50, 100));
//			 //System.out.println("MEDIA PARA 2=WIRE RESISTANCE--> "+			k.takeN2WireResistanceMeasuresWithDelayAndReturnMean(4, 50, 100));
//			 //System.out.println("MEDIA PARA 4=WIRE RESISTANCE--> "+			k.takeN4WireResistanceMeasuresWithDelayAndReturnMean(4, 50, 100));
//			 //System.out.println("MEDIA PARA VOLTAGE--> "+						k.takeNVoltageMeasuresWithDelayAndReturnMean(6, 50, 100));
//
//			 //System.out.println("TEMP--> "+										k.measurePT100Temperature(4));
//			 //System.out.println("AVG PARA TEMP--> "+							k.measureAveragePT100Temperature(4,20));
//			 //System.out.println("VOLTAGE--> "+									k.measureVoltage(6));
//			 //System.out.println("AVG PARA VOLTAGE--> "+							k.measureAverageVoltage(6,20));
//			 //System.out.println("2=WIRE RESISTANCE--> "+						k.measure2WireResistance(4));
//			 //System.out.println("AVG PARA 2=WIRE RESISTANCE--> "+				k.measureAverage2WireResistance(4,20));
//			 //System.out.println("4=WIRE RESISTANCE--> "+						k.measure4WireResistance(4));
//			 //System.out.println("AVG PARA 4=WIRE RESISTANCE--> "+				k.measureAverage4WireResistance(4,20));
//
//			 //System.out.println("STDEV PARA TEMPERATURA--> "+					k.takeNTemperatureMeasuresWithDelayAndReturnStDev(4, 50, 100));
//			 //System.out.println("STDEV PARA 2=WIRE RESISTANCE--> "+			k.takeN2WireResistanceMeasuresWithDelayAndReturnStDev(4, 50, 100));
//			 //System.out.println("STDEV PARA 4=WIRE RESISTANCE--> "+			k.takeN4WireResistanceMeasuresWithDelayAndReturnStDev(4, 50, 100));
//			 //System.out.println("STDEV PARA VOLTAGE--> "+						k.takeNVoltageMeasuresWithDelayAndReturnStDev(6, 50, 100));
//			 	i++;
//			 }
//
//			 System.out.println("Ending process...");
//			 System.out.println("Closing ports...");
//			 Thread.sleep(500);
//			 commPort.close();
//			 Thread.sleep(500);
//			 System.exit(1);

		 }
		 catch(Exception e)
		 {
			 System.err.println(
					 "StackTrace = " + e.getStackTrace()+ "\n" +
					 "Message = "+ e.getMessage()+ "\n" +
					 "Cause = "+ e.getCause());

		 }
	}


}
