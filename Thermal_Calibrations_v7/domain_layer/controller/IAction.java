package controller;


import java.io.*;
import javax.swing.JFrame;
import Main.MainController;

public interface IAction {
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest);
	/* Pass the main*/
	public void setMain(MainController main);
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception;
	/* Return the result of the execution*/
	public ActionResult getResult();
}