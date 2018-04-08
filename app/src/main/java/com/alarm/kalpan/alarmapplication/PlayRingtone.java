package com.alarm.kalpan.alarmapplication;

import android.media.AudioAttributes;
import android.media.Ringtone;

/**
 * Created by kalpanjasani on 3/25/18.
 */

public class PlayRingtone implements Runnable{
    Ringtone ringtone;
    int timeInSeconds;
    public PlayRingtone(Ringtone ringtone, int timeInSeconds)
    {
        this.ringtone = ringtone;
        this.timeInSeconds = timeInSeconds;
    }
    @Override
    public void run() {
        ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());

        // need to up the volume, vibrate the phone, and check the thread location of the play
        ringtone.play();
        try
        {
            Thread.sleep((long) timeInSeconds * 1000);
        }
        catch(InterruptedException e)
        {
            // do nothing
        }
        finally {
            ringtone.stop();
        }

    }
}
