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
public class CommunicationsComponent extends InstrumentComponent implements I_CommunicationsComponent{

	private static final int classVersion = 100;
	
	private OnlyOneSelected_InstrumentComponentList portsList = null;
	
	/**
	 * @param name
	 * @param id
	 * @param parent
	 */
	public CommunicationsComponent(String name, long id, I_InstrumentComponent parent) {
		super(name, id, parent);
		this.portsList = new OnlyOneSelected_InstrumentComponentList();
	}

	/**
	 * @param name
	 * @param id
	 * @param descriptiveTags
	 * @param components
	 * @param parent
	 */
	public CommunicationsComponent(String name, long id, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent) {
		super(name, id, descriptiveTags, components, parent);
		this.portsList = new OnlyOneSelected_InstrumentComponentList();
	}

	@Override
	public void addPort(I_CommPortComponent port) {
		Object c = this.(JavaComm_SerialPort_Component);
		
		if (port instanceof JavaComm_SerimdalPort_Component) this.deletePort(port);
		this.portsList.add(port);
	}

	@Override
	public void deletePort(I_CommPortComponent port) {
		this.portsList.remove(port);
		
	}

	@Override
	public I_CommPortComponent getPort(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_CommPortComponent getSelectedPort() {
		// TODO Auto-generated method stub
		return null;
	}

	public static int getClassversion() {
		return classVersion;
	}
	
	private I_CommPortComponent (String _interface)
	{
		Iterator<I_InstrumentComponent> i = this.portsList.iterator();
		while(i.hasNext())
		{
			I_InstrumentComponent c = i.next();
			if (cls.isInstance(obj)) return c;
		}
	}
}
