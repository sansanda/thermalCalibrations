package factories;

import java.util.ArrayList;


import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.RS232Interface_Component;
import jssc.SerialPort;
import testing.CommunicationsModule_Test;

public class CommunicationInterface_Factory {
	
	private static final int classVersion = 104;
		
	//Comm interfaces
	public static final String GPIB_INTERFACE	= "gpib_interface";
	public static final String LAN_INTERFACE	= "lan_interface";
	public static final String RS232_INTERFACE	= "rs232_interface";
	
	
	public static ArrayList<String> commInterfaceTypes = new ArrayList<String>(Arrays.asList(GPIB_INTERFACE,RS232_INTERFACE,LAN_INTERFACE));

	final static Logger logger = LogManager.getLogger(CommunicationsModule_Test.class);
	
	public static I_InstrumentComponent getCommInterface(String commInterfaceType) throws Exception
	{
		String commInterfaceTypeLC = commInterfaceType.toLowerCase();
		if (!commInterfaceTypes.contains(commInterfaceTypeLC)) return null;
		else
		{
			if (commInterfaceTypeLC.equals(RS232_INTERFACE))
			{
				logger.info("Creating RS232Interface_Component");
				return new RS232Interface_Component(
						commInterfaceTypeLC,
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
	
	//version 104: change to use only rs232_component_interface
	
}
