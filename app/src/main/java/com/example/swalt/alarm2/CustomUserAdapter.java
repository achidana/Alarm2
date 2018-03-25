package com.example.swalt.alarm2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/21/18.
 * Custom adapter, built based on Ashwin's Code
 */

class CustomUserAdapter extends ArrayAdapter<User> {
        User u;
        public Context context;


        CustomUserAdapter(Context context, ArrayList<User> userList) {
            super(context, R.layout.activity_group_alarm, userList);
            this.context = context;
        }


        @Override
        public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            //Think maybe need to set up overlap boundaries instead of new layout
            View customView = inflater.inflate(R.layout.activity_group_alarm, parent, false);


            u = getItem(position);

            TextView nameDisplay = customView.findViewById(R.id.displayName);
  //          TextView numberDisplay = customView.findViewById(R.id.phoneNumber);

            nameDisplay.setText(u.getName());
//            numberDisplay.setText(u.getPhoneNumber());

/*
            homescrn_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        a.setOnOff(true);
                        // write a request to schedule the alarm
                        //System.out.println("ON");
                    }
                    else {
                        a.setOnOff(false);
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
*/
            return customView;
        }

    }

