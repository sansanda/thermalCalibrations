package data;

public abstract class CalibrationProfile {

	public static final String TEMPERATURE_VS_VOLTAGE_TYPE = "TEMPERATURE_VS_VOLTAGE_TYPE";
	public static final String TEMPERATURE_VS_RESISTANCE_TYPE = "TEMPERATURE_VS_RESISTANCE_TYPE";


	private String type;
	private String observations;
	public CalibrationProfile(String _type,String _obs) throws Exception{
		validateType(_type);
		type=_type;
		observations=_obs;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @return the observations
	 */
	public String getObservations() {
		return observations;
	}
	/**
	 * @param type the type to set
	 * @throws Exception
	 */
	public void setType(String _type) throws Exception {
		validateType(_type);
		type = _type;
	}
	/**
	 * @param observations the observations to set
	 */
	public void setObservations(String _observations) {
		observations = _observations;
	}
	/**
	 *
	 * @param _type
	 * @throws Exception
	 */
	private void validateType(String _type)throws Exception {
		if (	_type.equals(TEMPERATURE_VS_RESISTANCE_TYPE) ||
				_type.equals(TEMPERATURE_VS_VOLTAGE_TYPE)){
			//Nothing todo
		}else{
			throw new Exception("InvalidCalibrationProfileType_Exception");
		}
	}
	public String toString(){
		String res="";
		res = res + "******CALIBRATION PROFILE******" + "\n";
		res = res + "type = "+ type + "\n";
		res = res + "observations = "+ observations + "\n";
		return res;
	}
}
