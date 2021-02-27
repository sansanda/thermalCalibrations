package testing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.CommunicationsModuleComponent;
import communications.RS232Interface_Component;
import information.GeneralInformation_Component;



public class Tests {

	private static final int classVersion = 101;
	
	final static Logger logger = LogManager.getLogger(Tests.class);
	
	public static void main(String[] args) {
		 try
		 {
			 String rs232_interface_filename = "./Components/testing/rs232_interface.json";
			 String gi_filename = "./Components/testing/general_information.json";
			 
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			logger.info("TESTNG COMMUNICATION PORT COMPONENTS");
			 
			//I_InstrumentComponent jssc_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JSSC_RS232);
			RS232Interface_Component rs232_interface = RS232Interface_Component.parseFromJSON(rs232_interface_filename);
			GeneralInformation_Component gi = GeneralInformation_Component.parseFromJSON(gi_filename);
			//Si estas en una maquina no de 32bits no podrás utilizar esta interface  
			//I_InstrumentComponent javacomm_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JAVACOMM_RS232);
			//System.out.println("Working Directory = " + System.getProperty("user.dir"));
			//I_InstrumentComponent rs232_interface2 = CommunicationInterface_Factory.parseFromJSON(filename);
			
			//logger.info(rs232_interface.toString());
			logger.info(gi.toString());
			
			
//			CommunicationsModuleComponent cmc = new CommunicationsModuleComponent(
//					"cmc", 
//					System.currentTimeMillis(), 
//					new ArrayList<String>(Arrays.asList("Communications","RS232","LAN","GPIB")), 
//					null, 
//					null);
//			
//			logger.info(cmc.toString());
//			
//			//cmc.addInterface(jssc_interface);
//			cmc.addInterface(rs232_interface);
//			//cmc.removeInterface(rs232_interface.getName());
//			logger.info(cmc.toString());
//			
//			logger.info(new String(cmc.ask("*IDN?"), StandardCharsets.UTF_8));
//			cmc.close();
			
			
			System.exit(0);
			
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	}

}	