/**
 * 
 */
package interfaces;

import java.util.ArrayList;

/**
 * @author david
 * Clase que modela el concepto de componente de un instrumento cualquiera que pueda usarse en cualquier laboratorio.
 * Un componente a su vez puede estar formado por otros componetes (hijos) pero solo ser hijo de un único componente (parent).
 */

public interface I_InstrumentComponent {
	
	//Naming
	void setName(String name);
	String getName();
	
	//Versioning
	void setVersion(int version);
	int getVersion();
	
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
	
	
	
}
