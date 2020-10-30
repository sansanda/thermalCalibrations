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

package views;
/*
 * TextComponentDemo.java requires one additional file:
 *   DocumentSizeFilter.java
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import Main.MainController;

public class CalibrationSetUp_AboutContents_JFrame extends JFrame implements ActionListener{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    private JTextArea 	upperTextArea;
    private JTextArea 	lowerTextArea;
    private String lowerText = "";
    private String upperText = "";
    private GridBagConstraints constraints;
    private JMenu editMenu;
    private JMenu styleMenu;
    private JMenuBar mb;
    private Dimension frameDimension = new Dimension(350,250);
    private Dimension upperTextAreaDimension = new Dimension(350,100);
    private Dimension lowerTextAreaDimension = new Dimension(350,100);
    private MainController mainController;

    public CalibrationSetUp_AboutContents_JFrame(MainController _mainController) {

    	super("AboutContentsFrame");
        setName("AboutContentsFrame");
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(frameDimension);
		setSize(frameDimension);
		setResizable(false);
		mainController = _mainController;

        constraints = new GridBagConstraints ();

        //setText()
        setLowerText();
        setUpperText();

        //Get ContentPane and configure it
        Container content_pane = this.getContentPane();
		content_pane.setLayout(new GridBagLayout());
		createFrameComponents();
		setComponentsInFrame(content_pane);
        //Add some key bindings.
        addBindings();
    }
    private void setLowerText(){
    	lowerText = "Advertencia: este programa está protegido por las leyes\n" +
    				"de derechos de autor y otros tratados internacionales.\n" +
    				"La reproducción o distribución no autorizadas de este programa,\n" +
    				"o de cualquier parte del mismo, pueden dar lugar a\n" +
    				"penalizaciones tanto civiles como penales y serán objeto\n" +
    				"de todas las acciones judiciales que correspondan.";
    }
    private void setUpperText(){
    	upperText = "\n" +
    				"	Thermal Calibration Program \n" +
    				"	CNM -IMB -CSIC				\n" +
    				"	Author: David Sanchez Sanchez\n" +
    				"	Version: 6.0\n" +
    				"	Date: 30-10-2020\n" +
    				"	Id de Producto: 123456";
    }
    private void createFrameComponents(){
    	//Create the text Area and configure it.
        upperTextArea =  createUpperTextArea(upperText);
        lowerTextArea =  createLowerTextArea(lowerText);
        //Create menu bar
        //editMenu = createEditMenu();
        //styleMenu = createFileMenu();
        //mb = new JMenuBar();
    }
    private JTextArea createUpperTextArea(String _text){
   	 	JTextArea tA = new JTextArea();
        tA.setPreferredSize(upperTextAreaDimension);
        //tA.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tA.setBackground(getContentPane().getBackground());
        tA.setEditable(false);
        tA.setMargin(new Insets(5,5,5,5));
        tA.insert(_text, 0);
        return tA;
   }
    private JTextArea createLowerTextArea(String _text){
    	 JTextArea tA = new JTextArea();
         tA.setPreferredSize(lowerTextAreaDimension);
         tA.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
         tA.setBackground(getContentPane().getBackground());
         tA.setEditable(false);
         //tA.setMargin(new Insets(5,5,5,5));
         tA.insert(_text, 0);
         return tA;
    }
    private void setComponentsInFrame(Container _content_pane){
	 	//0,0
	    setComponentInGrid(_content_pane,upperTextArea,0,0,1,1,1,1,GridBagConstraints.PAGE_START,GridBagConstraints.NONE);
	    //Set up the menu bar.
	    setComponentInGrid(_content_pane,lowerTextArea,0,0,1,1,1,1,GridBagConstraints.PAGE_END,GridBagConstraints.NONE);

	    //mb.add(editMenu);
        //mb.add(styleMenu);
        //setJMenuBar(mb);
    }
    //Add a couple of emacs key bindings for navigation.
    protected void addBindings() {
        InputMap inputMap = lowerTextArea.getInputMap();

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
        menu.addSeparator();
        menu.addSeparator();
        menu.addSeparator();
        return menu;
    }

    //Create the style menu.
    protected JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        menu.addSeparator();
        menu.addSeparator();
        menu.addSeparator();
        return menu;
    }
    public void setComponentInGrid(Container content_pane, Component comp,int gridX, int gridY, int gridWidth, int gridHeight, int weightX, int weightY, int anchor, int fill  ){
	   	/*

		gridx, gridy
		Specify the row and column at the upper left of the component. The leftmost column has address gridx=0 and the top row has address gridy=0. Use GridBagConstraints.RELATIVE (the default value) to specify that the component be placed just to the right of (for gridx) or just below (for gridy) the component that was added to the container just before this component was added. We recommend specifying the gridx and gridy values for each component rather than just using GridBagConstraints.RELATIVE; this tends to result in more predictable layouts.

		gridwidth, gridheight
		Specify the number of columns (for gridwidth) or rows (for gridheight) in the component's display area. These constraints specify the number of cells the component uses, not the number of pixels it uses. The default value is 1. Use GridBagConstraints.REMAINDER to specify that the component be the last one in its row (for gridwidth) or column (for gridheight). Use GridBagConstraints.RELATIVE to specify that the component be the next to last one in its row (for gridwidth) or column (for gridheight). We recommend specifying the gridwidth and gridheight values for each component rather than just using GridBagConstraints.RELATIVE and GridBagConstraints.REMAINDER; this tends to result in more predictable layouts.
		Note: GridBagLayout does not allow components to span multiple rows unless the component is in the leftmost column or you have specified positive gridx and gridy values for the component.


		fill
		Used when the component's display area is larger than the component's requested size to determine whether and how to resize the component. Valid values (defined as GridBagConstraints constants) include NONE (the default), HORIZONTAL (make the component wide enough to fill its display area horizontally, but do not change its height), VERTICAL (make the component tall enough to fill its display area vertically, but do not change its width), and BOTH (make the component fill its display area entirely).

		ipadx, ipady
		Specifies the internal padding: how much to add to the size of the component. The default value is zero. The width of the component will be at least its minimum width plus ipadx*2 pixels, since the padding applies to both sides of the component. Similarly, the height of the component will be at least its minimum height plus ipady*2 pixels.

		insets
		Specifies the external padding of the component -- the minimum amount of space between the component and the edges of its display area. The value is specified as an Insets object. By default, each component has no external padding.

		anchor
		Used when the component is smaller than its display area to determine where (within the area) to place the component. Valid values (defined as GridBagConstraints constants) are CENTER (the default), PAGE_START, PAGE_END, LINE_START, LINE_END, FIRST_LINE_START, FIRST_LINE_END, LAST_LINE_END, and LAST_LINE_START.
		Here is a picture of how these values are interpreted in a container that has the default, left-to-right component orientation.

		-------------------------------------------------
		|FIRST_LINE_START   PAGE_START     FIRST_LINE_END|
		|                                                |
		|                                                |
		|LINE_START           CENTER             LINE_END|
		|                                                |
		|                                                |
		|LAST_LINE_START     PAGE_END       LAST_LINE_END|
		-------------------------------------------------

		--------------------------------------------------------------------------------
		Version note:  The PAGE_* and *LINE_* constants were introduced in 1.4. Previous releases require values named after points of the compass. For example, NORTHEAST indicates the top-right part of the display area. We recommend that you use the new constants, instead, since they enable easier localization.
		--------------------------------------------------------------------------------


		weightx, weighty
		Specifying weights is an art that can have a significant impact on the appearance of the components a GridBagLayout controls. Weights are used to determine how to distribute space among columns (weightx) and among rows (weighty); this is important for specifying resizing behavior.
		Unless you specify at least one non-zero value for weightx or weighty, all the components clump together in the center of their container. This is because when the weight is 0.0 (the default), the GridBagLayout puts any extra space between its grid of cells and the edges of the container.

		Generally weights are specified with 0.0 and 1.0 as the extremes: the numbers in between are used as necessary. Larger numbers indicate that the component's row or column should get more space. For each column, the weight is related to the highest weightx specified for a component within that column, with each multicolumn component's weight being split somehow between the columns the component is in. Similarly, each row's weight is related to the highest weighty specified for a component within that row. Extra space tends to go toward the rightmost column and bottom row.


	   	 */

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
        final CalibrationSetUp_AboutContents_JFrame frame = new CalibrationSetUp_AboutContents_JFrame(null);
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


