package composites;

import basics.Command;
import basics.DelayCommand;
import basics.MeasureCommand;
import basics.SourceCommand;

public class SDMCommand extends Command {

	private SourceCommand sourceCommand = null;
	private DelayCommand delayCommand = null;
	private MeasureCommand measureCommand = null;
	
	
	public SDMCommand(SourceCommand _sourceCommand, DelayCommand _delayCommand, MeasureCommand _measureCommand) 
	{
		sourceCommand = _sourceCommand;
		delayCommand = _delayCommand;
		measureCommand = _measureCommand;
		setIsExecuted(false);
	}
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		setIsExecuted(false);
		if (sourceCommand!=null)
		{
			sourceCommand.execute();
			while(!sourceCommand.isExecuted()) {}
		}
		if (delayCommand!=null)
		{
			delayCommand.execute();
			while(!delayCommand.isExecuted()) {}
		}
		if (measureCommand!=null)
		{
			measureCommand.execute();
			while(!measureCommand.isExecuted()) {}
		}
		setIsExecuted(true);
	}
	
	/**
	 * @return the sourceCommand
	 */
	public SourceCommand getSourceCommand() {
		return sourceCommand;
	}
	/**
	 * @return the delayCommand
	 */
	public DelayCommand getDelayCommand() {
		return delayCommand;
	}
	/**
	 * @return the measureCommands
	 */
	public MeasureCommand getMeasureCommand() {
		return measureCommand;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SDMCommand [\n sourceCommand=" + sourceCommand + ", \n delayCommand=" + delayCommand + ", \n measureCommand="
				+ measureCommand + "\n]";
	}
	
}
