package jssc_examples;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import static jssc.SerialPort.*;


public class jssc_examples implements SerialPortEventListener{

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
	 public jssc_examples(String commPort, String terminator) throws Exception{
		 
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

	 public void open() {
		 try {
			 if (this.serialPort.isOpened()) return;
			 this.serialPort.openPort();
				
		} catch (SerialPortException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		}
	 }
	 
	 public void close() {
		 try {
			 if (!this.serialPort.isOpened()) return;
			 this.serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 
	 /**
	  *
	  * @param data
	  */
	 public void sendData(String data){
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getPortName());
		 try {
			 this.open();
			 this.serialPort.writeBytes((data + terminator).getBytes());
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 /**
	 * @return the txOK
	 */
	 public boolean hasData() {
		 return !fifo.isEmpty();
	 }
	
	 public void waitForIncomingData() throws Exception{
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
	 


	 //**************************************************************************
	 //****************************SETTERS***************************************
	 //**************************************************************************



	 //**************************************************************************
	 //****************************GETTERS***************************************
	 //**************************************************************************



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));

		 try
		 {
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			jssc_examples sp = new jssc_examples("COM9","\n");
			sp.sendData("*IDN?");
			sp.waitForIncomingData();
			System.out.println(new String(sp.getNewestReadedData(), StandardCharsets.UTF_8));
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}
}