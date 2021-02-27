package collections;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.AbstractInstrumentComponentList;
import common.I_InstrumentComponent;
import common.InstrumentComponent;

/**
 * Clase que extiende basicamente de AbstractInstrumentComponentList (ArrayList) creada expresamente como contenedor de InstrumentComponents
 * donde solo un componente de la colección puede estar en estado selected = true.
 *  
 * @author david
 *
 */
public class OnlyOneSelected_InstrumentComponentList extends InstrumentComponent implements PropertyChangeListener{
	
	//version 103: 	Vaersion 103 donde se evita extender de ArrayList y se pasa a extender InstrumentComponent para trabajar 
	//				según la filosofia de subcomponentes
	
	final static Logger logger = LogManager.getLogger(OnlyOneSelected_InstrumentComponentList.class);
	public static final int classVersion = 103;
	private static final long serialVersionUID = 1L;
	private I_InstrumentComponent selectedComponent = null;
	

	public OnlyOneSelected_InstrumentComponentList(String name, long id, I_InstrumentComponent parent) {
		super(name, id, parent);
	}

	/**
	 * Añade un componente a la lista. Previamente cambia su estado a select = false
	 * Después de añadir el componente realiza una actualización del los componentes de la lista para 
	 * poder mantenerla según la pólitica only one selected.
	 * Es importante seleccionar un nombre adecuado para el componente que se va a añadir ya que 
	 * dicho nombre será referencia a la hora de buscarlo.
	 */
	
	public void addComponent(I_InstrumentComponent c) throws Exception{
		this.setSelected(c, false);
		this.addSubComponent(c);
		this.updateListState();
	}

	/**
	 * Elimina un componente a la lista. 
	 * Después de eliminar el componente realiza una actualización del los componentes de la lista para 
	 * poder mantenerla según la pólitica only one selected.
	 * @param String componentName con el nombre del componente que se desea eliminar de la lista
	 * @return Una referencia al componente eliminado de la lista, null en caso que el componente no exista en la lista
	 */
	
	public I_InstrumentComponent removeComponent(String componentName) throws Exception {
		I_InstrumentComponent removedComponent = this.removeSubComponent(componentName);
		if (removedComponent!= null)
		{
			removedComponent.removePropertyChangeListener(this);
			this.updateListState();
		}
		
		return removedComponent;
	}

	public void removeAllComponents() throws Exception {
		this.removeAllSubComponent();
		this.updateListState();
	}


	/**
	 * Retorna el elemento de la lista que actualmente esta en estado selected.
	 * Solo un elemento de la lista puede estar en dicho estado
	 * @return el elemento de tipo I_InstrumentComponent que tiene su atributo select = true
	 */
	public I_InstrumentComponent getSelectedComponent() {
		return selectedComponent;
	}

	/**
	 * Asigna al atributo selectedComponent el parametro de entrada.
	 * @param selectedComponent the selectedComponent to set
	 */
	private void setSelectedComponent(I_InstrumentComponent selectedComponent) {
		if (this.selectedComponent != null & selectedComponent.equals(this.selectedComponent)) return;
		if (this.selectedComponent != null) this.setSelected(this.selectedComponent, false); //Solo en caso lista no vacia
																												
		this.selectedComponent = selectedComponent;
	}
	
