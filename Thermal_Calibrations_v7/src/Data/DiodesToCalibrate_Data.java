package Data;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class DiodesToCalibrate_Data {
	private int nDevicesToMeasure;
	private DiodeToCalibrate_Data[] devicesToMeasureData;
	private double devicesCurrentInMilliAmps;


	public DiodesToCalibrate_Data(Element _measuresConfigurationElement)throws Exception{
		initializeFromElement(_measuresConfigurationElement);
	}
	public DiodesToCalibrate_Data(DiodeToCalibrate_Data[] _devicesToMeasureData, double _devicesCurrent)throws Exception{
		devicesToMeasureData = _devicesToMeasureData;
		nDevicesToMeasure = getNDevicesToMeasure(_devicesToMeasureData);
		devicesCurrentInMilliAmps = _devicesCurrent;
	}
	public DiodeToCalibrate_Data[] getDiodesToMeasureData(){
		return devicesToMeasureData;
	}
	public int getNDevicesToMeasure(){
		return nDevicesToMeasure;
	}
	public double getDevicesCurrentInMilliAmps() {
		return devicesCurrentInMilliAmps;
	}
	public void setDevicesCurrentInMilliAmps(double _devicesCurrent) {
		devicesCurrentInMilliAmps = _devicesCurrent;
	}
	/**
	 * Title: convertMeasuresConfigurationDataToJDOMElement
	 * Description: M�todo que convierte una instancia de la classe TMeasuresConfiguration
	 * 				en un objeto Element de la librer�a JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param 	String _name con el nombre del elemento
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element measuresConfigurationDataDataElement = new org.jdom.Element(_name);
			org.jdom.Element devicesToMeasureDataElement = new org.jdom.Element("DevicesToMeasureData");
			for (int i=0;i<devicesToMeasureData.length;i++){
				devicesToMeasureDataElement.addContent(devicesToMeasureData[i].convertToJDOMElement("Device"+i+"Data"));
			}
			measuresConfigurationDataDataElement.addContent(devicesToMeasureDataElement);
			measuresConfigurationDataDataElement.setAttribute("DevicesCurrentInMilliAmps", Double.toString(getDevicesCurrentInMilliAmps()));
			return measuresConfigurationDataDataElement;
	}
	/**
	 * Title: initializeFromElement
	 * Description: M�todo que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _measuresConfigurationElement) throws Exception{
		Element devicesToMeasureDataElement = _measuresConfigurationElement.getChild("DevicesToMeasureData");
		List<Element> DevicesToMeasureDataChildrenList = devicesToMeasureDataElement.getChildren();
		devicesToMeasureData = new DiodeToCalibrate_Data[DevicesToMeasureDataChildrenList.size()];
		Iterator<Element> DevicesToMeasureDataChildrenListIterator = DevicesToMeasureDataChildrenList.iterator();
		for (int i=0;DevicesToMeasureDataChildrenListIterator.hasNext();i++){
			devicesToMeasureData[i] = new DiodeToCalibrate_Data(DevicesToMeasureDataChildrenListIterator.next());
		}
		nDevicesToMeasure = getNDevicesToMeasure(devicesToMeasureData);
		devicesCurrentInMilliAmps = Double.parseDouble(_measuresConfigurationElement.getAttributeValue("DevicesCurrentInMilliAmps"));
	}
	private int getNDevicesToMeasure(DiodeToCalibrate_Data[] _devicesToMeasureData){
		int devicesToMeasure = 0;
		for (int i=0;i<_devicesToMeasureData.length;i++){
			if (_devicesToMeasureData[i].isSelected())devicesToMeasure++;
		}
		return devicesToMeasure;
	}
	public String toString(){
		String res="";
		return res;
	}
	public static void main(String[] args) throws Exception{

		String programFilePath = "C:\\p.xml";
		DiodeToCalibrate_Data[] ttcToCalibrateData = new DiodeToCalibrate_Data[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new DiodeToCalibrate_Data(true,Integer.toString(i+1),i+2);
		}
		DiodesToCalibrate_Data ttcsToCalibrateData = new DiodesToCalibrate_Data(ttcToCalibrateData, 1.0);
	}
}
