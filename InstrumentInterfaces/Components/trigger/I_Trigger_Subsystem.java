package trigger;

public interface I_Trigger_Subsystem {
	public void abort() throws Exception;
	
	public void init() throws Exception;
	public void configureInitContinousInitiation(boolean enable) throws Exception;
	public boolean isInitContinousInitiationEnable() throws Exception;
	
	/**
	 * Configura el número de veces (trigger count) que se va a ejecutar un ciclo de trigger 
	 * @param count es un entero que contiene el trigger count y suele ser un numero que va desde un valor minimo igual a 1 hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface. Eventualmente suele reservarse cualquier valor fuera de dicho rango para indicar
	 * un trigger count infinito (INF).
	 * @throws Exception
	 */
	public void configureTriggerCount(int count) throws Exception;
	
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del trigger count.
	 * @return int con la configuracion del trigger count en el instrumento
	 * @throws Exception
	 */
	public int queryTriggerCount() throws Exception;
	
	/**
	 * Configura el delay dentro del modelo de trigger del instrumento.
	 * @param delay real dentro del rango permitido por el instrumento. Habitualmente toma valores desde 0 sec hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface. Eventualmente suele reservarse cualquier valor fuera de dicho rango para indicar autodelay.
	 * @throws Exception 
	 */
	public void configureTriggerDelay(double delay) throws Exception;
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del trigger delay.
	 * @return double con la configuracion del trigger delay en el instrumento. Si el valor es 0 se da por hecho que el trigger delay está en modo auto.
	 * @throws Exception
	 */
	public double queryTriggerDelay() throws Exception;
	public boolean isTriggerDelayInAutoMode() throws Exception;
	
	
	
	/**
	 * Configura el la fuente que dispara el trigger y hace avanzar al instrumento hacia el siguiente paso dentro del modelo.
	 * @param source String que solo puede tomar uno de los valores los cuales suelen ser: IMMediate, TIMer MANual, BUS o EXTernal
	 * @throws Exception 
	 */
	public void configureTriggerSource(String source) throws Exception;
	public String queryTriggerSource() throws Exception;
	
	/**
	 * Configura el timer del trigger para que, en caso de una configuracion trigger source como TIMer, disparar el trigger cada
	 * un determinado tiempo indicado por el parámetro de entrada.
	 * @param time real dentro del rango permitido por el instrumento. Habitualmente toma valores desde 0.001 sec hasta un máximo 
	 * @throws Exception 
	 */
	public void configureTriggerTimer(double time) throws Exception;
	public double queryTriggerTimer() throws Exception;
	
	/**
	 * Envia el comando signal al instrumento para "bypassear" la etapa de espera de señal procedente de la fuente de disparo del trigger
	 * lo que provoca que el instrumento continue hacia el siguiente paso dentro del modelo de trigger.
	 * @throws Exception 
	 */
	public void sendTriggerSignal() throws Exception;
	
	/**
	 * Configura el número de veces (sample count) que se va a ejecutar una medida dentro del mismo ciclo de trigger.
	 * Por ejemplo, cuando se ha configurado un scan y este está activo entonces el valor de sample count suele ser igual 
	 * al número de canales definidos en dicho scan. 
	 * @param count es un entero que contiene el sample count y suele ser un numero que va desde un valor minimo igual a 1 hasta un máximo
	 * definido por el instrumento en cuestion que implemente dicha interface.
	 * @throws Exception
	 */
	public void configureSampleCount(int count) throws Exception;
	/**
	 * Metodo que lanza una consulta al instrumento para que devuelva el valor del sample count.
	 * @return int con la configuracion del sample count en el instrumento
	 * @throws Exception
	 */
	public int querySampleCount() throws Exception;
	
	
	
}
