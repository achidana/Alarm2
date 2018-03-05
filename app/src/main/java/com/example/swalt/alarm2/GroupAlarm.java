package com.example.swalt.alarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/4/18.
 */

public class GroupAlarm extends Activity{
    private GroupAdmin ga;
    private TextView displayName;
    private Button toAlarm;
    private ArrayAdapter<User> adapter;
    private ListView listView;
    private ArrayList<User> users = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_alarm);
        displayName = findViewById(R.id.displayName);
        toAlarm = findViewById(R.id.toAlarm);
        listView = findViewById(R.id.userList);
        initialize();
    }

    public void initialize() {
        Intent data = getIntent();
        String username = data.getStringExtra("Username");
        String phoneNumber = data.getStringExtra("PhoneNumber");
        displayName.setText(username);
        Log.d("TAG", username);
        toAlarm.setText(phoneNumber);
        Log.d("TAG", phoneNumber);
        User admin = new User(username, phoneNumber, true);
        users.add(admin);
        ga = new GroupAdmin(username, phoneNumber, 1, users);
        adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, users);
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
