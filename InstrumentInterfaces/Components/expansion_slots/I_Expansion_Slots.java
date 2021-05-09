package expansion_slots;

import expansion_cards.ExpansionCard;

public interface I_Expansion_Slots {
	
	public int getNumberOfTotalSlots() throws Exception;
	public int getNumberOfEmptySlots() throws Exception;
	public int getNumberOfOccupiedSlots() throws Exception;
	public boolean isSlotNumberOccupied(int slotNumber) throws Exception;
	
	public ExpansionCard getExpansionCardAt(int slotNumber) throws Exception;
	public ExpansionCard getExpansionCardOfChannel(int _channelNumber) throws Exception;
}
