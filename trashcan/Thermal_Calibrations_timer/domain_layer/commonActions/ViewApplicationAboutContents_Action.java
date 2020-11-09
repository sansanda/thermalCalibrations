package commonActions;

import java.io.IOException;
import javax.swing.JFrame;
import Main.MainController;
import controller.*;
import view_ForDiodesCalibration.DiodesCalibration_MainScreenFrame;
import view_CommonParts.AboutContentsFrame;
import view_CommonParts.OvenTemperatureWarningFrame;


public class ViewApplicationAboutContents_Action implements Action{
	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;
	private AboutContentsFrame aboutContentsFrame;

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
		aboutContentsFrame = new AboutContentsFrame(main);
		aboutContentsFrame.addWindowListener(main);
		aboutContentsFrame.setVisible(true);
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
