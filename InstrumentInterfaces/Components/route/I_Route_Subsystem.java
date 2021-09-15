package route;

public interface I_Route_Subsystem {
	
	public void sendCloseChannelCommand(int channelNumber) throws Exception;
	public void sendOpenAllChannelsCommand() throws Exception;
	public int[] queryClosedChannels()throws Exception;
	
	public int[] queryClosureCountOfChannels(int[] channelsList) throws Exception;
	
	public boolean isChannelAvailable(int _channelNumber) throws Exception;
	public boolean areChannelsAvailable(int[] _channelsList) throws Exception;
	
	public void configureScanChannelsList(int[] _channelsList) throws Exception;
	public int[] queryScanChannelsList() throws Exception;
	
	public void configureEnableScan(boolean enable) throws Exception;
	public boolean queryScanEnable() throws Exception;
	
	public void configureTriggerSourceAsImmediate() throws Exception;
	public String queryTriggerSourceThatStartsScan() throws Exception;
	
	public void selectInput_OutputTerminalsRoute(String route) throws Exception;
	public String queryInput_OutputTerminalsRoute() throws Exception;
	
}
