package testing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.CommunicationsModule_Component;
import communications.GPIBInterface_Component;
import communications.RS232Interface_Component;
import information.GeneralInformation_Component;
import smus.K2700;



public class Tests {

	private static final int classVersion = 101;
	
	final static Logger logger = LogManager.getLogger(Tests.class);
	
	public static void main(String[] args) {
		 try
		 {
			 
			String rs232_interface_filename 					= "./Components/testing/rs232_interface.json";
			String gpib_interface_filename 					= "./Components/testing/gpib_interface.json";
			String gpib_interface_general_information_filename = "./Components/testing/gpibInterface_general_information.json";
			String gi_filename 								= "./Components/testing/general_information.json";
//			String k2700_filename	 							= "./Components/testing/k2700.json";
//			
//			logger.info("TESTNG k2700 creation from JSON file");
//			K2700 k2700 = K2700.parseFromJSON(k2700_filename);
//			logger.info(k2700.toString());
			 
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			logger.info("TESTNG COMMUNICATION PORT COMPONENTS");
			 
			//I_InstrumentComponent jssc_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JSSC_RS232);
			RS232Interface_Component 		rs232_interface 						= RS232Interface_Component.parseFromJSON(rs232_interface_filename);
			GPIBInterface_Component 		gpib_interface 							= GPIBInterface_Component.parseFromJSON(gpib_interface_filename);
			GeneralInformation_Component 	gpib_interface_general_information 		= GeneralInformation_Component.parseFromJSON(gpib_interface_general_information_filename);
			gpib_interface.addSubComponent(gpib_interface_general_information);
			logger.info(gpib_interface.getSubComponent("gpib_interface_general_information").toString());
			
			//Si estas en una maquina no de 32bits no podrás utilizar esta interface  
			//I_InstrumentComponent javacomm_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JAVACOMM_RS232);
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//I_InstrumentComponent rs232_interface2 = CommunicationInterface_Factory.parseFromJSON(filename);
			
			//logger.info(rs232_interface.toString());
			logger.info(gpib_interface_general_information.toString());
			
			
			CommunicationsModule_Component cmc = new CommunicationsModule_Component(
					"cmc", 
					System.currentTimeMillis(), 
					null,
					true,
					true,
					new ArrayList<String>(Arrays.asList("Communications","RS232","LAN","GPIB")), 
					null);
			
			logger.info(cmc.toString());
			
			//cmc.addInterface(jssc_interface);
			cmc.addInterface(rs232_interface);
			cmc.addInterface(gpib_interface);
		
			//cmc.removeInterface(rs232_interface.getName());
			//cmc.removeInterface(gpib_interface.getName());
			//logger.info(cmc.toString());
			
			
			gpib_interface.select(true);
			
			
			//fase de puesta a punto del canal de comunicaciones antes de iniciar la interacción con el instrumento
			cmc.initialize();
			cmc.open();
			
			//fase de interaccion con el instrumento
			logger.info(new String(cmc.ask("*IDN?"), StandardCharsets.UTF_8));
			
			cmc.write("SYST:BEEP OFF");
			cmc.write("DISPLAY:ENABLE ON");
			cmc.write("DISPLAY:WINDOW1:TEXT:STATE ON");
			cmc.write("DISPLAY:WINDOW1:TEXT:DATA 'HOLA MUNDOs'");
			
			logger.info(new String(cmc.ask("DISPLAY:WINDOW1:TEXT:DATA?")));
			logger.info(new String(cmc.ask("*STB?")));
			
			//fase de cierre del canal de comunicaciones
			cmc.close();
			
			System.exit(0);
			
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}

}	