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

public class HomeScreen extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Check if first time, and then do login and that stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

      //  SharedPreferences preferencesFile = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
      //  if(preferencesFile.getString("existsString", null) == null)
       // {
      //      SharedPreferences.Editor editor = preferencesFile.edit();

     //       editor.putBoolean("firstTime", false);
     //       editor.putString("username", "Kalpan");
     //       editor.putInt("newAlarmID", 0); // set the next id to be 0
     //       editor.putString("existsString", "exists");

     //   }


      //  else
      //  {
//
  //      }

        listView= (ListView) findViewById(R.id.listview);
        //  ArrayList <Alarm_object> alarmObjectsList= new ArrayList<Alarm_object>();
        Globals global_arraylist= (Globals) getApplication();
       ArrayList <Alarm_object> alarmObjectsList=global_arraylist.alarmObjectsList;

        ListAdapter customAdapter = new CustomAdapter(this, alarmObjectsList);

        listView.setAdapter(customAdapter);





    }

    @Override
    public void onResume()
    {
        super.onResume();
        Globals global_arraylist= (Globals) getApplication();
        ArrayList <Alarm_object> alarmObjectsList=global_arraylist.alarmObjectsList;
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

}


