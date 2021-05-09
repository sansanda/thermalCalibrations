package trigger;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import information.GeneralInformation_Component;

public class Trigger_Subsystem extends InstrumentComponent implements I_Trigger_Subsystem {

	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Trigger_Subsystem.class);
	
	public static int 		TRIGGER_COUNT_MIN; 	//= 1;
	public static int 		TRIGGER_COUNT_MAX; 	//= 55000;
	public static double 	TRIGGER_DELAY_MIN; //= 0;
	public static double 	TRIGGER_DELAY_MAX;	//= 999999.999;
	public static double 	TRIGGER_TIMER_MIN;	//= 0.001;
	public static double 	TRIGGER_TIMER_MAX;	//= 999999.999;
	public static int 		SAMPLE_COUNT_MIN;	//= 1;
	public static int 		SAMPLE_COUNT_MAX;	//= 55000;
	
	private static String[] CONTROL_SOURCE_VALID_VALUES; //IMMediate, TIMer, MANual, BUS, or EXTernal.
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	//Variable para el acceso al modulo de comunicaciones del multimetro.
	//Será necesario para permitir a Trigger tomar el control de dicho modulo
	//Y envíar de manera autónoma comandos y recibir respuestas
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
			
	public Trigger_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
	}

	public Trigger_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static Trigger_Subsystem parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
	
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return Trigger_Subsystem.parseFromJSON(jObj);
		 
	 }
	 
	public static Trigger_Subsystem parseFromJSON(JSONObject jObj) throws Exception
	{
		logger.info("Parsing Trigger_Subsystem from jObj ... ");
		
		
		///////////////////////////////////////temporal
		
		TRIGGER_COUNT_MIN = 1;
		TRIGGER_COUNT_MAX = 55000;
		TRIGGER_DELAY_MIN = 0d;
		TRIGGER_DELAY_MAX = 999999.999d;
		TRIGGER_TIMER_MIN = 0.001d;
		TRIGGER_TIMER_MAX = 999999.999d;
		SAMPLE_COUNT_MIN = 1;
		SAMPLE_COUNT_MAX = 55000;
		
		CONTROL_SOURCE_VALID_VALUES = new String[]{"IMMediate", "TIMer", "MANual", "BUS", "EXTernal","IMM", "TIM", "MAN", "BUS", "EXT" };
		
		///////////////////////////////////////////////
		
		
		Set keySet = jObj.keySet();
		
		Trigger_Subsystem trigger_subsystem = null;
		
		String name = "";
		Long id = 0l;
		InstrumentComponent parent = null;
		boolean enable = true;
		boolean selected = true;
		GeneralInformation_Component generalInformation = null;
		
		if (keySet.contains("name")) name = (String)jObj.get("name");
		if (keySet.contains("id")) id = (Long)jObj.get("id");
		//if (keySet.contains("parent")) parent = (InstrumentComponent)jObj.get("parent"); not implemented for the moment
		if (keySet.contains("enable")) enable = (boolean)jObj.get("enable");
		if (keySet.contains("selected")) selected = (boolean)jObj.get("selected");
		
		trigger_subsystem = new Trigger_Subsystem(
				 name, 
				 id, 
				 parent,
				 enable,
				 selected
			);
		
		if (keySet.contains("GeneralInformation")) {
			generalInformation = GeneralInformation_Component.parseFromJSON((JSONObject)jObj.get("GeneralInformation"));
			trigger_subsystem.addSubComponent(generalInformation);
		}
		
		return trigger_subsystem;
			
		
	 }
	
	public static int getClassversion() {
		return classVersion;
	}
	

	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************

	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Trigger SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Trigger SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Trigger SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
	}
	
	public void abortTriggerCycle() throws Exception
	{
		logger.info("Aborting Trigger ... ");
		this.communicationsInterface.write("ABORt");
	}
	
	public void initOneTriggerCycle() throws Exception
	{
		logger.info("Initiating one trigger cycle... ");
		this.communicationsInterface.write("INITiate:IMMediate");
	}
	
	public void configureInitContinousInitiation(boolean enable) throws Exception
	{
		String message = (enable? "Enabling init continous initiation...": "Disabling init continous initiation... ");
		String command = (enable? "INITiate:CONTinuous 1": "INITiate:CONTinuous 0");
		logger.info(message);
		this.communicationsInterface.write(command);
	}
	public boolean isInitContinousInitiationEnable() throws Exception
	{
		boolean enable = false;
		logger.info("Asking the instrument if Init Continous Initiation is enable...");
		if (new String(this.communicationsInterface.ask("INITiate:CONTinuous?")).equals("1")) enable = true;
		return enable;
	}
	
	/**
	 * Configura el número de veces (trigger count) que se va a ejecutar un ciclo de trigger 
	 * @param count es un entero que contiene el trigger count y suele ser un numero que va desde un valor minimo igual a 1 hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface. Eventualmente suele reservarse cualquier valor fuera de dicho rango para indicar
	 * un trigger count infinito (INF).
	 * @throws Exception
	 */
	public void configureTriggerCount(int count) throws Exception
	{
		logger.info("Configuring Trigger Count to " + count);
		if (count<TRIGGER_COUNT_MIN || count>TRIGGER_COUNT_MAX) this.communicationsInterface.write("TRIGger:COUNt INF");
		else this.communicationsInterface.write("TRIGger:COUNt " + count);
	}
	
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del trigger count.
	 * @return int con la configuracion del trigger count en el instrumento. Normalmente la query responde con un valor dentro del rango
	 * definido como valido en el instrumento para el triggerCount. Cualquier valor devuelto fuera de dicho rango será interpretado como INF. 
	 * @throws Exception
	 */
	public int queryTriggerCount() throws Exception
	{
		logger.info("Asking the instrument for the value of the trigger count...");
		
		int count;
		
		try {
			count = Integer.parseInt(new String(this.communicationsInterface.ask("TRIGger:COUNt?")));
		} catch (java.lang.NumberFormatException e) {
			//Cuando TRIGger:COUNt está configurado como INF en el instrumento, este response con un valor igual a +9.9e37 y no un entero.
			//Esta situación hay que tratarla y por lo tanto devolvemos el máximo valor posible para un integer 
			count = Integer.MAX_VALUE;
		}
		
		return count;
	}
	
	/**
	 * Configura el delay dentro del modelo de trigger del instrumento.
	 * @param delay real dentro del rango permitido por el instrumento. Habitualmente toma valores desde 0 sec hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface. Eventualmente suele reservarse cualquier valor fuera de dicho rango para indicar autodelay.
	 * @throws Exception 
	 */
	public void configureTriggerDelay(double delay) throws Exception
	{
		logger.info("Configuring Trigger Delay to " + delay);
		if (delay<TRIGGER_DELAY_MIN || delay>TRIGGER_DELAY_MAX) this.communicationsInterface.write("TRIGger:DELay:AUTO ON");
		else 
		{
			this.communicationsInterface.write("TRIGger:DELay " + Double.toString(delay)); //"999999.999"
		}
	}
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del trigger delay.
	 * @return double con la configuracion del trigger delay en el instrumento. Si el valor es 0 se da por hecho que el trigger delay está en modo auto.
	 * @throws Exception
	 */
	public double queryTriggerDelay() throws Exception
	{
		return Double.parseDouble(new String(this.communicationsInterface.ask("TRIGger:DELay?")));
		
	}
	public boolean isTriggerDelayInAutoMode() throws Exception
	{
		
		logger.info("Asking the auto configuration of the trigger delay...");
		
		boolean isInAutoMode = false;
		String response = new String(this.communicationsInterface.ask("TRIGger:DELay:AUTO?"));
		isInAutoMode = response.equals("1");
		
		return isInAutoMode;
	}
	
	
	
	/**
	 * Configura el la fuente que dispara el trigger y hace avanzar al instrumento hacia el siguiente paso dentro del modelo.
	 * @param source String que solo puede tomar uno de los valores los cuales suelen ser: IMMediate, TIMer MANual, BUS o EXTernal
	 * @throws Exception 
	 */
	public void configureTriggerSource(String source) throws Exception
	{
		logger.info("Configuring trigger source...");
		//check if source is contained in the CONTROL_SOURCE_VALID_VALUES
		boolean contains = Arrays.stream(CONTROL_SOURCE_VALID_VALUES).anyMatch(source::equals);
		if (!contains) throw new Exception("Not valid value for source. You give: " + source);
		this.communicationsInterface.write("TRIGger:SOURce " + source);
	}
	
	public String queryTriggerSource() throws Exception
	{
		logger.info("Asking the instrument for the value of the trigger source...");
		return new String(this.communicationsInterface.ask("TRIGger:SOURce?"));
	}
	
	/**
	 * Configura el timer del trigger para que, en caso de una configuracion trigger source como TIMer, disparar el trigger cada
	 * un determinado tiempo indicado por el parámetro de entrada. Si el valor time del parámetro de entrada está fuera del rango
	 * permitido se configurará el timer al minimo.
	 * @param time real dentro del rango permitido por el instrumento. Habitualmente toma valores desde 0.001 sec hasta un máximo 
	 * @throws Exception 
	 */
	public void configureTriggerTimer(double time) throws Exception
	{
		logger.info("Configuring Trigger timer to " + time);
		if (time<TRIGGER_TIMER_MIN || time>TRIGGER_TIMER_MAX) this.communicationsInterface.write("TRIGger:TIMer " + Double.toString(TRIGGER_TIMER_MIN));
		else 
		{
			this.communicationsInterface.write("TRIGger:TIMer " + Double.toString(time));
		}
	}
	
	public double queryTriggerTimer() throws Exception
	{
		logger.info("Asking the instrument for the value of the trigger timer...");
		return Double.parseDouble(new String(this.communicationsInterface.ask("TRIGger:TIMer?")));
	}
	
	/**
	 * Envia el comando signal al instrumento para "bypassear" la etapa de espera de señal procedente de la fuente de disparo del trigger
	 * lo que provoca que el instrumento continue hacia el siguiente paso dentro del modelo de trigger.
	 * @throws Exception 
	 */
	public void sendTriggerSignal() throws Exception
	{
		logger.info("Sending the trigger signal to the instrument...");
		this.communicationsInterface.write("TRIGger:SIGNal");
	}
	
	/**
	 * Configura el número de veces (sample count) que se va a ejecutar una medida dentro del mismo ciclo de trigger.
	 * Por ejemplo, cuando se ha configurado un scan y este está activo entonces el valor de sample count suele ser igual 
	 * al número de canales definidos en dicho scan. 
	 * @param count es un entero que contiene el sample count y suele ser un numero que va desde un valor minimo igual a 1 hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface. Si el parámetro count de entrada no se ecuentra dentro de
	 * dicho rango, el sample count del instrumento se configurará al mínimo por defecto.
	 * @throws Exception
	 */
	public void configureSampleCount(int count) throws Exception
	{
		logger.info("Configuring sample count to " + count);
		if (count<SAMPLE_COUNT_MIN || count>SAMPLE_COUNT_MAX) this.communicationsInterface.write("SAMPle:COUNt " + SAMPLE_COUNT_MIN);
		else 
		{
			this.communicationsInterface.write("SAMPle:COUNt " + count);
		}
	}
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del sample count.
	 * @return int con la configuracion del sample count en el instrumento
	 * @throws Exception
	 */
	public int querySampleCount() throws Exception
	{
		return Integer.parseInt(new String(this.communicationsInterface.ask("SAMPle:COUNt?")));
	}
	
	
}