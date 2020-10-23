package test_and_measurement_equipment;

public abstract class Instrument {
	//Constants
	//Variables
	private String manufacturer = "";
	private String model="";
	private String serialNumber="";

	//Constructors
	/**
	 * Default constructor
	 */
	public Instrument(){}
	/**
	 * Constructor
	 * @param _manufacturer
	 * @param _model
	 */
	public Instrument( String _manufacturer, String _model, String _serialNumber){
		setManufacturer(_manufacturer);
		setModel(_model);
		setSerialNumber(_serialNumber);
	}
	//Getters and Setters
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String _manufacturer) {manufacturer = _manufacturer;}
	/**
	 * @param manufacturerReference the manufacturerReference to set
	 */
	public void setModel(String _model) {model = _model;}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String _serialNumber) {serialNumber = _serialNumber;}
	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {return manufacturer;}
	/**
	 * @return the manufacturerReference
	 */
	public String getModel() {return model;}
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {return serialNumber;}
	//Other Methods
	public String toString(){
		String res="";
		res = res + "Manufacturer = " + getManufacturer() + "\n";
		res = res + "Model = " + getModel() + "\n";
		res = res + "Serial Number = " + getSerialNumber() + "\n";
		return res;
	}
}
