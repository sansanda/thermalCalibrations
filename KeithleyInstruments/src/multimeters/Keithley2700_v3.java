package multimeters;

import javax.measure.Measurable;
import javax.measure.Measure;
import javax.measure.quantity.ElectricPotential;
import javax.measure.quantity.ElectricResistance;
import javax.measure.quantity.Temperature;
import javax.measure.unit.SI;

import Ports.S_Port2_32bits;
import interfaces.ICommunicationPort;
import interfaces.ISingleChannelMultimeter;
import interfaces.IThermometer;
import interfaces.IVoltmeter;

public class Keithley2700_v3 implements IThermometer, IVoltmeter, ISingleChannelMultimeter{

	
	private ICommunicationPort serialPortRS232 = null;
	private WorkingMode workingMode = null;
	private Measurable<?> lastMeasure = null;
	
	public Keithley2700_v3(String wantedPortName) throws Exception{
		serialPortRS232 = new S_Port2_32bits(wantedPortName);
	}
	
	//IMultimeter
	@Override
	public void initialize() throws Exception {
		// TODO Auto-generated method stub
		configureAsWorkingMode(WorkingMode.VOLT_DC);
	}
	
	@Override
	public void configureAsWorkingMode(WorkingMode wm) throws Exception
	{
		if (workingMode!=wm)  
		{
			setInWorkingMode(wm);
			workingMode=wm;
		}
	}
	
	private void setInWorkingMode(WorkingMode wm) throws Exception
	{
		if (wm==WorkingMode.VOLT_DC) setInVoltDCMode();
		if (wm==WorkingMode.VOLT_AC) setInVoltACMode();
		if (wm==WorkingMode.CURR_DC) setInCurrDCMode();
		if (wm==WorkingMode.CURR_AC) setInCurrACMode();
		if (wm==WorkingMode.RES) setInResMode();
		if (wm==WorkingMode.FRES) setInFResMode();
		if (wm==WorkingMode.TEMP) setInTempMode();
		if (wm==WorkingMode.FREQ) setInFreqMode();
		if (wm==WorkingMode.PER) setInPerMode();
		if (wm==WorkingMode.CONT) setInContMode();
		
	}
	
	
	private void setInContMode() throws Exception{
		// TODO Auto-generated method stub
		
	}

	private void setInPerMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInFreqMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInFResMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInTempMode() throws Exception {
		
		System.out.println("Setting in MODE: Temperature ");	
		serialPortRS232.sendData(":INITiate:CONTinuous OFF");
		serialPortRS232.sendData(":ABORt");
		serialPortRS232.sendData("FUNC 'TEMP'");
		//serialPortRS232.sendData("DISP:ENAB OFF");
		serialPortRS232.sendData("TRIG:COUN 1");
		
	}

	private void setInResMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInCurrACMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInVoltACMode() throws Exception {
		// TODO Auto-generated method stub
		
	}

	private void setInVoltDCMode() throws Exception {	
		System.out.println("Setting in MODE: Voltage-DC ");
		
		serialPortRS232.sendData(":INITiate:CONTinuous OFF");
		serialPortRS232.sendData(":ABORt");
		serialPortRS232.sendData("SENS:FUNC 'VOLT:DC'");
		serialPortRS232.sendData("SENS:VOLT:DC:RANG:AUTO ON");
		serialPortRS232.sendData("SENS:VOLT:NPLC 1");
		//serialPortRS232.sendData("DISP:ENAB OFF");
		serialPortRS232.sendData("SENS:VOLT:DC:AVER:STAT OFF");
		serialPortRS232.sendData("TRIG:COUN 1");
	}
	
	private void setInCurrDCMode() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Configured as curr dc mode");
	}
	
	
	@Override
	public Measurable<?> measure() throws Exception {
		
		Measurable<?> measure = null;
		
		switch (workingMode) 
		{
			case TEMP:
				// TODO Auto-generated method stub
				configureAsWorkingMode(WorkingMode.TEMP);
				serialPortRS232.sendData("READ?");
				//System.out.println(serialPortRS232.readDataAsString());
				String measuredTemperatureAsString = serialPortRS232.readDataAsString().split(",")[0].replaceAll(ManufacturerUnits.KEITHLEY_CELSIUS.toString(), "");
				//System.out.println(measuredTemperatureAsString);
				measure = Measure.valueOf(Double.valueOf(measuredTemperatureAsString), SI.CELSIUS);
			case VOLT_DC:
				// TODO Auto-generated method stub
				configureAsWorkingMode(WorkingMode.VOLT_DC);
				serialPortRS232.sendData("READ?");
				String measuredVoltageAsString = serialPortRS232.readDataAsString().split(",")[0].replaceAll(ManufacturerUnits.KEITHLEY_VDC.toString(), "");
				measure = Measure.valueOf(Double.valueOf(measuredVoltageAsString), SI.VOLT);
			default:	
		}
		return measure;
	}
	

	@Override
	public void setTemperatureUnits(TemperatureUnits tu) throws Exception {
		// TODO Auto-generated method stub
		serialPortRS232.sendData("UNIT:TEMP "+tu.toString());		
	}

	@Override
	public TemperatureUnits getTemperatureUnits() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTemperatureTransducer(TemperatureTransducer tt) throws Exception {
		serialPortRS232.sendData("SENS:TEMP:TRAN "+ tt.toString());
	}

	@Override
	public TemperatureTransducer getTemperatureTransducer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setThermocoupleType(ThermocoupleType tt) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ThermocoupleType getThermocoupleType() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSimulatedSimulatedReferenceTemperature(Measurable<Temperature> srt) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<Temperature> getSimulatedSimulatedReferenceTemperature() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enableOpenThermocoupleDetection(boolean enable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpenThermocpupleDetectionEnabled() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setThermistorResistance(Measurable<ElectricResistance> tr) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Measurable<ElectricResistance> getThermistorResistance() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFRTDType(FRTDType frtdType) throws Exception {
		serialPortRS232.sendData("SENS:TEMP:FRTD:TYPE "+ frtdType.toString());	
	}

	@Override
	public FRTDType getFRTDType() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() throws Exception{
		serialPortRS232.sendData("*RST");
	}
}
