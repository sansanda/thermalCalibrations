package frontController;
import Actions.*;

abstract class ActionFactory {

	public static Action createAction(String _action) throws Exception
	{
		/* Return Action object */
		if (_action.equals("StartCalibrationProgramAction")) {
			return new StartCalibrationProgramAction();
		}
		if (_action.equals("StopCalibrationProgramAction")) {
			return new StopCalibrationProgramAction();
		}
		if (_action.equals("CreateNewCalibrationProgramAction")) {
			return new CreateNewCalibrationProgramAction();
		}
		if (_action.equals("EditExistingCalibrationProgramAction")) {
			return new EditExistingCalibrationProgramAction();
		}
		if (_action.equals("ViewExistingCalibrationProgramAction")) {
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
		return null;
	}
}