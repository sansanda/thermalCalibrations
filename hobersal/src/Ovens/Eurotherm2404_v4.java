package Ovens;

import java.io.*;

import com.schneide.quantity.Quantity;
import com.schneide.quantity.mechanicalQuantities.Hour;
import com.schneide.quantity.mechanicalQuantities.Kilometer;
import com.schneide.quantity.mechanicalQuantities.MeterPerSecond;
import com.schneide.quantity.mechanicalQuantities.Second;
import com.schneide.quantity.mechanicalQuantities.handlers.SpeedHandler;
import com.schneide.quantity.miscQuantities.GradCelsius;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.facade.ModbusSerialMaster;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
import net.wimpi.modbus.net.ModbusSerialListener;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.SerialParameters;


public class Eurotherm2404_v4 {

	/* The important instances of the classes mentioned before */

	SerialParameters sp;
	ModbusSerialTransaction trans = null;
	ModbusSerialMaster modbusSerialMaster = null;
	ReadInputRegistersRequest rirRequest = null;
	ReadInputRegistersResponse rirResponse = null;
	WriteSingleRegisterRequest wrRequest = null;
	WriteSingleRegisterResponse wrResponse = null;
	SerialConnection sc;
	ModbusSerialListener mbsl;

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
	public Eurotherm2404_v4(String wantedPortName,int _controllerID) throws Exception {
		CONTROLLER_ID = _controllerID;
		initializeSerialParameters(wantedPortName);
		//mbsl = new ModbusSerialListener(sp);
		//mbsl.setListening(true);

	}

	private void initializeSerialConnection(SerialParameters _sp) throws Exception {
		sc = new SerialConnection(_sp);
		if (!sc.isOpen()) sc.open();
		else {
			sc.close();
			sc.open();
		}
	}
	/**
	 *
	 * @param _wantedPortName
	 */
	private void initializeSerialParameters(String _wantedPortName) {
		//Setup serial parameters
		sp = new SerialParameters();
		sp.setPortName(_wantedPortName);
    	sp.setBaudRate(9600);
    	sp.setDatabits(8);
    	sp.setStopbits(1);
    	sp.setParity("None");
    	sp.setEncoding("rtu");
    	sp.setEcho(false);
	}
	/**
	 *
	 * @return
	 * @throws Exception
	 * @throws ModbusTransportException
	 */
	public int enableUserCalibration() throws Exception{
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, USER_CALIBRATION_ENABLE_ADDR,USER_CALIBRATION);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	/**
	 *
	 * @return
	 * @throws Exception
	 * @throws ModbusTransportException
	 */
	public int enableFactoryCalibration() throws Exception{
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, USER_CALIBRATION_ENABLE_ADDR,FACTORY_CALIBRATION);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	public int setTwoPointsUserCalibrationCalibration(float _p1,float _userP1,float _p2,float _userP2){
		// TODO Implementar el método para introducir al 2404 la calibracion de usuario por dos puntos
		return 0;
	}
	public int setTemperature(int _temp) throws Exception{
		if (_temp<MIN_TEMP || _temp>MAX_TEMP) return -1;
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, SET_TEMPERATURE_SETPOINT_ADDR,_temp);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	public GradCelsius readTemperature() throws Exception{
		int result = -500;
		initializeSerialConnection(sp);
		ReadInputRegistersRequest rirr = prepareReadInputRegisterRequest(CONTROLLER_ID, TEMP_ADDR,1);
		ModbusSerialTransaction t = prepareTransaction(sc,rirr);
		ReadInputRegistersResponse rir_resp = (ReadInputRegistersResponse) executeTransaction(sc,t);
		result = rir_resp.getRegisterValue(0);
		return new GradCelsius(result);
	}
	public  double readOutputPower() throws Exception{
		double result = -500;
		initializeSerialConnection(sp);
		ReadInputRegistersRequest rirr = prepareReadInputRegisterRequest(CONTROLLER_ID, OUTPUTPOWER_ADDR,1);
		ModbusSerialTransaction t = prepareTransaction(sc,rirr);
		ReadInputRegistersResponse rir_resp = (ReadInputRegistersResponse) executeTransaction(sc,t);
		result = rir_resp.getRegisterValue(0);
		return result;
	}
	public int changeMode(int mode) throws Exception{
		int result = -1;
		if (mode!=0 || mode!=1){}
		else{
			if (mode == 1) 	result = setInManualMode();
			if (mode == 0)	result = setInAutoMode();
		}
		return result;
	}
	public int setInManualMode() throws Exception{
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, MODE_ADDR,MANUAL_MODE);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	public int setInAutoMode() throws Exception{
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, MODE_ADDR,AUTO_MODE);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	public int setOutputPower(int _power) throws Exception{
		if (_power<MIN_OUTPUTPOWER || _power>MAX_OUTPUTPOWER) return -1;
		int result = -500;
		initializeSerialConnection(sp);
		WriteSingleRegisterRequest wsrr = prepareWriteSingleRegisterRequest(CONTROLLER_ID, OUTPUTPOWER_ADDR,_power);
		ModbusSerialTransaction t = prepareTransaction(sc,wsrr);
		WriteSingleRegisterResponse wsr_resp = (WriteSingleRegisterResponse) executeTransaction(sc,t);
		result = wsr_resp.getRegisterValue();
		return result;
	}
	/**
	 *
	 * @param sc
	 * @param t
	 * @return
	 * @throws ModbusException
	 */
	private ModbusResponse executeTransaction(SerialConnection sc,
			ModbusSerialTransaction t) throws ModbusException {
		//7. Execute the transaction
		t.execute();
		ModbusResponse res = trans.getResponse();
		//8. Close the connection
		sc.close();
		//9. Return the result
		return res;
	}
	/**
	 *
	 * @param scon
	 * @param rirr
	 * @return
	 */
	private ModbusSerialTransaction prepareTransaction(SerialConnection scon,
			ReadInputRegistersRequest rirr) {
		//6. Prepare a transaction
		trans = new ModbusSerialTransaction(scon);
		trans.setRequest(rirr);
		return trans;
	}
	/**
	 *
	 * @param scon
	 * @param wsrr
	 * @return
	 */
	private ModbusSerialTransaction prepareTransaction(SerialConnection scon,
			WriteSingleRegisterRequest wsrr) {
		//6. Prepare a transaction
		trans = new ModbusSerialTransaction(scon);
		trans.setRequest(wsrr);
		return trans;
	}

