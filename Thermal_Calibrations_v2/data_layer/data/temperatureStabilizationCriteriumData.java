package data;

import org.jdom.Element;

public class temperatureStabilizationCriteriumData {

	private double 	stDev;
	private double 	maxAdmissibleTemperatureError;
	private int 	firstTemperatureStepStabilitzationTimeInMinutes;
	private int 	measurementWindow;
	private long 	samplingPeriode;
	private int 	numerOfSuccessiveWindowsUnderStDev;
	private int 	temperatureStabilitzationTime;
	private int 	temperatureStabilitzationMethod; // 0 By St Dev, 1 By Time


	public temperatureStabilizationCriteriumData(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public temperatureStabilizationCriteriumData
	(		int _firstTemperatureStepStabilitzationTimeInMinutes,
			double _maxAdmissibleTemperatureError,
			double _stDev,
			int _measurementWindow,
			long _samplingPeriode,
			int _numberOfSuccessiveWindowsUnderStDev,
			int _temperatureStabilitzationMethod,
			int _temperatureStabilitzationTime) throws Exception{

		maxAdmissibleTemperatureError = _maxAdmissibleTemperatureError;
		firstTemperatureStepStabilitzationTimeInMinutes = _firstTemperatureStepStabilitzationTimeInMinutes;
		stDev = _stDev;
		measurementWindow = _measurementWindow;
		samplingPeriode = _samplingPeriode;
		numerOfSuccessiveWindowsUnderStDev = _numberOfSuccessiveWindowsUnderStDev;
		temperatureStabilitzationTime = _temperatureStabilitzationTime;
		temperatureStabilitzationMethod = _temperatureStabilitzationMethod;

	}
	/**
	 * @return the firstTemperatureStepStabilitzationTimeInMinutes
	 */
	public int getFirstTemperatureStepStabilitzationTimeInMinutes() {
		return firstTemperatureStepStabilitzationTimeInMinutes;
	}
	/**
	 * @param firstTemperatureStepStabilitzationTimeInMinutes the firstTemperatureStepStabilitzationTimeInMinutes to set
	 */
	public void setFirstTemperatureStepStabilitzationTimeInMinutes
	(int _firstTemperatureStepStabilitzationTimeInMinutes) {
		firstTemperatureStepStabilitzationTimeInMinutes = _firstTemperatureStepStabilitzationTimeInMinutes;
	}
	/**
	 * @return the maxAdminssibleTempError
	 */
	public double getMaxAdminssibleTemperatureError() {
		return maxAdmissibleTemperatureError;
	}
	public double getStDev(){
		return stDev;
	}
	public int getMeasurementWindow(){
		return measurementWindow;
	}
	public long getSamplingPeriode(){
		return samplingPeriode;
	}
	public int getNumberOfWindowsUnderStDev(){
		return numerOfSuccessiveWindowsUnderStDev;
	}
	public int getTemperatureStabilitzationTime(){
		return temperatureStabilitzationTime;
	}
	public int getTemperatureStabilitzationMethod(){
		return temperatureStabilitzationMethod;
	}
	/**
	 * @param maxAdminssibleTempError the maxAdminssibleTempError to set
	 */
	public void setMaxAdmissibleTempError(double _maxAdmissibleTemperatureError) {
		maxAdmissibleTemperatureError = _maxAdmissibleTemperatureError;
	}
	/**
	 * Title: toJDOMElement
	 * Description: M�todo que convierte una instancia de la classe GeneralProgramData
	 * 				en un objeto Element de la librer�a JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param 	String _name con el nombre del elemento
	 * @return Element
	 * @throws Exception
	 */
	public Element toJDOMElement(String _name){
			org.jdom.Element advanceProgramDataElement = new org.jdom.Element(_name);
			advanceProgramDataElement.setAttribute("FirstTemperatureStepStabilitzationTime",String.valueOf(getFirstTemperatureStepStabilitzationTimeInMinutes()));
			advanceProgramDataElement.setAttribute("MaximunAdmissibleTemperatureError",String.valueOf(getMaxAdminssibleTemperatureError()));
			advanceProgramDataElement.setAttribute("StandardDeviation",String.valueOf(getStDev()));
			advanceProgramDataElement.setAttribute("MeasurementWindow",String.valueOf(getMeasurementWindow()));
			advanceProgramDataElement.setAttribute("SamplingPeriode",String.valueOf(getSamplingPeriode()));
			advanceProgramDataElement.setAttribute("NumberOfSuccessiveWindowsUnderStDev",String.valueOf(getNumberOfWindowsUnderStDev()));
			advanceProgramDataElement.setAttribute("TemperatureStabilitzationTime",String.valueOf(getTemperatureStabilitzationTime()));
			advanceProgramDataElement.setAttribute("TemperatureStabilitzationMethod",String.valueOf(getTemperatureStabilitzationMethod()));
			return advanceProgramDataElement;
	}
	/**
	 * Title: initializeFromElement
	 * Description: M�todo que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _advanceProgramDataElement) throws Exception{
		firstTemperatureStepStabilitzationTimeInMinutes = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("FirstTemperatureStepStabilitzationTime"));
		maxAdmissibleTemperatureError = Double.parseDouble(_advanceProgramDataElement.getAttributeValue("MaximunAdmissibleTemperatureError"));
		stDev = Double.parseDouble(_advanceProgramDataElement.getAttributeValue("StandardDeviation"));
		measurementWindow = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("MeasurementWindow"));
		samplingPeriode = Long.parseLong(_advanceProgramDataElement.getAttributeValue("SamplingPeriode"));
		numerOfSuccessiveWindowsUnderStDev = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("NumberOfSuccessiveWindowsUnderStDev"));
		temperatureStabilitzationTime = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("TemperatureStabilitzationTime"));
		temperatureStabilitzationMethod = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("TemperatureStabilitzationMethod"));
	}
	public String toString(){
		String res="";
		res = res + "First Temperature Step Stabilitzation Time (Minutes) = "+getFirstTemperatureStepStabilitzationTimeInMinutes()+"\n";
		res = res + "M�ximun Admissible Temperature Error = "+getMaxAdminssibleTemperatureError()+"\n";
		res = res + "Standard Deviation = "+getStDev()+"\n";
		res = res + "Measurement Window = "+getMeasurementWindow()+"\n";
		res = res + "Sampling Periode = "+getSamplingPeriode()+"\n";
		res = res + "NumberOfSuccessiveWindowsUnderStDev = "+getNumberOfWindowsUnderStDev()+"\n";
		res = res + "TemperatureStabilitzationMethod = "+getTemperatureStabilitzationMethod()+"\n";
		res = res + "TemperatureStabilitzationTime = "+getTemperatureStabilitzationTime()+"\n";
		return res;
	}

}
