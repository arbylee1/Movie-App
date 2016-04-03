package com.gitgood.buzzmovie;

/**
 * Created by Orange Blossom on 3/2/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProviderInstrumentationTest
        extends ActivityInstrumentationTestCase2<RegistrationActivity>  {

    private String ret;
    Activity mActivity;
    public ProviderInstrumentationTest() {
        super(RegistrationActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    @Test
    public void randomObjectTest() throws Exception {
        Provider provider = new Provider(InstrumentationRegistry.getContext());
        provider.getRandomString(new Callback() {
            @Override
            public void onSuccess(String result) {
                setRet(result);
            }
            @Override
            public void onFailure(String result) {
            }
        });
    }

    public void setRet(String ret) {
        this.ret = ret;
    }
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
