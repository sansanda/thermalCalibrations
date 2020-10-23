package Data;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class MeasuresConfigurationData {
	private int nDevicesToMeasure;
	private DeviceToMeasureData[] devicesToMeasureData;
	public MeasuresConfigurationData(Element _measuresConfigurationElement)throws Exception{
		initializeFromElement(_measuresConfigurationElement);
	}
	public MeasuresConfigurationData(DeviceToMeasureData[] _devicesToMeasureData)throws Exception{
		devicesToMeasureData = _devicesToMeasureData;
		nDevicesToMeasure = getNDevicesToMeasure(_devicesToMeasureData);
	}
	public DeviceToMeasureData[] getDevicesToMeasureData(){
		return devicesToMeasureData;
	}
	public int getNDevicesToMeasure(){
		return nDevicesToMeasure;
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
		devicesToMeasureData = new DeviceToMeasureData[DevicesToMeasureDataChildrenList.size()];
		Iterator<Element> DevicesToMeasureDataChildrenListIterator = DevicesToMeasureDataChildrenList.iterator();
		for (int i=0;DevicesToMeasureDataChildrenListIterator.hasNext();i++){
			devicesToMeasureData[i] = new DeviceToMeasureData(DevicesToMeasureDataChildrenListIterator.next());
		}
		nDevicesToMeasure = getNDevicesToMeasure(devicesToMeasureData);
	}
	private int getNDevicesToMeasure(DeviceToMeasureData[] _devicesToMeasureData){
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
}
