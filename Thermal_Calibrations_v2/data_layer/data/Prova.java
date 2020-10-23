package data;

public class Prova extends CalibrationProfile {

	public Prova(String _type, String _obs) throws Exception {
		super(_type, _obs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Prova p = new Prova(Prova.TEMPERATURE_VS_VOLTAGE_TYPE,"a");
		System.out.println(p.toString());
		//p.setType("a");
	}

}
