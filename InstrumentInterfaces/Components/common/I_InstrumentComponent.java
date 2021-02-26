/**
 * 
 */
package common;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que modela el concepto de componente de un instrumento cualquiera que pueda usarse en cualquier laboratorio.
 * Un componente a su vez puede estar formado por otros componetes (hijos) pero solo ser hijo de un único componente (parent).
 * @author david
 */

public interface I_InstrumentComponent{
	
	//Identification
	void setName(String name);
	String getName();
	void setId(long id);
	long getId();
	
	//Search aids
	void addDescriptiveTag(String tag) throws Exception;
	void addDescriptiveTags(ArrayList<String> tags) throws Exception;
	ArrayList<String> getDescriptiveTags() throws Exception;
	boolean hasTag(String tag) throws Exception;

	//RelationShips
	void setParentInstrument(I_InstrumentComponent iC) throws Exception;
	I_InstrumentComponent getParentInstrument() throws Exception;
	 
	//Components
	void addSubComponent(I_InstrumentComponent iC) throws Exception;
	I_InstrumentComponent removeSubComponent(String componentName) throws Exception;
	void removeAllSubComponent() throws Exception;
	I_InstrumentComponent getSubComponent(String componentName) throws Exception;
	HashMap<String, I_InstrumentComponent> getAllSubComponents() throws Exception;
	boolean hasSubcomponents() throws Exception;

	//state
	void enable(boolean enable) throws Exception;
	boolean isEnable() throws Exception;
	void select(boolean select) throws Exception;
	boolean isSelected() throws Exception;
	
	void addPropertyChangeListener(PropertyChangeListener pcl);
    void removePropertyChangeListener(PropertyChangeListener pcl);
    
}
