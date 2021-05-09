/**
 * 
 */
package sense.senseFunctions;

/**
 * @author DavidS
 *
 */
public interface I_ThermistorBased_TemperatureFunction_Configuration extends I_TemperatureFunction_Configuration {
	public void setResistorValue(int value) throws Exception;
	public int 	getResistorValue() throws Exception;
}
