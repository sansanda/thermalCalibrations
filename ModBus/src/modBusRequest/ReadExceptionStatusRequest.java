package modBusRequest;
import data.CRC;


public class ReadExceptionStatusRequest extends ModBusRequest{
	private static char READEXCEPTIONSTATUS_FUNCTIONCODE = 0x07;
	public ReadExceptionStatusRequest(char _controllerID){
		super(_controllerID,READEXCEPTIONSTATUS_FUNCTIONCODE,0);
		
		char[] req = new char[6];
	
		req[0] = _controllerID;
		req[1] = getFunctionCode();
		CRC crc = new CRC();
		setDataToSend(crc.getCRC(req));
	}
}
