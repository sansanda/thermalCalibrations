package rs_232;
import java.io.*;

import java.util.*;
import javax.comm.*;
import javax.swing.JOptionPane;

import common.CommPort_I;

/**
 * Clase que implementa un puerto serie tipo RS232 haciendo uso de la libreria javacomm-20-win32 
 * compilada para maquinas de 32 bits en windows
 * @author DavidS
 *
 */

public class S_Port_32bits implements CommPort_I, SerialPortEventListener{

	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************


	 //**************************************************************************
	 //****************************VARIABLES*************************************
	 //**************************************************************************


	 static CommPortIdentifier 					portId = null;
	 static Enumeration<CommPortIdentifier>	   	portList = null;
	 static String 						wantedPortName = null;
	 private InputStream		      	inputStream;
	 private OutputStream       		outputStream;
	 private SerialPort		      		serialPort;
	 private byte[] 					buffer = new byte[256];
	 private int 						bufferPointer = 0;
	 private boolean 					txOK = false;
	 private String 					terminator = null;

	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public S_Port_32bits(String wantedPortName, String terminator) throws Exception{
		 this.wantedPortName = wantedPortName;
		 this.terminator = terminator;
		 this.initializePort();
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************


	 /**
	  *
	  */
	 private void initializePort()throws Exception{

		 this.searchForSerialCommPort();
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
	 private void setPortParams(){
		 try
		 {
			 this.serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
		 } 
		 catch (UnsupportedCommOperationException e) {}
	 }
	 

	 //**************************************************************************
	 //****************************GETTERS***************************************
	 //**************************************************************************


	 /**
	  *
	  */
	 private void searchForSerialCommPort() throws Exception{

		boolean				portFound 		= false;
		CommPortIdentifier 	WantedPortId 	= null;

		//Preguntamos a todos los puertos de la lista
		//si nuestro puerto se encuentra en el equipo
		System.out.println("Buscando puertos del sistema....\n");
		Thread.sleep(1000);
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = portList.nextElement();
			System.out.print(portId.getName()+ " ");
			Thread.sleep(500);
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(wantedPortName)) {
					portFound = true;
					//Memorizamos el identificador del puerto que buscamos
					WantedPortId = portId;
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
		portId = WantedPortId;
	 }

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
			System.out.println("Going to close the port....");
			this.serialPort.close();
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
		 this.serialPort.close();
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
		return buffer;
	}

	/**
	 * Sends the data to the output of the adapter
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public void write(String data) throws Exception{
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
