package com.company;

import javax.sound.sampled.*;
import java.util.Arrays;

import static java.util.Objects.isNull;

public class SoundCard {
    private String name;
    private TargetDataLine line;

    public SoundCard() {}

    public SoundCard(String name) {
        this.name = name;
    }

    public void obtainLine() {
        try {
            Mixer mixer = Arrays.stream(AudioSystem.getMixerInfo())
                    .filter(x -> x.getName().contains(name))
                    .findFirst()
                    .map(AudioSystem::getMixer)
                    .orElseThrow(() -> new Exception("Cannot read soundcard"));

            Line.Info info = mixer.getTargetLineInfo()[0];
            line = (TargetDataLine) mixer.getLine(info);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void open() {
        try {
            line.open();
        } catch (LineUnavailableException luex) {
            luex.printStackTrace();
        }
    }

    public void listen() throws Exception{
        if (isNull(line)) {
            throw new Exception("Cannot listen without line");
        }
        new Listener(line).run();
    }

    // G+S
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TargetDataLine getLine() {
        return line;
    }
}
