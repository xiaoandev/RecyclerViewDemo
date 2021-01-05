package com.example.recyclerviewdemo.utils;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

public class ActivityCollectorUtil {
    public static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    /**
     * onCreate()时添加
     * @param activity
     */
    public static void addActivity(Activity activity){
        //判断集合中是否已经添加，添加过的则不再添加
        if (!mActivityList.contains(activity)){
            mActivityList.add(activity);
        }
        Log.d("ActivityCollectorUtil", "addActivity()");
    }

    /**
     * onDestroy()时删除
     * @param activity
     */
    public static void removeActivity(Activity activity){
        mActivityList.remove(activity);
        Log.d("ActivityCollectorUtil", "removeActivity()");
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAllActivity(){
        Log.d("ActivityCollectorUtil", "finishAllActivity()");
        for (Activity activity : mActivityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
