package com.company;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

public class Listener implements Runnable {

    private TargetDataLine line;

    public Listener(TargetDataLine line) {
        this.line = line;
    }

    private AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 4;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    @Override
    public void run() {
        int SIZE = line.getBufferSize();
        try {
            line.open(getFormat());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        line.start();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[SIZE];

        int n = 0;
        while (true) {
            if (line.isOpen()) {
                out.reset();
                int count = line.read(buffer, 0, SIZE);
                if (count > 0) {
                    n++;
                    out.write(buffer, 0, count);
                }

                byte ba[] = out.toByteArray();

                short sa1[] = new short[ba.length / 2];
                short sa2[] = new short[ba.length / 2];
                short sa3[] = new short[ba.length / 2];
                short sa4[] = new short[ba.length / 2];

                int j = 0;
                for (int i = 0; i < ba.length; i += 8) {
                    sa1[j] = littleEndian(ba[i + 0], ba[i + 1]);
                    sa2[j] = littleEndian(ba[i + 2], ba[i + 3]);
                    sa3[j] = littleEndian(ba[i + 4], ba[i + 5]);
                    sa4[j] = littleEndian(ba[i + 6], ba[i + 7]);
                    j++;
                }

                long avg = 0;
                for (short s : sa4) {
                    avg += s;
                }

                System.out.println(avg / sa2.length);
            }
        }
    }

    private short bigEndian(byte b1, byte b2) {
        return (short) ((b1 << 8) | (b2 & 0xFF));
    }

    private short littleEndian(byte b1, byte b2) {
        return (short) ((b1 & 0xFF) | (b2 << 8));
    }

}
