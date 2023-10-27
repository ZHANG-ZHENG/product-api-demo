package top.zhost;

public class ByteUtil {
	
	public static byte[] getArrayCopy(byte[] srcBytes,int srcPos,int length){
		byte[] destBytes = new byte[length]; 
		System.arraycopy(srcBytes,srcPos,destBytes ,0,length);
		return destBytes;
	}

	public static int toInt(byte[] b) {
		return ((b[3] << 24) + (b[2] << 16) + (b[1] << 8) + (b[0] << 0));
	}
	
    static long toLong(byte[] bs)  throws Exception {
        int bytes = bs.length;
        if(bytes > 1) {
	        if((bytes % 2) != 0 || bytes > 8) {
	            throw new Exception("not support");
	        }
        }
        switch(bytes) {
	        case 0:
	            return 0;
	        case 1:
	            return (long)((bs[0] & 0xff));
	        case 2:
	            return (long)((bs[1] & 0xff) <<8 | (bs[0] & 0xff));
	        case 4:
	            return (long)((bs[3] & 0xffL) <<24 | (bs[2] & 0xffL) << 16 | (bs[1] & 0xffL) <<8 | (bs[0] & 0xffL));
	        case 8:
	            return (long)((bs[7] & 0xffL) <<56 | (bs[6] & 0xffL) << 48 | (bs[5] & 0xffL) <<40 | (bs[4] & 0xffL)<<32 | 
	                    (bs[3] & 0xffL) <<24 | (bs[2] & 0xffL) << 16 | (bs[1] & 0xffL) <<8 | (bs[0] & 0xffL));
	        default:
	            throw new Exception("not support");     
        }
        //return 0;
    }
 
	public static short toShort(byte[] b) {
		return (short) ((b[1] << 8) + (b[0] << 0));
	}
}
