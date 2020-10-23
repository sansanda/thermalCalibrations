package modBusRequest;
import data.CRC;


public class ReadInputRegistersRequest extends ModBusRequest{
	private static char READINPUTREGISTERS_FUNCTIONCODE = 0x04;
	public ReadInputRegistersRequest(int _controllerID, int _address,int _q){
		super(_controllerID,READINPUTREGISTERS_FUNCTIONCODE,_address);
		if (_q<0 || _q>125) return;
		
		char addressl,addressh,ql,qh;
		char[] req = new char[6];
		
		addressh = (char)(_address / 256);
		addressl = (char)(_address % 256);
		qh = (char)(_q / 256);
		ql = (char)(_q % 256);
		req[0] = (char)_controllerID;
		req[1] = getFunctionCode();
		req[2] = addressh;
		req[3] = addressl;
		req[4] = qh;
		req[5] = ql;
		for (int i=0;i<req.length;i++){
			System.out.print(Integer.toString(req[i]));
		}
		CRC crc = new CRC();
		setDataToSend(crc.getCRC(req));
	}
}
