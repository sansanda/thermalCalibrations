package jssc_examples;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPort;

public class PortReader implements SerialPortEventListener {

	private SerialPort sp;
	
    public PortReader(SerialPort sp) {
		super();
		this.sp = sp;
	}

	@Override
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = this.sp.readString(event.getEventValue());
                System.out.println("Received response: " + receivedData);
            }
            catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }

}
