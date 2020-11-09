package actionsForDiodeCalibration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import profiles.*;
import multimeters.Keithley2700;
import Main.Diodes_Calibration_MainController;
import Main.MainController;
import Ovens.Eurotherm2404_v2;
import Ovens.Eurotherm2404_v3;
import fileUtilities.TXTFilter;
import fileUtilities.XMLFilter;
import controller.*;
import threadManagement.*;
import view_ForDiodesCalibration.DiodesCalibrationViewerFrame;
import view_ForDiodesCalibration.DiodesCalibration_MainScreenFrame;
import view_CommonParts.OvenTemperatureWarningFrame;
import view_CommonParts.ProgressScreenFrame;



public class RunADiodesalibrationSetUp_Action implements Action{

    private static String 		DATE_FORMAT_NOW = "dd-MM-yyyy";
    private static String 		TIME_FORMAT_NOW = "HH:mm:ss";
    private static long 		PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS = 1000;
    private static String 		MADRID_TIME_ZONE = "GMT";
    private static long 		FIRST_STEP_ESTIMATED_TIME_DURATION = 900000;
    private static String 		INSTRUMENTS_DATA_FILE_PATH = "ConfigurationFiles\\instrumentsData.xml";
    private static String 		TEMPERATURE_SENSOR_DATA_FILE_PATH = "ConfigurationFiles\\temperatureSensorData.xml";
    private static Double 		DESIRED_STDEV_AT_ROOM_TEMPERATURE = 0.03;

    private ActionResult 							result;
    private Diodes_Calibration_MainController 		mainController;
    private JFrame 									frameWhoHasTheRequest;
    //IO to file
    private Formatter 								formatter = null;
    private File 									resultsFile = null;
    //Date
    private Calendar 								calendar = null;
    private SimpleDateFormat 						sdf = null;
    private SimpleDateFormat 						sdf2 = null;

    private DiodesCalibration_MainScreenFrame 		mainScreenFrame;
    private ProgressScreenFrame 					progressScreenFrame = null;
    private DiodesCalibrationSetUpData 				diodesCalibrationSetUp = null;
    private InstrumentsData 						instrumentsData = null;
    private TemperatureSensorData 					temperatureSensorData = null;

    private Keithley2700 		K2700;
    private Eurotherm2404_v3 	E2404;
    private int 				actualCalibrationStep;
    private Timer 				progressScreenRefreshTimer;
    private SwingWorker 		runProgramSwingWorker;
    private double 				roomTemperature = 0.0;

    private long 				calibrationProgressRemainingTimeInMillis = 0;
    private long 				calibrationProgressStartTimeInMillis = 0;
    private long 				calibrationProgressElapsedTimeInMillis = 0;
    private long 				calibrationProgressLastStepRealDurationTimeInMillis = 0;
    private long 				calibrationProgressLastStepTimeInMillis = 0;
    private long 				calibrationProgressNextStepEstimatedDurationTimeInMillis = 0;
    private int 				calibrationProgress = 0;

    private String				resultsTableHeaderFormat = "";
    private Object[]			resultsTableHeader = null;
    private Object[]			resultsRow;

