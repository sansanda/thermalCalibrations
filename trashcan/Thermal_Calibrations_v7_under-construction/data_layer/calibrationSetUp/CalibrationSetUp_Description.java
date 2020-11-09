package calibrationSetUp;

import org.jdom.Element;



public class CalibrationSetUp_Description {
	private String programName;
	private String programAuthor;
	private String programDescription;

	public CalibrationSetUp_Description(Element _generalProgramDataElement) throws Exception{
		initializeFromElement(_generalProgramDataElement);
	}
	public CalibrationSetUp_Description(String _programName, String _programAuthor,String _programDescription)throws Exception{
		programName = _programName;
		programAuthor = _programAuthor;
		programDescription = _programDescription;
	}
	public String getCalibrationSetUp_Name(){
		return programName;
	}
	public String getCalibrationSetUp_Author(){
		return programAuthor;
	}
	public String getCalibrationSetUp_Description(){
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
			generalProgramDataElement.setAttribute("ProgramName",getCalibrationSetUp_Name());
			generalProgramDataElement.setAttribute("ProgramAuthor",getCalibrationSetUp_Author());
			generalProgramDataElement.setAttribute("ProgramDescription",getCalibrationSetUp_Description());
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
		res = res + "DESCRIPTION"+ "\n";
		res = res + "CalibrationSetUp_Name = "+getCalibrationSetUp_Name()+"\n";
		res = res + "CalibrationSetUp_Author = "+getCalibrationSetUp_Author()+"\n";
		res = res + "CalibrationSetUp_Description = "+getCalibrationSetUp_Description()+"\n";
		return res;
	}
}
