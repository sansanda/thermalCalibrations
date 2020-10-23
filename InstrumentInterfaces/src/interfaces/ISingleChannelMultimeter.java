package interfaces;

public interface ISingleChannelMultimeter {
	
	public enum WorkingMode
	{
		VOLT_DC("VOLT:DC"),
		VOLT_AC("VOLT:AC"),
		CURR_DC("CURR:DC"),
		CURR_AC("CURR:AC"),
		RES("RES"),
		FRES("FRES"),
		TEMP("TEMP"),
		FREQ("FREQ"),
		PER("PER"),
		CONT("CONT");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    WorkingMode(final String text) {
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
	
	public void configureAsWorkingMode(WorkingMode wm) throws Exception;
	
	void initialize() throws Exception;
	void reset() throws Exception;
	
}
