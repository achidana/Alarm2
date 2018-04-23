package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by JohnRedmon on 4/11/18.
 */

public class FirebasePhoneVerify extends Activity{

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String userText;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private EditText number;
    private EditText code;
    private EditText name;
    private Button numberBtn;
    private Button codeBtn;
    private Button nameBtn;

    SharedPreferences preferencesFile;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firbase_verify_activity);

        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        setMCallback();

        number = findViewById(R.id.editPhoneNumber);
//After first        verify();

        code = findViewById(R.id.editCode);
        name = findViewById(R.id.editName);

        numberBtn = findViewById(R.id.verifyBtn);
        codeBtn = findViewById(R.id.verifyCodeBtn);
        nameBtn = findViewById(R.id.verifyNameBtn);

        nameBtn.setEnabled(true);
        name.setEnabled(true);

        number.setEnabled(false);
        numberBtn.setEnabled(false);

        codeBtn.setEnabled(false);
        code.setEnabled(false);


        mAuth = FirebaseAuth.getInstance();

        preferencesFile = getSharedPreferences(getString(R.string.preference_file_key) + ".prefs", Context.MODE_PRIVATE);
        editor = preferencesFile.edit();


        numberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });


        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyWithCode();
            }
        });

        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUsername();
            }
        });

    }



    public void setMCallback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("TAG", "onVerificationCompleted:" + credential);
                startApp();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);
                Toast toast = Toast.makeText(FirebasePhoneVerify.this, "Invalid Phone Number", Toast.LENGTH_LONG);
                toast.show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d("TAG", "invalid credentials");
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);
                codeBtn.setEnabled(true);
                code.setEnabled(true);
                numberBtn.setEnabled(false);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };
    }

    private void signInWithPhoneAuthCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            codeBtn.setEnabled(false);
                            nameBtn.setEnabled(true);
                            name.setEnabled(true);
                            startApp();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast toast = Toast.makeText(FirebasePhoneVerify.this, "Incorrect Verification Code", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }
                });
    }


    public void verify() {
        String plus = "+";
        String phoneNumber = number.getText().toString();
        phoneNumber = phoneNumber.replaceAll(" ", "");
        Globals globals = (Globals) getApplication();
        globals.userID = phoneNumber;
        if (!phoneNumber.startsWith(plus)) {
            phoneNumber = plus.concat(phoneNumber);
        }
        Log.d("TAG", phoneNumber);
        codeBtn.setEnabled(true);
        code.setEnabled(true);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    public void verifyWithCode() {
        //Change this from number to verification id
        signInWithPhoneAuthCode(mVerificationId, code.getText().toString());
    }

    public void createUsername() {
        String username = name.getText().toString();
        Globals globals = (Globals) getApplication();
        globals.userName = username;
        number.setEnabled(true);
        numberBtn.setEnabled(true);
    }

    public void startApp() {
        Globals globals = (Globals) getApplication();
        globals.firebaseToken = FirebaseInstanceId.getInstance().getToken();
        editor.putString("userID", globals.userID);
        editor.putString("userName", globals.userName);
        editor.putString("firebaseToken", globals.firebaseToken);
        editor.apply();
        Log.d("MainActivity", "BEFORE THREAD");
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Log.d("MainActivity", "ENTER THREAD");
                    Globals globals = (Globals) getApplication();
                    Token tok = new Token();
                    tok.token = globals.firebaseToken;
                    tok.userId = globals.userID;
                    Gson gson = new GsonBuilder().create();
                    StringBuilder result = new StringBuilder();
                    try {

                        URL url = new URL("http://45.56.125.90:5000/token");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        Log.d("MainActivity", "!!!!!!!!!!!!!");
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        String json = gson.toJson(tok);
                        Log.d("MainActivity", "%%%%%%%%%%%%%%%%");
                        wr.write(json);
                        wr.flush();
                        Log.d("MainActivity", "$$$$$$$$$$$$$$");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = "";
                        while((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        Log.d("MainActivity", "@@@@@@@@@@@@@@@@");
                        String res = result.toString();
                        conn.disconnect();
                        Log.d("MainActivity", "Response: " + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
