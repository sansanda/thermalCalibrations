/**
 * 
 */
package interfaces;

import SDM.ISource;


/**
 * @author DavidS
 *
 */
public interface IVoltageSource extends ISource{
	
	public enum ManufacturerCommands
	{
		KEITHLEY_VOLTAGESOURCE_COMMAND_STRING(":SOUR:VOLT ");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    ManufacturerCommands(final String text) {
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
