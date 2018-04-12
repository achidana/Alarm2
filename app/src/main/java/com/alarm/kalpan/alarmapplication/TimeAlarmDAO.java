package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Time;

/**
 * Created by kalpanjasani on 4/8/18.
 */

public abstract class TimeAlarmDAO {
    @Insert
    public abstract void insertAlarms(TimeAlarm... alarms);

    @Delete
    public abstract void deleteAlarms(TimeAlarm... alarms);

    @Update
    public abstract void updateAlarms(TimeAlarm... alarms);

    @Query("SELECT * FROM TimeAlarms")
    public abstract TimeAlarm[] loadTimeAlarms();

}
