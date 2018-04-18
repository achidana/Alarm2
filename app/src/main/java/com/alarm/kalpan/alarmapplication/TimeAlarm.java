package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ashwin on 2/22/18.
 */

@Entity
public class TimeAlarm {

    private int hour;
    private int min;
    private boolean isText;
    private boolean isCall;
    private String name;
    private boolean isOn;
    private Uri ringtoneUri;

    @PrimaryKey
    private int alarmID;
    private ArrayList<String> numbersToNotify;
    private String textMessage;
    private String voiceMessage;
    private int day;

    @Ignore
    private String ampm;
    //TODO: have other constructors that fill in default stuff if not provided (like ringtone and on off and such

    @Ignore
    public TimeAlarm(int hour, int min, boolean isText, boolean isCall, String name, boolean isOn, Uri ringtoneUri) {
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
        alarmID = r.nextInt();
    }

    public TimeAlarm(int hour, int min, int day, boolean isText, boolean isCall, String name, boolean isOn, Uri ringtoneUri, ArrayList<String> numbersToNotify, String textMessage, String voiceMessage, int alarmID)
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
    }


    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }


    public Uri getRingtoneUri() {
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

    public void setRingtoneUri(Uri ringtoneUri )
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
}
