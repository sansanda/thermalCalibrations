package sense.senseFunctions;

public interface I_CurrentDCFunction_Configuration extends I_SenseFunction_Configuration{
	
	public double getProtectionLevel() throws Exception;
	public void setProtectionLevel(double protectionLevel) throws Exception;
	
	public boolean isRangeSynchronizationEnabled() throws Exception;
	public void enableRangeSynchronization(boolean range_synchronization) throws Exception;

	
	
}
