package Data;

public class Program {
	private String programName;
	private String programType;
	public Program(){
	}
	public Program(String _programName,String _programType){
		programName = _programName;
		programType = _programType;
	}
	public String getProgramName(){return programName;}
	public String getProgramType(){return programType;}
	public void setProgramName(String _programName){programName = _programName;}
	public void setProgramType(String _programType){programType = _programType;}
	
	public String toString(){
		String res="";
		res = res + "*****************************************************************"+ "\n";
		res = res + "*****************programName = "+getProgramName()+"************************"+ "\n";
		res = res + "*****************************************************************"+ "\n";
		res = res + "programType = "+getProgramType() + "\n";
		return res;
	}
}
