package expansion_cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import common.I_InstrumentComponent;
import common.InstrumentComponent;
import communications.I_CommunicationsInterface;


/**
 * @author DavidS
 *
 */
public abstract class ExpansionCard extends InstrumentComponent{

	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	protected final static Logger logger = LogManager.getLogger(ExpansionCard.class);
	protected I_CommunicationsInterface communicationsInterface;
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	private int slot;
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************

	/**
	 * 
	 */
	public ExpansionCard(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected, int slot) {
		super(name, id, parent, enable, selected);
		this.slot = slot;
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
		logger.info("Initializing Expansion Card ... ");
		this.communicationsInterface = i_CommunicationsInterface;
	}
	
	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpansionCard [slot=").append(slot).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}
	
	
}

