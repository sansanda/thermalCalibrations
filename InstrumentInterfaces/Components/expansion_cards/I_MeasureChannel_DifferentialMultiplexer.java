/**
 * 
 */
package expansion_cards;

/**
 * @author DavidS
 *
 */
public interface I_MeasureChannel_DifferentialMultiplexer {
	
	public float getMaximum_allowable_voltage();
	
	public int getIntial_generalPurpose_measurementchannel_number();
	public int getFinal_generalPurpose_measurementchannel_number();
	
	public boolean support_multiplexed_channels();
	public boolean support_isolated_channels();
	public boolean isHave_builtIn_temp_comp_col_juntion_sensor();

	public int getIntial_amp_measurementchannel_number();
	public int getFinal_amp_measurementchannel_number();

	public int getIntial_isolated_channel_number();
	public int getFinal_isolated_channel_number();

	public void validateChannelRange(int _minChannel, int _maxChannel) throws Exception;
	public void validateChannelList(int[] _channelsList) throws Exception;
	public void validateChannel(int _channelNumber) throws Exception;
	
}
