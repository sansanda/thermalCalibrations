package clasesFicticias;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.ElectricCurrent;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.Quantity;
import javax.measure.unit.SI;

import SDM.IMeasure;
import SDM.ISource;

public class SMU implements ISource, IMeasure {

	private  String name;
	
	public SMU(String _name) {
		// TODO Auto-generated constructor stub
		name = _name;
	}

	public boolean output(Measurable<?> _level, Object _sourcePhysicMagnitude)
	{
		if (_sourcePhysicMagnitude instanceof ElectricPotential) 
		{
			System.out.println(name + " Outputing --> " + _level.toString());
		}
		if (_sourcePhysicMagnitude instanceof ElectricCurrent) 
		{
			System.out.println(name + " Outputing --> " + _level.toString());
		}
		// TODO Auto-generated method stub
		return true;
	}
	public boolean output(Measurable<?> _level)
	{
		if (getSourcePhysicMagnitude() instanceof ElectricPotential) 
		{
			System.out.println(name + " Outputing --> " + _level.toString());
		}
		if (getSourcePhysicMagnitude() instanceof ElectricCurrent) 
		{
			System.out.println(name + " Outputing --> " + _level.toString());
		}
		// TODO Auto-generated method stub
		return true;
	}
	public boolean output() 
	{
		if (getSourcePhysicMagnitude() instanceof ElectricPotential) 
		{
			System.out.println(name + " Outputing --> " + getSourceLevel().toString());
		}
		if (getSourcePhysicMagnitude() instanceof ElectricCurrent) 
		{
			System.out.println(name + " Outputing --> " + getSourceLevel().toString());
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Measurable<?> measure() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMeasureMagtinude(Object _measurePhysicMagnitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getMeasureMagnitude() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRange(Measurable<?> _range) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<?> getRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNPLC(double _nplc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<?> getNPLC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRepeatFilterN(int _repeatFilterN) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<?> getRepeatFilterN() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean enableFourMeasure(boolean _enableFourMeasure) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSourceLevel(Measurable<?> _sourceLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSourcePhysicsMagnitude(Object _sourcePhysicMagnitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<?> getSourceLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSourcePhysicMagnitude() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setComplianceLevel(Measurable<?> _complianceLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCompliancePhysicsMagnitude(Object _compliancePhysicMagnitude) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<?> getComplianceLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCompliancePhysicMagnitude() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
