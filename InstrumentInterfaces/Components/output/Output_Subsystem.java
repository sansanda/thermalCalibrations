package output;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;


public class Output_Subsystem extends InstrumentComponent implements I_Output_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Output_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private boolean outputOn 		= false;
	private boolean outputEnable 	= false;
	private String  outputMode 		= "NORMal";
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Output tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Output_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	public Output_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Output_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing Output Subsystem from jObj ... ");
		
		JSONObject outputSubsystemConfiguration = (JSONObject)jObj.get("Configuration");

		this.outputMode = (String)outputSubsystemConfiguration.get("output_mode");
		this.outputEnable = (outputSubsystemConfiguration.get("output_enable").equals("ON"));
		this.outputOn = (outputSubsystemConfiguration.get("outputOn").equals("ON"));
		
		
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
		
		logger.info("Initializing Output SubSystem ... ");
		
		this.communicationsInterface = i_CommunicationsInterface;
		
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Output SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		this.configureOutputMode(this.outputMode);
		this.configureOutputEnable(this.outputEnable);
		this.configureOutput(this.outputOn);
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Output SubSystem configuration from the instrument ... ");
		
		this.checkCommunicationsInterface();
	}

	private void checkCommunicationsInterface() throws Exception
	{
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}
	
	private void configureOutput(boolean on) throws Exception {
		this.checkCommunicationsInterface();
		logger.info("Configuring output to ... " + ((on)? "ON":"OFF"));
		this.communicationsInterface.write(":OUTPut:STATe " + ((on)? "ON":"OFF"));
	}

	private void configureOutputEnable(boolean enable) throws Exception {
		this.checkCommunicationsInterface();
		logger.info("Configuring output enable to ... " + ((enable)? "ON":"OFF"));
		this.communicationsInterface.write(":OUTPut:ENABle:STATe " + ((enable)? "ON":"OFF"));
	}

	private void configureOutputMode(String outputMode) throws Exception {
		this.checkCommunicationsInterface();
		logger.info("Configuring output mode to ... " + outputMode);
		this.communicationsInterface.write(":OUTPut:SMODe " + outputMode);
	}

	private boolean queryOutputEnableSate() throws Exception {
		this.checkCommunicationsInterface();
		return (new String(this.communicationsInterface.ask("OUTPut:ENABle:STATe?"))).equals("1");
	}

	private boolean queryOutputSate() throws Exception {
		this.checkCommunicationsInterface();
		return (new String(this.communicationsInterface.ask("OUTPut:STATe?"))).equals("1");
	}

	private String queryOutputMode() throws Exception {
		this.checkCommunicationsInterface();
		return new String(this.communicationsInterface.ask("OUTPut:SMODe?"));
	}
	
	@Override
	public boolean isOutputOn() throws Exception {
		this.outputOn = this.queryOutputSate();
		return this.outputOn;
	}

	@Override
	public void setOutputOn(boolean outputOn) throws Exception {
		this.configureOutput(outputOn);
		this.outputOn = outputOn;
	}

	@Override
	public boolean isOutputEnable() throws Exception {
		this.outputEnable = this.queryOutputEnableSate();
		return this.outputEnable;
	}

	@Override
	public void setOutputEnable(boolean outputEnable) throws Exception {
		this.configureOutputEnable(outputEnable);
		this.outputEnable = outputEnable;
	}

	@Override
	public String getOutputMode() throws Exception {
		this.outputMode = this.queryOutputMode();
		return this.outputMode;
	}

	@Override
	public void setOutputMode(String outputMode) throws Exception {
		this.configureOutputMode(outputMode);
		this.outputMode = outputMode;
		
	}	
}
