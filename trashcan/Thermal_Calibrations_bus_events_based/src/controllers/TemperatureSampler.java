package controllers;

import java.util.Timer;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import busEvents.NewTemperatureEvent;
import busEvents.StableTemperatureEvent;
import interfaces.IThermometer;
import model.GlobalVariablesRegister;
import model.TemperatureSamplesBuffer;
import statistics.ISamplesBuffer;
import tasks.MeasureTemperature_Task;

public class TemperatureSampler extends Thread {
	
	private Measurable<Duration> samplePeriod = null;
	private Measurable<Duration> sampleDelay = null;
	private int sampleMultipleNumber;
	
	private EventBus eventBus  = null;
	private IThermometer thermometer = null;		
	private TemperatureSamplesBuffer<Measurable<Duration>,Measurable<Temperature>> samplesBuffer = null;
	
	private MeasureTemperature_Task measureTemp_Task = null; 
	private Timer measureTemperature_Timer = null;	
	

	private Measurable<Temperature> desiredStDeviation;
	private int consecutiveTimesDesiredStDev;
	private int consecutiveTimesReachedStDev;
	
	public TemperatureSampler(EventBus _eventBus, IThermometer _t, ISamplesBuffer<Measurable<Duration>,Measurable<Temperature>> _samplesBuffer, Measurable<Duration> _samplePeriod, Measurable<Duration> _sampleDelay, Measurable<Temperature> _desiredStDev, int _consecutiveTimesDesiredStDev, int _sampleMultipleNumber) throws Exception {
		super();
		samplesBuffer = (TemperatureSamplesBuffer<Measurable<Duration>, Measurable<Temperature>>) _samplesBuffer;
		eventBus = _eventBus;
		thermometer = _t;
		desiredStDeviation = _desiredStDev;
		consecutiveTimesDesiredStDev = _consecutiveTimesDesiredStDev;
		consecutiveTimesReachedStDev = 0;
		
		samplePeriod = _samplePeriod;
		sampleDelay = _sampleDelay;
		sampleMultipleNumber = _sampleMultipleNumber;
		
		measureTemp_Task = new MeasureTemperature_Task(thermometer,eventBus);
		measureTemperature_Timer = new Timer();	
		init();
	}

	@Subscribe
    private void newTemperatureEvent(NewTemperatureEvent event) throws Exception {
		//System.out.println("Temperature: " + event.getTemperature().doubleValue(SI.CELSIUS));
		System.out.println("TS: " + event.getTimeStamp());
		if (samplesBuffer.addSample(event.getTemperature(), sampleMultipleNumber)) 
		{
			//The buffer has a number of samples multiple of sampleMultipleNumber
			//Then we calculate the st dev of the last N samples
			Measurable<Temperature> stDev = samplesBuffer.calculateStDevOfLastNSamples(SI.CELSIUS, sampleMultipleNumber);
			if (stDev.doubleValue(SI.CELSIUS)<desiredStDeviation.doubleValue(SI.CELSIUS)) 
			{
				consecutiveTimesReachedStDev++;
				if (consecutiveTimesReachedStDev==consecutiveTimesDesiredStDev) 
				{
					//Send an temperature stabilization reached event
					Measurable<Temperature> mean = samplesBuffer.calculateMeanOfLastNSamples(SI.CELSIUS, sampleMultipleNumber);
					
					System.out.println("mean: " + mean.toString() + " --- stDev: " + stDev.toString());
					
					
					long timeStamp = System.currentTimeMillis();
					if (GlobalVariablesRegister.relativeTimeStamp) timeStamp = timeStamp-GlobalVariablesRegister.timeStampsRef; 			
					eventBus.post(new StableTemperatureEvent(mean, stDev, timeStamp) );					
					consecutiveTimesReachedStDev = 0;
				}
			}
			else {consecutiveTimesReachedStDev=0;}
		}
		
    }
	
	/**
	 * @return the nSamplesReaded
	 */
	public long getNSamplesReaded() {
		return samplesBuffer.getSamplesCount();
	}

	/**
	 * @return the samplePeriod
	 */
	public Measurable<Duration> getSamplePeriod() {
		return samplePeriod;
	}

	/**
	 * @return the sampleDelay
	 */
	public Measurable<Duration> getSampleDelay() {
		return sampleDelay;
	}

	/**
	 * @param samplePeriod the samplePeriod to set
	 */
	public void setSamplePeriod(Measurable<Duration> samplePeriod) {
		this.samplePeriod = samplePeriod;
	}

	/**
	 * @param sampleDelay the sampleDelay to set
	 */
	public void setSampleDelay(Measurable<Duration> sampleDelay) {
		this.sampleDelay = sampleDelay;
	}

	private void init() {eventBus.register(this);}
	
	

	@Override
	public void run() {
		/*
	     * Set an initial delay of 0 secon	d, then repeat every half second.
	     */
		measureTemperature_Timer.scheduleAtFixedRate(measureTemp_Task, sampleDelay.longValue(SI.MILLI(SI.SECOND)), samplePeriod.longValue(SI.MILLI(SI.SECOND)));
	}
	
	public void pause() {
		measureTemperature_Timer.cancel();
		measureTemp_Task.cancel();
    }
	
	public void restart() {
		measureTemperature_Timer = new Timer();	
		measureTemp_Task = new MeasureTemperature_Task(thermometer,eventBus);
		measureTemperature_Timer.scheduleAtFixedRate(measureTemp_Task, sampleDelay.longValue(SI.MILLI(SI.SECOND)), samplePeriod.longValue(SI.MILLI(SI.SECOND)));	
    }
	public void terminate() {
		measureTemperature_Timer.cancel();
		measureTemperature_Timer.purge();
		measureTemp_Task.cancel();
    }
	
	
	
}
