package interfaces;
import java.awt.image.BufferedImage;

import common.CommPort_I;

/**
 * @author david
 *
 */
public interface I_Instrument {
	
	String 	getSerialNumber() throws Exception;
	String 	getModel() throws Exception;
	String 	getManufacturer() throws Exception;
	String 	getName() throws Exception;
	String 	getFirmwareVersion() throws Exception;
	String  getObservations() throws Exception;
	Object  getOtherAttribute(String attribute) throws Exception;
	BufferedImage getImage() throws Exception;
	
	void	setSerialNumber(String serialNumber) throws Exception;
	void	setModel(String model) throws Exception;
	void 	setManufacturer(String manufacturer) throws Exception;
	void 	setName(String name) throws Exception;
	void 	setFirmwareVersion(String firmwareVersion) throws Exception;
	void 	setObservations(String obsevations) throws Exception;
	void 	setOtherAttribute(String attribute, Object value) throws Exception;
	void 	setImage(BufferedImage image) throws Exception;
	
	//communications
	CommPort_I 	getCommPort() throws Exception;
	void 		setCommPort(CommPort_I commPort) throws Exception;
	
	
}
