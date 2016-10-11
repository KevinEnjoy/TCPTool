package com.util;

import android.os.Handler;

/**
 * Created by Administrator on 2016/6/14.
 */
public class HandlerHelper {

    private static Handler handler;
    synchronized public static Handler getInstance() {

        if(handler==null){
            handler = new Handler();
        }
        return handler;
    }
}
