/**
 * 
 */
package communications;

/**
 * @author david
 *
 */
public interface I_RS232CommunicationsStandard {
	
	void initialize(String address, int baudRate, int nDataBits, int nStopBits, int parityType ) throws Exception;
	
	void setBaudrate(int baudrate) throws Exception;
	void setDataBitsNumber(int n) throws Exception;
	void setStopBitsNumber(int n) throws Exception;
	void setParityType(int type) throws Exception;
	
	int getBaudrate() throws Exception;
	int getDataBitsNumber() throws Exception;
	int getStopBitsNumber() throws Exception;
	int getParityType() throws Exception;
	
}
