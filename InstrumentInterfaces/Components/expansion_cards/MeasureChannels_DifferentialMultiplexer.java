package expansion_cards;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.I_InstrumentComponent;
import communications.I_CommunicationsInterface;
import information.GeneralInformation_Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author DavidS
 *
 */
public abstract class MeasureChannels_DifferentialMultiplexer extends ExpansionCard implements I_MeasureChannel_DifferentialMultiplexer{
	
	//version 100:  First non operative implementation
	
	//**************************************************************************
	//****************************CONSTANTES************************************
	//**************************************************************************
	
	private static final int classVersion = 100;
	final static Logger logger = LogManager.getLogger(MeasureChannels_DifferentialMultiplexer.class);
	
	//**************************************************************************
	//****************************VARIABLES*************************************
	//**************************************************************************
	
	protected float 	maximum_allowable_voltage;
	protected boolean support_multiplexed_channels;
	protected boolean support_isolated_channels;
	protected boolean have_builtIn_temp_comp_col_juntion_sensor;
	protected int 	intial_generalPurpose_measurementChannel_number;
	protected int 	final_generalPurpose_measurementChannel_number;
	protected int 	intial_amp_measurementChannel_number;
	protected int 	final_amp_measurementChannel_number;
	protected int 	intial_isolated_measurementChannel_number;
	protected int 	final_isolated_measurementChannel_number;
	
	
	
	//**************************************************************************
	//****************************CONSTRUCTORES*********************************
	//**************************************************************************
	
	public MeasureChannels_DifferentialMultiplexer(String name, long id, I_InstrumentComponent parent, boolean enable,
			boolean selected, int slot) throws Exception {
		super(name, id, parent, enable, selected, slot);
		this.addSubComponent(new GeneralInformation_Component("general_information", 
				1l, null, true, true, "1", "7700", "Keithley", "", 
				"Tarjeta multiplexora 7700 de Keithley"));
	}

	//**************************************************************************
	//****************************METODOS ESTATICOS*****************************
	//**************************************************************************
	
	/**
	 * Método estico que permite la conversion de una lista de canales a formato String --> "(@_channelsList[0], _channelsList[1],.... , _channelsList[n])"
	 * Será utilizado por las clases que requieran la conversion de una lista de canales dados en un array int[], los cuales van a ser configurados 
	 * para algún proposito, por ejemplo un scan. Y que trabajen con la card indicada como parámetro de entrada.
	 * Alerta, este método solo compara con los canales dedicados a voltage o 2w!!!!
	 * @param __k7700 la card para la cual se hace la lista de canales.
	 * @param _channelsList array de enteros con los canales a incluir en la lista 
	 * @return String con la lista de canales en formato "(@_channelsList[0], _channelsList[1],.... , _channelsList[n])"
	 * @throws Exception 
	 */
	public static String createChannelsList(int[] _channelsList) throws Exception
	{
		//TODO Test
		
		logger.info("Creating channels list using an int array as parameter.");
		
		String channelListAsString = "";

		channelListAsString = "(@";
		
		for (int i=0;i<_channelsList.length;i++)
		{
			String channel = "";
			channel = channel.concat(String.valueOf(String.format("%02d", (_channelsList[i]))));
			
			channelListAsString = channelListAsString + channel + ",";
		}
		channelListAsString = channelListAsString.substring(0, channelListAsString.length()-1);
		channelListAsString = channelListAsString + ")";
		
		return channelListAsString;
		
	}
	
	/**
	 * Método estico que permite la conversion de una lista de canales consecutivos a formato String --> "(@_minChannel:_maxChannel)"
	 * Será utilizado por las clases que requieran la conversion de int _minChannel, int _maxChannel a una lista de canales, los cuales van a ser configurados 
	 * para algún proposito, por ejemplo un scan. Y que trabajen con la card indicada como parámetro de entrada.
	 * Alerta, este método solo compara con los canales dedicados a voltage o 2w!!!!
	 * @param __k7700 la card para la cual se hace la lista de canales.
	 * @param _channelsList array de enteros con los canales a incluir en la lista 
	 * @return String con la lista de canales en formato "(@_channelsList[0], _channelsList[1],.... , _channelsList[n])"
	 */
	public static String createChannelsList(int _minChannel, int _maxChannel) throws Exception
	{
		//TODO Test
		
		logger.info("Creating consecutive channels list using two parameters (min and max channel).");
		
		
		String channelListAsString = "";
		
		if (_minChannel==_maxChannel) channelListAsString = "(@" + String.format("%02d", _minChannel) + ")";
		else channelListAsString = "(@" + String.format("%02d", _minChannel) + ":"  + String.format("%02d", _maxChannel) + ")";
					
		return channelListAsString;
	}
	
