package keithley;

import common.CommPort_I;

import measure.Measure;
import measure.Measures;
import rs232.JSSC_S_Port;

/**
 * Clase creada para mostrar varios ejemplos de uso de la clase Keithley2700.
 * POr ejemplo, configuración de los canales de medida para un futuro scan, 
 * configuracion, ejecucion y recogida de datos de un scan, medidas innstantaneas,
 * etc...
 * 
 * @author DavidS
 *
 */
public class K2700_examples {

	
	final static String VERSION = "1.0.1";
	

	//RS232 Communication port parameters and objects
	private static String portName = "COM1";
	private static int baudRate = 19200;
	private static int numberOfDataBits = 8;
	private static int numberOfStopBits = 1;
	private static int parityType = 0;
	private static String terminator = "\n";
	
	private static int writePort_waitTime = 500; //in milliseconds
	private static int readPort_waitTime = 0; //in milliseconds
	boolean check_errors = false;
	private static int debug_level = 5;
	
	private static CommPort_I rs232_commPort = null;
	
	private static Keithley2700 k = null;
	
	
	private String inicializaInstrumento() throws Exception {
		
		String r = "";
		
		String[] ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING,
				Keithley2700.FORMAT_ELEMENT_UNITS,
				Keithley2700.FORMAT_ELEMENT_TSTAMP,
				Keithley2700.FORMAT_ELEMENT_RNUMBER,
				Keithley2700.FORMAT_ELEMENT_CHANNEL,
				Keithley2700.FORMAT_ELEMENT_LIMITS
				};
		
