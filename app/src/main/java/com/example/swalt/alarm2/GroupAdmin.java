package com.example.swalt.alarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/3/18.
 * GroupAdmin class, contains information relevant to create of group alarm
 */

public class GroupAdmin extends Activity {
    private String name;
    private String phoneNumber;
    private int numUsers;
    private ArrayList<User> userNumbers;
    //Alarm variable

    public GroupAdmin (String name, String phoneNumber, int numUsers, ArrayList<User> userNumbers) {
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setNumUsers(numUsers);
        this.setUserNumbers(userNumbers);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public ArrayList<User> getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(ArrayList<User> userNumbers) {
        this.userNumbers = userNumbers;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }
}
