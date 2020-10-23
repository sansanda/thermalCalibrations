package Data;

import org.jdom.Element;



public class AdvanceProgramData {
	private double 	standardDeviation;
	private int 	measuresPerCicle;
	private long 	timeBetweenSuccesivesMeasures;
	private int 	nPassesBelowStandardDeviation;
	private int 	temperatureStabilitzationTime;
	private int 	temperatureStabilitzationMethod; // 0 By St Dev, 1 By Time
	
	public AdvanceProgramData(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public AdvanceProgramData(double _programStandardDeviation, int _measuresPerCicle,long _timeBetweenSuccesivesMeasures,int _nPassesBelowStandardDeviation,int _temperatureStabilitzationMethod, int _temperatureStabilitzationTime)throws Exception{
		standardDeviation = _programStandardDeviation;
		measuresPerCicle = _measuresPerCicle;
		timeBetweenSuccesivesMeasures = _timeBetweenSuccesivesMeasures;
		nPassesBelowStandardDeviation = _nPassesBelowStandardDeviation;
		temperatureStabilitzationTime = _temperatureStabilitzationTime;
		temperatureStabilitzationMethod = _temperatureStabilitzationMethod;
	}
	public double getStandardDeviation(){
		return standardDeviation;
	}
	public int getMeasuresPerCicle(){
		return measuresPerCicle;
	}
	public long getTimeBetweenSuccesivesMeasures(){
		return timeBetweenSuccesivesMeasures;
	}
	public int getNPassesBelowStandardDeviation(){
		return nPassesBelowStandardDeviation;
	}
	public int getTemperatureStabilitzationTime(){
		return temperatureStabilitzationTime;
	}
	public int getTemperatureStabilitzationMethod(){
		return temperatureStabilitzationMethod;
	}
	/**
	 * Title: toJDOMElement
	 * Description: Método que convierte una instancia de la classe GeneralProgramData
	 * 				en un objeto Element de la librería JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	String _name con el nombre del elemento
	 * @return Element
	 * @throws Exception
	 */
	public Element toJDOMElement(String _name){
			org.jdom.Element advanceProgramDataElement = new org.jdom.Element(_name);
			advanceProgramDataElement.setAttribute("StandardDeviation",String.valueOf(getStandardDeviation()));
			advanceProgramDataElement.setAttribute("MeasuresPerCicle",String.valueOf(getMeasuresPerCicle()));
			advanceProgramDataElement.setAttribute("TimeBetweenSuccesivesMeasures",String.valueOf(getTimeBetweenSuccesivesMeasures()));
			advanceProgramDataElement.setAttribute("NPassesBelowStandardDeviation",String.valueOf(getNPassesBelowStandardDeviation()));
			advanceProgramDataElement.setAttribute("TemperatureStabilitzationTime",String.valueOf(getTemperatureStabilitzationTime()));
			advanceProgramDataElement.setAttribute("TemperatureStabilitzationMethod",String.valueOf(getTemperatureStabilitzationMethod()));
			return advanceProgramDataElement;
	}
	/**
	 * Title: initializeFromElement
	 * Description: Método que inicializa esta instancia de la classe GeneralProgramDataElement
	 * 				a partir de un objeto de la classe Element de JDOM
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param 	Element _generalProgramDataElement
	 * @return none
	 * @throws Exception
	 */
	public void initializeFromElement(Element _advanceProgramDataElement) throws Exception{
		standardDeviation = Double.parseDouble(_advanceProgramDataElement.getAttributeValue("StandardDeviation"));
		measuresPerCicle = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("MeasuresPerCicle"));
		timeBetweenSuccesivesMeasures = Long.parseLong(_advanceProgramDataElement.getAttributeValue("TimeBetweenSuccesivesMeasures"));
		nPassesBelowStandardDeviation = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("NPassesBelowStandardDeviation"));
		temperatureStabilitzationTime = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("TemperatureStabilitzationTime"));
		temperatureStabilitzationMethod = Integer.parseInt(_advanceProgramDataElement.getAttributeValue("TemperatureStabilitzationMethod"));
	}
	public String toString(){
		String res="";
		res = res + "Stnadard deviation = "+getStandardDeviation()+"\n";
		res = res + "Measures Per Cicle = "+getMeasuresPerCicle()+"\n";
		res = res + "TimeBetweenSuccesivesMeasures = "+getTimeBetweenSuccesivesMeasures()+"\n";
		res = res + "NPassesBelowStandardDeviation = "+getNPassesBelowStandardDeviation()+"\n";
		res = res + "TemperatureStabilitzationMethod = "+getTemperatureStabilitzationMethod()+"\n";
		res = res + "TemperatureStabilitzationTime = "+getTemperatureStabilitzationTime()+"\n";
		return res;
	}
}
