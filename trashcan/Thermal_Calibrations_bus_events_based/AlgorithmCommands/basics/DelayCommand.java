package basics;

import SDM.IDelay;

public class DelayCommand extends Command {

	private IDelay delayer = null;
	
	public DelayCommand(IDelay _delayer) 
	{
		delayer = _delayer;
		setIsExecuted(false);
	}
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Executing Delay Command");
		setIsExecuted(false);
		if (delayer!=null)
		{
			delayer.startDelay();
		}
		setIsExecuted(true);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DelayCommand [\n delayer=" + delayer + "\n]";
	}
	
}
