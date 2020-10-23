package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DiodesCalibrationSetUpData {

	private TemperatureProfileData 						temperatureProfileData;
	private DiodesToCalibrateData 						diodesToCalibrateData;
	private temperatureStabilizationCriteriumData 		temperatureStabilizationCriteriumData;
	private TTCSetUpDescriptionData 					diodesSetUpDescriptionData;

	public DiodesCalibrationSetUpData(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public DiodesCalibrationSetUpData(
			TemperatureProfileData _temperatureProfileData,
			DiodesToCalibrateData _ttcsToCalibrateData,
			temperatureStabilizationCriteriumData _temperatureStabilizationCriteriumData,
			TTCSetUpDescriptionData _ttcSetUpDescriptionData)throws Exception
	{
		temperatureProfileData = _temperatureProfileData;
		diodesToCalibrateData = _ttcsToCalibrateData;
		temperatureStabilizationCriteriumData = _temperatureStabilizationCriteriumData;
		diodesSetUpDescriptionData = _ttcSetUpDescriptionData;
	}
	public DiodesToCalibrateData getDiodesToCalibrateData(){
		return diodesToCalibrateData;
	}
	public TemperatureProfileData getTemperatureProfileData(){
		return temperatureProfileData;
	}
	public TTCSetUpDescriptionData getDiodesCalibrationSetUpDescriptionData(){
		return this.diodesSetUpDescriptionData;
	}
	public temperatureStabilizationCriteriumData getTemperatureStabilizationCriteriumData(){
		return this.temperatureStabilizationCriteriumData;
	}
	/**
	 * Title: toXMLFile
	 * Description: Método que convierte una instancia de la classe ThermalCalibrationProgram
	 * 				en un fichero XML
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	String _name con el nombre del objecto
	 * 			String _filePath con el destino del fichero XML
	 * @return none
	 * @throws Exception
	 */
	public void toXMLFile(String _name, String _filePath)throws Exception{
		Document document = new Document();
		document.setRootElement(new org.jdom.Element(_name));
		org.jdom.Element thermalCalibrationProgramDataRootElement = document.getRootElement();

		thermalCalibrationProgramDataRootElement.addContent(diodesSetUpDescriptionData.toJDOMElement("TTCSetUpDescriptionData"));
		thermalCalibrationProgramDataRootElement.addContent(diodesToCalibrateData.convertToJDOMElement("TTCsToCalibrateData"));
		thermalCalibrationProgramDataRootElement.addContent(temperatureStabilizationCriteriumData.toJDOMElement("TemperatureStabilizationCriteriumData"));
		thermalCalibrationProgramDataRootElement.addContent(temperatureProfileData.convertToJDOMElement("TemperatureProfileData"));

		FileOutputStream file = new FileOutputStream(_filePath);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(document, file);
		file.close();
	}
	/**
	 * Title: initializeFromXMLFile
	 * Description: Método que inicializa ThermalCalibrationProgram con los valores provenientes de
	 * 				in fichero XML.
	 * Data: 	09-06-2008
	 * @author 	David Sánchez Sánchez
	 * @param 	String _filePath con el origen del fichero XML
	 * @return 	none
	 * @throws 	Exception
	 */
	public void initializeFromXMLFile(String _filePath)throws Exception{
		SAXBuilder builder = new SAXBuilder();
		FileInputStream fileInputStream = new FileInputStream(_filePath);
		Document document = builder.build(fileInputStream);
		Element thermalCalibrationProgramElementRoot = document.getRootElement();
		diodesSetUpDescriptionData = new TTCSetUpDescriptionData(thermalCalibrationProgramElementRoot.getChild("TTCSetUpDescriptionData"));
		diodesToCalibrateData = new DiodesToCalibrateData(thermalCalibrationProgramElementRoot.getChild("TTCsToCalibrateData"));
		temperatureProfileData = new TemperatureProfileData(thermalCalibrationProgramElementRoot.getChild("TemperatureProfileData"));
		temperatureStabilizationCriteriumData = new temperatureStabilizationCriteriumData(thermalCalibrationProgramElementRoot.getChild("TemperatureStabilizationCriteriumData"));
	}
	public String toString(){
		String res="";
		res = res + temperatureProfileData.toString();
		res = res + diodesToCalibrateData.toString();
		res = res + temperatureStabilizationCriteriumData.toString();
		res = res + diodesSetUpDescriptionData.toString();
		return res;
	}
	public static void main(String[] args) throws Exception{

		String programFilePath = "C:\\p.xml";
		TTCSetUpDescriptionData ttcSetUpDescriptionData= new TTCSetUpDescriptionData("P1","David","For Testing");

		DiodeToCalibrateData[] ttcToCalibrateData = new DiodeToCalibrateData[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new DiodeToCalibrateData(true,Integer.toString(i+1),i+2);
		}
		DiodesToCalibrateData ttcsToCalibrateData = new DiodesToCalibrateData(ttcToCalibrateData, 1);

		int[] temperatures = new int[101];
		for (int i=0;i<101;i++){
			temperatures[i] = i;
		}
		//TemperatureProfileData temperatureProfileData = new TemperatureProfileData("temperatures",true,temperatures);
		//temperatureStabilizationCriteriumData temperatureStabilizationCriteriumData = new temperatureStabilizationCriteriumData(0.03,120,1000,3,0,60);
		//DiodesCalibrationSetUp_Data thermalCalibrationProgramData = new DiodesCalibrationSetUp_Data(temperatureProfileData,ttcsToCalibrateData,temperatureStabilizationCriteriumData,ttcSetUpDescriptionData);
		//thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData",programFilePath);
	}
}
