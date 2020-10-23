package Actions;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

import ThreadManagement.*;

public class StartCalibrationProgramAction implements Action{

	private static String 	DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static String 	TIME_FORMAT_NOW = "HH:mm:ss";
	private static long 	PROGRESS_SCREEN_REFRESH_TIME_PERIOD = 1000;
	private static String 	MADRID_TIME_ZONE = "Madrid";
	private static long 	FIRST_STEP_ESTIMATED_TIME_DURATION = 900000;

	private ActionResult 	result;
	private MainController 	main;
	private JFrame 			frameWhoHasTheRequest;
	//IO to file
	private Formatter 		formatter = null;
	private File 			resultsFile = null;
    //Date
	private Calendar 		calendar = null;
	private SimpleDateFormat sdf = null;
	private SimpleDateFormat sdf2 = null;

	private MainScreenFrame mainScreenFrame;
	private ThermalCalibrationProgramData program;

	private Keithley2700 	K2700;
	private Eurotherm2404 	E2404;
	private int 			actualProgramStep;
	private Timer 			t1;
	private SwingWorker 	worker;

	private DefaultTableModel defaultTableModel;

	private long 			remainingTimeInMillis = 0;
	private long 			startProgramTimeInMillis = 0;
	private long 			elapsedTimeInMillis = 0;
	private long 			lastStepRealDurationTimeInMillis = 0;
	private long 			lastStepTimeInMillis = 0;
	private long 			nextStepEstimatedDurationTimeInMillis = 0;
	private int 			programProgress = 0;

