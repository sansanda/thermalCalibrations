package model;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.unit.SI;

import SDM.IDelay;
import basics.DelayCommand;
import statistics.ElapsedTimeFilter2;

public class TimeDelayer extends Delayer {

	private Measurable<Duration> elapsedTimeDesired;

	public TimeDelayer(Measurable<Duration> _elapsedTimeDesired) {
		
		super(null, null);
		elapsedTimeDesired = _elapsedTimeDesired;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startDelay() throws Exception {
		//System.out.println("Starting delay");
		delayComplete = false;
		ElapsedTimeFilter2 etf2 = new ElapsedTimeFilter2(Measure.valueOf(elapsedTimeDesired.longValue(SI.MILLI(SI.SECOND))+ System.currentTimeMillis(), SI.MILLI(SI.SECOND)));
		setStatsFilter(etf2);
		while (!isDelayComplete()) {
			delayComplete = statsFilter.satisfies(Measure.valueOf(System.currentTimeMillis(), SI.MILLI(SI.SECOND)));
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long desiredTimeDelay = 2000; //ms
		IDelay delayer = new TimeDelayer(Measure.valueOf(desiredTimeDelay, SI.MILLI(SI.SECOND)));
		DelayCommand dc = new DelayCommand(delayer);
		try {
			dc.execute();
			//while (!dc.isExecuted()) {}
			dc.execute();
			//while (!dc.isExecuted()) {}
			dc.execute();
			//while (!dc.isExecuted()) {}
			dc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Delay Complete");
	}
}
