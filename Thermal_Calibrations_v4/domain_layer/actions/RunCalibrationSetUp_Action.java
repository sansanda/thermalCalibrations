package actions;

import instruments.InstrumentsData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import calibrationSetUp.CalibrationSetUp;
import controller.*;
import devices.Device;
import devices.Diode;
import devices.Resistance;
import sensors.TemperatureSensor;
import threadManagement.*;
import views.CalibrationSetUp_MainScreen_JFrame;
import views.CalibrationSetUp_ProgressScreen_JFrame;
import Main.Calibration_MainController;
import Main.MainController;
import Ovens.Eurotherm2404_v2;
import Ovens.Eurotherm2404_v3;
import multimeters.Keithley2700;
import fileUtilities.*;


public class RunCalibrationSetUp_Action implements Action{

	private static String 		DATE_FORMAT_NOW = "dd-MM-yyyy";
	private static String 		TIME_FORMAT_NOW = "HH:mm:ss";
	private static long 		PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS = 1000;
	private static String 		MADRID_TIME_ZONE = "GMT";
	private static long 		FIRST_STEP_ESTIMATED_TIME_DURATION = 900000;
	private static String 		INSTRUMENTS_DATA_FILE_PATH = "ConfigurationFiles\\instrumentsData.xml";
	private static String 		TEMPERATURE_SENSOR_DATA_FILE_PATH = "ConfigurationFiles\\temperatureSensorData.xml";
	private static Double 		DESIRED_STDEV_AT_ROOM_TEMPERATURE = 0.03;
	private static int 			MAX_PASSES_FOR_DETERMINING_ROOM_TEMPERATURE = 3;
	private static int 			N_SAMPLES_FOR_DETERMINING_ROOM_TEMPERATURE = 600;
	private static long			SAMPLING_PERIODE_IN_MILLISECONDS_FOR_DETERMINING_ROOM_TEMPERATURE = 1000;
	protected static int 		ROOM_TEMPERATURE_STABILIZATION_TIME_IN_MINUTES = 10;


	private ActionResult 					result;
	private Calibration_MainController 		mainController;
	private JFrame 							frameWhoHasTheRequest;
	//IO to file
	private Formatter 			formatter = null;
	private File 				resultsFile = null;
    //Date
	private Calendar 			calendar = null;
	private SimpleDateFormat 	sdf = null;
	private SimpleDateFormat 	sdf2 = null;

	private CalibrationSetUp_MainScreen_JFrame 			mainScreenFrame;
	CalibrationSetUp_ProgressScreen_JFrame 				progressScreenFrame = null;
	private CalibrationSetUp 							calibrationSetUp = null;
	private InstrumentsData 							instrumentsData = null;
	private TemperatureSensor 							temperatureSensorData = null;

	private Keithley2700 		K2700;
	private Eurotherm2404_v3	E2404;
	private int 				actualCalibrationStep;
	private Timer 				progressScreenRefreshTimer;
	private SwingWorker 		runProgramSwingWorker;
	private double 				roomTemperature = 0.0;
	private long 				remainingTimeInMillis = 0;
	private long 				startProgramTimeInMillis = 0;
	private long 				elapsedTimeInMillis = 0;
	private long 				lastStepRealDurationTimeInMillis = 0;
	private long 				lastStepTimeInMillis = 0;
	private long 				nextStepEstimatedDurationTimeInMillis = 0;
	private int 				programProgress = 0;

    private String				resultsTableHeaderFormat = "";
    private Object[]			resultsTableHeader = null;
    private Object[]			resultsRow;

