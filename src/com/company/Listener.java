package com.company;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Listener implements Runnable {

    private TargetDataLine line;

    public Listener(TargetDataLine line) {
        this.line = line;
    }

    private AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; //mono
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
                int count = line.read(buffer, 0, SIZE);
                if (count > 0) {
                    n++;
                    out.write(buffer, 0, count);
                }

                byte ba[] = out.toByteArray();

                System.out.println(Arrays.toString(ba));
            }
        }
    }
}
