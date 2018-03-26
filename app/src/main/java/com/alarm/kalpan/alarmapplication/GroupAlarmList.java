package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by JohnRedmon on 3/25/18.
 * List of group alarms
 */

public class GroupAlarmList extends Activity {
    private ArrayList<String> groupAlarms = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private String userText = "";
    private final int REQUEST_GROUP_ALARM = 1015;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_alarm_list);

        listView = (ListView) findViewById(R.id.GroupAlarmList);

        Globals globals = (Globals) getApplication();

        //No group alarms
        if (globals.groupList.isEmpty()) {
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, groupAlarms);
            listView.setAdapter(adapter);
        }
        else {
            Iterator iterator = globals.userList.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                //System.out.println(adapter.add(pair.getKey()));
                groupAlarms.add(pair.getKey().toString());
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, groupAlarms);
            listView.setAdapter(adapter);
        }
        initializeListener();
    }

    public void addGroup(View v) {
        final AlertDialog.Builder newGroup = new AlertDialog.Builder(this);
        newGroup.setTitle("New Group Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        newGroup.setView(input);

        //OK is pressed, opens editText and adds to adapter
        newGroup.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userText = input.getText().toString();
                if (groupAlarms.contains(userText)) {
                    input.setError("Group Already Exists");
                } else {
                    groupAlarms.add(userText);
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(GroupAlarmList.this, GroupAlarm.class);
                    intent.putExtra("NewGroupName", userText);
                    startActivityForResult(intent, RESULT_FIRST_USER);
                }
            }
        });

        //Cancel is pressed, closes the box without updating list
        newGroup.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        newGroup.show();
    }

    //When group alarm is pressed, corresponding alarm and listview are pulled up
    public void initializeListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "List->Alarm");
                String groupName = groupAlarms.get(i);

                Intent startGroupAlarm = new Intent(GroupAlarmList.this, GroupAlarm.class);
                startGroupAlarm.putExtra("SendToGroup", groupName);
                startActivity(startGroupAlarm);
            }
        });
    }
}
