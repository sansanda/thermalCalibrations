package expansion_cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.I_CommunicationsInterface;
import information.GeneralInformation_Component;

/**
 * @author DavidS
 *
 */
public class K7700 extends MeasureChannels_DifferentialMultiplexer{

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(K7700.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	/**
	 * @throws Exception 
	 * 
	 */
	public K7700(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected, int slot) throws Exception {
		super(name, id, parent, enable, selected,slot);
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	
	//****************************VERSION***************************************
	
	public static int getVersion() {
		return classVersion;
	}
	
	//**************************************************************************
	//****************************METODOS***************************************
	//**************************************************************************
	
	public void initialize(I_CommunicationsInterface i_CommunicationsInterface) throws Exception {
		logger.info("Initializing Format SubSystem ... ");
		super.initialize(i_CommunicationsInterface);
		this.downloadConfiguration();		
	}
	
	public void uploadConfiguration() throws Exception {
		
		logger.info("Uploading K7700 card configuration to the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		
	}
	
	public void downloadConfiguration() throws Exception {
		
		logger.info("Downloading K7700 card configuration from the instrument ... ");
		
		if (this.communicationsInterface == null) throw new Exception("Communications Interface not initialized!!!!");
		
		int slot = this.getSlot();
		
		this.intial_generalPurpose_measurementChannel_number = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":VCHANNEL:START?")));
		this.final_generalPurpose_measurementChannel_number = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":VCHANNEL:END?")));
		this.intial_amp_measurementChannel_number = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":ACHANNEL:START?")));
		this.final_amp_measurementChannel_number = Integer.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":ACHANNEL:END?")));
		
		((GeneralInformation_Component)this.getSubComponent("general_information")).setFirmwareVersion(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":SWREVISION?")));;
		((GeneralInformation_Component)this.getSubComponent("general_information")).setSerialNumber(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":SNUMBER?")));;
		
		this.maximum_allowable_voltage = Float.valueOf(new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":VMAX?")));
		
		this.support_multiplexed_channels = ((new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":MUX?")).equals("1")) ? true : false);
		this.support_isolated_channels = ((new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":ISOLATED?")).equals("1")) ? true : false);
		this.have_builtIn_temp_comp_col_juntion_sensor = ((new String(this.communicationsInterface.ask("SYSTEM:CARD"+(slot)+":TCOMpensated?")).equals("1")) ? true : false);
	
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("K7700 [toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

	
	
	
}

