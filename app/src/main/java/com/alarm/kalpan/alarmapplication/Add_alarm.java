package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.media.RingtoneManager;


import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

import static android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI;
import static android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
import static android.provider.Settings.System.DEFAULT_RINGTONE_URI;


public class Add_alarm extends AppCompatActivity {

    Uri ringtoneUri;
    boolean edit;
    boolean editGroup;
    Alarm_object alarm; // the old alarm if we are editting one alarm.
    String text = null; // if we change it, we will make it non-null, which indicates whether it is ready
    // same for the next few variables
    String number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Globals global_arraylist= (Globals) getApplication();
        final ArrayList<Alarm_object> alarmObjectsList=global_arraylist.alarmObjectsList;
        TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
        Switch textSwitch= (Switch) findViewById(R.id.text_switch);
        Switch callSwitch= (Switch) findViewById(R.id.Call_switch);
        EditText name= (EditText) findViewById(R.id.name);
        //Defaults name to Group Alarm name

        if (getIntent().getStringExtra("GroupAlarm") != null) {
            String groupAlarmName = getIntent().getStringExtra("defaultName");
            name.setText(groupAlarmName);
            for (int i = 0; i < alarmObjectsList.size(); i++) {
                if(alarmObjectsList.get(i).getName().equals(groupAlarmName)) {
                    Log.d("TAG", "Here");
                    alarm = alarmObjectsList.get(i);
                    editGroup = true;
                    timePicker.setHour(alarmObjectsList.get(i).getHour());
                    timePicker.setMinute(alarmObjectsList.get(i).getMin());
                    textSwitch.setChecked(alarmObjectsList.get(i).isText());
                    callSwitch.setChecked(alarmObjectsList.get(i).isCall());

                    name.setText(alarmObjectsList.get(i).getName());
                }
            }
        }
        Button selectRingtone = (Button) findViewById(R.id.button2);    /* button that shows that you want to select a ringtone */

        // if edit flag is up... Note that the second argument is the default value that will be there, if the extra is not there

        if( alarmObjectsList.size()>0 && getIntent().getBooleanExtra("edit_flag",false))
        {
            int edit_position = getIntent().getIntExtra("position",0);
            alarm = alarmObjectsList.get(edit_position);
            edit = true;
            timePicker.setHour(alarmObjectsList.get(edit_position).getHour());
            timePicker.setMinute(alarmObjectsList.get(edit_position).getMin());
            textSwitch.setChecked(alarmObjectsList.get(edit_position).isText());
            callSwitch.setChecked(alarmObjectsList.get(edit_position).isCall());
            ringtoneUri = alarm.ringtoneUri;
            name.setText(alarmObjectsList.get(edit_position).getName());


        }

        textSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //IMPORTANT: We start two activities, both of which are sitting on top of this activity. But we want enter a number to come first, and so we start it last
                Intent get_a_message = new Intent(getBaseContext(), TextArchive.class);
                startActivityForResult(get_a_message, 3);

                Intent getNumber = new Intent(getBaseContext(), GetInfo.class);
                startActivityForResult(getNumber, 2);

            }
        });

        callSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //read comment on textSwitch's listener
                Intent getVoiceMessage = new Intent(getBaseContext(), AudioArchive.class);
                startActivityForResult(getVoiceMessage, 4);

                Intent getNumber = new Intent(getBaseContext(), GetInfo.class);
                startActivityForResult(getNumber, 2);   /* request code 2 indicates get number */
            }
        });
        selectRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* next line makes an intent that will be used for startActivityForResult. That activity will be a popup */
                Intent ringtonePickerIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);   /* this basically is a mechanism for giving data to the activity where this intent would go. RingtoneManager defines all these constants and handles them approriately from the receiving end. This thing tells that we don't allow one option of silent in the list of sounds */
                ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);   /*important: we have all sounds, which I believe is more versatile and user friendly for the user. So even non alarm types would show up */
                startActivityForResult(ringtonePickerIntent, 1);    /*reqeust code is 1 but is more helpful probably with many calls or complex programs, not for this testing purposes scenario */
            }
        });
        Button save=(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
                Switch textSwitch= (Switch) findViewById(R.id.text_switch);
                Switch callSwitch= (Switch) findViewById(R.id.Call_switch);
                EditText name= (EditText) findViewById(R.id.name);
                Log.d("TAG", "Name: " + name.getText().toString());

                int timePicker_hour= timePicker.getCurrentHour();
                int timePicker_min= timePicker.getCurrentMinute();




                if (editGroup) {
                    if(ringtoneUri == null)
                    {
                        ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    }
                    alarm.setHour(timePicker_hour);
                    alarm.setMin(timePicker_min);
                    alarm.setText(textSwitch.isChecked());
                    alarm.setCall(callSwitch.isChecked());
                    alarm.setName(name.getText().toString());
                }

                else if(getIntent().getBooleanExtra("edit_flag",false) == false)
                {
                        if(ringtoneUri == null)
                        {
                            ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                        }
                        Alarm_object alarm_object = new Alarm_object(timePicker_hour, timePicker_min, textSwitch.isChecked(), callSwitch.isChecked(), name.getText().toString(), true, ringtoneUri);
                        if(alarm_object.isText())
                        {
                            alarm_object.setTextMessage(text);
                        }
                        Globals global_arraylist = (Globals) getApplication();
                        ArrayList<Alarm_object> alarmObjectsList = global_arraylist.alarmObjectsList;
                        alarmObjectsList.add(alarm_object);
                        System.out.println("Added: " + alarmObjectsList.get(0).getHour());
                        MyAlarmManager.myCreateTimeAlarm(alarm_object, getApplicationContext());    //second argument to be given as it cannot be obtained directly by the MyAlarmManager class
                }


                else
                {
                    if(alarm.isOnOff())
                    {
                        MyAlarmManager.myCancelTimeAlarm(alarm, getApplicationContext());
                        alarm.setHour(timePicker_hour);
                        alarm.setMin(timePicker_min);
                        alarm.setText(textSwitch.isChecked());
                        alarm.setCall(callSwitch.isChecked());
                        alarm.setName(name.getText().toString());
                        alarm.setRingtoneUri(ringtoneUri);
                        System.out.println("Flag 1");
                        MyAlarmManager.myCreateTimeAlarm(alarm, getApplicationContext());
                    }

                    else
                    {
                        alarm.setHour(timePicker_hour);
                        alarm.setMin(timePicker_min);
                        alarm.setText(textSwitch.isChecked());
                        alarm.setCall(callSwitch.isChecked());
                        alarm.setName(name.getText().toString());
                        System.out.println("Flag 2");
                        alarm.setRingtoneUri(ringtoneUri);
                    }
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
        if(resultCode == Activity.RESULT_CANCELED)  /* user backed out or operation failed for some reason */
        {
             /*
             * we keep 1 as request code for the ringtone picker activity.
             * There are many activities that we start, that can call this call back (the ones we start with startActivityForResult from this activity, will come here
             */
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
                    temp = data.getStringExtra("theNumber");
                    number = temp;



                case 3:
                    temp = data.getStringExtra("theText");
                    text = temp;   /* set object variable to this value */
                    break;

                case 4:
                    String name = data.getStringExtra("name");
                    String filepath = data.getStringExtra("filepath");

                    //important: todo: setting this as ringtone too, change in future
                    File file = new File(filepath);
                    ringtoneUri = Uri.fromFile(file);
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
}



