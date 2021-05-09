/**
 * 
 */
package format;

import java.util.ArrayList;

/**
 * Interface a implementar por cualquier clase que trabaje con instrumentos que permitan 
 * la configuración del formato de los datos devueltos por cada medida cuando se reclaman
 * resultados de voltaje, temperatura, resistencia, etc... 
 * @author david
 */
public interface I_Format_Subsystem {
	
	/**
	 * Metodo que permite configurar el tipo de formato de los valores devueltos por el instrumento 
	 * cuando se reclama el resultado de una medida como por ejemplo voltaje, temp, etc..
	 * @param format que puede ser: "ASCii", "REAL", "DREAL", "REAL, 32" o "REAL, 64" etc... Todo depende del instrumento...
	 * @throws Exception
	 */
	public void setFormatDataType(String format) throws Exception;
	
	/**
	 * Metodo que permite obtener el tipo de formato de los valores devueltos por el instrumento. 
	 * @param String con el tipo de formato que puede ser: "ASCii", "REAL", "DREAL", "REAL, 32", "REAL, 64", etc...  estos 4 últimos son formato binario.
	 * Todo depende del instrumento...
	 * @throws Exception
	 */
	public String getFormatDataType() throws Exception;
	
	/**
	 * Cuando se trabaja con formatDataType tipo binario ("REAL", "DREAL", "REAL, 32", "REAL, 64) 
	 * este metodo permite configurar el orden de los bytes devueltos por el instrumento.
	 * @param format_border que puede ser: "NORMAL" o "SWAPPED". Todo depende del instrumento...
	 * Por ejemplo, para el caso "NORMAL" y formatDataType = REAL, 32 los bytes se devuelven en el siguiente orden Byte 1, Byte 2, Byte 3, Byte 4 y
	 * para elk caso  "SWAPPED" y formatDataType = REAL, 32 los bytes se devuelven en el siguiente orden Byte 4, Byte 3, Byte 2, Byte 1
	 * @throws Exception 
	 */
	public void setFormatBorder(String format_border) throws Exception;
	
	/**
	 * Metodo que permite obtener la configuración Format:Border del instrumento el tipo de formato de los valores devueltos por el instrumento. 
	 * @return String con el tipo de formato para Format:Border que puede ser: "NORMAL" o "SWAPPED".
	 * Todo depende del instrumento...
	 * @throws Exception
	 */
	public String getFormatBorder() throws Exception;
	
	
	/**
	 * Método que permite configurar la cantidad de elementos o parámetros a tomar en cuenta cada vez que el instrumento ejecuta una medida, como voltage, temperatura,
	 * resistencia, etc... Todo depende del instrumento...
	 * @param format_elements con String que contiene la lista de los elementos. Por ejemplo puede ser: "READING, UNITS, TSTAMP"
	 * @throws Exception
	 */
	public void setFormatElements(String format_elements) throws Exception;
	
	/**
	 * Método que permite consulta la configurar actual del instrumento en relación a los elementos que devuelve por cada medida.
	 * @return String con la configuración de los elementos.
	 * @throws Exception
	 */
	public String getFormatElements() throws Exception;

}
