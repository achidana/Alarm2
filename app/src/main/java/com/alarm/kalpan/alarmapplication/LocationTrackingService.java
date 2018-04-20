package com.alarm.kalpan.alarmapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class LocationTrackingService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
