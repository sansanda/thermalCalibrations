package Testing;
/**

 * 
 */


import common.OnlyOneSelected_InstrumentComponentList;
import communications.CommunicationsComponent;
import information.GeneralInformation_Component;

/**
 * @author david
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try {
			
			System.out.println("TESTNG INDIVIDUAL COMPONENTS");
			//*************************************************Individual components***************************************************************
			GeneralInformation_Component.setVersion(0);
			GeneralInformation_Component gic = new GeneralInformation_Component("gic",0,null,"010203","model","davidCO","obs","firmware version 0");
			GeneralInformation_Component gic2 = new GeneralInformation_Component("gic",0,null,"010203","model","davidCO","obs","firmware version 0");
			
			CommunicationsComponent.setVersion(1);
			CommunicationsComponent cc = new CommunicationsComponent("myname",0,null);
			System.out.println(gic.toString());
			System.out.println(cc.toString());
			
			//************************************************Collections**************************************************************************
			System.out.println("TESTNG COLLECTIONS");
			OnlyOneSelected_InstrumentComponentList l = new OnlyOneSelected_InstrumentComponentList();
			l.add(gic);
			System.out.println(l);
			gic2.select(true);
			l.add(gic2);
			System.out.println(l);
			gic.select(false);
			System.out.println(l);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
