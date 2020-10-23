package Actions;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Actions.StartCalibrationProgramAction.ProgressScreenRefreshTask;
import Data.InstrumentsData;
import Data.ThermalCalibrationProgramData;
import Main.MainController;
import Ovens.Eurotherm2404;
import ThreadManagement.SwingWorker;
import multimeters.Keithley2700;
import frontController.Action;
import frontController.ActionRequest;
import frontController.ActionResult;
import gui.Configuration_Frame;
import gui.MainScreenFrame;
import gui.OvenManualOperation_JFrame;
import gui.ProgramProgressScreenFrame;

public class StartOvenInManualOperation_Action implements Action, ActionListener {

	private static long 	OVEN_MANUAL_SCREEN_REFRESH_TIME_PERIOD = 1000;
	private static String 	INSTRUMENTS_DATA_FILE_PATH = "ConfigurationFiles\\instrumentsData.xml";

	private ActionResult 	result = null;
	private MainController 	mainController = null;
	private JFrame 			frameWhoHasTheRequest = null;
	private MainScreenFrame mainScreenFrame = null;
	private OvenManualOperation_JFrame ovenManualOperation_JFrame = null;
	private Eurotherm2404 	E2404;
	private InstrumentsData instrumentsData = null;

	private Timer 			t1;
	private int 			ovenRealTemp = 0;
	private int 			ovenTempSetPoint = 0;


	public StartOvenInManualOperation_Action()throws Exception{
		printActionMessage("Setting the Security Manager to null");
		System.setSecurityManager(null);
		printActionMessage("Creando instancias para manejar fechas y horas");
		result=null;
		mainController=null;
	}
	@Override
	public boolean execute(ActionRequest _req) throws IOException, Exception {
		if (initializeTemperatureVariables()==-1) return false;
		if (createOven()==-1) return false;
		if (inializeOvenTemperatureSetPoint(ovenTempSetPoint) == -1) return false;
		if (createOvenManualOperationFrame()==-1) return false;
		if (addEventListenerToSetPointTemperaturejTextField(this) == -1) return false;
		//if (initializeTimer()==-1)return false;
		return true;
	}
	private int inializeOvenTemperatureSetPoint(int _ovenTempSetPoint) {
		this.setOvenTemperature(_ovenTempSetPoint);
		return 0;
	}
	private int initializeTemperatureVariables() {
		ovenRealTemp = 0;
		ovenTempSetPoint = 0;
		return 0;
	}
	public ActionResult getResult() {
		return result;
	}
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest) {
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main) {
		mainController = _main;
	}
	private int createOven()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de Instrumentos.");
		instrumentsData = new InstrumentsData(INSTRUMENTS_DATA_FILE_PATH);
		printActionMessage("Creando la instancia de Eurotherm2404.");
		//System.out.println(instrumentsData.getOvenData().getComPort()+"  ----------   "+instrumentsData.getOvenData().getControllerID());
		E2404 = new Eurotherm2404(instrumentsData.getOvenData().getComPort(),instrumentsData.getOvenData().getControllerID());
		return 0;
	}
	private int createOvenManualOperationFrame(){
		ovenManualOperation_JFrame = new OvenManualOperation_JFrame();
		ovenManualOperation_JFrame.addWindowListener(mainController);
		ovenManualOperation_JFrame.setVisible(true);
		return 0;
	}
	private int initializeTimer(){
		printActionMessage("Initializing Timer");
		if (t1 == null) t1 = new Timer();
		t1.scheduleAtFixedRate(new TTask(), 0, OVEN_MANUAL_SCREEN_REFRESH_TIME_PERIOD);
		return 0;
	}
	class TTask extends TimerTask {
		public void run() {
			//leer la temperatura del horno
			ovenRealTemp = getOvenTemperature();
			printActionMessage("ovenRealTemp = getOvenTemperature() = "+ getOvenTemperature());
			//Actualizar OvenManualOperation_JFrame con la temperatura deseada y la temperatura del horno
			//insertTempPointsInGraph(System.currentTimeMillis(),ovenRealTemp,ovenTempSetPoint);
			//ovenManualOperation_JFrame.getActualOvenTempjTextField().setText(String.valueOf(ovenRealTemp));
		}
	};
	private void insertTempPointsInGraph(long _SystemTimeInMilliseconds,double _realOvenTemp,double _desiredOvenTemp){
		ovenManualOperation_JFrame.insertTempPointAtRealOvenTempGraphSerie(_SystemTimeInMilliseconds, _realOvenTemp);
		ovenManualOperation_JFrame.insertTempPointAtDesiredOvenTempGraphSerie(_SystemTimeInMilliseconds, _desiredOvenTemp);
	}
	private int addEventListenerToSetPointTemperaturejTextField(EventListener _listener){
		printActionMessage("Adding PropertyChangeListener To setPointTemperaturejTextField.");
		ovenManualOperation_JFrame.getSetPointTemperaturejTextField().addActionListener((ActionListener) _listener);
		return  0;
	}
	public void actionPerformed(ActionEvent event) {
		printActionMessage(event.toString());
		int newTemp = Integer.valueOf(((JTextField)event.getSource()).getText());
		if (newTemp > 500 || newTemp <0){
			JOptionPane.showMessageDialog(null,"Only temperatures between 0 and 500 ºC are allowed. Try again.");
			((JTextField)event.getSource()).setText("0");
			return;
		}
		ovenTempSetPoint = newTemp;
		printActionMessage("new Temp Value = " + newTemp);
		this.setOvenTemperature(newTemp);
	}
	private int setOvenTemperature(int _newTemp){
		E2404.setTemperature(_newTemp);
		E2404.setInAutoMode();
		printActionMessage("oven Temp Value = " + this.getOvenTemperature());

		return 0;
	}
	private int getOvenTemperature(){
		return E2404.readTemperature();
	}
	private void printActionMessage(String _msg){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(_msg);
		for(int i=1;i<=((k/2)-(_msg.length()/2));i++){
			if((((k/2)-(_msg.length()/2))+_msg.length()+i)>100) break;
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
