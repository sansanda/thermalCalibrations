package sense;

public interface I_Sense_Subsystem {
	public void configureSenseFunction(String functionName, int[] channelsList) throws Exception;
	public String querySenseFunction(int[] channelsList) throws Exception;
	void configureSenseFunction(String functionName, int channel) throws Exception;
	
}
