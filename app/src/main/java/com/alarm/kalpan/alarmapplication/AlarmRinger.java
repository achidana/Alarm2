package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;

public class AlarmRinger extends AppCompatActivity {
    Ringtone ringtone;
    Thread playRingtoneThread;
    Thread failThread;
    PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button stopButton;
        super.onCreate(savedInstanceState);

        final int alarmID = getIntent().getIntExtra("AlarmID", -1);
        final String alarmType = getIntent().getStringExtra("AlarmType");

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork(alarmType, alarmID);
            }
        });

        worker.start();
        //this Window can be used to change settings. It has to be set before doing setContentView
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_alarm_ringer);


        //next lines are to ensure that device screen wakes up
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP, "Alarm application");
        wakeLock.acquire(60000);    // the actual call that will wake up the screen. Also, yo might want to call the matching method to give the lock, right now, it would do that with timeout of some seconds




        // TODO: make proper nomenclature of ID
        stopButton = findViewById(R.id.button);
        stopButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //todo: what if interrupted before started!
                playRingtoneThread.interrupt();
                failThread.interrupt();
                finish();
            }
        });
    }

    public void doWork(String alarmType, int id)
    {
        TimeAlarm timeAlarm;
        Uri ringtoneUri;
        Globals globals = (Globals) getApplication();
        TimeAlarmDAO timeAlarmDAO = globals.db.timeAlarmDAO();
        timeAlarm = timeAlarmDAO.getAlarm(id);

        //make threads to start timer and play the ringtone
        if(timeAlarm.getRingtoneUri() == null)
            ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        else
            ringtoneUri = Uri.parse(timeAlarm.getRingtoneUri());
        ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);

        Runnable runnable = new PlayRingtone(ringtone, 10);

        playRingtoneThread = new Thread(runnable, "playRingtoneThread");
        playRingtoneThread.start();

        failThread = new Thread(new FailHandler(10, timeAlarm));

        failThread.start();
    }
}