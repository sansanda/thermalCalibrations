package format;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import multimeters.KeithleyK2700.K2700;
import trace.Trace_Subsystem;
import trigger.Trigger_Subsystem;

public class Format_Subsystem extends InstrumentComponent implements I_Format_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(Format_Subsystem.class);
	
	public static final String ASCII_DATA_FORMAT = "ASCii";
	public static final String SREAL_DATA_FORMAT = "SREal";
	public static final String DREAL_DATA_FORMAT = "DREal";
	public static final String[] AVAILABLE_DATA_FORMATS = {ASCII_DATA_FORMAT,SREAL_DATA_FORMAT,DREAL_DATA_FORMAT};
	
	public static final String READING_DATA_ELEMENT = "READing";
	public static final String CHANNELNUMBER_DATA_ELEMENT = "CHANnel";
	public static final String UNITS_DATA_ELEMENT 	= "UNITs";
	public static final String READINGNUMBER_DATA_ELEMENT = "RNUMber";
	public static final String TIMESTAMP_DATA_ELEMENT 	= "TSTamp";
	public static final String LIMITS_DATA_ELEMENT 	= "LIMits";
	public static final String[] AVAILABLE_DATA_ELEMENTS = {READING_DATA_ELEMENT,CHANNELNUMBER_DATA_ELEMENT,UNITS_DATA_ELEMENT,READINGNUMBER_DATA_ELEMENT,TIMESTAMP_DATA_ELEMENT,LIMITS_DATA_ELEMENT};
	
	public static final String NORMAL_BINARY_BYTE_ORDER 	= "NORMal";
	public static final String SWAPPED_BINARY_BYTE_ORDER 	= "SWAPped";
	public static final String[] AVAILABLE_BINARY_BYTE_ORDERS = {NORMAL_BINARY_BYTE_ORDER,SWAPPED_BINARY_BYTE_ORDER};
	
	private static final String READINGNUMBER_TAG 	= "RDNG#";
	private static final String TIMESTAMPS_TAG 		= "SECS";
	private static final String CHANNELNUMBER_TAG 	= "INTCHAN";
	private static final String LIMITS_TAG 			= "LIMITS";
	
	//Use of regular expressions to match Strings containing substrings like:
	//-1.23E99 | 1E0 | -9.999e-999 --> Match
	//+10E0 | 2.3e5.4 --> Not Match
	//The objective is to extract +1.23456789E-03 from +1.23456789E-03VDC, for example
	//u0013 es el caracater º que puede aparecer en el caso de medidas en temperatura
	public static final Pattern SCIENTIFIC_NOTATION_PATTERN = Pattern.compile("[+-]?\\d\\.\\d+?[Ee][+-]?\\d+(\\u0013?[A-Z]+)?");
	//The objective is to match expressions like +36114.900SECS, for example
	public static final Pattern TIMESTAMP_NOTATION_PATTERN = Pattern.compile("[+]?\\d+\\.\\d+?("+TIMESTAMPS_TAG+")");
	//The objective is to match expressions like +00000RDNG#, for example
	public static final Pattern READINGNUMBER_NOTATION_PATTERN = Pattern.compile("[+]?\\d+("+READINGNUMBER_TAG+")");
	//The objective is to match expressions like 110INTCHAN, for example
	public static final Pattern CHANNEL_NUMBER_NOTATION_PATTERN = Pattern.compile("\\d+("+CHANNELNUMBER_TAG+")");
	//The objective is to match expressions like 0000LIMITS, for example
	public static final Pattern LIMITS_NOTATION_PATTERN = Pattern.compile("\\d+("+LIMITS_TAG+")");

	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private String	format_elements = "";
	private String	format_data_type = "";
	private String	format_border = "";
	
	private I_CommunicationsInterface communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Format_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);

	}

	public Format_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);

	}
	
	public Format_Subsystem(String jSONObject_filename) throws Exception 
	{
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	 
	public Format_Subsystem(JSONObject jObj) throws Exception {
		
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing Format_Subsystem from jObj ... ");
		
		JSONObject configurationObj = (JSONObject)jObj.get("Configuration");

		try {
			this.setFormatDataType((String) configurationObj.get("format_data_type"));
			this.setFormatBorder((String) configurationObj.get("format_border"));					
			this.setFormatElements((String) configurationObj.get("format_elements"));
		} catch (NullPointerException|IOException|ParseException e) {
			e.printStackTrace();
			this.setFormatDataType(ASCII_DATA_FORMAT);
			this.setFormatBorder(SWAPPED_BINARY_BYTE_ORDER);
			this.setFormatElements("\"" + READING_DATA_ELEMENT + "," + UNITS_DATA_ELEMENT + "," + TIMESTAMP_DATA_ELEMENT + "\"");
		} 
		
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	
	public static int getClassversion() {
		return classVersion;
	}
	
	public static ArrayList<Double> getReading_Element(String reading, String desiredElement) throws Exception
	{
		
		if (desiredElement==null || reading==null) throw new NullPointerException("Neither Param desiredElement or reading cannot be null");
		
		ArrayList<Double> elementsData = new ArrayList<Double>();
		
		String[] readingElements;
		Pattern pattern;
		
		switch (desiredElement) {
		
			case READING_DATA_ELEMENT:
				pattern = SCIENTIFIC_NOTATION_PATTERN;
				break;
			
			case CHANNELNUMBER_DATA_ELEMENT:
				pattern = CHANNEL_NUMBER_NOTATION_PATTERN;
				break;
						
			case READINGNUMBER_DATA_ELEMENT:
				pattern = READINGNUMBER_NOTATION_PATTERN;
				break;
				
			case TIMESTAMP_DATA_ELEMENT:
				pattern = TIMESTAMP_NOTATION_PATTERN;
				break;
				
			case LIMITS_DATA_ELEMENT:
				pattern = LIMITS_NOTATION_PATTERN;
				break;
				
			default:
				
				throw new Exception("No recognized data element " + desiredElement);
		}
		
		
		//partimos el string reading del parámetro de entrada que pueden ser 
		//READing = DMM reading, UNITs = Units, TSTamp = Timestamp, RNUMber = Reading number, CHANnel = Channel number or LIMits = Limits reading
		readingElements = reading.split(",");
		
		//System.out.println(Arrays.toString(readingElements));
		
		//Visitamos cada uno de los readingElements en busca del elemento que coincida con el desired element pasado como parámetro de entrada
		
		for (int i=0;i<readingElements.length;i++)
		{
			Matcher matcher = pattern.matcher(readingElements[i]);
			if (matcher.find())
			{
				//puede ser que NO exista el group 1 como en el caso overflow cuando devuelve algo del estilo +9.9E37
				if (matcher.group(1)==null) elementsData.add(Double.parseDouble(matcher.group(0)));
				else elementsData.add(Double.parseDouble(matcher.group(0).replace(matcher.group(1), "")));
			}
		}
		
		return elementsData;
	}
	
	public static ArrayList<String> getReadingData_Units(String reading, String desiredElement) throws Exception
	{
		
		if (desiredElement==null || reading==null) throw new NullPointerException("Neither Param desiredElement or reading cannot be null");
		
		ArrayList<String> units = new ArrayList<String>();
		
		String[] readingElements;
		Pattern pattern;
		
		switch (desiredElement) {
		
			case READING_DATA_ELEMENT:
				pattern = SCIENTIFIC_NOTATION_PATTERN;
				break;
			
			case CHANNELNUMBER_DATA_ELEMENT:
				pattern = CHANNEL_NUMBER_NOTATION_PATTERN;
				break;
						
			case READINGNUMBER_DATA_ELEMENT:
				pattern = READINGNUMBER_NOTATION_PATTERN;
				break;
				
			case TIMESTAMP_DATA_ELEMENT:
				pattern = TIMESTAMP_NOTATION_PATTERN;
				break;
				
			case LIMITS_DATA_ELEMENT:
				pattern = LIMITS_NOTATION_PATTERN;
				break;
				
			default:
				
				throw new Exception("No recognized data element " + desiredElement);
		}
		
		
		readingElements = reading.split(",");
		System.out.println(Arrays.toString(readingElements));
		
		for (int i=0;i<readingElements.length;i++)
		{
			Matcher matcher = pattern.matcher(readingElements[i]);
			if (matcher.find())
			{
				//puede ser que NO exista el group 1 como en el caso overflow cuando devuelve algo del estilo +9.9E37
				//Por lo tanto no hay unidades y se devuelve un string vacio
				if (matcher.group(1)!=null) units.add(matcher.group(1));
			}
		}
		
		
		return units;
	}
	
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************

	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		logger.info("Initializing Format SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
	}

	@Override
	public void setFormatDataType(String format_data_type) throws Exception {
		logger.info("Setting Format SubSystem data type to " + format_data_type);	
		this.format_data_type = format_data_type;
		
	}

	@Override
	public String getFormatDataType() throws Exception {
		logger.info("Getting Format SubSystem data type ... ");	
		return this.format_data_type;
	}

	@Override
	public void setFormatBorder(String format_border) throws Exception {
		logger.info("Setting Format SubSystem border to " + format_border);	
		this.format_border = format_border;
		
	}

	@Override
	public String getFormatBorder() throws Exception {
		logger.info("Getting Format SubSystem border ... ");	
		return this.format_border;
	}

	@Override
	public void setFormatElements(String format_elements) throws Exception {
		logger.info("Setting Format SubSystem elements ... ");
		this.format_elements = format_elements;
	}

	@Override
	public String getFormatElements() throws Exception {	
		logger.info("Getting Format SubSystem elements ... ");	
		return this.format_elements;
		
	}
	
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Format SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Uploading Format data type ... ");
		this.communicationsInterface.write("FORMAT:DATA " + this.getFormatDataType());
		
		logger.info("Uploading Format border ... ");
		this.communicationsInterface.write("FORMAT:BORDER " + this.getFormatBorder());
		
		
		logger.info("Uploading Format elements ... ");
		this.communicationsInterface.write("FORMAT:ELEMENTS " + this.getFormatElements());
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Format SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Downloading Format data type ... ");
		this.format_data_type = new String(this.communicationsInterface.ask("FORMAT:DATA?"));
		
		logger.info("Downloading Format border ... ");
		this.format_border = new String(this.communicationsInterface.ask("FORMAT:BORDER?"));
		
		logger.info("Downloading Format elements ... ");
		this.format_border = new String(this.communicationsInterface.ask("FORMAT:ELEMENTS?"));
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		String reading = "+9.51276588E+00VDC,DELTA+0.000SECS,+00000RDNG#,102INTCHAN,+9.96226660E+03OHM4W,DELTA+1.320SECS,+00001RDNG#,109INTCHAN,+2.53908043E+01C,DELTA+0.346SECS,+00002RDNG#,110INTCHAN";
		
		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READING_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READING_DATA_ELEMENT));
		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.READINGNUMBER_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.READINGNUMBER_DATA_ELEMENT));
		logger.info("Scan result ----> " + Format_Subsystem.getReading_Element(reading, Format_Subsystem.TIMESTAMP_DATA_ELEMENT) + Format_Subsystem.getReadingData_Units(reading, Format_Subsystem.TIMESTAMP_DATA_ELEMENT));
	}

	
	
}
