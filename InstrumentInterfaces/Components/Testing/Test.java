package Testing;
/**

 * 
 */


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
			GeneralInformation_Component.setVersion(0);
			GeneralInformation_Component gic = new GeneralInformation_Component("gic",0,null,"010203","model","davidCO","obs","firmware version 0");
			CommunicationsComponent.setVersion(1);
			CommunicationsComponent cc = new CommunicationsComponent("myname",0,null);
			System.out.println(gic.toString());
			System.out.println(cc.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
