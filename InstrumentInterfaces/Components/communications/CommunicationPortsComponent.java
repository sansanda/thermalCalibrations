/**
 * 
 */
package communications;

import java.util.ArrayList;

import common.I_ComponentButtonGroup;
import common.I_InstrumentComponent;
import common.InstrumentComponent;

/**
 * @author david
 *
 */
public class CommunicationPortsComponent extends InstrumentComponent implements I_ComponentButtonGroup{

	//TODO rehacer por completo adaptandola a OnlyOneSelected_InstrumentComponentList
	
	public static String[] protocols = {"GPIB","RS232","LAN"};
	private static int classVersion = 0;
	
	/**
	 * @param name
	 * @param id
	 * @param parent
	 */
	public CommunicationPortsComponent(String name, long id, I_InstrumentComponent parent) {
		super(name, id, parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param id
	 * @param descriptiveTags
	 * @param components
	 * @param parent
	 */
	public CommunicationPortsComponent(String name, long id, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent) {
		super(name, id, descriptiveTags, components, parent);
		// TODO Auto-generated constructor stub
	}
	
	public static int getVersion() throws Exception {
		return classVersion;
	}

	public static void setVersion(int version) throws Exception {
		classVersion = version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Communications_Component []");
		return builder.toString();
	}

	@Override
	public void setSelected(I_InstrumentComponent ic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public I_InstrumentComponent getSelected() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