	public StartCalibrationProgramAction()throws Exception{
		printActionMessage("Setting the Security Manager to null");
		System.setSecurityManager(null);
		printActionMessage("Creando instancias para manejar fechas y horas");
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
		if (initializeManagingTimeObjects(program)==-1) return false;
		if (initializeResultsFile(program,resultsFile)==-1) return false;
		if (createInstruments()==-1) return false;
		if (createProgressScreen(program)==-1)return false;
		if (initializeTimer()==-1)return false;
		runProgram();
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	private void runProgram(){
		worker = new SwingWorker() {
			public Object construct() {
				try {
					float pt100RealT = 0;
					pt100RealT = startProgram();

					boolean fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;

					checkRealTempVsFirstProgramStep(pt100RealT, program);
					waitForTemperatureStandardDeviationMatch(program);
					//En este momento la temperatura es estable, guardamos los datos
					//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
					pt100RealT = read2700ChannelsAndSaveDataToFile(program);
					if (goToTheFirstTemperatureSetPointAboveTheRealTemp(pt100RealT,program)==-1) return null;

					fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
					while (!fiTempProfile)
					{
						printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" ºC \n");
						E2404.setTemperature(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
						waitForTemperatureStandardDeviationMatch(program);
						//En este momento la temperatura es estable, guardamos los datos
						//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
						read2700ChannelsAndSaveDataToFile(program);
						actualProgramStep++;
						fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
					}
					finishProgram();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		worker.start();
	}
	private void checkRealTempVsFirstProgramStep(float _pt100RealT,ThermalCalibrationProgramData _program){
		boolean trobat = _pt100RealT<_program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		if (!trobat){
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es superior o igual al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a tomar la temperatura actual estable.");
			printActionMessageAndProgressScreenMessage("Posteriormente se determinará cual es step de la rampa por el cual iniciar el programa.");
		}else
		{
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es inferior al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a iniciar la rampa de temperaturas indicada por el programa.");
		}
	}
	private int goToTheFirstTemperatureSetPointAboveTheRealTemp(float _pt100RealT,ThermalCalibrationProgramData _program)throws Exception{
		boolean trobat = _pt100RealT<_program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		boolean fiTempProfile = actualProgramStep>=_program.getTemperatureProfileData().getTemperatures().length;
		printActionMessageAndProgressScreenMessage("Going to the first temperature set point above the actual oven temperature. \n");
		while(!trobat & !fiTempProfile){
			actualProgramStep++;
			fiTempProfile = actualProgramStep>=_program.getTemperatureProfileData().getTemperatures().length;
			if (!fiTempProfile) trobat = _pt100RealT<_program.getTemperatureProfileData().getTemperatures()[actualProgramStep];
		}
		if (fiTempProfile)
		{
			printActionMessageAndProgressScreenMessage("There is not temperatures in the temperature profile above the actual oven temperature");
			finishProgram();
			return -1;
		}
		printActionMessageAndProgressScreenMessage("The first temperature set point above the actual oven temperature is "+_program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" \n");
		return 0;
	}
	private void waitForTemperatureStandardDeviationMatch(ThermalCalibrationProgramData _program) throws Exception{
		float actualStepStandardDeviation = 0;
		printActionMessageAndProgressScreenMessage("Taking "+_program.getAdvanceProgramData().getMeasuresPerCicle()+" temperature measures in "+(_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures()*_program.getAdvanceProgramData().getMeasuresPerCicle())/1000+" and calulating the Standard Deviation\n");
		actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),_program.getAdvanceProgramData().getMeasuresPerCicle(),_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures());
		printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
		while (actualStepStandardDeviation>_program.getAdvanceProgramData().getStandardDeviation()){
			printActionMessageAndProgressScreenMessage("Standard Deviation is over than "+_program.getAdvanceProgramData().getStandardDeviation()+". Continue reading temperatures.\n");
			actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel(),_program.getAdvanceProgramData().getMeasuresPerCicle(),_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures());
			printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
		}
		printActionMessageAndProgressScreenMessage("Standard Deviation is less than "+_program.getAdvanceProgramData().getStandardDeviation()+"\n");
	}
	private float startProgram() throws Exception{
		float pt100RealT = 0;
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STARTING THE PROGRAM.... \n");
		}
		startProgramTimeInMillis = System.currentTimeMillis();
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		pt100RealT = K2700.measurePT100Temperature(program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel());
		printActionMessageAndProgressScreenMessage("actualTemperature = " + pt100RealT);
		return pt100RealT;
	}
	private void finishProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("FINISHING THE PROGRAM \n");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
	}
	private float read2700ChannelsAndSaveDataToFile(ThermalCalibrationProgramData _program) throws Exception{
		float pt100RealT = 0;
		float pt1004WResistance = 0;
		long currentTimeInMillis = 0;
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
		currentTimeInMillis = System.currentTimeMillis();
		recalculateEstimatedStepDurationTime(currentTimeInMillis);
		saveDataToFile(
				program,
				Integer.toString(actualProgramStep),
				sdf2.format(currentTimeInMillis),
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
	private int createProgressScreen(ThermalCalibrationProgramData _program){
		initializeResultsTable(_program);
		mainScreenFrame.getContentPane().setVisible(true);
		mainScreenFrame.getContentPane().repaint();
		mainScreenFrame.repaint();
		return 0;
	}
	private int initializeManagingTimeObjects(ThermalCalibrationProgramData _program){
		if (calendar == null) calendar = Calendar.getInstance();
		if (sdf2 == null) sdf2 = new SimpleDateFormat(TIME_FORMAT_NOW);
		sdf2.setTimeZone(TimeZone.getTimeZone(MADRID_TIME_ZONE));
		if (sdf == null) sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		startProgramTimeInMillis = System.currentTimeMillis();
		lastStepTimeInMillis = startProgramTimeInMillis;
		lastStepRealDurationTimeInMillis = FIRST_STEP_ESTIMATED_TIME_DURATION;
		nextStepEstimatedDurationTimeInMillis = lastStepRealDurationTimeInMillis;
		elapsedTimeInMillis = 0;
		remainingTimeInMillis = _program.getTemperatureProfileData().getTemperatures().length * nextStepEstimatedDurationTimeInMillis;

		programProgress = actualProgramStep/_program.getTemperatureProfileData().getTemperatures().length;
		return 0;
	}
	private int initializeResultsTable(ThermalCalibrationProgramData _program){
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.setRowCount(0);
		defaultTableModel.setColumnCount(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+5);
		mainScreenFrame.setDefaultTableModel(defaultTableModel);
		Object[] _data = new Object[_program.getMeasuresConfigurationData().getNDevicesToMeasure()+5];
		_data[0]="# step";
		_data[1]="time";
		_data[2]="Desired TºC";
		_data[3]="Real TºC (MChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")";
		_data[4]="Real T(Ohms) "+"(MChannel="+_program.getMeasuresConfigurationData().getTemperatureSensorData().getChannel()+")";
		 for (int i=5,j=0;i<(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+5);i++,j++){
			 _data[i] = _program.getMeasuresConfigurationData().getDevicesToMeasureData()[j].getDeviceReference()+"(MChannel="+_program.getMeasuresConfigurationData().getDevicesToMeasureData()[j].getDeviceChannel()+")";
		 }
		mainScreenFrame.insertRowAtResultsTable(_data);
		return 0;
	}
	private int initializeProgram()throws Exception{
		printActionMessage("Initializing Program");
		printActionMessage("Creando la instancia para el selector de ficheros");
		printActionMessage("Solicitamos los ficheros para ejecutar el programa y guardar los resultados.");
		JFileChooser fileChooser = new JFileChooser();
		JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ejecutar (enter to default.xml)");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		String programFilePath = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //if (programFilePath.equals("")) programFilePath = "default";
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
		printActionMessage("Initializing Timer");
		if (t1 == null) t1 = new Timer();
		t1.scheduleAtFixedRate(new TTask(), 0, PROGRESS_SCREEN_REFRESH_TIME_PERIOD);
		return 0;
	}
	private void recalculateEstimatedStepDurationTime(long _stepTimeInMillis){
		nextStepEstimatedDurationTimeInMillis = _stepTimeInMillis - lastStepTimeInMillis;
		remainingTimeInMillis = (program.getTemperatureProfileData().getTemperatures().length - actualProgramStep)* nextStepEstimatedDurationTimeInMillis;
		lastStepTimeInMillis = _stepTimeInMillis;
	}
	private int initializeResultsFile(ThermalCalibrationProgramData _program, File _resultsFile)throws Exception{
		String programResultsFilePath = "";
		JFileChooser fileChooser = new JFileChooser();
		JOptionPane.showMessageDialog(null,"Indique el fichero donde desea guardar los resultados.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			programResultsFilePath = fileChooser.getSelectedFile().getAbsolutePath();
			//if (programResultsFilePath.equals("")) programResultsFilePath = "default.txt";
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
	private void saveDataToFile(ThermalCalibrationProgramData _program,String _programStep, String _currentTime, String _desiredTemp, float _realTemp,float _realTempInOhms,float[] _measuredChannels){
		actualizeResultsTable(_program,_programStep,_currentTime,_desiredTemp,_realTemp,_realTempInOhms,_measuredChannels);
		printActionMessageAndProgressScreenMessage("Saving data to file.......\n");
		formatter.format("%1$-20s %2$-20s %3$-20s %4$-40s %5$-40s", _programStep, _currentTime, _desiredTemp, _realTemp,_realTempInOhms);
	    formatter.flush ();
		for (int i=0;i<_program.getMeasuresConfigurationData().getNDevicesToMeasure();i++){
	    	formatter.format("%1$-40s",Float.toString(_measuredChannels[i]));
		    formatter.flush ();
	    }
		formatter.format("%n");
	    formatter.flush ();
	}
	private void actualizeResultsTable(ThermalCalibrationProgramData _program,String _programStep, String _currentTimeInMillis, String _desiredTemp, float _realTemp,float _realTempInOhms,float[] _measuredChannels){
		printActionMessageAndProgressScreenMessage("Actualizing Results Table.......\n");
		Object[] _data = new Object[_program.getMeasuresConfigurationData().getNDevicesToMeasure()+5];
		BigDecimal bd;

		_data[0]=_programStep;
		_data[1]=_currentTimeInMillis;
		_data[2]=_desiredTemp;

		bd = new BigDecimal(Float.toString(_realTemp));
		bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
		_data[3]= bd.floatValue();

		bd = new BigDecimal(Float.toString(_realTempInOhms));
		bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
		_data[4]=bd.floatValue();

		for (int i=5,j=0;i<(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+5);i++,j++){
			 bd = new BigDecimal(Float.toString(_measuredChannels[j]));
			 bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
			 _data[i]=bd.floatValue();
		}
		mainScreenFrame.insertRowAtResultsTable(_data);
	}
	class TTask extends TimerTask {
		public void run() {
			elapsedTimeInMillis = System.currentTimeMillis() - startProgramTimeInMillis;
			programProgress = ((actualProgramStep*100)/program.getTemperatureProfileData().getTemperatures().length);
			remainingTimeInMillis = remainingTimeInMillis - PROGRESS_SCREEN_REFRESH_TIME_PERIOD;
			//System.out.println("program progress = "+programProgress);
			refreshProgressScreen();
		}
	};
	private void refreshProgressScreen(){
			mainScreenFrame.refreshProgressBarData(programProgress);
			mainScreenFrame.refreshElapsedTimeField(sdf2.format(elapsedTimeInMillis));
			mainScreenFrame.refreshRemainingTimeField(sdf2.format(remainingTimeInMillis));
			mainScreenFrame.getContentPane().repaint();
			mainScreenFrame.repaint();
			printActionMessage("Refreshing Progress Screen Data.");
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
		refreshProgressScreen();
	}
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
