package sensors_and_transducers;
/**
 * Resistance Temperature Detector
 * @author David Sánchez Sánchez
 *
 */
public abstract class RTD extends TemperatureSensor {
	//Constants
	public static final double UN_DECIMO_DIN_ACCURACY=0.012;
	public static final double UN_TERCIO_DIN_ACCURACY=0.03;
	public static final double CLASS_A_ACCURACY=0.06;
	public static final double CLASS_B_ACCURACY=0.12;
	//Variables
	//Constructors
	/**
	 * Default constructor
	 */
	public RTD() {
		super();
	}
	/**
	 *
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public RTD(double _accuracy,String _manufacturer, String _manufacturerReference) {
		super(_accuracy,_manufacturer, _manufacturerReference);
		// TODO Auto-generated constructor stub
	}
	/**
	 * constructor
	 * @param _MIN_TEMP IN CELSIUS
	 * @param _MAX_TEMP IN CELSIUS
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public RTD(double _MIN_TEMP, double _MAX_TEMP,double _accuracy,String _manufacturer, String _manufacturerReference) {
		super(_MIN_TEMP, _MAX_TEMP,_accuracy,_manufacturer, _manufacturerReference);
		// TODO Auto-generated constructor stub
	}
	//Getters and Setters
	//Other Methods
	public String toString(){
		return super.toString();
	}

}
