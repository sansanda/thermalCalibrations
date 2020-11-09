package profiles;

import org.jdom.Element;

public class InstrumentData {
	private String model;
	private String manufacturer;
	private int controllerID;
	private String comPort;
	
	public InstrumentData(Element _instrumentDataElement)throws Exception{
		initializeFromElement(_instrumentDataElement);
	}
	public InstrumentData(String _model,String _manufacturer,int _controllerID,String _comPort)throws Exception{
		model = _model;
		manufacturer = _manufacturer;
		controllerID = _controllerID;
		comPort = _comPort;
	}
	public String getModel(){
		return model;
	}
	public String getManufacturer(){
		return manufacturer;
	}
	public int getControllerID(){
		return controllerID;
	}
	public String getComPort(){
		return comPort;
	}
	/**
	 * Title: convertInstrumentDataToJDOMElement
	 * Description: Método que convierte una instancia de la classe InstrumentsData
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008 
	 * @author David Sánchez Sánchez
	 * @param 	String _name con el nombre del elemento
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element instrumentDataElement = new org.jdom.Element(_name);
			instrumentDataElement.setAttribute("Model",getModel());
			instrumentDataElement.setAttribute("Manufacturer",getManufacturer());
			instrumentDataElement.setAttribute("ControllerID",Integer.toString(getControllerID()));
			instrumentDataElement.setAttribute("ComPort",getComPort());
			return instrumentDataElement;
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
	public void initializeFromElement(Element _instrumentDataElement) throws Exception{
		model = _instrumentDataElement.getAttributeValue("Model");
		manufacturer = _instrumentDataElement.getAttributeValue("Manufacturer");
		controllerID = Integer.parseInt(_instrumentDataElement.getAttributeValue("ControllerID"));
		comPort = _instrumentDataElement.getAttributeValue("ComPort");
	}
	public String toString(){
		String res="";
		res = res + "Model = " + model + "\n";
		res = res + "Manufacturer =" + manufacturer + "\n";
		res = res + "ControllerID = " + controllerID + "\n";
		res = res + "Comm Port = " + comPort + "\n";
		return res;
	}
}
