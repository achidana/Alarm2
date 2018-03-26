package com.alarm.kalpan.alarmapplication;

/**
 * Created by JohnRedmon on 3/3/18.
 * User of group alarm, lacks group admin priveleges
 */

public class User{
    private String name;
    private String phoneNumber;
    private boolean isAdmin;
    private boolean stopAlarm;

    public User() {
        super();
    }

    public User(String name, String phoneNumber, boolean isAdmin) {
        super();
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setAdmin(isAdmin);
        this.stopAlarm = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getStopAlarm() {
        return stopAlarm;
    }

    public void setStopAlarm(boolean stopAlarm) {
        this.stopAlarm = stopAlarm;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPhoneNumber();
    }
}
