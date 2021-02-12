package communications;

public interface I_RS232CommunicationsInterface {
	
	void initialize(String address, int baudRate, int nDataBits, int nStopBits, int parityType ) throws Exception;
	
}