    public RunADiodesalibrationSetUp_Action()throws Exception{
        printActionMessage("Setting the Security Manager to null");
        System.setSecurityManager(null);
        printActionMessage("Creando instancias para manejar fechas y horas");
        result=null;
        mainController=null;
    }
    /* Pass the frame who has made the request*/
    public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
        frameWhoHasTheRequest = _frameWhoHasTheRequest;
        mainScreenFrame = (DiodesCalibration_MainScreenFrame)frameWhoHasTheRequest;
    }
    public void setMain(MainController _main){
        mainController = (Diodes_Calibration_MainController)_main;
    }
    /* Execute business logic */
    public boolean execute(ActionRequest _req) throws IOException, Exception
    {
        if (initializeDiodesCalibrationSetUp()==-1) return false;
        if (initializeManagingTimeObjects(diodesCalibrationSetUp)==-1) return false;
        if (createTemperatureSensor()==-1) return false;
        if (createResultsTableHeaderAndFormatIt(diodesCalibrationSetUp,temperatureSensorData)==-1) return false;
        if (initializeResultsRow(resultsRow)==-1) return false;
        if (initializeResultsFile(resultsFile)==-1) return false;
        if (createInstruments()==-1) return false;
        if (createCalibrationProgressScreen(diodesCalibrationSetUp,temperatureSensorData)==-1)return false;
        if (initializeTimer(progressScreenRefreshTimer,PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS, new ProgressScreenRefreshTask())==-1)return false;
        if (initializeOven()==-1) return false;
		if (testVoltagesAndTemperatureSensor(diodesCalibrationSetUp,temperatureSensorData)==-1) return false;
        runVoltageCalibrationSetUp();
        return true;
    }
    /* Return the page name (and path) to display the view */
    public ActionResult getResult()
    {
        return result;
    }
    public int createCalibrationProgressScreen(DiodesCalibrationSetUpData _diodesCalibrationSetUp,TemperatureSensorData _temperatureSensorData){
        progressScreenFrame = new ProgressScreenFrame(mainController);
        progressScreenFrame.initializeResultsTable(resultsTableHeader);
        return 0;
    }
    private int testVoltagesAndTemperatureSensor(DiodesCalibrationSetUpData diodesCalibrationSetUp,TemperatureSensorData _temperatureSensorData) throws Exception{

		float pt100RealT = 0;
		float pt1004WResistance = 0;
		long currentTimeInMillis = 0;
		int ovenDisplayTemp = 0;
		int avg = 10;
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
        for (int i=0;i<diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure();i++){
            printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
            resultRow[i+1] = Float.toString(K2700.measureAverageVoltage(diodesCalibrationSetUp.getDiodesToCalibrateData().getDiodesToMeasureData()[i].getDeviceChannel(),avg));
        }
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+1] = pt1004WResistance;
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+2] = ovenDisplayTemp;
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+3] = Integer.toString(actualCalibrationStep);
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+4] = sdf2.format(currentTimeInMillis);
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+5] = sdf.format(currentTimeInMillis);
        resultRow[diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()+6] = Integer.toString(diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);

        printActionMessageAndProgressScreenMessage("Test Result.");
        for (int i=0;i<resultRow.length;i++){
        	printActionMessageAndProgressScreenMessage(resultRow[i].toString());
        }
		return 0;
	}
    private void runVoltageCalibrationSetUp(){
        runProgramSwingWorker = new SwingWorker() {
            public Object construct() {
                try {
                	boolean fiTempProfile = false;
					printActionMessageAndProgressScreenMessage("CALIBRATION SET UP....");
		    		printActionMessageAndProgressScreenMessage(diodesCalibrationSetUp.toString());
                    //Wait for room temperature stabilization by time
                    determineRoomTemperatureByTime(diodesCalibrationSetUp, temperatureSensorData);
                    //Measure TEMP and devices at room temperature and save data
                    read2700ChannelsAndSaveDataToFile(diodesCalibrationSetUp,temperatureSensorData);
                    //Determine what is the first temperature point above the room temperature.
                    //If there is not any point then end Resistance calibration set up
                    if (setTheActualProgramStepAboveTheRoomTemperature(diodesCalibrationSetUp)==-1) return null;
                    fiTempProfile = actualCalibrationStep>=diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures().length;
                    //What is the temperature stabilitzation method? By Time or by Standard Deviation?
                    switch (diodesCalibrationSetUp.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationMethod()){
                    case 0: //Temperature Stabilitzation by St Dev case
                        while (!fiTempProfile)
                        {
                            //Set the desired oven temperature
                            printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]+" �C \n");
                            E2404.setTemperature(diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);
                            //wait for stable oven temperature reading the standard deviation
                            waitForTemperatureStandardDeviationMatch(diodesCalibrationSetUp,temperatureSensorData);
                            //Measure devices at stable temperature point and save data
                            read2700ChannelsAndSaveDataToFile(diodesCalibrationSetUp,temperatureSensorData);
                            //actualitze calibration step
                            actualCalibrationStep++;
                            fiTempProfile = actualCalibrationStep>=diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures().length;
                        }
                        finishProgram();
                        break;
                    case 1:		//Temperature Stabilitzation by Time
                        while (!fiTempProfile)
                        {
                            //Set the desired oven temperature
                            printActionMessageAndProgressScreenMessage("Setting the TSP1 to "+diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]+" �C \n");
                            E2404.setTemperature(diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);
                            //wait for stable oven temperature by time
                            waitForTemperatureStabilizationTime(diodesCalibrationSetUp,temperatureSensorData);
                            //Measure devices at stable temperature point and save data
                            read2700ChannelsAndSaveDataToFile(diodesCalibrationSetUp,temperatureSensorData);
                            //actualitze calibration step
                            actualCalibrationStep++;
                            fiTempProfile = actualCalibrationStep>=diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures().length;
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
    private void determineRoomTemperatureByTime(DiodesCalibrationSetUpData _program,TemperatureSensorData _temperatureSensorData) throws Exception
    {
        printActionMessageAndProgressScreenMessage("Serching for the room temperature after waiting 10 minutes. ");
        //Wait for room temperature stabilization by time
        waitForTemperatureStabilizationTime(_program,temperatureSensorData);
        roomTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
    }
    private void determineRoomTemperatureByStDev(final int _desiredNWindowsUnderStDev,final double _desiredStDev,final int _nSamples, final long _delayBetweenSamplesInMilliseconds, final DiodesCalibrationSetUpData _diodeCalibrationSetUpData,final TemperatureSensorData _temperatureSensorData) throws Exception
    {
        printActionMessageAndProgressScreenMessage("Serching for the stable room temperature at ( "+DESIRED_STDEV_AT_ROOM_TEMPERATURE+" stDev ). ");
        //Wait for room temperature stabilization by StDev
        waitForRoomTemperatureStabilization(_desiredStDev, _desiredNWindowsUnderStDev, _nSamples, _delayBetweenSamplesInMilliseconds,_diodeCalibrationSetUpData, _temperatureSensorData);
        roomTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
    }
    private int setTheActualProgramStepAboveTheRoomTemperature(DiodesCalibrationSetUpData _program)throws Throwable
    {
        boolean trobat = roomTemperature<_program.getTemperatureProfileData().getTemperatures()[actualCalibrationStep];
        boolean fiTempProfile = actualCalibrationStep>=_program.getTemperatureProfileData().getTemperatures().length;
        printActionMessageAndProgressScreenMessage("Going to the first temperature set point above the actual oven temperature. \n");
        while(!trobat & !fiTempProfile){
            actualCalibrationStep++;
            fiTempProfile = actualCalibrationStep>=_program.getTemperatureProfileData().getTemperatures().length;
            if (!fiTempProfile) trobat = roomTemperature<_program.getTemperatureProfileData().getTemperatures()[actualCalibrationStep];
        }
        if (fiTempProfile)
        {
            printActionMessageAndProgressScreenMessage("There is not temperatures in the temperature profile above the actual oven temperature");
            finishProgram();
            return -1;
        }
        printActionMessageAndProgressScreenMessage("The first temperature set point above the actual oven temperature is "+_program.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]+" \n");
        return 0;
    }
    private void waitForRoomTemperatureStabilization(double _desiredStDev, int _desiredNWindowsUnderStDev,int _measurementWindow, long _samplingPeriodeInMilliseconds, DiodesCalibrationSetUpData _diodeCalibrationSetUpData,TemperatureSensorData _temperatureSensorData) throws Exception{
        float 	pt100RealT = 0;
        float 	actualStepStandardDeviation = 0;
        int 	actualNWindowsUnderStDev = 0;

        printActionMessageAndProgressScreenMessage("Taking "+_measurementWindow+" temperature measures in "+(_samplingPeriodeInMilliseconds*_measurementWindow)/1000+" seconds and calulating the Standard Deviation\n");
        actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_measurementWindow,_samplingPeriodeInMilliseconds);
        printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
        pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
        insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);

        if (actualStepStandardDeviation<=_desiredStDev) {
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
            actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_measurementWindow,_samplingPeriodeInMilliseconds);
            printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
            if (actualStepStandardDeviation<=_desiredStDev) {
                actualNWindowsUnderStDev++;
                printActionMessageAndProgressScreenMessage("Standard Deviation Below Desired.");
                printActionMessageAndProgressScreenMessage("Actualizing Passes Below StandardDeviation = "+actualNWindowsUnderStDev);
            }
            else {
                actualNWindowsUnderStDev = 0;
                printActionMessageAndProgressScreenMessage("Standard Deviation Above Desired.");
                printActionMessageAndProgressScreenMessage("Setting Passes Below StandardDeviation = 0");
            }
            pt100RealT = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
            insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);
        }
        printActionMessageAndProgressScreenMessage("Standard Deviation has been less than "+_desiredStDev+" during "+_desiredNWindowsUnderStDev+" consecutive passes."+"\n");
    }
    private void waitForTemperatureStabilizationTime(DiodesCalibrationSetUpData _calibrationSetUp,TemperatureSensorData _temperatureSensorData) throws Exception{
        int _temperatureStabilizationTime = _calibrationSetUp.getTemperatureStabilizationCriteriumData().getTemperatureStabilitzationTime();
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
                insertTempPointsInGraph(System.currentTimeMillis(), pt100RealT,_calibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);
            }
        }
    }
    private void waitForTemperatureStandardDeviationMatch(DiodesCalibrationSetUpData _calibrationSetUp,TemperatureSensorData _temperatureSensorData) throws Exception{
        float 	measuredTemperature = 0;
        float 	actualStepStandardDeviation = 0;
        int 	actualNWindowsUnderStDev = 0;

    	double _desiredStDev = _calibrationSetUp.getTemperatureStabilizationCriteriumData().getStDev();
		double _desiredNWindowsUnderStDev = _calibrationSetUp.getTemperatureStabilizationCriteriumData().getNumberOfWindowsUnderStDev();

        printActionMessageAndProgressScreenMessage("Taking "+_calibrationSetUp.getTemperatureStabilizationCriteriumData().getMeasurementWindow()+" temperature measures in "+(_calibrationSetUp.getTemperatureStabilizationCriteriumData().getSamplingPeriode()*_calibrationSetUp.getTemperatureStabilizationCriteriumData().getMeasurementWindow())/1000+" seconds and calulating the Standard Deviation\n");
        actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_calibrationSetUp.getTemperatureStabilizationCriteriumData().getMeasurementWindow(),_calibrationSetUp.getTemperatureStabilizationCriteriumData().getSamplingPeriode());
        printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
        measuredTemperature = K2700.measurePT100Temperature(_temperatureSensorData.getChannel());
        insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,_calibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);

        if (actualStepStandardDeviation<=_desiredStDev &
        		isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(measuredTemperature,diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep],diodesCalibrationSetUp.getTemperatureStabilizationCriteriumData().getMaxAdminssibleTemperatureError())
        )
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
            actualStepStandardDeviation = K2700.takeNTemperatureMeasuresWithDelayAndReturnStDev(_temperatureSensorData.getChannel(),_calibrationSetUp.getTemperatureStabilizationCriteriumData().getMeasurementWindow(),_calibrationSetUp.getTemperatureStabilizationCriteriumData().getSamplingPeriode());
            printActionMessageAndProgressScreenMessage("Standard Deviation = "+actualStepStandardDeviation);
            if (actualStepStandardDeviation<=_desiredStDev)
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
            insertTempPointsInGraph(System.currentTimeMillis(), measuredTemperature,_calibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);
        }

        printActionMessageAndProgressScreenMessage("Standard Deviation has been less than "+_desiredStDev+" during "+_desiredNWindowsUnderStDev+" consecutive passes."+"\n");
    }
    private boolean isErrorBetweenMeasuredTempAndDesiredTemperatureOutOfAdmissible(double _measuredTemperature, double _desiredTemperature, double _maxAdmissibleError) {
        double absoluteError = 0.0;
        absoluteError = Math.abs(_measuredTemperature-_desiredTemperature);
        if (absoluteError<_maxAdmissibleError) return true; else return false;
    }
    private void finishProgram() throws Throwable{
        for (int i=0;i<10;i++){
            printActionMessageAndProgressScreenMessage("FINISHING THE PROGRAM \n");
        }
        printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 �C \n");
        E2404.setTemperature(0);
        printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
        E2404.setInAutoMode();
        mainController.doAction(new ActionRequest("StopCalibrationProgramAction",frameWhoHasTheRequest));
        this.finalize();
    }
    private float read2700ChannelsAndSaveDataToFile(DiodesCalibrationSetUpData _diodesCalibrationSetUp_Data,TemperatureSensorData _temperatureSensorData) throws Exception{
        float pt100RealT = 0;
        float pt1004WResistance = 0;
        long currentTimeInMillis = 0;
        int ovenDisplayTemp = 0;
        int avg = 10;
        Object[] resultRow = new Object[resultsTableHeader.length];

        printActionMessageAndProgressScreenMessage("Reading the Oven Display Temperature. \n");
        ovenDisplayTemp = E2404.readTemperature();
        printActionMessageAndProgressScreenMessage("Reading the average temperature at the PT100 \n");
        pt100RealT = K2700.measureAveragePT100Temperature(_temperatureSensorData.getChannel(),avg);
        printActionMessageAndProgressScreenMessage("Reading the average 4-Wire Resistance at the PT100 \n");
        pt1004WResistance = K2700.measureAverage4WireResistance(_temperatureSensorData.getChannel(),avg);
        printActionMessageAndProgressScreenMessage("Reading the Devices To Measure. \n");

        currentTimeInMillis = System.currentTimeMillis();
        recalculateEstimatedStepDurationTime(currentTimeInMillis);

        resultRow[0] = pt100RealT;
        for (int i=0;i<_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure();i++){
            printActionMessageAndProgressScreenMessage("Reading the Device Number "+Integer.toString(i+1)+". \n");
            resultRow[i+1] = Float.toString(K2700.measureAverageVoltage(diodesCalibrationSetUp.getDiodesToCalibrateData().getDiodesToMeasureData()[i].getDeviceChannel(),avg));
        }
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+1] = pt1004WResistance;
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+2] = ovenDisplayTemp;
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+3] = Integer.toString(actualCalibrationStep);
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+4] = sdf2.format(currentTimeInMillis);
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+5] = sdf.format(currentTimeInMillis);
        resultRow[_diodesCalibrationSetUp_Data.getDiodesToCalibrateData().getNDevicesToMeasure()+6] = Integer.toString(diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures()[actualCalibrationStep]);


        saveDataToFile(resultRow);

        return pt100RealT;
    }
    private int createResultsTableHeaderAndFormatIt(DiodesCalibrationSetUpData _program,TemperatureSensorData _temperatureSensorData){

        resultsTableHeader = new Object[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+7];

    	for (int i=1;i<=(_program.getDiodesToCalibrateData().getNDevicesToMeasure()+7);i++){
            resultsTableHeaderFormat = resultsTableHeaderFormat + ("%"+Integer.toString((i))+"$-30s");
        }

        resultsTableHeader[0] = "PT100_Temp(�C)_Channel="+_temperatureSensorData.getChannel();
        for (int i=0;i<(_program.getDiodesToCalibrateData().getNDevicesToMeasure());i++){
            resultsTableHeader[i+1] = "VF_"+_program.getDiodesToCalibrateData().getDiodesToMeasureData()[i].getDeviceReference()+"_Channel="+_program.getDiodesToCalibrateData().getDiodesToMeasureData()[i].getDeviceChannel()+"_"+_program.getDiodesToCalibrateData().getDevicesCurrentInMilliAmps()+"mAmps";
        }
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+1] = "R_PT100(Ohms)_Channel="+_temperatureSensorData.getChannel();
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+2] = "Oven_Temp(�C)";
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+3] = "#_step";
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+4] = "Hour";
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+5] = "Date";
        resultsTableHeader[_program.getDiodesToCalibrateData().getNDevicesToMeasure()+6] = "SP_Temp(�C)";

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
        temperatureSensorData = new TemperatureSensorData(TEMPERATURE_SENSOR_DATA_FILE_PATH);
        return 0;
    }
    private int initializeManagingTimeObjects(DiodesCalibrationSetUpData _program){
        if (calendar == null) calendar = Calendar.getInstance();
        if (sdf2 == null) sdf2 = new SimpleDateFormat(TIME_FORMAT_NOW);
        sdf2.setTimeZone(TimeZone.getTimeZone(MADRID_TIME_ZONE));
        if (sdf == null) sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        calibrationProgressStartTimeInMillis = System.currentTimeMillis();
        calibrationProgressLastStepTimeInMillis = calibrationProgressStartTimeInMillis;
        calibrationProgressLastStepRealDurationTimeInMillis = FIRST_STEP_ESTIMATED_TIME_DURATION;
        calibrationProgressNextStepEstimatedDurationTimeInMillis = calibrationProgressLastStepRealDurationTimeInMillis;
        calibrationProgressElapsedTimeInMillis = 0;
        calibrationProgressRemainingTimeInMillis = _program.getTemperatureProfileData().getTemperatures().length * calibrationProgressNextStepEstimatedDurationTimeInMillis;

        calibrationProgress = actualCalibrationStep/_program.getTemperatureProfileData().getTemperatures().length;
        return 0;
    }
    private int initializeDiodesCalibrationSetUp()throws Exception{
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
            diodesCalibrationSetUp = new DiodesCalibrationSetUpData(programFilePath);
            if (diodesCalibrationSetUp.getDiodesToCalibrateData().getNDevicesToMeasure()==0){
                JOptionPane.showMessageDialog(null,"En el programa que desea ejecutar no se ha determinado ning�n dispositivo a medir.");
                return -1;
            }
            System.out.println(diodesCalibrationSetUp.toString());
            actualCalibrationStep = 0;
            return 0;
        } else {
            //log.append("Open command cancelled by user." + newline);
            return -1;
        }
    }
    private int initializeOven() throws Exception{
        printActionMessageAndProgressScreenMessage("INITIALIZING THE OVEN.... \n");
        calibrationProgressStartTimeInMillis = System.currentTimeMillis();
        printActionMessageAndProgressScreenMessage("Setting the TSP1 to 0 �C \n");
        E2404.setTemperature(0);
        printActionMessageAndProgressScreenMessage("Setting the 2404 in Auto Mode \n");
        E2404.setInAutoMode();
        return 0;
    }
    private int initializeTimer(Timer _t, long _timePeriod, TimerTask _task){
        printActionMessage("Initializing Timer for Refreshing Progress Screen every " + PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS/1000 + " seconds");
    	if (_t == null) _t = new Timer();
        _t.scheduleAtFixedRate(_task, 0, _timePeriod);
        return 0;
    }
    private void recalculateEstimatedStepDurationTime(long _stepTimeInMillis){
        calibrationProgressNextStepEstimatedDurationTimeInMillis = _stepTimeInMillis - calibrationProgressLastStepTimeInMillis;
        calibrationProgressRemainingTimeInMillis = (diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures().length - actualCalibrationStep)* calibrationProgressNextStepEstimatedDurationTimeInMillis;
        calibrationProgressLastStepTimeInMillis = _stepTimeInMillis;
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
    public class ProgressScreenRefreshTask extends TimerTask {
        public void run() {
            calibrationProgressElapsedTimeInMillis = System.currentTimeMillis() - calibrationProgressStartTimeInMillis;
            calibrationProgress = ((actualCalibrationStep*100)/diodesCalibrationSetUp.getTemperatureProfileData().getTemperatures().length);
            calibrationProgressRemainingTimeInMillis = calibrationProgressRemainingTimeInMillis - PROGRESS_SCREEN_REFRESH_TIME_PERIOD_MILLISECONDS;
            //System.out.println("program progress = "+programProgress);
            //insertTempPointsInGraph(System.currentTimeMillis(),remainingTimeInMillis,actualProgramStep);
            refreshProgressScreen();
        }
    };
    private void refreshProgressScreen(){
            progressScreenFrame.refreshProgressBarData(calibrationProgress);
            progressScreenFrame.refreshElapsedTimeField(sdf2.format(calibrationProgressElapsedTimeInMillis));
            progressScreenFrame.refreshRemainingTimeField(sdf2.format(calibrationProgressRemainingTimeInMillis));
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