package actionsForTTCCalibration;

import java.io.IOException;

import javax.swing.JFrame;

import controller.Action;
import controller.ActionRequest;
import controller.ActionResult;
import view_CommonParts.InstrumentsComunicationsAndTemperatureSensorConfigFrame;
import Main.MainController;

public class InstrumentsComunicationsAndTemperatureSensorConfig_Action implements Action {

	private ActionResult result = null;
	private MainController mainController = null;
	private InstrumentsComunicationsAndTemperatureSensorConfigFrame configuration_Frame = null;
	private JFrame frameWhoHasTheRequest = null;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";

	public InstrumentsComunicationsAndTemperatureSensorConfig_Action(){
		result=null;
		frameWhoHasTheRequest = null;
		mainController=null;
	}
	public boolean execute(ActionRequest _req) throws IOException, Exception {
		configuration_Frame = new InstrumentsComunicationsAndTemperatureSensorConfigFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,mainController);
		configuration_Frame.addWindowListener(mainController);
		configuration_Frame.setVisible(true);
		return true;
	}
	public ActionResult getResult() {
		// TODO Auto-generated method stub
		return result;
	}

	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest) {
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public void setMain(MainController _main) {
		// TODO Auto-generated method stub
		mainController = _main;
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
