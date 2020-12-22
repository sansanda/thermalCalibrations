package controllers;
import com.google.common.eventbus.EventBus;
public class TemperatureSamplerController {
	
	private TemperatureSampler temperatureSampler = null; 
	
	public TemperatureSamplerController (EventBus _eventBus, TemperatureSampler _temperatureSampler) throws Exception {
		super();	
		temperatureSampler = _temperatureSampler;
		init();
	}
	
	
	private void init() {
	}
	
	public void start() {
		temperatureSampler.start();
	}
	
	public void pause() {
		temperatureSampler.pause();
    }
	
	public void restart() {
		temperatureSampler.restart();
    }
	public void terminate() {
		temperatureSampler.terminate();
    }
	
	
}
