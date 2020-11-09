package devices;

import org.jdom.Element;

public class Diode extends Device {
	private double i;
	private double v;
	private double io;
	private static double K = 8.67E-5;
	private double eta;


	public Diode(Element _diodeElement)throws Exception{
		super(_diodeElement);
		initializeDiodeFromElement(_diodeElement);
	}
	public Diode(String reference) throws Exception {
		super(Device.DIODE, reference);
		setEta(2.0);
		setIo(0.01);
		setI(0.010);
	}
	public Diode(String reference,double _eta,double _io,double _i) throws Exception {
		super(Device.DIODE, reference);
		setEta(_eta);
		setIo(_io);
		setI(_i);
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
	 * @return the io
	 */
	public double getIo() {
		return io;
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
	/**
	 * @param io the io to set
	 */
	public void setIo(double io) {
		this.io = io;
	}
	/**
	 * @return the eta
	 */
	public double getEta() {
		return eta;
	}
	/**
	 * @param eta the eta to set
	 */
	public void setEta(double eta) {
		this.eta = eta;
	}
	//XML
	public Element convertToJDOMElement(String _name){
			org.jdom.Element diodeElement;
			diodeElement = super.convertToJDOMElement(_name);
			diodeElement.setAttribute("I",Double.toString(getI()));
			diodeElement.setAttribute("V",Double.toString(getV()));
			diodeElement.setAttribute("Io",Double.toString(getIo()));
			diodeElement.setAttribute("ETA",Double.toString(getEta()));
			return diodeElement;
	}
	private void initializeDiodeFromElement(Element _diodeElement) throws Exception{
		super.initializeDUTFromElement(_diodeElement);
		setI(Double.valueOf(_diodeElement.getAttributeValue("I")));
		setV(Double.valueOf(_diodeElement.getAttributeValue("V")));
		setIo(Double.valueOf(_diodeElement.getAttributeValue("Io")));
		setEta(Double.valueOf(_diodeElement.getAttributeValue("ETA")));
	}
	//Other methods
	private double calculateI() {
		return getIo()*(Math.exp(getV()/(getEta()*K*getDeviceTemp()))-1);
	}
	private double calculateV() {
		return Math.log((getI()/getIo())+1)*(getEta()*K*getDeviceTemp());
	}
	public int compareTo(Object arg0) {
		if (super.compareTo(arg0)==0
				& Double.compare(getIo(),((Diode)arg0).getIo())==0
				& Double.compare(getEta(),((Diode)arg0).getEta())==0
				) return 0;
		else return 1;
	}
	public boolean equals(Object arg0){
		if (compareTo(arg0)==0) return true;
		else return false;
	}
	public String toString(){
		String res = super.toString();
		res = res + "Io = " + getIo()+"\n";
		res = res + "ETA = " + getEta()+"\n";
		res = res + "I = " + getI()+"\n";
		res = res + "V = " + getV()+"\n";
		return res;
	}
}
