package com.alarm.kalpan.alarmapplication;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

/**
 * Created by JohnRedmon on 3/26/18.
 * Tests User Story 6
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListViewUpdateTest {
    private String mStringToBeTyped;
    private String mUser;
    private String mUser2;

    @Rule
    public ActivityTestRule<GroupAlarmList> mActivityRule =
                new ActivityTestRule<GroupAlarmList>(GroupAlarmList.class);

    @Before
    public void initValidInputs() {
        //Tests for correct behavior with valid string/number
        mStringToBeTyped = "Bus Leaves";
        mUser ="Mark Russell";
        mUser2 = "Jerry Kincaid";
    }

    @Test
    //Checks that dialog box appears
    public void checkBox() {
        onView(withId(R.id.addGroup)).perform(click());
        onView(withText("New Group Name")).check(matches(isDisplayed()));
    }

    @Test
    public void setText() {
        onView(withId(R.id.addGroup)).perform(click());
        //onView(withText("New Group Name")).perform(typeText(mStringToBeTyped));

        onView(withClassName(endsWith("EditText"))).perform(replaceText(mStringToBeTyped));

        onView(withText("New Group Name")).perform(click());
        onView(withText(mStringToBeTyped)).check(matches(withText(mStringToBeTyped)));
    }

    @Test
    public void addUserUI() {
     //   Globals globals =
    }
}
