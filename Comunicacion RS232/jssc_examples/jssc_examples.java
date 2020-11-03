package jssc_examples;

import java.io.*;
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

	 //private InputStream		      	inputStream;
	 //private OutputStream       		outputStream;
	 private SerialPort		      		serialPort;
	 private Thread		      			serialPortThread;
	 private final int					BUFFER_LENGTH = 256;
	 private byte[] 					buffer = null;


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
		this.serialPort.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE);
		// this.serialPort.setParams(9600, 8, 1, 0); // alternate technique
		int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
		this.serialPort.setEventsMask(mask);
		this.serialPort.addEventListener(this /* defined below */);
		//this.serialPortThread = new Thread(this);
		//this.serialPortThread.start();
		this.close();
	 }

	 public void open() {
		 try {
			 if (this.serialPort.isOpened()) this.serialPort.closePort();
			 this.serialPort.openPort();
				
		} catch (SerialPortException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		}
	 }
	 
	 public void close() {
		 try {
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
			 this.close();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	 
	 private byte[] readData(int numBytes) {
		this.open();
		byte[] data = null;
		try {
			data = this.serialPort.readBytes(numBytes /* read first numBytes bytes */);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.close();
		return data;
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
	    	 byte[] readBuffer = this.readData(nBytesToRead);
	         System.arraycopy(readBuffer, 0, buffer, buffer.length, readBuffer.length);
	         if (buffer[buffer.length-1]==10) 
	         {
	        	 fifo.add(buffer);
	        	 buffer = new byte[BUFFER_LENGTH];
	         }
	         
	      } else if(event.isCTS()){ // CTS line has changed state
	         if(event.getEventValue() == 1){ // line is ON
	            System.out.println("CTS - ON");
	         } else {
	            System.out.println("CTS - OFF");
	         }
	      } else if(event.isDSR()){ // DSR line has changed state
	         if(event.getEventValue() == 1){ // line is ON
	            System.out.println("DSR - ON");
	         } else {
	            System.out.println("DSR - OFF");
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
			jssc_examples sp = new jssc_examples("COM3","\n");
			while (true){
				System.out.println("Introduce la cadena de caracteres a enviar por el puerto serie: ");
				sp.sendData(reader.readLine());
			}
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}

}