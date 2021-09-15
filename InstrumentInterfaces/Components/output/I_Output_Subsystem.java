package output;

public interface I_Output_Subsystem {
	
	
	public boolean 	isOutputOn() throws Exception;
	public void 	setOutputOn(boolean outputOn) throws Exception;
	public boolean 	isOutputEnable() throws Exception;
	public void 	setOutputEnable(boolean outputEnable) throws Exception;
	public String 	getOutputMode() throws Exception;
	public void 	setOutputMode(String outputMode) throws Exception;
		
}
