package com.example.ashwin.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        TextView test_output= (TextView) findViewById(R.id.test_output);
        //String output=getIntent().getIntExtra("hour",0)+"";
        String output=getIntent().getBooleanExtra("call_switch",false)+"";
        test_output.setText(output);
        //EditText dimple= (EditText) findViewById(R.id.dimple);
        //dimple.setText(getIntent().getExtras().getString("hour"));
    }
}
