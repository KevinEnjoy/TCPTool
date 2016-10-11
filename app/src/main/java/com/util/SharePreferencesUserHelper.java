package com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import net.tcp.tcptool.APP;


/**
 * Created by Administrator on 2016/6/1.
 * Key在KeyContent定义
 */
public class SharePreferencesUserHelper {

    private static final String TAG = "SharePFUserHelper";
    private static final String KEY_APP_USER = APP.context.getPackageName();


    private static SharedPreferences sp;

    public static SharedPreferences getSharePreferences(Context context){

        if(sp==null){
            sp = context.getSharedPreferences(KEY_APP_USER, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static String getStringValue(Context context, String key){

        SharedPreferences mShared = getSharePreferences(context);
        return mShared.getString(key,null);
    }

    public static boolean setStringValue(Context context, String key , String value){
        SharedPreferences mShared = getSharePreferences(context);
        mShared.edit().putString(key,value).apply();
        Log.i(TAG, "Key:" + key + "  setStringValue: " + value);
        return true;
    }

    public static boolean getBooleanValue(Context context, String key, boolean defaultValue){

        SharedPreferences mShared = getSharePreferences(context);
        return mShared.getBoolean(key,defaultValue);
    }

    public static boolean setBooleanValue(Context context, String key , boolean value){
        SharedPreferences mShared = getSharePreferences(context);
        mShared.edit().putBoolean(key,value).apply();
        Log.i(TAG, "Key:" + key + "  setValue: " + value);
        return true;
    }

    public static int getIntValue(Context context, String key){

        SharedPreferences mShared = getSharePreferences(context);
        return mShared.getInt(key,-1);
    }
    public static int getIntValue(Context context, String key, int defaultValue){

        SharedPreferences mShared = getSharePreferences(context);
        return mShared.getInt(key,defaultValue);
    }

    public static boolean setIntValue(Context context, String key , int value){
        SharedPreferences mShared = getSharePreferences(context);
        mShared.edit().putInt(key,value).apply();
        Log.i(TAG, "Key:" + key + "  setValue: " + value);
        return true;
    }

    public static boolean clear(Context context){
        SharedPreferences mShared = getSharePreferences(context);
        mShared.edit().clear().apply();
        return true;
    }
}
