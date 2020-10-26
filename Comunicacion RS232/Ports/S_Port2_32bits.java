package Ports;
import java.io.*;


import java.util.*;
import javax.comm.*;
import javax.swing.JOptionPane;

import interfaces.ICommunicationPort;


public class S_Port2_32bits implements ICommunicationPort, SerialPortEventListener{

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
	 

	 
	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public S_Port2_32bits(String wantedPortName) throws Exception{
		 receivedData = "";
		 readBuffer = new StringBuffer();
		 initialize(wantedPortName);
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************
	 
	 /**
	  *
	  * @return
	  */
	 public void open() throws Exception{
		if (portId.isCurrentlyOwned())
		{
			System.out.println("Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
			JOptionPane.showMessageDialog(null,"Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
		}else
		{
			serialPort = (SerialPort) portId.open("RS232_PortApp", 2000); //SimpleReadApp
		}
	 }
	 
	 /**
	  *
	  */
	 public void close() throws Exception{
		 Thread.sleep(2000);  // Be sure data is xferred before closing
		 serialPort.close(); 
	 }

	 /**
	  *
	  * @param message
	  */
	 public void sendData(String message){
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		 try {
			 outputStream.write((message + "\t\n").getBytes());
		 } catch (IOException e) {}
	 }
	 
	 /**
	  *
	  * @param message
	  */
	 public void sendData(byte[] message){
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		 byte[] m = new byte[message.length+2];
		 m[m.length-2] = 13;//\t
		 m[m.length-1] = 10;//\n
		 
		 try {
			 outputStream.write((m));
		 } catch (IOException e) {}
	 }
	 

	 /**
	  *
	  */
	 public void initialize(String wantedPortName) throws Exception{

		 portId = searchForSerialCommPort(wantedPortName);
		 open();
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
			readBuffer = new StringBuffer();
			int c;
            try {
                 while ((c=inputStream.read()) != 10){
                   if(c!=13)  readBuffer.append((char) c);
                 }
                 receivedData = readBuffer.toString();
                 String TimeStamp = new java.util.Date().toString();
                 //System.out.println(TimeStamp + ": scanned input received:" + receivedData);
                 inputStream.close();
                 txOK = true;
            } catch (IOException e) {}

            break;
		}
	 }
	 public byte[] readDataAsByteArray() throws Exception{
		 waitForIncomingData();
		 return readDataAsString().getBytes();
	 }
	 public String readDataAsString() throws Exception{
		 waitForIncomingData();
		 String copy = new String(receivedData);
		 receivedData = "";
		 return copy;
	 }
	 
	 private void waitForIncomingData() throws Exception{
		 while (!txOK){}
		 txOK = false;
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
			 serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			 serialPort.setDTR(false);
			 serialPort.setRTS(false);
		 } catch (UnsupportedCommOperationException e) {}
	 }
	 

	 //**************************************************************************
	 //****************************GETTERS***************************************
	 //**************************************************************************


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
			S_Port2_32bits sp = new S_Port2_32bits("COM7");
			while (true){
				System.out.println("Introduce la cadena de caracteres a enviar por el puerto serie: ");
				sp.sendData(reader.readLine());
			}
		 }
		 catch(Exception e)
		 {
			// TODO Auto-generated catch block
				e.printStackTrace();
		 }
	}

}
