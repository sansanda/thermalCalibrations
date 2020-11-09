package devices;

import org.jdom.Element;

public abstract class Device implements Comparable{
	//Constants
	public static final int DIODE = 0;
	public static final int RESISTANCE = 1;
	//Variables
	private int 	deviceType;
	private String 	deviceReference;
	private double 	deviceTemp;
	private int 	connectedToMultimeterChannelNumber;
	//Constructors
	public Device(Element _dutElement)throws Exception{
		initializeDUTFromElement(_dutElement);
	}
	public Device(int _deviceType,String _deviceReference)throws Exception{
		validateDeviceType(_deviceType);
		setDeviceType(_deviceType);
		setDeviceReference(_deviceReference);
		setDeviceTemp(0.0);
		setConnectedToMultimeterChannelNumber(-1);
	}
	public Device(int _deviceType,String _deviceReference,double _temp)throws Exception{
		validateDeviceType(_deviceType);
		setDeviceType(_deviceType);
		setDeviceReference(_deviceReference);
		setDeviceTemp(_temp);
		setConnectedToMultimeterChannelNumber(-1);
	}
	public Device(int _deviceType,String _deviceReference,double _temp, int _multimeterChannel)throws Exception{
		validateDeviceType(_deviceType);
		validateMultimeterChannel(_multimeterChannel);
		setDeviceType(_deviceType);
		setDeviceReference(_deviceReference);
		setDeviceTemp(_temp);
		setConnectedToMultimeterChannelNumber(_multimeterChannel);
	}
	//Getters and Setters
	/**
	 * @return the deviceType
	 */
	public int getDeviceType() {
		return deviceType;
	}
	/**
	 * @return the deviceReference
	 */
	public String getDeviceReference() {
		return deviceReference;
	}
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(int _deviceType) {
		deviceType = _deviceType;
	}
	/**
	 * @param deviceReference the deviceReference to set
	 */
	public void setDeviceReference(String deviceReference) {
		this.deviceReference = deviceReference;
	}
	/**
	 * @return the temp
	 */
	public double getDeviceTemp() {
		return deviceTemp;
	}
	/**
	 * @return the connectedToMultimeterChannelNumber
	 */
	public int getConnectedToMultimeterChannelNumber() {
		return connectedToMultimeterChannelNumber;
	}
	/**
	 * @param _connectedToMultimeterChannelNumber the connectedToMultimeterChannelNumber to set
	 */
	public void setConnectedToMultimeterChannelNumber(
			int _connectedToMultimeterChannelNumber) {
		connectedToMultimeterChannelNumber = _connectedToMultimeterChannelNumber;
	}
	/**
	 * @param _temp the temp to set
	 */
	public void setDeviceTemp(double _temp) {
		deviceTemp = _temp;
	}
	//XML
	public Element convertToJDOMElement(String _name){
			org.jdom.Element deviceElement = new org.jdom.Element(_name);
			deviceElement.setAttribute("DeviceType",Integer.toString(getDeviceType()));
			deviceElement.setAttribute("DeviceReference",getDeviceReference());
			deviceElement.setAttribute("DeviceTemp",Double.toString(getDeviceTemp()));
			deviceElement.setAttribute("MultimeterChannel",Integer.toString(getConnectedToMultimeterChannelNumber()));
			return deviceElement;
	}
	public void initializeDUTFromElement(Element _deviceElement) throws Exception{
		setDeviceType(Integer.valueOf(_deviceElement.getAttributeValue("DeviceType")));
		setDeviceReference(_deviceElement.getAttributeValue("DeviceReference"));
		setDeviceTemp(Double.valueOf(_deviceElement.getAttributeValue("DeviceTemp")));
		setConnectedToMultimeterChannelNumber(Integer.valueOf(_deviceElement.getAttributeValue("MultimeterChannel")));
	}
	//Other Methods
	public boolean isConnectedToMultimeter(){
		if (getConnectedToMultimeterChannelNumber()==-1) return false;
		else return true;
	}
	private void validateDeviceType(int _deviceType)throws Exception {
		if (	_deviceType==RESISTANCE ||
				_deviceType==DIODE){
			//Nothing to do
		}else{
			throw new Exception("Invalid Device-Type Exception");
		}
	}
	private void validateMultimeterChannel(int c) throws Exception{
		if (c>=-1 || c<=20);//Nothing to do
		else throw new Exception("Invalid Multimeter Channel Exception");
	}
	public int compareTo(Object arg0) {
		if (getDeviceType()==((Device)arg0).getDeviceType() & getDeviceReference().equals(((Device)arg0).getDeviceReference())) return 0;
		else return 1;
	}
	public boolean equals(Object arg0){
		if (compareTo(arg0)==0) return true;
		else return false;
	}
	public String toString(){
		String res = "***********DEVICE***************\n";
		res = res + "Device type = " + getDeviceType()+"\n";
		res = res + "Connected to multimeter channel = " + getConnectedToMultimeterChannelNumber()+"\n";
		res = res + "Device reference = " + getDeviceReference()+"\n";
		res = res + "Device temperature = " + getDeviceTemp()+"\n";
		return res;
	}

}
