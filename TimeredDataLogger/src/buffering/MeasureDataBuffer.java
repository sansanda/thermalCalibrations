package buffering;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que es un wrapper de ArrayList<Double> para descargar de resposabilidades a las clases TakeMeasureTask
 * de controlar el llenado de una ArrayList de datos de medida.
 * 
 * @author DavidS
 *
 */
public class MeasureDataBuffer{

	private ArrayList<Double> 	data;
	private ArrayList<Double> 	tStamp;
	private ArrayList<Integer> 	readingNumber;
	
	private PropertyChangeSupport support = null;
	
	public MeasureDataBuffer(ArrayList<Double> data, ArrayList<Double> tStamp, ArrayList<Integer> readingNumber) {
		
		super();
		this.data = data;
		this.tStamp = tStamp;
		this.readingNumber = readingNumber;
		this.support  = new PropertyChangeSupport(this);
	}

	public void addElements(double dataElement, double tStampElement, int rNumberElement)
	{
		
		this.data.add(dataElement);
		this.tStamp.add(tStampElement);
		this.readingNumber.add(rNumberElement);
		//Aqui debemos enviar un evento cada vez que se añade un elemento a la lista
		
		//Por cada elemento
		Object newValue = this.data.get(this.data.size()-1);
		Object oldValue = (this.data.size()<=1)? newValue:this.data.get(this.data.size()-2);
		
		System.out.println("firing event!!!!!!!!!!!!!!!!!!!!!");
		this.support.firePropertyChange(new PropertyChangeEvent(this, "newData", oldValue, newValue));
		
	}
	
	public List<Double> 	dataSubList(int fromIndex, int toIndex) 			{return  this.data.subList(fromIndex, toIndex);}
	public List<Double>		tStampSubList(int fromIndex, int toIndex) 			{return  this.tStamp.subList(fromIndex, toIndex);}
	public List<Integer> 	readingNumberSubList(int fromIndex, int toIndex) 	{return  this.readingNumber.subList(fromIndex, toIndex);}	
	
	public int size() {return this.data.size();}
	
	public void clearAll()
	{
		this.data.clear();
		this.tStamp.clear();
		this.readingNumber.clear();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }

    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasureDataBuffer \n[\ndata=").append(data).append(", \ntStamp=").append(tStamp)
				.append(", \nreadingNumber=").append(readingNumber).append("\n]");
		return builder.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
