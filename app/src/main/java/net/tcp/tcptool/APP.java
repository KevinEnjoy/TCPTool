package net.tcp.tcptool;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.KeyContent;
import com.util.SharePreferencesUserHelper;

import java.util.ArrayList;
import java.util.List;


public class APP extends Application {

    public static Context context;
    public static Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        SharePreferencesUserHelper.getSharePreferences(context);
    }


    public static List<String[]> getCmdData(){

        List<String[]>mItems;
        String cmdData = SharePreferencesUserHelper.getStringValue(context, KeyContent.CMD_DATA);
        if(TextUtils.isEmpty(cmdData)){
            mItems = new ArrayList<>();
        }else{
            mItems = gson.fromJson(cmdData,new TypeToken<List<String[]>>(){}.getType());
        }
        return mItems;
    }
}
