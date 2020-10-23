package basics;

import java.util.ArrayList;
import java.util.Iterator;

import javax.measure.Measurable;

import SDM.ISource;
import basics.MeasureCommand;

public class SourceCommand extends Command {

	private ISource source = null;
	private MeasureCommand mesureCommand = null;
	private ArrayList<ICommand> commands = null;
	
	public SourceCommand(ISource _source, 
							Object _sourcePhysicMagnitude, 
							Measurable<?> _sourceLevel, 
							Measurable<?> _complianceLevel, 
							Object _compliancePhysicMagnitude, 
							MeasureCommand _mesureCommand) {
		source = _source;
		source.setSourceLevel(_sourceLevel);
		source.setSourcePhysicsMagnitude(_sourcePhysicMagnitude);
		source.setComplianceLevel(_complianceLevel);
		source.setCompliancePhysicsMagnitude(_compliancePhysicMagnitude);
		mesureCommand = _mesureCommand;
		commands = new ArrayList<ICommand>();
		setIsExecuted(false);
	}
	
	/**
	 * @return the level
	 */
	public Measurable<?> getSourceLevel() {
		return source.getSourceLevel();
	}
	/**
	 * @return the compliance
	 */
	public Measurable<?> getComplianceLevel() {
		return source.getComplianceLevel();
	}
	
	public void addCommand(ICommand command) {
		commands.add(command);
	}
	@Override
	public void execute() throws Exception {
		//Source
		System.out.println("Executing Source Command");
		if (source!=null)
		{
			while (!source.output());
		}
		
		//Aqui ejecutamos los demás comandos a ejecutar antes de medir
		Iterator<ICommand> i = commands.iterator();
		while (i.hasNext())
		{
			System.out.println("xxxxxxxxxxxxxxxxxxx");
			ICommand c = i.next();
			c.execute();
			while(!c.isExecuted()) {}
		}
		//Medimos (Readback)
		if (mesureCommand!=null)
		{
			mesureCommand.execute();
			while(!mesureCommand.isExecuted()) {}
			
		}
		setIsExecuted(true);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "SourceCommand [\n source=" + source + ", mesureCommand=" + mesureCommand + ", commands=" + commands + "\n]";
	}
	
}
