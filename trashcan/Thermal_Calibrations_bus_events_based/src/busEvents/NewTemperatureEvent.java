package busEvents;

import javax.measure.Measurable;
import javax.measure.quantity.Temperature;

public class NewTemperatureEvent {
	Measurable<Temperature> temperature;
	long timeStamp;
	
	public NewTemperatureEvent(Measurable<Temperature> _temperature, long _timeStamp) {
		super();
		temperature = _temperature;
		timeStamp = _timeStamp;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the temperature
	 */
	public Measurable<Temperature> getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Measurable<Temperature> _temperature) {
		temperature = _temperature;
	}
}
