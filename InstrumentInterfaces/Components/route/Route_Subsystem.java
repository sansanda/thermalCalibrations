package route;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import expansion_cards.MeasureChannels_DifferentialMultiplexer;
import expansion_slots.Expansion_Slots;
import information.GeneralInformation_Component;

public class Route_Subsystem extends InstrumentComponent implements I_Route_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Route_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Route tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;

	//Variable para el acceso a los slots de expansion del multimetro 
	//Será necesario para la consulta de canales disponibles, etc...
	
	private Expansion_Slots expansion_slots;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Route_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Route_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Route_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Route_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Route_Subsystem from jObj ... ");
			
		Set<String> keySet = jObj.keySet();
		
		Route_Subsystem route_subsystem = null;
		
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
		
		route_subsystem = new Route_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			route_subsystem.addSubComponent(generalInformation);
		}
		
		return route_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface, Expansion_Slots expansion_slots) throws Exception {
		
		logger.info("Initializing Route SubSystem ... ");
		
		this.communicationsInterface = i_CommunicationsInterface;
		
		this.expansion_slots = expansion_slots;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Route SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Route SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}

	@Override
	public void openAllChannels() throws Exception {
		// TODO Auto-generated method stub
		if (this.expansion_slots.getNumberOfOccupiedSlots()<=0) throw new Exception("No occupied slots in the instruments!!!!. So we cannot open all channels!!!!");
		this.communicationsInterface.write("ROUTE:OPEN:ALL");
	}

	@Override
	/**
	 * 
	 */
	public void closeChannel(int _channelNumber) throws Exception
	{
		logger.info("Closing channel number: " + _channelNumber);
		if (!isChannelAvailable(_channelNumber)) throw new Exception("We cannot close the channel number " + _channelNumber + ". The channel is not available");
		this.communicationsInterface.write("ROUTE:CLOSE " + MeasureChannels_DifferentialMultiplexer.createChannelsList(_channelNumber, _channelNumber));	
	}
	
	/**
	 * Metodo que permite verificar que el numero de canal pasado como parametro est{a disponible los slots de expansion 
	 * @param _channelNumber integer con el numero de canal a verificar. 
	 * El digito de las centenas del _channelNumber indica el numero de slot.
	 * @return true si el numero de canal es valido. False de otra manera.
	 * @throws Exception si el canal no está disponible
	 */
	@Override
	public boolean isChannelAvailable(int _channelNumber) throws Exception {
		logger.info("Asking to expansion slots for the availabily of the channel: " + _channelNumber + " ...");
		((MeasureChannels_DifferentialMultiplexer)this.expansion_slots.getExpansionCardOfChannel(_channelNumber)).validateChannel(_channelNumber);
		return true;
	}
	/**
	 * Método que permite obtener consultar la disponibilidad de los canales indicados como parametro de entrada
	 * @param _channelsList que contiene los canales a consultar
	 * @return true si todos los canales están disponibles o false si alguno de ellos no está disponible.
	 * @throws Exception si algo va mal
	 */
	@Override
	public boolean areChannelsAvailable(int[] _channelsList) throws Exception {
		logger.info("Asking to expansion slots for the availabily of the channels list: " + Arrays.toString(_channelsList) + " ....");
		for (int i=0;i<_channelsList.length;i++) this.isChannelAvailable(_channelsList[i]);
		return true;
	}
	
	
	@Override
	/**
	 * Método que permite obtener la lista de los canales cerrados en el multimetro
	 * @return int[] con la lista de los canales cerrados
	 * @throws Exception si alguno de los canales a consultar no está disponible en los slots de expnasion
	 */
	public int[] getClosedChannels() throws Exception {
		logger.info("Asking to multimeter for the closed channels list...");
		return MeasureChannels_DifferentialMultiplexer.convertChannelList(new String(this.communicationsInterface.ask("ROUTE:CLOSE?")));
	}

	
	/**
	 * Método que permite obtener la cuenta de las operaciones (open/close) de los canales indicados como parametro de entrada.
	 * NOT AVAILABLE FOR THE MOMENT
	 * @param _channelsList que contiene los canales a consultar
	 * @return int[] con las cuentas de cada uno de canales a consultar
	 * @throws Exception si alguno de los canales a consultar no está disponible en los slots de expanasion
	 */
	@Override
	public int[] getClosureCountOfChannels(int[] _channelsList) throws Exception {
		logger.info("Asking to multimeter for the closed channels list...");
		if (!this.areChannelsAvailable(_channelsList)) throw new Exception("One or more of the channels " + Arrays.toString(_channelsList)+ " you get as parameter are not available.");
		System.out.println(this.communicationsInterface.ask("ROUT:CLOS:COUN? " + MeasureChannels_DifferentialMultiplexer.createChannelsList(_channelsList)));
		
		return null;
	}

	/**
	 * Método que permite configurar la lista de canales para ejecutar un futuro scan 
	 * @param _channelsList que contiene los canales a incluir en el scan
	 * @throws Exception si algo va mal o alguno de los canales no está disponible
	 */
	@Override
	public void setScanChannelsList(int[] _channelsList) throws Exception {
		logger.info("Setting the scan channels list...");
		if (!this.areChannelsAvailable(_channelsList)) throw new Exception("One or more of the channels " + Arrays.toString(_channelsList)+ " you get as parameter are not available.");
		this.communicationsInterface.write("ROUTe:SCAN:INTernal " + MeasureChannels_DifferentialMultiplexer.createChannelsList(_channelsList));
	}

	/**
	 * 
	 */
	@Override
	public int[] getScanChannelsList() throws Exception {
		logger.info("Asking to multimeter for the scan configuration (channels list)...");
		return MeasureChannels_DifferentialMultiplexer.convertChannelList(new String(this.communicationsInterface.ask("ROUTe:SCAN:INTernal?")));
	}
	
	/**
	 * 
	 */
	@Override
	public void enableScan(boolean enable) throws Exception {
		
		String message = (enable ? "Enabling scan..." : "Disabling scan");
		String command = (enable ? "ROUTe:SCAN:LSELect INTernal" : "ROUTe:SCAN:LSELect NONE");
		
		logger.info(message);
		this.communicationsInterface.write(command);
	}

	@Override
	public boolean isScanEnable() throws Exception {
		logger.info("Asking to multimeter for the state of the scan ...");
		boolean enable = false;
		String result = new String(this.communicationsInterface.ask("ROUTe:SCAN:LSELect?"));
		if (result.equals("INT"))  enable = true;
		return enable;
	}

	@Override
	public void setTriggerSourceAsImmediate() throws Exception {
		logger.info("Setting scan trigger source as immediate...");
		this.communicationsInterface.write("ROUT:SCAN:TSOurce IMM");
	}

	@Override
	public String getTriggerSourceThatStartsScan() throws Exception {
		logger.info("Asking to multimeter for scan trigger source ...");
		String result = new String(this.communicationsInterface.ask("ROUT:SCAN:TSOurce?"));
		return result;
	}
		
}
