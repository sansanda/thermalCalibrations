package action;

import gui.MainScreenFrame;

import java.io.IOException;
import javax.swing.JFrame;

import main.MainController;


public class Action extends AbstractAction {

	protected ActionResult result;
	protected MainController mainController;
	protected JFrame frameWhoHasTheRequest;
	protected MainScreenFrame mainScreenFrame;

	public Action() {
		result=null;
		mainController=null;
		frameWhoHasTheRequest = null;
		mainScreenFrame = null;
	}
	public boolean execute(ActionRequest _req) throws IOException, Exception {
		// TODO Auto-generated method stub
		return false;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}

}
