package test_and_measurement_equipment;

public abstract class Multimeter extends Instrument implements MultimeterInterface{
	//Constants
	//Variables
	//Constructors
	/**
	 * Default constructor
	 */
	public Multimeter(){super();}
	/**
	 * Constructor
	 * @param _manufacturer
	 * @param _model
	 */
	public Multimeter(String _manufacturer, String _model, String _serialNumber){super(_manufacturer, _model, _serialNumber);}
	//Getters and Setters

	//Other Methods
	public String toString(){
		String res="";
		res = res + super.toString();
		return res;
	}
}
