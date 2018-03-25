package com.example.swalt.alarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by JohnRedmon on 3/25/18.
 * Adds group name to Global Map of names to arraylists
 */

public class AddGroup extends Activity{
    EditText editTextGroup;
    Button btnCreateGroupAlarm;
    String group;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        editTextGroup = findViewById(R.id.editGroupName);

        btnCreateGroupAlarm=findViewById(R.id.OK);


        btnCreateGroupAlarm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                group = editTextGroup.getText().toString();

                //Checks master list to see if group name exists
                Globals globals = (Globals) getApplication();
                if(globals.groupList != null && globals.groupList.containsKey(group)) {
                    editTextGroup.setError("Group name already exists");
                }

                else {
                    // Save the Data in Database
                    sendToGroupAlarm();
                }
            }
        });
    }

    public void sendToGroupAlarm () {
        Log.d("TAG", "sendtogroup");
        Intent intent = new Intent(this, GroupAlarm.class);
        intent.putExtra("NewGroupName", group);
        startActivity(intent);
    }
}
