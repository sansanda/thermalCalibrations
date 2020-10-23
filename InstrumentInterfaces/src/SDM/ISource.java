package SDM;

import javax.measure.Measurable;


public interface ISource {
	
	boolean output(Measurable<?> _level, Object _sourcePhysicMagnitude);
	boolean output(Measurable<?> _level);
	boolean output();
	
	
	void setSourceLevel(Measurable<?> _sourceLevel);
	void setSourcePhysicsMagnitude(Object _sourcePhysicMagnitude);
	Measurable<?> getSourceLevel();
	Object getSourcePhysicMagnitude();
	
	void setComplianceLevel(Measurable<?> _complianceLevel);
	void setCompliancePhysicsMagnitude(Object _compliancePhysicMagnitude);
	Measurable<?> getComplianceLevel();
	Object getCompliancePhysicMagnitude();
}
