package com.example.swalt.alarm2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by JohnRedmon on 2/11/18.
 */

public class VerifyActivity extends Activity {
    public static final int RESOLVE_HINT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        requestHint();
    }

    private void requestHint() {
        HintRequest hintRequest = new HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build();
        GoogleApiClient apiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
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
                    }
                    else {
                        //Different screen that blocks
                    }
                }
            }
        }
    }
}
