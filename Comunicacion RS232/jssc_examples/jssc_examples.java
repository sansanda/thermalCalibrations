package jssc_examples;

import java.io.*;

import java.util.*;

import javax.swing.JOptionPane;

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
	 private byte[] 					buffer = new byte[256];
	 private int 						bufferPointer = 0;
	 private boolean 					txOK = false;


	 private String 					terminator = "\n";
	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public jssc_examples(String commPort, String terminator) throws Exception{
		 this.initializePort(commPort, terminator);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************

	 /**
	  *
	  */
	 public void closeSerialPort(){
		 try {
			this.serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 /**
	  *
	  * @param message
	  */
	 public void sendMessageToSerialPort(String message){
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		 try {
			this.serialPort.writeBytes((message + terminator).getBytes());
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	 /**
	  *
	  */
	 private void initializePort(String commPort, String terminator)throws Exception{

		this.terminator = terminator;
		this.serialPort = new SerialPort(commPort); 
		this.serialPort.setParams(BAUDRATE_9600,  DATABITS_8, STOPBITS_1, PARITY_NONE);
		// this.serialPort.setParams(9600, 8, 1, 0); // alternate technique
		int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
		this.serialPort.setEventsMask(mask);
		this.serialPort.addEventListener(this /* defined below */);
		//this.serialPortThread = new Thread(this);
		//this.serialPortThread.start();
	 }

	 public void open() {
		 try {
			this.serialPort.openPort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 /**
	  *
	  */
	 
	 public void serialEvent(SerialPortEvent event) {
	      if(event.isRXCHAR()){ // data is available
	         // read data, if 10 bytes available 
	         if(event.getEventValue() == 10){
	            try {
	               byte buffer[] = serialPort.readBytes(10);
	               System.out.println(Arrays.toString(buffer));
	            } catch (SerialPortException ex) {
	               System.out.println(ex);
	            }
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
	 
	 /*
	 public void serialEvent(SerialPortEvent event) {

		switch (event.getEventType()) {

		case SerialPortEvent.BI:
			//System.out.println("Break interrupt.");
		case SerialPortEvent.OE:
			//System.out.println("Overrun error.");
		case SerialPortEvent.FE:
			//System.out.println("Framing error.");
		case SerialPortEvent.PE:
			//System.out.println("Parity error.");
		case SerialPortEvent.CD:
			System.out.println("Carrier detect.");
		case SerialPortEvent.CTS:
			//System.out.println("Clear to send.");
		case SerialPortEvent.DSR:
			//System.out.println("Data set ready.");
		case SerialPortEvent.RI:
			//System.out.println("Ring indicator.");
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			//System.out.println("Output buffer is empty.");
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[256];
		    try
		    {
				while (inputStream.available() > 0) {
				    int numBytes = inputStream.read(readBuffer);
				    for (int i=0;i<numBytes;i++){
				    	byte b = readBuffer[i];
						buffer[bufferPointer+i]=b;
						
						if (b==10){txOK = true;}
					}
				    
				    bufferPointer = bufferPointer + numBytes;
				}
		    } catch (IOException e) {System.out.println(e.getStackTrace());System.out.println(e.getCause());}

		    break;
		}
	 }
	 */
	 
	 
	 public byte[] getReadData(){
		 bufferPointer = 0;
		 return buffer;
	 }
	 
	 /**
	 * @return the txOK
	 */
	 public boolean isTxOK() {
		 return txOK;
	 }
	
	 public void waitForIncomingData() throws Exception{
		 while (!txOK) {Thread.sleep(50);}
		 txOK = false;
	 }
	 public int getReadDataLength(){
		return bufferPointer-1;
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
			sp.open();
			while (true){
				System.out.println("Introduce la cadena de caracteres a enviar por el puerto serie: ");
				sp.sendMessageToSerialPort(reader.readLine());
			}
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}

}