package format;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import information.GeneralInformation_Component;

public class Format_Subsystem extends InstrumentComponent implements I_Format_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Format_Subsystem.class);
	
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

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Format_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Format_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Format_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Format_Subsystem from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		Format_Subsystem format_subsystem = null;
		
		String name = "";
		Long id = 0l;
		InstrumentComponent parent = null;
		boolean enable = true;
		boolean selected = true;
		GeneralInformation_Component generalInformation = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		format_subsystem = new Format_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			format_subsystem.addSubComponent(generalInformation);
		}
		
		if (keySet.contains("Configuration")) {
			JSONObject configurationObj = (JSONObject)jObj.get("Configuration");
			Set<String> configurationObj_keySet = configurationObj.keySet();
			if (configurationObj_keySet.contains("format_data_type")) 	format_subsystem.setFormatDataType((String) configurationObj.get("format_data_type"));
			if (configurationObj_keySet.contains("format_border")) 		format_subsystem.setFormatBorder((String) configurationObj.get("format_border"));					
			if (configurationObj_keySet.contains("format_elements"))	format_subsystem.setFormatElements((String) configurationObj.get("format_elements")); 
		}
		
		return format_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
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
	
	
	
	
}
