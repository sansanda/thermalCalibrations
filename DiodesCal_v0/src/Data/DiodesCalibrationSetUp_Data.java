package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class DiodesCalibrationSetUp_Data {

	private TemperatureProfile_Data 					temperatureProfileData;
	private DiodesToCalibrate_Data 					ttcsToCalibrateData;
	private TemperatureStabilizationCriterium_Data 	temperatureStabilizationCriteriumData;
	private DiodesCalibrationSetUpDescription_Data 				ttcSetUpDescriptionData;

	public DiodesCalibrationSetUp_Data(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public DiodesCalibrationSetUp_Data(
			TemperatureProfile_Data _temperatureProfileData,
			DiodesToCalibrate_Data _ttcsToCalibrateData,
			TemperatureStabilizationCriterium_Data _temperatureStabilizationCriteriumData,
			DiodesCalibrationSetUpDescription_Data _ttcSetUpDescriptionData)throws Exception
	{
		temperatureProfileData = _temperatureProfileData;
		ttcsToCalibrateData = _ttcsToCalibrateData;
		temperatureStabilizationCriteriumData = _temperatureStabilizationCriteriumData;
		ttcSetUpDescriptionData = _ttcSetUpDescriptionData;
	}
	public DiodesToCalibrate_Data getDiodesToCalibrateData(){
		return ttcsToCalibrateData;
	}
	public TemperatureProfile_Data getTemperatureProfileData(){
		return temperatureProfileData;
	}
	public DiodesCalibrationSetUpDescription_Data getDiodesCalibrationSetUpDescriptionData(){
		return this.ttcSetUpDescriptionData;
	}
	public TemperatureStabilizationCriterium_Data getTemperatureStabilizationCriteriumData(){
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
		ttcSetUpDescriptionData = new DiodesCalibrationSetUpDescription_Data(thermalCalibrationProgramElementRoot.getChild("TTCSetUpDescriptionData"));
		ttcsToCalibrateData = new DiodesToCalibrate_Data(thermalCalibrationProgramElementRoot.getChild("TTCsToCalibrateData"));
		temperatureProfileData = new TemperatureProfile_Data(thermalCalibrationProgramElementRoot.getChild("TemperatureProfileData"));
		temperatureStabilizationCriteriumData = new TemperatureStabilizationCriterium_Data(thermalCalibrationProgramElementRoot.getChild("TemperatureStabilizationCriteriumData"));
	}
	public String toString(){
		String res="";
		return res;
	}
	public static void main(String[] args) throws Exception{

		String programFilePath = "C:\\p.xml";
		DiodesCalibrationSetUpDescription_Data ttcSetUpDescriptionData= new DiodesCalibrationSetUpDescription_Data("P1","David","For Testing");

		DiodeToCalibrate_Data[] ttcToCalibrateData = new DiodeToCalibrate_Data[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new DiodeToCalibrate_Data(true,Integer.toString(i+1),i+2);
		}
		DiodesToCalibrate_Data ttcsToCalibrateData = new DiodesToCalibrate_Data(ttcToCalibrateData);

		int[] temperatures = new int[101];
		for (int i=0;i<101;i++){
			temperatures[i] = i;
		}
		TemperatureProfile_Data temperatureProfileData = new TemperatureProfile_Data("temperatures",true,temperatures);
		TemperatureStabilizationCriterium_Data temperatureStabilizationCriteriumData = new TemperatureStabilizationCriterium_Data(0.03,120,1000,3,0,60);
		DiodesCalibrationSetUp_Data thermalCalibrationProgramData = new DiodesCalibrationSetUp_Data(temperatureProfileData,ttcsToCalibrateData,temperatureStabilizationCriteriumData,ttcSetUpDescriptionData);
		thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData",programFilePath);
	}
}
