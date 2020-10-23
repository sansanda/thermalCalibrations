package Actions;

import java.awt.Dimension;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import Main.MainController;
import frontController.*;
import gui.TTCCalibrationViewerFrame;

public class CreateNewTTCalibrationSetUpAction implements Action{
	private ActionResult result;
	private MainController mainController;
	private TTCCalibrationViewerFrame ttcProgramViewer;
	private JFrame frameWhoHasTheRequest;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String ttcSetUpFilePath = "";


	public CreateNewTTCalibrationSetUpAction(){
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
		ttcProgramViewer = new TTCCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,ttcSetUpFilePath,null,0,mainController);
		ttcProgramViewer.addWindowListener(mainController);
		ttcProgramViewer.setVisible(true);
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
