package Data;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class InstrumentsData {
	private InstrumentData multimeterData;
	private InstrumentData ovenData;
	
	public InstrumentsData(Element _instrumentsDataElement)throws Exception{
		initializeFromElement(_instrumentsDataElement);
	}
	public InstrumentsData(InstrumentData _multimeterData,InstrumentData _ovenData)throws Exception{
		multimeterData = _multimeterData;
		ovenData = _ovenData;
	}
	public InstrumentData getMultimeterData(){
		return multimeterData;
	}
	public InstrumentData getOvenData(){
		return ovenData;
	}
	/**
	 * Title: convertInstrumentsDataToJDOMElement
	 * Description: Método que convierte una instancia de la classe InstrumentsData
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008 
	 * @author David Sánchez Sánchez
	 * @param 	String _name con el nombre del elemento
	 * @return Element
	 * @throws Exception
	 */
	public Element convertToJDOMElement(String _name){
			org.jdom.Element instrumentsDataElement = new org.jdom.Element(_name);
			instrumentsDataElement.addContent(multimeterData.convertToJDOMElement("MultimeterData"));
			instrumentsDataElement.addContent(ovenData.convertToJDOMElement("OvenData"));
			return instrumentsDataElement;
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
	public void initializeFromElement(Element _instrumentsDataElement) throws Exception{
		multimeterData = new InstrumentData(_instrumentsDataElement.getChild("MultimeterData"));
		ovenData = new InstrumentData(_instrumentsDataElement.getChild("OvenData"));	
	}
	public String toString(){
		String res="";
		return res;
	}
}