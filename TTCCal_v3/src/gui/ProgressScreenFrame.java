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

package gui;
/*
 * TextComponentDemo.java requires one additional file:
 *   DocumentSizeFilter.java
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;

import Data.ThermalCalibrationProgramData;

public class ProgressScreenFrame extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextPane	textPane;
    JTextArea 	changeLog;
    String[]	tableColumnNames;
    JTable 		calibrationResultTable;
    DefaultTableModel defaultTableModel;
    
    private JProgressBar progressBar;
    GridBagConstraints constraints;


    public ProgressScreenFrame(ThermalCalibrationProgramData _program, int _nTableRows, int _nTableColumns) {
        
    	super("ProgressScreenFrame");
        setName("ProgressScreenFrame");
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
        constraints = new GridBagConstraints ();
        
        //Create table and Init table
        createCalibrationResultTable(_program, _nTableRows, _nTableColumns);
        
        //Create the text pane and configure it.
        textPane = new JTextPane();
        textPane.setCaretPosition(0);
        textPane.setMargin(new Insets(5,5,5,5));
        
        
        JScrollPane scrollPane = new JScrollPane(calibrationResultTable);
        scrollPane.setPreferredSize(new Dimension(1000, 600));
       
        //Create the text area for the status log and configure it.
        changeLog = new JTextArea(5, 30);
        changeLog.setEditable(false);
        JScrollPane scrollPaneForLog = new JScrollPane(changeLog);
        scrollPaneForLog.setPreferredSize(new Dimension(1000, 200));
        
        
        //Create a split pane for the change log and the text area.
        JSplitPane splitPane = new JSplitPane(
                                       JSplitPane.VERTICAL_SPLIT,
                                       scrollPane, scrollPaneForLog);
        splitPane.setOneTouchExpandable(true);

        //Create the progress Bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        //Create the progress area.
        JPanel statusPane = new JPanel(new GridLayout(1, 2));
        JLabel calibrationProgressLabel =
                new JLabel("Calibration Progress");
        statusPane.add(calibrationProgressLabel);
        setComponentInGrid(statusPane, calibrationProgressLabel, 1, 1, 1, 1, 1, 1,  GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL);
        setComponentInGrid(statusPane, progressBar, 1, 2, 1, 1, 1, 1,  GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL);
        progressBar.setValue(0);

        //Add the components.
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusPane, BorderLayout.PAGE_END);

        //Set up the menu bar.
        //actions=createActionTable(textPane);
        JMenu editMenu = createEditMenu();
        JMenu styleMenu = createStyleMenu();
        JMenuBar mb = new JMenuBar();
        mb.add(editMenu);
        mb.add(styleMenu);
        setJMenuBar(mb);

        //Add some key bindings.
        addBindings();
        
        
    	
    }
    private void createCalibrationResultTable(ThermalCalibrationProgramData _program, int _rows, int _columns){
    	defaultTableModel = new DefaultTableModel();
    	defaultTableModel.setColumnCount(_columns);
    	defaultTableModel.setRowCount(_rows);
    	calibrationResultTable = new JTable(defaultTableModel);
    	calibrationResultTable.setFillsViewportHeight(true);
    }
    public void appendTextToLogPane(String _newLine){
    	changeLog.append(_newLine+"\n");	
    }
    private void insertRowAt(Object[] _data, int _row){
    	defaultTableModel.insertRow(_row, _data);
    }
    public void refreshCalibrationResultTableData(Object[][] _data){
    	for (int i=0;i<_data.length;i++){
    		insertRowAt(_data[i], i);
    	}
    }
    public void refreshProgressBarData(int _progress){
    	progressBar.setValue(_progress);
    }
    //Add a couple of emacs key bindings for navigation.
    protected void addBindings() {
        InputMap inputMap = textPane.getInputMap();

        //Ctrl-b to go backward one character
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.backwardAction);

        //Ctrl-f to go forward one character
        key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.forwardAction);

        //Ctrl-p to go up one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.upAction);

        //Ctrl-n to go down one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.downAction);
    }

    //Create the edit menu.
    protected JMenu createEditMenu() {
        JMenu menu = new JMenu("Edit");
        return menu;
    }

    //Create the style menu.
    protected JMenu createStyleMenu() {
        JMenu menu = new JMenu("Style");

        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        menu.add(action);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        menu.add(action);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        menu.add(action);

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontSizeAction("12", 12));
        menu.add(new StyledEditorKit.FontSizeAction("14", 14));
        menu.add(new StyledEditorKit.FontSizeAction("18", 18));

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontFamilyAction("Serif",
                                                      "Serif"));
        menu.add(new StyledEditorKit.FontFamilyAction("SansSerif",
                                                      "SansSerif"));

        menu.addSeparator();

        menu.add(new StyledEditorKit.ForegroundAction("Red",
                                                      Color.red));
        menu.add(new StyledEditorKit.ForegroundAction("Green",
                                                      Color.green));
        menu.add(new StyledEditorKit.ForegroundAction("Blue",
                                                      Color.blue));
        menu.add(new StyledEditorKit.ForegroundAction("Black",
                                                      Color.black));

        return menu;
    }
    public void setComponentInGrid(Container content_pane, Component comp,int gridX, int gridY, int gridWidth, int gridHeight, int weightX, int weightY, int anchor, int fill  ){
	   	constraints.gridx = gridX; 
	    constraints.gridy = gridY; 
	    constraints.gridwidth = gridWidth; 
	    constraints.gridheight = gridHeight; 
	    constraints.weighty = weightY; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = weightX; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.fill =fill;
	    constraints.anchor = anchor;
	    content_pane.add(comp, constraints);
	    constraints.fill = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.anchor = GridBagConstraints.CENTER; // Restauramos valores por defecto
	    constraints.weighty = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.
	    constraints.weightx = 0.0; // Restauramos al valor por defecto, para no afectar a los siguientes componentes.	
  }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        final ProgressScreenFrame frame = new ProgressScreenFrame(null,0,5);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //The standard main method.
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
		createAndShowGUI();
            }
        });
    }
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}


