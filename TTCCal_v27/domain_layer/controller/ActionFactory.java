package controller;
import commonActions.ExitApplication_Action;
import commonActions.ViewApplicationAboutContents_Action;
import commonActions.ViewApplicationHelpContents_Action;

import actionsForDiodeCalibration.AbortRunningDiodesCalibrationSetUp_Action;
import actionsForDiodeCalibration.CreateNewDiodesCalibrationSetUp_Action;
import actionsForDiodeCalibration.EditExistingDiodesCalibrationSetUp_Action;
import actionsForDiodeCalibration.InstrumentsComunicationsAndTemperatureSensorConfig_Action;
import actionsForDiodeCalibration.RunADiodesalibrationSetUp_Action;
import actionsForDiodeCalibration.ViewExistingDiodesCalibrationProgram_Action;
import actionsForTTCCalibration.*;

public abstract class ActionFactory {

	public static Action createAction(String _action) throws Exception
	{
		/* Return Action object */
		if (_action.equals("RunTTCalibrationSetUpAction")) {
			return new RunTTCalibrationSetUpAction();
		}
		if (_action.equals("AbortTTCalibrationSetUpAction")) {
			return new AbortTTCalibrationSetUpAction();
		}
		if (_action.equals("CreateNewTTCalibrationSetUpAction")) {
			return new CreateNewTTCalibrationSetUpAction();
		}
		if (_action.equals("EditExistingTTCalibrationSetUpAction")) {
			return new EditExistingTTCalibrationSetUpAction();
		}
		if (_action.equals("ViewExistingTTCalibrationSetUpAction")) {
			return new ViewExistingCalibrationProgramAction();
		}
		if (_action.equals("ExitApplicationAction")) {
			return new ExitApplication_Action();
		}
		if (_action.equals("ViewAboutContentsAction")) {
			return new ViewApplicationHelpContents_Action();
		}
		if (_action.equals("ViewHelpContentsAction")) {
			return new ViewApplicationHelpContents_Action();
		}
		if (_action.equals("InstrumentsComunicationsAndTemperatureSensorConfig_Action")) {
			return new InstrumentsComunicationsAndTemperatureSensorConfig_Action();
		}

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

		return null;
	}
}