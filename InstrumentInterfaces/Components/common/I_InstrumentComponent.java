/**
 * 
 */
package common;

import java.util.ArrayList;

/**
 * @author david
 * Clase que modela el concepto de componente de un instrumento cualquiera que pueda usarse en cualquier laboratorio.
 * Un componente a su vez puede estar formado por otros componetes (hijos) pero solo ser hijo de un único componente (parent).
 */

public interface I_InstrumentComponent {
	
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
	void addInstrumentComponent(I_InstrumentComponent iC) throws Exception;
	void deleteInstrumentComponent(I_InstrumentComponent iC) throws Exception;
	void deleteInstrumentComponent(String name) throws Exception;
	I_InstrumentComponent getInstrumentComponent(String name) throws Exception;
	ArrayList<I_InstrumentComponent> getAllComponents() throws Exception;

	//state
	void enable(boolean enable) throws Exception;
	boolean isEnable() throws Exception;
	void select(boolean select) throws Exception;
	boolean isSelected() throws Exception;
}
