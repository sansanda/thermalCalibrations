package gui;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import javax.swing.JTextField;
import java.awt.Color;

public class ProgressScreenPanel4 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollBar totalProgressInPercent = null;
	private JTextField timeElapsed = null;
	private JTextField timeRemaining = null;

	/**
	 * This is the default constructor
	 */
	public ProgressScreenPanel4() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(469, 33);
		this.setLayout(null);
		this.add(getTotalProgressInPercent(), null);
		this.add(getTimeElapsed(), null);
		this.add(getTimeRemaining(), null);
	}

	/**
	 * This method initializes totalProgressInPercent
	 *
	 * @return javax.swing.JScrollBar
	 */
	private JScrollBar getTotalProgressInPercent() {
		if (totalProgressInPercent == null) {
			totalProgressInPercent = new JScrollBar();
			totalProgressInPercent.setOrientation(JScrollBar.HORIZONTAL);
			totalProgressInPercent.setBounds(new Rectangle(237, 8, 220, 17));
		}
		return totalProgressInPercent;
	}

	/**
	 * This method initializes timeElapsed
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTimeElapsed() {
		if (timeElapsed == null) {
			timeElapsed = new JTextField();
			timeElapsed.setBounds(new Rectangle(12, 6, 94, 20));
			timeElapsed.setBackground(new Color(238, 238, 238));
		}
		return timeElapsed;
	}

	/**
	 * This method initializes timeRemaining
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getTimeRemaining() {
		if (timeRemaining == null) {
			timeRemaining = new JTextField();
			timeRemaining.setBounds(new Rectangle(118, 6, 107, 20));
			timeRemaining.setBackground(new Color(238, 238, 238));
		}
		return timeRemaining;
	}

}  //  @jve:decl-index=0:visual-constraint="164,149"
