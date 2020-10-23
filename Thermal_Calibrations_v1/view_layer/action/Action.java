package action;


import java.io.IOException;
import javax.swing.JFrame;

import view_ForTTCsCalibration.TTCsCalibration_MainScreenFrame;

import Main.MainController;
import Main.TTC_Calibration_MainController;


public class Action extends AbstractAction {

	protected ActionResult result;
	protected MainController mainController;
	protected JFrame frameWhoHasTheRequest;
	protected TTCsCalibration_MainScreenFrame mainScreenFrame;

	public Action() {
		result=null;
		mainController=null;
		frameWhoHasTheRequest = null;
		mainScreenFrame = null;
	}
	public boolean execute(ActionRequest _req) throws IOException, Exception {return false;}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult() {return result;}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (TTCsCalibration_MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main) {mainController = _main;}

}
