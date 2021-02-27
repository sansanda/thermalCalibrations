package units;

/**
 * Interface a implementar por cualquier clase que trabaje con instrumentos que permitan 
 * la configuración de las unidades de medida, por ejemplo, a  la hora de medir voltaje, 
 * temperatura, resistencia, etc... 
 * @author david
 */
public interface I_MeasureUnits_Configurator {
	
	/**
	 * Metodo que permite configurar las unidades de medida de una determinada magnitud para un cierto
	 * numero de canales.
	 * @param magnitude que puede ser TEMP, VOLT:DC, VOLT:AC, etc...
	 * @param unit que será en función de la magnitude. Por ejemplo, para TEMP solo 
	 * podrá aceptar valores como C, CEL, K, F o FAR y para VOLT V o DB, etc..
	 * @param channels con un array de enteros que identifica los canales a configurar. Puede ser null.
	 * @throws Exception
	 */
	public void 	setUnits(String magnitude, String unit, int[] channels) throws Exception;
	
	/**
	 * Metodo que permite obtener la configuración de las unidades de medida de una determinada magnitud 
	 * para un cierto numero de canales.
	 * @param magnitude cuyas unidades queremos consultar
	 * @param channels con un array de enteros que identifica los canales a consultar.
	 * Puede ser null. En ese caso el String[] solo contendrá una entrada.
	 * @return String[] con la consulta (las unidades).
	 * @throws Exception
	 */
	public String[] getUnits(String magnitude, int[] channels) throws Exception;
	
	/**
	 * Método que permite configurar la referencia para una unidad determinada, por ejemplo DB.
	 * Con ciertas unidades el metodo no tiene sentido, por ejemplo Volt
	 * @param unit con la unidad cuya referencia queremos configurar. 
	 * @param reference con el valor de referencia
	 * @throws Exception
	 */
	public void 	setUnitReference(String unit, String reference) throws Exception;
	
	/**
	 * Método que permite consulta la referencia de una unidad de medida determinada
	 * @param unit con la unidad a consultar
	 * @return la referencia de dicha unidad
	 * @throws Exception
	 */
	public String 	getUnitReference(String unit) throws Exception;
	
	
	
	
}
