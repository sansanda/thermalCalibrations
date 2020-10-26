package Ports;
import java.io.*;

import java.util.*;
import javax.comm.*;
import javax.swing.JOptionPane;


public class S_Port implements Runnable, SerialPortEventListener{

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
	 private Thread		      			serialPortThread;
	 private byte[] 					buffer = new byte[256];
	 private int 						bufferPointer = 0;
	 private boolean 					txOK = false;

	 //**************************************************************************
	 //****************************CONSTRUCTORES*********************************
	 //**************************************************************************

	 /**
	  *
	  *
	  */
	 public S_Port(String wantedPortName) throws Exception{
		 this.wantedPortName = wantedPortName;
		 this.initializePort();
	 }

	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************

	 /**
	  *
	  */
	 public void closeSerialPort(){
		 try {
			 Thread.sleep(2000);  // Be sure data is xferred before closing
		 }
		 catch (Exception e) {}
		 this.serialPort.close();
		 
	 }

	 /**
	  *
	  * @param message
	  */
	 public void sendMessageToSerialPort(String message){
		 //System.out.println("\n"+"Writing \""+message+"\" to "+serialPort.getName());
		 try {
			 outputStream.write((message + "\t\n").getBytes());
		 } catch (IOException e) {}
	 }

	 /**
	  *
	  */
	 private void initializePort()throws Exception{

		 this.searchForSerialCommPort();
		 this.openSerialPort();
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
		 this.serialPortThread = new Thread(this);
		 this.serialPortThread.start();
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
		    } catch (IOException e) {}
		    break;
		}
	 }
	 public byte[] getReadData(){
		 bufferPointer = 0;
		 return buffer;
	 }
	 public void waitForIncomingData() throws Exception{
		 while (!txOK){}
		 txOK = false;
	 }
	 public int getReadDataLength(){
		return bufferPointer-1;
	 }
	 /**
	  *
	  */
	 public void run() {
		try
		{
		    Thread.sleep(20000);
		} catch (InterruptedException e) {}
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
		 } catch (UnsupportedCommOperationException e) {}
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
	 /**
	  *
	  * @return
	  */
	 private void openSerialPort(){
		try{
			if (portId.isCurrentlyOwned())
			{
				System.out.println("Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
				JOptionPane.showMessageDialog(null,"Port "+portId.getName() + " currently owned"+" by"+portId.getCurrentOwner());
			}else
			{
				serialPort = (SerialPort) portId.open("RS232_PortApp", 2000); //SimpleReadApp
			}
		}
		catch(PortInUseException e){
			System.err.println("Port already in use: " + e.getMessage() +"\n"+ e.getCause() +"\n"+ e.getLocalizedMessage()+"\n"+e.getClass());
			JOptionPane.showMessageDialog(null,"Port already in use.");

		}
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
			S_Port sp = new S_Port("COM8");
			while (true){
				System.out.println("Introduce la cadena de caracteres a enviar por el puerto serie: ");
				sp.sendMessageToSerialPort(reader.readLine());
			}
		 }
		 catch(Exception e)
		 {

		 }
	}

}
