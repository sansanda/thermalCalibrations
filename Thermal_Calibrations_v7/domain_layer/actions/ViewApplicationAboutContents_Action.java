package actions;

import java.io.IOException;
import javax.swing.JFrame;
import Main.MainController;
import controller.*;
import views.CalibrationSetUp_AboutContents_JFrame;


public class ViewApplicationAboutContents_Action extends AbstractAction{
	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;
	private CalibrationSetUp_AboutContents_JFrame aboutContentsFrame;

	public ViewApplicationAboutContents_Action(){
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
		aboutContentsFrame = new CalibrationSetUp_AboutContents_JFrame(main);
		aboutContentsFrame.addWindowListener(main);
		aboutContentsFrame.setVisible(true);
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
}
