package actions;

import java.io.IOException;
import javax.swing.JFrame;

import calibrationSetUp.CalibrationSetUp;

import views.CalibrationSetUp_Viewer_JFrame;
import controller.*;
import Main.MainController;

public class NewDiodeCalibrationSetUp_Action implements Action{
	private ActionResult 					result;
	private MainController 					mainController;
	private CalibrationSetUp_Viewer_JFrame	calibrationSetUpViewer;
	private JFrame 							frameWhoHasTheRequest;
	private static String 					instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String 					temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String 						ttcSetUpFilePath = "";
	private static final int 				NEW_MODE = 0;
	private static final int 				VIEW_MODE = 1;
	private static final int 				EDIT_MODE = 2;

	public NewDiodeCalibrationSetUp_Action(){
		result=null;
		frameWhoHasTheRequest = null;
		mainController=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		CalibrationSetUp calibrationSetUp = new CalibrationSetUp(
				CalibrationSetUp.TEMPERATURE_VS_VOLTAGE_TYPE,
				null,
				null,
				null,
				null);
		calibrationSetUpViewer = new CalibrationSetUp_Viewer_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,ttcSetUpFilePath,calibrationSetUp,NEW_MODE,mainController);
		calibrationSetUpViewer.addWindowListener(mainController);
		calibrationSetUpViewer.setVisible(true);
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}

	private void printAction(String action){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(action.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(action);
		for(int i=1;i<=((k/2)-(action.length()/2));i++){
			if((((k/2)-(action.length()/2))+action.length()+i)>100) break;
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
