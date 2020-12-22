package examples;

import java.util.Arrays;

import common.CommPort_I;
import multimeters.Keithley2700_v6;
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

	
	final static String VERSION = "1.0.0";
	

	//RS232 Communication port parameters and objects
	private static String portName = "COM1";
	private static int baudRate = 19200;
	private static int numberOfDataBits = 8;
	private static int numberOfStopBits = 1;
	private static int parityType = 0;
	private static String terminator = "\n";
	
	private static int writePort_waitTime = 250; //in milliseconds
	private static int readPort_waitTime = 0; //in milliseconds
	boolean check_errors = true;
	private static int debug_level = 3;
	
	private static CommPort_I rs232_commPort = null;
	
	private static Keithley2700_v6 k = null;
	
	
	
	private String inicializaInstrumento() throws Exception {
		
		String r = "";
		
		String[] ELEMENTS = {
				Keithley2700_v6.FORMAT_ELEMENT_READING,
				Keithley2700_v6.FORMAT_ELEMENT_UNITS
				};
		
		String[] DEFAULT_ELEMENTS = {
				Keithley2700_v6.FORMAT_ELEMENT_READING
				};
	
		k = new Keithley2700_v6(rs232_commPort, check_errors, debug_level);

		
		r = r + k.resetInstrument();
		r = r + k.resetRegisters();
		r = r + k.clearErrorQueue();
		r = r + k.openAllChannels(100);
		
		r = r + k.formatElements(ELEMENTS, DEFAULT_ELEMENTS);
		r = r + k.formatData(Keithley2700_v6.FORMAT_TYPE_ASCII,1);
		 
		return r;
	}
	
	private void ejemplo_medidaInstantaneaDeTemperatura(int card, int ch, int nMeasures) throws Exception {
		String r = "";
		
		int 	filterCount 	= 30;
		float 	filterWindow 	= 0f;
		boolean enableFilter 	= true;
		
		//Configuramos el keithley para la medida de temperatura.
		r = r + k.configureForMeasuring_Temperature(
				Keithley2700_v6.TEMPERATURE_SENSOR_TCOUPLE, 
				Keithley2700_v6.TEMPERATURE_TCOUPLE_TYPE_K, 
				Keithley2700_v6.TEMPERATURE_UNIT_C,
				Keithley2700_v6.AVERAGE_FILTER_CONTROL_TYPE_REPEAT,
				filterWindow,
				filterCount,
				enableFilter);
		
		r = r + k.openAllChannels(100);
		 
		System.out.println(r);
		
		r = null;
		
		for (int i=0;i<nMeasures;i++) {
			System.out.println(Arrays.toString(k.measure(Keithley2700_v6.FUNCTION_TEMPERATURE, -1, 0.1f, card, ch))); 
			Thread.sleep(1000);		 
		}
	}
	
	private void ejemplo_scan(String[] functions, float[] ranges, float[] nplcs, int[] filters, int cardNumber, int[] channels) throws Exception {
		
		String r = "";
		
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
		r = r + k.setTriggerCount(Keithley2700_v6.TRIGGER_COUNT_MAX + 1);
		r = r + k.setSampleCount(channels.length);
		r = r + k.setTriggerSource(Keithley2700_v6.TRIGGER_SOURCE_BUS);
		 
			
		System.out.println("**************************************************");
		System.out.println("************END CONFIGURING TRIGGER***************");
		System.out.println("**************************************************");
		
		r = r + k.trigger_init();
		r = r + k.sendTriggerCommand();
	 	 
		Thread.sleep(1000);
		 
		System.out.println(Arrays.toString(k.getBufferData(channels.length)));
		 
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
				Keithley2700_v6.FUNCTION_TEMPERATURE,
				Keithley2700_v6.FUNCTION_FOUR_WIRE_RESISTANCE,
				Keithley2700_v6.FUNCTION_VOLTAGE_DC
		};
		
		examples.inicializaInstrumento();
		examples.ejemplo_medidaInstantaneaDeTemperatura(cardNumber, channel, 10);

		examples.ejemplo_scan(functions, ranges, nplcs, filters, cardNumber, channels);
		
		System.exit(0);
	}

}
