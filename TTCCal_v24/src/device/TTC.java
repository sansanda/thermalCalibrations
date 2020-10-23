package device;

import org.jdom.Element;

public class TTC extends Device{

	private int TTC_Channel;

	public TTC(Element _deviceDataElement)throws Exception{
		initializeFromElement(_deviceDataElement);
	}
	public TTC(String _deviceName, String _deviceReference,String _deviceDescription,boolean _selectedForTest,int _TTC_Channel)throws Exception{
		super(_deviceName,_deviceReference,_deviceDescription,_selectedForTest);
		TTC_Channel = _TTC_Channel;
	}

	public int getTTC_Channel(){return TTC_Channel;}
	public void setTTC_Channel(int _TTC_Channel) {TTC_Channel = _TTC_Channel;}
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
			deviceDataElement.setAttribute("TTCName",getDeviceName());
			deviceDataElement.setAttribute("TTCReference",getDeviceReference());
			deviceDataElement.setAttribute("TTCDescription",getDeviceDescription());
			deviceDataElement.setAttribute("isSelectedForTest",Boolean.toString(isSelected()));
			deviceDataElement.setAttribute("TTC_Channel",Integer.toString(getTTC_Channel()));
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
		setDeviceName(_deviceDataElement.getAttributeValue("TTCName"));
		setDeviceReference(_deviceDataElement.getAttributeValue("TTCReference"));
		setDeviceDescription(_deviceDataElement.getAttributeValue("TTCDescription"));
		setSelectedForTest(Boolean.valueOf(_deviceDataElement.getAttributeValue("isSelectedForTest")));
		setTTC_Channel(Integer.parseInt(_deviceDataElement.getAttributeValue("TTC_Channel")));
	}
	public String toString(){
		String res="";
		res = res + super.toString();
		res=res+"TTC Channel = "+getTTC_Channel()+"\n";
		return res;
	}
	/**
	 * For Test
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TTC ttc = new TTC("ttc1","ttc1_d1","ttc1 with b.colombo diode",true,1);
			System.out.println(ttc.toString());
			Element ttcElement = ttc.convertToJDOMElement("TTC1");
			TTC ttc2 = new TTC(ttcElement);
			System.out.println(ttc2.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
