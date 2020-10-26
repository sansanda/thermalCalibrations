package sourcemeters;
import instrumentsWithRS232.*;


public class Keithley2430 extends instrumentWithRS232_32bits{
	//Constants
	//Variables

	//default constructor
	public Keithley2430(String wantedPortName)throws Exception{
		super(wantedPortName);
	}
	//Getters and Setters
	//Other Methods
	public float read4WireResistance() throws Exception{
		System.out.println("Reading the instantaneous 4-WIRE RESISTANCE in K2430....");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("FUNC 'RES'");
		sendMessageToSerialPort("SENS:RES:OCOM ON");
		sendMessageToSerialPort("RES:MODE AUTO");
		sendMessageToSerialPort("RES:RANG 20E1");
		sendMessageToSerialPort("SYST:RSEN ON");
		sendMessageToSerialPort("FORM:ELEM RES");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");

		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");
		return res;
	}
	public float read2WireResistance() throws Exception{
		System.out.println("Reading the instantaneous 2-WIRE RESISTANCE in K2430....");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("FUNC 'RES'");
		sendMessageToSerialPort("SENS:RES:OCOM ON");
		sendMessageToSerialPort("RES:MODE AUTO");
		sendMessageToSerialPort("RES:RANG 20E1");
		sendMessageToSerialPort("SYST:RSEN OFF");
		sendMessageToSerialPort("FORM:ELEM RES");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");
		return res;
	}
	public float measureVoltage() throws Exception{
		System.out.println("Reading the instantaneous VOLTAGE in K2430....");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("SOUR:FUNC CURR");
		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
		sendMessageToSerialPort("SOUR:CURR:RANG MIN");
		sendMessageToSerialPort("SOUR:CURR:LEV 0");
		sendMessageToSerialPort("SENS:VOLT:PROT 25");
		sendMessageToSerialPort("SENS:VOLT:RANG 20");
		sendMessageToSerialPort("FORM:ELEM VOLT");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");
		return res;
	}
	public float measureCurrent() throws Exception{

		System.out.println("Reading the instantaneous CURRENT in K2430....");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");					//
		sendMessageToSerialPort("SOUR:FUNC VOLT");			//
		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");	//
		sendMessageToSerialPort("SENS:FUNC 'CURR'");		//
		sendMessageToSerialPort("SOUR:CURR:RANG MIN");		//
		sendMessageToSerialPort("SOUR:VOLT:LEV 0");			//
		sendMessageToSerialPort("SENS:CURR:PROT 1E-3");		//
		sendMessageToSerialPort("SENS:CURR:RANG 1E-3");		//
		sendMessageToSerialPort("FORM:ELEM CURR");			//
		sendMessageToSerialPort("OUTP ON");					//
		sendMessageToSerialPort("READ?");					//
		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");				//
		return res;
	}
	public float applyVoltageAndMeasureCurrent(float _voltageLevel,String _voltageRange, String _currentCompliance, String _currentRange) throws Exception{

		String voltageLevel = String.valueOf(_voltageLevel);
		System.out.println("Applying VOLTAGE to the output in K2430 and measuring CURRENT...");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("SOUR:FUNC VOLT");
		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");
		sendMessageToSerialPort("SOUR:VOLT:RANG "+ _voltageRange);
		sendMessageToSerialPort("SOUR:VOLT:LEV "+ 	voltageLevel);
		sendMessageToSerialPort("SENS:CURR:PROT "+ 	_currentCompliance);
		sendMessageToSerialPort("SENS:FUNC 'CURR'");
		sendMessageToSerialPort("SENS:CURR:RANG "+	_currentRange);
		sendMessageToSerialPort("FORM:ELEM CURR");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");


		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");
		return res;
	}
	public float applyCurrentAndMeasureVoltage(float _currentLevel,String _currentRange, String _voltageCompliance, String _voltageRange) throws Exception{

		String currentLevel = String.valueOf(_currentLevel);
		System.out.println("Applying CURRENT to the output in K2430 and measuring VOLTAGE...");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("SOUR:FUNC CURR");
		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
		sendMessageToSerialPort("SOUR:CURR:RANG "+ _currentRange);
		sendMessageToSerialPort("SOUR:CURR:LEV "+ 	currentLevel);
		sendMessageToSerialPort("SENS:VOLT:PROT "+ 	_voltageCompliance);
		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
		sendMessageToSerialPort("SENS:VOLT:RANG "+	_voltageRange);
		sendMessageToSerialPort("FORM:ELEM VOLT");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");


		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		sendMessageToSerialPort("OUTP OFF");
		return res;
	}
	public float applyVoltageAndMeasureCurrentAndLeftInstrumentOn(float _voltageLevel,String _voltageRange, String _currentCompliance, String _currentRange) throws Exception{

		String voltageLevel = String.valueOf(_voltageLevel);
		System.out.println("Applying VOLTAGE to the output in K2430 and measuring CURRENT...");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("SOUR:FUNC VOLT");
		sendMessageToSerialPort("SOUR:VOLT:MODE FIXED");
		sendMessageToSerialPort("SOUR:VOLT:RANG "+ _voltageRange);
		sendMessageToSerialPort("SOUR:VOLT:LEV "+ 	voltageLevel);
		sendMessageToSerialPort("SENS:CURR:PROT "+ 	_currentCompliance);
		sendMessageToSerialPort("SENS:FUNC 'CURR'");
		sendMessageToSerialPort("SENS:CURR:RANG "+	_currentRange);
		sendMessageToSerialPort("FORM:ELEM CURR");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");


		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		return res;
	}
	public float applyCurrentAndMeasureVoltageAndLeftInstrumentOn(float _currentLevel,String _currentRange, String _voltageCompliance, String _voltageRange) throws Exception{

		String currentLevel = String.valueOf(_currentLevel);
		System.out.println("Applying CURRENT to the output in K2430 and measuring VOLTAGE...");
		float res = -1;
		byte[] data;
		int dataLength;
		sendMessageToSerialPort("*RST");
		sendMessageToSerialPort("SOUR:FUNC CURR");
		sendMessageToSerialPort("SOUR:CURR:MODE FIXED");
		sendMessageToSerialPort("SOUR:CURR:RANG "+ _currentRange);
		sendMessageToSerialPort("SOUR:CURR:LEV "+ 	currentLevel);
		sendMessageToSerialPort("SENS:VOLT:PROT "+ 	_voltageCompliance);
		sendMessageToSerialPort("SENS:FUNC 'VOLT'");
		sendMessageToSerialPort("SENS:VOLT:RANG "+	_voltageRange);
		sendMessageToSerialPort("FORM:ELEM VOLT");
		sendMessageToSerialPort("OUTP ON");
		sendMessageToSerialPort("READ?");


		waitForIncomingData();
		dataLength = this.getReadDataLength();
		data = this.getReadData();
		if (dataLength>0){
			String str = new String(data,0,dataLength);
			if (str!=null)
			{
				//System.out.println(str);
				String[] rawData = str.split(",");
				res = Float.valueOf(rawData[0]);
				//System.out.println(res);
			}
		}
		return res;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 try
		 {
			 Keithley2430 k = new Keithley2430("COM6");
			 k.initialize("k2430_InitFile_For_TTC.txt");
			 k.test("k2430_TestFile_For_TTC.txt");
			 k.configure("k2430_ConfigFile_For_TTC.txt");


			 System.out.println("4-WIRE RESISTANCE --> "+k.read4WireResistance());
			 System.out.println("2-WIRE RESISTANCE --> "+k.read2WireResistance());
			 //System.out.println("VOLTAGE MEASURE --> "+k.measureVoltage());
			 //System.out.println("CURRENT MEASURE --> "+k.measureCurrent());
			 float voltageToBeApplied = (float)10.2515;
			 float currentToBeApplied = (float)0.2179847;

			 System.out.println("APPLYING VOLTAGE "+voltageToBeApplied+" AND MEASURING CURRENT --> "+k.applyVoltageAndMeasureCurrent(voltageToBeApplied,"20","10E-1","10E-1"));
			 System.out.println("APPLYING CURRENT "+currentToBeApplied+" AND MEASURING VOLTAGE --> "+k.applyCurrentAndMeasureVoltage(currentToBeApplied, "10E-1", "20", "20"));
			 //System.out.println("APPLYING VOLTAGE "+voltageToBeApplied+" AND MEASURING CURRENT. LEFT INSTRUMENT ON. --> "+k.applyVoltageAndMeasureCurrentAndLeftInstrumentOn(voltageToBeApplied,"20","10E-1","10E-1"));
			 //System.out.println("APPLYING CURRENT "+currentToBeApplied+" AND MEASURING VOLTAGE. LEFT INSTRUMENT ON. --> "+k.applyCurrentAndMeasureVoltageAndLeftInstrumentOn(currentToBeApplied, "10E-1", "20", "20"));

			 System.out.println("Ending process...");
			 System.out.println("Closing ports...");
			 Thread.sleep(500);
			 k.closeSerialPort();
			 Thread.sleep(500);
			 System.exit(1);

		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 System.err.println(
					 "StackTrace = " + e.getStackTrace()+ "\n" +
					 "Message = "+ e.getMessage()+ "\n" +
					 "Cause = "+ e.getCause());

		 }
	}
}