		String[] DEFAULT_ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING
				};
	
		k = new Keithley2700(rs232_commPort, check_errors, debug_level);

		
		r = r + k.resetInstrument();
		r = r + k.resetRegisters();
		r = r + k.clearErrorQueue();
		r = r + k.openAllChannels(100);
		
		k.formatElements(ELEMENTS, DEFAULT_ELEMENTS);
		r = r + k.formatData(Keithley2700.FORMAT_TYPE_ASCII,1);
		
		
		return r;
	}
	
	private void ejemplo_medidaInstantaneaDeTemperatura(int card, int ch, int nMeasures) throws Exception {
		
		String r = "";
		
		int 	filterCount 	= 20;
		float 	filterWindow 	= 0f;
		boolean enableFilter 	= true;
		Measure measure;
		
		r = r + k.openAllChannels(100);
		
		for (int i=0;i<nMeasures;i++)
		{
			//Configuramos el keithley para la medida de temperatura.
			
			long t0 = System.currentTimeMillis();
			
			measure = k.measure_Temperature(
					Keithley2700.TEMPERATURE_SENSOR_TCOUPLE, 
					Keithley2700.TEMPERATURE_TCOUPLE_TYPE_K, 
					Keithley2700.TEMPERATURE_UNIT_C,
					Keithley2700.AVERAGE_FILTER_CONTROL_TYPE_REPEAT,
					filterWindow,
					filterCount,
					enableFilter,
					1,
					10);
			
			long t1 = System.currentTimeMillis();
			
			System.out.print("Temperature measure = " + measure + "ºC takes " + (t1-t0) + " milliseconds. \n");
			
			
		}
	
	}
	
	private void ejemplo_medidaInstantaneaDeTemperatura2(int card, int ch, int nMeasures) throws Exception {
		
		String r = "";
		
		int 	filterCount 	= 1;
		float 	filterWindow 	= 0f;
		boolean enableFilter 	= true;
		Measure measure;		
		
		r = r + k.openAllChannels(100);
		
		k.resetInstrument();
		k.resetRegisters();
		
		k.configure_as_OneShot_TEMPERATURE_measure(
				Keithley2700.TEMPERATURE_SENSOR_TCOUPLE, 
				Keithley2700.TEMPERATURE_TCOUPLE_TYPE_K, 
				Keithley2700.TEMPERATURE_UNIT_C,
				Keithley2700.AVERAGE_FILTER_CONTROL_TYPE_REPEAT,
				filterWindow,
				filterCount,
				enableFilter,
				1,
				10);
		
		k.clearDataBuffer();
		k.openAllChannels(50);
		k.closeIndividualChannel(1, 10, 100);
		
		for (int i=0;i<nMeasures;i++)
		{
			//Configuramos el keithley para la medida de temperatura.
			
			long t0 = System.currentTimeMillis();
			
			measure = k.read(); //read always returns a new reading
			
			long t1 = System.currentTimeMillis();
			
			System.out.print(measure.toString() + "Takes " + (t1-t0) + " milliseconds. \n");
			
		}
	
	}

	
	private void ejemplo_scan(String[] functions, float[] ranges, float[] nplcs, int[] filters, int cardNumber, int[] channels) throws Exception {
		
		String r = "";
		
		k.resetInstrument();
		k.resetRegisters();
		
		k.redirectOutputTo("front");
		
		String[] ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING,
				Keithley2700.FORMAT_ELEMENT_UNITS,
				Keithley2700.FORMAT_ELEMENT_TSTAMP,
				Keithley2700.FORMAT_ELEMENT_RNUMBER,
				Keithley2700.FORMAT_ELEMENT_CHANNEL,
				Keithley2700.FORMAT_ELEMENT_LIMITS
				};
		
		String[] DEFAULT_ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING
				};
		
		k.formatElements(ELEMENTS, DEFAULT_ELEMENTS);
		
		System.out.println("**************************************************");
		System.out.println("****************CONFIGURING SCAN******************");
		System.out.println("**************************************************");
		
		r = r + k.setFunctionsForScanListChannels(functions, cardNumber, channels);
		 
		r = r + k.setRangesForScanListChannels(functions, ranges, cardNumber, channels);
		 
		//r = r + k.setRangeForScanListChannels(Keithley2700_v6.FUNCTION_VOLTAGE_DC, 10, cardNumber, minChannel, maxChannel);
		 
		r = r + k.setNPLCsForScanListChannels(functions, nplcs, cardNumber, channels);
		
		//r = r + k.setNPLCForScanListChannels(Keithley2700_v6.FUNCTION_VOLTAGE_DC, 10, cardNumber, minChannel, maxChannel);
		 
		r = r + k.setFiltersCountForScanListChannels(functions, filters, cardNumber, channels); 
		 
		//r = r + k.setFilterCountForScanListChannels(Keithley2700_v6.FUNCTION_VOLTAGE_DC, 10, cardNumber, minChannel, maxChannel); 
		
		r = r + k.setScanListChannels(cardNumber, channels);
		  
		r = r + k.setImmediateScan();
		 
		r = r + k.enableScan(true);
		 

		 
		System.out.println("**************************************************");
		System.out.println("*************END CONFIGURING SCAN*****************");
		System.out.println("**************************************************");
		
		
		System.out.println("**************************************************");
		System.out.println("*************CONFIGURING TRIGGER******************");
		System.out.println("**************************************************");
		
		r = r + k.trigger_abort(); 
		r = r + k.enableTriggerContinousInitiation(false);
		 
		r = r + k.setTriggerTimerInterval(0.1f);
		r = r + k.setTriggerDelay(0f);
		r = r + k.setTriggerCount(Keithley2700.TRIGGER_COUNT_MAX + 1);
		r = r + k.setSampleCount(channels.length);
		r = r + k.setTriggerSource(Keithley2700.TRIGGER_SOURCE_BUS);
		 
			
		System.out.println("**************************************************");
		System.out.println("************END CONFIGURING TRIGGER***************");
		System.out.println("**************************************************");
		
		
		r = r + k.trigger_init();
		r = r + k.sendTriggerCommand();
	 	 
		Thread.sleep(2000);
		
		Measures measures = k.getBufferData(channels.length);
		
		System.out.println(measures.toString());
		
		r = r + k.trigger_abort();		 
		 
		System.out.println(r);
		
		r = null;
		
	}
	
	
	public static String getVersion() {
		return VERSION;
	}

	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		rs232_commPort = new JSSC_S_Port(portName, baudRate, numberOfDataBits, numberOfStopBits, parityType, terminator, writePort_waitTime, readPort_waitTime, debug_level);
		K2700_examples examples = new K2700_examples();
		
		int cardNumber = 1;
		int channel = 10;
		int[] channels = {10,2,3};
		float[] ranges = {-1,10000,2};
		float[] nplcs = {1,2,2};
		int[] filters = {1,5,5};
		
		String[] functions = {
				Keithley2700.FUNCTION_TEMPERATURE,
				Keithley2700.FUNCTION_FOUR_WIRE_RESISTANCE,
				Keithley2700.FUNCTION_VOLTAGE_DC
		};
		
		examples.inicializaInstrumento();
			
		examples.ejemplo_medidaInstantaneaDeTemperatura(cardNumber, channel, 10);
		
		examples.ejemplo_medidaInstantaneaDeTemperatura2(cardNumber, channel, 10);

		examples.ejemplo_scan(functions, ranges, nplcs, filters, cardNumber, channels);
		
		examples.ejemplo_medidaInstantaneaDeTemperatura2(cardNumber, channel, 10);
	
		System.exit(0);
	}

}
