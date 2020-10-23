package actionsForDiodeCalibration;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Main.MainController;
import fileUtilities.XMLFilter;
import controller.*;
import data.DiodesCalibrationSetUpData;
import view_ForDiodesCalibration.DiodesCalibrationViewerFrame;
import view_ForDiodesCalibration.DiodesCalibration_MainScreenFrame;
import view_CommonParts.OvenTemperatureWarningFrame;

public class EditExistingDiodesCalibrationSetUp_Action implements Action{
	private ActionResult result;
	private MainController mainController;
	private JFrame frameWhoHasTheRequest;
	private DiodesCalibrationViewerFrame diodeCalibrationSetUpViewer;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private String diodeCalibrationSetUpFilePath = "";

	//IO to file
	private JFileChooser fileChooser;

	public EditExistingDiodesCalibrationSetUp_Action(){
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
		int editMode = 2;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			diodeCalibrationSetUpFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            System.out.println("Creando la instancia para el programa.");
    		DiodesCalibrationSetUpData _diodesCalibrationSetUpData = new DiodesCalibrationSetUpData(diodeCalibrationSetUpFilePath);
    		System.out.println(_diodesCalibrationSetUpData.toString());

    		diodeCalibrationSetUpViewer = new DiodesCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,diodeCalibrationSetUpFilePath,_diodesCalibrationSetUpData,editMode,mainController);
    		diodeCalibrationSetUpViewer.addWindowListener(mainController);
    		diodeCalibrationSetUpViewer.loadThermalCalibrationData(_diodesCalibrationSetUpData);
    		diodeCalibrationSetUpViewer.setVisible(true);
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
