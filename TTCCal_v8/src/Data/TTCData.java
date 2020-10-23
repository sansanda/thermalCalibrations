package Data;

import org.jdom.Element;

public class TTCData {
	private String reference;
	private int rSenseChannel;
	private int rHeatChannel;
	
	public TTCData(Element _ttcDataElement)throws Exception{
		initializeFromElement(_ttcDataElement);
	}
	public TTCData(String _reference,int _rSenseChannel,int _rHeatChannel)throws Exception{
		reference = _reference;
		rSenseChannel = _rSenseChannel;
		rHeatChannel = _rHeatChannel;
	}
	public String getRefernce(){return reference;}
	public int getRSenseChannel(){return rSenseChannel;}
	public int getRHeatChannel(){return rHeatChannel;}
	/**
	 * Title: convertTTCDataToJDOMElement
	 * Description: Método que convierte una instancia de la classe TTCData
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008 
	 * @author David Sánchez Sánchez
	 * @param 	none
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element ttcDataElement = new org.jdom.Element(_name);
			ttcDataElement.setAttribute("Reference",getRefernce());
			ttcDataElement.setAttribute("RSenseChannel",Integer.toString(getRSenseChannel()));
			ttcDataElement.setAttribute("RHeatChannel",Integer.toString(getRHeatChannel()));
			return ttcDataElement;
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
	public void initializeFromElement(Element _ttcDataElement) throws Exception{
		reference = _ttcDataElement.getAttributeValue("Reference");
		rSenseChannel  = Integer.parseInt(_ttcDataElement.getAttributeValue("RSenseChannel"));
		rHeatChannel  = Integer.parseInt(_ttcDataElement.getAttributeValue("RHeatChannel"));
	}
	public String toString(){
		String res="";
		return res;
	}
}
