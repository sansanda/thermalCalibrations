/**
 * 
 */
package smus;

import java.io.FileReader;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.CommunicationsModule_Component;
import information.GeneralInformation_Component;

/**
 * @author DavidS
 *
 */
public class K2700 extends InstrumentComponent{

	//version 101:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 101;
	final static Logger logger = LogManager.getLogger(K2700.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public K2700(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	//Inicializacion 
	
	public static K2700 parseFromJSON(String filename) throws Exception
	{
		logger.info("Parsing K2700 from filename " + filename);
		
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
		
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		
		return K2700.parseFromJSON((JSONObject)obj);
		 
	}
	
	public static K2700 parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing K2700 from jObj ... ");
		
		Set<String> keySet = jObj.keySet();
		
		K2700 k2700 = null;
		
		String name = "";
		Long id = 0l;
		InstrumentComponent parent = null;
		boolean enable = true;
		boolean selected = true;
		CommunicationsModule_Component communicationsModule = null;
		GeneralInformation_Component generalInformation = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		k2700 = new K2700(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("CommunicationsModule")) {
			communicationsModule = CommunicationsModule_Component.parseFromJSON((JSONObject)jObj.get("CommunicationsModule"));
			k2700.addSubComponent(communicationsModule);
		}
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			k2700.addSubComponent(generalInformation);
		}
		
		return k2700;
		 
	}
	
	//****************************VERSION***************************************
	
	public static int getVersion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS***************************************
	//**************************************************************************
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ***************** K2700_Component Instance Description ****************** \n");
		
		builder.append(", ").append(super.toString()).append("]\n");
		
		return builder.toString();
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String k2700_filename	= "./Components/testing/k2700.json";
		
		logger.info("TESTNG k2700 creation from JSON file");
		K2700 k2700 = K2700.parseFromJSON(k2700_filename);
		logger.info(k2700.toString());

	}

}
