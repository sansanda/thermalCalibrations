package Ovens;

import java.io.*;

import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadInputRegistersRequest;
import com.serotonin.modbus4j.msg.ReadInputRegistersResponse;
import com.serotonin.modbus4j.msg.WriteRegisterRequest;
import com.serotonin.modbus4j.msg.WriteRegisterResponse;
import com.serotonin.io.serial.SerialParameters;
import com.serotonin.io.serial.SerialUtils;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;


public class Eurotherm2404_v2 {

	/* The important instances of the classes mentioned before */

	SerialUtils su;
	SerialParameters sp;
	ModbusMaster modbusMaster;
	ReadInputRegistersRequest rirRequest;
	ReadInputRegistersResponse rirResponse;
	WriteRegisterRequest wrRequest;
	WriteRegisterResponse wrResponse;

	static File 			testFile 	= null;
	static File 			resFile 	= null;

	static BufferedWriter 	fWriter 	= null;
	static BufferedReader 	fReader 	= null;

	static int CONTROLLER_ID;

	static byte TEMP_ADDR = 1;

	static char SET_TEMPERATURE_SETPOINT_ADDR = 2;
	static char READ_SETPOINT1_ADDR = 24;
	static char READ_SETPOINT2_ADDR = 25;
	static char OUTPUTPOWER_ADDR = 3;
	static char MODE_ADDR = 273;

	static int 	USER_CALIBRATION_ENABLE_ADDR = 110;

	static int  MAX_TEMP = 500;
	static int  MIN_TEMP = 0;
	static int 	MIN_OUTPUTPOWER = 0;
	static int 	MAX_OUTPUTPOWER = 100;
	static byte MANUAL_MODE= 1;
	static byte AUTO_MODE = 0;
	static byte FACTORY_CALIBRATION = 0;
	static byte USER_CALIBRATION = 0;

	/**
	 *
	 * @param wantedPortName
	 * @param _controllerID
	 * @throws Exception
	 */
	public Eurotherm2404_v2(String wantedPortName,int _controllerID) throws Exception {

		//su = new SerialUtils();
		sp = new SerialParameters();

		CONTROLLER_ID = _controllerID;

		initializeSerialParameters(wantedPortName);
		initializeModbusMaster(sp, false);
	}
	/**
	 *
	 * @param sp
	 * @param b
	 * @throws Exception
	 */
	private void initializeModbusMaster(SerialParameters sp, boolean b) throws Exception {
		modbusMaster = new ModbusFactory().createRtuMaster(sp, b);
    	modbusMaster.init();
	}
	/**
	 *
	 * @param _wantedPortName
	 */
	private void initializeSerialParameters(String _wantedPortName) {
		//Setup serial parameters
		sp.setPortOwnerName(_wantedPortName);
    	sp.setCommPortId(_wantedPortName);
    	sp.setBaudRate(9600);
    	sp.setDataBits(8);
    	sp.setStopBits(1);
    	sp.setParity(0);
	}
	/**
	 *
	 * @return
	 * @throws ModbusTransportException
	 */
	public int enableUserCalibration() throws ModbusTransportException{
		wrRequest = new WriteRegisterRequest(CONTROLLER_ID,USER_CALIBRATION_ENABLE_ADDR,USER_CALIBRATION);
		wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
		return wrResponse.getFunctionCode();
	}
	/**
	 *
	 * @return
	 * @throws ModbusTransportException
	 */
	public int enableFactoryCalibration() throws ModbusTransportException{
		wrRequest = new WriteRegisterRequest(CONTROLLER_ID,USER_CALIBRATION_ENABLE_ADDR,FACTORY_CALIBRATION);
		wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
		return wrResponse.getFunctionCode();
	}
	public int setTwoPointsUserCalibrationCalibration(float _p1,float _userP1,float _p2,float _userP2){
		// TODO Implementar el método para introducir al 2404 la calibracion de usuario por dos puntos
		return 0;
	}
	public int setTemperature(int _temp) throws ModbusTransportException{
		int result = 0;
		if (_temp<MIN_TEMP || _temp>MAX_TEMP) return -1;
		wrRequest = new WriteRegisterRequest(1,SET_TEMPERATURE_SETPOINT_ADDR,_temp);
	    wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
		return result;
	}
	public int readTemperature() throws ModbusTransportException{
		int result = -500;
		int slaveID;
		int exceptionCode;
		int functionCode;
		short[] data;
		rirRequest = new ReadInputRegistersRequest(CONTROLLER_ID,TEMP_ADDR,1);
    	rirResponse = (ReadInputRegistersResponse)modbusMaster.send(rirRequest);
    	slaveID = rirResponse.getSlaveId();
	    exceptionCode = rirResponse.getExceptionCode();
	    functionCode = rirResponse.getFunctionCode();
	    result = rirResponse.getShortData()[0];
	    System.out.println("\n"+"oven read value = "+ result+"\n");
	    return result;
	}
	public int changeMode(int mode) throws ModbusTransportException{
		int result = -1;
		if (mode!=0 || mode!=1){}
		else{
			if (mode == 1) 	result = setInManualMode();
			if (mode == 0)	result = setInAutoMode();
		}
		return result;
	}
	public int setInManualMode(){
		int result=0;
		try {
			wrRequest = new WriteRegisterRequest(CONTROLLER_ID,MODE_ADDR,MANUAL_MODE);
			wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -500;
		}
	}
	public int setInAutoMode() throws ModbusTransportException{
		int result=0;
		wrRequest = new WriteRegisterRequest(CONTROLLER_ID,MODE_ADDR,AUTO_MODE);
		wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
		return result;
	}
	public int setOutputPower(int _power) throws ModbusTransportException{
		int result=0;
		if (_power<MIN_OUTPUTPOWER || _power>MAX_OUTPUTPOWER) return -1;
		wrRequest = new WriteRegisterRequest(CONTROLLER_ID,OUTPUTPOWER_ADDR,_power);
		wrResponse = (WriteRegisterResponse)modbusMaster.send(wrRequest);
		return result;

	}
	public static void main(String[] args) {
		try {
			Eurotherm2404_v2 eurotherm2404 = new Eurotherm2404_v2("COM6",1);
			int temp = eurotherm2404.readTemperature();
			while (temp==-500){
				temp = eurotherm2404.readTemperature();
				Thread.sleep(100);
			}
			System.out.println("temperature =" + temp);
			/*
			eurotherm2404.setInAutoMode();
			for (int i=0;i<100;i++){
				eurotherm2404.setTemperature(i);
			}
			*/
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
