/**
 * 
 */
package communications;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import collections.OnlyOneSelected_InstrumentComponentList;
import common.I_InstrumentComponent;
import common.InstrumentComponent;

/**
 * @author david
 * Clase que modela el modulo de comunicaciones de un instrumento de laboratorio.
 * Esta implementa las Interfaces CommunicationsModule y CommunicationsInterface.
 * La clase gestiona las posibles interfaces de comunicaciones que puede tener un instrumento de comunicaciones como, por ejemplo,
 * de tipo RS232, de tipo GPIB, de tipo LAN, etc... 
 * Por lo tanto, la clase debe permitir añadir y eliminar instancias de tipo de CommunicationsInterface que el usuario desee pero solo
 * una de ellas puede ser la interface de comunicaciones activa en un momento dado. 
 * Hemos dicho que CommunicationsModuleComponent implementa la Interface I_CommunicationsInterface pero en realidad lo que hace es
 * bypasear todas las llamadas o métodos de dicha Interface a la interface de comunicaciones activa en un momento dado.       
 */
public class CommunicationsModuleComponent extends InstrumentComponent implements I_CommunicationsModule, I_CommunicationsInterface{

	//version 102: CommunicationsModuleComponent implements I_CommunicationsInterface so it will act as a CommunicationsInterface bypassing
	//version 102: all the I_CommunicationsInterface calls to the respective active interface (subcomponents)
	//version 103: prevent the calls on communications module component with any interface added (new private method checkActiveInterface())
	//version 104: remove communication_interfaces_list as attribute of CommunicationsModuleComponent and added as subcomponent
	
	private static final int classVersion = 104;
	final static Logger logger = LogManager.getLogger(CommunicationsModuleComponent.class);
	
	private final String COMMUNICATION_INTERFACES_LIST_NAME = "communication_interfaces_list";
	
	/**
	 * @param name
	 * @param id
	 * @param parent
	 * @throws Exception 
	 */
	public CommunicationsModuleComponent(String name, long id, I_InstrumentComponent parent) throws Exception {
		
		super(name, id, parent);
		//communication_interfaces_list es un contenedor subcomponente de this que albergará todas las communication interfaces añadidas 
		super.addSubComponent( new OnlyOneSelected_InstrumentComponentList(COMMUNICATION_INTERFACES_LIST_NAME, 
																					System.currentTimeMillis(), 
																					this));

	}

	/**
	 * @param name
	 * @param id
	 * @param descriptiveTags
	 * @param subComponents
	 * @param parent
	 * @throws Exception 
	 */
	public CommunicationsModuleComponent(String name, long id, ArrayList<String> descriptiveTags,
			HashMap<String,I_InstrumentComponent> subComponents, I_InstrumentComponent parent) throws Exception {
		
		super(name, id, descriptiveTags, subComponents, parent);
		//communication_interfaces_list es un contenedor subcomponente de this que albergará todas las communication interfaces añadidas 
		super.addSubComponent( new OnlyOneSelected_InstrumentComponentList(COMMUNICATION_INTERFACES_LIST_NAME, 
																					System.currentTimeMillis(), 
																					this));
		
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
	
	/**
	 * Método de clase que devuelve la version de esta
	 * @return la version de la clase
	 */
	public static int getClassversion() {
		return classVersion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ************* CommunicationsModuleComponent Instance Description **************** \n");
		
		builder.append("[COMMUNICATION_INTERFACES_LIST_NAME = ").append(COMMUNICATION_INTERFACES_LIST_NAME);
		builder.append(", ").append(super.toString()).append("]\n");
		
		return builder.toString();
	}
	
	

}
