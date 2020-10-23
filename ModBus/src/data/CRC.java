package data;

public class CRC {
	public CRC(){
		
	}
	public char[] getCRC(char[] _data){
		char[] res = new char[8];
		long crc = 0xffff;
		long next;
		long carry;
		long n;
		char crcl,crch;
		
		for (int i = 0;i<_data.length;i++){
			next = (long)_data[i];
			crc ^= next;
			for(n=0;n<8;n++){
				carry = crc & 1;
				crc >>= 1;
				if (carry==1){
					crc ^= 0xA001;
				}
			}
		}
		crch = (char)(crc / 256);
		crcl = (char)(crc % 256);
		//System.out.println("crcl = "+Integer.toHexString(crcl));
		//System.out.println("crch = "+Integer.toHexString(crch));
		 for (int i=0;i<6;i++){
			 res[i]=_data[i];
		 }
		res[6]=crcl;
		res[7]=crch;
		return res;
	}
	
}
