package sense.channels;

import sense.senseFunctions.SenseFunction_Configuration;

public interface I_SenseChannel_Configuration {
	
	public int 	getSlotNumber() throws Exception;
	
	public void setChannelNumber(int channelNumber) throws Exception;
	public int 	getChannelNumber() throws Exception;
	
	public void setSenseFunction(SenseFunction_Configuration sfc) throws Exception;
	public SenseFunction_Configuration getSenseFunction() throws Exception;
	
}
