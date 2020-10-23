package Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CalibrateTTC_Program extends Program{
	
	private int[] temperatureProfile; 
	private int nPointsTemperatureProfile;
	private String ovenSerialPort;
	private String ovenControllerID;
	private String multimeterSerialPort;
	private int temperatureSensorChannel;
	private int TTCRSenseChannel;
	private int TTCRHeatingChannel;
	private String TTCReference;
	
	private String programFilePath;
	
	private FileReader FileReader;
	private BufferedReader FileBufferedReader;
	
	public CalibrateTTC_Program(String _programFilePath) throws Exception{
		System.out.println(programFilePath);
		programFilePath = _programFilePath;
		loadProgramFromTextFile(programFilePath);
	}
	private void loadProgramFromTextFile(String _programFilePath){
		String line;
		try {
			FileReader = new FileReader(_programFilePath);
			FileBufferedReader = new BufferedReader(FileReader);
			line = FileBufferedReader.readLine();
			String[] parameters;
			while(!(line==null)){
				//System.out.println(line);
				if (line.startsWith("TTCReference")){
					line = line.replaceFirst("TTCReference", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					for (int i=0;i<parameters.length;i++){
						//System.out.print(parameters[i]+" ");
						TTCReference = parameters[i];
					}
					//System.out.println("");
				}
				if (line.startsWith("temperatureSensorChannel")){
					line = line.replaceFirst("temperatureSensorChannel", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					for (int i=0;i<parameters.length;i++){
						//System.out.print(parameters[i]+" ");
						temperatureSensorChannel = Integer.parseInt(parameters[i]);
					}
					//System.out.println("");
				}
				if (line.startsWith("TTCRSenseChannel")){
					line = line.replaceFirst("TTCRSenseChannel", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					for (int i=0;i<parameters.length;i++){
						//System.out.print(parameters[i]+" ");
						TTCRSenseChannel = Integer.parseInt(parameters[i]);
					}
					//System.out.println("");
				}
				if (line.startsWith("TTCRHeatingChannel")){
					line = line.replaceFirst("TTCRHeatingChannel", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					for (int i=0;i<parameters.length;i++){
						//System.out.print(parameters[i]+" ");
						TTCRHeatingChannel = Integer.parseInt(parameters[i]);
					}
					//System.out.println("");
				}
				if (line.startsWith("programType")){
					line = line.replaceFirst("programType", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					setProgramType(parameters[0]);
					//System.out.println("");
				}
				if (line.startsWith("programName")){
					line = line.replaceFirst("programName", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					setProgramName(parameters[0]);
					//System.out.println("");
				}
				if (line.startsWith("temperatureProfile")){
					line = line.replaceFirst("temperatureProfile", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					//System.out.print(parameters.length);
					nPointsTemperatureProfile = parameters.length;
					temperatureProfile = new int[nPointsTemperatureProfile];
					for (int i=0;i<nPointsTemperatureProfile;i++){
						temperatureProfile[i]=Integer.parseInt(parameters[i]);
					}
					//System.out.println("");
				}
				if (line.startsWith("ovenSerialPort")){
					line = line.replaceFirst("ovenSerialPort", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					ovenSerialPort = parameters[0];
				}
				if (line.startsWith("multimeterSerialPort")){
					line = line.replaceFirst("multimeterSerialPort", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					multimeterSerialPort = parameters[0];
					System.out.println("");
				}
				if (line.startsWith("ovenControllerID")){
					line = line.replaceFirst("ovenControllerID", "");
					line = line.substring(1, line.length());
					parameters = line.split(",");
					ovenControllerID = parameters[0];
					System.out.println("");
				}
				line = FileBufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTTCReference(){return TTCReference;}
	public void setTTCReference(String _TTCReference){TTCReference = _TTCReference;}

	public int[] getTemperatureProfile(){return temperatureProfile;}
	public void setTemperatureProfile(int[] _temperatureProfile){temperatureProfile = _temperatureProfile;}

	public int getNPointsTemperatureProfile(){return nPointsTemperatureProfile;}
	public void setNPointsTemperatureProfile(int _nPointsTemperatureProfile){nPointsTemperatureProfile = _nPointsTemperatureProfile;}
	
	public int getTemperatureSensorChannel(){return temperatureSensorChannel;}
	public void setTemperatureSensorChannel(int _temperatureSensorChannel){temperatureSensorChannel = _temperatureSensorChannel;}

	public int getTTCRSenseChannel(){return TTCRSenseChannel;}
	public void setTTCRSenseChannel(int _TTCRSenseChannel){TTCRSenseChannel = _TTCRSenseChannel;}

	public int getTTCRHeatingChannel(){return TTCRHeatingChannel;}
	public void setTTCRHeatingChannel(int _TTCRHeatingChannel){TTCRHeatingChannel = _TTCRHeatingChannel;}

	public void setOvenSerialPort(String _ovenSerialPort){ovenSerialPort = _ovenSerialPort;}
	public String getOvenSerialPort(){return ovenSerialPort;}
	
	public void setMultimeterSerialPort(String _multimeterSerialPort){multimeterSerialPort = _multimeterSerialPort;}
	public String getMultimeterSerialPort(){return multimeterSerialPort;}
	
	public void setOvenControllerID(String _ovenControllerID){ovenControllerID = _ovenControllerID;}
	public String getOvenControllerID(){return ovenControllerID;}
		
	public void setProgramFilePath(String _programFilePath){programFilePath = _programFilePath;}
	public String getProgramFilePath(){return programFilePath;}
	
	/*
	private int[] temperatureProfile; 
	private int nPointsTemperatureProfile;
	*/
	public String toString(){
		String res="";
		res = res + super.toString();
		res = res + "TTCReference = "+getTTCReference()+"\n";
		res = res + "programFilePath = "+getProgramFilePath()+"\n";
		res = res + "ovenSerialPort = "+getOvenSerialPort()+"\n";
		res = res + "ovenControllerID = "+getOvenControllerID()+"\n";
		res = res + "multimeterSerialPort = "+getMultimeterSerialPort()+"\n";
		res = res + "temperatureSensorChannel = "+getTemperatureSensorChannel()+"\n";
		res = res + "TTCRSenseChannel = "+getTTCRSenseChannel()+"\n";
		res = res + "TTCRHeatingChannel = "+getTTCRHeatingChannel()+"\n";
		res = res + "nPointsTemperatureProfile = "+getNPointsTemperatureProfile()+"\n";
		res = res + "temperatureProfile = ";
		for (int i=0;i<temperatureProfile.length;i++){
			res = res + temperatureProfile[i]+",";
		}
		res = res + "\n";
		res = res + "*****************************************************************"+ "\n";
		res = res + "*****************************************************************"+ "\n";
		res = res + "\n";
		
		return res;
	}
}
