package statistics;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * 
 * @author DavidS
 *
 * @param <T>
 * @param <V>
 */
public abstract class SamplesBuffer<T,V> implements ISamplesBuffer<T, V> {
	
	private Vector<V> samples = null;
	private T samplePeriod = null;
	
	public SamplesBuffer(T _samplePeriod) {
		samples = new Vector<V>();
		samplePeriod = _samplePeriod;
	}
	@Override
	public void clear() {
		samples.clear();
	}

	@Override
	public boolean addSample(V sample, int sampleMultipleNumber) {
		samples.addElement(sample);	
		long samplesCount = getSamplesCount();
				
		if ((samplesCount%sampleMultipleNumber)==0 && samplesCount>0)
		{
			//El buffer contiene un numero de samples igual a un multiplo de sampleMultipleNumber y no está vacio
			return true;
		}
		return false;
	}

	@Override
	public int getSamplesCount() {
		return samples.size();
	}

	@Override
	public Vector<V> getBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getSamplingPeriod() {
		return samplePeriod;
	}
	@Override
	public Vector<V> getLastNSamples(int n) throws Exception
	{
		Vector<V> lastNSamples = new Vector<V>();
		if (samples.isEmpty()) throw new Exception();
		Iterator<V> i = samples.iterator();
		while(i.hasNext())
		{
			lastNSamples.add(i.next());
		}
		return lastNSamples;
	}
	
	/////STATIC METHODS
	public static double calculeMean(Vector<Double> v) throws Exception{
		if (v.isEmpty()) throw new Exception("Empty Vector");
		return getSumOfVectorElements(v)/v.size();
	}
	
	public static double calculeStDev(Vector<Double> v) throws Exception{
		if (v.isEmpty()) throw new Exception("Empty Vector");
		double mean = calculeMean(v);
		Vector<Double> v2 = new Vector<Double>(v.size());
		ListIterator<Double> i = v.listIterator();
		while(i.hasNext()) {
			v2.add(Math.pow(i.next()-mean, 2));
		}
		return Math.sqrt(getSumOfVectorElements(v2)/v2.size());
	}
	public static double getSumOfVectorElements(Vector<Double> v)
	{
		double sum = 0.0;
		ListIterator<Double> i = v.listIterator();
		while(i.hasNext()) {
			sum = sum + i.next();
		}
		return sum;
	}
}
