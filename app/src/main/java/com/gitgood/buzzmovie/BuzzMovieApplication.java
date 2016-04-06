package com.gitgood.buzzmovie;

import android.app.Application;
import android.content.Context;

/**
 * Created by Albert on 3/2/2016.
 */
public class BuzzMovieApplication extends Application {
    private static BuzzMovieApplication instance;
    private static Context appContext;

    @Override
    public final void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
    }

    public final void setAppContext(Context context) {
        appContext = context;
    }
    public static Context getAppContext () {
        return appContext;
    }
    public static BuzzMovieApplication getInstance () {
        return instance;
    }


}
