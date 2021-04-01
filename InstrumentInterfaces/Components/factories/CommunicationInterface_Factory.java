package factories;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.GPIBInterface_Component;
import communications.RS232Interface_Component;
import jssc.SerialPort;
import testing.Tests;

public class CommunicationInterface_Factory {
	
	//version 105: changed to be adapted to the new constructors
	//version 104: change to use only rs232_component_interface
	//version 105: Added gpib interface component
	
	private static final int classVersion = 105;
		
	//Comm interfaces types
	public static final String SERIAL_TYPE		= "serial";
	public static final String PARALLEL_TYPE	= "parallel";
		
	//Comm interfaces
	public static final String GPIB_STANDARD	= "gpib_interface";
	public static final String LAN_STANDARD	= "lan_interface";
	public static final String RS232_STANDARD	= "rs232_interface";
	
	
	public static ArrayList<String> commInterfaceTypes = new ArrayList<String>(Arrays.asList(GPIB_STANDARD,RS232_STANDARD,LAN_STANDARD));

	final static Logger logger = LogManager.getLogger(Tests.class);
	
	public static I_InstrumentComponent getCommInterface(String commInterfaceType) throws Exception
	{
		String commInterfaceTypeLC = commInterfaceType.toLowerCase();
		if (!commInterfaceTypes.contains(commInterfaceTypeLC)) return null;
		else
		{
			if (commInterfaceTypeLC.equals(RS232_STANDARD))
			{
				logger.info("Creating RS232Interface_Component");
				return new RS232Interface_Component(
						commInterfaceTypeLC,
						System.currentTimeMillis(),
						null,
						true,
						true,
						SERIAL_TYPE,
						RS232_STANDARD,
						"COM1", 
						SerialPort.BAUDRATE_9600, 
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1, 
						SerialPort.PARITY_NONE, 
						SerialPort.FLOWCONTROL_NONE,
						"\n", 
						250, 
						0);
			}
			
			if (commInterfaceTypeLC.equals(GPIB_STANDARD))
			{
				logger.info("Creating GPIB Interface_Component");
				return new GPIBInterface_Component(
						commInterfaceTypeLC,
						System.currentTimeMillis(),
						null,
						true,
						true,
						PARALLEL_TYPE,
						GPIB_STANDARD,
						"14", 
						"\n", 
						1.000f,
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
