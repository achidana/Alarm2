package com.alarm.kalpan.alarmapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kalpanjasani on 2/22/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent ringerIntent;

        ringerIntent = new Intent(context, AlarmRinger.class);

        //setting up the stuff in the intent. This is to give the state of the alarm that rang.
        ringerIntent.setAction(intent.getAction());
        ringerIntent.putExtra("AlarmType", intent.getStringExtra("AlarmType"));
        ringerIntent.putExtra("AlarmID", intent.getIntExtra("AlarmID", -1));
        ringerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(ringerIntent);
    }
}
