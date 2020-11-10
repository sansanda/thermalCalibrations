package rs_232;
import java.io.*;


import java.util.*;
import javax.swing.JOptionPane;

import common.CommPort_I;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;


/**
 * Clase que implementa un puerto serie tipo RS232 haciendo uso de la libreria rxtx 
 * compilada para maquinas de 32 bits en windows.
 * @author DavidS
 *
 */
public class S_Port2_32bits implements CommPort_I, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************


	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************


	 private CommPortIdentifier 		portId = null;
	 private InputStream		      	inputStream;
	 private OutputStream       		outputStream;
	 private SerialPort		      		serialPort;
	 private boolean 					txOK = false;
	 private StringBuffer 				readBuffer = null;
	 private String 					receivedData = null;
	 private String 					terminator = null;
	 

	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public S_Port2_32bits(String wantedPortName, String terminator) throws Exception{
		 this.receivedData = "";
		 this.terminator = terminator;
		 readBuffer = new StringBuffer();
		 initialize(wantedPortName);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************
	 

	 /**
	  *
	  */
	 private void initialize(String wantedPortName) throws Exception{

		 portId = searchForSerialCommPort(wantedPortName);
		 this.open();
		 this.inputStream = this.getPortInputStream();
		 this.outputStream = this.getPortOutputStream();
		 try
		 {
			 this.serialPort.addEventListener(this);
		 }
		 catch (TooManyListenersException e) {}
		 this.setPortParams();

		 try
		 {
			 this.serialPort.notifyOnDataAvailable(true);
		 }
		 catch (Exception e) {
			System.out.println("Error setting event notification");
			System.out.println(e.toString());
			System.exit(-1);
		 }
		 try
		 {
			 this.serialPort.notifyOnOutputEmpty(true);
		 }
		 catch (Exception e) {
			System.out.println("Error setting event notification");
			System.out.println(e.toString());
			System.exit(-1);
		 }
	 }

	 /**
	  *
	  */
	 
	private CommPortIdentifier searchForSerialCommPort(String wantedPortName) throws Exception{

		boolean								portFound 		= false;
		CommPortIdentifier 					portId 	= null;
		Enumeration<CommPortIdentifier>	   	portList = null;
		 
		//Preguntamos a todos los puertos de la lista
		//si nuestro puerto se encuentra en el equipo
		System.out.println("Buscando puertos del sistema....\n");
		Thread.sleep(1000);
		portList = CommPortIdentifier.getPortIdentifiers();
		
		while (portList.hasMoreElements() && !portFound) {
			portId = portList.nextElement();
			System.out.print(portId.getName()+ " ");
			Thread.sleep(500);
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(wantedPortName)) {
					portFound = true;	
				}
			}
		}
		
		if (!portFound) {
		    System.out.println("port " + wantedPortName + " not found.");
		    JOptionPane.showMessageDialog(null,"port " + wantedPortName + " not found.");
		}else
		{
			System.out.println(" ............ Found port: "+wantedPortName);
		}
		//portId es el identificador del puerto que buscamos
		return portId;
	 }
	
	 /**
	  *
	  */
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
	 private void setPortParams() throws Exception{
		 serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
		 serialPort.setDTR(false);
		 serialPort.setRTS(false);
	 }
	 

	 //**************************************************************************
	 //****************************GETTERS***************************************
	 //**************************************************************************


	 /**
	  * Permite obtener el flujo de datos de entrada del puerto serie
	  * para poder leer datos provenientes de este.
	  * @return
	  */
	 private InputStream getPortInputStream(){
		 InputStream is = null;
		 try {
			is = this.serialPort.getInputStream();
		 } catch (IOException e) {}
		 return is;
	 }
	 /**
	  * Permite obtener el flujo de datos de entrada del puerto serie
	  * para poder leer datos provenientes de este.
	  * @return
	  */
	 private OutputStream getPortOutputStream(){
		 OutputStream os = null;
		 try {
			 os = this.serialPort.getOutputStream();
		 } catch (IOException e) {}
		 return os;
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
			System.out.println("Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
			JOptionPane.showMessageDialog(null,"Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
		}else
		{
			serialPort = (SerialPort) this.portId.open("RS232_PortApp", 2000); //SimpleReadApp
		}			
	 }
	 
	 /**
	  * Close the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void close() throws Exception {
		 Thread.sleep(2000);  // Be sure data is xferred before closing
		 serialPort.close();
	 }
	 

	 private String readDataAsString() throws Exception{
		 String copy = new String(receivedData);
		 receivedData = "";
		 return copy;
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
		return this.readDataAsString().getBytes();
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
		//System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		outputStream.write((data + this.terminator).getBytes());
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