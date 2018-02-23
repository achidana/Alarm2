package com.example.swalt.alarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.KeyStore;


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
        HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        apiClient.connect();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(apiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (data != null) {
                Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (cred != null) {
                    final String unformattedPhone = cred.getId(); //Put this in server
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
