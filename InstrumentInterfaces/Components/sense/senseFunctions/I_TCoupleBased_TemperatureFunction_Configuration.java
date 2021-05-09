/**
 * 
 */
package sense.senseFunctions;

/**
 * @author DavidS
 *
 */
public interface I_TCoupleBased_TemperatureFunction_Configuration extends I_TemperatureFunction_Configuration {
	public void enableOpenDetection(boolean enable) throws Exception;
	public boolean isOpenDetectionEnable() throws Exception;
	public void setReferenceJunctionType(String type) throws Exception;
	public String getReferenceJunctionType() throws Exception;
	public void setSimulatedReferenceJunctionTemperature(int value) throws Exception;
	public int getSimulatedReferenceJunctionTemperature() throws Exception;
}
