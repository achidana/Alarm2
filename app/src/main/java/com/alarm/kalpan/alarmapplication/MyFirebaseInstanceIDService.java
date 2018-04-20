package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by swalters23 on 3/14/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstIDService";


    @Override
    public void onTokenRefresh() {
        //Get updated token
        Globals globals = (Globals) getApplication();
        globals.firebaseToken = FirebaseInstanceId.getInstance().getToken();


        //Log.d(TAG, "New Token: " + refreshedToken);
    }
}
