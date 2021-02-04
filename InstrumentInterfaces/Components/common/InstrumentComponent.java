/**
 * 
 */
package common;

import java.util.ArrayList;

import java.util.Iterator;

/**
 * @author david
 *
 */
public abstract class InstrumentComponent implements I_InstrumentComponent, Comparable<I_InstrumentComponent>{

	protected String name;
	protected long id;
	protected boolean enable;
	protected boolean selected;
	protected ArrayList<String> descriptiveTags;
	protected ArrayList<I_InstrumentComponent> subcomponents;
	protected I_InstrumentComponent parent;
	
	public InstrumentComponent(String name, long id, I_InstrumentComponent parent) {
		super();
		this.name = name;
		this.id = id;
		this.enable = true;
		this.selected = false;
		this.descriptiveTags = new ArrayList<String>();
		this.descriptiveTags.add(name);
		this.subcomponents = new ArrayList<I_InstrumentComponent>();
		this.parent = parent;
	}
	
	public InstrumentComponent(String name, long id, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent) {
		super();
		this.name = name;
		this.id = id;
		this.enable = true;
		this.selected = false;
		this.descriptiveTags = descriptiveTags;
		this.subcomponents = components;
		this.parent = parent;
	}
	
	//non-static methods
	
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
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	
	@Override
	public void enable(boolean enable) throws Exception {
		this.enable = enable;
	}

	@Override
	public boolean isEnable() throws Exception {
		return enable;
	}

	
	@Override
	public void select(boolean select) throws Exception {
		this.selected = select;
	}

	@Override
	public boolean isSelected() throws Exception {
		return this.selected;
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
		if (this.subcomponents == null) 
		{
			this.subcomponents = new ArrayList<I_InstrumentComponent>();
		}
		this.subcomponents.add(iC);
	}

	@Override
	public void deleteInstrumentComponent(I_InstrumentComponent iC) throws Exception {
		if (this.subcomponents != null) 
		{
			this.subcomponents.remove(iC);
		}		
	}
	
	@Override
	public void deleteInstrumentComponent(String name) throws Exception {
		if (this.subcomponents != null) 
		{
			Iterator<I_InstrumentComponent> it = this.subcomponents.iterator();
			while (it.hasNext()) {
				I_InstrumentComponent c = it.next();
				if (c.getName().contentEquals(name)) this.subcomponents.remove(c);
			}
		}	
	}
	
	@Override
	public I_InstrumentComponent getInstrumentComponent(String name) throws Exception {
		if (this.subcomponents != null) 
		{
			Iterator<I_InstrumentComponent> it = this.subcomponents.iterator();
			while (it.hasNext()) {
				I_InstrumentComponent c = it.next();
				if (c.getName().contentEquals(name)) return c;
			}
		}	
		return null;
	}

	@Override
	public ArrayList<I_InstrumentComponent> getAllComponents() throws Exception {
		return this.subcomponents;
	}

	@Override
	public int compareTo(I_InstrumentComponent component) {
		if (this.name.equals(component.getName()) && this.id==component.getId()) return 0;
		else return -1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Instrument_Component [name=").append(name).append(", id=").append(id)
				.append(", descriptiveTags=").append(descriptiveTags).append(", components=").append(subcomponents)
				.append(", parent=").append(parent).append("]");
		return builder.toString();
	}

	
	
	
}
