package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by ashwin on 2/22/18.
 */

@Entity
public class TimeAlarm implements AlarmDisplayable {

    private int hour;
    private int min;
    private boolean isText;
    private boolean isCall;
    private String name;
    private boolean isOn;
    private String ringtoneUri;

    @PrimaryKey
    private int alarmID;
    private ArrayList<String> numbersToNotify;
    private String textMessage;
    private String voiceMessage;
    private int day;

    @Ignore
    private String ampm;
    //TODO: have other constructors that fill in default stuff if not provided (like ringtone and on off and such

    private ArrayList<String> members = new ArrayList<String>();
    private String esID;
    private String gAdmin;


    @Ignore
    public TimeAlarm(int hour, int min, boolean isText, boolean isCall, String name, boolean isOn, String ringtoneUri) {
        this.hour = hour;
        this.min = min;
        this.isText = isText;
        this.isCall = isCall;
        this.name = name;
        this.ampm = "AM";
        if(this.hour >= 12) {
            ampm = "PM";
        }
        this.isOn=isOn;
        this.ringtoneUri = ringtoneUri;

        Random r = new Random();

        //todo: have correct id
        alarmID = r.nextInt();
        members = new ArrayList<>(0);

        esID = "";
        gAdmin = "";
    }

    public TimeAlarm(Context context)
    {
        ArrayList<TimeAlarm> timeAlarms = ((Globals)context.getApplicationContext()).timeAlarms;
        boolean availableFlag = true;
        for(int i = 0;; i++)
        {
            availableFlag = true;
            for(TimeAlarm timeAlarm : timeAlarms)
            {
                if(i == timeAlarm.getAlarmID())
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
    }
    public TimeAlarm(int hour, int min, int day, boolean isText, boolean isCall, String name, boolean isOn, String ringtoneUri, ArrayList<String> numbersToNotify, String textMessage, String voiceMessage, int alarmID, String esID, String gAdmin, ArrayList<String> members)
    {
        this.hour = hour;
        this.min = min;
        this.isText = isText;
        this.isCall = isCall;
        this.name = name;
        this.isOn = isOn;
        this.ringtoneUri = ringtoneUri;
        this.numbersToNotify = numbersToNotify;
        this.textMessage = textMessage;
        this.voiceMessage = voiceMessage;
        this.alarmID = alarmID;
        this.day = day;

        if(hour >= 12)
        {
            ampm = "PM";
        }

        else
        {
            ampm = "AM";
        }

        this.esID = esID;
        this.gAdmin = gAdmin;
        this.members = members;
    }

    @Ignore
    public TimeAlarm(Map<String, String> map) {

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();


        this.hour = Integer.parseInt(map.get("hour"));
        this.min = Integer.parseInt(map.get("min"));
        this.isText = Boolean.parseBoolean(map.get("isText"));
        this.isCall = Boolean.parseBoolean(map.get("isCall"));
        this.name = map.get("name");
        this.ampm = "AM";
        if(this.hour >= 12) {
            ampm = "PM";
        }
        this.isOn = Boolean.parseBoolean(map.get("isOn"));
        this.ringtoneUri = map.get("ringtoneUri");
        this.esID = map.get("esID");
        this.gAdmin = map.get("gAdmin");
        //todo: check this
        numbersToNotify = gson.fromJson(map.get("numbersToNotify"), type);
        members = gson.fromJson(map.get("members"), type);

        //todo: scheme for setting id
        alarmID = 100 + Integer.parseInt(map.get("alarmID"));
    }


    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }


    public String getRingtoneUri() {
        return ringtoneUri;
    }

    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }

    public String getTextMessage()
    {
        return textMessage;
    }
    public void setNumbersToNotify(ArrayList<String> numbersToNotify)
    {
        this.numbersToNotify = numbersToNotify;
    }

    public ArrayList<String> getNumbersToNotify()
    {
        return numbersToNotify;
    }
    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void setHour(int hour) {
        this.hour = hour;
        if(this.hour >= 12) {
            ampm = "PM";
        }
        else {
            ampm="AM";
        }
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

    public void setIsCall(boolean isCall) {
        this.isCall = isCall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRingtoneUri(String ringtoneUri )
    {
        this.ringtoneUri = ringtoneUri;
    }


    public boolean getIsOn() {
        return isOn;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public boolean getIsText() {
        return isText;
    }

    public boolean getIsCall() {
        return isCall;
    }

    public String getName() {
        return name;
    }

    public String getTime()
    {
        int tempHour;
        tempHour = this.hour;

        if(this.hour >= 12) {
            tempHour -= 12;
        }
        String min_temp = min+"";
        if(min < 10) {
            min_temp = "0"+ min;
        }
        return tempHour + ":" + min_temp + " " + ampm;
    }

    public String getVoiceMessage()
    {
        return voiceMessage;
    }

    public void setVoiceMessage(String voiceMessage)
    {
        this.voiceMessage = voiceMessage;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public ArrayList<String> getMembers() {return members;}

    public String getEsID() {return esID;}

    public String getGAdmin() {return gAdmin;}

    public void setEsID(String esID) { this.esID = esID; }

    public void clearMembers() { this.members.clear(); }

    public void setMembers(ArrayList<String> members)
    {
        this.members = members;
    }

    public void setGAdmin(String gAdmin) {this.gAdmin = gAdmin;}

    public String getShortDetail()
    {
        return getTime();
    }

    public void startActivity(Context context, Intent intent)
    {
        context.startActivity(intent);
    }
}
