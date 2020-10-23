package Data;

import org.jdom.Element;



public class GeneralProgramData {
	private String programName;
	private String programType;
	private String programFilePath;
	private String programAuthor;
	private String programDescription;

	public GeneralProgramData(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public GeneralProgramData(String _programName, String _programType,String _programFilePath, String _programAuthor,String _programDescription)throws Exception{
		programName = _programName;
		programType = _programType;
		programFilePath = _programFilePath;
		programAuthor = _programAuthor;
		programDescription = _programDescription;
	}
	public String getProgramType(){
		return programType;
	}
	public String getProgramName(){
		return programName;
	}
	public String getProgramFilePath(){
		return programFilePath;
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
			generalProgramDataElement.setAttribute("ProgramType",getProgramType());
			generalProgramDataElement.setAttribute("ProgramName",getProgramName());
			generalProgramDataElement.setAttribute("ProgramFilePath",getProgramFilePath());
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
		programType = _generalProgramDataElement.getAttributeValue("ProgramType");
		programName = _generalProgramDataElement.getAttributeValue("ProgramName");
		programFilePath = _generalProgramDataElement.getAttributeValue("ProgramFilePath");
		programAuthor = _generalProgramDataElement.getAttributeValue("ProgramAuthor");
		programDescription = _generalProgramDataElement.getAttributeValue("ProgramDescription");
	}
	public String toString(){
		String res="";
		res = res + "ProgramName = "+getProgramName()+"\n";
		res = res + "ProgramType = "+getProgramType()+"\n";
		res = res + "ProgramFilePath = "+getProgramFilePath()+"\n";
		res = res + "ProgramAuthor = "+getProgramAuthor()+"\n";
		res = res + "ProgramDescription = "+getProgramDescription()+"\n";
		return res;
	}
}
