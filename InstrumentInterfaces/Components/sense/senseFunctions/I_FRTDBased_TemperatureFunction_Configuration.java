/**
 * 
 */
package sense.senseFunctions;

/**
 * @author DavidS
 *
 */
public interface I_FRTDBased_TemperatureFunction_Configuration extends I_TemperatureFunction_Configuration {
	public void setRZero(int value) throws Exception;
	public int getRZero() throws Exception;
	public void setAlpha(float value) throws Exception;
	public float getAlpha() throws Exception;
	public void setBeta(float value) throws Exception;
	public float getBeta() throws Exception;
	public void setDelta(float value) throws Exception;
	public float getDelta() throws Exception;
}
