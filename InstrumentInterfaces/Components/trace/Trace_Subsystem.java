package trace;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;

public class Trace_Subsystem extends InstrumentComponent implements I_Trace_Subsystem {

	//version 100: Initial version. Not working
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Trace_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Trace tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	private boolean traceBufferAutoclear = true;
	private int 	traceBufferSize = 100;
	private int 	traceBuffer_NumberOfReadings_Notify = 50;
	private String 	traceBufferTStampFormat = "DELTa";
	private String  traceBufferReadingsSource = "SENSe";
	private String 	traceBufferControlMode = "NEVer";
	
	private boolean dataBufferAutoclear = true;
	private int 	dataBufferSize = 100;
	private int 	dataBuffer_NumberOfReadings_Notify = 50;
	private String 	dataBufferTStampFormat = "DELTa";
	private String  dataBufferReadingsSource = "SENSe";
	private String 	dataBufferControlMode = "NEVer";
	
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Trace_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	public Trace_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
	}

	public Trace_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Trace_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Creating new instance of Trace_Subsystem from jObj ... ");
		
		JSONObject configuration = (JSONObject) jObj.get("Configuration");
		
		JSONObject trace_configuration = (JSONObject) configuration.get("trace");
		
		this.setTraceBufferAutoclear(((String)trace_configuration.get("autoclear")).equals("ON"));
		this.setTraceBufferSize(((Long)trace_configuration.get("size")).intValue());
		this.setTraceBuffer_NumberOfReadings_Notify(((Long)trace_configuration.get("notify")).intValue());
		this.setTraceBufferTStampFormat((String)trace_configuration.get("tstamp_format"));
		this.setTraceBufferReadingsSource((String)trace_configuration.get("readings_source"));
		this.setTraceBufferControlMode((String)trace_configuration.get("buffer_control_mode"));
		
		
		JSONObject data_configuration = (JSONObject) configuration.get("data");
		
		this.setDataBufferAutoclear(((String)data_configuration.get("autoclear")).equals("ON"));
		this.setDataBufferSize(((Long)data_configuration.get("size")).intValue());
		this.setDataBuffer_NumberOfReadings_Notify(((Long)data_configuration.get("notify")).intValue());
		this.setDataBufferTStampFormat((String)data_configuration.get("tstamp_format"));
		this.setDataBufferReadingsSource((String)data_configuration.get("readings_source"));
		this.setDataBufferControlMode((String)data_configuration.get("buffer_control_mode"));
		
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	
	public static int getClassversion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Trace SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Trace SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		logger.info("Uploading trace buffer configuration ... ");
		
		this.communicationsInterface.write("TRACe:CLEar:AUTO " + ((this.isTraceBufferAutoclear())? "ON":"OFF"));
		this.communicationsInterface.write("TRACe:POINts " + this.getTraceBufferSize());
		this.communicationsInterface.write("TRACe:NOTify " + this.getTraceBuffer_NumberOfReadings_Notify());
		this.communicationsInterface.write("TRACe:TSTamp:FORMat " + this.getTraceBufferTStampFormat());
		this.communicationsInterface.write("TRACe:FEED " + this.getTraceBufferReadingsSource());
		this.communicationsInterface.write("TRACe:FEED:CONTrol " + this.getTraceBufferControlMode());
	
		logger.info("Uploading data buffer configuration ... ");
		
		this.communicationsInterface.write("DATA:CLEar:AUTO " + ((this.isDataBufferAutoclear())? "ON":"OFF"));
		this.communicationsInterface.write("DATA:POINts " + this.getDataBufferSize());
		this.communicationsInterface.write("DATA:NOTify " + this.getDataBuffer_NumberOfReadings_Notify());
		this.communicationsInterface.write("DATA:TSTamp:FORMat " + this.getDataBufferTStampFormat());
		this.communicationsInterface.write("DATA:FEED " + this.getDataBufferReadingsSource());
		this.communicationsInterface.write("DATA:FEED:CONTrol " + this.getDataBufferControlMode());
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Trace SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}

	/**
	 * clear buffer
	 */
	@Override
	public void sendClearBufferCommand() throws Exception{
		
		logger.info("Clearing the trac buffer  ... ");
		this.communicationsInterface.write("TRACe:CLEar");
		
	}
	
	@Override
	public String queryTraceData() throws Exception {
		logger.info("Querying the trace buffer data  ... ");
		byte[] byteArrayResponse = this.communicationsInterface.ask("TRACe:DATA?");
		return new String(byteArrayResponse, StandardCharsets.UTF_8);
	}
	
	
	private boolean isTraceBufferAutoclear() {
		return traceBufferAutoclear;
	}

	private void setTraceBufferAutoclear(boolean traceBufferAutoclear) {
		this.traceBufferAutoclear = traceBufferAutoclear;
	}

	private int getTraceBufferSize() {
		return traceBufferSize;
	}

	private void setTraceBufferSize(int traceBufferSize) {
		this.traceBufferSize = traceBufferSize;
	}

	private int getTraceBuffer_NumberOfReadings_Notify() {
		return traceBuffer_NumberOfReadings_Notify;
	}

	private void setTraceBuffer_NumberOfReadings_Notify(int traceBuffer_NumberOfReadings_Notify) {
		if (traceBuffer_NumberOfReadings_Notify>=this.traceBufferSize) this.traceBuffer_NumberOfReadings_Notify = this.traceBufferSize-1;
		else this.traceBuffer_NumberOfReadings_Notify = traceBuffer_NumberOfReadings_Notify;
	}

	private String getTraceBufferTStampFormat() {
		return traceBufferTStampFormat;
	}

	private void setTraceBufferTStampFormat(String traceBufferTStampFormat) {
		this.traceBufferTStampFormat = traceBufferTStampFormat;
	}

	private String getTraceBufferReadingsSource() {
		return traceBufferReadingsSource;
	}

	private void setTraceBufferReadingsSource(String traceBufferReadingsSource) {
		this.traceBufferReadingsSource = traceBufferReadingsSource;
	}

	private String getTraceBufferControlMode() {
		return traceBufferControlMode;
	}

	private void setTraceBufferControlMode(String traceBufferControlMode) {
		this.traceBufferControlMode = traceBufferControlMode;
	}

	private boolean isDataBufferAutoclear() {
		return dataBufferAutoclear;
	}

	private void setDataBufferAutoclear(boolean dataBufferAutoclear) {
		this.dataBufferAutoclear = dataBufferAutoclear;
	}

	private int getDataBufferSize() {
		return dataBufferSize;
	}

	private void setDataBufferSize(int dataBufferSize) {
		this.dataBufferSize = dataBufferSize;
	}

	private int getDataBuffer_NumberOfReadings_Notify() {
		return dataBuffer_NumberOfReadings_Notify;
	}

	private void setDataBuffer_NumberOfReadings_Notify(int dataBuffer_NumberOfReadings_Notify) {
		if (dataBuffer_NumberOfReadings_Notify>=this.dataBufferSize) this.dataBuffer_NumberOfReadings_Notify = this.dataBufferSize-1;
		else this.dataBuffer_NumberOfReadings_Notify = dataBuffer_NumberOfReadings_Notify;
	}

	private String getDataBufferTStampFormat() {
		return dataBufferTStampFormat;
	}

	private void setDataBufferTStampFormat(String dataBufferTStampFormat) {
		this.dataBufferTStampFormat = dataBufferTStampFormat;
	}

	private String getDataBufferReadingsSource() {
		return dataBufferReadingsSource;
	}

	private void setDataBufferReadingsSource(String dataBufferReadingsSource) {
		this.dataBufferReadingsSource = dataBufferReadingsSource;
	}

	private String getDataBufferControlMode() {
		return dataBufferControlMode;
	}

	private void setDataBufferControlMode(String dataBufferControlMode) {
		this.dataBufferControlMode = dataBufferControlMode;
	}



}
