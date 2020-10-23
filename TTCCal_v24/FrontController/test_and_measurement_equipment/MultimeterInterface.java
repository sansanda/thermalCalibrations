package test_and_measurement_equipment;

public interface MultimeterInterface {
	public double 	measureResistance();
	public double 	measureCurrent();
	public double 	measureVoltage();
	public void   	setVoltageRange();
	public void 	setCurrentRange();
	public void 	setResistanceRange();

}
