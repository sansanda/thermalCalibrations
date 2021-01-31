package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import interfaces.I_InstrumentComponent;

public class GeneralInformation_Component extends Instrument_Component {
	
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
	public GeneralInformation_Component(String name, int version, I_InstrumentComponent parent, String serialNumber,
			String model, String manufacturer, String observations, String firmwareVersion) {
		super(name, version, parent);
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
		this.observations = observations;
		this.firmwareVersion = firmwareVersion;
		this.otherAttributes = new HashMap<String, Object>();
	}
	
	public GeneralInformation_Component(String name, int version, ArrayList<String> descriptiveTags,
			ArrayList<I_InstrumentComponent> components, I_InstrumentComponent parent, String serialNumber,
			String model, String manufacturer, String observations, String firmwareVersion,
			HashMap<String, Object> otherAttributes) {
		super(name, version, descriptiveTags, components, parent);
		this.serialNumber = serialNumber;
		this.model = model;
		this.manufacturer = manufacturer;
		this.observations = observations;
		this.firmwareVersion = firmwareVersion;
		this.otherAttributes = otherAttributes;
	}
	
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
		builder.append("GeneralInformation_Component [serialNumber=").append(serialNumber).append(", model=")
				.append(model).append(", manufacturer=").append(manufacturer).append(", observations=")
				.append(observations).append(", firmwareVersion=").append(firmwareVersion).append(", otherAttributes=")
				.append(otherAttributes).append(", name=").append(name).append(", version=").append(version)
				.append(", descriptiveTags=").append(descriptiveTags).append(", components=").append(components)
				.append(", parent=").append(parent).append("]");
		return builder.toString();
	}

	public static void main(String[] args)
	{
		GeneralInformation_Component gic = new GeneralInformation_Component("gic",0,null,"010203","model","davidCO","obs","firmware version 0");
		System.out.println(gic.toString());
	}
	
}
