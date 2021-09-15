package ports;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import be.ac.ulb.gpib.*;
import be.ac.ulb.tools.*;


/**
 * 
 * Ciclo de vida:
 * 
 * 			Declarar instancia. Ejemplo:  gpib_port = new GPIB_Port("14");
			Inicializar puerto. Ejemplo: gpib_port.initialize();
			Abrir puerto. 		Ejemplo gpib_port.open(1.00f);
			Interacción con el instrumento todo lo necesario via el puerto GPIB
			Cerrar puerto. Ejemplo: gpib_port.close();
			
 * @author DavidS
 *
 */
public class GPIB_Port {
	
	
	//version 100: Initial version not still working 
	//version 101: First operative version 
	
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************

	private static final int classVersion = 101;
	final static Logger logger = LogManager.getLogger(GPIB_Port.class);
	public static int ADDRESS_MAX_VALUE = 31;
	public static int ADDRESS_MIN_VALUE = 1;
	
	/** Indicates whether or not the native driver has been loaded, yet. */
	private static boolean libraryLoaded = false;
	
	private boolean opened = false;
	
	 //**************************************************************************
	 //****************************METODOS ESTATICOS*****************************
	 //**************************************************************************
	 
	//****************************VERSION****************************************
			
	public static int getVersion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************

	
	private String address = null;
	private GPIBDevice instrumentHandler = null;
	
	//**************************************************************************
	//****************************CODIGO ESTATICO*******************************
	//**************************************************************************
	
	//Este codigo se va a ejecutar cuando declaremos un objeto de esta clase 
	
	static {

		if (!libraryLoaded) {
			/* Load the OS depended Class-Driver */
			
			String absolutePath;
			
			switch (OSUtils.getOS()) {
			
				case WINDOWS32:
					absolutePath = FileSystems.getDefault().getPath("\\repos\\java_development_libraries","\\comms\\parallel\\GPIB\\JPIB V1.2\\jpib_32.dll").normalize().toAbsolutePath().toString();
					break;
					
				case WINDOWS64:
					absolutePath = FileSystems.getDefault().getPath("\\repos\\java_development_libraries","\\comms\\parallel\\GPIB\\JPIB V1.2\\jpib_64.dll").normalize().toAbsolutePath().toString();
					
					break;
	
					
				default: throw new IllegalStateException("No supported driver for OS "+ OSUtils.getOS());
			}
			
			logger.info("Loading library from path " + absolutePath);
			
			System.load(absolutePath);
			
			logger.info("Library loaded" );
			
			libraryLoaded = true;
		}

	}
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public GPIB_Port(String address) throws Exception {
		this.setAddress(address);
	}
	
	
	 //**************************************************************************
	 //****************************METODOS PUBLICOS******************************
	 //**************************************************************************
	
	/**
	 * Opens the device and sets the descriptor of the device.
	 * @param _timeout Timeout for the GPIB driver in seconds
	 * @throws Exception, IOException 
	 */
	public void open(float _timeout) throws Exception, IOException {
		
		if (this.instrumentHandler == null) throw new Exception("You are trying to open the instrument but instrument handler is null!!! Did you forget the GPIB Port initialization step????" );
		if (this.opened) return;
		
		try {
			this.instrumentHandler.open(_timeout);
			this.opened = true;
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.opened = false;
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.opened = false;
		}
		
	}
	
	/**
	 * Ask the device if it is opened.
	 * @param _timeout Timeout for the GPIB driver in seconds
	 * @throws Exception, IOException 
	 */
	public boolean isOpened() throws Exception, IOException {
		return this.opened;
	}
	
	/**
	 * Close method not inplemented in this version on JPIB library
	 * @throws IOException
	 */
	public void close() throws IOException {
		this.instrumentHandler = null;
		this.opened = false;
	}
	
