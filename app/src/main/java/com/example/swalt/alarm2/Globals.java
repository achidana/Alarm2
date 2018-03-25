package com.example.swalt.alarm2;

import android.app.Application;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JohnRedmon on 3/25/18.
 * Global variables
 */

public class Globals extends Application{
    public Map<String, ArrayList<String>> groupList = new HashMap<>();
    public Map<String, ArrayList<User>> userList = new HashMap<>();
}
