package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class TTCSetUpData {

	private TemperatureProfileData 					temperatureProfileData;
	private TTCsToCalibrateData 					ttcsToCalibrateData;
	private temperatureStabilizationCriteriumData 	temperatureStabilizationCriteriumData;
	private TTCSetUpDescriptionData 				ttcSetUpDescriptionData;

	public TTCSetUpData(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public TTCSetUpData(
			TemperatureProfileData _temperatureProfileData,
			TTCsToCalibrateData _ttcsToCalibrateData,
			temperatureStabilizationCriteriumData _temperatureStabilizationCriteriumData,
			TTCSetUpDescriptionData _ttcSetUpDescriptionData)throws Exception
	{
		temperatureProfileData = _temperatureProfileData;
		ttcsToCalibrateData = _ttcsToCalibrateData;
		temperatureStabilizationCriteriumData = _temperatureStabilizationCriteriumData;
		ttcSetUpDescriptionData = _ttcSetUpDescriptionData;
	}
	public TTCsToCalibrateData getTTCsToCalibrateData(){
		return ttcsToCalibrateData;
	}
	public TemperatureProfileData getTemperatureProfileData(){
		return temperatureProfileData;
	}
	public TTCSetUpDescriptionData getTTCSetUpDescriptionData(){
		return this.ttcSetUpDescriptionData;
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

		thermalCalibrationProgramDataRootElement.addContent(ttcSetUpDescriptionData.toJDOMElement("TTCSetUpDescriptionData"));
		thermalCalibrationProgramDataRootElement.addContent(ttcsToCalibrateData.convertToJDOMElement("TTCsToCalibrateData"));
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
		ttcSetUpDescriptionData = new TTCSetUpDescriptionData(thermalCalibrationProgramElementRoot.getChild("TTCSetUpDescriptionData"));
		ttcsToCalibrateData = new TTCsToCalibrateData(thermalCalibrationProgramElementRoot.getChild("TTCsToCalibrateData"));
		temperatureProfileData = new TemperatureProfileData(thermalCalibrationProgramElementRoot.getChild("TemperatureProfileData"));
		temperatureStabilizationCriteriumData = new temperatureStabilizationCriteriumData(thermalCalibrationProgramElementRoot.getChild("TemperatureStabilizationCriteriumData"));
	}
	public String toString(){
		String res="";
		return res;
	}
	public static void main(String[] args) throws Exception{

		String programFilePath = "C:\\p.xml";
		TTCSetUpDescriptionData ttcSetUpDescriptionData= new TTCSetUpDescriptionData("P1","David","For Testing");

		TTCToCalibrateData[] ttcToCalibrateData = new TTCToCalibrateData[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new TTCToCalibrateData(true,Integer.toString(i+1),i+2);
		}
		TTCsToCalibrateData ttcsToCalibrateData = new TTCsToCalibrateData(ttcToCalibrateData);

		int[] temperatures = new int[101];
		for (int i=0;i<101;i++){
			temperatures[i] = i;
		}
		TemperatureProfileData temperatureProfileData = new TemperatureProfileData("temperatures",temperatures);
		temperatureStabilizationCriteriumData temperatureStabilizationCriteriumData = new temperatureStabilizationCriteriumData(0.0,0.03,120,1000,3,0,60);
		TTCSetUpData thermalCalibrationProgramData = new TTCSetUpData(temperatureProfileData,ttcsToCalibrateData,temperatureStabilizationCriteriumData,ttcSetUpDescriptionData);
		thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData",programFilePath);
	}
}
