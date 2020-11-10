package rs_232;

import static jssc.SerialPort.BAUDRATE_9600;
import static jssc.SerialPort.DATABITS_8;
import static jssc.SerialPort.PARITY_NONE;
import static jssc.SerialPort.STOPBITS_1;

import java.util.LinkedList;


import common.CommPort_I;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


/**
 * Clase que implementa un puerto serie tipo RS232 haciendo uso de la libreria jssc_2.9.2 
 * compatible con maquinas de 32 y 64 bits en windows.
 * @author DavidS
 *
 */
public class S_Port_JSSC implements CommPort_I, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************


	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************


	 private SerialPort		      		serialPort;
	 private final int					BUFFER_LENGTH = 256;
	 private byte[] 					buffer = null;
	 private int 						bufferPointer;

	 private String 					terminator = "\n";
	 
	 private LinkedList<byte[]> 		fifo = null;
	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public S_Port_JSSC(String commPort, String terminator) throws Exception{
		 buffer = new byte[BUFFER_LENGTH];
		 bufferPointer = 0;
		 fifo = new LinkedList<byte[]>();
		 this.initializePort(commPort, terminator);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************

	 
	 
	 /**
	  *
	  */
	 private void initializePort(String commPort, String terminator)throws Exception{

		this.terminator = terminator;
		this.serialPort = new SerialPort(commPort); 
		this.open();
		//this.pr = new PortReader(this.serialPort);
		this.serialPort.addEventListener(this);/* defined below */
		
		this.serialPort.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE);
		// this.serialPort.setParams(9600, 8, 1, 0); // alternate technique
		//serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
		
		int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
		this.serialPort.setEventsMask(mask);

	 }
	 
	 public byte[] getNewestReadedData(){
		 return this.fifo.getFirst();
	 }
	 
	 public byte[] getOldestReadedData(){
		 return this.fifo.getLast();
	 }
	 
	 public byte[] getReadedDataAtIndex(int index){
		 return this.fifo.get(index);
	 }
	 
	 public boolean hasData() {
		 return !fifo.isEmpty();
	 }
	
	 private void waitForIncomingData() throws Exception{
		 while (!this.hasData()) {Thread.sleep(50);}
	 }

	

	 /**
	  *
	  */
	 
	 public void serialEvent(SerialPortEvent event) {
		 
		 if(event.isRXCHAR()){ // data is available
	         // read data, quantity = getEventValue bytes 
	    	 int nBytesToRead = event.getEventValue();
	    	 //System.out.println(nBytesToRead);
	    	 byte[] readBuffer;
	    	 try {
				readBuffer = this.serialPort.readBytes(nBytesToRead);
				
			    for (int i=0;i<nBytesToRead;i++){
			    	byte b = readBuffer[i];
					buffer[bufferPointer+i]=b;
				}
			    
			    bufferPointer = bufferPointer + nBytesToRead;
			      
				if (buffer[bufferPointer-1]==10) 
				{
					fifo.add(buffer);
					bufferPointer = 0;
				}
				
	    	 } catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    	 }    
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
	 * Get last income data (the newest arrived data)
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @return the data readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public byte[] read() throws Exception{
		this.waitForIncomingData();
		return this.fifo.getFirst();
	}

	/**
	 * Sends the data to the output of the adapter
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public void write(String data) throws Exception{
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getPortName());
		 this.serialPort.writeBytes((data + terminator).getBytes());
	}
	
	/**
	 * Sends a query to the output of the adapter and waits for the response
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @return the response readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @throws Exception 
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public byte[] ask(String query) throws Exception {
		this.write(query);
		return this.read();
	}
	
	 //**************************************************************************
	 //****************************TESTING***************************************
	 //**************************************************************************	
	
}
