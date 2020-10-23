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

import Data.InstrumentsData;
import Data.TemperatureSensorData;
import Data.ThermalCalibrationProgramData;
import Main.MainController;
import Ovens.Eurotherm2404;
import multimeters.Keithley2700;
import frontController.*;
import gui.MainScreenFrame;
import gui.ProgramProgressScreenFrame;

import ThreadManagement.*;

public class StartCalibrationProgramAction implements Action{

	private static String 	DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static String 	TIME_FORMAT_NOW = "HH:mm:ss";
	private static long 	PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS = 1000;
	private static String 	MADRID_TIME_ZONE = "GMT+1";
	private static long 	FIRST_STEP_ESTIMATED_TIME_DURATION = 900000;
	private static String 	INSTRUMENTS_DATA_FILE_PATH = "ConfigurationFiles\\instrumentsData.xml";
	private static String 	TEMPERATURE_SENSOR_DATA_FILE_PATH = "ConfigurationFiles\\temperatureSensorData.xml";

	private ActionResult 	result;
	private MainController 	mainController;
	private JFrame 			frameWhoHasTheRequest;
	//IO to file
	private Formatter 		formatter = null;
	private File 			resultsFile = null;
    //Date
	private Calendar 		calendar = null;
	private SimpleDateFormat sdf = null;
	private SimpleDateFormat sdf2 = null;

	private MainScreenFrame mainScreenFrame;
	ProgramProgressScreenFrame progressScreenFrame = null;
	private ThermalCalibrationProgramData program = null;
	private InstrumentsData instrumentsData = null;
	private TemperatureSensorData temperatureSensorData = null;

