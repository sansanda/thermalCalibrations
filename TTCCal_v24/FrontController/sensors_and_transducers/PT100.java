package sensors_and_transducers;

public class PT100 extends RTD {
	//Constants
	//Variables
	private double Ro = 100.0;
	private double A = 3.9083E-3;
	private double B = -5.775E-7;
	private double C = -4.183E-12;
	//Constructors
	/**
	 * Default constructor
	 */
	public PT100(){
		super();
	}
	/**
	 * constructor
	 * @param _manufacturer
	 * @param _manufacturerReference
	 */
	public PT100(double _accuracy,String _manufacturer, String _manufacturerReference){
		super(_accuracy,_manufacturer, _manufacturerReference);
	}
	/**
	 * constructor
	 * @param _MAX_TEMP IN CELSIUS
	 * @param _MIN_TEMP IN CELSIUS
	 * @param _manufacrurer
	 * @param _manufacturerReference
	 */
	public PT100(double _MAX_TEMP, double _MIN_TEMP,double _accuracy,String _manufacturer, String _manufacturerReference){
		super(_MIN_TEMP, _MAX_TEMP,_accuracy,_manufacturer, _manufacturerReference);
	}
	/**
	 * constructor
	 * @param _MAX_TEMP IN CELSIUS
	 * @param _MIN_TEMP IN CELSIUS
	 * @param _manufacrurer
	 * @param _manufacturerReference
	 * @param _Ro IN OHMS
	 * @param _A IN CELSIUS-1
	 * @param _B IN CELSIUS-2
	 * @param _C IN CELSIUS-4
	 */
	public PT100(double _MAX_TEMP, double _MIN_TEMP,double _accuracy,String _manufacturer, String _manufacturerReference,double _Ro,double _A,double _B,double _C) {
		super(_MIN_TEMP, _MAX_TEMP,_accuracy,_manufacturer, _manufacturerReference);
		setRo(_Ro);
		setA(_A);
		setB(_B);
		setC(_C);
	}
	//Getters and Setters
	/**
	 * @return the ro IN OHMS
	 */
	public double getRo() {return Ro;}
	/**
	 * @return the a IN CELSIUS-1
	 */
	public double getA() {return A;}
	/**
	 * @return the b IN CELSIUS-2
	 */
	public double getB() {return B;}
	/**
	 * @return the c IN CELSIUS-4
	 */
	public double getC() {return C;}
	/**
	 * @param ro the ro to set IN OHMS
	 */
	public void setRo(double _Ro) {Ro = _Ro;}
	/**
	 * @param a the a to set IN CELSIUS-1
	 */
	public void setA(double _A) {A = _A;}
	/**
	 * @param b the b to set IN CELSIUS-2
	 */
	public void setB(double _B) {B = _B;}
	/**
	 * @param c the c to set IN CELSIUS-4
	 */
	public void setC(double _C) {C = _C;}
	//Other Methods
	public String toString() {
		String res="";
		res = res + super.toString();
		res = res + "Ro = " + getRo() + " Ohms" + "\n";
		res = res + "A = " + getA() + " CelsiusE-1" + "\n";
		res = res + "B = " + getB() + " CelsiusE-2" + "\n";
		res = res + "C = " + getC() + " CelsiusE-4" + "\n";
		return res;
	}

}
