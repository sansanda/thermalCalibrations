package instruments;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class InstrumentsData {
	private InstrumentData multimeterData;
	private InstrumentData ovenData;

	public InstrumentsData(Element _instrumentsDataElement)throws Exception{
		initializeFromElement(_instrumentsDataElement);
	}
	public InstrumentsData(String _filePath)throws Exception{
		this.initializeFromXMLFile(_filePath);
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
	 * Description: M�todo que convierte una instancia de la classe InstrumentsData
	 * 				en un objeto Element de la librer�a JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
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
	 * Description: M�todo que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _instrumentsDataElement) throws Exception{
		multimeterData = new InstrumentData(_instrumentsDataElement.getChild("MultimeterData"));
		ovenData = new InstrumentData(_instrumentsDataElement.getChild("OvenData"));
	}
	/**
	 * Title: toXMLFile
	 * Description: M�todo que convierte una instancia de la classe InstrumentsData
	 * 				en un fichero XML
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
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
	 * Description: M�todo que inicializa InstrumentsData con los valores provenientes de
	 * 				in fichero XML.
	 * Data: 	09-06-2008
	 * @author 	David S�nchez S�nchez
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
		InstrumentData _2404Data = new InstrumentData("2404","Obersal",1,"COM1");
		InstrumentData _2700Data = new InstrumentData("2700","Keithley",0,"COM2");
		InstrumentsData instrumentsData = new InstrumentsData(_2404Data, _2700Data);
		instrumentsData.toXMLFile("InstrumentsData",instrumentsDataFilePath);
		InstrumentsData instrumentsData2 = new InstrumentsData(instrumentsDataFilePath);
		System.out.println(instrumentsData2.toString());
	}
}