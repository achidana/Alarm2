package com.alarm.kalpan.alarmapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Created by kalpanjasani on 2/22/18.
 */

public class MyAlarmManager {
    static boolean myCreateTimeAlarm(TimeAlarm timeAlarm, Context context)
    {
        AlarmManager alarmManager;
        PendingIntent alarmIntent;
        int hour;
        int minutes;
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("ringtoneUri", timeAlarm.getRingtoneUri());
        //important: always use the above method to make an intent... To retrieve the PendingIntent we make next, which disappears in to the system, we actually need to have the same Intent things of the underlying intent
        alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), timeAlarm.getAlarmID(), intent, 0);

        hour = timeAlarm.getHour();
        minutes = timeAlarm.getMin();

        // Set the alarm to start
        Calendar calendar = Calendar.getInstance();
        Calendar rightNow = (Calendar) calendar.clone();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        // we always want to start the alarm at the start of the minute, not relative to when we actually set it
        calendar.set(Calendar.SECOND, 0);

        if(calendar.compareTo(rightNow) < 0)
        {
            calendar.add(Calendar.DATE, 1);
        }

        //we probably should check for Android version and then make the call below
        alarmManager.setExactAndAllowWhileIdle(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        /*how do we understand that the call above was successful? TODO */
        return true;    /*we return true in any branch, so not useful TODO*/
    }

    static boolean myCancelTimeAlarm(TimeAlarm timeAlarm, Context context)
    {
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, timeAlarm.getAlarmID(), intent, 0);
        AlarmManager alarmManager;

        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        return true;
    }

}
