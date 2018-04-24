package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by ashwin on 2/22/18.
 */

class CustomAdapter extends ArrayAdapter<AlarmDisplayable>  {
    AlarmDisplayable a;
    public Context context;
    private AdapterView.OnItemClickListener listCallback;
    private int selected;

    private int selectedCellBackroundColorResourceID;


    CustomAdapter(Context context, ArrayList < AlarmDisplayable> alarmDisplayables, AdapterView.OnItemClickListener listCallback, int drawableResourceID) {
        super(context,R.layout.custom_row_homescreen,alarmDisplayables);
        this.context = context;
        this.listCallback = listCallback;
        this.selectedCellBackroundColorResourceID = drawableResourceID;
        selected = -1;
    }



    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.custom_row_homescreen,parent,false);

        a = getItem(position);

        TextView alarm_display= customView.findViewById(R.id.alarm_display);
        TextView name_display= customView.findViewById(R.id.name_display);
        Switch homescrn_toggle=(Switch) customView.findViewById(R.id.homescrn_toggle);

        alarm_display.setText(a.getShortDetail());
        name_display.setText(a.getName());


        if(a.getIsOn())
        {
            homescrn_toggle.setChecked(true);
        }

        //Shared prefs?
        System.out.println("Get: " + getSelected());
        System.out.println("POS: " + position);
        if(getSelected() == position)
        {
            System.out.println("Kalpan2");
            //customView.setBackgroundResource(selectedCellBackroundColorResourceID);
            customView.setBackgroundResource(android.R.color.darker_gray);
        }
        homescrn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a.setIsOn(true);
                    if(a.getClass() == TimeAlarm.class)
                        MyAlarmManager.myCreateTimeAlarm((TimeAlarm) a, context.getApplicationContext());
                    else if(a.getClass() == LocationAlarm.class)
                        MyAlarmManager.myCreateLocationAlarm((LocationAlarm)a, context.getApplicationContext());
                    //debug line
                    System.out.println("ON");
                }
                else {
                    a.setIsOn(false);
                    if(a.getClass() == TimeAlarm.class)
                        MyAlarmManager.myCancelTimeAlarm((TimeAlarm) a, context.getApplicationContext());
                    //else if location alarm todo: cancel location alarm
                    //System.out.println("OFF");
                }
            }
        });

        customView.setTag(position);
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCallback.onItemClick((AdapterView) parent, customView, position, customView.getId());
            }
        });
        return customView;
    }

    public void setTest(int i) {
        selected = i;
        System.out.println("here");
    }

    public int getSelected() {
        return selected;
    }
}


