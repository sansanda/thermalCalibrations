package modBusRequest;

public abstract class ModBusRequest {
	private char functionCode;
	private char controllerID;
	private int address;
	char[] req_plus_crc;

	public ModBusRequest(int _controllerID,char _functionCode, int _address){
		if (_functionCode>43) return;
		if (_address<0x0000 || _address>0xFFFF) return;
		if (_controllerID<0x00 || _controllerID>0xFF) return;
		address = _address;
		functionCode = _functionCode;
		controllerID = (char)_controllerID;
	}
	public void setControllerID(int _controllerID){
		controllerID = (char)_controllerID;
	}
	public char getControllerID(){
		return controllerID;
	}
	public void setFunctionCode(char _functionCode){
		functionCode = _functionCode;
	}
	public char getFunctionCode(){
		return functionCode;
	}
	public void setAddress(int _address){
		address = _address;
	}
	public int getAddress(){
		return address;
	}
	public char[] getDataToSend(){
		return req_plus_crc;
	}
	public void setDataToSend(char[] _dataToSend){
		req_plus_crc = _dataToSend;
	}
	public String toString (){
		String res="/";
		if (!req_plus_crc.equals(null)){
			for (int i=0;i<req_plus_crc.length;i++){
				res = res + (int)req_plus_crc[i]+"/";
			}
		}else
		{
			res = res + "Data to send is empty.";
		}
		return res;
	}
}
