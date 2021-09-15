package expansion_slots;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import expansion_cards.ExpansionCard;
import expansion_cards.K7700;
import information.GeneralInformation_Component;

/**
 * Clase que permite gestionar toda la info relacionada con el modulo de slots de expansion de cualquier instrumento
 * Ojo, los slots de expansion se numeran desde 1 en adelante (no cero)
 * @author DavidS
 *
 */
public class Expansion_Slots extends InstrumentComponent implements I_Expansion_Slots {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Expansion_Slots.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Route tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//Esta estructurá contendrá las tarjetas expansoras instaladas.
	//Es decir, si el instrumento dispone de una tarjeta expansora modelo 7700 en el slot 1 podría estar 
	//llena de la siguiente forma: [instancia de la clase Keithley7700] <-- Values para keys de 1  
	//Esta estructura se debería crear y actualizar durante la llamada al método initialize
	
	private HashMap<Integer,ExpansionCard> 		slots = null;
	
	private int totalSlots;
	private int emptySlots;
	private int occupiedSlots;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Expansion_Slots(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected, int totalSlots) {
		super(name, id, parent, enable, selected);
		this.slots = new HashMap<Integer, ExpansionCard>();
		this.totalSlots = totalSlots;
		this.emptySlots = totalSlots;
	}
	
	public Expansion_Slots(String jSONObject_filename) throws Exception 
	{
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	 
	public Expansion_Slots(JSONObject jObj) throws Exception {
		
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected"),
				((Long)((JSONObject)jObj.get("Configuration")).get("totalSlots")).intValue()
			);
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
		
		logger.info("Initializing Expansion_Slots ... ");
		
		this.communicationsInterface = i_CommunicationsInterface;
		
		//FASE DE IDENTIFICACION DE LAS TARJETAS DE EXPANSION INSTALADAS EN EL INSTRUMENTO
		
		//Preguntamos por el numero de tarjetas expansoras instaladas en el multimetro
		logger.info("Asking to multimeter for installed cards ... ");
		String[] tarjetas = new String(this.communicationsInterface.ask("*OPT?")).split(",");
		this.totalSlots = tarjetas.length;
		
		//Recorremos todos los slots. Solo visitaremos los que presentan tarjeta instalada  
		for (int i=0;i<tarjetas.length;i++)
		{
			String tarjeta = tarjetas[i];
			
			switch (tarjeta) {
			case "7700":
				logger.info("K7700 expansion card found in slot " + (i+1));
				logger.info("Adding expansion card to system...");
				
				K7700 k7700 = new K7700("k7700_" + (i+1), System.currentTimeMillis(), null, true, true, i+1);
				k7700.initialize(this.communicationsInterface);
				this.slots.put(i+1, k7700);
				this.emptySlots--;
				this.occupiedSlots++;
				break;

			case "NONE":
				logger.info("NO expansion card found in slot " + (i+1));
				break;
				
			default:
				throw new Exception("No handler defined for the card model " + tarjeta);
			}
		}
		
		////////////
		//FIN FASE//
		////////////
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Expansion_Slots configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Expansion_Slots configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}
	
	public ExpansionCard getExpansionCardAt(int slotNumber) throws Exception {
		ExpansionCard slot = this.slots.get(slotNumber);
		if (slot==null) throw new Exception("No slot number "+ slotNumber + " available int the expansion slots module!!!!");
		return slot;
	}
	
	public ExpansionCard getExpansionCardOfChannel(int _channelNumber) throws Exception {
		int slotNumber = (int)(_channelNumber / 100);
		if (slotNumber==0) throw new Exception("Zero is not a valid slot number");
		return this.getExpansionCardAt(slotNumber);
	}
	
	@Override
	public int getNumberOfTotalSlots() throws Exception {
		return this.totalSlots;
	}

	@Override
	public int getNumberOfEmptySlots() throws Exception {
		return this.emptySlots;
	}

	@Override
	public int getNumberOfOccupiedSlots() throws Exception {
		return this.occupiedSlots;
	}

	@Override
	public boolean isSlotNumberOccupied(int slotNumber) throws Exception {
		if (this.slots.get(slotNumber)==null) return false;
		return true;
	}
		
}
