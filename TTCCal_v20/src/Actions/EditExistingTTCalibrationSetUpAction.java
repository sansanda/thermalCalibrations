package Actions;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Formatter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Data.TTCSetUpData;
import Main.MainController;
import frontController.*;
import gui.TTCCalibrationViewerFrame;

public class EditExistingTTCalibrationSetUpAction implements Action{
	private ActionResult result;
	private MainController mainController;
	private JFrame frameWhoHasTheRequest;
	private TTCCalibrationViewerFrame ttcProgramViewer;
	private static String instrumentsDataFilePath = "ConfigurationFiles\\instrumentsData.xml";
	private static String temperatureSensorDataFilePath = "ConfigurationFiles\\temperatureSensorData.xml";
	private  String ttcSetUpFilePath = "";

	//IO to file
	private JFileChooser fileChooser;

	public EditExistingTTCalibrationSetUpAction(){
		result=null;
		frameWhoHasTheRequest = null;
		mainController=null;
		System.out.println("Creando la instancia para el selector de ficheros. Solicitamos los ficheros para ejecutar el programa y guardar los resultados.");
		fileChooser = new JFileChooser();
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
			ttcSetUpFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            System.out.println("Creando la instancia para el programa.");
    		TTCSetUpData _program = new TTCSetUpData(ttcSetUpFilePath);
    		System.out.println(_program.toString());

    		ttcProgramViewer = new TTCCalibrationViewerFrame(instrumentsDataFilePath,temperatureSensorDataFilePath,ttcSetUpFilePath,_program,2,mainController);
    		ttcProgramViewer.addWindowListener(mainController);
    		ttcProgramViewer.loadThermalCalibrationData(_program);
    		ttcProgramViewer.setVisible(true);
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
