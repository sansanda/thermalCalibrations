package DataLogger;
import java.util.Timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import buffering.MeasureDataBuffer;
import multimeters.I_Multimeter;
import timerTasks.TakeMeasureTask;

public class TimeredDataLogger{


	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	//////////////////////ERRORS
	
	//////////////////////VERSIONS
	private static final int classVersion = 102;
	
	//////////////////////LOGGER
	//final static Logger 	logger = LogManager.getLogger(TimeredDataLogger.class);
	
	private MeasureDataBuffer 	dataBuffer = null;
	
	private I_Multimeter 	multimeter = null;
	private int 			channelToMeasure;
	
	Timer t = null;
	TakeMeasureTask tm_task = null;
	
	private long 			initialDelay = 0;
	private long 			period = 1000;
	
	
	public TimeredDataLogger(MeasureDataBuffer dataBuffer, long initialDelay, long period, I_Multimeter multimeter, int channelToMeasure) {
		
		super(); //daemon false
		this.dataBuffer = dataBuffer;
		this.multimeter = multimeter;
		this.channelToMeasure = channelToMeasure;
		
		this.initialDelay = initialDelay;
		this.period = period;
		
	}

	public void start()
	{
		if (t!=null) return;
		t = new Timer(false);
		this.tm_task = new TakeMeasureTask(this.dataBuffer,this.multimeter,this.channelToMeasure);
		t.schedule(tm_task, this.initialDelay, this.period);
	}
	
	public void stop() {
		this.t.cancel();
		this.t.purge();
		this.t = null;
	}
}

