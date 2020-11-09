package busEvents;

import javax.measure.Measurable;
import javax.measure.quantity.Temperature;

public class StableTemperatureEvent {
	private Measurable<Temperature> mean;
	private Measurable<Temperature> stDev;

	private long timeStamp; 
	
	public StableTemperatureEvent(Measurable<Temperature> _mean, Measurable<Temperature> _stDev, long _timeStamp) {
		super();
		mean = _mean;
		stDev = _stDev;
		timeStamp = _timeStamp;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the temperature
	 */
	public Measurable<Temperature> getMean() {
		return mean;
	}
	
	/**
	 * @return the stDev
	 */
	public Measurable<Temperature> getStDev() {
		return stDev;
	}
	
}