	/**
	 *
	 * @param unitid
	 * @param ref
	 * @param count
	 * @return
	 */
	private ReadInputRegistersRequest prepareReadInputRegisterRequest(int unitid, int ref, int count) {
		//5. Prepare a request
		ReadInputRegistersRequest req = null; //the request
		req = new ReadInputRegistersRequest(ref, count);
		req.setUnitID(unitid);
		req.setHeadless();
		return req;
	}
	private WriteSingleRegisterRequest prepareWriteSingleRegisterRequest(
			int unitid, int ref, int valueToWrite) {
		//5. Prepare a request
		SimpleRegister sr = new SimpleRegister();
		sr.setValue(valueToWrite);
		WriteSingleRegisterRequest req = null; //the request
		req = new WriteSingleRegisterRequest(ref, sr);
		req.setUnitID(unitid);
		req.setHeadless();
		return req;
	}
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			/*Eurotherm2404_v4 eurotherm2404 = new Eurotherm2404_v4("COM4",1);
			long timeA = System.currentTimeMillis();
			long elapsed = 10000;
			while ((System.currentTimeMillis()-timeA)<elapsed){
				System.out.println(eurotherm2404.readTemperature());
				Thread.sleep(500);
			}
			int temp = 75;
			System.out.println("Setting temperature to " + temp);
			eurotherm2404.setTemperature(temp);

			timeA = System.currentTimeMillis();
			while ((System.currentTimeMillis()-timeA)<elapsed){
				System.out.println(eurotherm2404.readTemperature());
				Thread.sleep(500);

			}*/

			Hour time = new Hour(1);
			time.add(new Second(3600));
			Kilometer length = new Kilometer(100);
			Quantity averageSpeed = Quantity.divide(length, time);
			MeterPerSecond speed = SpeedHandler.changeToMeterPerSecond(averageSpeed);
			System.out.println("Average speed: " + speed.toString());


			//System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
