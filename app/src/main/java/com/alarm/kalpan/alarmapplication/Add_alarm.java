package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.media.RingtoneManager;


import java.util.ArrayList;

import static android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI;
import static android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
import static android.provider.Settings.System.DEFAULT_RINGTONE_URI;

public class Add_alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Button save=(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent= new Intent(getApplicationContext(),HomeScreen.class);
                TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);
                Switch textSwitch= (Switch) findViewById(R.id.text_switch);
                Switch callSwitch= (Switch) findViewById(R.id.Call_switch);

                EditText name= (EditText) findViewById(R.id.name);
                int timePicker_hour= timePicker.getCurrentHour();
                int timePicker_min= timePicker.getCurrentMinute();
              //  String alarmName=name.getText().toString();
              //  startIntent.putExtra("hour",timePicker_hour);
             //   startIntent.putExtra("min",timePicker_min);
             //   startIntent.putExtra("text_switch",textSwitch.isChecked());
             //   startIntent.putExtra("call_switch",callSwitch.isChecked());
             //   startIntent.putExtra("name",alarmName);
                Alarm_object alarm_object=new Alarm_object(timePicker_hour, timePicker_min, textSwitch.isChecked(), callSwitch.isChecked(), name.getText().toString(), true);

                Globals global_arraylist= (Globals) getApplication();
                ArrayList<Alarm_object> alarmObjectsList=global_arraylist.alarmObjectsList;

                alarmObjectsList.add(alarm_object);
                startActivity(startIntent);     // go back to homescreen on hit save

            }
        });

        /* next line makes an intent that will be used for startActivityForResult. That activity will be a popup */
        Intent ringtonePickerIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);   /* this basically is a mechanism for giving data to the activity where this intent would go. RingtoneManager defines all these constants and handles them approriately from the receiving end. This thing tells that we don't allow one option of silent in the list of sounds */
        ringtonePickerIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);   /*important: we have all sounds, which I believe is more versatile and user friendly for the user. So even non alarm types would show up */
        startActivityForResult(ringtonePickerIntent, 1);    /*reqeust code is 1 but is more helpful probably with many calls or complex programs, not for this testing purposes scenario */
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
            if(requestCode == 1)
            {
                Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);   /* get the URI lying in the data of the intent parameter given in this function */

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

                // or else the ringtone is the selected ringtone


                //important: we just need to save the URI right now, in the program, or in the memory. We don't need to play pause etc right now, right? (possibly right)

            }
        }

    }
}



