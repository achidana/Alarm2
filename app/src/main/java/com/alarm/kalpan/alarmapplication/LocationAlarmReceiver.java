package com.alarm.kalpan.alarmapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class LocationAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("flag ashwinkalpan");
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Intent ringer = new Intent(context, AlarmRinger.class);
        ringer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ringer.setAction(intent.getAction());
        ringer.putExtra("AlarmType", intent.getStringExtra("AlarmType"));
        ringer.putExtra("AlarmID", intent.getIntExtra("AlarmID", -1));

        context.startActivity(ringer);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(ringer);
        }
    }
}
