package system;

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

public class System_Subsystem extends InstrumentComponent implements I_System_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(System_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a System tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	
	private Boolean isBeeperEnable = null;
	private String 	tStampType = "REL";
	private Boolean resetRelativeTStamp = null;
	private Boolean resetReadingNumber = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public System_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	public System_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
	}

	public System_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public System_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing System_Subsystem from jObj ... ");
		
		JSONObject configuration = (JSONObject) jObj.get("Configuration");
		if (configuration!=null)
		{
			this.enableBeeper(configuration.get("beeper").equals("ON"));
		}
		
		JSONObject tStampConfiguration = (JSONObject) configuration.get("tStamp");
		if (tStampConfiguration!=null)
		{
			this.settStampType((String)tStampConfiguration.get("type"));
			this.enableResetRelativeTStamp(tStampConfiguration.get("reset_relative_tstamp").equals("ON"));
		}
		
		JSONObject readingNumberConfiguration = (JSONObject) configuration.get("readingNumber");
		if (readingNumberConfiguration!=null)
		{
			this.enableResetReadingNumber(readingNumberConfiguration.get("reset").equals("ON"));
		}
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	
	public static int getClassversion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing System SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading System SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Uploading Beeper configuration ... ");
		
		this.communicationsInterface.write("SYSTem:BEEPer:STATe " + ((this.isBeeperEnable())? "ON":"OFF"));
		this.communicationsInterface.write("SYSTem:TSTamp:TYPE " + this.gettStampType());
		if (this.isResetRelativeTStampEnable()) this.communicationsInterface.write("SYSTem:TSTamp:RELative:RESet");
		if (this.isResetReadingNumberEnable()) this.communicationsInterface.write("SYSTem:RNUMber:RESet");
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading System SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}

	public boolean isBeeperEnable() {
		return isBeeperEnable;
	}

	public void enableBeeper(boolean enable) throws Exception {
		this.isBeeperEnable = enable;
		//this.communicationsInterface.write("SYSTem:BEEPer:STATe " + ((this.isBeeperEnable())? "ON":"OFF"));
	}

	private String gettStampType() {
		return tStampType;
	}

	private void settStampType(String tStampType) throws Exception {
		this.tStampType = tStampType;
		//this.communicationsInterface.write("SYSTem:TSTamp:TYPE " + this.gettStampType());
	}

	private boolean isResetRelativeTStampEnable() {
		return resetRelativeTStamp;
	}

	private void enableResetRelativeTStamp(boolean enable) throws Exception {
		this.resetRelativeTStamp = enable;
		//if (this.isResetRelativeTStampEnable()) this.communicationsInterface.write("SYSTem:TSTamp:RELative:RESet");
	}

	private boolean isResetReadingNumberEnable() {
		return resetReadingNumber;
	}

	private void enableResetReadingNumber(boolean enable) throws Exception {
		this.resetReadingNumber = enable;
		//if (this.isResetReadingNumberEnable()) this.communicationsInterface.write("SYSTem:RNUMber:RESet");
	}

}