	public RunCalibrationSetUp_Action()throws Exception{
		printActionMessage("Setting the Security Manager to null");
		System.setSecurityManager(null);
		printActionMessage("Creando instancias para manejar fechas y horas");
		result=null;
		mainController=null;
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
		mainScreenFrame = (CalibrationSetUp_MainScreen_JFrame)frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = (Calibration_MainController)_main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		if (initializeCalibrationSetUp()==-1) return false;
		if (initializeManagingTimeObjects(calibrationSetUp)==-1) return false;
		if (createTemperatureSensor()==-1) return false;
		if (createResultsTableHeaderAndFormatIt(calibrationSetUp,temperatureSensorData)==-1) return false;
	    if (initializeResultsRow(resultsRow)==-1) return false;
		if (initializeResultsFile(resultsFile)==-1) return false;
		if (createInstruments()==-1) return false;
		if (createCalibrationProgressScreen(calibrationSetUp,temperatureSensorData)==-1)return false;
		if (initializeTimer(progressScreenRefreshTimer,PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS, new ProgressScreenRefreshTask())==-1)return false;
		if (initializeOven()==-1) return false;
		if (testCalibrationSetUp(calibrationSetUp,temperatureSensorData)==-1) return false;
		runCalibrationSetUp();
		return true;
	}

	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	public int createCalibrationProgressScreen(CalibrationSetUp _program,TemperatureSensor _temperatureSensorData){
		progressScreenFrame = new CalibrationSetUp_ProgressScreen_JFrame(mainController);
		progressScreenFrame.initializeResultsTable(resultsTableHeader);
		return 0;
	}
	private int testCalibrationSetUp(CalibrationSetUp _calibrationSetUp,TemperatureSensor _temperatureSensorData) throws Exception{

		float pt100RealT = 0;
		float pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		int ovenDisplayTemp = 0;
		int avg = 10;

		Enumeration devicesEnumeration = _calibrationSetUp.getDevicesToCalibrate().getEnumeration();
		int nDevicesToCalibrate = _calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate();
		int i=0;
		Diode d = null;
		Resistance r = null;

		Object[] resultRow = new Object[resultsTableHeader.length];

		printActionMessageAndProgressScreenMessage("Testing the devices to measure.");

		printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
		ovenDisplayTemp = E2404.readTemperature();
		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		pt100RealT = K2700.measureAveragePT100Temperature(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = K2700.measureAverage4WireResistance(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

		currentTimeInMillis = System.currentTimeMillis();
		recalculateEstimatedStepDurationTime(currentTimeInMillis);

		resultRow[0] = pt100RealT;

		switch (_calibrationSetUp.getType()){
		case Device.RESISTANCE:
			while (devicesEnumeration.hasMoreElements()){
	            r = (Resistance)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Float.toString(K2700.measureAverage4WireResistance(r.getConnectedToMultimeterChannelNumber(),avg));
	            i++;
			}
	        resultRow[nDevicesToCalibrate+1] = pt1004WResistance;
	        resultRow[nDevicesToCalibrate+2] = ovenDisplayTemp;
	        resultRow[nDevicesToCalibrate+3] = Integer.toString(actualCalibrationStep);
	        resultRow[nDevicesToCalibrate+4] = sdf2.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+5] = sdf.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+6] = Integer.toString(_calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
	        printActionMessageAndProgressScreenMessage("Test Result.");
	        for (int j=0;j<resultRow.length;j++){
	        	printActionMessageAndProgressScreenMessage(resultRow[j].toString());
	        }
	        break;
		case Device.DIODE:
			while (devicesEnumeration.hasMoreElements()){
	            d = (Diode)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Float.toString(K2700.measureAverageVoltage(d.getConnectedToMultimeterChannelNumber(),avg));
	            i++;
			}
	        resultRow[nDevicesToCalibrate+1] = pt1004WResistance;
	        resultRow[nDevicesToCalibrate+2] = ovenDisplayTemp;
	        resultRow[nDevicesToCalibrate+3] = Integer.toString(actualCalibrationStep);
	        resultRow[nDevicesToCalibrate+4] = sdf2.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+5] = sdf.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+6] = Integer.toString(_calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
	        printActionMessageAndProgressScreenMessage("Test Result.");
	        for (int j=0;j<resultRow.length;j++){
	        	printActionMessageAndProgressScreenMessage(resultRow[j].toString());
	        }
	        break;
			default:
		}
		return 0;
	}
	private void runCalibrationSetUp(){
		runProgramSwingWorker = new SwingWorker() {
			public Object construct() {
				try {
					boolean fiTempProfile = false;
					printActionMessageAndProgressScreenMessage("CALIBRATION SET UP....");
		    		printActionMessageAndProgressScreenMessage(calibrationSetUp.toString());
					//Wait for room temperature stabilization by time
					determineRoomTemperatureByTime(calibrationSetUp.getTemperatureStabilizationCriteria().getFirstTemperatureStepStabilitzationTimeInMinutes(), temperatureSensorData);
					//Measure TEMP and devices at room temperature and save data
					read2700ChannelsAndSaveDataToFile(calibrationSetUp,temperatureSensorData);
					//Determine what is the first temperature point above the room temperature.
					//If there is not any point then end Resistance calibration set up
					if (setTheActualProgramStepAboveTheRoomTemperature(calibrationSetUp)==-1) return null;
					fiTempProfile = actualCalibrationStep>=calibrationSetUp.getTemperatureProfile().getTemperatures().length;
					//What is the temperature stabilitzation method? By Time or by Standard Deviation?
					switch (calibrationSetUp.getTemperatureStabilizationCriteria().getTemperatureStabilitzationMethod()){
					case 0: //Temperature Stabilitzation by St Dev case
						while (!fiTempProfile)
						{
							//Set the desired oven temperature
							printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]+" ºC \n");
							E2404.setTemperature(calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
							//wait for stable oven temperature reading the standard deviation
							waitForTemperatureStandardDeviationMatch(
									calibrationSetUp.getTemperatureStabilizationCriteria().getStDev(),
									calibrationSetUp.getTemperatureStabilizationCriteria().getNumberOfWindowsUnderStDev(),
									calibrationSetUp,
									temperatureSensorData);
							//Measure devices at stable temperature point and save data
							read2700ChannelsAndSaveDataToFile(calibrationSetUp,temperatureSensorData);
							//actualitze calibration step
							actualCalibrationStep++;
							fiTempProfile = actualCalibrationStep>=calibrationSetUp.getTemperatureProfile().getTemperatures().length;
						}
						break;
					case 1:		//Temperature Stabilitzation by Time
						while (!fiTempProfile)
						{
							//Set the desired oven temperature
							printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]+" ºC \n");
							E2404.setTemperature(calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
							//wait for stable oven temperature by time
							waitForTemperatureStabilizationTime(calibrationSetUp.getTemperatureStabilizationCriteria().getTemperatureStabilitzationTime(),temperatureSensorData);
							//Measure devices at stable temperature point and save data
							read2700ChannelsAndSaveDataToFile(calibrationSetUp,temperatureSensorData);
							//actualitze calibration step
							actualCalibrationStep++;
							fiTempProfile = actualCalibrationStep>=calibrationSetUp.getTemperatureProfile().getTemperatures().length;
						}
						break;
					default:
						break;
					}
					finishProgram();
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
		runProgramSwingWorker.start();
	}
	private void determineRoomTemperatureByTime(int _temperatureStabilizationTimeInMinutes, TemperatureSensor _temperatureSensorData) throws Exception
	{
		printActionMessageAndProgressScreenMessage("Serching for the room temperature after waiting "+ calibrationSetUp.getTemperatureStabilizationCriteria().getFirstTemperatureStepStabilitzationTimeInMinutes()+" minutes. ");
		//Wait for room temperature stabilization by time
		waitForTemperatureStabilizationTime(_temperatureStabilizationTimeInMinutes, temperatureSensorData);
		roomTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
	}
	private int setTheActualProgramStepAboveTheRoomTemperature(CalibrationSetUp _program)throws Throwable
	{
		boolean trobat = roomTemperature<_program.getTemperatureProfile().getTemperatures()[actualCalibrationStep];
		boolean fiTempProfile = actualCalibrationStep>=_program.getTemperatureProfile().getTemperatures().length;
		printActionMessageAndProgressScreenMessage("Going to the first temperature set point above the actual oven temperature. \n");
		while(!trobat & !fiTempProfile){
			actualCalibrationStep++;
			fiTempProfile = actualCalibrationStep>=_program.getTemperatureProfile().getTemperatures().length;
			if (!fiTempProfile) trobat = roomTemperature<_program.getTemperatureProfile().getTemperatures()[actualCalibrationStep];
		}
		if (fiTempProfile)
		{
			printActionMessageAndProgressScreenMessage("There is not temperatures in the temperature profile above the actual oven temperature");
			finishProgram();
			return -1;
		}
		printActionMessageAndProgressScreenMessage("The first temperature set point above the actual oven temperature is "+_program.getTemperatureProfile().getTemperatures()[actualCalibrationStep]+" \n");
		return 0;
	}
	private void waitForTemperatureStabilizationTime(int _temperatureStabilizationTime,TemperatureSensor _temperatureSensorData) throws Exception{
		printActionMessageAndProgressScreenMessage("Waiting... "+_temperatureStabilizationTime+" minutes for Temperature Stabilization ");
		long actualTime = System.currentTimeMillis();
		long futureTime = System.currentTimeMillis() + _temperatureStabilizationTime*60*1000;
		long actualTimeInSeconds = (actualTime/1000);
		float 	pt100RealT = 0;
		while (actualTime<futureTime){
			actualTime = System.currentTimeMillis();
			actualTimeInSeconds = (actualTime/1000);
			if ((actualTimeInSeconds % 10)==0){
				pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
				insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
			}
		}
	}
	private void waitForTemperatureStandardDeviationMatch(double _desiredStDev, int _desiredNWindowsUnderStDev, CalibrationSetUp _ttcSetUpData,TemperatureSensor _temperatureSensorData) throws Exception{
		float 	measuredTemperature = 0;
		float 	actualStepStandardDeviation = 0;
		int 	actualNWindowsUnderStDev = 0;

		printActionMessageAndProgressScreenMessage("Taking "+_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow()+" temperature measures in "+(_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode()*_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow())/1000+" seconds and calulating the Standard Deviation\n");
		actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow(),_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode());
		printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
		measuredTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
		insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);

