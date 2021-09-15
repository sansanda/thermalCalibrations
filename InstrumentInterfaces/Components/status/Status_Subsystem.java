package status;

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

public class Status_Subsystem extends InstrumentComponent implements I_Status_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Status_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Status tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Status_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	public Status_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
	}

	public Status_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Status_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Creating new instance of Status_Subsystem from jObj ... ");
		
	}
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
//	public static Status_Subsystem parseFromJSON(String filename) throws Exception
//	{
//		//JSON parser object to parse read file
//		JSONParser jsonParser = new JSONParser();
//		FileReader reader = new FileReader(filename);
//	
//		//Read JSON file
//		Object obj = jsonParser.parse(reader);
//		jsonParser = null;
//		 
//		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
//		 
//		return Status_Subsystem.parseFromJSON(jObj);
//		 
//	 }
//	 
//	public static Status_Subsystem parseFromJSON(JSONObject jObj) throws Exception
//	{
//		logger.info("Parsing Status_Subsystem from jObj ... ");
//		Set keySet = jObj.keySet();
//		
//		Status_Subsystem status_subsystem = null;
//		
//		String name = "";
//		Long id = 0l;
//		InstrumentComponent parent = null;
//		boolean enable = true;
//		boolean selected = true;
//		GeneralInformation_Component generalInformation = null;
//		
//		if (keySet.contains("name")) name = (String)jObj.get("name");
//		if (keySet.contains("id")) id = (Long)jObj.get("id");
//		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
//		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
//		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
//		
//		status_subsystem = new Status_Subsystem(
//				 name, 
//				 id, 
//				 parent,
//				 enable,
//				 selected
//			);
//		
//		if (keySet.contains("GeneralInformation")) {
//			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
//			status_subsystem.addSubComponent(generalInformation);
//		}
//		
//		return status_subsystem;
//			
//		
//	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	

	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	/**
	 * Return status registers to default states.
	 * @throws Exception
	 */
	public void presetStatusRegisters() throws Exception {
		this.communicationsInterface.write("STATUS:PRESET");
	}
	/**
	 * Clears all messages from Error Queue.
	 * @throws Exception
	 */
	public void clearAllErrorMessagesFromQueue() throws Exception {
		this.communicationsInterface.write("STATUS:QUEUE:CLEAR");
	}
	
	public String getMostrecentErrorMessage() throws Exception {
		String errorMessage = "";
		errorMessage = new String(this.communicationsInterface.ask("STATUS:QUEUE:NEXT?"));
		return errorMessage;
	}
	
	//measurement event registers
	
	public String getMeasurementEventRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:MEASUREMENT:EVENT?"));
		return registerValue;
	}
	
	public String getMeasurementEnableRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:MEASUREMENT:ENABLE?"));
		return registerValue;
	}

	public String getMeasurementConditionRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:MEASUREMENT:CONDITION?"));
		return registerValue;
	}
	
	//operation status registers
	
	public String getOperationEventRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:OPERATION:EVENT?"));
		return registerValue;
	}
	
	public String getOperationEnableRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:OPERATION:ENABLE?"));
		return registerValue;
	}

	public String getOperationConditionRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:OPERATION:CONDITION?"));
		return registerValue;
	}
	
	//Questionable status registers
	
	public String getQuestionableEventRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:QUESTIONABLE:EVENT?"));
		return registerValue;
	}
	
	public String getQuestionableEnableRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:QUESTIONABLE:ENABLE?"));
		return registerValue;
	}

	public String getQuestionableConditionRegister() throws Exception {
		String registerValue = "";
		registerValue = new String(this.communicationsInterface.ask("STATUS:QUESTIONABLE:CONDITION?"));
		return registerValue;
	}
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Status SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Status SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Status SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}
}