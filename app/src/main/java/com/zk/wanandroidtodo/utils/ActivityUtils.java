package com.zk.wanandroidtodo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zk.wanandroidtodo.R;

/**
 * @description: Activity跳转工具类
 * @author: zhukai
 * @date: 2018/2/27 13:24
 */
public class ActivityUtils {

    /**
     * 跳转新的Activity
     *
     * @param context
     * @param intent
     */
    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 跳转新的Activity
     *
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 关闭Activity
     *
     * @param context
     */
    public static void finishActivity(Context context) {
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
