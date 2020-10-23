package Data;

import org.jdom.Element;

public class TemperatureSensorData {
	private String type;
	private int channel;
	public TemperatureSensorData(Element _temperatureSensorDataDataElement)throws Exception{
		this.initializeFromElement(_temperatureSensorDataDataElement);
	}
	public TemperatureSensorData(String _type, int _channel)throws Exception{
		type = _type; 
		channel = _channel;
	}
	public String getType(){
		return type;
	}
	public int getChannel(){
		return channel;
	}
	/**
	 * Title: convertTemperatureSensorToJDOMElement
	 * Description: M�todo que convierte una instancia de la classe TemperatureSensor
	 * 				en un objeto Element de la librer�a JDOM
	 * Data: 09-06-2008 
	 * @author David S�nchez S�nchez
	 * @param 	TemperatureSensorData _temperatureSensor con la instancia de classe TemperatureSensor
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element temperatureSensorDataElement = new org.jdom.Element(_name);
			temperatureSensorDataElement.setAttribute("Type",getType());
			temperatureSensorDataElement.setAttribute("Channel",Integer.toString(getChannel()));
			return temperatureSensorDataElement;
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
	public void initializeFromElement(Element _temperatureSensorDataDataElement) throws Exception{
		type = _temperatureSensorDataDataElement.getAttributeValue("Type");
		channel = Integer.parseInt(_temperatureSensorDataDataElement.getAttributeValue("Channel"));
	}
	public String toString(){
		String res="";
		return res;
	}
}
