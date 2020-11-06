package common;
import java.io.BufferedReader;


import java.io.File;
import java.io.FileReader;

import common.CommPort_I;


public abstract class instrumentWithCommAdapter{
	
	protected CommPort_I commAdapter = null;
	
	public instrumentWithCommAdapter(CommPort_I commAdapter) throws Exception{
		this.commAdapter = commAdapter;
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
				this.commAdapter.write(line);
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
				this.commAdapter.write(line);
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
				this.commAdapter.write(line);
				Thread.sleep(500);
			}
		}
		fReader.close();
	}
}
