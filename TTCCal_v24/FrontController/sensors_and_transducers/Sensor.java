/**
 *
 */
package sensors_and_transducers;

/**
 * @author David Sánchez Sánchez
 *
 */
public abstract class Sensor {
	//Constants
	//Variables
	private String Manufacturer = "";
	private String ManufacturerReference = "";
	private double accuracy = 0.0;
	//Constructors
	/**
	 * Default constructor
	 */
	public Sensor(){}
	/**
	 * Constructor
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public Sensor(double _accuracy, String _manufacturer, String _manufacturerReference){
		setAccuracy(_accuracy);
		setManufacturer(_manufacturer);
		setManufacturerReference(_manufacturerReference);
		}
	//Getters and Setters
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(double _accuracy) {accuracy = _accuracy;}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String _manufacturer) {Manufacturer = _manufacturer;}
	/**
	 * @param manufacturerReference the manufacturerReference to set
	 */
	public void setManufacturerReference(String _manufacturerReference) {ManufacturerReference = _manufacturerReference;}
	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {return accuracy;}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {return Manufacturer;}
	/**
	 * @return the manufacturerReference
	 */
	public String getManufacturerReference() {return ManufacturerReference;}
	//Other Methods
	public String toString(){
		String res="";
		res = res + "Manufacturer = " + getManufacturer() + "\n";
		res = res + "Manufacturer Reference = " + getManufacturerReference() + "\n";
		res = res + "Accuracy = " + getAccuracy() + "\n";
		return res;
	}
}
