package actionsForTTCCalibration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.*;
import data.TTCSetUpData;

import threadManagement.*;
import view_CommonParts.AboutContentsFrame;
import view_CommonParts.OvenTemperatureWarningFrame;
import view_ForTTCsCalibration.TTCsCalibration_MainScreenFrame;

import Main.MainController;
import Main.TTC_Calibration_MainController;
import Ovens.Eurotherm2404;
import multimeters.Keithley2700;


public class AbortTTCalibrationSetUpAction implements Action{

	private ActionResult result;
	private MainController mainController;
	private JFrame frameWhoHasTheRequest;
	private OvenTemperatureWarningFrame ovenTemperatureWarningFrame;

	private TTCsCalibration_MainScreenFrame mainScreenFrame;

	private Ovens.Eurotherm2404_v2 E2404;


	public AbortTTCalibrationSetUpAction()throws Exception{
		result=null;
		mainController=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (TTCsCalibration_MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (stopProgram()==-1) return false;
		ovenTemperatureWarningFrame = new OvenTemperatureWarningFrame(mainController);
		ovenTemperatureWarningFrame.addWindowListener(mainController);
		ovenTemperatureWarningFrame.setVisible(true);
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private int stopProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessage("STOPPING THE PROGRAM \n");
		}
		printActionMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessage("Setting the 2404 in Auto Mode \n");
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
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
