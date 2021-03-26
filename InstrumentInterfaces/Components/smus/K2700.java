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

/**
 * @author DavidS
 *
 */
public class K2700 extends InstrumentComponent{

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
	public K2700(String name, long id, I_InstrumentComponent parent) {
		super(name, id, parent);
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
		
		JSONObject jObj = (JSONObject) obj;
		
		K2700 k2700 = null;
		
		k2700 = new K2700(
			 (String)jObj.get("name"), 
			 (Long)jObj.get("id"), 
			 (InstrumentComponent)jObj.get("parent")
		);
		
		
		Set<String> subComponents_Names = jObj.keySet();
		JSONObject s = (JSONObject)jObj.get("CommunicationsModule");
		
		System.out.println("Hola" + subComponents_Names.toString());
		System.out.println("Hola" + s.toString());
		
		logger.info(subComponents_Names.toString());
		
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
	 */
	public static void main(String[] args) {
		

	}

}
