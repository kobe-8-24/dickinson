package com.htsc.design_pattern.adapter.play;

class MyVideoPlayer implements VideoPlayer {
    @Override
    public void playVideo(String fileName) {
        System.out.println("Playing. Name: "+ fileName);
    }
}
