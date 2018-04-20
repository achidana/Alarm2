package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import android.support.test.espresso.contrib.PickerActions;

import java.util.ArrayList;


/**
 * Created by JohnRedmon on 4/13/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddAlarmTest{

    private String mAlarmName;
    private int hour;
    private int minutes;

    @Rule
    public ActivityTestRule<Add_alarm> mActivityRule =
            new ActivityTestRule<Add_alarm>(Add_alarm.class);

    @Before
    public void initialize() {
        mAlarmName = "Gym";
        hour = 2;
        minutes = 45;
    }

    @Test
    public void addAlarm() {
        onView(withId(R.id.name)).perform(replaceText(mAlarmName));
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        onView(withId(R.id.save)).perform(click());

        //EXCEL spreadsheet to take results
        //EXCEL spreadsheet to show images of alarms getting added

        //onView(withId(R.id.listview)).check(matches(withText(hour + ":" + minutes)));

    }

    @Test
    public void addSecAlarm() {
        mAlarmName = "Work";
        hour = 3;
        minutes = 24;

        onView(withId(R.id.name)).perform(replaceText(mAlarmName));
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        onView(withId(R.id.save)).perform(click());

    }

    /*
    //Checks for dialog box to appear
    @Test
    public void customRingtone() {
        onView(withId(R.id.button2)).perform(click());
        //onView(withText("Alarm Sounds")).check(matches(isDisplayed()));
    }
    */
}
