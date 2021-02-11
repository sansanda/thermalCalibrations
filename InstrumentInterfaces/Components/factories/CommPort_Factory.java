package factories;

import java.util.ArrayList;

import java.util.Arrays;

import communications.I_CommPortComponent;
import communications.JSSC_SerialPort_Component;
import communications.JavaComm_SerialPort_Component;
import communications.RXTX_SerialPort_Component;
import jssc.SerialPort;

public class CommPort_Factory {
	
	private static final int classVersion = 102;
	
	public static final String GPIB 			= "gpib";
	public static final String JSSC_RS232 		= "jssc_rs232";
	public static final String RXTX_RS232 		= "rxtx_rs232";
	public static final String JAVACOMM_RS232 	= "javacomm_rs232";
	public static final String LAN 				= "lan";
	
	public static ArrayList<String> portTypes = new ArrayList<String>(Arrays.asList(GPIB,JSSC_RS232,RXTX_RS232,JAVACOMM_RS232,LAN));
	
	
	public static I_CommPortComponent getPort(String portType) throws Exception
	{
		String portTypeLC = portType.toLowerCase();
		if (!portTypes.contains(portTypeLC)) return null;
		else
		{
			if (portTypeLC.equals(GPIB)) 	return null;
			if (portTypeLC.equals(LAN)) 	return null;
			if (portTypeLC.equals(JSSC_RS232))
			{
				return new JSSC_SerialPort_Component(
						portTypeLC,
						System.currentTimeMillis(),
						null,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						"\n", 
						250, 
						0);
			}
				
			if (portTypeLC.equals(RXTX_RS232))
			{
				return new RXTX_SerialPort_Component(
						portTypeLC,
						System.currentTimeMillis(),
						null,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						"\n", 
						250, 
						0);
			}
			
			if (portTypeLC.equals(JAVACOMM_RS232))
			{
				return new JavaComm_SerialPort_Component(
						portTypeLC,
						System.currentTimeMillis(),
						null,
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
