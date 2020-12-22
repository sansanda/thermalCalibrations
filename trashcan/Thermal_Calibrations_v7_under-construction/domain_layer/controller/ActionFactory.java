package controller;

import actions.AbortCalibrationSetUp_Action;
import actions.InstrumentsComunicationsConfig_Action;
import actions.NewDiodeCalibrationSetUp_Action;
import actions.NewTTCCalibrationSetUp_Action;
import actions.EditCalibrationSetUp_Action;
import actions.ExitApplication_Action;
import actions.RunCalibrationSetUp_Action;
import actions.TemperatureSensorConfig_Action;
import actions.ViewApplicationHelpContents_Action;
import actions.ViewCalibrationProgram_Action;

public abstract class ActionFactory {

	public static String RunCalibrationSetUp_Action = 		"RunCalibrationSetUp_Action";
	public static String AbortCalibrationSetUp_Action = 	"AbortCalibrationSetUp_Action";
	public static String ExitApplication_Action = 			"ExitApplication_Action";
	public static String NewTTCCalibrationSetUp_Action = 	"NewTTCCalibrationSetUp_Action";
	public static String NewDiodeCalibrationSetUp_Action = 	"NewDiodeCalibrationSetUp_Action";
	public static String EditCalibrationSetUp_Action = 		"EditCalibrationSetUp_Action";
	public static String ViewCalibrationSetUp_Action = 		"ViewCalibrationSetUp_Action";
	public static String ShowAboutContents_Action = 		"ShowAboutContentsAction";
	public static String ShowHelpContents_Action = 			"ShowHelpContents_Action";
	public static String TemperatureSensorConfig_Action = 	"TemperatureSensorConfig_Action";
	public static String InstrumentsConfig_Action = 	"InstrumentsComunicationsConfig_Action";

	public static Action createAction(String _action) throws Exception
	{
		/* Return Action object */
		if (_action.equals(RunCalibrationSetUp_Action)) {
			return new RunCalibrationSetUp_Action();
		}
		if (_action.equals(AbortCalibrationSetUp_Action)) {
			return new AbortCalibrationSetUp_Action();
		}
		if (_action.equals(NewTTCCalibrationSetUp_Action)) {
			return new NewTTCCalibrationSetUp_Action();
		}
		if (_action.equals(NewDiodeCalibrationSetUp_Action)) {
			return new NewDiodeCalibrationSetUp_Action();
		}
		if (_action.equals(EditCalibrationSetUp_Action)) {
			return new EditCalibrationSetUp_Action();
		}
		if (_action.equals(ViewCalibrationSetUp_Action)) {
			return new ViewCalibrationProgram_Action();
		}
		if (_action.equals(ExitApplication_Action)) {
			return new ExitApplication_Action();
		}
		if (_action.equals(ShowAboutContents_Action)) {
			return new ViewApplicationHelpContents_Action();
		}
		if (_action.equals(ShowHelpContents_Action)) {
			return new ViewApplicationHelpContents_Action();
		}
		if (_action.equals(TemperatureSensorConfig_Action)) {
			return new TemperatureSensorConfig_Action();
		}
		if (_action.equals(InstrumentsConfig_Action)) {
			return new InstrumentsComunicationsConfig_Action();
		}
		return null;
	}
}