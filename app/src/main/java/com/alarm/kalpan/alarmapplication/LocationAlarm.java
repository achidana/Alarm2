package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Entity;
import android.net.Uri;

import com.google.android.gms.location.Geofence;

//@Entity
public class LocationAlarm {
    private boolean isOn;
    private Geofence geofence;
    private Uri RingtoneURI;
    private String name;
    private long radius;
    private int alarmID;

    private double latitude;
    private double longitude;


    private TimeAlarm backupTimeAlarm;


    public LocationAlarm(String name, boolean isOn, double latitude, double longitude, TimeAlarm backupTimeAlarm)
    {
        this.name = name;
        this.isOn = isOn;
        this.latitude=latitude;
        this.longitude=longitude;
        this.backupTimeAlarm=backupTimeAlarm;
    }


    public TimeAlarm getBackupTimeAlarm() {
        return backupTimeAlarm;
    }

    public void setBackupTimeAlarm(TimeAlarm backupTimeAlarm) {
        this.backupTimeAlarm = backupTimeAlarm;
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



    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public Geofence getGeofence() {
        return geofence;
    }

    public void setGeofence(Geofence geofence) {
        this.geofence = geofence;
    }

    public Uri getRingtoneURI() {
        return RingtoneURI;
    }

    public void setRingtoneURI(Uri ringtoneURI) {
        RingtoneURI = ringtoneURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }



}
