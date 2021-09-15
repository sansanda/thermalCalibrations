/**
 * 
 */
package multimeters;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author DavidS
 *
 */
public abstract class Multimeter implements I_Multimeter {

	public static final String K2700_NAME= "KEITHLEY:K2700";
	
	public static final ArrayList<String> AVAILABLE_MULTIMETERS = new ArrayList<String>(
			Arrays.asList(
					K2700_NAME)
			);
	
	
	/**
	 * 
	 */
	public Multimeter() {
		// TODO Auto-generated constructor stub
	}
}
