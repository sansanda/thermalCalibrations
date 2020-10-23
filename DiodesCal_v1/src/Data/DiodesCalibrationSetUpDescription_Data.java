package Data;

import org.jdom.Element;



public class DiodesCalibrationSetUpDescription_Data {
	private String programName;
	private String programAuthor;
	private String programDescription;

	public DiodesCalibrationSetUpDescription_Data(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public DiodesCalibrationSetUpDescription_Data(String _programName, String _programAuthor,String _programDescription)throws Exception{
		programName = _programName;
		programAuthor = _programAuthor;
		programDescription = _programDescription;
	}
	public String getProgramName(){
		return programName;
	}
	public String getProgramAuthor(){
		return programAuthor;
	}
	public String getProgramDescription(){
		return programDescription;
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
			generalProgramDataElement.setAttribute("ProgramName",getProgramName());
			generalProgramDataElement.setAttribute("ProgramAuthor",getProgramAuthor());
			generalProgramDataElement.setAttribute("ProgramDescription",getProgramDescription());
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
		programName = _generalProgramDataElement.getAttributeValue("ProgramName");
		programAuthor = _generalProgramDataElement.getAttributeValue("ProgramAuthor");
		programDescription = _generalProgramDataElement.getAttributeValue("ProgramDescription");
	}
	public String toString(){
		String res="";
		res = res + "ProgramName = "+getProgramName()+"\n";
		res = res + "ProgramAuthor = "+getProgramAuthor()+"\n";
		res = res + "ProgramDescription = "+getProgramDescription()+"\n";
		return res;
	}
}
