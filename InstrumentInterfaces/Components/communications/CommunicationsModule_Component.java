/**
 * 
 */
package communications;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import collections.OnlyOneSelected_InstrumentComponentList;
import common.I_InstrumentComponent;
import common.InstrumentComponent;
import factories.CommunicationInterface_Factory;
import information.GeneralInformation_Component;
import smus.K2700;

/**
 * @author david
 * Clase que modela el modulo de comunicaciones de un instrumento de laboratorio.
 * Esta implementa las Interfaces CommunicationsModule y CommunicationsInterface.
 * La clase gestiona las posibles interfaces de comunicaciones que puede tener un instrumento de comunicaciones como, por ejemplo,
 * de tipo RS232, de tipo GPIB, de tipo LAN, etc... 
 * Por lo tanto, la clase debe permitir añadir y eliminar instancias de tipo de CommunicationsInterface que el usuario desee pero solo
 * una de ellas puede ser la interface de comunicaciones activa en un momento dado. 
 * Hemos dicho que CommunicationsModule_Component implementa la Interface I_CommunicationsInterface pero en realidad lo que hace es
 * bypasear todas las llamadas o métodos de dicha Interface a la interface de comunicaciones activa en un momento dado.       
 */
public class CommunicationsModule_Component extends InstrumentComponent implements I_CommunicationsModule, I_CommunicationsInterface{

	//version 106:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method
	//version 105: adapted to the new I_CommunicationsInterface
	//version 104: remove communication_interfaces_list as attribute of CommunicationsModule_Component and added as subcomponent
	//version 103: prevent the calls on communications module component with any interface added (new private method checkActiveInterface())
	//version 102: all the I_CommunicationsInterface calls to the respective active interface (subcomponents)
	//version 102: CommunicationsModule_Component implements I_CommunicationsInterface so it will act as a CommunicationsInterface bypassing
	
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 106;
	final static Logger logger = LogManager.getLogger(CommunicationsModule_Component.class);
	
	private final String COMMUNICATION_INTERFACES_LIST_NAME = "communication_interfaces_list";

	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @param name
	 * @param id
	 * @param parent
	 * @throws Exception 
	 */
	public CommunicationsModule_Component(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) throws Exception {
		
		super(name, id, parent, enable, selected);
		//communication_interfaces_list es un contenedor subcomponente de this que albergará todas las communication interfaces añadidas 
		super.addSubComponent( new OnlyOneSelected_InstrumentComponentList(COMMUNICATION_INTERFACES_LIST_NAME, 
																					System.currentTimeMillis(), 
																					this,
																					true,
																					true));

	}

	/**
	 * @param name
	 * @param id
	 * @param descriptiveTags
	 * @param subComponents
	 * @param parent
	 * @throws Exception 
	 */
	public CommunicationsModule_Component(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String,I_InstrumentComponent> subComponents) throws Exception {
		
		super(name, id, parent, enable, selected, descriptiveTags, subComponents);
		//communication_interfaces_list es un contenedor subcomponente de this que albergará todas las communication interfaces añadidas 
		super.addSubComponent( new OnlyOneSelected_InstrumentComponentList(COMMUNICATION_INTERFACES_LIST_NAME, 
																					System.currentTimeMillis(), 
																					this,
																					true,
																					true));
		
	}

	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	
	 public static CommunicationsModule_Component parseFromJSON(String filename) throws Exception
	 {
		 logger.info("Parsing Communications Module from file ... ");
		 
		 //JSON parser object to parse read file
		 JSONParser jsonParser = new JSONParser();
		 FileReader reader = new FileReader(filename);
		
		 //Read JSON file
		 Object obj = jsonParser.parse(reader);
		 jsonParser = null;
		 
		 org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		 return CommunicationsModule_Component.parseFromJSON(jObj);
		 
	 }
	 
	 public static CommunicationsModule_Component parseFromJSON(JSONObject jObj) throws Exception
	 {
		logger.info("Parsing Communications Module from jObj ... ");
		Set<String> keySet = jObj.keySet();
		
		InstrumentComponent parent = null;
		String name = "";
		Long id = 0l;
		boolean enable = false;
		boolean selected = true;
		
		CommunicationsModule_Component communicationsModule = null;
		GeneralInformation_Component generalInformation = null;
		JSONArray communicationInterfaces = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		communicationsModule = new CommunicationsModule_Component(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
		);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			communicationsModule.addSubComponent(generalInformation);
		}
		

		communicationInterfaces = (JSONArray) jObj.get("communication_interfaces_list");
		Iterator<JSONObject> i = communicationInterfaces.iterator();

		while (i.hasNext()) {
			JSONObject communicationInterface = i.next();
			
			
		    // Here I try to take the title element from my slide but it doesn't work!
			JSONObject communicationInterface_params = (JSONObject)communicationInterface.get("communication_interface_params");
			String comm_name =  (String)communicationInterface_params.get("name");
			
			switch (comm_name) {
			
			case CommunicationInterface_Factory.GPIB_STANDARD:
				GPIBInterface_Component gpib_interface = GPIBInterface_Component.parseFromJSON(communicationInterface_params);
				communicationsModule.addInterface(gpib_interface);
				break;
			
			case CommunicationInterface_Factory.RS232_STANDARD:
				RS232Interface_Component rs232_interface = RS232Interface_Component.parseFromJSON(communicationInterface_params);		
				communicationsModule.addInterface(rs232_interface);
				break;
				
			case CommunicationInterface_Factory.LAN_STANDARD:
				throw new Exception("No LAN communication interface implemented!!!!");
				
				
			default:
				throw new Exception("Unknnown communication interface!!!!");
			}
		}
		
