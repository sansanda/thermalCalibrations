package actions;

import java.io.IOException;
import javax.swing.JFrame;

import calibrationSetUp.CalibrationSetUp;

import views.CalibrationSetUp_Viewer_JFrame;
import controller.*;
import Main.MainController;

public class NewTTCCalibrationSetUp_Action extends AbstractAction{
	private ActionResult 					result;
	private MainController 					mainController;
	private CalibrationSetUp_Viewer_JFrame	calibrationSetUpViewer;
	private JFrame 							frameWhoHasTheRequest;
	private static String 					instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String 					temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String 						ttcSetUpFilePath = "";

	public NewTTCCalibrationSetUp_Action(){
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
				CalibrationSetUp.TEMPERATURE_VS_RESISTANCE_TYPE,
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
}
