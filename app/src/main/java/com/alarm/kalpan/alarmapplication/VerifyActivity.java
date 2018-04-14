package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.security.KeyStore;
import java.util.concurrent.TimeUnit;


/**
 * Created by JohnRedmon on 2/11/18.
 */

//I think this can't connect because there's no attached phone number
public class VerifyActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final int RESOLVE_HINT = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        /* To be done in homescreen
        SharedPreferences.Editor editor = getSharedPreferences("Verify bool", MODE_PRIVATE).edit();
        editor.putBoolean("Verified", false);
        editor.apply();
        */
        requestHint();
    }

    private void requestHint() {
        Log.d("TAG", "In request");
        HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        apiClient.connect();
        Log.d("TAG", "Connected");
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(apiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e){
            Log.d("TAG", "Failed to send");
            e.printStackTrace();
        }
        Log.d("TAG", "Finished request");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "result");
        Integer rc = requestCode;
        Integer rc2 = resultCode;
        Log.d("TAG", rc.toString());
        Log.d("TAG", rc2.toString());
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
                    final String unformattedPhone = cred.getId(); //Put this in server
                    Log.d("TAG", unformattedPhone);
                    sendToServer(unformattedPhone);
                    /*
                    if(PhoneNumberUtils.isGlobalPhoneNumber(unformattedPhone)) {
                        //Phone number is real, allow entry
                        //Built in variable will be moved to homescreen,
                        //Left here for now
                        SharedPreferences.Editor editor = getSharedPreferences("Verify bool", MODE_PRIVATE).edit();
                        editor.putBoolean("Verified", true);
                        editor.apply();
                        //switch intent
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Invalid Phone Number");
                        //Do not switch intent, lock at screen
                    }
                    */
                }
            }

    }

    public void sendToServer(String number) {
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // successfully started an SMS Retriever for one SMS message
                //Format number?
                //Send phone number to server
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Failed
            }
        });
    }

    public class MySMSBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch(status.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        break;
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Connect Success", "Success");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Connect Suspend", "Suspend");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Connect Fail", "Fail");
    }
}