	private Keithley2700 	K2700;
	private Eurotherm2404 	E2404;
	private int 			actualProgramStep;
	private Timer 			progressScreenRefreshTimer, temperatureStabilitzationTimer;
	private SwingWorker 	worker;

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
		mainController=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (MainScreenFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (initializeProgram()==-1) return false;
		if (initializeManagingTimeObjects(program)==-1) return false;
		if (createTemperatureSensor()==-1) return false;
		if (initializeResultsFile(program,temperatureSensorData,resultsFile)==-1) return false;
		if (createInstruments()==-1) return false;
		if (createProgressScreen(program,temperatureSensorData)==-1)return false;
		printActionMessage("Initializing Timer for Refreshing Progress Screen every " + PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS/1000 + " seconds");
		if (initializeTimer(progressScreenRefreshTimer,PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS, new ProgressScreenRefreshTask())==-1)return false;
		runProgram();
		return true;
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	public int createProgressScreen(ThermalCalibrationProgramData _program,TemperatureSensorData _temperatureSensorData){
		progressScreenFrame = new ProgramProgressScreenFrame(mainController);
		progressScreenFrame.initializeResultsTable(_program,_temperatureSensorData);
		return 0;
	}
	private void runProgram(){
		worker = new SwingWorker() {
			public Object construct() {
				try {

					float pt100RealT = 0;
					boolean fiTempProfile = false;

					switch (program.getAdvanceProgramData().getTemperatureStabilitzationMethod()){
					case 0: //Temperature Stabilitzation by St Dev

						pt100RealT = startProgram(temperatureSensorData);
						fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;

						if (isActualOvenTempBelowActualProgramStep(pt100RealT, program, actualProgramStep)){

						}
						else
						{
							waitForTemperatureStandardDeviationMatch(program,temperatureSensorData);
							//En este momento la temperatura es estable, guardamos los datos
							//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
							pt100RealT = read2700ChannelsAndSaveDataToFile(program,temperatureSensorData);
						}
						if (setTheActualProgramStepAboveTheRealTemp(pt100RealT,program)==-1) return null;
						fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
						while (!fiTempProfile)
						{
							printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" ºC \n");
							E2404.setTemperature(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
							waitForTemperatureStandardDeviationMatch(program,temperatureSensorData);
							//En este momento la temperatura es estable, guardamos los datos
							//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
							read2700ChannelsAndSaveDataToFile(program,temperatureSensorData);
							actualProgramStep++;
							fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
						}
						finishProgram();
						break;
					case 1:		//Temperature Stabilitzation by Time

						pt100RealT = startProgram(temperatureSensorData);
						fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;

						if (isActualOvenTempBelowActualProgramStep(pt100RealT, program, actualProgramStep)){

						}
						else
						{
							waitForTemperatureStabilizationTime(program);
							//En este momento la temperatura es estable, guardamos los datos
							//de consigna, Tª en Pt100, Resistencia en Pt100 en un fichero txt
							pt100RealT = read2700ChannelsAndSaveDataToFile(program,temperatureSensorData);
						}

						if (setTheActualProgramStepAboveTheRealTemp(pt100RealT,program)==-1) return null;

						while (!fiTempProfile)
						{
							printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+program.getTemperatureProfileData().getTemperatures()[actualProgramStep]+" ºC \n");
							E2404.setTemperature(program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
							printActionMessage("Initializing Timer for Taking temperature every " + program.getAdvanceProgramData().getTemperatureStabilitzationTime() + " minutes....");
							if (initializeTimer(temperatureStabilitzationTimer,program.getAdvanceProgramData().getTemperatureStabilitzationTime()*60*1000, new TemperatureStabilitzationTimeTask())==-1) return false;
							fiTempProfile = actualProgramStep>=program.getTemperatureProfileData().getTemperatures().length;
						}
						finishProgram();
						break;
					default:
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		worker.start();
	}
	private boolean isActualOvenTempBelowActualProgramStep(float _pt100RealT,ThermalCalibrationProgramData _program,int _actualProgramStep){
		boolean trobat = _pt100RealT<_program.getTemperatureProfileData().getTemperatures()[_actualProgramStep];
		if (!trobat){
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es superior o igual al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a tomar la temperatura actual estable.");
			printActionMessageAndProgressScreenMessage("Posteriormente se determinará cual es step de la rampa por el cual iniciar el programa.");

		}else
		{
			printActionMessageAndProgressScreenMessage("La temperatura actual del horno es inferior al primer valor de la rampa indicada en el programa.");
			printActionMessageAndProgressScreenMessage("Se va a proceder a iniciar la rampa de temperaturas indicada por el programa.");
		}
		return trobat;
	}
	private int setTheActualProgramStepAboveTheRealTemp(float _pt100RealT,ThermalCalibrationProgramData _program)throws Throwable{
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
	private void waitForTemperatureStabilizationTime(ThermalCalibrationProgramData _program) throws Exception{
		printActionMessageAndProgressScreenMessage("Waiting... "+_program.getAdvanceProgramData().getTemperatureStabilitzationTime()+" minutes for Temperature Stabilization ");
		long actualTime = System.currentTimeMillis();
		long futureTime = System.currentTimeMillis() + _program.getAdvanceProgramData().getTemperatureStabilitzationTime()*60*1000;
		while (actualTime<futureTime){
			actualTime = System.currentTimeMillis();
		}
	}
	private void waitForTemperatureStandardDeviationMatch(ThermalCalibrationProgramData _program,TemperatureSensorData _temperatureSensorData) throws Exception{
		float 	pt100RealT = 0;
		float 	actualStepStandardDeviation = 0;
		int 	actualNPassesBelowStandardDeviation = 0;
		printActionMessageAndProgressScreenMessage("Taking "+_program.getAdvanceProgramData().getMeasuresPerCicle()+" temperature measures in "+(_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures()*_program.getAdvanceProgramData().getMeasuresPerCicle())/1000+" seconds and calulating the Standard Deviation\n");
		actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_program.getAdvanceProgramData().getMeasuresPerCicle(),_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures());
		if (actualStepStandardDeviation<=_program.getAdvanceProgramData().getStandardDeviation()) actualNPassesBelowStandardDeviation++;
		else actualNPassesBelowStandardDeviation = 0;
		printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
		printActionMessageAndProgressScreenMessage("Passes Below StandardDeviation = "+actualNPassesBelowStandardDeviation);
		pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
		insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
		while (actualNPassesBelowStandardDeviation<_program.getAdvanceProgramData().getNPassesBelowStandardDeviation()){
			printActionMessageAndProgressScreenMessage("Standard Deviation is over than "+_program.getAdvanceProgramData().getStandardDeviation()+". Continue reading temperatures.\n");
			actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_program.getAdvanceProgramData().getMeasuresPerCicle(),_program.getAdvanceProgramData().getTimeBetweenSuccesivesMeasures());
			if (actualStepStandardDeviation<=_program.getAdvanceProgramData().getStandardDeviation()) actualNPassesBelowStandardDeviation++;
			else actualNPassesBelowStandardDeviation = 0;
			printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
			printActionMessageAndProgressScreenMessage("Passes Below StandardDeviation = "+actualNPassesBelowStandardDeviation);
			pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
			insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
		}
		printActionMessageAndProgressScreenMessage("Standard Deviation is less than "+_program.getAdvanceProgramData().getStandardDeviation()+" during "+_program.getAdvanceProgramData().getNPassesBelowStandardDeviation()+" consecutive passes."+"\n");
	}
	private float startProgram(TemperatureSensorData _temperatureSensorData) throws Exception{
		float pt100RealT = 0;
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("STARTING THE PROGRAM.... \n");
		}
		startProgramTimeInMillis = System.currentTimeMillis();
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
		insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,program.getTemperatureProfileData().getTemperatures()[actualProgramStep]);
		printActionMessageAndProgressScreenMessage("actualTemperature = " + pt100RealT);
		return pt100RealT;
	}
	private void finishProgram() throws Throwable{
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("FINISHING THE PROGRAM \n");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		mainController.doAction(new ActionRequest("StopCalibrationProgramAction",frameWhoHasTheRequest));
		this.finalize();
	}
	private float read2700ChannelsAndSaveDataToFile(ThermalCalibrationProgramData _program,TemperatureSensorData _temperatureSensorData) throws Exception{
		float pt100RealT = 0;
		float pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		int ovenDisplayTemp = 0;
		float[] channelsToMeasure= new float[_program.getMeasuresConfigurationData().getNDevicesToMeasure()];
		printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
		ovenDisplayTemp = E2404.readTemperature();
		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = K2700.measure4WireResistance(_temperatureSensorData.getChannel());
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
				ovenDisplayTemp,
				pt100RealT,
				pt1004WResistance,
				channelsToMeasure
				);
		return pt100RealT;
	}
	private int createInstruments()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de Instrumentos.");
		instrumentsData = new InstrumentsData(INSTRUMENTS_DATA_FILE_PATH);
		printActionMessage("Creando la instancia de Keithley2700.");
		K2700 = new Keithley2700(instrumentsData.getMultimeterData().getComPort());
		printActionMessage("Creando la instancia de Eurotherm2404.");
		E2404 = new Eurotherm2404(instrumentsData.getOvenData().getComPort(),instrumentsData.getOvenData().getControllerID());
		return 0;
	}
	private int createTemperatureSensor()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de sensor de temperatura.");
		temperatureSensorData = new TemperatureSensorData(TEMPERATURE_SENSOR_DATA_FILE_PATH);
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
	private int initializeTimer(Timer _t, long _timePeriod, TimerTask _task){
		if (_t == null) _t = new Timer();
		_t.scheduleAtFixedRate(_task, 0, _timePeriod);
		return 0;
	}
	private void recalculateEstimatedStepDurationTime(long _stepTimeInMillis){
		nextStepEstimatedDurationTimeInMillis = _stepTimeInMillis - lastStepTimeInMillis;
		remainingTimeInMillis = (program.getTemperatureProfileData().getTemperatures().length - actualProgramStep)* nextStepEstimatedDurationTimeInMillis;
		lastStepTimeInMillis = _stepTimeInMillis;
	}
	private int initializeResultsFile(ThermalCalibrationProgramData _program,TemperatureSensorData _temperatureSensorData, File _resultsFile)throws Exception{
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
			formatter.format("%1$-20s %2$-20s %3$-20s %4$-40s %5$-40s %6$-40s", "# step", "time", "Desired Temp TºC", "Oven Temp ºC", "Real Temp ºC (MultimeterChannel="+_temperatureSensorData.getChannel()+")","Real Temp(Ohms) "+"(MultimeterChannel="+_temperatureSensorData.getChannel()+")");
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
	private void saveDataToFile(ThermalCalibrationProgramData _program,String _programStep, String _currentTime, String _desiredTemp,int _ovenDisplayTemp, float _realTemp,float _realTempInOhms,float[] _measuredChannels){
		actualizeResultsTable(_program,_programStep,_currentTime,_desiredTemp,_ovenDisplayTemp,_realTemp,_realTempInOhms,_measuredChannels);
		printActionMessageAndProgressScreenMessage("Saving data to file.......\n");
		formatter.format("%1$-20s %2$-20s %3$-20s %4$-40s %5$-40s %6$-40s", _programStep, _currentTime, _desiredTemp, _ovenDisplayTemp, _realTemp,_realTempInOhms);
	    formatter.flush ();
		for (int i=0;i<_program.getMeasuresConfigurationData().getNDevicesToMeasure();i++){
	    	formatter.format("%1$-40s",Float.toString(_measuredChannels[i]));
		    formatter.flush ();
	    }
		formatter.format("%n");
	    formatter.flush ();
	}
	private void actualizeResultsTable(ThermalCalibrationProgramData _program,String _programStep, String _currentTimeInMillis, String _desiredTemp,int _ovenDisplayTemp, float _realTemp,float _realTempInOhms,float[] _measuredChannels){
		printActionMessageAndProgressScreenMessage("Actualizing Results Table.......\n");
		Object[] _data = new Object[_program.getMeasuresConfigurationData().getNDevicesToMeasure()+6];
		BigDecimal bd;

		_data[0]=_programStep;
		_data[1]=_currentTimeInMillis;
		_data[2]=_desiredTemp;
		_data[3]=_ovenDisplayTemp;

		bd = new BigDecimal(Float.toString(_realTemp));
		bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
		_data[4]= bd.floatValue();

		bd = new BigDecimal(Float.toString(_realTempInOhms));
		bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
		_data[5]=bd.floatValue();

		for (int i=6,j=0;i<(_program.getMeasuresConfigurationData().getNDevicesToMeasure()+6);i++,j++){
			 bd = new BigDecimal(Float.toString(_measuredChannels[j]));
			 bd = bd.setScale( 4, BigDecimal.ROUND_HALF_UP );
			 _data[i]=bd.floatValue();
		}
		progressScreenFrame.insertRowAtResultsTable(_data);
	}
	private void insertTempPointsInGraph(long _SystemTimeInMilliseconds,double _realOvenTemp,double _desiredOvenTemp){
		progressScreenFrame.insertTempPointAtRealOvenTempGraphSerie(_SystemTimeInMilliseconds, _realOvenTemp);
		progressScreenFrame.insertTempPointAtDesiredOvenTempGraphSerie(_SystemTimeInMilliseconds, _desiredOvenTemp);
	}
	class ProgressScreenRefreshTask extends TimerTask {
		public void run() {
			elapsedTimeInMillis = System.currentTimeMillis() - startProgramTimeInMillis;
			programProgress = ((actualProgramStep*100)/program.getTemperatureProfileData().getTemperatures().length);
			remainingTimeInMillis = remainingTimeInMillis - PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS;
			//System.out.println("program progress = "+programProgress);
			//insertTempPointsInGraph(System.currentTimeMillis(),remainingTimeInMillis,actualProgramStep);
			refreshProgressScreen();
		}
	};
	class TemperatureStabilitzationTimeTask extends TimerTask {
		public void run() {
			try {
				read2700ChannelsAndSaveDataToFile(program,temperatureSensorData);
				actualProgramStep++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	private void refreshProgressScreen(){
			progressScreenFrame.refreshProgressBarData(programProgress);
			progressScreenFrame.refreshElapsedTimeField(sdf2.format(elapsedTimeInMillis));
			progressScreenFrame.refreshRemainingTimeField(sdf2.format(remainingTimeInMillis));
			progressScreenFrame.repaint();
			//printActionMessage("Refreshing Progress Screen Data.");
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
		progressScreenFrame.appendTextToLogTextArea(_msg);
		refreshProgressScreen();
	}
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
