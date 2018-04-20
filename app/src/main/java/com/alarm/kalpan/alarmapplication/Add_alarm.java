package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.support.v7.app.AppCompatActivity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI;
import static android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
import static android.provider.Settings.System.DEFAULT_RINGTONE_URI;


public class Add_alarm extends AppCompatActivity {

    Uri ringtoneUri;
    boolean edit;
    boolean editGroup;
    TimeAlarm alarm; // the old alarm if we are editting one alarm.
    String text; // if we change it, we will make it non-null, which indicates whether it is ready
    // same for the next few variables
    ArrayList<String> phoneNumbers;
    List<TimeAlarm> timeAlarms;
    ApplicationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        // having everything in its correct initial state
        alarm = null;
        ringtoneUri = null;
        edit = false;
        editGroup = false;
        phoneNumbers = new ArrayList<String>();
        text = null;
        timeAlarms = null;

        final Globals globals= (Globals) getApplication();

        //retrieve the global list of time alarms and store its reference as a member variable
        timeAlarms = globals.timeAlarms;


        // we make them final as the objects being reference won't be changed in this activity. Now we can use them in classes that we make in callback listeners
        final TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);  // the clock
        final Switch textSwitch= (Switch) findViewById(R.id.text_switch); //the switch to start the text a friend feature
        final Switch callSwitch= (Switch) findViewById(R.id.Call_switch); // the switch to initiate the call a friend feature
        final EditText nameView= (EditText) findViewById(R.id.name);  // the thing to get the name of the alarm
        final EditText textView = (EditText) findViewById(R.id.textMessageBox);
        final Button selectRingtone = (Button) findViewById(R.id.selectRingtone);    /* button that shows that you want to select a ringtone */
        final Button save=(Button) findViewById(R.id.save);   /* the button for save */
        final Button deleteButton = (Button) findViewById(R.id.deleteButton);
        final Button chooseContact = (Button) findViewById(R.id.chooseContactButton);
        final Button choosePremade = (Button) findViewById(R.id.choosePremadeButton);

        db = globals.db;
        //Defaults name to Group Alarm name

        textView.setEnabled(false);

        if (getIntent().getStringExtra("GroupAlarm") != null) {
            String groupAlarmName = getIntent().getStringExtra("defaultName");
            nameView.setText(groupAlarmName);
            for (int i = 0; i < timeAlarms.size(); i++) {
                if(timeAlarms.get(i).getName().equals(groupAlarmName)) {
                    Log.d("TAG", "Here");
                    alarm = timeAlarms.get(i);
                    editGroup = true;
                    timePicker.setHour(alarm.getHour());
                    timePicker.setMinute(alarm.getMin());
                    textSwitch.setChecked(alarm.getIsText());
                    callSwitch.setChecked(alarm.getIsCall());
                    nameView.setText(alarm.getName());
                }
            }
        }

        // if edit flag is up... Note that the second argument is the default value that will be there, if the extra is not there
        if(getIntent().getBooleanExtra("edit_flag",false))
        {
            int edit_position = getIntent().getIntExtra("position",0);
            alarm = timeAlarms.get(edit_position);
            edit = true;
            timePicker.setHour(alarm.getHour());
            timePicker.setMinute(alarm.getMin());
            textSwitch.setChecked(alarm.getIsText());
            callSwitch.setChecked(alarm.getIsCall());
            ringtoneUri = alarm.getRingtoneUri();
            nameView.setText(alarm.getName());
        }

        // if its a new alarm being created
        else
        {
            deleteButton.setVisibility(View.GONE);
        }

        /* setting call backs for the different widgets:
        1 the call switch
        2 the text switch
        3 the save button
        4 the red delete button
         */

        textSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!callSwitch.isChecked()) {
                    if (b) {
                        if (!textView.isEnabled()) {
                            textView.setEnabled(true);
                        } else {
                            textView.setEnabled(false);
                        }
                        //John
                        //IMPORTANT: We start two activities, both of which are sitting on top of this activity. But we want enter a number to come first, and so we start it last
                        //Intent get_a_message = new Intent(getBaseContext(), TextArchive.class);
                        //startActivityForResult(get_a_message, 3);

                        //Intent getNumber = new Intent(getBaseContext(), GetInfo.class);
                        //startActivityForResult(getNumber, 2);
                        //End John
                    } else {
                        if (!textView.isEnabled()) {
                            textView.setEnabled(true);
                        } else {
                            textView.setEnabled(false);
                        }
                    }
                }
            }
        });

        //John
        callSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!textSwitch.isChecked()) {
                    if (b) {
                        if (!textView.isEnabled()) {
                            textView.setEnabled(true);
                        } else {
                            textView.setEnabled(false);
                        }
                    } else {
                        if (!textView.isEnabled()) {
                            textView.setEnabled(true);
                        } else {
                            textView.setEnabled(false);
                        }
                    }
                }
            }
        });
        //End John

        selectRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* next line makes an intent that will be used for startActivityForResult. That activity will be a popup */
                Intent ringtonePickerIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);   /* this basically is a mechanism for giving data to the activity where this intent would go. RingtoneManager defines all these constants and handles them approriately from the receiving end. This thing tells that we don't allow one option of silent in the list of sounds */
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);   /*important: we have all sounds, which I believe is more versatile and user friendly for the user. So even non alarm types would show up */
                startActivityForResult(ringtonePickerIntent, 1);    /*reqeust code is 1  */

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText textView = findViewById(R.id.textMessageBox);
                Log.d("TAG", "Name: " + nameView.getText().toString());

                int timePicker_hour= timePicker.getCurrentHour();
                int timePicker_min= timePicker.getCurrentMinute();

                if (editGroup) {
                    if(ringtoneUri == null)
                    {
                        ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    }
                    alarm.setHour(timePicker_hour);
                    alarm.setMin(timePicker_min);
                    alarm.setIsText(textSwitch.isChecked());
                    alarm.setIsCall(callSwitch.isChecked());
                    alarm.setName(nameView.getText().toString());
                }

                else if(! getIntent().getBooleanExtra("edit_flag",false))
                {
                        if(ringtoneUri == null)
                        {
                            ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        }

                        final TimeAlarm timeAlarm = new TimeAlarm(timePicker_hour, timePicker_min, textSwitch.isChecked(), callSwitch.isChecked(), nameView.getText().toString(), true, ringtoneUri);

                        //TODO: have option of different set of numbers to be notified for text and call
                        if(timeAlarm.getIsText())
                        {
                            timeAlarm.setTextMessage(textView.getText().toString());
                            timeAlarm.setNumbersToNotify(phoneNumbers);
                        }

                        if(timeAlarm.getIsCall())
                        {
                            //add file path of voice message
                            timeAlarm.setNumbersToNotify(phoneNumbers);
                        }

                        timeAlarms.add(timeAlarm);
                        // inserting alarm over to the database
                        Thread insertToDatabaseThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                addToDatabase(timeAlarm);
                            }
                        });

                        // starting that thread for database things (TODO: have better design  in terms of placement of this thread start)
                        insertToDatabaseThread.start();
                        MyAlarmManager.myCreateTimeAlarm(timeAlarm, getApplicationContext());    //second argument to be given as it cannot be obtained directly by the MyAlarmManager class
                }
                else
                {
                    if(alarm.getIsOn())
                    {
                        MyAlarmManager.myCancelTimeAlarm(alarm, getApplicationContext());
                        alarm.setHour(timePicker_hour);
                        alarm.setMin(timePicker_min);

                        alarm.setIsText(textSwitch.isChecked());
                        alarm.setTextMessage(textView.getText().toString());

                        //TODO: store the file path of the voice message ??
                        alarm.setIsCall(callSwitch.isChecked());

                        alarm.setName(nameView.getText().toString());

                        alarm.setRingtoneUri(ringtoneUri);

                        MyAlarmManager.myCreateTimeAlarm(alarm, getApplicationContext());
                    }

                    else
                    {
                        alarm.setHour(timePicker_hour);
                        alarm.setMin(timePicker_min);

                        alarm.setIsText(textSwitch.isChecked());
                        alarm.setTextMessage(textView.getText().toString());

                        //TODO: store the file path of the voice message ??
                        alarm.setIsCall(callSwitch.isChecked());

                        alarm.setName(nameView.getText().toString());

                        alarm.setRingtoneUri(ringtoneUri);
                    }

                    //updating the alarm in the database
                    //TODO: have better design for placement of this thread
                    Thread updateDatabaseThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            updateDatabase(alarm);
                        }
                    });

                    updateDatabaseThread.start();
                }


                if (getIntent().getStringExtra("GroupAlarm") != null) {
                    finishActivity(timePicker_hour, timePicker_min);
                }
                else {
                    finish();   //this is better than calling startActivity of Homescreen, as we will now go back to the previous activity that called this activity (may it be settings or group alarm or whatever)
                    // we cleaer out memory and so don't need to reset any variables after calling finish(). Note that we would have had to reset variables otherwise as we may have data of previous alarm when we create on additional alarm. The state of this activity isn't reset (in that case)
                }

            }
        }); //end of listener's call back for save button

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAlarmManager.myCancelTimeAlarm(alarm, getApplicationContext());

                timeAlarms.remove(alarm);

                //todo: update in databases

                finish();
            }
        });

        //John
        //Choose contact starts contact activity
        chooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getNumber = new Intent(getBaseContext(), GetInfo.class);
                startActivityForResult(getNumber, 2);
            }
        });

        //Choose premade starts TextArchive activity
        choosePremade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent get_a_message = new Intent(getBaseContext(), TextArchive.class);
                startActivityForResult(get_a_message, 3);
            }
        });
        //End John
    }   // end of onCreate method

    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //can add stuff here if important
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /*
         we keep 1 as request code for the ringtone picker activity.
         There are many activities that we start, that can call this call back (the ones we start with startActivityForResult from this activity, will come here
         */

        if(resultCode == Activity.RESULT_CANCELED)  /* user backed out or operation failed for some reason */
        {
            if(requestCode == 1)
            {
                /*this case means that user pressed cancel on selecting the ringtone */
                // handle this case
            }
        }

        if(resultCode == Activity.RESULT_OK)
        {
            String temp;
            switch(requestCode)
            {
                case 1:
                    //ringtone
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    /* the uri above is the picked ringtone, or default and so on.
                     * the ringtone Manager class does all of that
                     * We have to use that but have to check its type, right ? (that is done below
                    */

                    // nothing special comes to mind right now, but these default cases might require special consideration

                    if(uri.equals(DEFAULT_RINGTONE_URI))
                    {
                        //handle
                    }
                    if(uri.equals(DEFAULT_ALARM_ALERT_URI))
                    {
                        //handle
                    }
                    if(uri.equals(DEFAULT_NOTIFICATION_URI))
                    {
                        //handle
                    }
                    ringtoneUri = uri;

                    break;

                case 2:
                    // a number choosing activity's return
                    Bundle bundle = data.getBundleExtra(getString(R.string.app_name) + ".PhoneNumbers");
                    phoneNumbers = bundle.getStringArrayList("PhoneNumbers");
                    break;


                case 3:
                    // a text choosing activity's return
                    temp = data.getStringExtra("theText");
                    EditText textView = findViewById(R.id.textMessageBox);
                    textView.setText(temp);
                    break;

                case 4:
                    // a voice message choosing activity's return
                    String name = data.getStringExtra("name");
                    String filepath = data.getStringExtra("filepath");

                    File file = new File(filepath);

                    // one way to use is given below
                    // ringtoneUri = Uri.fromFile(file);

                    //put into audio message object

                    break;
            }
        }
    }

    //Used for group alarm
    //Group alarm appears in main list but only after time alarm is created
    //Consider adding flag
    public void finishActivity(int h, int m) {
        Intent startIntent = new Intent();
        Integer hour = h;
        Integer minute = m;
        String displayTime = hour.toString() + ":" + minute.toString();
        startIntent.putExtra("AlarmName", displayTime);
        setResult(RESULT_OK, startIntent);
        finish();
    }

    public void addToDatabase(TimeAlarm... timeAlarms)
    {
        db.timeAlarmDAO().insertAlarms(timeAlarms);
    }

    public void updateDatabase(TimeAlarm... timeAlarms)
    {
        db.timeAlarmDAO().updateAlarms(timeAlarms);
    }
}