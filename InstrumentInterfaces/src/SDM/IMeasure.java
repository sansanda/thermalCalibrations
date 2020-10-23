package SDM;

import javax.measure.Measurable;

public interface IMeasure {
	Measurable<?> measure() throws Exception;
	void setMeasureMagtinude(Object _measurePhysicMagnitude); 
	Object getMeasureMagnitude();
	void setRange(Measurable<?> _range); //If range == null the range will be set to auto if the meter has got autorange
	Measurable<?> getRange();
	void setNPLC(double _nplc); //If nplc == null we will consider that the instrument doesn't have nplc option
	Measurable<?> getNPLC();
	void setRepeatFilterN(int _repeatFilterN); //If _n >1 then we will activate the repeat filter otherwise we will not consider the option. 
	Measurable<?> getRepeatFilterN();
	boolean enableFourMeasure(boolean _enableFourMeasure);
	
	
	
}
