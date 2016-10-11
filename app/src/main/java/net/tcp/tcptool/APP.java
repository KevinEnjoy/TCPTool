package net.tcp.tcptool;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.util.KeyContent;
import com.util.SharePreferencesUserHelper;


public class APP extends Application {

    public static Context context;
    private static String token;
    private static String userUUID;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        SharePreferencesUserHelper.getSharePreferences(context);
    }


    public static String getUserUUID(){
        if(TextUtils.isEmpty(userUUID)){
            userUUID = SharePreferencesUserHelper.getStringValue(context, KeyContent.USERUUID);
        }
        return userUUID;
    }


    public static void clearMemory(){
        token = null;
        userUUID = null;
    }
}
