package com.alarm.kalpan.alarmapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomRingtone extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusome_ringtone_layout);
        Button selectButton;

        selectButton = (Button) findViewById(R.id.button3);

        selectButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent openMedia = new Intent(Intent.ACTION_GET_CONTENT);
                startActivity(openMedia);
            }
        });
    }
}
