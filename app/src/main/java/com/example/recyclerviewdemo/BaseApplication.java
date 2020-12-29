package com.example.recyclerviewdemo;

import org.litepal.LitePalApplication;

public class BaseApplication extends LitePalApplication {
    private static BaseApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
