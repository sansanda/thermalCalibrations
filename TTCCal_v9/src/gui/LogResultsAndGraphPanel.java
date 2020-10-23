package gui;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;

public class LogResultsAndGraphPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JTextArea jTextArea = null;
	private JTextArea jTextArea1 = null;
	/**
	 * This is the default constructor
	 */
	public LogResultsAndGraphPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize() {
		this.setSize(587, 383);

		this.setEnabled(true);
		this.setName("");
		this.setToolTipText("");
		this.addTab(null, null, getJSplitPane(), null);
		this.addTab(null, null, getJPanel(), null);
	}

	/**
	 * This method initializes jSplitPane
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setTopComponent(getJTextArea());
			jSplitPane.setBottomComponent(getJTextArea1());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jTextArea1
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
		}
		return jTextArea1;
	}

}  //  @jve:decl-index=0:visual-constraint="124,92"
