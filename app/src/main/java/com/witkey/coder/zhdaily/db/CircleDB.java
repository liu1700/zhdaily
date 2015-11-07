package com.witkey.coder.zhdaily.db;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.witkey.coder.zhdaily.R;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * 使用 MapDB 作为数据库
 */
public class CircleDB extends Service {
    private static ConcurrentNavigableMap<Integer,String> map;
    private static DB db;

    public static void init(Context context) {
        String dbName = String.format("%s%s", context.getFilesDir(), context.getString(R.string.db_daily));

        db = DBMaker
                .fileDB(new File(dbName))
                .closeOnJvmShutdown()
                .encryptionEnable("DailyDB")
                .make();
        map = db.treeMap(context.getString(R.string.db_daily_map));
    }

    // For persistent db
    public static void write(int key, String value) {
        map.put(key, value);
        db.compact();
        db.commit();
    }

    public static String read(int key) {
        return map.get(key);
    }

    public static void delete(int key) {
        map.remove(key);
        db.compact();
        db.commit();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // 确保数据库在app关闭时也同时关闭
        db.close();
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
