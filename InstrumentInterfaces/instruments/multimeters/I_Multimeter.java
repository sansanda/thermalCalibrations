/**
 * 
 */
package multimeters;

import common.I_InstrumentComponent;

/**
 * @author DavidS
 *
 */
public interface I_Multimeter extends I_InstrumentComponent{
	
	public void reset() throws Exception;
	
	public void configureAsChannelScanner(
			String triggerSource,
			int numberOfScans,
			int numberOfChannelsToScan,
			int[] channelsToScan
			) throws Exception;
	
	public void configureAsDCVoltmeter(
			float nplc,
			int range,
			byte resolutionDigits,
			int reference,
			int averageCount,
			float averageWindow,
			String averageTControl,
			boolean enableAverage,
			boolean enable10MOhmsDivider) throws Exception;
	
	public void configureAsTCThermometer(
			float nplc,
			byte resolutionDigits,
			int reference,
			int averageCount,
			float averageWindow,
			String averageTControl,
			boolean enableAverage,
			String transducerType,
			boolean enableODetect,
			String rJuntion,
			int simulatedTemperature
			) throws Exception;

	void configureAs2WOhmeter(
			float nplc, 
			int range, 
			byte resolutionDigits, 
			int reference, 
			int averageCount,
			float averageWindow, 
			String averageTControl, 
			boolean enableAverage) throws Exception;

	void configureAs4WOhmeter(
			float nplc, 
			int range, 
			byte resolutionDigits, 
			int reference, 
			int averageCount,
			float averageWindow, 
			String averageTControl, 
			boolean enableAverage, 
			boolean enableOffsetCompensation) throws Exception;
	
	public String measureVoltage(int channelNumber) throws Exception;
}
