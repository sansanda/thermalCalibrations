package Data;

import org.jdom.Element;



public class GeneralProgramData {
	private String programName;
	private String programType;
	private String programFilePath;
	
	public GeneralProgramData(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public GeneralProgramData(String _programName, String _programType,String _programFilePath)throws Exception{
		programName = _programName; 
		programType = _programType;
		programFilePath = _programFilePath;
	}
	public String getType(){
		return programType;
	}
	public String getName(){
		return programName;
	}
	public String getProgramFilePath(){
		return programFilePath;
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
			generalProgramDataElement.setAttribute("ProgramType",getType());
			generalProgramDataElement.setAttribute("ProgramName",getName());
			generalProgramDataElement.setAttribute("ProgramFilePath",getProgramFilePath());
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
		programType = _generalProgramDataElement.getAttributeValue("ProgramType");
		programName = _generalProgramDataElement.getAttributeValue("ProgramName");
		programFilePath = _generalProgramDataElement.getAttributeValue("ProgramFilePath");
	}
	public String toString(){
		String res="";
		return res;
	}
}
