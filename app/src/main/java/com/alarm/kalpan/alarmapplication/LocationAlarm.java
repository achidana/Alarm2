package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.location.Geofence;

import java.util.ArrayList;

@Entity
public class LocationAlarm implements AlarmDisplayable {
    private boolean isOn;
    private String ringtoneUri;
    private String name;
    private float radius;

    @PrimaryKey
    private int alarmID;

    private double latitude;
    private double longitude;

    @Ignore
    public LocationAlarm(Context context)
    {
        ArrayList<LocationAlarm> locationAlarms = ((Globals) context.getApplicationContext()).locationAlarms;
        radius = 200;
        boolean availableFlag = true;
        for(int i = 0;; i++)
        {
            availableFlag = true;
            for(LocationAlarm locationAlarm : locationAlarms)
            {
                if(i == locationAlarm.getAlarmID())
                {
                    availableFlag = false;
                    break;
                }
            }

            if(availableFlag)
            {
                this.alarmID = i;
                break;
            }
        }

        this.latitude = 0;
        this.longitude = 0;
        isOn = false;
        name = "Location Alarm " + alarmID;
    }


    //this constructor is called by the database manager
    public LocationAlarm(String name, boolean isOn, double latitude, double longitude, int alarmID, float radius, String ringtoneUri)
    {
        this.name = name;
        this.isOn = isOn;
        this.latitude=latitude;
        this.longitude=longitude;
        this.alarmID = alarmID;
        this.radius = radius;
        this.ringtoneUri = ringtoneUri;
    }

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public boolean getIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public String getRingtoneUri() {
        return ringtoneUri;
    }

    public void setRingtoneUri(String ringtoneUri) {
        this.ringtoneUri = ringtoneUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getShortDetail()
    {
        return String.format("Location: %.2f, %.2f", latitude, longitude);

    }

    public void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
