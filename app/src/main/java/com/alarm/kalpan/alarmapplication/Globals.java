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
    public Map<String, ArrayList<String>> groupList;
    public Map<String, ArrayList<User>> userList;
    public Map<String, String> timeList;
    public ApplicationDatabase db;      // the single db for the whole application
    User theUser;

    @Override
    public void onCreate()
    {
        super.onCreate();
        timeAlarms = new ArrayList<>();
        groupList = new HashMap<>();
        userList = new HashMap<>();
        timeList = new HashMap<>();
        db = Room.databaseBuilder(getApplicationContext(), ApplicationDatabase.class, "AppDatabase").build();
    }
}

