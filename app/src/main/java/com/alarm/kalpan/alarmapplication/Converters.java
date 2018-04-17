package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;
/* this class is used by Room Database stuff to convert the objects to string
 */
public class Converters {
    @TypeConverter
    public static Uri fromString(String value)
    {
        return Uri.parse(value);
    }

    @TypeConverter
    public static String fromUri(Uri uri)
    {
        return uri.toString();
    }

}
