package com.alarm.kalpan.alarmapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.endsWith;
import android.widget.ListView;
import android.widget.AdapterView;
import com.robotium.solo.Solo;

import android.support.test.espresso.contrib.PickerActions;

import java.util.ArrayList;

/**
 * Created by JohnRedmon on 4/18/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextArchiveTest extends ActivityUnitTestCase<TextArchive> {

    String mStringToBeTyped;

    public TextArchiveTest() {
        super (TextArchive.class);
    }

    @Rule
    public ActivityTestRule<TextArchive> mActivityRule =
            new ActivityTestRule<TextArchive>(TextArchive.class);

    @Before
    public void initialize() {
        mStringToBeTyped = "Wake up you fool";
    }

    @Test
    public void TextArchive() {
        onView(withId(R.id.addBtn)).perform(click());
        onView(withText("New Message")).check(matches(isDisplayed()));
    }

    //@TEST
    //Manually show defaults-Add print statements?

    @Test
    public void TextArchiveReplaceText() {
        onView(withId(R.id.addBtn)).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(replaceText(mStringToBeTyped));
        onView(withText("New Message")).perform(click());
        onView(withText(mStringToBeTyped)).check(matches(withText(mStringToBeTyped)));
    }

}

