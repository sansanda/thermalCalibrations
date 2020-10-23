/**
 * 
 */
package interfaces;
import SDM.IMeasure;


/**
 * @author DavidS
 *
 */
public interface IVoltmeter extends IMeasure{
	
	public enum ManufacturerUnits
	{
		KEITHLEY_VDC("VDC"),
		KEITHLEY_CELSIUS(Character.toString ((char) 19)+"C");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    ManufacturerUnits(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
}
