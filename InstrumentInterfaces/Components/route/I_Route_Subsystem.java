package route;

public interface I_Route_Subsystem {
	
	public void closeChannel(int channelNumber) throws Exception;
	public void openAllChannels() throws Exception;
	public int[] getClosedChannels()throws Exception;
	
	public int[] getClosureCountOfChannels(int[] channelsList) throws Exception;
	
	public boolean isChannelAvailable(int _channelNumber) throws Exception;
	public boolean areChannelsAvailable(int[] _channelsList) throws Exception;
	
	public void setScanChannelsList(int[] _channelsList) throws Exception;
	public int[] getScanChannelsList() throws Exception;
	
	public void enableScan(boolean enable) throws Exception;
	public boolean isScanEnable() throws Exception;
	
	public void setTriggerSourceAsImmediate() throws Exception;
	public String getTriggerSourceThatStartsScan() throws Exception;
}
