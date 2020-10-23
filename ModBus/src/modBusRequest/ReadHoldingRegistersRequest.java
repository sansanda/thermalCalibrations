package modBusRequest;
import data.CRC;


public class ReadHoldingRegistersRequest extends ModBusRequest{
	private static char READHOLDINGREGISTERS_FUNCTIONCODE = 0x03;
	public ReadHoldingRegistersRequest(char _controllerID, int _address,int _q){
		super(_controllerID,READHOLDINGREGISTERS_FUNCTIONCODE,_address);
		if (_q<0 || _q>125) return;
		
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
