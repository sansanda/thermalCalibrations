package modBusRequest;
import data.CRC;


public class readCoilsRequest extends ModBusRequest {
	private static char READCOILS_FUNCTIONCODE = 0x01;
	public readCoilsRequest(char _functionCode,char _controllerID, int _address,int _q){
		super(_controllerID,READCOILS_FUNCTIONCODE,_address);
		if (_q<0 || _q>2000) return;
		
		char addressl,addressh,ql,qh;
		char[] req = new char[6];
		
		addressh = (char)(_address / 256);
		addressl = (char)(_address % 256);
		qh = (char)(_q / 256);
		ql = (char)(_q % 256);
		req[0] = _controllerID;
		req[1] = getFunctionCode();
		req[2] = addressh;
		req[3] = addressl;
		req[4] = qh;
		req[5] = ql;
		CRC crc = new CRC();
		setDataToSend(crc.getCRC(req));
	}
}
