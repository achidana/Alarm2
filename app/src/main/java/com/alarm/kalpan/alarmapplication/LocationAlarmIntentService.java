package com.alarm.kalpan.alarmapplication;

import android.app.IntentService;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;

public class LocationAlarmIntentService extends IntentService {


    public LocationAlarmIntentService() {
        super("LocationAlarm");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("flag ashwinkalpan");
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Intent ringer = new Intent(getApplicationContext(), AlarmRinger.class);
        ringer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ringer.putExtra("ringtoneUri", ringtoneUri);
        getApplicationContext().startActivity(ringer);


    }
}
