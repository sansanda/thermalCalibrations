package Data;

import org.jdom.Element;

public class DeviceToMeasureData {
	private boolean selected;
	private String deviceReference;
	private int deviceChannel;
	private String deviceType;

	public DeviceToMeasureData(Element _deviceDataElement)throws Exception{
		initializeFromElement(_deviceDataElement);
	}
	public DeviceToMeasureData(boolean _selected,String _deviceReference,int _deviceChannel,String _deviceType)throws Exception{
		selected = _selected;
		deviceReference = _deviceReference;
		deviceChannel = _deviceChannel;
		deviceType = _deviceType;
	}
	public boolean isSelected(){return selected;}
	public String getDeviceReference(){return deviceReference;}
	public int getDeviceChannel(){return deviceChannel;}
	public String getDeviceType(){return deviceType;}
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
			org.jdom.Element deviceDataElement = new org.jdom.Element(_name);
			deviceDataElement.setAttribute("Selected",Boolean.toString(isSelected()));
			deviceDataElement.setAttribute("DeviceReference",getDeviceReference());
			deviceDataElement.setAttribute("DeviceChannel",Integer.toString(getDeviceChannel()));
			deviceDataElement.setAttribute("DeviceType",getDeviceType());
			return deviceDataElement;
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
	public void initializeFromElement(Element _deviceDataElement) throws Exception{
		selected = Boolean.valueOf(_deviceDataElement.getAttributeValue("Selected"));
		deviceReference = _deviceDataElement.getAttributeValue("DeviceReference");
		deviceChannel  = Integer.parseInt(_deviceDataElement.getAttributeValue("DeviceChannel"));
		deviceType  = _deviceDataElement.getAttributeValue("DeviceType");
	}
	public String toString(){
		String res="";
		res=res+"Selected = "+isSelected()+"\n";
		res=res+"Device Reference = "+getDeviceReference()+"\n";
		res=res+"Device Channel = "+getDeviceChannel()+"\n";
		res=res+"Device Type = "+getDeviceType()+"\n";
		return res;
	}
}
