package com.company;

public class Main {
    public static void main(String[] args) throws Exception {
        SoundCard ES8 = new SoundCard("ES8");
        ES8.obtainLine();
        ES8.listen();
    }
}
