package actions;

import java.io.IOException;
import javax.swing.JFrame;
import controller.*;
import views.CalibrationSetUp_MainScreen_JFrame;
import views.CalibrationSetUp_OvenTemperatureWarning_JFrame;
import Main.MainController;
import Ovens.Eurotherm2404_v5;

public class AbortCalibrationSetUp_Action implements Action{

	private ActionResult result;
	private MainController mainController;
	private JFrame frameWhoHasTheRequest;
	private CalibrationSetUp_OvenTemperatureWarning_JFrame ovenTemperatureWarningFrame;

	private CalibrationSetUp_MainScreen_JFrame mainScreenFrame;

	private Eurotherm2404_v5 E2404_v5;


	public AbortCalibrationSetUp_Action() throws Exception{
		result=null;
		mainController=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (CalibrationSetUp_MainScreen_JFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (stopProgram()==-1) return false;
		ovenTemperatureWarningFrame = new CalibrationSetUp_OvenTemperatureWarning_JFrame(mainController);
		ovenTemperatureWarningFrame.addWindowListener(mainController);
		ovenTemperatureWarningFrame.setVisible(true);
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private int stopProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessage("STOPPING THE PROGRAM \n");
		}
		printActionMessage("Setting the TSP1 to 0 ºC \n");
		E2404_v5.setTemperatureSetpoint1(0);
		printActionMessage("Setting the 2404 in Auto Mode \n");
		E2404_v5.setInAutoMode();
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
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
