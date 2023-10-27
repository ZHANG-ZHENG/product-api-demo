package top.zhost.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class VolumeUtils {

    public static int calculateVolume(byte[] waveBytes) {
        short[] wave = new short[waveBytes.length / 2];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(waveBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(wave);

        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < wave.length; i++) {
            v += wave[i] * wave[i];
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) wave.length;
        double volume = 10 * Math.log10(mean);
        return (int) volume;
    }

    public static int calculateAmplitude(byte[] waveBytes) {
        short[] wave = new short[waveBytes.length / 2];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(waveBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(wave);

        long v = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < wave.length; i++) {
            v += Math.abs(wave[i]);
        }
        double amp = v / (double) wave.length / 2;
        return (int) amp;
    }

    public static int computeMaxAmplitude(byte[] waveBytes) {
        short[] wave = new short[waveBytes.length / 2];
        // to turn bytes to shorts as either big endian or little endian.
        ByteBuffer.wrap(waveBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(wave);

        long amplitude = 0;
        // 将 buffer 内容取出，进行平方和运算
        for (int i = 0; i < wave.length; i++) {
            if (Math.abs(wave[i]) > amplitude) {
                amplitude = Math.abs(wave[i]);
            }
        }
        return (int) amplitude;
    }
}
