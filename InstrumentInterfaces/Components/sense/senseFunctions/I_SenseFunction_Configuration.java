package sense.senseFunctions;

/**
 * Configuracion base para gran parte de las funciones de medida que suele usar un multimetro.
 * Esta configuracion base podria ser adoptada por, por ejemplo, las siguientes funciones:
 * TEMPERATURE o FRESistance, etc...
 * 
 * @author DavidS
 *
 */
public interface I_SenseFunction_Configuration{
	
	public String 	getFunctionName() throws Exception;
	public void 	setFunctionName(String functionName) throws Exception;
	
	//NPLC
	public float 	getNPLC() throws Exception;
	public void		setNPLC(float nplc) throws Exception;
	//RANGE
	public int 		getRange() throws Exception;
	public void		setRange(int range) throws Exception;
	//DIGITS
	public byte		getResolutionDigits() throws Exception;
	public void		setResolutionDigits(byte digits) throws Exception;
	//REFERENCE
	public int 		getReference() throws Exception;
	public void 	setReference(int reference) throws Exception;
	
	//AVERAGE
	public String 	getAverageTControl() throws Exception;
	public void 	setAverageTControl(String averageTControl) throws Exception;
	
	public float 	getAverageWindow() throws Exception;
	public void 	setAverageWindow(float window) throws Exception;
	
	public int 		getAverageCount() throws Exception;
	public void		setAverageCount(int count) throws Exception;
	
	public boolean	isAverageEnable() throws Exception;
	public void		enableAverage(boolean enable) throws Exception;
}
