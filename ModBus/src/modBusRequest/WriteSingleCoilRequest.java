package modBusRequest;
import data.CRC;


public class WriteSingleCoilRequest extends ModBusRequest{
	private static char WRITESINGLECOIL_FUNCTIONCODE = 0x05;
	public WriteSingleCoilRequest(char _controllerID, int _address,int _value){
		super(_controllerID,WRITESINGLECOIL_FUNCTIONCODE,_address);
		if (_value!=0x0000 || _value!=0xFF00) return;
		
		char addressL,addressH,valueL,valueH;
		char[] req = new char[6];
		
		addressH = (char)(_address / 256);
		addressL = (char)(_address % 256);
		valueH = (char)(_value / 256);
		valueL = (char)(_value % 256);
		req[0] = _controllerID;
		req[1] = getFunctionCode();
		req[2] = addressH;
		req[3] = addressL;
		req[4] = valueH;
		req[5] = valueL;
		CRC crc = new CRC();
		setDataToSend(crc.getCRC(req));
	}
}
