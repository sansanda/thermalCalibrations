package modBusRequest;
import data.CRC;


public class WriteSingleRegisterRequest extends ModBusRequest{
	private static char WRITESINGLEREGISTER_FUNCTIONCODE = 0x06;
	public WriteSingleRegisterRequest(char _controllerID, int _address,int _value){
		super(_controllerID,WRITESINGLEREGISTER_FUNCTIONCODE,_address);
		if (_value<0x0000 || _value>0xFFFF) return;
		
		char addressL,addressH,valueL,valueH;
		char[] req = new char[6];
		
		addressH = (char)(_address / 256);
		addressL = (char)(_address % 256);
		
		valueH = (char)(_value / 256);
		valueL = (char)(_value % 256);
		/*
		System.out.print("valueL in hex = ");
		for (int i=7;i==0;i--){
			System.out.print((valueL>>i) & 0x0001);
		}
		System.out.print("\n");
		System.out.print("valueH in hex = ");
		for (int i=7;i==0;i--){
			System.out.print((valueH>>i) & 0x0001);
		}
		System.out.print("\n");
		*/
		System.out.println("valueH = "+Integer.toString(valueH));
		System.out.println("valueL = "+Integer.toString(valueL));
		//System.out.println("char max value = "+);
		
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
