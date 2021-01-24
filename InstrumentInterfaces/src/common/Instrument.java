package common;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import interfaces.I_Instrument;

public abstract class Instrument implements I_Instrument {

	protected String serialNumber = null;
	protected String model = null;
	protected String manufacturer = null;
	protected String name = null;
	protected String observations = null;
	protected String firmwareVersion = null;
	
	protected BufferedImage image = null;
	
	protected HashMap<String,Object> otherAttributes = null;
	
	protected CommPort_I commPort = null;
	
	
	public Instrument() {
		super();
		this.otherAttributes = new HashMap<String,Object>();
	}

	public Instrument(	String serialNumber, 
						String model, 
						String manufacturer, 
						String name, 
						String observations,
						BufferedImage image,
						CommPort_I commPort) {
		
		this();
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
		this.name = name;
		this.observations = observations;
		this.image = image;
		
		this.commPort = commPort;
	}

	@Override
	public String getSerialNumber() throws Exception {
		return serialNumber;
	}

	@Override
	public String getModel() throws Exception {
		return model;
	}

	@Override
	public String getManufacturer() throws Exception {
		return manufacturer;
	}

	@Override
	public String getName() throws Exception {
		return name;
	}

	@Override
	public String getFirmwareVersion() throws Exception {
		return firmwareVersion;
	}

	@Override
	public String getObservations() throws Exception {
		return observations;
	}
	
	@Override
	public Object getOtherAttribute(String attribute) throws Exception {
		return this.otherAttributes.get(attribute);
	}
	
	@Override
	public BufferedImage getImage() throws Exception {
		return this.image;
	}
	
	@Override
	public CommPort_I getCommPort() {
		return commPort;
	}
	
	@Override
	public void setSerialNumber(String serialNumber) throws Exception {
		this.serialNumber = serialNumber;
	}

	@Override
	public void setModel(String model) throws Exception {
		this.model = model;
	}

	@Override
	public void setManufacturer(String manufacturer) throws Exception {
		this.manufacturer = manufacturer;
	}

	@Override
	public void setName(String name) throws Exception {
		this.name = name;
	}

	@Override
	public void setFirmwareVersion(String firmwareVersion) throws Exception {
		this.firmwareVersion = firmwareVersion;
	}

	@Override
	public void setObservations(String obsevations) throws Exception {
		this.observations = obsevations;
	}
	
	@Override
	public void setOtherAttribute(String attribute, Object value) throws Exception {
		this.otherAttributes.put(attribute, value);
	}

	@Override
	public void setImage(BufferedImage image) throws Exception {
		this.image = image;
	}
	
	@Override
	public void setCommPort(CommPort_I commPort) {
		this.commPort = commPort;
	}

	@Override
	public String toString() {
		return "Instrument [serialNumber=" + serialNumber + ", model=" + model + ", manufacturer=" + manufacturer
				+ ", name=" + name + ", observations=" + observations + ", firmwareVersion=" + firmwareVersion
				+ ", image=" + image + ", otherAttributes=" + otherAttributes + ", commPort=" + commPort + "]";
	}
	
}
