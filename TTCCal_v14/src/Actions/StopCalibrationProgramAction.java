package Actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Data.ThermalCalibrationProgramData;
import Main.MainController;
import Ovens.Eurotherm2404;
import multimeters.Keithley2700;
import frontController.*;
import gui.MainScreenFrame;

import ThreadManagement.*;

public class StopCalibrationProgramAction implements Action{

	private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static String TIME_FORMAT_NOW = "HH:mm:ss";
	private long PROGRESS_SCREEN_REFRESH_TIME_PERIOD = 5000;

	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;

	private MainScreenFrame mainScreenFrame;
	private ThermalCalibrationProgramData program;

	private Keithley2700 K2700;
	private Eurotherm2404 E2404;


	public StopCalibrationProgramAction()throws Exception{
		result=null;
		main=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		main = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (stopProgram()==-1) return false;
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private int stopProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STOPPING THE PROGRAM \n");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		return 0;
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
	private void printActionMessageAndProgressScreenMessage(String _msg){
		printActionMessage(_msg);
		mainScreenFrame.appendTextToLogTextArea(_msg);
	}
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
