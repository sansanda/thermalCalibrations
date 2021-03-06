package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DiodesCalibrationSetUp_Data {

	private TemperatureProfileData 						temperatureProfileData;
	private DiodesToCalibrate_Data 						diodesToCalibrateData;
	private temperatureStabilizationCriteriumData 		temperatureStabilizationCriteriumData;
	private TTCSetUpDescriptionData 					diodesSetUpDescriptionData;

	public DiodesCalibrationSetUp_Data(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public DiodesCalibrationSetUp_Data(
			TemperatureProfileData _temperatureProfileData,
			DiodesToCalibrate_Data _ttcsToCalibrateData,
			temperatureStabilizationCriteriumData _temperatureStabilizationCriteriumData,
			TTCSetUpDescriptionData _ttcSetUpDescriptionData)throws Exception
	{
		temperatureProfileData = _temperatureProfileData;
		diodesToCalibrateData = _ttcsToCalibrateData;
		temperatureStabilizationCriteriumData = _temperatureStabilizationCriteriumData;
		diodesSetUpDescriptionData = _ttcSetUpDescriptionData;
	}
	public DiodesToCalibrate_Data getDiodesToCalibrateData(){
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
	 * Description: M�todo que convierte una instancia de la classe ThermalCalibrationProgram
	 * 				en un fichero XML
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
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
	 * Description: M�todo que inicializa ThermalCalibrationProgram con los valores provenientes de
	 * 				in fichero XML.
	 * Data: 	09-06-2008
	 * @author 	David S�nchez S�nchez
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
		diodesToCalibrateData = new DiodesToCalibrate_Data(thermalCalibrationProgramElementRoot.getChild("TTCsToCalibrateData"));
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

		DiodeToCalibrate_Data[] ttcToCalibrateData = new DiodeToCalibrate_Data[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new DiodeToCalibrate_Data(true,Integer.toString(i+1),i+2);
		}
		DiodesToCalibrate_Data ttcsToCalibrateData = new DiodesToCalibrate_Data(ttcToCalibrateData, 1);

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
