package Actions;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.CalibrateTTC_Program;
import Data.ThermalCalibrationProgramData;
import Main.MainController;
import Ovens.Eurotherm2404;
import multimeters.Keithley2700;
import frontController.*;
import gui.ProgressScreenFrame;

public class StartCalibrationProgramAction implements Action{

	private static double TEMPERATURE_DEV_MAX_ERROR = 0.03;
	private static int NUMBER_MEASURES_PER_TEMPERATURE_POINT = 120;
	private static int TIME_BETWEEN_MEASURES_PER_TEMPERATURE_POINT_IN_MILLISECONDS = 1000;
	private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static String TIME_FORMAT_NOW = "HH:mm:ss";
	private static String DEFAULT_OPENNING_DIR = "Y:\\Proyectos\\TTCCal_v1\\Programs";


	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;
	//IO to file
	private Formatter formatter = null;
	private File file = null;
    //Date
	private Calendar calendar;
	private SimpleDateFormat sdf = null;
	private SimpleDateFormat sdf2 = null;
	private ProgressScreenFrame progressScreen;
	private ThermalCalibrationProgramData program;

	private Keithley2700 K2700;
	private Eurotherm2404 E2404;
	private int actualProgramStep;


	public StartCalibrationProgramAction()throws Exception{
		printActionMessage("Setting the Security Manager to null");
		System.setSecurityManager(null);
		printActionMessage("Creando instancias para manejar fechas y horas");
		calendar = Calendar.getInstance();
		sdf2 = new SimpleDateFormat(TIME_FORMAT_NOW);
		sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		result=null;
		main=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		main = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		initializeProgram();
		initializeResultsFile();
		createProgressScreen();
		createInstruments();
		runCalibrationProgram();
		return true;
	}
	public void runCalibrationProgram(){
		float pt100RealT = 0;
		float pt1004WResistance = 0;
		float TTC_SENSE_4WResistance = 0;
		float TTC_HEATING_4WResistance = 0;

		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STARTING THE PROGRAM FOR CALIBRATING THE "+ program.getMeasuresConfigurationData().getTTCsData()[0].getRefernce()+" TTC");
		}
		try {
			printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
			E2404.setTemperature(0);
			printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
			E2404.setInAutoMode();
			pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
			printActionMessageAndProgressScreenMessage("actualTemperature = " + pt100RealT);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (pt100RealT>=program.getTemperatureProfileData().getTemperatures()[actualProgramStep]){
			//JOptionPane.showMessageDialog(null,"La temperatura actual del horno es superior o igual al primer valor de la rampa indicada en el programa.");
			//JOptionPane.showMessageDialog(null,"Se va a proceder a tomar la temperatura actual estable.");
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es superior o igual al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a tomar la temperatura actual estable.");
			printActionMessageAndProgressScreenMessage("Posteriormente se determinará cual es step de la rampa por el cual iniciar el programa.");
		}
		try {
			printActionMessageAndProgressScreenMessage("Taking "+NUMBER_MEASURES_PER_TEMPERATURE_POINT+" temperature measures in "+(TIME_BETWEEN_MEASURES_PER_TEMPERATURE_POINT_IN_MILLISECONDS*NUMBER_MEASURES_PER_TEMPERATURE_POINT)/1000+" and calulating the Standard Deviation\n");
			while (K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),NUMBER_MEASURES_PER_TEMPERATURE_POINT,TIME_BETWEEN_MEASURES_PER_TEMPERATURE_POINT_IN_MILLISECONDS)>TEMPERATURE_DEV_MAX_ERROR){}
			printActionMessageAndProgressScreenMessage("Standard Deviation less than "+TEMPERATURE_DEV_MAX_ERROR+"\n");
			//En este momento la temperatura es estable, guardamos los datos
			//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
			printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
			pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
			printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
			pt1004WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
			printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the TTC RSense \n");
			TTC_SENSE_4WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTTCsData()[0].getRSenseChannel());
			printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the TTC RHeat \n");
			TTC_HEATING_4WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTTCsData()[0].getRHeatChannel());
			printActionMessageAndProgressScreenMessage("Saving data to file  \n");
			saveDataToFile("Tamb",sdf2.format(System.currentTimeMillis()),0,pt100RealT,pt1004WResistance,TTC_SENSE_4WResistance,0);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (actualProgramStep<program.getTemperatureProfileData().getTemperatures().length)
			{
				if (pt100RealT>=program.getTemperatureProfileData().getTemperatures()[actualProgramStep]){
					printActionMessageAndProgressScreenMessage("Going to the first temperature set point above the actual oven temperature. \n");
					while(pt100RealT>=program.getTemperatureProfileData().getTemperatures()[actualProgramStep]){
						actualProgramStep++;
						progressScreen.refreshProgressBarData(actualProgramStep/program.getTemperatureProfileData().getTemperatures().length);
					}
				}
				if (actualProgramStep<program.getTemperatureProfileData().getTemperatures().length){
					printActionMessageAndProgressScreenMessage("The first temperature set point above the actual oven temperature is "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" \n");
					printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" ºC \n");
					E2404.setTemperature(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
					printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
					E2404.setInAutoMode();
					printActionMessageAndProgressScreenMessage("Taking "+NUMBER_MEASURES_PER_TEMPERATURE_POINT+" temperature measures in "+(TIME_BETWEEN_MEASURES_PER_TEMPERATURE_POINT_IN_MILLISECONDS*NUMBER_MEASURES_PER_TEMPERATURE_POINT)/1000+" and calulating the Standard Deviation\n");
					while (K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),NUMBER_MEASURES_PER_TEMPERATURE_POINT,TIME_BETWEEN_MEASURES_PER_TEMPERATURE_POINT_IN_MILLISECONDS)>TEMPERATURE_DEV_MAX_ERROR){}
					printActionMessageAndProgressScreenMessage("Standard Deviation less than "+TEMPERATURE_DEV_MAX_ERROR+"\n");
					//En este momento la temperatura es estable, guardamos los datos
					//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
					printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
					pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
					printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
					pt1004WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
					printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the TTC RSense \n");
					TTC_SENSE_4WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTTCsData()[0].getRSenseChannel());
					printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the TTC RHeat \n");
					TTC_HEATING_4WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTTCsData()[0].getRHeatChannel());
					printActionMessageAndProgressScreenMessage("Saving data to file  \n");
					saveDataToFile(Integer.toString(actualProgramStep),sdf2.format(System.currentTimeMillis()),program.getTemperatureProfileData().getTemperatures()[actualProgramStep],pt100RealT,pt1004WResistance,TTC_SENSE_4WResistance,TTC_HEATING_4WResistance);
					actualProgramStep++;
					progressScreen.refreshProgressBarData(actualProgramStep/program.getTemperatureProfileData().getTemperatures().length);
				}else
				{
					printActionMessageAndProgressScreenMessage("There is not temperatures in the temperature profile above the actual oven temperature");
				}
			}
			for (int i=0;i<10;i++){
				printActionMessageAndProgressScreenMessage("STOPPING THE PROGRAM\n");
			}
			printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
			E2404.setTemperature(0);
			//TODO Liberar recursos y finalizar programa
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createInstruments()throws Exception{
		printActionMessage("Creando la instancia de Keithley2700.");
		K2700 = new Keithley2700(program.getInstrumentsData().getMultimeterData().getComPort());
		printActionMessage("Creando la instancia de Eurotherm2404.");
		E2404 = new Eurotherm2404(program.getInstrumentsData().getOvenData().getComPort(),program.getInstrumentsData().getOvenData().getControllerID());

	}
	private void createProgressScreen(){
		printActionMessage("Creando la instancia para el panel de muestra de resultados.");
		progressScreen = new ProgressScreenFrame(program,5,5);
		progressScreen.addWindowListener(main);
		progressScreen.addKeyListener(main);
		progressScreen.setSize(new Dimension(1000, 800));
		progressScreen.setVisible(true);
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private void initializeProgram()throws Exception{
		printActionMessage("Initializing Program");
		printActionMessage("Creando la instancia para el selector de ficheros");
		printActionMessage("Solicitamos los ficheros para ejecutar el programa y guardar los resultados.");
		JFileChooser fileChooser = new JFileChooser();
		JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ejecutar.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		String programFilePath = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
		printActionMessage("Creando la instancia para el programa.");
		program = new ThermalCalibrationProgramData(programFilePath);
		System.out.println(program.toString());
		actualProgramStep = 0;
	}
	private void initializeResultsFile()throws Exception{
		String programResultsFilePath = "";
		JFileChooser fileChooser = new JFileChooser();
		JOptionPane.showMessageDialog(null,"Indique el fichero donde desea guardar los resultados.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			programResultsFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
		printActionMessage("Inicializando el fichero de salida de resultados....");
		file = new File(programResultsFilePath);
		formatter = new Formatter (file);
		formatter.format("%1$-20s%n", "TTC Reference: " + program.getMeasuresConfigurationData().getTTCsData()[0].getRefernce());
	    formatter.flush ();
		formatter.format("%1$-20s%n", "DATE: " + sdf.format(calendar.getTime()));
	    formatter.flush ();
		formatter.format("%1$-20s %2$-30s %3$-30s %4$-30s %5$-30s %6$-30s %7$-30s%n", "# measure", "time", "Desired TºC", "Real TºC","PT100 4W-RESISTANCE","TTC 4W-SENSE-RESISTANCE","TTC 4W-HEATING-RESISTANCE");
	    formatter.flush ();
	}
	private void saveDataToFile(String var0, String var1, float var2, float var3,float var4,float var5,float var6){
		formatter.format("%1$-20s %2$-30s %3$-30s %4$-30s %5$-30s %6$-30s %7$-30s%n", var0, var1, var2, var3,var4,var5,var6);
		formatter.flush ();
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
		progressScreen.appendTextToLogPane(_msg);
	}
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
