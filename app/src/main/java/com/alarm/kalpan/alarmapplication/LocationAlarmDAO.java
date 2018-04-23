package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class LocationAlarmDAO {
    @Insert
    public abstract void insertLocationAlarms(LocationAlarm... locationAlarms);

    @Query("SELECT * FROM LocationAlarm")
    public abstract List<LocationAlarm> loadAllLocationAlarms();

    @Delete
    public abstract void deleteLocationAlarms(LocationAlarm... locationAlarms);

    @Update
    public abstract void updateLocationAlarms(LocationAlarm... locationAlarms);

    @Query("SELECT * FROM LocationAlarm WHERE alarmID = :id")
    public abstract LocationAlarm getAlarm(int id);

    @Query("DELETE FROM locationalarm WHERE alarmID = :alarmID")
    public abstract void deleteLocationAlarmFromID(int alarmID);

}
