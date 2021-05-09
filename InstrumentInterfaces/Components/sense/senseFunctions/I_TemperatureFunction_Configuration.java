package sense.senseFunctions;

/**
 * @author DavidS
 *
 */
public interface I_TemperatureFunction_Configuration extends I_SenseFunction_Configuration{
	public void setType(String tt) throws Exception;
	public String getType() throws Exception;
	public void setTransducer(String t) throws Exception;
	public String getTransducer() throws Exception;
}
