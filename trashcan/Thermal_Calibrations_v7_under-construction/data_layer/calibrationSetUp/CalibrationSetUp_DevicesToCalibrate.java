package calibrationSetUp;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

import devices.Device;
import devices.Diode;
import devices.Resistance;

import uoc.ei.tads.sequencies.LlistaAfSeq;

public class CalibrationSetUp_DevicesToCalibrate {
	private LlistaAfSeq devicesToCalibrate;

	public CalibrationSetUp_DevicesToCalibrate()throws Exception{
		devicesToCalibrate = new LlistaAfSeq();
	}
	public CalibrationSetUp_DevicesToCalibrate(Element _devicesToCalibreElement)throws Exception{
		initializeFromElement(_devicesToCalibreElement);
	}
	public CalibrationSetUp_DevicesToCalibrate(LlistaAfSeq _devicesToCalibrate)throws Exception{
		setDevicesToCalibrate(_devicesToCalibrate);
	}
	/**
	 * @return the nDevicesToCalibrate
	 */
	public int getNDevicesToCalibrate() {
		return devicesToCalibrate.nbElems();
	}
	/**
	 * @return the devicesToCalibrate
	 */
	public LlistaAfSeq getDevicesToCalibrate() {
		return devicesToCalibrate;
	}
	/**
	 * @param _devicesToCalibrate the devicesToCalibrate to set
	 */
	public void setDevicesToCalibrate(LlistaAfSeq _devicesToCalibrate) {
		devicesToCalibrate = _devicesToCalibrate;
	}
	//XML
	public Element toJDOMElement(String _name){
			org.jdom.Element devicesToCalibrateElement = new org.jdom.Element(_name);
			Enumeration devicesToCalibrateElements = devicesToCalibrate.elements();
			int i = 0;
			while (devicesToCalibrateElements.hasMoreElements()){
				devicesToCalibrateElement.addContent(((Device)devicesToCalibrateElements.nextElement()).convertToJDOMElement("DeviceToCalibrate"+i));
				i++;
			}
			return devicesToCalibrateElement;
	}
	public void initializeFromElement(Element _measuresConfigurationElement) throws Exception{
		List<Element> DevicesToCalibrateChildrenList = _measuresConfigurationElement.getChildren();
		devicesToCalibrate = new LlistaAfSeq(DevicesToCalibrateChildrenList.size());
		Iterator<Element> DevicesToCalibrateChildrenListIterator = DevicesToCalibrateChildrenList.iterator();
		while (DevicesToCalibrateChildrenListIterator.hasNext()){
			Element e = DevicesToCalibrateChildrenListIterator.next();
			int deviceType = Integer.valueOf(e.getAttributeValue("DeviceType"));
			switch (deviceType){
				case Device.RESISTANCE:
					devicesToCalibrate.afegeix(new Resistance(e));
					break;
				case Device.DIODE:
					devicesToCalibrate.afegeix(new Diode(e));
					break;
				default:
			}
		}
	}
	//Other Methods
	public void addDevice(Device _dut){
		devicesToCalibrate.afegeix(_dut);
	}
	public Enumeration getEnumeration(){
		return devicesToCalibrate.elements();
	}
	public String toString(){
		String res="";
		res = res + "DEVICES TO CALIBRATE" + "\n";
		Enumeration devicesToCalibrateEnumeration = devicesToCalibrate.elements();
		while (devicesToCalibrateEnumeration.hasMoreElements()){
			res = res + devicesToCalibrateEnumeration.nextElement().toString() + "\n";
		}
		return res;
	}
	//Main Method
	public static void main(String[] args) throws Exception{
		String programFilePath = "C:\\p.xml";
		LlistaAfSeq resistancesToCalibrateData = new LlistaAfSeq(7);
		for (int i=0;i<7;i++){
			resistancesToCalibrateData.afegeix(new Resistance(Integer.toString(i),i));
		}
		CalibrationSetUp_DevicesToCalibrate devicesToCalibrateData = new CalibrationSetUp_DevicesToCalibrate(resistancesToCalibrateData);
		System.out.println(devicesToCalibrateData.toString());

	}
}
