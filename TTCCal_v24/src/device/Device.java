package device;

public abstract class Device {
	private String deviceName;
	private String deviceReference;
	private String deviceDescription;
	private boolean selectedForTest;

	//Constructors
	public Device(){}
	public Device(String _deviceName,String _deviceReference,String _deviceDescription,boolean _selected){
		deviceName = _deviceName;
		deviceReference = _deviceReference;
		deviceDescription = _deviceDescription;
		selectedForTest = _selected;
	}
	//Getters and Setters Methods
	/**
	 *
	 */
	public String getDeviceReference(){return deviceReference;}
	/**
	 *
	 * @param _deviceReference
	 */
	public void setDeviceReference(String _deviceReference){deviceReference = _deviceReference;}
	/**
	 *
	 * @return
	 */
	public boolean isSelected(){return selectedForTest;}
	/**
	 *
	 * @param _selected
	 */
	public void setSelectedForTest(boolean _selected) {selectedForTest = _selected;}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String _deviceName) {deviceName = _deviceName;}
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {return deviceName;}
	/**
	 * @param deviceDescription the deviceDescription to set
	 */
	public void setDeviceDescription(String _deviceDescription) {deviceDescription = _deviceDescription;}
	/**
	 * @return the deviceDescription
	 */
	public String getDeviceDescription() {return deviceDescription;}
	//Other Methods
	public String toString(){
		String res="";
		res=res+"Device Name = "+getDeviceName()+"\n";
		res=res+"Device Description = "+getDeviceDescription()+"\n";
		res=res+"Device Reference = "+getDeviceReference()+"\n";
		res=res+"Selected For Test = "+isSelected()+"\n";
		return res;
	}


}
