package com.htsc.design_pattern.adapter.play;

public class PlayMain {
    public static void main(String[] args) {
        MyPlayer myPlayer = new MyPlayer();

        myPlayer.play("mp3", "h.mp3");
        myPlayer.play("avi", "me.avi");
    }
}
