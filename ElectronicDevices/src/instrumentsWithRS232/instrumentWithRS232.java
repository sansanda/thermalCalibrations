package instrumentsWithRS232;
import java.io.BufferedReader;


import java.io.File;
import java.io.FileReader;

import Ports.S_Port;


public abstract class instrumentWithRS232 extends S_Port{
	public instrumentWithRS232(String wantedPortName) throws Exception{
		super(wantedPortName);
	}
	public void configure(String _configurationFile) throws Exception{
		System.out.println("Configurando el instrumento...");
		File instrumentConfigFile;
		BufferedReader 	fReader 	= null;
		instrumentConfigFile = new File(_configurationFile);
		fReader  = new BufferedReader(new FileReader(instrumentConfigFile));
		String line;
		while (!((line = fReader.readLine()) == null))
		{
			if (line.startsWith("#")){
				line = line.replaceFirst("#", "");
				line = line.substring(0, line.length());
				System.out.println(line);
			}else
			{
				sendMessageToSerialPort(line);
				Thread.sleep(200);
			}
		}
		fReader.close();
	}
	public void initialize(String _initializationFile) throws Exception{
		System.out.println("Inicializando el instrumento...");
		File instrumentInitFile;
		BufferedReader 	fReader 	= null;
		instrumentInitFile = new File(_initializationFile);
		fReader  = new BufferedReader(new FileReader(instrumentInitFile));
		String line;
		while (!((line = fReader.readLine()) == null))
		{
			if (line.startsWith("#")){
				line = line.replaceFirst("#", "");
				line = line.substring(0, line.length());
				System.out.println(line);
			}else
			{
				sendMessageToSerialPort(line);
				Thread.sleep(500);
			}
		}
		fReader.close();
	}
	public void test(String _testFile) throws Exception{
		System.out.println("Testeando el instrumento...");
		File instrumentConfigFile;
		BufferedReader 	fReader 	= null;
		instrumentConfigFile = new File(_testFile);
		fReader  = new BufferedReader(new FileReader(instrumentConfigFile));
		String line;
		while (!((line = fReader.readLine()) == null))
		{
			if (line.startsWith("#")){
				line = line.replaceFirst("#", "");
				line = line.substring(0, line.length());
				System.out.println(line);
			}else
			{
				sendMessageToSerialPort(line);
				Thread.sleep(500);
			}
		}
		fReader.close();
	}
}
