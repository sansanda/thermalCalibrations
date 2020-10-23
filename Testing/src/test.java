import javax.measure.quantity.ElectricPotential;

import Ports.S_Port2;
import interfaces.ICommunicationPort;
import interfaces.ISingleChannelMultimeter;
import interfaces.ISingleChannelMultimeter.WorkingMode;
import interfaces.IThermometer;
import interfaces.IThermometer.FRTDType;
import interfaces.IThermometer.TemperatureTransducer;
import interfaces.IThermometer.TemperatureUnits;
import multimeters.Keithley2700_v3;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//ICommunicationPort sp = new S_Port2("COM7");
			//sp.sendData("*IDN?");
			//System.out.println(sp.readDataAsString());		
			
			Keithley2700_v3 k = new Keithley2700_v3("COM7");
			ISingleChannelMultimeter scm = k;
			IThermometer t = k;		
			
			scm.reset();			
			t.setTemperatureUnits(TemperatureUnits.C);
			t.setTemperatureTransducer(TemperatureTransducer.FRTD);
			t.setFRTDType(FRTDType.PT100);
			
			double readedTemperature;
			while(true)
			{
				readedTemperature = t.measureTemperature().doubleValue(SI.CELSIUS);
				
				System.out.println(readedTemperature);
			}
			
			//double readedVolts = k.measureVoltage().doubleValue(SI.VOLT);
			//System.out.println(readedVolts);
			
			//System.exit(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
