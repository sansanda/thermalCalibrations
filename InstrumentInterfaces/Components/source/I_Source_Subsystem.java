package source;

public interface I_Source_Subsystem {
	
	
	
	public void  	clear() throws Exception;
	public void 	setAutoClear(boolean on) throws Exception;
	public void 	setAutoClearMode(String mode) throws Exception;
	public void 	setFunctionShape(String shape) throws Exception;
	public void 	setFunctionMode(String mode) throws Exception;
	public void 	setCurrentMode(String mode) throws Exception;
	public void 	setVoltageMode(String mode) throws Exception;
	public void 	setCurrentRange(Object range) throws Exception;
	public void 	setVoltageRange(Object range) throws Exception;
	public void 	setCurrentAutoRange(boolean enable) throws Exception;
	public void 	setVoltageAutoRange(boolean enable) throws Exception;
	public void 	setCurrentAmplitudeLevel(Object amplitude) throws Exception;
	public void 	setVoltageAmplitudeLevel(Object amplitude) throws Exception;
	public void 	setVoltageProtectionLevel(Object limit) throws Exception;
	
	public void 	setDelay(Object delay) throws Exception;
	public void 	setAutoDelay(boolean enable ) throws Exception;
		
}
