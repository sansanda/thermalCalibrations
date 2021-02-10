/**
 * 
 */
package communications;

import java.util.ArrayList;

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
	public void addPort(String type) {
		
	}

	@Override
	public void deletePort(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public I_InstrumentComponent getPort(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public I_InstrumentComponent getSelectedPort() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
