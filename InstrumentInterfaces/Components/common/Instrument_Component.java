/**
 * 
 */
package common;

import java.util.ArrayList;
import java.util.Iterator;

import interfaces.I_InstrumentComponent;

/**
 * @author david
 *
 */
public abstract class Instrument_Component implements I_InstrumentComponent, Comparable<Instrument_Component>{

	protected String name;
	protected int version;
	protected ArrayList<String> descriptiveTags;
	protected ArrayList<I_InstrumentComponent> components;
	protected I_InstrumentComponent parent;
	
	public Instrument_Component(String name, int version, I_InstrumentComponent parent) {
		super();
		this.name = name;
		this.version = version;
		this.descriptiveTags = new ArrayList<String>();
		this.descriptiveTags.add(name);
		this.components = new ArrayList<I_InstrumentComponent>();
		this.parent = parent;
	}
	
	public Instrument_Component(String name, int version, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent) {
		super();
		this.name = name;
		this.version = version;
		this.descriptiveTags = descriptiveTags;
		this.components = components;
		this.parent = parent;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int getVersion() {
		// TODO Auto-generated method stub
		return version;
	}

	@Override
	public void addDescriptiveTag(String tag) throws Exception {
		if (this.descriptiveTags == null) 
		{
			this.descriptiveTags = new ArrayList<String>();
		}
		this.descriptiveTags.add(tag);
	}

	@Override
	public void addDescriptiveTags(ArrayList<String> tags) throws Exception {
		if (this.descriptiveTags == null) 
		{
			this.descriptiveTags = new ArrayList<String>();
		}
		this.descriptiveTags.addAll(tags);
	}

	@Override
	public ArrayList<String> getDescriptiveTags() throws Exception {
		// TODO Auto-generated method stub
		return this.descriptiveTags;
	}

	@Override
	public boolean hasTag(String tag) throws Exception {
		if (this.descriptiveTags!=null && this.descriptiveTags.contains(tag)) return true;
		else return false;
	}

	@Override
	public void setParentInstrument(I_InstrumentComponent iC) throws Exception {
		this.parent = iC;
	}

	@Override
	public I_InstrumentComponent getParentInstrument() throws Exception {
		// TODO Auto-generated method stub
		return this.parent;
	}

	@Override
	public void addInstrumentComponent(I_InstrumentComponent iC) throws Exception {
		if (this.components == null) 
		{
			this.components = new ArrayList<I_InstrumentComponent>();
		}
		this.components.add(iC);
	}

	@Override
	public void deleteInstrumentComponent(I_InstrumentComponent iC) throws Exception {
		if (this.components != null) 
		{
			this.components.remove(iC);
		}		
	}
	
	@Override
	public void deleteInstrumentComponent(String name) throws Exception {
		if (this.components != null) 
		{
			Iterator<I_InstrumentComponent> it = this.components.iterator();
			while (it.hasNext()) {
				I_InstrumentComponent c = it.next();
				if (c.getName().contentEquals(name)) this.components.remove(c);
			}
		}	
	}
	
	@Override
	public I_InstrumentComponent getInstrumentComponent(String name) throws Exception {
		if (this.components != null) 
		{
			Iterator<I_InstrumentComponent> it = this.components.iterator();
			while (it.hasNext()) {
				I_InstrumentComponent c = it.next();
				if (c.getName().contentEquals(name)) return c;
			}
		}	
		return null;
	}

	@Override
	public ArrayList<I_InstrumentComponent> getAllComponents() throws Exception {
		return this.components;
	}

	@Override
	public int compareTo(Instrument_Component component) {
		if (this.name.equals(component.getName()) && this.version==component.getVersion()) return 0;
		else return -1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Instrument_Component [name=").append(name).append(", version=").append(version)
				.append(", descriptiveTags=").append(descriptiveTags).append(", components=").append(components)
				.append(", parent=").append(parent).append("]");
		return builder.toString();
	}

	
	
	
}
