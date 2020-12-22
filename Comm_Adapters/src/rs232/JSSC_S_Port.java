package rs232;

import static jssc.SerialPort.BAUDRATE_9600;
import static jssc.SerialPort.DATABITS_8;
import static jssc.SerialPort.PARITY_NONE;
import static jssc.SerialPort.STOPBITS_1;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


import common.CommPort_I;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import debug_tools.Debuggable;

/**
 * Clase que implementa un puerto serie tipo RS232 haciendo uso de la libreria jssc_2.9.2 
 * compatible con maquinas de 32 y 64 bits en windows.
 * @author DavidS
 *
 */
public class JSSC_S_Port implements CommPort_I, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	final static String VERSION = "1.0.0";
	
	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************


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
	 
	 private int 						debug_level = 0;
	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	/**
	 * @param readWaitTime 
	  *
	  *
	  */
	 public JSSC_S_Port(String wantedPortName, 
			 			int baudRate, 
			 			int nDataBits, 
			 			int nStopBits, 
			 			int parityType, 
			 			String terminator, 
			 			int writeWaitTime,
			 			int readWaitTime,
			 			int debug_level) throws Exception{
		 
		 this.buffer = new byte[BUFFER_LENGTH];
		 this.bufferPointer = 0;
		 this.terminator = terminator;
		 this.debug_level = debug_level;
		 
		 this.writeWaitTime = writeWaitTime;
		 this.readWaitTime = readWaitTime;
		 
		 this.initialize(wantedPortName, baudRate, nDataBits, nStopBits, parityType);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************
	 
	 /**
	  *
	  */
	 private void initialize(String wantedPortName, int baudRate, int nDataBits, int nStopBits, int parityType)throws Exception{

		this.serialPort = new SerialPort(wantedPortName); 
		this.open();
		//this.pr = new PortReader(this.serialPort);
		this.serialPort.addEventListener(this);/* defined below */
		
		this.serialPort.setParams(baudRate,  nDataBits, nStopBits, parityType);
		// this.serialPort.setParams(9600, 8, 1, 0); // alternate technique
		//serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
		
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
				//System.out.println("Break interrupt.");
				break;
				
			case SerialPortEvent.ERR:
				//System.out.println("Error.");
				break;
				
			case SerialPortEvent.CTS:
				//System.out.println("Clear to send.");
				break;
				
			case SerialPortEvent.DSR:
				//System.out.println("Data set ready.");
				break;
				
			case SerialPortEvent.RING:
				//System.out.println("Ring indicator.");				
				break;
				
				
			case SerialPortEvent.TXEMPTY:
				//System.out.println("Output buffer is empty.");
				break;
				
			
			case SerialPortEvent.RXCHAR:
				
				int nBytesToRead = event.getEventValue();
				//System.out.println(nBytesToRead);
		    	
				if (nBytesToRead==0) break; //nothing to retrieve
				
				byte[] tempReadingBuffer;
				
			    try
			    {
			    	
			    	tempReadingBuffer = this.serialPort.readBytes(nBytesToRead);
			    	
			    	if (nBytesToRead==1 && this.bufferPointer==0 && tempReadingBuffer[0]==10)
			    	{
			    		//the buffer is empty. Only contains the terminator
			    		//System.out.println("Empty buffer");
			    		
			    	}
			    	else
			    	{
			    		//System.out.println(Arrays.toString(tempReadingBuffer));
				    	
				    	System.arraycopy(tempReadingBuffer, 0, this.buffer, this.bufferPointer, nBytesToRead);
				    	
				    	this.bufferPointer = this.bufferPointer + nBytesToRead;
				    	//System.out.println(Arrays.toString(readBuffer));
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
//			    	System.out.println(nBytesToRead);
//			    	
//			    	System.out.println(Arrays.toString(tempReadingBuffer));
//			    	
//			    	System.arraycopy(tempReadingBuffer, 0, this.buffer, this.bufferPointer, nBytesToRead);
//			    	
//			    	this.bufferPointer = this.bufferPointer + nBytesToRead;
//			    	//System.out.println(Arrays.toString(readBuffer));
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
//			    	//System.out.println(nBytesToRead);
//			    	
//			    	System.out.println(Arrays.toString(tempReadingBuffer));
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
			 System.out.println("Port "+this.serialPort.getPortName() + " is opened");
			 System.out.println("Going to close the port....");
			 
			 //JOptionPane.showMessageDialog(null,"Port "+this.serialPort.getPortName() + " currently owned");
			 this.serialPort.closePort();
		 }
		 this.serialPort.openPort();			
	 }
	 
	 /**
	  * Close the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void close() throws Exception {
		 if (!this.serialPort.isOpened()) return;
		 this.serialPort.closePort();
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
		Thread.sleep(this.readWaitTime);
		this.waitForIncomingData();
		
		byte[] returnBuffer = new byte[this.readBuffer.length];
		System.arraycopy(this.readBuffer, 0, returnBuffer, 0, this.readBuffer.length);
		this.readBuffer = null;
		
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
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getPortName());
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		if (this.debug_level<4) System.out.println(st[1].getClassName()+":"+st[1].getMethodName()+"("+data+")");
		st = null;
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
		
	public static String getVersion() {
		return VERSION;
	}
		
	//**************************************************************************
	//****************************DEBUGGING*************************************
	//**************************************************************************

	
	

	//**************************************************************************
	//****************************TESTING***************************************
	//**************************************************************************	
	public static void main(String[] args) {
		try {
			int debug_level = 4;
			CommPort_I commPort = new JSSC_S_Port("COM1", 19200, 8, 1, 0, "\n", 250, 0, debug_level);
			System.out.println(new String(commPort.ask("*IDN?")));
			System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
