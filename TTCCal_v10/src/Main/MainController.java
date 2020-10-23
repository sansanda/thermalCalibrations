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

import frontController.Action;
import frontController.ActionRequest;
import frontController.ActionResult;
import frontController.ReqUtility;
import gui.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;


/* Framework.java requires no other files. */
public class MainController extends WindowAdapter implements KeyListener{

    private Point lastLocation = null;
    private MainScreenFrame mainScreen;

    public MainController() {
        makeMainWindow();
    }
    public void doAction(ActionRequest _req){
		try{
	    	/* Wrap request object with helper */
			ReqUtility reqUtil = new ReqUtility(_req);
			/* Create an Action object based on request parameters */
			Action action = reqUtil.getAction();
			//Set this Main Controller
			action.setMain(this);
			//Set Frame who has made the call
			action.setFrameWhoHasDoneTheRequest(_req.getFrameWhoHasTheRequest());
			//Execute the action
			action.execute(_req);
			/* Get appropriate view for action */
			ActionResult result = action.getResult();
		}catch(Exception e) {
			printExceptionMessage(e);
		}
	}
    public void makeMainWindow() {

        mainScreen = new MainScreenFrame(this);
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
    public void windowClosed(WindowEvent e) {
    	System.out.println("windowClosed");
    	if (e.getWindow().getName().equals("TTCProgramViewerFrame"))
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
		if (e.getWindow().getName().equals("TTCProgramViewerFrame"))
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
    	if (e.getSource().getClass().getName().equals("ProgressScreenFrame"))
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
	 * Description: Método que imprime por pantalla un texto formateado
	 * 				indicando el tipo de excepción que ha ocurrido
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param  Exception e con la excepción lanzada
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
	 * Description: Método que imprime por pantalla un texto formateado
	 * 				indicando el tipo de acción solicitada por el cliente
	 * Data: 09-06-2008
	 * @author David Sánchez Sánchez
	 * @param  String action con la acción a imprimir
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
        MainController framework = new MainController();
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


