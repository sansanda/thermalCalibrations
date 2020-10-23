package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Instruments_Data {
	private Instrument_Data multimeterData;
	private Instrument_Data ovenData;

	public Instruments_Data(Element _instrumentsDataElement)throws Exception{
		initializeFromElement(_instrumentsDataElement);
	}
	public Instruments_Data(String _filePath)throws Exception{
		this.initializeFromXMLFile(_filePath);
	}
	public Instruments_Data(Instrument_Data _multimeterData,Instrument_Data _ovenData)throws Exception{
		multimeterData = _multimeterData;
		ovenData = _ovenData;
	}
	public Instrument_Data getMultimeterData(){
		return multimeterData;
	}
	public Instrument_Data getOvenData(){
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
		multimeterData = new Instrument_Data(_instrumentsDataElement.getChild("MultimeterData"));
		ovenData = new Instrument_Data(_instrumentsDataElement.getChild("OvenData"));
	}
	/**
	 * Title: toXMLFile
	 * Description: Método que convierte una instancia de la classe InstrumentsData
	 * 				en un fichero XML
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	String _name con el nombre del objecto
	 * 			String _filePath con el destino del fichero XML
	 * @return none
	 * @throws Exception
	 */
	public void toXMLFile(String _name, String _filePath)throws Exception{
		Document document = new Document();
		document.setRootElement(this.convertToJDOMElement(_name));

		FileOutputStream file = new FileOutputStream(_filePath);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(document, file);
		file.close();
	}
	/**
	 * Title: initializeFromXMLFile
	 * Description: Método que inicializa InstrumentsData con los valores provenientes de
	 * 				in fichero XML.
	 * Data: 	09-06-2008
	 * @author 	David Sánchez Sánchez
	 * @param 	String _filePath con el origen del fichero XML
	 * @return 	none
	 * @throws 	Exception
	 */
	public void initializeFromXMLFile(String _filePath)throws Exception{
		SAXBuilder builder = new SAXBuilder();
		FileInputStream fileInputStream = new FileInputStream(_filePath);
		Document document = builder.build(fileInputStream);
		Element instrumentsDataElementRoot = document.getRootElement();
		initializeFromElement(instrumentsDataElementRoot);
	}
	public String toString(){
		String res="";
		res = res + "MultimeterData. \n" + multimeterData.toString()+ "\n";
		res = res + "OvenData. \n" + ovenData.toString()+ "\n";
		return res;
	}
	public static void main(String[] args) throws Exception{
		String instrumentsDataFilePath = "C:\\a.xml";
		Instrument_Data _2404Data = new Instrument_Data("2404","Obersal",1,"COM1");
		Instrument_Data _2700Data = new Instrument_Data("2700","Keithley",0,"COM2");
		Instruments_Data instrumentsData = new Instruments_Data(_2404Data, _2700Data);
		instrumentsData.toXMLFile("InstrumentsData",instrumentsDataFilePath);
		Instruments_Data instrumentsData2 = new Instruments_Data(instrumentsDataFilePath);
		System.out.println(instrumentsData2.toString());
	}
}