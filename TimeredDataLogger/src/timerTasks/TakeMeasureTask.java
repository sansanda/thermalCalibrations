package timerTasks;

import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import buffering.MeasureDataBuffer;
import format.Format_Subsystem;
import multimeters.I_Multimeter;
import multimeters.KeithleyK2700.K2700;
import route.Route_Subsystem;
import trace.Trace_Subsystem;
import trigger.Trigger_Subsystem;
import sense.Sense_Subsystem;



public class TakeMeasureTask extends TimerTask {
	
	private final Logger 	logger = LogManager.getLogger(TakeMeasureTask.class);
	
	private MeasureDataBuffer dataBuffer = null;
	private I_Multimeter multimeter = null;
	private int channelToMeasure;
	
	
    public TakeMeasureTask(MeasureDataBuffer dataBuffer, I_Multimeter multimeter, int channelToMeasure) {
		super();
		this.dataBuffer = dataBuffer;
		this.multimeter = multimeter;
		this.channelToMeasure = channelToMeasure;
	}


	@Override
    public void run() {
    	try {
			
    		System.out.println("Taking measure!!!!");

    		((Route_Subsystem)this.multimeter.getSubComponent(K2700.ROUTE_SUBSYSTEM)).sendCloseChannelCommand(this.channelToMeasure);
    		((Trace_Subsystem)this.multimeter.getSubComponent(K2700.TRACE_SUBSYSTEM)).sendClearBufferCommand();
    		((Trigger_Subsystem)this.multimeter.getSubComponent(K2700.TRIGGER_SUBSYSTEM)).configureTriggerAsIdle();
    		((Trigger_Subsystem)this.multimeter.getSubComponent(K2700.TRIGGER_SUBSYSTEM)).init();
    		
    		
			String measureStr 		= ((Sense_Subsystem)this.multimeter.getSubComponent(K2700.SENSE_SUBSYSTEM)).queryLastFreshReading();
			
			System.out.println(measureStr);
			
			double 	measure 		= Format_Subsystem.getReading_Element(measureStr, Format_Subsystem.READING_DATA_ELEMENT).get(0);
			double 	tStamp 			= Format_Subsystem.getReading_Element(measureStr, Format_Subsystem.TIMESTAMP_DATA_ELEMENT).get(0);
			int 	readingNumber 	= ((Double)Format_Subsystem.getReading_Element(measureStr, Format_Subsystem.READINGNUMBER_DATA_ELEMENT).get(0)).intValue();
			
			
			this.dataBuffer.addElements(measure,tStamp,readingNumber);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
}