	/**
	 * Cambia, de manera controlada, el atributo select de un componente.
	 * @param c es el objeto de tipo I_InstrumentComponent objeto del cambio 
	 * @param selected es el nuevo valor del atributo select
	 */
	private void setSelected(I_InstrumentComponent c, boolean selected)
	{
		try {
			//hacemos esto para evitar caer en un bucle sin fin 
			c.removePropertyChangeListener(this);
			c.select(selected);
			c.addPropertyChangeListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Actualiza el estado de la lista para asegurar que haya uno y solo un componente en estado selected, si la lista no está vacia.
	 * @param e
	 * @throws Exception
	 */
	private void updateListState()
	{
		//logger.info("Ajustando la lista....... ");
		
		try {
			if (this.subcomponents.isEmpty()) 
			{
				logger.info("La lista está vacia. No es necesario ajustar la lista.");
				return;
			}
			else if (!hasOneComponentSelected()) 
			{
				logger.info("La lista no tiene un componente en estado selected. Procediendo a ajustar la lista. ");
				//Aqui el orden de las operaciones es importante especialmente en el caso que el 
				//actual componente y el nuevo componente a seleccionar sean el mismo
				
				Iterator<Entry<String, I_InstrumentComponent>> it = this.subcomponents.entrySet().iterator();
				I_InstrumentComponent c = it.next().getValue();
				this.setSelectedComponent(c);
				this.setSelected(c, true);
				
			}
			else if (hasOnlyOneComponentSelected()) 
			{
				logger.info("La lista solo tiene un componente en estado selected. No es necesario ajustar la lista. ");
				return;
			}
			else 
			{
				logger.info("La lista tiene más de un componente en estado selected. Procediendo a ajustar la lista. ");
				//En este caso hay más de un componente en estado selected lo que deja la lista en estado inconsistente.
				//La solución es dejar la lista con un solo componente en estado selected
				this.setOnlyOneComponentSelectedInList();	
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Método que repasa todos los componentes de la lista y asegura que, después de ejecutar dicho método la lista solo contendrá un unico componente en
	 * estado selected.
	 * Para ello recorre por completo la lista y conserva en estado selected (select = true) el primer elemento que encuentra que cumple dicha condición.
	 * Una vez hecho esto, el resto de componentes que quedan por visitar en la lista serán cambiados a estado select = false en caso que se encontraran como select = true.
	 * Si no hay nigún elemento en la lista en estado select = true entonces, por defecto, se pone a dicho estado el primer elemento de la lista.
	 * @throws Exception
	 */
	private void setOnlyOneComponentSelectedInList() throws Exception
	{
		
		if (this.subcomponents.isEmpty()) return;
		
		boolean alreadyOneComponentFoundAsSelected = false;
		
		Iterator<Entry<String, I_InstrumentComponent>> it = this.subcomponents.entrySet().iterator();
		
		
		while (it.hasNext())
		{
			Entry<String, I_InstrumentComponent> entryPair = it.next();
			
			I_InstrumentComponent c = entryPair.getValue();
			
			if (c.isSelected() & !alreadyOneComponentFoundAsSelected) alreadyOneComponentFoundAsSelected = true;
			else if (c.isSelected() & alreadyOneComponentFoundAsSelected) 
			{
				this.setSelected(c, false);
			}
		}
		
		//si hemos llegado aqui y se cumple la condicion es porque no hay ningún compoenente en estado selected y la lista no está vacia
		
		if (!alreadyOneComponentFoundAsSelected)
		{
			it = this.subcomponents.entrySet().iterator();
			I_InstrumentComponent c = it.next().getValue();
			this.setSelected(c, true);
			this.setSelectedComponent(c);
		}
	}
	
	/**
	 * Busca en la lista de componentes si uno y solo uno está seleccionado.
	 * @return true si uno y solo uno de todos de los componentes de la lista está esn estado selected , false otherwise. 
	 * @throws Exception
	 */
	private boolean hasOnlyOneComponentSelected() throws Exception
	{
		boolean alreadyOneComponentFoundAsSelected = false;
		
		Iterator<Entry<String, I_InstrumentComponent>> it = this.subcomponents.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, I_InstrumentComponent> entryPair = it.next();
			
			I_InstrumentComponent c = entryPair.getValue();
			if (c.isSelected() & !alreadyOneComponentFoundAsSelected) alreadyOneComponentFoundAsSelected = true;
			else if (c.isSelected() & alreadyOneComponentFoundAsSelected) return false;
		}
		return alreadyOneComponentFoundAsSelected;
	}
	
	/**
	 * Busca en la lista de componentes si al menos uno está seleccionado.
	 * No nos preocuparemos del posible caso de mas de uno seleccionado ya que 
	 * esto se resuelve en momento de insercion.
	 * @return true if finds a component of the list that is selected, false otherwise. 
	 * @throws Exception
	 */
	private boolean hasOneComponentSelected() throws Exception
	{
		Iterator<Entry<String, I_InstrumentComponent>> it = this.subcomponents.entrySet().iterator();
		
		while (it.hasNext())
		{
			Entry<String, I_InstrumentComponent> entryPair = it.next();
			
			if (entryPair.getValue().isSelected()) return true;
		}
		return false;
	}

	@Override
	/**
	 * Callback function que se ejecuta siempre que el estado de uno de los componentes de la lista cambia la propiedad select
	 */
	public void propertyChange(PropertyChangeEvent e) {
		try {
			logger.info("El estado de un componente de la lista ha cambiado. Procedemos a ajustar la lista si es necesario");
			//logger.info("Causante ----->" + e.getSource().toString());
			I_InstrumentComponent source = (I_InstrumentComponent)e.getSource();
			if (source.isSelected()==true)
			{
				//Si se cumple esta condición entonces el objeto causante ha cambiado a estado select = true
				this.setSelectedComponent(source);
				
			}
			//Si el componente ha pasado a esado select = false no hacemos nada, solo actualizar el estado de la lista
			this.updateListState();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlyOneSelected_InstrumentComponentList [selectedComponent=").append(selectedComponent)
				.append(", name=").append(name).append(", id=").append(id).append(", enable=").append(enable)
				.append(", selected=").append(selected).append(", descriptiveTags=").append(descriptiveTags)
				.append(", subcomponents=").append(subcomponents).append(", parent=").append(parent).append("]");
		return builder.toString();
	}

	

	
	
	
	
	

}
