package com.example.swalt.alarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/4/18.
 * d
 */

public class GroupAlarm extends Activity{
    private GroupAdmin ga;
    //private TextView displayName;
    //private Button toAlarm;
    private ListView listView;
    private ArrayList<User> users = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_alarm);
        //displayName = findViewById(R.id.displayName);
        //toAlarm = findViewById(R.id.toAlarm);
        listView = findViewById(R.id.userList);
        initialize();
    }

    public void initialize() {
        Intent data = getIntent();
        String username = data.getStringExtra("Username");
        String phoneNumber = data.getStringExtra("PhoneNumber");
        User admin = new User(username, phoneNumber, true);
        users.add(admin);
        ga = new GroupAdmin(username, phoneNumber, 1, users);
        ListAdapter customAdapter = new CustomUserAdapter(this, users);
        listView.setAdapter(customAdapter);
    }


    //Called when toAlarm is pressed
    public void createAlarm(View v) {
        Log.d("TAG", "pressed");
    }

    public void addUser(View v) {
        //Change from new group alarm to creating a new class
        Log.d("TAG", "backtonew");
        Intent intent = new Intent(this, NewGroupAlarm.class);
        startActivity(intent);
    }
}
