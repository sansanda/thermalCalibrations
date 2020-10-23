import java.io.*;
import Ports.S_Port;

public class TEK extends S_Port {

	
	static File 			testFile 	= null; 
	static File 			resFile 	= null;

	static BufferedWriter 	fWriter 	= null;
	static BufferedReader 	fReader 	= null;

	
	public TEK(String wantedPortName) throws Exception {
		super(wantedPortName);
		// TODO Auto-generated constructor stub	
		testFile = new File(System.getProperty("user.dir"),"Test.txt");
		resFile  = new File(System.getProperty("user.dir"),"Res.txt");
		fWriter	 = new BufferedWriter(new FileWriter(resFile));
		fReader  = new BufferedReader(new FileReader(testFile));
		
	}
	public boolean testSystem() throws Exception{
		boolean OK = true;
		String line;
		while (!((line = fReader.readLine()) == null))
		{
			this.sendMessageToSerialPort(line);
			
			Thread.sleep(2000);
			//fWriter.write((String)System.out);
		}
		return OK;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 try
		 {
			 BufferedReader consoleReader = new BufferedReader (new InputStreamReader(System.in));  
			 System.out.println("Inicializando el Osciloscopio...");
			 Thread.sleep(2000);
			 TEK t = new TEK("COM4");
			
			 System.out.println("Testeando el Oscilioscopio...");
			 Thread.sleep(2000);
			 t.testSystem();
			
			 System.out.print("Ending process...");
			 Thread.sleep(2000);
			 System.out.print("Closing ports...");
			 t.closeSerialPort();
			 System.exit(1);
			 
		 }
		 catch(Exception e)
		 {
			 System.err.println(e.getMessage() + e.getCause());
		 }
	}

}
