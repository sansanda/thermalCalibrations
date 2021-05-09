package sense.channels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sense.senseFunctions.SenseFunction_Configuration;

/**
 * Configuracion exclusiva para un canal de medida de un multimetro tipo 2700 de Keithley u otro compatible.
 * La configuracion se basa en el numero de slot del canal, el numero de canal y la funcion de medida para configurar dicho canal.
 * @author DavidS
 *
 */
public class SenseChannel_Configuration implements I_SenseChannel_Configuration {

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(SenseChannel_Configuration.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private int channelNumber;
	private SenseFunction_Configuration senseFunction_Configuration;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * 
	 */
	public SenseChannel_Configuration(int channelNumber, SenseFunction_Configuration  senseFunction_Configuration) {
		this.channelNumber = channelNumber;
		this.senseFunction_Configuration = senseFunction_Configuration;
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
		
	//****************************VERSION***************************************
		
	public static int getVersion() {
		return classVersion;
	}
		
	//**************************************************************************
	//****************************METODOS **************************************
	//**************************************************************************

	/**
	 * Método que retorna el numero de slot asociado al numero de canal en su formato canonico.
	 * Es decir, el número de canal incluye el número de slot al cual 
	 * pertenece dicho canal. Por ejemplo: el canal 103 corresponde al slot 1
	 * y es el canal 03. El canal 204, corresponde al alot 2 y es el canal 04.
	 * Por ejemplo, si channelNumber es igual a 203 la función devuelve 2.
	 * @return int con el numero de slot al cual pertenece el canal.
	 */
	@Override
	public int getSlotNumber() throws Exception {
		return (int)(this.channelNumber / 100);
	}

	/**
	 * Método que permite configurar el numero de canal en su formato canonico,
	 * es decir, el número de canal incluye el número de slot al cual 
	 * pertenece dicho canal. Por ejemplo: el canal 103 corresponde al slot 1
	 * y es el canal 03. El canal 204, corresponde al alot 2 y es el canal 04.
	 * @param int channelNumber con el numero e canal.
	 * @return void
	 * @throws Exception
	 */
	@Override
	public void setChannelNumber(int channelNumber) throws Exception {
		this.channelNumber = channelNumber;
	}

	
	/**
	 * Método que retorna el numero de canal en su formato canonico,
	 * es decir, el número de canal incluye el número de slot al cual 
	 * pertenece dicho canal. Por ejemplo: el canal 103 corresponde al slot 1
	 * y es el canal 03. El canal 204, corresponde al alot 2 y es el canal 04.
	 * @return int con el numero del canal
	 */
	@Override
	public int getChannelNumber() throws Exception {
		return this.channelNumber;
	}

	@Override
	public void setSenseFunction(SenseFunction_Configuration sfc) throws Exception {
		this.senseFunction_Configuration = sfc;
		
	}

	@Override
	public SenseFunction_Configuration getSenseFunction() throws Exception {
		return this.senseFunction_Configuration;
	}
	
	
	
}
