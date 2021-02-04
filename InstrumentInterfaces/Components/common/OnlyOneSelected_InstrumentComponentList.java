package common;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class OnlyOneSelected_InstrumentComponentList extends AbstractInstrumentComponentList {

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
		try {
			this.updateComponentStateBeforeAdd(c);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return super.add(c);
	}

	@Override
	public void add(int index, I_InstrumentComponent c) {
		try {
			this.updateComponentStateBeforeAdd(c);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		super.add(index, c);
	}

	@Override
	public boolean addAll(Collection<? extends I_InstrumentComponent> list) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends I_InstrumentComponent> c) {
		return false;
	}

	@Override
	public I_InstrumentComponent remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return super.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean removeIf(Predicate<? super I_InstrumentComponent> filter) {
		// TODO Auto-generated method stub
		return super.removeIf(filter);
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		super.removeRange(fromIndex, toIndex);
	}

	@Override
	public void replaceAll(UnaryOperator<I_InstrumentComponent> operator) {
		// TODO Auto-generated method stub
		super.replaceAll(operator);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return super.retainAll(c);
	}

	@Override
	public I_InstrumentComponent set(int index, I_InstrumentComponent element) {
		// TODO Auto-generated method stub
		return super.set(index, element);
	}

	
	
	/**
	 * Actualiza el estado de un componente antes de añadirlo a la lista si esta está vacia o 
	 * si tiene elementos pero ninguno de ellos se encuentra selected.
	 * @param e
	 * @throws Exception
	 */
	private void updateComponentStateBeforeAdd(I_InstrumentComponent e) throws Exception
	{
		try {
			if (this.isEmpty() || !hasOneComponentSelected(this)) e.select(true);
			else e.select(false);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	
	

}
