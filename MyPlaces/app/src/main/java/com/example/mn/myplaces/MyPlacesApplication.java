package com.example.mn.myplaces;

import android.app.Application;
import android.content.Context;

/**
 * Created by Milan on 12.4.2016..
 */
public class MyPlacesApplication extends Application {
    private static MyPlacesApplication instance;

    public MyPlacesApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
