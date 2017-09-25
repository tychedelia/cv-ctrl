package com.company;

import javax.sound.sampled.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Mixer mixer = Arrays.stream(AudioSystem.getMixerInfo())
                    .filter(x -> x.getName().contains("ES8"))
                    .findFirst()
                    .map(AudioSystem::getMixer)
                    .orElseThrow(() -> new Exception("Cannot read soundcard"));

            Line.Info info = mixer.getTargetLineInfo()[0];
            TargetDataLine line = (TargetDataLine) mixer.getLine(info);
            line.open();
            new Listener(line).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; //mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
}
