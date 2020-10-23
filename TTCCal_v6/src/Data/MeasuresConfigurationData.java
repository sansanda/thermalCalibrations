package Data;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class MeasuresConfigurationData {
	private TemperatureSensorData temperatureSensorData;
	private TTCData[] ttcsData;
	public MeasuresConfigurationData(Element _measuresConfigurationElement)throws Exception{
		initializeFromElement(_measuresConfigurationElement);
	}
	public MeasuresConfigurationData(TTCData[] _ttcsData,TemperatureSensorData _temperatureSensorData)throws Exception{
		ttcsData = _ttcsData;
		temperatureSensorData = _temperatureSensorData;
	}
	public TTCData[] getTTCsData(){
		return ttcsData;
	}
	public TemperatureSensorData getTemperatureSensorData(){
		return temperatureSensorData;
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
			org.jdom.Element ttcsDataElement = new org.jdom.Element("TTCsData");
			measuresConfigurationDataDataElement.addContent(temperatureSensorData.convertToJDOMElement("TemperatureSensorData"));
			for (int i=0;i<ttcsData.length;i++){
				ttcsDataElement.addContent(ttcsData[i].convertToJDOMElement("TTC"+i+"Data"));
			}
			measuresConfigurationDataDataElement.addContent(ttcsDataElement);
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
		
		temperatureSensorData= new TemperatureSensorData(_measuresConfigurationElement.getChild("TemperatureSensorData"));
		Element ttcsDataElement = _measuresConfigurationElement.getChild("TTCsData");
		List<Element> TTCsDataChildrenList = ttcsDataElement.getChildren();
		ttcsData = new TTCData[TTCsDataChildrenList.size()];
		Iterator<Element> TTCsDataChildrenListIterator = TTCsDataChildrenList.iterator();
		for (int i=0;TTCsDataChildrenListIterator.hasNext();i++){
			ttcsData[i] = new TTCData(TTCsDataChildrenListIterator.next());
		}
	}
	public String toString(){
		String res="";
		return res;
	}
}
