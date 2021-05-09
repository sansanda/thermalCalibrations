/**
 * 
 */
package sense.senseFunctions;

/**
 * @author DavidS
 *
 */
public interface I_FWResistance_Function_Configuration extends I_SenseFunction_Configuration {
	public boolean 	isOffsetCompensatedEnabled() throws Exception;
	public void		enableOffsetCompensation(boolean enable) throws Exception;
}
