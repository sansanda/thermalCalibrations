package interfaces;

public interface IMultichannelMultimeter {
	
	//Channels operation
	public void closeChannel(int wm) throws Exception;
	void openAllChannels() throws Exception;
	//Card identification
	boolean isMultichannelCardInstalled(int slot) throws Exception;
}