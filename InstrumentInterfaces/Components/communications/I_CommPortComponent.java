package communications;

public interface I_CommPortComponent {
	public void open() throws Exception;
	public void close() throws Exception;
	public byte[] read() throws Exception;
	public void write(String data) throws Exception;
	public byte[] ask(String query) throws Exception;
}
