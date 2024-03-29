/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package Main;

import controller.Action;
import controller.ActionRequest;
import controller.ActionResult;
import controller.ReqUtility;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.*;

import view_ForDiodesCalibration.DiodesCalibration_MainScreenFrame;

/* Framework.java requires no other files. */
public class Diodes_Calibration_MainController extends MainController implements KeyListener{

	static Image image = Toolkit.getDefaultToolkit().getImage("trayIcon.gif");
	static TrayIcon trayIcon = new TrayIcon(image, "Temperature Calibration");

	//private Point lastLocation = null;
    private DiodesCalibration_MainScreenFrame mainScreen;
	private static int RUN_PROGRAM_MODE = 1;
	private static int STOP_PROGRAM_MODE = 0;

    public Diodes_Calibration_MainController() {
        makeMainWindow();
        createTrayIcon();
    }
    public void makeMainWindow() {

        mainScreen = new DiodesCalibration_MainScreenFrame(this);
        /*
        if (lastLocation != null) {
            //Move the window over and down 40 pixels.
            lastLocation.translate(40, 40);
            if ((lastLocation.x > maxX) || (lastLocation.y > maxY)) {
                lastLocation.setLocation(0, 0);
            }
            mainScreen.setLocation(lastLocation);
        } else {
            lastLocation = mainScreen.getLocation();
        }

        System.out.println("Frame location: " + lastLocation);*/
        mainScreen.setVisible(true);
    }
    private void createTrayIcon() {
   	 	if (SystemTray.isSupported()) {
   	      SystemTray tray = SystemTray.getSystemTray();

   	      trayIcon.setImageAutoSize(true);
   	      trayIcon.addActionListener(new ActionListener() {
   	        public void actionPerformed(ActionEvent e) {
   	          System.out.println("In here");
   	          trayIcon.displayMessage("Temperature Calibration!", "Diodes", TrayIcon.MessageType.INFO);
   	        }
   	      });

   	      try {
   	        tray.add(trayIcon);
   	      } catch (AWTException e) {
   	        System.err.println("TrayIcon could not be added.");
   	      }
   	    }
	}
	private void removeTrayIcon() {
		if (SystemTray.isSupported()) {
	   	      SystemTray tray = SystemTray.getSystemTray();
	   	      tray.remove(trayIcon);
		}

	}
    public void doAction(ActionRequest _req){
		try{
	    	/* Wrap request object with helper */
			ReqUtility reqUtil = new ReqUtility(_req);
			/* Create an Action object based on request parameters */
			Action action = reqUtil.getAction();
			System.out.println("action ===== " + action);
			//Set this Main Controller
			action.setMain(this);
			//Set Frame who has made the call
			action.setFrameWhoHasDoneTheRequest(_req.getFrameWhoHasTheRequest());
			//Execute the action
			action.execute(_req);
			/* Get appropriate view for action */
			ActionResult result = action.getResult();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error message = " +e.getMessage());
			System.out.println("Error cause = "+e.getCause());
			System.out.println("Localized message  = "+e.getLocalizedMessage());
		}
	}

    public void windowClosed(WindowEvent e) {
    	System.out.println("windowClosed");
    	if (e.getWindow().getName().equals("ThermalCalibrationProgramViewerFrame"))
    	{
			mainScreen.setEnabled(true);
			mainScreen.setFocusable(true);
		}
    	if (e.getWindow().getName().equals("ProgressScreenFrame"))
		{
			mainScreen.configureMainScreenMenuBarMode(STOP_PROGRAM_MODE);
		}
    	if (e.getWindow().getName().equals("Configuration_Frame"))
		{
    		mainScreen.setEnabled(true);
			mainScreen.setFocusable(true);
		}
    	if (e.getWindow().getName().equals("OvenManualOperationJFrame"))
		{
    		mainScreen.setEnabled(true);
			mainScreen.setFocusable(true);
		}
    }
    public void windowActivated(WindowEvent e) {
		System.out.println("windowActivated");
		// TODO Auto-generated method stub

	}
	public void windowClosing(WindowEvent e) {
		System.out.println("windowClosing");
		removeTrayIcon();
		// TODO Auto-generated method stub

	}
	public void windowDeactivated(WindowEvent e) {
		System.out.println("windowDeactivated");
		// TODO Auto-generated method stub

	}
	public void windowDeiconified(WindowEvent e) {
		System.out.println("windowDeiconified");
		// TODO Auto-generated method stub

	}
	public void windowIconified(WindowEvent e) {
		System.out.println("windowIconified");
		// TODO Auto-generated method stub

	}
	public void windowOpened(WindowEvent e) {
		System.out.println("windowOpened");
		if (e.getWindow().getName().equals("ThermalCalibrationProgramViewerFrame"))
		{
			mainScreen.setEnabled(false);
			mainScreen.setFocusable(false);
		}
		if (e.getWindow().getName().equals("ProgressScreenFrame"))
		{
			mainScreen.configureMainScreenMenuBarMode(RUN_PROGRAM_MODE);
		}
		if (e.getWindow().getName().equals("Configuration_Frame"))
		{
			mainScreen.setEnabled(false);
			mainScreen.setFocusable(false);
		}
		if (e.getWindow().getName().equals("OvenManualOperationJFrame"))
		{
			mainScreen.setEnabled(false);
			mainScreen.setFocusable(false);
		}
		System.out.println(e.getWindow().getName());
		// TODO Auto-generated method stub

	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("KeyPressed");
    	if (e.getSource().getClass().getName().equals("MainScreenFrame"))
    	{
			System.out.println(e.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent e) {
		System.out.println("KeyReleased");
		// TODO Auto-generated method stub

	}
	public void keyTyped(KeyEvent e) {
		System.out.println("KeyTyped");
		// TODO Auto-generated method stub

	}
    /**
	 * Title: printExceptionMessage
	 * Description: M�todo que imprime por pantalla un texto formateado
	 * 				indicando el tipo de excepci�n que ha ocurrido
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param  Exception e con la excepci�n lanzada
	 * @return none
	 * @throws Exception
	 */
	private void printExceptionMessage(Exception e){
		System.out.println(e.getMessage());
		System.out.println(e.getCause());
		e.printStackTrace();
	}
	/**
	 * Title: printMainControllerRequestedAction
	 * Description: M�todo que imprime por pantalla un texto formateado
	 * 				indicando el tipo de acci�n solicitada por el cliente
	 * Data: 09-06-2008
	 * @author David S�nchez S�nchez
	 * @param  String action con la acci�n a imprimir
	 * @return none
	 * @throws Exception
	 */
	private void printMainControllerRequestedAction(String _actionReq){
		int k=100;
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=((k/2)-(_actionReq.length()/2));i++){
			System.out.print("*");
		}
		System.out.print(_actionReq);
		for(int i=1;i<=((k/2)-(_actionReq.length()/2));i++){
			if((((k/2)-(_actionReq.length()/2))+_actionReq.length()+i)>100) break;
			System.out.print("*");
		}
		System.out.print("\n");
		for(int i=1;i<=k;i++){
			System.out.print("*");
		}
		System.out.println("\n");
	}
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        Diodes_Calibration_MainController framework = new Diodes_Calibration_MainController();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}


