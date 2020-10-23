package Actions;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Data.DiodesCalibrationSetUp_Data;
import Main.MainController;
import fileUtilities.XMLFilter;
import frontController.*;
import gui.DiodesCalibrationViewer_Frame;

public class ViewExistingDiodesCalibrationProgram_Action implements Action{
	private ActionResult result;
	private MainController mainController;
	private JFrame frameWhoHasTheRequest;
	private DiodesCalibrationViewer_Frame diodesCalibrationSetUpViewer;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String diodesCalibrationSetUpFilePath = "";

	//IO to file
    private JFileChooser fileChooser;

	public ViewExistingDiodesCalibrationProgram_Action(){
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
		int viewMode = 1;
		String programFilePath = "";
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            System.out.println("Creando la instancia para el programa.");
    		DiodesCalibrationSetUp_Data _program = new DiodesCalibrationSetUp_Data(programFilePath);
    		System.out.println(_program.toString());

    		diodesCalibrationSetUpViewer = new DiodesCalibrationViewer_Frame(instrumentsDataFilePath,temperatureSensorDataFilePath,diodesCalibrationSetUpFilePath,_program,viewMode,mainController);
    		diodesCalibrationSetUpViewer.addWindowListener(mainController);
    		diodesCalibrationSetUpViewer.setVisible(true);
    		diodesCalibrationSetUpViewer.loadDiodesCalibrationSetUpData(_program);
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
	private void printAction(String action){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(action.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(action);
		for(int i=1;i<=((k/2)-(action.length()/2));i++){
			if((((k/2)-(action.length()/2))+action.length()+i)>100) break;
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.println("\n");
	}
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
}
