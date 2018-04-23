package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalpanjasani on 4/8/18.
 */
@Dao
public abstract class TimeAlarmDAO {
    @Insert
    public abstract void insertAlarms(TimeAlarm... alarms);

    @Delete
    public abstract void deleteAlarms(TimeAlarm... alarms);

    @Update
    public abstract void updateAlarms(TimeAlarm... alarms);

    @Query("SELECT * FROM TimeAlarm")
    public abstract List<TimeAlarm> loadTimeAlarms();

    @Query("SELECT * FROM TimeAlarm WHERE alarmID = :id")
    public abstract TimeAlarm getAlarm(int id);

    @Query("DELETE FROM timealarm WHERE alarmID = :alarmID")
    public abstract void deleteAlarmFromID(int alarmID);

}
