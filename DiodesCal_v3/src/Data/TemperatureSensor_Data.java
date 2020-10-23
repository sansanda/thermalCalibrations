package Data;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class TemperatureSensor_Data {
	private String type;
	private int channel;
	public TemperatureSensor_Data(Element _temperatureSensorDataDataElement)throws Exception{
		this.initializeFromElement(_temperatureSensorDataDataElement);
	}
	public TemperatureSensor_Data(String _type, int _channel)throws Exception{
		type = _type;
		channel = _channel;
	}
	public TemperatureSensor_Data(String _filePath)throws Exception{
		this.initializeFromXMLFile(_filePath);
	}

	public String getType(){
		return type;
	}
	public int getChannel(){
		return channel;
	}
	/**
	 * Title: convertTemperatureSensorToJDOMElement
	 * Description: Método que convierte una instancia de la classe TemperatureSensor
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	TemperatureSensor_Data _temperatureSensor con la instancia de classe TemperatureSensor
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
	 * Description: Método que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _temperatureSensorDataDataElement) throws Exception{
		type = _temperatureSensorDataDataElement.getAttributeValue("Type");
		channel = Integer.parseInt(_temperatureSensorDataDataElement.getAttributeValue("Channel"));
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
		res = res + "Temperature Sensor Type =  \n" + this.type+ "\n";
		res = res + "Temperature Sensor Channel =  \n" + this.channel+ "\n";
		return res;
	}
	public static void main(String[] args) throws Exception{
		String temperatureSensorDataFilePath = "C:\\ts.xml";
		TemperatureSensor_Data temperatureSensorData = new TemperatureSensor_Data("PT100", 4);
		temperatureSensorData.toXMLFile("InstrumentsData",temperatureSensorDataFilePath);
		TemperatureSensor_Data temperatureSensorData2 = new TemperatureSensor_Data(temperatureSensorDataFilePath);
		System.out.println(temperatureSensorData2.toString());
	}
}
