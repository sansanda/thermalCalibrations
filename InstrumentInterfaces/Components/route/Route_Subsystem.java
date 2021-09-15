package route;

import java.io.FileReader;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import expansion_cards.MeasureChannels_DifferentialMultiplexer;
import expansion_slots.Expansion_Slots;

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
	
	private String terminals_route = "FRONt"; //terminals
	
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
	
	public Route_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Route_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing Route Subsystem from jObj ... ");
		
		JSONObject configuration = (JSONObject)jObj.get("Configuration");
		
		this.terminals_route = (String)configuration.get("terminals_route");
		
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
	
	/**
	 * Fase de inicialización del componente Route_Subsystem donde principalmente se pasa la referencia del modulo de comunicaciones y 
	 * la referencia del objeto que gestiona los expansion slots que pueda tener el instrumento y que será de absoluta necesidad para este componente
	 * si se quieren ejecutar todo tipo de comandos relacionados con los canales de medida de las tarjetas multiplexoras que pueden tener conectados 
	 * instrumentos como un K2700.
	 * @param i_CommunicationsInterface con la referencia al modulo de comunicaciones 
	 * @param expansion_slots con la referencia al objeto que gestiona los expansion slots. Ojo porque algunos instrumentos pueden no disponer de 
	 * expansion slots, en ese caso la referencia apuntará a null.
	 * @throws Exception
	 */
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface, Expansion_Slots expansion_slots) throws Exception {
		
		logger.info("Initializing Route SubSystem ... ");
		
		this.communicationsInterface = i_CommunicationsInterface;
		
		this.expansion_slots = expansion_slots;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Route SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		if (this.expansion_slots == null) 
		{
			this.selectInput_OutputTerminalsRoute(this.terminals_route);
		}
		else
		{
			//Nothing todo here 
		}
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Route SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}

	@Override
	public void sendOpenAllChannelsCommand() throws Exception {
		if (this.expansion_slots == null) return;
		if (this.expansion_slots.getNumberOfOccupiedSlots()<=0) throw new Exception("No occupied slots in the instruments!!!!. So we cannot open all channels!!!!");
		this.communicationsInterface.write("ROUTE:OPEN:ALL");
	}

	@Override
	/**
	 * 
	 */
	public void sendCloseChannelCommand(int _channelNumber) throws Exception
	{
		if (this.expansion_slots == null) return;
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
		if (this.expansion_slots == null) return false;
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
		if (this.expansion_slots == null) return false;
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
	public int[] queryClosedChannels() throws Exception {
		if (this.expansion_slots == null) throw new Exception("No slots detected in this instruments. Are you sure that you are working with a multimeter/multiplexer instrument?");
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
	public int[] queryClosureCountOfChannels(int[] _channelsList) throws Exception {
		if (this.expansion_slots == null) throw new Exception("No slots detected in this instruments. Are you sure that you are working with a multimeter/multiplexer instrument?");
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
	public void configureScanChannelsList(int[] _channelsList) throws Exception {
		if (this.expansion_slots == null) return;
		logger.info("Setting the scan channels list...");
		if (!this.areChannelsAvailable(_channelsList)) throw new Exception("One or more of the channels " + Arrays.toString(_channelsList)+ " you get as parameter are not available.");
		this.communicationsInterface.write("ROUTe:SCAN:INTernal " + MeasureChannels_DifferentialMultiplexer.createChannelsList(_channelsList));
	}

	/**
	 * 
	 */
	@Override
	public int[] queryScanChannelsList() throws Exception {
		if (this.expansion_slots == null) throw new Exception("No slots detected in this instruments. Are you sure that you are working with a multimeter/multiplexer instrument?");
		logger.info("Asking to multimeter for the scan configuration (channels list)...");
		return MeasureChannels_DifferentialMultiplexer.convertChannelList(new String(this.communicationsInterface.ask("ROUTe:SCAN:INTernal?")));
	}
	
	/**
	 * 
	 */
	@Override
	public void configureEnableScan(boolean enable) throws Exception {
		
		if (this.expansion_slots == null) return;
		
		String message = (enable ? "Enabling scan..." : "Disabling scan");
		String command = (enable ? "ROUTe:SCAN:LSELect INTernal" : "ROUTe:SCAN:LSELect NONE");
		
		logger.info(message);
		this.communicationsInterface.write(command);
	}

	@Override
	public boolean queryScanEnable() throws Exception {
		
		if (this.expansion_slots == null) throw new Exception("No slots detected in this instruments. Are you sure that you are working with a multimeter/multiplexer instrument?");
		logger.info("Asking to multimeter for the state of the scan ...");
		boolean enable = false;
		String result = new String(this.communicationsInterface.ask("ROUTe:SCAN:LSELect?"));
		if (result.equals("INT"))  enable = true;
		return enable;
	}

	@Override
	public void configureTriggerSourceAsImmediate() throws Exception {
		if (this.expansion_slots == null) return;
		logger.info("Setting scan trigger source as immediate...");
		this.communicationsInterface.write("ROUT:SCAN:TSOurce IMM");
	}

	@Override
	public String queryTriggerSourceThatStartsScan() throws Exception {
		if (this.expansion_slots == null) throw new Exception("No slots detected in this instruments. Are you sure that you are working with a multimeter/multiplexer instrument?");
		logger.info("Asking to multimeter for scan trigger source ...");
		String result = new String(this.communicationsInterface.ask("ROUT:SCAN:TSOurce?"));
		return result;
	}
	/**
	 * This command is used to select which set of input/output terminals to enable (front panel or rear panel).
	 * @param route that can be 'FRONt' for the Front panel in/out jacks or 'REAR' for the Rear panel in/out jacks
	 */
	public void selectInput_OutputTerminalsRoute(String route) throws Exception
	{
		logger.info("Selecting the input_output terminals route as" + route);
		this.terminals_route = route;
		this.communicationsInterface.write(":ROUTe:TERMinals " + route);
	}
	
	public String queryInput_OutputTerminalsRoute() throws Exception
	{
		logger.info("Asking to instrument for the input_output terminals route ...");
		String result = new String(this.communicationsInterface.ask(":ROUTe:TERMinals?"));
		return result;
	}
		
}
