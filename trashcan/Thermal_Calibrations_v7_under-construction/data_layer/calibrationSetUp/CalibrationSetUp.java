package calibrationSetUp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import uoc.ei.tads.sequencies.LlistaAfSeq;
import devices.Resistance;

public class CalibrationSetUp {

	public static final int 		TEMPERATURE_VS_VOLTAGE_TYPE = 0;
	public static final int 		TEMPERATURE_VS_RESISTANCE_TYPE = 1;


	private int 												calibrationSetUp_Type;
	private CalibrationSetUp_TemperatureProfile 				calibrationSetUp_TemperatureProfile;
	private CalibrationSetUp_TemperatureStabilizationCriteria 	calibrationSetUp_TemperatureStabilizationCriteria;
	private CalibrationSetUp_DevicesToCalibrate					calibrationSetUp_DevicesToCalibrate;
	private CalibrationSetUp_Description						calibrationSetUp_Description;


	public CalibrationSetUp(String _xmlFilePath)throws Exception
	{
		initializeFromXMLFile(_xmlFilePath);
	}
	public CalibrationSetUp(
			int _calibrationSetUp_Type,
			CalibrationSetUp_TemperatureProfile _calibrationSetUp_TemperatureProfile,
			CalibrationSetUp_DevicesToCalibrate _calibrationSetUp_DevicesToCalibrate,
			CalibrationSetUp_TemperatureStabilizationCriteria _calibrationSetUp_TemperatureStabilizationCriteria,
			CalibrationSetUp_Description _calibrationSetUp_Description) throws Exception {

		validateType(_calibrationSetUp_Type);
		calibrationSetUp_Type=_calibrationSetUp_Type;
		calibrationSetUp_TemperatureProfile = _calibrationSetUp_TemperatureProfile;
		calibrationSetUp_DevicesToCalibrate = _calibrationSetUp_DevicesToCalibrate;
		calibrationSetUp_TemperatureStabilizationCriteria = _calibrationSetUp_TemperatureStabilizationCriteria;
		calibrationSetUp_Description = _calibrationSetUp_Description;
	}
	//Getters and Setters
	/**
	 * @return the calibrationSetUp_Type
	 */
	public int getType() {
		return calibrationSetUp_Type;
	}
	/**
	 * @return the calibrationSetUp_TemperatureProfile
	 */
	public CalibrationSetUp_TemperatureProfile getTemperatureProfile() {
		return calibrationSetUp_TemperatureProfile;
	}
	/**
	 * @return the calibrationSetUp_TemperatureStabilizationCriteria
	 */
	public CalibrationSetUp_TemperatureStabilizationCriteria getTemperatureStabilizationCriteria() {
		return calibrationSetUp_TemperatureStabilizationCriteria;
	}
	/**
	 * @return the calibrationSetUp_DevicesToCalibrate
	 */
	public CalibrationSetUp_DevicesToCalibrate getDevicesToCalibrate() {
		return calibrationSetUp_DevicesToCalibrate;
	}
	/**
	 * @return the calibrationSetUp_Description
	 */
	public CalibrationSetUp_Description getDescription() {
		return calibrationSetUp_Description;
	}
	/**
	 * @param calibrationSetUp_Type the calibrationSetUp_Type to set
	 */
	public void setType(int calibrationSetUp_Type) {
		this.calibrationSetUp_Type = calibrationSetUp_Type;
	}
	/**
	 * @param calibrationSetUp_TemperatureProfile the calibrationSetUp_TemperatureProfile to set
	 */
	public void setTemperatureProfile(
			CalibrationSetUp_TemperatureProfile calibrationSetUp_TemperatureProfile) {
		this.calibrationSetUp_TemperatureProfile = calibrationSetUp_TemperatureProfile;
	}
	/**
	 * @param calibrationSetUp_TemperatureStabilizationCriteria the calibrationSetUp_TemperatureStabilizationCriteria to set
	 */
	public void setTemperatureStabilizationCriteria(
			CalibrationSetUp_TemperatureStabilizationCriteria calibrationSetUp_TemperatureStabilizationCriteria) {
		this.calibrationSetUp_TemperatureStabilizationCriteria = calibrationSetUp_TemperatureStabilizationCriteria;
	}
	/**
	 * @param calibrationSetUp_DevicesToCalibrate the calibrationSetUp_DevicesToCalibrate to set
	 */
	public void setDevicesToCalibrate(
			CalibrationSetUp_DevicesToCalibrate calibrationSetUp_DevicesToCalibrate) {
		this.calibrationSetUp_DevicesToCalibrate = calibrationSetUp_DevicesToCalibrate;
	}
	/**
	 * @param calibrationSetUp_Description the calibrationSetUp_Description to set
	 */
	public void setDescription(
			CalibrationSetUp_Description calibrationSetUp_Description) {
		this.calibrationSetUp_Description = calibrationSetUp_Description;
	}
	//XML
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
		org.jdom.Element calibrationSetUp_ElementRoot = document.getRootElement();

