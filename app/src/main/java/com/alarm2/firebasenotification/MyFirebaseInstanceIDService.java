package com.alarm2.firebasenotification;

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
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String token = FirebaseInstanceId.getInstance().getToken();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Token tok = new Token();
                    tok.token = FirebaseInstanceId.getInstance().getToken();
                    String pNum;
                    TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    pNum = "9374596097";
                    Log.d("MainActivity", pNum);
                    Gson gson = new GsonBuilder().create();
                    StringBuilder result = new StringBuilder();
                    try {

                        URL url = new URL("http://45.56.125.90:5000/token");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        Log.d("MainActivity", "!!!!!!!!!!!!!");
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        String json = gson.toJson(tok);
                        Log.d("MainActivity", "%%%%%%%%%%%%%%%%");
                        wr.write(json);
                        wr.flush();
                        Log.d("MainActivity", "$$$$$$$$$$$$$$");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        Log.d("MainActivity", "@@@@@@@@@@@@@@@@");
                        String res = result.toString();
                        conn.disconnect();
                        Log.d("MainActivity", "Response: " + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        Log.d(TAG, "New Token: " + refreshedToken);
    }
}
