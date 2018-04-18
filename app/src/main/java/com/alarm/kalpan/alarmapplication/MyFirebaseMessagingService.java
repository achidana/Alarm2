package com.alarm.kalpan.alarmapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * Created by swalters23 on 3/14/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From:" + remoteMessage.getFrom());

        //Check if the message contains title
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message title: " + remoteMessage.getNotification().getTitle());
            String title = remoteMessage.getNotification().getTitle();
            //MainActivity.displayData(remoteMessage.getData().toString());
            Globals globals = (Globals) getApplication();
            ArrayList<Alarm_object> alarmList = globals.alarmObjectsList;
            Gson gson = new GsonBuilder().create();
            Log.d(TAG, "JSON: " + remoteMessage.getData());
            Log.d(TAG, "JSON: " + remoteMessage.getData().toString());
            Log.d(TAG, "TYPE: " + remoteMessage.getMessageType());
            //Alarm_object alarm = gson.fromJson(remoteMessage.getData().toString(), Alarm_object.class);
            Alarm_object alarm = new Alarm_object(remoteMessage.getData());
            alarm.setName("Group: " + alarm.getName());
            if (title.equals("NEW")) {
                alarmList.add(alarm);
            }
        }
        //Check if the message contains data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
            //MainActivity.displayData(remoteMessage.getData().toString());
            //sendNotification("Message data: " + remoteMessage.getData());
        }

        //Check if the message contains notification

        if(remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String body) {
        //TODO: Make sure this is home screen instead of main activity
        Intent intent = new Intent(this, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, intent, PendingIntent.FLAG_ONE_SHOT);
        //set sound of notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Alarm 2")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*ID of notification*/, notifiBuilder.build());
    }
}

