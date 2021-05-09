package sense;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import dataValidators.DataValidators;
import expansion_cards.ExpansionCard;
import expansion_cards.MeasureChannels_DifferentialMultiplexer;
import information.GeneralInformation_Component;
import sense.senseFunctions.FRTDBased_TemperatureFunction_Configuration;
import sense.senseFunctions.FWResistanceFunction_Configuration;
import sense.senseFunctions.ResistanceFunction_Configuration;
import sense.senseFunctions.SenseFunction_Configuration;
import sense.senseFunctions.TCoupleBased_TemperatureFunction_Configuration;
import sense.senseFunctions.ThermistorBased_TemperatureFunction_Configuration;
import sense.senseFunctions.VoltageDCFunction_Configuration;

public class Sense_Subsystem extends InstrumentComponent implements I_Sense_Subsystem {

	//TODO: integrar Expansion slots o multiplexer (exapnsion cards) para verificar channel available
	
	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Sense_Subsystem.class);
	
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Sense tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	private HashMap<Integer,ExpansionCard> 		slots = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Sense_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Sense_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Sense_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Sense_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Sense_Subsystem from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		Sense_Subsystem sense_subsystem = null;
		
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
		
		sense_subsystem = new Sense_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			sense_subsystem.addSubComponent(generalInformation);
		}
		
		
		
		/////Testing 
		if (keySet.contains("Configuration")) {
			JSONObject conf = (JSONObject)jObj.get("Configuration");
			JSONArray channelsArray = (JSONArray)conf.get("channels");
			JSONArray validFunctionParametersArray = (JSONArray)conf.get("validFunctionParameters");
			
			JSONObject voltageDC_vfp = (JSONObject)validFunctionParametersArray.get(0);
			JSONObject voltageAC_vfp = (JSONObject)validFunctionParametersArray.get(1);
			JSONObject currentDC_vfp = (JSONObject)validFunctionParametersArray.get(2);
			JSONObject currentAC_vfp = (JSONObject)validFunctionParametersArray.get(3);
			JSONObject resistance_vfp = (JSONObject)validFunctionParametersArray.get(4);
			JSONObject fResistance_vfp = (JSONObject)validFunctionParametersArray.get(5);
			JSONObject temperature_vfp = (JSONObject)validFunctionParametersArray.get(6);
			JSONObject frequency_vfp = (JSONObject)validFunctionParametersArray.get(7);
			JSONObject period_vfp = (JSONObject)validFunctionParametersArray.get(8);
			JSONObject continuity_vfp = (JSONObject)validFunctionParametersArray.get(9);
			
			
			JSONObject channel1Atts = (JSONObject)channelsArray.get(0);
			JSONObject channel9Atts = (JSONObject)channelsArray.get(6); //temperature tcouple
			JSONObject channel13Atts = (JSONObject)channelsArray.get(10); //temperature frtd
			JSONObject channel14Atts = (JSONObject)channelsArray.get(11); //temperature thermistor
			JSONObject channel8Atts = (JSONObject)channelsArray.get(5); //fw resistance
			JSONObject channel6Atts = (JSONObject)channelsArray.get(4); //resistance
			
			VoltageDCFunction_Configuration vDCConf = new VoltageDCFunction_Configuration();
			vDCConf.initializeFromJSON(voltageDC_vfp, channel1Atts);
			logger.info(vDCConf.toString());
			
			TCoupleBased_TemperatureFunction_Configuration tcTConf = new TCoupleBased_TemperatureFunction_Configuration();
			tcTConf.initializeFromJSON(temperature_vfp, channel9Atts);
			logger.info(tcTConf.toString());
			
			FRTDBased_TemperatureFunction_Configuration frtdConf = new FRTDBased_TemperatureFunction_Configuration();
			frtdConf.initializeFromJSON(temperature_vfp, channel13Atts);
			logger.info(frtdConf.toString());
			
			ThermistorBased_TemperatureFunction_Configuration thermistorConf = new ThermistorBased_TemperatureFunction_Configuration();
			thermistorConf.initializeFromJSON(temperature_vfp, channel14Atts);
			logger.info(thermistorConf.toString());
			
			FWResistanceFunction_Configuration fwResistanceConf = new FWResistanceFunction_Configuration();
			fwResistanceConf.initializeFromJSON(fResistance_vfp, channel8Atts);
			logger.info(fwResistanceConf.toString());
			
			ResistanceFunction_Configuration resistanceConf = new ResistanceFunction_Configuration();
			resistanceConf.initializeFromJSON(resistance_vfp, channel6Atts);
			logger.info(resistanceConf.toString());

		}
		
		///////
		
		return sense_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	

	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Sense SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
		////////////
		//FIN FASE//
		////////////
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Sense SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Sense SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}

	/**
	 * Método creado para poder configurar la funcion de medida del instrumento o bien configurar la funcion de medida de los 
	 * canales de medida de la lista de canales pasada como parámetro. Se trata de una función que requiere tiempo para ser ejecutada,
	 * por lo que se aconseja dejar un tiempo antes de enviar otro comando al instrumento.
	 * @param functionName con el String que incluye el nombre de la funcion de medida a aplicar al instrumento o a la lista de canles (si esta es diferente a null) 
	 * @param channelList con el array de enteros que identifican los canales a configurar con la functionName. Si es null la configuración se aplicará a la funcion de 
	 * medida del instrumento.
	 * @throws Exception si se hace uso de una función de medida no existente en el set de funciones definidas para el instrumento o si,
	 * alguno de los canales de medida de la lista de canales no existe o no está disponible en cualquiera de los slots de expansion.
	 */
	@Override
	public void configureSenseFunction(String functionName, int[] channelsList) throws Exception {
		
		String message = "";
		String command = "";
		
		if (channelsList==null)
		{
			message = "Configuring sense function to " + functionName + "...";
			command = "SENSe:FUNCtion '" + functionName + "'";			
		}
		else
		{
			message = "Configuring sense function to " + functionName + "..." + " at channels " + MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList);
			command = "SENSe:FUNCtion '" + functionName + "'" + "," +  MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList);
		}
		
		logger.info(message);
		
		if (DataValidators.discreteSet_Validator(functionName, SenseFunction_Configuration.AVAILABLE_FUNCTIONS_SET, "NO").equals("NO")) throw new Exception("function name = '" + functionName + "'" +  " is not valid function!!!!");
		
		this.communicationsInterface.write(command);
	}

	/**
	 * Método creado para poder configurar la funcion de medida del instrumento o bien configurar la funcion de medida del
	 * canal de medida pasado como parámetro. Se trata de una función que requiere tiempo para ser ejecutada,
	 * por lo que se aconseja dejar un tiempo antes de enviar otro comando al instrumento.
	 * @param functionName con el String que incluye el nombre de la funcion de medida a aplicar al instrumento o al canal (si esta es diferente a null) 
	 * @param channel con un entero que identifican el canal a configurar con la functionName. Si es null la configuración se aplicará a la funcion de 
	 * medida del instrumento. HAce uso del método public void configureSenseFunction(String functionName, int[] channelsList)
	 * @throws Exception si se hace uso de una función de medida no existente en el set de funciones definidas para el instrumento o si,
	 * el canal de medida no existe o no está disponible en cualquiera de los slots de expansion.
	 */
	@Override
	public void configureSenseFunction(String functionName, int channel) throws Exception {
		
		int[] channelsList;
		
		if (channel==-1) channelsList = null;
		else channelsList = new int[]{channel};
		
		this.configureSenseFunction(functionName, channelsList);
	}
	
	/**
	 * Método creado para poder obtener la configuración de la funcion de medida del instrumento (si channelsList es null) o la configuracion de la funcion de medida de 
	 * cada uno de los canales indicados por el parametro de entrada channelsList.
	 * POr ejemplo, si channelsList = [101,102,103] y esta lista de canales es válida y la respectiva configuración de sus funciones en el instrumento es TEMP, VOLT:DC y VOLT:AC
	 * entonces el string devuelto será: "TEMP","VOLT:DC","VOLT:AC"
	 * @param channelList con el array de enteros que identifican los canales que queremos consultar. Si es null el método devolverá la funcion de medida del instrumento.
	 * @throws Exception si alguno de los canales de medida de la lista de canales no existe o no está disponible en cualquiera de los slots de expansion.
	 * @return String con un array a modo cvs las funciones de medida de los canales o un String con la funcion de medida del instrumento.
	 */
	@Override
	public String querySenseFunction(int[] channelsList) throws Exception {
		String message = "";
		String query = "";
		
		if (channelsList==null)
		{
			message = "Querying sense function configuration ...";
			query = "SENSe:FUNCtion?";			
		}
		else
		{
			message = "Querying sense function configuration at channels " + MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList) + "...";
			query = "SENSe:FUNCtion? " +  MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList);
		}
		
		logger.info(message);
		return new String(this.communicationsInterface.ask(query));
	}
		
}
