package view_CommonParts;

import java.awt.Color;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Data.TTCSetUpData;
import Data.TemperatureProfileData;

public class Thermal_Calibration_SetUp_Temperature_Profile_JPanel extends JPanel implements ActionListener {

	//Constants
	private static final long 	serialVersionUID = 1L;
	private static final int 	MAX_TEMP_OVEN = 500;
	private static final int 	N_TEMPERATURE_VALUES = 500;
	private static final int 	MIN_TEMP_OVEN = 0;

	//Variables
	private JLabel 		minTempLabel = null;
	private JComboBox 	minTempComboBox = null;
	private JLabel 		maxTempLabel = null;
	private JTextField 	maxTempTextField = null;
	private JLabel 		nStepsLabel = null;
	private JComboBox 	nStepsComboBox = null;
	private JLabel 		stepValueLabel = null;
	private JComboBox 	stepValueComboBox = null;
	private JButton 	clearTemperatureProfileButton = null;
	private JLabel 		ttcsToCalibrateConfigurationLabel = null;
	private JLabel 		temperatureProfileConfigurationLabel1 = null;
	private JLabel 		temperatureProfileConfigurationLabel;

	private JButton 	mark0To100TemperatureProfileButton = null;
	private JButton 	mark0To125TemperatureProfileButton = null;
	private JButton 	mark0To150TemperatureProfileButton = null;
	private JButton 	mark0To175TemperatureProfileButton = null;
	private JButton 	mark0To200TemperatureProfileButton = null;
	private JButton 	mark0To225TemperatureProfileButton = null;
	private JButton 	mark0To250TemperatureProfileButton = null;

	private List 		temperatureProfileList = null;

	private ItemListener 	temperatureProfileListItemListener = null;
	private ItemListener 	minTempComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	nStepsComboBoxItemListener = null;  //  @jve:decl-index=0:
	private ItemListener 	stepValueComboBoxItemListener = null;  //  @jve:decl-index=0:


	/**
	 * This is the default constructor
	 */
	public Thermal_Calibration_SetUp_Temperature_Profile_JPanel(String _panelName) {
		super();
		initialize(_panelName);
	}

