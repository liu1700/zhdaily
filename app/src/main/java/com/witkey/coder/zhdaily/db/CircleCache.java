package com.witkey.coder.zhdaily.db;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 使用MapDB HTreeMap 作为缓存
 */
public class CircleCache extends Service {

    private static final double cacheSizeInGB = 0.05;
    private static HTreeMap<Integer, byte[]> cache;
    private static DB cacheDB;

    public static void init(Context context) {
        String cacheName = String.format("%s%s", context.getFilesDir(), "cache");

        cacheDB = DBMaker
                .memoryDirectDB()
                .transactionDisable()
                .compressionEnable()
                .cacheLRUEnable()
                .cacheExecutorEnable()
                .make();

        cache = cacheDB
                .hashMapCreate(cacheName)
                .expireStoreSize(cacheSizeInGB)
                .counterEnable()
                .keySerializer(Serializer.INTEGER)
                .valueSerializer(Serializer.BYTE_ARRAY)
                .makeOrGet();
    }

    // For in-memory cache db
    public static void put(int key, Object object) {
        try {
            cache.put(key, serialize(object));
            cacheDB.compact();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object get(int key) {
        try {
            return deserialize(cache.get(key));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAsString(int key) {
        return (String)get(key);
    }

    public static void clear(int key) {
        cache.remove(key);
        cacheDB.compact();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        if(data == null) return null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // 确保数据库在app关闭时同时关闭
        cacheDB.close();
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

