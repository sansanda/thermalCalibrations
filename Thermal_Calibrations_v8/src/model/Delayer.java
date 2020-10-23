package model;

import SDM.IDelay;
import statistics.IStatsFilter;
import statistics.SamplesBuffer;

public abstract class Delayer implements IDelay   {

	boolean delayComplete = false;
	IStatsFilter statsFilter;

	SamplesBuffer<?, ?> samples = null;
	
	public Delayer(SamplesBuffer<?, ?> _samples,IStatsFilter _statsFilter) 
	{
		delayComplete = false;
		statsFilter = _statsFilter;
		samples = _samples;
	}
	
	/**
	 * @param statsFilter the statsFilter to set
	 */
	public void setStatsFilter(IStatsFilter statsFilter) {
		this.statsFilter = statsFilter;
	}
	
	public boolean isDelayComplete() {
		// TODO Auto-generated method stub		
		return delayComplete;
	}
}
