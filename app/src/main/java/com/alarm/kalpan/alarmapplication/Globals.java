package com.alarm.kalpan.alarmapplication;

import android.app.Application;
import android.arch.persistence.room.Room;

import java.sql.Time;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by ashwin on 2/22/18.
 */

public class Globals extends Application
{

    ArrayList<TimeAlarm> timeAlarms;
    ArrayList<LocationAlarm> locationAlarms;
    public ApplicationDatabase db;      // the single db for the whole application
    User theUser;

    public Map<String, ArrayList<String>> groupList = new HashMap<>();
    public Map<String, ArrayList<String>> numberList = new HashMap<>();
    public Map<String, ArrayList<User>> userList = new HashMap<>();
    public Map<String, String> timeList = new HashMap<>();

    public String userName = "developer"; //Input username during verification
    public String userID = "developerID";   //phone number
    public String firebaseToken;    //id
    public String eID;

    public Token userInfo;  //

    public boolean isVerified;

    // the maximum number of backup numbers allowed for one call
    int maxBackupNumbers;

    @Override
    public void onCreate()
    {
        super.onCreate();
        groupList = new HashMap<>();
        userList = new HashMap<>();
        timeList = new HashMap<>();
        db = Room.databaseBuilder(getApplicationContext(), ApplicationDatabase.class, "AppDatabase").build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                timeAlarms = new ArrayList<>(db.timeAlarmDAO().loadTimeAlarms());
                locationAlarms = new ArrayList<>(db.locationAlarmDAO().loadAllLocationAlarms());
            }
        }).start();
        maxBackupNumbers = 5;
    }
}

