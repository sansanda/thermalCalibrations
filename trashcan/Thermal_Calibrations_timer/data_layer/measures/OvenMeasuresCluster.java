package measures;

public class OvenMeasuresCluster {
	private TemperatureMeasure 	ovenDesiredTemperature;
	private TemperatureMeasure 	ovenTemperatureMeasure;
	private TemperatureMeasure 	ovenPt100TemperatureMeasure;
	private ResistanceMeasure 	ovenPt100ResistanceMeasure;
	private double 				ovenTemperatureErrorInPercent;
	private double				ovenOutputPowerInPercent;
	public OvenMeasuresCluster(TemperatureMeasure ovenDesiredTemperature,
			TemperatureMeasure ovenTemperatureMeasure,
			TemperatureMeasure ovenPt100TemperatureMeasure,
			ResistanceMeasure ovenPt100ResistanceMeasure,
			double ovenTemperatureErrorInPercent,
			double ovenOutputPowerInPercent) {
		super();
		this.ovenDesiredTemperature = ovenDesiredTemperature;
		this.ovenTemperatureMeasure = ovenTemperatureMeasure;
		this.ovenPt100TemperatureMeasure = ovenPt100TemperatureMeasure;
		this.ovenPt100ResistanceMeasure = ovenPt100ResistanceMeasure;
		this.ovenTemperatureErrorInPercent = ovenTemperatureErrorInPercent;
		this.ovenOutputPowerInPercent = ovenOutputPowerInPercent;
	}
	/**
	 * @return the ovenDesiredTemperature
	 */
	public TemperatureMeasure getOvenDesiredTemperature() {
		return ovenDesiredTemperature;
	}
	/**
	 * @return the ovenTemperatureMeasure
	 */
	public TemperatureMeasure getOvenTemperatureMeasure() {
		return ovenTemperatureMeasure;
	}
	/**
	 * @return the ovenPt100TemperatureMeasure
	 */
	public TemperatureMeasure getOvenPt100TemperatureMeasure() {
		return ovenPt100TemperatureMeasure;
	}
	/**
	 * @return the ovenPt100ResistanceMeasure
	 */
	public ResistanceMeasure getOvenPt100ResistanceMeasure() {
		return ovenPt100ResistanceMeasure;
	}
	/**
	 * @return the ovenTemperatureErrorInPercent
	 */
	public double getOvenTemperatureErrorInPercent() {
		return ovenTemperatureErrorInPercent;
	}
	/**
	 * @return the ovenOutputPowerInPercent
	 */
	public double getOvenOutputPowerInPercent() {
		return ovenOutputPowerInPercent;
	}
	/**
	 * @param ovenDesiredTemperature the ovenDesiredTemperature to set
	 */
	public void setOvenDesiredTemperature(TemperatureMeasure ovenDesiredTemperature) {
		this.ovenDesiredTemperature = ovenDesiredTemperature;
	}
	/**
	 * @param ovenTemperatureMeasure the ovenTemperatureMeasure to set
	 */
	public void setOvenTemperatureMeasure(TemperatureMeasure ovenTemperatureMeasure) {
		this.ovenTemperatureMeasure = ovenTemperatureMeasure;
	}
	/**
	 * @param ovenPt100TemperatureMeasure the ovenPt100TemperatureMeasure to set
	 */
	public void setOvenPt100TemperatureMeasure(
			TemperatureMeasure ovenPt100TemperatureMeasure) {
		this.ovenPt100TemperatureMeasure = ovenPt100TemperatureMeasure;
	}
	/**
	 * @param ovenPt100ResistanceMeasure the ovenPt100ResistanceMeasure to set
	 */
	public void setOvenPt100ResistanceMeasure(
			ResistanceMeasure ovenPt100ResistanceMeasure) {
		this.ovenPt100ResistanceMeasure = ovenPt100ResistanceMeasure;
	}
	/**
	 * @param ovenTemperatureErrorInPercent the ovenTemperatureErrorInPercent to set
	 */
	public void setOvenTemperatureErrorInPercent(
			double ovenTemperatureErrorInPercent) {
		this.ovenTemperatureErrorInPercent = ovenTemperatureErrorInPercent;
	}
	/**
	 * @param ovenOutputPowerInPercent the ovenOutputPowerInPercent to set
	 */
	public void setOvenOutputPowerInPercent(double ovenOutputPowerInPercent) {
		this.ovenOutputPowerInPercent = ovenOutputPowerInPercent;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
