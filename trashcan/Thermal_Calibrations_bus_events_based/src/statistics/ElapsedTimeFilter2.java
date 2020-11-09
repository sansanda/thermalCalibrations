package statistics;

import javax.measure.Measurable;
import javax.measure.quantity.Duration;
import javax.measure.unit.SI;

public class ElapsedTimeFilter2 implements IStatsFilter {

	Measurable<Duration> elapsedTimeDesired;
	
	//Time in milliseconds, between the current time and midnight, January 1, 1970 UTC.
	public ElapsedTimeFilter2(Measurable<Duration> _elapsedTimeDesired) {
		elapsedTimeDesired = _elapsedTimeDesired;
	}
	
	/**
	 * @param elapsedTimeDesired the elapsedTimeDesired to set
	 */
	public void setElapsedTimeDesired(Measurable<Duration> elapsedTimeDesired) {
		this.elapsedTimeDesired = elapsedTimeDesired;
	}
	
	@Override
	public boolean satisfies(Object _actualTime) throws Exception {
		if (!(_actualTime instanceof Measurable<?>)) {return false;}//is not Measurable<?> type
		Measurable<Duration> actualTime = (Measurable<Duration>) _actualTime;

		//System.out.println("actualTime = " + actualTime.doubleValue(SI.MILLI(SI.SECOND)));		
		//System.out.println("elapsedTimeDesired = " + elapsedTimeDesired.doubleValue(SI.MILLI(SI.SECOND)));
		
		if (actualTime.doubleValue(SI.MILLI(SI.SECOND))>=elapsedTimeDesired.doubleValue(SI.MILLI(SI.SECOND))) return true;
		return false;
	}

}
	
