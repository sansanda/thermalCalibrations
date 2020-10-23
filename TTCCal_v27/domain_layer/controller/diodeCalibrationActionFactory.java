package controller;
import commonActions.ExitApplication_Action;
import commonActions.ViewApplicationAboutContents_Action;
import commonActions.ViewApplicationHelpContents_Action;

import controller.Action;
import actionsForDiodeCalibration.*;


abstract class diodeCalibrationActionFactory {

	public static Action createAction(String _action) throws Exception
	{
		/* Return Action object */
		if (_action.equals("RunDiodesCalibrationSetUpAction")) {
			return new RunADiodesalibrationSetUp_Action();
		}
		if (_action.equals("AbortDiodesCalibrationSetUpAction")) {
			return new AbortRunningDiodesCalibrationSetUp_Action();
		}
		if (_action.equals("CreateNewDiodesCalibrationSetUpAction")) {
			return new CreateNewDiodesCalibrationSetUp_Action();
		}
		if (_action.equals("EditExistingDiodesCalibrationSetUpAction")) {
			return new EditExistingDiodesCalibrationSetUp_Action();
		}
		if (_action.equals("ViewExistingDiodesCalibrationSetUpAction")) {
			return new ViewExistingDiodesCalibrationProgram_Action();
		}
		if (_action.equals("ExitApplicationAction")) {
			return new ExitApplication_Action();
		}
		if (_action.equals("ViewAboutContentsAction")) {
			return new ViewApplicationAboutContents_Action();
		}
		if (_action.equals("ViewHelpContentsAction")) {
			return new ViewApplicationHelpContents_Action();
		}
		if (_action.equals("InstrumentsComunicationsAndTemperatureSensorConfig_Action")) {
			return new InstrumentsComunicationsAndTemperatureSensorConfig_Action();
		}
		return null;
	}
}