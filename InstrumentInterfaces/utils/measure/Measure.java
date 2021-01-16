/**
 * 
 */
package measure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DavidS
 * Clase creada para la gestión de las medidas devueltas por los instrumento de Keithley tipo multimetes k2700, 24xx, etc..
 */
public class Measure {
	
	final static String VERSION = "1.0.0";
	
	private double 	value;
	private String 	unit;
	private float  	tStamp = -1f;
	private long	readingNumber = -1;
	private int 	channelNumber = -1;
	private int 	cardNumber = -1;
	
	private boolean highLimit1Passes = 	false;
	private boolean highLimit2Passes = 	false;
	private boolean lowLimit1Passes = 	false;
	private boolean lowLimit2Passes = 	false;

	public static String tStampTag = 		"SECS";
	public static String readingNumberTag = "RDNG#";
	public static String channelNumberTag = "INTCHAN";
	public static String limitsTag = 		"LIMITS";
	
	//The objective is to extract +1.23456789E-03 from +1.23456789E-03VDC, for example
	public static Pattern SCIENTIFIC_NOTATION_PATTERN = Pattern.compile("[+-]?\\d(\\.\\d+)?[Ee][+-]?\\d+");
	
	/**
	 * 
	 * @param dataArray is the data of a reading that contains all the elements of the reading. p.e reading, channel, etc... it depends on the 
	 * format elemets configuration
	 */
	public Measure(String[] dataArray) {
		
		
		for (int i=0;i<dataArray.length;i++) {
			
			String data = dataArray[i];
			data = data.replaceAll("\\s", "");
			//System.out.println(att);
			
			if (data.contains(tStampTag)) 
			{
				data = data.replace(tStampTag, "");
				this.tStamp = Float.parseFloat(data);
			}
			else
			{
				if (data.contains(readingNumberTag)) 
				{
					data = data.replace(readingNumberTag, "");
					this.readingNumber = Long.parseLong(data);
				}
				else
				{
					if (data.contains(channelNumberTag)) 
					{
						data = data.replace(channelNumberTag, "");
						this.cardNumber = Integer.parseInt(data.substring(0, 1));
						this.channelNumber = Integer.parseInt(data.substring(1, data.length()));
						
					}
					else
					{
						if (data.contains(limitsTag)) 
						{
							data = data.replace(limitsTag, "");
							this.highLimit2Passes = data.substring(0, 1).equals("0");
							this.lowLimit2Passes =  data.substring(1, 2).equals("0");;
							this.highLimit1Passes = data.substring(2, 3).equals("0");;
							this.lowLimit1Passes =  data.substring(3, 4).equals("0");;
							
						}
						else
						{
							//value and unit case
							Matcher matcher = SCIENTIFIC_NOTATION_PATTERN.matcher(data);
							if (matcher.find())
							{
								String value = matcher.group(0);
								this.value = Double.parseDouble(value);
								this.unit = data.substring(value.length(), data.length());
							}
						}
					}
					
				}
				
				
			}
		}
	}

	public double getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public float gettStamp() {
		return tStamp;
	}

	public long getReadingNumber() {
		return readingNumber;
	}

	public int getChannelNumber() {
		return channelNumber;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public boolean isHighLimit1Passes() {
		return highLimit1Passes;
	}

	public boolean isHighLimit2Passes() {
		return highLimit2Passes;
	}

	public boolean isLowLimit1Passes() {
		return lowLimit1Passes;
	}

	public boolean isLowLimit2Passes() {
		return lowLimit2Passes;
	}
	
	public static String getVersion() {
		return VERSION;
	}
	
	@Override
	public String toString() {
		return "Measure [value=" + value + ", unit=" + unit + ", tStamp=" + tStamp + ", readingNumber=" + readingNumber
				+ ", channelNumber=" + channelNumber + ", cardNumber=" + cardNumber + ", highLimit1Passes="
				+ highLimit1Passes + ", highLimit2Passes=" + highLimit2Passes + ", lowLimit1Passes=" + lowLimit1Passes
				+ ", lowLimit2Passes=" + lowLimit2Passes + "]";
	}

	
	public static void main(String[] args)
	{
		Measure m = new Measure("+1.05720816E+01ºC,+40670.266SECS,+214613RDNG#,110INTCHAN,0000LIMITS".split(","));
		System.out.println(m.toString());
	}
	
}
