package source;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;


public class Source_Subsystem extends InstrumentComponent implements I_Source_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Source_Subsystem.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private boolean autoClear;
	private String 	autoClearMode;
	private String 	functionShape;
	private String  functionMode;
	private String 	currentMode;
	private String  voltageMode;
	private Object 	currentRange;
	private Object 	voltageRange;
	private boolean	currentRangeAuto;
	private boolean	voltageRangeAuto;
	private Object 	currentAmplitudeLevel;
	private Object 	voltageAmplitudeLevel;
	private Object 	voltageProtectionLevel;
	
	private Object 	delay;
	private boolean delayAuto;
	
	
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Output tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Source_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}
	
	public Source_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Source_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing Source Subsystem from jObj ... ");
		
		JSONObject sourceSubsystemConfiguration = (JSONObject) ((JSONObject)jObj.get("Configuration")).get("source");
		
		this.autoClear = ((String)sourceSubsystemConfiguration.get("auto_clear")).equals("ON");
		this.autoClearMode = (String)sourceSubsystemConfiguration.get("auto_clear_mode");
		
		JSONObject functionConfiguration = (JSONObject)sourceSubsystemConfiguration.get("function");
		
		this.functionShape = (String)functionConfiguration.get("shape");
		this.functionMode =(String)functionConfiguration.get("mode");
		this.voltageMode = (String)functionConfiguration.get("voltageMode");
		this.currentMode = (String)functionConfiguration.get("currentMode");
		this.voltageRange = (Object)functionConfiguration.get("voltageRange");
		this.currentRange = (Object)functionConfiguration.get("currentRange");
		this.voltageRangeAuto = ((String)functionConfiguration.get("voltageRangeAuto")).equals("ON");
		this.currentRangeAuto = ((String)functionConfiguration.get("currentRangeAuto")).equals("ON");
		this.voltageAmplitudeLevel = (Object)functionConfiguration.get("voltageLevel");
		this.currentAmplitudeLevel = (Object)functionConfiguration.get("currentLevel");
		this.voltageProtectionLevel = (Object)sourceSubsystemConfiguration.get("voltageprotection_limit");
		this.delay = (Object)sourceSubsystemConfiguration.get("delay");
		this.delayAuto = ((String)sourceSubsystemConfiguration.get("auto_delay")).equals("ON");
		
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
		
		logger.info("Initializing Source SubSystem ... ");
		
		this.communicationsInterface = i_CommunicationsInterface;
		
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Source SubSystem configuration to the instrument ... ");
		
		this.checkCommunicationsInterface();
		this.setAutoClear(this.autoClear);
		this.setAutoClearMode(this.autoClearMode);
		this.setFunctionShape(this.functionShape);
		this.setFunctionMode(this.functionMode);
		this.setVoltageMode(this.voltageMode);
		this.setCurrentMode(this.currentMode);
		this.setVoltageRange(this.voltageRange);
		this.setCurrentRange(this.currentRange);
		this.setVoltageAutoRange(this.voltageRangeAuto);
		this.setCurrentAutoRange(this.currentRangeAuto);
		this.setVoltageAmplitudeLevel(this.voltageAmplitudeLevel);
		this.setCurrentAmplitudeLevel(this.currentAmplitudeLevel);
		this.setVoltageProtectionLevel(this.voltageProtectionLevel);
		this.setDelay(this.delay);
		this.setAutoDelay(this.delayAuto);
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Source SubSystem configuration from the instrument ... ");
		
		this.checkCommunicationsInterface();
	}

	private void checkCommunicationsInterface() throws Exception
	{
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}

	/**
	 * 	This command is used to turn off the source output. The output will
		turn off after all programmed source-measure operations are completed
		and the instrument returns to the idle state.
		Note that if auto output-off is enabled, the source output will automatically
		turn off. (See setAutoClear.)
	 */
	@Override
	public void clear() throws Exception {
		this.checkCommunicationsInterface();
		this.communicationsInterface.write(":SOURce:CLEar:IMMediate");
	}

	/**
	 * 	This command is used to control auto output-off for the source. With
		auto output-off enabled, an :INITiate (or :READ? or MEASure?) will
		start source-measure operation. The output will turn on at the beginning
		of each SDM (source-delay-measure) cycle and turn off after
		each measurement is completed.
		<p>
		With auto output-off disabled, the source output must be on before
		an :INITiate or :READ? can be used to start source-measure operation.
		The :MEASure? command will automatically turn on the source
		output. Once operation is started, the source output will stay on even
		after the instrument returns to the idle state. Auto output-off disabled
		is the *RST and :SYSTem:PRESet default.
		<p>
		<b>WARNING</b>
		<p>
		 With auto output-off disabled, the source output will remain on
		after all programmed source-measure operations are completed.
		Beware of hazardous voltage that may be present on the output
		terminals.
		@param on or off the auto clear configuration
	 */
	@Override
	public void setAutoClear(boolean on_off) throws Exception {
		this.checkCommunicationsInterface();
		this.autoClear = on_off;
		this.communicationsInterface.write(":SOURce:CLEar:AUTO " + ((on_off)? "ON":"OFF"));
	}

	/**
	 * 	The source will turn off after every SDM
		cycle with the ALWAYS option. With the TCOunt option, the source
		will turn off when the trigger count has expired.
		@param mode that can be ALWAYS or TCOunt
	 */
	@Override
	public void setAutoClearMode(String mode) throws Exception {
		this.checkCommunicationsInterface();
		this.autoClearMode = mode;
		this.communicationsInterface.write(":SOURce:CLEar:AUTO:MODE " + mode);
	}


	/**
	 * SHAPe <name> (Model 2430 only)
	 * Description This command is used to select the output mode for the Model 2430.
	 * @param shape that indicates the DC Mode that can be Pulse Mode or DC
	 */
	@Override
	public void setFunctionShape(String shape) throws Exception {
		this.checkCommunicationsInterface();
		this.functionShape = shape;
		this.communicationsInterface.write(":SOURce:FUNCtion:SHAPe " + shape);
	}

	/**
		This command is used to select the source mode. With VOLTage
		selected, the V-Source will be used, and with CURRent selected, the
		I-Source will be used.
		With MEMory selected, a memory sweep can be performed. Operating
		setups (up to 100) saved in memory can be sequentially recalled.
		This allows multiple source/measure functions to be used in a
		sweep.
		
		@param mode that can be:
		<p> 
		<list>
		<li>'VOLTage' Select voltage mode
		<li>'CURRent' Select current mode
		<li>'MEMory'  Select memory mode
		</list>
		<p>
	 */
	@Override
	public void setFunctionMode(String mode) throws Exception {
		this.checkCommunicationsInterface();
		this.functionMode = mode;
		this.communicationsInterface.write(":SOURce:FUNCtion:MODE " + mode);
	}

	/**
	 * 	Description This command is used to select the DC sourcing mode for the specified current mode.
	 * <p>
		The three modes are explained as follows:
		<p>
		FIXed — In this DC sourcing mode, the specified source will output a
		fixed level. Use the :RANGe and :AMPLitude commands to specify
		the fixed source level. (See “Select range,” page 18-74, and “Set
		amplitude for fixed source,” page 18-77.)
		<p>
		LIST — In this mode, the source will output levels that are specified
		in a list. See “Configure list” for commands to define and control the
		execution of the list.
		<p>
		SWEep — In this mode, the source will perform a voltage, current or
		memory sweep. See “Configure voltage and current sweeps,”
		page 18-83, and “Configure memory sweep,” page 18-93, for commands
		to define the sweep.
		<p>
		NOTE The sourcing mode will default to FIXed whenever the SourceMeter goes to
		the local state.
		@param mode that can be:
		<list>
		<li>'FIXed' Select fixed sourcing mode
		<li>'LIST' Select list sourcing mode
		<li>'SWEep' Select sweep sourcing mode
		</list>			
	 */
	@Override
	public void setCurrentMode(String mode) throws Exception {
		this.checkCommunicationsInterface();
		this.currentMode = mode;
		this.communicationsInterface.write(":SOURce:CURRent:MODE " + mode);	
	}

	/**
	 * 	Description This command is used to select the DC sourcing mode for the specified voltage mode.
	 * <p>
		The three modes are explained as follows:
		<p>
		FIXed — In this DC sourcing mode, the specified source will output a
		fixed level. Use the :RANGe and :AMPLitude commands to specify
		the fixed source level. (See “Select range,” page 18-74, and “Set
		amplitude for fixed source,” page 18-77.)
		<p>
		LIST — In this mode, the source will output levels that are specified
		in a list. See “Configure list” for commands to define and control the
		execution of the list.
		<p>
		SWEep — In this mode, the source will perform a voltage, current or
		memory sweep. See “Configure voltage and current sweeps,”
		page 18-83, and “Configure memory sweep,” page 18-93, for commands
		to define the sweep.
		<p>
		NOTE The sourcing mode will default to FIXed whenever the SourceMeter goes to
		the local state.
		
		@param mode that can be:
		<list>
		<li>'FIXed' Select fixed sourcing mode
		<li>'LIST' Select list sourcing mode
		<li>'SWEep' Select sweep sourcing mode
		</list>			
	 */
	@Override
	public void setVoltageMode(String mode) throws Exception {
		this.checkCommunicationsInterface();
		this.voltageMode = mode;
		this.communicationsInterface.write(":SOURce:VOLTage:MODE " + mode);	
		
	}

	/**
	 * 	This command is used to manually select the range for the current source. 
	 * <p>
	 * Range is selected by specifying the approximate source
		magnitude that you will be using. The instrument will then go to the
		lowest range that can accommodate that level. 
		<p>
		For example, if you
		expect to source levels around 3V, send the following command:
		:SOURce:VOLTage:RANGe 3
		<p>
		The above command will select the 20V range for the V-Source.
		18-76 SCPI Command Reference 2400 Series SourceMeter® User’s Manual
		As listed in the “Parameters,” you can also use the MINimum,
		MAXimum and DEFault parameters to manually select the source
		range. The UP parameter selects the next higher source range, while
		DOWN selects the next lower source range.
		<p>
		Note that source range can be selected automatically by the instrument
		(see next command).
		<p>
		
		Next we will show the range params for a specific source_meters as example  
		
		<list>
			<li><b>2400/2400-LV/2401 ranges</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-210 to 210 Specify V-Source level volts (-21 to 21 for 2400-LV and 2401)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 1uA range (I-Source), 200mV range (V-Source) (20V range, 2400-LV and 2401)
					<li>MAXimum 1A range (I-Source), 200V range (V-Source) (20V,2400-LV and 2401)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		
			<li><b>2410 ranges</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-1100 to 1100 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 1uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 1A range (I-Source), 1100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>

			<li><b>2420 ranges</b>
				<ul>
					<li>-3.15 to 3.15 Specify I-Source level (amps)
					<li>-63 to 63 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 3A range (I-Source), 63V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
					<li>2400 Series SourceMeter® User’s Manual SCPI Command Reference 18-75
				</ul>
			</li>
		
			<li><b>2425 and 2430 in DC Mode ranges</b>
				<ul>
					<li>-3.15 to 3.15 Specify I-Source level (amps)
					<li>-105 to 105 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 3A range (I-Source), 100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
			
			<li><b>2430 in Pulse Mode ranges</b>
				<ul>
					<li>-10.5 to 10.5 Specify I-Source level (amps)
					<li>-105 to 105 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 10A range (I-Source), 100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		</list>
		

		<list>
			<li><b>2440 ranges</b>
				<ul>
					<li>-5.25 to 5.25 Specify I-Source level (amps)
					<li>-42 to 42 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 10V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 5A range (I-Source), 40V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		</list>
		
		</list>
		
		@param range that can be an String or a Float
		
	 */
	@Override
	public void setCurrentRange(Object range) throws Exception {
		this.checkCommunicationsInterface();
		this.currentRange = range;
		this.communicationsInterface.write(":SOURce:CURRent:RANGe " + range.toString());	
	}

	/**
	 * 	This command is used to manually select the range for the current source. 
	 * <p>
	 * Range is selected by specifying the approximate source
		magnitude that you will be using. The instrument will then go to the
		lowest range that can accommodate that level. 
		<p>
		For example, if you
		expect to source levels around 3V, send the following command:
		:SOURce:VOLTage:RANGe 3
		<p>
		The above command will select the 20V range for the V-Source.
		18-76 SCPI Command Reference 2400 Series SourceMeter® User’s Manual
		As listed in the “Parameters,” you can also use the MINimum,
		MAXimum and DEFault parameters to manually select the source
		range. The UP parameter selects the next higher source range, while
		DOWN selects the next lower source range.
		<p>
		Note that source range can be selected automatically by the instrument
		(see next command).
		<p>
		
		Next we will show the range params for a specific source_meters as example  
		
		<list>
			<li><b>2400/2400-LV/2401 ranges</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-210 to 210 Specify V-Source level volts (-21 to 21 for 2400-LV and 2401)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 1uA range (I-Source), 200mV range (V-Source) (20V range, 2400-LV and 2401)
					<li>MAXimum 1A range (I-Source), 200V range (V-Source) (20V,2400-LV and 2401)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		
			<li><b>2410 ranges</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-1100 to 1100 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 1uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 1A range (I-Source), 1100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>

			<li><b>2420 ranges</b>
				<ul>
					<li>-3.15 to 3.15 Specify I-Source level (amps)
					<li>-63 to 63 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 3A range (I-Source), 63V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
					<li>2400 Series SourceMeter® User’s Manual SCPI Command Reference 18-75
				</ul>
			</li>
		
			<li><b>2425 and 2430 in DC Mode ranges</b>
				<ul>
					<li>-3.15 to 3.15 Specify I-Source level (amps)
					<li>-105 to 105 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 3A range (I-Source), 100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
			
			<li><b>2430 in Pulse Mode ranges</b>
				<ul>
					<li>-10.5 to 10.5 Specify I-Source level (amps)
					<li>-105 to 105 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 20V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 10A range (I-Source), 100V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		</list>
		

		<list>
			<li><b>2440 ranges</b>
				<ul>
					<li>-5.25 to 5.25 Specify I-Source level (amps)
					<li>-42 to 42 Specify V-Source level (volts)
					<li>DEFault 100uA range (I-Source), 10V range (V-Source)
					<li>MINimum 10uA range (I-Source), 200mV range (V-Source)
					<li>MAXimum 5A range (I-Source), 40V range (V-Source)
					<li>UP Select next higher range
					<li>DOWN Select next lower range
				</ul>
			</li>
		</list>
		
		</list>
		
		@param range that can be an String or a Float
		
	 */
	@Override
	public void setVoltageRange(Object range) throws Exception {
		this.checkCommunicationsInterface();
		this.voltageRange = range;
		this.communicationsInterface.write(":SOURce:VOLTage:RANGe " + range.toString());	
	}

	/**
	 * 	This command is used to enable or disable auto range for the current source. 
	 * <p>
	 * When enabled, the instrument will automatically select
		the most sensitive range for the specified source level. When disabled,
		the instrument will use the range that the instrument is currently
		on.
		<p>
		Auto range will be disabled if a fixed range is selected (see previous
		command).
		<p>
		Both *RST and :SYSTem:PREset enables source auto range. When
		the SourceMeter goes into the local state, source auto range disables.
		
		@param enable (true or false) the current auto range configuration
	 */
	@Override
	public void setCurrentAutoRange(boolean enable) throws Exception {
		this.checkCommunicationsInterface();
		this.autoClear = enable;
		this.communicationsInterface.write(":SOURce:CURRent:RANGe:AUTO " + ((enable)? "ON":"OFF"));	
	}

	/**
	 * 	This command is used to enable or disable auto range for the voltage source. 
	 * <p>
	 * When enabled, the instrument will automatically select
		the most sensitive range for the specified source level. When disabled,
		the instrument will use the range that the instrument is currently
		on.
		<p>
		Auto range will be disabled if a fixed range is selected (see previous
		command).
		<p>
		Both *RST and :SYSTem:PREset enables source auto range. When
		the SourceMeter goes into the local state, source auto range disables.
		
		@param enable (true or false) the current auto range configuration
	 */
	@Override
	public void setVoltageAutoRange(boolean enable) throws Exception {
		this.checkCommunicationsInterface();
		this.autoClear = enable;
		this.communicationsInterface.write(":SOURce:VOLTage:RANGe:AUTO " + ((enable)? "ON":"OFF"));	
	}


	/**
	
	 * 	This command is used to select a fixed source. (See “Select sourcing mode,” page 18-73.) 
	 * <p>
		If a manual source range is presently selected, then the specified
		amplitude cannot exceed that range.  In auto range, the amplitude can be set to
		any level that is within the capabilities of the source.
		<p>
		For example, if the V-Source is
		on the 2V range (auto range disabled), you will not be able to set the
		V-Source amplitude to 3V.
		<p>
		The MINimum and MAXimum parameters are only valid if the highest
		source range is presently selected. Sending the MINimum or MAXimum
		parameters on a lower source range will generate error -221
		(Setting Conflict).
		<p>
		
		Next we will show the amplitude params for a specific source_meters as example  
		
		<list>
			<li><b>2400/2400-LV/2401 amplitudes</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-210 to 210 Specify V-Source level volts (-21 to 21 for 2400-LV and 2401)
					<li>DEFault 0A or 0V
					<li>MINimum -1.05A or -210V
					<li>MAXimum +1.05A or +210V (+21V, 2400-LVand 2401)
				</ul>
			</li>
		
			<li><b>2410 amplitudes</b>
				<ul>
					<li>-1.05 to 1.05 Set I-Source amplitude (amps)
					<li>-1100 to 1100 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -1.05A or -1100V
					<li>MAXimum +1.05A or +1100V
				</ul>
			</li>

			<li><b>2420 amplitudes</b>
				<ul>
					<li>-3.15 to 3.15 Set I-Source amplitude (amps)
					<li>-63 to 63 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -3.15A or -63V
					<li>MAXimum +3.15A or +63V
				</ul>
			</li>
		
			<li><b>2425 and 2430 in DC Mode amplitudes</b>
				<ul>
					<li>-3.15 to 3.15 Set I-Source amplitude (amps)
					<li>-105 to 105 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -3.15A or -105V
					<li>MAXimum +3.15A or +105V	
				</ul>
			</li>
		
			<li><b>2430 in Pulse Mode amplitudes</b>
				<ul>
					<li>-10.5 to 10.5 Set I-Source amplitude (amps)
					<li>-105 to 105 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -10.5A or -105V
					<li>MAXimum +10.5A or +105V
				</ul>
			</li>
		</list>
		
		<list>
			<li><b>2440 amplitudes</b>
				<ul>
					<li>-5.25 to 5.25 Set I-Source amplitude (amps)
					<li>-42 to 42 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -5.25A or -42V
					<li>MAXimum +5.25A or +42V	
				</ul>
			</li>
		</list>
		
		</list>
	
		@param amplitude that can be an String or a Float
		
	 */
	@Override
	public void setCurrentAmplitudeLevel(Object amplitude) throws Exception {
		this.checkCommunicationsInterface();
		this.currentAmplitudeLevel = amplitude;
		this.communicationsInterface.write(":SOURce:CURRent:LEVel:IMMediate:AMPLitude " + amplitude.toString());	
	}

	/**
	
	 * 	This command is used to select a fixed source. (See “Select sourcing mode,” page 18-73.) 
	 * <p>
		If a manual source range is presently selected, then the specified
		amplitude cannot exceed that range.  In auto range, the amplitude can be set to
		any level that is within the capabilities of the source.
		<p>
		For example, if the V-Source is
		on the 2V range (auto range disabled), you will not be able to set the
		V-Source amplitude to 3V.
		<p>
		The MINimum and MAXimum parameters are only valid if the highest
		source range is presently selected. Sending the MINimum or MAXimum
		parameters on a lower source range will generate error -221
		(Setting Conflict).
		<p>
		
		Next we will show the amplitude params for a specific source_meters as example  
		
		<list>
			<li><b>2400/2400-LV/2401 amplitudes</b>
				<ul>
					<li>-1.05 to 1.05 Specify I-Source level (amps)
					<li>-210 to 210 Specify V-Source level volts (-21 to 21 for 2400-LV and 2401)
					<li>DEFault 0A or 0V
					<li>MINimum -1.05A or -210V
					<li>MAXimum +1.05A or +210V (+21V, 2400-LVand 2401)
				</ul>
			</li>
		
			<li><b>2410 amplitudes</b>
				<ul>
					<li>-1.05 to 1.05 Set I-Source amplitude (amps)
					<li>-1100 to 1100 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -1.05A or -1100V
					<li>MAXimum +1.05A or +1100V
				</ul>
			</li>

			<li><b>2420 amplitudes</b>
				<ul>
					<li>-3.15 to 3.15 Set I-Source amplitude (amps)
					<li>-63 to 63 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -3.15A or -63V
					<li>MAXimum +3.15A or +63V
				</ul>
			</li>
		
			<li><b>2425 and 2430 in DC Mode amplitudes</b>
				<ul>
					<li>-3.15 to 3.15 Set I-Source amplitude (amps)
					<li>-105 to 105 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -3.15A or -105V
					<li>MAXimum +3.15A or +105V	
				</ul>
			</li>
		
			<li><b>2430 in Pulse Mode amplitudes</b>
				<ul>
					<li>-10.5 to 10.5 Set I-Source amplitude (amps)
					<li>-105 to 105 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -10.5A or -105V
					<li>MAXimum +10.5A or +105V
				</ul>
			</li>
		</list>
		
		<list>
			<li><b>2440 amplitudes</b>
				<ul>
					<li>-5.25 to 5.25 Set I-Source amplitude (amps)
					<li>-42 to 42 Set V-Source amplitude (volts)
					<li>DEFault 0A or 0V
					<li>MINimum -5.25A or -42V
					<li>MAXimum +5.25A or +42V	
				</ul>
			</li>
		</list>
		
		</list>
	
		@param amplitude that can be an String or a Float
		
	 */
	@Override
	public void setVoltageAmplitudeLevel(Object amplitude) throws Exception {
		this.checkCommunicationsInterface();
		this.voltageAmplitudeLevel = amplitude;
		this.communicationsInterface.write(":SOURce:VOLTage:LEVel:IMMediate:AMPLitude " + amplitude.toString());	
	}

	/**
	 * This command is used to set the Over Voltage Protection (OVP) limit for the V-Source. 
	 * <p>
	 * The V-Source output will not exceed the selected limit. An exception to this is a parameter value that exceeds 160V for the Model 2400, 
	 * 500V for the Model 2410, 48V for the Model 2420, 80V for the Models 2425 and 2430, or 32V for the Model 2440.
	 * Exceeding those values allows the V-Source to output its maximum voltage. The OVP limit is also enforced when in the I-Source Mode.
	 * The limit parameter values are magnitudes and are in effect for both positive and negative output voltage. You can express the limit as a
	 * positive or negative value. If you specify a value that is less than the lowest limit, the lowest limit will be selected. 
	 * If you specify a value that is between limits, the lower limit will be selected. For example, if you specify a value of 110 
	 * for the Model 2400, the 100V limit will be selected.
	 * <p>
	 * Next we will show the limit params for a specific source_meters as example  
	 * <p>
	 *	<list>
		<li><b>2400</b>
			<ul>
				<li>-210 to 210 Specify V-Source limit
				<li>20 Set limit to 20V
				<li>40 Set limit to 40V
				<li>60 Set limit to 60V
				<li>80 Set limit to 80V
				<li>100 Set limit to 100V
				<li>120 Set limit to 120V
				<li>160 Set limit to 160V
				<li>161 to 210 Set limit to 210V (NONE)
				<li>DEFault Set limit to 210V (NONE)
				<li>MINimum Set limit to 20V
				<li>MAXimum Set limit to 210V (NONE)
			</ul>
		</li>
		<li><b>2401</b>
			<ul>
				<li>-40 to 40 Specify V-Source limit
				<li>20 Set limit to 20V
				<li>DEFault Set limit to 40V (NONE)
				<li>MINimum Set limit to 20V
				<li>MAXimum Set limit to 40V (NONE)
			</ul>
		</li>
		<li><b>2410</b>
			<ul>
				<li>-1100 to 1100 Specify V-Source limit
				<li>20 Set limit to 20V
				<li>40 Set limit to 40V
				<li>100 Set limit to 100V
				<li>200 Set limit to 200V
				<li>300 Set limit to 300V
				<li>400 Set limit to 400V
				<li>500 Set limit to 500V
				<li>501 to 1100 Set limit to NONE
				<li>DEFault Set limit to 1100V (NONE)
				<li>MINimum Set limit to 20V
				<li>MAXimum Set limit to 1100V (NONE)
			</ul>
		</li>
		<li><b>2420</b>
			<ul>
				<li>-63 to 63 Specify V-Source limit
				<li>6 Set limit to 6V
				<li>12 Set limit to 12V
				<li>18 Set limit to 18V
				<li>24 Set limit to 24V
				<li>30 Set limit to 30V
				<li>36 Set limit to 36V
				<li>48 Set limit to 48V
				<li>49 to 63 Set limit to NONE
				<li>DEFault Set limit to 63V (NONE)
				<li>MINimum Set limit to 6V
				<li>MAXimum Set limit to 63V (NONE)	
			</ul>
		</li>
		<li><b>2425 and 2430</b>
			<ul>
				<li>-105 to 105 Specify V-Source limit
				<li>10 Set limit to 20V
				<li>20 Set limit to 20V
				<li>40 Set limit to 40V
				<li>60 Set limit to 60V
				<li>80 Set limit to 80V
				<li>81 to 105 Set limit to NONE
				<li>DEFault Set limit to 105V (NONE)
				<li>MINimum Set limit to 10V
				<li>MAXimum Set limit to 105V (NONE)
			</ul>
		</li>
		<li><b>2440</b>
			<ul>
				<li>-42 to 42 Specify V-Source limit
				<li>4 Set limit to 4V
				<li>8 Set limit to 8V
				<li>12 Set limit to 12V
				<li>16 Set limit to 16V
				<li>20 Set limit to 20V
				<li>24 Set limit to 24V
				<li>32 Set limit to 32V
				<li>33 to 42 Set limit to NONE
				<li>DEFault Set limit to 42V (NONE)
				<li>MINimum Set limit to 4V
				<li>MAXimum Set limit to 42V (NONE)
			</ul>
		</li>
	</list>		
	
	@param limit that can be an String or a Float
	 */
	@Override
	public void setVoltageProtectionLevel(Object limit) throws Exception {
		this.checkCommunicationsInterface();
		this.voltageProtectionLevel = limit;
		this.communicationsInterface.write(":SOURce:VOLTage:PROTection:LEVel " + limit.toString());	
	}
	
	/**
	 *  This command is used to manually set a delay (settling time) for the source. 
	 *  <p>
	 *  After the programmed source is turned on, this delay occurs
		to allow the source level to settle before a measurement is taken.
		Note that this delay is the same for both the I-Source and V-Source.
		Do not confuse this source delay with the trigger delay. The source
		delay is part of the device action (SDM cycle) while the trigger delay
		occurs before the device action. See Section 11, “Trigger models,” for more information.
		<p>
		Auto delay can instead be used to automatically set the source delay. (See next command.)
		
		@param delay that can be a Float or an String:
		<list>
		<li>0 to 999.9999 Specify delay in seconds
		<li>'MINimum' 0 seconds
		<li>'MAXimum' 999.9999 seconds
		<li>'DEFault' 0 seconds
		</list>
	 */
	@Override
	public void setDelay(Object delay) throws Exception {
		this.checkCommunicationsInterface();
		this.delay = delay;
		this.communicationsInterface.write(":SOURce:DELay " + delay.toString());			
	}

	/**
	 * 	This command is used to enable or disable auto delay.
	 * <p> 
	 * When enabled, the instrument will automatically select a delay period that
		is appropriate for the present source/measure setup configuration
		(Table 3-4). *RST and SYST:PRES default is ON.
		
		@param enable the autodelay if it is true, disable otherwise.
	 */
	@Override
	public void setAutoDelay(boolean enable) throws Exception {
		this.checkCommunicationsInterface();
		this.delayAuto = enable;
		this.communicationsInterface.write(":SOURce:DELay:AUTO " + ((enable)? "ON":"OFF"));
	}

	
}
