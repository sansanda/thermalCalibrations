/**
 * 
 */
package communications;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import collections.OnlyOneSelected_InstrumentComponentList;
import common.I_InstrumentComponent;	
import common.InstrumentComponent;
import factories.CommunicationInterface_Factory;

/**
 * @author david
 * Clase que modela el modulo de comunicaciones de un instrumento de laboratorio.
 * Esta implementa las Interfaces CommunicationsModule y CommunicationsInterface.
 * La clase gestiona las posibles interfaces de comunicaciones que puede tener un instrumento de comunicaciones como, por ejemplo,
 * de tipo RS232, de tipo GPIB, de tipo LAN, etc... 
 * Por lo tanto, la clase debe permitir a�adir y eliminar instancias de tipo de CommunicationsInterface que el usuario desee pero solo
 * una de ellas puede ser la interface de comunicaciones activa en un momento dado. 
 * Hemos dicho que CommunicationsModule_Component implementa la Interface I_CommunicationsInterface pero en realidad lo que hace es
 * bypasear todas las llamadas o m�todos de dicha Interface a la interface de comunicaciones activa en un momento dado.       
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
		//communication_interfaces_list es un contenedor subcomponente de this que albergar� todas las communication interfaces a�adidas 
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
		//communication_interfaces_list es un contenedor subcomponente de this que albergar� todas las communication interfaces a�adidas 
		super.addSubComponent( new OnlyOneSelected_InstrumentComponentList(COMMUNICATION_INTERFACES_LIST_NAME, 
																					System.currentTimeMillis(), 
																					this,
																					true,
																					true));
		
	}

	public CommunicationsModule_Component(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public CommunicationsModule_Component(JSONObject jObj) throws Exception {
		
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);

		logger.info("Parsing Communications Module from jObj ... ");

		JSONArray communicationInterfaces = (JSONArray) jObj.get("communication_interfaces_list");
		Iterator<JSONObject> i = communicationInterfaces.iterator();

		while (i.hasNext()) {
			JSONObject communicationInterface = i.next();
			
			
		    // Here I try to take the title element from my slide but it doesn't work!
			JSONObject communicationInterface_params = (JSONObject)communicationInterface.get("communication_interface_params");
			String comm_name =  (String)communicationInterface_params.get("name");
			
			switch (comm_name) {
			
			case CommunicationInterface_Factory.GPIB_STANDARD:
				GPIBInterface_Component gpib_interface = new GPIBInterface_Component(communicationInterface_params);
				this.addInterface(gpib_interface);
				break;
			
			case CommunicationInterface_Factory.RS232_STANDARD:
				RS232Interface_Component rs232_interface = new RS232Interface_Component(communicationInterface_params);		
				this.addInterface(rs232_interface);
				break;
				
			case CommunicationInterface_Factory.LAN_STANDARD:
				throw new Exception("No LAN communication interface implemented!!!!");
				
				
			default:
				throw new Exception("Unknnown communication interface!!!!");
			}
		}	
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	 
	//****************************VERSION***************************************
	/**
	 * M�todo de clase que devuelve la version de esta
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
			//a�adimos la interface a la estructura de datos
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
	

	
	/**
	 * Devuelve el standard de la interface de comunicaciones activa en el momento de hacer la llamada
	 */
	@Override
	public String getStandard() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getStandard();
	}

	
	/**
	 * Devuelve el tipo de la interface de comunicaciones activa.
	 * Por ejemplo, pueden ser serial, parallel, etc...
	 */
	@Override
	public String getType() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getType();
	}
	
	
	/**
	 * Devuelve la direcci�n de la interface de comunicaciones activa en el momento de hacer la llamada
	 */
	@Override
	public String getAddress() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getAddress();
	}

	
	/**
	 * Actualiza la direcci�n de la interface de comunicaciones activa en el momento de hacer la llamada
	 * @param address String con la nueva direccion
	 */
	@Override
	public void setAddress(String address) throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().setAddress(address);
		
	}

	
	/**
	 * Inicializa la interface de comunicaciones activa 
	 */
	@Override
	public void initialize() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().initialize();
		
	}
	
	
	/**
	 * Abre la interface de comunicaciones activa 
	 */
	@Override
	public void open() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().open();	
	}
	
	/**
	 * Pregunta a la interface de comunicaciones activa si est� abierta.
	 */
	@Override
	public boolean isOpened() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().isOpened();
	}
	
	
	
	/**
	 * Cierra la interface de comunicaciones activa
	 */
	@Override
	public void close() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().close();
		
	}

	
	/**
	 * Lee los datos actualizados (los m�s nuevos llegados por la interface de comunicaciones activa)
	 * Get last income data (the newest arrived data)
	 * @return the data readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public byte[] read() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().read();
	}

	
	/**
	 * Envia los datos a la interface de comunicaciones activa
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	data is the String to send to the output
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public void write(String data) throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().write(data);	
	}

	
	/**
	 * Env�a una consulta (un comando) por la interface de comunicaciones activa y espera a la respuesta por el mismo canal de comunicaciones
	 * En la practica se traduce en un write seguido de un read (este suele ser sincrono pero depende de la interface cosa que puede llegar a complicar el c�digo).
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	query is the String to send to the output as query
	 * @return 	the response readed as byte array.
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public byte[] ask(String query) throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().ask(query);
	}

	/**
	 * M�todo privado que resuelve si el modulo de comunicaciones dispone de alguna interface.
	 * @throws Exception en caso de que el m�dulo no presente ninguna interface de comunicaciones
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
