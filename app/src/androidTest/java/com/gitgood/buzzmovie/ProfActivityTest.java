package com.gitgood.buzzmovie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.Espresso;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.concurrent.TimeUnit;

/**
 * Created by albert on 4/6/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfActivityTest extends ActivityInstrumentationTestCase2{
    private final String name = "Testy McTesterson";
    private final String major = "Bachelors of Testing";
    private final String interests = "Testing Things";
    private String mNameString;
    private String mMajorString;
    private String mInterestsString;
    private String previousUser;
    SharedPreferences currentUserSharedPreferences;
    SharedPreferences userInfoSharedPreferences;
    Activity mActivity;

    public ProfActivityTest() {
        super(ProfActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
        userInfoSharedPreferences = mActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        currentUserSharedPreferences = mActivity.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        previousUser = currentUserSharedPreferences.getString("username", null);
        SharedPreferences.Editor currentUserEditor = currentUserSharedPreferences.edit();
        SharedPreferences.Editor userInfoEditor = userInfoSharedPreferences.edit();
        currentUserEditor.putString("username", "test");
        currentUserEditor.commit();
        userInfoEditor.putString("test_name", name);
        userInfoEditor.putString("test_major", major);
        userInfoEditor.putString("test_interests", interests);
        userInfoEditor.commit();
        mNameString = "Alice Bobby";
        mMajorString = "Bobbing for carrots";
        mInterestsString = "Car dodging. Drying eggs.";
    }

    @After
    public void tearDown() {
        SharedPreferences.Editor currentUserEditor = currentUserSharedPreferences.edit();
        currentUserEditor.putString("username", previousUser);
        currentUserEditor.commit();
    }

    @Test
    public void cancelButtonTest() {
        Espresso.onView(ViewMatchers.withId(R.id.bbCancel)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        populateFields();
        Espresso.onView(ViewMatchers.withId(R.id.bbCancel)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        assertEquals(name, userInfoSharedPreferences.getString("test_name", null));
        assertEquals(major, userInfoSharedPreferences.getString("test_major", null));
        assertEquals(interests, userInfoSharedPreferences.getString("test_interests", null));
        sleep(2);
    }

    @Test
    public void updateButtonTest() {
        populateFields();
        Espresso.onView(ViewMatchers.withId(R.id.bbUpdate)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        assertEquals(mNameString, userInfoSharedPreferences.getString("test_name", null));
        assertEquals(mMajorString, userInfoSharedPreferences.getString("test_major", null));
        assertEquals(mInterestsString, userInfoSharedPreferences.getString("test_interests", null));
        sleep(2);
    }

//    @Test
//    public void backButtonTest() {
//        populateFields();
//        Espresso.pressBack();
//        Espresso.onView(ViewMatchers.withId(R.id.LoginButton)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//        assertNoChange();
//    }

    private void populateFields () {
        Espresso.onView(ViewMatchers.withId(R.id.eName)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.eName)).perform(ViewActions.replaceText(mNameString));
        sleep(1);
        Espresso.onView(ViewMatchers.withId(R.id.eMajor)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.eMajor)).perform(ViewActions.replaceText(mMajorString));
        sleep(1);
        Espresso.onView(ViewMatchers.withId(R.id.eInterest)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.eInterest)).perform(ViewActions.replaceText(mInterestsString));
        sleep(1);
    }
    private void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {

        }
    }
}