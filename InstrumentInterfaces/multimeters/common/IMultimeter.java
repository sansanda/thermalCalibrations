package common;
import common.CommPort_I;

public interface IMultimeter {
	
	//functions 
	public String[] getAvailableFunctions() throws Exception;
	public void 	setAvailableFunctions(String[] functions) throws Exception;
	
	//channels
	public int[] 	getAvailableChannels() throws Exception;
	public void		setAvailableChannels(int[] channels) throws Exception;
	
	//multimeter configuration
	public void		configureMultimeter(String xml_configurationFilePath) throws Exception;
	
	//expansion slots
	public int 		getNumberOfExpansionSlots() throws Exception;
	public void 	setNumberOfExpansionSlots(int n) throws Exception;
	
	//communications
	public CommPort_I 	getCommPort() throws Exception;
	public void 		setCommPort(CommPort_I commPort) throws Exception;
	
	//outputs
	public String[]	getAvailableOutputConnections() throws Exception; //return something like a ["rear","front","other, .... "]
	public void 	setAvailableOutputConnection(String output) throws Exception; //output could be rear o front or other
	public void 	redirectOutputTo(String output) throws Exception;
	public void 	enableOutput(boolean enable) throws Exception;
	
	
	
	
	
}