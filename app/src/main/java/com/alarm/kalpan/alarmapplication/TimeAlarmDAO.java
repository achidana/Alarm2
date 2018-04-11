package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

/**
 * Created by kalpanjasani on 4/8/18.
 */

public abstract class TimeAlarmDAO {
    @Insert
    public abstract void insertAlarms(Alarm_object... alarms);

    @Delete
    public abstract void deleteAlarms(Alarm_object... alarms);

    @Update
    public abstract void updateAlarms(Alarm_object... alarms);

    @Query("SELECT * FROM TimeAlarms")
    public abstract Alarm_object[] loadAlarm_objects();

}
