/**
 * 
 */
package communications;

import java.util.ArrayList;

import java.util.Iterator;

import collections.OnlyOneSelected_InstrumentComponentList;
import common.I_InstrumentComponent;
import common.InstrumentComponent;

/**
 * @author david
 *
 */
public class CommunicationsModuleComponent extends InstrumentComponent implements I_CommunicationsModule, I_CommunicationsInterface{

	private static final int classVersion = 103;
	
	private OnlyOneSelected_InstrumentComponentList communicationInterfaces = null;
	
	/**
	 * @param name
	 * @param id
	 * @param parent
	 */
	public CommunicationsModuleComponent(String name, long id, I_InstrumentComponent parent) {
		super(name, id, parent);
		this.communicationInterfaces = new OnlyOneSelected_InstrumentComponentList();
	}

	/**
	 * @param name
	 * @param id
	 * @param descriptiveTags
	 * @param components
	 * @param parent
	 */
	public CommunicationsModuleComponent(String name, long id, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent) {
		super(name, id, descriptiveTags, components, parent);
		this.communicationInterfaces = new OnlyOneSelected_InstrumentComponentList();
	}

	@Override
	public void addInterface(I_InstrumentComponent commInterface) throws Exception {	
		I_CommunicationsInterface ci = this.hasInterface(((I_CommunicationsInterface)commInterface).getStandard());
		if (ci!=null) this.removeInterface(ci);
		this.communicationInterfaces.add(commInterface);
	}

	@Override
	public void removeInterface(I_CommunicationsInterface commInterface) throws Exception{
		this.communicationInterfaces.remove(commInterface);
	}

	@Override
	public I_CommunicationsInterface getInterface(String standard) throws Exception {
		return this.hasInterface(standard);
	}

	@Override
	public I_CommunicationsInterface getActiveInterface() throws Exception {
		return (I_CommunicationsInterface) this.communicationInterfaces.getSelectedComponent();
	}
	
	private I_CommunicationsInterface hasInterface (String standard) throws Exception
	{
		Iterator<I_InstrumentComponent> i = this.communicationInterfaces.iterator();
		I_CommunicationsInterface ci = null;
		while (i.hasNext())
		{
			ci = (I_CommunicationsInterface)i.next();
			if (ci.getStandard().equals(standard)) return ci;
		}
		return null;
	}

	@Override
	/**
	 * Devuelve el standard de la interface activa en el momento de hacer la llamada
	 */
	public String getStandard() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getStandard();
	}

	@Override
	/**
	 * Devuelve la dirección de la interface activa en el momento de hacer la llamada
	 */
	public String getAddress() throws Exception {
		this.checkActiveInterface();
		return this.getActiveInterface().getAddress();
	}

	@Override
	/**
	 * Actualiza la dirección de la interface activa en el momento de hacer la llamada
	 * @param address String con la nueva direccion
	 */
	public void setAddress(String address) throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().setAddress(address);
		
	}

	@Override
	/**
	 * Abre la interface activa 
	 */
	public void open() throws Exception {
		this.checkActiveInterface();
		this.getActiveInterface().open();
		
	}

	@Override
	/**
	 * Cierra la interface activa
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommunicationsModuleComponent [name=").append(name).append(", id=").append(id).append(", enable=").append(enable)
				.append(", selected=").append(selected).append(",\n descriptiveTags=").append(descriptiveTags)
				.append(", subcomponents=").append(subcomponents).append(", parent=").append(parent)
				.append(", communicationInterfaces=").append(communicationInterfaces).append("]");
		return builder.toString();
	}
	
	private void checkActiveInterface() throws Exception
	{
		if (this.communicationInterfaces.isEmpty()) throw new Exception ("No any inteface added in communications module component");
	}
	//version 102: CommunicationsModuleComponent implements I_CommunicationsInterface so it will act as a CommunicationsInterface bypassing
	//version 102: all the I_CommunicationsInterface calls to the respective active interface (subcomponents)
	//version 103: prevent the calls on communications module component with any interface added (new private method checkActiveInterface)
}
