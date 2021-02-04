package common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Clase que extiende basicamente de AbstractInstrumentComponentList (ArrayList) creada expresamente como contenedor de InstrumentComponents
 * donde solo un componente de la colección puede estar en estado selected = true.
 *  
 * @author david
 *
 */
public class OnlyOneSelected_InstrumentComponentList extends AbstractInstrumentComponentList implements PropertyChangeListener{

	//TODO controlar el cambio "externo" del estado de cualquiera de los elementos de la lista
	// y reaccionar según el cambio
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OnlyOneSelected_InstrumentComponentList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(I_InstrumentComponent c) {
		c.addPropertyChangeListener(this);
		boolean result = super.add(c);
		this.updateListState();
		return result;
	}

	@Override
	public void add(int index, I_InstrumentComponent c) {
		c.addPropertyChangeListener(this);
		super.add(index, c);
		this.updateListState();
	}

	@Override
	public boolean addAll(Collection<? extends I_InstrumentComponent> list) {
		list.forEach((c) -> {c.addPropertyChangeListener(this);});
		boolean result = super.addAll(list);
		this.updateListState();
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends I_InstrumentComponent> list) {
		list.forEach((c) -> {c.addPropertyChangeListener(this);});
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
	public I_InstrumentComponent set(int index, I_InstrumentComponent element) {
		I_InstrumentComponent c = super.set(index, element);
		this.updateListState();
		return c;
	}

	
	/**
	 * Actualiza el estado de la lista para evitar que haya uno y solo un componente en estado selected.
	 * @param e
	 * @throws Exception
	 */
	private void updateListState()
	{
		this.forEach((c) -> {try {
			System.out.println(c.isSelected());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		
		
		try {
			if (this.isEmpty()) 
			{
				System.out.println("La lista está vacia. No es necesario ajustar la lista. ");
				return;
			}
			else if (!hasOneComponentSelected(this)) 
			{
				System.out.println("La lista no tiene un componente en estado selected. Procediendo a ajustar la lista. ");
				//hacemos esto para evitar caer en un bucle sin fin 
				this.get(0).removePropertyChangeListener(this);
				this.get(0).select(true);
				this.get(0).addPropertyChangeListener(this);
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
				//hacemos esto para evitar caer en un bucle sin fin
				c.removePropertyChangeListener(this);
				c.select(false);
				c.addPropertyChangeListener(this);
			}
		}
		//si hemos llegado aqui y se cumple la condicion es porque no hay ningún compoenente en estado selected y la lista no está vacia
		if (!alreadyOneComponentFoundAsSelected)
		{
			//hacemos esto para evitar caer en un bucle sin fin 
			this.get(0).removePropertyChangeListener(this);
			this.get(0).select(true);
			this.get(0).addPropertyChangeListener(this);
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
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println("El estado de un componente ha cambiado. Procedemos a ajustar la lista si es necesario");
		this.updateListState();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlyOneSelected_InstrumentComponentList:\n");
		this.forEach((c) -> {builder.append(c.toString()).append("\n");});
		return builder.toString();
	}
	
	
	

}
