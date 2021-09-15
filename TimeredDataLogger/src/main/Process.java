package main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataLogger.TimeredDataLogger;
import buffering.MeasureDataBuffer;
import multimeters.KeithleyK2700.K2700;
import sense.Sense_Subsystem;
import system.System_Subsystem;

import multimeters.KeithleyK2700.configuration_files.*;

public class Process implements PropertyChangeListener{

	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	//////////////////////ERRORS
	
	
	//////////////////////VERSIONS
	private static final int classVersion = 102;
	
	//////////////////////LOGGER
	final static Logger logger = LogManager.getLogger(Process.class);
	
	private K2700 k2700;
	private TimeredDataLogger tdl;

	private MeasureDataBuffer dataBuffer = null;
	
	private int counter = 0;
	
	public Process(K2700 _k2700, MeasureDataBuffer _dataBuffer) throws Exception {
		super();
	
		this.k2700 = _k2700;
		
		this.dataBuffer = _dataBuffer;
		this.dataBuffer.addPropertyChangeListener(this);	
		
		this.tdl = new TimeredDataLogger(dataBuffer, 0, 5000, this.k2700, 110);
		
		this.tdl.start();
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		try {
		
		System.out.println(evt.getPropertyName());
		
		switch (evt.getPropertyName()) {

			case "newData":
				this.newDataEventHandler(evt);
				this.dataBuffer.size();
				
				
			default:
				break;
		}
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void newDataEventHandler(PropertyChangeEvent evt) throws Exception {
		this.counter++;
		System.out.println("-----------------------------------------------------------------------------------------------" + counter);
		System.out.println(dataBuffer.toString());
		double dataBuffer_stDev = maths.maths.stDev(this.dataBuffer.dataSubList(0, this.dataBuffer.size())); 
		System.out.println("--------------------------------> " + dataBuffer_stDev);
	}
	
	public static void main(String[] args) throws Exception {
		
		//String k2700_filename	= "../InstrumentInterfaces/Components/configuration_files/k2700_default_configuration.json";
		String k2700_filename	= FILES_PATHS.K2700_PARAMETERS_FILENAME;

		ArrayList<Double> 	dataList = new ArrayList<Double>();
		ArrayList<Double> 	tStampList = new ArrayList<Double>();
		ArrayList<Integer> 	readingNumberList = new ArrayList<Integer>();	
		MeasureDataBuffer _dataBuffer = new MeasureDataBuffer(dataList,tStampList,readingNumberList);
		
		K2700 k2700 = new K2700(k2700_filename);
		k2700.initialize();
		
		((System_Subsystem)k2700.getSubComponent(K2700.SYSTEM_SUBSYSTEM)).enableBeeper(false);
		
		k2700.configureAsTCThermometer(1.0f, (byte)7, 0, 8, 0.1f, Sense_Subsystem.AVERAGE_REPEAT_TCONTROL, true,
		Sense_Subsystem.TCOUPLE_K_TYPE_TRANSDUCER,
		true,
		Sense_Subsystem.TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE,
		23);
		
		Process proccess = new Process(k2700, _dataBuffer);
		
	}

}

