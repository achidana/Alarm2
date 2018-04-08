package com.alarm.kalpan.alarmapplication;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;

/**
 * Created by JohnRedmon on 3/25/18.
 * Tests User Story 5
 * Group admin creates alarm->appears in list
 * Group admin created->custom class instantiated
 * Group alarm updated->notifies users' alarm
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddGroupAlarm {
    private String mStringToBeTyped;
    private String mNumberToBeTyped;

    private String mAdminName;
    private String mAdminNumber;
    private int mNumUsers;
    private ArrayList<User> mUsers;
    private GroupAdmin mAdmin;

    @Rule
    public ActivityTestRule<GroupAlarm> mActivityRule =
            new ActivityTestRule<GroupAlarm>(GroupAlarm.class);

    @Before
    public void initValidInputs() {
        //Tests for correct behavior with valid string/number
        mStringToBeTyped = "Jack Frost";
        mNumberToBeTyped = "8122175555";

        mAdminName = "John Redmon";
        mAdminNumber = "8128975585";
        mNumUsers = 3;
        mUsers = new ArrayList<>(mNumUsers);
        mAdmin = new GroupAdmin(mAdminName, mAdminNumber, mNumUsers, mUsers);
    }

    @Test
    //Checks that dialog box appears
    public void addUser() {
        onView(withId(R.id.addUser)).perform(click());
        onView(withText("Add User")).check(matches(isDisplayed()));
        //Do with Excel
    }

    @Test
    public void createAdmin() {
        GroupAdmin testAdmin = new GroupAdmin(mAdminName, mAdminNumber, mNumUsers, mUsers);
        assert(testAdmin.equals(mAdmin));
    }
}
