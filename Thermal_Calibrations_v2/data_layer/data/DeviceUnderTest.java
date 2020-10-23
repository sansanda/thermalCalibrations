package data;

public abstract class DeviceUnderTest {
	//Constants
	private static final String R2W = "R2W";
	private static final String R3W = "R3W";
	private static final String R4W = "R4W";
	private static final String V = "V";
	//Variables
	private String deviceType;
	private String deviceReference;
	//Constructors
	private DeviceUnderTest(){}
	public DeviceUnderTest(String _deviceType,String _deviceReference)throws Exception{
		deviceType = _deviceType;
		deviceReference = _deviceReference;
	}
	//Getters and Setters
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
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
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @param deviceReference the deviceReference to set
	 */
	public void setDeviceReference(String deviceReference) {
		this.deviceReference = deviceReference;
	}
	//Other Methods
	public String toString(){
		String res = "***********DUT***************\n";
		res = res + "Device type = " + getDeviceType()+"\n";
		res = res + "Device reference = " + getDeviceReference()+"\n";
		return res;
	}

}
