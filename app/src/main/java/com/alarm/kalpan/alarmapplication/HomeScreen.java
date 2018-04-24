package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    ListView listView;
    ArrayList<TimeAlarm> timeAlarms;
    ArrayList<LocationAlarm> locationAlarms;
    Button addButton;
    Button deleteButton;
    ArrayList<AlarmDisplayable> alarmDisplayables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        String name = "Ashwin";
        String number = "7736839870";
        String userToken = "23423423";

        //MenuItem menuEdit= findViewById(R.id.action_edit);
        //Menu menu = findViewById(R.id.my_toolbar);
        //MenuItem menuOk = menu.findItem(R.id.action_ok);
        //Log.d("TAG", menuOk.toString());

        SharedPreferences preferencesFile = getSharedPreferences(getString(R.string.preference_file_key) + ".prefs", Context.MODE_PRIVATE);
        if(preferencesFile.getBoolean("firstTime", true))
        {
            SharedPreferences.Editor editor = preferencesFile.edit();
//
            editor.putBoolean("firstTime", false);
            editor.apply(); //important: editor.apply will do in background (so gui freeze)
            System.out.println("flag 1");
            Intent verify = new Intent(this, FirebasePhoneVerify.class);
            startActivity(verify);
            // TODO: in connect with the login screen: load the variables name, user and userToken
        }


        else
        {
            Map<String, ?> kvset = preferencesFile.getAll();
            System.out.println("flag 2");

        }

        User user = new User();
        user.setPhoneNumber(number);
        user.setName(name);
        User.setUser(user); // the user of this application


        final Globals globals = (Globals) getApplication();

        timeAlarms = new ArrayList<>(globals.timeAlarms);
        locationAlarms = new ArrayList<>(globals.locationAlarms);

        listView= (ListView) findViewById(R.id.listview);


        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton3);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AddAlarmMenu addAlarmMenu = new AddAlarmMenu();
                    addAlarmMenu.show(getSupportFragmentManager(), "addAlarmMenu");
            }
        });

        AdapterView.OnItemClickListener listViewCallback = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(addButton.getVisibility() == View.VISIBLE)
                {
                    Intent startIntent = null;
                    if(alarmDisplayables.get(position).getClass() == TimeAlarm.class)
                    {
                        TimeAlarm alarm = (TimeAlarm) alarmDisplayables.get(position);
                        startIntent = new Intent(getApplicationContext(), Add_alarm.class);
                        startIntent.putExtra("AlarmID", alarm.getAlarmID());

                    }
                    else if(alarmDisplayables.get(position).getClass() == LocationAlarm.class)
                    {
                        LocationAlarm alarm = (LocationAlarm)alarmDisplayables.get(position);
                        startIntent = new Intent(getApplicationContext(), AddLocationAlarm.class);
                        startIntent.putExtra("AlarmID", alarm.getAlarmID());
                    }
                    if(startIntent != null)
                    {
                        startIntent.putExtra("edit_flag", true);
                        startActivity(startIntent);
                    }

                }

                else if(deleteButton.getVisibility() == View.VISIBLE)
                {
                    listView.setItemChecked(position, true);
                    listView.setSelection(position);
                    setPos(position);
                    ((ArrayAdapter)(adapterView.getAdapter())).notifyDataSetChanged();
                    System.out.println("Kalpan");
                }
            }
        };

        listView.setOnItemClickListener(listViewCallback);
        alarmDisplayables = new ArrayList<>();
        alarmDisplayables.clear();
        alarmDisplayables.addAll(timeAlarms);
        alarmDisplayables.addAll(locationAlarms);

        final ArrayAdapter customAdapter = new CustomAdapter(this, alarmDisplayables, listViewCallback, R.drawable.common_google_signin_btn_icon_dark_normal_background);
        listView.setAdapter(customAdapter);

        //deleting a selected alarm
        //todo: change from time alarm to list of alarms (all extend alarm!!)
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = listView.getCheckedItemPosition();
                if(position < 0)
                {
                    return;
                }

                AlarmDisplayable alarmDisplayable = alarmDisplayables.get(position);
                alarmDisplayables.remove(position);



                System.out.println("flag 3");

                if(alarmDisplayable.getClass() == TimeAlarm.class)
                {
                    if(MyAlarmManager.removeTimeAlarmFromID(globals, alarmDisplayable.getAlarmID()))
                    {
                        /* if removal matched a valid id in time alarms (global time alarms) */
                        timeAlarms.clear(); //match the global time alarms
                        timeAlarms.addAll(globals.timeAlarms);  //match the global time alarms
                        //todo: call server too
                    }

                }

                else if(alarmDisplayable.getClass() == LocationAlarm.class)
                {
                    if(MyAlarmManager.removeLocationAlarmFromID(globals, alarmDisplayable.getAlarmID()))
                    {
                        locationAlarms.clear();
                        locationAlarms.addAll(globals.locationAlarms);
                        //todo: call server too
                    }
                }

                alarmDisplayables.clear();
                alarmDisplayables.addAll(timeAlarms);
                alarmDisplayables.addAll(locationAlarms);
                setPos(-1);
                timeAlarms.remove(position);
                listView.setSelection(-1);
                listView.setItemChecked(position, false);

                ((ArrayAdapter)listView.getAdapter()).notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Globals globals = (Globals) getApplication();

        timeAlarms.clear();
        timeAlarms.addAll(globals.timeAlarms);

        locationAlarms.clear();
        locationAlarms.addAll(globals.locationAlarms);

        alarmDisplayables.clear();
        alarmDisplayables.addAll(timeAlarms);
        alarmDisplayables.addAll(locationAlarms);

        ((ArrayAdapter) (listView.getAdapter())).notifyDataSetChanged();
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
            case R.id.action_edit:

                //changing which button is on the bottom of the screen
                addButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);

                break;

            case R.id.action_ok:
                addButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                setPos(-1);
                break;
            default:
                super.onOptionsItemSelected(item);
        }



        return super.onOptionsItemSelected(item);
    }



    public void setPos(int i) {
        ((CustomAdapter) listView.getAdapter()).setTest(i);
    }
}


