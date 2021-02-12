package factories;

import java.util.ArrayList;

import java.util.Arrays;

import common.I_InstrumentComponent;
import communications.I_CommunicationsInterface;
import communications.JSSCBased_RS232Interface_Component;
import communications.JavaCommBased_RS232Interface_Component;
import communications.RXTXBased_RS232Interface_Component;
import jssc.SerialPort;

public class CommunicationInterface_Factory {
	
	private static final int classVersion = 103;
	
	//Comm interface drivers
	public static final String JSSC_RS232 		= "jssc_rs232";
	public static final String RXTX_RS232 		= "rxtx_rs232";
	public static final String JAVACOMM_RS232 	= "javacomm_rs232";
		
	//Comm interfaces
	public static final String GPIB_INTERFACE	= "gpib_interface";
	public static final String LAN_INTERFACE	= "lan_interface";
	public static final String RS232_INTERFACE	= "rs232_interface";
	
	
	public static ArrayList<String> registeredCommInterfaceDrivers = new ArrayList<String>(Arrays.asList(JSSC_RS232,RXTX_RS232,JAVACOMM_RS232));
	public static ArrayList<String> registeredCommInterfaces = new ArrayList<String>(Arrays.asList(GPIB_INTERFACE,RS232_INTERFACE,LAN_INTERFACE));

	
	public static I_InstrumentComponent getInterfaceDriver(String driverName) throws Exception
	{
		String driverNameLC = driverName.toLowerCase();
		if (!registeredCommInterfaceDrivers.contains(driverNameLC)) return null;
		else
		{
			if (driverNameLC.equals(JSSC_RS232))
			{
				return new JSSCBased_RS232Interface_Component(
						driverNameLC,
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
				
			if (driverNameLC.equals(RXTX_RS232))
			{
				return new RXTXBased_RS232Interface_Component(
						driverNameLC,
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
			
			if (driverNameLC.equals(JAVACOMM_RS232))
			{
				return new JavaCommBased_RS232Interface_Component(
						driverNameLC,
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
