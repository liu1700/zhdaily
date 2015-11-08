package com.witkey.coder.zhdaily;

import android.app.Application;
import android.content.Context;

import com.witkey.coder.zhdaily.db.CircleDB;

/**
 * 存储App上下文
 *
 * 为需要在整个周期一直存在的服务提供上下文，例如Networking，DB
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
