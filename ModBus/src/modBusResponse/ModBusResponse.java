package modBusResponse;

public abstract class ModBusResponse {
	private byte functionCode;
	private byte byteCount;
	private byte[] data;

	public ModBusResponse(byte[] _responseData){
		if (_responseData == null) return;
		data = new byte[_responseData.length];
		setFunctionCode(_responseData[0]);
		setByteCount(_responseData[1]);
		for (int i=2;i<_responseData.length;i++){
			data[i-2] = _responseData[i-2];
		}
		System.out.print(this.toString());
	}
	public void setFunctionCode(byte _functionCode){
		functionCode = _functionCode;
	}
	public byte getFunctionCode(){
		return functionCode;
	}
	public void setByteCount(byte _byteCount){
		byteCount = _byteCount;
	}
	public byte getByteCount(){
		return byteCount;
	}
	public void setData(byte[] _data){
		data = _data;
	}
	public byte[] getData(){
		return data;
	}
	public String toString(){
		String res="";
		res = res + "functionCode = "+functionCode+"\n";
		res = res + "byteCount = "+byteCount+"\n";
		if (!data.equals(null)){
			for (int i=0;i<data.length;i++){
				res = res + data[i] + " ";
			}
			res = res + "\n";
		}else
		{
			res = res + "No Data in Response.... \n";
		}

		return res;
	}
}
