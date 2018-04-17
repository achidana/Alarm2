package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by ashwin on 2/22/18.
 */

class CustomAdapter extends ArrayAdapter<TimeAlarm>  {
    TimeAlarm a;
    public Context context;



    CustomAdapter(Context context, ArrayList < TimeAlarm> alarmObjectsList) {
        super(context,R.layout.custom_row_homescreen,alarmObjectsList);
        this.context = context;
    }



    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_homescreen,parent,false);

        a=getItem(position);

        TextView alarm_display= customView.findViewById(R.id.alarm_display);
        TextView name_display= customView.findViewById(R.id.name_display);
        Switch homescrn_toggle=(Switch) customView.findViewById(R.id.homescrn_toggle);

        alarm_display.setText(a.getTime());
        name_display.setText(a.getName());


        if(a.getIsOn())
        {
            homescrn_toggle.setChecked(true);
        }


        homescrn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a.setIsOn(true);
                    MyAlarmManager.myCreateTimeAlarm(a, context.getApplicationContext());
                    //debug line
                    System.out.println("ON");
                }
                else {
                    a.setIsOn(false);
                    MyAlarmManager.myCancelTimeAlarm(a, context.getApplicationContext());
                    //System.out.println("OFF");
                }
            }
        });



        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startIntent= new Intent(context, Add_alarm.class);
                startIntent.putExtra("position", position);
                startIntent.putExtra("edit_flag", true);
                context.startActivity(startIntent);



            }
        });





        return customView;
    }
}


