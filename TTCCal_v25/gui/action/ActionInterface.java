package action;


import java.io.IOException;

import javax.swing.JFrame;

import main.MainController;


public interface ActionInterface {
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest);
	/* Pass the main*/
	public void setMain(MainController main);
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception;
	/* Return the result of the execution*/
	public ActionResult getResult();
}