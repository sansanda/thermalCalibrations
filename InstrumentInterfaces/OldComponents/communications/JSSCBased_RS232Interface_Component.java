package communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import common.InstrumentComponent;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Clase que modela una interface de comunicaciones serie tipo RS232 haciendo uso de la libreria jssc_2.9.2 
 * compatible con maquinas de 32 y 64 bits en windows.
 * @author DavidS
 *
 */
public class JSSCBased_RS232Interface_Component extends InstrumentComponent implements I_CommunicationsInterface, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 102;
	
	
	final static Logger logger = LogManager.getLogger(JSSCBased_RS232Interface_Component.class);
	
	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************

	 private String 					type = null;
	 private String						standard = null;
	 private String 					address = null;
	 private SerialPort		      		serialPort;
	 private final int					BUFFER_LENGTH = 4096;
	 //The buffer for receiving all the possible input data of the RS232 interface 
	 private byte[] 					buffer = null;
	 //The pointer for filling the buffer
	 private int 						bufferPointer;
	 //The buffer that will return the JSSC_S_Port whwn you call the read method 
	 //readbuffer will only contain useful data incoming from the instrument via the RS232 interface
	 private byte[]						readBuffer = null;
	 //All the data (bytes) received and sended from/to the instrument will always end with the byte=10, the '\n' terminator  
	 private String 					terminator = "\n";
	 private int 						writeWaitTime = 100;
	 private int 						readWaitTime = 100;
	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	/**
	 * @param readWaitTime 
	  *
	  *
	  */
		 
	 public JSSCBased_RS232Interface_Component(
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
			 int readWaitTime) throws Exception{
			 
		 super(name, id, parent);
		 
		 this.type = type;
		 this.standard = standard;
		 this.address = address;
		 
		 this.buffer = new byte[BUFFER_LENGTH];
		 this.bufferPointer = 0;
		 this.terminator = terminator;
		 
		 this.writeWaitTime = writeWaitTime;
		 this.readWaitTime = readWaitTime;
		 
		 this.initialize(address, baudRate, nDataBits, nStopBits, parityType);
		 
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
		return address;
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
	 private void initialize(String address, int baudRate, int nDataBits, int nStopBits, int parityType)throws Exception{

		this.serialPort = new SerialPort(address); 
		this.open();
		this.serialPort.addEventListener(this);/* defined below */
		
		this.serialPort.setParams(baudRate,  nDataBits, nStopBits, parityType);
		
		int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
		this.serialPort.setEventsMask(mask);

	 }
	 
	
	 private void waitForIncomingData() throws Exception{
		 while (this.readBuffer==null || this.readBuffer[this.readBuffer.length - 1]!=10) 
		 {
			 Thread.sleep(50);
		 }
		 
	 }

	

	 /**
	  *
	  */
	 
	 public void serialEvent(SerialPortEvent event) {
		 
		 switch (event.getEventType()) {

			case SerialPortEvent.BREAK:
				//logger.debug("Break interrupt.");
				break;
				
			case SerialPortEvent.ERR:
				//logger.debug("Error.");
				break;
				
			case SerialPortEvent.CTS:
				//logger.debug("Clear to send.");
				break;
				
			case SerialPortEvent.DSR:
				//logger.debug("Data set ready.");
				break;
				
			case SerialPortEvent.RING:
				//logger.debug("Ring indicator.");				
				break;
				
				
			case SerialPortEvent.TXEMPTY:
				//logger.debug("Output buffer is empty.");
				break;
				
			
			case SerialPortEvent.RXCHAR:
				
				int nBytesToRead = event.getEventValue();
				//logger.debug(nBytesToRead);
		    	
				if (nBytesToRead==0) break; //nothing to retrieve
				
				byte[] tempReadingBuffer;
				
			    try
			    {
			    	
			    	tempReadingBuffer = this.serialPort.readBytes(nBytesToRead);
			    	
			    	if (nBytesToRead==1 && this.bufferPointer==0 && tempReadingBuffer[0]==10)
			    	{
			    		//the buffer is empty. Only contains the terminator
			    		//logger.debug("Empty buffer");
			    		
			    	}
			    	else
			    	{
			    		//logger.debug(Arrays.toString(tempReadingBuffer));
				    	
				    	System.arraycopy(tempReadingBuffer, 0, this.buffer, this.bufferPointer, nBytesToRead);
				    	
				    	this.bufferPointer = this.bufferPointer + nBytesToRead;
				    	//logger.debug(Arrays.toString(readBuffer));
				    	if (this.bufferPointer>0 && this.buffer[this.bufferPointer-1]==10) 
						{
				    		this.readBuffer = new byte[this.bufferPointer];
				    		System.arraycopy(this.buffer, 0, this.readBuffer, 0, this.bufferPointer);
				    		//reinicializamos el buffer de recepcion de datos
				    		this.buffer = new byte[BUFFER_LENGTH];
							this.bufferPointer = 0;
						}
			    	}
			    	
			    	
			    } catch (SerialPortException e) {}
			    break;
			    
//			case SerialPortEvent.RXCHAR:
//				int nBytesToRead = event.getEventValue();
//				
//				if (nBytesToRead==0) return; //nothing to retrieve
//				
//				byte[] tempReadingBuffer;
//				
//			    try
//			    {
//			    	
//			    	tempReadingBuffer = this.serialPort.readBytes(nBytesToRead);
//			    	logger.debug(nBytesToRead);
//			    	
//			    	logger.debug(Arrays.toString(tempReadingBuffer));
//			    	
//			    	System.arraycopy(tempReadingBuffer, 0, this.buffer, this.bufferPointer, nBytesToRead);
//			    	
//			    	this.bufferPointer = this.bufferPointer + nBytesToRead;
//			    	//logger.debug(Arrays.toString(readBuffer));
//			    	if (this.bufferPointer>0 && this.buffer[this.bufferPointer-1]==10) 
//					{
//			    		this.readBuffer = new byte[this.bufferPointer];
//			    		System.arraycopy(this.buffer, 0, this.readBuffer, 0, this.bufferPointer);
//			    		//reinicializamos el buffer de recepcion de datos
//			    		this.buffer = new byte[BUFFER_LENGTH];
//						this.bufferPointer = 0;
//					}
//			    	
//			    } catch (SerialPortException e) {}
//			    break;
			
			    
//			case SerialPortEvent.RXCHAR:
//				int nBytesToRead = event.getEventValue();
//				byte[] tempReadingBuffer;
//			    try
//			    {
//			    	
//			    	tempReadingBuffer = this.serialPort.readBytes(nBytesToRead);
//			    	//logger.debug(nBytesToRead);
//			    	
//			    	logger.debug(Arrays.toString(tempReadingBuffer));
//			    	
//			    } catch (SerialPortException e) {}
//			    break;
				
				
		 }
	}

	 /**
	  * Open the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void open() throws Exception {
		 if (this.serialPort.isOpened())
		 {
			 logger.info("Port "+this.serialPort.getPortName() + " already is opened. Nothing to do.");
			 //logger.info("Going to close the port....");
			 
			 //JOptionPane.showMessageDialog(null,"Port "+this.serialPort.getPortName() + " currently owned");
			 //this.serialPort.closePort();
		 } 
		 else 
		 {
			 logger.info("Openning the port....");
			 this.serialPort.openPort();			
		 }
	 }
	 
	 /**
	  * Close the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void close() throws Exception {
		 if (!this.serialPort.isOpened()) 
		 {
			 logger.info("Port "+this.serialPort.getPortName() + " is already closed. Nothing to do.");
			 
		 return;
		 }
		 else
		 {
			 logger.info("Closing the port....");
			 this.serialPort.closePort(); 
		 }
	 }
	 

	 
	/**
	 * Get and remove the last income data (the newest arrived data) 
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
		
		byte[] returnBuffer = new byte[this.readBuffer.length];
		System.arraycopy(this.readBuffer, 0, returnBuffer, 0, this.readBuffer.length);
		this.readBuffer = null;
		
		logger.trace(returnBuffer);
		
		return returnBuffer;
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
		logger.trace(data);
		Thread.sleep(this.writeWaitTime);
		this.serialPort.writeBytes((data + terminator).getBytes());
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
	
}
