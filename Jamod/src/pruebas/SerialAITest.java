//Now let's start writing code. We need a simple Java application skeleton, with imports of all jamod packages: 
    
package pruebas;
import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;
 
public class SerialAITest {

  public static void main(String[] args) {
    try {
    		
    	//Next we add the instances and variables the application will need: 
    		
    	/* The important instances of the classes mentioned before */
    	SerialConnection con = null; //the connection
    	ModbusSerialTransaction trans = null; //the transaction
    	ReadInputRegistersRequest req = null; //the request
    	ReadInputRegistersResponse res = null; //the response

    	/* Variables for storing the parameters */
    	String portname= null; //the name of the serial port to be used
    	int unitid = 0; //the unit identifier we will be talking to
    	int ref = 0; //the reference, where to start reading from
    	int count = 0; //the count of IR's to read
    	int repeat = 1; //a loop for repeating the transaction
    	
    	
    	/*Next the application needs to read in the parameters: 

    		<portname [String]> as String into portname 
    		<Unit Address [int8]> as String into unitid 
    		<register [int16]> as int into ref 
    		<wordcount [int16]> as int into count 
    		{<repeat [int]>} as int into repeat, 1 by default (optional) */

    	portname = "COM6";
    	unitid = 1;
    	ref = 0;
    	count = 1;
    	repeat = 1;
    		
    	//These will be used subsequently to setup the connection and the request. First, however, we need to set the identifier of the Master on the serial network (in this case to 1), as well as the parameters for the connection: 

    		
    	//2. Set master identifier
    	ModbusCoupler.createModbusCoupler(null);
    	ModbusCoupler.getReference().setUnitID(1);
    	//3. Setup serial parameters
    	SerialParameters params = new SerialParameters();
    	params.setPortName(portname);
    	params.setBaudRate(9600);
    	params.setDatabits(8);
    	params.setParity("None");
    	params.setStopbits(1);
    	params.setEncoding("ascii");
    	params.setEcho(false);
    	
    	//4. Open the connection
    	con = new SerialConnection(params);
    	con.open();
    	//5. Prepare a request
    	req = new ReadInputRegistersRequest(ref, count);
    	req.setUnitID(unitid);
    	req.setHeadless();
    	//6. Prepare a transaction
    	trans = new ModbusSerialTransaction(con);
    	trans.setRequest(req);
    	
    	//7. Execute the transaction repeat times
    	int k = 0;
    	do {
	    	trans.execute();
	    	res = (ReadInputRegistersResponse) trans.getResponse();
	    	for (int n = 0; n < res.getWordCount(); n++) {
	    	System.out.println("Word " + n + "=" + res.getRegisterValue(n));
    	}
    	k++;
    	} while (k < repeat);
    	//8. Close the connection
    	con.close();
    	
    	System.exit(0);
    				
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }//main
  
}//class SerialAITest
