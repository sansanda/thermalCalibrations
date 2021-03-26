package communications;

import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;

import ports.GPIB_Port;

/**
 * Clase que modela una interface de comunicaciones serie tipo RS232 haciendo uso de la libreria jssc_2.9.2 
 * compatible con maquinas de 32 y 64 bits en windows.
 * <p>
 * Ciclo de vida:
 * 
 * 			<p><b>Declarar instancia.</b> 	Ejemplo:  	gpibInterface = new GPIBInterface_Component(bla bla bla ...);
			<p><b>Inicializar puerto.</b> 	Ejemplo: 	gpibInterface.initialize();
			<p><b>Abrir puerto.</b> 		Ejemplo 	gpibInterface.open();
			<p><b>Interacción con el instrumento todo lo necesario via el puerto GPIB</b>
			<p><b>Cerrar puerto.</b> 		Ejemplo: 	gpibInterface.close();
			
 * @author DavidS
 *
 */
public class GPIBInterface_Component extends InstrumentComponent implements I_CommunicationsInterface{

	
	//version 102: Adapted to the updated I_CommunicationsInterface
	//version 101: First operative version 
	//version 100: Initial version not still working 
	
	
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************

	private static final int classVersion = 102;
	
	
	final static Logger logger = LogManager.getLogger(GPIBInterface_Component.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private String				type 		= null;
	private String				standard 	= null;
	private String 				address 	= null;
	private GPIB_Port		    gpibPort;
	//All the data (bytes) received and sended from/to the instrument will always end with the byte=10, the '\n' terminator  
	private String 				terminator 	= "\n";
	private float 				timeout 	= 1.000f;
	private int 				writeWaitTime 	= 100;
	private int 				readWaitTime 	= 100;
	 
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************

	/**
	 * @param readWaitTime 
	  *
	  *
	  */
		 
	 public GPIBInterface_Component(
			 String name, 
			 long id, 
			 I_InstrumentComponent parent,
			 String type,
			 String standard,
			 String address,
			 String terminator,
			 float timeout,
			 int writeWaitTime, 
			 int readWaitTime) throws Exception{
			 
		 super(name, id, parent);
		 
		 this.type 		= type;
		 this.standard 	= standard;
		 this.address 	= address;
		 
		 this.terminator 	= terminator;
		 this.timeout 		= timeout;
		 this.writeWaitTime = writeWaitTime;
		 this.readWaitTime 	= readWaitTime;
		 
		 this.gpibPort = new GPIB_Port(address);
		 
	 }
	 
	 //**************************************************************************
	 //****************************METODOS ESTATICOS*****************************
	 //**************************************************************************
	 
	 public static GPIBInterface_Component parseFromJSON(String filename) throws Exception
	 {
		 //JSON parser object to parse read file
		 JSONParser jsonParser = new JSONParser();
		 FileReader reader = new FileReader(filename);
		
		 //Read JSON file
		 Object obj = jsonParser.parse(reader);
		 jsonParser = null;
		 
		 org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		 return new GPIBInterface_Component(
				 (String)jObj.get("name"), 
				 (Long)jObj.get("id"), 
				 (InstrumentComponent)jObj.get("parent"), 
				 (String)jObj.get("type"),
				 (String)jObj.get("standard"), 
				 (String)jObj.get("address"), 
				 (String)jObj.get("terminator"),
				 ((Double)jObj.get("timeout")).floatValue(),
				 ((Long)jObj.get("writeWaitTime")).intValue(), 
				 ((Long)jObj.get("readWaitTime")).intValue());
		 
	 }
	 
	//****************************VERSION***************************************
			
	public static int getVersion() {
		return classVersion;
	}
	 
	 //**************************************************************************
	 //****************************METODOS***************************************
	 //**************************************************************************

	@Override
	public String getType() throws Exception {
		// TODO Auto-generated method stub
		return this.type;
	}
		
	@Override
	public String getAddress() {
		return this.address;
	}
	
	@Override
	public void setAddress(String address) throws Exception {
		this.address = address;
	}
	
	@Override
	public String getStandard() throws Exception {
		return this.standard;
	}
	
	 /**
	  *
	  */
	@Override
	 public void initialize() throws Exception{
		 this.gpibPort.initialize();
	 }
	 

	 /**
	  * Open the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void open() throws Exception {
		 this.gpibPort.open(this.timeout);
	 }
	 
	 /**
	  * Close the adapter
	  * @throws Exception 
	  */
	 @Override
	 public void close() throws Exception {
		 this.gpibPort.close();
	 }
	 

	 
	/**
	 * Get the last income data (the newest arrived data) 
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @return the data readed as byte array.
	 * @trhows Exception if something goes wrong
	 * @author David Sanchez Sanchez
	 * @mail dsanchezsanc@uoc.edu
	 */
	@Override
	public byte[] read() throws Exception{
		this.open();
		byte[] returnBuffer = this.gpibPort.read();	
		logger.trace(returnBuffer);
		return returnBuffer;
	}

	/**
	 * Sends the data to the output of the adapter
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	data is the String to send to the output
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public void write(String data) throws Exception{
		this.open();
		logger.trace(data);
		this.gpibPort.write(data);
	}
	
	/**
	 * Sends a query to the output of the adapter and waits for the response
	 * This method is synchrone, so if you invoque this method it will stop de Thread until a new data arrives 
	 * @author 	David Sanchez Sanchez
	 * @mail 	dsanchezsanc@uoc.edu
	 * @param 	query is the String to send to the output as query
	 * @return 	the response readed as byte array.
	 * @trhows 	Exception if something goes wrong
	 */
	@Override
	public byte[] ask(String query) throws Exception {
		logger.trace(query);
		return this.gpibPort.ask(query);
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ***************** GPIBInterface_Component Instance Description ****************** \n");
		builder.append(" [type = ").append(type);
		builder.append(", standard = ").append(standard);
		builder.append(", address = ").append(address);
		builder.append(", gpibPort = ").append(gpibPort);
		builder.append(", terminator ascii code = ").append((int)terminator.toCharArray()[0]);
		builder.append(", writeWaitTime = ").append(writeWaitTime);
		builder.append(", readWaitTime = ").append(readWaitTime);
		builder.append(", ").append(super.toString()).append("]\n");
		
		return builder.toString();
		
	}
	
	
	
}
