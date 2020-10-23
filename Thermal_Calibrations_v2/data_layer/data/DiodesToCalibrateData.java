package data;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class DiodesToCalibrateData {
	private int nDevicesToMeasure;
	private DiodeToCalibrateData[] devicesToMeasureData;
	private double devicesCurrentInMilliAmps;


	public DiodesToCalibrateData(Element _measuresConfigurationElement)throws Exception{
		initializeFromElement(_measuresConfigurationElement);
	}
	public DiodesToCalibrateData(DiodeToCalibrateData[] _devicesToMeasureData, double _devicesCurrent)throws Exception{
		devicesToMeasureData = _devicesToMeasureData;
		nDevicesToMeasure = getNDevicesToMeasure(_devicesToMeasureData);
		devicesCurrentInMilliAmps = _devicesCurrent;
	}
	public DiodeToCalibrateData[] getDiodesToMeasureData(){
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
	 * Description: Método que convierte una instancia de la classe TMeasuresConfiguration
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
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
	 * Description: Método que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _measuresConfigurationElement) throws Exception{
		Element devicesToMeasureDataElement = _measuresConfigurationElement.getChild("DevicesToMeasureData");
		List<Element> DevicesToMeasureDataChildrenList = devicesToMeasureDataElement.getChildren();
		devicesToMeasureData = new DiodeToCalibrateData[DevicesToMeasureDataChildrenList.size()];
		Iterator<Element> DevicesToMeasureDataChildrenListIterator = DevicesToMeasureDataChildrenList.iterator();
		for (int i=0;DevicesToMeasureDataChildrenListIterator.hasNext();i++){
			devicesToMeasureData[i] = new DiodeToCalibrateData(DevicesToMeasureDataChildrenListIterator.next());
		}
		nDevicesToMeasure = getNDevicesToMeasure(devicesToMeasureData);
		devicesCurrentInMilliAmps = Double.parseDouble(_measuresConfigurationElement.getAttributeValue("DevicesCurrentInMilliAmps"));
	}
	private int getNDevicesToMeasure(DiodeToCalibrateData[] _devicesToMeasureData){
		int devicesToMeasure = 0;
		for (int i=0;i<_devicesToMeasureData.length;i++){
			if (_devicesToMeasureData[i].isSelected())devicesToMeasure++;
		}
		return devicesToMeasure;
	}
	public String toString(){
		String res="";
		res = res + "DIODES" + "\n";
		for (int i=0;i<nDevicesToMeasure;i++){
			res = res + devicesToMeasureData[i].toString() + "\n";
		}
		res = res + "DIODES CURRENT" + "\n";
		res = res + devicesCurrentInMilliAmps+ "\n";
		return res;
	}
	public static void main(String[] args) throws Exception{
		String programFilePath = "C:\\p.xml";
		DiodeToCalibrateData[] ttcToCalibrateData = new DiodeToCalibrateData[7];
		for (int i=0;i<7;i++){
			ttcToCalibrateData[i] = new DiodeToCalibrateData(true,Integer.toString(i+1),i+2);
		}
		DiodesToCalibrateData ttcsToCalibrateData = new DiodesToCalibrateData(ttcToCalibrateData, 1.0);
	}
}
