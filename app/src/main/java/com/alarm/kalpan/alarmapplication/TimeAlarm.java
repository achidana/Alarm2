package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Entity;
import android.net.Uri;

/**
 * Created by ashwin on 2/22/18.
 */

@Entity
public class TimeAlarm {

    private int hour;
    private int min;
    private boolean text;
    private boolean call;
    private String name;
    private String ampm;
    private boolean onOff;



    private Uri ringtoneUri;

    private int alarmID;
    private String numberToCall;
    private String textMessage;
    private String fileToVoiceMessage;

    //TODO: have other constructors that fill in default stuff if not provided (like ringtone and on off and such
    public TimeAlarm(int hour, int min, boolean text, boolean call, String name, boolean onOff, Uri ringtoneUri) {
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
