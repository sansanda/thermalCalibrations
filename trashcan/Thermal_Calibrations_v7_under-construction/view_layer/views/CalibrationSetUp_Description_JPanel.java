package views;

import java.awt.Color;

import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import calibrationSetUp.CalibrationSetUp_Description;



public class CalibrationSetUp_Description_JPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel 		programDescriptionLabel;
	private JLabel	 	programAuthorLabel;
	private JLabel 		programNameLabel;
	private JLabel 		temperatureProfileConfigurationLabel1;
	private JTextField 	programNameTextField;
	private JTextField 	programAuthorTextField;
	private JTextArea 	programDescriptionTextArea;

	/**
	 * This is the default constructor
	 */
	public CalibrationSetUp_Description_JPanel(
			String _panelName,
			String _programName,
			String _programAuthor,
			String _programDescription)
	{
		super();
		initialize(_panelName,_programName,_programAuthor,_programDescription);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(String panelName,String programName,String _programAuthor,String _programDescription) {
		this.setBounds(new Rectangle(30, 180, 655, 289));
		this.setName(panelName);
		this.setLayout(null);
		this.add(createTemperatureProfileConfigurationLabel1(), null);
		this.add(createProgramNameLabel(), null);
		this.add(createProgramAuthorLabel(), null);
		this.add(createProgramDescriptionLabel(), null);
		this.add(createProgramNameTextField(programName), null);
		this.add(createProgramAuthorTextField(_programAuthor), null);
		this.add(createProgramDescriptionTextArea(_programDescription), null);
		this.setDefaultDescriptionData();
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 * @return the programAuthorLabel
	 */
	private JLabel createProgramAuthorLabel() {
		if(programAuthorLabel==null){
			programAuthorLabel = new JLabel();
			programAuthorLabel.setBounds(new Rectangle(30, 135, 91, 31));
			programAuthorLabel.setText("Author");
			programAuthorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return programAuthorLabel;
	}
	/**
	 * @return the programDescriptionLabel
	 */
	private JLabel createProgramDescriptionLabel() {
		if (programDescriptionLabel == null) {
			programDescriptionLabel = new JLabel();
			programDescriptionLabel.setBounds(new Rectangle(30, 180, 91, 76));
			programDescriptionLabel.setText("Description");
			programDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return programDescriptionLabel;
	}

	/**
	 * @return the programNameLabel
	 */
	private JLabel createProgramNameLabel() {
		if (programNameLabel==null){
			programNameLabel = new JLabel();
			programNameLabel.setBounds(new Rectangle(30, 90, 91, 31));
			programNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			programNameLabel.setText("Program Name");
		}
		return programNameLabel;
	}

	/**
	 * @return the temperatureProfileConfigurationLabel1
	 */
	private JLabel createTemperatureProfileConfigurationLabel1() {
		if (temperatureProfileConfigurationLabel1==null){
			temperatureProfileConfigurationLabel1 = new JLabel();
			temperatureProfileConfigurationLabel1.setBounds(new Rectangle(30, 30, 601, 31));
			temperatureProfileConfigurationLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel1.setText("TTC SET-UP DESCRIPTION");
			temperatureProfileConfigurationLabel1.setBackground(new Color(204, 204, 255));
		}
		return temperatureProfileConfigurationLabel1;
	}

	/**
	 * This method initializes programNameTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField createProgramNameTextField(String name) {
		if (programNameTextField == null) {
			programNameTextField = new JTextField();
			programNameTextField.setBounds(new Rectangle(135, 90, 496, 31));
			programNameTextField.setText(name);
			programNameTextField.setHorizontalAlignment(JTextField.LEFT);

		}
		return programNameTextField;
	}
	/**
	 * This method initializes programAuthorTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField createProgramAuthorTextField(String name) {
		if (programAuthorTextField == null) {
			programAuthorTextField = new JTextField();
			programAuthorTextField.setBounds(new Rectangle(135, 135, 496, 31));
			programAuthorTextField.setHorizontalAlignment(JTextField.LEFT);
			programAuthorTextField.setText(name);
		}
		return programAuthorTextField;
	}
	/**
	 * This method initializes programDescriptionTextArea
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea createProgramDescriptionTextArea(String name) {
		if (programDescriptionTextArea == null) {
			programDescriptionTextArea = new JTextArea();
			programDescriptionTextArea.setBounds(new Rectangle(135, 180, 496, 76));
			programDescriptionTextArea.setLineWrap(false);
			programDescriptionTextArea.setRows(10);
			programDescriptionTextArea.setText(name);
		}
		return programDescriptionTextArea;
	}
	private void setDefaultDescriptionData(){
		this.programNameTextField.setText("Program1");
		this.programAuthorTextField.setText("Author x");
		this.programDescriptionTextArea.setText("Program for calibrating Thermal Test Chips.");
	}
	public void loadDescriptionData(CalibrationSetUp_Description _TTCSetUpDescriptionData){
		this.programNameTextField.setText((String)_TTCSetUpDescriptionData.getCalibrationSetUp_Name());
		this.programAuthorTextField.setText((String)_TTCSetUpDescriptionData.getCalibrationSetUp_Author());
		this.programDescriptionTextArea.setText((String)_TTCSetUpDescriptionData.getCalibrationSetUp_Description());
	}
	public CalibrationSetUp_Description getTTCSetUpDescriptionData() throws Exception{
		if (!this.validateDescriptionData()){
			return null;
		}
		return new CalibrationSetUp_Description(this.programNameTextField.getText(),this.programAuthorTextField.getText(),this.programDescriptionTextArea.getText());  //  @jve:decl-index=0:
	}
	public boolean validateDescriptionData(){
		if (programNameTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para el programa a crear.");
			return false;
		}
		if (programAuthorTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir un nombre para author del programa a crear.");
			return false;
		}
		if (programDescriptionTextArea.getText().equals("")){
			JOptionPane.showMessageDialog(null,"Debe introducir la descripción del programa a crear.");
			return false;
		}
		return true;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
