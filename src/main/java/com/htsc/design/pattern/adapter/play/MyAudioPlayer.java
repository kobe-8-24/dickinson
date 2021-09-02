package com.htsc.design.pattern.adapter.play;

class MyAudioPlayer implements AudioPlayer {
    @Override
    public void playAudio(String fileName) {
        System.out.println("Playing. Name: "+ fileName);
    }
}
