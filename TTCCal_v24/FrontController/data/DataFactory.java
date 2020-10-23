package data;

import data.Data;
import factoryCreator.Factory;

public class DataFactory extends Factory implements DataFactoryInterface{

	public static Data createData(String _data) throws Exception
	{
		/* Return Action object */
		if (_data.equals("")) {
			//return new RunTTCalibrationSetUpAction();
		}
		return null;
	}
}