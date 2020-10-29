package Ovens;

import java.util.Arrays;

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


/*
 * Copyright (C) 2016 "Invertor" Factory", JSC
 * All rights reserved
 *
 * This file is part of JLibModbus.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Authors: Vladislav Y. Kochedykov, software engineer.
 * email: vladislav.kochedykov@gmail.com
 * 
 * Clase hecha para la practica con la libreria JLibModbus con un horno hobersal que 
 * utiliza un Controlador Eurotherm 2404
 *  
 */
public class Eurotherm2404_practices {
	//TODO: Modificar la clase llamada Eurotherm2404_v5 para pasarle el mosdbus master en el constructory a partir de alli implementar todos los metodos de lectura e imposicion de temparatura etc.
	
	
	public Eurotherm2404_practices(String commPort) throws RuntimeException, ModbusIOException, Exception{
		super();
		// TODO Auto-generated constructor stub
		this.initialize(commPort);
	}


	static String COMM_PORT = "COM1";
	static int SLAVE_ID = 1;

	static byte TEMP_ADDR = 1;

	static char SET_TEMPERATURE_SETPOINT_ADDR = 2;
	static char READ_SETPOINT1_ADDR = 24;
	static char READ_SETPOINT2_ADDR = 25;
	static char OUTPUTPOWER_ADDR = 3;
	static char MODE_ADDR = 273;

	static int 	USER_CALIBRATION_ENABLE_ADDR = 110;
	
	SerialParameters sp = null;
	ModbusMaster m = null;
			
	public void initialize(String commPort) throws RuntimeException, ModbusIOException, Exception{
		this.sp = initializeSerialConnection(commPort);
		this.m = initializeModBusMaster(this.sp);
	}
	
	public SerialParameters initializeSerialConnection(String commPort) throws RuntimeException, ModbusIOException, Exception 
	{
		SerialParameters sp = new SerialParameters(); 

        // set the serial port name
        sp.setDevice(Eurotherm2404_practices.COMM_PORT);
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
	
	public ModbusMaster initializeModBusMaster(SerialParameters sp) throws RuntimeException, ModbusIOException, Exception {
		//The Master is the PC
		Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
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
	
	
    static public void main(String[] arg) {
    	try {
			Eurotherm2404_practices e = new Eurotherm2404_practices("COM1");
			System.out.println(e.readTemperature().getValue());
			
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}