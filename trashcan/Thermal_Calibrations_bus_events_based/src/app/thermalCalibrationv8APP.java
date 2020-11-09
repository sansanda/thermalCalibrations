package app;


import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.Duration;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;

import org.jfree.data.xy.XYSeries;

import com.google.common.eventbus.EventBus;

import controllers.TemperatureSamplerController;
//import controllers.TemperatureViewController;
import controllers.IViewController;
import controllers.TemperatureSampler;
import interfaces.ISingleChannelMultimeter;
import interfaces.IThermometer;
import interfaces.IThermometer.TemperatureTransducer;
import interfaces.IThermometer.TemperatureUnits;
import jFreeChart.TemperatureVSMeasureNumberFrame;
import model.GlobalVariablesRegister;
import model.TemperatureSamplesBuffer;
import multimeters.Keithley2700_v3;


public class thermalCalibrationv8APP{
	
	final int DEFAULT_WIDTH = 640;
	final int DEFAULT_HEIGHT = DEFAULT_WIDTH /12*9;
	
	private EventBus eventBus = null;				//UI Event Bus created for communication between UIController and Views  
	private ISingleChannelMultimeter scm = null;
	private TemperatureSamplerController tsc = null;
	private TemperatureSampler temperatureSampler = null;
	private XYSeries allTemperatureSamples = null;
	private IThermometer thermometer = null;
	private TemperatureSamplesBuffer<Measurable<Duration>,Measurable<Temperature>> temperatureSamplerBuffer = null;
	private TemperatureVSMeasureNumberFrame temperatureFrame = null;
	
	private IViewController temperatureViewController = null;
	
	public thermalCalibrationv8APP(IThermometer _k, ISingleChannelMultimeter _scm ) throws Exception {
		
		GlobalVariablesRegister.relativeTimeStamp = true;
		
		Measurable<Duration> samplePeriodInMilliseconds = Measure.valueOf(1000, SI.MILLI(SI.SECOND)); //Recommended 1000ms. Min 700 ms
		Measurable<Duration> sampleDelayInMilliseconds = Measure.valueOf(0, SI.MILLI(SI.SECOND));
		Measurable<Temperature> desiredStDev = Measure.valueOf(0.1, SI.CELSIUS);
		int consecutiveTimesDesiredStDev = 1;
		int nSamplesPerWindow = 30;
		String temperatureSamplerBufferName = "Temperatures";
		
		scm = _scm;
		scm.reset();
		
		thermometer = _k;
		thermometer.setTemperatureTransducer(TemperatureTransducer.FRTD);
		thermometer.setTemperatureUnits(TemperatureUnits.C);
		
		eventBus = new EventBus();
		temperatureSamplerBuffer = new TemperatureSamplesBuffer(samplePeriodInMilliseconds);
		//temperatureViewController = new TemperatureViewController(eventBus);
		
		temperatureSampler = new TemperatureSampler(
				eventBus,
				thermometer,
				temperatureSamplerBuffer,
				samplePeriodInMilliseconds,
				sampleDelayInMilliseconds,
				desiredStDev,
				consecutiveTimesDesiredStDev,
				nSamplesPerWindow);
		
		tsc = new TemperatureSamplerController(eventBus,temperatureSampler);	
		
		//temperatureFrame = new TemperatureVSMeasureNumberFrame(temperatureSamplerBufferName,allTemperatureSamples);
		
		init();
		start();
	}
	
	private void init() throws Exception {
		
	}
	
	public void start() 
	{
		tsc.start();
	}
	public void terminate() {
		tsc.terminate();
		eventBus = null;
		scm=null;
		tsc=null;
		System.exit(0);
    }
	
	
	public static void main(String[] args) {
		
		try {
			
			Keithley2700_v3 k = new Keithley2700_v3("COM5");		
			thermalCalibrationv8APP app = new thermalCalibrationv8APP(k,k);
			//Thread.sleep(100000);
			//app.terminate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}
	
}
