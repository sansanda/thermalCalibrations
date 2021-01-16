package actions;

import instruments.InstrumentsData;
import keithley.Keithley2700;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort.BaudRate;




import calibrationSetUp.CalibrationSetUp;
import common.CommPort_I;
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
import Ovens.Eurotherm2404;
import rs232.JSSC_S_Port;
import fileUtilities.*;

public class RunCalibrationSetUp_Action2 implements Action{

	private static String 		DATE_FORMAT_NOW = "dd-MM-yyyy";
	private static String 		TIME_FORMAT_NOW = "HH:mm:ss";
	private static long 		PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS = 1000;
	private static String 		MADRID_TIME_ZONE = "GMT";
	private static long 		FIRST_STEP_ESTIMATED_TIME_DURATION = 900000;
	private static String 		INSTRUMENTS_DATA_FILE_PATH = "ConfigurationFiles\\instrumentsData.xml";
	private static String 		TEMPERATURE_SENSOR_DATA_FILE_PATH = "ConfigurationFiles\\temperatureSensorData.xml";
	protected static int 		ROOM_TEMPERATURE_STABILIZATION_TIME_IN_MINUTES = 10;


	private ActionResult 								result;
	private Calibration_MainController 					mainController;
	private JFrame 										frameWhoHasTheRequest;
	//IO to file
	private Formatter 									formatter = null;
	private File 										resultsFile = null;
    //Date
	private Calendar 									calendar = null;
	private SimpleDateFormat 							sdf = null;
	private SimpleDateFormat 							sdf2 = null;

	private CalibrationSetUp_MainScreen_JFrame 			mainScreenFrame;
	CalibrationSetUp_ProgressScreen_JFrame 				progressScreenFrame = null;
	private CalibrationSetUp 							calibrationSetUp = null;
	private InstrumentsData 							instrumentsData = null;
	private TemperatureSensor 							temperatureSensorData = null;

	private Keithley2700 		k2700;
	private Eurotherm2404		e2404;
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
	private double 				diodesCurrentInMilliAmps;

