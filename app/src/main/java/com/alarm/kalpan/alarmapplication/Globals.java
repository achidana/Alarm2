package com.alarm.kalpan.alarmapplication;

import android.app.Application;

import java.sql.Time;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by ashwin on 2/22/18.
 */

public class Globals extends Application
{

    ArrayList<TimeAlarm> alarmObjectsList= new ArrayList<Alarm_object>();

    public Map<String, ArrayList<String>> groupList = new HashMap<>();
    public Map<String, ArrayList<User>> userList = new HashMap<>();
    public Map<String, String> timeList = new HashMap<>();


}

