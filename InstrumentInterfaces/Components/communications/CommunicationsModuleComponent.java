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
public class CommunicationsModuleComponent extends InstrumentComponent implements I_CommunicationsModule{

	private static final int classVersion = 101;
	
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommunicationsModuleComponent [name=").append(name).append(", id=").append(id).append(", enable=").append(enable)
				.append(", selected=").append(selected).append(",\n descriptiveTags=").append(descriptiveTags)
				.append(", subcomponents=").append(subcomponents).append(", parent=").append(parent)
				.append(", communicationInterfaces=").append(communicationInterfaces).append("]");
		return builder.toString();
	}
	
	
}