		if (	actualStepStandardDeviation<=_desiredStDev &
				isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,_ttcSetUpData.getTemperatureProfile().getTemperatures()[actualCalibrationStep],_ttcSetUpData.getTemperatureStabilizationCriteria().getMaxAdminssibleTemperatureError()))
		{
			actualNWindowsUnderStDev++;
			printActionMessageAndProgressScreenMessage("Standard Deviation Below Desired.");
			printActionMessageAndProgressScreenMessage("Actualizing Passes Below StandardDeviation = "+actualNWindowsUnderStDev);
		}
		else {
			actualNWindowsUnderStDev = 0;
			printActionMessageAndProgressScreenMessage("Standard Deviation Above Desired.");
			printActionMessageAndProgressScreenMessage("Setting Passes Below StandardDeviation = 0");
		}
		while (actualNWindowsUnderStDev<_desiredNWindowsUnderStDev){
			printActionMessageAndProgressScreenMessage("Standard Deviation is over than "+_desiredStDev+". Continue reading temperatures.\n");
			actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow(),_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode());
			printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
			if (	actualStepStandardDeviation<=_desiredStDev &
					isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,_ttcSetUpData.getTemperatureProfile().getTemperatures()[actualCalibrationStep],_ttcSetUpData.getTemperatureStabilizationCriteria().getMaxAdminssibleTemperatureError()))
			{
				actualNWindowsUnderStDev++;
				printActionMessageAndProgressScreenMessage("Standard Deviation Below Desired.");
				printActionMessageAndProgressScreenMessage("Actualizing Passes Below StandardDeviation = "+actualNWindowsUnderStDev);
			}
			else {
				actualNWindowsUnderStDev = 0;
				printActionMessageAndProgressScreenMessage("Standard Deviation Above Desired.");
				printActionMessageAndProgressScreenMessage("Setting Passes Below StandardDeviation = 0");
			}
			measuredTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
			insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
		}
		printActionMessageAndProgressScreenMessage("Standard Deviation has been less than "+_desiredStDev+" during "+_desiredNWindowsUnderStDev+" consecutive passes."+"\n");
	}
	private boolean isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(double _measuredTemperature, double _desiredTemperature, double _maxAdmissibleError) {
		double absoluteError = 0.0;
		absoluteError = Math.abs(_measuredTemperature-_desiredTemperature);
		if (absoluteError<_maxAdmissibleError) return true; else return false;
	}
	private int initializeOven() throws Exception{
		printActionMessageAndProgressScreenMessage("INITIALIZING THE OVEN.... \n");
		startProgramTimeInMillis = System.currentTimeMillis();
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		E2404.setTemperature(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		E2404.setInAutoMode();
		//TODO Implementar la llamada a la calibracion de usuario del 2404 mediante dos puntos guardados en un archivo 2404TwoPointsUserCalibration.XML

		return 0;
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
	private float read2700ChannelsAndSaveDataToFile(CalibrationSetUp _calibrationSetUp,TemperatureSensor _temperatureSensorData) throws Exception{
		float pt100RealT = 0;
		float pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		int ovenDisplayTemp = 0;
		int avg = 3;

		Enumeration devicesEnumeration = _calibrationSetUp.getDevicesToCalibrate().getEnumeration();
		int nDevicesToCalibrate = _calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate();
		int i=0;
		Diode d = null;
		Resistance r = null;

		Object[] resultRow = new Object[resultsTableHeader.length];

		printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
		ovenDisplayTemp = E2404.readTemperature();
		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		pt100RealT = K2700.measureAveragePT100Temperature(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = K2700.measureAverage4WireResistance(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

		currentTimeInMillis = System.currentTimeMillis();
		recalculateEstimatedStepDurationTime(currentTimeInMillis);

		resultRow[0] = pt100RealT;

        switch (_calibrationSetUp.getType()){
		case Device.RESISTANCE:
			while (devicesEnumeration.hasMoreElements()){
	            r = (Resistance)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Float.toString(K2700.measureAverage4WireResistance(r.getConnectedToMultimeterChannelNumber(),avg));
	            i++;
			}
	        resultRow[nDevicesToCalibrate+1] = pt1004WResistance;
	        resultRow[nDevicesToCalibrate+2] = ovenDisplayTemp;
	        resultRow[nDevicesToCalibrate+3] = Integer.toString(actualCalibrationStep);
	        resultRow[nDevicesToCalibrate+4] = sdf2.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+5] = sdf.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+6] = Integer.toString(_calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
	        break;
		case Device.DIODE:
			while (devicesEnumeration.hasMoreElements()){
	            d = (Diode)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Float.toString(K2700.measureAverageVoltage(d.getConnectedToMultimeterChannelNumber(),avg));
	            i++;
			}
	        resultRow[nDevicesToCalibrate+1] = pt1004WResistance;
	        resultRow[nDevicesToCalibrate+2] = ovenDisplayTemp;
	        resultRow[nDevicesToCalibrate+3] = Integer.toString(actualCalibrationStep);
	        resultRow[nDevicesToCalibrate+4] = sdf2.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+5] = sdf.format(currentTimeInMillis);
	        resultRow[nDevicesToCalibrate+6] = Integer.toString(_calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
	        break;
			default:
		}

        saveDataToFile(resultRow);
		return pt100RealT;
	}
	private int createResultsTableHeaderAndFormatIt(CalibrationSetUp _calibrationSetUp,TemperatureSensor _temperatureSensorData){

		Enumeration devicesEnumeration = _calibrationSetUp.getDevicesToCalibrate().getEnumeration();
		int nDevicesToCalibrate = _calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate();
		int i=0;
		Diode d = null;
		Resistance r = null;

        resultsTableHeader = new Object[nDevicesToCalibrate+7];
    	for (int j=1;j<=(nDevicesToCalibrate+7);j++){
            resultsTableHeaderFormat = resultsTableHeaderFormat + ("%"+Integer.toString((j))+"$-30s");
        }
        resultsTableHeader[0] = "PT100_Temp(ºC)_Channel="+_temperatureSensorData.getChannel();
        switch (_calibrationSetUp.getType()){
		case Device.RESISTANCE:
			while (devicesEnumeration.hasMoreElements()){
	            r = (Resistance)devicesEnumeration.nextElement();
	            resultsTableHeader[i+1] = "Res_"+r.getDeviceReference()+"_Channel="+r.getConnectedToMultimeterChannelNumber();
	            i++;
			}
			break;
		case Device.DIODE:
			while (devicesEnumeration.hasMoreElements()){
	            d = (Diode)devicesEnumeration.nextElement();
	            resultsTableHeader[i+1] = "VF_"+d.getDeviceReference()+"_Channel="+d.getConnectedToMultimeterChannelNumber();
	            i++;
			}
			break;
			default:
        }
        resultsTableHeader[nDevicesToCalibrate+1] = "R_PT100(Ohms)_Channel="+_temperatureSensorData.getChannel();
        resultsTableHeader[nDevicesToCalibrate+2] = "Oven_Temp(ºC)";
        resultsTableHeader[nDevicesToCalibrate+3] = "#_step";
        resultsTableHeader[nDevicesToCalibrate+4] = "Hour";
        resultsTableHeader[nDevicesToCalibrate+5] = "Date";
        resultsTableHeader[nDevicesToCalibrate+6] = "SP_Temp(ºC)";
        return 0;
    }
	private int initializeResultsRow(Object[] _resultsRow){
        resultsRow = new Object[resultsTableHeader.length];
        return 0;
    }

	private int createInstruments()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de Instrumentos.");
		instrumentsData = new InstrumentsData(INSTRUMENTS_DATA_FILE_PATH);
		printActionMessage("Creando la instancia de Keithley2700.");
		K2700 = new Keithley2700(instrumentsData.getMultimeterData().getComPort());
		printActionMessage("Creando la instancia de Eurotherm2404.");
		E2404 = new Eurotherm2404_v3(instrumentsData.getOvenData().getComPort(),instrumentsData.getOvenData().getControllerID());
		return 0;
	}
	private int createTemperatureSensor()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de sensor de temperatura.");
		temperatureSensorData = new TemperatureSensor(TEMPERATURE_SENSOR_DATA_FILE_PATH);
		return 0;
	}
	private int initializeManagingTimeObjects(CalibrationSetUp _calibrationSetUp){
		if (calendar == null) calendar = Calendar.getInstance();
		if (sdf2 == null) sdf2 = new SimpleDateFormat(TIME_FORMAT_NOW);
		sdf2.setTimeZone(TimeZone.getTimeZone(MADRID_TIME_ZONE));
		if (sdf == null) sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		startProgramTimeInMillis = System.currentTimeMillis();
		lastStepTimeInMillis = startProgramTimeInMillis;
		lastStepRealDurationTimeInMillis = FIRST_STEP_ESTIMATED_TIME_DURATION;
		nextStepEstimatedDurationTimeInMillis = lastStepRealDurationTimeInMillis;
		elapsedTimeInMillis = 0;
		remainingTimeInMillis = _calibrationSetUp.getTemperatureProfile().getTemperatures().length * nextStepEstimatedDurationTimeInMillis;

		programProgress = actualCalibrationStep/_calibrationSetUp.getTemperatureProfile().getTemperatures().length;
		return 0;
	}
	private int initializeCalibrationSetUp()throws Exception{
		printActionMessage("Initializing Program");
		printActionMessage("Creando la instancia para el selector de ficheros");
		printActionMessage("Solicitamos los ficheros para ejecutar el programa y guardar los resultados.");
		JFileChooser fileChooser = new JFileChooser();
		configureFileChooserForXML(fileChooser);
		JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ejecutar (enter to default.xml)");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		String programFilePath = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //if (programFilePath.equals("")) programFilePath = "default";
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            printActionMessage("Creando la instancia para el programa.");
    		calibrationSetUp = new CalibrationSetUp(programFilePath);
    		if (calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate()==0){
    			JOptionPane.showMessageDialog(null,"En el programa que desea ejecutar no se ha determinado ningún dispositivo a medir.");
    			return -1;
    		}
    		System.out.println(calibrationSetUp.toString());
    		actualCalibrationStep = 0;
    		return 0;
		} else {
            //log.append("Open command cancelled by user." + newline);
			return -1;
        }
	}
	private int initializeTimer(Timer _t, long _timePeriod, TimerTask _task){
		printActionMessage("Initializing Timer for Refreshing Progress Screen every " + _timePeriod/1000 + " seconds");
		if (_t == null) _t = new Timer();
		_t.scheduleAtFixedRate(_task, 0, _timePeriod);
		return 0;
	}
	private void recalculateEstimatedStepDurationTime(long _stepTimeInMillis){
		nextStepEstimatedDurationTimeInMillis = _stepTimeInMillis - lastStepTimeInMillis;
		remainingTimeInMillis = (calibrationSetUp.getTemperatureProfile().getTemperatures().length - actualCalibrationStep)* nextStepEstimatedDurationTimeInMillis;
		lastStepTimeInMillis = _stepTimeInMillis;
	}
	private int initializeResultsFile(File _resultsFile)throws Exception{
		String programResultsFilePath = "";
		JFileChooser fileChooser = new JFileChooser();
		configureFileChooserForTXT(fileChooser);
		JOptionPane.showMessageDialog(null,"Indique el fichero donde desea guardar los resultados.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			programResultsFilePath = fileChooser.getSelectedFile().getAbsolutePath();
			//if (programResultsFilePath.equals("")) programResultsFilePath = "default.txt";
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
			printActionMessage("Inicializando el fichero de salida de resultados....");
			_resultsFile = new File(programResultsFilePath+".txt");
			formatter = new Formatter (_resultsFile);
			formatter.format(resultsTableHeaderFormat,resultsTableHeader);
            formatter.flush ();
            formatter.format("%n");
            formatter.flush ();
            return 0;
		} else {
            //log.append("Open command cancelled by user." + newline);
			return -1;
        }
	}
	private void saveDataToFile(Object[] _resultRow){
		actualizeResultsTable(_resultRow);
        printActionMessageAndProgressScreenMessage("Saving data to file.......\n");
        formatter.format(resultsTableHeaderFormat, _resultRow);
        formatter.flush ();
        formatter.format("%n");
        formatter.flush ();
	}
	private void actualizeResultsTable(Object[] _newRow){
		printActionMessageAndProgressScreenMessage("Actualizing Results Table.......\n");
        progressScreenFrame.insertRowAtResultsTable(_newRow);
	}
	private void insertTempPointsInGraph(long _SystemTimeInMilliseconds,double _realOvenTemp,double _desiredOvenTemp){
		progressScreenFrame.insertTempPointAtRealOvenTempGraphSerie(_SystemTimeInMilliseconds, _realOvenTemp);
		progressScreenFrame.insertTempPointAtDesiredOvenTempGraphSerie(_SystemTimeInMilliseconds, _desiredOvenTemp);
	}
	class ProgressScreenRefreshTask extends TimerTask {
		public void run() {
			elapsedTimeInMillis = System.currentTimeMillis() - startProgramTimeInMillis;
			programProgress = ((actualCalibrationStep*100)/calibrationSetUp.getTemperatureProfile().getTemperatures().length);
			remainingTimeInMillis = remainingTimeInMillis - PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS;
			//System.out.println("program progress = "+programProgress);
			//insertTempPointsInGraph(System.currentTimeMillis(),remainingTimeInMillis,actualProgramStep);
			refreshProgressScreen();
		}
	};
	private void refreshProgressScreen(){
			progressScreenFrame.refreshProgressBarData(programProgress);
			progressScreenFrame.refreshElapsedTimeField(sdf2.format(elapsedTimeInMillis));
			progressScreenFrame.refreshRemainingTimeField(sdf2.format(remainingTimeInMillis));
			progressScreenFrame.repaint();
			//printActionMessage("Refreshing Progress Screen Data.");
	}
	public void configureFileChooserForXML(JFileChooser _fileChooser){
		_fileChooser.setFileHidingEnabled(false);
		_fileChooser.setFileFilter(new XMLFilter());
	}
	public void configureFileChooserForTXT(JFileChooser _fileChooser){
		_fileChooser.setFileHidingEnabled(false);
		_fileChooser.setFileFilter(new TXTFilter());
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
