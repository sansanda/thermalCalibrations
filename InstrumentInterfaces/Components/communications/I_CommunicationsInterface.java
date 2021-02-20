package communications;

public interface I_CommunicationsInterface {
	
	public String 	getType() throws Exception;
	public String 	getStandard() throws Exception;
	public String 	getAddress() throws Exception;
	public void 	setAddress(String address) throws Exception;
	public void 	open() throws Exception;
	public void 	close() throws Exception;
	public byte[] 	read() throws Exception;
	public void 	write(String data) throws Exception;
	public byte[] 	ask(String query) throws Exception;
	
}
