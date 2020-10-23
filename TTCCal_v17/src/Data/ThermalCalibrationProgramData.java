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


public class ThermalCalibrationProgramData {

	private TemperatureProfileData temperatureProfileData;
	private MeasuresConfigurationData measuresConfigurationData;
	private AdvanceProgramData advanceProgramData;
	private GeneralProgramData generalProgramData;

	public ThermalCalibrationProgramData(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public ThermalCalibrationProgramData(
			TemperatureProfileData _temperatureProfileData,
			MeasuresConfigurationData _measuresConfigurationData,
			AdvanceProgramData _advanceProgramData,
			GeneralProgramData _generalProgramData)throws Exception
	{
		temperatureProfileData = _temperatureProfileData;
		measuresConfigurationData = _measuresConfigurationData;
		advanceProgramData = _advanceProgramData;
		generalProgramData = _generalProgramData;
	}
	public MeasuresConfigurationData getMeasuresConfigurationData(){
		return measuresConfigurationData;
	}
	public TemperatureProfileData getTemperatureProfileData(){
		return temperatureProfileData;
	}
	public GeneralProgramData getGeneralProgramData(){
		return this.generalProgramData;
	}
	public AdvanceProgramData getAdvanceProgramData(){
		return this.advanceProgramData;
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
	public void toXMLFile(String _name)throws Exception{
		Document document = new Document();
		document.setRootElement(new org.jdom.Element(_name));
		org.jdom.Element thermalCalibrationProgramDataRootElement = document.getRootElement();

		thermalCalibrationProgramDataRootElement.addContent(generalProgramData.toJDOMElement("GeneralProgramData"));
		thermalCalibrationProgramDataRootElement.addContent(measuresConfigurationData.convertToJDOMElement("MeasuresConfigurationData"));
		thermalCalibrationProgramDataRootElement.addContent(advanceProgramData.toJDOMElement("AdvanceProgramData"));
		thermalCalibrationProgramDataRootElement.addContent(temperatureProfileData.convertToJDOMElement("TemperatureProfileData"));

		FileOutputStream file = new FileOutputStream(generalProgramData.getProgramFilePath());
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
		generalProgramData = new GeneralProgramData(thermalCalibrationProgramElementRoot.getChild("GeneralProgramData"));
		measuresConfigurationData = new MeasuresConfigurationData(thermalCalibrationProgramElementRoot.getChild("MeasuresConfigurationData"));
		temperatureProfileData = new TemperatureProfileData(thermalCalibrationProgramElementRoot.getChild("TemperatureProfileData"));
		advanceProgramData = new AdvanceProgramData(thermalCalibrationProgramElementRoot.getChild("AdvanceProgramData"));
	}
	public String toString(){
		String res="";
		return res;
	}
	public static void main(String[] args) throws Exception{

		String programFilePath = "C:\\p.xml";
		GeneralProgramData generalProgramData = new GeneralProgramData("P1","TTCCalibration",programFilePath,"David","For Testing");

		DeviceToMeasureData[] devicesToMeasureData = new DeviceToMeasureData[7];
		for (int i=0;i<7;i++){
			devicesToMeasureData[i] = new DeviceToMeasureData(true,Integer.toString(i+1),i+2,"Resistor");
		}
		MeasuresConfigurationData measuresConfigurationData = new MeasuresConfigurationData(devicesToMeasureData);

		int[] temperatures = new int[101];
		for (int i=0;i<101;i++){
			temperatures[i] = i;
		}
		TemperatureProfileData temperatureProfileData = new TemperatureProfileData("temperatures",temperatures);

		AdvanceProgramData advanceProgramData = new AdvanceProgramData(0.03,120,1000,3,0,60);

		ThermalCalibrationProgramData thermalCalibrationProgramData = new ThermalCalibrationProgramData(temperatureProfileData,measuresConfigurationData,advanceProgramData,generalProgramData);
		thermalCalibrationProgramData.toXMLFile("ThermalCalibrationProgramData");
	}
}
