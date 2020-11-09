package statistics;

import javax.measure.Measurable;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;
import model.TemperatureSamplesBuffer;

public class TemperatureStDevFilter implements IStatsFilter {

	Measurable<Temperature> desiredTemperatureStDev;
	int nSamples;
	
	public TemperatureStDevFilter(Measurable<Temperature> _desiredTemperatureStDev, int _nSamples) {
		desiredTemperatureStDev = _desiredTemperatureStDev;
		nSamples = _nSamples; 
	}
	
	@Override
	public boolean satisfies(Object samples) throws Exception {
		if (!(samples instanceof SamplesBuffer<?, ?>)) {return false;}
		TemperatureSamplesBuffer<Measurable<Duration>, Measurable<Temperature>> temperatureSamplesBuffer = (TemperatureSamplesBuffer<Measurable<Duration>, Measurable<Temperature>>) ((SamplesBuffer<?, ?>)samples);
		Measurable<Temperature> tSBufferStDev = temperatureSamplesBuffer.calculateStDevOfLastNSamples(SI.CELSIUS, nSamples);
		if (tSBufferStDev.doubleValue(SI.CELSIUS)<=desiredTemperatureStDev.doubleValue(SI.CELSIUS)) return true;
		return false;
	}
}
