package tasks;

import java.util.TimerTask;

import javax.measure.Measurable;
import javax.measure.quantity.Temperature;

import com.google.common.eventbus.EventBus;

import busEvents.NewTemperatureEvent;
import interfaces.IThermometer;
import model.GlobalVariablesRegister;


//import interfaces.IThermometer;

public class MeasureTemperature_Task extends TimerTask {
	//private IThermometer thermometer;
	private long timeStamp = 0;
	private IThermometer thermometer;
	private EventBus eventBus;
	
	public MeasureTemperature_Task(IThermometer _thermometer, EventBus _eventBus) {

		try {
			thermometer = _thermometer;
			eventBus = _eventBus;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			if (GlobalVariablesRegister.timeStampsRef==0) {GlobalVariablesRegister.timeStampsRef = System.currentTimeMillis();}
			Measurable<Temperature> readedTemperature = (Measurable<Temperature>) thermometer.measure();
			timeStamp = System.currentTimeMillis();
			if (GlobalVariablesRegister.relativeTimeStamp) timeStamp = timeStamp-GlobalVariablesRegister.timeStampsRef; 
			eventBus.post(new NewTemperatureEvent(readedTemperature,timeStamp));	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
