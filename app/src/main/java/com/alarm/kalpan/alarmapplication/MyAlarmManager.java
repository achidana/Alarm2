package com.alarm.kalpan.alarmapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kalpanjasani on 2/22/18.
 */

public class MyAlarmManager {
    static boolean myCreateTimeAlarm(TimeAlarm timeAlarm, Context context) {
        AlarmManager alarmManager;
        PendingIntent alarmIntent;
        int hour;
        int minutes;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);

        intent.setAction("Ring time alarm");    //this makes intents of time alarms unique
        intent.putExtra("AlarmType", "TimeAlarm");
        intent.putExtra("AlarmID", timeAlarm.getAlarmID());

        //important: always use the above method to make an intent... To retrieve the PendingIntent we make next, which disappears in to the system, we actually need to have the same Intent things of the underlying intent
        // but put extra doesn't make the intent different.
        // in our app, we differentiate two alarms' pending intent using the alarm id
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

        if (calendar.compareTo(rightNow) < 0) {
            calendar.add(Calendar.DATE, 1);
        }

        //we probably should check for Android version and then make the call below
        alarmManager.setExactAndAllowWhileIdle(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        /*how do we understand that the call above was successful? TODO */
        return true;    /*we return true in any branch, so not useful TODO*/
    }

    static boolean myCancelTimeAlarm(TimeAlarm timeAlarm, Context context) {
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        intent.setAction("Ring time alarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, timeAlarm.getAlarmID(), intent, 0);
        AlarmManager alarmManager;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
        return true;
    }


    static boolean myCreateLocationAlarm(LocationAlarm locationAlarm, Context context) {

        String key;
        double latitude;
        double longitude;
        float radius;
        int alarmID;
        GeofencingClient mGeofencingClient;


        latitude = locationAlarm.getLatitude();
        longitude = locationAlarm.getLongitude();
        key = latitude + ":" + longitude;
        radius = locationAlarm.getRadius();
        alarmID = locationAlarm.getAlarmID();

        PendingIntent geofencePendingIntent;
        GeofencingRequest geofencingRequest;

        mGeofencingClient = LocationServices.getGeofencingClient(context.getApplicationContext());

        Geofence g = new Geofence.Builder()
                .setRequestId(key) //v2 is the raduis
                .setCircularRegion(
                        latitude, longitude, radius   // the last argument is the radius in metres TODO: change this to dynamic
                )
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();

        Intent intent = new Intent(context.getApplicationContext(), LocationAlarmReceiver.class);
        intent.setAction("Ring location alarm");    //this makes intents of time alarms unique
        intent.putExtra("AlarmType", "LocationAlarm");
        intent.putExtra("AlarmID", locationAlarm.getAlarmID());
        geofencePendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // add the geofence

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // initial_transition_enter indicates that if user is already in one geofence, trigger the transition_enter move
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(g);
        geofencingRequest = builder.build();

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGeofencingClient.addGeofences(geofencingRequest, geofencePendingIntent);
        }

        // if permission failed for fine location, return false
        else
            return false;

        return true;
    }

    static boolean myCancelLocationAlarm(LocationAlarm locationAlarm, Context context)
    {
        return true;
    }

    // remove from arraylist and database
    public static boolean removeTimeAlarmFromID(final Globals globals, final int alarmID)
    {
        ArrayList<TimeAlarm> timeAlarms = globals.timeAlarms;
        boolean removedFlag = false;

        for(int i = 0; i < timeAlarms.size(); i++)
        {
            if(alarmID == timeAlarms.get(i).getAlarmID())
            {
                timeAlarms.remove(i);
                removedFlag = true;
                break;
            }
        }

        if(removedFlag)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    globals.db.timeAlarmDAO().deleteAlarmFromID(alarmID);
                }
            }).start();
        }

        return removedFlag;

    }

    //remove from data structure and database
    public static boolean removeLocationAlarmFromID(final Globals globals, final int alarmID)
    {
        ArrayList<LocationAlarm> locationAlarms = globals.locationAlarms;
        boolean removedFlag = false;

        for (int i = 0; i < locationAlarms.size(); i++) {
            if(alarmID == locationAlarms.get(i).getAlarmID())
            {
                locationAlarms.remove(i);
                removedFlag = true;
                break;
            }
        }

        if(removedFlag)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    globals.db.locationAlarmDAO().deleteLocationAlarmFromID(alarmID);
                }
            }).start();
        }

        return removedFlag;
    }

    public static boolean updateTimeAlarmToDatabase(final Context context, final TimeAlarm timeAlarm)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDatabase db = ((Globals) context.getApplicationContext()).db;
                TimeAlarmDAO timeAlarmDAO = db.timeAlarmDAO();
                timeAlarmDAO.updateAlarms(timeAlarm);
            }
        }).start();

        //todo: manage return type based on succes, currently returning true for all database query functions
        return true;
    }

    public static boolean deleteTimeAlarmFromDatabase(final Context context, final  TimeAlarm timeAlarm)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDatabase db = ((Globals) context.getApplicationContext()).db;
                TimeAlarmDAO timeAlarmDAO = db.timeAlarmDAO();
                timeAlarmDAO.deleteAlarms(timeAlarm);
            }
        }).start();

        return true;
    }

    public static boolean insertTimeAlarmToDatabase(final Context context, final TimeAlarm timeAlarm)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ApplicationDatabase db = ((Globals) context.getApplicationContext()).db;
                TimeAlarmDAO timeAlarmDAO = db.timeAlarmDAO();
                timeAlarmDAO.insertAlarms(timeAlarm);
            }
        }).start();

        return true;
    }

}
