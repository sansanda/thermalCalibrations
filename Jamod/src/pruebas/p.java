package pruebas;
import net.wimpi.modbus.cmd.SerialAITest;

public class p {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		net.wimpi.modbus.cmd.SerialAITest s = new net.wimpi.modbus.cmd.SerialAITest();
		String[] r = new String[4];
		
		/*
		0 <portname [String]> as String into portname 
		1 <Unit Address [int8]> as String into unitid 
		2 <register [int16]> as int into ref 
		3 <wordcount [int16]> as int into count 
		4 {<repeat [int]>} as int into repeat, 1 by default (optional) 
		*/
		
		r[0] = "COM5";
		r[1] = "01";
		r[2] = "02";
		r[3] = "01";
		s.main(r);
	}

}
