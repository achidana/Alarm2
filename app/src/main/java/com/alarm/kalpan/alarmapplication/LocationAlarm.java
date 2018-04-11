package com.alarm.kalpan.alarmapplication;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class LocationAlarm extends IntentService {


    public LocationAlarm() {
        super("LocationAlarm");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Toast.makeText(getApplicationContext(), "Error in GEOFENCE ", Toast.LENGTH_LONG).show();
            return;

        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            Toast.makeText(getApplicationContext(), triggeringGeofences.get(0).getRequestId() + "!!!!!", Toast.LENGTH_LONG).show();
            Log.e("!!!!!", "!!!!!!!@");


        }


    }
}