		return communicationsModule;
		 
	 }
	 
	 
	//****************************VERSION***************************************
	/**
	 * Método de clase que devuelve la version de esta
	 * @return la version de la clase
	 */
	 
	public static int getVersion() {
		return classVersion;
	}
	
	
	
	@Override
	public void addInterface(I_InstrumentComponent commInterface) throws Exception {	
		if (commInterface instanceof I_CommunicationsInterface) 
		{
			//si la interface existe entonces primero la borramos
			if (this.getInterface(commInterface.getName())!=null) this.removeInterface(commInterface.getName());
			//añadimos la interface a la estructura de datos
			((OnlyOneSelected_InstrumentComponentList) this.getSubComponent(COMMUNICATION_INTERFACES_LIST_NAME)).addComponent(commInterface);
		}
		else throw new Exception("The component to remove must implement I_CommunicationsInterface ");
	}

	@Override
	public I_InstrumentComponent removeInterface(String componentName) throws Exception{
		return ((OnlyOneSelected_InstrumentComponentList) this.getSubComponent(COMMUNICATION_INTERFACES_LIST_NAME)).removeComponent(componentName);
	}
	
	@Override
	public I_CommunicationsInterface getInterface(String componentName) throws Exception {
		return (I_CommunicationsInterface) ((OnlyOneSelected_InstrumentComponentList) this.getSubComponent(COMMUNICATION_INTERFACES_LIST_NAME)).getSubComponent(componentName);
	}

	@Override
	public I_CommunicationsInterface getActiveInterface() throws Exception {
		return (I_CommunicationsInterface) ((OnlyOneSelected_InstrumentComponentList) this.getSubComponent(COMMUNICATION_INTERFACES_LIST_NAME)).getSelectedComponent();
	}
	

	@Override
	/**
	 * Devuelve el standard de la interface de comunicaciones activa en el momento de hacer la llamada
	 */
	public String getStandard() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getStandard();
	}

	@Override
	/**
	 * Devuelve el tipo de la interface de comunicaciones activa.
	 * Por ejemplo, pueden ser serial, parallel, etc...
	 */
	public String getType() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getType();
	}
	
	@Override
	/**
	 * Devuelve la dirección de la interface de comunicaciones activa en el momento de hacer la llamada
	 */
	public String getAddress() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getAddress();
	}

	@Override
	/**
	 * Actualiza la dirección de la interface de comunicaciones activa en el momento de hacer la llamada
	 * @param address String con la nueva direccion
	 */
	public void setAddress(String address) throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().setAddress(address);
		
	}

	@Override
	/**
	 * Inicializa la interface de comunicaciones activa 
	 */
	public void initialize() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().initialize();
		
	}
	
	@Override
	/**
	 * Abre la interface de comunicaciones activa 
	 */
	public void open() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().open();
		
	}

	@Override
	/**
	 * Cierra la interface de comunicaciones activa
	 */
	public void close() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().close();
		
	}

	@Override
	/**
	 * Lee los datos actualizados (los más nuevos llegados por la interface de comunicaciones activa)
	 * Get last income data (the newest arrived data)
	 * @return the data readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	public byte[] read() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().read();
	}

	@Override
	/**
	 * Envia los datos a la interface de comunicaciones activa
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	data is the String to send to the output
	 * @trhows 	Exception if something goes wrong
	 */
	public void write(String data) throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().write(data);
		
	}

	@Override
	/**
	 * Envía una consulta (un comando) por la interface de comunicaciones activa y espera a la respuesta por el mismo canal de comunicaciones
	 * En la practica se traduce en un write seguido de un read (este suele ser sincrono pero depende de la interface cosa que puede llegar a complicar el código).
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	query is the String to send to the output as query
	 * @return 	the response readed as byte array.
	 * @trhows 	Exception if something goes wrong
	 */
	public byte[] ask(String query) throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().ask(query);
	}

	/**
	 * Método privado que resuelve si el modulo de comunicaciones dispone de alguna interface.
	 * @throws Exception en caso de que el módulo no presente ninguna interface de comunicaciones
	 */
	private void checkActiveInterface() throws Exception
	{
		OnlyOneSelected_InstrumentComponentList communication_interfaces_list = ((OnlyOneSelected_InstrumentComponentList) this.getSubComponent(COMMUNICATION_INTERFACES_LIST_NAME));
		if (!communication_interfaces_list.hasSubcomponents()) throw new Exception ("No any inteface added in communications module component");
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ************* CommunicationsModule_Component Instance Description **************** \n");
		
		builder.append("[COMMUNICATION_INTERFACES_LIST_NAME = ").append(COMMUNICATION_INTERFACES_LIST_NAME);
		builder.append(", ").append(super.toString()).append("]\n");
		
		return builder.toString();
	}
	
	

}
