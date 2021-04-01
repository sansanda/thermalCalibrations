package units;

import java.util.ArrayList;
import java.util.HashMap;

import common.I_InstrumentComponent;
import common.InstrumentComponent;

public class MeasureUnits_Configurator extends InstrumentComponent implements I_MeasureUnits_Configurator {

	//version 101:  changed constructor for including enable and selected parameters and added  parseFromJSON(JSONObject jObj) method. Still not operative
	//version 100: Initial version. Not working
	
	
	 //**************************************************************************
	 //****************************CONSTANTES************************************
	 //**************************************************************************

	private static final int classVersion = 101;
	
	public MeasureUnits_Configurator(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected) {
		super(name, id, parent, enable, selected);
		// TODO Auto-generated constructor stub
	}

	public MeasureUnits_Configurator(String name, long id, I_InstrumentComponent parent, boolean enable, boolean selected,
			ArrayList<String> descriptiveTags,
			HashMap<String, I_InstrumentComponent> subcomponents) {
		super(name, id, parent, enable, selected, descriptiveTags, subcomponents);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setUnits(String magnitude, String unit, int[] channels) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getUnits(String magnitude, int[] channels) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnitReference(String unit, String reference) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUnitReference(String unit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static int getClassversion() {
		return classVersion;
	}

	
}
