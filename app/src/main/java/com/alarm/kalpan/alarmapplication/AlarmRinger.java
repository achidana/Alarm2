package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;

public class AlarmRinger extends AppCompatActivity {
    Ringtone ringtone;
    Thread thread;
    Thread thread1;
    PowerManager.WakeLock wakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Uri ringtoneUri;
        Button stopButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringer);
        ringtoneUri = (Uri) getIntent().getParcelableExtra("ringtoneUri");
        ringtone = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri);
        Runnable runnable = new PlayRingtone(ringtone, 10);

        //next lines are to ensure that device screen wakes up
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP, "Alarm application");
        wakeLock.acquire(60000);    // the actual call that will wake up the screen. Also, yo might want to call the matching method to give the lock, right now, it would do that with timeout of some seconds
        thread = new Thread(runnable, "playRingtoneThread");
        thread.start();

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                afterFail(10);  // 25 second fail save time
            }
        });

        thread1.start();
        // TODO: make proper nomenclature of ID
        stopButton = findViewById(R.id.button);
        stopButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                thread.interrupt();
                thread1.interrupt();
                finish();
            }
        });
    }

    public void afterFail(int timeInSeconds)
    {
        try
        {
            Thread.sleep(timeInSeconds * 1000);
        }

        catch(InterruptedException e)
        {
            return;
        }

        String text;
        String phoneNumber;

        phoneNumber = "7656378554";

        text = "hello%20world";
        System.out.println(text);
        URL url;
        HttpURLConnection conn;
        StringBuilder result = new StringBuilder();
        try
        {
            url = new URL("http://45.56.125.90:5000/" + "text/" + phoneNumber + "/" + text);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line = rd.readLine()) != null)
            {
                result.append(line);
            }

            String res = result.toString();
            conn.disconnect();
        }

        catch (MalformedURLException e)
        {
            url = null;
            // handle
        }

        catch(IOException e)
        {
            conn = null;
        }

        // do scott
    }
}