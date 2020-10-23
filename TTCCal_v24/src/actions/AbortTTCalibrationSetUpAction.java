package actions;
import java.io.IOException;
import action.*;
import gui.OvenTemperatureWarningFrame;


public class AbortTTCalibrationSetUpAction extends Action{

	private OvenTemperatureWarningFrame ovenTemperatureWarningFrame;
	private Ovens.Eurotherm2404_v2 E2404;

	public AbortTTCalibrationSetUpAction()throws Exception{
		super();
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (stopProgram()==-1) return false;
		ovenTemperatureWarningFrame = new OvenTemperatureWarningFrame(mainController);
		ovenTemperatureWarningFrame.addWindowListener(mainController);
		ovenTemperatureWarningFrame.setVisible(true);
		return true;
	}
	private int stopProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessage("STOPPING THE PROGRAM \n");
		}
		printActionMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		return 0;
	}
	private void printActionMessage(String _msg){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(_msg);
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			if((((k/2)-(_msg.length()/2))+_msg.length()+i)>100) break;
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.println("\n");
	}
	@SuppressWarnings("unused")
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
