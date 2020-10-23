package modBusRequest;
import data.CRC;


public class ReadDiscreteInputsRequest extends ModBusRequest{
	private static char READDISCRETEINPUTS_FUNCTIONCODE = 0x02;
	public ReadDiscreteInputsRequest(char _controllerID, int _address,int _q){
		super(_controllerID,READDISCRETEINPUTS_FUNCTIONCODE,_address);
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
		
		for (int i=0;i<req.length;i++){
			System.out.print(req[i]);
		}
		
		CRC crc = new CRC();
		setDataToSend(crc.getCRC(req));
	}
}
