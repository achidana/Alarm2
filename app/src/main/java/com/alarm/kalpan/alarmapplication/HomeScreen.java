package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        listView= (ListView) findViewById(R.id.listview);
        //  ArrayList <Alarm_object> alarmObjectsList= new ArrayList<Alarm_object>();
        Globals global_arraylist= (Globals) getApplication();
        ArrayList <Alarm_object> alarmObjectsList=global_arraylist.alarmObjectsList;



        int hour = getIntent().getIntExtra("hour",0);
        int min = getIntent().getIntExtra("min",0);
        boolean text = getIntent().getBooleanExtra("text_switch",false);
        boolean call = getIntent().getBooleanExtra("call_switch",false);
        String name = getIntent().getExtras().getString("name");

        Alarm_object alarm_object=new Alarm_object(hour, min, text, call, name);

        alarmObjectsList.add(alarm_object);

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


        }

        return super.onOptionsItemSelected(item);
    }

}


