package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by kalpanjasani on 4/8/18.
 */

// TODO: add GroupAlarm class and LocationAlarmClass too
@Database(version = 1, entities = {TimeAlarm.class})
@TypeConverters({Converters.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract TimeAlarmDAO timeAlarmDAO();
    //public abstract LocationAlarmDAO locationAlarmDAO();
    //public abstract GroupAlarmDAO groupAlarmDAO();

}
