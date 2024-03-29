package actions;

import java.io.IOException;

import javax.swing.JFrame;

import controller.ActionRequest;
import controller.ActionResult;
import views.CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame;
import Main.MainController;

public class InstrumentsComunicationsConfig_Action extends AbstractAction{

	private ActionResult result = null;
	private MainController mainController = null;
	private CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame configuration_Frame = null;
	private JFrame frameWhoHasTheRequest = null;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";

	public InstrumentsComunicationsConfig_Action(){
		result=null;
		frameWhoHasTheRequest = null;
		mainController=null;
	}
	public boolean execute(ActionRequest _req) throws IOException, Exception {
		configuration_Frame = new CalibrationSetUp_InstrumentsComunicationsAndTemperatureSensor_Config_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,mainController);
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
	

}
