package Ovens;

import java.io.*;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.msg.WriteSingleRegisterRequest;
import net.wimpi.modbus.msg.WriteSingleRegisterResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.SimpleRegister;
import net.wimpi.modbus.util.SerialParameters;


public class Eurotherm2404 {

	/* The important instances of the classes mentioned before */
	SerialConnection con = null; //the connection
	ModbusSerialTransaction trans = null; //the transaction
	SerialParameters params;
	
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
	
	static int  MAX_TEMP = 500;
	static int  MIN_TEMP = 0;
	static int 	MIN_OUTPUTPOWER = 0;
	static int 	MAX_OUTPUTPOWER = 100;
	static byte MANUAL_MODE= 1;
	static byte AUTO_MODE = 0;
	
	public Eurotherm2404(String wantedPortName,int _controllerID) throws Exception {
		CONTROLLER_ID = _controllerID;
		//ModbusCoupler.getReference().setUnitID(CONTROLLER_ID);
		
		//3. Setup serial parameters
		params = new SerialParameters();
		params.setPortName(wantedPortName);
		params.setBaudRate(9600);
		params.setDatabits(javax.comm.SerialPort.DATABITS_8);
		params.setParity(javax.comm.SerialPort.PARITY_EVEN);
		params.setStopbits(javax.comm.SerialPort.STOPBITS_1);
		params.setFlowControlIn(javax.comm.SerialPort.FLOWCONTROL_NONE);
		params.setEncoding(Modbus.SERIAL_ENCODING_RTU);
		params.setEcho(false);
		//4. Create the connection
		con = new SerialConnection(params);
	}
	public int openConnection(){
		//4bis. Open the connection
		try {
			System.out.println("E2404 OPENNING THE SERIAL CONNECTION");
			con.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int closeConnection(){
		//4bis. Open the connection
		System.out.println("E2404 CLOSING THE SERIAL CONNECTION");
		con.close();
		return 0;
	}
	public int setTemperature(int _temp){
		int result = 0;
		try {
			if (_temp<MIN_TEMP || _temp>MAX_TEMP) return -1;
			
			//4. Open the connection
			this.openConnection();

			
			//6. Prepare a transaction
			trans = new ModbusSerialTransaction(con);
			
			WriteSingleRegisterResponse res = null; //the response
			WriteSingleRegisterRequest req = null; //the request;
			SimpleRegister s = new SimpleRegister((byte)(_temp/256),(byte)(_temp%256));

			req = new WriteSingleRegisterRequest(SET_TEMPERATURE_SETPOINT_ADDR, s);
			req.setUnitID(CONTROLLER_ID);
			req.setHeadless();
			
			//6. Prepare a transaction
			trans.setRequest(req);
			trans.execute();
			
			res = (WriteSingleRegisterResponse) trans.getResponse();
			result = res.getRegisterValue();
			System.out.println("WriteSingleRegisterResponse = " + result);
			
			this.closeConnection();
			return result;
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return result;
		} catch (ModbusSlaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		}
	}
	public int readTemperature(){
		int result = -500;
		try {
			
			//4. Open the connection
			//this.closeConnection();
			this.openConnection();
			//6. Prepare a transaction
			trans = new ModbusSerialTransaction(con);
			//trans.setCheckingValidity(true);
			
			ReadInputRegistersResponse res = null; //the response
			ReadInputRegistersRequest req = null; //the request;
	
			req = new ReadInputRegistersRequest(TEMP_ADDR,1);
			req.setUnitID(CONTROLLER_ID);
			//req.setHeadless();
			
			//6. Prepare a transaction
			trans.setRequest(req);
			trans.execute();
			res = (ReadInputRegistersResponse) trans.getResponse();
			result = res.getRegisterValue(0);
			//System.out.println("ReadInputRegistersResponse = " + result);
			
			
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusSlaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
	public int changeMode(int mode){
		int result = -1;
		if (mode!=0 || mode!=1){}
		else{
			if (mode == 1) 	result = setInManualMode();
			if (mode == 0)	result = setInAutoMode();
		}
		return result;
	}
	public int setInManualMode(){
		int result = 0;
		try {
			//4. Open the connection
			this.openConnection();

			
			//6. Prepare a transaction
			trans = new ModbusSerialTransaction(con);
			
			WriteSingleRegisterResponse res = null; //the response
			WriteSingleRegisterRequest req = null; //the request;
			SimpleRegister s = new SimpleRegister((byte)0,MANUAL_MODE);

			req = new WriteSingleRegisterRequest(MODE_ADDR, s);
			req.setUnitID(CONTROLLER_ID);
			req.setHeadless();
			
			//6. Prepare a transaction
			trans.setRequest(req);
			trans.execute();
			
			res = (WriteSingleRegisterResponse) trans.getResponse();
			result = res.getRegisterValue();
			System.out.println("WriteSingleRegisterResponse = " + result);
			
			this.closeConnection();
			return result;
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusSlaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		}
	}
	public int setInAutoMode(){
		int result = 0;
		try {
			//4. Open the connection
			this.openConnection();

			
			//6. Prepare a transaction
			trans = new ModbusSerialTransaction(con);
			
			WriteSingleRegisterResponse res = null; //the response
			WriteSingleRegisterRequest req = null; //the request;
			SimpleRegister s = new SimpleRegister((byte)0,AUTO_MODE);

			req = new WriteSingleRegisterRequest(MODE_ADDR, s);
			req.setUnitID(CONTROLLER_ID);
			req.setHeadless();
			
			//6. Prepare a transaction
			trans.setRequest(req);
			trans.execute();
			
			res = (WriteSingleRegisterResponse) trans.getResponse();
			result = res.getRegisterValue();
			System.out.println("WriteSingleRegisterResponse = " + result);
			
			this.closeConnection();
			return result;
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusSlaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		}
	}
	public int setOutputPower(int _power){
		int result=0;
		try {
			if (_power<MIN_OUTPUTPOWER || _power>MAX_OUTPUTPOWER) return -1;
			
			//4. Open the connection
			this.openConnection();

			
			//6. Prepare a transaction
			trans = new ModbusSerialTransaction(con);
			
			WriteSingleRegisterResponse res = null; //the response
			WriteSingleRegisterRequest req = null; //the request;
			SimpleRegister s = new SimpleRegister((byte)(_power/256),(byte)(_power%256));

			req = new WriteSingleRegisterRequest(OUTPUTPOWER_ADDR, s);
			req.setUnitID(CONTROLLER_ID);
			req.setHeadless();
			
			//6. Prepare a transaction
			trans.setRequest(req);
			
			trans.execute();
			
			res = (WriteSingleRegisterResponse) trans.getResponse();
			result = res.getRegisterValue();
			System.out.println("WriteSingleRegisterResponse = " + result);
			
			this.closeConnection();
			return result;
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusSlaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.closeConnection();
			return result;
		}
	}
	public static void main(String[] args) {
		try {
			Eurotherm2404 eurotherm2404 = new Eurotherm2404("COM6",1);
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
