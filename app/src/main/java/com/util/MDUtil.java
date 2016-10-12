package com.util;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import net.tcp.tcptool.R;

public class MDUtil {

    /**
     * 只需要配置title和message,没有点击事件回调
     * @param context
     * @param title
     * @param message
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, String title, String message){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(R.string.delete_food_dialog_agree);
        builder.negativeText(R.string.delete_food_dialog_disagree);
        //builder.show();就可以显示了
        return builder;
    }
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, int message){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(R.string.delete_food_dialog_agree);
        builder.negativeText(R.string.delete_food_dialog_disagree);
        //builder.show();就可以显示了
        return builder;
    }


    /**
     * 可以配置title和message，onPositive的callback
     * @param context
     * @param title
     * @param message
     * @param callback
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, String title, String message, MaterialDialog.SingleButtonCallback callback){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(R.string.delete_food_dialog_agree);
        builder.negativeText(R.string.delete_food_dialog_disagree);
        builder.onPositive(callback);
        //builder.show();就可以显示了
        return builder;
    }
    /**
     * 可以配置title/message/positiveText/negativeText，onPositive的callback
     * @param context
     * @param title
     * @param message
     * @param callback
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, int message, int positiveText, int negativeText, MaterialDialog.SingleButtonCallback callback){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.onPositive(callback);
        //builder.show();就可以显示了
        return builder;
    }

    /**
     * 可以配置title/message，onPositive的callback
     * positiveText和negativeText为确认和取消
     * @param context
     * @param title
     * @param message
     * @param callback
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, int message,
                                                           MaterialDialog.SingleButtonCallback callback){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(R.string.delete_food_dialog_agree);
        builder.negativeText(R.string.delete_food_dialog_disagree);
        builder.onPositive(callback);
        //builder.show();就可以显示了
        return builder;
    }

    /**
     * 可以配置Text: title/message/onPositive/onNegative的callback，
     * Callback: onPositive/onNegative
     *
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param negativeText
     * @param callback
     * @param callbackNegative
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, int message,
                                                           int positiveText, int negativeText,
                                                           MaterialDialog.SingleButtonCallback callback,
                                                           MaterialDialog.SingleButtonCallback callbackNegative){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        builder.onPositive(callback);
        builder.onNegative(callbackNegative);
        //builder.show();就可以显示了
        return builder;
    }
    /**
     * 可以配置所有的文字，并且是以resource的方式传参
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param negativeText
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, int message,
                                                           int positiveText, int negativeText){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        //builder.show();就可以显示了
        return builder;
    }

    /**
     * 可以配置所有的文字，并且是以String的方式传参
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param negativeText
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, String title, String message,
                                                           String positiveText, String negativeText){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.content(message);
        builder.positiveText(positiveText);
        builder.negativeText(negativeText);
        //builder.show();就可以显示了
        return builder;
    }

    /**
     * 一个缓冲弹框，当message为null，message会默认为waiting
     * @param context
     * @param message
     * @return
     */
    public static MaterialDialog.Builder getWaitingDialog(Context context, String message){
        if(message==null){
            message = context.getString(R.string.win_wait);
        }
        return new MaterialDialog.Builder(context)
                .content(message)
                .progress(true, 0);
    }

    /**
     * 可以设置title和自定义View,onPositive的callback
     * @param context
     * @param title
     * @param view
     * @param callback
     * @return
     */
    public static MaterialDialog.Builder getMaterialDialog(Context context, int title, View view, MaterialDialog.SingleButtonCallback callback){

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.customView(view ,false);
        builder.positiveText(R.string.delete_food_dialog_agree);
        builder.negativeText(R.string.delete_food_dialog_disagree);
        builder.onPositive(callback);
        //builder.show();就可以显示了
        return builder;
    }
}
