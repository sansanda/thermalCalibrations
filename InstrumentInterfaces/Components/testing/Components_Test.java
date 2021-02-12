package testing;
/**

 * 
 */


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import collections.OnlyOneSelected_InstrumentComponentList;
import communications.CommunicationsModuleComponent;
import information.GeneralInformation_Component;

/**
 * @author david
 *
 */
public class Components_Test {
	
	private static final int classVersion = 100;
	
	final static Logger logger = LogManager.getLogger(Components_Test.class);
	

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try {
						
			logger.info("TESTNG INDIVIDUAL COMPONENTS");
			//*************************************************Individual components***************************************************************
			GeneralInformation_Component gic = new GeneralInformation_Component("gic",0,null,"010203","model","davidCO","obs","firmware version 0");
			GeneralInformation_Component gic2 = new GeneralInformation_Component("gic2",0,null,"------","model","davidCO","obs","firmware version 0");
			
			CommunicationsModuleComponent cc = new CommunicationsModuleComponent("myname",0,null);
			System.out.println(gic.toString());
			System.out.println(cc.toString());
			
			//************************************************Collections**************************************************************************
			logger.info("TESTNG COLLECTIONS");
			OnlyOneSelected_InstrumentComponentList l = new OnlyOneSelected_InstrumentComponentList();
			l.add(gic);
			System.out.println(l);
			gic2.select(true);
			l.add(gic2);
			System.out.println(l);
			gic.select(false);
			System.out.println(l);
			gic2.select(true);
			System.out.println(l);
			l.remove(gic);
			System.out.println(l);
			l.remove(gic2);
			System.out.println(l);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
