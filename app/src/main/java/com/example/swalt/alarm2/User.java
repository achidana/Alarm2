package com.example.swalt.alarm2;

/**
 * Created by JohnRedmon on 3/3/18.
 * User of group alarm, lacks group admin priveleges
 */

public class User {
    private String name;
    private String phoneNumber;
    private boolean isAdmin;

    public User(String name, String phoneNumber, boolean isAdmin) {
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
}
