package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
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

public class Add_alarm extends Activity {

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
                String alarmName=name.getText().toString();
                startIntent.putExtra("hour",timePicker_hour);
                startIntent.putExtra("min",timePicker_min);
                startIntent.putExtra("text_switch",textSwitch.isChecked());
                startIntent.putExtra("call_switch",callSwitch.isChecked());
                startIntent.putExtra("name",alarmName);


                startActivity(startIntent);
            }
        });





    }
}



