package frontController;
import Actions.*;

abstract class ActionFactory {

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
			return new ExitApplicationAction();
		}
		if (_action.equals("ViewAboutContentsAction")) {
			return new ViewAboutContentsAction();
		}
		if (_action.equals("ViewHelpContentsAction")) {
			return new ViewHelpContentsAction();
		}
		if (_action.equals("InstrumentsComunicationsAndTemperatureSensorConfig_Action")) {
			return new InstrumentsComunicationsAndTemperatureSensorConfig_Action();
		}
		return null;
	}
}