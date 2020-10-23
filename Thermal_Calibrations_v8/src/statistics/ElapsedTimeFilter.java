package statistics;

import java.util.Iterator;
import java.util.Vector;

import javax.measure.Measurable;
import javax.measure.quantity.Duration;
import javax.measure.unit.SI;

public class ElapsedTimeFilter implements IStatsFilter {

	Measurable<Duration> elapsedTimeDesired;
	int nSamples;
	
	public ElapsedTimeFilter(Measurable<Duration> _elapsedTimeDesired, int _nSamples) {
		elapsedTimeDesired = _elapsedTimeDesired;
		nSamples = _nSamples;
	}
	@Override
	public boolean satisfies(Object samples) throws Exception {
		if (!(samples instanceof SamplesBuffer<?, ?>)) {return false;}
		Vector<Measurable<Duration>> lastSamples = (Vector<Measurable<Duration>>) ((SamplesBuffer<?, ?>)samples).getLastNSamples(nSamples);
		Iterator<Measurable<Duration>> i = lastSamples.iterator();
		while(i.hasNext())
		{
			Measurable<Duration> item = (Measurable<Duration>) i.next();
			if (item.doubleValue(SI.MILLI(SI.SECOND))>=elapsedTimeDesired.doubleValue(SI.MILLI(SI.SECOND))) return true;
		}
		return false;
	}

}
