package factories;

import java.util.ArrayList;

import java.util.Arrays;

import communications.I_CommPortComponent;
import communications.JSSC_SerialPort_Component;
import communications.JavaComm_SerialPort_Component;
import communications.RXTX_SerialPort_Component;
import jssc.SerialPort;

public class CommPort_Factory {
	
	private static final int classVersion = 103;
	
	//Comm port names
	public static final String JSSC_RS232 		= "jssc_rs232";
	public static final String RXTX_RS232 		= "rxtx_rs232";
	public static final String JAVACOMM_RS232 	= "javacomm_rs232";
		
	//Comm interfaces
	public static final String GPIB_INTERFACE	= "gpib_interface";
	public static final String LAN_INTERFACE	= "lan_interface";
	public static final String RS232_INTERFACE	= "rs232_interface";
	
	
	public static ArrayList<String> registeredPortNames = new ArrayList<String>(Arrays.asList(JSSC_RS232,RXTX_RS232,JAVACOMM_RS232));
	public static ArrayList<String> registeredCommInterfaces = new ArrayList<String>(Arrays.asList(GPIB_INTERFACE,RS232_INTERFACE,LAN_INTERFACE));

	
	public static I_CommPortComponent getPort(String portName) throws Exception
	{
		String portNameLC = portName.toLowerCase();
		if (!registeredPortNames.contains(portNameLC)) return null;
		else
		{
			if (portNameLC.equals(JSSC_RS232))
			{
				return new JSSC_SerialPort_Component(
						portNameLC,
						System.currentTimeMillis(),
						null,
						RS232_INTERFACE,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						"\n", 
						250, 
						0);
			}
				
			if (portNameLC.equals(RXTX_RS232))
			{
				return new RXTX_SerialPort_Component(
						portNameLC,
						System.currentTimeMillis(),
						null,
						RS232_INTERFACE,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						"\n", 
						250, 
						0);
			}
			
			if (portNameLC.equals(JAVACOMM_RS232))
			{
				return new JavaComm_SerialPort_Component(
						portNameLC,
						System.currentTimeMillis(),
						null,
						RS232_INTERFACE,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						"\n", 
						250, 
						0);
			}
			
			return null;
		}
	}


	public static int getClassversion() {
		return classVersion;
	}
	
}
