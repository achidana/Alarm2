package com.alarm2.firebasenotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowToken = (Button)findViewById(R.id.button_show_token);
        btnShowToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the token

                String token = FirebaseInstanceId.getInstance().getToken();
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            Token tok = new Token();
                            tok.token = FirebaseInstanceId.getInstance().getToken();
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

                Log.d("MainActivity", "Token: " + token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
