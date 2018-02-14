package com.example.ashwin.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

public class Add_alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        TimePicker timePicker= (TimePicker) findViewById(R.id.timePicker);


    }
}
