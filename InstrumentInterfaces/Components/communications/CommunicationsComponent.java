/**
 * 
 */
package communications;

import java.util.ArrayList;


import common.I_InstrumentComponent;
import common.InstrumentComponent;
import common.OnlyOneSelected_InstrumentComponentList;

/**
 * @author david
 *
 */
public class CommunicationsComponent extends InstrumentComponent implements I_CommunicationsComponent{

	public static String[] portTypes = {"GPIB","RS232","LAN"};
	private static final int classVersion = 0;
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
	public void addPort(I_InstrumentComponent ic) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deletePort(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePort(I_InstrumentComponent ic) {
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
