package sense;

import sense.senseFunctions.I_SenseFunction_Configuration;

public interface I_Sense_Subsystem {
	
	public void setChannels_SenseFunction(int[] channelsList, I_SenseFunction_Configuration configuration) throws Exception;
	public void setChannel_SenseFunction(int channel, I_SenseFunction_Configuration configuration) throws Exception;
	
	public String querySenseFunction(int[] channelsList) throws Exception;
	
	public String queryLatestReading() throws Exception;
	public String queryLastFreshReading() throws Exception;
	
}
