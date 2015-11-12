package com.witkey.coder.zhdaily;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 存储App上下文
 *
 * 为需要在整个周期一直存在的服务提供上下文，例如Networking，DB
 */
public class DailyApp extends Application {

    private static Context appContext;
    private static ArrayList<Integer> storyIdList;

    @Override
    public void onCreate() {
        super.onCreate();
        this.setAppContext(getApplicationContext());
        storyIdList = new ArrayList<>();
    }

    public static Context getAppContext() {
        return appContext;
    }
    private void setAppContext(Context a) {
        appContext = a;
    }

    public static ArrayList getStoryIdList() {
        return storyIdList;
    }

    public static void storeIdToList(int id) {
        storyIdList.add(id);
    }

    public static void storeAllIdsToList(ArrayList<Integer> ids) {
        storyIdList.addAll(ids);
    }

    public static void addAllIdAt(int pos, ArrayList<Integer> ids) {
        storyIdList.addAll(pos, ids);
    }

    public static void addStoryIdAt(int pos, int id) {
        storyIdList.add(pos, id);
    }
}
