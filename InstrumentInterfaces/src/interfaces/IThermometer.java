package interfaces;

import javax.measure.Measurable;
import javax.measure.quantity.Temperature;

import SDM.IMeasure;

import javax.measure.quantity.ElectricResistance;

public interface IThermometer extends IMeasure{
	
	public enum TemperatureTransducer
	{
		FRTD("FRTD"),
		TCOUPLE("TCOUPLE"),
		THERMISTOR("4W-RTD");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    TemperatureTransducer(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum TemperatureUnits
	{
		C("C"),
		F("F"),
		K("K");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    TemperatureUnits(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum ThermocoupleType
	{
		J("J"),
		K("K"),
		T("T"),
		E("E"),
		R("R"),
		S("S"),
		B("B"),
		N("N");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    ThermocoupleType(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum ThermocoupleReferenceJunction
	{
		SIM("SIM"),
		INT("INT"),
		EXT("EXT");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    ThermocoupleReferenceJunction(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum ThermistorResistanceInOhms
	{
		O2200("2200"),
		O5000("5000"),
		O10000("10000");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    ThermistorResistanceInOhms(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public enum FRTDType
	{
		PT100("PT100"),
		D100("D100"),
		F100("F100"),
		PT385("PT385"),
		PT3916("PT3916");
		
	    private final String text;

	    /**
	     * @param text
	     */
	    FRTDType(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	
	public void setTemperatureUnits(TemperatureUnits tu) throws Exception;
	public TemperatureUnits getTemperatureUnits() throws Exception;	
	
	public void setTemperatureTransducer(TemperatureTransducer tt) throws Exception;
	public TemperatureTransducer getTemperatureTransducer() throws Exception;
	
	
	//Thermocuple measurement configuration
	public void setThermocoupleType(ThermocoupleType tt) throws Exception;
	public ThermocoupleType getThermocoupleType() throws Exception;
	
	public void setSimulatedSimulatedReferenceTemperature(Measurable<Temperature> srt) throws Exception;
	public Measurable<Temperature> getSimulatedSimulatedReferenceTemperature() throws Exception;
	
	public void enableOpenThermocoupleDetection(boolean enable) throws Exception; 
	public boolean isOpenThermocpupleDetectionEnabled() throws Exception;
	
	//Thermistor measurement configuration (RTD)
	public void setThermistorResistance(Measurable<ElectricResistance> tr) throws Exception;
	public Measurable<ElectricResistance> getThermistorResistance() throws Exception;
	
	//FRTD (Four-Wire RTD)
	public void setFRTDType(FRTDType frtdType) throws Exception;
	public FRTDType getFRTDType() throws Exception;
	
	
	
}
