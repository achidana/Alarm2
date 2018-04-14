package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String name = "Ashwin";
        String number = "7736839870";
        String userToken = "23423423";

        SharedPreferences preferencesFile = getSharedPreferences(getString(R.string.preference_file_key) + ".prefs", Context.MODE_PRIVATE);
        if(preferencesFile.getBoolean("firstTime", true))
        {
            SharedPreferences.Editor editor = preferencesFile.edit();

            editor.putBoolean("firstTime", false);
            editor.putString("username", name); //connect with login screen and get username and number
            editor.putString("userToken", userToken); //TODO: connect with login screen to get first time token (what if no network?)
            editor.putString("userNumber", number); //TODO: put correct number
            editor.apply(); //important: editor.apply will do in background (so gui freeze)

            System.out.println("flag 1");
            // TODO: in connect with the login screen: load the variables name, user and userToken
        }


        else
        {
            Map<String, ?> kvset = preferencesFile.getAll();
            name = new String((String)(kvset.get("username")));   // making a new string as original is to be considered immutable
            number = new String((String)(kvset.get("userNumber")));
            userToken = new String((String) (kvset.get("userToken")));
            System.out.println("flag 2");

        }

        User user = new User();
        user.setPhoneNumber(number);
        user.setName(name);
        User.setUser(user); // the user of this application

        //loading the database


        listView= (ListView) findViewById(R.id.listview);
        //  ArrayList <TimeAlarm> alarmObjectsList= new ArrayList<TimeAlarm>();
        Globals global_arraylist= (Globals) getApplication();
       ArrayList <TimeAlarm> alarmObjectsList=global_arraylist.timeAlarms;

        ListAdapter customAdapter = new CustomAdapter(this, alarmObjectsList);

        listView.setAdapter(customAdapter);





    }

    @Override
    public void onResume()
    {
        super.onResume();
        Globals global_arraylist= (Globals) getApplication();
        ArrayList <TimeAlarm> alarmObjectsList=global_arraylist.timeAlarms;
        ListAdapter customAdapter = new CustomAdapter(this, alarmObjectsList);

        listView.setAdapter(customAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home_screen,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.add_alarm_action:
                startActivity(new Intent(this, Add_alarm.class));
                return true;

            case R.id.add_group_alarm:
                startActivity(new Intent(this, GroupAlarmList.class));
                return true;

            case R.id.location_alarm:
                startActivity(new Intent(this, MapsActivity.class));
                return true;



            case R.id.update_database:
                Toast.makeText(getApplicationContext(), "starting", Toast.LENGTH_SHORT).show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{Thread.sleep(1500);} catch(InterruptedException e){}    // pause for 2 seconds to let toast finish on ui thread (main thread is UI thread, and it is not this one, but the default thread)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "in progress", Toast.LENGTH_SHORT).show();
                            }
                        });
                        updateDatabase();   // the payload of this thread is this line. Also, this thread also makes a toast of saved on completion, and hence it needs the looper queing and stuff
                    }
                }, "database");

                t.start();

                return true;


        }

        return super.onOptionsItemSelected(item);
    }


    public void updateDatabase()
    {
        try{Thread.sleep(5000);} catch(InterruptedException e){};   // dummy time absorption for mimicking updation of database
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }

    public void loadFromDataBase()
    {

    }

}