		calibrationSetUp_ElementRoot.setAttribute("CalibrationSetUp_Type", String.valueOf(getType()));
		calibrationSetUp_ElementRoot.addContent(getDescription().toJDOMElement("CalibrationSetUp_Description"));
		calibrationSetUp_ElementRoot.addContent(getDevicesToCalibrate().toJDOMElement("CalibrationSetUp_DevicesToCalibrate"));
		calibrationSetUp_ElementRoot.addContent(getTemperatureProfile().toJDOMElement("CalibrationSetUp_TemperatureProfile"));
		calibrationSetUp_ElementRoot.addContent(getTemperatureStabilizationCriteria().toJDOMElement("CalibrationSetUp_TemperatureStabilizationCriteria"));

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
		Element calibrationSetUp_ElementRoot = document.getRootElement();
		setType(Integer.valueOf(calibrationSetUp_ElementRoot.getAttributeValue("CalibrationSetUp_Type")));
		setDescription(new CalibrationSetUp_Description(calibrationSetUp_ElementRoot.getChild("CalibrationSetUp_Description")));
		setDevicesToCalibrate(new CalibrationSetUp_DevicesToCalibrate(calibrationSetUp_ElementRoot.getChild("CalibrationSetUp_DevicesToCalibrate")));
		setTemperatureProfile(new CalibrationSetUp_TemperatureProfile(calibrationSetUp_ElementRoot.getChild("CalibrationSetUp_TemperatureProfile")));
		setTemperatureStabilizationCriteria(new CalibrationSetUp_TemperatureStabilizationCriteria(calibrationSetUp_ElementRoot.getChild("CalibrationSetUp_TemperatureStabilizationCriteria")));
	}
	//Other Methods
	/**
	 *
	 * @param _type
	 * @throws Exception
	 */
	private void validateType(int _type)throws Exception {
		if (	_type==TEMPERATURE_VS_RESISTANCE_TYPE ||
				_type==TEMPERATURE_VS_VOLTAGE_TYPE){
			//Nothing todo
		}else{
			throw new Exception("Invalid CalibrationSetUp-Type Exception");
		}
	}
	public String toString(){
		String res="";
		res = res + "**********CalibrationSetUp*********" + "\n";
		res = res + "CALIBRATION SET UP TYPE = "+ getType() + "\n";
		res = res + calibrationSetUp_TemperatureProfile.toString();
		res = res + calibrationSetUp_TemperatureStabilizationCriteria.toString();
		res = res + calibrationSetUp_DevicesToCalibrate.toString();
		res = res + calibrationSetUp_Description.toString();
		return res;
	}
	public static void main(String[] args) throws Exception{

		String calibrationSetUp_FilePath = "C:\\calibrationSetUp.xml";

		int calibrationSetUp_Type = CalibrationSetUp.TEMPERATURE_VS_RESISTANCE_TYPE;

		CalibrationSetUp_Description CalibrationSetUp_Description= new CalibrationSetUp_Description("P1","David","For Testing");

		int[] temperatures = new int[101];
		for (int i=0;i<101;i++){
			temperatures[i] = i;
		}
		CalibrationSetUp_TemperatureProfile calibrationSetUp_TemperatureProfile = new CalibrationSetUp_TemperatureProfile("temperatures",temperatures);

		CalibrationSetUp_TemperatureStabilizationCriteria calibrationSetUp_TemperatureStabilizationCriteria = new CalibrationSetUp_TemperatureStabilizationCriteria(5,0.0,0.03,120,1000,3,0,60);


		LlistaAfSeq devicesToCalibrate = new LlistaAfSeq();
		for (int i=0;i<7;i++){
			devicesToCalibrate.afegeix(new Resistance(Integer.toString(i),i));
		}
		CalibrationSetUp_DevicesToCalibrate calibrationSetUp_DevicesToCalibrate = new CalibrationSetUp_DevicesToCalibrate(devicesToCalibrate);


		CalibrationSetUp calibrationSetUp = new CalibrationSetUp(
				calibrationSetUp_Type,
				calibrationSetUp_TemperatureProfile,
				calibrationSetUp_DevicesToCalibrate,
				calibrationSetUp_TemperatureStabilizationCriteria,
				CalibrationSetUp_Description);
		calibrationSetUp.toXMLFile("ThermalCalibrationProgramData",calibrationSetUp_FilePath);

		CalibrationSetUp calibrationSetUp2 = new CalibrationSetUp(calibrationSetUp_FilePath);
		System.out.println(calibrationSetUp2.toString());
	}
}
