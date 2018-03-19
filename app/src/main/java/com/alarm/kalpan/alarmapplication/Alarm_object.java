package com.alarm.kalpan.alarmapplication;

/**
 * Created by ashwin on 2/22/18.
 */

public class Alarm_object {

    int hour;
    int min;
    boolean text;
    boolean call;
    String name;
    String ampm;
    boolean onOff;

    public Alarm_object(int hour, int min, boolean text, boolean call, String name, boolean onOff) {
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
