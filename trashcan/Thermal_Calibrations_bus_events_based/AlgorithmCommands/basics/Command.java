package basics;


/**
 * Superclass of 
 */
public abstract class Command implements ICommand{

    private boolean is_executed;

    public Command(){
        is_executed = false;
    }

    public abstract void execute() throws Exception;

    public boolean isExecuted(){
        return is_executed;
    }

    public void setIsExecuted(boolean _is_executed) {
        is_executed = _is_executed;
    }
}