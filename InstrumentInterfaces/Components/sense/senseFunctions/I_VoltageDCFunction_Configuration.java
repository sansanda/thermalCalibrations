package sense.senseFunctions;

public interface I_VoltageDCFunction_Configuration extends I_SenseFunction_Configuration{
	
	public void enable10MOhmsInputDivider(boolean enable) throws Exception;
	public boolean is10MOhmsInputDividerEnabled() throws Exception;
	
	public double getProtectionLevel() throws Exception;
	public void setProtectionLevel(double protectionLevel) throws Exception;
	
	public boolean isRangeSynchronizationEnabled() throws Exception;
	public void enableRangeSynchronization(boolean range_synchronization) throws Exception;

	
	
}
