package com.alarm.kalpan.alarmapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import org.w3c.dom.Text;
import java.util.ArrayList;

/**
 * Created by ashwin on 2/22/18.
 */

class CustomAdapter extends ArrayAdapter<Alarm_object> {

    CustomAdapter(Context context, ArrayList < Alarm_object> alarmObjectsList) {
        super(context,R.layout.custom_row_homescreen,alarmObjectsList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_homescreen,parent,false);
        Alarm_object a= getItem(position);

        TextView alarm_display= customView.findViewById(R.id.alarm_display);
        TextView name_display= customView.findViewById(R.id.name_display);
        alarm_display.setText(a.getTime());
        name_display.setText(a.getName());



        return customView;
    }
}


