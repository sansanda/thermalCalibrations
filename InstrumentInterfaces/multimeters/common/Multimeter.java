package common;

public abstract class Multimeter implements IMultimeter {

	@Override
	public String[] getAvailableFunctions() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method getAvailableFunctions not implemented");
	}

	@Override
	public void setAvailableFunctions(String[] functions) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method setAvailableFunctions not implemented");
	}

	@Override
	public int[] getAvailableChannels() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method getAvailableChannels not implemented");
	}

	@Override
	public void setAvailableChannels(int[] channels) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method setAvailableChannels not implemented");
	}

	@Override
	public void configureMultimeter(String xml_configurationFilePath) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method configureMultimeter not implemented");
	}

	@Override
	public int getNumberOfExpansionSlots() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method getNumberOfExpansionSlots not implemented");
	}

	@Override
	public void setNumberOfExpansionSlots(int n) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method setNumberOfExpansionSlots not implemented");
	}

	@Override
	public CommPort_I getCommPort() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method getCommPort not implemented");
	}

	@Override
	public void setCommPort(CommPort_I commPort) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method setCommPort not implemented");
	}

	@Override
	public String[] getAvailableOutputConnections() throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method getAvailableOutputConnections not implemented");
	}

	@Override
	public void setAvailableOutputConnection(String output) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method setAvailableOutputConnection not implemented");
	}

	@Override
	public void redirectOutputTo(String output) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method redirectOutputTo not implemented");
	}

	@Override
	public void enableOutput(boolean enable) throws Exception {
		// TODO Auto-generated method stub
		throw new Exception("Method enableOutput not implemented");
	}

}
