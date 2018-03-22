package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetInfo extends Activity {

    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);



        final EditText editText = (EditText) findViewById(R.id.editTextPhoneNumber);
        Button okButton = findViewById(R.id.OK);

        okButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent dataIntent = new Intent();
                phoneNumber = editText.getText().toString();
                dataIntent.putExtra("theNumber", phoneNumber);
                setResult(RESULT_OK, dataIntent);   //set the ok status, and the dataIntent to be sent when finished
                finish();
            }
        });
    }
}
