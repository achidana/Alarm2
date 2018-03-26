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
        Intent intent2 = new Intent(context, AlarmRinger.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("ringtoneUri", intent.getParcelableExtra("ringtoneUri"));  /* pass on the correct uri forward */
        context.startActivity(intent2);
    }
}
