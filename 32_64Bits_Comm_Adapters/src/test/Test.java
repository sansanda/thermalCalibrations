package test;

import java.nio.charset.StandardCharsets;

import common.CommPort_I;
import rs_232.S_Port_JSSC;

public class Test {

	public static void main(String[] args) {
		 try
		 {
			//El puerto se encuenta en el equipo y adquirimos un objeto
			//tipo SerialPort para poder manejar dicho puerto
			CommPort_I sp = new S_Port_JSSC("COM9","\n");	
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