	public RunCalibrationSetUp_Action2()throws Exception{
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
	public boolean execute(ActionRequest _req) throws Exception
	{
		if (initializeCalibrationSetUp()==-1) return false;
		if (initializeManagingTimeObjects(calibrationSetUp)==-1) return false;
		if (createTemperatureSensor()==-1) return false;
		if (createResultsTableHeaderAndFormatIt(calibrationSetUp,temperatureSensorData)==-1) return false;
	    if (initializeResultsRow(resultsRow)==-1) return false;
		if (initializeResultsFile(resultsFile)==-1) return false;
		if (createInstruments()==-1) return false;
		if (initializeMultimeter(calibrationSetUp,temperatureSensorData)==-1) return false;
		if (initializeOven()==-1) return false;
		if (createCalibrationProgressScreen(calibrationSetUp,temperatureSensorData)==-1)return false;
		if (testCalibrationSetUp(calibrationSetUp,temperatureSensorData)==-1) return false;
		//Wait for room temperature stabilization by time
		roomTemperature = determineRoomTemperatureByTime(calibrationSetUp.getTemperatureStabilizationCriteria().getFirstTemperatureStepStabilitzationTimeInMinutes(), temperatureSensorData);
		//Measure TEMP and devices at room temperature and save data
		read2700ChannelsAndSaveDataToFile(calibrationSetUp,temperatureSensorData);
		//Determine what is the first temperature point above the room temperature.
		//If there is not any point then end Resistance calibration set up
		if (setTheActualCalibrationSetUpStepAboveTheMeasuredRoomTemperature(calibrationSetUp)==-1) return false;
		if (initializeTimer(progressScreenRefreshTimer,PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS, new ProgressScreenRefreshTask())==-1)return false;
		runCalibrationSetUp();
		return true;
	}

	/* Return the page name (and path) to display the view */
	public ActionResult getResult() {return result;}
	//Temperature management
	private double determineRoomTemperatureByTime(int _temperatureStabilizationTimeInMinutes, TemperatureSensor _temperatureSensorData) throws Exception
	{
		printActionMessageAndProgressScreenMessage("Serching for the room temperature after waiting "+ calibrationSetUp.getTemperatureStabilizationCriteria().getFirstTemperatureStepStabilitzationTimeInMinutes()+" minutes. ");
		//Wait for room temperature stabilization by time
		waitForTemperatureStabilizationByTime(_temperatureStabilizationTimeInMinutes, temperatureSensorData);
		//Neither range nor resolution needed when measuring temperature 
		int slotNumber = 1;
		return k2700.measure(
				Keithley2700.FUNCTION_TEMPERATURE, 
				-1f, 
				-1f, 
				slotNumber, 
				_temperatureSensorData.getChannel()
				)[0];
	}
	private void waitForTemperatureStabilizationByTime(int _temperatureStabilizationTime,TemperatureSensor _temperatureSensorData) throws Exception{
		printActionMessageAndProgressScreenMessage("Waiting... "+_temperatureStabilizationTime+" minutes for Temperature Stabilization ");
		long actualTime = System.currentTimeMillis();
		long futureTime = System.currentTimeMillis() + _temperatureStabilizationTime*60*1000;
		double 	pt100RealT = 0;
		int slotNumber = 1;
		
		while (actualTime<futureTime){
			actualTime = System.currentTimeMillis();
			if ((actualTime % PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS)==0){
				pt100RealT = k2700.measure(
						Keithley2700.FUNCTION_TEMPERATURE, 
						-1f, 
						-1f, 
						slotNumber, 
						_temperatureSensorData.getChannel()
						)[0];
				insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
			}
		}
	}
	private void waitForTemperatureStandardDeviation(double _desiredStDev, int _desiredNWindowsUnderStDev, CalibrationSetUp _ttcSetUpData,TemperatureSensor _temperatureSensorData) throws Exception{
		double 	measuredTemperature = 0;
		double 	desiredTemperature = 0;
		double 	actualAbsoluteTemperatureError = 0;
		double 	maximumAdmissibleTemperatureError = 0;
		float 	actualStepStandardDeviation = 0;
		int 	actualNWindowsUnderStDev = 0;
		int slotNumber = 1;
		
		printActionMessageAndProgressScreenMessage("Taking "+_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow()+" temperature measures in "+(_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode()*_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow())/1000+" seconds and calulating the Standard Deviation\n");
		
		actualStepStandardDeviation = k2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow(),_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode());
		measuredTemperature = k2700.measure(
				Keithley2700.FUNCTION_TEMPERATURE, 
				-1f, 
				-1f, 
				slotNumber, 
				_temperatureSensorData.getChannel()
				)[0];
		desiredTemperature = _ttcSetUpData.getTemperatureProfile().getTemperatures()[actualCalibrationStep];
		actualAbsoluteTemperatureError = Math.abs(measuredTemperature-desiredTemperature);
		maximumAdmissibleTemperatureError = _ttcSetUpData.getTemperatureStabilizationCriteria().getMaxAdminssibleTemperatureError();
		
		insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
		
		printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
		printActionMessageAndProgressScreenMessage("Measured Temperature = "+measuredTemperature);
		printActionMessageAndProgressScreenMessage("Desired Temperature = "+desiredTemperature);
		printActionMessageAndProgressScreenMessage("Absolute Temperature Error = "+ actualAbsoluteTemperatureError);
		printActionMessageAndProgressScreenMessage("Maximum Admissible Temperature Error = "+ maximumAdmissibleTemperatureError);	
		printActionMessageAndProgressScreenMessage("Number Of Windows Under Standard Deviation = "+ actualNWindowsUnderStDev);	
		printActionMessageAndProgressScreenMessage("Desired Number Of Windows Under Standard Deviation = "+ _desiredNWindowsUnderStDev);	
		printActionMessageAndProgressScreenMessage("|");
		printActionMessageAndProgressScreenMessage("|");

		if (actualStepStandardDeviation<=_desiredStDev & isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,desiredTemperature,maximumAdmissibleTemperatureError))
		{
			actualNWindowsUnderStDev++;
			printActionMessageAndProgressScreenMessage("Standard Deviation Below Desired.");
			printActionMessageAndProgressScreenMessage("Absolute Temperature Error Below Maximum Admissible Temperature Error .");
			printActionMessageAndProgressScreenMessage("Actualizing Passes Below StandardDeviation = "+actualNWindowsUnderStDev);
		}
		else {
			actualNWindowsUnderStDev = 0;
			if (actualStepStandardDeviation>_desiredStDev) printActionMessageAndProgressScreenMessage("Standard Deviation Above Desired.");
			if (isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,desiredTemperature,maximumAdmissibleTemperatureError)) printActionMessageAndProgressScreenMessage("Absolute Temperature Error Above Maximum Admissible Temperature Error .");
			printActionMessageAndProgressScreenMessage("Setting Passes Below StandardDeviation = 0");
		}
		while (actualNWindowsUnderStDev<_desiredNWindowsUnderStDev){
			
			printActionMessageAndProgressScreenMessage("Number Of Windows Under Standard Deviation = "+ actualNWindowsUnderStDev);	
			printActionMessageAndProgressScreenMessage("Desired Number Of Windows Under Standard Deviation = "+ _desiredNWindowsUnderStDev);	
			printActionMessageAndProgressScreenMessage("So.... Continue With Temperature Stabilization Process.");
			printActionMessageAndProgressScreenMessage("|");
			printActionMessageAndProgressScreenMessage("|");
			printActionMessageAndProgressScreenMessage("Taking "+_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow()+" temperature measures in "+(_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode()*_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow())/1000+" seconds and calulating the Standard Deviation\n");
			
			actualStepStandardDeviation = k2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_ttcSetUpData.getTemperatureStabilizationCriteria().getMeasurementWindow(),_ttcSetUpData.getTemperatureStabilizationCriteria().getSamplingPeriode());
			measuredTemperature = k2700.measure(
					Keithley2700.FUNCTION_TEMPERATURE, 
					-1f, 
					-1f, 
					slotNumber, 
					_temperatureSensorData.getChannel()
					)[0];
			desiredTemperature = _ttcSetUpData.getTemperatureProfile().getTemperatures()[actualCalibrationStep];
			actualAbsoluteTemperatureError = Math.abs(measuredTemperature-desiredTemperature);
			maximumAdmissibleTemperatureError = _ttcSetUpData.getTemperatureStabilizationCriteria().getMaxAdminssibleTemperatureError();
			insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);

			printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
			printActionMessageAndProgressScreenMessage("Measured Temperature = "+measuredTemperature);
			printActionMessageAndProgressScreenMessage("Desired Temperature = "+desiredTemperature);
			printActionMessageAndProgressScreenMessage("Absolute Temperature Error = "+ actualAbsoluteTemperatureError);
			printActionMessageAndProgressScreenMessage("Maximum Admissible Temperature Error = "+ maximumAdmissibleTemperatureError);	
			printActionMessageAndProgressScreenMessage("Number Of Windows Under Standard Deviation = "+ actualNWindowsUnderStDev);	
			printActionMessageAndProgressScreenMessage("Desired Number Of Windows Under Standard Deviation = "+ _desiredNWindowsUnderStDev);	
			printActionMessageAndProgressScreenMessage("|");
			printActionMessageAndProgressScreenMessage("|");
			if (actualStepStandardDeviation<=_desiredStDev & isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,desiredTemperature,maximumAdmissibleTemperatureError))
			{
				actualNWindowsUnderStDev++;
				printActionMessageAndProgressScreenMessage("Standard Deviation Below Desired.");
				printActionMessageAndProgressScreenMessage("Absolute Temperature Error Below Maximum Admissible Temperature Error .");
				printActionMessageAndProgressScreenMessage("Actualizing Passes Below StandardDeviation = "+actualNWindowsUnderStDev);
			}
			else {
				actualNWindowsUnderStDev = 0;
				if (actualStepStandardDeviation>_desiredStDev) printActionMessageAndProgressScreenMessage("Standard Deviation Above Desired.");
				if (isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,desiredTemperature,maximumAdmissibleTemperatureError)) printActionMessageAndProgressScreenMessage("Absolute Temperature Error Above Maximum Admissible Temperature Error .");
				printActionMessageAndProgressScreenMessage("Setting Passes Below StandardDeviation = 0");
			}
		}
		printActionMessageAndProgressScreenMessage("Standard Deviation has been less than "+_desiredStDev+" during "+_desiredNWindowsUnderStDev+" consecutive passes."+"\n");
		printActionMessageAndProgressScreenMessage("Temperature complains the stabilization criteria for this set up.");
	}
	private boolean isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(double _measuredTemperature, double _desiredTemperature, double _maxAdmissibleError) {
		double absoluteError = 0.0;
		absoluteError = Math.abs(_measuredTemperature-_desiredTemperature);
		if (absoluteError<_maxAdmissibleError) return true; else return false;
	}
	//CalibrationSetUp Management
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
		} else {
            //log.append("Open command cancelled by user." + newline);
			return -1;
        }
		//Ask for the diodes current in case of Diodes Calibration
		if (calibrationSetUp.getType()==calibrationSetUp.TEMPERATURE_VS_VOLTAGE_TYPE) diodesCurrentInMilliAmps = getDiodesCurrentInMilliAmps();
		return 0;
	}
	private int testCalibrationSetUp(CalibrationSetUp _calibrationSetUp,TemperatureSensor _temperatureSensorData) throws Exception{

		printActionMessageAndProgressScreenMessage("CALIBRATION SET UP....");
		printActionMessageAndProgressScreenMessage(calibrationSetUp.toString());

		double pt100RealT = 0;
		double pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		double ovenDisplayTemp = 0;
		int avg = 2;
		Enumeration devicesEnumeration = _calibrationSetUp.getDevicesToCalibrate().getEnumeration();
		int nDevicesToCalibrate = _calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate();
		int i=0;
		Diode d = null;
		Resistance r = null;

		Object[] resultRow = new Object[resultsTableHeader.length];

		printActionMessageAndProgressScreenMessage("Testing the devices to measure.");

		printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
		ovenDisplayTemp = e2404.readTemperature().getValue();
		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		pt100RealT = k2700.measureAveragePT100Temperature(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = k2700.measureAverage4WireResistance(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

		currentTimeInMillis = System.currentTimeMillis();
		recalculateEstimatedStepDurationTime(currentTimeInMillis);

		resultRow[0] = pt100RealT;

		switch (_calibrationSetUp.getType()){
		case Device.RESISTANCE:
			while (devicesEnumeration.hasMoreElements()){
	            r = (Resistance)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Double.toString(k2700.measureAverage4WireResistance(r.getConnectedToMultimeterChannelNumber(),avg));
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
	            resultRow[i+1] = Double.toString(k2700.configureAsDCVoltageAverageMeasure(d.getConnectedToMultimeterChannelNumber(),avg));
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
					fiTempProfile = actualCalibrationStep>=calibrationSetUp.getTemperatureProfile().getTemperatures().length;
					//What is the temperature stabilitzation method? By Time or by Standard Deviation?
					switch (calibrationSetUp.getTemperatureStabilizationCriteria().getTemperatureStabilitzationMethod()){
					case 0: //Temperature Stabilitzation by St Dev case
						while (!fiTempProfile)
						{
							//Set the desired oven temperature
							printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]+" ºC \n");
							e2404.setTemperatureSetpoint1(calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
							//wait for stable oven temperature reading the standard deviation
							waitForTemperatureStandardDeviation(
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
							e2404.setTemperatureSetpoint1(calibrationSetUp.getTemperatureProfile().getTemperatures()[actualCalibrationStep]);
							//wait for stable oven temperature by time
							waitForTemperatureStabilizationByTime(calibrationSetUp.getTemperatureStabilizationCriteria().getTemperatureStabilitzationTime(),temperatureSensorData);
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
					printExceptionMessage(e);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					printExceptionMessage((Exception) e);
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		runProgramSwingWorker.start();
	}
	private int setTheActualCalibrationSetUpStepAboveTheMeasuredRoomTemperature(CalibrationSetUp _program)throws Exception
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
	//Devices measurement management
	private double read2700ChannelsAndSaveDataToFile(CalibrationSetUp _calibrationSetUp,TemperatureSensor _temperatureSensorData) throws Exception{
		double pt100RealT = 0;
		double pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		double ovenDisplayTemp = 0;
		int avg = 3;

		Enumeration devicesEnumeration = _calibrationSetUp.getDevicesToCalibrate().getEnumeration();
		int nDevicesToCalibrate = _calibrationSetUp.getDevicesToCalibrate().getNDevicesToCalibrate();
		int i=0;
		Diode d = null;
		Resistance r = null;

		Object[] resultRow = new Object[resultsTableHeader.length];

		printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
		ovenDisplayTemp = e2404.readTemperature().getValue();
		printActionMessageAndProgressScreenMessage("Reading the temperature at the PT100 \n");
		
		pt100RealT = k2700.measureAveragePT100Temperature(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the 4-Wire Resistance at the PT100 \n");
		pt1004WResistance = k2700.measureAverage4WireResistance(_temperatureSensorData.getChannel(),avg);
		printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

		currentTimeInMillis = System.currentTimeMillis();
		recalculateEstimatedStepDurationTime(currentTimeInMillis);

		resultRow[0] = pt100RealT;

        switch (_calibrationSetUp.getType()){
		case Device.RESISTANCE:
			while (devicesEnumeration.hasMoreElements()){
	            r = (Resistance)devicesEnumeration.nextElement();
				printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
	            resultRow[i+1] = Double.toString(k2700.measureAverage4WireResistance(r.getConnectedToMultimeterChannelNumber(),avg));
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
	            resultRow[i+1] = Double.toString(k2700.configureAsDCVoltageAverageMeasure(d.getConnectedToMultimeterChannelNumber(),avg));
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
	private double getDiodesCurrentInMilliAmps(){
		boolean enteredValueIsDouble = false;
		double _diodesCurrentInMilliAmps = 0.0;
		while (!enteredValueIsDouble){
			String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Indicate the current for calibrating the diodes (mAmps)\n",
                    "Current Diodes",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "10.0");
			try {
				enteredValueIsDouble = true;
				_diodesCurrentInMilliAmps = Double.parseDouble(s);
			} catch (NumberFormatException nfe) {
				enteredValueIsDouble = false;
			}
		}
		return _diodesCurrentInMilliAmps;
	}
	//Instruments and sensor management
	private int createInstruments()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de Instrumentos.");
		instrumentsData = new InstrumentsData(INSTRUMENTS_DATA_FILE_PATH);
		
		
		printActionMessage("Creando la instancia de Keithley2700.");
		
		int baudRate = 19200;
		int numberOfDataBits = 8;
		int numberOfStopBits = 1;
		int parityType = 0;
		String terminator = "\n";
		
		int writePort_waitTime = 400; //in milliseconds
		int readPort_waitTime = 0; //in milliseconds
		boolean check_errors = false;
		int debug_level = 4;
		
		CommPort_I rs232_commPort = new JSSC_S_Port(instrumentsData.getMultimeterData().getComPort(), 19200, numberOfDataBits, numberOfStopBits, parityType, terminator, writePort_waitTime, readPort_waitTime, debug_level);

		
		
		k2700 = new Keithley2700(rs232_commPort, check_errors, debug_level);
		
		printActionMessage("Creando la instancia de Eurotherm2404.");
		
		SerialParameters sp = Eurotherm2404.createSerialConnection(instrumentsData.getOvenData().getComPort(), 
																		BaudRate.BAUD_RATE_9600, 8, 
																		SerialPort.Parity.NONE, 1);
		ModbusMaster m = Eurotherm2404.createModBusMaster(sp, Modbus.LogLevel.LEVEL_WARNINGS);	
		e2404 = new Eurotherm2404(sp,m, instrumentsData.getOvenData().getControllerID());
		
		return 0;
	}
	
	private int initializeMultimeter(CalibrationSetUp calibrationSetUp, TemperatureSensor temperatureSensorData) throws Exception{
		
		
		printActionMessageAndProgressScreenMessage("INITIALIZING THE MULTIMETER.... \n");
		startProgramTimeInMillis = System.currentTimeMillis();
		
		String[] ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING,
				Keithley2700.FORMAT_ELEMENT_UNITS
				};
		
		String[] DEFAULT_ELEMENTS = {
				Keithley2700.FORMAT_ELEMENT_READING
				};
		
		k2700.resetInstrument();
		k2700.resetRegisters();
		k2700.clearErrorQueue();
		k2700.openAllChannels(100);
		
		k2700.formatElements(ELEMENTS, DEFAULT_ELEMENTS);
		k2700.formatData(Keithley2700.FORMAT_TYPE_ASCII,1);
		
		k2700.enableBeeper(false);
		
		return 0;
	}
	
	private int initializeOven() throws Exception{
		printActionMessageAndProgressScreenMessage("INITIALIZING THE OVEN.... \n");
		startProgramTimeInMillis = System.currentTimeMillis();
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		e2404.setTemperatureSetpoint1(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		e2404.setInAutoMode();
		//TODO Implementar la llamada a la calibracion de usuario del 2404 mediante dos puntos guardados en un archivo 2404TwoPointsUserCalibration.XML

		return 0;
	}
	
	private int createTemperatureSensor()throws Exception{
		printActionMessage("Leyendo el fichero de configuracion de sensor de temperatura.");
		temperatureSensorData = new TemperatureSensor(TEMPERATURE_SENSOR_DATA_FILE_PATH);
		return 0;
	}
	//Time Management
	private int initializeTimer(Timer _t, long _timePeriod, TimerTask _task){
		printActionMessage("Initializing Timer for Refreshing Progress Screen every " + _timePeriod/1000 + " seconds");
		if (_t == null) _t = new Timer();
		_t.scheduleAtFixedRate(_task, 0, _timePeriod);
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
	private void recalculateEstimatedStepDurationTime(long _stepTimeInMillis){
		nextStepEstimatedDurationTimeInMillis = _stepTimeInMillis - lastStepTimeInMillis;
		remainingTimeInMillis = (calibrationSetUp.getTemperatureProfile().getTemperatures().length - actualCalibrationStep)* nextStepEstimatedDurationTimeInMillis;
		lastStepTimeInMillis = _stepTimeInMillis;
	}
	//Progress screen management
	public int createCalibrationProgressScreen(CalibrationSetUp _program,TemperatureSensor _temperatureSensorData){
		progressScreenFrame = new CalibrationSetUp_ProgressScreen_JFrame(mainController);
		progressScreenFrame.initializeResultsTable(resultsTableHeader);
		return 0;
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
	//Results Table Management
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
	            resultsTableHeader[i+1] = "VF_"+d.getDeviceReference()+"_Channel="+d.getConnectedToMultimeterChannelNumber()+"_"+diodesCurrentInMilliAmps+"mAmps";
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
	//Results File Management
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
	//Extensions File Management
	public void configureFileChooserForXML(JFileChooser _fileChooser){
		_fileChooser.setFileHidingEnabled(false);
		_fileChooser.setFileFilter(new XMLFilter());
	}
	public void configureFileChooserForTXT(JFileChooser _fileChooser){
		_fileChooser.setFileHidingEnabled(false);
		_fileChooser.setFileFilter(new TXTFilter());
	}
	//Messages Management
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
	//End management
	private void finishProgram() throws Exception{
		for (int i=0;i<10;i++){
			printActionMessageAndProgressScreenMessage("FINISHING THE PROGRAM \n");
		}
		printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 ºC \n");
		e2404.setTemperatureSetpoint1(0);
		printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
		e2404.setInAutoMode();
		mainController.doAction(new ActionRequest("StopCalibrationProgramAction",frameWhoHasTheRequest));
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
