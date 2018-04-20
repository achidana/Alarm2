package com.alarm.kalpan.alarmapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

public class AddAlarmMenu extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                //todo: check if above is correct, or try the one below
                //getActivity().finish();
            }
        });

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.add_alarm_dialog_view, null);
        builder.setView(dialogView);

        final ImageButton button1 = dialogView.findViewById(R.id.time_alarm_icon);
        ImageButton button2 = dialogView.findViewById(R.id.location_alarm_icon);
        ImageButton button3 = dialogView.findViewById(R.id.group_alarm_icon);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTimeAlarm = new Intent(getActivity(), Add_alarm.class);
                addTimeAlarm.putExtra("edit_flag", false);
                startActivity(addTimeAlarm);
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddLocationAlarm.class);
                intent.putExtra("edit_flag", false);
                startActivity(intent);
                dismiss();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // connect with john's stuff
            }
        });
        return builder.create();
    }
}
