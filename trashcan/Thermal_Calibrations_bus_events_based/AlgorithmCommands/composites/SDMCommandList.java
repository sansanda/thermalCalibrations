package composites;

import java.util.ArrayList;
import java.util.Iterator;

import algorimthRunner.IAlgorithmStep;
import basics.Command;

public class SDMCommandList extends Command implements IAlgorithmStep{

	private int executionLevel;

	private ArrayList<SDMCommand> sdmCommandList = null;

	public SDMCommandList(ArrayList<SDMCommand> _commands, int _executionLevel) {
		sdmCommandList = _commands;
		executionLevel = _executionLevel;
		setIsExecuted(false);
	}
	
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("EXECUTING SDM COMMAND LIST.......");
		Iterator<SDMCommand> i = sdmCommandList.iterator();
		setIsExecuted(false);
		while (i.hasNext())
		{
			SDMCommand sdmCommand = i.next();
			sdmCommand.execute();
			while (!sdmCommand.isExecuted()) {}	
		}
		setIsExecuted(true);
	}

	/**
	 * @param _SDMcommandList the sdmSweep to set
	 */
	public void setSDMCommands(ArrayList<SDMCommand> _SDMcommandList) {
		this.sdmCommandList = _SDMcommandList;
	}
	
	/**
	 * @return the commands
	 */
	public ArrayList<SDMCommand> getSDMCommands() {
		return sdmCommandList;
	}

	@Override
	public int getExecutionLevel() {
		// TODO Auto-generated method stub
		return executionLevel;
	}
	/**
	 * @param executionLevel the executionLevel to set
	 */
	public void setExecutionLevel(int _executionLevel) {
		if (_executionLevel<0) return;
		this.executionLevel = _executionLevel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SDMCommandList [\n executionLevel=" + executionLevel + ", \n sdmCommandList=" + sdmCommandList + "\n]";
	}
	
	

}
