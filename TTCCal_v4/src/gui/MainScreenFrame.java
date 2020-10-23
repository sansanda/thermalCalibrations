package gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import frontController.ActionRequest;

import Actions.CreateNewCalibrationProgramAction;
import Actions.EditExistingCalibrationProgramAction;
import Actions.ExitApplicationAction;
import Actions.StartCalibrationProgramAction;
import Actions.ViewExistingCalibrationProgramAction;
import Main.MainController;


public class MainScreenFrame extends JFrame implements ActionListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem fMenuExitApp ;
	private JMenuItem fMenuNewProgram ;
    private JMenuItem fMenuOpenProgram;
    private JMenuItem fMenuEditProgram;
    private JMenuItem fMenuRunProgram;
    private JMenuItem fMenuAboutProgram;
    private JMenuItem fMenuContentsProgram;
    private ProgressScreenFrame progressScreen;
	private MainController mainController;
	private Dimension frameDimension = new Dimension(1200,800);

	public MainScreenFrame(MainController _mainController){
		setName("MainScreenFrame");
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(frameDimension);
		setSize(frameDimension);
		setResizable(false);
		mainController = _mainController;
		createMenuBar();
	}
	/** Get the menu events here. Open an instance of ParticleFrame
	    * or close the one currently displayed.
	**/
	public void actionPerformed (ActionEvent e){
		try {
			String command = e.getActionCommand ();
			System.out.println(command);
			if (command.equals ("Exit")) { //Exit from the application
				mainController.doAction(new ActionRequest("ExitApplicationAction",this));
			}
			if (command.equals ("Open")) { //Open a existing TTC Calibration Program
				mainController.doAction(new ActionRequest("ViewExistingCalibrationProgramAction",this));
			}
			if (command.equals ("New")) { //Create a new TTC Calibration Program
				mainController.doAction(new ActionRequest("CreateNewCalibrationProgramAction",this));
			}
			if (command.equals ("Edit")) { //Edit a existing TTC Calibration Program
				mainController.doAction(new ActionRequest("EditExistingCalibrationProgramAction",this));
			}
			if (command.equals ("About")) { //get the application About
				mainController.doAction(new ActionRequest("ViewAboutContentsAction",this));
			}
			if (command.equals ("Contents")) { //get the application help contents
				mainController.doAction(new ActionRequest("ViewHelpContentsAction",this));
			}
			if (command.equals ("Program")) { // Run a existing program
				mainController.doAction(new ActionRequest("StartCalibrationProgramAction",this));
			}
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} // actionPerformed
	private void createMenuBar(){
		 JMenuBar mb = new JMenuBar ();

	     JMenu fileMenu = new JMenu ("File");
	     fMenuExitApp= new JMenuItem ("Exit");
	     fileMenu.add(fMenuExitApp);
	     fMenuExitApp.addActionListener (this);
	     mb.add(fileMenu);

	     JMenu programMenu = new JMenu ("Program");
	     fMenuOpenProgram= new JMenuItem ("Open");
	     programMenu.add (fMenuOpenProgram);
	     fMenuOpenProgram.addActionListener (this);
	     fMenuNewProgram = new JMenuItem ("New");
	     programMenu.add (fMenuNewProgram);
	     fMenuNewProgram.addActionListener (this);
	     fMenuEditProgram = new JMenuItem ("Edit");
	     programMenu.add (fMenuEditProgram);
	     fMenuEditProgram.addActionListener (this);
	     mb.add (programMenu);

	     JMenu runMenu = new JMenu ("Run");
	     fMenuRunProgram= new JMenuItem ("Program");
	     runMenu.add (fMenuRunProgram);
	     fMenuRunProgram.addActionListener (this);
	     mb.add(runMenu);

	     JMenu helpMenu = new JMenu ("Help");
	     fMenuAboutProgram= new JMenuItem ("About");
	     helpMenu.add (fMenuAboutProgram);
	     fMenuAboutProgram.addActionListener (this);
	     fMenuContentsProgram= new JMenuItem ("Contents");
	     helpMenu.add (fMenuContentsProgram);
	     fMenuContentsProgram.addActionListener (this);
	     mb.add(helpMenu);

	     setJMenuBar (mb);
	}
	private void printMessage(String _msg){
		System.out.println(_msg);
		progressScreen.appendTextToLogPane(_msg);
	}
}
