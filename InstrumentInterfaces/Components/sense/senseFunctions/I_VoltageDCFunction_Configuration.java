package sense.senseFunctions;

public interface I_VoltageDCFunction_Configuration extends I_SenseFunction_Configuration{
	
	public void enable10MOhmsInputDivider(boolean enable) throws Exception;
	public boolean is10MOhmsInputDividerEnabled() throws Exception;
	
}
