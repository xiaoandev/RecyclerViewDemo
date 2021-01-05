package com.example.recyclerviewdemo;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class BaseApplication extends Application {

    //全局Context
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
