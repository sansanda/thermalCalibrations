package measure;

import java.util.Arrays;

public class Measures {

	final static String VERSION = "1.0.0";

	public Measure[] measures = null;
	
	/**
	 * 
	 * @param dataArrays is a String[] with the dataArray of all the measures readed
	 * @param nElementsPerMaesure i s the number of elements that containts every measure readed
	 */
	public Measures(String[] dataArrays, int nElementsPerMaesure)
	{
		this.measures = new Measure[dataArrays.length/nElementsPerMaesure];
		
		for (int i=0;i<dataArrays.length;i=i+nElementsPerMaesure)
		{
			String[] dataArray = new String[nElementsPerMaesure];
			System.arraycopy(dataArrays, i, dataArray, 0, dataArray.length);
			this.measures[i/nElementsPerMaesure] = new Measure(dataArray);
		}
	}
	
	public static String getVersion() {
		return VERSION;
	}
	
	@Override
	public String toString() {
		return "Measures [measures=" + Arrays.toString(measures) + "]";
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
