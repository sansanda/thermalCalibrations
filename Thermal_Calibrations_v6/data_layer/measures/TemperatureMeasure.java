package measures;

import com.schneide.quantity.miscQuantities.GradCelsius;

public class TemperatureMeasure extends Measure {
	private GradCelsius temperature;
	public TemperatureMeasure(long measureNumber, String measureDescription,
			double temperature) {
		super(measureNumber, measureDescription);
		this.temperature = new GradCelsius(temperature);
	}
	public GradCelsius getTemperatureInCelsius(){
		return temperature;
	}
	public void sub(double t2){
		this.temperature = new GradCelsius(temperature.getValue()-t2);
	}
	public void add(double t2){
		this.temperature = new GradCelsius(temperature.getValue()+t2);
	}
	public String toString() {
		String res = "";
		res = res + super.toString();
		res = res + "temperature = " + temperature.toString()+ "\n";
		return res;
	}
}
