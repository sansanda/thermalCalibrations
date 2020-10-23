package Actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Data.ThermalCalibrationProgramData;
import Main.MainController;
import Ovens.Eurotherm2404;
import multimeters.Keithley2700;
import frontController.*;
import gui.MainScreenFrame;

public class StartCalibrationProgramAction implements Action{

	private static String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static String TIME_FORMAT_NOW = "HH:mm:ss";
	private long PROGRESS_SCREEN_REFRESH_TIME_PERIOD = 5000;

	private ActionResult result;
	private MainController main;
	private JFrame frameWhoHasTheRequest;
	//IO to file
	private Formatter formatter = null;
	private File resultsFile = null;
    //Date
	private Calendar calendar;
	private SimpleDateFormat sdf = null;
	private SimpleDateFormat sdf2 = null;

	private MainScreenFrame mainScreenFrame;
	private ThermalCalibrationProgramData program;

	private Keithley2700 K2700;
	private Eurotherm2404 E2404;
	private int actualProgramStep;
	private Timer t1;

	private DefaultTableModel defaultTableModel;

	public StartCalibrationProgramAction()throws Exception{
		printActionMessage("Setting the Security Manager to null");
		System.setSecurityManager(null);
		printActionMessage("Creando instancias para manejar fechas y horas");
		calendar = Calendar.getInstance();
		sdf2 = new SimpleDateFormat(TIME_FORMAT_NOW);
		sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		t1 = new Timer();
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
		if (initializeProgram()==-1) return false;
		if (initializeResultsFile(program,resultsFile)==-1) return false;
		if (createInstruments()==-1) return false;
		if (createProgressScreen()==-1)return false;
		if (initializeTimer()==-1)return false;
		runCalibrationProgram();

		return true;
	}
	public void runCalibrationProgram() throws Exception{
		float pt100RealT = 0;
		pt100RealT = startProgramm();
		boolean trobat = pt100RealT<program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		boolean fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;

		if (!trobat){
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es superior o igual al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a tomar la temperatura actual estable.");
			printActionMessageAndProgressScreenMessage("Posteriormente se determinará cual es step de la rampa por el cual iniciar el programa.");
		}
		printActionMessageAndProgressScreenMessage("Taking "+program.getAdvanceProgramData().getMeasuresPerCicle()+" temperature measures in "+(program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures()*program.getAdvanceProgramData().getMeasuresPerCicle())/1000+" and calulating the Standard Deviation\n");
		while (K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),program.getAdvanceProgramData().getMeasuresPerCicle(),program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures())>program.getAdvanceProgramData().getStandardDeviation()){}
		printActionMessageAndProgressScreenMessage("Standard Deviation less than "+program.getAdvanceProgramData().getStandardDeviation()+"\n");
		//En este momento la temperatura es estable, guardamos los datos
		//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
		pt100RealT = read2700ChannelsAndSaveDataToFile(program);
		trobat = pt100RealT<program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
		printActionMessageAndProgressScreenMessage("Going to the first temperature set point above the actual oven temperature. \n");
		while(!trobat & !fiTempProfile){
			actualProgramStep++;
			fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
			if (!fiTempProfile) trobat = pt100RealT<program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		}
		if (fiTempProfile)
		{
			printActionMessageAndProgressScreenMessage("There is not temperatures in the temperature profile above the actual oven temperature");
			stopProgramm();
			return;
		}
		while (!fiTempProfile)
		{
			printActionMessageAndProgressScreenMessage("The first temperature set point above the actual oven temperature is "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" \n");
			printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" ºC \n");
			E2404.setTemperature(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
			printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
			E2404.setInAutoMode();
			printActionMessageAndProgressScreenMessage("Taking "+program.getAdvanceProgramData().getMeasuresPerCicle()+" temperature measures in "+(program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures()*program.getAdvanceProgramData().getMeasuresPerCicle())/1000+" and calulating the Standard Deviation\n");
			while (K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),program.getAdvanceProgramData().getMeasuresPerCicle(),program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures())>program.getAdvanceProgramData().getStandardDeviation()){}
			printActionMessageAndProgressScreenMessage("Standard Deviation less than "+program.getAdvanceProgramData().getStandardDeviation()+"\n");
			//En este momento la temperatura es estable, guardamos los datos
			//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
			read2700ChannelsAndSaveDataToFile(program);
			actualProgramStep++;
			fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
		}
		stopProgramm();
	}
	private float startProgramm() throws Exception{
		float pt100RealT = 0;
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STARTING THE PROGRAM FOR CALIBRATING THE "+ program.getMeasuresConfigurationData().getDevicesToMeasureData()[0].getDeviceReference()+" TTC");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
		printActionMessageAndProgressScreenMessage("actualTemperature = " + pt100RealT);
		return pt100RealT;
	}
	private void stopProgramm() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STOPPING THE PROGRAM\n");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		//TODO Liberar recursos y finalizar programa
	}
	private float read2700ChannelsAndSaveDataToFile(ThermalCalibrationProgramData _program) throws Exception{
		float pt100RealT = 0;
		float pt1004WResistance = 0;

		float[] channelsToMeasure= new float[_program.getMeasuresConfigurationData().getNDevicesToMeasure()];

		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
		printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

		for (int i=0;i<_program.getMeasuresConfigurationData().getNDevicesToMeasure();i++){
			printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
			channelsToMeasure[i] = K2700.measure4WireResistance(program.getMeasuresConfigurationData().getDevicesToMeasureData()[i].getDeviceChannel());
		}
		//actualizeResultsTable(this.mainScreenFrame.insertRowAtResultsTable(_data, _row));
		saveDataToFile(
				program,
				Integer.toString(actualProgramStep),
				sdf2.format(System.currentTimeMillis()),
				Integer.toString(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]),
				pt100RealT,
				pt1004WResistance,
				channelsToMeasure
				);
		return pt100RealT;
	}
	private int createInstruments()throws Exception{
		printActionMessage("Creando la instancia de Keithley2700.");
		K2700 = new Keithley2700(program.getInstrumentsData().getMultimeterData().getComPort());
		printActionMessage("Creando la instancia de Eurotherm2404.");
		E2404 = new Eurotherm2404(program.getInstrumentsData().getOvenData().getComPort(),program.getInstrumentsData().getOvenData().getControllerID());
		return 0;
	}
	private int createProgressScreen(){
		defaultTableModel = new DefaultTableModel();
    	defaultTableModel.setColumnCount(5);
    	defaultTableModel.setRowCount(5);
		mainScreenFrame.setDefaultTableModel(defaultTableModel);
		mainScreenFrame.getContentPane().setVisible(true);
		return 0;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private int initializeProgram()throws Exception{
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
            printActionMessage("Creando la instancia para el programa.");
    		program = new ThermalCalibrationProgramData(programFilePath);
    		System.out.println(program.toString());
    		actualProgramStep = 0;
    		return 0;
		} else {
            //log.append("Open command cancelled by user." + newline);
			return -1;
        }
	}
	private int initializeTimer(){
		printActionMessage("Initializing Program");
		t1.scheduleAtFixedRate(new RefreshDataTask(), 0, PROGRESS_SCREEN_REFRESH_TIME_PERIOD);
		return 0;
	}
	private int initializeResultsFile(ThermalCalibrationProgramData _program, File _resultsFile)throws Exception{
		String programResultsFilePath = "";
		JFileChooser fileChooser = new JFileChooser();
		JOptionPane.showMessageDialog(null,"Indique el fichero donde desea guardar los resultados.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			programResultsFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
			printActionMessage("Inicializando el fichero de salida de resultados....");
			_resultsFile = new File(programResultsFilePath);
			formatter = new Formatter (_resultsFile);
			formatter.format("%1$-30s%n", "Program Name: " + program.getGeneralProgramData().getProgramName());
		    formatter.flush ();
		    formatter.format("%1$-30s%n", "Program Type: " + program.getGeneralProgramData().getProgramType());
		    formatter.flush ();
		    formatter.format("%1$-30s%n", "Program Author: " + program.getGeneralProgramData().getProgramAuthor());
		    formatter.flush ();
		    formatter.format("%1$-30s%n", "Program Description: " + program.getGeneralProgramData().getProgramDescription());
		    formatter.flush ();
			formatter.format("%1$-30s%n", "DATE: " + sdf.format(calendar.getTime()));
		    formatter.flush ();
		    formatter.format("*********************************************************************************************************************************************************************************************************************************************************************************************************************************************%n");
		    formatter.flush ();
			formatter.format("%1$-20s %2$-20s %3$-20s %4$-40s %5$-40s", "# step", "time", "Desired TºC", "Real TºC (MultimeterChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")","Real T(Ohms) "+"(MultimeterChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")");
		    formatter.flush ();
		    for (int i=0;i<_program.getMeasuresConfigurationData().getNDevicesToMeasure();i++){
		    	formatter.format("%1$-40s",_program.getMeasuresConfigurationData().getDevicesToMeasureData()[i].getDeviceReference()+"(MultimeterChannel="+_program.getMeasuresConfigurationData().getDevicesToMeasureData()[i].getDeviceChannel()+")");
			    formatter.flush ();
		    }
		    formatter.format("%n");
		    formatter.flush ();
		    return 0;
		} else {
            //log.append("Open command cancelled by user." + newline);
			return -1;
        }
	}
	private void saveDataToFile(ThermalCalibrationProgramData _program,String _programStep, String _currentTimeInMillis, String _desiredTemp, float _realTemp,float _realTempInOhms,float[] _measuredChannels){
		printActionMessageAndProgressScreenMessage("Saving data to file  \n");
		formatter.format("%1$-20s %2$-20s %3$-20s %4$-40s %5$-40s", _programStep, _currentTimeInMillis, _desiredTemp, _realTemp,_realTempInOhms);
	    formatter.flush ();
		for (int i=0;i<_program.getMeasuresConfigurationData().getNDevicesToMeasure();i++){
	    	formatter.format("%1$-40s",Float.toString(_measuredChannels[i]));
		    formatter.flush ();
	    }
		formatter.format("%n");
	    formatter.flush ();
	}
	class RefreshDataTask extends TimerTask {
	    public void run() {
	    	mainScreenFrame.refreshProgressBarData(actualProgramStep/program.getTemperatureProfileData().getTemperatures().length);
			printActionMessageAndProgressScreenMessage("Refreshing Progress Screen Data.");

	    }
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
