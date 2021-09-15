package examples;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortFactoryJSSC;
import com.intelligt.modbus.jlibmodbus.serial.SerialUtils;
import com.schneide.quantity.miscQuantities.GradCelsius;

import Ovens.Eurotherm2404;


/*
 *
 * Authors: David Sanchez Sanchez, software engineer.
 * email: dsancheznsanc@uoc.edu
 * 
 * Clase hecha para la practica con la libreria JLibModbus con un horno hobersal que 
 * utiliza un Controlador Eurotherm 2404
 *  
 */
public class Eurotherm2404_practices {
	//TODO: Modificar la clase llamada Eurotherm2404_v5 para pasarle el mosdbus master en el constructory a partir de alli implementar todos los metodos de lectura e imposicion de temparatura etc.
	
	
	public Eurotherm2404_practices(String commPort, Modbus.LogLevel logLevel) throws RuntimeException, ModbusIOException, Exception{
		super();
		// TODO Auto-generated constructor stub
		this.initialize(commPort, logLevel);
	}


	static String COMM_PORT = "COM1";
	static int SLAVE_ID = 1;

	static byte TEMP_ADDR = 1;

	static char SET_TEMPERATURE_SETPOINT1_ADDR = 2;
	static char SET_TEMPERATURE_SETPOINT2_ADDR = 3;
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
	
	SerialParameters sp = null;
	ModbusMaster m = null;
			
	public void initialize(String commPort,  Modbus.LogLevel logLevel) throws RuntimeException, ModbusIOException, Exception{
		this.sp = initializeSerialConnection(commPort);
		this.m = initializeModBusMaster(this.sp, logLevel);
	}
	
	public SerialParameters initializeSerialConnection(String commPort) throws RuntimeException, ModbusIOException, Exception 
	{
		SerialParameters sp = new SerialParameters(); 

        // set the serial port name
        sp.setDevice(Eurotherm2404.COMM_PORT_1);
        // these parameters are set by default
        sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_9600);
        sp.setDataBits(8);
        sp.setParity(SerialPort.Parity.NONE);
        sp.setStopBits(1);
        //you can choose the library to use.
        //the library uses jssc by default.
        //
        //first, you should set the factory that will be used by library to create an instance of SerialPort.
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryRXTX());
        //  JSSC is Java Simple Serial Connector
        SerialUtils.setSerialPortFactory(new SerialPortFactoryJSSC());
        //  PJC is PureJavaComm.
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryPJC());
        //  JavaComm is the Java Communications API (also known as javax.comm)
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryJavaComm());
        //in case of using serial-to-wifi adapter
        //String ip = "192.168.0.180";//for instance
        //int port  = 777;
        //SerialUtils.setSerialPortFactory(new SerialPortFactoryTcp(new TcpParameters(InetAddress.getByName(ip), port, true)));
        // you should use another method:
        //next you just create your master and use it.
        
        return sp;
    
	}
	
	public ModbusMaster initializeModBusMaster(SerialParameters sp, Modbus.LogLevel logLevel) throws RuntimeException, ModbusIOException, Exception {
		//The Master is the PC
		Modbus.setLogLevel(logLevel);
        ModbusMaster m = ModbusMasterFactory.createModbusMasterRTU(sp);
        m.connect();
        return m;
	}
	
	
	public GradCelsius readTemperature(){
		double result = Double.NaN;
		
		try {
			int[] data = this.m.readInputRegisters(SLAVE_ID, TEMP_ADDR, 1);
			result = data[0];
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new GradCelsius(result);
	}
	
	public GradCelsius readOutputPower(){
		double result = Double.NaN;
		
		try {
			int[] data = this.m.readInputRegisters(SLAVE_ID, OUTPUTPOWER_ADDR, 1);
			result = data[0];
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new GradCelsius(result);
	}
	
	public GradCelsius readTemperatureSetpoint1(){
		double result = Double.NaN;
		
		try {
			int[] data = this.m.readInputRegisters(SLAVE_ID, READ_SETPOINT1_ADDR, 1);
			result = data[0];
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new GradCelsius(result);
	}
	
	public GradCelsius readTemperatureSetpoint2(){
		double result = Double.NaN;
		
		try {
			int[] data = this.m.readInputRegisters(SLAVE_ID, READ_SETPOINT2_ADDR, 1);
			result = data[0];
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new GradCelsius(result);
	
	}
	
	public void setTemperatureSetpoint1(int setPoint){
		try {
			this.m.writeSingleRegister(SLAVE_ID, SET_TEMPERATURE_SETPOINT1_ADDR, setPoint);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setTemperatureSetpoint2(int setPoint){
		try {
			this.m.writeSingleRegister(SLAVE_ID, SET_TEMPERATURE_SETPOINT2_ADDR, setPoint);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void toggleMode(int mode) throws Exception{
		if (mode!=AUTO_MODE || mode!=MANUAL_MODE){ return;}
		
		if (mode == AUTO_MODE) 	setInManualMode();
		else if (mode == MANUAL_MODE)	setInAutoMode();
		
	}
	
	public void setInManualMode() throws Exception{
		try {
			this.m.writeSingleRegister(SLAVE_ID, MODE_ADDR, MANUAL_MODE);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInAutoMode() throws Exception{
		try {
			this.m.writeSingleRegister(SLAVE_ID, MODE_ADDR, AUTO_MODE);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 */
	public void enableUserCalibration(){
		try {
			this.m.writeSingleRegister(SLAVE_ID, USER_CALIBRATION_ENABLE_ADDR,USER_CALIBRATION);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 */
	public void enableFactoryCalibration(){
		try {
			this.m.writeSingleRegister(SLAVE_ID, USER_CALIBRATION_ENABLE_ADDR,FACTORY_CALIBRATION);
		} catch (ModbusProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    static public void main(String[] arg) {
    	try {
    		
			Eurotherm2404_practices e = new Eurotherm2404_practices("COM1", Modbus.LogLevel.LEVEL_WARNINGS);
			System.out.println("Temperatura actual = " + e.readTemperature().getValue());
			System.out.println("Potencia = " + e.readOutputPower().getValue());
			e.setInAutoMode();
			e.setTemperatureSetpoint1(0);
			while (true)
			{
				System.out.println("Temperatura actual = " + e.readTemperature().getValue());
				System.out.println("Potencia = " + e.readOutputPower().getValue());
				Thread.sleep(1000);
			}
			
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}
