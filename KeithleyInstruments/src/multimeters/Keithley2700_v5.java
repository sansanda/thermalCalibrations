package multimeters;
import instrumentsWithRS232.*;

/**
 * Keithley2700_v5 es una copia de Keithley2700 pero modificada para trabajar
 * en un sistema de 64 bits. Por lo tanto hace uso de las librerias RXTX compiladas 
 * para trabajar con esta arquitectura.
 * @author david
 *
 */
public class Keithley2700_v5 extends instrumentWithRS232_64bits{
	//Constants
	private static int MAX_NUMBER_OF_CHANNELS = 22;
	private static int MIN_NUMBER_OF_CHANNELS = 1;
	private static int MAX_AVG = 100;
	private static int MIN_AVG = 1;
	private static int MAX_NUMBER_OF_READINGS_IN_BUFFER = 55000;
	private static int MIN_NUMBER_OF_READINGS_IN_BUFFER = 1;
	private static long MAX_DELAY_IN_MILLISECONDS = 10000;
	private static long MIN_DELAY_IN_MILLISECONDS = 500;

	//Variables

	//default constructor
	public Keithley2700_v5(String wantedPortName, String terminator)throws Exception{
		super(wantedPortName, terminator);
	}
	//Getters and Setters
	//Other Methods
	public float measureVoltage(int _ch) throws Exception{
		
		verifyParameters(_ch, -1,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
	
		String closeChannelOrder = createCloseChannelOrder(_ch);
		
		System.out.println("Measuring the instantaneous VOLTAGE in K2430....");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("SENS:FUNC 'VOLT:DC'");
		sendMessageToSerialPort("SENS:VOLT:DC:RANG:AUTO ON");

		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-3);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measure2WireResistance(int _ch) throws Exception{
		
		verifyParameters(_ch, -1,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		
		String closeChannelOrder = createCloseChannelOrder(_ch);
		
		System.out.println("Reading the instantaneous 2Wire resistance in K2700....");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'RES'");
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-3);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measure4WireResistance(int _ch) throws Exception{
		verifyParameters(_ch, -1,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		
		String closeChannelOrder = createCloseChannelOrder(_ch);
		
		System.out.println("Reading the instantaneous 4 Wire resistance in K2700....");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'FRES'");
		sendMessageToSerialPort("FRES:OCOM ON");
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-5);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measurePT100Temperature(int _ch) throws Exception{
		
		verifyParameters(_ch, -1,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		
		String closeChannelOrder = createCloseChannelOrder(_ch);
		
		System.out.println("Measuring the instantaneous temp in K2700....");

		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'TEMP'");
		sendMessageToSerialPort("UNIT:TEMP C");
		sendMessageToSerialPort("TEMP:TRAN FRTD");
		sendMessageToSerialPort("TEMP:FRTD:TYPE PT100");
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-2);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measureAverageVoltage(int _ch, int _avg) throws Exception{
		verifyParameters(_ch, _avg,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String averageOrder = 		"SENSE:VOLT:AVER:COUN "+Integer.toString(_avg);
		
		System.out.println("Reading the average voltage in K2700...."+" for "+Integer.toString(_avg)+" samples...");

		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("SENS:FUNC 'VOLT:DC'");
		sendMessageToSerialPort("SENS:VOLT:DC:RANG:AUTO ON");
		sendMessageToSerialPort("SENS:VOLT:AVER:STAT ON");
		sendMessageToSerialPort(averageOrder);
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-3);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measureAveragePT100Temperature(int _ch, int _avg) throws Exception{
		
		verifyParameters(_ch, _avg,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String averageOrder = 		"TEMP:AVER:COUN "+Integer.toString(_avg);

		System.out.println("Reading the average temp in K2700...."+" for "+Integer.toString(_avg)+" samples...");

		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'TEMP'");
		sendMessageToSerialPort("UNIT:TEMP C");
		sendMessageToSerialPort("TEMP:TRAN FRTD");
		sendMessageToSerialPort("TEMP:FRTD:TYPE PT100");
		sendMessageToSerialPort("TEMP:AVER:STAT ON");
		sendMessageToSerialPort(averageOrder);
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-2);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measureAverage2WireResistance(int _ch, int _avg) throws Exception{
		
		verifyParameters(_ch, _avg,-1,-1);

		float res = -1;
		byte[] data;
		int dataLength;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String averageOrder = 	 	"SENSE:RES:AVER:COUN "+Integer.toString(_avg);

		System.out.println("Reading the average 2Wire resistance in K2700....");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'RES'");
		sendMessageToSerialPort("SENSE:RES:AVER:STAT ON");
		sendMessageToSerialPort(averageOrder);
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-3);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public float measureAverage4WireResistance(int _ch, int _avg) throws Exception{

		verifyParameters(_ch, _avg,-1,-1);
		
		float res = -1;
		byte[] data;
		int dataLength;
		String closeChannelOrder = "ROUT:CLOS (@10"+Integer.toString(_ch)+")";
		String averageOrder = 	 	"SENSE:FRES:AVER:COUN "+Integer.toString(_avg);

		System.out.println("Reading the instantaneous 4 Wire resistance in K2700....");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		sendMessageToSerialPort("TRIG:COUN 1");
		sendMessageToSerialPort("SAMP:COUN 1");
		sendMessageToSerialPort("FUNC 'FRES'");
		sendMessageToSerialPort("SENSE:FRES:AVER:STAT ON");
		sendMessageToSerialPort(averageOrder);
		sendMessageToSerialPort("FRES:OCOM ON");
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length()-5);
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		return res;
	}
	public void read2WireResistanceAndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{

		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);
		
		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/10000));

		System.out.println("taking "+_nSamples+" 2-Wire resistance samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");

		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		sendMessageToSerialPort("TRAC:CLE");
		//Especificamos el tamaño del buffer
		sendMessageToSerialPort(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		sendMessageToSerialPort(sampleCountOrder);
		//Configuramos para medida de resistencia 4wire
		sendMessageToSerialPort("FUNC 'FRES'");
		sendMessageToSerialPort("FRES:OCOM ON");

		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		//Configuramos el multimetro para medida con OCOMP`
		sendMessageToSerialPort("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
	}
	public void read4WireResistanceAndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds) throws Exception{
		
		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);
		
		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
 		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/10000));
 		
		System.out.println("taking "+_nSamples+" 4-Wire resistance samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");

		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		sendMessageToSerialPort("TRAC:CLE");
		//Especificamos el tamaño del buffer
		sendMessageToSerialPort(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		sendMessageToSerialPort(sampleCountOrder);
		//Configuramos para medida de resistencia 4wire
		sendMessageToSerialPort("FUNC 'FRES'");
		//Configuramos el multimetro para medida con OCOMP
		sendMessageToSerialPort("FRES:OCOM ON");

		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);

		sendMessageToSerialPort("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
		sendMessageToSerialPort("ROUT:OPEN:ALL");

	}
	public void readVoltageAndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{

		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);
		
		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/10000));

		System.out.println("taking "+_nSamples+" voltage samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");
		
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		sendMessageToSerialPort("TRAC:CLE");
		//Especificamos el tamaño del buffer
		sendMessageToSerialPort(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		sendMessageToSerialPort(sampleCountOrder);

		sendMessageToSerialPort("SENS:FUNC 'VOLT:DC'");
		sendMessageToSerialPort("SENS:VOLT:DC:RANG:AUTO ON");

		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		//Configuramos el multimetro para medida con OCOMP`
		sendMessageToSerialPort("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
	}
	public void readTemperaturePT100AndStoreInBuffer(int _ch, int _nSamples,long _delayBetweenSamplesInMilliseconds)throws Exception{
		
		verifyParameters(_ch, -1,_nSamples, _delayBetweenSamplesInMilliseconds);

		long totalDelayInMilliseconds = _delayBetweenSamplesInMilliseconds*_nSamples;
		String closeChannelOrder = createCloseChannelOrder(_ch);
		String sampleCountOrder = 	"SAMP:COUN "+ Integer.toString(_nSamples);
 		String delayOrder = 		"TRIG:DEL " + Long.toString((_delayBetweenSamplesInMilliseconds/1000));

		System.out.println("taking "+_nSamples+" temperature samples in K2700 during time = "+ totalDelayInMilliseconds/1000 +" secs");

 		sendMessageToSerialPort("*RST");
 		sendMessageToSerialPort("INIT:CONT OFF");
		//El buffer se limpia automaticamente cuando empieza una tanda de medidas
		sendMessageToSerialPort("TRAC:CLE:AUTO ON");
		//Especificamos el tamaño del buffer
		sendMessageToSerialPort(delayOrder);
		//Indicamos que las medidas se almacenen en el buffer
		sendMessageToSerialPort(sampleCountOrder);

		sendMessageToSerialPort("FUNC 'TEMP'");
		sendMessageToSerialPort("UNIT:TEMP C");
		sendMessageToSerialPort("TEMP:TRAN FRTD");
		sendMessageToSerialPort("TEMP:FRTD:TYPE PT100");

		sendMessageToSerialPort("ROUT:OPEN:ALL");
		sendMessageToSerialPort(closeChannelOrder);
		sendMessageToSerialPort("INIT");
		Thread.sleep(totalDelayInMilliseconds + 5000);
	}
	public float calculeVoltageStandardDeviationFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the stDev of voltage.");
		
		sendMessageToSerialPort("CALC2:FORM SDEV");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("stDev of voltage = "+res);
		return res;
	}
	public float calculeVoltageMeanFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the mean of voltage.");
		
		sendMessageToSerialPort("CALC2:FORM MEAN");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("mean of voltage = "+res);
		return res;
	}
	public float calculeTemperatureStandardDeviationFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the stDev of Temperature.");

		sendMessageToSerialPort("CALC2:FORM SDEV");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("stDev of Temperature = "+res);
		return res;
	}
	public float calculeTemperatureMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		int dataLength;
		
		System.out.println(" Calculing the mean of Temperature.");

		sendMessageToSerialPort("CALC2:FORM MEAN");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		//sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("mean of Temperature = "+res);
		return res;
	}
	public float calcule2WireResistanceStandardDeviationFromBufferData() throws Exception{
		
		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the stDev of 2-wire resistance.");

		sendMessageToSerialPort("CALC2:FORM SDEV");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("stDev of 2-wire resistance = "+res);
		return res;
	}
	public float calcule2WireResistanceMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the mean of 2-wire resistance.");
		
		sendMessageToSerialPort("CALC2:FORM MEAN");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("mean of 2-wire resistance = "+res);
		return res;
	}
	public float calcule4WireResistanceStandardDeviationFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the stDev of 4-wire resistance.");

		//sendMessageToSerialPort("FUNC 'FRES'");
		//sendMessageToSerialPort("FRES:OCOM ON");

		sendMessageToSerialPort("CALC2:FORM SDEV");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
		//System.out.println("stDev of 4-wire resistance = "+res);
		return res;
	}
	public float calcule4WireResistanceMeanFromBufferData() throws Exception{

		float res = -1;
		byte[] data;
		int dataLength;

		System.out.println(" Calculing the mean of 4-wire resistance.");

		//sendMessageToSerialPort("FUNC 'FRES'");
		//sendMessageToSerialPort("FRES:OCOM ON");

		sendMessageToSerialPort("CALC2:FORM MEAN");
		sendMessageToSerialPort("CALC2:STAT ON");
		sendMessageToSerialPort("CALC2:IMM?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				rawData[0] = rawData[0].substring(0, rawData[0].length());
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("ROUT:OPEN:ALL");
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
	 */
	public void enableBeeper(boolean b) {
		if (b==true) 	sendMessageToSerialPort("SYST:BEEP:STAT ON");
		else 			sendMessageToSerialPort("SYST:BEEP:STAT OFF");
	}
	public String getIdentification() throws Exception{
		byte[] data;
		int dataLength;
	
		sendMessageToSerialPort("*IDN?");
		Thread.sleep(1000);
		waitForIncomingData();
		dataLength = getReadDataLength();
		data = getReadData();
		
		String str = new String(data,0,dataLength);
		return str;
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
		int dataLength;
	
		sendMessageToSerialPort("*OPT?");
		Thread.sleep(1000);
		waitForIncomingData();
		dataLength = getReadDataLength();
		data = getReadData();
		
		String str = new String(data,0,dataLength);
		return str;
	}
	/**
	 * Tests the ROM memory of the instrument
	 * @return 	0 if test passed
	 * 			1 if test failed
	 * @throws Exception
	 */
	public int testROM () throws Exception{
		
		byte[] data;
		int dataLength;
		
		resetInstrument();
		Thread.sleep(1000);
		sendMessageToSerialPort("*TST?");
		Thread.sleep(1000);
		waitForIncomingData();
		dataLength = getReadDataLength();
		data = getReadData();
		
		String str = new String(data,0,dataLength);
		return Integer.parseInt(str);
	}
	/**
	 * 	When the *RST command is sent, Model 2700 performs the following operations:
		1. Returns Model 2700 to the RST default conditions (see “Default” column of SCPI tables).
		2. Cancels all pending commands.
		3. Cancels response to any previously received *OPC and *OPC? commands.
	 */
	public void resetInstrument(){
		sendMessageToSerialPort("*RST");
	}
	public void verifyParameters(int _nChannels, int _avg, int _nSamples, long _delayBetweenSamplesInMilliseconds) throws Exception{
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
		if (_delayBetweenSamplesInMilliseconds != -1){
			if(_delayBetweenSamplesInMilliseconds > MAX_DELAY_IN_MILLISECONDS || _delayBetweenSamplesInMilliseconds<MIN_DELAY_IN_MILLISECONDS){
				throw new Exception("Error en parametros de entrada. Numero de delay incorrecto");
			}
		}	
	}
	public String createCloseChannelOrder (int _ch){
		String channel="";
		if (_ch>=1 & _ch<=9) channel = channel.concat("@10"+Integer.toString(_ch));
		if (_ch>=10) channel = channel.concat("@1"+Integer.toString(_ch));
		return "ROUT:CLOS ("+channel+")";	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try
		 {
			 Keithley2700_v5 k = new Keithley2700_v5("COM9", "\t\n");
			 //k.initialize("k2700_InitFile_For_TTC.txt");
			 //k.test("k2700_TestFile_For_TTC.txt");
			 //k.configure("k2700_ConfigFile_For_TTC.txt");

			 //System.out.println("VOLTAGE MEASURE --> "+k.measureVoltage(6));
			 //k.readVoltageAndStoreInBuffer(6,10,500);
			 //System.out.println("DESVIACION ESTANDARD PARA EL VOLTAGE--> "+k.calculeVoltageStandardDeviationFromBufferData());
			 //System.out.println("DESVIACION ESTANDARD PARA EL VOLTAGE con 10 muestras--> "+k.takeNVoltageMeasuresWithDelayAndReturnStDev(6, 100, 100));


			 System.out.println("4-WIRE RESISTANCE --> "+k.measure4WireResistance(4));
			 //System.out.println("2-WIRE RESISTANCE --> "+k.read2WireResistance(4));
			 //System.out.println("TEMPERATURE --> "+k.readPT100Temperature(4));


			 //k.read4WireResistanceAndStoreInBuffer(4,5,1000);
			 //System.out.println("DESVIACION ESTANDARD PARA 4-WIRE RESISTANCE --> "+k.calcule4WireResistanceStandardDeviationFromBufferData());

			 //k.read2WireResistanceAndStoreInBuffer(4,5,1000);
			 //System.out.println("DESVIACION ESTANDARD PARA 2-WIRE RESISTANCE --> "+k.calcule2WireResistanceStandardDeviationFromBufferData());
			 

			 //System.out.println("MEDIA PARA TEMPERATURA--> "+					k.takeNTemperatureMeasuresWithDelayAndReturnMean(4, 50, 100));
			 //System.out.println("MEDIA PARA 2=WIRE RESISTANCE--> "+			k.takeN2WireResistanceMeasuresWithDelayAndReturnMean(4, 50, 100));
			 //System.out.println("MEDIA PARA 4=WIRE RESISTANCE--> "+			k.takeN4WireResistanceMeasuresWithDelayAndReturnMean(4, 50, 100));
			 //System.out.println("MEDIA PARA VOLTAGE--> "+						k.takeNVoltageMeasuresWithDelayAndReturnMean(6, 50, 100));

			 System.out.println("TEMP--> "+										k.measurePT100Temperature(4));
			 System.out.println("AVG PARA TEMP--> "+							k.measureAveragePT100Temperature(4,20));
			 System.out.println("VOLTAGE--> "+									k.measureVoltage(6));
			 System.out.println("AVG PARA VOLTAGE--> "+							k.measureAverageVoltage(6,20));
			 System.out.println("2=WIRE RESISTANCE--> "+						k.measure2WireResistance(4));
			 System.out.println("AVG PARA 2=WIRE RESISTANCE--> "+				k.measureAverage2WireResistance(4,20));
			 System.out.println("4=WIRE RESISTANCE--> "+						k.measure4WireResistance(4));
			 System.out.println("AVG PARA 4=WIRE RESISTANCE--> "+				k.measureAverage4WireResistance(4,20));

			 //System.out.println("STDEV PARA TEMPERATURA--> "+					k.takeNTemperatureMeasuresWithDelayAndReturnStDev(4, 50, 100));
			 //System.out.println("STDEV PARA 2=WIRE RESISTANCE--> "+			k.takeN2WireResistanceMeasuresWithDelayAndReturnStDev(4, 50, 100));
			 //System.out.println("STDEV PARA 4=WIRE RESISTANCE--> "+			k.takeN4WireResistanceMeasuresWithDelayAndReturnStDev(4, 50, 100));
			 //System.out.println("STDEV PARA VOLTAGE--> "+						k.takeNVoltageMeasuresWithDelayAndReturnStDev(6, 50, 100));



			 System.out.println("Ending process...");
			 System.out.println("Closing ports...");
			 Thread.sleep(500);
			 k.closeSerialPort();
			 Thread.sleep(500);
			 System.exit(1);

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
