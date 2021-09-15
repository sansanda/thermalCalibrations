package units;

/**
 * Interface a implementar por cualquier clase que trabaje con instrumentos que permitan 
 * la configuraci�n de las unidades de medida, por ejemplo, a  la hora de medir voltaje, 
 * temperatura, resistencia, etc... 
 * @author david
 */
public interface I_Units_Subsystem {
	
	
	/**
	 * Metodo que permite configurar las unidades de medida para el caso de magnitud = Temperature
	 * @param units que ser� "C", "CEL", "K", "F" o "FAR" u otro en funci�n del instrumento.
	 * @throws Exception
	 */
	public void setTemperatureUnits(String units) throws Exception;
	
	/**
	 * Metodo que retorna la configuraci�n de las unidades en las medidas de Temperature
	 * @return "C", "CEL", "K", "F" o "FAR" u otro en funci�n del instrumento.
	 * @throws Exception
	 */
	public String getTemperatureUnits() throws Exception;
	
	/**
	 * Metodo que permite configurar las unidades de medida para el caso de magnitudes tipo  = VOLTAGE:DC o VOLTAGE:AC
	 * @param units que ser� "UNIT:VOLT:DC V" o "UNIT:VOLT:AC V"  o "UNIT:VOLT:DC DB" o "UNIT:VOLT:AC DB" u otro en funci�n del instrumento.
	 * @param channel que puede ser -1 (si se trabaja o no con tarjeta expansora) o puede indicar el canal a configurar. 
	 * @throws Exception
	 */
	public void setChannelVoltageUnits(int channel, String units) throws Exception;
	
	/**
	 * Metodo que retorna la configuraci�n de las unidades en las medidas de VOLTAGE:DC
	 * @return "V" o "DB" u otro en funci�n del instrumento.
	 * @throws Exception
	 */
	public String	getChannelVoltageUnits(int channel) throws Exception;
	
	
	/**
	 * Metodo que permite configurar la referencia en el caso de unidades de medida tipo VOLTAGE:DC:DB
	 * @param reference que ser� un valor del siguiente rango [1e-7, 1000].
	 * @throws Exception
	 */
	public void setDCVoltageDBReference(float reference) throws Exception;
	
	/**
	 * Metodo que retorna la configuraci�n de la referencia para el caso de unidades de medida de VOLTAGE:DC:DB
	 * @return una valor float del siguiente rango [1e-7, 1000].
	 * @throws Exception
	 */
	public float getDCVoltageDBReference() throws Exception;
	
	/**
	 * Metodo que permite configurar la referencia en el caso de unidades de medida tipo VOLTAGE:AC:DB
	 * @param reference que ser� un valor del siguiente rango [1e-7, 1000].
	 * @throws Exception
	 */
	public void setACVoltageDBReference(float reference) throws Exception;
	
	/**
	 * Metodo que retorna la configuraci�n de la referencia para el caso de unidades de medida de VOLTAGE:AC:DB
	 * @return una valor float del siguiente rango [1e-7, 1000].
	 * @throws Exception
	 */
	public float getACVoltageDBReference() throws Exception;
}
