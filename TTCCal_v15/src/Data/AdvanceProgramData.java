package Data;

import org.jdom.Element;



public class AdvanceProgramData {
	private double standardDeviation;
	private int measuresPerCicle;
	private long timeBetweenSuccesivesMeasures;

	public AdvanceProgramData(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public AdvanceProgramData(double _programStandardDeviation, int _measuresPerCicle,long _timeBetweenSuccesivesMeasures)throws Exception{
		standardDeviation = _programStandardDeviation;
		measuresPerCicle = _measuresPerCicle;
		timeBetweenSuccesivesMeasures = _timeBetweenSuccesivesMeasures;
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
			org.jdom.Element generalProgramDataElement = new org.jdom.Element(_name);
			generalProgramDataElement.setAttribute("StandardDeviation",String.valueOf(getStandardDeviation()));
			generalProgramDataElement.setAttribute("MeasuresPerCicle",String.valueOf(getMeasuresPerCicle()));
			generalProgramDataElement.setAttribute("TimeBetweenSuccesivesMeasures",String.valueOf(getTimeBetweenSuccesivesMeasures()));
			return generalProgramDataElement;
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
	public void initializeFromElement(Element _generalProgramDataElement) throws Exception{
		standardDeviation = Double.parseDouble(_generalProgramDataElement.getAttributeValue("StandardDeviation"));
		measuresPerCicle = Integer.parseInt(_generalProgramDataElement.getAttributeValue("MeasuresPerCicle"));
		timeBetweenSuccesivesMeasures = Long.parseLong(_generalProgramDataElement.getAttributeValue("TimeBetweenSuccesivesMeasures"));
	}
	public String toString(){
		String res="";
		res = res + "Stnadard deviation = "+getStandardDeviation()+"\n";
		res = res + "Measures Per Cicle = "+getMeasuresPerCicle()+"\n";
		res = res + "TimeBetweenSuccesivesMeasures = "+getTimeBetweenSuccesivesMeasures()+"\n";
		return res;
	}
}
