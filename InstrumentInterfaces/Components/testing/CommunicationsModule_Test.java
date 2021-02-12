package testing;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.CommunicationsModuleComponent;
import communications.I_CommunicationsInterface;
import factories.CommunicationInterface_Factory;


public class CommunicationsModule_Test {

	private static final int classVersion = 101;
	
	final static Logger logger = LogManager.getLogger(CommunicationsModule_Test.class);
	
	public static void main(String[] args) {
		 try
		 {
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			logger.info("TESTNG COMMUNICATION PORT COMPONENTS");
			 
			
			I_InstrumentComponent jssc_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JSSC_RS232);
			//I_InstrumentComponent rxtx_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.RXTX_RS232);
			//I_InstrumentComponent javacomm_interface = CommunicationInterface_Factory.getInterfaceDriver(CommunicationInterface_Factory.JAVACOMM_RS232);

			
			logger.info(new String(((I_CommunicationsInterface) jssc_interface).ask("*IDN?"), StandardCharsets.UTF_8));
			((I_CommunicationsInterface) jssc_interface).close();
			
			
			
			CommunicationsModuleComponent cmc = new CommunicationsModuleComponent(
					"cmc", 
					System.currentTimeMillis(), 
					new ArrayList<String>(Arrays.asList("Communications","RS232","LAN","GPIB")), 
					null, 
					null);
			
			cmc.addInterface(jssc_interface);
			cmc.addInterface(jssc_interface);
			logger.info(cmc.toString());
			
			
			System.exit(0);
			
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}

}	