	/**
	 * Método que transforma una lista de canales devuelta por el multimetro la cual puede venir en formato 
	 *  (@n) o (@n1:n2) o (@n1,n2.....,nn) en un array e enteros. 
	 * @param _channelList con la lista de canales proveniente del multimetro
	 * @return el array de enteros correspopndiente a la lista de canales
	 * @throws Exception si el formato es incorrecto o se detecta alguna incongruencia es la lista de canales
	 */
	public static int[] convertChannelList(String _channelList) throws Exception
	{
		//TODO Test
		
		//_channelList = "(@101:103,105:107,120)";
		
		logger.info("Converting channel list " + _channelList + " to int[] array");
		
		//verificamos mediantes expresiones regulares que el parámetro de entrada viene correctamente construido en una de sus cuatro posibles formas,
		//es decir: (@), (@n), (@n1:n2), (@n1,n2.....,nn) o una combinacion de las anteriores, donde nn son siempre digitos de tres números 
		
		Pattern emptyListPattern = Pattern.compile("\\(@\\)", Pattern.CASE_INSENSITIVE);
		//Matcher channelRangePatternMatcher = channelRangePattern.matcher("(@101:102)");
		Matcher emptyListPatternMatcher = emptyListPattern.matcher(_channelList);
		
		Pattern genericChannelListPattern = Pattern.compile("(\\(@(?:([1-9][0-9]{2}:[1-9][0-9]{2})|(([1-9][0-9]{2})))(,(?:([1-9][0-9]{2}:[1-9][0-9]{2})|(([1-9][0-9]{2}))))*)\\)", Pattern.CASE_INSENSITIVE);
		//Matcher channelListPatternMatcher = channelListPattern.matcher("(@101,121,111)");
		Matcher genericChannelListPatternMatcher = genericChannelListPattern.matcher(_channelList);
	    
		//Si no cumple con un formato valido de entrada entoces no podemos seguir
		if (!emptyListPatternMatcher.matches() && !genericChannelListPatternMatcher.matches()) throw new Exception("Not valid format for input parameter _channelList. You get " + _channelList);
		
		//Si estás aquí es porque se ha cumplido con el formato del parametrode entrada
		ArrayList<Integer> arrayAsInt = new ArrayList<Integer>();
		
		//case (@)
		if(emptyListPatternMatcher.matches()) {
			//Nothing happens
		}
		
		//case (@n), (@n1:n2), (@n1,n2..,n3:n4,...,nn or nn:nm)
		if(genericChannelListPatternMatcher.matches()) {
			
			//Todo este proceso podria hacerse de forma recursiva. Queda pendiente.
			
			//Eliminamos los parentesis y el @
			String channelList = _channelList.substring(2, _channelList.length()-1); 
			if (!channelList.contains(",")) {
				//case n or n1:n2
				if (channelList.contains(":"))
				{
					//case n1:n2
					String[] numbers = channelList.split(":");
					int n1 = Integer.valueOf(numbers[0]);
					int n2 = Integer.valueOf(numbers[1]);
					for (int number=n1;number<=n2;number++)
					{
						arrayAsInt.add(number);
					}
				}
				else
				{
					//case n
					arrayAsInt.add(Integer.valueOf(channelList));
				}
			}else
			{
				//case (n1,n2..,n3:n4,...,nn or nn:nm)
				//we will call unit at every value between characters = ',' 
				String[] units = channelList.split(",");
				for (int i=0;i<units.length;i++)
				{
					String unit = units[i];
					//case n or n1:n2
					if (unit.contains(":"))
					{
						//case n1:n2
						String[] splitResult2 = unit.split(":");
						int n1 = Integer.valueOf(splitResult2[0]);
						int n2 = Integer.valueOf(splitResult2[1]);
						for (int number=n1;number<=n2;number++)
						{
							arrayAsInt.add(number);
						}
					}
					else
					{
						//case n
						arrayAsInt.add(Integer.valueOf(unit));
					}
				}
			}	
		}

		//Paso de la ArrayList of ints a int[]
		int[] ret = new int[arrayAsInt.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrayAsInt.get(i).intValue();
	    }
	    
	    return ret;
		
	}
	
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
	}
	
	/**
	 * Método que permite saber si el range de canales pasado como parámetro es aceptado para las tarjeta expansora.
	 * Los números _minChannel y _maxChannel deben llegar como números de tres dígitos (el del las centenas indica el slot).
	 * El rango indicado por el mínimo y el máximo debe corresponder a la misma tarjeta exapnsora. Es decir, no sería valido indicar el siguiente
	 * rango: _minChannel = 101 y _maxChannel = 202 (202 perteneche a otro slot y por lo tanto a otra tarjeta expansora), pero si sería correcto 
	 * este otro: _minChannel = 101 y _maxChannel = 118.
	 * 
	 * @param _minChannel el número inferior del rango de canales
	 * @param _maxChannel el número superior del rango de canales
	 * @throws Exception en caso que caombinacion del _minChannel y _maxChannel no sea correcta
	 */
	@Override
	public void validateChannelRange(int _minChannel, int _maxChannel) throws Exception
	{
		//TODO Test
		
		logger.info("Validating channel range using two parameters (min and max channel)...");
		
		//Adaptación de los números de canal a dos digitos
		int minChannel, maxChannel, minChannel_SlotNumber, maxChannel_SlotNumber;
		
		minChannel = (int)(_minChannel % 100);
		maxChannel = (int)(_maxChannel % 100);
		minChannel_SlotNumber = (int) (_minChannel / 100);
		maxChannel_SlotNumber = (int) (_maxChannel / 100);
		
		if (minChannel_SlotNumber!=maxChannel_SlotNumber) throw new Exception("channel range must belong to the same expansion card");
		if (minChannel_SlotNumber!=this.getSlot()) throw new Exception("min channel slot is " + minChannel_SlotNumber +" but must be equal to " + this.getSlot() + " which is the slot where we can find the expansion card.");
		if (maxChannel_SlotNumber!=this.getSlot()) throw new Exception("max channel slot is " + maxChannel_SlotNumber +" but must be equal to " + this.getSlot() + " which is the slot where we can find the expansion card.");
				
		
		if (minChannel<this.intial_generalPurpose_measurementChannel_number || minChannel>this.final_generalPurpose_measurementChannel_number) 
			throw new Exception("Min Channel must be a int value between " + this.intial_generalPurpose_measurementChannel_number + " and " + this.final_generalPurpose_measurementChannel_number);
		if (maxChannel<this.intial_generalPurpose_measurementChannel_number || maxChannel>this.final_generalPurpose_measurementChannel_number) 
			throw new Exception("Max Channel must be a int value between " + this.intial_generalPurpose_measurementChannel_number + " and " + this.final_generalPurpose_measurementChannel_number);
		if (minChannel>_maxChannel)
			throw new Exception("Max Channel must greater than Min Channel ");	
	}
	
	@Override
	public void validateChannelList(int[] _channelsList) throws Exception
	{
		//TODO Test
		
		logger.info("Validating channel list...");
		
		if (_channelsList.length == 0) throw new Exception ("Channel list can't be empty!!!");
		for (int i=0;i<_channelsList.length;i++)
		{
			this.validateChannelRange(_channelsList[i], _channelsList[i]);
		}
	}
	
	@Override
	public void validateChannel(int _channelNumber) throws Exception
	{
		//TODO Test
		
		logger.info("Validating channel ...");
		
		this.validateChannelRange(_channelNumber, _channelNumber);
	}
	
	@Override
	public float getMaximum_allowable_voltage() {
		return this.maximum_allowable_voltage;
	}
	
	@Override
	public int getIntial_generalPurpose_measurementchannel_number() {
		return this.intial_generalPurpose_measurementChannel_number;
	}
	@Override
	public int getFinal_generalPurpose_measurementchannel_number() {
		return this.final_generalPurpose_measurementChannel_number;
	}
	@Override
	public boolean support_multiplexed_channels() {
		return this.support_multiplexed_channels;
	}
	@Override
	public boolean support_isolated_channels() {
		return this.support_isolated_channels;
	}
	@Override
	public boolean isHave_builtIn_temp_comp_col_juntion_sensor() {
		return this.have_builtIn_temp_comp_col_juntion_sensor;
	}
	@Override
	public int getIntial_amp_measurementchannel_number() {
		return this.intial_amp_measurementChannel_number;
	}
	@Override
	public int getFinal_amp_measurementchannel_number() {
		return this.final_amp_measurementChannel_number;
	}
	@Override
	public int getIntial_isolated_channel_number() {
		return this.intial_isolated_measurementChannel_number;
	}
	@Override
	public int getFinal_isolated_channel_number() {
		return this.final_isolated_measurementChannel_number;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MeasureChannels_DifferentialMultiplexer [maximum_allowable_voltage=")
				.append(maximum_allowable_voltage).append(", support_multiplexed_channels=")
				.append(support_multiplexed_channels).append(", support_isolated_channels=")
				.append(support_isolated_channels).append(", have_builtIn_temp_comp_col_juntion_sensor=")
				.append(have_builtIn_temp_comp_col_juntion_sensor)
				.append(", intial_generalPurpose_measurementChannel_number=")
				.append(intial_generalPurpose_measurementChannel_number)
				.append(", final_generalPurpose_measurementChannel_number=")
				.append(final_generalPurpose_measurementChannel_number)
				.append(", intial_amp_measurementChannel_number=").append(intial_amp_measurementChannel_number)
				.append(", final_amp_measurementChannel_number=").append(final_amp_measurementChannel_number)
				.append(", intial_isolated_measurementChannel_number=")
				.append(intial_isolated_measurementChannel_number).append(", final_isolated_measurementChannel_number=")
				.append(final_isolated_measurementChannel_number).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}
	
	
	
}
