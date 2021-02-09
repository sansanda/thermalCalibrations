package testing;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import communications.I_CommPortComponent;
import communications.JSSC_SerialPort_Component;

public class CommunicationPorts_Test {

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(CommunicationPorts_Test.class);
	
	public static void main(String[] args) {
		 try
		 {
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			logger.info("TESTNG COMMUNICATION PORT COMPONENTS");
			 
			I_CommPortComponent sp = new JSSC_SerialPort_Component("COM1", 19200, 8, 1, 0, "\n", 250, 0);	
			logger.info(new String(sp.ask("*IDN?"), StandardCharsets.UTF_8));
			sp.close();
			System.exit(0);
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}

}