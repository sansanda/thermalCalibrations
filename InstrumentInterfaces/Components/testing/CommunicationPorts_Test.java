package testing;

import java.nio.charset.StandardCharsets;

import communications.I_CommPortComponent;
import communications.JSSC_SerialPort_Component;

public class CommunicationPorts_Test {

	public static void main(String[] args) {
		 try
		 {
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			I_CommPortComponent sp = new JSSC_SerialPort_Component("COM1", 19200, 8, 1, 0, "\n", 250, 0);	
			System.out.println(new String(sp.ask("*IDN?"), StandardCharsets.UTF_8));
			sp.close();
			System.exit(0);
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
	}

}