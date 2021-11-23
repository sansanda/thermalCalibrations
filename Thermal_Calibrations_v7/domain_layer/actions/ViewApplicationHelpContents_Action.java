package actions;

import java.io.IOException;
import javax.swing.JFrame;
import Main.MainController;
import controller.*;
import views.CalibrationSetUp_HelpContents_JFrame;


public class ViewApplicationHelpContents_Action extends AbstractAction{
	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;
	private CalibrationSetUp_HelpContents_JFrame helpContentsFrame;

	public ViewApplicationHelpContents_Action(){
		result=null;
		main=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		main = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		helpContentsFrame = new CalibrationSetUp_HelpContents_JFrame(main);
		helpContentsFrame.addWindowListener(main);
		helpContentsFrame.setVisible(true);
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
}
