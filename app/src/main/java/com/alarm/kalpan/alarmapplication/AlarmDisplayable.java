package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.Intent;

public interface AlarmDisplayable {

    public String getName();
    public String getShortDetail();
    public boolean getIsOn();
    public void setIsOn(boolean isOn);
    public void startActivity(Context context, Intent intent);
    public int getAlarmID();

}
