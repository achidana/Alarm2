package com.example.swalt.alarm2;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



/**
 * Created by JohnRedmon on 3/21/18.
 * Tests input handling in New Group Alarm
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewGroupAlarmTest {
    private String mStringToBeTyped;
    private String mNumberToBeTyped;

    @Rule
    public ActivityTestRule<NewGroupAlarm> mActivityRule =
            new ActivityTestRule<NewGroupAlarm>(NewGroupAlarm.class);

    @Before
    public void initValidInputs() {
        //Tests for correct behavior with valid string/number
        mStringToBeTyped = "John Redmon";
        mNumberToBeTyped = "8122170571";
    }

    //Double displayName maybe? Figure out what's up with loading two screens
    @Test
    public void setText() {
        onView(withId(R.id.editTextUserName)).perform(typeText(mStringToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.editTextPhoneNumber)).perform(typeText(mNumberToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.OK)).perform(click());

        onView(withId(R.id.displayName)).check(matches(withText(mStringToBeTyped)));
        onView(withId(R.id.phoneNumber)).check(matches(withText(mNumberToBeTyped)));
    }
}