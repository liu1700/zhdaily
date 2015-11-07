package com.witkey.coder.zhdaily;

import android.app.Application;
import android.content.Context;

import com.witkey.coder.zhdaily.db.CircleDB;

/**
 * 存储上下文
 *
 */
public class DailyApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.setAppContext(getApplicationContext());
        CircleDB.init(getApplicationContext());
    }

    public static Context getAppContext() {
        return appContext;
    }
    public void setAppContext(Context a) {
        appContext = a;
    }

}