	/**
	 * This method initializes this
	 *
	 * @return void
	 */
	private void initialize(String _panelName) {
		this.setBounds(new Rectangle(30, 30, 557, 406));
		this.setName(_panelName);
		this.setLayout(null);

		this.add(createTemperatureProfileConfigurationLabel(), null);
		addPredefinedProfilesButtonsToTemperatureProfilePanel(this);
		addTemperatureProfileListToTemperatureProfilePanel(this);
		addButtonsForDefiningManualTemperatureProfileToTemperatureProfilePanel(this);

		this.setDefaultTempProfileData();

	}
	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addButtonsForDefiningManualTemperatureProfileToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		_temperatureProfilePanel.add(this.createMinTempLabel(), null);
		_temperatureProfilePanel.add(getMinTempComboBox(), null);
		_temperatureProfilePanel.add(this.createMaxTempLabel(), null);
		_temperatureProfilePanel.add(getMaxTempTextField(), null);
		_temperatureProfilePanel.add(this.createnStepsLabel(), null);
		_temperatureProfilePanel.add(getNStepsComboBox(), null);
		_temperatureProfilePanel.add(this.creatStepValueLabel(), null);
		_temperatureProfilePanel.add(getStepValueComboBox(), null);
		_temperatureProfilePanel.add(getClearTemperatureProfileButton(), null);
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addTemperatureProfileListToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		_temperatureProfilePanel.add(getTemperatureProfileList(), null);
	}
	/**
	 *
	 * @param _temperatureProfilePanel
	 */
	private void addPredefinedProfilesButtonsToTemperatureProfilePanel(JPanel _temperatureProfilePanel) {
		_temperatureProfilePanel.add(getMark0To100TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To125TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To150TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To175TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To200TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To225TemperatureProfileButton(), null);
		_temperatureProfilePanel.add(getMark0To250TemperatureProfileButton(), null);
	}



	private JLabel creatStepValueLabel (){
		if (stepValueLabel==null){
			stepValueLabel = new JLabel();
			stepValueLabel.setBounds(new Rectangle(345, 210, 76, 31));
			stepValueLabel.setText("Step Value");
			stepValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return stepValueLabel;
	}
	private JLabel createnStepsLabel (){
		if (nStepsLabel==null){
			nStepsLabel = new JLabel();
			nStepsLabel.setBounds(new Rectangle(345, 165, 76, 31));
			nStepsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nStepsLabel.setText("N Steps");
		}
		return nStepsLabel;
	}
	private JLabel createMaxTempLabel (){
		if (maxTempLabel==null){
			maxTempLabel = new JLabel();
			maxTempLabel.setBounds(new Rectangle(345, 120, 76, 31));
			maxTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			maxTempLabel.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			maxTempLabel.setText("Max Temp");
		}
		return maxTempLabel;
	}
	private JLabel createMinTempLabel (){
		if (minTempLabel==null){
			minTempLabel = new JLabel();
			minTempLabel.setBounds(new Rectangle(345, 75, 76, 31));
			minTempLabel.setHorizontalAlignment(SwingConstants.CENTER);
			minTempLabel.setText("Min Temp");
		}
		return minTempLabel;
	}
	private JLabel createTemperatureProfileConfigurationLabel (){
		if (temperatureProfileConfigurationLabel==null){
			temperatureProfileConfigurationLabel = new JLabel();
			temperatureProfileConfigurationLabel.setBounds(new Rectangle(30, 30, 601, 31));
			temperatureProfileConfigurationLabel.setBackground(new Color(204, 204, 255));
			temperatureProfileConfigurationLabel.setHorizontalAlignment(SwingConstants.CENTER);
			temperatureProfileConfigurationLabel.setText("TEMPERATURE PROFILE CONFIGURATION");
		}
		return temperatureProfileConfigurationLabel;
	}
	/**
	 * This method initializes temperatureProfileList
	 *
	 * @return java.awt.List
	 */
	private List getTemperatureProfileList() {
		if (temperatureProfileList == null) {
			temperatureProfileList = new List(10,true);
			temperatureProfileList.setBounds(new Rectangle(180, 75, 151, 301));
			temperatureProfileList.setBackground(new Color(238, 238, 238));
			for (int i=MIN_TEMP_OVEN;i<=MAX_TEMP_OVEN;i++){
		    	temperatureProfileList.addItem(Integer.toString(i));
		    }
			temperatureProfileListItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
	        		int nSelectedItems=0;

	        		String[] selectedItems = temperatureProfileList.getSelectedItems();
	        		nSelectedItems = selectedItems.length;
		        	if (nSelectedItems<=0){
		        		setDefaultTempProfileData();
		        	}else
		        	{
		        		removeTemperatureProfileItemListeners();
		        		nStepsComboBox.setSelectedItem(String.valueOf(nSelectedItems));
		        		maxTempTextField.setText(selectedItems[selectedItems.length-1]);
		        		minTempComboBox.setSelectedItem(selectedItems[0]);
		        		addTemperatureProfileItemListeners();
		        	}
		        }
		      };
		      temperatureProfileList.addItemListener(this.temperatureProfileListItemListener);
		}
		return temperatureProfileList;
	}
	/**
	 * This method initializes clearTemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getClearTemperatureProfileButton() {
		if (clearTemperatureProfileButton == null) {
			clearTemperatureProfileButton = new JButton("Clear Temp Profile");
			clearTemperatureProfileButton.setBounds(new Rectangle(345, 345, 166, 31));
			clearTemperatureProfileButton.setText("Clear Temp Profile");
			clearTemperatureProfileButton.addActionListener(this);
		}
		return clearTemperatureProfileButton;
	}
	private void fillTemperatureProfileList(){

		  int temperatureProfileMinTemp = Integer.valueOf((String)minTempComboBox.getSelectedItem());
		  int temperatureProfileNSteps = Integer.valueOf((String)nStepsComboBox.getSelectedItem());
		  int stepsValue = Integer.valueOf((String)stepValueComboBox.getSelectedItem());
		  if (((temperatureProfileNSteps-1)*stepsValue + temperatureProfileMinTemp)>MAX_TEMP_OVEN){
			JOptionPane.showMessageDialog(null,"El valor maximo de la rampa de temperaturas no puede superar la temperatura máxima del horno.");
			setDefaultTempProfileData();
			clearTemperatureProfileList();
			return;
		  }else
		  {
			  temperatureProfileList.select(temperatureProfileMinTemp);
			  maxTempTextField.setText(String.valueOf((temperatureProfileNSteps-1)*stepsValue + temperatureProfileMinTemp));
			  for (int i=0;i<temperatureProfileNSteps;i++){
					 temperatureProfileList.select(i*stepsValue + temperatureProfileMinTemp);
			  }
		  }
	  }
	  private void reFillTemperatureProfileList(){
		  clearTemperatureProfileList();
		  fillTemperatureProfileList();
	  }
	  private void clearTemperatureProfileList(){
		  for (int i=0;i<=MAX_TEMP_OVEN;i++){temperatureProfileList.deselect(i);}
	  }

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To100TemperatureProfileButton() {
		if (mark0To100TemperatureProfileButton == null) {
			mark0To100TemperatureProfileButton = new JButton("0-100 Step-5ºC");
			mark0To100TemperatureProfileButton.addActionListener(this);
			mark0To100TemperatureProfileButton.setBounds(new Rectangle(30, 75, 121, 31));
		}
		return mark0To100TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To125TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To125TemperatureProfileButton() {
		if (mark0To125TemperatureProfileButton == null) {
			mark0To125TemperatureProfileButton = new JButton("0-125 Step-5ºC");
			mark0To125TemperatureProfileButton.addActionListener(this);
			mark0To125TemperatureProfileButton.setBounds(new Rectangle(30, 120, 121, 31));
		}
		return mark0To125TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To150TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To150TemperatureProfileButton() {
		if (mark0To150TemperatureProfileButton == null) {
			mark0To150TemperatureProfileButton = new JButton("0-150 Step-5ºC");
			mark0To150TemperatureProfileButton.addActionListener(this);
			mark0To150TemperatureProfileButton.setBounds(new Rectangle(30, 165, 121, 31));
		}
		return mark0To150TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To175TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To175TemperatureProfileButton() {
		if (mark0To175TemperatureProfileButton == null) {
			mark0To175TemperatureProfileButton = new JButton("0-175 Step-5ºC");
			mark0To175TemperatureProfileButton.addActionListener(this);
			mark0To175TemperatureProfileButton.setBounds(new Rectangle(30, 210, 121, 31));
		}
		return mark0To175TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To200TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To200TemperatureProfileButton() {
		if (mark0To200TemperatureProfileButton == null) {
			mark0To200TemperatureProfileButton = new JButton("0-200 Step-5ºC");
			mark0To200TemperatureProfileButton.addActionListener(this);
			mark0To200TemperatureProfileButton.setBounds(new Rectangle(30, 255, 121, 31));
		}
		return mark0To200TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To225TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To225TemperatureProfileButton() {
		if (mark0To225TemperatureProfileButton == null) {
			mark0To225TemperatureProfileButton = new JButton("0-225 Step-5ºC");
			mark0To225TemperatureProfileButton.setBounds(new Rectangle(30, 300, 118, 31));
			mark0To225TemperatureProfileButton.addActionListener(this);
		}
		return mark0To225TemperatureProfileButton;
	}

	/**
	 * This method initializes mark0To250TemperatureProfileButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getMark0To250TemperatureProfileButton() {
		if (mark0To250TemperatureProfileButton == null) {
			mark0To250TemperatureProfileButton = new JButton("0-250 Step-5ºC");
			mark0To250TemperatureProfileButton.setBounds(new Rectangle(30, 345, 118, 31));
			mark0To250TemperatureProfileButton.addActionListener(this);
		}
		return mark0To250TemperatureProfileButton;
	}

	/**
	 * This method initializes minTempComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getMinTempComboBox() {
		if (minTempComboBox == null) {
			minTempComboBox = new JComboBox();
			minTempComboBox.setBounds(new Rectangle(435, 75, 76, 31));
			for (int i=MIN_TEMP_OVEN;i<=N_TEMPERATURE_VALUES;i++){
				minTempComboBox.addItem (Integer.toString(i));
			}
			minTempComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	reFillTemperatureProfileList();
		        }
		     };
			minTempComboBox.addItemListener(minTempComboBoxItemListener);
		}
		return minTempComboBox;
	}

	/**
	 * This method initializes maxTempTextField
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getMaxTempTextField() {
		if (maxTempTextField == null) {
			maxTempTextField = new JTextField();
			maxTempTextField.setBounds(new Rectangle(435, 120, 76, 31));
			maxTempTextField.setBackground(new Color(238, 238, 238));
			maxTempTextField.setEnabled(false);
		}
		return maxTempTextField;
	}

	/**
	 * This method initializes nStepsComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getNStepsComboBox() {
		if (nStepsComboBox == null) {
			nStepsComboBox = new JComboBox();
			nStepsComboBox.setBounds(new Rectangle(435, 165, 76, 31));
			for (int i=1;i<=(N_TEMPERATURE_VALUES+1);i++){
				nStepsComboBox.addItem (Integer.toString(i));
			}
			nStepsComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	switch(Integer.parseInt((String)stepValueComboBox.getSelectedItem())){
		        		case(0):
							JOptionPane.showMessageDialog(null,"Debe indicar primero un valor superior a 0 para Step.");
							clearTemperatureProfileList();
							setDefaultTempProfileData();
				        	break;
		        		default:
		        			reFillTemperatureProfileList();
		        	}
		        	reFillTemperatureProfileList();
		        }
		      };
			nStepsComboBox.addItemListener(nStepsComboBoxItemListener);
		}
		return nStepsComboBox;
	}

	/**
	 * This method initializes stepValueComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getStepValueComboBox() {
		if (stepValueComboBox == null) {
			stepValueComboBox = new JComboBox();
			stepValueComboBox.setBounds(new Rectangle(435, 210, 76, 31));
			for (int i=MIN_TEMP_OVEN;i<=N_TEMPERATURE_VALUES;i++){
				stepValueComboBox.addItem (Integer.toString(i));
			}
			stepValueComboBoxItemListener = new ItemListener()
		    {
		        public void itemStateChanged(ItemEvent e)
		        {
		        	if(((String)stepValueComboBox.getSelectedItem()).equals("0")){
						setDefaultTempProfileData();
					}
		        	reFillTemperatureProfileList();
		        }
		      };
		      stepValueComboBox.addItemListener(stepValueComboBoxItemListener);
		}
		return stepValueComboBox;
	}

	private void removeTemperatureProfileItemListeners(){
		minTempComboBox.removeItemListener(minTempComboBoxItemListener);
		nStepsComboBox.removeItemListener(nStepsComboBoxItemListener);
		temperatureProfileList.removeItemListener(temperatureProfileListItemListener);
		stepValueComboBox.removeItemListener(stepValueComboBoxItemListener);
	}
	private void addTemperatureProfileItemListeners(){
		stepValueComboBox.addItemListener(stepValueComboBoxItemListener);
		temperatureProfileList.addItemListener(temperatureProfileListItemListener);
		nStepsComboBox.addItemListener(nStepsComboBoxItemListener);
		minTempComboBox.addItemListener(minTempComboBoxItemListener);
	}
	public void actionPerformed (ActionEvent event) {
		  String cmd = event.getActionCommand ();
		  System.out.println(cmd);
		  if (cmd.equals("Cancel")) {}
		  if (cmd.equals("0-100 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("21");
			  maxTempTextField.setText("100");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-125 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("26");
			  maxTempTextField.setText("125");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-150 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("31");
			  maxTempTextField.setText("150");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-175 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("36");
			  maxTempTextField.setText("175");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-200 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("41");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-225 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("46");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("0-250 Step-5ºC")){
			  minTempComboBox.setSelectedItem("0");
			  stepValueComboBox.setSelectedItem("5");
			  nStepsComboBox.setSelectedItem("51");
			  reFillTemperatureProfileList();
		  }
		  if (cmd.equals("Clear Temp Profile")){
			  clearTemperatureProfileList();
			  setDefaultTempProfileData();
		  }
	  }
	private void setDefaultTempProfileData(){
			removeTemperatureProfileItemListeners();
			minTempComboBox.setSelectedItem("0");
			maxTempTextField.setText("0");
			nStepsComboBox.setSelectedItem("1");
			temperatureProfileList.select(0);
			stepValueComboBox.setSelectedItem("0");
			addTemperatureProfileItemListeners();
	}
	public void loadTemperatureProfileData(TemperatureProfileData _temperatureProfileData){
		removeTemperatureProfileItemListeners();
		maxTempTextField.setText(String.valueOf(_temperatureProfileData.getTemperatures()[_temperatureProfileData.getTemperatures().length-1]));
		minTempComboBox.setSelectedItem(String.valueOf(_temperatureProfileData.getTemperatures()[0]));
		nStepsComboBox.setSelectedItem(String.valueOf(_temperatureProfileData.getTemperatures().length));
		int[] temperatures = _temperatureProfileData.getTemperatures();
		for (int i=0;i<temperatures.length;i++){
			temperatureProfileList.select(temperatures[i]);
		}
		addTemperatureProfileItemListeners();
	}
	public TemperatureProfileData getTemperatureProfileData() throws NumberFormatException, Exception{
		if (!this.validateTemperatureProfileData()){
			return null;
		}
	  Object[] temperaturesList = temperatureProfileList.getSelectedObjects();
	  int[] temperatures = new int[temperaturesList.length];
	  for (int i=0;i<temperaturesList.length;i++){
		  temperatures[i] = Integer.parseInt((String)temperaturesList[i]);
	  }
	  return new TemperatureProfileData("TemperatureProfileData_1",temperatures);

	}
	public boolean validateTemperatureProfileData(){
		return true;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
