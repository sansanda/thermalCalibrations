package actions;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import calibrationSetUp.CalibrationSetUp;
import controller.*;
import views.CalibrationSetUp_Viewer_JFrame;
import Main.MainController;
import fileUtilities.XMLFilter;

public class ViewCalibrationProgram_Action extends AbstractAction{
	
	private ActionResult 					result;
	private MainController 					mainController;
	private JFrame							frameWhoHasTheRequest;
	private CalibrationSetUp_Viewer_JFrame 	ttcProgramViewer;
	private static String 					instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String 					temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String 						ttcSetUpFilePath = "";

	//IO to file
    private JFileChooser fileChooser;

	public ViewCalibrationProgram_Action(){
		result=null;
		frameWhoHasTheRequest = null;
		mainController=null;
		System.out.println("Creando la instancia para el selector de ficheros. Solicitamos los ficheros para ejecutar el programa y guardar los resultados.");
		fileChooser = new JFileChooser();
		configureFileChooser(fileChooser);
	}
	/* Pass the frame who has made the request*/
	public void setFrameWhoHasDoneTheRequest(JFrame _frameWhoHasTheRequest){
		frameWhoHasTheRequest = _frameWhoHasTheRequest;
	}
	public void setMain(MainController _main){
		mainController = _main;
	}
	/* Execute business logic */
	public boolean execute(ActionRequest _req) throws IOException, Exception
	{
		JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea ver.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		String programFilePath = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            System.out.println("Creando la instancia para el programa.");
            CalibrationSetUp _program = new CalibrationSetUp(programFilePath);
    		System.out.println(_program.toString());

    		ttcProgramViewer = new CalibrationSetUp_Viewer_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,ttcSetUpFilePath,_program,VIEW_MODE,mainController);
    		ttcProgramViewer.addWindowListener(mainController);
    		ttcProgramViewer.setVisible(true);
    		ttcProgramViewer.loadThermalCalibrationData(_program);
    		return true;
        } else {
        	System.out.println("Problemas abriendo el fichero.");
        	return false;
        }
	}
	/* Return the page name (and path) to display the view */
	public ActionResult getResult()
	{
		return result;
	}
	public void configureFileChooser(JFileChooser _fileChooser){
		_fileChooser.setFileHidingEnabled(false);
		_fileChooser.setFileFilter(new XMLFilter());
	}
}
