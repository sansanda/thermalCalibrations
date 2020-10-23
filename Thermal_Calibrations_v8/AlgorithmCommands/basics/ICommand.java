package basics;

public interface ICommand {
    void execute() throws Exception;
    boolean isExecuted();
    void setIsExecuted(boolean _is_executed);
}
