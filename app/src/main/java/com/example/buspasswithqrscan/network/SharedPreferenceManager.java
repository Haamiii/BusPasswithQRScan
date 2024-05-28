package com.example.buspasswithqrscan.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

final public class SharedPreferenceManager {

    private static final String KEY_USER_ID = "userId";
    private static SharedPreferenceManager sharedPreferenceManager = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static void setSingletonInstance(Context context) {
        synchronized (SharedPreferenceManager.class) {
            if (sharedPreferenceManager == null)
                sharedPreferenceManager = new SharedPreferenceManager(context);
            else
                Log.d("Shared","SharedPreferenceManager instance already exists.");
        }
    }

    private SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences("industrial_watch", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static SharedPreferenceManager getInstance() {
        return sharedPreferenceManager;
    }

    public void clearPreferences() {
        editor.clear();
        editor.commit();
    }

    public String read(String valueKey, String valueDefault) {
        return sharedPreferences.getString(valueKey, valueDefault);
    }

    public void save(String valueKey, String value) {
        editor.putString(valueKey, value);
        editor.commit();
    }

    public int read(String valueKey, int valueDefault) {
        return sharedPreferences.getInt(valueKey, valueDefault);
    }

    public void save(String valueKey, int value) {
        editor.putInt(valueKey, value);
        editor.commit();
    }

    public boolean read(String valueKey, boolean valueDefault) {
        return sharedPreferences.getBoolean(valueKey, valueDefault);
    }

    public void save(String valueKey, boolean value) {
        editor.putBoolean(valueKey, value);
        editor.commit();
    }

    public long read(String valueKey, long valueDefault) {
        return sharedPreferences.getLong(valueKey, valueDefault);
    }

    public void save(String valueKey, long value) {
        editor.putLong(valueKey, value);
        editor.commit();
    }

    public float read(String valueKey, float valueDefault) {
        return sharedPreferences.getFloat(valueKey, valueDefault);
    }

    public void save(String valueKey, float value) {
        editor.putFloat(valueKey, value);
        editor.commit();
    }
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveUserId(int userId) {
        save(KEY_USER_ID, userId);
    }

    public int getUserId() {
        return getInt(KEY_USER_ID, -1);
    }
    public Object get(String key) {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        return allEntries.get(key);
    }

}
