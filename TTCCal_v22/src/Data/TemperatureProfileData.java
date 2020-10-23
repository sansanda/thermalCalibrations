package Data;

import org.jdom.Element;

public class TemperatureProfileData {
	private String temperatureProfileName;
	private boolean waitForGoodRoomTemperatureStabilization;
	private int[] temperatures;

	public TemperatureProfileData(Element _temperatureProfileDataElement)throws Exception{
		initializeFromElement(_temperatureProfileDataElement);
	}
	public TemperatureProfileData(String _temperatureProfileName, boolean _waitForGoodRoomTemperatureStabilization, int[] _temperatures){
		temperatureProfileName = _temperatureProfileName;
		waitForGoodRoomTemperatureStabilization = _waitForGoodRoomTemperatureStabilization;
		temperatures = _temperatures;
	}
	public String getTemperatureProfileName(){
		return temperatureProfileName;
	}
	public int[] getTemperatures(){
		return temperatures;
	}
	public boolean isWaitForGoodRoomTemperatureStabilization() {
		return waitForGoodRoomTemperatureStabilization;
	}
	public void setWaitForGoodRoomTemperatureStabilization(boolean _takeMeasureAtRoomTemperature) {
		waitForGoodRoomTemperatureStabilization = _takeMeasureAtRoomTemperature;
	}
	public String getTemperaturesString(){
		String res="";
		for (int i=0;i<temperatures.length;i++){
			res = res + Integer.toString(temperatures[i])+",";
		}
		return res;
	}
	/**
	 * Title: convertTemperatureProfileToJDOMElement
	 * Description: Método que convierte una instancia de la classe TemperatureProfile
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	TemperatureProfileData _temperatureProfile con la instancia de classe TemperatureProfile
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element temperatureProfileElement = new org.jdom.Element(_name);
			temperatureProfileElement.setAttribute("TemperatureProfileName",getTemperatureProfileName());
			temperatureProfileElement.setAttribute("WaitForGoodRoomTemperatureStabilization",Boolean.toString(isWaitForGoodRoomTemperatureStabilization()));
			temperatureProfileElement.setAttribute("Temperatures",getTemperaturesString());
			return temperatureProfileElement;
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
	public void initializeFromElement(Element _temperatureProfileDataElement) throws Exception{
		temperatureProfileName = _temperatureProfileDataElement.getAttributeValue("TemperatureProfileName");
		waitForGoodRoomTemperatureStabilization = Boolean.valueOf(_temperatureProfileDataElement.getAttributeValue("WaitForGoodRoomTemperatureStabilization"));
		String temperaturesString = _temperatureProfileDataElement.getAttributeValue("Temperatures");
		String[] temperaturesStringRaw;
		temperaturesStringRaw = temperaturesString.split(",");
		temperatures = new int[temperaturesStringRaw.length];
		//System.out.print(parameters.length);
		for (int i=0;i<temperaturesStringRaw.length;i++){
			temperatures[i]=Integer.parseInt(temperaturesStringRaw[i]);
		}
	}
	public String toString(){
		String res="";
		return res;
	}
}
