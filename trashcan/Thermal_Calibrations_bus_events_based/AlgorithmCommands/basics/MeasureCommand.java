package basics;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Quantity;
import javax.measure.unit.SI;

import SDM.IMeasure;

public class MeasureCommand extends Command {

	private IMeasure meter = null;
	private Measurable<?> measure = null;
	private Measurable<Duration> timeStamp = null;

	public MeasureCommand(IMeasure _meter, 
							Object _measurePhysicsMagnitude, 
							Measurable<?> _range, 
							double _nplc, 
							int _repeatFilterN, 
							boolean _enableFourMeasure) 
	{
		meter = _meter;
		meter.setMeasureMagtinude(_measurePhysicsMagnitude);
		meter.setRange(_range);
		meter.setNPLC(_nplc);
		meter.setRepeatFilterN(_repeatFilterN);
		meter.enableFourMeasure(_enableFourMeasure);
		setIsExecuted(false);
	}
	
	/**
	 * @return the measure
	 */
	public Measurable<?> getMeasure() {
		return measure;
	}
	
	/**
	 * @return the timeStamp
	 */
	public Measurable<?> getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * @return the measureUnits
	 */
	public Object getMeasureMagnitude() {
		return meter.getMeasureMagnitude();
	}
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Executing Measure Command");
		if (meter!=null)
		{
			measure = meter.measure();
			timeStamp = Measure.valueOf(System.currentTimeMillis(), SI.MILLI(SI.SECOND));
		}
		setIsExecuted(true);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MeasureCommand [\n meter=" + meter + ", measure=" + measure + ", timeStamp=" + timeStamp + "\n]";
	}
	
}