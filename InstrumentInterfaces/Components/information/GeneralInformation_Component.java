package information;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.parser.JSONParser;

import common.I_InstrumentComponent;
import common.InstrumentComponent;

public class GeneralInformation_Component extends InstrumentComponent {
	
	//version 101:  Cambio de constructor para utilizar HashMap en subcomponentes
	//				Actualizacion de toString
	//				Añadimos getVersion
	
	
	private static final int classVersion = 101;
	
	protected String serialNumber = null;
	protected String model = null;
	protected String manufacturer = null;
	protected String observations = null;
	protected String firmwareVersion = null;
	
	protected HashMap<String,Object> otherAttributes = null;
	
	/**
	 * @param name
	 * @param version
	 * @param parent
	 * @param serialNumber
	 * @param model
	 * @param manufacturer
	 * @param observations
	 * @param firmwareVersion
	 * @param otherAttributes
	 */
	public GeneralInformation_Component(String name, long id, I_InstrumentComponent parent, String serialNumber,
			String model, String manufacturer, String observations, String firmwareVersion) {
		super(name, id, parent);
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
		this.observations = observations;
		this.firmwareVersion = firmwareVersion;
		this.otherAttributes = new HashMap<String, Object>();
	}

	public GeneralInformation_Component(
			String name, 
			long id, 
			ArrayList<String> descriptiveTags,
			HashMap<String,I_InstrumentComponent> subcomponents, 
			I_InstrumentComponent parent, 
			String serialNumber,
			String model, 
			String manufacturer, 
			String observations, 
			String firmwareVersion,
			HashMap<String, Object> otherAttributes
			) {
		
		super(name, id, descriptiveTags, subcomponents, parent);
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
		this.observations = observations;
		this.firmwareVersion = firmwareVersion;
		this.otherAttributes = otherAttributes;
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	 
	public static GeneralInformation_Component parseFromJSON(String filename) throws Exception
	{
		//JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
		FileReader reader = new FileReader(filename);
		
		//Read JSON file
		Object obj = jsonParser.parse(reader);
		jsonParser = null;
		 
		org.json.simple.JSONObject jObj = (org.json.simple.JSONObject) obj;
		 
		return new GeneralInformation_Component(
			 (String)jObj.get("name"), 
			 (Long)jObj.get("id"), 
			 (InstrumentComponent)jObj.get("parent"), 
			 (String)jObj.get("serialNumber"),
			 (String)jObj.get("model"), 
			 (String)jObj.get("manufacturer"), 
			 (String)jObj.get("observations"), 
			 (String)jObj.get("firmwareVersion"));
		
	}
	 
	//****************************VERSION***************************************
			
	public static int getVersion() {
		return classVersion;
	}
		
	//**************************************************************************
	//****************************METODOS PUBLICOS******************************
	//**************************************************************************
	
	public String getSerialNumber() throws Exception {
		return serialNumber;
	}

	public String getModel() throws Exception {
		return model;
	}

	public String getManufacturer() throws Exception {
		return manufacturer;
	}

	public String getFirmwareVersion() throws Exception {
		return firmwareVersion;
	}

	public String getObservations() throws Exception {
		return observations;
	}
	
	public Object getOtherAttribute(String attribute) throws Exception {
		if (this.otherAttributes==null) return null;
		return this.otherAttributes.get(attribute);
	}
	
	public void setSerialNumber(String serialNumber) throws Exception {
		this.serialNumber = serialNumber;
	}

	public void setModel(String model) throws Exception {
		this.model = model;
	}

	public void setManufacturer(String manufacturer) throws Exception {
		this.manufacturer = manufacturer;
	}

	public void setFirmwareVersion(String firmwareVersion) throws Exception {
		this.firmwareVersion = firmwareVersion;
	}

	public void setObservations(String obsevations) throws Exception {
		this.observations = obsevations;
	}
	
	public void setOtherAttribute(String attribute, Object value) throws Exception {
		if (this.otherAttributes==null) this.otherAttributes = new HashMap<String, Object>();
		this.otherAttributes.put(attribute, value);
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("\n\n ***************** General Information Component Instance Description ****************** \n");
		builder.append("[serialNumber=").append(serialNumber);
		builder.append(", model=").append(model);
		builder.append(", manufacturer=").append(manufacturer);
		builder.append(", observations=").append(observations);
		builder.append(", firmwareVersion=").append(firmwareVersion);
		builder.append(", otherAttributes=").append(otherAttributes);
		builder.append(super.toString()).append("]");
		
		return builder.toString();
	}

}
