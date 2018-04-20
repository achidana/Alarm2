package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class AddLocationAlarm extends AppCompatActivity {

    LocationAlarm locationAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_alarm);

        Button deleteButton;
        Button saveButton;
        Button selectRingtoneButton;
        EditText nameText;
        final EditText radiusText;
        TextView latLongTextView;
        Switch onOffSwitch;
        Button selectLocation;

        deleteButton = findViewById(R.id.deleteButton4);
        saveButton = findViewById(R.id.save2);
        selectRingtoneButton = findViewById(R.id.selectRingtone2);
        nameText = findViewById(R.id.name2);
        radiusText = findViewById(R.id.radius_map);
        latLongTextView = findViewById(R.id.lat_long_display);
        onOffSwitch = findViewById(R.id.onOffSwitch2);
        selectLocation = findViewById(R.id.selectLocationButton);

        if(getIntent().getBooleanExtra("edit_flag", false))
        {
            deleteButton.setVisibility(View.VISIBLE);

            locationAlarm = getAlarmFromDatabase(getIntent().getIntExtra("AlarmID", -1));

            nameText.setText(locationAlarm.getName());
            latLongTextView.setText(locationAlarm.getLatitude() + ":" + locationAlarm.getLongitude());
            radiusText.setText(locationAlarm.getRadius() + "");
            onOffSwitch.setChecked(locationAlarm.isOn());

        }

        else
        {
            locationAlarm = new LocationAlarm();
            radiusText.setText("200");
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        deleteFromDatebase(locationAlarm);
                    }
                });
            }
        });

        selectRingtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* next line makes an intent that will be used for startActivityForResult. That activity will be a popup */
                Intent ringtonePickerIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);   /* this basically is a mechanism for giving data to the activity where this intent would go. RingtoneManager defines all these constants and handles them approriately from the receiving end. This thing tells that we don't allow one option of silent in the list of sounds */
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);   /*important: we have all sounds, which I believe is more versatile and user friendly for the user. So even non alarm types would show up */
                startActivityForResult(ringtonePickerIntent, 1);    /*reqeust code is 1  */
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlarmManager.myCreateLocationAlarm(locationAlarm, getApplicationContext());
                //save to database
            }
        });

        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("Radius", Float.parseFloat(radiusText.getText().toString()));
                intent.putExtra("AlarmID",locationAlarm.getAlarmID());

                //request code for maps activity
                startActivityForResult(intent, 2);
            }
        });
    }

    public void deleteFromDatebase(LocationAlarm locationAlarm)
    {
        // delete
    }

    public void addToDatabase(LocationAlarm locationAlarm)
    {
        //add
    }

    public void updateDatabase(LocationAlarm locationAlarm)
    {
        //update database
    }

    public LocationAlarm getAlarmFromDatabase(int alarmID)
    {
        return null;
        // load from database
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_CANCELED)  /* user backed out or operation failed for some reason */
        {

        }

        if(resultCode == Activity.RESULT_OK)
        {
            switch(requestCode)
            {
                case 1:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    locationAlarm.setRingtoneURI(uri);
                    break;
                case 2:
                    locationAlarm.setRadius(data.getFloatExtra("Radius", 200));
                    locationAlarm.setLatitude(data.getDoubleExtra("Latitude", 0));
                    locationAlarm.setLongitude(data.getDoubleExtra("Longitude", 0));
                    break;
            }
        }
    }
}
