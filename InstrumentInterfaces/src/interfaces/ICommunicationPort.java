package interfaces;

public interface ICommunicationPort {
	public void open() throws Exception;
	public void close() throws Exception;
	public void sendData(String message) throws Exception;
	public void sendData(byte[] message) throws Exception;
	public byte[] readDataAsByteArray() throws Exception;
	public String readDataAsString() throws Exception;
	public void initialize(String wantedPortName) throws Exception;
}
