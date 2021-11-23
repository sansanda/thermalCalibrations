package measures;

import com.schneide.quantity.electricalQuantities.Ohm;

public class ResistanceMeasure extends Measure {
	private Ohm resistance;

	public ResistanceMeasure(long measureNumber, String measureDescription,
			double resistance) {
		super(measureNumber, measureDescription);
		this.resistance = new Ohm(resistance);
	}
	public Ohm getResistanceInOhms(){
		return resistance;
	}
	public String toString() {
		String res = "";
		res = res + super.toString();
		res = res + "resistance = " + resistance.toString()+ "\n";
		return res;
	}
}
