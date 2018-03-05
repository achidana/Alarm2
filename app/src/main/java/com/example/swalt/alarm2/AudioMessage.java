package com.example.swalt.alarm2;

import android.media.MediaPlayer;

/**
 * Created by JohnRedmon on 2/20/18.
 * Contents of an audio message, name is what user sees mp3 is attached file
 */

public class AudioMessage {
    private String name;
    private MediaPlayer mp3;

    public AudioMessage(String name, MediaPlayer mp3) {
        this.name = name;
        this.mp3 = mp3;
    }

    public String getName() {
        return this.name;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mp3;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setMp3(MediaPlayer mp) {
        this.mp3 = mp;
    }
}
