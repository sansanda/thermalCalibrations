package sensors_and_transducers;

public class KThermocouple extends TemperatureSensor{

	/**
	 * Default constructor
	 */
	public KThermocouple(){
		super();
	}
	/**
	 * constructor
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public KThermocouple(double _accuracy,String _manufacturer, String _manufacturerReference) {
		super(_accuracy,_manufacturer, _manufacturerReference);
	}
	/**
	 *
	 * @param _MAX_TEMP IN CELSIUS
	 * @param _MIN_TEMP IN CELSIUS
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public KThermocouple(double _MAX_TEMP, double _MIN_TEMP,double _accuracy,String _manufacturer, String _manufacturerReference) {
		super(_MAX_TEMP, _MIN_TEMP,_accuracy,_manufacturer, _manufacturerReference);
	}
}
