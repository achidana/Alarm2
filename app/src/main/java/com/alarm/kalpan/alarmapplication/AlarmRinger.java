package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.media.AudioManager.STREAM_ALARM;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;

public class AlarmRinger extends AppCompatActivity {
    Ringtone ringtone;
    Thread thread;
    PowerManager.WakeLock wakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Uri ringtoneUri;
        Button stopButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);
        ringtoneUri = (Uri) getIntent().getParcelableExtra("ringtoneUri");
        ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
        Runnable runnable = new PlayRingtone(ringtone, 25);

        //next lines are to ensure that device screen wakes up
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP, "Alarm application");
        wakeLock.acquire(60000);    // the actual call that will wake up the screen. Also, yo might want to call the matching method to give the lock, right now, it would do that with timeout of some seconds
        thread = new Thread(runnable, "playRingtoneThread");
        thread.start();

        // TODO: make proper nomenclature of ID
        stopButton = findViewById(R.id.button);
        stopButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                thread.interrupt();
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    //do nothing
                }
                finally
                {
                    finish();
                }
            }
        });
    }

}