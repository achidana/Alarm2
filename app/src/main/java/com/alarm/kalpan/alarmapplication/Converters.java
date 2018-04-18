package com.alarm.kalpan.alarmapplication;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/* this class is used by Room Database stuff to convert the objects to string
 */
public class Converters {
    @TypeConverter
    public static Uri fromStringToUri(String value)
    {
        return Uri.parse(value);
    }

    @TypeConverter
    public static String fromUri(Uri uri)
    {
        return uri.toString();
    }

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<String> fromStringToStringArrayList(String json)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
