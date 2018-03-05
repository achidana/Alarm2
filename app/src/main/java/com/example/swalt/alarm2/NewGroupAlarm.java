package com.example.swalt.alarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/3/18.
 * Backend logic to create and manage group alarms
 */

public class NewGroupAlarm extends Activity{
    EditText editTextUserName,editTextPhoneNumber;
    Button btnCreateGroupAlarm;
    String username;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_alarm);

        editTextUserName=findViewById(R.id.editTextUserName);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);

        btnCreateGroupAlarm=findViewById(R.id.OK);


        btnCreateGroupAlarm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                username=editTextUserName.getText().toString();
                phoneNumber=editTextPhoneNumber.getText().toString();

                // check if any of the fields are vaccant
                if(username.equals("")) {
                    editTextUserName.setError("Invalid Username");
                }
                if(phoneNumber.equals("") || phoneNumber.length() > 11 || !PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                    editTextPhoneNumber.setError("Invalid Phone Number");
                }
                else {
                    // Save the Data in Database
                    sendToGroupAlarm();
                }
            }
        });
    }

    public void sendToGroupAlarm () {
        Intent intent = new Intent(this, GroupAlarm.class);
        intent.putExtra("Username", username);
        intent.putExtra("PhoneNumber", phoneNumber);
        startActivity(intent);
    }
}