	/**
	 * Sends a command to the instrument and returns a response. Similiar function to ask, hence is ask.
	 * @param command - the command to send.
	 * @return the answer byteArray sent by the device. It returns an empty byteArray if no return was available.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] ask(String command) throws IOException
	{
		try {
			this.open(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.instrumentHandler.sendCommandBin(command);
	}
	
	/**
	 * Writes a command to the instrument. No response is expected. 
	 * @param command - the command to write.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void write(String command) throws IOException
	{
		try {
			this.open(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.instrumentHandler.writeCommand(command);
	}
	
	
	/**
	 * CAUTION!!!!! FOR THE MOMENT OUT OF SERVICE. ALWAYS WILL RETURN NULL!!!!
	 * 
	 * Sends the Fetch? command to the instrument and returns a response. Similiar function to read, hence is read.
	 * This command requests the latest post-processed reading. After sending this command and addressing the Model 2700 to talk, the reading is sent
	 to the computer. This command does not affect the instrument setup.
	 This command does not trigger a measurement. The command simply requests the last available reading.
	 FETCh? can also be used to return more than one reading. When returning more than one reading, the readings are automatically stored in the buffer.
	 In order to return multiple reading strings, continuous initiation must be disabled (INIT:CONT OFF) so that the sample count
	(SAMPle:COUNt), which specifies the number of measurements to be performed, can be set >1. 
	After INITiate is sent to trigger the measurements, FETCh? will return the reading strings. FETCh? is automatically asserted when the READ? or MEASure? command is sent.
	
	NOTES FETCh? can repeatedly return the same reading. Until there is a new reading, this command continues to return the old reading.
	When an instrument setting that is relevant to the readings in the sample buffer
	is changed, the FETCh? command will cause error -230 (data corrupt or stale) or a bus time-out to occur. To get FETCh? working again, a new reading must be triggered.
	
	 * @param command - the command to send.
	 * @return the answer byteArray sent by the device. It returns an empty byteArray if no return was available.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public byte[] read() throws IOException
	{
		//return this.ask("FETCH?");
		return null;
	}
	
	
	//**************************************************************************
	//****************************METODOS PRIVADOS******************************
	//**************************************************************************
	
	private void setAddress (String _address) throws Exception, NumberFormatException
	{
		
		int address;
		address = Integer.parseInt(_address);
		if (address>GPIB_Port.ADDRESS_MAX_VALUE || address<GPIB_Port.ADDRESS_MIN_VALUE) throw new Exception("Not valid number for GPIB address");
		logger.info("Setting the address to " + _address);
		this.address = _address;
	}
	
	public void initialize() throws Exception{
		
		GPIBDeviceIdentifier deviceIdentifier;
		Enumeration<?> gpibDevicesList;
		GPIBDevice gpibDevice = null;
		
		logger.info("Initializing the GPIB Port ...");
		
		GPIBDeviceIdentifier.initialize("be.ac.ulb.gpib.WindowsGPIBDriver", false);
		
		logger.info("Looking for devices at GPIB Bus ...");
		
		gpibDevicesList = GPIBDeviceIdentifier.getDevices();
		
		if (!gpibDevicesList.hasMoreElements()) throw new Exception("No instruments detected on the BUS GPIB");
		
		while (gpibDevicesList.hasMoreElements())
	    {
			deviceIdentifier = (GPIBDeviceIdentifier) gpibDevicesList.nextElement();
			if (deviceIdentifier.getAddress() == Integer.parseInt(this.address)) {
				logger.info("Instrument detected on the GPIB BUS with address " + address);
				gpibDevice = new GPIBDevice(deviceIdentifier.getAddress(), deviceIdentifier.getDriver());
				break;
			}
	    }
		
		if (gpibDevice==null) throw new Exception("No instrument detected on the GPIB BUS with address " + address);
		
		logger.info("Initialing the instrument handler for the instrument with address " + this.address);
		
		this.instrumentHandler = gpibDevice;
	}

	private void test() throws Exception {
		
		//System.out.println("----" + myDevice.sendCommand("*IDN?") + "----");
		
		this.instrumentHandler.writeCommand("SYST:BEEP OFF");
		this.instrumentHandler.writeCommand("DISPLAY:ENABLE ON");
		this.instrumentHandler.writeCommand("DISPLAY:WINDOW1:TEXT:STATE ON");
		this.instrumentHandler.writeCommand("DISPLAY:WINDOW1:TEXT:DATA 'HOLA MUNDOs'");
		
		System.out.println(this.instrumentHandler.sendCommand("DISPLAY:WINDOW1:TEXT:DATA?"));
		//System.out.println(this.instrumentHandler.sendCommand("*STB?"));
	}

	//**************************************************************************
	//**********************************MAIN************************************
	//**************************************************************************
	
	public static void main(String[] args) {
	    	
		GPIB_Port gpib_port;
		
//		try {
//			
//			gpib_port = new GPIB_Port("16");
//			gpib_port.initialize();
//			System.out.println(gpib_port.ask("*IDN?"));
//			//gpib_port.test();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		try {

			gpib_port = new GPIB_Port("14");
			gpib_port.initialize();
			gpib_port.open(1.00f);
			logger.info(new String(gpib_port.ask("*IDN?")));
			//gpib_port.close();

			
			
			for (int i=1;i<400;i++)
			{
				try {
					logger.info("--------------------------------------------------------->"+i);
					logger.info(new String(gpib_port.ask("MEAS:VOLT? 10, 0.01, (@102)")));
					//Thread.sleep(200);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	 }
	 
}