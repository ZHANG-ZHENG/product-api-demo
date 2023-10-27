package top.zhost.utils;

import top.zhost.ByteUtil;

public class SoundUtil {
	/**
	 * 放大或缩小音量
	 * @param volume 原音量
	 * @param ratio 比率
	 * @return (short) ((b[1] << 8) + (b[0] << 0))
	 */
	public static byte[] getDealVolume(byte[] volumeBytes,float ratio){
		short volume = ByteUtil.toShort(volumeBytes);
		int volumeInt = volume;
		int volumeIntRatio = (int) (volumeInt * ratio);
		if(volume > 32767){
			volume = 32767;
		}else if(volume < -32767){
			volume = -32767;
		}
		short volumeShortRatio = (short) volumeIntRatio;
		
        byte[] result = new byte[2]; 
        result[0] = (byte)((volumeShortRatio >> 0)&0xff); 
        result[1] = (byte)((volumeShortRatio >> 8)&0xff);
		return result;
	}
}
