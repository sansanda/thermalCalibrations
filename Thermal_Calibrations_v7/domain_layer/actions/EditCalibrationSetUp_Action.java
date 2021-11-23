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

public class EditCalibrationSetUp_Action extends AbstractAction{
	
	private ActionResult 					result;
	private MainController 					mainController;
	private JFrame 							frameWhoHasTheRequest;
	private CalibrationSetUp_Viewer_JFrame 	calibrationSetUpViewer;
	private static String 					instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String 					temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String 						calibrationSetUpFilePath = "";

	//IO to file
	private JFileChooser fileChooser;

	public EditCalibrationSetUp_Action(){
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
		JOptionPane.showMessageDialog(null,"Indique fichero con el programa que desea editar.");
		int returnVal = fileChooser.showOpenDialog(frameWhoHasTheRequest);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			calibrationSetUpFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            System.out.println("Creando la instancia para el programa.");
            CalibrationSetUp _calibrationSetUp = new CalibrationSetUp(calibrationSetUpFilePath);
    		System.out.println(_calibrationSetUp.toString());

    		calibrationSetUpViewer = new CalibrationSetUp_Viewer_JFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,calibrationSetUpFilePath,_calibrationSetUp,EDIT_MODE,mainController);
    		calibrationSetUpViewer.addWindowListener(mainController);
    		calibrationSetUpViewer.loadThermalCalibrationData(_calibrationSetUp);
    		calibrationSetUpViewer.setVisible(true);
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
