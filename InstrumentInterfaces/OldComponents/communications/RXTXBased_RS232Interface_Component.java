package communications;

import java.io.*;


import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import common.InstrumentComponent;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;


/**
 * Clase que modela una interface de comunicaciones serie tipo RS232 haciendo uso de la libreria rxtx.
 * De dicha libreria se dispone de dos versiones: la compilada para maquinas de 32
 * bits o para maquinas de 64 bits, todas en windows.
 * for 32 bits use: rxtx-2.1-7r2-win-32 or   
 * for 64 bits use: mfz-rxtx-2.2-20081207-win-x64 
 * @author DavidS
 *
 */
public class RXTXBased_RS232Interface_Component extends InstrumentComponent implements I_CommunicationsInterface, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 102;
	
	final static Logger logger = LogManager.getLogger(RXTXBased_RS232Interface_Component.class);

	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************

	private String 					type = null;
	private String 					standard = null;
	private String 					address = null;
	private CommPortIdentifier 		portId = null;
	private InputStream		      	inputStream;
	private OutputStream       		outputStream;
	private SerialPort		      	serialPort;
	private boolean 				txOK = false;
	private StringBuffer 			readBuffer = null;
	private String 					receivedData = null;
	private String 					terminator = null;
	private int 					writeWaitTime = 100;
	private int 					readWaitTime = 100; 
	 

	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 
	 
	 
	 public RXTXBased_RS232Interface_Component(
			 String name, 
			 long id, 
			 I_InstrumentComponent parent,
			 String type,
			 String standard,
			 String address, 
			 int baudRate, 
			 int nDataBits, 
			 int nStopBits, 
			 int parityType, 
			 String terminator, 
			 int writeWaitTime, 
			 int readWaitTime) throws Exception {
		
		 super(name, id, parent);
		 
		 this.type = type;
		 this.standard = standard;
		 this.address = address;
		 
		 this.receivedData = "";
		 this.terminator = terminator;
		 readBuffer = new StringBuffer();
		 
		 this.writeWaitTime = writeWaitTime;
		 this.readWaitTime = readWaitTime;
		 
		 initialize(address, baudRate, nDataBits, nStopBits, parityType);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************
	 

	@Override
	public String getType() throws Exception {
		// TODO Auto-generated method stub
		return this.type;
	}
	 
	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return this.address;
	}
	
	@Override
	public void setAddress(String address) throws Exception {
		this.address = address;
	}
	
	@Override
	public String getStandard() throws Exception {
		return this.standard;
	}
	
	 /**
	  *
	  */
	 private void initialize(String address, int baudRate, int nDataBits, int nStopBits, int parityType ) throws Exception{

		 portId = searchForSerialCommPort(address);
		 this.open();
		 this.inputStream = this.getPortInputStream();
		 this.outputStream = this.getPortOutputStream();
		 try
		 {
			 this.serialPort.addEventListener(this);
		 }
		 catch (TooManyListenersException e) {}
		 this.setPortParams(baudRate, nDataBits, nStopBits, parityType);

		 try
		 {
			 this.serialPort.notifyOnDataAvailable(true);
		 }
		 catch (Exception e) {
			logger.error("Error setting event notification");
			logger.error(e.toString());
			System.exit(-1);
		 }
		 try
		 {
			 this.serialPort.notifyOnOutputEmpty(true);
		 }
		 catch (Exception e) {
			logger.error("Error setting event notification");
			logger.error(e.toString());
			System.exit(-1);
		 }
		 //this.close();
	 }

	 /**
	  *
	  */
	 
	private CommPortIdentifier searchForSerialCommPort(String address) throws Exception{

		boolean								portFound 		= false;
		CommPortIdentifier 					portId 	= null;
		Enumeration<CommPortIdentifier>	   	portList = null;
		 
		//Preguntamos a todos los puertos de la lista
		//si nuestro puerto se encuentra en el equipo
		logger.info("Buscando puertos del sistema....\n");
		Thread.sleep(1000);
		portList = CommPortIdentifier.getPortIdentifiers();
		
		
		while (portList.hasMoreElements() && !portFound) {
			portId = portList.nextElement();
			logger.debug(portId.getName()+ " ");
			Thread.sleep(500);
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(address)) {
					//TODO: mirar si podemos salir del bucle con un continue
					portFound = true;	
				}
			}
		}
		
		if (!portFound) {
		    logger.info("port " + address + " not found.");
		    return null;
		}
		
		logger.info(" ............ Found port: "+address);
		//portId es el identificador del puerto que buscamos
		return portId;
	 }
	
	 /**
	  *
	  */
	 public void serialEvent(SerialPortEvent event) {

		switch (event.getEventType()) {

		case SerialPortEvent.BI:
			//logger.debug("Break interrupt.");
		case SerialPortEvent.OE:
			//logger.debug("Overrun error.");
		case SerialPortEvent.FE:
			//logger.debug("Framing error.");
		case SerialPortEvent.PE:
			//logger.debug("Parity error.");
		case SerialPortEvent.CD:
			logger.debug("Carrier detect.");
		case SerialPortEvent.CTS:
			//logger.debug("Clear to send.");
		case SerialPortEvent.DSR:
			//logger.debug("Data set ready.");
		case SerialPortEvent.RI:
			//logger.debug("Ring indicator.");
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			//logger.debug("Output buffer is empty.");
		case SerialPortEvent.DATA_AVAILABLE:
			this.txOK = false;
			this.readBuffer = new StringBuffer();
			int c;
            try {
                 while ((c=this.inputStream.read()) != 10){
                   if(c!=13)  this.readBuffer.append((char) c);
                 }
                 this.receivedData = this.readBuffer.toString();
                 this.inputStream.close();
                 this.txOK = true;
            } catch (IOException e) {}

            break;
		}
	 }
	 
	 /*
	 public void serialEvent(SerialPortEvent event) {

			switch (event.getEventType()) {

			case SerialPortEvent.BI:
				//logger.debug("Break interrupt.");
			case SerialPortEvent.OE:
				//logger.debug("Overrun error.");
			case SerialPortEvent.FE:
				//logger.debug("Framing error.");
			case SerialPortEvent.PE:
				//logger.debug("Parity error.");
			case SerialPortEvent.CD:
				logger.debug("Carrier detect.");
			case SerialPortEvent.CTS:
				//logger.debug("Clear to send.");
			case SerialPortEvent.DSR:
				//logger.debug("Data set ready.");
			case SerialPortEvent.RI:
				//logger.debug("Ring indicator.");
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
				//logger.debug("Output buffer is empty.");
			case SerialPortEvent.DATA_AVAILABLE:
				this.txOK = false;
				byte[] readBuffer = new byte[256];
			    try
			    {
					while (this.inputStream.available() > 0) {
					    int numBytes = this.inputStream.read(readBuffer);
					    for (int i=0;i<numBytes;i++){
					    	byte b = readBuffer[i];
							this.buffer[this.bufferPointer+i]=b;
							if (b==10){
								this.bufferPointer = 0;
								this.txOK = true;
							}
						}
					    this.bufferPointer = this.bufferPointer + numBytes;
					}
			    } catch (IOException e) {}
			    break;
			}
		 }*/

	 private void waitForIncomingData() throws Exception{
		 while (!this.txOK){Thread.sleep(50);}
		 this.txOK = false;
	 }
	 
	 //**************************************************************************
	 //****************************SETTERS***************************************
	 //**************************************************************************



	 /**
	  *
	  * Permite inicializar todos los parametros del puerto
	  */
	 private void setPortParams(int baudRate, int nDataBits, int nStopBits, int parityType) throws Exception{
		 serialPort.setSerialPortParams(baudRate,nDataBits,nStopBits,parityType);
		 //serialPort.setDTR(false);
		 //serialPort.setRTS(false);
	 }
	 

	 //**************************************************************************
	 //****************************GETTERS***************************************
	 //**************************************************************************


	 /**
	  * Permite obtener el flujo de datos de entrada del puerto serie
	  * para poder leer datos provenientes de este.
	  * @return
	 * @throws IOException 
	  */
	 private InputStream getPortInputStream() throws IOException{
		 InputStream is = null;
		 is = this.serialPort.getInputStream();
		 return is;
	 }
	 /**
	  * Permite obtener el flujo de datos de entrada del puerto serie
	  * para poder leer datos provenientes de este.
	  * @return
	 * @throws IOException 
	  */
	 private OutputStream getPortOutputStream() throws IOException{
		 OutputStream os = null;
		 os = this.serialPort.getOutputStream();
		 return os;
	 }

	 //**************************************************************************
	 //****************************OTHER METHODS*********************************
	 //**************************************************************************

	 private String readDataAsString() throws Exception{
		 String copy = new String(receivedData);
		 receivedData = "";
		 return copy;
	 }
	 
	 //**************************************************************************
	 //*********************COMM_PORT_I INTERFACE METHODS************************
	 //**************************************************************************
	 
	 /**
	  * Open the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void open() throws Exception {	
		if (portId.isCurrentlyOwned())
		{
			logger.info("Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
		}else
		{
			this.serialPort = (SerialPort) portId.open("RS232_PortApp", 2000); //SimpleReadApp
		}			
	 }
	 
	 /**
	  * Close the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void close() throws Exception {
		 Thread.sleep(2000);  // Be sure data is xferred before closing
		 logger.info("Closing the port ");
		 serialPort.close();
	 }
	 
	 
	/**
	 * Get last income data (the newest arrived data)
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @return the data readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public byte[] read() throws Exception{
		this.open();
		Thread.sleep(this.readWaitTime);
		this.waitForIncomingData();
		return this.readDataAsString().getBytes();
	}

	/**
	 * Sends the data to the output of the adapter
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	data is the String to send to the output
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public void write(String data) throws Exception{
		this.open();
		//logger.debug("\n"+"Writing \""+message+"\" to "+serialPort.getPortName());
		//logger.debug("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		Thread.sleep(this.writeWaitTime);
		outputStream.write((data + this.terminator).getBytes());
	}
	
	/**
	 * Sends a query to the output of the adapter and waits for the response
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	query is the String to send to the output as query
	 * @return 	the response readed as byte array.
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public byte[] ask(String query) throws Exception {
		this.write(query);
		return this.read();
	}
	
	//**************************************************************************
	//****************************VERSION***************************************
	//**************************************************************************
		
	public static int getVersion() {
		return classVersion;
	}


	 //**************************************************************************
	 //****************************TESTING***************************************
	 //**************************************************************************
	
}
