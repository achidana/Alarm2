package com.example.swalt.alarm2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 3/3/18.
 * User of group alarm, lacks group admin priveleges
 */

public class User{
    private String name;
    private String phoneNumber;
    private boolean isAdmin;

    public User() {
        super();
    }

    public User(String name, String phoneNumber, boolean isAdmin) {
        super();
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setAdmin(isAdmin);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPhoneNumber();
    }
}
