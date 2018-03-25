package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.media.AudioManager.STREAM_ALARM;

public class AlarmRinger extends AppCompatActivity {
    Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Uri ringtoneUri;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);
        ringtoneUri = (Uri) getIntent().getParcelableExtra("ringtoneUri");
        ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
        ringtone.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(STREAM_ALARM).build());

        ringtone.play();
    }
}