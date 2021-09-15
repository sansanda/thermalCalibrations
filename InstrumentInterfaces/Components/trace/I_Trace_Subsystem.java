package trace;

public interface I_Trace_Subsystem {
	
	public void sendClearBufferCommand() throws Exception;
	public String queryTraceData() throws Exception;
	
	
}
