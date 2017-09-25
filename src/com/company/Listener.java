package com.company;

import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;

public class Listener implements Runnable {
    private TargetDataLine line;

    public Listener(TargetDataLine line) {
        this.line = line;
    }

    @Override
    public void run() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[32];

        int n = 0;
        while (n != 10) {
            int count = line.read(buffer, 0, buffer.length);
            if (count > 0) {
                n++;
                out.write(buffer, 0, count);
            }

            byte ba[] = out.toByteArray();
            for (byte b : ba) {
                System.out.println(b);
            }
        }
    }
}
