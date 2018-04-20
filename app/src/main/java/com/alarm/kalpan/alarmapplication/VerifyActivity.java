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
import android.widget.Toast;

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
public class VerifyActivity  {

    public boolean validateNumber(String phoneNumber) {
        String plus = "+";
        phoneNumber = phoneNumber.replaceAll(" ", "");
        //First checks to add + sign
        if (!phoneNumber.startsWith(plus)) {
            //Checks that all input is numeric
            if (phoneNumber.matches(".*[0-9].*")) {
                phoneNumber = plus.concat(phoneNumber);
                System.out.println("With plus: " + phoneNumber);
            }
            else {
                System.out.println("Nonnumeric " + phoneNumber);
                return false;
            }
        }
        //Number starts with +, check for all numeric value after +
        else {
            //Creates new string removing the plus
            String numberNoPlus = phoneNumber.replace(plus, "");
            numberNoPlus = numberNoPlus.replaceAll(" ", "");
            if (!numberNoPlus.matches(".*[0-9].*")) {
                System.out.println("No plus, nonnumeric: " + numberNoPlus);
                return false;
            }
        }
        //Number should be formatted +1(123)456-7890
        if (phoneNumber.length() != 12) {
            System.out.println("Bad length: " + phoneNumber);
            //Toast toast = Toast.makeText("Make sure you add 1 to the beginning of your ten digit number", this, Toast.LENGTH_LONG);
            //toast.show();
            return false;
        }
        System.out.println("Success: " + phoneNumber);
        return true;

    }

    public static void main(String args[]) {
        VerifyActivity va = new VerifyActivity();
        boolean test;
        test = va.validateNumber("11234567890");
        System.out.println(test);
        test = va.validateNumber("+11234567890");
        System.out.println(test);
        test = va.validateNumber("abcdefg");
        System.out.println(test);
        test = va.validateNumber("+abcdefg");
        System.out.println(test);
        test = va.validateNumber("123456789012345667");
        System.out.println(test);

    }
}
