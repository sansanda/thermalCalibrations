package sensors_and_transducers;

public abstract class TemperatureSensor extends Sensor{
	//Constants
	//Variables
	private double MAX_TEMP = 850;
	private double MIN_TEMP = -200;

	//Constructors
	/**
	 * Default constructor
	 */
	public TemperatureSensor(){super();}
	/**
	 * constructor
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public TemperatureSensor(double _accuracy,String _manufacturer, String _manufacturerReference){
		super(_accuracy, _manufacturer, _manufacturerReference);
	}
	/**
	 * constructor
	 * @param _MIN_TEMP IN CELSIUS
	 * @param _MAX_TEMP IN CELSIUS
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public TemperatureSensor(double _MIN_TEMP, double _MAX_TEMP,double _accuracy, String _manufacturer, String _manufacturerReference){
		super(_accuracy,_manufacturer, _manufacturerReference);
		setMAX_TEMP_IN_CELSIUS(_MAX_TEMP);
		setMIN_TEMP_IN_CELSIUS(_MIN_TEMP);
	}
	//Getters and Setters
	/**
	 * @return the mAX_TEMP IN CELSIUS
	 */
	protected double getMAX_TEMP_IN_CELSIUS() {return MAX_TEMP;}
	/**
	 * @return the mIN_TEMP IN CELSIUS
	 */
	protected double getMIN_TEMP_IN_CELSIUS() {return MIN_TEMP;}
	/**
	 * @param max_temp the mAX_TEMP to set IN CELSIUS
	 */
	protected void setMAX_TEMP_IN_CELSIUS(double max_temp) {MAX_TEMP = max_temp;}
	/**
	 * @param min_temp the mIN_TEMP to set IN CELSIUS
	 */
	protected void setMIN_TEMP_IN_CELSIUS(double min_temp) {MIN_TEMP = min_temp;}
	//Other Methods
	public String toString(){
		String res = "";
		res = res + super.toString();
		res = res + "Max Temperature = " + getMAX_TEMP_IN_CELSIUS()+" ºC" +"\n";
		res = res + "min Temperature = " + getMIN_TEMP_IN_CELSIUS()+" ºC" +"\n";
		return res;
		}
}
