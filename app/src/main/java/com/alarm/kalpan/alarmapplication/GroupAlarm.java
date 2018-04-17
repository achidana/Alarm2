package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Entity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/4/18.
 * d
 */
//@Entity
public class GroupAlarm extends Activity{
    private GroupAdmin ga;
    private TextView groupAdmin;
    private TextView groupHeader;
    private Button toAlarm;
    private ListView listView;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private String userText = "";
    private String userPhoneText = "";
    Button activeAlarms;
    private int totalAlarms = 0;
    private int acknowledgedAlarms = 0; //Loop through users and increment for every activated alarm
    private boolean firstTime = false;
    private final int REQUEST_GROUP_ALARM = 1015;
    private String alarmName;
    private String groupName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_alarm);
        //displayName = findViewById(R.id.displayName);
        toAlarm = findViewById(R.id.toAlarm);
        listView = findViewById(R.id.userList);
        Globals globals = (Globals) getApplication();
        groupAdmin = findViewById(R.id.displayName);
        groupHeader = findViewById(R.id.groupName);

        Intent data = getIntent();
        //New group
        if (data.getStringExtra("NewGroupName") != null) {
            //Adds to global list
            groupName = data.getStringExtra("NewGroupName");
            globals.groupList.put(groupName, usernames);
            globals.userList.put(groupName, users);
            //Sets list to empty usernames
            //Sets first time
            firstTime = true;
            Log.d("TAG", groupName);
            groupHeader.setText(groupName);
        }

        //Existing group
        if (data.getStringExtra("SendToGroup") != null) {
            String groupToOpen = data.getStringExtra("SendToGroup");
            //Sets list to current usernames
            usernames = globals.groupList.get(groupToOpen);
            users = globals.userList.get(groupToOpen);
            //toAlarm.setText(globals.timeList.get(groupToOpen));
            for (int i = 0; i < globals.timeAlarms.size(); i++) {
                if (globals.timeAlarms.get(i).getName().equals(groupToOpen)) {
                    toAlarm.setText(globals.timeAlarms.get(i).getTime());
                }
            }
            groupHeader.setText(groupToOpen);
            groupName = groupToOpen;
        }

        initialize();
        initializeListener();
    }

    public void initialize() {
        //String username = data.getStringExtra("Username");
        //String phoneNumber = data.getStringExtra("PhoneNumber");
        //String isUser = data.getStringExtra("isUser");

        //username and phoneNumber will be scraped from phone owner's login info
        //Dummy values for now
        String username = "John Redmon";
        String phoneNumber = "81274733";

        activeAlarms = findViewById(R.id.activeAlarms);

        if (firstTime) {
            User admin = new User(username, phoneNumber, true);
            users.add(admin);
            usernames.add(admin.getName());
            //Store this in database of group admins(?)
            ga = new GroupAdmin(username, phoneNumber, 1, users);
        }
        updateAlarmCount();


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, usernames);
        listView.setAdapter(adapter);

        groupAdmin.setText(username);
    }


    //Called when toAlarm is pressed
    public void createAlarm(View v) {
        Log.d("TAG", "pressed");
        //Goes to new Alarm from sprint 1
        Intent intent = new Intent(this, Add_alarm.class);
        intent.putExtra("GroupAlarm", "GroupAlarm");
        intent.putExtra("defaultName", groupName);
        System.out.println("flag " + groupName);
        startActivityForResult(intent, REQUEST_GROUP_ALARM);
    }

    //Fix String builder
    public void showAlarmStatus(View v) {
        AlertDialog.Builder showUsers = new AlertDialog.Builder(this);
        String notAwake = "";
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getStopAlarm()) {
                notAwake = notAwake + (users.get(i).getName() + "\n");
            }
        }
        showUsers.setTitle(notAwake);
        showUsers.show();
    }

    public void addUser(View v) {
        final boolean userAccepts = true;
        //TODO: Implement User notification
        //Admin types in name and number
        //Message sent to phonenumber to confirm if this person wants to be added to the group
        //If no, the group admin is notified and the user is not added
        //If yes, user is added to group
        //For current purposes, local additions are used instead that always assume yes is pressed

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add User");

        //Linear layout allows two EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputName = new EditText(this);
        inputName.setHint("Username");
        layout.addView(inputName);

        final EditText inputNumber = new EditText(this);
        inputNumber.setHint("Phone Number");
        layout.addView(inputNumber);

        builder.setView(layout); // Again this is a set method, not add

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //User notified, userAccepts updated accordingly
                //TODO

                //User denies request
                if (!userAccepts) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupAlarm.this);
                    builder.setTitle("User Denied Request");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });            builder.show();
                }

                //User accepts request
                else {
                    //TODO: add verification of valid name/number
                    userText = inputName.getText().toString();
                    userPhoneText = inputNumber.getText().toString();
                    User u = new User(userText, userPhoneText, false);
                    users.add(u);
                    adapter.notifyDataSetChanged();
                    usernames.add(userText);
                    adapter.notifyDataSetChanged();
                    updateAlarmCount();
                }
            }
        });

        builder.show();
    }


    public void initializeListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, final View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupAlarm.this);
                String alarmStatus = "Alarm Ignored";
                if (users.get(i).getStopAlarm()) {
                    alarmStatus = "Alarm Acknowledged";
                }

                String toShow = "Phone Number: " + users.get(i).getPhoneNumber() +
                        "\nAlarm Status: " + alarmStatus;
                builder.setTitle(toShow);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    public void updateAlarmCount() {
        int alarmcount = 0;
        if (users == null) totalAlarms = 0;
        else totalAlarms = users.size();
        for (int i = 0; i < totalAlarms; i++) {
            if (users.get(i).getStopAlarm()) alarmcount++;
        }
        acknowledgedAlarms = alarmcount;
        String testS = getString((R.string.user_count), acknowledgedAlarms, totalAlarms);
        activeAlarms.setText(testS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_GROUP_ALARM) {
            alarmName = data.getStringExtra("AlarmName");
            toAlarm.setText(alarmName);
            Globals globals = (Globals) getApplication();
            globals.timeList.put(groupName, alarmName);
        }
    }
}
