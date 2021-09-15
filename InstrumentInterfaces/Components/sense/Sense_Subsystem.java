package sense;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;
import expansion_cards.MeasureChannels_DifferentialMultiplexer;
import multimeters.Multimeter;
import sense.senseFunctions.I_SenseFunction_Configuration;
import sense.senseFunctions.SenseFunctions_Factory;


public class Sense_Subsystem extends InstrumentComponent implements I_Sense_Subsystem {

	//TODO: integrar Expansion slots o multiplexer (exapnsion cards) para verificar channel available
	
	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Sense_Subsystem.class);
	
	////////////////////////////////////FUNCTION CONSTANTS
	public final static String FUNCTION_VOLTAGE_DC = 				"VOLTage:DC";
	public final static String FUNCTION_VOLTAGE_AC = 				"VOLTage:AC";
	public final static String FUNCTION_CURRENT_DC = 				"CURRent:DC";
	public final static String FUNCTION_CURRENT_AC = 				"CURRent:AC";
	public final static String FUNCTION_RESISTANCE = 				"RESistance";
	public final static String FUNCTION_FOUR_WIRE_RESISTANCE = 		"FRESistance";
	public final static String FUNCTION_FREQUENCY = 				"FREQuency";
	public final static String FUNCTION_PERIOD = 					"PERiod";
	public final static String FUNCTION_TEMPERATURE = 				"TEMPerature";
	public final static String FUNCTION_CONTINUITY = 				"CONTinuity";
	
	public final static String[] AVAILABLE_FUNCTIONS_SET =
	{
		FUNCTION_VOLTAGE_DC,
		FUNCTION_VOLTAGE_AC,
		FUNCTION_CURRENT_DC,
		FUNCTION_CURRENT_AC,
		FUNCTION_RESISTANCE,
		FUNCTION_FOUR_WIRE_RESISTANCE,
		FUNCTION_FREQUENCY,
		FUNCTION_PERIOD,
		FUNCTION_TEMPERATURE,
		FUNCTION_CONTINUITY
	};
	
	
	public final static String TCOUPLE_TEMPERATURE_TRANSDUCER 		= "TCouple";
	public final static String FRTD_TEMPERATURE_TRANSDUCER 			= "FRTD";
	public final static String THERMISTOR_TEMPERATURE_TRANSDUCER 	= "THERmistor";
	
	public final static String[] AVAILABLE_TEMPERATURE_TRANSDUCERS =
	{
		TCOUPLE_TEMPERATURE_TRANSDUCER,
		FRTD_TEMPERATURE_TRANSDUCER,
		THERMISTOR_TEMPERATURE_TRANSDUCER
	};
	
	public final static String THERMISTOR_RESISTIVE_TYPE_TRANSDUCER = "resistive";
	
	public final static String TCOUPLE_J_TYPE_TRANSDUCER 		= "J";
	public final static String TCOUPLE_K_TYPE_TRANSDUCER 		= "K";
	public final static String TCOUPLE_T_TYPE_TRANSDUCER 		= "T";
	public final static String TCOUPLE_E_TYPE_TRANSDUCER 		= "E";
	public final static String TCOUPLE_R_TYPE_TRANSDUCER 		= "R";
	public final static String TCOUPLE_S_TYPE_TRANSDUCER 		= "S";
	public final static String TCOUPLE_B_TYPE_TRANSDUCER 		= "B";
	public final static String TCOUPLE_N_TYPE_TRANSDUCER 		= "N";
	
	public final static String[] AVAILABLE_TCOUPLE_TRANSDUCER_TYPES =
	{
		TCOUPLE_J_TYPE_TRANSDUCER,
		TCOUPLE_K_TYPE_TRANSDUCER,
		TCOUPLE_T_TYPE_TRANSDUCER,
		TCOUPLE_E_TYPE_TRANSDUCER,
		TCOUPLE_R_TYPE_TRANSDUCER,
		TCOUPLE_S_TYPE_TRANSDUCER,
		TCOUPLE_B_TYPE_TRANSDUCER,
		TCOUPLE_N_TYPE_TRANSDUCER
	};
	
	public final static String TCOUPLE_SIMULATED_REFERENCE_JUNCTION_TYPE = "SIMulated";
	public final static String TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE = "INTernal";
	public final static String TCOUPLE_EXTERNAL_REFERENCE_JUNCTION_TYPE = "EXTernal";
	
	public final static String[] TCOUPLE_REFERENCE_JUNCTION_TYPES = 
	{
		TCOUPLE_SIMULATED_REFERENCE_JUNCTION_TYPE,
		TCOUPLE_INTERNAL_REFERENCE_JUNCTION_TYPE,
		TCOUPLE_EXTERNAL_REFERENCE_JUNCTION_TYPE
	};
	
	public final static String FRTD_PT100_TYPE_TRANSDUCER 		= "PT100";
	public final static String FRTD_D100_TYPE_TRANSDUCER 		= "D100";
	public final static String FRTD_F100_TYPE_TRANSDUCER 		= "F100";
	public final static String FRTD_PT3916_TYPE_TRANSDUCER 		= "PT3916";
	public final static String FRTD_PT385_TYPE_TRANSDUCER 		= "PT385";
	public final static String FRTD_USER_TYPE_TRANSDUCER 		= "USER";
	
	public final static String[] AVAILABLE_FRTD_TRANSDUCER_TYPES =
	{
		FRTD_PT100_TYPE_TRANSDUCER,
		FRTD_D100_TYPE_TRANSDUCER,
		FRTD_F100_TYPE_TRANSDUCER,
		FRTD_PT3916_TYPE_TRANSDUCER,
		FRTD_PT385_TYPE_TRANSDUCER,
		FRTD_USER_TYPE_TRANSDUCER
	};
	

	public final static String AVERAGE_MOVING_TCONTROL = "MOVing";
	public final static String AVERAGE_REPEAT_TCONTROL = "REPeat";

	public final static String[] AVAILABLE_AVERAGE_TCONTROL_TYPES =
	{
		AVERAGE_MOVING_TCONTROL,
		AVERAGE_REPEAT_TCONTROL,
	};
	
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	/**
	 * Variable para el acceso al modulo de comunicaciones del multimetro.
	 * Será necesario para permitir a Sense tomar el control de dicho modulo
	 * Y envíar de manera autónoma comandos y recibir respuestas
	 */
	
	private I_CommunicationsInterface 	communicationsInterface = null;
	
	/**
	 * Estructura de tipo HashMap para albergar la configuración de cada uno de los canales que una instancia dada de esta 
	 * clase lee del fichero cuya ruta es pasada al constructor (normalmente sensesubsystem_configuration.json o sense_configuration.json).
	 */
	private HashMap<Integer,I_SenseFunction_Configuration> senseChannels_Configuration = null;
	
	
	private boolean concurrentMeasures = false;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public Sense_Subsystem(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
		this.senseChannels_Configuration = new HashMap<Integer,I_SenseFunction_Configuration>();
	}
	
	public Sense_Subsystem(String jSONObject_filename) throws Exception {
		this((org.json.simple.JSONObject)new JSONParser().parse(new FileReader(jSONObject_filename)));
	}
	
	public Sense_Subsystem(JSONObject jObj) throws Exception {
		this(
				(String)jObj.get("name"),
				(Long)jObj.get("id"),
				null,							//(InstrumentComponent)jObj.get("parent") not implemented for the moment
				(boolean)jObj.get("enable"),
				(boolean)jObj.get("selected")
			);
		
		logger.info("Parsing Sense_Subsystem from jObj ... ");
		
		JSONObject configuration = (JSONObject) jObj.get("Configuration");
		
		this.concurrentMeasures = ((String)configuration.get("concurrent_measures")).equals("ON");
		
		JSONArray channelsConfiguration = (JSONArray) configuration.get("channels");
		
		Iterator<JSONObject> channelsConfiguration_Iterator = channelsConfiguration.iterator();

		
		while (channelsConfiguration_Iterator.hasNext()) {
			
			
			JSONObject channelConfiguration = channelsConfiguration_Iterator.next();
			
			
			//Cuando leemos la configuracion de los canales de medida del fichero de configuracion json podemos encontrar 
			//el campo channelNumer en formato String, normalmente vacio, caso indicativo que se trata de la configuración 
			//del system channel del multimetro (normalmente el channel que hay fuera de las trajetas expansoras accesible por las
			//entradas del front o del back)
			
			Integer cNumber;
			if (channelConfiguration.get("channelNumber") instanceof String) cNumber = null;
			else cNumber = ((Long)channelConfiguration.get("channelNumber")).intValue();
			
			String function = ((String)channelConfiguration.get("function"));
			I_SenseFunction_Configuration functionConfiguration = SenseFunctions_Factory.getConfiguredSenseFunction(function,channelConfiguration);
			logger.info("-------->    " + functionConfiguration.toString());
			this.senseChannels_Configuration.put(cNumber, functionConfiguration);
			
		}
	}
	
	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	public static int getClassversion() {
		return classVersion;
	}
	
	
	//**************************************************************************
	//****************************METODOS PRIVADOS******************************
	//**************************************************************************
	
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	

	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		
		logger.info("Initializing Sense SubSystem ... ");
		this.communicationsInterface = i_CommunicationsInterface;
		
		////////////
		//FIN FASE//
		////////////
		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading Sense SubSystem configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		Set<Entry<Integer, I_SenseFunction_Configuration>> es = this.senseChannels_Configuration.entrySet();
		
		Iterator<Entry<Integer, I_SenseFunction_Configuration>> i = es.iterator();
		
		this.communicationsInterface.write(":SENSe:FUNCtion:CONCurrent " + (this.concurrentMeasures? "ON":"OFF"));
		
		while(i.hasNext())
		{
			Entry<Integer, I_SenseFunction_Configuration> entry = i.next();
			
			int[] channelNumberArray = null;
			if (entry.getKey()!=null) channelNumberArray = new int[] {(entry.getKey())};
			
			I_SenseFunction_Configuration channelConfiguration = entry.getValue();
			logger.info(channelConfiguration.toString());
			ArrayList<String> commandArray = SenseFunctions_Factory.getConfigurationCommandArrayForInstrument(channelConfiguration, Multimeter.K2700_NAME, channelNumberArray);
			Iterator<String> commandArrayIt = commandArray.iterator();
			while (commandArrayIt.hasNext())
			{
				this.communicationsInterface.write(commandArrayIt.next());
			}
			
		}
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading Sense SubSystem configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
	}

	/**
	 * Método creado para poder configurar la funcion de medida del instrumento o bien configurar la funcion de medida de los 
	 * canales de medida de la lista de canales pasada como parámetro. Se trata de una función que requiere tiempo para ser ejecutada,
	 * por lo que se aconseja dejar un tiempo antes de enviar otro comando al instrumento.
	 * @param functionName con el String que incluye el nombre de la funcion de medida a aplicar al instrumento o a la lista de canles (si esta es diferente a null) 
	 * @param channelList con el array de enteros que identifican los canales a configurar con la functionName. Si es null la configuración se aplicará a la funcion de 
	 * medida del instrumento.
	 * @throws Exception si se hace uso de una función de medida no existente en el set de funciones definidas para el instrumento o si,
	 * alguno de los canales de medida de la lista de canales no existe o no está disponible en cualquiera de los slots de expansion.
	 */
	@Override
	public void setChannels_SenseFunction(int[] channelsList, I_SenseFunction_Configuration configuration) throws Exception {
		
		
		for (int i=0;i<channelsList.length;i++)
		{
			this.senseChannels_Configuration.put(channelsList[i], configuration);
		}
		
	}

	/**
	 * Método creado para poder configurar la funcion de medida del instrumento o bien configurar la funcion de medida del
	 * canal de medida pasado como parámetro. Se trata de una función que requiere tiempo para ser ejecutada,
	 * por lo que se aconseja dejar un tiempo antes de enviar otro comando al instrumento.
	 * @param functionName con el String que incluye el nombre de la funcion de medida a aplicar al instrumento o al canal (si esta es diferente a null) 
	 * @param channel con un entero que identifican el canal a configurar con la functionName. Si es null la configuración se aplicará a la funcion de 
	 * medida del instrumento. HAce uso del método public void configureSenseFunction(String functionName, int[] channelsList)
	 * @throws Exception si se hace uso de una función de medida no existente en el set de funciones definidas para el instrumento o si,
	 * el canal de medida no existe o no está disponible en cualquiera de los slots de expansion.
	 */
	@Override
	public void setChannel_SenseFunction(int channel, I_SenseFunction_Configuration configuration) throws Exception {
		
		int[] channelsList;
		
		if (channel==-1) channelsList = null;
		else channelsList = new int[]{channel};
		
		this.setChannels_SenseFunction(channelsList, configuration);
	}
	
	/**
	 * Método creado para poder obtener la configuración de la funcion de medida del instrumento (si channelsList es null) o la configuracion de la funcion de medida de 
	 * cada uno de los canales indicados por el parametro de entrada channelsList.
	 * POr ejemplo, si channelsList = [101,102,103] y esta lista de canales es válida y la respectiva configuración de sus funciones en el instrumento es TEMP, VOLT:DC y VOLT:AC
	 * entonces el string devuelto será: "TEMP","VOLT:DC","VOLT:AC"
	 * @param channelList con el array de enteros que identifican los canales que queremos consultar. Si es null el método devolverá la funcion de medida del instrumento.
	 * @throws Exception si alguno de los canales de medida de la lista de canales no existe o no está disponible en cualquiera de los slots de expansion.
	 * @return String con un array a modo cvs las funciones de medida de los canales o un String con la funcion de medida del instrumento.
	 */
	@Override
	public String querySenseFunction(int[] channelsList) throws Exception {
		String message = "";
		String query = "";
		
		if (channelsList==null)
		{
			message = "Querying sense function configuration ...";
			query = "SENSe:FUNCtion?";			
		}
		else
		{
			message = "Querying sense function configuration at channels " + MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList) + "...";
			query = "SENSe:FUNCtion? " +  MeasureChannels_DifferentialMultiplexer.createChannelsList(channelsList);
		}
		
		logger.info(message);
		return new String(this.communicationsInterface.ask(query));
	}

	/**
	 * Method created to query to the instrument the last reading string.
	 * This method do not trigger a reading. They simply return the last reading string. 
	 * The reading reflects what is applied to the input.
	 * While the instrument is performing measurements, you can use these 
	 * commands to return the last reading. If the instrument is not performing 
	 * measurements, DATA[:LATest]? will keep returning the same reading string.
	 * * 
	 * <p> <b> What it does </b> </p>
	 * This query will return the last reading the instrument had, regardless of what may have
	 * invalidated that reading, such as changing ranges or functions.
	 * 
	 * <p> <b> Limitations </b> </p>
	 * This query is fully capable of returning meaningless, old data.
	 * 
	 * <p> <b> When appropriate </b> </p>
	 * If, for some reason, the user wanted the last completed reading, even after changing ranges
	 * or other measurement settings, which would invalidate the old reading.
	 * The :CALC:DATA:LATest? query is similar to the :DATA:LAT? query, but applies to
	 * readings that have math applied to them (e.g.,:MX+B scaling).
	 * <p> 
	 * 
	 * @return String with the latest reading in the format defined in the format subsystem
	 */
	@Override
	public String queryLatestReading() throws Exception {
		byte[] byteArrayResponse = this.communicationsInterface.ask("SENSe:DATA:LATest?");
		return new String(byteArrayResponse, StandardCharsets.UTF_8);
	}

	/**
	 * Method created to query to the instrument the last fresh reading string.
	 * This method do not trigger a reading. They simply return the last fresh reading string. 
	 * The reading reflects what is applied to the input.
	 * DATA:FRESh? can only be used once to return the same reading string. That
	 * is, the reading must be “fresh.” Sending this command again to retrieve the
	 * same reading string will generate error -230 (data corrupt or stale), or cause a
	 * the GPIB to time-out. In order to again use DATA:FRESh? a new (fresh)
	 * reading must be triggered.
	 * 
	 * <p> <b> What it does </b> </p>
	 * This query is similar to :FETCh? in that it returns the latest reading from the instrument,
	 * but it has the advantage of making sure that it does not return the same reading twice.
	 * 
	 * <p> <b> Limitations </b> </p>
	 * Like the :FETCh? query, this command does not trigger a reading.
	 * 
	 * <p> <b> When appropriate </b> </p>
	 * This is a much better choice than the :FETCh? query because it cannot return the same
	 * reading twice. This would be a good query to use when triggering by BUS or
	 * EXTERNAL, because it will wait for a reading to complete if a reading is in progress.
	 * The :CALC:DATA:FRESh? query is similar to the :DATA:FRESh? query, but applies to
	 * readings that have math applied to them (e.g.:MX+B scaling).
	 * <p> 
	 * 
	 * @return String with the latest fresh reading in the format defined in the format subsystem
	 */
	@Override
	public String queryLastFreshReading() throws Exception {
		byte[] byteArrayResponse = this.communicationsInterface.ask("SENSe:DATA:FRESh?");
		return new String(byteArrayResponse, StandardCharsets.UTF_8);
	}
		
}
