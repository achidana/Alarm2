package com.alarm.kalpan.alarmapplication;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by swalters23 on 4/16/2018.
 */

public class GroupAlarm_object {

    int hour;
    int min;
    boolean text;
    boolean call;
    String name;
    String ampm;
    boolean onOff;
    Uri ringtoneUri;
    int alarmID;
    ArrayList<String> members;
    String esID;
    public static int alarmIDGenerator = 0;

    String numberToCall;
    String textMessage;
    String fileToVoiceMessage;

    public GroupAlarm_object(int hour, int min, boolean text, boolean call, String name, boolean onOff, Uri ringtoneUri, ArrayList<String> members) {
        this.hour = hour;
        this.min = min;
        this.text = text;
        this.call = call;
        this.name = name;
        this.ampm = "AM";
        if(this.hour >= 12) {
            ampm = "PM";
        }
        this.onOff=onOff;
        this.ringtoneUri = ringtoneUri;
        this.members = members;
        alarmID = alarmIDGenerator++;
    }

    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }

    public void setNumberToCall(String numberToCall)
    {
        this.numberToCall = numberToCall;
    }

    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
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

    public void setText(boolean text) {
        this.text = text;
    }

    public void setCall(boolean call) {
        this.call = call;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRingtoneUri(Uri ringtoneUri )
    {
        this.ringtoneUri = ringtoneUri;
    }


    public boolean isOnOff() {
        return onOff;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public boolean isText() {
        return text;
    }

    public boolean isCall() {
        return call;
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

}
