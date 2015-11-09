package com.witkey.coder.zhdaily.db;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.witkey.coder.zhdaily.DailyApp;
import com.witkey.coder.zhdaily.R;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.NavigableSet;

/**
 * 使用 MapDB 作为数据库
 */
public class CircleDB extends Service {
    private static BTreeMap<String, byte[]> map;
    private static DB db;

    static void init(Context context) {
        String dbName = String.format("%s%s", context.getFilesDir(), context.getString(R.string.db_daily));

        db = DBMaker
                .fileDB(new File(dbName))
                .closeOnJvmShutdown()
                .asyncWriteEnable()
                .encryptionEnable("DailyDB")
                .make();
        map = db.treeMapCreate(context.getString(R.string.db_daily_map))
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.BYTE_ARRAY)
                .makeOrGet();
    }

    public static void write(String key, Object value) {
        try {
            map.put(key, serialize(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.compact();
        db.commit();
    }

    public static Object read(String key) {
        try {
            return deserialize(map.get(key));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void delete(String key) {
        map.remove(key);
        db.compact();
        db.commit();
    }

    // 获取Key迭代器
    public static Iterator<String> getKeyIterator() {
        NavigableSet<String> set = map.descendingKeySet();
        return set.iterator();
    }

    static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if(data == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 确保数据库在app关闭时也同时关闭
        db.close();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init(DailyApp.getAppContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
