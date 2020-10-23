package test;

public class testApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//ICommunicationPort sp = new S_Port2("COM7");
			//sp.sendData("*IDN?");
			
/*			int dataLength = sp.getReadDataLength();
			byte[] data = sp.getReadData();
			Double res;
			
			if (dataLength>0){
				String str = new String(data,0,dataLength);
				if (str!=null)
				{
					System.out.println(str);
					String[] rawData = str.split(",");
					rawData = rawData[0].split("VDC");
					for (int i=0;i<rawData.length;i++){
						System.out.print(rawData[i]);
						System.out.print(",");
					}
					res = Double.valueOf(rawData[0]);
					System.out.println(res);
				}
			}*/
			
			//System.out.println(sp.readDataAsString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
