package dataValidators;

public class DataValidators {

	
	private DataValidators() {}

	//////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////GENERIC VALIDATORS/////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////DISCRETE SET VALIDATORS
	
	/**
	 * Validator que permite verificar si un valor String pasado como parametro se encuentra dentro de un set.
	 * @param _value con el String a validar 
	 * @param _set con el set de valores que _value puede adoptar
	 * @param _notMatchValue con el String por defecto que devuelve la funcion en caso que _value no se encuentre dentro del set
	 * @return _value si este se encuentra dentro del set o _notMatchValue si no se encuentra dentro del set
	 */
	public static String discreteSet_Validator(String _value, String[] _set, String _notMatchValue) 
	{
		for (int i=0;i<_set.length;i++)
		{
			if (_value.equals(_set[i])) return _value;  
		}
		return _notMatchValue;
	}
	
	/**
	 * Validator que permite verificar si un valor int pasado como parametro se encuentra dentro de un set.
	 * @param _value con el int a validar 
	 * @param _set con el set de valores que _value puede adoptar
	 * @param _notMatchValue con el int por defecto que devuelve la funcion en caso que _value no se encuentre dentro del set
	 * @return _value si este se encuentra dentro del set o _notMatchValue si no se encuentra dentro del set
	 */
	public static int discreteSet_Validator(int _value, int[] _set, int _notMatchValue) 
	{
		for (int i=0;i<_set.length;i++)
		{
			if (_value == _set[i]) return _value;  
		}
		return _notMatchValue;
	}
	
	/**
	 * Validator que permite verificar si un valor double pasado como parametro se encuentra dentro de un set.
	 * @param _value con el double a validar 
	 * @param _set con el set de valores que _value puede adoptar
	 * @param _notMatchValue con el String por defecto que devuelve la funcion en caso que _value no se encuentre dentro del set
	 * @return _value si este se encuentra dentro del set o _notMatchValue si no se encuentra dentro del set
	 */
	public static double discreteSet_Validator(double _value, double[] _set, double _notMatchValue) 
	{
		for (int i=0;i<_set.length;i++)
		{
			if (Double.compare(_value, _set[i]) == 0) return _value;  
		}
		return _notMatchValue;
	}
	
	/**
	 * Validator que permite verificar si un valor int pasado como parametro se encuentra dentro de un rango determinado por un min y un max.
	 * @param _value con el int a validar 
	 * @param _min con el entero que define el rango por el extremo inferior
	 * @param _max con el entero que define el rango por el extremo superior
	 * @param _notMatchValue con el int por defecto que devuelve la funcion en caso que _value no se encuentre dentro del rango
	 * @return _value si este se encuentra dentro del rango o _notMatchValue si no se encuentra dentro del rango
	 */
	public static int range_Validator(int _value, int _min, int _max, int _notMatchValue) 
	{
	
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
	
	/**
	 * Validator que permite verificar si un valor float pasado como parametro se encuentra dentro de un rango determinado por un min y un max.
	 * @param _value con el float a validar 
	 * @param _min con el float que define el rango por el extremo inferior
	 * @param _max con el float que define el rango por el extremo superior
	 * @param _notMatchValue con el float por defecto que devuelve la funcion en caso que _value no se encuentre dentro del rango
	 * @return _value si este se encuentra dentro del rango o _notMatchValue si no se encuentra dentro del rango
	 */
	public static float range_Validator(float _value, float _min, float _max, float _notMatchValue) 
	{
	
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
	
	/**
	 * Validator que permite verificar si un valor double pasado como parametro se encuentra dentro de un rango determinado por un min y un max.
	 * @param _value con el double a validar 
	 * @param _min con el double que define el rango por el extremo inferior
	 * @param _max con el double que define el rango por el extremo superior
	 * @param _notMatchValue con el double por defecto que devuelve la funcion en caso que _value no se encuentre dentro del rango
	 * @return _value si este se encuentra dentro del rango o _notMatchValue si no se encuentra dentro del rango
	 */
	public static double range_Validator(double _value, double _min, double _max, double _notMatchValue) 
	{
	
		if (Double.compare(_value,_min)<0 || Double.compare(_value,_max)>0) return _notMatchValue;
		return _value;
	}
}
