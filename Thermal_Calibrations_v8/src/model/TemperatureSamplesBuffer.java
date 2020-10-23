package model;

import java.util.ListIterator;
import java.util.Vector;
import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Temperature;
import javax.measure.quantity.Duration;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import statistics.SamplesBuffer;

/**
 * 
 * @author DavidS
 *
 * @param <T> 
 * @param <V> 
 */
public class TemperatureSamplesBuffer<T,V> extends SamplesBuffer<T, V> {
	
	public TemperatureSamplesBuffer(T _samplePeriod) throws Exception {
		super(_samplePeriod);		
	}

	public Measurable<Temperature> calculateMeanOfAllSamples(Unit<Temperature> u) throws Exception {		
		double temperaturesMean = super.calculeMean(getTemperatures(super.getBuffer(),u));
		return Measure.valueOf(temperaturesMean, u);
	}

	public Measurable<Temperature> calculateStDevOfAllSamples(Unit<Temperature> u) throws Exception {
		double temperaturesStDev = super.calculeStDev(getTemperatures(super.getBuffer(),u));
		return Measure.valueOf(temperaturesStDev, u);
	}

	public Measurable<Temperature> calculateMeanOfLastNSamples(Unit<Temperature> u, int n) throws Exception {
		double temperaturesMean = super.calculeMean(getTemperatures(super.getLastNSamples(n),u));
		return Measure.valueOf(temperaturesMean, u);
	}

	public Measurable<Temperature> calculateStDevOfLastNSamples(Unit<Temperature> u, int n) throws Exception {
		double temperaturesStDev = super.calculeStDev(getTemperatures(super.getLastNSamples(n),u));
		return Measure.valueOf(temperaturesStDev, u);
	}
	
	private Vector<Double> getTemperatures(Vector<V> buffer,Unit<Temperature> u) throws Exception
	{	
		if (buffer.isEmpty()) throw new Exception("Empty Buffer");
		Vector<Double> temperatures = new Vector<Double>(buffer.size());
		ListIterator<V> i = buffer.listIterator();
		while (i.hasNext())
		{
			Measurable<Temperature> t = (Measurable<Temperature>) i.next();
			temperatures.add(t.doubleValue(u));
		}
		return temperatures;	
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TemperatureSamplesBuffer<Measurable<Duration>,Measurable<Temperature>> tsb = new TemperatureSamplesBuffer<Measurable<Duration>,Measurable<Temperature>>(Measure.valueOf(1000, SI.MILLI(SI.SECOND)));
	}
}
