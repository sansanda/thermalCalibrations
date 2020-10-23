package devices;

import org.jdom.Element;

public class Resistance extends Device{
	private double R;
	private double i;
	private double v;
	public Resistance(Element _resistanceElement)throws Exception{
		super(_resistanceElement);
		initializeResistanceFromElement(_resistanceElement);
	}
	public Resistance(String reference) throws Exception {
		super(Device.RESISTANCE, reference);
		setR(100);
		setI(0.01);
	}
	public Resistance(String reference,double _r) throws Exception {
		super(Device.RESISTANCE, reference);
		setR(_r);
		setI(0.01);
	}
	/**
	 * @return the resistance
	 */
	public double getR() {
		return R;
	}
	/**
	 * @return the i
	 */
	public double getI() {
		return i;
	}
	/**
	 * @return the v
	 */
	public double getV() {
		return v;
	}
	/**
	 * @param _resistance the resistance to set
	 */
	public void setR(double _resistance) {
		R = _resistance;
	}
	/**
	 * @param _i the i to set
	 */
	public void setI(double _i) {
		i = _i;
		v = calculateV();
	}
	/**
	 * @param _v the v to set
	 */
	public void setV(double _v) {
		v = _v;
		i = calculateI();
	}
	//XML
	public Element convertToJDOMElement(String _name){
			org.jdom.Element resistanceElement;
			resistanceElement = super.convertToJDOMElement(_name);
			resistanceElement.setAttribute("R",Double.toString(getR()));
			resistanceElement.setAttribute("I",Double.toString(getI()));
			resistanceElement.setAttribute("V",Double.toString(getV()));
			return resistanceElement;
	}
	private void initializeResistanceFromElement(Element _resistanceElement) throws Exception{
		super.initializeDUTFromElement(_resistanceElement);
		setR(Double.valueOf(_resistanceElement.getAttributeValue("R")));
		setI(Double.valueOf(_resistanceElement.getAttributeValue("I")));
		setV(Double.valueOf(_resistanceElement.getAttributeValue("V")));
	}
	//Other methods
	private double calculateI() {
		return getV()/getR();
	}
	private double calculateV() {
		return getI()*getR();
	}
	public int compareTo(Object arg0) {
		if (super.compareTo(arg0)==0 & Double.compare(getR(),((Resistance)arg0).getR())==0) return 0;
		else return Double.compare(getR(),((Resistance)arg0).getR());
	}
	public boolean equals(Object arg0){
		if (compareTo(arg0)==0) return true;
		else return false;
	}
	public String toString(){
		String res = super.toString();
		res = res + "Resistance = " + getR()+"\n";
		res = res + "I = " + getI()+"\n";
		res = res + "V = " + getV()+"\n";
		return res;
	}
}
