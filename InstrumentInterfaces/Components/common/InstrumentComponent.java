/**
 * 
 */
package common;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author david
 *
 */
public abstract class InstrumentComponent implements I_InstrumentComponent, Comparable<I_InstrumentComponent>{

	//version 102:  changed constructor for including enable and selected parameters 
	//version 101:  change subcomponents container for working with a hashmap intead of an arraylist
	//				also we have changed related methods and I_InstrumentComponent interface
	
	private static final int classVersion = 102;
	
	protected String name;
	protected long id;
	protected boolean enable;
	protected boolean selected;
	protected ArrayList<String> descriptiveTags;
	protected HashMap<String,I_InstrumentComponent> subcomponents;
	protected I_InstrumentComponent parent;
	private PropertyChangeSupport support;

	public InstrumentComponent(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		
		super();
		this.name = name;
		this.id = id;
		this.parent = parent;
		this.enable = enable;
		this.selected = selected;
		this.descriptiveTags = new ArrayList<String>();
		this.descriptiveTags.add(name);
		this.subcomponents = new HashMap<String,I_InstrumentComponent>();
		this.support = new PropertyChangeSupport(this);
	}
	
	public InstrumentComponent(String name, long id,I_InstrumentComponent parent, boolean enable, boolean selected, 
			ArrayList<String> descriptiveTags,
			HashMap<String,I_InstrumentComponent> subcomponents) {
		this(name, id, parent, enable, selected);
		this.descriptiveTags = descriptiveTags;
		this.subcomponents = subcomponents;
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
		boolean oldValue = this.selected;
		this.selected = select;
		support.firePropertyChange("selected", oldValue, this.selected);
		
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
	public void addSubComponent(I_InstrumentComponent iC) throws Exception {
		if (this.subcomponents == null) 
		{
			this.subcomponents = new HashMap<String,I_InstrumentComponent>();
		}
		this.subcomponents.put(iC.getName(), iC);
	}
	
	@Override
	public I_InstrumentComponent removeSubComponent(String componentName) throws Exception {
		I_InstrumentComponent removedComponent = null;
		if (this.subcomponents != null) removedComponent = this.subcomponents.remove(componentName);
		return removedComponent;
	}
	
	@Override
	public void removeAllSubComponent() throws Exception {
		if (this.subcomponents != null) this.subcomponents.clear();
	}
	
	@Override
	/**
	 * Devuelve el subComponente identificado (nombre) por el String del parámetro de entrada
	 * @return el subcomponente con dicho nombre o null si no existe
	 */
	public I_InstrumentComponent getSubComponent(String componentName) throws Exception {
		I_InstrumentComponent obtainedComponent = null;
		if (this.subcomponents != null) obtainedComponent = this.subcomponents.get(componentName);
		return obtainedComponent;
	}

	@Override
	public HashMap<String, I_InstrumentComponent> getAllSubComponents() throws Exception {
		return this.subcomponents;
	}
	
	@Override
	/**
	 * @return true si el componente tiene subcomponentes, false en caso contrario.
	 */
	public boolean hasSubcomponents() throws Exception {
		return !this.subcomponents.isEmpty();
	}

	@Override
	public int compareTo(I_InstrumentComponent component) {
		if (this.name.equals(component.getName())) return 0;
		else return -1;
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    
	@Override
	public String toString() {
				
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ***************** InstrumentComponent Instance Description *********************** \n");
		builder.append(" [name = ").append(name);
		builder.append(", id = ").append(id);
		builder.append(", enable = ").append(enable);
		builder.append(", selected = ").append(selected);
		builder.append(", descriptiveTags = ").append(descriptiveTags);
		
		builder.append("\n, subcomponents = ");
		
		Iterator it = subcomponents.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry entry = (Map.Entry)it.next();
	        builder.append(((InstrumentComponent)entry.getValue()).getName() + " -- ");
	    }
		
	    builder.delete(builder.length()-4, builder.length());
		builder.append("\n, parent = ").append(parent).append("]");
		
		return builder.toString();
	}

	public static int getClassversion() {
		return classVersion;
	}

	
	
	
}
