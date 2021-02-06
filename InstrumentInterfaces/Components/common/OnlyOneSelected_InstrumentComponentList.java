package common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.log4j.*;

/**
 * Clase que extiende basicamente de AbstractInstrumentComponentList (ArrayList) creada expresamente como contenedor de InstrumentComponents
 * donde solo un componente de la colección puede estar en estado selected = true.
 *  
 * @author david
 *
 */
public class OnlyOneSelected_InstrumentComponentList extends AbstractInstrumentComponentList implements PropertyChangeListener{
	

	public static final int classVersion = 1;
	private static final long serialVersionUID = 1L;
	private I_InstrumentComponent selectedComponent = null;
	
	private static org.apache.log4j.Logger log = null;
	log = Logger.


	public OnlyOneSelected_InstrumentComponentList() {
		super();
	}

	@Override
	/**
	 * Añade un componente a la lista. Previamente cambia su estado a select = false
	 */
	public boolean add(I_InstrumentComponent c) {
		this.setSelected(c, false);
		boolean result = super.add(c);
		this.updateListState();
		return result;
	}

	@Override
	public void add(int index, I_InstrumentComponent c) {
		this.setSelected(c, false);
		super.add(index, c);
		this.updateListState();
	}

	@Override
	public boolean addAll(Collection<? extends I_InstrumentComponent> list) {
		list.forEach((c) -> {this.setSelected(c, false);});
		boolean result = super.addAll(list);
		this.updateListState();
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends I_InstrumentComponent> list) {
		list.forEach((c) -> {this.setSelected(c, false);});
		boolean result = super.addAll(index,list);
		this.updateListState();
		return result;
	}

	@Override
	public I_InstrumentComponent remove(int index) {
		I_InstrumentComponent c = super.remove(index);
		c.removePropertyChangeListener(this);
		this.updateListState();
		return c;
	}

	@Override
	public boolean remove(Object o) {
		((I_InstrumentComponent)o).removePropertyChangeListener(this);
		boolean result = super.remove(o);
		this.updateListState();
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> list) {
		list.forEach((c) -> {((I_InstrumentComponent) c).removePropertyChangeListener(this);});
		boolean result = super.removeAll(list);
		this.updateListState();
		return result;
	}

	@Override
	public boolean removeIf(Predicate<? super I_InstrumentComponent> filter) {
		boolean result = super.removeIf(filter);
		this.updateListState();
		return result;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		super.removeRange(fromIndex, toIndex);
		this.updateListState();
	}

	@Override
	public void replaceAll(UnaryOperator<I_InstrumentComponent> operator) {
		super.replaceAll(operator);
		this.updateListState();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = super.retainAll(c);
		this.updateListState();
		return result;
	}

	@Override
	public I_InstrumentComponent set(int index, I_InstrumentComponent newComponent) {
		this.setSelected(newComponent, false);
		I_InstrumentComponent replacedComponent = super.set(index, newComponent);
		this.updateListState();
		return replacedComponent;
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
		//System.out.println("Ajustando la lista....... ");
		
		try {
			if (this.isEmpty()) 
			{
				System.out.println("La lista está vacia. No es necesario ajustar la lista. ");
				return;
			}
			else if (!hasOneComponentSelected(this)) 
			{
				System.out.println("La lista no tiene un componente en estado selected. Procediendo a ajustar la lista. ");
				//Aqui el orden de las operaciones es importante especialmente en el caso que el 
				//actual componente y el nuevo componente a seleccionar sean el mismo
				this.setSelectedComponent(this.get(0));
				this.setSelected(this.get(0), true);
				
			}
			else if (hasOnlyOneComponentSelected(this)) 
			{
				System.out.println("La lista solo tiene un componente en estado selected. No es necesario ajustar la lista. ");
				return;
			}
			else 
			{
				System.out.println("La lista tiene más de un componente en estado selected. Procediendo a ajustar la lista. ");
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
		
		if (this.isEmpty()) return;
		
		boolean alreadyOneComponentFoundAsSelected = false;
		Iterator<? extends I_InstrumentComponent> it = this.iterator();
		while (it.hasNext())
		{
			I_InstrumentComponent c = it.next();
			if (c.isSelected() & !alreadyOneComponentFoundAsSelected) alreadyOneComponentFoundAsSelected = true;
			else if (c.isSelected() & alreadyOneComponentFoundAsSelected) 
			{
				this.setSelected(c, false);
			}
		}
		//si hemos llegado aqui y se cumple la condicion es porque no hay ningún compoenente en estado selected y la lista no está vacia
		if (!alreadyOneComponentFoundAsSelected)
		{
			this.setSelected(this.get(0), true);
			this.setSelectedComponent(this.get(0));
		}
	}
	
	/**
	 * Busca en la lista de componentes si uno y solo uno está seleccionado.
	 * @return true si uno y solo uno de todos de los componentes de la lista está esn estado selected , false otherwise. 
	 * @throws Exception
	 */
	public static boolean hasOnlyOneComponentSelected(Collection<? extends I_InstrumentComponent> list) throws Exception
	{
		boolean alreadyOneComponentFoundAsSelected = false;
		Iterator<? extends I_InstrumentComponent> it = list.iterator();
		while (it.hasNext())
		{
			I_InstrumentComponent c = it.next();
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
	public static boolean hasOneComponentSelected(Collection<? extends I_InstrumentComponent> list) throws Exception
	{
		Iterator<? extends I_InstrumentComponent> it = list.iterator();
		while (it.hasNext())
		{
			if (it.next().isSelected()) return true;
		}
		return false;
	}

	@Override
	/**
	 * Callback function que se ejecuta siempre que el estado de uno de los componentes de la lista cambia la propiedad select
	 */
	public void propertyChange(PropertyChangeEvent e) {
		try {
			System.out.println("El estado de un componente de la lista ha cambiado. Procedemos a ajustar la lista si es necesario");
			//System.out.println("Causante ----->" + e.getSource().toString());
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
		builder.append("OnlyOneSelected_InstrumentComponentList:\n");
		this.forEach((c) -> {builder.append(c.toString()).append("\n");});
		return builder.toString();
	}
	
	
	

}
