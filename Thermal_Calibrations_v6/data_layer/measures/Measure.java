package measures;

public class Measure {
	private long measureNumber;
	private String measureDescription;
	public Measure(long measureNumber, String measureDescription) {
		super();
		this.measureNumber = measureNumber;
		this.measureDescription = measureDescription;
	}
	/**
	 * @return the measureNumber
	 */
	public long getMeasureNumber() {
		return measureNumber;
	}
	/**
	 * @return the measureDescription
	 */
	public String getMeasureDescription() {
		return measureDescription;
	}
	/**
	 * @param measureNumber the measureNumber to set
	 */
	public void setMeasureNumber(long measureNumber) {
		this.measureNumber = measureNumber;
	}
	/**
	 * @param measureDescription the measureDescription to set
	 */
	public void setMeasureDescription(String measureDescription) {
		this.measureDescription = measureDescription;
	}
	public String toString() {
		String res = "";
		res = res + "MeasureNumber = " + this.getMeasureNumber() + "\n";
		res = res + "MeasureDescription = " + this.getMeasureDescription() + "\n";
		return res;
	}

}
