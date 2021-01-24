package common;

public interface I_Multimeter {
	
	//functions 
	String[] 	getAvailableFunctions() throws Exception;
	void 		setAvailableFunctions(String[] functions) throws Exception;
	
	//channels
	int[] 	getAvailableChannels() throws Exception;
	void	setAvailableChannels(int[] channels) throws Exception;
	
	//multimeter configuration
	void		configureMultimeter(String xml_configurationFilePath) throws Exception;
	
	//expansion slots
	int 	getNumberOfExpansionSlots() throws Exception;
	void 	setNumberOfExpansionSlots(int n) throws Exception;
	
	//outputs
	String[]	getAvailableOutputConnections() throws Exception; //return something like a ["rear","front","other, .... "]
	void 	setAvailableOutputConnection(String output) throws Exception; //output could be rear o front or other
	void 	redirectOutputTo(String output) throws Exception;
	void 	enableOutput(boolean enable) throws Exception;
	
	
	
	
	
}